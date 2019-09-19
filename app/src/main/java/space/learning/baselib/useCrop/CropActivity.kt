package space.learning.baselib.useCrop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
