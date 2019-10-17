package space.learning.myui.touchEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class ViewGroupA extends FrameLayout {

    public ViewGroupA(Context context) {
        this(context, null);
    }

    public ViewGroupA(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.d(getClass().getSimpleName(), "dispatchTouchEvent: " + MotionEvent.actionToString(ev.getAction()));

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.d(getClass().getSimpleName(), "onInterceptTouchEvent: ");

        return true;
//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(getClass().getSimpleName(), "onTouchEvent: ");

        return true;
//        return super.onTouchEvent(event);
    }
}
