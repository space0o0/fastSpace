package space.learning.baselib.useNetWorkSubscriber

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_net_work_state.*
import space.learning.baselib.R
import space.learning.baselib.constant.Constants
import space.learning.baselibrary.network_subscriber.NetWorkManager
import space.learning.baselibrary.network_subscriber.annotation.NetWorkSubscriber
import space.learning.baselibrary.network_subscriber.enums.NetType

class NetWorkStateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_work_state)

        button.setOnClickListener {

            NetWorkManager.getInstance().register(this)
        }
    }

    @NetWorkSubscriber
    fun netChange(netType: NetType) {

        Log.d(Constants.LOG_TAG, "网络发生了改变 -> ${netType.name} ")
    }
}
