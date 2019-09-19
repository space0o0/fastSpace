package com.zeyjr.bmc.std.picture

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.AlertDialog
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
import space.learning.baselibrary.photoChooser.bean.PhotoError
import space.learning.baselibrary.photoChooser.listener.IPhotoChooserListener
import space.learning.baselibrary.photoChooser.utils.PhotoUtils
import space.learning.baselibrary.photoCrop.CropManager
import space.learning.baselibrary.photoCrop.listener.CropListener
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PhotoChooserFragment(activity: FragmentActivity) : Fragment() {

    val GALLERY_REQUESTCODE = 80
    val TAKE_PHOTO_REQUESTCODE = 81
    val AUTHORITY = "space.learning.baselib.provider"

    private lateinit var callBack: IPhotoChooserListener
    var mActivity: FragmentActivity = activity

    lateinit var mCurrentUri: String

    var shouldCrop = true//允许crop

    var manager: CropManager? = null

    init {
        mActivity.supportFragmentManager
            .beginTransaction()
            .add(this, "PhotoChooserFragment")
            .commitAllowingStateLoss()

        mActivity.supportFragmentManager.executePendingTransactions()
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
        fun newInstance(activity: FragmentActivity) = PhotoChooserFragment(activity)
    }

    fun setListener(listener: IPhotoChooserListener): PhotoChooserFragment {
        this.callBack = listener
        return this;
    }

    /**
     * 直接跳转拍照
     */
    fun fromTakePhoto(crop: Boolean) {
        this.shouldCrop = crop

        dispatchTakePictureIntent()
    }

    /**
     * 直接跳转相册选择
     */
    fun fromGallery(crop: Boolean) {
        this.shouldCrop = crop

        dispatchGalleryIntent()
    }

    /**
     * 让用户选择从哪里选择
     */
    fun fromAll(crop: Boolean) {
        this.shouldCrop = crop

        createSelectDialog(mActivity)
    }


    /**
     * 选择图库/拍照
     */
    private fun createSelectDialog(activity: FragmentActivity) {

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
                callBack.onPhotoChooserExecute(Uri.parse(mCurrentUri))
            }

        } else if (requestCode == GALLERY_REQUESTCODE) {
            //图库照片

            if (shouldCrop) {
                val uri = data?.data
                ucropImg(uri!!)

            } else {
                if (data?.data != null) {
                    callBack.onPhotoChooserExecute(
                        PhotoUtils.getImagePathFromURI(
                            activity,
                            data.data!!
                        )
                    )
                }
            }
        }

        manager?.activityResult(requestCode, resultCode, data)

        Log.d("", data.toString())
    }

    fun ucropImg(uri: Uri) {

        manager = CropManager.getInstance(uri).setCropListener(object : CropListener {
            override fun onCropExecute(path: Uri?) {
                callBack.onPhotoChooserExecute(path)
            }

            override fun onFail(error: String?) {
                callBack.onPhotoChooserFial(PhotoError(error))
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

}