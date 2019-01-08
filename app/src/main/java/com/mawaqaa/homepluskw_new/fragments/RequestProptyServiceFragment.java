package com.mawaqaa.homepluskw_new.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JijoCJ on 11/24/2016.
 */
public class RequestProptyServiceFragment extends HomePlusBaseFragment implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private static final String TAG = "ReqProptyServiceFrag";
    Button btnSubmit, btnReset;
    Spinner spinnerSelectRegion, spinnerSelectPurpose, spinnerSelectPropertyType;
    TextView head_request_service,txt_SelectionRegion,txt_Selectpurpose,txt_selectPropertytype,txt_fillForm;
    List<String> propertyTypeList;
    List<String> purposeList;
    List<String> regionList;
    String region="",purpose="",propertyType="";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_property_service, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_my_account_title_action_bar));
        initView(view);
       /* BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.propertyServiceButtonState();*/
        return view;
    }

    private void initView(View view) {

        spinnerSelectRegion = (Spinner) view.findViewById(R.id.spinnerSelectRegion);

        spinnerSelectPurpose = (Spinner) view.findViewById(R.id.spinnerSelectPurpose);

        spinnerSelectPropertyType = (Spinner) view.findViewById(R.id.spinnerSelectPropertyType);

        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        btnReset = (Button) view.findViewById(R.id.btnReset);

        head_request_service=(TextView)view.findViewById(R.id.head_request_property);

        txt_SelectionRegion =(TextView)view.findViewById(R.id.txtSelectRegion);

        txt_Selectpurpose=(TextView)view.findViewById(R.id.txtSelectPurpose);

        txt_selectPropertytype=(TextView)view.findViewById(R.id.txtSelectPropertyType);

        txt_fillForm=(TextView)view.findViewById(R.id.txt_filltheform);

        btnSubmit.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(Activity,spinnerSelectRegion);
            FontUtils.setHomeplusRegularFont(Activity,spinnerSelectPurpose);
            FontUtils.setHomeplusRegularFont(Activity,spinnerSelectPropertyType);
            FontUtils.setHomeplusRegularFont(Activity,btnSubmit);
            FontUtils.setHomeplusRegularFont(Activity,btnReset);
            FontUtils.setHomeplusRegularFont(Activity,head_request_service);
            FontUtils.setHomeplusRegularFont(Activity,txt_SelectionRegion);
            FontUtils.setHomeplusRegularFont(Activity,txt_Selectpurpose);
            FontUtils.setHomeplusRegularFont(Activity,txt_selectPropertytype);
            FontUtils.setHomeplusRegularFont(Activity,txt_fillForm);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity,spinnerSelectRegion);
            FontUtils.setHomeplusArabicRegularFont(Activity,spinnerSelectPurpose);
            FontUtils.setHomeplusArabicRegularFont(Activity,spinnerSelectPropertyType);
            FontUtils.setHomeplusArabicRegularFont(Activity,btnSubmit);
            FontUtils.setHomeplusArabicRegularFont(Activity,btnReset);
            FontUtils.setHomeplusArabicRegularFont(Activity,head_request_service);
            FontUtils.setHomeplusArabicRegularFont(Activity,txt_SelectionRegion);
            FontUtils.setHomeplusArabicRegularFont(Activity,txt_Selectpurpose);
            FontUtils.setHomeplusArabicRegularFont(Activity,txt_selectPropertytype);
            FontUtils.setHomeplusArabicRegularFont(Activity,txt_fillForm);
        }
        spinnerSelectRegion.setOnItemSelectedListener(this);
        spinnerSelectPurpose.setOnItemSelectedListener(this);
        spinnerSelectPropertyType.setOnItemSelectedListener(this);
        fetchRequestFormData();
    }

    private void fetchRequestFormData() {
        try{
            if(Activity.isNetworkAvailable()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.USER_ID,PreferenceUtil.getUserId(Activity));
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                Log.e(TAG, "Json Req:"+jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_PROPERTY_SERVICE_REQUEST_DATA, jsonObject);
            }else{
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPopertyServiceDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onRequestPopertyServiceDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseServiceRequestLoadData(jsonObject);
    }

    private void parseServiceRequestLoadData(JSONObject jsonObject) {
        try{
            if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)){

                JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.PROPERTY_TYPE_ARR);
                if(jsonArray.length() > 0){
                    propertyTypeList =  new ArrayList<>();
                    propertyTypeList.add(getString(R.string.spinner_title_txt_select));
                    for(int i = 0; i < jsonArray.length(); i++)
                        propertyTypeList.add(jsonArray.getString(i));

                    ArrayAdapter<String> mSpinnerPropertytypeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_request_property, R.id.txtSpinnerCountryItem, propertyTypeList);
                    spinnerSelectPropertyType.setAdapter(mSpinnerPropertytypeAdapter);
                }else{
                    propertyTypeList.add(getString(R.string.spinner_title_txt_select));
                    ArrayAdapter<String> mSpinnerPropertytypeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_request_property, R.id.txtSpinnerCountryItem, propertyTypeList);
                    spinnerSelectPropertyType.setAdapter(mSpinnerPropertytypeAdapter);
                }

                JSONArray jsonArrayPurpose = jsonObject.getJSONArray(AppConstants.PURPOSE_ARR);
                if(jsonArrayPurpose.length() > 0){
                    purposeList = new ArrayList<>();
                    purposeList.add(getString(R.string.spinner_title_txt_select));
                    for(int i = 0; i < jsonArrayPurpose.length(); i++)
                        purposeList.add(jsonArrayPurpose.getString(i));
                    ArrayAdapter<String> mSpinnerPurposeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_request_property, R.id.txtSpinnerCountryItem, purposeList);
                    spinnerSelectPurpose.setAdapter(mSpinnerPurposeAdapter);
                }else{
                    purposeList.add(getString(R.string.spinner_title_txt_select));
                    ArrayAdapter<String> mSpinnerPurposeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_request_property, R.id.txtSpinnerCountryItem, purposeList);
                    spinnerSelectPurpose.setAdapter(mSpinnerPurposeAdapter);
                }

                JSONArray jsonArrayRegion = jsonObject.getJSONArray(AppConstants.REGION_ARR);
                if(jsonArrayRegion.length() > 0){
                    regionList = new ArrayList<>();
                    regionList.add(getString(R.string.spinner_title_txt_select));
                    for(int i = 0; i < jsonArrayRegion.length(); i++)
                        regionList.add(jsonArrayRegion.getString(i));
                    ArrayAdapter<String> mSpinnerPurposeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_request_property, R.id.txtSpinnerCountryItem, regionList);
                    spinnerSelectRegion.setAdapter(mSpinnerPurposeAdapter);
                }else{
                    regionList.add(getString(R.string.spinner_title_txt_select));
                    ArrayAdapter<String> mSpinnerPurposeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_request_property, R.id.txtSpinnerCountryItem, regionList);
                    spinnerSelectRegion.setAdapter(mSpinnerPurposeAdapter);
                }

            }else{
                Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPopertyServiceDataLoadedFailed(JSONObject jsonObject) {
        super.onRequestPopertyServiceDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                submitData();
                break;
            case R.id.btnReset:
                fetchRequestFormData();
                break;
            default:
                break;
        }
    }

    private void submitData() {
        if(nullCheck()){
            getSpinnerVAlues();
            createJsonToSubmitData(region,purpose,propertyType);
        }else{
            Toast.makeText(Activity, getString(R.string.alert_no_null_in_spinner), Toast.LENGTH_LONG).show();
        }
    }

    private void createJsonToSubmitData(String region,String purpose,String propertyType) {
        Log.e("spinner",">>>>>"+region+""+">>>>"+purpose+">>>>>"+propertyType);
        SharedPreferences pref = Activity.getSharedPreferences(AppConstants.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        try {
                if(Activity.isNetworkAvailable()){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.USER_ID, PreferenceUtil.getUserId(Activity));
                    jsonObject.putOpt(AppConstants.MUREQUESTEMAIL, PreferenceUtil.getUserEmail(Activity));
                    jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                    jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                    jsonObject.putOpt(AppConstants.PURPOSE,purpose );
                    jsonObject.putOpt(AppConstants.REGION,region);
                    jsonObject.putOpt(AppConstants.TYPE,propertyType );
                    jsonObject.putOpt(AppConstants.DEVICE_TOKEN, regId);
                    jsonObject.putOpt(AppConstants.DEVICE_PLATFORM, 0);
                    Log.e(TAG, "Json Req:"+jsonObject);
//                    startSpinwheel(false, true);
                    BaseApplication.getInstance().progressON(getActivity(), null);

                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_PROPERTY_SERVICE_SEND_REQUEST, jsonObject);
                }else
                    Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean nullCheck() {
        if(spinnerSelectPropertyType.getSelectedItem().equals(getString(R.string.spinner_title_txt_select)) &&
                spinnerSelectPurpose.getSelectedItem().equals(getString(R.string.spinner_title_txt_select)) &&
                spinnerSelectRegion.getSelectedItem().equals(getString(R.string.spinner_title_txt_select))){
            return false;
        }else
            return  true;
    }

    @Override
    public void onRequestPopertyServiceSendDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onRequestPopertyServiceSendDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        try{
            if(jsonObject.getBoolean(AppConstants.IS_SUCCESS))
                Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPopertyServiceSendDataLoadedFailed(JSONObject jsonObject) {
        super.onRequestPopertyServiceSendDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getSpinnerVAlues();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    void getSpinnerVAlues() {
        region = spinnerSelectRegion.getSelectedItem().toString();
        if (region.equals(getString(R.string.spinner_title_txt_select))) {
            region = "";
        }
        purpose=spinnerSelectPurpose.getSelectedItem().toString();
        if (purpose.equals(getString(R.string.spinner_title_txt_select))) {
            purpose = "";
        }

        propertyType=spinnerSelectPropertyType.getSelectedItem().toString();
        if (propertyType.equals(getString(R.string.spinner_title_txt_select))) {
            propertyType = "";
        }
    }
}
