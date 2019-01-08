package com.mawaqaa.homepluskw_new.utility;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;

import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.fragments.MyAccountFragment;
import com.mawaqaa.homepluskw_new.fragments.MyAccountMenuFragment;
import com.mawaqaa.homepluskw_new.fragments.SocialMediaFragment;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;


public class DrawerUtils {
    protected final static String TAG = "DrawerUtils";

    private static SocialMediaFragment socialMediaFragment;

    public static final void openDrawerView(Context context,
                                            DrawerLayout mDrawerLayout, ImageView btnMore) {
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState)context;
        buttonClickState.MoreButtonState();
        try {
            if (HomePlusBaseActivity.getHpBaseActivity().getCurrentFragment() == new MyAccountFragment().getClass().getName()) {
                Log.e("DrawerUtils","jvsd");
                FragmentManager manager= HomePlusMainActivity.getHpBaseActivity().getSupportFragmentManager();
                MyAccountMenuFragment menuFragment=new MyAccountMenuFragment();
                menuFragment.show(manager,TAG);
            } else{
            if (PreferenceUtil.getLanguage(context).equals(
                    AppConstants.HP_ENGLISH)) {
                Log.e(TAG, "Language in OpenDrawer() English");
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    Log.e(TAG, "Language in OpenDrawer() english set to inactive");
                    //btnMore.setBackgroundResource(R.drawable.drawer_menu_icon_inactive);
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    //btnMore.setBackgroundResource(R.drawable.drawer_menu_icon_active);
                    mDrawerLayout.openDrawer(Gravity.RIGHT | Gravity.BOTTOM);
                }
            } else {
                Log.e(TAG, "Language in OpenDrawer() Arabic");
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    //btnMore.setBackgroundResource(R.drawable.drawer_menu_icon_inactive);
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    //btnMore.setBackgroundResource(R.drawable.drawer_menu_icon_active);
                    mDrawerLayout.openDrawer(Gravity.LEFT | Gravity.BOTTOM);
                }
            }}
        } catch (Exception e) {
            Log.e(TAG, "Exception in OpenDrawer Method");
            e.printStackTrace();
        }
    }

    public static final boolean closeDrawerVeiw(Context context,
                                             DrawerLayout mDrawerLayout, ImageView btnMore) {
        try {
            Log.e(TAG, "Language in CloseDrawer() in Language:" + PreferenceUtil.getLanguage(context).equals(
                    AppConstants.HP_ENGLISH));
            //btnMore.setBackgroundResource(R.drawable.drawer_menu_icon_inactive);
            if (PreferenceUtil.getLanguage(context).equals(
                    AppConstants.HP_ENGLISH)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
               return false;
            } else {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in closeDrawer Method");
            e.printStackTrace();
        }
        return true;
    }

//    public static final void closeDrawerVeiw(Context context,
//                                             DrawerLayout mDrawerLayout, ImageView btnMore) {
//        try {
//            Log.e(TAG, "Language in CloseDrawer() in Language:" + PreferenceUtil.getLanguage(context).equals(
//                    AppConstants.HP_ENGLISH));
//            //btnMore.setBackgroundResource(R.drawable.drawer_menu_icon_inactive);
//            if (PreferenceUtil.getLanguage(context).equals(
//                    AppConstants.HP_ENGLISH))
//                mDrawerLayout.closeDrawer(Gravity.RIGHT);
//            else
//                mDrawerLayout.closeDrawer(Gravity.LEFT);
//        } catch (Exception e) {
//            Log.e(TAG, "Exception in closeDrawer Method");
//            e.printStackTrace();
//        }
//    }

    /* showing and dismissing Social Media Fragment */
    public static void showSocialMediaDialog(FragmentManager fm, String tag) {
        Log.d(TAG, "showSocialMediaDialog()");
        if (socialMediaFragment == null)
            socialMediaFragment = new SocialMediaFragment();
        else if (socialMediaFragment.isVisible()) {
            //socialMediaFragment = new SocialMediaFragment();
            return;
        }
        socialMediaFragment.setCancelable(true);
        socialMediaFragment.show(fm, tag);
    }

    public static void dismissSocialDialog() {
        Log.d(TAG, "dismissSocialDialog()");
        if (socialMediaFragment != null && socialMediaFragment.isVisible())
            socialMediaFragment.dismiss();
    }


    public static void dismissShareSocialDialog() {
        Log.d(TAG, "dismissSocialDialog()");
        if (socialMediaFragment != null && socialMediaFragment.isVisible())
            socialMediaFragment.dismiss();
    }
}
