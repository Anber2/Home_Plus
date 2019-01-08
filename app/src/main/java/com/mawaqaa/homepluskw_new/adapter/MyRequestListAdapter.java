package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.MyRequestListData;
import com.mawaqaa.homepluskw_new.fragments.HomePlusBaseFragment;
import com.mawaqaa.homepluskw_new.interfaces.RemoveitemListener;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by JijoCJ on 1/2/2017.
 */
public class MyRequestListAdapter extends RecyclerView.Adapter<MyRequestListAdapter.ViewHolderMyRequest> {
    public static final String TAG="MyRequestListAdapter";
    HomePlusBaseFragment BaseFragment;
    List<MyRequestListData> myRequestList;
    RemoveitemListener onRemoveRequestItem;
    Context context;
    String request_url;
    JSONObject jsonObject;

    public MyRequestListAdapter(Context context, List<MyRequestListData> myRequestList, RemoveitemListener onRemoveRequestItem){
        this.context = context;
        this.myRequestList = myRequestList;
        this.onRemoveRequestItem=onRemoveRequestItem;
    }
    @Override
    public ViewHolderMyRequest onCreateViewHolder(ViewGroup parent, int viewType) {
        View iteView = LayoutInflater.from(context).inflate(R.layout.my_request_list_item, parent, false);
        return new ViewHolderMyRequest(iteView);
    }

