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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.ConstructionInnerListAdapter;
import com.mawaqaa.homepluskw_new.adapter.ConstructionListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.ConstructionData;
import com.mawaqaa.homepluskw_new.data.ConstructionInnerData;
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
 * Created by JijoCJ on 11/10/2016.
 */
public class ConstructionIndividualFrag extends HomePlusBaseFragment implements View.OnClickListener {

    private static final String TAG = "ConstructionIndvdlFrag";
    ImageButton ImgBtnSearch_construction;
    EditText etSearchInConstnIndividual, etSearchConstnCorporate;
    Button btnTabIndividual, btnTabCorporate;

    LinearLayout linearLaySearchInIndividual, linearLayFilterSearchInCorporate, linearLaySortInCorporate;
    TextView txtNoData;
    RecyclerView recycViewConstruction, recycViewConstruction2;
    List<ConstructionInnerData> individualListproduct;

    List<ConstructionData> individualList;
    ConstructionListAdapter mAdapter;
    ConstructionInnerListAdapter mAdapter2;
    String Type = "Individual";

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
        View view = inflater.inflate(R.layout.fragment_construction, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.txt_construction_title_action_bar));
        initView(view);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.constructionButtonState();
        return view;
    }

    private void initView(View view) {
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ARABIC)) {
            txtNoData = (TextView) view.findViewById(R.id.txtNoData);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtNoData);
            etSearchInConstnIndividual = (EditText) view.findViewById(R.id.etSearchInConstnIndividual);
            FontUtils.setHomeplusArabicRegularFont(Activity, etSearchInConstnIndividual);
            etSearchConstnCorporate = (EditText) view.findViewById(R.id.etSearchConstnCorporate);
            FontUtils.setHomeplusArabicRegularFont(Activity, etSearchConstnCorporate);
            btnTabIndividual = (Button) view.findViewById(R.id.btnTabIndividual);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabIndividual);
            btnTabCorporate = (Button) view.findViewById(R.id.btnTabCorporate);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabCorporate);
        } else {
            txtNoData = (TextView) view.findViewById(R.id.txtNoData);
            FontUtils.setHomeplusRegularFont(Activity, txtNoData);
            etSearchInConstnIndividual = (EditText) view.findViewById(R.id.etSearchInConstnIndividual);
            FontUtils.setHomeplusRegularFont(Activity, etSearchInConstnIndividual);
            etSearchConstnCorporate = (EditText) view.findViewById(R.id.etSearchConstnCorporate);
            FontUtils.setHomeplusRegularFont(Activity, etSearchConstnCorporate);
            btnTabIndividual = (Button) view.findViewById(R.id.btnTabIndividual);
            FontUtils.setHomeplusRegularFont(Activity, btnTabIndividual);
            btnTabCorporate = (Button) view.findViewById(R.id.btnTabCorporate);
            FontUtils.setHomeplusRegularFont(Activity, btnTabCorporate);
        }
        // linearLaySearchInIndividual = (LinearLayout) view.findViewById(R.id.linearLaySearchInIndividual);
        ImgBtnSearch_construction = (ImageButton) view.findViewById(R.id.imgBtnSearch_construction);
        linearLayFilterSearchInCorporate = (LinearLayout) view.findViewById(R.id.linearLayFilterSearchInCorporate);
        linearLaySortInCorporate = (LinearLayout) view.findViewById(R.id.linearLaySortInCorporate);

        recycViewConstruction = (RecyclerView) view.findViewById(R.id.recycViewConstruction);
        recycViewConstruction2 = (RecyclerView) view.findViewById(R.id.recycViewConstruction2);
        //recycViewRealEstate.setHasFixedSize(true);
        recycViewConstruction.setLayoutManager(new GridLayoutManager(Activity, 2));
        recycViewConstruction2.setLayoutManager(new GridLayoutManager(Activity, 2));
        //recycViewRealEstate.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            btnTabIndividual.setSelected(true);
            btnTabCorporate.setSelected(false);
        } else {
            btnTabIndividual.setSelected(true);
            btnTabCorporate.setSelected(false);
        }

        //linearLaySearchInIndividual.setVisibility(View.VISIBLE);
        linearLayFilterSearchInCorporate.setVisibility(View.GONE);
        linearLaySortInCorporate.setVisibility(View.GONE);

        btnTabIndividual.setOnClickListener(this);
        btnTabCorporate.setOnClickListener(this);
        ImgBtnSearch_construction.setOnClickListener(this);
        etSearchInConstnIndividual.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG, "Enter pressed");
                    searchConstructionIndividual();
                }
                return false;
            }
        });
        // fetch Real Estate Data From Server
        if (Activity.isNetworkAvailable())
            fetchConstructionIndividualData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void fetchConstructionIndividualData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SEARCHKEYWORD, etSearchInConstnIndividual.getText().toString());
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            Log.e(TAG, "Json Request:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);
            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_INDIVIDUAL, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseConstructionIndividualData(JSONObject jsonObject) {
        Log.d("jsonREs",jsonObject.toString());
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.CONSTRUCTION_INDIVIDUAL_ARR);
                    if (jsonArray.length() > 0) {
                        individualList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            individualList.add(new ConstructionData(jsonArray.getJSONObject(i).getString(AppConstants.ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_SERVICE_IMAGE), jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_SERVICE_TITLE)));
                        }
                        mAdapter = new ConstructionListAdapter(Activity, individualList);
                        recycViewConstruction.setAdapter(mAdapter);

                        // creating an object of interface 'OnItemClickListener' in 'ConstructionListAdapter'
                        mAdapter.setOnItemClickListener(new ConstructionListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                ConstructionData constructionData = individualList.get(position);
                                Activity.pushFragments(ConstrctnIndividualInnerFrag.newInstance(constructionData.getId(), constructionData.getTitle()), false, true);
                            }
                        });
                        recycViewConstruction.setVisibility(View.VISIBLE);
                        txtNoData.setVisibility(View.GONE);
                        etSearchInConstnIndividual.setText("");
                    } else {
                        recycViewConstruction.setVisibility(View.GONE);
                        txtNoData.setVisibility(View.VISIBLE);
                    }

                    JSONArray jsonArray2 = jsonObject.getJSONArray(AppConstants.DETAIL_LIST_ARR);
                    Log.d("DeatailsSearch", jsonArray2.toString());
                    if (jsonArray2.length() > 0) {
                        recycViewConstruction2.setVisibility(View.VISIBLE);
                        individualListproduct = new ArrayList<>();
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            individualListproduct.add(new ConstructionInnerData(jsonArray2.getJSONObject(i).getString(AppConstants.CONSTRUCTION_ID),
                                    jsonArray2.getJSONObject(i).getString(AppConstants.CONSTRUCTION_NAME), jsonArray2.getJSONObject(i).getString(AppConstants.AVERAGE_RATING),
                                    jsonArray2.getJSONObject(i).getString(AppConstants.PERSONS_RATED), jsonArray2.getJSONObject(i).getString(AppConstants.PROFILE_PHOTO),
                                    jsonArray2.getJSONObject(i).getString(AppConstants.LOCATION)));
                        }
                        mAdapter2 = new ConstructionInnerListAdapter(Activity, individualListproduct, Type);
