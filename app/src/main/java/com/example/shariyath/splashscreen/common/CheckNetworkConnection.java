package com.example.shariyath.splashscreen.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkConnection {

	public static boolean isInternetAvailable(Context context) {

		ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = conMan.getActiveNetworkInfo();

		final boolean connected = networkInfo != null
		&& networkInfo.isAvailable()
		&& networkInfo.isConnectedOrConnecting();

		if ( !connected) {
			return false;
		}

		return true;

	}
}
