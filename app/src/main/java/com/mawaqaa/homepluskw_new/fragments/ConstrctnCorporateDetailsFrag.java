package com.mawaqaa.homepluskw_new.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.mawaqaa.homepluskw_new.activity.VideoActivity;
import com.mawaqaa.homepluskw_new.adapter.ExperienceDetailsListAdapter;
import com.mawaqaa.homepluskw_new.adapter.HighlightsImageAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.dialog.AddressDialogFragment;
import com.mawaqaa.homepluskw_new.dialog.CompanyDetailsDialogFragment;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.BitmapUtil;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.GPSTracker;
import com.mawaqaa.homepluskw_new.utility.GeneralUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.views.CirclePageIndicator;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JijoCJ on 11/22/2016.
 */
public class ConstrctnCorporateDetailsFrag extends HomePlusBaseFragment implements View.OnClickListener,ViewPager.OnPageChangeListener,RatingBar.OnRatingBarChangeListener{

    private static final String TAG = "CstrtnCorprteDetailFrag";
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private Bitmap b;
    TextView txt_workinhours,servicedetails,expdetails;
    ImageButton imgBtnSlidePrevious, imgBtnSlideNext;
    ImageView imgViewIndividual, imgViewVideoThumbNail;

    LinearLayout linearLayServiceDetails, linearLayExperienceDetails, linearLayRateAction;
    RelativeLayout relativeLayCompanyName,relativeLaySharenow, relativeLayAddress;

    RecyclerView recycViewServiceDetails, recycViewExpernceDetails;

    List<String> serviceList;
    List<String> experienceList;
    List<String> imageBannerList;

    HighlightsImageAdapter mImageAdapter;
    ExperienceDetailsListAdapter mExperienceListAdapter;

    ViewPager viewPagerInCorporateDetails;
    CirclePageIndicator circleIndicatorInCorporateDetails;

    RatingBar ratingBarInIndivdualDetails, ratingBarYour,ratingBarconstuctiondetails;

    MapView mapViewCorporateDetails;
    GoogleMap googleMap;
    String android_link;
    String constructionInnerId;
    String videoUrl;
    String companyAddress = "";
    String workingHrs = "";
    String companyName = "";
    String companyPhone = "";
    String companyEmail = "";
    String companyWebsite = "";
    String share_image="";
    double latitude, longitude,current_lattitude,current_longitude;
    private GPSTracker gps;
    boolean isFromOffers;

