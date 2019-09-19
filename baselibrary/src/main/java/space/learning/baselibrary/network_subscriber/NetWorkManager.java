package space.learning.baselibrary.network_subscriber;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * 在init处动态注册广播,广播寻找项目中使用到的annotation，通过反射通知订阅的方法
 */

/**
 * @author space
 * @date 2019/9/10
 */
public class NetWorkManager {

    private Application application;

    private NetWorkReceiver receiver;

    public static NetWorkManager getInstance() {
        return Holder.manager;
    }

    private static class Holder {
        static NetWorkManager manager = new NetWorkManager();
    }

    /**
     * 初始化方法
     * <p>
     * 动态注册广播，可做版本兼容入口
     *
     * @param application
     */
    public void init(Application application) {
        this.application = application;

        receiver = new NetWorkReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        application.registerReceiver(receiver, intentFilter);
    }

    /**
     * 注册网络监听广播
     */
    public void register(Object object) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            JobScheduler jobScheduler = (JobScheduler) application.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//            int JobId = 999;
//            JobInfo jobInfo = new JobInfo.Builder(JobId, new ComponentName(application, ConnectivityJob.class))
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                    .setRequiresCharging(true)
//                    .build();
//            jobScheduler.schedule(jobInfo);
//
//        } else {

            receiver.register(object);
//        }
    }

    public Application getApplication() {
        return application;
    }

}
