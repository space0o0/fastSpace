package space.learning.baselibrary.photoChooser;

import android.Manifest;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import space.learning.baselibrary.SimplePermission.IPermissionListenerWrap;
import space.learning.baselibrary.SimplePermission.Permission;
import space.learning.baselibrary.SimplePermission.PermissionHelper;
import space.learning.baselibrary.photoChooser.ui.PhotoChooserFragment;

import space.learning.baselibrary.photoChooser.listener.IPhotoChooserListener;

public class PhotoChooser {

    private FragmentActivity activity;

    //是否裁剪
    private boolean shouldCrop = true;

    //选择监听
    private IPhotoChooserListener listener;

    private Boolean hasPermission = false;

    public PhotoChooser(FragmentActivity activity) {
        this.activity = activity;

        hasPermission = checkPermission(activity);
    }

    public static PhotoChooser newInstance(FragmentActivity activity) {

        return new PhotoChooser(activity);
    }

    /**
     * 是否需要裁剪
     *
     * @param crop
     * @return
     */
    public PhotoChooser setCrop(boolean crop) {
        this.shouldCrop = crop;
        return this;
    }

    /**
     * 添加获取图片的监听
     *
     * @param listener
     * @return
     */
    public PhotoChooser addListener(IPhotoChooserListener listener) {
        this.listener = listener;
        return this;
    }

    public void show() {

        if (!hasPermission){
            return;
        }

        PhotoChooserFragment.newInstance(activity).setListener(listener).fromAll(shouldCrop);
    }

    public void showTakePhoto() {

        if (!hasPermission){
            return;
        }

        PhotoChooserFragment.newInstance(activity).setListener(listener).fromTakePhoto(shouldCrop);
    }

    public void showGallery() {

        if (!hasPermission){
            return;
        }

        PhotoChooserFragment.newInstance(activity).setListener(listener).fromGallery(shouldCrop);
    }

    private Boolean checkPermission(FragmentActivity activity) {

        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (PermissionHelper.checkPermissions(permissions)) {

            return true;

        } else {

            PermissionHelper.getInstance(activity).requestPermission(permissions, new IPermissionListenerWrap.IPermissionListener() {
                @Override
                public void onAccepted(@NotNull Permission permission) {

                    Log.d("", permission.toString());

                }

                @Override
                public void onException(@NotNull Throwable throwable) {

                }
            });

            return false;
        }
    }
}
