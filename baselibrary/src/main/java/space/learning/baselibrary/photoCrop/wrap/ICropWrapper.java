package space.learning.baselibrary.photoCrop.wrap;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

public interface ICropWrapper {

    void crop(AppCompatActivity activity);

    void crop(Fragment fragment);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

}
