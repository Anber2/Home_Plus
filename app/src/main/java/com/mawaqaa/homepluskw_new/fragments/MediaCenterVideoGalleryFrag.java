package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.MediaCenterVideoGalleryAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.MediaCenterVideoGalleryData;
import com.mawaqaa.homepluskw_new.data.MediaVideoGalleryInnerData;
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

/**
 * Created by JijoCJ on 12/14/2016.
 */
public class MediaCenterVideoGalleryFrag extends HomePlusBaseFragment implements View.OnClickListener {

    private static final String TAG = "MediaCentrVideoGlryFrag";

    TextView txtTitleOnMediaCenter, txtNoData;
    Spinner spinnerMonth, spinnerYear;
    Button btnTabPhotoGallery, btnTabNews, btnTabVideoGallery;

    RecyclerView recycViewMediaCenter;

    LinearLayout LinearLayFilterGallery;

    List<MediaCenterVideoGalleryData> videoGalleryList;
    MediaCenterVideoGalleryAdapter mediaCenterVideoGalleryAdapter;
    Map<Integer, List> videoGalleryMap;

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
        recycViewMediaCenter.setLayoutManager(new GridLayoutManager(Activity, 2));

        LinearLayFilterGallery = (LinearLayout) view.findViewById(R.id.LinearLayFilterGallery);
        LinearLayFilterGallery.setVisibility(View.GONE);

        btnTabPhotoGallery = (Button) view.findViewById(R.id.btnTabPhotoGallery);
        btnTabNews = (Button) view.findViewById(R.id.btnTabNews);
        btnTabVideoGallery = (Button) view.findViewById(R.id.btnTabVideoGallery);

        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity, btnTabPhotoGallery);
            FontUtils.setHomeplusRegularFont(Activity, btnTabNews);
            FontUtils.setHomeplusRegularFont(Activity, btnTabVideoGallery);
            btnTabPhotoGallery.setSelected(false);
            btnTabNews.setSelected(false);
            btnTabVideoGallery.setSelected(true);
        } else {
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabPhotoGallery);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabNews);
            FontUtils.setHomeplusArabicRegularFont(Activity, btnTabVideoGallery);
            btnTabPhotoGallery.setSelected(false);
            btnTabNews.setSelected(false);
            btnTabVideoGallery.setSelected(true);
            recycViewMediaCenter.setRotationY(180);
        }

        btnTabPhotoGallery.setOnClickListener(this);
        btnTabNews.setOnClickListener(this);
        btnTabVideoGallery.setOnClickListener(this);

        txtTitleOnMediaCenter.setText(getString(R.string.btn_txt_video_gallery));
        fetchVideoGalleryData();
    }

    private void fetchVideoGalleryData() {
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
                    commandFactory.sendPostCommand(AppConstants.HP_VIDEO_GALLERY_DATA, jsonObject);
                }
            } else
                Toast.makeText(Activity, getResources().getString(R.string.alert_no_internet), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMediaVideoGalleryDataLoadedSuccessfully(JSONObject jsonObject) {
        super.onMediaVideoGalleryDataLoadedSuccessfully(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Json Response:" + jsonObject);
        parseJsonVideoGalleryResponse(jsonObject);
    }

    private void parseJsonVideoGalleryResponse(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getBoolean(AppConstants.IS_SUCCESS)) {

                    JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.VIDEO_GALLERY_LIST);
                    if (jsonArray.length() > 0) {
                        videoGalleryList = new ArrayList<>();
                        videoGalleryMap = new LinkedHashMap<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            videoGalleryList.add(new MediaCenterVideoGalleryData(jsonArray.getJSONObject(i).getString(AppConstants.VIDEO_ALBUM_ID),
                                    jsonArray.getJSONObject(i).getString(AppConstants.VIDEO_ALBUM_NAME), jsonArray.getJSONObject(i).getString(AppConstants.VIDEO_GALLERY_DATE),
                                    jsonArray.getJSONObject(i).getString(AppConstants.VIDEO_COVER_IMAGE)));

                            JSONArray videoGalleryArray = jsonArray.getJSONObject(i).getJSONArray(AppConstants.VIDEOS_ARR);
                            List<MediaVideoGalleryInnerData> videoList = new ArrayList<>();
                            for (int j = 0; j < videoGalleryArray.length(); j++)
                                videoList.add(new MediaVideoGalleryInnerData(videoGalleryArray.getJSONObject(j).getString(AppConstants.VIDEO_ALBUM_ID),
                                        videoGalleryArray.getJSONObject(j).getString(AppConstants.VIDEO_ALBUM_NAME), videoGalleryArray.getJSONObject(j).getString(AppConstants.VIDEO_LINK),
                                        videoGalleryArray.getJSONObject(j).getString(AppConstants.VIDEO_NAME), videoGalleryArray.getJSONObject(j).getString(AppConstants.VIDEO_THUMB_IMAGE)));
                            videoGalleryMap.put(i, videoList);
                        }
                        mediaCenterVideoGalleryAdapter = new MediaCenterVideoGalleryAdapter(Activity, videoGalleryList);
                        recycViewMediaCenter.setAdapter(mediaCenterVideoGalleryAdapter);

                        mediaCenterVideoGalleryAdapter.setOnItemClickListener(new MediaCenterVideoGalleryAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Log.e(TAG, "Position in Video Gallery Click::" + position);
                                Activity.pushFragments(MediaCenterVideoGalleryInnerFrag.newInstance(videoGalleryMap.get(position)), false, true);
                            }
                        });
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                    }

                } else
                    Toast.makeText(Activity, jsonObject.getString(AppConstants.MESSAGE), Toast.LENGTH_LONG).show();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMediaVideoGalleryDataLoadedFailed(JSONObject jsonObject) {
        super.onMediaVideoGalleryDataLoadedFailed(jsonObject);
//        BaseApplication.getInstance().progressOFF()();
        BaseApplication.getInstance().progressOFF();
        Log.e(TAG, "Error:" + jsonObject);
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
                break;
            default:
                break;
        }
    }
}

