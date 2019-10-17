package space.learning.myui.percentLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import space.learning.myui.R;

public class PercentLayout extends RelativeLayout {
    public PercentLayout(Context context) {
        this(context, null);
    }

    public PercentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取父容器的宽高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {

            View child = getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();

            if (layoutParams instanceof CustomLayoutParam) {

                CustomLayoutParam lp = (CustomLayoutParam) layoutParams;

                float widthPercent = lp.widthPercent;
                float heightPercent = lp.heightPercent;
                float marginTopPercent = lp.marginTopPercent;
                float marginBottomPercent = lp.marginBottomPercent;
                float marginLeftPercent = lp.marginLeftPercent;
                float marginRightPercent = lp.marginRightPercent;

                if (widthPercent > 0) {
                    lp.width = (int) (widthSize * widthPercent);
                }

                if (heightPercent > 0) {
                    lp.height = (int) (heightSize * heightPercent);
                }

                if (marginTopPercent > 0) {
                    lp.topMargin = (int) (heightSize * marginTopPercent);
                }

                if (marginBottomPercent > 0) {
                    lp.bottomMargin = (int) (heightSize * marginBottomPercent);
                }

                if (marginLeftPercent > 0){
                    lp.leftMargin= (int) (widthSize* marginLeftPercent);
                }

                if (marginRightPercent > 0){
                    lp.rightMargin = (int) (widthSize * marginRightPercent);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParam(getContext(), attrs);
    }

    private static class CustomLayoutParam extends RelativeLayout.LayoutParams {
        //声明自定义属性
        private float widthPercent;
        private float heightPercent;
        private float marginTopPercent;
        private float marginBottomPercent;
        private float marginLeftPercent;
        private float marginRightPercent;

        public CustomLayoutParam(Context context, AttributeSet attrs) {
            super(context, attrs);

            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.PercentLayout);

            widthPercent = typedArray.getFloat(R.styleable.PercentLayout_widthPercent, 0f);
            heightPercent = typedArray.getFloat(R.styleable.PercentLayout_heightPercent, 0f);
            marginTopPercent = typedArray.getFloat(R.styleable.PercentLayout_marginTopPercent, 0f);
            marginBottomPercent = typedArray.getFloat(R.styleable.PercentLayout_marginBottomPercent, 0f);
            marginLeftPercent = typedArray.getFloat(R.styleable.PercentLayout_marginLeftPercent, 0f);
            marginRightPercent = typedArray.getFloat(R.styleable.PercentLayout_marginRightPercent, 0f);

            typedArray.recycle();
        }
    }


}
