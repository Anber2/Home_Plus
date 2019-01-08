package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by JijoCJ on 1/5/2017.
 */
public class ContactusFeedbackFragment extends HomePlusBaseFragment implements View.OnClickListener {
    public static final String TAG="Feedbackform";
    TextView sendfeed_back;
    Button btn_addressInformation,btn_feeddback,btn_feedback_submit,btn_feedback_rest;
    EditText feedBack_name,feedBack_email,feedBack_telno,feedBack_mobno,feedBack_message,
            feedBack_subject,edit_feedback_lastname;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_feedbackform,container,false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_my_account_title_action_bar));
        initView(rootView);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.constructionButtonState();


        return rootView;
    }

    private void initView(View rootView) {
        sendfeed_back=(TextView)rootView.findViewById(R.id.sendfeed_back);
        feedBack_name=(EditText)rootView.findViewById(R.id.edit_feedback_fname);
        feedBack_email=(EditText)rootView.findViewById(R.id.edit_feedback_Email);
        feedBack_telno=(EditText)rootView.findViewById(R.id.edit_feedback_telephno);
        feedBack_mobno=(EditText)rootView.findViewById(R.id.edit_feedback_mobileno);
        feedBack_subject=(EditText)rootView.findViewById(R.id.editsubject);
        feedBack_message=(EditText)rootView.findViewById(R.id.edit_feedback_message);
        btn_addressInformation=(Button)rootView.findViewById(R.id.btnAddrsinformation);
        btn_feeddback=(Button)rootView.findViewById(R.id.btnTabFeedback);
        btn_feedback_submit=(Button)rootView.findViewById(R.id.btn_feedback_submit);
        btn_feedback_rest=(Button)rootView.findViewById(R.id.btn_feedback_reset);
        edit_feedback_lastname=(EditText)rootView.findViewById(R.id.edit_feedback_lastname);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(Activity,feedBack_name);
            FontUtils.setHomeplusRegularFont(Activity,feedBack_email);
            FontUtils.setHomeplusRegularFont(Activity,feedBack_telno);
            FontUtils.setHomeplusRegularFont(Activity,feedBack_mobno);
            FontUtils.setHomeplusRegularFont(Activity,feedBack_subject);
            FontUtils.setHomeplusRegularFont(Activity,feedBack_message);
            FontUtils.setHomeplusRegularFont(Activity,btn_addressInformation);
            FontUtils.setHomeplusRegularFont(Activity,btn_feeddback);
            FontUtils.setHomeplusRegularFont(Activity,sendfeed_back);
            FontUtils.setHomeplusRegularFont(Activity,edit_feedback_lastname);
            btn_addressInformation.setSelected(false);
            btn_feeddback.setSelected(true);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity,feedBack_name);
            FontUtils.setHomeplusArabicRegularFont(Activity,feedBack_email);
            FontUtils.setHomeplusArabicRegularFont(Activity,feedBack_telno);
            FontUtils.setHomeplusArabicRegularFont(Activity,feedBack_mobno);
            FontUtils.setHomeplusArabicRegularFont(Activity,feedBack_subject);
            FontUtils.setHomeplusArabicRegularFont(Activity,feedBack_message);
            FontUtils.setHomeplusArabicRegularFont(Activity,btn_addressInformation);
            FontUtils.setHomeplusArabicRegularFont(Activity,sendfeed_back);
            FontUtils.setHomeplusArabicRegularFont(Activity,btn_feeddback);
            FontUtils.setHomeplusArabicRegularFont(Activity,edit_feedback_lastname);
            btn_addressInformation.setSelected(false);
            btn_feeddback.setSelected(true);
        }
        btn_addressInformation.setOnClickListener(this);
        btn_feeddback.setOnClickListener(this);
        btn_feedback_submit.setOnClickListener(this);
        btn_feedback_rest.setOnClickListener(this);
        //fetchRequestFormData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnAddrsinformation:
                Activity.pushFragments(new ContactusFragment(), false, true);
                break;
            case  R.id.btnTabFeedback:
                Activity.pushFragments(new ContactusFeedbackFragment(), false, true);
            case R.id.btn_feedback_submit:
                fetchRequestFormData();
                break;
            case  R.id.btn_feedback_reset:
                restField();
                break;
        }
    }

    private void restField() {
        feedBack_name.setText("");
        feedBack_email.setText("");
        feedBack_mobno.setText("");
        feedBack_telno.setText("");
        feedBack_subject.setText("");
        feedBack_message.setText("");
        edit_feedback_lastname.setText("");

    }

    private void fetchRequestFormData() {
        if(nullCheck()) {
            if (feedBack_email.getText().toString().trim()
                    .matches(AppConstants.EMAIL_PATTERN)) {

                if (feedBack_mobno.getText().toString().trim().length() >= 8) {
                    if (Activity.isNetworkAvailable())
                        createJsontoPost();
                    else
                        Toast.makeText(getActivity(),
                                R.string.alert_no_internet,
                                Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), R.string.invalid_phone,
                            Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(), R.string.invalid_email,
                        Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(getActivity(), R.string.alert_no_empty_field,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void createJsontoPost() {
        try {

            JSONObject jsonObject = new JSONObject();


            jsonObject.putOpt(AppConstants.FEEDBACK_EMAIL, feedBack_email.getText()
                    .toString().trim());
            jsonObject.putOpt(AppConstants.FEEDBACK_NAME, feedBack_name.getText().toString()
                    .trim());
            jsonObject.putOpt(AppConstants.LASTNAME,edit_feedback_lastname.getText().toString().trim());
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));

            jsonObject.putOpt(AppConstants.FEEDBACK_MESSAGE, feedBack_message.getText()
                    .toString().trim());

            jsonObject.putOpt(AppConstants.FEEDBACK_MOBNO,feedBack_mobno.getText().toString().trim());
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.FEEDBACK_SUBJECT, feedBack_subject.getText().toString().trim());
            jsonObject.putOpt(AppConstants.FEEDBACK_TELNO, feedBack_telno.getText().toString().trim());
            BaseApplication.getInstance().progressON(getActivity(), null);
//            startSpinwheel(false, true);
            if (VolleyUtils.volleyEnabled) {
                Log.d("Json:",""+jsonObject.toString());
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_FEDDBACK_FORM,
                        jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception in Creating JSON");
        }
    }

    @Override
    public void onFeedbackformRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onFeedbackformRequestDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        try{
            if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                Activity.popFragments(this);
            }

            else {
                Toast.makeText(Activity, R.string.alert_no_internet, Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFeedbackformRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onFeedbackformRequestDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    private boolean nullCheck() {

        if (feedBack_name.getText().toString().trim().length() <= 0

                || feedBack_email.getText().toString().trim().length() <= 0
                || feedBack_mobno.getText().toString().trim().length() <= 0
                || feedBack_telno.getText().toString().trim().length() <= 0
                || feedBack_subject.getText().toString().trim().length() <= 0
                || feedBack_message.getText().toString().trim().length() <= 0||
                edit_feedback_lastname.getText().toString().trim().length() <=0)
        {
            Log.e(TAG, "Null Fields");
            return false;
        } else {
            Log.e(TAG, "No Null Fields");
            return true;
        }
    }


}
