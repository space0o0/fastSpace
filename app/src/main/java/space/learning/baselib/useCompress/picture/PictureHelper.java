package space.learning.baselib.useCompress.picture;

import android.Manifest;

import androidx.fragment.app.FragmentActivity;

import com.zeyjr.bmc.std.picture.IPictureListenerWrap;
import com.zeyjr.bmc.std.picture.PictureFragment;

public class PictureHelper {

    private FragmentActivity mActivity;
    private Boolean hasPermission = false;

    public PictureHelper(FragmentActivity activity) {
        this.mActivity = activity;

        //check permission 读写文件权限
//        hasPermission = checkPermission();
        hasPermission = true;
    }

    public static PictureHelper newInstance(FragmentActivity activity) {
        return new PictureHelper(activity);
    }

    /**
     * 选择拍照还是相册 -> 裁剪
     *
     * @param callBack
     */
    public void getPhoto(IPictureListenerWrap.IPictureListener callBack) {
        if (!hasPermission) {
            return;
        }
        createPictureFragment().startSelect(mActivity, callBack);
    }

    /**
     * 选择拍照还是相册 -> 不裁剪
     *
     * @param callBack
     */
    public void getPhotoNoCrop(IPictureListenerWrap.IPictureListener callBack) {
        if (!hasPermission) {
            return;
        }
        createPictureFragment().startSelectNoCrop(mActivity, callBack);
    }

    /**
     * 只选择拍照
     */
    public void getPhotoWithGallery(IPictureListenerWrap.IPictureListener callBack) {
        if (!hasPermission) {
            return;
        }
        createPictureFragment().startSelectFromGallery(mActivity, callBack);
    }

    /**
     * 只选择拍照 -> 不裁剪
     */
    public void getPhotoWithGalleryNoCrop(IPictureListenerWrap.IPictureListener callBack) {
        if (!hasPermission) {
            return;
        }
        createPictureFragment().startSelectFromGalleryNoCrop(mActivity, callBack);
    }

    /**
     * 只选择相册
     */
    public void getPhotoWithTakePhoto(IPictureListenerWrap.IPictureListener callBack) {
        if (!hasPermission) {
            return;
        }
        createPictureFragment().startSelectFromTakePhoto(mActivity, callBack);
    }

    private PictureFragment createPictureFragment() {

        PictureFragment fragment = PictureFragment.newInstance(mActivity);

        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(fragment, "pictureFragment")
                .commitAllowingStateLoss();

        mActivity.getSupportFragmentManager().executePendingTransactions();
        return fragment;
    }

//    private Boolean checkPermission() {
//
//        String[] permissions = new String[]{
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE};
//
//        if (PermissionHelper.checkPermissions(permissions)) {
//
//            return true;
//
//        } else {
//
//            PermissionHelper.getInstance(mActivity).requestPermission(permissions, new IPermissionListenerWrap.IPermissionListener() {
//                @Override
//                public void onAccepted(@NotNull Permission permission) {
//
//                    LogUtils.d("", permission.toString());
//
//                }
//
//                @Override
//                public void onException(@NotNull Throwable throwable) {
//
//                }
//            });
//
//            return false;
//        }
//    }
}
