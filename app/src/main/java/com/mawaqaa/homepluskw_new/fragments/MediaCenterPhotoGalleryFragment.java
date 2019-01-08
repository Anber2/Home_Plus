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
import com.mawaqaa.homepluskw_new.adapter.MediaCenterPhotoGalleryAdapter;
import com.mawaqaa.homepluskw_new.adapter.MonthaAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.MediaCenterPhotoGalleryData;
import com.mawaqaa.homepluskw_new.data.Monthdata;
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
 * Created by JijoCJ on 12/12/2016.
 */
public class MediaCenterPhotoGalleryFragment extends HomePlusBaseFragment implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private static final String TAG = "MediaPhotoGalryFragment";
    boolean spinnerLocationSate = false;
    int i=0;
    TextView txtTitleOnMediaCenter, txtNoData;
    Spinner spinnerMonth, spinnerYear;
    Button btnTabPhotoGallery, btnTabNews, btnTabVideoGallery;
    Monthdata monthdata;
    private LinkedHashMap<String, String> monthMap;
    private LinkedHashMap<String, String> YearMap;
    RecyclerView recycViewMediaCenter;
    private String [] arrYear;
    ArrayList<Monthdata> monthList;
    List<String> yearList;
    MonthaAdapter mArrayMonthAdapter;
    ArrayAdapter<String> mArrayYearAdapter;
    List<MediaCenterPhotoGalleryData> photoGalleryList;
    MediaCenterPhotoGalleryAdapter mediaCenterPhotoGalleryAdapter;
    Map<Integer, List> photoGalleryMap;

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
        Log.e(TAG, "User Id:"+ PreferenceUtil.getUserId(Activity));
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
        btnTabNews = (Button) view.findViewById(R.id.btnTabNews);
        btnTabVideoGallery = (Button) view.findViewById(R.id.btnTabVideoGallery);
        fetchPhotoGalleryListData();
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)){
            FontUtils.setHomeplusRegularFont(Activity,btnTabPhotoGallery);
            FontUtils.setHomeplusRegularFont(Activity,btnTabNews);
            FontUtils.setHomeplusRegularFont(Activity,btnTabVideoGallery);
            FontUtils.setHomeplusRegularFont(Activity,txtTitleOnMediaCenter);
            FontUtils.setHomeplusRegularFont(Activity,txtNoData);
            btnTabPhotoGallery.setSelected(true);
            btnTabNews.setSelected(false);
            btnTabVideoGallery.setSelected(false);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity,btnTabPhotoGallery);
            FontUtils.setHomeplusArabicRegularFont(Activity,btnTabNews);
            FontUtils.setHomeplusArabicRegularFont(Activity,btnTabVideoGallery);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtTitleOnMediaCenter);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtNoData);
            btnTabPhotoGallery.setSelected(true);
            btnTabNews.setSelected(false);
            btnTabVideoGallery.setSelected(false);

        }
        btnTabPhotoGallery.setOnClickListener(this);
        btnTabNews.setOnClickListener(this);
        btnTabVideoGallery.setOnClickListener(this);
       spinnerMonth.setOnItemSelectedListener(this);
        spinnerYear.setOnItemSelectedListener(this);

    }


    private void fetchPhotoGalleryListData() {
        try {
            if(Activity.isNetworkAvailable()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                Log.e(TAG, "Json Req:"+jsonObject);
//                startSpinwheel(false, true);
                BaseApplication.getInstance().progressON(getActivity(), null);

                if(VolleyUtils.volleyEnabled){
                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_PHOTO_GALLERY_lIST_DATA, jsonObject);
                }
            }else
                Toast.makeText(Activity, getResources().getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMediaPhotoGalleryDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onMediaPhotoGalleryDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:"+jsonObject);
        parseJsonPhotoGalleryResponse(jsonObject);
    }

    private void parseJsonPhotoGalleryResponse(JSONObject jsonObject) {
        try {
            if(jsonObject!= null){
                if(jsonObject.getBoolean(AppConstants.IS_SUCCESS)){

                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.PHOTO_GALLERY_lIST);
                    if(jsonArray.length() > 0){
                        photoGalleryList = new ArrayList<>();
                        photoGalleryMap = new LinkedHashMap<>();
                        for(int i = 0; i < jsonArray.length(); i++){
                            photoGalleryList.add(new MediaCenterPhotoGalleryData(jsonArray.getJSONObject(i).getString(AppConstants.PHOTO_GALLERY_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_IMAGE), jsonArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_TITLE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_DATE), jsonArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_COVER_PHOTO)));

                            JSONArray photoGalleryArray = jsonArray.getJSONObject(i).getJSONArray(AppConstants.PHOTO_ALBUM_PHOTO_ARR);
                            List<String> photoList = new ArrayList<String>();
                            for(int j = 0 ; j < photoGalleryArray.length(); j++){
                                photoList.add(photoGalleryArray.getJSONObject(j).getString(AppConstants.PHOTO_GALLERY_ID));
                            }
                            photoGalleryMap.put(i, photoList);
                        }
                        mediaCenterPhotoGalleryAdapter = new MediaCenterPhotoGalleryAdapter(Activity, photoGalleryList);
                        recycViewMediaCenter.setAdapter(mediaCenterPhotoGalleryAdapter);

                        mediaCenterPhotoGalleryAdapter.setOnItemClickListener(new MediaCenterPhotoGalleryAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                MediaCenterPhotoGalleryData photoGalleryData = photoGalleryList.get(position);
                                Activity.pushFragments(MediaCenterPhotoInnerGalleryFrag.newInstance(photoGalleryMap.get(position), photoGalleryData.getAlbumTitle()), false, true);
                            }
                        });
                    }else{
                        txtNoData.setVisibility(View.VISIBLE);
                    }

                    JSONArray jsonMonthArray = jsonObject.getJSONArray(AppConstants.PHOTO_ALBUM_MONTH_LIST);
                    Log.e("Month List Length", "MediaAnnounce:"+jsonArray.length());
                    if(jsonMonthArray.length() > 0) {
                        monthMap = new LinkedHashMap<>();
                        monthMap.put(getString(R.string.spinner_title_txt_month),"");
                        for (int i = 0; i < jsonMonthArray.length(); i++) {
                            monthMap.put(jsonMonthArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_TEXT), jsonMonthArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_VALUE));
                        }
                        Set<String> monthSet = monthMap.keySet();
                        String[] arrMonth = monthSet.toArray(new String[monthSet.size()]);
                        mArrayMonthAdapter = new MonthaAdapter(Activity, new ArrayList<String>(monthSet));
                        spinnerMonth.setAdapter(mArrayMonthAdapter);
                    }
                    else
                    {
                        monthMap = new LinkedHashMap<>();
                        monthMap.put(getString(R.string.spinner_title_txt_month),"");
                        Set<String> monthSet = monthMap.keySet();
                        String[] arrMonth = monthSet.toArray(new String[monthSet.size()]);
                        mArrayMonthAdapter = new MonthaAdapter(Activity, new ArrayList<String>(monthSet));
                        spinnerMonth.setAdapter(mArrayMonthAdapter);
                    }


                    JSONArray jsonYearArray = jsonObject.getJSONArray(AppConstants.PHOTO_ALBUM_YEAR_LIST);
                    if(jsonYearArray.length() > 0){
                        YearMap=new LinkedHashMap<>();
                        YearMap.put(getString(R.string.txt_year),"");
                        for(int i=0;i<jsonYearArray.length();i++) {
                            YearMap.put(jsonYearArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_TEXT),
                                    jsonYearArray.getJSONObject(i).getString(AppConstants.PHOTO_ALBUM_FILTER_VALUE));
                        }
                        Set<String>yearSet=YearMap.keySet();
                        mArrayMonthAdapter = new MonthaAdapter(Activity,  new ArrayList<String>((yearSet)));
                        spinnerYear.setAdapter(mArrayMonthAdapter);
                    }
                    else
                    {
                        YearMap=new LinkedHashMap<>();
                        YearMap.put(getString(R.string.txt_year),"");
                        Set<String>yearSet=YearMap.keySet();
                        mArrayMonthAdapter = new MonthaAdapter(Activity,  new ArrayList<String>((yearSet)));
                        spinnerYear.setAdapter(mArrayMonthAdapter);
                    }

                }else
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
            }else{

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMediaPhotoGalleryDataLoadedFailed(JSONObject jsonObject) {
        super.onMediaPhotoGalleryDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTabPhotoGallery:
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
        Log.e("Jijo","Onselected event Month");
        if(!spinnerMonth.getSelectedItem().equals(getString(R.string.spinner_title_txt_month))&& !spinnerYear.getSelectedItem().equals(getString(R.string.txt_year)))
        {
            fetchFilterSearchData();



        }/*else{
            if(spinnerLocationSate){
                spinnerLocationSate = false;
                fetchPhotoGalleryListData();
            }

        }*/
    }
//  jsonObject.putOpt(AppConstants.MEDIA_ANNOUNCE_MONTH, monthMap.get(spinnerMonth.getSelectedItem().toString()).toString());
    private void fetchFilterSearchData() {

        String selectedyear=spinnerYear.getSelectedItem().toString();

            Log.e("selectlocation",""+monthMap.get(spinnerMonth.getSelectedItem().toString()).toString());
            Log.e("selectlocationyr",""+selectedyear);
            if(Activity.isNetworkAvailable()){
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt(AppConstants.LANGUAGE_KEY, PreferenceUtil.getLanguage(Activity));
                    jsonObject.putOpt(AppConstants.SECURITY_KEY, AppConstants.HP_SECURITY_KEY_VALUE);
                    jsonObject.putOpt(AppConstants.PHOTOGALLERYMONTH, monthMap.get(spinnerMonth.getSelectedItem().toString()).toString());
                    jsonObject.putOpt(AppConstants.PHOTOGALLERYYEAR,selectedyear);
                    Log.e(TAG, "Json Request:"+jsonObject);
//                    startSpinwheel(false, true);
                    BaseApplication.getInstance().progressON(getActivity(), null);

                    CommandFactory commandFactory = new CommandFactory();
                    commandFactory.sendPostCommand(AppConstants.HP_PHOTOSEARCH, jsonObject);
                    //commandFactory.sendPostCommand(AppConstants.HP_CONSTRUCTION_CORPRATE_INNER, jsonObject);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void onPhotoGalleryFilterLoadedSuccessfully(JSONObject jsonObject) {
        super.onPhotoGalleryFilterLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "JsonresponseFilter:"+jsonObject);
        parseJsonPhotoGalleryResponse(jsonObject);
    }

    @Override
    public void onPhotoGalleryFilterLoadedFailed(JSONObject jsonObject) {
        super.onPhotoGalleryFilterLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.e(TAG,"Nothing selected");
    }
}


