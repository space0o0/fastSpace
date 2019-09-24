package space.learning.baselibrary.network_subscriber.jobSchedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;


import space.learning.baselibrary.constant.Constants;

/**
 * @author space
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ConnectivityJob extends JobService {

    private ConnectivityManager connectivityManager;

    private ConnectivityManager.NetworkCallback networkCallback;

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(Constants.LOG_TAG, "startJob");

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);

                Log.d(Constants.LOG_TAG, "onAvailable");
            }
        };

        connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), networkCallback);


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        connectivityManager.unregisterNetworkCallback(networkCallback);

        return true;
    }
}
