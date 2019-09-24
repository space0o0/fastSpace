package space.learning.baselibrary.intentTask.bean;

import android.text.TextUtils;


public class TaskManagerInfo {

    //记录需要处理的activity/fragment的 packageName+className
    private String fromAct;//源Activity的canonicalName
    private String toAct;//目标Activity的canonicalName
    private String toFragment;//目标fragment的canonicalName
    private IntentTaskInfo taskInfo;//任务信息

    public TaskManagerInfo() {
    }


    public String getFromAct() {
        return fromAct;
    }

    public void setFromAct(String fromAct) {
        this.fromAct = fromAct;
    }

    public String getToAct() {
        return toAct;
    }

    public void setToAct(String toAct) {
        this.toAct = toAct;
    }

    public String getToFragment() {
        return toFragment;
    }

    public void setToFragment(String toFragment) {
        this.toFragment = toFragment;
    }

    public IntentTaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(IntentTaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    /**
     * 如果是在activity中处理task，则返回toAct
     *
     * @return
     */
    public String getCanonicalName() {

        String canonicalName;

        if (TextUtils.isEmpty(this.toAct)) {
            throw new RuntimeException("目标activity为空，无法跳转");
        } else {

            if (!TextUtils.isEmpty(this.toFragment)) {
                canonicalName = toFragment;
            } else {
                canonicalName = toAct;
            }
        }

        return canonicalName;
    }
}
