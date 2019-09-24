package space.learning.baselibrary.photoChooser;

import android.support.v4.app.FragmentActivity;

import space.learning.baselibrary.photoChooser.ui.PhotoChooserFragment;

import space.learning.baselibrary.photoChooser.listener.IPhotoChooserListener;

public class PhotoChooser {

    private FragmentActivity activity;

    //是否裁剪
    private boolean shouldCrop = true;

    //选择监听
    private IPhotoChooserListener listener;

    public PhotoChooser(FragmentActivity activity) {
        this.activity = activity;
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
        PhotoChooserFragment.newInstance(activity).setListener(listener).fromAll(shouldCrop);
    }

    public void showTakePhoto() {
        PhotoChooserFragment.newInstance(activity).setListener(listener).fromTakePhoto(shouldCrop);
    }

    public void showGallery() {
        PhotoChooserFragment.newInstance(activity).setListener(listener).fromGallery(shouldCrop);
    }


}
