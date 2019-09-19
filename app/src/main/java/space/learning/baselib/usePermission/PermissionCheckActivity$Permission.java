package space.learning.baselib.usePermission;

import android.Manifest;
import android.util.Log;

import java.lang.ref.WeakReference;

import space.learning.baselib.constant.Constants;
import space.learning.baselibrary.permission.listener.ShowRationalListener;
import space.learning.baselibrary.permission.utils.PermissionUtils;

public class PermissionCheckActivity$Permission {

    /**
     * 方法名+Permissions 存放请求
     */
    static String[] openCameraPermissions = new String[]{Manifest.permission.CAMERA};

    /**
     * 方法名+RequestCode 0，1，2，3，4
     */
    final static int openCameraRequestCode = 0;

    /**
     * @param activity
     * @checkPermission标记的方法 </p>
     * methodName+CheckPermission
     */
    public static void openCamera_checkPermission(PermissionCheckActivity activity) {

        //检查是否有权限
        if (PermissionUtils.hasPermission(activity, openCameraPermissions)) {
            //存在权限
            activity.openCamera();
        } else {
            //请求权限

            if (PermissionUtils.showShowRequestPermissionRationale(activity, openCameraPermissions)) {
                //显示请求权限理由
                activity.showRationale(new OpenCameraRationaleListener(activity));
            } else {
                //申请权限
                PermissionUtils.requestPermissions(activity, openCameraPermissions, openCameraRequestCode);
            }
        }
    }

    public static void onRequestPermissionsResult(PermissionCheckActivity activity, int requestCode, String[] permissions, int[] grantResult) {

        switch (requestCode) {
            case openCameraRequestCode:

                if (PermissionUtils.verifyGrantResult(grantResult)) {
                    //同意授权权限
                    activity.openCamera();
                } else {
                    if (PermissionUtils.showShowRequestPermissionRationale(activity, permissions)) {
                        //不同意授权
                        activity.permissionDenied();
                    } else {
                        //不再显示
                        activity.neverShow();
                    }
                }

                break;
            default:
                break;
        }

    }

    /**
     * 这个是用来监听：显示权限理由后，用户是否同意给予权限
     */
    static class OpenCameraRationaleListener implements ShowRationalListener {

        private WeakReference<PermissionCheckActivity> weakActivity;

        public OpenCameraRationaleListener(PermissionCheckActivity activity) {
            this.weakActivity = new WeakReference<PermissionCheckActivity>(activity);
        }

        @Override
        public void allow() {
            if (weakActivity.get()!=null){
                Log.d(Constants.LOG_TAG,"allow");
                PermissionUtils.requestPermissions(weakActivity.get(), openCameraPermissions, openCameraRequestCode);
            }
        }

        @Override
        public void denied() {
            if (weakActivity.get()!=null){
                Log.d(Constants.LOG_TAG,"denied");
                weakActivity.get().permissionDenied();
            }
        }
    }

}
