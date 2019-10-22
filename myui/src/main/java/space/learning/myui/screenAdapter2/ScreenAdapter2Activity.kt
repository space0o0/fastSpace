package space.learning.myui.screenAdapter2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import space.learning.myui.R

class ScreenAdapter2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DensityUtils.setDensity(this.application, this)
        setContentView(R.layout.activity_screen_adapter2)
    }
}
