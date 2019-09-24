package space.learning.baselib

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import space.learning.baselib.useIntentTask.IntentTaskActivity
import space.learning.baselibrary.intentTask.IntentTaskManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button6.setOnClickListener {
            IntentTaskManager.getInstance()
                .from(this)
                .goTo(IntentTaskActivity::class.java)
                .task(1)
                .go()
        }
    }
}
