package space.learning.baselibrary.network_subscriber.bean;

import java.lang.reflect.Method;

import space.learning.baselibrary.network_subscriber.enums.NetType;

/**
 * 记录方法的参数
 */
public class MethodManageInfo {

    /**
     * 方法参数类型。
     */
    private Class<?> parameterType;

    /**
     * 订阅的方法。
     */
    private Method method;

    /**
     * annotation传入的值。
     */
    private NetType annoNetTypeValue;

    public MethodManageInfo(Class<?> parameterType, Method method, NetType annoNetTypeValue) {
        this.parameterType = parameterType;
        this.method = method;
        this.annoNetTypeValue = annoNetTypeValue;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public NetType getAnnoNetTypeValue() {
        return annoNetTypeValue;
    }

    public void setAnnoNetTypeValue(NetType annoNetTypeValue) {
        this.annoNetTypeValue = annoNetTypeValue;
    }
}
