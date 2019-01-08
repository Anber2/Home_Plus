package com.mawaqaa.homepluskw_new.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

/**
 * Created by JijoCJ on 12/2/2016.
 */
public class MyAccountFragment extends HomePlusBaseFragment implements View.OnClickListener{

    private static final String TAG = "MyAccountFragment";
    Button btnEditProfile, btnRequestPropertyService, btnMyRequest, btnNotification, btnLogout;
    Button btnReqProptyService;

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
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        Log.e(TAG, "User Id:"+ PreferenceUtil.getUserId(Activity));
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.my_account));
        initView(view);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.homeButtonState();
        return view;
    }

    private void initView(View view) {
        btnEditProfile = (Button) view.findViewById(R.id.btnEditProfile);

        btnRequestPropertyService = (Button) view.findViewById(R.id.btnRequestPropertyService);
        btnMyRequest = (Button) view.findViewById(R.id.btnMyRequest);
        btnNotification = (Button) view.findViewById(R.id.btnNotification);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);


        btnEditProfile.setOnClickListener(this);
        btnRequestPropertyService.setOnClickListener(this);
        btnMyRequest.setOnClickListener(this);
        btnNotification.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditProfile:
                Log.e("ee","554545");
                Activity.pushFragments(new EditProfileFragment(), false, true);
                break;
            case R.id.btnRequestPropertyService:
               //Activity.pushFragments(new EditProfileFragment(), false, true);
                Activity.pushFragments(new RequestProptyServiceFragment(), false, true);
                break;
            case R.id.btnMyRequest:
                Activity.pushFragments(new MyRequestListFragment(), false, true);
                break;
            case R.id.btnNotification:
                Activity.pushFragments(new PushNotificationFragment(), false, true);
                break;
            case R.id.btnLogout:
                try {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePlusBaseActivity.getHpBaseActivity());
                        // set title
                       // alertDialogBuilder.setTitle(R.string.logout);

                        // set dialog message
                        alertDialogBuilder
                                .setMessage(R.string.alert_confirm_logout)
                                .setCancelable(false)
                                .setPositiveButton(getResources().getString(R.string.yes),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                                // do whatever you want to do when user
                                                // clicks ok
                                                PreferenceUtil.setKeepMeSignedIn(Activity, false);
                                                PreferenceUtil.setUserId(Activity, "");
                                                PreferenceUtil.setUserSignedIn(Activity, false);
                                                Toast.makeText(HomePlusBaseActivity.getHpBaseActivity(), Activity.getString(R.string.alert_log_out),
                                                        Toast.LENGTH_SHORT).show();
                                                Activity.clearAllBackStackEntries();

                                                Fragment loginFragment = new LoginFragment();
                                                Activity.pushFragments(loginFragment, false, true);
                                            }
                                        })
                                .setNegativeButton(getResources().getString(R.string.no),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                // if this button is clicked, just close
                                                // the dialog box and do nothing
                                                dialog.cancel();
                                            }
                                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}