//                        recycViewConstruction.swapAdapter(mAdapter2,true);
                        recycViewConstruction2.setAdapter(mAdapter2);
                        mAdapter2.setOnItemClickListener(new ConstructionInnerListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                ConstructionInnerData innerData = individualListproduct.get(position);
                                Activity.pushFragments(ConstrctnIndividualDetailsFrag.newInstance(innerData.getId(), false), false, true);

                            }
                        });
                    } else {
                        recycViewConstruction2.setVisibility(View.GONE);
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
    public void onConstructionIndividualDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onConstructionIndividualDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseConstructionIndividualData(jsonObject);
    }

    @Override
    public void onConstructionIndividualDataLoadedFailed(JSONObject jsonObject) {
        super.onConstructionIndividualDataLoadedFailed(jsonObject);
//        stopSpinWheel();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTabIndividual:
                //sendRegistrationData();
                break;
            case R.id.btnTabCorporate:
                Activity.pushFragments(new ConstructionCorporateFrag(), false, true);
                break;
            case R.id.imgBtnSearch_construction:
                searchConstructionIndividual();
                break;
            default:
                break;
        }
    }

    private void searchConstructionIndividual() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SEARCHKEYWORD, etSearchInConstnIndividual.getText().toString().trim());
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            Log.e(TAG, "Json Request:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);
            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                String search = etSearchInConstnIndividual.getText().toString().trim();
                if (search.isEmpty()){
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_INDIVIDUAL, jsonObject);
                }else{
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_INDIVIDUAL_NEW, jsonObject);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

