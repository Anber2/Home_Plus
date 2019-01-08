package com.mawaqaa.homepluskw_new.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by JijoCJ on 10/27/2016.
 */
public class RegisterFragment extends HomePlusBaseFragment implements View.OnClickListener {
    String Gender_array[] = {"", "Male", "Female"};
    String Gender_array_ar[] = {"", "ذكر", "أنثى"};
    String selectedItemText;
    private static final String TAG = "RegisterFragment";
    EditText etName, etEmail, etAge, etMobile, etPwd, etConfirmPwd;
    Spinner etGender;
    TextView spinnerTextView;
    Button btnRegisterAccount;
    ArrayAdapter<String> spinnerArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Activity = (HomePlusBaseActivity) this.getActivity();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume" + this.getClass().getName());
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_register_title_action_bar));
        initView(view);
        return view;
    }

    private void initView(View view) {
        etName = (EditText) view.findViewById(R.id.etName);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPwd = (EditText) view.findViewById(R.id.etPwd);
        etConfirmPwd = (EditText) view.findViewById(R.id.etConfirmPwd);
        etGender = (Spinner) view.findViewById(R.id.etGender);
        etAge = (EditText) view.findViewById(R.id.etAge);
        etMobile = (EditText) view.findViewById(R.id.etMobile);
        btnRegisterAccount = (Button) view.findViewById(R.id.btnRegisterAccount);
        spinnerTextView = (TextView) view.findViewById(R.id.spinner_textview);
        btnRegisterAccount.setOnClickListener(this);

        if (PreferenceUtil.getLanguage(Activity).equals("en")) {
            FontUtils.setHomeplusRegularFont(Activity, etName);
            FontUtils.setHomeplusRegularFont(Activity, etEmail);
            FontUtils.setHomeplusRegularFont(Activity, etPwd);
            FontUtils.setHomeplusRegularFont(Activity, etConfirmPwd);
            FontUtils.setHomeplusRegularFont(Activity, etGender);
            FontUtils.setHomeplusRegularFont(Activity, etAge);
            FontUtils.setHomeplusRegularFont(Activity, spinnerTextView);
        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, etName);
            FontUtils.setHomeplusArabicRegularFont(Activity, etEmail);
            FontUtils.setHomeplusArabicRegularFont(Activity, etPwd);
            FontUtils.setHomeplusArabicRegularFont(Activity, etConfirmPwd);
            FontUtils.setHomeplusArabicRegularFont(Activity, etGender);
            FontUtils.setHomeplusArabicRegularFont(Activity, etAge);
            FontUtils.setHomeplusArabicRegularFont(Activity, etMobile);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnRegisterAccount);
            FontUtils.setHomeplusArabicRegularFont(Activity, spinnerTextView);
        }
        // Initializing an ArrayAdapter
        spinnerArrayAdapter = new ArrayAdapter<String>(
                Activity, getSpinnerLayout(), getGenderArray()) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(getSpinnerId());

                tv.setTextColor(Color.BLACK);
                FontUtils.setHomeplusRegularFont(Activity, tv);

                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(getSpinnerLayout());
        etGender.setAdapter(spinnerArrayAdapter);
        etGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                    selectedItemText = getGenderArray()[position];
                    spinnerTextView.setVisibility(View.GONE);
                }

                Log.e(TAG, "fggdj,bv>>" + selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String[] getGenderArray() {
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            return Gender_array;
        } else {
            return Gender_array_ar;
        }

    }

    public int getSpinnerId() {
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            return R.id.tv_reg_gender;
        } else {
            return R.id.tv_reg_gender_arabic;
        }

    }

    public int getSpinnerLayout() {
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            return R.layout.spinner_item;
        } else {
            return R.layout.spinner_gender_arabic_item;
        }
    }

    private void sendRegistrationData() {
        if (Activity.isNetworkAvailable()) {
            if (nullCheck()) {
                if (etEmail.getText().toString().matches(AppConstants.EMAIL_PATTERN)) {
                    if (etPwd.getText().toString().length() >= 6 || etConfirmPwd.getText().toString().length() >= 6) {
                        if (etPwd.getText().toString().equals(etConfirmPwd.getText().toString())) {
                            if (etMobile.getText().toString().length() >= 10) {
                                createJsonRequestData();
                            } else {
                                etMobile.requestFocus();
                                etMobile.setError(getResources().getString(R.string.alert_invalid_mobile_no));
                            }
                        } else {
                            Toast.makeText(Activity, getString(R.string.alert_mismatched_password), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Activity, getString(R.string.alert_invalid_password), Toast.LENGTH_LONG).show();
                    }
                } else {
                    etEmail.requestFocus();
                    etEmail.setError(getResources().getString(R.string.alert_invalid_email));
                }
            } else {
                Toast.makeText(Activity, getString(R.string.alert_no_empty_field), Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }


    private boolean nullCheck() {
        if (etName.getText().toString().length() <= 0 ||
                etAge.getText().toString().length() <= 0 ||
                etEmail.getText().toString().length() <= 0 ||
                etMobile.getText().toString().length() <= 0 ||
                etPwd.getText().toString().length() <= 0 ||
                etConfirmPwd.getText().toString().length() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    private void createJsonRequestData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.NAME, etName.getText().toString());
            jsonObject.putOpt(AppConstants.EMAIL, etEmail.getText().toString());
            jsonObject.putOpt(AppConstants.PASSWORD, etPwd.getText().toString());
            jsonObject.putOpt(AppConstants.GENDER, selectedItemText);
            jsonObject.putOpt(AppConstants.AGE, etAge.getText().toString());
            jsonObject.putOpt(AppConstants.MOBILE_NO, etMobile.getText().toString());
            Log.e(TAG, "Json Request:" + jsonObject.toString());
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.REGISTRATION, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRegistrationDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onRegistrationDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseRegistrationJsonData(jsonObject);
    }

    private void parseRegistrationJsonData(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                    Activity.popFragments(this);
                    Activity.pushFragments(new LoginFragment(), false, true);
                } else {
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRegistrationDataLoadedFailed(JSONObject jsonObject) {
        super.onRegistrationDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegisterAccount:
                sendRegistrationData();
                break;

            default:
                break;
        }
    }
}