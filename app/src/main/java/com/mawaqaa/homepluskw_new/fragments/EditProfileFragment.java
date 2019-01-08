package com.mawaqaa.homepluskw_new.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by aswathy on 1/3/2017.
 */
public class EditProfileFragment extends HomePlusBaseFragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    public static final String TAG="EditProfileFragment";
    Button btnSubmit, btnReset,btn_changepassword;
    EditText edtFname,edtLastname,edtEmai,edtAge,edtMobileno;
    RadioGroup radiogroupGender;
    RadioButton radioButton_male, radioButton_female;
    TextView txt_edit_profile,txt_edit_your_profile,txt_first_name,txt_email,hint_txt_gender,hint_txt_age,
            hint_txt_mobile;
    String Gender;
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
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_my_account_title_action_bar));
        initView(view);
       /* BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.propertyServiceButtonState();*/
        return view;

    }

    private void initView(View view) {
        btnSubmit=(Button)view.findViewById(R.id.btn_edit_submit);
        btnReset=(Button)view.findViewById(R.id.btn_edit_reset);
        edtFname=(EditText)view.findViewById(R.id.editTextfname);
        edtLastname=(EditText)view.findViewById(R.id.editlastname);
        edtEmai=(EditText)view.findViewById(R.id.editTextEmail);
        edtAge=(EditText)view.findViewById(R.id.editAge);
        edtMobileno=(EditText)view.findViewById(R.id.editmobileno);
        radioButton_female=(RadioButton)view.findViewById(R.id.radioFemale);
        radioButton_male=(RadioButton)view.findViewById(R.id.radioMale);
        radiogroupGender=(RadioGroup)view.findViewById(R.id.radioSex);
        //txt_edit_profile,txt_edit_your_profile,txt_first_name,txt_email,hint_txt_gender,hint_txt_age,
        hint_txt_mobile=(TextView)view.findViewById(R.id.textmbno);
        txt_edit_profile=(TextView)view.findViewById(R.id.txt_editsuperhead);
        txt_edit_your_profile=(TextView)view.findViewById(R.id.txt_edit_subhead);
        txt_first_name=(TextView)view.findViewById(R.id.txtfirstname);
        txt_email=(TextView)view.findViewById(R.id.textedit_email);
        hint_txt_gender=(TextView)view.findViewById(R.id.textgender);
        hint_txt_age=(TextView)view.findViewById(R.id.txtAge);
        btn_changepassword=(Button)view.findViewById(R.id.btn_changepassword);
        btn_changepassword.setOnClickListener(this);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(Activity,edtFname);
            FontUtils.setHomeplusRegularFont(Activity,edtLastname);
            FontUtils.setHomeplusRegularFont(Activity,edtEmai);
            FontUtils.setHomeplusRegularFont(Activity,edtAge);
            FontUtils.setHomeplusRegularFont(Activity,edtMobileno);
            FontUtils.setHomeplusRegularFont(Activity,radioButton_female);
            FontUtils.setHomeplusRegularFont(Activity,radioButton_male);
            FontUtils.setHomeplusRegularFont(Activity,hint_txt_mobile);
            FontUtils.setHomeplusRegularFont(Activity,txt_first_name);
            FontUtils.setHomeplusRegularFont(Activity,txt_email);
            FontUtils.setHomeplusRegularFont(Activity,hint_txt_gender);
            FontUtils.setHomeplusRegularFont(Activity,hint_txt_age);
            FontUtils.setHomeplusRegularFont(Activity,btnSubmit);
            FontUtils.setHomeplusRegularFont(Activity,btnReset);
            FontUtils.setHomeplusRegularFont(Activity,btn_changepassword);

        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity,edtFname);
            FontUtils.setHomeplusArabicRegularFont(Activity,edtLastname);
            FontUtils.setHomeplusArabicRegularFont(Activity,edtEmai);
            FontUtils.setHomeplusArabicRegularFont(Activity,edtAge);
            FontUtils.setHomeplusArabicRegularFont(Activity,edtMobileno);
            FontUtils.setHomeplusArabicRegularFont(Activity,radioButton_female);
            FontUtils.setHomeplusArabicRegularFont(Activity,radioButton_male);
            FontUtils.setHomeplusArabicRegularFont(Activity,hint_txt_mobile);
            FontUtils.setHomeplusArabicRegularFont(Activity,txt_first_name);
            FontUtils.setHomeplusArabicRegularFont(Activity,txt_email);
            FontUtils.setHomeplusArabicRegularFont(Activity,hint_txt_gender);
            FontUtils.setHomeplusArabicRegularFont(Activity,hint_txt_age);
            FontUtils.setHomeplusArabicRegularFont(Activity,btnSubmit);
            FontUtils.setHomeplusArabicRegularFont(Activity,btnReset);
            FontUtils.setHomeplusArabicRegularFont(Activity,btn_changepassword);

        }
        fetchRequestFormData();
        radiogroupGender.setOnCheckedChangeListener(this);
        btnSubmit.setOnClickListener(this);
        btnReset.setOnClickListener(this);


    }

    private void fetchRequestFormData() {

        try{
            if(Activity.isNetworkAvailable()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.VIEWPROFILE_ID, PreferenceUtil.getUserId(Activity));
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                Log.e(TAG, "Json Req:"+jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_VIEWPROFILE_REQUEST_DATA, jsonObject);
            }else{
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewProfileRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onViewProfileRequestDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseServiceRequestLoadData(jsonObject);
    }

    private void parseServiceRequestLoadData(JSONObject jsonObject) {
        if(jsonObject!=null) {
            Log.d("json::",""+jsonObject);
            try {

                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS))
                {
                    edtFname.setText(jsonObject.getString(AppConstants.NAME));
                    //edtLastname.setText(jsonObject.getString(AppConstants.NAME));
                    edtEmai.setText(jsonObject.getString(AppConstants.EMAIL));
                    edtAge.setText(jsonObject.getString(AppConstants.AGE));
                    edtMobileno.setText(jsonObject.getString(AppConstants.MOBILE_NO));
                    Gender=jsonObject.getString(AppConstants.GENDER);
                    if (Gender.equals("Male"))
                    {
                    radioButton_male.setChecked(true);
                    }
                    else
                        radioButton_female.setChecked(true);
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
    public void onViewProfileRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onViewProfileRequestDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_edit_submit:
                if(nullCheck())
                {
                    if (edtEmai.getText().toString().trim()
                            .matches(AppConstants.EMAIL_PATTERN)) {

                        if (edtMobileno.getText().toString().trim().length() >= 8) {

                            if (Activity.isNetworkAvailable())
                                createJsontoPost();
                            else
                                Toast.makeText(getActivity(),
                                        R.string.alert_no_internet,
                                        Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), R.string.invalid_phone,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), R.string.invalid_email,
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), R.string.alert_no_empty_field,
                            Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_edit_reset:
                 ResetValues();
                break;
            case R.id.btn_changepassword:
                setDialog();
                break;
            default:
                break;
        }

    }

    private void setDialog() {
        final Dialog dlgForgotPassword = new Dialog(getActivity());
        dlgForgotPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgForgotPassword
                .setContentView(R.layout.change_password_dialog);
        TextView title1=(TextView) dlgForgotPassword.findViewById(R.id.txtchangepassword);
        final EditText old_password=(EditText)dlgForgotPassword.findViewById(R.id.txt_oldpassword);
        final EditText new_password=(EditText)dlgForgotPassword.findViewById(R.id.txt_newpassword);
        final EditText confirm_password=(EditText)dlgForgotPassword.findViewById(R.id.txt_confirm_newpassword);
        Button close=(Button) dlgForgotPassword.findViewById(R.id.btnCloseInDialog) ;
        Button submit=(Button) dlgForgotPassword.findViewById(R.id.btn_submit_changes) ;
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(Activity,title1);
            FontUtils.setHomeplusRegularFont(Activity,old_password);
            FontUtils.setHomeplusRegularFont(Activity,new_password);
            FontUtils.setHomeplusRegularFont(Activity,confirm_password);
            FontUtils.setHomeplusRegularFont(Activity,close);
            FontUtils.setHomeplusRegularFont(Activity,submit);


        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity,title1);
            FontUtils.setHomeplusArabicRegularFont(Activity,old_password);
            FontUtils.setHomeplusArabicRegularFont(Activity,new_password);
            FontUtils.setHomeplusArabicRegularFont(Activity,confirm_password);
            FontUtils.setHomeplusArabicRegularFont(Activity,close);
            FontUtils.setHomeplusArabicRegularFont(Activity,submit);

        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlgForgotPassword.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword=old_password.getText().toString().trim();
                String newPassword=new_password.getText().toString().trim();
                String conFirmPassword=confirm_password.getText().toString().trim();
                if(newPassword.length() >=6 || conFirmPassword.length() >=6){
                    if(newPassword.equals(conFirmPassword)){

                            if (Activity.isNetworkAvailable()) {
                                ChangepasswordService(oldPassword,newPassword);
                            }
                            else {
                                Toast.makeText(getActivity(),
                                        R.string.alert_no_internet,
                                        Toast.LENGTH_SHORT).show();
                                }
                        }
                    else
                        {
                            Toast.makeText(Activity, getString(R.string.alert_mismatched_password), Toast.LENGTH_LONG).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Activity, getString(R.string.alert_invalid_password), Toast.LENGTH_LONG).show();
                    }



               // Activity.pushFragments(LoginFragment.newInstance(true), false, true);
                dlgForgotPassword.dismiss();
            }
        });
        dlgForgotPassword.show();
    }

    private void ChangepasswordService(String oldPassword, String newPassword) {
        try {
                Log.e("changepwd",">>>"+oldPassword+">>>>"+newPassword);
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.HOME_PLUS_NEWPASSWORD,newPassword);
            jsonObject.putOpt(AppConstants.HOME_PLUS_OLDPASSWORD,oldPassword);
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.USER_ID, PreferenceUtil.getUserId(Activity));


