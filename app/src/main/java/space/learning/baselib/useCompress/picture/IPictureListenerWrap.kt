package com.zeyjr.bmc.std.picture

import android.graphics.Bitmap

interface IPictureListenerWrap {

    interface IPictureListener {

        fun onExecute(bitmapPath: String)

        fun cropFail()
    }
}