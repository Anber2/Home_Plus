package com.mawaqaa.homepluskw_new.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;

/**
 * Created by JijoCJ on 11/30/2016.
 */
public class AddressDialogFragment  extends DialogFragment implements View.OnClickListener{

    private String TAG = "AddressDialogFragment";
    private TextView txtPopUpAddress;
    private Button btnCloseInDialog;
    private Context context;
    public HomePlusBaseActivity Activity;

    String address;

    public static AddressDialogFragment newInstance(String address){
        AddressDialogFragment addressDialogFragment = new AddressDialogFragment();
        addressDialogFragment.address = address;
        return addressDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View view = View.inflate(getActivity(), R.layout.fragment_address_pop_up, null);

        Dialog dialog = new Dialog(getActivity(), R.style.DialogFragment);
        dialog.setContentView(view);
        onViewCreated(dialog.findViewById(R.id.dialog_id), savedInstanceState);
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("Dialog", ""+address);
        /*Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.7f; // dim only a little bit
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(params);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 500);
        window.setGravity(Gravity.CENTER);*/
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 500);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // add view code
        //getDialog().setCanceledOnTouchOutside(true);
        //FontUtils.setEbrimaFont(Activity, relativelayoutchangepaswd);
        txtPopUpAddress= (TextView) view.findViewById(R.id.txtPopUpAddress);
        btnCloseInDialog= (Button) view.findViewById(R.id.btnCloseInDialog);
        btnCloseInDialog.setOnClickListener(this);
        //txtPopUpAddress.setMovementMethod(new ScrollingMovementMethod());
        txtPopUpAddress.setText(Html.fromHtml(address).toString().trim());
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

