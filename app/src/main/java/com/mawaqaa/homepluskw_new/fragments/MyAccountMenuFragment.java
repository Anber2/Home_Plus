package com.mawaqaa.homepluskw_new.fragments;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.interfaces.DrawerPopUpListener;
import com.mawaqaa.homepluskw_new.interfaces.LanguageSwitcherListener;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

/**
 * Created by anson on 4/21/2017.
 */

public class MyAccountMenuFragment extends DialogFragment implements View.OnClickListener {
    String TAG = "MyAccountMenuFragment";
    RelativeLayout rlLanguage, rlAboutUs, rlMyAccount, rlMediaCenter, rlSocialMedia, rlCOntactUs;
    HomePlusBaseActivity Activity;



    public static MyAccountMenuFragment newInstance(DrawerPopUpListener mPopUpListener, LanguageSwitcherListener mLanguageSwitcher) {
        MyAccountMenuFragment menuFragment = new MyAccountMenuFragment();

        return menuFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setStyle(STYLE_NO_FRAME, 0);
        Activity = (HomePlusBaseActivity) this.getActivity();
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
            getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogueAnim;
        else
            getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogueAnim_Ar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_account_menu, container, false);
        rlAboutUs = (RelativeLayout) view.findViewById(R.id.rl_about_us);
        rlCOntactUs = (RelativeLayout) view.findViewById(R.id.rl_contact_us);
        rlLanguage = (RelativeLayout) view.findViewById(R.id.rl_language);
        rlMyAccount = (RelativeLayout) view.findViewById(R.id.rl_my_account);
        rlMediaCenter = (RelativeLayout) view.findViewById(R.id.rl_media_center);
        rlSocialMedia = (RelativeLayout) view.findViewById(R.id.rl_social_media);
        rlSocialMedia.setOnClickListener(this);
        rlMediaCenter.setOnClickListener(this);
        rlMyAccount.setOnClickListener(this);
        rlLanguage.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlCOntactUs.setOnClickListener(this);

        getDialog().setCanceledOnTouchOutside(true);
        setDialogPosition();

        return view;
    }

    private void setDialogPosition() {
        /*if (((MainActivity) getActivity()).getmTabHost() == null) {
            return; // Leave the dialog in default position
		}*/
        Window window = getDialog().getWindow();
        WindowManager wm = window.getWindowManager();
        Display mdisp = wm.getDefaultDisplay();
        Point mdispSize = new Point();
        int maxX = 0;
        if (Build.VERSION.SDK_INT >= 13) {
            mdisp.getSize(mdispSize);
            maxX = mdispSize.x;
        } else {
            maxX = mdisp.getWidth(); // deprecated

        }
        int rowCount = 1; // for not become zero in multiplication


        // set "origin" to top left corner
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
            window.setGravity(Gravity.TOP | Gravity.LEFT);
        else
            window.setGravity(Gravity.TOP | Gravity.RIGHT);

        WindowManager.LayoutParams params = window.getAttributes();

        params.x = (int) calculateWidth(maxX);
        params.y = (int) calucalateHeight(rowCount);

        window.setAttributes(params);
    }


    private float getTabHeight() {
        int[] location = new int[2];
        //tabWidget.getLocationOnScreen(location);
        int sourceY = location[1];
        return sourceY;
    }

    private float calculateWidth(int maxX) {
        float dailogueWidth = dpToPx(250); // assumes listviews height is 250
        float sourceX = maxX - (dailogueWidth);
        return sourceX;
    }

    private float calucalateHeight(int rowCount) {

        float dialogueHeight = rowCount * dpToPx(51);// assumes each more items
        // height is 50
        float tabHeight = getTabHeight();
        float sourceY = tabHeight - dialogueHeight;
        return sourceY;
    }

    public int dpToPx(float valueInDp) {
        DisplayMetrics metrics = getActivity().getResources()
                .getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                valueInDp, metrics);
    }


    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        //moreFragListener = (MoreFragListener) activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0.8f; // dim only a little bit
            params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            //Window w = getWindow(); // in Activity's onCreate() for instance

        } else {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0.8f; // dim only a little bit
            params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_about_us:
                Log.e(TAG, "about us");
                getDialog().dismiss();
                HomePlusBaseActivity.getHpBaseActivity().pushFragments(new AboutUsFragment(), false, true);
                break;
            case R.id.rl_contact_us:
                getDialog().dismiss();
                HomePlusBaseActivity.getHpBaseActivity().pushFragments(new ContactusFragment(), false, true);
                break;
            case R.id.rl_language:
                getDialog().dismiss();
                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePlusBaseActivity.getHpBaseActivity());
                    // set title
                    alertDialogBuilder.setTitle(R.string.language);

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(R.string.language_flip)
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            HomePlusMainActivity.mLanguageListener.doLanguageSwitch();
                                            // do whatever you want to do when user
                                            // clicks ok
                                        /*PreferenceUtil.setKeepMeSignedIn(context, false);
                                        Toast.makeText(HomePlusBaseActivity.getHpBaseActivity(), context.getString(R.string.alert_log_out),
												Toast.LENGTH_SHORT).show();
										HomePlusBaseActivity.getHpBaseActivity().clearAllBackStackEntries();

										Fragment loginFragment = new LoginFragment();
										HomePlusBaseActivity.getHpBaseActivity().pushFragments(loginFragment, false, true);*/
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_media_center:
                getDialog().dismiss();
                HomePlusBaseActivity.getHpBaseActivity().pushFragments(new MediaCenterPhotoGalleryFragment(), false, true);

                break;
            case R.id.rl_my_account:
                getDialog().dismiss();
                break;
            case R.id.rl_social_media:
                HomePlusMainActivity.mPopUpListener.onDrawerItemClick(rlSocialMedia, 5);
                break;
            default:
                break;
        }
    }
}
