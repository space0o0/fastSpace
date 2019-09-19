package space.learning.baselibrary.network_subscriber.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import space.learning.baselibrary.network_subscriber.enums.NetType;
import space.learning.baselibrary.network_subscriber.NetWorkManager;

public class NetUtils {

    public static NetType getNetType() {

        ConnectivityManager connectivityManager = (ConnectivityManager) NetWorkManager.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return NetType.NONE;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return NetType.NONE;
        }

        int type = networkInfo.getType();

        if (type == ConnectivityManager.TYPE_MOBILE) {
            return NetType.PHONE;
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }

        return NetType.NONE;
    }
}
