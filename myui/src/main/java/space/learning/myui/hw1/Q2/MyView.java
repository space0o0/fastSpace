package space.learning.myui.hw1.Q2;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    Paint mPaint;
    float x, y;//圆心
    int r = 40;//半径
    RectF rect;//圆的范围

    boolean inRange = false;//记录触摸点是否在范围内

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

        rect = new RectF(0, 0, 2 * r, 2 * r);
        x = r;
        y = r;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rect, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //判断是否在view范围内
            checkTouchInRange(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //移动view
            if (inRange){
                updateCircleRect(event.getX(), event.getY());
                invalidate();
            }
        }
        return true;
    }

    /**
     * 检查触摸范围在view中
     */
    private void checkTouchInRange(float x, float y) {
        if (x > rect.left && x < rect.right && y > rect.top && y < rect.bottom) {
            inRange = true;
        } else {
            inRange = false;
        }
    }

    /**
     * 更新圆的位置
     *
     * @param x
     * @param y
     */
    private void updateCircleRect(float x, float y) {
        this.x = x;
        this.y = y;

        rect.left = this.x - r;
        rect.right = this.x + r;
        rect.top = this.y - r;
        rect.bottom = this.y + r;
    }
}
