package space.learning.myui.writeTouchEvent;

import android.text.method.Touch;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class ViewGroup extends View {

    List<View> childList = new ArrayList<>();
    private View[] mChildren = new View[0];

    private TouchTarget mFirstTouchTarget;


    public ViewGroup(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public void addView(View view) {

        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = childList.toArray(new View[childList.size()]);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {

        System.out.println(name + "     dispatchTouchEvent");

        int actionMasked = event.getActionMasked();
        boolean intercept = onInterceptTouchEvent(event);

        boolean handled = false;

        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercept) {
            //不是cancel && 不拦截

            if (actionMasked == MotionEvent.ACTION_DOWN) {
                //Down事件
                final View[] children = mChildren;

                for (int i = children.length - 1; i >= 0; i--) {
                    //最后添加的view，代表z轴最上面，点击时候的优先级也最高，先从最上面的view开始查找事件消费

                    View child = mChildren[i];
                    if (!child.isContainer(event.getX(), event.getY())) {
                        //当前child不在点击范围内，跳过
                        continue;
                    }

                    if (dispatchTransformedTouchEvent(event, child)) {
                        //有view消费了事件
                        handled = true;
                        //记录每一层的view
                        addTouchTarget(child);
                        break;
                    }

                }
            }
        }

        if (mFirstTouchTarget == null) {
            //没有view消费事件,调用父类dispatchTouchEvent
            handled = dispatchTransformedTouchEvent(event, null);
        } else {
            TouchTarget target = mFirstTouchTarget;
            while (target != null) {
                if (dispatchTransformedTouchEvent(event, target.child)){
                    handled=true;
                }
                target = target.next;
            }
        }

        return handled;
    }

    private TouchTarget addTouchTarget(View child) {

        System.out.println("add touchTarget " + child.getClass().getName());

        TouchTarget target = TouchTarget.obtain(child);
        target.next = mFirstTouchTarget;
        mFirstTouchTarget = target;
        return target;

    }

    public static class TouchTarget {

        private View child;

        //回收池
        private static TouchTarget sRecyclePool;

        private static final Object lock = new Object[0];
        public TouchTarget next;
        public static int sRecyclePoolCount;

        public static TouchTarget obtain(View child) {
            TouchTarget target;
            synchronized (lock) {
                if (sRecyclePool == null) {
                    target = new TouchTarget();
                } else {
                    target = sRecyclePool;
                }

                sRecyclePool = target.next;
                sRecyclePoolCount--;
                target.next = null;
            }
            target.child = child;
            return target;
        }

    }

    private boolean dispatchTransformedTouchEvent(MotionEvent event, View child) {

        boolean handled = false;

        if (child != null) {
            handled = child.dispatchTouchEvent(event);
        } else {
            handled = super.dispatchTouchEvent(event);
        }

        return handled;
    }


    /**
     * 拦截事件
     *
     * @param event
     * @return
     */
    public boolean onInterceptTouchEvent(MotionEvent event) {

        return false;
    }
}
