package space.learning.baselibrary.intentTask;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import space.learning.baselibrary.intentTask.bean.IntentTaskInfo;
import space.learning.baselibrary.intentTask.bean.TaskManagerInfo;

public class IntentTaskManager {

    private HashMap<String, TaskManagerInfo> cache = new HashMap<>();

    private static Application app;

    private static TaskManagerInfo taskManager;

    private Activity mActivity;
    private Class<?> targetActivity;

    static class Holder {
        static IntentTaskManager instance = new IntentTaskManager();
    }

    public void init(Application application) {
        app = application;
    }

    public static IntentTaskManager getInstance() {
        taskManager = new TaskManagerInfo();

        return Holder.instance;
    }

    /**
     * 添加 来源于activity
     *
     * @param activity
     * @return
     */
    public IntentTaskManager from(Activity activity) {
        this.mActivity = activity;
        taskManager.setFromAct(activity.getClass().getCanonicalName());
        return this;
    }

    /**
     * 添加 跳转目标activity
     *
     * @param activity
     * @return
     */
    public IntentTaskManager goTo(Class<?> activity) {
        this.targetActivity = activity;
        //记录目标activity的全路径名
        taskManager.setToAct(activity.getCanonicalName());
        return this;
    }

    /**
     * 添加 目标activity的fragment处理task
     * 指定目标activity的fragment处理task
     *
     * @param fragment
     * @return
     */
    public IntentTaskManager withFragment(Fragment fragment) {
        //记录目标fragment的全路径名
        taskManager.setToFragment(fragment.getClass().getCanonicalName());
        return this;
    }

    /**
     * 添加 任务标示
     *
     * @return
     */
    public IntentTaskManager task(IntentTaskInfo taskInfo) {
        taskManager.setTaskInfo(taskInfo);
        return this;
    }

    /**
     * 添加 任务标示
     *
     * @return
     */
    public IntentTaskManager task(int taskCode) {
        taskManager.setTaskInfo(new IntentTaskInfo(taskCode));
        return this;
    }

    /**
     * 跳转
     */
    public void go() {
        cache.put(taskManager.getCanonicalName(), taskManager);
        mActivity.startActivity(new Intent(mActivity, targetActivity));
    }

    /**
     * 从任务队列中获取任务
     *
     * @param object
     * @return
     */
    public IntentTaskInfo getTask(Object object) {
        if (cache == null || cache.isEmpty()) {
            return null;
        }

        String objectName = object.getClass().getCanonicalName();

        if (cache.containsKey(objectName)) {
            return cache.get(objectName).getTaskInfo();
        }

        return null;
    }


    public static Application getAppContent() {
        return app;
    }
}
