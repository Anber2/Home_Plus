package com.mawaqaa.homepluskw_new.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mawaqaa.homepluskw_new.constants.AppConstants;

import org.json.JSONObject;

/**
 * Created by JijoCJ on 10/26/2016.
 */
public class PreferenceUtil {

    public static SharedPreferences sp;

    private final static String TAG = "PreferenceUtil";
    private static final String SHARED_APP_SETTINGS = "PreferenceUtil";
    private final static String IS_USER_SIGNED_IN = "is_user_signed_in";
    private final static String IS_GUEST_USER = "is_guest_user";
    private final static String IS_KEEPME_SIGNED_IN = "is_keepme_signed_in";
    private final static String HP_LANGUAGE = "language";
    private final static String EXPO_BASKET_COUNT = "basket_count";
    private final static String EXPO_PUSH_NOTIFICATION = "push_notification";
    private final static String EXPO_NEWSLETTER = "newsletter";
    private final static String MOF_TAB_VALUE = "tabValue";
    private static final String IS_SHOWN_TUTORIAL = "is_show_tutorial";

    private static final String IS_APP_FIRSTTIME = "is_app_first_time";
    private SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    public PreferenceUtil(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_APP_SETTINGS, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }


    public final static void setTutorialShowStatus(Context context, boolean status){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(IS_SHOWN_TUTORIAL, status).apply();;
    }
    public final static boolean isTutorialShows(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(IS_SHOWN_TUTORIAL, false);
    }


    public final static boolean isAppFirstTime(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);

        return sp.getBoolean(IS_APP_FIRSTTIME, true);
    }
    public final static void setAppFirstTimeStatus(Context context, boolean status){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        Log.e(TAG,"status>>>>"+status);
        sp.edit().putBoolean(IS_APP_FIRSTTIME, status).apply();;
    }


    public final static void setUserSignedIn(Context context,boolean signed_in){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(IS_USER_SIGNED_IN, signed_in).apply();;
    }

    public final static boolean isUserSignedIn(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(IS_USER_SIGNED_IN, false);
    }

    public final static void setKeepMeSignedIn(Context context, boolean flag){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(IS_KEEPME_SIGNED_IN, flag).apply();;
    }
    public final static boolean isKeepMeSignedIn(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(IS_KEEPME_SIGNED_IN, false);
    }

    /*Language Preference Part*/
    public final static void setLanguage(Context context,String lang){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(HP_LANGUAGE, lang).apply();;
    }
    public final static String getLanguage(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(HP_LANGUAGE, "en");
    }
	public final static void setUserDetails(Context context,JSONObject jsonObject){
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		try{
			SharedPreferences.Editor edit = sp.edit();
			if(jsonObject.has(AppConstants.NAME))
				edit.putString(AppConstants.NAME, jsonObject.getString(AppConstants.NAME));
			else
				edit.putString(AppConstants.NAME,"");
			if(jsonObject.has(AppConstants.EMAIL))
				edit.putString(AppConstants.EMAIL,jsonObject.getString(AppConstants.EMAIL));
			else
				edit.putString(AppConstants.EMAIL,"");
			if(jsonObject.has(AppConstants.MOBILE_NO))
				edit.putString(AppConstants.MOBILE_NO, jsonObject.getString(AppConstants.MOBILE_NO));
			else
				edit.putString(AppConstants.MOBILE_NO,"");
			if(jsonObject.has(AppConstants.USER_ID))
				edit.putString(AppConstants.USER_ID,jsonObject.getString(AppConstants.USER_ID));
			else
				edit.putString(AppConstants.USER_ID, AppConstants.USER_ID);
            if(jsonObject.has(AppConstants.AGE))
                edit.putString(AppConstants.AGE, jsonObject.getString(AppConstants.AGE));
            else
                edit.putString(AppConstants.AGE, "");
            if(jsonObject.has(AppConstants.GENDER))
                edit.putString(AppConstants.GENDER, jsonObject.getString(AppConstants.GENDER));
            else
                edit.putString(AppConstants.GENDER, "");
		edit.apply();
		}catch(Exception e){
			Log.e(TAG,"Exception in Setting User Details");
			e.printStackTrace();
		}
	}

	public final static String getUserId(Context context){
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getString(AppConstants.USER_ID, "");
	}
	public final static void setUserId(Context context,String USERID){
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putString(AppConstants.USER_ID, USERID).apply();;
	}

    public final static void setBasketCount(Context context,int count){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(EXPO_BASKET_COUNT, count).apply();;
    }
    public final static int getBasketCount(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(EXPO_BASKET_COUNT,0);
    }
    public final static void setasGuestUser(Context context,boolean signed_in){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(IS_GUEST_USER, signed_in).apply();;
    }
    public final static boolean isGuestUser(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(IS_GUEST_USER, false);
    }
    public final static void setPushEnable(Context context, boolean status) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(EXPO_PUSH_NOTIFICATION, status).apply();
        ;
    }

    public final static void setNewsletterEnable(Context context, boolean status) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(EXPO_NEWSLETTER, status).apply();
        ;
    }

    public final static boolean isPushEnable(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(EXPO_PUSH_NOTIFICATION, false);
    }

    public final static boolean isNewsletterEnable(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(EXPO_NEWSLETTER, false);
    }


    public static void setInstagramUrl(Context context, String value) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(AppConstants.HP_INSTAGRAM, value).apply();
    }
    public final static String getInstagramUrl(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(AppConstants.HP_INSTAGRAM,"");
    }

    public static void setTwitterUrl(Context context, String value) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(AppConstants.HP_MEDIA_LINK, value).apply();
    }
    public final static String getTwitterUrl(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(AppConstants.HP_TWITTER,"");
    }

    public static void setLinkedinUrl(Context context, String value) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(AppConstants.HP_TWITTER, value).apply();
    }

    public final static String getLinkediUrl(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(AppConstants.HP_LINKEDIN,"");
    }

    public static void setFacebookUrl(Context context, String value) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(AppConstants.HP_FACEBOOK, value).apply();
    }
    public final static String getFacebookUrl(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(AppConstants.HP_FACEBOOK,"");
    }
    public final static void setEmailId(Context context,String email){
        Log.e("preference","))))))"+email);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(AppConstants.LOGINEMAIL, email).apply();;
    }
    public final static String getUserEmail(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(AppConstants.LOGINEMAIL, "");
    }
    public final static void setPassword(Context context,String password){
        Log.e("preference","))))))"+password);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(AppConstants.PASSWORDLOGIN, password).apply();
    }
    public final static String getPassword(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(AppConstants.PASSWORDLOGIN, "");
    }
}

