package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.interfaces.DrawerLocker;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by JijoCJ on 10/26/2016.
 */
public class PreHomeFragment extends HomePlusBaseFragment implements View.OnClickListener {

    private static final String TAG = "PreHomeFragment";
    ImageButton imgBtnSignUp, imgBtnSkip;

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
        ((HomePlusMainActivity) getActivity()).hideBottomBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pre_home, container, false);
        ((HomePlusMainActivity) getActivity()).hideActionBar();
        ((HomePlusMainActivity) getActivity()).hideBottomBar();
        // FetchSocialmediaUrl();
        initView(view);
        PreferenceUtil.setUserId(Activity, "");
        //PreferenceUtil.setUserSignedIn(Activity, false);
        DrawerLocker locker = (DrawerLocker) Activity;
        locker.setDrawerEnabled(false);
        return view;
    }

    private void initView(View view) {
        imgBtnSkip = (ImageButton) view.findViewById(R.id.imgBtnSkip);
        FontUtils.setHomeplusBoldFont(Activity, imgBtnSkip);
        imgBtnSignUp = (ImageButton) view.findViewById(R.id.imgBtnSignUp);
        FontUtils.setHomeplusBoldFont(Activity, imgBtnSignUp);
        imgBtnSignUp.setOnClickListener(this);
        imgBtnSkip.setOnClickListener(this);
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
                Toast.makeText(Activity, "testing" + getString(R.string.alert_no_internet), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSocialmediaRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onSocialmediaRequestDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        FetchUrl(jsonObject);
    }

    @Override
    public void onSocialmediaRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onSocialmediaRequestDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    private void FetchUrl(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.HP_SOCIALMEDIA_ARRAY);
                    if (jsonArray.length() > 0) {
                        //if(jsonArray.getJSONObject(0).getString(AppConstants.HP_MEDIANAME))
                        Log.e(TAG, "Socialmedia1" + jsonArray.getJSONObject(0).getString(AppConstants.HP_MEDIA_LINK));
                        PreferenceUtil.setInstagramUrl(Activity, jsonArray.getJSONObject(0).getString(AppConstants.HP_MEDIA_LINK));
                       /* Log.e(TAG, "Socialmedia2" + jsonArray.getJSONObject(1).getString(AppConstants.HP_MEDIA_LINK));
                        PreferenceUtil.setTwitterUrl(Activity,jsonArray.getJSONObject(1).getString(AppConstants.HP_MEDIA_LINK));
                        Log.e(TAG, "Socialmedia3" + jsonArray.getJSONObject(2).getString(AppConstants.HP_MEDIA_LINK));
                        PreferenceUtil.setLinkedinUrl(Activity,jsonArray.getJSONObject(2).getString(AppConstants.HP_MEDIA_LINK));
                        Log.e(TAG, "Socialmedia4" + jsonArray.getJSONObject(3).getString(AppConstants.HP_MEDIA_LINK));
                        PreferenceUtil.setFacebookUrl(Activity,jsonArray.getJSONObject(3).getString(AppConstants.HP_MEDIA_LINK));
*/
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
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            BaseApplication.getInstance().progressOFF() ();
            BaseApplication.getInstance().progressOFF();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnSkip:
                PreferenceUtil.setUserId(Activity, "");
                PreferenceUtil.setUserSignedIn(Activity, false);
                Activity.pushFragments(new HomeFragment(), false, true);
                break;
            case R.id.imgBtnSignUp:
                Activity.pushFragments(LoginFragment.newInstance(false), false, true);
                break;
            default:
                break;
        }
    }
}