    @Override
    public void onBindViewHolder(ViewHolderMyRequest holder, final int position) {
        MyRequestListData myRequestListData = myRequestList.get(position);

        String [] arr = myRequestListData.getAppliedDate().split(" ", 2);
        if(arr.length > 1){
            String date = getColoredSpanned(arr[0], "#C62C36");
            String time = getColoredSpanned(arr[1], "#000000");
            holder.txtDateMyReq.setText(Html.fromHtml(date+" "+time));

        }
        //holder.txtDateMyReq.setText(myRequestListData.getAppliedDate());
        //holder.txtTimeMyReq.setText(myRequestListData.ge);
        holder.txtRegionValueMyReq.setText(myRequestListData.getPropertyLocation());

        holder.txtPurposeValueMyReq.setText(myRequestListData.getPropertyPurpose());

        holder.txtPrprtyTypeValueMyReq.setText(myRequestListData.getPropertyType());

        if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(context, holder.txtDateMyReq);
            FontUtils.setHomeplusRegularFont(context, holder.txtRegionValueMyReq);
            FontUtils.setHomeplusRegularFont(context, holder.txtPurposeValueMyReq);
            FontUtils.setHomeplusRegularFont(context, holder.txtPrprtyTypeValueMyReq);
            FontUtils.setHomeplusRegularFont(context,holder.tct_myrequest_region);
            FontUtils.setHomeplusRegularFont(context,holder.txt_myrequest_purpose);
            FontUtils.setHomeplusRegularFont(context,holder.txt_myrequest_property_type);

        }
        else
        {
            FontUtils.setHomeplusRegularFont(context, holder.txtDateMyReq);
            FontUtils.setHomeplusArabicRegularFont(context, holder.txtRegionValueMyReq);
            FontUtils.setHomeplusArabicRegularFont(context, holder.txtPurposeValueMyReq);
            FontUtils.setHomeplusArabicRegularFont(context, holder.txtPrprtyTypeValueMyReq);
            FontUtils.setHomeplusArabicRegularFont(context,holder.tct_myrequest_region);
            FontUtils.setHomeplusArabicRegularFont(context,holder.txt_myrequest_purpose);
            FontUtils.setHomeplusArabicRegularFont(context,holder.txt_myrequest_property_type);
        }
        holder.request_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Remove Button Clicked");
                //myRequestList.remove(position);
                //notifyItemRemoved(position);
                removeItem(true,position);
            }


        });
   }

    private void removeItem(boolean b, final int position) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePlusBaseActivity.getHpBaseActivity());
            // set title
            //alertDialogBuilder.setTitle(R.string.delete);
            // set dialog message
            alertDialogBuilder
                    .setMessage(R.string.alert_delete)
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                        if(((HomePlusBaseActivity) context).isNetworkAvailable())
                                        {
                                            Log.e(TAG, "Json Req:"+"networkcheck");


                                            if(VolleyUtils.volleyEnabled){
//                                                ((HomePlusBaseActivity) context).BaseFragment
//                                                        .startSpinwheel(false, true);
                                                BaseApplication.getInstance().progressON((HomePlusBaseActivity) context, null);
                                                 jsonObject = new JSONObject();
                                                try {
                                                    jsonObject.putOpt(AppConstants.MyREQUEST_USERID, myRequestList.get(position).getId());
                                                    jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(context));
                                                    jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                                                    request_url=AppConstants.HP_MY_REQUEST_LIST_DELETE;
                                                    Log.e(TAG, "Change Url: " + AppConstants.HP_MY_REQUEST_LIST_DELETE);
                                                    Log.e(TAG, "Change JsonObj: " + jsonObject.toString());
                                                    // CommandFactory commandFactory = new CommandFactory();
                                                    //commandFactory.sendPostCommand(AppConstants.HP_MY_REQUEST_LIST_DELETE, jsonObject);
                                                    RequestQueue queue = VolleyUtils.getRequestQueue();
                                                    Log.e(TAG,
                                                            "Response : "
                                                                    + queue);
                                                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                                                            Request.Method.POST, request_url, jsonObject,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {

                                                                    Log.e(TAG,
                                                                            "succsss : " );
//                                                                    ((HomePlusBaseActivity) context).BaseFragment
//                                                                            .BaseApplication.getInstance().progressOFF()();
                                                                    BaseApplication.getInstance().progressOFF();

                                                                    onRemoveItemSuccessfully(response);
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(
                                                                VolleyError error) {
                                                            Log.e(TAG, "Response : Error");
//                                                            ((HomePlusBaseActivity) context).BaseFragment
//                                                                    .BaseApplication.getInstance().progressOFF()();
                                                            BaseApplication.getInstance().progressOFF();
                                                        }
                                                    });
                                                    jsObjRequest
                                                            .setRetryPolicy(new DefaultRetryPolicy(
                                                                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                                                                    2, 2));
                                                    queue.add(jsObjRequest);
                                                }
                                                catch (Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        else{
                                            Toast.makeText(context, context.getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
                                        }
                                    /*// do whatever you want to do when user
                                    // clicks ok
                                    PreferenceUtil.setKeepMeSignedIn(Activity, false);
                                    PreferenceUtil.setUserId(Activity, "");
                                    PreferenceUtil.setUserSignedIn(Activity, false);
                                    Toast.makeText(HomePlusBaseActivity.getHpBaseActivity(), Activity.getString(R.string.alert_log_out),
                                            Toast.LENGTH_SHORT).show();
                                    Activity.clearAllBackStackEntries();

                                    Fragment loginFragment = new LoginFragment();
                                    Activity.pushFragments(loginFragment, false, true);*/
                                }
                            })
                    .setNegativeButton("No",
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



    }

    private void onRemoveItemSuccessfully(JSONObject jsonObject) {

        Log.e(TAG, "Remove Success Method");
        if (jsonObject != null) {
            try {
                //jsonObject = jsonObject.getJSONObject(AppConstants.IS_SUCCESS);
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                     Toast.makeText(context,
                    jsonObject.getString(AppConstants.MESSAGE),
                    Toast.LENGTH_SHORT).show();

                    onRemoveRequestItem.onRemove();


                } else {
                    Log.e(TAG, "Remove Item Response Success: False");
                    Toast.makeText(context,
                    jsonObject.getString(AppConstants.MESSAGE),
                     Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, R.string.server_error,
                           //Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception in Remove Success Method");
                e.printStackTrace();
            } finally {
//                ((HomePlusBaseActivity) context).BaseFragment
//                        .BaseApplication.getInstance().progressOFF()();
                BaseApplication.getInstance().progressOFF();
            }


        }
    }

    @Override
    public int getItemCount() {
        return myRequestList.size();
    }

    public static class ViewHolderMyRequest extends RecyclerView.ViewHolder{

        TextView txtDateMyReq, txtTimeMyReq, txtRegionValueMyReq, txtPurposeValueMyReq, txtPrprtyTypeValueMyReq,
        tct_myrequest_region,txt_myrequest_purpose,txt_myrequest_property_type;
        Button request_delete;


        public ViewHolderMyRequest(View itemView) {
            super(itemView);

            txtDateMyReq = (TextView) itemView.findViewById(R.id.txtDateMyReq);
            txtTimeMyReq = (TextView) itemView.findViewById(R.id.txtTimeMyReq);
            txtRegionValueMyReq = (TextView) itemView.findViewById(R.id.txtRegionValueMyReq);
            txtPurposeValueMyReq = (TextView) itemView.findViewById(R.id.txtPurposeValueMyReq);
            txtPrprtyTypeValueMyReq = (TextView) itemView.findViewById(R.id.txtPrprtyTypeValueMyReq);
            request_delete=(Button)itemView.findViewById(R.id.btnCloseInDialog);
            tct_myrequest_region=(TextView)itemView.findViewById(R.id.myrequestregion);
            txt_myrequest_purpose=(TextView)itemView.findViewById(R.id.myrequestpurpose);
            txt_myrequest_property_type=(TextView)itemView.findViewById(R.id.myrequestpropertytype);


            txtRegionValueMyReq.setSelected(true);
            txtPurposeValueMyReq.setSelected(true);
            txtPrprtyTypeValueMyReq.setSelected(true);
        }
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}
