package space.learning.myui

import android.app.Activity
import android.os.Bundle
import space.learning.myui.split.SplitView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(SplitView(this))
    }

}
