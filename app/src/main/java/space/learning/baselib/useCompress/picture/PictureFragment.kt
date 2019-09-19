package com.zeyjr.bmc.std.picture

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import space.learning.baselib.useCompress.picture.PhotoUtils
import space.learning.baselibrary.photoCrop.CropManager
import space.learning.baselibrary.photoCrop.listener.CropListener
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PictureFragment(activity: FragmentActivity) : Fragment() {

    val GALLERY_REQUESTCODE = 80
    val TAKE_PHOTO_REQUESTCODE = 81
    val CROP_FINISH_REQUESTCODE = 82
    val AUTHORITY = "space.learning.baselib";

    lateinit var callBack: IPictureListenerWrap.IPictureListener
    var mActivity: FragmentActivity = activity

    lateinit var mCurrentUri: String

    var shouldCrop = true//允许crop

    lateinit var manager: CropManager

    init {
        //需要检测权限
    }

    fun PictureFragment() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }

    companion object {
        @JvmStatic
        fun newInstance(activity: FragmentActivity) = PictureFragment(activity)
    }

    fun startSelect(activity: FragmentActivity, callBack: IPictureListenerWrap.IPictureListener) {
        this.callBack = callBack
        this.mActivity = activity
        createSelectDialog(this.mActivity)
    }

    fun startSelectNoCrop(
        activity: FragmentActivity,
        callBack: IPictureListenerWrap.IPictureListener
    ) {
        startSelect(activity, callBack)
        this.shouldCrop = false
    }

    fun startSelectFromGallery(
        activity: FragmentActivity,
        callBack: IPictureListenerWrap.IPictureListener
    ) {
        this.mActivity = activity
        this.callBack = callBack
        dispatchGalleryIntent()
    }

    fun startSelectFromGalleryNoCrop(
        activity: FragmentActivity,
        callBack: IPictureListenerWrap.IPictureListener
    ) {
        shouldCrop = false
        startSelectFromGallery(activity, callBack)
    }

    fun startSelectFromTakePhoto(
        activity: FragmentActivity,
        callBack: IPictureListenerWrap.IPictureListener
    ) {
        this.mActivity = activity
        this.callBack = callBack
        dispatchTakePictureIntent()
    }

    /**
     *
     * 选择图库/拍照
     */
    private fun createSelectDialog(activity: Activity) {

        val builder = AlertDialog.Builder(activity)

        val title = "<font color='#EE9A00'>选择头像</font>"
        builder.setTitle(Html.fromHtml(title))

        val types = arrayOf("拍照", "相册")

        builder.setItems(types) { dialog, which ->
            // TODO Auto-generated method stub
            when (types[which]) {
                "拍照" -> dispatchTakePictureIntent()
                "相册" -> dispatchGalleryIntent()
                else -> {
                }
            }
            dialog.dismiss()
        }

        builder.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //开始区分是裁剪还是完成的图片

        if (resultCode == RESULT_CANCELED) {
            return
        }

        if (requestCode == TAKE_PHOTO_REQUESTCODE) {
            //拍照图片

            if (shouldCrop) {

                var uri = FileProvider.getUriForFile(
                    activity?.baseContext!!,
                    AUTHORITY,
                    File(mCurrentUri)
                )

                ucropImg(uri)

            } else {
                callBack.onExecute(mCurrentUri)
            }

        } else if (requestCode == GALLERY_REQUESTCODE) {
            //图库照片

            if (shouldCrop) {
                val uri = data?.data

                ucropImg(uri!!)

            } else {

                if (data?.data != null) {
                    callBack.onExecute(PhotoUtils.getImagePathFromURI(activity, data.data!!))
                }
            }

        } /*else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {

            val uri = UCrop.getOutput(data!!)

            if (uri != null) {
                callBack.onExecute(uri.toString())
            }
        }*/

        if (manager!=null){
            manager.activityResult(requestCode, resultCode, data)
        }

        Log.d("", data.toString())
    }

    fun ucropImg(uri: Uri) {
//        UCrop.of(uri, Uri.fromFile(createCropFile()))
//            .withAspectRatio(16f, 16f)
//            .withMaxResultSize(1080, 1920)
//            .start(mActivity, this)
        manager = CropManager.getInstance(uri).setCropListener(object : CropListener {
            override fun onCropExecute(path: Uri?) {
                callBack.onExecute(path.toString())
            }

            override fun onFail(error: String?) {
            }
        }).start(this)

    }

    private fun createFile(): File {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            //此处记录保存文件的路径
            mCurrentUri = absolutePath
        }
    }

    private fun createCropFile(): File {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "CROP_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            //此处记录保存文件的路径
            mCurrentUri = absolutePath
        }
    }

    /**
     * 跳转拍照intent
     */
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(mActivity.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        mActivity.baseContext,
                        AUTHORITY,
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUESTCODE)
                }
            }
        }
    }

    /**
     * 跳转图库intent
     */
    private fun dispatchGalleryIntent() {

        val intentFromGallery = Intent()
        // 设置文件类型
        intentFromGallery.type = "image/*"
        intentFromGallery.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intentFromGallery, GALLERY_REQUESTCODE)
    }

    @SuppressLint("Recycle")
    private fun getImageContentUri(path: String): Uri {
        val cursor = mActivity.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf<String>(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ",
            arrayOf<String>(path), null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            return Uri.withAppendedPath(baseUri, "" + id)
        } else {
            val contentValues = ContentValues(1)
            contentValues.put(MediaStore.Images.Media.DATA, path)
            return mActivity.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )!!
        }
    }

    private fun checkPermission(): Boolean {
        //检测读写权限

//        if (PermissionHelper.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            PermissionHelper.getInstance(mActivity).requestPermission()
//        }

        return true
    }

    fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {
            val f = File(mCurrentUri)
            it.data = Uri.fromFile(f)
            mActivity.sendBroadcast(it)
        }
    }

}