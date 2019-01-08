package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.PushnotificationAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.NotificationData;
import com.mawaqaa.homepluskw_new.interfaces.RemoveitemListener;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aswathy on 1/3/2017.
 */
public class PushNotificationFragment extends HomePlusBaseFragment {
    NotificationData notificationdata;
    ArrayList<NotificationData>notification_array;
    public static final String TAG="pshnfrag";
    ListView pushnotification_list;
    TextView notification_head;
    PushnotificationAdapter pushadapter;
    RemoveitemListener onRemoveRequestItem;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.push_notification,container,false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_my_account_title_action_bar));
        initView(view);
        return view;
    }

    private void initView(View view) {
        pushnotification_list = (ListView) view.findViewById(R.id.lview_notification);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            notification_head = (TextView) view.findViewById(R.id.notification_head);
            FontUtils.setHomeplusRegularFont(Activity,notification_head);
            Log.e(TAG,"insideinitview  listview id>>>"+pushnotification_list);
        }
        else
        {
            notification_head = (TextView) view.findViewById(R.id.notification_head);
            FontUtils.setHomeplusArabicRegularFont(Activity,notification_head);
        }
        fetchRequestFormData();
        onRemoveRequestItem=new RemoveitemListener() {
            @Override
            public void onRemove() {
                fetchRequestFormData();
            }
        };

    }

    private void fetchRequestFormData() {

        try{
            if(Activity.isNetworkAvailable()){
                JSONObject jsonObject = new JSONObject();

                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                jsonObject.putOpt(AppConstants.USER_ID, PreferenceUtil.getUserId(Activity));


                Log.e(TAG, "Json Req:"+jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_PUSHNOTIFICATION_LIST, jsonObject);
            }else{
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPushNotificationfileRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onPushNotificationfileRequestDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseJsonMyRequestData(jsonObject);
    }

    private void parseJsonMyRequestData(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS))
                {
                    JSONArray jsonArray=jsonObject.getJSONArray(AppConstants.NOTIFICATION_ARR);
                    if(jsonArray.length()>0)
                    {
                    notification_array=new ArrayList<>();
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            Log.e(TAG,">>>>>"+jsonArray.getJSONObject(i).getString(AppConstants.RETURNIMAGE));
                            notification_array.add(new NotificationData(jsonArray.getJSONObject(i).getString(AppConstants.PROPERTY_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PROPERTYLOCATION),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PROPERTY_PURPOSE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PROPERTYTYPE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.READSTATUS),
                                    jsonArray.getJSONObject(i).getString(AppConstants.TITLE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.RETURNIMAGE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.POSTON)));
                        }
                       pushadapter = new PushnotificationAdapter(Activity, notification_array,onRemoveRequestItem);
                        Log.e("listview id parsing",">>>"+pushnotification_list);
                       pushnotification_list.setAdapter(pushadapter);
                        pushadapter.notifyDataSetChanged();
                        notification_head.setText(getResources().getString(R.string.you_have_missed)+" "+jsonArray.length()+" "+getResources().getString(R.string.new_notifications));
                       // notification_head.setText("You have missed"+" "+jsonArray.length()+""+" new notifications");
                       // setStatus(notification_array);
                    }
                    //else
                        //txtNoData.setVisibility(View.VISIBLE);

                }
                else{
                    notification_head.setText(getResources().getString(R.string.you_have_missed)+" "+0+" "+getResources().getString(R.string.new_notifications));
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setStatus(ArrayList<NotificationData> notification_array) {
        if(notification_array.size()>0)
        {
            String notificationStatus=notificationdata.getReadystatus().toString();
            int status=Integer.parseInt(notificationStatus);

        }
    }

    @Override
    public void onPushNotificationRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onPushNotificationRequestDataLoadedFailed(jsonObject);
    }
}
