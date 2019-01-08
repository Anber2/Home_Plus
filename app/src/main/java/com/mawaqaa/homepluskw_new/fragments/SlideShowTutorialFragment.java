package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.SlideShowListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.views.CirclePageIndicator;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JijoCJ on 10/27/2016.
 */
public class SlideShowTutorialFragment extends HomePlusBaseFragment implements View.OnClickListener {

    private static final String TAG = "SlideShowTutorialFrag";

    ImageView txtSkip;
    ViewPager viewPagerSlideShow;
    CirclePageIndicator circleIndicator;

    SlideShowListAdapter mAdapter;
    List<String> bannerList;
    Timer timer;
    int page = 0;
    String isapp = "0";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Activity = (HomePlusBaseActivity) this.getActivity();
        // Activity.hideErrorAndProgressImg();
    }

    public void onResume() {
        Log.d(TAG, "onResume" + this.getClass().getName());
        super.onResume();
        ((HomePlusBaseActivity) getActivity()).BaseFragment = this;
        ((HomePlusMainActivity) getActivity()).hideActionBar();
        ((HomePlusMainActivity) getActivity()).hideBottomBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_plus_slide_show, container, false);
        //((ExpoMainActivity) getActivity()).setHeading(getResources().getString(R.string.categories));
        ((HomePlusMainActivity) getActivity()).hideActionBar();
        ((HomePlusMainActivity) getActivity()).hideBottomBar();
        initView(v);
       /* BottomBarButtonClickState dh = (BottomBarButtonClickState)Activity;
        dh.categoriesButtonState();*/
        return v;
    }

    private void initView(View v) {
        txtSkip = (ImageView) v.findViewById(R.id.txtSkip);
        viewPagerSlideShow = (ViewPager) v.findViewById(R.id.viewPagerSlideShow);
        circleIndicator = (CirclePageIndicator) v.findViewById(R.id.circleIndicator);
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusBoldFont(Activity, txtSkip);
        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, txtSkip);
        }
        txtSkip.setOnClickListener(this);

        if (Activity.isNetworkAvailable()) {
            fetchSlideShowData();
        } else
            Toast.makeText(Activity, getResources().getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();

    }

    private void fetchSlideShowData() {
//        Log.e("insidefetch....,.,",isapp);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.ISFIRST, isapp);
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
//            startSpinwheel(false, true);
            Log.d("JSONBanner",jsonObject.toString());
            BaseApplication.getInstance().progressON(getActivity(), null);
            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.GET_SLIDSHOW_BANNER, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSlidShowBannerLoadedSuccessfully(JSONObject jsonObject) {
        super.onSlidShowBannerLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        parseJsonData(jsonObject);
    }

    private void parseJsonData(JSONObject jsonObject) {
        try {
            Log.d("Json::", "" + jsonObject);
            if (jsonObject != null) {
                JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.BANNER_LIST);
                if (jsonArray.length() > 0) {
                    bannerList = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        bannerList.add(jsonArray.getJSONObject(j).getString(AppConstants.URL));
                    }
                    mAdapter = new SlideShowListAdapter(Activity, bannerList);
                    viewPagerSlideShow.setAdapter(mAdapter);
                    circleIndicator.setViewPager(viewPagerSlideShow);
//                      pageSwitcher(1);
                    PreferenceUtil.setTutorialShowStatus(Activity, false);
                } else {
                    PreferenceUtil.setTutorialShowStatus(Activity, false);
                }

                Log.e("insideSucess", isapp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            BaseApplication.getInstance().progressOFF()();
            BaseApplication.getInstance().progressOFF();
        }
    }

    @Override
    public void onSlidShowBannerLoadedFailed(JSONObject jsonObject) {
        super.onSlidShowBannerLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSkip:
                Activity.popFragments(this);
                Activity.pushFragments(new PreHomeFragment(), false, true);
                break;
            default:
                break;
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

            Activity.runOnUiThread(new Runnable() {
                public void run() {

                    if (page > bannerList.size()) { // In my case the number of pages are 5
                        page = 0;
                    }
                    viewPagerSlideShow.setCurrentItem(page++);

                }
            });

        }
    }
}