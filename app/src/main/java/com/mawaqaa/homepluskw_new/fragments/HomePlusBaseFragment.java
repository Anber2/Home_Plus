package com.mawaqaa.homepluskw_new.fragments;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ProgressBar;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;

import org.json.JSONObject;

/**
 * Created by JijoCJ on 10/25/2016.
 */
public class HomePlusBaseFragment extends Fragment {
    private static final String TAG = "ExpoBaseFragment";
    public HomePlusBaseActivity Activity;
    public static  int flagURL=0;
    public static  int flagURLOnFilter=0;
    public static String spinnerSelectedId = "";
    public static int spinnerSelectedPosition = 0;
    Animation animation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Activity = (HomePlusBaseActivity) this.getActivity();
    }
    public void onResume() {
        Log.d(TAG, "onResume" + this.getClass().getName());
        super.onResume();
        ((HomePlusBaseActivity) getActivity()).BaseFragment = this;
    }

    public void onSlidShowBannerLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onSlidShowBannerLoadedFailed(JSONObject jsonObject) {}
    public void onLoginDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onLoginDataLoadedFailed(JSONObject jsonObject) {}
    public void onRegistrationDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onRegistrationDataLoadedFailed(JSONObject jsonObject) {}
    public void onForgotPasswordDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onForgotPasswordDataLoadedFailed(JSONObject jsonObject) {}
    public void onRealEstateDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onRealEstateDataLoadedFailed(JSONObject jsonObject) {}
    public void onRealEstateDetailsDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onRealEstateDetailsDataLoadedFailed(JSONObject jsonObject) {}
    public void onConstructionIndividualDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onConstructionIndividualDataLoadedFailed(JSONObject jsonObject) {}
    public void onConstructionIndividualInnerDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onConstructionIndividualInnerDataLoadedFailed(JSONObject jsonObject) {}
    public void onConstructionIndividualInnerSearchLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onConstructionIndividualInnerSearchLoadedFailed(JSONObject jsonObject) {}
    public void onConstructionIndividualInnerDetailsDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onConstructionIndividualInnerDetailsDataLoadedFailed(JSONObject jsonObject) {}

    public void onConstructionCorporateDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onConstructionCorporateDataLoadedFailed(JSONObject jsonObject) {}
    public void onConstructionCorporateInnerDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onConstructionCorporateInnerDataLoadedFailed(JSONObject jsonObject) {}
    public void onConstructionCorporateInnerDetailsDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onConstructionCorporateInnerDetailsDataLoadedFailed(JSONObject jsonObject) {}

    public void onRequestPopertyServiceDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onRequestPopertyServiceDataLoadedFailed(JSONObject jsonObject) {}
    public void onRequestPopertyServiceSendDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onRequestPopertyServiceSendDataLoadedFailed(JSONObject jsonObject) {}
    public void onSpecialOffersDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onSpecialOffersDataLoadedFailed(JSONObject jsonObject) {}
    public void onMediaPhotoGalleryDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onMediaPhotoGalleryDataLoadedFailed(JSONObject jsonObject) {}
    public void onMediaNewsDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onMediaNewsDataLoadedFailed(JSONObject jsonObject) {}
    public void onMediaVideoGalleryDataLoadedSuccessfully(JSONObject jsonObject) {}
    public void  onMediaVideoGalleryDataLoadedFailed(JSONObject jsonObject) {}
    public void onRegisterDeviceLoadedSuccessfully(JSONObject jsonObject) {}
    public void onRegisterDeviceLoadedLoadedFailed(JSONObject jsonObject) {}
    public void onMyRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onMyRequestDataLoadedFailed(JSONObject jsonObject){}

    public void onViewProfileRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onViewProfileRequestDataLoadedFailed(JSONObject jsonObject){}

    public void onEditProfileRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onEditProfileRequestDataLoadedFailed(JSONObject jsonObject){}

    public void onPushNotificationfileRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onPushNotificationRequestDataLoadedFailed(JSONObject jsonObject){}

    public void onAboutusRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onAboutusRequestDataLoadedFailed(JSONObject jsonObject){}

    public void onAboutusPartnersRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onAboutusPartnersRequestDataLoadedFailed(JSONObject jsonObject){}

    public void onContactusAddressinfoRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onContactusAddressinfoRequestDataLoadedFailed(JSONObject jsonObject){}

    public void onFeedbackformRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onFeedbackformRequestDataLoadedFailed(JSONObject jsonObject){}

    public void onSocialmediaRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onSocialmediaRequestDataLoadedFailed(JSONObject jsonObject){}
    public void onIndividualRatingRequestDataLoadedSuccessfully(JSONObject jsonObject){}
    public void onIndividualRatingRequestDataLoadedFailed(JSONObject jsonObject){}
    public void onConstructionCorporateInnerSearchLoadedSuccessfully(JSONObject jsonObject){}
    public void onConstructionCorporateInnerSearchLoadedFailed(JSONObject jsonObject){}

    public void onPhotoGalleryFilterLoadedSuccessfully(JSONObject jsonObject){}
    public void onPhotoGalleryFilterLoadedFailed(JSONObject jsonObject){}
    public void onNewsFilterFilterLoadedSuccessfully(JSONObject jsonObject){}
    public void onNewsFilterFilterLoadedFailed(JSONObject jsonObject){}

    public void onHomeSpecialOfferLoadedSuccessfully(JSONObject jsonObject){}
    public void onHomeSpecialOfferLoadedFailed(JSONObject jsonObject){}
    public void onChangePasswordLoadedSuccessfully(JSONObject jsonObject){}
    public void onChangePasswordLoadedFailed(JSONObject jsonObject){}
    private Dialog spinWheelDialog;
    Handler spinWheelTimer = new Handler(); // Handler to post a runnable that
    // can dismiss spinweheel afrer a
    // specific time
    public static final int SPINWHEEL_LIFE_TIME = 700; /*
														 * Dismiss spin wheel
														 * after 5 seconds
														 */

    public void startSpinwheel(boolean setDefaultLifetime, boolean isCancelable) {
        // Log.d(TAG, "startSpinwheel"+getCurrentActivity().getClass() );
        // If already showing no need to create.
        if (spinWheelDialog != null && spinWheelDialog.isShowing())
            return;
        spinWheelDialog = new Dialog(Activity, R.style.wait_spinner_style);
        ProgressBar progressBar = new ProgressBar(Activity);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        spinWheelDialog.addContentView(progressBar, layoutParams);
        spinWheelDialog.setCancelable(isCancelable);
        spinWheelDialog.show();
        // start timer for SPINWHEEL_LIFE_TIME
        spinWheelTimer.removeCallbacks(dismissSpinner);
        if (setDefaultLifetime) // If requested for default dismiss time.
            spinWheelTimer.postAtTime(dismissSpinner, SystemClock.uptimeMillis() + SPINWHEEL_LIFE_TIME);

        spinWheelDialog.setCanceledOnTouchOutside(false);
    }

    public void startSpinwheel(boolean isCancelable, int layoutid, int timeOutSec) {
        startSpinwheel(true, isCancelable);
        spinWheelTimer.removeCallbacks(dismissSpinner);
        spinWheelTimer.postAtTime(dismissSpinner, SystemClock.uptimeMillis() + timeOutSec);
        spinWheelDialog.setContentView(layoutid);
    }

    /**
     * Closes the spin wheel dialog
     */

    public void stopSpinWheel() {
        // Log.d(TAG, "stopSpinWheel"+getCurrentActivity().getClass());
        if (spinWheelDialog != null)
            try {
                spinWheelDialog.dismiss();
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Parent is died while tryingto dismiss spin wheel dialog ");
                e.printStackTrace();
            }
        spinWheelDialog = null;
    }

    Runnable dismissSpinner = new Runnable() {

        @Override
        public void run() {
           stopSpinWheel();
//            BaseApplication.getInstance().progressOFF();
        }

    };

    // Callback for spin wheel dismissal
    protected void onSpinWheelDismissed() {
        Log.d(TAG, "Spin wheel disconnected");
    }


}
