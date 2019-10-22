package space.learning.myui.anim.lib;

import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

import space.learning.myui.anim.lib.interpolator.IInterpolator;

/**
 * 动画管理者
 */
public class MyObjectAnimator implements VSYNCManager.VSYNCCallBack {

    private WeakReference<View> mView;

    private PropertyAnimHolder propertyAnimHolder;

    //动画时长
    private int duration;
    private float animMaxCount = 0;//动画执行刷新总次数
    private int currentAnimIndex = 0;//当前动画刷新次数

    //插值器
    private IInterpolator interpolator;

    //重复次数
    private int repeate = -1;

    public MyObjectAnimator(View view, String propertyName, Float... values) {
        mView = new WeakReference<>(view);
        propertyAnimHolder = new PropertyAnimHolder(propertyName, values);
    }

    public static MyObjectAnimator ofFloat(View view, String propertyName, Float... values) {
        return new MyObjectAnimator(view, propertyName, values);
    }

    public void setDuration(int duration) {
        this.duration = duration;

        //计算动画刷新总次数
        animMaxCount = this.duration / 16;
    }

    public void setInterpolator(IInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    /**
     * 接收到刷新信号
     *
     * @param currentTime 当前事件
     * @return
     */
    @Override
    public boolean onVsync(long currentTime) {

//        Log.d(getClass().getSimpleName(), "onVsync: ");
        //当前执行动画进度百分比
        float animPercent = (currentAnimIndex++) / animMaxCount;

        //插值器介入
        if (interpolator != null) {
            animPercent = interpolator.getInterpolator(animPercent);
        }

        if (repeate == -1 && currentAnimIndex >= animMaxCount) {
            //重复开始，index大于执行一遍刷新次数，index从0开始
            currentAnimIndex = 0;
        }

        propertyAnimHolder.setAnimValue(mView.get(), animPercent);
        return false;
    }

    public void start() {
        Log.d(getClass().getSimpleName(), "start: ");
//        VSYNCManager.getInstance().addCallBack(this);
    }
}
