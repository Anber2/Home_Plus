package com.mawaqaa.homepluskw_new.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.HomefragmentAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.HomeSpecialOfferData;
import com.mawaqaa.homepluskw_new.data.SocialmediaData;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
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
public class HomeFragment extends HomePlusBaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "HomeFragment";
    ImageButton btnConstructionInHome, btnRealEstateInHome;
    Button btnReqProptyService;
    ArrayList<SocialmediaData> medialLink_Array;
    SocialmediaData socialMedia;
    ViewPager homePageViewaPAger;
    ImageView iconlogo, iconlogo1;
    HomefragmentAdapter mAdapter;
    List<HomeSpecialOfferData> homeeSpecialOfferDataList;
    // TextSwitcher textSwitcher, textSwitchertwo;
    TextView textViewBuildingName, textViewBuildingType, textSwitcher, textSwitcher2;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    Timer timer;
    int page = 0;

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
        ((HomePlusMainActivity) getActivity()).hideActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();

        if (timer != null) {
            timer.cancel();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(TAG, "onCview" + this.getClass().getName());
        ((HomePlusMainActivity) getActivity()).hideActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        SendPushNotificationData();
        initView(view);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.homeButtonState();
        // setTexts();
        return view;
    }

    private void setTexts() {
        textViewBuildingType.setText("Appartment");
        textViewBuildingName.setText("3BHK");
    }

    private void FetchSocialmediaUrl() {
        try {
            if (Activity.isNetworkAvailable()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                Log.e(TAG, "Json Request media:" + jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);
                if (VolleyUtils.volleyEnabled) {
                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.GET_SOCIALMEDIA_LINK, jsonObject);
                }
            } else {
//                Toast.makeText(Activity, "testing"+getString(R.string.alert_no_internet), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSocialmediaRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onSocialmediaRequestDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        FetchUrl(jsonObject);
    }

    @Override
    public void onSocialmediaRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onSocialmediaRequestDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    private void FetchUrl(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.HP_SOCIALMEDIA_ARRAY);
                    if (jsonArray.length() > 0) {
                        //Log.e(TAG, "Socialmedia1" + jsonArray.getJSONObject(0).getString(AppConstants.HP_MEDIA_LINK));
                        PreferenceUtil.setInstagramUrl(Activity, jsonArray.getJSONObject(0).getString(AppConstants.HP_INSTAGRAM));
                        PreferenceUtil.setTwitterUrl(Activity, jsonArray.getJSONObject(1).getString(AppConstants.HP_TWITTER));
                        PreferenceUtil.setLinkedinUrl(Activity, jsonArray.getJSONObject(2).getString(AppConstants.HP_LINKEDIN));
                        PreferenceUtil.setFacebookUrl(Activity, jsonArray.getJSONObject(3).getString(AppConstants.HP_FACEBOOK));
                        Log.e(TAG, "Socialmedia1" + jsonArray.getJSONObject(0).getString(AppConstants.HP_INSTAGRAM));
                        Log.e(TAG, "Socialmedia1" + jsonArray.getJSONObject(1).getString(AppConstants.HP_TWITTER));

                        Log.e(TAG, "Socialmedia3" + jsonArray.getJSONObject(2).getString(AppConstants.HP_MEDIA_LINK));
                        Log.e(TAG, "Socialmedia4" + jsonArray.getJSONObject(3).getString(AppConstants.HP_MEDIA_LINK));
                        //medialLink_Array=new ArrayList<>();
                        // for(int i=0;i<jsonArray.length();i++) {

                            /*medialLink_Array.add(new SocialmediaData(jsonArray.getJSONObject(i).getString(AppConstants.HP_MEDIA_LINK),jsonArray.getJSONObject(i).getString(AppConstants.HP_MEDIANAME)));
                            Log.e(TAG,"Socialmedia1"+jsonArray.getJSONObject(0).getString(AppConstants.HP_MEDIA_LINK));
                            Log.e(TAG,"Socialmedia2"+jsonArray.getJSONObject(1).getString(AppConstants.HP_MEDIA_LINK));
                            Log.e(TAG,"Socialmedia3"+jsonArray.getJSONObject(2).getString(AppConstants.HP_MEDIA_LINK));
                            Log.e(TAG,"Socialmedia4"+jsonArray.getJSONObject(3).getString(AppConstants.HP_MEDIA_LINK));
*/
                        // }

                    }
                } else {
//                    Toast.makeText(Activity,jsonObject.getString(AppConstants.MESSAGE),Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseApplication.getInstance().progressOFF();
        }
    }

    private void SendPushNotificationData() {
        SharedPreferences pref = Activity.getSharedPreferences(AppConstants.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
//        Toast.makeText(getActivity(), "regId"+regId, Toast.LENGTH_SHORT).show();
        try {
            Log.e(TAG, "inside try>>>>");
            if (Activity.isNetworkAvailable()) {
                if (PreferenceUtil.getUserId(Activity).equals("")) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.DEVICE_ID, Settings.Secure.getString(Activity.getContentResolver(), Settings.Secure.ANDROID_ID));
                    jsonObject.putOpt(AppConstants.DEVICE_TOKEN, regId);
                    jsonObject.putOpt(AppConstants.DEVICE_PLATFORM, 0);
                    //jsonObject.putOpt(AppConstants.USER_ID, PreferenceUtil.getUserId(this));
                    jsonObject.putOpt(AppConstants.USER_ID, "");
                    Log.e(TAG, "Json Req:" + jsonObject);
//                    startSpinwheel(false, true);
                    BaseApplication.getInstance().progressON(getActivity(), null);

                    if (VolleyUtils.volleyEnabled) {
                        CommandFactory commandFactory = new CommandFactory();
                        commandFactory.sendPostCommand(AppConstants.HP_DEVICE_REGISTRATION, jsonObject);
                    }
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.DEVICE_ID, Settings.Secure.getString(Activity.getContentResolver(), Settings.Secure.ANDROID_ID));
                    jsonObject.putOpt(AppConstants.DEVICE_TOKEN, regId);
                    jsonObject.putOpt(AppConstants.DEVICE_PLATFORM, 0);
                    jsonObject.putOpt(AppConstants.USER_ID, PreferenceUtil.getUserId(Activity));
                    //jsonObject.putOpt(AppConstants.USER_ID, "");
                    Log.e(TAG, "Json Req###:" + jsonObject);
//                    startSpinwheel(false, true);
                    BaseApplication.getInstance().progressON(getActivity(), null);

                    if (VolleyUtils.volleyEnabled) {
                        CommandFactory commandFactory = new CommandFactory();
                        commandFactory.sendPostCommand(AppConstants.HP_DEVICE_REGISTRATION, jsonObject);
                    }
                }
            } else {
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseApplication.getInstance().progressOFF();
        }
    }

    @Override
    public void onRegisterDeviceLoadedSuccessfully(JSONObject jsonObject) {
        super.onRegisterDeviceLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        if (jsonObject != null) {
            try {
                FetchSocialmediaUrl();
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {

                    //Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();

                } else {
                    Log.e("Device Registeration", "Failed.......");
                    // Toast.makeText(Activity, "else"+jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRegisterDeviceLoadedLoadedFailed(JSONObject jsonObject) {
        super.onRegisterDeviceLoadedLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    private void initView(View view) {
        btnConstructionInHome = (ImageButton) view.findViewById(R.id.btnConstructionInHome);
        btnRealEstateInHome = (ImageButton) view.findViewById(R.id.btnRealEstateInHome);
        iconlogo = (ImageView) view.findViewById(R.id.iconlogo);
        //iconlogo1=(ImageView)view.findViewById(R.id.iconlogo1);
        homePageViewaPAger = (ViewPager) view.findViewById(R.id.recyViewRealEstateOffer);
        btnReqProptyService = (Button) view.findViewById(R.id.btnReqProptyService);
        textSwitcher = (TextView) view.findViewById(R.id.textSwitcher);
        textSwitcher.setSelected(true);

        textSwitcher2 = (TextView) view.findViewById(R.id.textSwitcher2);
        textSwitcher2.setSelected(true);

        if (PreferenceUtil.getLanguage(Activity).equals("ar")) {

            FontUtils.setHomeplusArabicRegularFont(Activity, btnReqProptyService);
            FontUtils.setHomeplusArabicRegularFont(Activity, textSwitcher);
            FontUtils.setHomeplusArabicRegularFont(Activity, textSwitcher2);
        } else {

            FontUtils.setHomeplusRegularFont(Activity, btnReqProptyService);
            FontUtils.setHomeplusRegularFont(Activity, textSwitcher);
            FontUtils.setHomeplusRegularFont(Activity, textSwitcher2);
        }

        btnConstructionInHome.setOnClickListener(this);
        btnRealEstateInHome.setOnClickListener(this);
        btnReqProptyService.setOnClickListener(this);
        homePageViewaPAger.setOnPageChangeListener(this);

       /* textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView () {
                return new TextView(Activity);
            }
        });*/

        //textSwitcher.setInAnimation(Activity, R.anim.slide_in_right);
        //textSwitcher.setOutAnimation(Activity, R.anim.slide_out_left);

        if (Activity.isNetworkAvailable())
            fetchSpecialOfferData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
        // FetchSocialmediaUrl();

    }

    private void fetchSpecialOfferData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            Log.e(TAG, "Json Request:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_HOME_SPECIAL_OFFER, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseApplication.getInstance().progressOFF();
        }
    }

    @Override
    public void onHomeSpecialOfferLoadedSuccessfully(JSONObject jsonObject) {
        super.onHomeSpecialOfferLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseSpecialOfferJsonData(jsonObject);
    }

    private void parseSpecialOfferJsonData(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.HOME_SPECIAL_OFFER);

                    if (jsonArray.length() > 0) {
                        homeeSpecialOfferDataList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            homeeSpecialOfferDataList.add(new HomeSpecialOfferData(jsonArray.getJSONObject(i).getString(AppConstants.HOME_SPECIAL_DESCRIPTION),
                                    jsonArray.getJSONObject(i).getString(AppConstants.HOME_SPECIAL_OFFER_ID), jsonArray.getJSONObject(i).getString(AppConstants.HOME_SPECIAL_OFFER_IMAGE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.HOME_SPECIAL_OFFER_TITLE), jsonArray.getJSONObject(i).getString(AppConstants.HOME_SPECIAL_OFFER_TYPE)));
                        }
                        mAdapter = new HomefragmentAdapter(Activity, homeeSpecialOfferDataList);
                        NUM_PAGES = homeeSpecialOfferDataList.size();
                        homePageViewaPAger.setAdapter(mAdapter);
                        pageSwitcher(1);
                        Log.e("pgswitch", "jhjh");

                           /*final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == NUM_PAGES-1) {
                                        currentPage = 0;
                                    }
                                    homePageViewaPAger.setCurrentItem(currentPage++, true);
                                }
                            };
                            Timer swipeTimer = new Timer();
                            swipeTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, 2000, 4000);*/
                    }
                } else {

                }
            } else {

            }
        } catch (Exception e) {

        }
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new HomeFragment.RemindTask(), 1, seconds * 8000); // delay
        //timer.schedule(new HomeFragment.RemindTask(),4);
        // in
        // milliseconds
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "ONPAUSE>>>>>" + timer);
        if (timer != null) {
            timer.cancel();
        }
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

                    if (page > homeeSpecialOfferDataList.size()) { // In my case the number of pages are 5
                        page = 0;
                        Log.d(TAG, "pager" + page);
                    }
                    homePageViewaPAger.setCurrentItem(page++);

                }
            });

        }
    }

    @Override
    public void onHomeSpecialOfferLoadedFailed(JSONObject jsonObject) {
        super.onHomeSpecialOfferLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConstructionInHome:
                Activity.pushFragments(new ConstructionCorporateFrag(), false, true);
                break;
            case R.id.btnRealEstateInHome:
                Activity.pushFragments(new RealEstateFragment(), false, true);
                break;
            case R.id.btnReqProptyService:
                //if(!PreferenceUtil.getUserId(Activity).equals(""))
                if (PreferenceUtil.isKeepMeSignedIn(Activity)) {
                    btnReqProptyService.setVisibility(View.VISIBLE);

                    Activity.pushFragments(new RequestProptyServiceFragment(), false, true);
                } else {
                    setDialog();
                    // Toast.makeText(Activity, getResources().getString(R.string.alert_on_request_property_service), Toast.LENGTH_LONG).show();
                    //Activity.pushFragments(LoginFragment.newInstance(true), false, true);
                }
               /* else{
                    Toast.makeText(Activity, getResources().getString(R.string.alert_on_request_property_service), Toast.LENGTH_LONG).show();
                    Activity.pushFragments(new LoginFragment(), false, true);
                }*/
                break;
            default:
                break;
        }
    }

    private void setDialog() {
        final Dialog dlgForgotPassword = new Dialog(getActivity());
        dlgForgotPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgForgotPassword
                .setContentView(R.layout.layout_loggedin_alert);
        TextView title1 = (TextView) dlgForgotPassword.findViewById(R.id.alert_head1);
        TextView title2 = (TextView) dlgForgotPassword.findViewById(R.id.alert_head2);
        Button btn_yes = (Button) dlgForgotPassword.findViewById(R.id.btn_login_yes);

        Button btn_no = (Button) dlgForgotPassword.findViewById(R.id.btn_login_no);
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity, title1);
            FontUtils.setHomeplusRegularFont(Activity, title2);
            FontUtils.setHomeplusBoldFont(Activity, btn_yes);
            FontUtils.setHomeplusBoldFont(Activity, btn_no);


        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, title1);
            FontUtils.setHomeplusArabicRegularFont(Activity, title2);
            FontUtils.setHomeplusArabicRegularFont(Activity, btn_yes);
            FontUtils.setHomeplusArabicRegularFont(Activity, btn_no);
        }
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlgForgotPassword.dismiss();
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity.pushFragments(LoginFragment.newInstance(true), false, true);
                dlgForgotPassword.dismiss();
            }
        });
        dlgForgotPassword.show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e(TAG, "@@@" + homeeSpecialOfferDataList.get(position).getTitle());
        textSwitcher.setSelected(true);
        textSwitcher2.setSelected(true);
        textSwitcher.setText(homeeSpecialOfferDataList.get(position).getTitle());
        //textSwitcher.setText("kjhfskjhfskjfhskjfhswlkf");


        textSwitcher2.setText(homeeSpecialOfferDataList.get(position).getDescription());

        //textSwitcher.startAnimation(Activity,R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

