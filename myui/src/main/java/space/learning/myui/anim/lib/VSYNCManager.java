package space.learning.myui.anim.lib;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class VSYNCManager {

    private List<VSYNCCallBack> callBacks = new ArrayList<>();

    private static VSYNCManager instance = new VSYNCManager();

    private Thread mThread;

    static VSYNCManager getInstance() {
        return instance;
    }

    private VSYNCManager() {
        if (mThread == null) {
            mThread = new Thread(syncRunner);
            mThread.start();
        }
    }

    //模拟每16ms发送vsync信号
    private Runnable syncRunner = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d(getClass().getSimpleName(), "run: ");
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //发送刷新信号
            for (VSYNCCallBack callBack : callBacks) {
                callBack.onVsync(System.currentTimeMillis());
            }
        }
    };

    /**
     * 暴露一个信号接口，让动画模拟VSYNC刷新信号。
     */
    public interface VSYNCCallBack {
        boolean onVsync(long currentTime);
    }

    /**
     * 添加需要信号的动画
     *
     * @param callBack
     */
    void addCallBack(VSYNCCallBack callBack) {

        if (callBacks.contains(callBack)){
            Log.d(getClass().getSimpleName(), "has callback return ! ");
            return;
        }

        Log.d(getClass().getSimpleName(), "addCallBack: ");

        callBacks.add(callBack);
    }
}
