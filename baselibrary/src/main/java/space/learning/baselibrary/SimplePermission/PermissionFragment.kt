package space.learning.baselibrary.SimplePermission

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*


class PermissionFragment : Fragment() {

    var mCallBacks = SparseArray<IPermissionListenerWrap.IPermissionListener>()
    var random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //configuration change保存状态，不会重新创建
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_permission, container, false)
        return null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PermissionFragment()
    }

    fun requestPermissions(permissions: Array<String>, callBack: IPermissionListenerWrap.IPermissionListener) {

        var requestCode = createRequestCode()
        mCallBacks.put(requestCode, callBack)
        requestPermissions(permissions, requestCode)

    }

    /**
     * 随机创建 requestcode
     */
    private fun createRequestCode(): Int {

        var requestCode: Int
        var cycleCount = 0

        do {
            requestCode = random.nextInt(0x0000FFFF)
            cycleCount++

        } while (mCallBacks.indexOfKey(requestCode) >= 0 && cycleCount < 10)

        return requestCode
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        handlePermissionCallBack(requestCode, permissions, grantResults)

    }

    private fun handlePermissionCallBack(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        var callBack: IPermissionListenerWrap.IPermissionListener? = mCallBacks.get(requestCode)
                ?: return

        var length = permissions.size

        for (i in 0 until length) {

            var name = permissions[i]
            var grantResult = grantResults[i]

            if (grantResult == PackageManager.PERMISSION_GRANTED) {

                callBack?.onAccepted(Permission(name, true))
            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, name)) {
                    //可以显示解释，denied
                    callBack?.onAccepted(Permission(name, false, true))
                } else {
                    //不再显示解释，denied
                    callBack?.onAccepted(Permission(name, false, false))
                }
            }

        }

    }
}
