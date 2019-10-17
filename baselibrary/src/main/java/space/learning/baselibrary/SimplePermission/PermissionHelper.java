package space.learning.baselibrary.SimplePermission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import space.learning.baselibrary.MyApplication;

public class PermissionHelper {

    private FragmentActivity activity;

    private final String TAG_PERMISSION = "TAG_PERMISSION";

    public PermissionHelper(FragmentActivity activity) {
        this.activity = activity;
    }

    public static PermissionHelper getInstance(FragmentActivity activity) {
        return new PermissionHelper(activity);
    }

    public void requestPermission(String[] permissions, IPermissionListenerWrap.IPermissionListener callBack) {

        createPermissionFragment(activity).requestPermissions(permissions, callBack);

    }

    private PermissionFragment createPermissionFragment(FragmentActivity activity) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();

        PermissionFragment fragment = (PermissionFragment) fragmentManager.findFragmentByTag(TAG_PERMISSION);

        if (fragment == null) {
            fragment = PermissionFragment.newInstance();

            fragmentManager.beginTransaction()
                    .add(fragment, TAG_PERMISSION)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        return fragment;
    }

    /**
     * @param permission
     */
    public static boolean checkPermission(String permission) {

        int permissionCheck = ContextCompat.checkSelfPermission(MyApplication.getInstance(), permission);

        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissions(String[] permissions) {

        boolean hasPermission = true;

        for (String permission : permissions) {

            hasPermission = hasPermission && checkPermission(permission);

        }

        return hasPermission;
    }

    /**
     * 用户点击不再提示
     */
    public void requestDialogAgain(final Activity activity, String title, String message, String confirm, String cancel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle(title).setMessage(message).setPositiveButton(TextUtils.isEmpty(confirm) ? "ok" : confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSettingActivity(activity);
            }
        });

        if (!TextUtils.isEmpty(cancel)) {
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        builder.setCancelable(false);

        builder.show();

    }

    /**
     * 打开设置页面
     *
     * @param activity
     */
    public void openSettingActivity(@NonNull Activity activity) {

        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.getPackageName()));

            intent.addCategory(Intent.CATEGORY_DEFAULT);

            activity.startActivityForResult(intent, 10);//该requestcode必须和onActivityResult的requestcode对应
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
