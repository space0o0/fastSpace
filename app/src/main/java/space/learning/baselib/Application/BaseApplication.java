package space.learning.baselib.Application;


import android.app.Application;
import android.content.Context;

import space.learning.baselibrary.intentTask.IntentTaskManager;
import space.learning.baselibrary.network_subscriber.NetWorkManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NetWorkManager.getInstance().init(this);

        IntentTaskManager.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);


    }
}
