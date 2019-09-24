package space.learning.baselib.useIntentTask

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_intent_task.*
import space.learning.baselib.R
import space.learning.baselib.constant.Constants
import space.learning.baselibrary.intentTask.IntentTaskManager

class IntentTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_task)

        val task = IntentTaskManager.getInstance().getTask(this)

        Log.d(Constants.LOG_TAG, "get activity's task ${task.taskCode}")


        var taskFragment = TaskFragment.newInstance("", "")
        supportFragmentManager.beginTransaction().add(contentLayout.id, taskFragment)
            .commitAllowingStateLoss()

        //fragment能否获取到activity的intent

//        Intent(this,MainActivity::class.java).setAction()

//        IntentTaskManager.from(this)
//        .to(MainActivity.class)
//        .withFragment(fragment.class)
//        .task(1).go()


    }
}