    public static ConstrctnCorporateDetailsFrag newInstance(String constructionInnerId, boolean isFromOffers){

        ConstrctnCorporateDetailsFrag corporateDetailsFrag = new ConstrctnCorporateDetailsFrag();
        corporateDetailsFrag.constructionInnerId = constructionInnerId;
        corporateDetailsFrag.isFromOffers = isFromOffers;
        return corporateDetailsFrag;
    }

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
        View view = inflater.inflate(R.layout.fragment_construction_corporate_details, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        if(!isFromOffers){
            ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_construction_title_action_bar));
            buttonClickState.constructionButtonState();
        }else {
            ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_special_offer_title_action_bar));
            buttonClickState.specialOfferButtonState();
        }
        initView(view);

        mapViewCorporateDetails.onCreate(savedInstanceState);

        mapViewCorporateDetails.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(Activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapViewCorporateDetails.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
               // googleMap.setMyLocationEnabled(true);
                // For dropping a marker at a point on the Map
                LatLng location = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(location).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return view;
    }

    private void initView(View view) {
        txt_workinhours=(TextView)view.findViewById(R.id.txt_workinghours);
        servicedetails=(TextView)view.findViewById(R.id.servicehead);
        expdetails=(TextView)view.findViewById(R.id.expdetails);
        linearLayRateAction=(LinearLayout)view.findViewById(R.id.linearLayRateAction);
        ratingBarconstuctiondetails=(RatingBar)view.findViewById(R.id.ratingBarconstuctiondetails);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(Activity,txt_workinhours);
            FontUtils.setHomeplusRegularFont(Activity,servicedetails);
            FontUtils.setHomeplusRegularFont(Activity,expdetails);

        }
        else
        {
            FontUtils.setHomeplusRegularFont(Activity,txt_workinhours);
            FontUtils.setHomeplusArabicRegularFont(Activity,servicedetails);
            FontUtils.setHomeplusArabicRegularFont(Activity,expdetails);
        }
        imgBtnSlideNext = (ImageButton) view.findViewById(R.id.imgBtnSlideNext);
        imgBtnSlidePrevious = (ImageButton) view.findViewById(R.id.imgBtnSlidePrevious);

        imgViewIndividual = (ImageView) view.findViewById(R.id.imgViewIndividual);
        imgViewVideoThumbNail = (ImageView) view.findViewById(R.id.imgViewVideoThumbNail);

        imgViewVideoThumbNail.setOnClickListener(this);

        viewPagerInCorporateDetails = (ViewPager) view.findViewById(R.id.viewPagerInCorporateDetails);
        circleIndicatorInCorporateDetails = (CirclePageIndicator) view.findViewById(R.id.circleIndicatorInCorporateDetails);
        circleIndicatorInCorporateDetails.setRadius(1);

        ratingBarInIndivdualDetails = (RatingBar) view.findViewById(R.id.ratingBarInIndivdualDetails);
        ratingBarYour = (RatingBar) view.findViewById(R.id.ratingBarYour);
        ratingBarYour.setOnRatingBarChangeListener(this);
        mapViewCorporateDetails = (MapView) view.findViewById(R.id.mapViewCorporateDetails);

        recycViewServiceDetails = (RecyclerView) view.findViewById(R.id.recycViewServiceDetails);
        recycViewExpernceDetails = (RecyclerView) view.findViewById(R.id.recycViewExpernceDetails);
        //recycViewRealEstate.setHasFixedSize(true);
        recycViewServiceDetails.setLayoutManager(new LinearLayoutManager(Activity));
        recycViewExpernceDetails.setLayoutManager(new LinearLayoutManager(Activity));
        //recycViewRealEstate.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        linearLayServiceDetails = (LinearLayout) view.findViewById(R.id.linearLayServiceDetails);
        linearLayExperienceDetails = (LinearLayout) view.findViewById(R.id.linearLayExperienceDetails);
        relativeLayCompanyName = (RelativeLayout) view.findViewById(R.id.relativeLayCompanyName);
        relativeLaySharenow = (RelativeLayout) view.findViewById(R.id.relativeLayShareNow);
        relativeLayAddress = (RelativeLayout) view.findViewById(R.id.relativeLayAddress);

        relativeLayAddress.setOnClickListener(this);
        relativeLaySharenow.setOnClickListener(this);
        relativeLayCompanyName.setOnClickListener(this);

        imgBtnSlideNext.setOnClickListener(this);
        imgBtnSlidePrevious.setOnClickListener(this);
        gps = new GPSTracker(getActivity());
        if(gps.canGetLocation()){
            current_lattitude = gps.getLatitude();
            current_longitude = gps.getLongitude();
            Log.e("Location",""+current_lattitude+""+current_longitude);
        }else{
            gps.showSettingsAlert();
        }


        if(!PreferenceUtil.getUserId(Activity).equals(""))
            linearLayRateAction.setVisibility(View.VISIBLE);
        else
            linearLayRateAction.setVisibility(View.GONE);


        // fetch Real Estate Data From Server
        if(Activity.isNetworkAvailable())
            fetchConstrctnCorporateInnerDetailsData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void fetchConstrctnCorporateInnerDetailsData() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionInnerId);
            Log.e(TAG, "Json Request:"+jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if(VolleyUtils.volleyEnabled){
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_DETAILS, jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseConstrctnCorporateDetailsData(JSONObject jsonObject) {
        Log.d("CORDEATAILS",jsonObject.toString());
        try{
            if(jsonObject != null){
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)){
                    //
                    android_link=jsonObject.getString(AppConstants.HOME_PLUS_ANDROID_LINK);
                    JSONArray jsonServiceArray = jsonObject.getJSONArray(AppConstants.SERVICE_DESCRIPTION_ARR);
                    if(jsonServiceArray.length() > 0){
                        serviceList = new ArrayList<>();
                        for(int i = 0; i < jsonServiceArray.length(); i++){
                            serviceList.add(jsonServiceArray.getString(i));
                        }
                        mExperienceListAdapter =  new ExperienceDetailsListAdapter(Activity, serviceList);
                        recycViewServiceDetails.setAdapter(mExperienceListAdapter);
                    }else{
                        linearLayServiceDetails.setVisibility(View.GONE);
                    }

                    JSONArray jsonExperienceArray = jsonObject.getJSONArray(AppConstants.EXPERIENCE_DETAILS_ARR);
                    if(jsonExperienceArray.length() > 0){
                        experienceList = new ArrayList<>();
                        for(int i = 0; i < jsonExperienceArray.length(); i++){
                            experienceList.add(jsonExperienceArray.getString(i));
                        }
                        mExperienceListAdapter =  new ExperienceDetailsListAdapter(Activity, experienceList);
                        recycViewExpernceDetails.setAdapter(mExperienceListAdapter);
                    }else{
                        linearLayExperienceDetails.setVisibility(View.GONE);
                    }

                    JSONArray jsonImagesArray = jsonObject.getJSONArray(AppConstants.IMAGE_LIST);
                    if(jsonImagesArray!=null && jsonImagesArray.length() > 0){
                        imageBannerList = new ArrayList<>();
                        for (int j = 0; j < jsonImagesArray.length(); j++){
                            imageBannerList.add(jsonImagesArray.getString(j));
                            share_image=imageBannerList.get(0);
                        }
                        mImageAdapter = new HighlightsImageAdapter(Activity, imageBannerList);
                        viewPagerInCorporateDetails.setAdapter(mImageAdapter);
                        circleIndicatorInCorporateDetails.setViewPager(viewPagerInCorporateDetails);
                        final float density = getResources().getDisplayMetrics().density;
                        circleIndicatorInCorporateDetails.setRadius(5 * density);
                        NUM_PAGES =imageBannerList.size();
                        if(NUM_PAGES==0)
                        {
                            circleIndicatorInCorporateDetails.setVisibility(View.GONE);
                        }
                        else {
                            circleIndicatorInCorporateDetails.setVisibility(View.VISIBLE);
                        }
                        // Auto start of viewpager
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == NUM_PAGES) {
                                    currentPage = 0;
                                }
                                viewPagerInCorporateDetails.setCurrentItem(currentPage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, 4000, 4000);
                        circleIndicatorInCorporateDetails.setOnPageChangeListener(this);

                    }
                    videoUrl = jsonObject.getString(AppConstants.VIDEO_URL);
                    if(videoUrl!=null && !videoUrl.isEmpty() && !videoUrl.equals("null"))

                    {
                        String videoID = GeneralUtils.getYouTubeVideoId(jsonObject.getString(AppConstants.VIDEO_URL));
                        String videoThumbUrl = AppConstants.MAKE_THUMBNAIL_BASE_URL + videoID + AppConstants.MAKE_THUMBNAIL_DEFAULT;
                        Log.e(TAG, "ThumbNail Video:" + videoThumbUrl);
                        Picasso.with(Activity).load(videoThumbUrl).resize(400, 400).into(imgViewVideoThumbNail);
                    }else{
                        String thumbNaildemo = jsonObject.getString(AppConstants.VIDEO_THUMBNAIL);
                        Picasso.with(Activity).load(thumbNaildemo).into(imgViewVideoThumbNail);
                    }
                    float fom_value;
                    try {
                        fom_value = Float.parseFloat(jsonObject.getString(AppConstants.LONGITUDE_CORPORATE_DETAILS_RATING));
                    }
                    catch(NumberFormatException ex) {
                        fom_value = (float) 0.0; // default ??
                    }
                     ratingBarconstuctiondetails.setRating(fom_value);
                    ratingBarconstuctiondetails.setIsIndicator(true);
                    txt_workinhours.setText(jsonObject.getString(AppConstants.WORKING_HOURS));
                    Log.e("workinghr",">>>>"+txt_workinhours);

                    companyAddress = jsonObject.getString(AppConstants.COMPANY_ADDRESS);
                    workingHrs = jsonObject.getString(AppConstants.WORKING_HOURS);
                    companyName = jsonObject.getString(AppConstants.COMPANY_NAME);

                    companyPhone = jsonObject.getString(AppConstants.COMPANY_PHONE);
                    companyEmail = jsonObject.getString(AppConstants.COMPANY_EMAIL);
                    companyWebsite = jsonObject.getString(AppConstants.COMPANY_WEBSITE);


                    //Google Map
                    latitude = Double.parseDouble(jsonObject.getString(AppConstants.LATITUDE_CORPORATE_DETAILS));
                    longitude = Double.parseDouble(jsonObject.getString(AppConstants.LONGITUDE_CORPORATE_DETAILS));
                    Log.e(TAG, ""+latitude+","+longitude);
                    LatLng location = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(location).title(jsonObject.getString(AppConstants.COMPANY_LOCATION)).snippet("Marker Description"));

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


                }else{

                }
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConstructionCorporateInnerDetailsDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onConstructionCorporateInnerDetailsDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseConstrctnCorporateDetailsData(jsonObject);
    }


    @Override
    public void onConstructionCorporateInnerDetailsDataLoadedFailed(JSONObject jsonObject) {
        super.onConstructionCorporateInnerDetailsDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnSlideNext:
                if (viewPagerInCorporateDetails.getCurrentItem() < imageBannerList.size()) {
                    viewPagerInCorporateDetails.setCurrentItem(viewPagerInCorporateDetails.getCurrentItem() + 1);
                }
                break;
            case R.id.imgBtnSlidePrevious:
                if (viewPagerInCorporateDetails.getCurrentItem() > 0) {
                    viewPagerInCorporateDetails.setCurrentItem(viewPagerInCorporateDetails.getCurrentItem() - 1);
                }
                break;
            case R.id.imgViewVideoThumbNail:
                if(videoUrl!=null || !videoUrl.equals("") || !videoUrl.equals("null")) {
                    Log.e("VLINk","ss"+videoUrl);
                    Intent intent = new Intent(Activity, VideoActivity.class);
                    intent.putExtra(AppConstants.VIDEO_URL, GeneralUtils.getYouTubeVideoId(videoUrl));
                    Activity.startActivity(intent);
                }
                /*Intent intent = new Intent(Activity, VideoActivity.class);
                intent.putExtra(AppConstants.VIDEO_URL, GeneralUtils.getYouTubeVideoId(videoUrl));
                Activity.startActivity(intent);*/
                break;
            case R.id.relativeLayCompanyName:
                FragmentTransaction fttt = ((HomePlusBaseActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                CompanyDetailsDialogFragment companyDialog = CompanyDetailsDialogFragment.newInstance(companyName, companyPhone, companyEmail, companyWebsite);
                companyDialog.show(fttt, "MyDialogFragment");
                break;
            case R.id.relativeLayShareNow:
               /* FragmentTransaction ftt = ((HomePlusBaseActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                WorkingHoursDialogFragment workingHrsDialog = WorkingHoursDialogFragment.newInstance(workingHrs);
                workingHrsDialog.show(ftt, "MyDialogFragment");*/
               // shareIt();
                ShareItNew();
                break;
            case R.id.relativeLayAddress:
                FragmentTransaction ft = ((HomePlusBaseActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                AddressDialogFragment dialog = AddressDialogFragment.newInstance(companyAddress);
                dialog.show(ft, "MyDialogFragment");
                break;
            default:
                break;
        }
    }
    private void ShareItNew() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "HomePlus");
            String sAux = "\nLet me recommend you this application\n\n";
            //sAux = sAux + "http://www.homepluskw.com/ar/Constructions/Individual/Individual-List/Individual-Details?Id="+constructionInnerId;
            sAux = sAux +android_link;
            getBitmap();
            String path = MediaStore.Images.Media.insertImage(Activity.getContentResolver(), b, "", null);
            Uri screenshotUri = Uri.parse(path);

            File file = getSlectedBytesArrayForTwitter(b);
            if (file != null) {
                Log.e("Instagram FILEBNOTNULL", "Instagram button Clicked");
                Uri uri = Uri.fromFile(getSlectedBytesArrayForTwitter(b));
                i.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_email)+":"+"  "+companyEmail + "    " +getResources().getString(R.string.ph_no)+":"+""+companyPhone);
                i.putExtra(Intent.EXTRA_STREAM,screenshotUri);

            }
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }

    }

    public File getSlectedBytesArrayForTwitter(Bitmap image) {
        if (image == null)
            return null;

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/Pictures");
        dir.mkdirs();
        File file = new File(dir, "FWPic"
                + SystemClock.currentThreadTimeMillis() + ".png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(baos.toByteArray());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return file;

    }
    private void getBitmap() {

        if (BitmapUtil.PICASSO_ENABLED) {
            Log.e("Share Dialog", "Bitmap String: " + share_image);
            Picasso.with(getActivity()).load(share_image).into(target);


        }
    }
    private Target target = new Target() {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (bitmap != null) {
                b = bitmap;
                // mProgressDialog.setVisibility(View.GONE);
            }
        }

        @Override
        public void onBitmapFailed(Drawable arg0) {
            // mProgressDialog.setVisibility(View.GONE);
        }

        @Override
        public void onPrepareLoad(Drawable arg0) {
            // TODO Auto-generated method stub

        }
    };


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        int ratingvalue=Math.round(rating);
        Log.e("rating","@@@@"+ratingvalue);
        if(Activity.isNetworkAvailable())
            SendConstructionIndividualRating(ratingvalue);
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();

    }
    private void SendConstructionIndividualRating(int ratingvalue) {

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.SERVICEID, constructionInnerId);
            jsonObject.putOpt(AppConstants.RATING, ratingvalue);
            jsonObject.putOpt(AppConstants.SERVICETYPE,"c");
            jsonObject.putOpt(AppConstants.USERID,PreferenceUtil.getUserId(Activity));
            Log.e(TAG, "Json Request:"+jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if(VolleyUtils.volleyEnabled){
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_INDIVIDUAL_RATING, jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onIndividualRatingRequestDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onIndividualRatingRequestDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        try {
            if (jsonObject != null) {

                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {

                    Toast.makeText(Activity,jsonObject.getString(AppConstants.MESSAGE),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Activity,jsonObject.getString(AppConstants.MESSAGE),Toast.LENGTH_SHORT).show();
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onIndividualRatingRequestDataLoadedFailed(JSONObject jsonObject) {
        super.onIndividualRatingRequestDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }
}