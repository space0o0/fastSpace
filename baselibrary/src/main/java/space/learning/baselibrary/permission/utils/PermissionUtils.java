package space.learning.baselibrary.permission.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.Permission;

public class PermissionUtils {

    /**
     * 检查权限
     *
     * @param context
     * @param checkPermissions
     * @return
     */
    public static boolean hasPermission(Context context, String[] checkPermissions) {

        for (String checkPermission : checkPermissions) {

            if (ContextCompat.checkSelfPermission(context, checkPermission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }

        }

        return true;

    }

    /**
     * Activity的检查是否需要提示
     *
     * @param context
     * @param openCameraPermissions
     * @return
     */
    public static boolean showShowRequestPermissionRationale(Activity context, String[] openCameraPermissions) {

        for (String openCameraPermission : openCameraPermissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, openCameraPermission)) {
                return true;
            }
        }

        return false;
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static boolean verifyGrantResult(int[] grantResult) {

        if (grantResult.length == 0) {
            return false;
        }

        for (int grant : grantResult) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}
