package space.learning.myui.screenAdapter2;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class DensityUtils {

    private final static float WIDTH = 360;//适配机型的宽为360dp，屏幕宽/屏幕密度=360dp

    private static float appDensity;
    private static float appScaleDensity;

    public static void setDensity(final Application application, Activity activity) {
        //获取当前app的屏幕显示信息
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();

        appDensity = displayMetrics.density;
        appScaleDensity = displayMetrics.scaledDensity;

        //监听字体改变，重新获取scaleDensity
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }
            }

            @Override
            public void onLowMemory() {

            }
        });

        //计算等比缩放后的density和scaleDensity
        //WIDTH相对于所有屏幕宽度都是相等的，它是用dp作为单位，所以 屏幕宽度/屏幕密度=WIDTH
        //targetDensity = targetWidht/WIDTH
        float targetDensity = displayMetrics.widthPixels / WIDTH;
        //appScaleDensity/appDensity=targetScaleDensity/targetDensity;
        float targetScaleDensity = targetDensity * (appScaleDensity / appDensity);
        int targetDensityDpi = (int) (targetDensity * 160);

        //替换activity的density，appdensity，densityDpi
        DisplayMetrics aDisplayMertics = activity.getResources().getDisplayMetrics();
        aDisplayMertics.density = targetDensity;
        aDisplayMertics.scaledDensity = targetScaleDensity;
        aDisplayMertics.densityDpi = targetDensityDpi;
    }
}
