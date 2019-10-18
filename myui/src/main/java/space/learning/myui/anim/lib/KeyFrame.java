package space.learning.myui.anim.lib;

public class KeyFrame {

    //动画百分比
    float percent;

    Class valueType;

    float value;

    public KeyFrame(float percent, float value) {
        this.percent = percent;
        this.value = value;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public Class getValueType() {
        return valueType;
    }

    public void setValueType(Class valueType) {
        this.valueType = valueType;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
