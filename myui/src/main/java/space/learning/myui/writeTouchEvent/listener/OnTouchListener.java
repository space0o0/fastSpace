package space.learning.myui.writeTouchEvent.listener;

import space.learning.myui.writeTouchEvent.MotionEvent;
import space.learning.myui.writeTouchEvent.View;

public interface OnTouchListener {

    boolean onTouch(View view, MotionEvent event);
}
