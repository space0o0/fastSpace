package space.learning.myui.split

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import space.learning.myui.R
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class SplitView : View {

    var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

    var grains: ArrayList<Grain> = ArrayList<Grain>()

    var d = 6f

    var mPaint: Paint = Paint()

    var animator: ValueAnimator

    var sampleInterval = 4

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {

        for (i in 0 until bitmap.width step sampleInterval) {
            for (j in 0 until bitmap.height step sampleInterval) {

                grains.add(Grain().apply {
                    color = bitmap.getPixel(i, j)

                    vx =
                        ((-1).toDouble().pow(ceil(Math.random() * 1000)) * 20 * Math.random()).toFloat()

                    vy = rangInt(-15, 35).toFloat()

                    ax = 0f
                    ay = 0.98f

                    x = i * d / sampleInterval + d / 2
                    y = j * d / sampleInterval + d / 2
                    r = d / 2
                })
            }
        }

        animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 2000
        animator.repeatCount = -1
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {

            Log.d("", "value = ${it.values}")
            updateGrains()
            invalidate()
        }
    }

    private fun rangInt(i: Int, j: Int): Int {
        val max = max(i, j)
        val min = min(i, j) - 1
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (min + ceil(Math.random() * (max - min))).toInt()
    }

    private fun updateGrains() {

        for (i in 0 until grains.size) {

            grains[i].x += grains[i].vx
            grains[i].y += grains[i].vy

            grains[i].vx += grains[i].ax
            grains[i].vy += grains[i].ay
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (animator.isStarted) {
            for (i in 0 until grains.size) {
                mPaint.color = grains[i].color
                canvas?.drawCircle(grains[i].x, grains[i].y, grains[i].r, mPaint)
            }
        } else {
            canvas?.drawBitmap(bitmap, grains[0].x, grains[0].y, mPaint)
        }


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_DOWN) {
            animator.start()
        }

        return super.onTouchEvent(event)
    }


}