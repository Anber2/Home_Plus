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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.ConstructionInnerListAdapter;
import com.mawaqaa.homepluskw_new.adapter.SpinnerListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.ConstructionInnerData;
import com.mawaqaa.homepluskw_new.data.LocationListData;
import com.mawaqaa.homepluskw_new.data.SessionForRecycleView;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JijoCJ on 11/22/2016.
 */
public class ConstrctnCorporateInnerFrag extends HomePlusBaseFragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener, AdapterView.OnItemSelectedListener {
    ImageButton imgBtnSearch;

    private static final String TAG = "ConstnCorprateInnerFrag";
    FrameLayout linearLaySearchInIndividual;
    TextView txtRealEstateType, txtResultCount, txtNoData;
    EditText etSearchConstnCorporate;
    Button btnTabIndividual, btnTabCorporate;
    Spinner spinnerLocation, spinnerSort;
    LinearLayout linearLayFilterSearchInCorporate, linearLaySortInCorporate;
    RecyclerView recycViewConstruction, recycViewConstruction2;
    View viewDividerInCorporateTop;
    List<ConstructionInnerData> corporateList;
    List<String> locationList;
    List<LocationListData> locationDetailsList;
    List<String> sortByList;
    ConstructionInnerListAdapter mAdapter;
    SpinnerListAdapter mSpinnerAdapter;
    String constructionId, search_key;
    int selectedCountryPosition;
    boolean spinnerLocationSate = false;
    String title;
    String Type = "Corporate";
    boolean isfirsttime = false;
    String itemSelectedLocation, itemSelectedSort;


    public static ConstrctnCorporateInnerFrag newInstance(String constructionId, String title) {
        ConstrctnCorporateInnerFrag corporateInnerFrag = new ConstrctnCorporateInnerFrag();
        corporateInnerFrag.constructionId = constructionId;
        corporateInnerFrag.title = title;
        return corporateInnerFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Activity = (HomePlusBaseActivity) this.getActivity();
        Log.e(TAG, "Spinner Flag: " + "onCreate()" + spinnerLocationSate);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume" + this.getClass().getName());
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        spinnerLocationSate = false;
        Log.e(TAG, "Spinner Flag: " + "onResume()" + spinnerLocationSate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_construction, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_construction_title_action_bar));
        Log.e(TAG, "Spinner Flag: " + "onCreateView()" + spinnerLocationSate);
        initView(view);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.constructionButtonState();

        Log.d("InnerPostion",""+SessionForRecycleView.constrctnCorporateInnerPostion);

