package space.learning.myui.hw2.Q2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {

    public static final String TAG = "CustomLinearLayout";

    //设计尺寸
    private static final int WIDTH = 1080;
    private static final int HEIGHT = 1920;

    private static final int targetWidthDP = 360;//适配目标的宽度 360dp

    private float appDensity = 0f;

    private boolean calculate = false;

    public CustomLinearLayout(Context context) {
        this(context, null);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!calculate) {

            //获取当前界面的density
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            appDensity = displayMetrics.density;

            int childCount = getChildCount();

            //计算等比缩放值
            float scale = appDensity * targetWidthDP / WIDTH;
            Log.d(TAG, "onMeasure: 缩放比 = " + scale);
            Log.d(TAG, "onMeasure: 屏幕宽度 = " + displayMetrics.widthPixels);

            for (int i = 0; i < childCount; i++) {

                //获取子View的宽高
                View child = getChildAt(i);
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();

                Log.d(TAG, "onMeasure: 标准尺寸 width = " + width + "  height = " + height);

                //等比缩放子View的宽高
                int targetWidth = (int) (width * scale);
                int targetHeight = (int) (height * scale);

                ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
                layoutParams.width = targetWidth;
                layoutParams.height = targetHeight;

                child.setLayoutParams(layoutParams);
                Log.d(TAG, "onCalculate: 目标尺寸 width = " + targetWidth + "  height = " + targetHeight);

                calculate = true;
            }
        }
    }
}
