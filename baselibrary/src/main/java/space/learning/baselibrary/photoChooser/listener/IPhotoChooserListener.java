package space.learning.baselibrary.photoChooser.listener;

import android.net.Uri;

import space.learning.baselibrary.photoChooser.bean.PhotoError;

public interface IPhotoChooserListener {

    void onPhotoChooserExecute(Uri uri);

    void onPhotoChooserFial(PhotoError error);
}
