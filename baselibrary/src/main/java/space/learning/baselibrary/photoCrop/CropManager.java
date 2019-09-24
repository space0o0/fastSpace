package space.learning.baselibrary.photoCrop;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import org.jetbrains.annotations.Nullable;

import space.learning.baselibrary.photoCrop.conf.CropConfigure;
import space.learning.baselibrary.photoCrop.listener.CropListener;
import space.learning.baselibrary.photoCrop.wrap.ICropWrapper;
import space.learning.baselibrary.photoCrop.wrap.UcropWrapper;

public class CropManager {

    //裁剪配置
    private CropConfigure cropConfigure;

    //裁剪监听
    private CropListener listener;

    //需要裁剪的图片地址
    private Uri srcUri;

    private ICropWrapper cropWrapper;

    public CropManager(Uri srcUri) {
        this.srcUri = srcUri;
    }

    /**
     * @return
     */
    public static CropManager getInstance(Uri srcUri) {
        return new CropManager(srcUri);
    }

    /**
     * 添加裁剪配置
     *
     * @param config
     * @return
     */
    public CropManager setConfig(CropConfigure config) {
        this.cropConfigure = config;
        return this;
    }

    /**
     * 配置裁剪监听
     *
     * @param listener
     * @return
     */
    public CropManager setCropListener(CropListener listener) {
        this.listener = listener;
        return this;
    }

    private void initICropWrapper(){
        cropWrapper = UcropWrapper.newInstance(this.srcUri)
                .setConfigure(this.cropConfigure)
                .setCropListener(listener);
    }

    /**
     * 裁剪开始的入口-activiy
     *
     * @return
     */
    public CropManager start(AppCompatActivity activity) {

        initICropWrapper();
        cropWrapper.crop(activity);
        return this;
    }

    /**
     * 裁剪开始的入口-fragment
     *
     * @return
     */
    public CropManager start(Fragment fragment) {

        initICropWrapper();
        cropWrapper.crop(fragment);
        return this;
    }


    /**
     * 在调用裁剪的界面监听返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void activityResult(int requestCode, int resultCode, @Nullable Intent data) {
        cropWrapper.onActivityResult(requestCode, resultCode, data);
    }

}
