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
import android.widget.RatingBar;
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
 * Created by JijoCJ on 11/12/2016.
 */
public class ConstructionCorporateFrag extends HomePlusBaseFragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    String search_key;
    private static final String TAG = "ConstrnCorporateFrag";
    ImageButton ImgBtnSearch_construction;
    TextView txtRealEstateType, txtResultCount, txtNoData;
    EditText etSearchConstnCorporate;
    Button btnTabIndividual, btnTabCorporate;


    LinearLayout linearLaySearchInIndividual, linearLayFilterSearchInCorporate, linearLaySortInCorporate;
    String Type = "Corporate";

    RecyclerView recycViewConstruction, recycViewConstruction2;
    View viewDividerInCorporateTop;

    List<ConstructionData> corporateList;
    ConstructionListAdapter mAdapter;

    List<ConstructionInnerData> corporateList2;

    ConstructionInnerListAdapter mAdapter2;

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
        ((HomePlusMainActivity)
                getActivity()).showBottomBar();
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

        if (SessionForRecycleView.constrctnCorporatePostion == -1) {

        } else {
            recycViewConstruction.scrollToPosition(SessionForRecycleView.constrctnCorporatePostion);
        }

        return view;
    }

    private void initView(View view) {
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ARABIC)) {
            txtRealEstateType = (TextView) view.findViewById(R.id.txtRealEstateType);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtRealEstateType);
            txtNoData = (TextView) view.findViewById(R.id.txtNoData);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtNoData);
            txtResultCount = (TextView) view.findViewById(R.id.txtResultCount);
            FontUtils.setHomeplusArabicRegularFont(Activity, txtResultCount);
            txtResultCount.setSelected(true);

            etSearchConstnCorporate = (EditText) view.findViewById(R.id.etSearchInConstnIndividual);
            FontUtils.setHomeplusArabicRegularFont(Activity, etSearchConstnCorporate);
            btnTabIndividual = (Button) view.findViewById(R.id.btnTabIndividual);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabIndividual);
            btnTabCorporate = (Button) view.findViewById(R.id.btnTabCorporate);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabCorporate);
        } else {
            txtRealEstateType = (TextView) view.findViewById(R.id.txtRealEstateType);
            FontUtils.setHomeplusRegularFont(Activity, txtRealEstateType);
            txtNoData = (TextView) view.findViewById(R.id.txtNoData);
            FontUtils.setHomeplusRegularFont(Activity, txtNoData);
            txtResultCount = (TextView) view.findViewById(R.id.txtResultCount);
            FontUtils.setHomeplusRegularFont(Activity, txtResultCount);
            txtResultCount.setSelected(true);

            etSearchConstnCorporate = (EditText) view.findViewById(R.id.etSearchInConstnIndividual);
            FontUtils.setHomeplusRegularFont(Activity, etSearchConstnCorporate);
            btnTabIndividual = (Button) view.findViewById(R.id.btnTabIndividual);
            FontUtils.setHomeplusRegularFont(Activity, btnTabIndividual);
            btnTabCorporate = (Button) view.findViewById(R.id.btnTabCorporate);
            FontUtils.setHomeplusRegularFont(Activity, btnTabCorporate);
        }
        //linearLaySearchInIndividual = (LinearLayout) view.findViewById(R.id.linearLaySearchInIndividual);
        ImgBtnSearch_construction = (ImageButton) view.findViewById(R.id.imgBtnSearch_construction);
        linearLayFilterSearchInCorporate = (LinearLayout) view.findViewById(R.id.linearLayFilterSearchInCorporate);
        linearLaySortInCorporate = (LinearLayout) view.findViewById(R.id.linearLaySortInCorporate);

        viewDividerInCorporateTop = (View) view.findViewById(R.id.viewDividerInCorporateTop);

        recycViewConstruction = (RecyclerView) view.findViewById(R.id.recycViewConstruction);
        recycViewConstruction2 = (RecyclerView) view.findViewById(R.id.recycViewConstruction2);
        //recycViewRealEstate.setHasFixedSize(true);

        recycViewConstruction.setLayoutManager(new GridLayoutManager(Activity, 2));
        recycViewConstruction2.setLayoutManager(new GridLayoutManager(Activity, 2));
        //recycViewRealEstate.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            btnTabIndividual.setSelected(false);
            btnTabCorporate.setSelected(true);
        } else {
            btnTabIndividual.setSelected(false);
            btnTabCorporate.setSelected(true);
        }

        // linearLaySearchInIndividual.setVisibility(View.VISIBLE);
        linearLayFilterSearchInCorporate.setVisibility(View.GONE);
        linearLaySortInCorporate.setVisibility(View.GONE);
        viewDividerInCorporateTop.setVisibility(View.GONE);

        btnTabIndividual.setOnClickListener(this);
        btnTabCorporate.setOnClickListener(this);
        ImgBtnSearch_construction.setOnClickListener(this);
        etSearchConstnCorporate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG, "Enter pressed");
                    serachConstructionCorporate();
                }
                return false;
            }
        });
        // fetch Construction Data From Server
        if (Activity.isNetworkAvailable())
            fetchConstructionCorporateData();
        else
            Toast.makeText(Activity, getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
    }

    private void fetchConstructionCorporateData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SEARCHKEYWORD, etSearchConstnCorporate.getText().toString());
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            Log.e(TAG, "Json Request:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseConstructionCorporateData(JSONObject jsonObject) {
        Log.d("searchREs", jsonObject.toString());
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.CORPORATE_ARR);
                    if (jsonArray.length() > 0) {
                        corporateList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            corporateList.add(new ConstructionData(jsonArray.getJSONObject(i).getString(AppConstants.ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_SERVICE_IMAGE), jsonArray.getJSONObject(i).getString(AppConstants.CONSTRUCTION_SERVICE_TITLE)));
                        }
                        mAdapter = new ConstructionListAdapter(Activity, corporateList);

