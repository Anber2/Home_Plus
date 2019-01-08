package com.mawaqaa.homepluskw_new.adapter;

        import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.support.v7.app.AlertDialog;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
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
        import com.mawaqaa.homepluskw_new.data.NotificationData;
        import com.mawaqaa.homepluskw_new.fragments.RealEstateFragment;
        import com.mawaqaa.homepluskw_new.interfaces.RemoveitemListener;
        import com.mawaqaa.homepluskw_new.utility.BaseApplication;
        import com.mawaqaa.homepluskw_new.utility.FontUtils;
        import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
        import com.mawaqaa.homepluskw_new.volley.VolleyUtils;
        import com.squareup.picasso.Picasso;

        import org.json.JSONObject;

        import java.util.ArrayList;

/**
 * Created by aswathy on 1/3/2017.
 */
public class PushnotificationAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    String request_url;
    JSONObject jsonObject;
    public static final String TAG="PushnotificationAdapter";
    //NotificationData notidicationdata;
    NotificationData row_pos;
    Context context;
    RemoveitemListener onRemoveRequestItem;
    ArrayList<NotificationData>notification_array;
    public PushnotificationAdapter(Activity activity, ArrayList<NotificationData> notification_array, RemoveitemListener onRemoveRequestItem) {
        this.context=activity;
        this.notification_array=notification_array;
        mInflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.onRemoveRequestItem=onRemoveRequestItem;
    }

    @Override
    public int getCount() {
        return notification_array.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.notification_list_item, null);
        }
        ImageView Property_image=(ImageView)convertView.findViewById(R.id.imageViewproperty);
        TextView textViewregion=(TextView)convertView.findViewById(R.id.textViewregion);
        TextView txtviewpurpose=(TextView)convertView.findViewById(R.id.txtviewpurpose);
        TextView txtviewproptype=(TextView)convertView.findViewById(R.id.txtviewproptype);
        Button btnCloseInDialog=(Button)convertView.findViewById(R.id.btnCloseInDialog);
        TextView Property_region=(TextView) convertView.findViewById(R.id.pushnotification_region);
        TextView Property_purpose=(TextView) convertView.findViewById(R.id.pushnotification_purpose);
        TextView property_date=(TextView) convertView.findViewById(R.id.propdate);
        TextView property_type=(TextView) convertView.findViewById(R.id.pushnotification_proptype);

        //TextView txtCountry = (TextView) convertView.findViewById(R.id.txtSpinnerItem);
         row_pos = notification_array.get(position);

        // setting the image resource and title
        Picasso.with(context).load(row_pos.getReturnImage()).into(Property_image);
        Log.e("image",""+row_pos.getReturnImage());
        Property_region.setText(row_pos.getLocation());
        Property_purpose.setText(row_pos.getPurpose());
        property_type.setText(row_pos.getProptype());
        property_type.setSelected(true);
        property_date.setText(row_pos.getPoston());

        /*String [] arr = row_pos.getPoston().split(" ", 2);
        if(arr.length > 1){
            String date = getColoredSpanned(arr[0], "#C62C36");
            String time = getColoredSpanned(arr[1], "#000000");
            property_date.setText(Html.fromHtml(date+" "+time));

            //FontUtils.setHomeplusRegularFont(context,property_date);
        }*/
        if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(context,textViewregion);
            FontUtils.setHomeplusRegularFont(context,txtviewpurpose);
            FontUtils.setHomeplusRegularFont(context,txtviewproptype);
            FontUtils.setHomeplusRegularFont(context,property_type);
            FontUtils.setHomeplusRegularFont(context,Property_region);
            FontUtils.setHomeplusRegularFont(context,Property_purpose);
            FontUtils.setHomeplusRegularFont(context,property_date);



        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(context,textViewregion);
            FontUtils.setHomeplusArabicRegularFont(context,txtviewpurpose);
            FontUtils.setHomeplusArabicRegularFont(context,txtviewproptype);
            FontUtils.setHomeplusArabicRegularFont(context,property_type);
            FontUtils.setHomeplusArabicRegularFont(context,Property_region);
            FontUtils.setHomeplusArabicRegularFont(context,Property_purpose);
            //FontUtils.setHomeplusRegularFont(context,property_date);



        }
        //property_date.setText(row_pos.getPoston());
        // property_date.setText(row_pos.getPropertyPostedDate());
        //need to display property time
        //property_time.setText(row_pos.getPropertyPostedDate());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePlusBaseActivity.getHpBaseActivity().pushFragments(RealEstateFragment.newInstance(row_pos.getId(),true), false, true);
               /* if(row_pos.getIsRequest().equalsIgnoreCase("true"))
                {
                    HomePlusBaseActivity.getHpBaseActivity().pushFragments(RealEstateFragment.newInstance(row_pos.getId(),true), false, true);
                }
                else
                {
                    HomePlusBaseActivity.getHpBaseActivity().pushFragments(new SpecialOffersFragment().newInstance(row_pos.getId(),true), false, true);


                }*/
            }
        });
        btnCloseInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(true,position);
            }
        });

        return convertView;
    }

    private void removeItem(boolean b, int position) {

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
//                                        ((HomePlusBaseActivity) context).BaseFragment
//                                                .startSpinwheel(false, true);
                                        BaseApplication.getInstance().progressON((HomePlusBaseActivity) context, null);
                                        jsonObject = new JSONObject();
                                        try {
                                            jsonObject.putOpt(AppConstants.HOME_PLUS_NOTIFICATION_ID, row_pos.getId());
                                           // jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(context));
                                            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                                            request_url=AppConstants.HP_DELETENOTIFICATION;
                                            Log.e(TAG, "Change Url: " + AppConstants.HP_DELETENOTIFICATION);
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
//                                                            ((HomePlusBaseActivity) context).BaseFragment
//                                                                    .BaseApplication.getInstance().progressOFF()();
                                                            BaseApplication.getInstance().progressOFF();
                                                            onRemoveItemSuccessfully(response);
                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(
                                                        VolleyError error) {
                                                    Log.e(TAG, "Response : Error");
//                                                    ((HomePlusBaseActivity) context).BaseFragment
//                                                            .BaseApplication.getInstance().progressOFF()();
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

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}

