package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.HighlightsImageAdaptersPartner;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.PartnersData;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.meetme.android.horizontallistview.HorizontalListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JijoCJ on 1/4/2017.
 */
public class AboutUsPartnersFrag extends HomePlusBaseFragment implements View.OnClickListener {
    public static final String TAG = "Partnersfrg";
    WebView webviewPartners;
    ImageButton imgbtn_right_partner, imgbtn_left_partner;
    Button btn_corporateView, btn_partners;
    String contactInfo;
    HorizontalListView horizontalListView;
    ViewPager viewPagerAboutusPartners;
    Boolean isOnePressed = false, isSecondPlace = false;
    boolean check = false;
    PartnersData partnersData;
    HighlightsImageAdaptersPartner partnersAdapter;
    ArrayList<PartnersData> partners_List;
    List<String> realEstateSponsersList;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    Timer timer;
    int page = 0;
    final Handler handler = new Handler();
    public Timer swipeTimer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Activity = (HomePlusBaseActivity) this.getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume" + this.getClass().getName());
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        if (swipeTimer != null) {
            swipeTimer.cancel();
            // stopTimer();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_partners_new, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.btn_txt_partners));
        initView(rootView);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.MoreButtonState();
        return rootView;
    }

    private void initView(View rootView) {
        //horizontalListView=(HorizontalListView)rootView.findViewById(R.id.HorizontalListView) ;
        viewPagerAboutusPartners = (ViewPager) rootView.findViewById(R.id.viewPagerpartner);
        webviewPartners = (WebView) rootView.findViewById(R.id.webView_partners);
        btn_corporateView = (Button) rootView.findViewById(R.id.btnCorporateview);
        btn_partners = (Button) rootView.findViewById(R.id.btnTabPartners);
        //imgbtn_right_partner = (ImageButton) rootView.findViewById(R.id.imgbtn_right_partner);
        //imgbtn_left_partner = (ImageButton) rootView.findViewById(R.id.imgbtn_left_partner);
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity, webviewPartners);
            FontUtils.setHomeplusRegularFont(Activity, btn_corporateView);
            FontUtils.setHomeplusRegularFont(Activity, btn_partners);
            btn_corporateView.setSelected(false);
            btn_partners.setSelected(true);

        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, webviewPartners);
            FontUtils.setHomeplusArabicRegularFont(Activity, btn_corporateView);
            FontUtils.setHomeplusArabicRegularFont(Activity, btn_partners);
            btn_corporateView.setSelected(false);
            btn_partners.setSelected(true);
        }
        //viewPagerAboutusPartners=(ViewPager)rootView.findViewById(R.id.viewPagerAboutusPartners);
       /* if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)){
            btn_corporateView.setBackgroundResource(R.drawable.button_curved_yellow);
            btn_partners.setBackgroundResource(R.drawable.button_curved_white);
        }else{
            btn_corporateView.setBackgroundResource(R.drawable.button_curved_yellow);
            btn_partners.setBackgroundResource(R.drawable.button_curved_left_white);
        }
*/
        btn_corporateView.setOnClickListener(this);
        btn_partners.setOnClickListener(this);
        //imgbtn_right_partner.setOnClickListener(this);
        //imgbtn_left_partner.setOnClickListener(this);
        fetchRequestFormData();


    }

    private void fetchRequestFormData() {

        try {
            if (Activity.isNetworkAvailable()) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                Log.e(TAG, "Json Req:" + jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_PARTNERS, jsonObject);
            } else {
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAboutusPartnersRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onAboutusPartnersRequestDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseServiceRequestLoadData(jsonObject);
    }

    private void parseServiceRequestLoadData(JSONObject jsonObject) {
        if (jsonObject != null) {
            Log.d("json::", "" + jsonObject);
            try {
                // partners_List=new ArrayList<>();
                realEstateSponsersList = new ArrayList<>();
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    Log.e("html...contact", "" + jsonObject.getString(AppConstants.MESSAGE));
                    contactInfo = jsonObject.getString(AppConstants.MESSAGE);
                    webviewPartners.getSettings().setJavaScriptEnabled(true);
                    if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ARABIC)) {
                        Log.e("BVBV", "here");
                        contactInfo = FontUtils.getHtmlDatawithArabicFont(Activity, contactInfo);
                        webviewPartners.loadDataWithBaseURL(null,
                                contactInfo, "text/html", "UTF-8", "");
                    } else {
                        webviewPartners.loadDataWithBaseURL(null,
                                FontUtils.getHtmlDatawithEnglishFont(Activity, contactInfo), "text/html", "UTF-8", "");

                    }
                    //webviewPartners.loadData(contactInfo, "text/html; charset=utf-8", "UTF-8");
                    JSONArray jsonarray_partners = jsonObject.getJSONArray(AppConstants.PARTNERS_HOMECLIENTS_ARRAY);

                    for (int i = 0; i < jsonarray_partners.length(); i++) {
                        //  realEstateSponsersList.add(jsonArraySponser.getJSONObject(i).getString(AppConstants.IMAGE));
                        Log.e("partnersurl", "" + jsonarray_partners);
                        realEstateSponsersList.add(jsonarray_partners.getJSONObject(i).getString(AppConstants.PARNERS_IMAGE));
                    }
                    partnersAdapter = new HighlightsImageAdaptersPartner(Activity, realEstateSponsersList);
                    viewPagerAboutusPartners.setAdapter(partnersAdapter);
                    partnersAdapter.notifyDataSetChanged();
                    pageSwitcher(4);
                    // pageSwitcher(1);
                    NUM_PAGES = realEstateSponsersList.size();
                    // Auto start of viewpager
                  /*  final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == NUM_PAGES) {
                                currentPage = 0;
                            }
                            viewPagerAboutusPartners.setCurrentItem(currentPage++, true);
                        }
                    };
                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, 2000, 4000);*/


                } else {
                    contactInfo = getResources().getString(R.string.nodatahtml);
                    webviewPartners.getSettings().setJavaScriptEnabled(true);
                    webviewPartners.loadData(contactInfo, "text/html; charset=utf-8", "UTF-8");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(Activity, "Error, Please try again", Toast.LENGTH_LONG).show();
        }
    }

    private void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.

            Activity.runOnUiThread(new Runnable() {
                public void run() {

                    if (page > getPageCount(realEstateSponsersList.size()) - 1) { // In my case the number of pages are 5
                        page = 0;
                        Log.d(TAG, "pager" + page);
                    }
                    viewPagerAboutusPartners.setCurrentItem(page++);

                }
            });

        }
    }

    public int getPageCount(int count) {
        int pageCount, reminder;
        pageCount = count / 3;
        reminder = count % 3;
        if (reminder > 0) {
            pageCount = pageCount + 1;
        }
        Log.e("PAGE COUNT", ">>>>>>>>>>>>>>>" + pageCount);
        return pageCount;
    }

    @Override
    public void onAboutusPartnersRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onAboutusPartnersRequestDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCorporateview:
                Activity.pushFragments(new AboutUsFragment(), false, true);
                break;
            case R.id.btnTabPartners:
                Activity.pushFragments(new AboutUsPartnersFrag(), false, true);
                break;
            case R.id.imgbtn_right_partner:
                horizontalListView.scrollTo(500);

                break;
            case R.id.imgbtn_left_partner:
                Log.e(TAG, "<<<<<pre");
                horizontalListView.scrollTo(-500);

                break;

        }

    }

}
