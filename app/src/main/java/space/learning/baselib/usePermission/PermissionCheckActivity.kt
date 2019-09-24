package space.learning.baselib.usePermission

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_permission_check.*
import space.learning.anno.*
import space.learning.baselib.R
import space.learning.baselib.constant.Constants
import space.learning.baselibrary.permission.PermissionManager
import space.learning.baselibrary.permission.listener.ShowRationalListener

class PermissionCheckActivity : AppCompatActivity() {

    val REQUESTCODE = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_check)

        PermissionManager.getInstance().check(this)

        button2.setOnClickListener {
                        `PermissionCheckActivity$Permission`.openCamera_checkPermission(this)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        `PermissionCheckActivity$Permission`.onRequestPermissionsResult(
            this,
            requestCode,
            permissions,
            grantResults
        )

//        if (!PermissionUtils.hasPermission(this, arrayOf(Manifest.permission.CAMERA))) {
//            //在没有权限的情况下，判断是否需要显示rationale UI
//            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                Log.d(Constants.LOG_TAG, "denied")
//
//            } else {
////                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUESTCODE)
//                Log.d(Constants.LOG_TAG, "never ask again")
//            }
//        }

    }

    @CheckPermission(Manifest.permission.CAMERA)
    fun openCamera() {
        Log.d(Constants.LOG_TAG, "openCamera")
    }

    @PermissionDenied(Manifest.permission.CAMERA)
    fun permissionDenied() {
        Log.d(Constants.LOG_TAG, "denied")
    }

    @ShowRationale(Manifest.permission.CAMERA)
    fun showRationale(listener: ShowRationalListener) {
        var dialog = AlertDialog.Builder(this)
            .setPositiveButton("给") { _, _ ->
                //                requestPermissions(
//                    arrayOf(Manifest.permission.CAMERA),
//                    REQUESTCODE
//                )

                listener.allow()
            }
            .setNegativeButton("不给") { _, _ ->
                listener.denied()
            }
            .setMessage("请求权限申请")
            .create()

        dialog.show()
    }

    @NeverAskAgain(Manifest.permission.CAMERA)
    fun neverShow() {
        Log.d(Constants.LOG_TAG, "neverShow")
    }
}
