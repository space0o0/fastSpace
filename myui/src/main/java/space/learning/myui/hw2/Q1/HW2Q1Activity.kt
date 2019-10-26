package space.learning.myui.hw2.Q1

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import space.learning.myui.R

//图片状态栏沉浸式
class HW2Q1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hw2_q1)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            //1、设置状态栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            window.statusBarColor = Color.TRANSPARENT
            //2、设置内容全屏显示
            var uiVisibility = window.decorView.systemUiVisibility
            uiVisibility = uiVisibility or  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            uiVisibility = uiVisibility or  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.visibility = uiVisibility
        }else{
        }

    }
}
