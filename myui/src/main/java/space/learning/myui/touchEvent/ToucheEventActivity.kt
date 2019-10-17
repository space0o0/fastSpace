package space.learning.myui.touchEvent

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import space.learning.myui.R

class ToucheEventActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touche_event)

    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        Log.d(javaClass.simpleName, "dispatchTouchEvent: ${MotionEvent.actionToString(ev!!.action)}")

        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        Log.d(javaClass.simpleName, "onTouchEvent: ")

        return super.onTouchEvent(event)
    }

}
