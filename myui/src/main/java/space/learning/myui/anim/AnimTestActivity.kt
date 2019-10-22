package space.learning.myui.anim

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_anim_test.*
import space.learning.myui.R
import space.learning.myui.anim.lib.MyObjectAnimator
import space.learning.myui.anim.lib.interpolator.LinearInterpolator

class AnimTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim_test)

        val anim = MyObjectAnimator.ofFloat(textView, "ScaleX", 1f, 2f, 4f, 1f)
        anim.setDuration(3000)
        anim.setInterpolator(LinearInterpolator())
        anim.start()

        var bitmap = null
        var canvas = Canvas();
    }
}
