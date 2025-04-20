package com.example.wordup.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkReceiver extends BroadcastReceiver {

    public interface NetworkStateListener {
        void onNetworkChanged(boolean isConnected);
    }

    private static NetworkStateListener listener;

    public static void setListener(NetworkStateListener networkListener) {
        listener = networkListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
            if (listener != null) {
                listener.onNetworkChanged(isConnected);
            }
        }
    }
}