        return view;
    }

    private void initView(View view) {
        isfirsttime = false;

        imgBtnSearch = (ImageButton) view.findViewById(R.id.imgBtnSearch);
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            txtRealEstateType = (TextView) view.findViewById(R.id.txtRealEstateType);
            FontUtils.setHomeplusRegularFont(Activity, txtRealEstateType);
            txtNoData = (TextView) view.findViewById(R.id.txtNoData);
            FontUtils.setHomeplusRegularFont(Activity, txtNoData);
            txtResultCount = (TextView) view.findViewById(R.id.txtResultCount);
            FontUtils.setHomeplusRegularFont(Activity, txtResultCount);
            txtResultCount.setSelected(true);
            txtRealEstateType.setText(title);
            txtRealEstateType.setSelected(true);
            FontUtils.setHomeplusRegularFont(Activity, txtRealEstateType);
            etSearchConstnCorporate = (EditText) view.findViewById(R.id.etSearchConstnCorporate);
            FontUtils.setHomeplusRegularFont(Activity, etSearchConstnCorporate);
            btnTabIndividual = (Button) view.findViewById(R.id.btnTabIndividual);
            FontUtils.setHomeplusRegularFont(Activity, btnTabIndividual);
            btnTabCorporate = (Button) view.findViewById(R.id.btnTabCorporate);
            FontUtils.setHomeplusRegularFont(Activity, btnTabCorporate);
            btnTabIndividual.setSelected(false);
            btnTabCorporate.setSelected(true);
        } else {
            txtRealEstateType = (TextView) view.findViewById(R.id.txtRealEstateType);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtRealEstateType);
            txtNoData = (TextView) view.findViewById(R.id.txtNoData);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtNoData);
            txtResultCount = (TextView) view.findViewById(R.id.txtResultCount);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtResultCount);
            txtResultCount.setSelected(true);
            txtRealEstateType.setText(title);
            txtRealEstateType.setSelected(true);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtRealEstateType);
            etSearchConstnCorporate = (EditText) view.findViewById(R.id.etSearchConstnCorporate);
            FontUtils.setHomeplusArabicRegularFont(Activity, etSearchConstnCorporate);
            btnTabIndividual = (Button) view.findViewById(R.id.btnTabIndividual);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabIndividual);
            btnTabCorporate = (Button) view.findViewById(R.id.btnTabCorporate);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabCorporate);
            btnTabIndividual.setSelected(false);
            btnTabCorporate.setSelected(true);

        }
        spinnerLocation = (Spinner) view.findViewById(R.id.spinnerLocation);
        spinnerSort = (Spinner) view.findViewById(R.id.spinnerSort);
        etSearchConstnCorporate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG, "Enter pressed");
                    serachConstructinCorporateInner();
                }
                return false;
            }
        });

        spinnerLocation.setOnItemSelectedListener(this);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isfirsttime) {
                    itemSelectedSort = spinnerSort.getSelectedItem().toString();

                    if (!itemSelectedSort.equals("SortBy")) {
                        /*if(itemSelectedLocation.equalsIgnoreCase(getString(R.string.spinner_txt_tile_location)))
                            itemSelectedLocation = " ";
                        else{
                            itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
                        }*/
                        fetchFilterSearchData();
                        spinnerLocationSate = true;
                        Log.e("SOTBY", "1");
                    } else {
                        Log.e("SOTBY", "2");
                        if (spinnerLocationSate) {
                            spinnerLocationSate = false;
                            fetchConstructionCorporateInnerData();
                        }
                        // Toast.makeText(Activity, getResources().getString(R.string.alert_select_location), Toast.LENGTH_LONG).show();
                        // FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //ft.detach(this).attach(this).commit();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Check for if search is Location or not
      /*  spinnerLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
                if(itemSelectedLocation.equals(getString(R.string.spinner_txt_tile_location))){
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(ConstrctnIndividualInnerFrag.this).attach(ConstrctnIndividualInnerFrag.this).commit();
                }
            }
        });*/

        linearLaySearchInIndividual = (FrameLayout) view.findViewById(R.id.linearLaySearchInIndividual);
        linearLayFilterSearchInCorporate = (LinearLayout) view.findViewById(R.id.linearLayFilterSearchInCorporate);
        linearLaySortInCorporate = (LinearLayout) view.findViewById(R.id.linearLaySortInCorporate);

        viewDividerInCorporateTop = (View) view.findViewById(R.id.viewDividerInCorporateTop);

        recycViewConstruction = (RecyclerView) view.findViewById(R.id.recycViewConstruction);
//        recycViewConstruction2 = (RecyclerView) view.findViewById(R.id.recycViewConstruction2);
        //recycViewRealEstate.setHasFixedSize(true);
        recycViewConstruction.setLayoutManager(new GridLayoutManager(Activity, 2));
//        recycViewConstruction2.setLayoutManager(new GridLayoutManager(Activity, 2));
        //recycViewRealEstate.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
/*
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)){
            btnTabIndividual.setBackgroundResource(R.drawable.button_curved_left_white);
            btnTabCorporate.setBackgroundResource(R.drawable.button_curved_yellow);
        }else{
            btnTabIndividual.setBackgroundResource(R.drawable.button_curved_white);
            btnTabCorporate.setBackgroundResource(R.drawable.button_curved_yellow);
        }*/
        Log.e(TAG, "Corporate inner");

        linearLaySearchInIndividual.setVisibility(View.GONE);
        linearLayFilterSearchInCorporate.setVisibility(View.VISIBLE);
        linearLaySortInCorporate.setVisibility(View.VISIBLE);
        viewDividerInCorporateTop.setVisibility(View.VISIBLE);

        btnTabIndividual.setOnClickListener(this);
        btnTabCorporate.setOnClickListener(this);
        imgBtnSearch.setOnClickListener(this);

        // fetch Construction Data From Server
        if (Activity.isNetworkAvailable())
            fetchConstructionCorporateInnerData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }


    private void fetchConstructionCorporateInnerData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
            Log.e(TAG, "Json Request:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);
            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseConstructnCorporateInnerData(JSONObject jsonObject) {
        try {
            Log.d("searchRes",jsonObject.toString());
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.DETAIL_LIST_ARR);
                    if (jsonArray.length() > 0) {
                        txtNoData.setVisibility(View.GONE);
                        corporateList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            corporateList.add(new ConstructionInnerData(jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.COMPANY_NAME), jsonArray.getJSONObject(i).getString(AppConstants.AVERAGE_RATING),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PERSONS_RATED), jsonArray.getJSONObject(i).getString(AppConstants.FEATURED_IMAGE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.COMPANY_LOCATION)));
                        }
                        mAdapter = new ConstructionInnerListAdapter(Activity, corporateList, Type);
                        recycViewConstruction.setAdapter(mAdapter);
                        if (SessionForRecycleView.constrctnCorporateInnerPostion == -1) {

                        } else {
                            recycViewConstruction.scrollToPosition(SessionForRecycleView.constrctnCorporateInnerPostion);
                        }
                        //txtResultCount.setText("("+String.valueOf(individualList.size()+" "+"Results Found)"));
                        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
                            // txtResultCount.setText("("+getString(R.string.txt_result_found) +" "+ corporateList.size()+ ")");
                            txtResultCount.setText("(" + corporateList.size() + " " + getString(R.string.txt_result_found) + ")");
                        } else {
                            txtResultCount.setText("(" + corporateList.size() + " " + getString(R.string.txt_result_found) + ")");
                        }
                        //txtResultCount.setText("("+getString(R.string.txt_result_found, corporateList.size())+")");
                        mAdapter.setOnItemClickListener(new ConstructionInnerListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                ConstructionInnerData innerData = corporateList.get(position);
                                SessionForRecycleView.constrctnCorporateInnerPostion = position;
                                Activity.pushFragments(ConstrctnCorporateDetailsFrag.newInstance(innerData.getId(), false), false, true);

                            }
                        });
                    } else {
                        txtResultCount.setText("(" + getString(R.string.txt_result_found, 0) + ")");
                        txtNoData.setVisibility(View.VISIBLE);
                    }

                    //Spinner for Location
                    JSONArray jsonLocationArray = jsonObject.getJSONArray(AppConstants.NATIONALITY_ARR);
                    if (jsonLocationArray.length() > 0) {
                        locationDetailsList = new ArrayList<>();
                        locationList = new ArrayList<>();
                        locationList.add(getString(R.string.spinner_txt_tile_location));
                        for (int j = 0; j < jsonLocationArray.length(); j++) {
                            locationDetailsList.add(new LocationListData(jsonLocationArray.getJSONObject(j).getString(AppConstants.COUNTRY_ID),
                                    jsonLocationArray.getJSONObject(j).getString(AppConstants.COUNTRY_CODE), jsonLocationArray.getJSONObject(j).getString(AppConstants.COUNTRY_NAME)));
                            locationList.add(jsonLocationArray.getJSONObject(j).getString(AppConstants.COUNTRY_NAME));
                        }
                        //ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, locationList);
                        mSpinnerAdapter = new SpinnerListAdapter(Activity, locationList);
                        spinnerLocation.setAdapter(mSpinnerAdapter);

                    } else {
                        locationList = new ArrayList<>();
                        locationList.add(getString(R.string.spinner_txt_tile_location));
                        ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, locationList);
                        spinnerLocation.setAdapter(mSpinnerAdapter);
                    }

                    //Spinner for sort by
                    JSONArray jsonSortByArray = jsonObject.getJSONArray(AppConstants.SORT_BY_ARR);
                    if (jsonSortByArray.length() > 0) {
                        sortByList = new ArrayList<>();
                        sortByList.add(getString(R.string.sortby));

                        for (int i = 0; i < jsonSortByArray.length(); i++)
                            sortByList.add(jsonSortByArray.getString(i));
                        ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, sortByList);
                        spinnerSort.setAdapter(mSpinnerAdapter);

                    }
                    isfirsttime = true;
                } else {
                }
            } else {
                Toast.makeText(Activity, "", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConstructionCorporateInnerDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onConstructionCorporateInnerDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseConstructnCorporateInnerData(jsonObject);
    }

    @Override
    public void onConstructionCorporateInnerDataLoadedFailed(JSONObject jsonObject) {
        super.onConstructionCorporateInnerDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTabIndividual:
                Activity.pushFragments(new ConstructionIndividualFrag(), false, true);
                break;
            case R.id.btnTabCorporate:
                Activity.pushFragments(new ConstructionCorporateFrag(), false, true);
                break;
            case R.id.imgBtnSearch:
                serachConstructinCorporateInner();
                //fetchFilterSearchData();
                break;
            default:
                break;
        }
    }

    private void serachConstructinCorporateInner() {
        itemSelectedLocation = "";
        itemSelectedSort = "";
        if (!spinnerLocation.getSelectedItem().toString().equals((getString(R.string.spinner_txt_tile_location)))) {
            itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
        }
        if (!spinnerSort.getSelectedItem().toString().equals((getString(R.string.sortby)))) {
            itemSelectedSort = spinnerSort.getSelectedItem().toString();
        }

        Log.e(" chk selectlocation", "" + itemSelectedLocation);
        Log.e(" chk sortby", "" + itemSelectedSort);
        if (Activity.isNetworkAvailable()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
                jsonObject.putOpt(AppConstants.NATION, itemSelectedLocation);
                jsonObject.putOpt(AppConstants.TEXT, etSearchConstnCorporate.getText().toString());
                jsonObject.putOpt(AppConstants.TYPE, itemSelectedSort);
                Log.e(TAG, "Json Request:" + jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER_SEARCH, jsonObject);
                //commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConstructionCorporateInnerSearchLoadedSuccessfully(JSONObject jsonObject) {
        super.onConstructionCorporateInnerSearchLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "sucess" + jsonObject);
        parseSearchData(jsonObject);
    }

    @Override
    public void onConstructionCorporateInnerSearchLoadedFailed(JSONObject jsonObject) {
        super.onConstructionCorporateInnerSearchLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    private void FetchInnerSearch(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
       /* curRate = Float.valueOf(decimalFormat.format((curRate * count + rating)
                / ++count));
        Toast.makeText(InteractiveRatingBarActivity.this,
                "New Rating: " + curRate, Toast.LENGTH_SHORT).show();
        setRatingBar.setRating(curRate);
        countText.setText(count + " Ratings");*/
    }

    private void fetchFilterSearchData() {
        //  String itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
        itemSelectedSort = spinnerSort.getSelectedItem().toString();
        if (!itemSelectedLocation.equals((getString(R.string.spinner_txt_tile_location)))) {
            Log.e("selectlocation", "" + itemSelectedLocation);
            if (Activity.isNetworkAvailable()) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                    jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                    jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
                    jsonObject.putOpt(AppConstants.NATION, itemSelectedLocation);
                    jsonObject.putOpt(AppConstants.TEXT, etSearchConstnCorporate.getText().toString());
                    jsonObject.putOpt(AppConstants.TYPE, itemSelectedSort);
                    Log.e(TAG, "Json Request:" + jsonObject);
//                    startSpinwheel(false, true);
                    BaseApplication.getInstance().progressON(getActivity(), null);

                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER_SEARCH, jsonObject);
                    //commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }/*else {
            if (Activity.isNetworkAvailable()) {
            try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                    jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                    jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
                    jsonObject.putOpt(AppConstants.NATION, "");
                    jsonObject.putOpt(AppConstants.TEXT, etSearchConstnCorporate.getText().toString());
                    jsonObject.putOpt(AppConstants.TYPE, itemSelectedSort);
                    Log.e(TAG, "Json Request:" + jsonObject);
                    startSpinwheel(false, true);
                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER_SEARCH, jsonObject);
                    //commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }*/
    }

    private void fetchButtonSearchData() {
        String itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
        String itemSelectedSort = spinnerSort.getSelectedItem().toString();
        if (!itemSelectedLocation.equals((getString(R.string.spinner_txt_tile_location)))) {
            Log.e("selectlocation", "" + itemSelectedLocation);
            if (Activity.isNetworkAvailable()) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                    jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                    jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
                    jsonObject.putOpt(AppConstants.NATION, itemSelectedLocation);
                    jsonObject.putOpt(AppConstants.TEXT, etSearchConstnCorporate.getText().toString());
                    jsonObject.putOpt(AppConstants.TYPE, itemSelectedSort);
                    Log.e(TAG, "Json Request:" + jsonObject);
//                    startSpinwheel(false, true);
                    BaseApplication.getInstance().progressON(getActivity(), null);

                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER_SEARCH, jsonObject);
                    //commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }/*else {
            if (Activity.isNetworkAvailable()) {
            try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                    jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                    jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
                    jsonObject.putOpt(AppConstants.NATION, "");
                    jsonObject.putOpt(AppConstants.TEXT, etSearchConstnCorporate.getText().toString());
                    jsonObject.putOpt(AppConstants.TYPE, itemSelectedSort);
                    Log.e(TAG, "Json Request:" + jsonObject);
                    startSpinwheel(false, true);
                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER_SEARCH, jsonObject);
                    //commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (isfirsttime) {
            itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
            String itemSelectedSort = spinnerSort.getSelectedItem().toString();
            if (!itemSelectedLocation.equals(getString(R.string.spinner_txt_tile_location))) {
                fetchFilterSearchData();
                spinnerLocationSate = true;
            } else {
                itemSelectedLocation = "";
                fetchFilterSearchData();
                if (spinnerLocationSate) {
                    spinnerLocationSate = false;
                    fetchConstructionCorporateInnerData();
                }
                // Toast.makeText(Activity, getResources().getString(R.string.alert_select_location), Toast.LENGTH_LONG).show();
                // FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.detach(this).attach(this).commit();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.e(TAG, "Nothing Selected excecuted");
    }


    private void parseSearchData(JSONObject jsonObject) {
        Log.d("searchRes",jsonObject.toString());
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.DETAIL_LIST_ARR);
                    if (jsonArray.length() > 0) {
                        txtNoData.setVisibility(View.GONE);
                        corporateList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            corporateList.add(new ConstructionInnerData(jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.COMPANY_NAME), jsonArray.getJSONObject(i).getString(AppConstants.AVERAGE_RATING),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PERSONS_RATED), jsonArray.getJSONObject(i).getString(AppConstants.FEATURED_IMAGE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.COMPANY_LOCATION)));
                        }
                        mAdapter = new ConstructionInnerListAdapter(Activity, corporateList, Type);
                        recycViewConstruction.setAdapter(mAdapter);
                        if (SessionForRecycleView.constrctnCorporateInnerPostion == -1) {

                        } else {
                            recycViewConstruction.scrollToPosition(SessionForRecycleView.constrctnCorporateInnerPostion);
                        }
                        //txtResultCount.setText("("+String.valueOf(corporateList.size()+" "+"Results Found)"));
                        txtResultCount.setText("(" + corporateList.size() + " " + getString(R.string.txt_result_found) + ")");
                        // txtResultCount.setText("("+getString(R.string.txt_result_found, corporateList.size())+")");

                    } else {
                        txtResultCount.setText("(" + getString(R.string.txt_result_found, 0) + ")");
                        txtNoData.setVisibility(View.VISIBLE);
                    }

                } else {
                }
            } else {
                Toast.makeText(Activity, "", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
