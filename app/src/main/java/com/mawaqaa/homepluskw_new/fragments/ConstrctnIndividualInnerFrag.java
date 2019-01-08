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
 * Created by JijoCJ on 11/14/2016.
 */
public class ConstrctnIndividualInnerFrag extends HomePlusBaseFragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "ConstrtnIndivlInnerFrag";

    TextView txtRealEstateType, txtResultCount, txtNoData;
    EditText etSearchConstnCorporate;
    ImageButton imgBtnSearch;
    Button btnTabIndividual, btnTabCorporate;
    Spinner spinnerLocation, spinnerSort;
    FrameLayout linearLaySearchInIndividual;
    LinearLayout linearLayFilterSearchInCorporate, linearLaySortInCorporate;
    RecyclerView recycViewConstruction;
    View viewDividerInCorporateTop;
    List<ConstructionInnerData> individualList;
    List<String> locationList;
    List<LocationListData> locationDetailsList;
    List<String> sortByList;
    ConstructionInnerListAdapter mAdapter;
    SpinnerListAdapter mSpinnerAdapter;
    String constructionId,search_key;
    int selectedCountryPosition;
    boolean isfirsttime = false;
    String title;
    String Type="Individual";
    String itemSelectedLocation,itemSelectedSort;
    boolean spinnerLocationSate = false;


    public static ConstrctnIndividualInnerFrag newInstance(String constructionId, String title) {
        ConstrctnIndividualInnerFrag individualInnerFrag = new ConstrctnIndividualInnerFrag();
        individualInnerFrag.constructionId = constructionId;
        individualInnerFrag.title = title;
        return individualInnerFrag;
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
        spinnerLocationSate =false;
        Log.e(TAG, "Spinner Flag: " + "onResume()" + spinnerLocationSate);
        //boolean isfirsttime = false;
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
        return view;
    }

    private void initView(View view) {
         isfirsttime = false;
        imgBtnSearch=(ImageButton)view.findViewById(R.id.imgBtnSearch);
        txtRealEstateType = (TextView) view.findViewById(R.id.txtRealEstateType);
        txtNoData = (TextView) view.findViewById(R.id.txtNoData);
        txtResultCount = (TextView) view.findViewById(R.id.txtResultCount);
        btnTabIndividual = (Button) view.findViewById(R.id.btnTabIndividual);
        btnTabCorporate = (Button) view.findViewById(R.id.btnTabCorporate);
        txtResultCount.setSelected(true);
        txtRealEstateType.setText(title);
        txtRealEstateType.setSelected(true);
        etSearchConstnCorporate = (EditText) view.findViewById(R.id.etSearchConstnCorporate);
        spinnerLocation = (Spinner) view.findViewById(R.id.spinnerLocation);
        spinnerSort = (Spinner) view.findViewById(R.id.spinnerSort);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(Activity, txtRealEstateType);
            FontUtils.setHomeplusRegularFont(Activity, txtNoData);
            FontUtils.setHomeplusRegularFont(Activity, txtResultCount);
            FontUtils.setHomeplusRegularFont(Activity, txtRealEstateType);
            FontUtils.setHomeplusRegularFont(Activity, etSearchConstnCorporate);
            FontUtils.setHomeplusRegularFont(Activity, spinnerLocation);
            FontUtils.setHomeplusRegularFont(Activity, btnTabIndividual);
            FontUtils.setHomeplusRegularFont(Activity, btnTabCorporate);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity, txtRealEstateType);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtNoData);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtResultCount);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtRealEstateType);
            FontUtils.setHomeplusArabicRegularFont(Activity, etSearchConstnCorporate);
            //FontUtils.setHomeplusArabicRegularFont(Activity, spinnerLocation);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabIndividual);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabCorporate);
        }
        spinnerLocation.setOnItemSelectedListener(this);

        etSearchConstnCorporate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG,"Enter pressed");
                    serachConstructinIndividual();
                }
                return false;
            }
        });
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG,"sortclicked");
                //SortBy
                //String itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
                if(isfirsttime) {
                    String itemSelectedSort = spinnerSort.getSelectedItem().toString();
                    if (!itemSelectedSort.equals(getResources().getString(R.string.sortby))) {
                        fetchFilterSearchData();
                        spinnerLocationSate = true;
                    } else {
                        if (spinnerLocationSate) {
                            spinnerLocationSate = false;
                            fetchConstructionIndividualInnerData();
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        linearLaySearchInIndividual = (FrameLayout) view.findViewById(R.id.linearLaySearchInIndividual);
        linearLayFilterSearchInCorporate = (LinearLayout) view.findViewById(R.id.linearLayFilterSearchInCorporate);
        linearLaySortInCorporate = (LinearLayout) view.findViewById(R.id.linearLaySortInCorporate);

        viewDividerInCorporateTop = (View) view.findViewById(R.id.viewDividerInCorporateTop);

        recycViewConstruction = (RecyclerView) view.findViewById(R.id.recycViewConstruction);
        //recycViewRealEstate.setHasFixedSize(true);
        recycViewConstruction.setLayoutManager(new GridLayoutManager(Activity, 2));
        //recycViewRealEstate.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            btnTabIndividual.setSelected(true);
            btnTabCorporate.setSelected(false);
        } else {
            btnTabIndividual.setSelected(true);
            btnTabCorporate.setSelected(false);
        }


        linearLaySearchInIndividual.setVisibility(View.GONE);
        linearLayFilterSearchInCorporate.setVisibility(View.VISIBLE);
        linearLaySortInCorporate.setVisibility(View.VISIBLE);
        viewDividerInCorporateTop.setVisibility(View.VISIBLE);

        btnTabIndividual.setOnClickListener(this);
        btnTabCorporate.setOnClickListener(this);
        imgBtnSearch.setOnClickListener(this);
     /* etSearchConstnCorporate.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etSearchConstnCorporate) {
            @Override
           public boolean onDrawableClick() {

               Toast.makeText(Activity, "right>>", Toast.LENGTH_LONG).show();

              return true;
           }
      });
       etSearchConstnCorporate.setOnTouchListener(new DrawableClickListener.LeftDrawableClickListener(etSearchConstnCorporate) {
           @Override
           public boolean onDrawableClick() {

               Toast.makeText(Activity, "Left<<<", Toast.LENGTH_LONG).show();
               return true;
           }
       });
*/

        // fetch Construction Data From Server
        if (Activity.isNetworkAvailable())
            fetchConstructionIndividualInnerData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }


    private void fetchConstructionIndividualInnerData() {
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
                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_INDIVIDUAL_INNER, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseConstructnIndividualInnerData(JSONObject jsonObject) {
        Log.d("searchRes",jsonObject.toString());

        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.DETAIL_LIST_ARR);
                    if (jsonArray.length() > 0) {
                        txtNoData.setVisibility(View.GONE);
                        individualList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            individualList.add(new ConstructionInnerData(jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_NAME), jsonArray.getJSONObject(i).getString(AppConstants.AVERAGE_RATING),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PERSONS_RATED), jsonArray.getJSONObject(i).getString(AppConstants.PROFILE_PHOTO),
                                    jsonArray.getJSONObject(i).getString(AppConstants.LOCATION)));
                        }
                        mAdapter = new ConstructionInnerListAdapter(Activity, individualList,Type);
                        recycViewConstruction.setAdapter(mAdapter);
                        //txtResultCount.setText("("+String.valueOf(individualList.size()+" "+"Results Found)"));
                        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
                            //txtResultCount.setText("("+getString(R.string.txt_result_found) +" "+ individualList.size()+ ")");
                            txtResultCount.setText("("+individualList.size()+" "+getString(R.string.txt_result_found)+")");
                        }
                        else{
                            txtResultCount.setText("("+individualList.size()+" "+getString(R.string.txt_result_found)+")");
                        }
                       // txtResultCount.setText("(" + getString(R.string.txt_result_found, individualList.size()) + ")");
                        mAdapter.setOnItemClickListener(new ConstructionInnerListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                ConstructionInnerData innerData = individualList.get(position);
                                Activity.pushFragments(ConstrctnIndividualDetailsFrag.newInstance(innerData.getId(), false), false, true);

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
                        locationList.add(getString(R.string.txt_nationality));
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
                        locationList.add(getString(R.string.txt_nationality));
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
    public void onConstructionIndividualInnerDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onConstructionIndividualInnerDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseConstructnIndividualInnerData(jsonObject);
    }


    @Override
    public void onConstructionIndividualInnerDataLoadedFailed(JSONObject jsonObject) {
        super.onConstructionIndividualInnerDataLoadedFailed(jsonObject);
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
                serachConstructinIndividual();
                //fetchFilterSearchData();
                break;
            default:
                break;
        }
    }

    private void serachConstructinIndividual() {

        itemSelectedLocation ="";
        itemSelectedSort = "";
        if(!spinnerLocation.getSelectedItem().toString().equals((getString(R.string.txt_nationality)))){
            itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
        }
        if(!spinnerSort.getSelectedItem().toString().equals((getString(R.string.sortby)))){
            itemSelectedSort = spinnerSort.getSelectedItem().toString();
        }

        Log.e(" chk selectlocation",""+itemSelectedLocation);
        Log.e(" chk sortby",""+itemSelectedSort);
        if(Activity.isNetworkAvailable()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
                jsonObject.putOpt(AppConstants.NATION, "");
                jsonObject.putOpt(AppConstants.TEXT, etSearchConstnCorporate.getText().toString());
                jsonObject.putOpt(AppConstants.TYPE, itemSelectedSort);
                Log.e(TAG, "Json Request:" + jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_INDIVIDUAL_INNER_SEARCH, jsonObject);
                //commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
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
       // String itemSelectedLocation = spinnerLocation.getSelectedItem().toString();

        String itemSelectedSort = spinnerSort.getSelectedItem().toString();
        Log.e(TAG,"@@@achu"+itemSelectedSort);
        if (!itemSelectedLocation.equals(getString(R.string.txt_nationality))) {
            if (Activity.isNetworkAvailable()) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                    jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                    jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
                    jsonObject.putOpt(AppConstants.NATION, itemSelectedLocation);
                    jsonObject.putOpt(AppConstants.TEXT, etSearchConstnCorporate.getText().toString());
                    jsonObject.putOpt(AppConstants.TYPE, itemSelectedSort);
                    Log.e(TAG, "individualinner:" + jsonObject);
//                    startSpinwheel(false, true);
                    BaseApplication.getInstance().progressON(getActivity(), null);

                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_INDIVIDUAL_INNER_SEARCH, jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
       /* else if(!itemSelectedLocation.equals(())) {
        } */
      /*      else
         {
             if (Activity.isNetworkAvailable()) {
                 try {
                     JSONObject jsonObject = new JSONObject();
                     jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                     jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                     jsonObject.putOpt(AppConstants.CONSTRUCTION_ID, constructionId);
                     jsonObject.putOpt(AppConstants.NATION,"");
                     jsonObject.putOpt(AppConstants.TEXT, etSearchConstnCorporate.getText().toString());
                     jsonObject.putOpt(AppConstants.TYPE, itemSelectedSort);
                     Log.e(TAG, "individualinner:" + jsonObject);
                     startSpinwheel(false, true);
                     CommandFactory commandFactory = new CommandFactory();
                     commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_INDIVIDUAL_INNER_SEARCH, jsonObject);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(isfirsttime) {
             itemSelectedLocation = spinnerLocation.getSelectedItem().toString();
            String itemSelectedSort = spinnerSort.getSelectedItem().toString();
            if (!itemSelectedLocation.equals(getString(R.string.txt_nationality))) {
                fetchFilterSearchData();
                spinnerLocationSate = true;
            } else {
                itemSelectedLocation ="";
                fetchFilterSearchData();
                if (spinnerLocationSate) {
                    spinnerLocationSate = false;

                    fetchConstructionIndividualInnerData();
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

    @Override
    public void onConstructionIndividualInnerSearchLoadedSuccessfully(JSONObject jsonObject) {
        super.onConstructionIndividualInnerSearchLoadedSuccessfully(jsonObject);
        Log.e(TAG, "Json Response:" + jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        parseSearchData(jsonObject);
    }

    private void parseSearchData(JSONObject jsonObject) {
        Log.d("searchRes",jsonObject.toString());
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.DETAIL_LIST_ARR);
                    if (jsonArray.length() > 0) {
                        txtNoData.setVisibility(View.GONE);
                        individualList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            individualList.add(new ConstructionInnerData(jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_NAME), jsonArray.getJSONObject(i).getString(AppConstants.AVERAGE_RATING),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PERSONS_RATED), jsonArray.getJSONObject(i).getString(AppConstants.PROFILE_PHOTO),
                                    jsonArray.getJSONObject(i).getString(AppConstants.LOCATION)));
                        }
                        mAdapter = new ConstructionInnerListAdapter(Activity, individualList,Type);
                        recycViewConstruction.setAdapter(mAdapter);
                        //txtResultCount.setText("("+String.valueOf(individualList.size()+" "+"Results Found)"));
                       // txtResultCount.setText("(" + getString(R.string.txt_result_found, individualList.size()) + ")");
                        txtResultCount.setText("("+individualList.size()+" "+getString(R.string.txt_result_found)+")");
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

    @Override
    public void onConstructionIndividualInnerSearchLoadedFailed(JSONObject jsonObject) {
        super.onConstructionIndividualInnerSearchLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }


}
