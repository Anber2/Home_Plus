package com.mawaqaa.homepluskw_new.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by JijoCJ on 12/1/2016.
 */
public class CompanyDetailsDialogFragment extends DialogFragment implements View.OnClickListener {

    private String TAG = "CompanyDetailsDialogFragment";
    private TextView txtCompanyNameValue, txtCompanyPhoneValue, txtCompanyEmailValue, txtCompanyWebsiteValue, txtCompanyName, txtCompanyPhone,
            txtCompanyEmail, txtCompanyWebsite, txtCompanyPhoneValuetwo;
    private Button btnCloseInDialog;
    private Context context;
    private ListView listViewPhone;
    public HomePlusBaseActivity Activity;
    List<String> phoneNumberList;
    String phone2;
    String companyName, companyPhoneNo, companyEmail, companyWebsite;

    public static CompanyDetailsDialogFragment newInstance(String companyName, String companyPhoneNo, String companyEmail, String companyWebsite) {
        CompanyDetailsDialogFragment companyDetailsFragment = new CompanyDetailsDialogFragment();
        companyDetailsFragment.companyName = companyName;
        companyDetailsFragment.companyPhoneNo = companyPhoneNo;
        companyDetailsFragment.companyEmail = companyEmail;
        companyDetailsFragment.companyWebsite = companyWebsite;
        return companyDetailsFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View view = View.inflate(getActivity(), R.layout.fragment_company_details_pop_up, null);

        Dialog dialog = new Dialog(getActivity(), R.style.DialogFragment);
        dialog.setContentView(view);
        onViewCreated(dialog.findViewById(R.id.dialog_id), savedInstanceState);
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        //Log.e("Dialog", ""+workingHrs);
        /*Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.7f; // dim only a little bit
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(params);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 500);
        window.setGravity(Gravity.CENTER);*/
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 600);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // add view code
        //getDialog().setCanceledOnTouchOutside(true);
        //FontUtils.setEbrimaFont(Activity, relativelayoutchangepaswd);
        txtCompanyName = (TextView) view.findViewById(R.id.txtCompanyName);

        txtCompanyPhone = (TextView) view.findViewById(R.id.txtCompanyPhone);

        txtCompanyEmail = (TextView) view.findViewById(R.id.txtCompanyEmail);

        txtCompanyWebsite = (TextView) view.findViewById(R.id.txtCompanyWebsite);

        txtCompanyPhoneValuetwo = (TextView) view.findViewById(R.id.txtCompanyPhoneValueTwo);

        txtCompanyNameValue = (TextView) view.findViewById(R.id.txtCompanyNameValue);

        txtCompanyPhoneValue = (TextView) view.findViewById(R.id.txtCompanyPhoneValue);

        txtCompanyEmailValue = (TextView) view.findViewById(R.id.txtCompanyEmailValue);

        txtCompanyWebsiteValue = (TextView) view.findViewById(R.id.txtCompanyWebsiteValue);
        txtCompanyWebsiteValue.setLinkTextColor(Color.BLUE);
        txtCompanyWebsiteValue.setSelected(true);

       /* if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity,txtCompanyName);
            FontUtils.setHomeplusRegularFont(Activity,txtCompanyPhone);
             FontUtils.setHomeplusRegularFont(Activity,txtCompanyEmail);
             FontUtils.setHomeplusRegularFont(Activity,txtCompanyWebsite);
             FontUtils.setHomeplusRegularFont(Activity,txtCompanyNameValue);
              FontUtils.setHomeplusRegularFont(Activity,txtCompanyPhoneValue);
             FontUtils.setHomeplusRegularFont(Activity,txtCompanyEmailValue);
            FontUtils.setHomeplusRegularFont(Activity,txtCompanyWebsiteValue);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity,txtCompanyName);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtCompanyPhone);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtCompanyEmail);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtCompanyWebsite);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtCompanyNameValue);
            FontUtils.setHomeplusRegularFont(Activity,txtCompanyPhoneValue);
            FontUtils.setHomeplusRegularFont(Activity,txtCompanyEmailValue);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtCompanyWebsiteValue);
        }*/
        txtCompanyNameValue.setSelected(true);
        txtCompanyPhoneValue.setSelected(true);
        txtCompanyEmailValue.setSelected(true);
        txtCompanyWebsiteValue.setSelected(true);

        btnCloseInDialog = (Button) view.findViewById(R.id.btnCloseInDialog);
        btnCloseInDialog.setOnClickListener(this);
        //txtPopUpAddress.setMovementMethod(new ScrollingMovementMethod());
        txtCompanyNameValue.setText(companyName);

        txtCompanyEmailValue.setText(companyEmail);
        txtCompanyWebsiteValue.setText(companyWebsite);
        phoneNumberList = Arrays.asList(companyPhoneNo.split(","));
        Log.e("Phone Number", "" + phoneNumberList.size() + "");

        String string = phoneNumberList.get(0);
        SpannableString content = new SpannableString(string);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        txtCompanyPhoneValue.setText(content);
        txtCompanyPhoneValue.setOnClickListener(this);

        for (int i = 1; i < phoneNumberList.size(); i++) {
            txtCompanyPhoneValuetwo.setVisibility(View.VISIBLE);
             phone2 = phoneNumberList.get(i);
            SpannableString content2 = new SpannableString(phone2);
            content2.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            txtCompanyPhoneValuetwo.setText(content2);
            txtCompanyPhoneValuetwo.setLinkTextColor(Color.BLUE);
            txtCompanyPhoneValuetwo.setOnClickListener(this);
        }


        // txtCompanyPhoneValue.setText(companyPhoneNo);

        //txtCompanyWebsiteValue.setText(companyWebsite);
        //txtCompanyPhoneValue.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        //Linkify.addLinks(txtCompanyPhoneValue, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(txtCompanyEmailValue, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
        txtCompanyEmailValue.setLinkTextColor(Color.BLUE);
    }

    private class PhoneAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return phoneNumberList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCloseInDialog:
                getDialog().dismiss();
                break;
            case R.id.txtCompanyPhoneValue:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", companyPhoneNo, null));
                startActivity(phoneIntent);
                break;
            case R.id.txtCompanyPhoneValueTwo:
                Intent phoneIntent2=new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",phone2,null));
                startActivity(phoneIntent2);
                break;
            default:
                break;
        }
    }
}



