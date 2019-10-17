package space.learning.baselibrary.SimplePermission

interface IPermissionListenerWrap {

    interface IPermissionListener {
        fun onAccepted(permission: Permission)
        fun onException(throwable: Throwable)
    }
}