//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                Log.d("Json:",""+jsonObject.toString());
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_CHANGEPASSWORD,
                        jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception in Creating JSON");
        }

    }

    private void ResetValues() {
        edtFname.setText("");
        edtEmai.setText("");
        edtAge.setText("");
        edtMobileno.setText("");
        radioButton_male.setChecked(false);
        radioButton_female.setChecked(false);

    }

    private void createJsontoPost() {
        try {

            JSONObject jsonObject = new JSONObject();


            jsonObject.putOpt(AppConstants.AGE, edtAge.getText()
                    .toString().trim());
            jsonObject.putOpt(AppConstants.EMAIL, edtEmai.getText().toString()
                    .trim());

            jsonObject.putOpt(AppConstants.FIRSTNAME, edtFname.getText()
                    .toString().trim());

            jsonObject.putOpt(AppConstants.LASTNAME,"");
            jsonObject.putOpt(AppConstants.GENDER, Gender);

            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));

            jsonObject.putOpt(AppConstants.MOBILE_NO,edtMobileno.getText().toString().trim());
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.USER_ID, PreferenceUtil.getUserId(Activity));


//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                Log.d("Json:",""+jsonObject.toString());
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_EDIT_PROFILE,
                        jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception in Creating JSON");
        }


    }

    @Override
    public void onEditProfileRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onEditProfileRequestDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        try{
            if(jsonObject.getBoolean(AppConstants.IS_SUCCESS))
               // Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
               Activity.popFragments(this);

            else
                Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onEditProfileRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onEditProfileRequestDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    private boolean nullCheck() {
        if (edtFname.getText().toString().trim().length() <= 0

                || edtEmai.getText().toString().trim().length() <= 0
                || edtAge.getText().toString().trim().length() <= 0
                || edtMobileno.getText().toString().trim().length() <= 0) {
            Log.e(TAG, "Null Fields");
            return false;
        } else {
            Log.e(TAG, "No Null Fields");
            return true;
        }

    }

    @Override
    public void onChangePasswordLoadedSuccessfully(JSONObject jsonObject) {
        super.onChangePasswordLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        if(jsonObject!=null)
        {
            Log.e(TAG, "Json Response:"+jsonObject);
            try{
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS))
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                   // Activity.popFragments(this);

                else
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onChangePasswordLoadedFailed(JSONObject jsonObject) {
        super.onChangePasswordLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        RadioButton checkedRadioButton=(RadioButton)radiogroupGender.findViewById(id);
        boolean isChecked = checkedRadioButton.isChecked();
        if (isChecked)
        {
            //Toast.makeText(getActivity(),
                    //checkedRadioButton.getText(),Toast.LENGTH_LONG).show();
            Gender=checkedRadioButton.getText().toString();
        }

    }
}
