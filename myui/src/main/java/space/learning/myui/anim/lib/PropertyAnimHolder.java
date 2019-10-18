package space.learning.myui.anim.lib;

import android.view.View;

import java.lang.reflect.Method;


/**
 * 属性动画执行者
 */
public class PropertyAnimHolder {

    //需要修改的属性名
    private String propertyName;

    private Class valueType;

    //反射方法
    private Method setter;

    private KeyFrameSet keyFrameSet;

    PropertyAnimHolder(String propertyName, Float... values) {

        this.propertyName = propertyName;
        valueType = float.class;
        keyFrameSet = KeyFrameSet.ofFloat(values);

        initSetter();
    }

    /**
     * 构造结束后，初始化反射需要的参数
     * 反射的方法名
     * 反射的方法
     */
    private void initSetter() {
        //第1位字母大写
        char firstChar = Character.toUpperCase(this.propertyName.charAt(0));
        //截取第二位开始的字符
        String theRest = this.propertyName.substring(1);
        //方法名
        String methodName = "set" + firstChar + theRest;

        //获取反射
        try {
            setter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置view的属性，完成当前百分比的动画效果
     * @param view
     * @param animPercent
     */
    void setAnimValue(View view, float animPercent) {
        //根据动画进度百分比，获取属性值
        Object value = keyFrameSet.getValue(animPercent);

        try {
            setter.invoke(view, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
