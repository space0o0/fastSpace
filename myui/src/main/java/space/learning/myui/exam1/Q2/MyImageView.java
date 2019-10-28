package space.learning.myui.exam1.Q2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import space.learning.myui.R;

/**
 * 旋转 平移 缩放
 */
public class MyImageView extends View {

    private static final String TAG = "MyImageView@#";
    private Bitmap mBitmap;
    private Matrix matrix;
    private Paint mPaint;

    //手指1，手指2
    private MyPoint point1, point2;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        matrix = new Matrix();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "onDraw: " + matrix.toString());
        canvas.drawBitmap(mBitmap, matrix, mPaint);
    }

    private MyPoint lastPoint1, lastPoint2;

    /**
     * 判断旋转 平移 缩放
     * 一指平移
     * 两指有旋转和缩放
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            //第一根手指按下

            point1 = new MyPoint((int) event.getX(event.getActionIndex()), (int) event.getY(event.getActionIndex()), event.getPointerId(event.getActionIndex()));
            lastPoint1 = new MyPoint(point1.x, point1.y, point1.getId());
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) {
            //第二根手指按下

            point2 = new MyPoint((int) event.getX(event.getActionIndex()), (int) event.getY(event.getActionIndex()), event.getPointerId(event.getActionIndex()));
            lastPoint2 = new MyPoint(point2.x, point2.y, point2.getId());

            lastDegree = 0f;//默认旋转角度
        } else if (action == MotionEvent.ACTION_MOVE) {
            //手指移动

            //判断是否移动
            if (checkMove(event)) {
                movePic(event);
            }

            //判断是否旋转
            if (checkRotate(event)) {
                rotatePic(event);
            }

            //判断是否缩放
            if (checkScale(event)) {
                scalePic(event);
            }

            //更新手指坐标
            updateLastPoint(event);

            invalidate();
        }

        return true;
    }


    /**
     * 移动
     */
    private void movePic(MotionEvent event) {

        if (event.getPointerId(0) == lastPoint1.getId()) {
            matrix.postTranslate(event.getX(0) - lastPoint1.x, event.getY(0) - lastPoint1.y);
        }
    }

    float lastDegree = 0f;

    /**
     * 旋转
     *
     * @param event
     */
    private void rotatePic(MotionEvent event) {

        //当前旋转角度
        float currDegree = calculateDegree(event);

        float rotateDegree = 0f;

        if (lastDegree != 0f) {
            //计算旋转角度
            rotateDegree = currDegree - lastDegree;
        }

//        Log.d(TAG, "rotatePic: " + rotateDegree);

        float centerX, centerY;

        centerX = (event.getX(0) + event.getX(1)) / 2;
        centerY = (event.getY(0) + event.getY(1)) / 2;

//        Log.d(TAG, "rotatePic: centerxy" + centerX + "  " + centerY);

        matrix.postRotate(rotateDegree, centerX, centerY);

        //记录当前角度
        lastDegree = currDegree;
    }

    private float calculateDegree(MotionEvent event) {

        float deltax = (event.getX(0) - event.getX(1));
        float deltay = event.getY(0) - event.getY(1);

        float angle = (float) Math.atan2(deltay, deltax);

        float degree = (float) Math.toDegrees(angle);
//        Log.d(TAG, "calculateAngle: " + degree);

        return degree;
    }

    float lastScale = 1f;

    /**
     * 缩放
     *
     * @param event
     */
    private void scalePic(MotionEvent event) {

        Log.d(TAG, "scalePic: mBitmap size" + mBitmap.getWidth() * lastScale + " * " + mBitmap.getHeight() * lastScale);

        float centerX, centerY;

        centerX = (event.getX(0) + event.getX(1)) / 2;
        centerY = (event.getY(0) + event.getY(1)) / 2;

        //当前滑动距离 / 当前图片大小

        float deltaX1 = event.getX(0) - lastPoint1.x;
        float deltaY1 = event.getY(0) - lastPoint1.y;
        float move1 = (float) Math.sqrt(deltaX1 * deltaX1 + deltaY1 * deltaY1);

        float deltaX2 = event.getX(1) - lastPoint2.x;
        float deltaY2 = event.getY(1) - lastPoint2.y;
        float move2 = (float) Math.sqrt(deltaX2 * deltaX2 + deltaY2 * deltaY2);

        float bw = mBitmap.getWidth() * lastScale;
        float bh = mBitmap.getHeight() * lastScale;
        float bitmapDiagnal = (float) Math.sqrt(bw * bw + bh * bh);//当前图片大小

        float currMove = move1 + move2;//当前滑动距离

        float currScale = currMove / bitmapDiagnal;

        float scale = 1f;

        float lastDistance = (float) Math.sqrt((lastPoint1.x - lastPoint2.x) * (lastPoint1.x - lastPoint2.x) + (lastPoint1.y - lastPoint2.y) * (lastPoint1.y - lastPoint2.y));
        float currDistance = (float) Math.sqrt((event.getX(0) - event.getX(1)) * (event.getX(0) - event.getX(1)) +
                (event.getY(0) - event.getY(1)) * (event.getY(0) - event.getY(1)));

        if (lastDistance > currDistance) {
            //缩小
            scale = (lastScale - currScale) / lastScale;
        } else {
            //放大
            scale = (lastScale + currScale) / lastScale;
        }

        Log.d(TAG, "scalePic:   lastScale" + lastScale + "   currScale" + currScale + "     scale" + scale);

        matrix.postScale(scale, scale, centerX, centerY);

        lastScale = scale;
    }

    /**
     * 监测移动
     *
     * @param event
     * @return
     */
    private boolean checkMove(MotionEvent event) {

        if (event.getPointerCount() == 1) {
            return true;
        }

        return false;
    }

    /**
     * 监测旋转
     *
     * @param event
     * @return
     */
    private boolean checkRotate(MotionEvent event) {

        //必须2个手指才旋转
        if (event.getPointerCount() >= 2) {
            return true;
        }

        return false;
    }

    /**
     * 监测缩放
     *
     * @param event
     * @return
     */
    private boolean checkScale(MotionEvent event) {

        //必须2个手指才缩放
        if (event.getPointerCount() >= 2) {
            return true;
        }

        return false;
    }


    /**
     * 更新手指点
     *
     * @param event
     */
    private void updateLastPoint(MotionEvent event) {

        int pointCount = event.getPointerCount();

        if (pointCount == 2) {

            lastPoint1.set((int) event.getX(0), (int) event.getY(0));
            lastPoint2.set((int) event.getX(1), (int) event.getY(1));

        } else if (pointCount == 1) {
            lastPoint1.set((int) event.getX(0), (int) event.getY(0));

        }
    }

}
