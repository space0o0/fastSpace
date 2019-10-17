package space.learning.baselibrary;

import android.app.Application;

public class MyApplication {

    private Application app;

    static class Holder {
        static MyApplication instance = new MyApplication();
    }

    public static void init(Application application) {
        Holder.instance.app = application;
    }

    public static Application getInstance() {
        return Holder.instance.app;
    }

}
