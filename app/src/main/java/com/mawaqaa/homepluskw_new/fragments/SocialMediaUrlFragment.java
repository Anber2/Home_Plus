package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

/**
 * Created by JijoCJ on 1/25/2017.
 */
public class SocialMediaUrlFragment extends HomePlusBaseFragment {
    String socialMediaName;
    WebView socialmediawebview;
    String socialMediaUrl=null;
    public static final String TAG="SocialMediaUrlFragment";

    public static SocialMediaUrlFragment newInstance(String socialMediaName){
        SocialMediaUrlFragment socialMediaUrlFragment = new SocialMediaUrlFragment();
        socialMediaUrlFragment.socialMediaName = socialMediaName;
        return socialMediaUrlFragment;

    }
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

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.fragmnet_socialmediaurl,container,false);
        ((HomePlusMainActivity) getActivity()).hideActionBar();
        ((HomePlusMainActivity) getActivity()).hideBottomBar();
        initView(rootview);
        return rootview;
    }

    private void initView(View rootview) {
        socialmediawebview=(WebView)rootview.findViewById(R.id.webViewLoadSocialMedia);
        socialmediawebview.getSettings().setJavaScriptEnabled(true);
        socialmediawebview.getSettings().setPluginState(WebSettings.PluginState.ON);
        socialmediawebview.setWebViewClient(new WebViewClient());
        if(Activity.isNetworkAvailable())
        {

            if(socialMediaName.equalsIgnoreCase("instagram")) {
                socialMediaUrl = PreferenceUtil.getInstagramUrl(Activity);
            }
           else if(socialMediaName.equalsIgnoreCase("twitter")) {
                socialMediaUrl = PreferenceUtil.getTwitterUrl(Activity);
            }
            else if(socialMediaName.equalsIgnoreCase("linkedin")) {
                socialMediaUrl = PreferenceUtil.getLinkediUrl(Activity);
            }
            else {
                socialMediaUrl = PreferenceUtil.getFacebookUrl(Activity);
            }
            Log.e(TAG, "Social Media Url:"+socialMediaUrl);
            socialmediawebview.loadUrl(socialMediaUrl);
        } else {
            Toast.makeText(Activity,R.string.alert_no_internet,Toast.LENGTH_LONG).show();
        }
        }


}
