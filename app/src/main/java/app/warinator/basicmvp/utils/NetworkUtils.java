package app.warinator.basicmvp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import app.warinator.basicmvp.data.network.ShowsAPI;

/**
 * Network utilities
 */

public class NetworkUtils {
    private NetworkUtils(){}

    public static boolean isConnectedToNetwork(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String getPosterURL(String poster_path){
        return ShowsAPI.POSTER_BASE+poster_path;
    }
}
