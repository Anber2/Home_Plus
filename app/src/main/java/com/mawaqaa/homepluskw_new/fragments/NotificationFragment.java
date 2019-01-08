package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.MyRequestListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.MyRequestListData;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JijoCJ on 1/2/2017.
 */
public class NotificationFragment extends HomePlusBaseFragment implements View.OnClickListener{

    private static final String TAG = "MyRequestListFragment";

    TextView txtNoData;
    RecyclerView recycViewMyReq;
    MyRequestListAdapter myRequestListAdapter;
    List<MyRequestListData> requestList;

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
        View view = inflater.inflate(R.layout.fragment_my_request, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        Log.e(TAG, "User Id:"+ PreferenceUtil.getUserId(Activity));
        initView(view);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.homeButtonState();
        return view;
    }

    private void initView(View view) {

        txtNoData = (TextView) view.findViewById(R.id.txtNoData);

        recycViewMyReq = (RecyclerView) view.findViewById(R.id.recycViewMyReq);
        recycViewMyReq.setLayoutManager(new LinearLayoutManager(Activity));

        fetchMyRequestData();

    }

    private void fetchMyRequestData() {
        try {
            if(Activity.isNetworkAvailable()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                jsonObject.putOpt(AppConstants.USER_ID, PreferenceUtil.getUserId(Activity));
                Log.e(TAG, "Json Req:"+jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                if(VolleyUtils.volleyEnabled){
                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_MY_REQUEST_LIST, jsonObject);
                }
            }else{
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMyRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onMyRequestDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseJsonMyRequestData(jsonObject);
    }

    private void parseJsonMyRequestData(JSONObject jsonObject) {
        try{
            if(jsonObject != null){
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)){
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.DETAILS_ARR);
                    if(jsonArray.length() > 0){
                        requestList = new ArrayList<>();
                        for(int i =0; i < jsonArray.length(); i++){
                            requestList.add(new MyRequestListData(jsonArray.getJSONObject(i).getString(AppConstants.APPLIATION_ID), jsonArray.getJSONObject(i).getString(AppConstants.APPLIED_DATE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PROPERTY_LOCATION), jsonArray.getJSONObject(i).getString(AppConstants.PROPERTY_TYPE), jsonArray.getJSONObject(i).getString(AppConstants.PURPOSE)));
                        }
                       // myRequestListAdapter = new MyRequestListAdapter(Activity, requestList);
                        //recycViewMyReq.setAdapter(myRequestListAdapter);
                    }else
                        txtNoData.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMyRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onMyRequestDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Server Error:"+jsonObject);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }
}

