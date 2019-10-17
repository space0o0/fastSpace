package space.learning.myui.writeTouchEvent;

import space.learning.myui.writeTouchEvent.listener.OnTouchListener;

public class MainTouchEvent {

    public static void main(String[] args) {

        ViewGroup root = new ViewGroup(0, 0, 1080, 1920);
        root.setName("根view");
        ViewGroup viewGroup = new ViewGroup(0, 0, 700, 700);
        viewGroup.setName("viewGroup");
        View view = new View(0, 0, 200, 200);
        view.setName("子view");

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                System.out.println("view add touch listener ~ ");

                return true;
            }
        });

        root.addView(viewGroup);
        viewGroup.addView(view);

        MotionEvent downEvent = new MotionEvent(100, 100);
        downEvent.setActionMasked(MotionEvent.ACTION_DOWN);
        MotionEvent upEvent = new MotionEvent(100, 100);
        upEvent.setActionMasked(MotionEvent.ACTION_UP);

        root.dispatchTouchEvent(downEvent);

        root.dispatchTouchEvent(upEvent);
    }
}
