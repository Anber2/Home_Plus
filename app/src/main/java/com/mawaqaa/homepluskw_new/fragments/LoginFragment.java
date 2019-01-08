package com.mawaqaa.homepluskw_new.fragments;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.mawaqaa.homepluskw_new.utility.GeneralUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JijoCJ on 10/28/2016.
 */
public class LoginFragment extends HomePlusBaseFragment implements View.OnClickListener{

    private static final String TAG = "LoginFragment";
    EditText etUserName, etPassword, etGender, etAge, etMobile;
    TextView txtForgetPassword,txtRememberme;
    Button btnRegister, btnLogin;
    CheckBox checkBoxKeepLogin;
    String Email="",Password="";

    boolean propertyServiceReqListener = false;

    public static LoginFragment newInstance(boolean propertyServiceReqListener){
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.propertyServiceReqListener = propertyServiceReqListener;
        return loginFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Activity = (HomePlusBaseActivity) this.getActivity();
        //Log.e(TAG, "propertyServiceReqListener"+propertyServiceReqListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume" + this.getClass().getName());
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).hideBottomBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).hideBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_login_title_action_bar));
        initView(view);

            Log.d(TAG, "@@@@@@");
            etUserName.setText(PreferenceUtil.getUserEmail(Activity));
            etPassword.setText(PreferenceUtil.getPassword(Activity));


        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.defaultButtonState();
        return view;
    }

    private void initView(View view) {
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ARABIC)) {
            txtRememberme = (TextView) view.findViewById(R.id.txtRememberme);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtRememberme);
            etUserName = (EditText) view.findViewById(R.id.etUserName);
            FontUtils.setHomeplusArabicRegularFont(Activity, etUserName);
            etPassword = (EditText) view.findViewById(R.id.etPassword);
            FontUtils.setHomeplusArabicRegularFont(Activity, etPassword);
            checkBoxKeepLogin = (CheckBox) view.findViewById(R.id.checkBoxKeepLogin);
            //checkBoxKeepLogin.setChecked(true);
            txtForgetPassword = (TextView) view.findViewById(R.id.txtForgetPassword);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtForgetPassword);
            txtForgetPassword.setPaintFlags(txtForgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            btnRegister = (Button) view.findViewById(R.id.btnRegister);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnRegister);
            btnLogin = (Button) view.findViewById(R.id.btnLogin);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnLogin);
        }
        else
        {
            txtRememberme = (TextView) view.findViewById(R.id.txtRememberme);
            FontUtils.setHomeplusRegularFont(Activity, txtRememberme);
            etUserName = (EditText) view.findViewById(R.id.etUserName);
            FontUtils.setHomeplusRegularFont(Activity, etUserName);
            etPassword = (EditText) view.findViewById(R.id.etPassword);
            FontUtils.setHomeplusRegularFont(Activity, etPassword);
            checkBoxKeepLogin = (CheckBox) view.findViewById(R.id.checkBoxKeepLogin);
           // checkBoxKeepLogin.setChecked(true);
            txtForgetPassword = (TextView) view.findViewById(R.id.txtForgetPassword);
            FontUtils.setHomeplusRegularFont(Activity, txtForgetPassword);
            txtForgetPassword.setPaintFlags(txtForgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            btnRegister = (Button) view.findViewById(R.id.btnRegister);
            FontUtils.setHomeplusRegularFont(Activity, btnRegister);
            btnLogin = (Button) view.findViewById(R.id.btnLogin);
            FontUtils.setHomeplusRegularFont(Activity, btnLogin);
        }
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);
    }

    private void sendLoginData() {
        try{
            if(Activity.isNetworkAvailable()){
                if(nullCheck()){
                    if(etUserName.getText().toString().matches(AppConstants.EMAIL_PATTERN)){
                        if(etPassword.getText().toString().length()>= 6){
                            Password=etPassword.getText().toString();
                            createJsonData();
                        }else{
                            Toast.makeText(Activity, getString(R.string.alert_invalid_password),Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Activity, getResources().getString(R.string.alert_invalid_email), Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createJsonData() {
        try{
            JSONObject jsonObject =  new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.NAME, etUserName.getText().toString());
            jsonObject.putOpt(AppConstants.PASSWORD, etPassword.getText().toString());
            Log.e(TAG, "json request:"+jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if(VolleyUtils.volleyEnabled){
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.LOGIN, jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean nullCheck() {
        if(etUserName.getText().toString().length() <=0 ||
                etPassword.getText().toString().length() <= 0){
            Toast.makeText(Activity, getString(R.string.alert_no_empty_field), Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onLoginDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onLoginDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "json response:"+jsonObject);
        parseJsonData(jsonObject);
    }

    private void parseJsonData(JSONObject jsonObject){
        try{
            if(jsonObject != null){
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)){
                    Email=jsonObject.getString(AppConstants.LOGINEMAIL);
                    Log.d("useremail",""+Email);


                    // refresh for login in drawer to change as myaccount
                    ((HomePlusMainActivity) getActivity()).setDrawerInLogin(Activity);
                    if(!propertyServiceReqListener){
                        if(checkBoxKeepLogin.isChecked()) {
                            PreferenceUtil.setEmailId(Activity,Email);
                            PreferenceUtil.setPassword(Activity,Password);
                            PreferenceUtil.setKeepMeSignedIn(Activity, true);
                            Activity.popFragments(new PreHomeFragment());
                        }else{
                            PreferenceUtil.setEmailId(Activity,"");
                            PreferenceUtil.setPassword(Activity,"");
                            PreferenceUtil.setKeepMeSignedIn(Activity, true);
                            Activity.popFragments(new PreHomeFragment());
                        }
                        //PreferenceUtil.setKeepMeSignedIn(Activity, true);
                        PreferenceUtil.setUserSignedIn(Activity, true);
                        PreferenceUtil.setUserDetails(Activity, jsonObject);
                        Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Keep Signed In:"+PreferenceUtil.isKeepMeSignedIn(Activity));
                        registerDevice();
                        Activity.popFragments(this);
                        Activity.pushFragments(new HomeFragment(), true, true);
                    }else{
                        if(checkBoxKeepLogin.isChecked()) {
                            PreferenceUtil.setEmailId(Activity,Email);
                            PreferenceUtil.setPassword(Activity,Password);
                            PreferenceUtil.setKeepMeSignedIn(Activity, true);
                            Activity.popFragments(new PreHomeFragment());
                        }else{
                            PreferenceUtil.setEmailId(Activity,"");
                            PreferenceUtil.setPassword(Activity,"");
                            PreferenceUtil.setKeepMeSignedIn(Activity, true);
                            Activity.popFragments(new PreHomeFragment());
                        }
                        PreferenceUtil.setUserDetails(Activity, jsonObject);
                        PreferenceUtil.setUserSignedIn(Activity, true);
                        Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Keep Signed In:"+PreferenceUtil.isKeepMeSignedIn(Activity));
                        registerDevice();
                        propertyServiceReqListener = false;
                        Activity.popFragments(this);
                        Activity.pushFragments(new RequestProptyServiceFragment(), true, true);
                    }

                }else
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(Activity, "Error, Please try again", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            BaseApplication.getInstance().progressOFF();
        }
    }

    @Override
    public void onLoginDataLoadedFailed(JSONObject jsonObject) {
        super.onLoginDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    private void sendEmail4ForgotPassword() {
        if(Activity.isNetworkAvailable()){
            if(etUserName.getText().toString().length()>0){
                if(etUserName.getText().toString().matches(AppConstants.EMAIL_PATTERN)){
                    crateJson4ForgotPwd();
                }
            }else
                Toast.makeText(Activity, getString(R.string.alert_enter_email_id), Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void crateJson4ForgotPwd() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.EMAIL, etUserName.getText().toString());
            Log.e(TAG, "Json Reques:"+jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if(VolleyUtils.volleyEnabled){
                CommandFactory commandFactory =  new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_FORGOT_PASSWORD, jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onForgotPasswordDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onForgotPasswordDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseForgotPwdJsonResponse(jsonObject);

    }

    private void parseForgotPwdJsonResponse(JSONObject jsonObject) {
        try{
            if(jsonObject!= null){
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)){
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onForgotPasswordDataLoadedFailed(JSONObject jsonObject) {
        super.onForgotPasswordDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }


    public void registerDevice(){
        try {
            SharedPreferences pref = Activity.getSharedPreferences(AppConstants.SHARED_PREF, 0);
            String regId = pref.getString("regId", null);
            if(Activity.isNetworkAvailable()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.DEVICE_ID, GeneralUtils.getDeviceId(Activity));
                jsonObject.putOpt(AppConstants.DEVICE_TOKEN, regId);
                jsonObject.putOpt(AppConstants.DEVICE_PLATFORM, 0);
                jsonObject.putOpt(AppConstants.USER_ID, PreferenceUtil.getUserId(Activity));
                Log.e(TAG, "Json Req:"+ jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                if(VolleyUtils.volleyEnabled){
                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_DEVICE_REGISTRATION, jsonObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseApplication.getInstance().progressOFF();
        }
    }

    @Override
    public void onRegisterDeviceLoadedSuccessfully(JSONObject jsonObject) {
        super.onRegisterDeviceLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        try {
            Log.e(TAG, "Device Registration"+jsonObject.getString(AppConstants.MESSAGE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRegisterDeviceLoadedLoadedFailed(JSONObject jsonObject) {
        super.onRegisterDeviceLoadedLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
        try {
            Log.e(TAG, "Device Registration"+jsonObject.getString(AppConstants.MESSAGE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
//                Activity.popFragments(this);
                Activity.pushFragments(new RegisterFragment(), false, true);
                break;
            case R.id.btnLogin:

                sendLoginData();
                break;
            case R.id.txtForgetPassword:
                sendEmail4ForgotPassword();
                break;
            default:
                break;
        }
    }

}
