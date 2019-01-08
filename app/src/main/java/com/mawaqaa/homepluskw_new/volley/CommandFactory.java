package com.mawaqaa.homepluskw_new.volley;

import com.android.volley.Request.Method;

import org.json.JSONObject;

public class CommandFactory {

	public void sendgetCommand(String url) {

		ServerConnectionChannel serverConnectionChannel = VolleyUtils
				.getServerConnectionChannel();
		serverConnectionChannel.doSendJsonRequest(createGetRequest(url));

	}
	
	public void sendPostCommand(String url, JSONObject jsonObject) {

		ServerConnectionChannel serverConnectionChannel = VolleyUtils
				.getServerConnectionChannel();
		serverConnectionChannel.doSendJsonRequest(createPostRequest(url, jsonObject));

	}

	
	/*method for get method*/
	private HpRequest createGetRequest(String url) {
		HpRequest babtainRequest = new HpRequest();
		babtainRequest.method = Method.GET;
		babtainRequest.mReqUrl = url;
		return babtainRequest;

	}

	/*method for post method*/
	private HpRequest createPostRequest(String url, JSONObject jsonObject) {
		HpRequest babtainRequest = new HpRequest();
		babtainRequest.method = Method.POST;
		babtainRequest.mReqUrl = url;
		babtainRequest.jsonObject = jsonObject;

		return babtainRequest;

	}

}
