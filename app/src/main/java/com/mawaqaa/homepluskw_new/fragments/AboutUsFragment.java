package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;

import org.json.JSONObject;

/**
 * Created by JijoCJ on 1/4/2017.
 */
public class AboutUsFragment extends HomePlusBaseFragment implements View.OnClickListener{
    public static final String TAG="Abtfrag";
   WebView webviewAboutus;
    String contactInfo;
    //TextView textViewaboutus;

    Button btn_corporateView,btn_partners;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_aboutus,container,false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.about_us));
        initView(rootView);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.MoreButtonState();
        return rootView;

    }

    private void initView(View rootView) {
        fetchRequestFormData();
       // textViewaboutus=(TextView)rootView.findViewById(R.id.textViewaboutus);
       webviewAboutus=(WebView)rootView.findViewById(R.id.webViewaboutus);
        /*WebSettings webSettings = webviewAboutus.getSettings();
        Typeface font = Typeface.createFromAsset(Activity.getAssets(), "fonts/NEOSANSARABIC.TTF");
        webSettings.setFixedFontFamily(String.valueOf(font));*/
        btn_corporateView=(Button)rootView.findViewById(R.id.btnCorporateview);
        btn_partners=(Button)rootView.findViewById(R.id.btnTabPartners);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
          //  FontUtils.setHomeplusRegularFont(Activity,textViewaboutus);
            FontUtils.setHomeplusRegularFont(Activity,btn_corporateView);
            FontUtils.setHomeplusRegularFont(Activity,btn_partners);
            btn_corporateView.setSelected(true);
            btn_partners.setSelected(false);
        }
        else {

           // FontUtils.setHomeplusArabicRegularFont(Activity,textViewaboutus);
            FontUtils.setHomeplusArabicRegularFont(Activity, btn_corporateView);
            FontUtils.setHomeplusArabicRegularFont(Activity, btn_partners);
            btn_corporateView.setSelected(true);
            btn_partners.setSelected(false);
        }

        btn_corporateView.setOnClickListener(this);
        btn_partners.setOnClickListener(this);



    }

    private void fetchRequestFormData() {
        try{
            if(Activity.isNetworkAvailable()){

                JSONObject jsonObject = new JSONObject();

                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                Log.e(TAG, "Json Req:"+jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_ABOUTUS, jsonObject);
            }else{
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void onAboutusRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onAboutusRequestDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseServiceRequestLoadData(jsonObject);

    }

    @Override
    public void onAboutusRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onAboutusRequestDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    private void parseServiceRequestLoadData(JSONObject jsonObject) {
        if(jsonObject!=null) {
            Log.d("json::",""+jsonObject);
            try {

                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS))
                {
                    Log.e("html...contact",""+jsonObject.getString(AppConstants.MESSAGE));
                    contactInfo = jsonObject.getString(AppConstants.MESSAGE);
                    //textViewaboutus.setText(Html.fromHtml(contactInfo));
                   webviewAboutus.getSettings().setJavaScriptEnabled(true);
                  //webviewAboutus.loadData(contactInfo, "text/html; charset=utf-8", "UTF-8");
                    if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ARABIC)) {
                        Log.e("BVBV","here");
                        contactInfo =  FontUtils.getHtmlDatawithArabicFont(Activity, contactInfo);
                        webviewAboutus.loadDataWithBaseURL(null,
                                contactInfo, "text/html", "UTF-8", "");
                    }else{
                        contactInfo =  FontUtils.getHtmlDatawithEnglishFont(Activity, contactInfo);
                       webviewAboutus.loadDataWithBaseURL(null,
                                contactInfo, "text/html", "UTF-8", "");

                    }
                }
                else
                {
                    contactInfo = getResources().getString(R.string.nodatahtml);
                    //textViewaboutus.setText(contactInfo);
                    webviewAboutus.getSettings().setJavaScriptEnabled(true);
                    webviewAboutus.loadData(contactInfo, "text/html; charset=utf-8", "UTF-8");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(Activity, "Error, Please try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCorporateview:
                break;
            case  R.id.btnTabPartners:
                Activity.pushFragments(new AboutUsPartnersFrag(), false, true);
                break;
        }


    }
}
