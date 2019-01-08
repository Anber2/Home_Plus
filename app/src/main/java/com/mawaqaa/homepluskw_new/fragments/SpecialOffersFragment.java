package com.mawaqaa.homepluskw_new.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.SpecialOfferConstrctnAdapter;
import com.mawaqaa.homepluskw_new.adapter.SpecialOfferRealEstateAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.SpecialOfferRealEstateData;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.views.CirclePageIndicator;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JijoCJ on 12/2/2016.
 */
public class SpecialOffersFragment extends HomePlusBaseFragment implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private static final String TAG = "SpecialOffersFragment";
    CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    //RecyclerView homePageViewaPAger;
    ViewPager recyViewRealEstateOffer;
    RecyclerView recycViewContrctnOffer;
    ImageView specialofferimagenew;

    List<SpecialOfferRealEstateData> specialOfferRealEstateList;
   // List<SpecialOfferConstructionData> specialOfferConstrtnList;
    List<SpecialOfferRealEstateData> specialOfferConstrtnList;

    SpecialOfferRealEstateAdapter mAdapter;
    SpecialOfferConstrctnAdapter mConstnAdapter;
    LinearLayoutManager mLinearLayoutManager;
    LinearLayout layoutoneTop,layoutsecond,layoutthird;
    TextView txtregion,txtpurpose,textType,textDescription,txtTitle,txtdate,offer_purpose,offer_region,offer_propertytype;
    String id;
    ImageView specialofferimagetwo;
    RelativeLayout rllayoutspecialoffer;
    boolean isFromOffers;
    public static SpecialOffersFragment newInstance(String constructionInnerId, boolean isFromOffers){

        SpecialOffersFragment specialofferFrag = new SpecialOffersFragment();
        specialofferFrag.id = constructionInnerId;
        specialofferFrag.isFromOffers = isFromOffers;
        return specialofferFrag;
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
        View view = inflater.inflate(R.layout.fragment_special_offer, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_special_offer_title_action_bar));
        initView(view);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.specialOfferButtonState();
        return view;
    }

    private void initView(View view) {
       // offer_purpose=(TextView)view.findViewById(R.id.offer_purpose) ;
        //offer_region=(TextView)view.findViewById(R.id.offer_region) ;
       // offer_propertytype=(TextView)view.findViewById(R.id.offer_propertytype) ;
        rllayoutspecialoffer=(RelativeLayout)view.findViewById(R.id.rllayoutspecialoffer);
        txtTitle=(TextView)view.findViewById(R.id.txtTitlespoffer);
        txtdate=(TextView)view.findViewById(R.id.txtDatespoffer);
        txtdate.setSelected(true);
        specialofferimagenew=(ImageView)view.findViewById(R.id.specialofferimagetwo);
        layoutoneTop=(LinearLayout)view.findViewById(R.id.layouttop_spoffer) ;
        layoutsecond=(LinearLayout)view.findViewById(R.id.linearllout_spoffer) ;
        layoutthird=(LinearLayout)view.findViewById(R.id.lineralloutthird) ;
        txtregion=(TextView)view.findViewById(R.id.spofferone);
        txtregion.setSelected(true);
        txtpurpose=(TextView)view.findViewById(R.id.spoffertwo) ;
        txtpurpose.setSelected(true);
        /*textType=(TextView)view.findViewById(R.id.spofferthree) ;
        textType.setSelected(true);*/
        textDescription=(TextView)view.findViewById(R.id.spofferfour) ;
        textDescription.setSelected(true);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(Activity,txtTitle);
            FontUtils.setHomeplusRegularFont(Activity,txtdate);
            FontUtils.setHomeplusRegularFont(Activity,txtregion);
            FontUtils.setHomeplusRegularFont(Activity,txtpurpose);
            FontUtils.setHomeplusRegularFont(Activity,textType);
            FontUtils.setHomeplusRegularFont(Activity,textDescription);
            FontUtils.setHomeplusRegularFont(Activity,offer_purpose);
            FontUtils.setHomeplusRegularFont(Activity,offer_region);
            FontUtils.setHomeplusRegularFont(Activity,offer_propertytype);

        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity,txtTitle);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtdate);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtregion);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtpurpose);
            FontUtils.setHomeplusArabicRegularFont(Activity,textType);
            FontUtils.setHomeplusArabicRegularFont(Activity,textDescription);
            FontUtils.setHomeplusArabicRegularFont(Activity,offer_purpose);
            FontUtils.setHomeplusArabicRegularFont(Activity,offer_region);
            FontUtils.setHomeplusArabicRegularFont(Activity,offer_propertytype);
        }
        //homePageViewaPAger = (RecyclerView) view.findViewById(R.id.homePageViewaPAger);
        recyViewRealEstateOffer = (ViewPager) view.findViewById(R.id.recyViewRealEstateOffer);
        recycViewContrctnOffer = (RecyclerView) view.findViewById(R.id.recycViewContrctnOffer);

        //homePageViewaPAger.setNestedScrollingEnabled(false);
        indicator = (CirclePageIndicator)view.
                findViewById(R.id.circleIndicator);
        indicator.setRadius(1);
        //recycViewRealEstate.setHasFixedSize(true);
      //mLinearLayoutManager = new LinearLayoutManager(Activity, LinearLayoutManager.HORIZONTAL, false);
      // homePageViewaPAger.setLayoutManager(mLinearLayoutManager);
        //mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycViewContrctnOffer.setLayoutManager(new GridLayoutManager(Activity, 2));
        specialofferimagenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecialOfferRealEstateData specialOfferConstructionData=specialOfferRealEstateList.get(1);
                Log.e("getid",""+specialOfferConstructionData.getId());
                if(specialOfferConstructionData.getType().equals("C")) {
                    Activity.pushFragments(ConstrctnCorporateDetailsFrag.newInstance(specialOfferConstructionData.getId(), true), false, true);
                }
                else if(specialOfferConstructionData.getType().equals("I")) {
                    Activity.pushFragments(ConstrctnIndividualDetailsFrag.newInstance(specialOfferConstructionData.getId(), true), false, true);
                }
                else
                {
                    Activity.pushFragments(RealEstateDeatailsInnerFragment.newInstance(specialOfferConstructionData.getId(), true), false, true);
                }
            }
        });
       // recycViewContrctnOffer.setLayoutManager(new WrappingLinearLayoutManager(Activity));
       // recycViewContrctnOffer.setNestedScrollingEnabled(true);
        //recycViewContrctnOffer.setHasFixedSize(true);

        // fetch Real Estate Data From Server
        if(Activity.isNetworkAvailable())
            fetchSpecialOfferData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void fetchSpecialOfferData() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            Log.e(TAG, "Json Request:"+jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if(VolleyUtils.volleyEnabled){
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_SPECIAL_OFFER, jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseSpecialOfferJsonData(JSONObject jsonObject) {
        try{
            if(jsonObject != null){
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)){
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.SPECIAL_OFFER_REAL_ESTATE_ALLOFFERS);
                    if(jsonArray.length()>0){
                        specialOfferRealEstateList =  new ArrayList<>();
                        for(int i = 0; i < jsonArray.length(); i++){
                            specialOfferRealEstateList.add(new SpecialOfferRealEstateData(jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_DESCRIPTION), jsonArray.getJSONObject(i).getString(AppConstants.IMAGE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_DATE), jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_TITLE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_PURPOSE),jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_REGION),
                                    jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_TYPE),jsonArray.getJSONObject(i).getString(AppConstants.SPECIALOFFER_PROPERTYTYPE)));

                        }
                        setSubSpecialoffer(specialOfferRealEstateList);
                        mAdapter = new SpecialOfferRealEstateAdapter(Activity, specialOfferRealEstateList);
                        recyViewRealEstateOffer.setAdapter(mAdapter);
                        /*indicator.setViewPager(recyViewRealEstateOffer);

                        final float density = getResources().getDisplayMetrics().density;
                        indicator.setRadius(5 * density);
                        NUM_PAGES =specialOfferRealEstateList.size();
                        // Auto start of viewpager
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == NUM_PAGES) {
                                    currentPage = 0;
                                }
                                recyViewRealEstateOffer.setCurrentItem(currentPage++, true);
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
*/
                            mAdapter.setOnItemClickListener(new SpecialOfferRealEstateAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                SpecialOfferRealEstateData specialOfferRealEstateData = specialOfferRealEstateList.get(position);
                                if(specialOfferRealEstateData.getType().equals("C")) {
                                    Activity.pushFragments(ConstrctnCorporateDetailsFrag.newInstance(specialOfferRealEstateData.getId(), true), false, true);
                                }
                                else if(specialOfferRealEstateData.getType().equals("I")) {
                                    Activity.pushFragments(ConstrctnIndividualDetailsFrag.newInstance(specialOfferRealEstateData.getId(), true), false, true);
                                }
                                else
                                {
                                    Activity.pushFragments(RealEstateDeatailsInnerFragment.newInstance(specialOfferRealEstateData.getId(), true), false, true);
                                }

                                //Activity.pushFragments(RealEstateDeatailsInnerFragment.newInstance(specialOfferRealEstateData.getId(), true), false, true);

                            }
                        });
                    }

                    JSONArray jsonArray1 = jsonObject.getJSONArray(AppConstants.SPECIAL_OFFER_REAL_ESTATE_ALLOFFERS);
                    if(jsonArray1.length() > 2){
                        specialOfferConstrtnList = new ArrayList<>();
                        for(int i = 2; i < jsonArray1.length(); i++){
                            specialOfferConstrtnList.add(new SpecialOfferRealEstateData(jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_DESCRIPTION), jsonArray.getJSONObject(i).getString(AppConstants.IMAGE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_DATE), jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_TITLE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_PURPOSE),jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_REGION),
                                    jsonArray.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_TYPE),jsonArray.getJSONObject(i).getString(AppConstants.SPECIALOFFER_PROPERTYTYPE)));
                           /* specialOfferConstrtnList.add(new SpecialOfferConstructionData(jsonArray1.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_ID),
                                    jsonArray1.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_DESCRIPTION), jsonArray1.getJSONObject(i).getString(AppConstants.IMAGE),
                                    jsonArray1.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_DATE), jsonArray1.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_TITLE),
                                    jsonArray1.getJSONObject(i).getString(AppConstants.SPECIAL_OFFER_TYPE)));*/

                        }
                        mConstnAdapter = new SpecialOfferConstrctnAdapter(Activity,specialOfferConstrtnList);
                        recycViewContrctnOffer.setAdapter(mConstnAdapter);
                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
                        Log.e(TAG,"heighgt"+height);
                        Log.e(TAG,"khls"+convertDpToPixel(250,Activity));
                        if(PreferenceUtil.getLanguage(Activity).equals("ar"))
                        {
                            ViewGroup.LayoutParams params1=recycViewContrctnOffer.getLayoutParams();
                           // params1.height= (int) (convertDpToPixel(250,Activity)*jsonArray1.length()/2);
                            params1.height= (int) (convertDpToPixel(180,Activity)*jsonArray1.length()/2);
//                            params1.height= (400*jsonArray1.length());

                            recycViewContrctnOffer.setLayoutParams(params1);
                            recycViewContrctnOffer.setNestedScrollingEnabled(false);
                        }
                        else {
                            ViewGroup.LayoutParams params = recycViewContrctnOffer.getLayoutParams();
                           // params.height = (int) (convertDpToPixel(250,Activity) * jsonArray1.length()/2);
                            params.height = (int) (convertDpToPixel(210,Activity) * jsonArray1.length()/2);
//                            params.height= (400*jsonArray1.length());
                            recycViewContrctnOffer.setLayoutParams(params);
                            recycViewContrctnOffer.setNestedScrollingEnabled(false);
                        }
                       mConstnAdapter.setOnItemClickListener(new SpecialOfferConstrctnAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //SpecialOfferConstructionData specialOfferConstructionData = specialOfferConstrtnList.get(position);
                                SpecialOfferRealEstateData specialOfferConstructionData=specialOfferConstrtnList.get(position);
                                if(specialOfferConstructionData.getType().equals("C")) {
                                    Activity.pushFragments(ConstrctnCorporateDetailsFrag.newInstance(specialOfferConstructionData.getId(), true), false, true);
                                }
                                else if(specialOfferConstructionData.getType().equals("I")) {
                                    Activity.pushFragments(ConstrctnIndividualDetailsFrag.newInstance(specialOfferConstructionData.getId(), true), false, true);
                                }
                                else
                                {
                                    Activity.pushFragments(RealEstateDeatailsInnerFragment.newInstance(specialOfferConstructionData.getId(), true), false, true);
                                }
                            }
                        });
                    }
                }else{

                }
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static float convertDpToPixel(float dp, HomePlusBaseActivity context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
    private void setSubSpecialoffer(List<SpecialOfferRealEstateData> specialOfferRealEstateList) {
        if(specialOfferRealEstateList.size()>1)
        {

            Picasso.with(Activity).load(specialOfferRealEstateList.get(1).getImageUrl()).resize(400, 400).into(specialofferimagenew);


//            animation = AnimationUtils.loadAnimation(getApplicationContext(),
//                    R.anim.rotate);
//            animation.setAnimationListener(Activity);
//            specialofferimagenew.startAnimation(animation);

            txtTitle.setText(specialOfferRealEstateList.get(1).getTitle());
            txtdate.setText(specialOfferRealEstateList.get(1).getOfferDate());
            if(specialOfferRealEstateList.get(1).getType().equals("R"))
            {
                rllayoutspecialoffer.setVisibility(View.VISIBLE);
                layoutoneTop.setVisibility(View.VISIBLE);
                layoutsecond.setVisibility(View.VISIBLE);
                txtregion.setText(specialOfferRealEstateList.get(1).getPurpose());
                txtpurpose.setText(specialOfferRealEstateList.get(1).getRegion());
                //textType.setText(specialOfferRealEstateList.get(1).getPropertyType());
                txtTitle.setText(specialOfferRealEstateList.get(1).getPropertyType());
               // txtdate.setText(specialOfferRealEstateList.get(1).getOfferDate());
                Log.e("timeanddate",""+txtTitle+"???"+txtdate);
                layoutthird.setVisibility(View.GONE);
            }
            else
            {
                rllayoutspecialoffer.setVisibility(View.VISIBLE);
                layoutoneTop.setVisibility(View.VISIBLE);
                layoutthird.setVisibility(View.VISIBLE);
               // txtTitle.setText(specialOfferRealEstateList.get(1).getTitle());
                //txtdate.setText(specialOfferRealEstateList.get(1).getOfferDate());
                Log.e("timeanddate",""+txtTitle+"???"+txtdate);
                textDescription.setText(specialOfferRealEstateList.get(1).getDescription());
                layoutsecond.setVisibility(View.GONE);
            }

        }
        else
        {
            rllayoutspecialoffer.setVisibility(View.GONE);


        }
    }

    @Override
    public void onSpecialOffersDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onSpecialOffersDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseSpecialOfferJsonData(jsonObject);
    }

    @Override
    public void onSpecialOffersDataLoadedFailed(JSONObject jsonObject) {
        super.onSpecialOffersDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdvanceSearch:
                //sendRegistrationData();
                break;
            default:
                break;
        }
    }

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
}

