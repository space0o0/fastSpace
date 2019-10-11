package space.learning.myui.screenAdapter.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 屏幕适配：按效果图自动缩放内部控件
 */
public class ScreenAdapterLayout extends RelativeLayout {
    /**
     * 标记是否缩放子view，防止多次缩放。
     */
    private boolean hasScale = false;

    public ScreenAdapterLayout(Context context) {
        this(context, null);
    }

    public ScreenAdapterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScreenAdapterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!hasScale) {
            //得到缩放比例
            float horizontalScale = Utils.getInstance(getContext()).getHorizontalScale();
            float verticalScale = Utils.getInstance(getContext()).getVerticalScale();

            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

                //动态缩放子view的宽高
                layoutParams.width = (int) (layoutParams.width * horizontalScale);
                layoutParams.height = (int) (layoutParams.height * verticalScale);

                //考虑子view的margin间隔
                layoutParams.leftMargin = (int) (layoutParams.leftMargin * horizontalScale);
                layoutParams.rightMargin = (int) (layoutParams.rightMargin * horizontalScale);
                layoutParams.topMargin = (int) (layoutParams.topMargin * verticalScale);
                layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * verticalScale);

                child.setLayoutParams(layoutParams);

            }
            hasScale = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
