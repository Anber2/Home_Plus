package com.mawaqaa.homepluskw_new.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
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

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.activity.VideoActivity;
import com.mawaqaa.homepluskw_new.adapter.ExperienceDetailsListAdapter;
import com.mawaqaa.homepluskw_new.adapter.HighlightsImageAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.BitmapUtil;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
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
 * Created by JijoCJ on 11/17/2016.
 */
public class ConstrctnIndividualDetailsFrag extends HomePlusBaseFragment implements RatingBar.OnRatingBarChangeListener,View.OnClickListener,ViewPager.OnPageChangeListener{
    boolean isFirstTime=true;
    private static final String TAG = "CnstrtnIndvdlDetailFrag";
    RelativeLayout relativeLaySharenow_individual;
    TextView txtPhoneNoValue, txtEmailValue, txtNationalityValue,text_idividual_head,txt_youcan_ratenow,
    txt_service_details,txt_experience_details,txt_video_gallery;
    ImageButton imgBtnSlidePrevious, imgBtnSlideNext;
    ImageView imgViewIndividual, imgViewVideoThumbNail;
    CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    LinearLayout linearLayServiceDetails, linearLayExperienceDetails, linearLayRateAction;

    RecyclerView recycViewServiceDetails, recycViewExpernceDetails;
    TextView txtPhoneNo,txtEmail,txtNationality,txtRating;
    List<String> serviceList;
    List<String> experienceList;
    List<String> imageBannerList;
    HighlightsImageAdapter mImageAdapter;
    ExperienceDetailsListAdapter mExperienceListAdapter;
    ViewPager viewPagerInContrctnDetails;
    RatingBar ratingBarInIndivdualDetails, ratingBarYour;
    String constructionInnerId;
    String videoUrl,share_image="",email,phoneno,android_link;
    boolean isFromOffers;
    private Bitmap b;