//                        recycViewConstruction.setNestedScrollingEnabled(false);
                        recycViewConstruction.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new ConstructionListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                ConstructionData constructionData = corporateList.get(position);
                                SessionForRecycleView.constrctnCorporatePostion = position;
                                Activity.pushFragments(ConstrctnCorporateInnerFrag.newInstance(constructionData.getId(), constructionData.getTitle()), false, true);
                            }
                        });
                        etSearchConstnCorporate.setText("");
                        recycViewConstruction.setVisibility(View.VISIBLE);
                        txtNoData.setVisibility(View.GONE);
                    } else {
                        recycViewConstruction.setVisibility(View.GONE);
                        txtNoData.setVisibility(View.VISIBLE);
                    }

                    JSONArray jsonArray2 = jsonObject.getJSONArray(AppConstants.DETAIL_LIST_ARR);
                    Log.d("DeatailsSearch", jsonArray2.toString());
                    if (jsonArray2.length() > 0) {
                        recycViewConstruction2.setVisibility(View.VISIBLE);
                        corporateList2 = new ArrayList<>();
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            corporateList2.add(new ConstructionInnerData(jsonArray2.getJSONObject(i).getString(AppConstants.CONSTRUCTION_ID),
                                    jsonArray2.getJSONObject(i).getString(AppConstants.COMPANY_NAME), jsonArray2.getJSONObject(i).getString(AppConstants.AVERAGE_RATING),
                                    jsonArray2.getJSONObject(i).getString(AppConstants.PERSONS_RATED), jsonArray2.getJSONObject(i).getString(AppConstants.FEATURED_IMAGE),
                                    jsonArray2.getJSONObject(i).getString(AppConstants.COMPANY_LOCATION)));
                        }
                        mAdapter2 = new ConstructionInnerListAdapter(Activity, corporateList2, Type);
//                        recycViewConstruction.swapAdapter(mAdapter2,true);
                        recycViewConstruction2.setAdapter(mAdapter2);
                        mAdapter2.setOnItemClickListener(new ConstructionInnerListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                ConstructionInnerData innerData = corporateList2.get(position);
                                SessionForRecycleView.constrctnCorporateInnerPostion = position;
                                Activity.pushFragments(ConstrctnCorporateDetailsFrag.newInstance(innerData.getId(), false), false, true);

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
        } finally {
            BaseApplication.getInstance().progressOFF();
        }
    }

    @Override
    public void onConstructionCorporateDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onConstructionCorporateDataLoadedSuccessfully(jsonObject);
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseConstructionCorporateData(jsonObject);
    }

    @Override
    public void onConstructionCorporateDataLoadedFailed(JSONObject jsonObject) {
        super.onConstructionCorporateDataLoadedFailed(jsonObject);
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
            case R.id.imgBtnSearch_construction:
                serachConstructionCorporate();
                break;

            default:
                break;
        }
    }

    private void serachConstructionCorporate() {
        search_key = etSearchConstnCorporate.getText().toString().trim();
        Log.e(TAG, "searchkey:" + search_key);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
            jsonObject.putOpt(AppConstants.SEARCHKEYWORD, search_key);
            jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
            Log.e(TAG, "Json Request:" + jsonObject);
//            startSpinwheel(false, true);
            BaseApplication.getInstance().progressON(getActivity(), null);

            if (VolleyUtils.volleyEnabled) {
                CommandFactory commandFactory = new CommandFactory();
                Log.e("urlSearch:", AppConstants.HP_CONSTRUCTION_CORPRATE);
//                commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE, jsonObject);
                if (search_key.isEmpty()) {
                    Log.e("urlSearch:", AppConstants.HP_CONSTRUCTION_CORPRATE);
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE, jsonObject);
                } else {
                    Log.e("urlSearch:", AppConstants.HP_CONSTRUCTION_CORPRATE_NEW);
                    commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_NEW, jsonObject);
                }

            }
//            HP_CONSTRUCTION_CORPRATE
        } catch (Exception e) {
            e.printStackTrace();
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
}