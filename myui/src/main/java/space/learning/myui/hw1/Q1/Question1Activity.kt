package space.learning.myui.hw1.Q1

import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_question1.*
import space.learning.myui.R

class Question1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question1)

        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic1)
        image.setImageBitmap(bitmap)

        var copyBitmap = bitmap.copy(Bitmap.Config.RGB_565, true)
        image2.setImageBitmap(copyBitmap)

        val colorMatrixColorFilter = getFilter2()

        var canvas = Canvas(copyBitmap)
        var paint = Paint()

        paint.colorFilter = colorMatrixColorFilter
        canvas.drawBitmap(copyBitmap, 0f, 0f, paint)
    }

    //黑白色矩阵改变图片颜色
    private fun getFilter(): ColorMatrixColorFilter {
        var src = floatArrayOf(
            0.8f, 1.6f, 0.2f, 0f, -163.9f,
            0.8f, 1.6f, 0.2f, 0f, -163.9f,
            0.8f, 1.6f, 0.2f, 0f, -163.9f,
            0f, 0f, 0f, 1.0f, 0f
        )
        val cm = ColorMatrix(src)
        return ColorMatrixColorFilter(cm)
    }

    //修改图片饱和度
    private fun getFilter2(): ColorMatrixColorFilter {
        var cm2 = ColorMatrix()
        cm2.setSaturation(0f)
        return ColorMatrixColorFilter(cm2)
    }
}