    public static ConstrctnIndividualDetailsFrag newInstance(String constructionInnerId, boolean isFromOffers){

        ConstrctnIndividualDetailsFrag individualDetailsFrag = new ConstrctnIndividualDetailsFrag();
        individualDetailsFrag.constructionInnerId = constructionInnerId;
        individualDetailsFrag.isFromOffers = isFromOffers;
        return individualDetailsFrag;
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
        View view = inflater.inflate(R.layout.fragment_construction_indivdal_details, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        if(!isFromOffers) {
            ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_construction_title_action_bar));
            buttonClickState.constructionButtonState();
        }
        else {
            ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_special_offer_title_action_bar));
            buttonClickState.specialOfferButtonState();
        }

        initView(view);
        return view;
    }

    private void initView(View view) {
        text_idividual_head = (TextView) view.findViewById(R.id.text_idividual_head);
        txtPhoneNoValue = (TextView) view.findViewById(R.id.txtPhoneNoValue);
        txtPhoneNoValue.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        txtPhoneNoValue.setLinkTextColor(Color.BLUE);
        //Linkify.addLinks(txtPhoneNoValue,Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);

        txtEmailValue = (TextView) view.findViewById(R.id.txtEmailValue);
        txtEmailValue.setLinkTextColor(Color.BLUE);
        Linkify.addLinks(txtEmailValue,Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES);
        txtNationalityValue = (TextView) view.findViewById(R.id.txtNationalityValue);
        txtPhoneNo = (TextView) view.findViewById(R.id.txtPhoneNo);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtEmail.setLinkTextColor(Color.BLUE);
        txtNationality = (TextView) view.findViewById(R.id.txtNationality);
        txtRating = (TextView) view.findViewById(R.id.txtRating);
        txt_youcan_ratenow=(TextView)view.findViewById(R.id.youcan_ratenow);
        txt_service_details=(TextView)view.findViewById(R.id.servicedetails);
        txt_experience_details=(TextView)view.findViewById(R.id.experience_details);
        txt_video_gallery=(TextView)view.findViewById(R.id.videogallery);
        relativeLaySharenow_individual=(RelativeLayout)view.findViewById(R.id.relativeLaySharenow_individual);
        relativeLaySharenow_individual.setOnClickListener(this);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {

            FontUtils.setHomeplusRegularFont(Activity, text_idividual_head);

            FontUtils.setHomeplusRegularFont(Activity, txtPhoneNoValue);

            FontUtils.setHomeplusRegularFont(Activity, txtEmailValue);

            FontUtils.setHomeplusRegularFont(Activity, txtNationalityValue);

            FontUtils.setHomeplusRegularFont(Activity, txtPhoneNo);

            FontUtils.setHomeplusRegularFont(Activity, txtEmail);

            FontUtils.setHomeplusRegularFont(Activity, txtNationality);

            FontUtils.setHomeplusRegularFont(Activity, txtRating);
            FontUtils.setHomeplusRegularFont(Activity, txt_youcan_ratenow);
            FontUtils.setHomeplusRegularFont(Activity, txt_service_details);
            FontUtils.setHomeplusRegularFont(Activity, txt_experience_details);
            FontUtils.setHomeplusRegularFont(Activity, txt_video_gallery);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity, text_idividual_head);

            FontUtils.setHomeplusRegularFont(Activity, txtPhoneNoValue);

            FontUtils.setHomeplusArabicRegularFont(Activity, txtEmailValue);

            FontUtils.setHomeplusArabicRegularFont(Activity, txtNationalityValue);

            FontUtils.setHomeplusArabicRegularFont(Activity, txtPhoneNo);

            FontUtils.setHomeplusArabicRegularFont(Activity, txtEmail);

            FontUtils.setHomeplusArabicRegularFont(Activity, txtNationality);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtRating);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_experience_details);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_youcan_ratenow);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_service_details);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_experience_details);
            FontUtils.setHomeplusArabicRegularFont(Activity, txt_video_gallery);



        }
        txtPhoneNoValue.setSelected(true);
        txtEmailValue.setSelected(true);
        txtNationalityValue.setSelected(true);

       // imgBtnSlideNext = (ImageButton) view.findViewById(R.id.imgBtnSlideNext);
       // imgBtnSlidePrevious = (ImageButton) view.findViewById(R.id.imgBtnSlidePrevious);

        imgViewIndividual = (ImageView) view.findViewById(R.id.imgViewIndividual);

        imgViewVideoThumbNail = (ImageView) view.findViewById(R.id.imgViewVideoThumbNail);

        imgViewVideoThumbNail.setOnClickListener(this);

        viewPagerInContrctnDetails = (ViewPager) view.findViewById(R.id.viewPagerInContrctnDetails);
        indicator = (CirclePageIndicator)view.
                findViewById(R.id.circleIndicator);
        indicator.setRadius(1);
        ratingBarInIndivdualDetails = (RatingBar) view.findViewById(R.id.ratingBarInIndivdualDetails);
        ratingBarYour = (RatingBar) view.findViewById(R.id.ratingBarYour);
        ratingBarYour.setOnRatingBarChangeListener(this);

        recycViewServiceDetails = (RecyclerView) view.findViewById(R.id.recycViewServiceDetails);
        recycViewExpernceDetails = (RecyclerView) view.findViewById(R.id.recycViewExpernceDetails);
        //recycViewRealEstate.setHasFixedSize(true);
        recycViewServiceDetails.setLayoutManager(new LinearLayoutManager(Activity));
        recycViewExpernceDetails.setLayoutManager(new LinearLayoutManager(Activity));
        //recycViewRealEstate.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        linearLayServiceDetails = (LinearLayout) view.findViewById(R.id.linearLayServiceDetails);
        linearLayExperienceDetails = (LinearLayout) view.findViewById(R.id.linearLayExperienceDetails);
        linearLayRateAction = (LinearLayout) view.findViewById(R.id.linearLayRateAction);

        //imgBtnSlideNext.setOnClickListener(this);
        //imgBtnSlidePrevious.setOnClickListener(this);

        if(!PreferenceUtil.getUserId(Activity).equals(""))
            linearLayRateAction.setVisibility(View.VISIBLE);
        else
            linearLayRateAction.setVisibility(View.GONE);

        // fetch Real Estate Data From Server
        if(Activity.isNetworkAvailable())
            fetchConstrctnIndvdlInnerDetailsData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void fetchConstrctnIndvdlInnerDetailsData() {
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
                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_INDIVIDUAL_DETAILS, jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseConstrctnIndividualDetailsData(JSONObject jsonObject) {
        try{
            if(jsonObject != null){
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)){
                    android_link=jsonObject.getString(AppConstants.HOME_PLUS_ANDROID_LINK);
                    email=jsonObject.getString(AppConstants.EMAIL_CONSTRUCTION);
                    phoneno=jsonObject.getString(AppConstants.PHONE_NUMBER);
                    ratingBarInIndivdualDetails.setRating(Float.parseFloat(jsonObject.getString(AppConstants.AVERAGE_RATING)));
                    txtEmailValue.setText(jsonObject.getString(AppConstants.EMAIL_CONSTRUCTION));

                    txtNationalityValue.setText(jsonObject.getString(AppConstants.NATIONALITY));
                    txtPhoneNoValue.setText(jsonObject.getString(AppConstants.PHONE_NUMBER));

                    //
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

                    JSONArray jsonImagesArray = jsonObject.getJSONArray(AppConstants.IMAGES);
                    if(jsonImagesArray.length() > 0){
                        imageBannerList = new ArrayList<>();
                        for (int j = 0; j < jsonImagesArray.length(); j++){
                            imageBannerList.add(jsonImagesArray.getString(j));
                            share_image=imageBannerList.get(0);
                        }
                        mImageAdapter = new HighlightsImageAdapter(Activity, imageBannerList);
                        viewPagerInContrctnDetails.setAdapter(mImageAdapter);

                        indicator.setViewPager(viewPagerInContrctnDetails);
                        final float density = getResources().getDisplayMetrics().density;
                        indicator.setRadius(5 * density);
                        NUM_PAGES =imageBannerList.size();
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
                                viewPagerInContrctnDetails.setCurrentItem(currentPage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, 4000, 4000);
                        indicator.setOnPageChangeListener(this);
                    }


                    videoUrl = jsonObject.getString(AppConstants.VIDEO_URL);


                    if(videoUrl!=null && !videoUrl.isEmpty() && !videoUrl.equals("null"))

                    {
                        String videoID = GeneralUtils.getYouTubeVideoId(jsonObject.getString(AppConstants.VIDEO_URL));
                        String videoThumbUrl = AppConstants.MAKE_THUMBNAIL_BASE_URL + videoID + AppConstants.MAKE_THUMBNAIL_DEFAULT;
                        Log.e(TAG, "ThumbNail Video:" + videoThumbUrl);
                        Picasso.with(Activity).load(videoThumbUrl).into(imgViewVideoThumbNail);
                    }
                    else {
                        String thumbNaildemo = jsonObject.getString(AppConstants.VIDEO_THUMBNAIL);
                        Picasso.with(Activity).load(thumbNaildemo).into(imgViewVideoThumbNail);
                        Log.e(TAG, "ThumbNail Video:" + thumbNaildemo);
                    }
                    //mImageAdapter = new HighlightsImageAdapter()


                }else{

                }
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConstructionIndividualInnerDetailsDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onConstructionIndividualInnerDetailsDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseConstrctnIndividualDetailsData(jsonObject);
    }


    @Override
    public void onConstructionIndividualInnerDetailsDataLoadedFailed(JSONObject jsonObject) {
        super.onConstructionIndividualInnerDetailsDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBtnSlideNext:
                if (viewPagerInContrctnDetails.getCurrentItem() < imageBannerList.size()) {
                    viewPagerInContrctnDetails.setCurrentItem(viewPagerInContrctnDetails.getCurrentItem() + 1);
                }
                break;
            case R.id.imgBtnSlidePrevious:
                if (viewPagerInContrctnDetails.getCurrentItem() > 0) {
                    viewPagerInContrctnDetails.setCurrentItem(viewPagerInContrctnDetails.getCurrentItem() - 1);
                }
                break;
            case R.id.imgViewVideoThumbNail:
                if(videoUrl!=null || !videoUrl.equals("") || !videoUrl.equals("null")) {
                    Log.e("VLINk","ss"+videoUrl);
                    Intent intent = new Intent(Activity, VideoActivity.class);
                    intent.putExtra(AppConstants.VIDEO_URL, GeneralUtils.getYouTubeVideoId(videoUrl));
                    Activity.startActivity(intent);
                }
               /* Intent intent = new Intent(Activity, VideoActivity.class);
                intent.putExtra(AppConstants.VIDEO_URL, GeneralUtils.getYouTubeVideoId(videoUrl));
                Activity.startActivity(intent);*/
                break;
            case R.id.relativeLaySharenow_individual:
                ShareItNew();
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
                i.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_email) + ":" + "  " + email + "    " + getResources().getString(R.string.ph_no) + ":" + "" + phoneno);
                i.putExtra(Intent.EXTRA_STREAM,screenshotUri);

            }
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
       /* try {
            Intent i = new Intent(Intent.ACTION_SEND);

            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "HomePlus");
            String sAux = "\nLet me recommend you this application\n\n";
            //sAux = sAux + "http://www.homepluskw.com/ar/Constructions/Individual/Individual-List/Individual-Details?Id="+constructionInnerId;
            sAux = sAux +android_link;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }*/


