package space.learning.myui.exam1.Q1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import space.learning.myui.R;

/**
 * n位密码输入框，自动切换
 */
public class PassWordView extends View {

    //密码长度
    private static final int CODE_LENGTH = 4;
    //1个密码框高度   px
    private static final int INPUTLAYOUT_HEIGHT = 120;
    //1个密码框宽度   px
    private static final int INPUTLAYOUT_WIDTH = 120;
    //边框宽度
    private static final int STROKE_WIDTH = 4;
    //横向间隔
    private static final int HOR_MARGIN = 60;
    //密码点半径
    private static final int CODE_POINT_R = 16;

    int viewWidth, viewHeight;

    private Paint mPaint;
    private Paint codePaint;

    //记录密码
    private String[] code;
    private int currentCodeLength = 0;
    private String TAG = "PassWordView";

    public PassWordView(Context context) {
        this(context, null);
    }

    public PassWordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PassWordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        code = new String[CODE_LENGTH];
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        //绘制密码点的paint
        codePaint = new Paint();
        codePaint.setAntiAlias(true);
        codePaint.setStyle(Paint.Style.FILL);
        codePaint.setColor(ContextCompat.getColor(getContext(), R.color.dimgrey));

        setFocusable(true);
        setFocusableInTouchMode(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        viewWidth = INPUTLAYOUT_WIDTH * CODE_LENGTH + (CODE_LENGTH + 1) * STROKE_WIDTH + (CODE_LENGTH - 1) * HOR_MARGIN;
        viewHeight = INPUTLAYOUT_HEIGHT + 2 * STROKE_WIDTH;

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.AT_MOST);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.AT_MOST);

        Log.d("", "onMeasure: viewWidth=" + viewWidth + " viewHeight" + viewHeight);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFrame(canvas);
    }

    /**
     * 绘制边框和密码点
     */
    private void drawFrame(Canvas canvas) {
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.dimgrey));
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);

        RectF rectf = new RectF();

        for (int i = 0; i < CODE_LENGTH; i++) {

            int top = STROKE_WIDTH;
            int bottom = top + INPUTLAYOUT_HEIGHT;
            int left = i * (INPUTLAYOUT_WIDTH + HOR_MARGIN) + STROKE_WIDTH;
            int right = left + INPUTLAYOUT_WIDTH;

            //密码框rect
            rectf = new RectF(left, top, right, bottom);

            //current的下一个输入框变色
            if (i == currentCodeLength) {
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            } else {
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.dimgrey));
            }

            canvas.drawRoundRect(rectf, 10, 10, mPaint);

            //判断输入密码长度,绘制密码点
            if (currentCodeLength != 0 && i <= currentCodeLength - 1) {
                drawCodePoint(rectf, canvas);
            }
        }
    }

    /**
     * 绘制输入后的密码点
     *
     * @param rectF
     * @param canvas
     */
    private void drawCodePoint(RectF rectF, Canvas canvas) {

        int top = (int) (rectF.top + (rectF.bottom - rectF.top) / 2 - CODE_POINT_R);
        int bottom = top + CODE_POINT_R * 2;
        int left = (int) (rectF.left + (rectF.right - rectF.left) / 2 - CODE_POINT_R);
        int right = left + CODE_POINT_R * 2;

        RectF oRectf = new RectF(left, top, right, bottom);
        canvas.drawOval(oRectf, codePaint);
    }

    private void add(String value) {

        if (currentCodeLength >= CODE_LENGTH) {
            return;
        }

        code[currentCodeLength] = value;
        currentCodeLength++;

        //隐藏键盘
        if (currentCodeLength == CODE_LENGTH) {
            showSoftInput();
        }

        show();

        postInvalidate();
    }

    private void del() {
        currentCodeLength--;
        if (currentCodeLength < 0) {
            currentCodeLength = 0;
        }
        code[currentCodeLength] = "";

        show();
        postInvalidate();
    }

    private void show() {
        Log.d(TAG, "show: ===================");
        code.toString();
        for (String i : code) {
            Log.d(TAG, "show: " + i);
        }
        Log.d(TAG, "show: ===================");
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("onTouchEvent", "onTouchEvent: down show softInput");
            showSoftInput();
        }

        return super.onTouchEvent(event);
    }

    public void showSoftInput() {
        this.requestFocus();
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        manager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {

        outAttrs.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI;
        outAttrs.inputType = EditorInfo.TYPE_CLASS_NUMBER;
        return new MyInputConnection(this, true);
    }

    class MyInputConnection extends BaseInputConnection {

        public MyInputConnection(View targetView, boolean fullEditor) {
            super(targetView, fullEditor);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {

            Log.d(TAG, "commitText: " + text);

            return true;
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_UP) {
                //在UP事件监听按键，否则其他事件也会触发该方法，导致多次调用
                if (event.getKeyCode() == KeyEvent.KEYCODE_0) {
                    add("0");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_1) {
                    add("1");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_2) {
                    add("2");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_3) {
                    add("3");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_4) {
                    add("4");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_5) {
                    add("5");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_6) {
                    add("6");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_7) {
                    add("7");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_8) {
                    add("8");
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_9) {
                    add("9");
                }
            }

            return true;
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            Log.d(TAG, "deleteSurroundingText: del");
            del();
            return super.deleteSurroundingText(beforeLength, afterLength);
        }

        @Override
        public boolean finishComposingText() {
            if (currentCodeLength == CODE_LENGTH) {
                StringBuilder sb = new StringBuilder();
                for (String s : code) {
                    sb.append(s);
                }
                Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_LONG).show();

                return true;
            }
            return super.finishComposingText();
        }
    }
}
