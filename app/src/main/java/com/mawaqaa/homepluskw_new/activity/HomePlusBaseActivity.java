package com.mawaqaa.homepluskw_new.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.fragments.HomePlusBaseFragment;
import com.mawaqaa.homepluskw_new.volley.HpResponse;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by JijoCJ on 10/25/2016.
 */
public class HomePlusBaseActivity extends AppCompatActivity implements Animation.AnimationListener {
    protected static HomePlusBaseActivity BaseActivity;
    public HomePlusBaseFragment BaseFragment;
    private static final String TAG = "HomePlusBaseActivity";
    RelativeLayout fragment_container, relLayTopBar;
    public static Context con;
    public static String downloadUrl;
    public static boolean isTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WindowUtils.setFullScreenWithAction(this);
        BaseActivity = this;
        VolleyUtils.init(this);
    }

    public static HomePlusBaseActivity getHpBaseActivity() {
        return BaseActivity;
    }

    public void serviceResponseSuccess(HpResponse Response) {
        if (Response != null) {
            String reqUrl = Response.mReqUrl;
            Log.d(TAG, "serviceResponseSuccess" + reqUrl);
            switch (reqUrl) {
                case AppConstants.GET_SLIDSHOW_BANNER:
                    BaseFragment.onSlidShowBannerLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.LOGIN:
                    BaseFragment.onLoginDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.REGISTRATION:
                    BaseFragment.onRegistrationDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_FORGOT_PASSWORD:
                    BaseFragment.onForgotPasswordDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_REAL_ESTATE:
                    BaseFragment.onRealEstateDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_REAL_ESTATE_INNER_DATA:
                    BaseFragment.onRealEstateDetailsDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL_NEW:
                    BaseFragment.onConstructionIndividualDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL:
                    BaseFragment.onConstructionIndividualDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL_INNER:
                    BaseFragment.onConstructionIndividualInnerDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL_INNER_SEARCH:
                    BaseFragment.onConstructionIndividualInnerSearchLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL_DETAILS:
                    BaseFragment.onConstructionIndividualInnerDetailsDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE_NEW:
                    BaseFragment.onConstructionCorporateDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE:
                    BaseFragment.onConstructionCorporateDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE_INNER:
                    BaseFragment.onConstructionCorporateInnerDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE_INNER_SEARCH:
                    BaseFragment.onConstructionCorporateInnerSearchLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE_DETAILS:
                    BaseFragment.onConstructionCorporateInnerDetailsDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_PROPERTY_SERVICE_REQUEST_DATA:
                    BaseFragment.onRequestPopertyServiceDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_PROPERTY_SERVICE_SEND_REQUEST:
                    BaseFragment.onRequestPopertyServiceSendDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_SPECIAL_OFFER:
                    BaseFragment.onSpecialOffersDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_PHOTO_GALLERY_lIST_DATA:
                    BaseFragment.onMediaPhotoGalleryDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_NEWS_lIST_DATA:
                    BaseFragment.onMediaNewsDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_VIDEO_GALLERY_DATA:
                    BaseFragment.onMediaVideoGalleryDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_DEVICE_REGISTRATION:
                    BaseFragment.onRegisterDeviceLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_MY_REQUEST_LIST:
                    BaseFragment.onMyRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_VIEWPROFILE_REQUEST_DATA:
                    BaseFragment.onViewProfileRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_EDIT_PROFILE:
                    BaseFragment.onEditProfileRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_PUSHNOTIFICATION_LIST:
                    BaseFragment.onPushNotificationfileRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_ABOUTUS:
                    BaseFragment.onAboutusRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_PARTNERS:
                    BaseFragment.onAboutusPartnersRequestDataLoadedSuccessfully(Response.jsonObject);
                case AppConstants.HP_CONTACTUS_ADDRESSINFO:
                    BaseFragment.onContactusAddressinfoRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_FEDDBACK_FORM:
                    BaseFragment.onFeedbackformRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.GET_SOCIALMEDIA_LINK:
                    BaseFragment.onSocialmediaRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_INDIVIDUAL_RATING:
                    BaseFragment.onIndividualRatingRequestDataLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_PHOTOSEARCH:
                    BaseFragment.onPhotoGalleryFilterLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_NEWSFILTER:
                    BaseFragment.onNewsFilterFilterLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_HOME_SPECIAL_OFFER:
                    BaseFragment.onHomeSpecialOfferLoadedSuccessfully(Response.jsonObject);
                    break;
                case AppConstants.HP_CHANGEPASSWORD:
                    BaseFragment.onChangePasswordLoadedSuccessfully(Response.jsonObject);
                    break;
                default:
                    break;
            }
        }
    }

    public String getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
        String fragmentName = currentFragment.getClass().getName();
        return fragmentName;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    public void serviceResponseError(HpResponse Response) {
        if (Response != null) {
            String reqUrl = Response.mReqUrl;
            Log.d(TAG, "serviceResponseError" + reqUrl);
            switch (reqUrl) {
                case AppConstants.GET_SLIDSHOW_BANNER:
                    BaseFragment.onSlidShowBannerLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.LOGIN:
                    BaseFragment.onLoginDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.REGISTRATION:
                    BaseFragment.onRegistrationDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_FORGOT_PASSWORD:
                    BaseFragment.onForgotPasswordDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_REAL_ESTATE:
                    BaseFragment.onRealEstateDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_REAL_ESTATE_INNER_DATA:
                    BaseFragment.onRealEstateDetailsDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL:
                    BaseFragment.onConstructionIndividualDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL_NEW:
                    BaseFragment.onConstructionIndividualDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL_INNER:
                    BaseFragment.onConstructionIndividualInnerDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL_INNER_SEARCH:
                    BaseFragment.onConstructionIndividualInnerSearchLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_INDIVIDUAL_DETAILS:
                    BaseFragment.onConstructionIndividualInnerDetailsDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE:
                    BaseFragment.onConstructionCorporateDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE_NEW:
                    BaseFragment.onConstructionCorporateDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE_INNER:
                    BaseFragment.onConstructionCorporateInnerDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE_INNER_SEARCH:
                    BaseFragment.onConstructionCorporateInnerSearchLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CONSTRUCTION_CORPRATE_DETAILS:
                    BaseFragment.onConstructionCorporateInnerDetailsDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_PROPERTY_SERVICE_REQUEST_DATA:
                    BaseFragment.onRequestPopertyServiceDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_PROPERTY_SERVICE_SEND_REQUEST:
                    BaseFragment.onRequestPopertyServiceSendDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_SPECIAL_OFFER:
                    BaseFragment.onSpecialOffersDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_PHOTO_GALLERY_lIST_DATA:
                    BaseFragment.onMediaPhotoGalleryDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_NEWS_lIST_DATA:
                    BaseFragment.onMediaNewsDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_VIDEO_GALLERY_DATA:
                    BaseFragment.onMediaVideoGalleryDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_DEVICE_REGISTRATION:
                    BaseFragment.onRegisterDeviceLoadedLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_MY_REQUEST_LIST:
                    BaseFragment.onMyRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_VIEWPROFILE_REQUEST_DATA:
                    BaseFragment.onViewProfileRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_EDIT_PROFILE:
                    BaseFragment.onEditProfileRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_PUSHNOTIFICATION_LIST:
                    BaseFragment.onPushNotificationRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_ABOUTUS:
                    BaseFragment.onPushNotificationRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_PARTNERS:
                    BaseFragment.onAboutusPartnersRequestDataLoadedFailed(Response.jsonObject);
                case AppConstants.HP_CONTACTUS_ADDRESSINFO:
                    BaseFragment.onContactusAddressinfoRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_FEDDBACK_FORM:
                    BaseFragment.onFeedbackformRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.GET_SOCIALMEDIA_LINK:
                    BaseFragment.onSocialmediaRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_INDIVIDUAL_RATING:
                    BaseFragment.onIndividualRatingRequestDataLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_PHOTOSEARCH:
                    BaseFragment.onPhotoGalleryFilterLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_NEWSFILTER:
                    BaseFragment.onNewsFilterFilterLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_HOME_SPECIAL_OFFER:
                    BaseFragment.onHomeSpecialOfferLoadedFailed(Response.jsonObject);
                    break;
                case AppConstants.HP_CHANGEPASSWORD:
                    BaseFragment.onChangePasswordLoadedFailed(Response.jsonObject);
                    break;
                default:
                    break;


            }
        }
    }

    public void pushFragments(Fragment fragment, boolean shouldAnimate,
                              boolean shouldAdd) {
        FragmentManager manager = getSupportFragmentManager();
        String backStateName = fragment.getClass().getName();
        Log.e("backstack name>>>>>>>>", backStateName);

        if (isNeedTransaction(backStateName)) {
            boolean fragmentPopped = manager.popBackStackImmediate(
                    backStateName, 0);

            if (!fragmentPopped) { // fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                if (shouldAnimate)
                    ft.setCustomAnimations(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                ft.replace(R.id.fragment_container, fragment, backStateName);
                if (shouldAdd)
                    ft.addToBackStack(backStateName);
                ft.commit();
                manager.executePendingTransactions();
            }
        }
    }

    /*public void pushFragments(Fragment fragment, boolean shouldAnimate,
                              boolean shouldAdd, String fragTag) {
        FragmentManager manager = getSupportFragmentManager();
        // String backStateName = fragment.getClass().getName();
        Log.d(TAG, "backStateName :" + fragTag);
       if (BaseFragment != null)
            Log.d(TAG, "baseFragment :" + BaseFragment.getClass().getName());

         if (isNeedTransaction(fragTag)) {
            Log.e(TAG, "Need Transaction");
            boolean fragmentPopped = manager.popBackStackImmediate(fragTag, 0);

            if (!fragmentPopped) { // fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                if (shouldAnimate)
                    ft.setCustomAnimations(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                ft.replace(R.id.fragment_container, fragment, fragTag);
                if (shouldAdd)
                    ft.addToBackStack(fragTag);
                ft.commit();
                manager.executePendingTransactions();
            } else
                Log.d(TAG, "fragmentPopped :" + fragmentPopped);
        } else {
            Log.d(TAG, "already in same fragment no need to call it again :");
        }
    }*/

    private boolean isNeedTransaction(String backStateName) {
        boolean needTransaction = true;
        if (BaseFragment != null) {
            String baseFrag = BaseFragment.getClass().getName();
            if (baseFrag.equals(backStateName)) {

                needTransaction = false;
            } else
                needTransaction = true;
        }
        return needTransaction;
    }

    public void pushFragments4LanSwitch(Fragment fragment, boolean shouldAnimate) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate)
            // ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
        manager.executePendingTransactions();
    }

    public void clearAllBackStackEntries() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public void popFragments(Fragment frag) {
        Log.e("Enterd here", "Inside pop fragment");
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            String fragName = frag.getClass().getName();
            manager.popBackStack(fragName,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.remove(frag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        String fragTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        Log.e(TAG, "frag Tag : " + fragTag);
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            this.finish();
            super.onBackPressed();

            //PreferenceUtil.setasGuestUser(this,false);
        } else {

            Log.e(TAG, "In else of BackPressed()");
            /*if(fragTag.equalsIgnoreCase("com.mawaqaa.homepluskw.fragments.PreHomeFragment")){
                super.onBackPressed();
            }*/
            //this.finish();
            super.onBackPressed();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

