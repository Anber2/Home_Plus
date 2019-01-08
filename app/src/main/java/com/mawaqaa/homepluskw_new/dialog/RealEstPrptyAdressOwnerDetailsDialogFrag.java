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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.utility.FontUtils;

/**
 * Created by JijoCJ on 12/1/2016.
 */
public class RealEstPrptyAdressOwnerDetailsDialogFrag extends DialogFragment implements View.OnClickListener{
    private String TAG = "RealEstPrptOwnrDetailsFrag";
    private TextView txtOwnerNameValue, txtOwnerPhoneValue, txtOwnerEmailValue, txtAddressLine1,
            txtOwnerName,txtOwnerPhone,txtOwnerEmail;
    LinearLayout linearPropertyAddressLay;
    RelativeLayout relativeOwnerDetailsLay;
    private Button btnCloseInDialog;
    private Context context;
    public HomePlusBaseActivity Activity;

    String name, phoneNo, email, address;
    boolean isOwnerDetails;

    public static RealEstPrptyAdressOwnerDetailsDialogFrag newInstance(String name, String phoneNo , String email, String address, boolean isOwnerDetails){
        RealEstPrptyAdressOwnerDetailsDialogFrag ownerDetailsFragment = new RealEstPrptyAdressOwnerDetailsDialogFrag();
        ownerDetailsFragment.name = name;
        ownerDetailsFragment.phoneNo = phoneNo;
        ownerDetailsFragment.email = email;
        ownerDetailsFragment.address = address;
        ownerDetailsFragment.isOwnerDetails = isOwnerDetails;
        return ownerDetailsFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View view = View.inflate(getActivity(), R.layout.fragment_real_estate_inner_pop_up, null);

        Dialog dialog = new Dialog(getActivity(), R.style.DialogFragment);
        dialog.setContentView(view);

        txtOwnerNameValue= (TextView) view.findViewById(R.id.txtOwnerNameValue);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerNameValue);
        txtOwnerPhoneValue= (TextView) view.findViewById(R.id.txtOwnerPhoneValue);

        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerPhoneValue);
        txtOwnerEmailValue= (TextView) view.findViewById(R.id.txtOwnerEmailValuenew);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerEmailValue);
        txtAddressLine1= (TextView) view.findViewById(R.id.txtAddressLine1);
        FontUtils.setHomeplusRegularFont(getActivity(),txtAddressLine1);
        txtOwnerName =(TextView) view.findViewById(R.id.txtOwnerName);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerName);
        txtOwnerPhone=(TextView) view.findViewById(R.id.txtOwnerPhone);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerPhone);
        txtOwnerEmail=(TextView) view.findViewById(R.id.txtOwnerEmail);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerEmail);
        linearPropertyAddressLay = (LinearLayout) view.findViewById(R.id.linearPropertyAddressLay);
        relativeOwnerDetailsLay = (RelativeLayout) view.findViewById(R.id.relativeOwnerDetailsLay);

        if(isOwnerDetails) {
            relativeOwnerDetailsLay.setVisibility(View.VISIBLE);
            linearPropertyAddressLay.setVisibility(View.GONE);
            txtOwnerNameValue.setText(name);
            //txtOwnerPhoneValue.setText(phoneNo);
          /*  Spannable wordtoSpan = new SpannableString(phoneNo);

            wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 1,name.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            txtOwnerPhoneValue.setText(wordtoSpan);*/

            SpannableString content = new SpannableString(phoneNo);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            txtOwnerPhoneValue.setText(content);

            txtOwnerEmailValue.setText(email);
            txtOwnerEmailValue.setLinkTextColor(Color.BLUE);
          /*  SpannableString s = new SpannableString(email);
            Linkify.addLinks(s, Linkify.ALL);
            txtOwnerEmailValue.setMovementMethod(LinkMovementMethod.getInstance());*/

            Log.e("fjvjvkj","b"+email);
            // Linkify.addLinks(txtOwnerPhoneValue, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
            //txtOwnerPhoneValue.setAutoLinkMask(Linkify.PHONE_NUMBERS);
            Linkify.addLinks(txtOwnerEmailValue, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
        }
        else {
            linearPropertyAddressLay.setVisibility(View.VISIBLE);
            relativeOwnerDetailsLay.setVisibility(View.GONE);
            txtAddressLine1.setText(address);
        }

        txtOwnerNameValue.setSelected(true);
         txtOwnerPhoneValue.setSelected(true);
        //txtOwnerEmailValue.setSelected(true);

        btnCloseInDialog= (Button) view.findViewById(R.id.btnCloseInDialog);
        btnCloseInDialog.setOnClickListener(this);

       /* TextView myTextView = Textoo
                .config((TextView) view.findViewById(R.id.txtOwnerPhoneValue))
                .linkifyAll().apply();*/

       // myTextView.setText(phoneNo);
       txtOwnerPhoneValue.setOnClickListener(this);
        //myTextView.setOnClickListener(this);
        //txtPopUpAddress.setMovementMethod(new ScrollingMovementMethod());
        /*txtOwnerNameValue.setText(name);
        txtOwnerPhoneValue.setText(phoneNo);
        txtOwnerEmailValue.setText(email);*/

        onViewCreated(dialog.findViewById(R.id.dialog_pop_up), savedInstanceState);
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("Dialog", ""+name);
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

       /* txtOwnerNameValue= (TextView) view.findViewById(R.id.txtOwnerNameValue);
       FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerNameValue);
        txtOwnerPhoneValue= (TextView) view.findViewById(R.id.txtOwnerPhoneValue);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerPhoneValue);
        txtOwnerEmailValue= (TextView) view.findViewById(R.id.txtOwnerEmailValuenew);
       FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerEmailValue);
        txtAddressLine1= (TextView) view.findViewById(R.id.txtAddressLine1);
       FontUtils.setHomeplusRegularFont(getActivity(),txtAddressLine1);
        txtOwnerName =(TextView) view.findViewById(R.id.txtOwnerName);
       FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerName);
        txtOwnerPhone=(TextView) view.findViewById(R.id.txtOwnerPhone);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerPhone);
        txtOwnerEmail=(TextView) view.findViewById(R.id.txtOwnerEmail);
       FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerEmail);
        linearPropertyAddressLay = (LinearLayout) view.findViewById(R.id.linearPropertyAddressLay);
        relativeOwnerDetailsLay = (RelativeLayout) view.findViewById(R.id.relativeOwnerDetailsLay);

        if(isOwnerDetails) {
            relativeOwnerDetailsLay.setVisibility(View.VISIBLE);
            linearPropertyAddressLay.setVisibility(View.GONE);
            txtOwnerNameValue.setText(name);
            txtOwnerPhoneValue.setText(phoneNo);
            txtOwnerEmailValue.setText(email);
            SpannableString s = new SpannableString(email);
            Linkify.addLinks(s, Linkify.ALL);
            txtOwnerEmailValue.setMovementMethod(LinkMovementMethod.getInstance());

            Log.e("fjvjvkj","b"+email);
          // Linkify.addLinks(txtOwnerPhoneValue, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
           //Linkify.addLinks(txtOwnerEmailValue, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
        }
        else {
            linearPropertyAddressLay.setVisibility(View.VISIBLE);
            relativeOwnerDetailsLay.setVisibility(View.GONE);
            txtAddressLine1.setText(address);
        }

        txtOwnerNameValue.setSelected(true);
       // txtOwnerPhoneValue.setSelected(true);
        //txtOwnerEmailValue.setSelected(true);

        btnCloseInDialog= (Button) view.findViewById(R.id.btnCloseInDialog);
        btnCloseInDialog.setOnClickListener(this);
        txtOwnerPhoneValue.setOnClickListener(this);
        //txtPopUpAddress.setMovementMethod(new ScrollingMovementMethod());
        *//*txtOwnerNameValue.setText(name);
        txtOwnerPhoneValue.setText(phoneNo);
        txtOwnerEmailValue.setText(email);*//*
    }*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCloseInDialog:
                getDialog().dismiss();
                break;
            case R.id.txtOwnerPhoneValue:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", phoneNo, null));
                startActivity(phoneIntent);
                break;
            default:
                break;
        }
    }
}

