package com.mawaqaa.homepluskw_new.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.mawaqaa.homepluskw_new.adapter.HighlightsImageAdapter;
import com.mawaqaa.homepluskw_new.adapter.HighlightsImageAdaptersponsers;
import com.mawaqaa.homepluskw_new.adapter.RealEstatePropertySpecListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.dialog.RealEstPrptyAdressOwnerDetailsDialogFrag;
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
 * Created by JijoCJ on 11/3/2016.
 */
public class RealEstateDeatailsInnerFragment extends HomePlusBaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    TextView txt_sponsors, txt_propertylocation, txt_videogallery, txt_property_spec;
    private static final String TAG = "RealEstateInnerFrag";
    CirclePageIndicator indicator;//indicator_sponser;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static int pageBanner = 0, pageSponser = 0;
    String share_prop_pup, share_prop_price, share_image, android_link;
    TextView txtDescription, txtValuePropertyPurpose, txtValuePropertyPrice,
            txtValuePropertyType, txtValuePropertySpace, txtValuePropertyRegion,
            txtValuePropertyLocation, txtPropertyAddress, txtOwnerDetails, txtShareNow;
    ImageButton imgBtnSlidePrevious, imgBtnSlideNext, imgBtnSlideSponserPre,
            imgBtnSlideSponserNext;
    ImageView imgViewVideoGalleryThumbNail;

    ViewPager viewPagerRealEstate, viewPagerSponsers;
    RecyclerView recycViewPropertySpec;

    RelativeLayout relativeLayPropertyAddress, relativeLayOwnerDetails, relativeLaySharenow;

    MapView mapViewRealEstate;
    GoogleMap googleMap;
    private Bitmap b;
    List<String> realEstateBannerList;
    List<String> realEstatePropertSpecList;
    List<String> realEstateSponsersList;
    RealEstatePropertySpecListAdapter mAdapter;
    HighlightsImageAdapter highlightsImageAdapter;
    HighlightsImageAdaptersponsers highlightsImageAdaptersp;
    private GPSTracker gps;
    String propertyId, videoUrl, name, phone, email, property_block, property_street, property_address, region, property_house;
    StringBuilder sb;
    boolean isFromOffers;
    double latitude, longitude, current_lattitude, current_longitude;
    Timer timer, swipeTimer;

    public static RealEstateDeatailsInnerFragment newInstance(String propertyId, boolean isFromOffers) {
        RealEstateDeatailsInnerFragment realEstateDeatailsInnerFragment = new RealEstateDeatailsInnerFragment();
        realEstateDeatailsInnerFragment.propertyId = propertyId;
        realEstateDeatailsInnerFragment.isFromOffers = isFromOffers;
        return realEstateDeatailsInnerFragment;
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
        if (timer != null) {
            timer.cancel();

        }
        if (swipeTimer != null) {
            swipeTimer.cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_estate_details_inner, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;

        if (!isFromOffers) {
            ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_real_estate_title_action_bar));
            buttonClickState.realEstateButtonState();
        } else {
            ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_special_offer_title_action_bar));
            buttonClickState.specialOfferButtonState();
        }

        initView(view);


        mapViewRealEstate.onCreate(savedInstanceState);

        mapViewRealEstate.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(Activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapViewRealEstate.getMapAsync(new OnMapReadyCallback() {
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
        return view;
    }

    private void initView(View view) {
        txt_sponsors = (TextView) view.findViewById(R.id.sponcers);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);

        txtValuePropertyPurpose = (TextView) view.findViewById(R.id.txtValuePropertyPurpose);

        txtValuePropertyPrice = (TextView) view.findViewById(R.id.txtValuePropertyPrice);

        txtValuePropertyType = (TextView) view.findViewById(R.id.txtValuePropertyType);

        txtValuePropertySpace = (TextView) view.findViewById(R.id.txtValuePropertySpace);

        txtValuePropertyRegion = (TextView) view.findViewById(R.id.txtValuePropertyRegion);

        txtValuePropertyLocation = (TextView) view.findViewById(R.id.txtValuePropertyLocation);

        //imgBtnSlidePrevious = (ImageButton) view.findViewById(R.id.imgBtnSlidePrevious);
        // imgBtnSlideNext = (ImageButton) view.findViewById(R.id.imgBtnSlideNext);
        //imgBtnSlideSponserPre = (ImageButton) view.findViewById(R.id.imgBtnSlideSponserPre);
        // imgBtnSlideSponserNext = (ImageButton) view.findViewById(R.id.imgBtnSlideSponserNext);
        txtPropertyAddress = (TextView) view.findViewById(R.id.txtPropertyAddress);
        txtOwnerDetails = (TextView) view.findViewById(R.id.txtOwnerDetails);
        txtShareNow = (TextView) view.findViewById(R.id.txtShareNow);
        // FontUtils.setHomeplusRegularFont(Activity,txtShareNow);
        relativeLayPropertyAddress = (RelativeLayout) view.findViewById(R.id.relativeLayPropertyAddress);
        relativeLayOwnerDetails = (RelativeLayout) view.findViewById(R.id.relativeLayOwnerDetails);
        relativeLaySharenow = (RelativeLayout) view.findViewById(R.id.relativeLayShareNow);
        txt_propertylocation = (TextView) view.findViewById(R.id.propertylocation);
        txt_videogallery = (TextView) view.findViewById(R.id.video_gallery);
        txt_property_spec = (TextView) view.findViewById(R.id.propertysepcification);

        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity, txtDescription);
            FontUtils.setHomeplusBoldFont(Activity, txtValuePropertyPurpose);
            FontUtils.setHomeplusRegularFont(Activity, txtValuePropertyPrice);
            FontUtils.setHomeplusBoldFont(Activity, txtValuePropertyType);
            FontUtils.setHomeplusBoldFont(Activity, txtValuePropertySpace);
            FontUtils.setHomeplusBoldFont(Activity, txtValuePropertyRegion);
            FontUtils.setHomeplusBoldFont(Activity, txtValuePropertyLocation);
            FontUtils.setHomeplusRegularFont(Activity, txtPropertyAddress);
            FontUtils.setHomeplusRegularFont(Activity, txtOwnerDetails);
            FontUtils.setHomeplusRegularFont(Activity, txt_propertylocation);
            FontUtils.setHomeplusRegularFont(Activity, txt_videogallery);
            FontUtils.setHomeplusRegularFont(Activity, txt_property_spec);
            FontUtils.setHomeplusRegularFont(Activity, txt_sponsors);
        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, txtDescription);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtValuePropertyPurpose);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtValuePropertyPrice);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtValuePropertyType);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtValuePropertySpace);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtValuePropertyRegion);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtValuePropertyLocation);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtPropertyAddress);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtOwnerDetails);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_propertylocation);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_videogallery);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_property_spec);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_sponsors);

        }
        //imgBtnSlideSponserPre.setOnClickListener(this);
        //imgBtnSlideSponserNext.setOnClickListener(this);
        relativeLayPropertyAddress.setOnClickListener(this);
        relativeLayOwnerDetails.setOnClickListener(this);
        relativeLaySharenow.setOnClickListener(this);
        imgViewVideoGalleryThumbNail = (ImageView) view.findViewById(R.id.imgViewVideoGalleryThumbNail);
        imgViewVideoGalleryThumbNail.setOnClickListener(this);
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {

            current_lattitude = gps.getLatitude();
            current_longitude = gps.getLongitude();
            Log.e("Location", "" + current_lattitude + "" + current_longitude);
          /*  currentLocation = gps.getLocation();
            if(currentLocation!=null)
                myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
*/

            //Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }


        viewPagerRealEstate = (ViewPager) view.findViewById(R.id.viewPagerRealEstate);
        viewPagerSponsers = (ViewPager) view.findViewById(R.id.viewPagerSponsers);


        indicator = (CirclePageIndicator) view.
                findViewById(R.id.circleIndicator);
        indicator.setRadius(1);
        //indicator_sponser = (CirclePageIndicator) view.
        //findViewById(R.id.circleIndicatorsponser);
        recycViewPropertySpec = (RecyclerView) view.findViewById(R.id.recycViewPropertySpec);

        mapViewRealEstate = (MapView) view.findViewById(R.id.mapViewRealEstate);


        //recycViewRealEstate.setHasFixedSize(true);
        recycViewPropertySpec.setLayoutManager(new LinearLayoutManager(Activity));
        //recycViewRealEstate.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //btnAdvanceSearch.setOnClickListener(this);
        // fetch Real Estate Data From Server
        if (Activity.isNetworkAvailable())
            fetchRelaEstateInnerData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void fetchRelaEstateInnerData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.PROPERTY_ID, propertyId);
            Log.e(TAG, "Json Request:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_REAL_ESTATE_INNER_DATA, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseRealEstateInnerJsonData(JSONObject jsonObject) {
        try {
            final JSONObject json = jsonObject;
            Log.e("Testinggggg", "" + jsonObject.getString(AppConstants.OWNER_EMAIL));
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {

                    name = jsonObject.getString(AppConstants.OWNER_NAME);
                    phone = jsonObject.getString(AppConstants.OWNER_MOBILE);
                    email = jsonObject.getString(AppConstants.OWNER_EMAIL);

                    property_block = jsonObject.getString(AppConstants.PROPERTY_ADDRESS1);
                    property_street = jsonObject.getString(AppConstants.PROPERTY_ADDRESS2);
                    property_address = jsonObject.getString(AppConstants.PROPERTY_ADDRESS3);
                    property_house = jsonObject.getString(AppConstants.PROPERTY_ADDRESS4);
                    android_link = jsonObject.getString(AppConstants.HOME_PLUS_ANDROID_LINK);
                    sb = new StringBuilder();
                    sb.append(jsonObject.getString(AppConstants.PROPERTY_ADDRESS1)).append(",").append(jsonObject.getString(AppConstants.PROPERTY_ADDRESS2)).append(",").
                            append(jsonObject.getString(AppConstants.PROPERTY_ADDRESS3));
                    // Slider for Real Estate
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.IMAGES);
                    if (jsonArray.length() > 0) {
                        realEstateBannerList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            realEstateBannerList.add(jsonArray.getString(i));
                            share_image = realEstateBannerList.get(0);
                        }
                        Log.e("image", ">>>>" + share_image);
                        highlightsImageAdapter = new HighlightsImageAdapter(Activity, realEstateBannerList);
                        viewPagerRealEstate.setAdapter(highlightsImageAdapter);
                        indicator.setViewPager(viewPagerRealEstate);
                        final float density = getResources().getDisplayMetrics().density;
                        indicator.setRadius(5 * density);
                        pageBanner = realEstateBannerList.size();
                        if (pageBanner == 1) {
                            indicator.setVisibility(View.GONE);
                        } else {
                            indicator.setVisibility(View.VISIBLE);
                            pageSwitcher(1);
                        }
                       /* final float density = getResources().getDisplayMetrics().density;
                        indicator.setRadius(5 * density);
                        NUM_PAGES = realEstateBannerList.size();
                        if(NUM_PAGES==1)
                        {
                            indicator.setVisibility(View.GONE);
                        }
                        else {
                            indicator.setVisibility(View.VISIBLE);
                        }
                            // Auto start of viewpager
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == NUM_PAGES) {
                                        currentPage = 0;
                                    }
                                    viewPagerRealEstate.setCurrentItem(currentPage++, true);
                                }
                            };
                             swipeTimer = new Timer();
                            swipeTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            },4000, 4000);*/
                        indicator.setOnPageChangeListener(this);


                    }
                    if (jsonObject.getString(AppConstants.PROPERTY_DETAILS) != null)
                        if (jsonObject.getString(AppConstants.PROPERTY_DETAILS).startsWith("<html"))
                            txtDescription.setText(Html.fromHtml(jsonObject.getString(AppConstants.PROPERTY_DETAILS).trim()));
                        else
                            txtDescription.setText(jsonObject.getString(AppConstants.PROPERTY_DETAILS).trim());

                    txtValuePropertyPurpose.setText(jsonObject.getString(AppConstants.PURPOSE));
                    share_prop_pup = jsonObject.getString(AppConstants.PURPOSE);
                    txtValuePropertyPrice.setText(jsonObject.getString(AppConstants.PRICE));
                    share_prop_price = jsonObject.getString(AppConstants.PRICE);
                    txtValuePropertyType.setText(getResources().getString(R.string.txt_property_type) + " " + jsonObject.getString(AppConstants.PROPERTY_TYPE));
                    txtValuePropertySpace.setText(getResources().getString(R.string.txt_property_space) + " " + jsonObject.getString(AppConstants.PROPERTY_SPACE));
                    txtValuePropertyRegion.setText(getResources().getString(R.string.txt_property_region) + " " + jsonObject.getString(AppConstants.REGION));
                    txtValuePropertyLocation.setText(getResources().getString(R.string.txt_property_location) + " " + jsonObject.getString(AppConstants.LOCATION));
                    region = jsonObject.getString(AppConstants.REGION);
                    //Video Gallery
                    videoUrl = jsonObject.getString(AppConstants.VIDEO_LINK);
                    if (videoUrl != null && !videoUrl.isEmpty() && !videoUrl.equals("null"))

                    {

                        Log.e(TAG, "hhhdddd:" + "gggg");
                        String videoId = GeneralUtils.getYouTubeVideoId(jsonObject.getString(AppConstants.VIDEO_LINK));
                        String thumbNailVideo = AppConstants.MAKE_THUMBNAIL_BASE_URL + videoId + AppConstants.MAKE_THUMBNAIL_DEFAULT;
                        Picasso.with(Activity).load(thumbNailVideo).resize(250, 250).into(imgViewVideoGalleryThumbNail);
//.placeholder(R.drawable.progress_animation)
                    } else {
                        String thumbNaildemo = jsonObject.getString(AppConstants.VIDEO_COVER_IMAGE);
                        Picasso.with(Activity).load(thumbNaildemo).resize(250, 250).into(imgViewVideoGalleryThumbNail);

                    }
                    // Property Specification List Contents
                    JSONArray jsonArrayProperySpec = jsonObject.getJSONArray(AppConstants.PROPERTY_SPEC_ARRAY);
                    if (jsonArrayProperySpec.length() > 0) {
                        realEstatePropertSpecList = new ArrayList<>();
                        for (int i = 0; i < jsonArrayProperySpec.length(); i++)
                            realEstatePropertSpecList.add(jsonArrayProperySpec.getString(i));
                        mAdapter = new RealEstatePropertySpecListAdapter(Activity, realEstatePropertSpecList);
                        recycViewPropertySpec.setAdapter(mAdapter);

                    } else {

                    }
                    // Sponser Slider
                    JSONArray jsonArraySponser = jsonObject.getJSONArray(AppConstants.SPONSERS);
                    if (jsonArraySponser.length() > 0) {
                        realEstateSponsersList = new ArrayList<>();
                        for (int i = 0; i < jsonArraySponser.length(); i++)
                            realEstateSponsersList.add(jsonArraySponser.getJSONObject(i).getString(AppConstants.IMAGE));

                        highlightsImageAdaptersp = new HighlightsImageAdaptersponsers(Activity, realEstateSponsersList);
                        viewPagerSponsers.setAdapter(highlightsImageAdaptersp);
                        pageSwitcherSponser(4);
                        NUM_PAGES = realEstateSponsersList.size();
                        // Auto start of viewpager

/*
                        indicator_sponser.setViewPager(viewPagerSponsers);
                        final float density = getResources().getDisplayMetrics().density;
                        indicator_sponser.setRadius(5 * density);
                        NUM_PAGES = realEstateSponsersList.size();
                        // Auto start of viewpager
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == NUM_PAGES) {
                                    currentPage = 0;
                                }
                                viewPagerSponsers.setCurrentItem(currentPage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, 3000, 3000);
                        indicator_sponser.setOnPageChangeListener(this);*/
                    }

                    //Google Map
                   /* latitude = Double.parseDouble(json.getString(AppConstants.LATITUDE));
                    longitude = Double.parseDouble(json.getString(AppConstants.LONGITUDE));*/
                    latitude = Double.parseDouble(json.getString(AppConstants.LATITUDE));
                    longitude = Double.parseDouble(json.getString(AppConstants.LONGITUDE));
                    Log.e(TAG, "langlong???>>>>" + json.getString(AppConstants.LATITUDE) + "," + json.getString(AppConstants.LONGITUDE));
                    LatLng location = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(location).title(jsonObject.getString(AppConstants.REGION)).snippet("Marker Description"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("http://maps.google.com/maps?saddr=" + gps.getLatitude() + ","
                                            + gps.getLongitude()
                                            + "&daddr="
                                            + latitude + "," + longitude + "&dirflg=d"));
                            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                            startActivity(intent);


                            return false;
                        }
                    });

                } else {

                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void pageSwitcherSponser(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTaskSponser(), 0, seconds * 1000); // delay
    }

    // this is an inner class...
    class RemindTaskSponser extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.

            Activity.runOnUiThread(new Runnable() {
                public void run() {

                    if (pageSponser > getPageCount(realEstateSponsersList.size()) - 1) { // In my case the number of pages are 5
                        pageSponser = 0;
                        Log.d(TAG, "pager" + pageSponser);
                    }
                    viewPagerSponsers.setCurrentItem(pageSponser++);

                }
            });

        }
    }

    public int getPageCount(int count) {
        int pageCount, reminder;
        pageCount = count / 3;
        reminder = count % 3;
        if (reminder > 0) {
            pageCount = pageCount + 1;
        }
        Log.e("PAGE COUNT", ">>>>>>>>>>>>>>>" + pageCount);
        return pageCount;
    }

    @Override
    public void onRealEstateDetailsDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onRealEstateDetailsDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseRealEstateInnerJsonData(jsonObject);
    }

    @Override
    public void onRealEstateDetailsDataLoadedFailed(JSONObject jsonObject) {
        super.onRealEstateDetailsDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdvanceSearch:
                //sendRegistrationData();
                break;
           /* case R.id.imgBtnSlideNext:
                if (viewPagerRealEstate.getCurrentItem() < realEstateBannerList.size()) {
                    viewPagerRealEstate.setCurrentItem(viewPagerRealEstate.getCurrentItem() + 1);
                }
                break;
           case R.id.imgBtnSlidePrevious:
                if (viewPagerRealEstate.getCurrentItem() > 0) {
                    viewPagerRealEstate.setCurrentItem(viewPagerRealEstate.getCurrentItem() - 1);
                }
                break;*/
           /* case R.id.imgBtnSlideSponserNext:
                if (viewPagerSponsers.getCurrentItem() < realEstateSponsersList.size()) {
                    viewPagerSponsers.setCurrentItem(viewPagerSponsers.getCurrentItem() + 1);
                }
                break;
            case R.id.imgBtnSlideSponserPre:
                if (viewPagerSponsers.getCurrentItem() > 0) {
                    viewPagerSponsers.setCurrentItem(viewPagerSponsers.getCurrentItem() - 1);
                }
                break;*/
            case R.id.imgViewVideoGalleryThumbNail:
                if (videoUrl != null || !videoUrl.equals("") || !videoUrl.equals("null")) {
                    Log.e("VLINk", "ss" + videoUrl);
                    Intent intent = new Intent(Activity, VideoActivity.class);
                    intent.putExtra(AppConstants.VIDEO_URL, GeneralUtils.getYouTubeVideoId(videoUrl));
                    Activity.startActivity(intent);
                }
                break;
            case R.id.relativeLayOwnerDetails:
                FragmentTransaction ft = ((HomePlusBaseActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                RealEstPrptyAdressOwnerDetailsDialogFrag dialogFrag = RealEstPrptyAdressOwnerDetailsDialogFrag.newInstance(name, phone, email, "", true);
                dialogFrag.show(ft, "MydialogFrag");
                break;
            case R.id.relativeLayPropertyAddress:
               /* FragmentTransaction ftt = ((HomePlusBaseActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                RealEstPrptyAdressOwnerDetailsDialogFrag dialogFragg = RealEstPrptyAdressOwnerDetailsDialogFrag.newInstance("", "", "", sb.toString(), false);
                dialogFragg.show(ftt, "MydialogFrag");*/
                FragmentTransaction ftt = ((HomePlusBaseActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                RealEstPrptyAdressDialogFrag dialogFragg = RealEstPrptyAdressDialogFrag.newInstance(region, property_block, property_street, property_address, property_house, false);
                dialogFragg.show(ftt, "MydialogFrag");
                break;
            case R.id.relativeLayShareNow:
                // shareIt();

                ShareItNew();
                //Activity.pushFragments(new ShareAppFragment(),false,true);
                // Activity.pushFragments(new MediaCenterVideoGalleryFrag(), false, true);

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
            sAux = sAux + android_link;
            getBitmap();
            String path = MediaStore.Images.Media.insertImage(Activity.getContentResolver(), b, "", null);
            Uri screenshotUri = Uri.parse(path);

            File file = getSlectedBytesArrayForTwitter(b);
            if (file != null) {
                Log.e("Instagram FILEBNOTNULL", "Instagram button Clicked");
                Uri uri = Uri.fromFile(getSlectedBytesArrayForTwitter(b));
                i.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.txt_purpose) + ":" + "  " + share_prop_pup + "    " + getResources().getString(R.string.price) + ":" + "" + share_prop_price);
                i.putExtra(Intent.EXTRA_STREAM, screenshotUri);

            }
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
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

    private void shareIt() {
        Uri imageUri = Uri.parse(share_image);
        //sharing implementation here
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
      /*  sharingIntent.setType("text/plain");*/
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "HomePlus");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Purpose :" + share_prop_pup + "Price :" + share_prop_price);
        sharingIntent.setType("image/jpeg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onPause() {
        super.onPause();
        //mapViewRealEstate.onPause();
    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
       mapViewRealEstate.onDestroy();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("Mapoview>>>", mapViewRealEstate + "");
//    mapViewRealEstate.onDestroy();
    }
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        //mapViewRealEstate.onLowMemory();
//    }

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

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 5000); // delay
        // in
        // milliseconds
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.

            Activity.runOnUiThread(new Runnable() {
                public void run() {

                    if (pageBanner > realEstateBannerList.size()) { // In my case the number of pages are 5
                        pageBanner = 0;
                        Log.d(TAG, "pager" + pageBanner);
                    }
                    viewPagerRealEstate.setCurrentItem(pageBanner++);
                    Log.e("BannerList", ">>>>>>>>>>" + viewPagerRealEstate.getCurrentItem());

                }
            });

        }
    }

}
