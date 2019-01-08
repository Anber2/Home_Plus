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

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.PhotoGalleryInnerListAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.MediaCenterPhotoGalleryData;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by JijoCJ on 12/22/2016.
 */
public class MediaCenterPhotoInnerGalleryFrag extends HomePlusBaseFragment implements View.OnClickListener{

    private static final String TAG = "MedPhotoInnerGalryFrag";

    TextView txtTitleOnMediaCenter, txtTitlrForGalleryInner,txtphoyoAlbum;
    Spinner spinnerMonth, spinnerYear;
    Button btnTabPhotoGallery, btnTabNews, btnTabVideoGallery;

    RecyclerView recycViewMediaCenter;

    LinearLayout LinearLayFilterGallery;

    List<MediaCenterPhotoGalleryData> photoGalleryList;
    PhotoGalleryInnerListAdapter mPhotoGalleryAdapter;
    Map<Integer, List> photoGalleryMap;
    List<String> photoList;

    String albumTitle;

    public static MediaCenterPhotoInnerGalleryFrag newInstance(List<String> photoList, String albumTitle){
        MediaCenterPhotoInnerGalleryFrag mediaGallleryInnerFrag = new MediaCenterPhotoInnerGalleryFrag();
        mediaGallleryInnerFrag.photoList = photoList;
        mediaGallleryInnerFrag.albumTitle = albumTitle;
        return mediaGallleryInnerFrag;
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
        txtTitleOnMediaCenter.setVisibility(View.GONE);
        txtTitlrForGalleryInner = (TextView) view.findViewById(R.id.txtTitlrForGalleryInner);
        txtphoyoAlbum=(TextView)view.findViewById(R.id.txtTitleOnMediaCenterphotoalbum);
        txtphoyoAlbum.setVisibility(View.VISIBLE);
        spinnerMonth = (Spinner) view.findViewById(R.id.spinnerMonth);
        spinnerYear = (Spinner) view.findViewById(R.id.spinnerYear);

        recycViewMediaCenter = (RecyclerView) view.findViewById(R.id.recycViewMediaCenter);
        recycViewMediaCenter.setLayoutManager(new GridLayoutManager(Activity, 2));

        LinearLayFilterGallery = (LinearLayout) view.findViewById(R.id.LinearLayFilterGallery);
        LinearLayFilterGallery.setVisibility(View.GONE);

        btnTabPhotoGallery = (Button) view.findViewById(R.id.btnTabPhotoGallery);
        btnTabNews = (Button) view.findViewById(R.id.btnTabNews);
        btnTabVideoGallery = (Button) view.findViewById(R.id.btnTabVideoGallery);

        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH)){
           /* btnTabPhotoGallery.setBackgroundResource(R.drawable.button_curved_yellow);
            btnTabNews.setBackgroundResource(R.drawable.button_white_plane);
            btnTabVideoGallery.setBackgroundResource(R.drawable.button_curved_left_white);
            recycViewMediaCenter.setRotationY(180);*/
            FontUtils.setHomeplusRegularFont(Activity,btnTabPhotoGallery);
            FontUtils.setHomeplusRegularFont(Activity,btnTabNews);
            FontUtils.setHomeplusRegularFont(Activity,btnTabVideoGallery);
            FontUtils.setHomeplusRegularFont(Activity,txtTitleOnMediaCenter);
            FontUtils.setHomeplusRegularFont(Activity,txtTitlrForGalleryInner);
            FontUtils.setHomeplusRegularFont(Activity,txtphoyoAlbum);
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
            FontUtils.setHomeplusArabicRegularFont(Activity,txtTitlrForGalleryInner);
            FontUtils.setHomeplusArabicRegularFont(Activity,txtphoyoAlbum);
            btnTabPhotoGallery.setSelected(true);
            btnTabNews.setSelected(false);
            btnTabVideoGallery.setSelected(false);
        }

        btnTabPhotoGallery.setOnClickListener(this);
        btnTabNews.setOnClickListener(this);
        btnTabVideoGallery.setOnClickListener(this);

        txtTitlrForGalleryInner.setVisibility(View.VISIBLE);
        txtTitlrForGalleryInner.setText(albumTitle);
        setPhotoGalleryData();
    }

    private void setPhotoGalleryData() {
        try {
            mPhotoGalleryAdapter = new PhotoGalleryInnerListAdapter(Activity, photoList);
            recycViewMediaCenter.setAdapter(mPhotoGalleryAdapter);

            mPhotoGalleryAdapter.setOnItemClickListener(new PhotoGalleryInnerListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    Activity.pushFragments(PhotoGalleryViewpager.newInstance(photoList,true,position),false,true);

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

