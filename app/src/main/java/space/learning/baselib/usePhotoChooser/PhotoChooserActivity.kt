package space.learning.baselib.usePhotoChooser

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_photo_chooser.*
import space.learning.baselib.R
import space.learning.baselibrary.photoChooser.PhotoChooser
import space.learning.baselibrary.photoChooser.bean.PhotoError
import space.learning.baselibrary.photoChooser.listener.IPhotoChooserListener

class PhotoChooserActivity : AppCompatActivity() {

    val TAG = "PhotoChooserActivity@#"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_chooser)

        val listener = object : IPhotoChooserListener {

            override fun onPhotoChooserExecute(uri: Uri?) {
                Log.d(TAG, "获取到的uri $uri")
                imageView.setImageURI(uri)
            }

            override fun onPhotoChooserFial(error: PhotoError?) {
            }
        }

        button3.setOnClickListener {

            PhotoChooser.newInstance(this)
                .addListener(listener)
                .setCrop(checkBox.isChecked)
                .showTakePhoto()
        }

        button4.setOnClickListener {

            PhotoChooser.newInstance(this)
                .addListener(listener)
                .setCrop(checkBox.isChecked)
                .showGallery()
        }

        button5.setOnClickListener {

            PhotoChooser.newInstance(this)
                .addListener(listener)
                .setCrop(checkBox.isChecked)
                .show()
        }

    }
}
