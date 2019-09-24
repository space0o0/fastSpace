package space.learning.baselibrary.intentTask.bean;

public class IntentTaskInfo {

    //任务id
    private int taskCode;

    public IntentTaskInfo(int taskCode) {
        this.taskCode = taskCode;
    }

    public int getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(int taskCode) {
        this.taskCode = taskCode;
    }
}
