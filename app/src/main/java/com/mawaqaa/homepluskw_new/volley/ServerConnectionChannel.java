package com.mawaqaa.homepluskw_new.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerConnectionChannel {
	
	private static final String TAG = "ServerConnectionChannel";
	private int BABTAIN_BACKOFF_MULT = 2;
	private int BABTAIN_MAX_RETRIES = 2;

	
	public ServerConnectionChannel() {
	}

	public void doSendJsonRequest(HpRequest tabdealRequest) {
		RequestQueue queue = VolleyUtils.getRequestQueue();
		try {
			JSONObject jsonObject = tabdealRequest.jsonObject;

			ExpoJsonRequest myReq = new ExpoJsonRequest(
					tabdealRequest.method, tabdealRequest.mReqUrl,
					jsonObject, createReqSuccessListener(tabdealRequest),
					createReqErrorListener(tabdealRequest)){
				protected Map<String, String> getParams()
						throws AuthFailureError {
					Map<String, String> params = new HashMap<String, String>();
					params.put("Content-Type", "text/json");
					return params;
				}
			};
			
			myReq.setRetryPolicy(new DefaultRetryPolicy(
					DefaultRetryPolicy.DEFAULT_TIMEOUT_MS *20 , 
					BABTAIN_MAX_RETRIES, 
	                BABTAIN_BACKOFF_MULT)); 
			
			
			//myReq.setHeader("Cache-Control", "no-cache");
			//myReq.setHeader("Content-Type", "text/json");
			
			queue.add(myReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private ErrorListener createReqErrorListener(final HpRequest tabdealRequest) {
		return new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "ReqErrorListener"+error.getMessage());
				HpResponse hpResponse = new HpResponse();
				hpResponse.mReqUrl = tabdealRequest.mReqUrl;
				HomePlusBaseActivity.getHpBaseActivity().serviceResponseError(hpResponse);
			}
		};
	}

	private Listener<JSONObject> createReqSuccessListener(final HpRequest tabdealRequest) {
		return new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, "ReqSuccessListener :"+tabdealRequest.mReqUrl);
				HpResponse hpResponse = new HpResponse();
				hpResponse.mReqUrl = tabdealRequest.mReqUrl;
				hpResponse.jsonObject = response;
				HomePlusBaseActivity.getHpBaseActivity().serviceResponseSuccess(hpResponse);
			}
		};
	}
}
