package space.learning.baselibrary.photoCrop.wrap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yalantis.ucrop.UCrop;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import space.learning.baselibrary.photoCrop.conf.CropConfigure;
import space.learning.baselibrary.photoCrop.listener.CropListener;
import space.learning.baselibrary.photoCrop.utils.Constants;

public class UcropWrapper implements ICropWrapper {

    private Uri srcUri;

    private Uri destUri;

    private CropListener listener;

    private CropConfigure configure;

    private UcropWrapper(Uri srcUri) {
        this.srcUri = srcUri;
    }

    public static UcropWrapper newInstance(Uri src) {
        return new UcropWrapper(src);
    }

    public UcropWrapper setCropListener(CropListener listener) {
        this.listener = listener;
        return this;
    }

    public UcropWrapper setConfigure(CropConfigure configure) {
        this.configure = configure;
        return this;
    }

    /**
     * 开始裁剪-activity
     *
     * @param activity
     */
    @Override
    public void crop(AppCompatActivity activity) {

        createDestUri(activity);
        verify();

        UCrop.of(this.srcUri, this.destUri)
                .withOptions(getOptions())
                .withMaxResultSize(configure.getMaxWidth(), configure.getMaxHeight())
                .start(activity);
    }


    /**
     * 开始裁剪-fragment
     *
     * @param fragment
     */
    @Override
    public void crop(Fragment fragment) {

        createDestUri(fragment.getActivity());
        verify();

        UCrop.of(this.srcUri, this.destUri)
                .withOptions(getOptions())
                .withMaxResultSize(configure.getMaxWidth(), configure.getMaxHeight())
                .start(Objects.requireNonNull(fragment.getActivity()), fragment);
    }

    private void verify() {

        if (this.configure == null) {
            this.configure = CropConfigure.getDefault();
        }

        if (this.listener == null) {
            throw new RuntimeException("必须添加裁剪监听，否则无法获取到裁剪图片路径");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == UCrop.REQUEST_CROP) {

                handleCropResult(data);
            }
        }

        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    /**
     * 配置Ucrop的options
     *
     * @return
     */
    private UCrop.Options getOptions() {
        UCrop.Options options = new UCrop.Options();

        //图片压缩质量
        options.setCompressionQuality(this.configure.getCompressQuality());

        //图片可以各种比例
        options.setFreeStyleCropEnabled(true);
        return options;
    }

    /**
     * 裁剪完成解析data
     *
     * @param data
     */
    private void handleCropResult(Intent data) {

        final Uri resultUri = UCrop.getOutput(data);
        if (resultUri != null) {
            this.listener.onCropExecute(resultUri);
        } else {
            this.listener.onFail("无法找到裁剪的图片地址");
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(space.learning.baselibrary.constant.Constants.LOG_TAG, "handleCropError: " + cropError.getMessage());
        } else {

        }
    }

    private void createDestUri(Context context) {
        this.destUri = getCropFile(context);
    }

    //utils
    public Uri getCropFile(Context context) {
        String timeStamp = new Date().getTime() + "";
        File dir = new File(Constants.SDcardRoot + File.separator + context.getPackageName() + Constants.cropDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = null;
        try {
            file = File.createTempFile("Crop_" + timeStamp, ".jpg", dir);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return Uri.fromFile(file);
    }
}