/*
        try {

            Log.e("Instagram Error", "Instagram button Clicked");
            getBitmap();

            // Log.e("Bitmnn",""+b.toString());
            String path = MediaStore.Images.Media.insertImage(Activity.getContentResolver(), b, "", null);
            //String path = MediaStore.Images.Media.insertImage(Activity.getContentResolver(),share_image,"",null);

            Uri screenshotUri = Uri.parse(path);

            File file = getSlectedBytesArrayForTwitter(b);
            if (file != null) {
                Log.e("Instagram FILEBNOTNULL", "Instagram button Clicked");
                Uri uri = Uri.fromFile(getSlectedBytesArrayForTwitter(b));
                //Uri imageUri= Uri.parse(share_image);

                Intent shareIntent = new Intent(
                        Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.setType("image*//*
*/
/**//*
*/
/*");

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "HomePlus");
                //shareIntent.putExtra(Intent.EXTRA_TEXT,getResources().getString(R.string.share_email)+"  "+":"+email);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_email)+":"+"  "+email + "    " +getResources().getString(R.string.ph_no)+":"+""+phoneno);
                //shareIntent.putExtra(Intent.EXTRA_TEXT,getResources().getString(R.string.ph_no)+"  "+":"+phoneno);
                shareIntent.putExtra(Intent.EXTRA_STREAM,screenshotUri);
                String sAux = "\nLet me recommend you this application\n\n";
                //sAux = sAux + "http://www.homepluskw.com/ar/Constructions/Individual/Individual-List/Individual-Details?Id="+constructionInnerId;
                sAux = sAux +android_link;
                shareIntent.putExtra(Intent.EXTRA_TEXT, sAux);
                //shareIntent.setPackage(getString(R.string.inst_package));
                //startActivity(shareIntent);
                startActivity(Intent.createChooser(shareIntent, "Share link!"));
            } else {
                Log.e("Instagram Error", "Image not found");
                Toast.makeText(getActivity(), "No Image ",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (ActivityNotFoundException e) {
            Log.e("Instagram Error", "Instagram App not found");
            Toast.makeText(getActivity(), "An error Occured",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Log.e("Instagram Error", "Exception Catched");
            e.printStackTrace();

        }
*/

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
       // txtRatingValue.setText(String.valueOf(rating));
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
            jsonObject.putOpt(AppConstants.SERVICETYPE,"i");
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
        BaseApplication.getInstance().progressOFF();
    }
}
