package com.mawaqaa.homepluskw_new.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.GPSTracker;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;

import org.json.JSONObject;

/**
 * Created by JijoCJ on 1/5/2017.
 */
public class ContactusFragment extends HomePlusBaseFragment implements View.OnClickListener {
    GoogleMap googleMap;
    MapView googlemapview;
    public static final String TAG="ContactUs";
    WebView contactusWebview;
    String contactInfo;
    double latitude, longitude,current_lattitude,current_longitude;
    Button btn_addressInformation,btn_feeddback;
    private GPSTracker gps;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_contact_addrsinfo,container,false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.contact_us));
        initView(rootView);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.constructionButtonState();
        googlemapview = (MapView) rootView.findViewById(R.id.mapViewcontactus);
        googlemapview.onCreate(savedInstanceState);

        googlemapview.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(Activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        googlemapview.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);
                // For dropping a marker at a point on the Map
                LatLng location = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(location).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return rootView;
    }

    private void initView(View rootView) {

        contactusWebview=(WebView)rootView.findViewById(R.id.webviewaddressinfo);
        btn_addressInformation=(Button)rootView.findViewById(R.id.btnAddrsinformation);
        btn_feeddback=(Button)rootView.findViewById(R.id.btnTabFeedback);

       if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
           FontUtils.setHomeplusRegularFont(Activity,btn_addressInformation);
           FontUtils.setHomeplusRegularFont(Activity,btn_feeddback);
           btn_addressInformation.setSelected(true);
           btn_feeddback.setSelected(false);
       }
           else
       {
           FontUtils.setHomeplusArabicRegularFont(Activity,btn_addressInformation);
           FontUtils.setHomeplusArabicRegularFont(Activity,btn_feeddback);
           btn_addressInformation.setSelected(true);
           btn_feeddback.setSelected(false);
        }
        btn_addressInformation.setOnClickListener(this);
        btn_feeddback.setOnClickListener(this);
        gps = new GPSTracker(getActivity());
        if(gps.canGetLocation()){
            current_lattitude = gps.getLatitude();
            current_longitude = gps.getLongitude();
            Log.e("Location",""+current_lattitude+""+current_longitude);
        }else{
            gps.showSettingsAlert();
        }
        fetchRequestFormData();
    }

    private void fetchRequestFormData() {
        try{
            if(Activity.isNetworkAvailable()){
                JSONObject jsonObject = new JSONObject();

                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                Log.e(TAG, "Json Req:"+jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_CONTACTUS_ADDRESSINFO, jsonObject);
            }else{
                Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onContactusAddressinfoRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onContactusAddressinfoRequestDataLoadedSuccessfully(jsonObject);
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
                    Log.e("html...contact",""+jsonObject.getString(AppConstants.MESSAGE));
                    contactInfo = jsonObject.getString(AppConstants.MESSAGE);
                    contactusWebview.getSettings().setJavaScriptEnabled(true);
                    if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ARABIC)) {
                        Log.e("BVBV","here");
                        contactInfo =  FontUtils.getHtmlDatawithArabicFont(Activity, contactInfo);
                        contactusWebview.loadDataWithBaseURL(null,
                                contactInfo, "text/html", "UTF-8", "");
                    }else{
                        contactusWebview.loadDataWithBaseURL(null,
                                FontUtils.getHtmlDatawithEnglishFont(Activity, contactInfo), "text/html", "UTF-8", "");

                    }
                   // contactusWebview.loadData(contactInfo, "text/html; charset=utf-8", "UTF-8");
                    String path=jsonObject.getString(AppConstants.LATITUDECONTACT);
                    path = path.substring(0, path.length() - 1);
                    System.out.println("trimming"+path);
                    String path1=jsonObject.getString(AppConstants.LONGITUDE);
                    path1 = path1.substring(0, path1.length() - 1);
                    latitude = Double.parseDouble(path);
                    longitude = Double.parseDouble(path1);
                    Log.e(TAG, ""+latitude+","+longitude);
                    LatLng location = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(location).title("hi").snippet("Marker Description"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("http://maps.google.com/maps?saddr="+  gps.getLatitude()+ ","
                                            + gps.getLongitude()
                                            + "&daddr="
                                            + latitude + "," + longitude+"&dirflg=d"));
                            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                            startActivity(intent);
                            return false;
                        }
                    });



                }
                else
                {
                    contactInfo = getResources().getString(R.string.nodatahtml);
                    contactusWebview.getSettings().setJavaScriptEnabled(true);
                    contactusWebview.loadData(contactInfo, "text/html; charset=utf-8", "UTF-8");
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
    public void onContactusAddressinfoRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onContactusAddressinfoRequestDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnAddrsinformation:

                break;
            case  R.id.btnTabFeedback:
                Activity.pushFragments(new ContactusFeedbackFragment(), false, true);
                break;
        }
    }
}
