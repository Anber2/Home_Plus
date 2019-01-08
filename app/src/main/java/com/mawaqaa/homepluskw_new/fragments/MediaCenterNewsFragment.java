package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.MediaCenterNewsListAdapter;
import com.mawaqaa.homepluskw_new.adapter.MonthaAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.MediaCenterNewsListData;
import com.mawaqaa.homepluskw_new.data.RelatedImageDate;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.BaseApplication;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.volley.CommandFactory;
import com.mawaqaa.homepluskw_new.volley.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JijoCJ on 12/14/2016.
 */
public class MediaCenterNewsFragment extends HomePlusBaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "MediaCenterNewsFragment";

    TextView txtTitleOnMediaCenter, txtNoData;
    Spinner spinnerMonth, spinnerYear;
    Button btnTabPhotoGallery, btnTabNews, btnTabVideoGallery;
    RecyclerView recycViewMediaCenter;
    JSONObject news_json;
    List<String> monthList;
    List<String> yearList;

    ArrayAdapter<String> mArrayYearAdapter;
    List<MediaCenterNewsListData> newsDataList;
    MediaCenterNewsListAdapter mediaCenterNewsdapter;
    RelatedImageDate relatedimagedata;
    ArrayList<String> relatedimage_array;
    Map<Integer, List> photoGalleryMap;
    //Hash map for month and year for getting spinner value
    private LinkedHashMap<String, String> monthMap;
    private LinkedHashMap<String, String> YearMap;
    MonthaAdapter mArrayMonthAdapter;

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
        View view = inflater.inflate(R.layout.fragment_media_center, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        ((HomePlusMainActivity) getActivity()).setTxtInActionBarTitle(getResources().getString(R.string.media_center));
        Log.e(TAG, "User Id:" + PreferenceUtil.getUserId(Activity));
        initView(view);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.MoreButtonState();
        return view;
    }

    private void initView(View view) {

        txtTitleOnMediaCenter = (TextView) view.findViewById(R.id.txtTitleOnMediaCenter);
        txtNoData = (TextView) view.findViewById(R.id.txtNoData);

        spinnerMonth = (Spinner) view.findViewById(R.id.spinnerMonth);
        spinnerYear = (Spinner) view.findViewById(R.id.spinnerYear);

        recycViewMediaCenter = (RecyclerView) view.findViewById(R.id.recycViewMediaCenter);
        recycViewMediaCenter.setLayoutManager(new LinearLayoutManager(Activity));
        btnTabPhotoGallery = (Button) view.findViewById(R.id.btnTabPhotoGallery);
        btnTabVideoGallery = (Button) view.findViewById(R.id.btnTabVideoGallery);
        btnTabNews = (Button) view.findViewById(R.id.btnTabNews);
        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity, btnTabPhotoGallery);
            FontUtils.setHomeplusRegularFont(Activity, btnTabNews);
            FontUtils.setHomeplusRegularFont(Activity, btnTabVideoGallery);
            btnTabPhotoGallery.setSelected(false);
            btnTabNews.setSelected(true);
            btnTabVideoGallery.setSelected(false);
        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabPhotoGallery);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabNews);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabVideoGallery);
            btnTabPhotoGallery.setSelected(false);
            btnTabNews.setSelected(true);
            btnTabVideoGallery.setSelected(false);
        }
        btnTabPhotoGallery.setOnClickListener(this);
        btnTabNews.setOnClickListener(this);
        btnTabVideoGallery.setOnClickListener(this);
        spinnerMonth.setOnItemSelectedListener(this);
        spinnerYear.setOnItemSelectedListener(this);
        txtTitleOnMediaCenter.setText(getResources().getString(R.string.btn_txt_photo_news));

        fetchNewsListData();
    }

    private void fetchNewsListData() {
        try {
            if (Activity.isNetworkAvailable()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                Log.e(TAG, "Json Req:" + jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);
                if (VolleyUtils.volleyEnabled) {
                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_NEWS_lIST_DATA, jsonObject);
                }
            } else
                Toast.makeText(Activity, getResources().getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMediaNewsDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onMediaNewsDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseJsonNewsListResponse(jsonObject);
    }

    private void parseJsonNewsListResponse(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.NEWS_LIST);
                    if (jsonArray.length() > 0) {
                        newsDataList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            news_json = jsonArray.getJSONObject(i);


                            newsDataList.add(new MediaCenterNewsListData(jsonArray.getJSONObject(i).getString(AppConstants.PHOTO_GALLERY_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.NEWS_IMAGE), jsonArray.getJSONObject(i).getString(AppConstants.NEWS_DATE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.NEWS_DESCRIPTION), jsonArray.getJSONObject(i).getString(AppConstants.NEWS_TITLE), jsonArray.getJSONObject(i).getString(AppConstants.NEWS_IMAGE)));
                            JSONArray json_related_images = news_json.getJSONArray(AppConstants.RELATEDLIST_IMAGE);
                            relatedimage_array = new ArrayList<>();
                            for (int j = 0; j < json_related_images.length(); j++) {

                                relatedimage_array.add(json_related_images.getString(j));
                                Log.e("relatedimageArray", "" + relatedimage_array.get(j));
                            }


                            //JSONArray newsGalleryArray = jsonArray.getJSONObject(i).getJSONArray(AppConstants.RELATEDLIST_IMAGE);
                           /* List<String> photoList = new ArrayList<>();
                            for(int j = 0 ; j < newsGalleryArray.length(); j++){
                                photoList.add(newsGalleryArray.getString());
                            }
                            photoGalleryMap.put(i, photoList);
*/
                        }
                        mediaCenterNewsdapter = new MediaCenterNewsListAdapter(Activity, newsDataList);
                        recycViewMediaCenter.setAdapter(mediaCenterNewsdapter);
                        mediaCenterNewsdapter.setOnItemClickListener(new MediaCenterNewsListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                MediaCenterNewsListData mediaCenterNewsListData = newsDataList.get(position);
                                Activity.pushFragments(NewsInnerDetailsFragment.newInstance(mediaCenterNewsListData.getId(), mediaCenterNewsListData.getNewsDate(),
                                        mediaCenterNewsListData.getNewsDescription(), mediaCenterNewsListData.getNewsTitle(), relatedimage_array, mediaCenterNewsListData.getNewsImage(), true), false, true);

                            }
                        });
                      /*  mediaCenterNewsdapter.setOnItemClickListener(new SpecialOfferRealEstateAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                SpecialOfferRealEstateData specialOfferRealEstateData = specialOfferRealEstateList.get(position);
                                Activity.pushFragments(RealEstateDeatailsInnerFragment.newInstance(specialOfferRealEstateData.getId(), true), false, true);

                            }
                        });
                       */

                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                    }

                    JSONArray jsonMonthArray = jsonObject.getJSONArray(AppConstants.NEWS_MONTH);
                    monthList = new ArrayList<>();
                    monthList.add(getResources().getString(R.string.spinner_title_txt_month));
                    if (jsonMonthArray.length() > 0) {
                        monthMap = new LinkedHashMap<>();
                        monthMap.put(getString(R.string.spinner_title_txt_month), "");
                        for (int i = 0; i < jsonMonthArray.length(); i++) {
                            monthMap.put(jsonMonthArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_TEXT), jsonMonthArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_VALUE));
                        }
                        Set<String> monthSet = monthMap.keySet();
                        String[] arrMonth = monthSet.toArray(new String[monthSet.size()]);
                        mArrayMonthAdapter = new MonthaAdapter(Activity, new ArrayList<String>(monthSet));
                        spinnerMonth.setAdapter(mArrayMonthAdapter);
                    } else {
                        monthMap = new LinkedHashMap<>();
                        monthMap.put(getString(R.string.spinner_title_txt_month), "");
                        Set<String> monthSet = monthMap.keySet();
                        String[] arrMonth = monthSet.toArray(new String[monthSet.size()]);
                        mArrayMonthAdapter = new MonthaAdapter(Activity, new ArrayList<String>(monthSet));
                        spinnerMonth.setAdapter(mArrayMonthAdapter);
                    }

                    JSONArray jsonYearArray = jsonObject.getJSONArray(AppConstants.NEWS_YEAR);
                    if (jsonYearArray.length() > 0) {
                        YearMap = new LinkedHashMap<>();
                        YearMap.put(getString(R.string.txt_year), "");
                        for (int i = 0; i < jsonYearArray.length(); i++) {
                            YearMap.put(jsonYearArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_TEXT),
                                    jsonYearArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_VALUE));
                        }
                        Set<String> yearSet = YearMap.keySet();
                        mArrayMonthAdapter = new MonthaAdapter(Activity, new ArrayList<String>((yearSet)));
                        spinnerYear.setAdapter(mArrayMonthAdapter);
                    } else {
                        YearMap = new LinkedHashMap<>();
                        YearMap.put(getString(R.string.txt_year), "");
                        Set<String> yearSet = YearMap.keySet();
                        mArrayMonthAdapter = new MonthaAdapter(Activity, new ArrayList<String>((yearSet)));
                        spinnerYear.setAdapter(mArrayMonthAdapter);
                    }

                    /*yearList = new ArrayList<>();
                    if(jsonYearArray.length() > 0){
                        for(int i = 0; i < jsonYearArray.length(); i++)
                            yearList.add(jsonYearArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_TEXT));
                        mArrayYearAdapter = new ArrayAdapter<String>(Activity, R.layout.spinner_country_item, R.id.txtSpinnerCountryItem, yearList);
                        spinnerYear.setAdapter(mArrayYearAdapter);
                    }*/

                } else
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
            } else {
                txtNoData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMediaNewsDataLoadedFailed(JSONObject jsonObject) {
        super.onMediaNewsDataLoadedFailed(jsonObject);
        Log.e(TAG, "Error" + jsonObject);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTabPhotoGallery:
                Activity.pushFragments(new MediaCenterPhotoGalleryFragment(), false, true);
                break;
            case R.id.btnTabNews:
                Activity.pushFragments(new MediaCenterNewsFragment(), false, true);
                break;
            case R.id.btnTabVideoGallery:
                Activity.pushFragments(new MediaCenterVideoGalleryFrag(), false, true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!spinnerMonth.getSelectedItem().equals(getString(R.string.spinner_title_txt_month)) && !spinnerYear.getSelectedItem().equals(getString(R.string.txt_year))) {
            fetchFilterSearchData();

        }
    }

    private void fetchFilterSearchData() {
        String selectedyear = spinnerYear.getSelectedItem().toString();

        Log.e("selectlocation", "" + monthMap.get(spinnerMonth.getSelectedItem().toString()).toString());
        Log.e("selectlocationyr", "" + selectedyear);
        if (Activity.isNetworkAvailable()) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                jsonObject.putOpt(AppConstants.PHOTOGALLERYMONTH, monthMap.get(spinnerMonth.getSelectedItem().toString()).toString());
                jsonObject.putOpt(AppConstants.PHOTOGALLERYYEAR, selectedyear);
                Log.e(TAG, "Json Request:" + jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                CommandFactory commandFactory = new CommandFactory();
                commandFactory.sendPostCommand(AppConstants.HP_NEWSFILTER, jsonObject);
                //commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onNewsFilterFilterLoadedSuccessfully(JSONObject jsonObject) {
        super.onNewsFilterFilterLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        parseJsonNewsListResponse(jsonObject);
    }

    @Override
    public void onNewsFilterFilterLoadedFailed(JSONObject jsonObject) {
        super.onNewsFilterFilterLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }
}



