package com.mawaqaa.homepluskw_new.fragments;

import android.content.Intent;
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

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.activity.VideoActivity;
import com.mawaqaa.homepluskw_new.adapter.MediaVideoGalleryInnerAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.MediaVideoGalleryInnerData;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.GeneralUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by JijoCJ on 12/29/2016.
 */
public class MediaCenterVideoGalleryInnerFrag extends HomePlusBaseFragment implements View.OnClickListener{

    private static final String TAG = "MediaCtrVideoInnerFrag";

    TextView txtTitleOnMediaCenter, txtNoData;
    Spinner spinnerMonth, spinnerYear;
    Button btnTabPhotoGallery, btnTabNews, btnTabVideoGallery;

    RecyclerView recycViewMediaCenter;

    LinearLayout LinearLayFilterGallery;

    MediaVideoGalleryInnerAdapter mediaVideoGalleryAdapter;
    Map<Integer, List> videoGalleryMap;

    List<MediaVideoGalleryInnerData> videoList;

    public static MediaCenterVideoGalleryInnerFrag newInstance(List<MediaVideoGalleryInnerData> videoList){
        MediaCenterVideoGalleryInnerFrag videoGalleryInnerFrag = new MediaCenterVideoGalleryInnerFrag();
        videoGalleryInnerFrag.videoList = videoList;
        return videoGalleryInnerFrag;
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
        recycViewMediaCenter.setLayoutManager(new GridLayoutManager(Activity, 2));

        LinearLayFilterGallery = (LinearLayout) view.findViewById(R.id.LinearLayFilterGallery);
        LinearLayFilterGallery.setVisibility(View.GONE);

        btnTabPhotoGallery = (Button) view.findViewById(R.id.btnTabPhotoGallery);
        btnTabNews = (Button) view.findViewById(R.id.btnTabNews);
        btnTabVideoGallery = (Button) view.findViewById(R.id.btnTabVideoGallery);

        if (PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(Activity,btnTabPhotoGallery);
            FontUtils.setHomeplusRegularFont(Activity,btnTabNews);
            FontUtils.setHomeplusRegularFont(Activity,btnTabVideoGallery);
            FontUtils.setHomeplusRegularFont(Activity,txtTitleOnMediaCenter);
            FontUtils.setHomeplusRegularFont(Activity,txtNoData);
            btnTabPhotoGallery.setSelected(false);
            btnTabNews.setSelected(false);
            btnTabVideoGallery.setSelected(true);
        }else{
            FontUtils.setHomeplusArabicRegularFont(Activity,btnTabPhotoGallery);
            FontUtils.setHomeplusArabicRegularFont(Activity,btnTabNews);
            FontUtils.setHomeplusArabicRegularFont(Activity,btnTabVideoGallery);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtTitleOnMediaCenter);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtNoData);
            btnTabPhotoGallery.setSelected(false);
            btnTabNews.setSelected(false);
            btnTabVideoGallery.setSelected(true);

        }

        btnTabPhotoGallery.setOnClickListener(this);
        btnTabNews.setOnClickListener(this);
        btnTabVideoGallery.setOnClickListener(this);

        txtTitleOnMediaCenter.setText(getString(R.string.btn_txt_video_gallery));
        setVideoGalleryInnerData();
    }

    private void setVideoGalleryInnerData() {
        try {
            mediaVideoGalleryAdapter = new MediaVideoGalleryInnerAdapter(Activity, videoList);
            recycViewMediaCenter.setAdapter(mediaVideoGalleryAdapter);

            mediaVideoGalleryAdapter.setOnItemClickListener(new MediaVideoGalleryInnerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, final int position) {

                   /* ImageButton ytimg = (ImageButton) v.findViewById(R.id.imgBtnYoutubeIcon);
                    ytimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaVideoGalleryInnerData innerVideoData = videoList.get(position);
                            String videoId = GeneralUtils.getYouTubeVideoId(innerVideoData.getVideoLink());

                            Intent intent = new Intent(Activity, VideoActivity.class);
                            intent.putExtra(AppConstants.VIDEO_URL, videoId);
                            startActivity(intent);
                        }
                    });*/

                    MediaVideoGalleryInnerData innerVideoData = videoList.get(position);
                    String videoId = GeneralUtils.getYouTubeVideoId(innerVideoData.getVideoLink());

                    Intent intent = new Intent(Activity, VideoActivity.class);
                    intent.putExtra(AppConstants.VIDEO_URL, videoId);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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


