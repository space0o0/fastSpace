package space.learning.myui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import space.learning.myui.split.SplitView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(SplitView(this))
    }

}
