package space.learning.baselib.useCrop

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import space.learning.baselib.R
import space.learning.baselibrary.photoCrop.CropManager

class CropActivity : AppCompatActivity() {

    lateinit var manager: CropManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        manager.activityResult(requestCode, resultCode, data)
    }
}
