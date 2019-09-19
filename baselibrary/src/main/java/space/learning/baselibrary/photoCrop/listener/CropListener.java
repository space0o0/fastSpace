package space.learning.baselibrary.photoCrop.listener;

import android.net.Uri;

public interface CropListener {

    void onCropExecute(Uri path);

    void onFail(String error);

}
