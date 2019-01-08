package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.MonthaAdapter;
import com.mawaqaa.homepluskw_new.adapter.RealEstateListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.RealEstateListData;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JijoCJ on 11/1/2016.
 */
public class RealEstateFragment extends HomePlusBaseFragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private static final String TAG = "RealEstateFragment";
    boolean isFirstTime = false,selectflag=true;
    int searchCase = 0;
    String search_key;
    EditText etSearch;
    ImageButton imgBtnSearch;
    Button btnAdvanceSearch;
    RecyclerView recycViewRealEstate;
    ArrayList<RealEstateListData> search_list;
    ArrayList<RealEstateListData> realestate_list;
    List<RealEstateListData> realEstateList;
    RealEstateListAdapter mAdapter;
    LinearLayout realestatelinear;
    boolean flag = false;
    TextView realestatepropertyspace, realestatepropertypickrange,txtnoData;

    JSONArray jsonprice_range_From_Array,jsonprice_range_To_Array;


    Spinner spinner_region, spinner_property_type, spinner_streetlocation, spinner_purpose, spinner_propertyspace_From,
            spinner_propertyspace_To, spinner_pricerange_From, spinner_pricerange_To;
    List<String> regionList;
    List<String> propertytypeList;
    List<String> streetlocationList;
    List<String> purposeList;
    List<String> propertyspaceFromList;
    List<String> propertyspaceToList;
    List<String> pricerangeFromList;
    List<String> pricerangeToList;
    ArrayAdapter<String> regionAdapter;
    ArrayAdapter<String> propertytypeAdapter;
    ArrayAdapter<String> streetlocationAdapter;
    ArrayAdapter<String> purposeAdapter;
    ArrayAdapter<String> propertyfromAdapter;
    ArrayAdapter<String> propertyToAdapter;
    ArrayAdapter<String> priceFromAdapter;
    ArrayAdapter<String> priceToAdapter;
    /*private LinkedHashMap<String, String> regionMap;
    private LinkedHashMap<String, String> propertytypeMap;
    private LinkedHashMap<String, String> streetlocationMap;
    private LinkedHashMap<String, String> purposeMap;
    private LinkedHashMap<String, String> propertyspaceFromMap;
    private LinkedHashMap<String, String> propertyspaceToMap;
    private LinkedHashMap<String, String> pricerangeFromMap;
    private LinkedHashMap<String, String> pricerangeToMap;*/
    MonthaAdapter mArrayMonthAdapter;
    String id;
    boolean isFromOffers;
    String region = "", apartments = "", streetlocation = "", purpose = "", propertyspacefrom = "", propertspaceTo = "", pricerangefrom = "", pricerangeto = "";
    public static RealEstateFragment newInstance(String constructionInnerId, boolean isFromOffers){

        RealEstateFragment realestateFrag = new RealEstateFragment();
        realestateFrag.id = constructionInnerId;
        realestateFrag.isFromOffers = isFromOffers;
        return realestateFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mAdapter = new RealEstateListAdapter(Activity, realEstateList);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume>>>>>>" + this.getClass().getName());
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_estate, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_real_estate_title_action_bar));
        initView(view);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.realEstateButtonState();
        return view;
    }

    private void initView(View view) {
        isFirstTime=false;
        txtnoData=(TextView)view.findViewById(R.id.txtNoData);
        spinner_region = (Spinner) view.findViewById(R.id.spinnerSelectRegion);
        spinner_property_type = (Spinner) view.findViewById(R.id.spinnerSelectPropertyType);
        spinner_streetlocation = (Spinner) view.findViewById(R.id.spinnerSelectStreetlocation);
        spinner_purpose = (Spinner) view.findViewById(R.id.spinnerSelectPurpose);
        spinner_propertyspace_From = (Spinner) view.findViewById(R.id.spinnerSelectFrom);
        spinner_propertyspace_To = (Spinner) view.findViewById(R.id.spinnerSelectTo);
        spinner_pricerange_From = (Spinner) view.findViewById(R.id.spinnerSelectPickrangeFrom);
        spinner_pricerange_To = (Spinner) view.findViewById(R.id.spinnerSelectPickrangeTo);

        spinner_property_type.setOnItemSelectedListener(this);
        spinner_streetlocation.setOnItemSelectedListener(this);
        spinner_purpose.setOnItemSelectedListener(this);
        spinner_propertyspace_From.setOnItemSelectedListener(this);
        spinner_propertyspace_To.setOnItemSelectedListener(this);
        spinner_pricerange_From.setOnItemSelectedListener(this);
        spinner_pricerange_To.setOnItemSelectedListener(this);
        spinner_region.setOnItemSelectedListener(this);

        realestatepropertyspace = (TextView) view.findViewById(R.id.realestatepropertyspace);
        realestatepropertypickrange = (TextView) view.findViewById(R.id.realestatepropertypickrange);
        imgBtnSearch = (ImageButton) view.findViewById(R.id.imgBtnSearch);
        etSearch = (EditText) view.findViewById(R.id.etSearch);

        recycViewRealEstate = (RecyclerView) view.findViewById(R.id.recycViewRealEstate);
        btnAdvanceSearch = (Button) view.findViewById(R.id.btnAdvanceSearch);

        recycViewRealEstate.setLayoutManager(new GridLayoutManager(Activity, 2));
        realestatelinear = (LinearLayout) view.findViewById(R.id.linearhide);
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity, realestatepropertyspace);
            FontUtils.setHomeplusRegularFont(Activity, realestatepropertypickrange);
            FontUtils.setHomeplusRegularFont(Activity, etSearch);
            FontUtils.setHomeplusRegularFont(Activity, btnAdvanceSearch);
        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, realestatepropertyspace);
            FontUtils.setHomeplusArabicRegularFont(Activity, realestatepropertypickrange);
            FontUtils.setHomeplusArabicRegularFont(Activity, etSearch);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnAdvanceSearch);
        }

        btnAdvanceSearch.setOnClickListener(this);
        imgBtnSearch.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG,"Enter pressed");
                    getSpinnerVAlues();
                    advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
                }
                return false;
            }
        });
        // fetch Real Estate Data From Server
        if (Activity.isNetworkAvailable())
            fetchRelaEstateData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void fetchRelaEstateData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.REALESTATE_PROPERTY_LOCATION, "");
            jsonObject.putOpt(AppConstants.REALESTATE_PRICE_FROM, "");
            jsonObject.putOpt(AppConstants.REALESTATE_PRICE_TO, "");
            jsonObject.putOpt(AppConstants.REALESTATE_PURPOSE, "");
            jsonObject.putOpt(AppConstants.REALESTATE_REGION, "");
            jsonObject.putOpt(AppConstants.REALESTATE_SIZEFROM, "");
            jsonObject.putOpt(AppConstants.REALESTATE_SIZETO, "");
            jsonObject.putOpt(AppConstants.REALESTATE_TYPE, "");
            jsonObject.putOpt(AppConstants.SEARCHKEYWORD, etSearch.getText().toString());
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            Log.e(TAG, "Json Request:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_REAL_ESTATE, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getSpinnerVAlues() {
        region = spinner_region.getSelectedItem().toString();
        if (region.equals(getString(R.string.txt_region))||region.equalsIgnoreCase(getString(R.string.txt_All))) {
            region = "";
        }
        apartments = spinner_property_type.getSelectedItem().toString();
        if (apartments.equals(getString(R.string.txt_property))||apartments.equalsIgnoreCase(getString(R.string.txt_All))) {
            apartments = "";
        }
        streetlocation = spinner_streetlocation.getSelectedItem().toString();
        if (streetlocation.equals(getString(R.string.txt_property_location_spinner))||streetlocation.equalsIgnoreCase(getString(R.string.txt_All))) {
            streetlocation = "";
        }
        purpose = spinner_purpose.getSelectedItem().toString();
        if (purpose.equals(getString(R.string.txt_purpose))||purpose.equalsIgnoreCase(getString(R.string.txt_All))) {
            purpose = "";
        }
        propertyspacefrom = spinner_propertyspace_From.getSelectedItem().toString();
        if (propertyspacefrom.equals(getString(R.string.txt_spinner_size_from))||propertyspacefrom.equalsIgnoreCase(getString(R.string.txt_All))) {
            propertyspacefrom = "";
        }
        propertspaceTo = spinner_propertyspace_To.getSelectedItem().toString();
        if (propertspaceTo.equals(getString(R.string.txt_spinner_size_To))||propertspaceTo.equalsIgnoreCase(getString(R.string.txt_All))) {
            propertspaceTo = "";
        }
        //if(pricerangeFromList.size()>1) {
            Log.e("checkinggggg","!!!!!!!!!!!!!!!"+pricerangeFromList.size());
            pricerangefrom = spinner_pricerange_From.getSelectedItem().toString();
            if (pricerangefrom.equals(getString(R.string.txt_spinner_size_from))) {
                pricerangefrom = "";
            }
       // }
       // if(pricerangeToList.size()>1) {
            pricerangeto = spinner_pricerange_To.getSelectedItem().toString();
            if (pricerangeto.equals(getString(R.string.txt_spinner_size_To))) {
                pricerangeto = "";
            }
       // }
        Log.e(TAG, region + ">>" + apartments + ">>" + streetlocation + ">>" + purpose + ">>" + propertyspacefrom + ">>" + propertspaceTo + ">>" + pricerangefrom + ">>" + pricerangeto + "");

    }

    private void parseRealEstateJsonData(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.REAL_ESTATE_ARR);
                    if (jsonArray.length() > 0) {
                        realEstateList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            realEstateList.add(new RealEstateListData(jsonArray.getJSONObject(i).getString(AppConstants.ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.IMAGE), jsonArray.getJSONObject(i).getString(AppConstants.LOCATION),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PRICE), jsonArray.getJSONObject(i).getString(AppConstants.PROPERTY_NAME),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PROPERTY_TYPE), jsonArray.getJSONObject(i).getString(AppConstants.PURPOSE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.REGION)));
                        }
                        mAdapter = new RealEstateListAdapter(Activity, realEstateList);
                        recycViewRealEstate.setAdapter(mAdapter);
                        mAdapter.SetOnItemClickListener(new RealEstateListAdapter.OnItemClickListener() {

                            @Override
                            public void onItemClick(View v, int position) {
                                Log.e(TAG, "Click Position:" + position);
                                RealEstateListData listData = realEstateList.get(position);
                                RealEstateDeatailsInnerFragment realEstateDeatailsInnerFragment = RealEstateDeatailsInnerFragment.newInstance(listData.getId(), false);
                                Activity.pushFragments(realEstateDeatailsInnerFragment, false, true);
                            }

                        });
                        recycViewRealEstate.setVisibility(View.VISIBLE);
                    } else {
                        recycViewRealEstate.setVisibility(View.GONE);
                       txtnoData.setVisibility(View.VISIBLE);


                    }

                    JSONArray jsonRegionArray = jsonObject.getJSONArray(AppConstants.REALESTATE_REGION);
                    JSONArray jsonPropertyTypeArray = jsonObject.getJSONArray(AppConstants.REALESTATE_TYPE);
                    JSONArray jsonStreetlocationArray = jsonObject.getJSONArray(AppConstants.REALESTATE_PROPERTY_LOCATION);
                    JSONArray jsonPurposeArray = jsonObject.getJSONArray(AppConstants.REALESTATE_PURPOSE);
                    JSONArray jsonpropertyspace_From_Array = jsonObject.getJSONArray(AppConstants.REALESTATE_PROPERTY_SPACE);
                    JSONArray jsonpropertyspace_To_Array = jsonObject.getJSONArray(AppConstants.REALESTATE_PROPERTY_SPACE);
                     jsonprice_range_From_Array= jsonObject.getJSONArray(AppConstants.REALESTATE_PRICE_RANGE);
                     jsonprice_range_To_Array = jsonObject.getJSONArray(AppConstants.REALESTATE_PRICE_RANGE);

                    if (! isFirstTime) {
                        Log.e("PasringFirstTime","hjgvc");
                        setSpinnerRegion(jsonRegionArray);
                        setSpinnerPropertyType(jsonPropertyTypeArray);
                        setStreetLocation(jsonStreetlocationArray);
                        setPurpose(jsonPurposeArray);
                        setSpceFrom(jsonpropertyspace_From_Array);
                        setSpaceTo(jsonpropertyspace_To_Array);
                        setRangeFrom(jsonprice_range_From_Array);
                        setRangeTo(jsonprice_range_To_Array);
                        isFirstTime=true;
                    }
                } else {

                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRealEstateDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onRealEstateDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();

        Log.e(TAG, "Json Response:" + jsonObject);
        parseRealEstateJsonData(jsonObject);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onRealEstateDataLoadedFailed(JSONObject jsonObject) {
        super.onRealEstateDataLoadedFailed(jsonObject);
        BaseApplication.getInstance().progressOFF();
        if (jsonObject == null)
            Toast.makeText(Activity, getString(R.string.alert_txt_server_error), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdvanceSearch:
                if (flag) {
                    realestatelinear.setVisibility(View.GONE);
                    flag = false;
                    break;
                } else {
                    realestatelinear.setVisibility(View.VISIBLE);
//                    fetchRelaEstateData();
                    searchCase=0;
                    flag = true;
                    Log.e("visiblie", "l;kmzvg");
                    break;
                }
            case R.id.imgBtnSearch:

                getSpinnerVAlues();
                advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
                break;
            default:
                break;
        }
    }

    private void advanceRealestateSearch(String region, String apartments, String streetlocation, String purpose, String propertyspacefrom, String propertspaceTo, String pricerangefrom, String pricerangeto) {

        search_key = etSearch.getText().toString().toUpperCase().trim();
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.REALESTATE_PROPERTY_LOCATION, streetlocation);
            jsonObject.putOpt(AppConstants.REALESTATE_PRICE_FROM, pricerangefrom);
            jsonObject.putOpt(AppConstants.REALESTATE_PRICE_TO, pricerangeto);
            jsonObject.putOpt(AppConstants.REALESTATE_PURPOSE, purpose);
            jsonObject.putOpt(AppConstants.REALESTATE_REGION, region);
            jsonObject.putOpt(AppConstants.REALESTATE_SIZEFROM, propertyspacefrom);
            jsonObject.putOpt(AppConstants.REALESTATE_SIZETO, propertspaceTo);
            jsonObject.putOpt(AppConstants.REALESTATE_TYPE, apartments);
            jsonObject.putOpt(AppConstants.SEARCHKEYWORD, search_key);
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            Log.e(TAG, "Json Request >>>:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_REAL_ESTATE, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setSpinnerRegion(JSONArray jsonRegionArray) {
        try {

            if (jsonRegionArray.length() > 0) {
                regionList = new ArrayList<>();
                regionList.add(getResources().getString(R.string.txt_region));
                Log.e("if", "jh,g,jhvf");
                for (int i = 0; i < jsonRegionArray.length(); i++) {
                    Log.e("for", "jh,g,jhvf");
                    regionList.add(jsonRegionArray.getJSONObject(i).getString(AppConstants.REALESTATE_REGION_TEXT));
                }
                regionAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, regionList);
                spinner_region.setAdapter(regionAdapter);
                regionAdapter.notifyDataSetChanged();

            }
            else
            {
                regionList = new ArrayList<>();
                regionList.add(getResources().getString(R.string.txt_region));
                regionAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, regionList);
                spinner_region.setAdapter(regionAdapter);
                regionAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setSpinnerPropertyType(JSONArray jsonPropertyTypeArray) {
        try {

            if (jsonPropertyTypeArray.length() > 0) {
                propertytypeList = new ArrayList<>();
                propertytypeList.add(getResources().getString(R.string.txt_property));
                for (int i = 0; i < (jsonPropertyTypeArray.length()); i++) {
                    propertytypeList.add(jsonPropertyTypeArray.getJSONObject(i).getString(AppConstants.REALESTATE_REGION_TEXT));
                }

                propertytypeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, propertytypeList);
                spinner_property_type.setAdapter(propertytypeAdapter);
                propertyToAdapter.notifyDataSetChanged();
            }
            else
            {
                propertytypeList = new ArrayList<>();
                propertytypeList.add(getResources().getString(R.string.txt_property));
                propertytypeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, propertytypeList);
                spinner_property_type.setAdapter(propertytypeAdapter);
                propertyToAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setStreetLocation(JSONArray jsonStreetlocationArray) {
        try {


            if (jsonStreetlocationArray.length() > 0) {
                streetlocationList = new ArrayList<>();
                streetlocationList.add(getResources().getString(R.string.txt_property_location_spinner));
                for (int i = 0; i < (jsonStreetlocationArray.length()); i++) {
                    streetlocationList.add(jsonStreetlocationArray.getJSONObject(i).getString(AppConstants.REALESTATE_REGION_TEXT));
                }
                streetlocationAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, streetlocationList);
                spinner_streetlocation.setAdapter(streetlocationAdapter);
                streetlocationAdapter.notifyDataSetChanged();
            }
            else
            {
                streetlocationList = new ArrayList<>();
                streetlocationList.add(getResources().getString(R.string.txt_property_location_spinner));
                streetlocationAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, streetlocationList);
                spinner_streetlocation.setAdapter(streetlocationAdapter);
                streetlocationAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void setPurpose(JSONArray jsonPurposeArray) {
        try {

            if (jsonPurposeArray.length() > 0) {
                purposeList = new ArrayList<>();
                purposeList.add(getResources().getString(R.string.txt_purpose));
                for (int i = 0; i < (jsonPurposeArray.length()); i++) {
                    purposeList.add(jsonPurposeArray.getJSONObject(i).getString(AppConstants.REALESTATE_REGION_TEXT));
                }
                purposeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, purposeList);
                spinner_purpose.setAdapter(purposeAdapter);
                purposeAdapter.notifyDataSetChanged();
            }
            else
            {
                purposeList = new ArrayList<>();
                purposeList.add(getResources().getString(R.string.txt_purpose));
                purposeAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, purposeList);
                spinner_purpose.setAdapter(purposeAdapter);
                purposeAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setSpceFrom(JSONArray jsonpropertyspace_From_Array) {
        try {

            if (jsonpropertyspace_From_Array.length() > 0) {
                propertyspaceFromList = new ArrayList<>();
                propertyspaceFromList.add(getResources().getString(R.string.txt_spinner_size_from));
                for (int i = 0; i < (jsonpropertyspace_From_Array.length()); i++) {
                    propertyspaceFromList.add(jsonpropertyspace_From_Array.getJSONObject(i).getString(AppConstants.REALESTATE_REGION_TEXT));
                }
                propertyfromAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, propertyspaceFromList);
                spinner_propertyspace_From.setAdapter(propertyfromAdapter);
                propertyfromAdapter.notifyDataSetChanged();
            }
            else
            {
                propertyspaceFromList = new ArrayList<>();
                propertyspaceFromList.add(getResources().getString(R.string.txt_spinner_size_from));
                propertyfromAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, propertyspaceFromList);
                spinner_propertyspace_From.setAdapter(propertyfromAdapter);
                propertyfromAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setSpaceTo(JSONArray jsonpropertyspace_To_Array) {
        try {

            if (jsonpropertyspace_To_Array.length() > 0) {
                propertyspaceToList = new ArrayList<>();
                propertyspaceToList.add(getResources().getString(R.string.txt_spinner_size_To));
                for (int i = 0; i < (jsonpropertyspace_To_Array.length()); i++) {
                    propertyspaceToList.add(jsonpropertyspace_To_Array.getJSONObject(i).getString(AppConstants.REALESTATE_REGION_TEXT));
                }
                propertyToAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, propertyspaceToList);
                spinner_propertyspace_To.setAdapter(propertyToAdapter);
                propertyToAdapter.notifyDataSetChanged();
            }
            else
            {
                propertyspaceToList = new ArrayList<>();
                propertyspaceToList.add(getResources().getString(R.string.txt_spinner_size_To));
                propertyToAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, propertyspaceToList);
                spinner_propertyspace_To.setAdapter(propertyToAdapter);
                propertyToAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setRangeFrom(JSONArray jsonprice_range_From_Array) {
        try {


            if (jsonprice_range_From_Array.length() > 0) {
                pricerangeFromList = new ArrayList<>();
                pricerangeFromList.add(getResources().getString(R.string.txt_spinner_size_from));
                for (int i = 0; i < (jsonprice_range_From_Array.length()); i++) {
                    pricerangeFromList.add(jsonprice_range_From_Array.getJSONObject(i).getString(AppConstants.REALESTATE_REGION_TEXT));
                }
                priceFromAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, pricerangeFromList);
                spinner_pricerange_From.setAdapter(priceFromAdapter);
                priceFromAdapter.notifyDataSetChanged();
            }
            else
            {
                pricerangeFromList = new ArrayList<>();
                pricerangeFromList.add(getResources().getString(R.string.txt_spinner_size_from));
                priceFromAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, pricerangeFromList);
                spinner_pricerange_From.setAdapter(priceFromAdapter);
                priceFromAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setRangeTo(JSONArray jsonprice_range_To_Array) {
        try {


            if (jsonprice_range_To_Array.length() > 0) {
                pricerangeToList = new ArrayList<>();
                pricerangeToList.add(getResources().getString(R.string.txt_spinner_size_To));
                for (int i = 0; i < (jsonprice_range_To_Array.length()); i++) {
                    pricerangeToList.add(jsonprice_range_To_Array.getJSONObject(i).getString(AppConstants.REALESTATE_REGION_TEXT));
                }
                priceToAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, pricerangeToList);
                spinner_pricerange_To.setAdapter(priceToAdapter);
                priceToAdapter.notifyDataSetChanged();
            }
            else
            {   pricerangeToList = new ArrayList<>();
                pricerangeToList.add(getResources().getString(R.string.txt_spinner_size_To));
                priceToAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, pricerangeToList);
                spinner_pricerange_To.setAdapter(priceToAdapter);
                priceToAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------unused-------------------------------------//
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // getSpinnerVAlues();

        Log.e("onselecteditem",">>>>."+isFirstTime);
        //isFirstTime=false;
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerSelectRegion)
        {
            //do this
            if(spinner_region.getSelectedItemPosition()!=position)
            {
                searchCase=1;
            }

            //Log.e("onselecteditem",">>>>.1");
        }
       else if(spinner.getId() == R.id.spinnerSelectPropertyType)
        {
            //do this

            searchCase=2;
            Log.e("onselecteditem",">>>>.2");
        }
        else if(spinner.getId() == R.id.spinnerSelectStreetlocation)
        {
            //do this
             searchCase=3;
            Log.e("onselecteditem",">>>>.3");
        }
        else if(spinner.getId() == R.id.spinnerSelectPurpose)
        {
            //do this
            searchCase=4;
            Log.e("onselecteditem",">>>>.4");
        }
        else if(spinner.getId() == R.id.spinnerSelectFrom)
        {
            //do this
            searchCase=5;
            Log.e("onselecteditem",">>>>.5");
        }
        else if(spinner.getId() == R.id.spinnerSelectTo)
        {
            //do this
            searchCase=6;
            Log.e("onselecteditem",">>>>.6");
        }
        else if(spinner.getId() == R.id.spinnerSelectPickrangeFrom)
        {

            searchCase=7;
            Log.e("onselecteditem",">>>>.7");
        }
        else if(spinner.getId() == R.id.spinnerSelectPickrangeTo)
        {
            //do this
            searchCase=8;
            Log.e("onselecteditem",">>>>.8");
        }

        getSpinnerVAlues();
        if(region != "") {
            Log.e("NOTNULL","region");
            advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
        }
        else if(apartments != "") { Log.e("NOTNULL","apartment");
            advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
        }
        else if(streetlocation != "") { Log.e("NOTNULL","streetlocation");
            advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
        }
        else if(purpose != "") { Log.e("NOTNULL","purpose");
            advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
        }
        else if(propertyspacefrom != "") { Log.e("NOTNULL","propertyspacefrom");
            advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
        }
        else if(propertspaceTo != "") { Log.e("NOTNULL","propertspaceTo");
            advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
        }
        else if(pricerangefrom != "") { Log.e("NOTNULL","pricerangefrom");
            advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
        }
        else if(pricerangeto != "") { Log.e("NOTNULL","pricerangeto");
            advanceRealestateSearch(region, apartments, streetlocation, purpose, propertyspacefrom, propertspaceTo, pricerangefrom, pricerangeto);
        }
else
        {

           fetchRelaEstateData();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        getSpinnerVAlues();

    }
//-----------------------------------------unused-------------------------------------//
    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = recycViewRealEstate.indexOfChild(v);

        }
    }
}
