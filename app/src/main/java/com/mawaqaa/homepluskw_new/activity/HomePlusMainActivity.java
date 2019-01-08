package com.mawaqaa.homepluskw_new.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.adapter.DrawerMenuListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.DrawerMenuListData;
import com.mawaqaa.homepluskw_new.fragments.AdvertisementFragment;
import com.mawaqaa.homepluskw_new.fragments.ConstrctnCorporateDetailsFrag;
import com.mawaqaa.homepluskw_new.fragments.ConstrctnIndividualDetailsFrag;
import com.mawaqaa.homepluskw_new.fragments.ConstructionCorporateFrag;
import com.mawaqaa.homepluskw_new.fragments.HomeFragment;
import com.mawaqaa.homepluskw_new.fragments.MyAccountFragment;
import com.mawaqaa.homepluskw_new.fragments.PreHomeFragment;
import com.mawaqaa.homepluskw_new.fragments.RealEstateDeatailsInnerFragment;
import com.mawaqaa.homepluskw_new.fragments.RealEstateFragment;
import com.mawaqaa.homepluskw_new.fragments.SlideShowTutorialFragment;
import com.mawaqaa.homepluskw_new.fragments.SpecialOffersFragment;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.interfaces.DrawerLocker;
import com.mawaqaa.homepluskw_new.interfaces.DrawerPopUpListener;
import com.mawaqaa.homepluskw_new.interfaces.LanguageSwitcherListener;
import com.mawaqaa.homepluskw_new.listener.SlideMenuClickListener;
import com.mawaqaa.homepluskw_new.utility.DrawerUtils;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.NotificationUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomePlusMainActivity extends HomePlusBaseActivity implements View.OnClickListener, DrawerLocker, BottomBarButtonClickState {

    private final static String TAG = "HomePlusMainActivity";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbarActionBar;
    private boolean isButtonClicked = true;
    TextView txtInActionBarTitle, tvMediaMenu;
    ImageButton imgBtnBack, imgBtnHome, imgBtnRealEstate, imgBtnPropertyService,
            imgBtnConstruction, imgBtnMore;
    ImageView imgviewFb, imgviewTwitter, imgviewLinkedIn, imgviewInstagram;

    LinearLayout LinearBottomBarLay;
    RelativeLayout relativeLaySubMenuPopUp;
    LayoutInflater inflater;

    DrawerMenuListAdapter mDrawerListAdapter;
    List<DrawerMenuListData> listViewItems;

    public static DrawerPopUpListener mPopUpListener;
    public static LanguageSwitcherListener mLanguageListener;
    int popUpContents[];

    String isLoggedUser;
    public static String n_type, dataid, lang;

    private BroadcastReceiver mRegistrationBroadcastReceiver;


    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_plus_main);
        setInitLanguageSwitcher();
        setInitView();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    /*String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    pushFragments(new RealEstateFragment(), false, true);
*/

                    //txtMessage.setText(message);

                }
            }
        };
        displayFirebaseRegId();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.e("INNN", "NOTF");

            n_type = extras.getString("notificationtype"); // Retrieve the id
            dataid = extras.getString("dataid"); // Retrieve the id
            lang = extras.getString("lang"); // Retrieve the id
            Log.e("CHECKN", "val" + n_type + "" + dataid + "" + lang);
            Log.e("CHECKNLANG", PreferenceUtil.getLanguage(this));
            if (lang.equals(PreferenceUtil.getLanguage(this))) {

                if (n_type.equals("1")) {
                    RealEstateDeatailsInnerFragment realEstateDeatailsInnerFragment = RealEstateDeatailsInnerFragment.newInstance(dataid, false);
                    pushFragments(realEstateDeatailsInnerFragment, false, true);
                } else if (n_type.equals("2")) {
                    pushFragments(ConstrctnCorporateDetailsFrag.newInstance(dataid, false), false, true);
                } else if (n_type.equals("3")) {
                    pushFragments(ConstrctnIndividualDetailsFrag.newInstance(dataid, false), false, true);
                } else if (n_type.equals("4")) {
                    pushFragments(new HomeFragment(), false, true);
                }
            } else {
                pushFragments(new HomeFragment(), false, true);
            }
        } else {

            Log.e("OUT", "NOTF");
            if (PreferenceUtil.isTutorialShows(this)) {
                if (PreferenceUtil.isKeepMeSignedIn(this))
                    pushFragments(new HomeFragment(), false, true);
                else {
                    PreferenceUtil.setUserSignedIn(this, false);
                    pushFragments(new PreHomeFragment(), false, true);
                }
            } else {
                Log.e(TAG, "Preference Value" + PreferenceUtil.isAppFirstTime(this));
                if (PreferenceUtil.isAppFirstTime(this)) {
                    Log.e(TAG, "True>>>>>>>");
                    PreferenceUtil.setAppFirstTimeStatus(this, false);
                    PreferenceUtil.setUserSignedIn(this, false);
                    pushFragments(new SlideShowTutorialFragment(), false, true);

                } else {
                    Log.e(TAG, "False>>>>>>>>>>>");
                    PreferenceUtil.setUserSignedIn(this, false);
                    pushFragments(new AdvertisementFragment(), false, true);
                }

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(AppConstants.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        // PreferenceUtil.setDeviceToken(regId);
        Log.e(TAG, "Firebase reg id: " + regId);
        Log.e(TAG, "Access Token: " + regId);
        /*try {
           *//* if(isNetworkAvailable()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt("ApiKey", AppConstants.FCM_WEB_API);
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                jsonObject.putOpt("SenderId", regId);
                jsonObject.putOpt("Title", "Testing");
                jsonObject.putOpt("Message", "hi.....");
                jsonObject.putOpt("Topic", "");
                Log.e(TAG, "Json Req:"+jsonObject);

                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_SEND_NOTIFICATION, jsonObject);
            }*//*

            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt("ApiKey", AppConstants.FCM_SERVER_API);
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt("SenderId", regId);
            jsonObject.putOpt("Title", "Testing");
            jsonObject.putOpt("Message", "hi.....");
            jsonObject.putOpt("Topic", "");
            Log.e(TAG, "Json Req:"+jsonObject);
            if (VolleyUtils.volleyEnabled) {
                //Activity.BaseFragment.startSpinwheel(false, true);


                RequestQueue queue = VolleyUtils.getRequestQueue();
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST, AppConstants.HP_SEND_NOTIFICATION, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e(TAG,
                                        "Response : " + response.toString());
                                //onSaveMyAccountInfoSuccessfully(response);
                            }


                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Response : Error"+error.getMessage());

                    }
                });
                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, 2));
                queue.add(jsObjRequest);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       /* if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");*/
    }

    private void setInitView() {
        // Object for views
        toolbarActionBar = (Toolbar) findViewById(R.id.toolbarActionBar);
        txtInActionBarTitle = (TextView) findViewById(R.id.txtInActionBarTitle);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        imgBtnHome = (ImageButton) findViewById(R.id.imgBtnHome);
        imgBtnRealEstate = (ImageButton) findViewById(R.id.imgBtnRealEstate);
        imgBtnPropertyService = (ImageButton) findViewById(R.id.imgBtnPropertyService);
        imgBtnConstruction = (ImageButton) findViewById(R.id.imgBtnConstruction);
        imgBtnMore = (ImageButton) findViewById(R.id.imgBtnMore);
        imgviewFb = (ImageView) findViewById(R.id.imgviewFb);
        imgviewTwitter = (ImageView) findViewById(R.id.imgviewTwitter);
        imgviewLinkedIn = (ImageView) findViewById(R.id.imgviewLinkedIn);
        imgviewInstagram = (ImageView) findViewById(R.id.imgviewInstagram);
        LinearBottomBarLay = (LinearLayout) findViewById(R.id.LinearBottomBarLay);
        relativeLaySubMenuPopUp = (RelativeLayout) findViewById(R.id.relativeLaySubMenuPopUp);

        //popup menu in drawer
        mPopUpListener = new DrawerPopUpListener() {
            @Override
            public void onDrawerItemClick(View view, int position) {
                //relativeLaySubMenuPopUp.setVisibility(View.VISIBLE);
                tvMediaMenu = (TextView) view.findViewById(R.id.txtMenuItem);
                ImageView drawe_image = (ImageView) view.findViewById(R.id.imgViewMenuIconItem);

                if (isButtonClicked) {
                    if (HomePlusBaseActivity.getHpBaseActivity().getCurrentFragment() == new MyAccountFragment().getClass().getName()) {

                    } else {
                        tvMediaMenu.setTextColor(getResources().getColor(R.color.bg_color_dark_white));
                    }

                    //drawe_image.setImageResource(R.drawable.more_btn);
                    DrawerUtils.showSocialMediaDialog(getSupportFragmentManager(),
                            "socialMedia");
                    isButtonClicked = true;


                } else {
                    //set image for drawermenu change icon
                    //drawe_image.setImageResource(R.drawable.menu_5);
                    tvMediaMenu.setTextColor(getResources().getColor(R.color.bg_color_dark_white));
                    isButtonClicked = true;
                }


            }
        };

        mLanguageListener = new LanguageSwitcherListener() {
            @Override
            public void doLanguageSwitch() {
                setLanguageSwitcher();
            }
        };

        // onclick events for button
        imgBtnBack.setOnClickListener(this);
        imgBtnHome.setOnClickListener(this);
        imgBtnRealEstate.setOnClickListener(this);
        imgBtnPropertyService.setOnClickListener(this);
        imgBtnConstruction.setOnClickListener(this);
        imgBtnMore.setOnClickListener(this);

        // drawer menu initialization method
        setDrawerLayout();
        //Log.e(TAG, "Access Token FCM:"+FirebaseInstanceId.getInstance().getToken());
    }

    private void setDrawerLayout() {

        // convert to simple array
        // popUpContents = new int[]{R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_4};
        //dogsList.toArray(popUpContents);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.mDrawerList);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = (ViewGroup) inflater.inflate(R.layout.drawer_menu_list_header, mDrawerList, false);
        if (mDrawerList.getHeaderViewsCount() == 0 && !(mDrawerList.getHeaderViewsCount() > 1))
            mDrawerList.addHeaderView(view);

        try {
            isLoggedUser = PreferenceUtil.getUserId(this);
            if (PreferenceUtil.isKeepMeSignedIn(this)) {
                Log.e("MNN", "Keep Signed In:" + PreferenceUtil.isKeepMeSignedIn(this));
                listViewItems = new ArrayList<DrawerMenuListData>();
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.language), R.drawable.menu_1));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.about_us), R.drawable.menu_2));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.my_account), R.drawable.menu_3));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.media_center), R.drawable.menu_4));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.social_media), R.drawable.menu_5));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.contact_us), R.drawable.menu_6));
            } else {
                Log.e("MNN", "Keep Signed In:" + PreferenceUtil.isKeepMeSignedIn(this));
                listViewItems = new ArrayList<DrawerMenuListData>();
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.language), R.drawable.menu_1));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.about_us), R.drawable.menu_2));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.logout), R.drawable.menu_2));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.media_center), R.drawable.menu_4));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.social_media), R.drawable.menu_5));
                listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.contact_us), R.drawable.menu_6));
            }

            //
            Log.e(TAG, "Logged User:" + PreferenceUtil.isUserSignedIn(this));
            mDrawerList.setOnItemClickListener(new SlideMenuClickListener(getApplicationContext(), mDrawerLayout, imgBtnMore, mPopUpListener, mLanguageListener, mDrawerList));
            //isLoggedUser = false;


        } catch (Exception e) {
            e.printStackTrace();
        }

        mDrawerListAdapter = new DrawerMenuListAdapter(getApplicationContext(), listViewItems);
        mDrawerList.setAdapter(mDrawerListAdapter);

    }

    public void setDrawerInLogin(Context context) {
        //setDrawerLayout();
        PreferenceUtil.setUserSignedIn(this, true);
        //isLoggedUser = PreferenceUtil.getUserId(this);
        listViewItems = new ArrayList<DrawerMenuListData>();
        listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.language), R.drawable.menu_1));
        listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.about_us), R.drawable.menu_2));
        listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.my_account), R.drawable.menu_3));
        listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.media_center), R.drawable.menu_4));
        listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.social_media), R.drawable.menu_5));
        listViewItems.add(new DrawerMenuListData(getResources().getString(R.string.contact_us), R.drawable.menu_6));
        Log.e(TAG, "Logged User in setDrawerInLogin:" + isLoggedUser);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener(getApplicationContext(), mDrawerLayout, imgBtnMore, mPopUpListener, mLanguageListener, mDrawerList));

        mDrawerListAdapter = new DrawerMenuListAdapter(getApplicationContext(), listViewItems);
        mDrawerList.setAdapter(mDrawerListAdapter);
    }


    private void setInitLanguageSwitcher() {
        // Fragment fr = FragmentUtils.getActiveFragment(BaseActivity);
        Fragment fr = new Fragment();
        String str = "";
        if (PreferenceUtil.getLanguage(getApplicationContext()).equals(
                AppConstants.HP_ENGLISH)) {
            PreferenceUtil.setLanguage(getApplicationContext(),
                    AppConstants.HP_ENGLISH);
            Log.e(TAG, "Set locale to Arabic");
            setLanguage("en");
        } else {
            PreferenceUtil.setLanguage(getApplicationContext(),
                    AppConstants.HP_ARABIC);
            setLanguage("ar");
            Log.e(TAG, "Set locale to English");
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
                    .getBackStackEntryAt(
                            getSupportFragmentManager()
                                    .getBackStackEntryCount() - 1);
            str = backEntry.getName();
            Log.e(TAG, "Fragment Name: " + str);
            fr = getSupportFragmentManager().findFragmentByTag(str);
        }
        if (fr != null) {
            Log.e(TAG, "Not Null Fragment");
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            /*
             * ft.detach(fr); ft.attach(fr);
			 */// Errornious code.. don't use.. it casue illegalStateException
            // while starting another Activity
            //ft.detach(fr);
            //ft.attach(fr);
            ft.commit();
            //getSupportFragmentManager().executePendingTransactions();
            // popFragments(fr);
            // pushFragments(fr, false, true,str);
            // pushFragments4LanSwitch(fr, false);
        } else
            Log.e(TAG, "Null Fragment Return");
    }

    private void setLanguage(String lang) {
       /* String languageToLoad = lang;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getResources().getDisplayMetrics());*/
        String languageToLoad = lang;
        Locale locale = new Locale(languageToLoad);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

        onConfigurationChanged(conf);

        setContentView(R.layout.activity_home_plus_main);
        reInitView4LangSwitch();
    }

    public void reInitView4LangSwitch() {
        setInitView();

    }

    public static void getnotification(String n_typee, String dataide) {
        n_type = n_typee;
        dataid = dataide;
        //navigatetoeach();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnBack:
                goBack();
                break;
            case R.id.imgBtnMore:
                DrawerUtils.openDrawerView(this, mDrawerLayout, imgBtnMore);

                break;
            case R.id.imgBtnHome:
                DrawerUtils.closeDrawerVeiw(this, mDrawerLayout, imgBtnMore);
                pushFragments(new HomeFragment(), false, true);
                break;
            case R.id.imgBtnRealEstate:
                DrawerUtils.closeDrawerVeiw(this, mDrawerLayout, imgBtnMore);
                pushFragments(new RealEstateFragment(), false, true);
                //pushFragments(new RealestateTest(), false, true);
                break;
            case R.id.imgBtnPropertyService: //Special Offer
                DrawerUtils.closeDrawerVeiw(this, mDrawerLayout, imgBtnMore);
                pushFragments(new SpecialOffersFragment(), false, true);
                break;
            case R.id.imgBtnConstruction:
                DrawerUtils.closeDrawerVeiw(this, mDrawerLayout, imgBtnMore);
                pushFragments(new ConstructionCorporateFrag(), false, true);
                break;
            default:
                break;
        }
    }

    public void showActionBar() {
        toolbarActionBar.setVisibility(View.VISIBLE);
    }

    public void hideActionBar() {
        toolbarActionBar.setVisibility(View.GONE);
    }

    public void showBottomBar() {
        LinearBottomBarLay.setVisibility(View.VISIBLE);
    }

    public void hideBottomBar() {
        LinearBottomBarLay.setVisibility(View.GONE);
    }

    public void setTxtInActionBarTitle(String title) {
        txtInActionBarTitle.setText(title);
        if (PreferenceUtil.getLanguage(this).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(getApplicationContext(), txtInActionBarTitle);
        } else {
            FontUtils.setHomeplusArabicRegularFont(getApplicationContext(), txtInActionBarTitle);
        }

    }

    public void goBack() {
        super.onBackPressed();
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        mDrawerLayout.setDrawerLockMode(lockMode);
    }

    @Override
    public void defaultButtonState() {
        imgBtnHome.setBackgroundResource(R.drawable.home_btn);
        imgBtnRealEstate.setBackgroundResource(R.drawable.real_estate_btn);
        imgBtnPropertyService.setImageResource(R.drawable.propert_service_btn);
        imgBtnConstruction.setBackgroundResource(R.drawable.construction_btn);
        imgBtnMore.setImageResource(R.drawable.more_btn);
    }

    @Override
    public void homeButtonState() {
        imgBtnHome.setBackgroundResource(R.drawable.home_btn_sel);
        imgBtnRealEstate.setBackgroundResource(R.drawable.real_estate_btn);
        imgBtnPropertyService.setImageResource(R.drawable.propert_service_btn);
        imgBtnConstruction.setBackgroundResource(R.drawable.construction_btn);
        imgBtnMore.setImageResource(R.drawable.more_btn);
    }

    @Override
    public void realEstateButtonState() {
        imgBtnHome.setBackgroundResource(R.drawable.home_btn);
        imgBtnRealEstate.setBackgroundResource(R.drawable.real_estate_btn_sel);
        imgBtnPropertyService.setImageResource(R.drawable.propert_service_btn);
        imgBtnConstruction.setBackgroundResource(R.drawable.construction_btn);
        imgBtnMore.setImageResource(R.drawable.more_btn);
    }

    @Override
    public void specialOfferButtonState() {
        imgBtnHome.setBackgroundResource(R.drawable.home_btn);
        imgBtnRealEstate.setBackgroundResource(R.drawable.real_estate_btn);
        imgBtnPropertyService.setImageResource(R.drawable.property_service_btn_sel);
        imgBtnConstruction.setBackgroundResource(R.drawable.construction_btn);
        imgBtnMore.setImageResource(R.drawable.more_btn);
    }

    @Override
    public void constructionButtonState() {
        imgBtnHome.setBackgroundResource(R.drawable.home_btn);
        imgBtnRealEstate.setBackgroundResource(R.drawable.real_estate_btn);
        imgBtnPropertyService.setImageResource(R.drawable.propert_service_btn);
        imgBtnConstruction.setBackgroundResource(R.drawable.constrctn_btn_sel);
        imgBtnMore.setImageResource(R.drawable.more_btn);
    }

    @Override
    public void MoreButtonState() {
        imgBtnHome.setBackgroundResource(R.drawable.home_btn);
        imgBtnRealEstate.setBackgroundResource(R.drawable.real_estate_btn);
        imgBtnPropertyService.setImageResource(R.drawable.propert_service_btn);
        imgBtnConstruction.setBackgroundResource(R.drawable.construction_btn);
        imgBtnMore.setImageResource(R.drawable.more_btn_sel);
    }

    public void doLanguageChange() {
        setLanguageSwitcher(); //Use this Method
    }

    public void setLanguageSwitcher() {

        // Fragment fr = FragmentUtils.getActiveFragment(BaseActivity);
        Fragment fr = new Fragment();
        String str = "";
        if (PreferenceUtil.getLanguage(getApplicationContext()).equals(
                AppConstants.HP_ENGLISH)) {
            PreferenceUtil.setLanguage(getApplicationContext(),
                    AppConstants.HP_ARABIC);
            Log.e(TAG, "Set locale to Arabic");
            setLanguage(AppConstants.HP_ARABIC);
        } else {
            PreferenceUtil.setLanguage(getApplicationContext(),
                    AppConstants.HP_ENGLISH);
            setLanguage(AppConstants.HP_ENGLISH);
            Log.e(TAG, "Set locale to English");
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Log.e(TAG, "BackStack Count Zero");
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
                    .getBackStackEntryAt(
                            getSupportFragmentManager()
                                    .getBackStackEntryCount() - 1);
            str = backEntry.getName();
            Log.e(TAG, "Fragment Name: " + str);
            fr = getSupportFragmentManager().findFragmentByTag(str);
        } else {
            Log.e(TAG, "BackStack Count Zero");
        }
        if (fr != null) {
            Log.e(TAG, "Not Null Fragment");
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            ft.detach(fr);
            ft.attach(fr);
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            //recreate();
            // popFragments(fr);
            // pushFragments(fr, false, true,str);
            // pushFragments4LanSwitch(fr, false);
        } else
            Log.e(TAG, "Null Fragment Return");

    }

}
