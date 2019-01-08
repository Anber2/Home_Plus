package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.PhotoViewpagerAdapter;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.views.CirclePageIndicator;

import java.util.List;

/**
 * Created by JijoCJ on 1/6/2017.
 */
public class PhotoGalleryViewpager extends HomePlusBaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    public static final String TAG = "PhotoGallery";
    List<String> photoList;
    boolean isFromOffers;
    ViewPager photoGallery_viewpager;
    CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    int pageNumber;
    PhotoViewpagerAdapter photoviewpageradapter;
    ImageButton imgBtnSlidePrevious, imgBtnSlideNext;

    public static PhotoGalleryViewpager newInstance(List<String> photoList, boolean isFromOffers, int position) {
        PhotoGalleryViewpager photogalleryViewpager = new PhotoGalleryViewpager();
        photogalleryViewpager.pageNumber = position;
        photogalleryViewpager.photoList = photoList;
        photogalleryViewpager.isFromOffers = isFromOffers;
        return photogalleryViewpager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photogallery_vpager, container, false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        InitView(rootView);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.MoreButtonState();
        return rootView;
    }

    private void InitView(View rootView) {
        imgBtnSlidePrevious = (ImageButton) rootView.findViewById(R.id.imgBtnSlidePrevious);
        imgBtnSlideNext = (ImageButton) rootView.findViewById(R.id.imgBtnSlideNext);
        photoGallery_viewpager = (ViewPager) rootView.findViewById(R.id.viewPagerphotoGallery);
        indicator = (CirclePageIndicator) rootView.
                findViewById(R.id.circleIndicator);
        photoviewpageradapter = new PhotoViewpagerAdapter(Activity, photoList);
        photoGallery_viewpager.setAdapter(photoviewpageradapter);
        Log.e(TAG,">>>>>"+pageNumber);
        photoGallery_viewpager.setCurrentItem(pageNumber);
        indicator.setViewPager(photoGallery_viewpager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = photoList.size();
        if(NUM_PAGES==1)
        {
            indicator.setVisibility(View.GONE);
        }
        else {
            indicator.setVisibility(View.VISIBLE);
        }
        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                photoGallery_viewpager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 4000, 4000);
//        indicator.setOnPageChangeListener(this);

        //imgBtnSlidePrevious.setOnClickListener(this);
        // imgBtnSlideNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnSlideNext:
                if (photoGallery_viewpager.getCurrentItem() < photoList.size()) {
                    photoGallery_viewpager.setCurrentItem(photoGallery_viewpager.getCurrentItem() + 1);
                }
                break;
            case R.id.imgBtnSlidePrevious:
                if (photoGallery_viewpager.getCurrentItem() > 0) {
                    photoGallery_viewpager.setCurrentItem(photoGallery_viewpager.getCurrentItem() - 1);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
