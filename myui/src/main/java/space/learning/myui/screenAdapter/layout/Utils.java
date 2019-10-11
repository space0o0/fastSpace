package space.learning.myui.screenAdapter.layout;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utils {

    private static Utils utils;

    //效果图标准尺寸为1280*720
    private final float UI_HEIGHT = 1280;
    private final float UI_WIDTH = 720;

    //记录屏幕尺寸
    private float screenWidth;
    private float screenHeight;

    private float statusBarHeight;

    private Utils(Context context) {
        screenMeasure(context);
        getStatusBarHeight(context);
    }

    public static Utils getInstance(Context context) {
        if (utils == null) {
            utils = new Utils(context.getApplicationContext());
        }
        return utils;
    }

    /**
     * 获取屏幕的宽高
     *
     * @param context
     */
    private void screenMeasure(Context context) {

        if (screenWidth == 0 || screenHeight == 0) {

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            if (wm != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(displayMetrics);

                if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                    //横屏显示
                    screenHeight = displayMetrics.heightPixels;
                    screenWidth = displayMetrics.widthPixels;
                } else {
                    //竖屏显示
                    screenWidth = displayMetrics.widthPixels;
                    screenHeight = displayMetrics.heightPixels;
                }
            }
        }
    }

    /**
     * 获取屏幕的状态栏高度
     *
     * @param context
     * @return
     */
    private void getStatusBarHeight(Context context) {
        int resID = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resID > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resID);
        } else {
            statusBarHeight = 0f;
        }
    }

    /**
     * 获得横向缩放比例
     *
     * @return
     */
    public float getHorizontalScale() {
        return screenWidth / UI_WIDTH;
    }

    /**
     * 获得竖直方向缩放比例
     *
     * @return
     */
    public float getVerticalScale() {
        return screenHeight / UI_HEIGHT;
    }

    /**
     * 获得状态栏高度
     *
     * @return
     */
    public float getStatusBarHeight() {
        return statusBarHeight;
    }


}
