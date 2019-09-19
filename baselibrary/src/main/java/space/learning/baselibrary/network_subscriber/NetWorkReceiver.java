package space.learning.baselibrary.network_subscriber;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import space.learning.baselibrary.network_subscriber.annotation.NetWorkSubscriber;
import space.learning.baselibrary.network_subscriber.bean.MethodManageInfo;
import space.learning.baselibrary.network_subscriber.enums.NetType;
import space.learning.baselibrary.network_subscriber.utils.NetUtils;

/**
 * @author space
 * @date 2019/9/10
 * <p>
 * 监听网络变化的通知广播
 */
public class NetWorkReceiver extends BroadcastReceiver {

    /**
     * key：activity/fragment
     * value：该activity/fragment下的所有订阅方法
     */
    private HashMap<Object, List<MethodManageInfo>> methodCache;

    public NetWorkReceiver() {
        //初始化订阅方法的缓存缓存
        this.methodCache = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //监听到了网络的改变
        if (intent.getAction() != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            //获取当前的网络状态
            NetType netType = NetUtils.getNetType();

            //通知订阅的方法，网络状态改变
            postNetState(netType);
        }
    }

    /**
     * 通知所有的订阅方法
     *
     * @param netType
     */
    private void postNetState(NetType netType) {

        if (methodCache.isEmpty()) {
            return;
        }

        Set<Object> objects = methodCache.keySet();

        for (Object object : objects) {

            List<MethodManageInfo> methodManageInfoList = methodCache.get(object);

            for (MethodManageInfo methodManageInfo : methodManageInfoList) {

                if (methodManageInfo.getParameterType().isAssignableFrom(NetType.class)) {

                    invoke(methodManageInfo, object, netType);

                }

            }

        }
    }

    /**
     * 反射调用订阅方法
     *
     * @param methodManageInfo
     * @param object
     * @param netType
     */
    private void invoke(MethodManageInfo methodManageInfo, Object object, NetType netType) {

        try {
            methodManageInfo.getMethod().invoke(object, netType);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册需要监听的页面
     *
     * @param object activity 或 fragment
     */
    public void register(Object object) {

        //缓存所有的订阅方法
        cacheMethods(object);

    }

    /**
     * 把订阅方法添加到缓存
     *
     * @param object
     */
    private void cacheMethods(Object object) {

        if (methodCache.containsKey(object)) {
            methodCache.get(object).addAll(parseMethodSubscriber(object));
        } else {
            methodCache.put(object, parseMethodSubscriber(object));
        }
    }


    /**
     * 从activity/fragment中寻找所有订阅的方法
     *
     * @param object
     */
    private ArrayList<MethodManageInfo> parseMethodSubscriber(Object object) {
        Method[] methods = object.getClass().getMethods();

        ArrayList<MethodManageInfo> list = new ArrayList<>();

        for (Method method : methods) {
            NetWorkSubscriber annotation = method.getAnnotation(NetWorkSubscriber.class);

            if (annotation == null) {
                continue;
            }

            if (method.getAnnotations().length != 1) {
                throw new RuntimeException("订阅方法必须有一个NetType的参数！！！");
            }

            //需要记录method传入的参数，annotation的value

            NetType annoValue = annotation.netType();
            Class<?> parameterType = method.getParameterTypes()[0];

            list.add(new MethodManageInfo(parameterType, method, annoValue));
        }

        return list;
    }
}
