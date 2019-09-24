package space.learning.baselib.useCompress

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_comress_img.*
import space.learning.baselib.R
import space.learning.baselibrary.compress.CompressImageManager
import space.learning.baselibrary.compress.bean.Photo
import space.learning.baselibrary.compress.config.CompressConfig
import space.learning.baselibrary.compress.listener.CompressImage
import space.learning.baselibrary.photoChooser.PhotoChooser
import space.learning.baselibrary.photoChooser.bean.PhotoError
import space.learning.baselibrary.photoChooser.listener.IPhotoChooserListener
import java.util.ArrayList

class ComressImgActivity : FragmentActivity() {

    val TAG = "ComressImgActivity@#"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comress_img)

        checkPermission()

        val listener = object : IPhotoChooserListener {

            override fun onPhotoChooserExecute(uri: Uri?) {
                Log.d(TAG, "获取到的uri $uri")

                singleImageCompress(uri!!)
            }

            override fun onPhotoChooserFial(error: PhotoError?) {
            }
        }

        btn1.setOnClickListener {

            PhotoChooser.newInstance(this)
                .addListener(listener)
                .setCrop(true)
                .showTakePhoto()
        }

        btn2.setOnClickListener {

        }
    }

    fun checkPermission() {
        // 运行时权限申请
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val perms =
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            if (checkSelfPermission(perms[0]) === PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    perms[1]
                ) === PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(perms, 200)
            }
        }
    }

    //单图压缩
    fun singleImageCompress(uri: Uri) {

        var photo = Photo(uri.path)

        var config = CompressConfig.builder()
            .setMaxPixel(1000)
            .setMaxSize(300 * 1024)
            .create()

        var listener = object : CompressImage.CompressListener {
            override fun onCompressSuccess(images: ArrayList<Photo>?) {

                Log.d(TAG, images!!.size.toString())

                for (i in 0 until images.size) {
//                    Log.d(TAG, images[i].compressPath)
                    imageView2.setImageURI(Uri.parse(images[0].compressPath))
                }

            }

            override fun onCompressFailed(images: ArrayList<Photo>?, error: String?) {

                Log.d(TAG, error)

            }
        }


        var compressManager = CompressImageManager.build(this, config, arrayListOf(photo), listener)

        compressManager.compress()

    }

    //多图压缩
    fun moreImageCompress() {

    }
}
