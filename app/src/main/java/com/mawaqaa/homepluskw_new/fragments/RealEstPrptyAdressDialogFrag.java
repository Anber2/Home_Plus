package com.mawaqaa.homepluskw_new.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;

/**
 * Created by JijoCJ on 3/3/2017.
 */

public class RealEstPrptyAdressDialogFrag extends DialogFragment implements View.OnClickListener {
    String property_block,property_steet,property_address,property_region,property_house;
    boolean isOwnerDetails;
    Button btnCloseInDialog;
    TextView property_block_txt,property_street_txt,property_address_txt,txtpropertyregionValuenw,txtpropertyhousevalue;

    public static RealEstPrptyAdressDialogFrag newInstance(String region,String property_block, String property_steet, String property_address,String property_house, boolean isOwnerDetails){
        RealEstPrptyAdressDialogFrag ownerDetailsFragment = new RealEstPrptyAdressDialogFrag();
        ownerDetailsFragment.property_block = property_block;
        ownerDetailsFragment.property_steet = property_steet;
        ownerDetailsFragment.property_address = property_address;
        ownerDetailsFragment.property_region = region;
        ownerDetailsFragment.property_house = property_house;
        ownerDetailsFragment.isOwnerDetails = isOwnerDetails;
        return ownerDetailsFragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View view = View.inflate(getActivity(), R.layout.dialog_proprtyaddress, null);

        Dialog dialog = new Dialog(getActivity(), R.style.DialogFragment);
        dialog.setContentView(view);
        onViewCreated(dialog.findViewById(R.id.dialog_prop), savedInstanceState);
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();

        /*Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.7f; // dim only a little bit
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(params);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 500);
        window.setGravity(Gravity.CENTER);*/
        //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onViewCreated(View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);
        property_block_txt=(TextView) view1.findViewById(R.id.txtblockvalue);
        property_street_txt=(TextView) view1.findViewById(R.id.txtpropertystreetValue);
        property_address_txt=(TextView) view1.findViewById(R.id.txtpropertyaddressValue);
        txtpropertyregionValuenw=(TextView)view1.findViewById(R.id.txtpropertyregionValuenw);
        txtpropertyhousevalue=(TextView)view1.findViewById(R.id.txtpropertyhousevalue);
        btnCloseInDialog=(Button) view1.findViewById(R.id.btnCloseInDialog);

        property_block_txt.setText(property_block);
        property_street_txt.setText(property_steet);
        property_address_txt.setText(property_address);
        txtpropertyregionValuenw.setText(property_region);
        txtpropertyhousevalue.setText(property_house);
        //btnCloseInDialog= (Button) view1.findViewById(R.id.btnCloseInDialog);
        btnCloseInDialog.setOnClickListener(this);

       /* txtOwnerNameValue= (TextView) view.findViewById(R.id.txtOwnerNameValue);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerNameValue);
        txtOwnerPhoneValue= (TextView) view.findViewById(R.id.txtOwnerPhoneValue);
        FontUtils.setHomeplusRegularFont(getActivity(),txtOwnerPhoneValue);
        txtOwnerEmailValue= (TextView) view.findViewById(R.id.txtOwnerEmailValue);
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

            Linkify.addLinks(txtOwnerPhoneValue, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
            Linkify.addLinks(txtOwnerEmailValue, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
        }
        else {
            linearPropertyAddressLay.setVisibility(View.VISIBLE);
            relativeOwnerDetailsLay.setVisibility(View.GONE);

            txtAddressLine1.setText(address);
        }

        txtOwnerNameValue.setSelected(true);
        txtOwnerPhoneValue.setSelected(true);
        txtOwnerEmailValue.setSelected(true);

        btnCloseInDialog= (Button) view.findViewById(R.id.btnCloseInDialog);
        btnCloseInDialog.setOnClickListener(this);
        txtOwnerPhoneValue.setOnClickListener(this);*/
        //txtPopUpAddress.setMovementMethod(new ScrollingMovementMethod());
        /*txtOwnerNameValue.setText(name);
        txtOwnerPhoneValue.setText(phoneNo);
        txtOwnerEmailValue.setText(email);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCloseInDialog:
                getDialog().dismiss();
                break;
            default:
                break;
        }

    }
}
