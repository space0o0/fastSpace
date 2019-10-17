package space.learning.myui.writeTouchEvent;

import space.learning.myui.writeTouchEvent.listener.OnClickListener;
import space.learning.myui.writeTouchEvent.listener.OnTouchListener;

public class View {

    private int left, top, right, bottom;

    private OnClickListener onClickListener;
    private OnTouchListener onTouchListener;

    public String name;

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 触摸点x、y是否在该View中
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isContainer(int x, int y) {

        if (x > left && x < right && y > top && y < bottom) {
            return true;
        }
        return false;
    }


    /**
     * dispatchTouchEvent 事件分发
     *
     * @param event
     * @return
     */
    public boolean dispatchTouchEvent(MotionEvent event) {

        boolean result = false;

        if (onTouchListener != null && onTouchListener.onTouch(this, event)) {
            result = true;
        }

        if (!result && onTouch(event)) {
            result = true;
        }

        return result;
    }

    /**
     * onTouch事件消费
     *
     * @param event
     * @return
     */
    public boolean onTouch(MotionEvent event) {

        if (onClickListener != null) {
            onClickListener.onClick(this);
            return true;
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(getClass().getName() + "     onTouch : down");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("onTouch : move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("onTouch : up");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("onTouch : cancel");
                break;
            default:
                break;
        }

        return false;
    }

}
