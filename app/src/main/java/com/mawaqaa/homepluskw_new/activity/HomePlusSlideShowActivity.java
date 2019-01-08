package com.mawaqaa.homepluskw_new.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.adapter.SlideShowListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.views.CirclePageIndicator;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JijoCJ on 10/26/2016.
 */
public class HomePlusSlideShowActivity extends HomePlusBaseActivity {

    private static final String TAG = "HpSlideShowActivity";
    ImageView txtSkip;
    ViewPager viewPagerSlideShow;
    CirclePageIndicator circleIndicator;
    SlideShowListAdapter mAdapter;
    List<String> bannerList;
    Timer timer;
    int page = 0;
    String isapp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_plus_slide_show);

        txtSkip = (ImageView) findViewById(R.id.txtSkip);
        viewPagerSlideShow = (ViewPager) findViewById(R.id.viewPagerSlideShow);
        circleIndicator = (CirclePageIndicator) findViewById(R.id.circleIndicator);

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePlusMainActivity.class);
                startActivity(intent);
            }
        });

        if(isNetworkAvailable()) {
            /*if (PreferenceUtil.isAppFirstTime(this)) {
                isapp = "1";
            } else {
                isapp = "0";
            }*/
            fetchSlideShowData();
        }
        else
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void fetchSlideShowData() {
        try{
            Log.e("insidefetch",isapp);
                JSONObject jsonObject = new JSONObject();
           // jsonObject.putOpt(AppConstants.ISFIRST, isapp);
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(getApplicationContext()));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, "WEBNASECURITY");
                //startSpinwheel(false, true);
            if (VolleyUtils.volleyEnabled) {
                RequestQueue queue = VolleyUtils.getRequestQueue();
                Log.e(TAG,"Change JsonObj: "+jsonObject.toString());
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST, AppConstants.GET_SLIDSHOW_BANNER, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e(TAG,
                                        "Response : " + response.toString());
                                onSlidShowBannerLoadedSuccessfully(response);
                            }


                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Response : Error");

                    }
                });
                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, 2));
                queue.add(jsObjRequest);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onSlidShowBannerLoadedSuccessfully(JSONObject response){
        try{
            Log.e(TAG," response:"+response);
            if(response != null){
                JSONArray jsonArray = response.getJSONArray(AppConstants.BANNER_LIST);
                if(jsonArray.length() > 0){
                    bannerList = new ArrayList<>();
                    for(int j = 0; j < jsonArray.length(); j++){
                        bannerList.add(jsonArray.getJSONObject(j).getString(AppConstants.URL));
                    }
                    mAdapter = new SlideShowListAdapter(this, bannerList);
                    viewPagerSlideShow.setAdapter(mAdapter);
                    circleIndicator.setViewPager(viewPagerSlideShow);
                    pageSwitcher(1);
                   PreferenceUtil.setTutorialShowStatus(this, false);
                }else{
                    PreferenceUtil.setTutorialShowStatus(this, false);
                }
            }
           /* PreferenceUtil.setAppFirstTimeStatus(this,true);
            Log.e("insideSucess",isapp);*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 8000); // delay
        // in
        // milliseconds
    }
    // this is an inner class...
    class RemindTask extends TimerTask {
        @Override
        public void run() {
            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {
                    if (page > bannerList.size()) { // In my case the number of pages are 5
                        page=0;
                    }
                    viewPagerSlideShow.setCurrentItem(page++);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
