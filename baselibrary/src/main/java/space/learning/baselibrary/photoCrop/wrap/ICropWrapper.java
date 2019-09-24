package space.learning.baselibrary.photoCrop.wrap;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import org.jetbrains.annotations.Nullable;

public interface ICropWrapper {

    void crop(AppCompatActivity activity);

    void crop(Fragment fragment);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

}
