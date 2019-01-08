package com.mawaqaa.homepluskw_new.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyUtils {

	private static RequestQueue mRequestQueue;
	private static ServerConnectionChannel mServerConnectionChannel;
	public static final boolean volleyEnabled = true;

	public static void init(Context context) {
		if (mRequestQueue == null)
			mRequestQueue = Volley.newRequestQueue(context);
		if (mServerConnectionChannel == null)
			mServerConnectionChannel = new ServerConnectionChannel();
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}
	
	public static ServerConnectionChannel getServerConnectionChannel() {
		if (mServerConnectionChannel != null) {
			return mServerConnectionChannel;
		} else {
			throw new IllegalStateException("mServerConnectionChannel not initialized");
		}
	}

}
