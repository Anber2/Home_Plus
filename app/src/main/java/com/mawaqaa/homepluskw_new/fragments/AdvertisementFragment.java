package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by JijoCJ on 2/25/2017.
 */

public class AdvertisementFragment extends HomePlusBaseFragment implements View.OnClickListener {
    public static final String TAG = "AdvertisementFragment";
    ImageView txtSkip;
    ViewPager viewPagerAdvertisement;
    CirclePageIndicator circleIndicator;

    boolean isAppFirstTime;
    SlideShowListAdapter mAdapter;
    List<String> bannerList;
    Timer timer;
    int page = 0;
    Boolean buttonclickstate = false;

    public static AdvertisementFragment newInstance(String isApp, boolean isAppFirstTime) {
        AdvertisementFragment advertisemetfrag = new AdvertisementFragment();
        advertisemetfrag.isAppFirstTime = isAppFirstTime;
        return advertisemetfrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate advertisement");
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_advertisement, container, false);
        ((HomePlusMainActivity) getActivity()).hideActionBar();
        ((HomePlusMainActivity) getActivity()).hideBottomBar();
        initView(v);

        return v;
    }

    private void initView(View v) {
        txtSkip = (ImageView) v.findViewById(R.id.txtSkipad);
        viewPagerAdvertisement = (ViewPager) v.findViewById(R.id.viewPagerAdvertisementad);
        circleIndicator = (CirclePageIndicator) v.findViewById(R.id.circleIndicatorad);
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity, txtSkip);
        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, txtSkip);
        }
        txtSkip.setOnClickListener(this);
        if (Activity.isNetworkAvailable()) {
            buttonclickstate = false;
            fetchSlideShowData();
        } else {
            Toast.makeText(Activity, getResources().getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
        }
    }

    private void fetchSlideShowData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.ISFIRST, "1");
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
//            startSpinwheel(false, true);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSkipad:
                if (buttonclickstate) {
                    Activity.popFragments(this);
                    if (PreferenceUtil.isKeepMeSignedIn(this.getActivity())) {
                        Log.e("ADV", "Login true");
                        Activity.pushFragments(new HomeFragment(), false, true);
                    } else {
                        Log.e("ADV", "Login false");
                        Activity.pushFragments(new PreHomeFragment(), false, true);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSlidShowBannerLoadedSuccessfully(JSONObject jsonObject) {
        super.onSlidShowBannerLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        parseJsonData(jsonObject);
    }

    private void parseJsonData(JSONObject jsonObject) {
        try {
            buttonclickstate = true;
            Log.d("Json::", "" + jsonObject);
            if (jsonObject != null) {
                JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.BANNER_LIST);
                if (jsonArray.length() > 0) {
                    bannerList = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        bannerList.add(jsonArray.getJSONObject(j).getString(AppConstants.URL));
                    }
                    mAdapter = new SlideShowListAdapter(Activity, bannerList);
                    viewPagerAdvertisement.setAdapter(mAdapter);
                    circleIndicator.setViewPager(viewPagerAdvertisement);
                    pageSwitcher(1);
                } else {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseApplication.getInstance().progressOFF();
        }
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 8000); // delay
        // in
        // milliseconds
    }


    @Override
    public void onSlidShowBannerLoadedFailed(JSONObject jsonObject) {
        super.onSlidShowBannerLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
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
                        Log.d(TAG, "pager" + page);
                    }
                    viewPagerAdvertisement.setCurrentItem(page++);
                }
            });
        }
    }
}