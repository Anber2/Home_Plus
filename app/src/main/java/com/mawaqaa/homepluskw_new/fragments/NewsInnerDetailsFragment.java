package com.mawaqaa.homepluskw_new.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.activity.HomePlusMainActivity;
import com.mawaqaa.homepluskw_new.adapter.NewsInnerHorizontalListAdapter;
import com.mawaqaa.homepluskw_new.adapter.NewsrelatedImageAdapter;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.interfaces.BottomBarButtonClickState;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.meetme.android.horizontallistview.HorizontalListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JijoCJ on 1/5/2017.
 */
public class NewsInnerDetailsFragment extends HomePlusBaseFragment{
    public static final String TAG="NewsInnerfrg";
    String NewsId,NewsDate,NewsDescription,NewsTitle,newsImage;
    boolean isFromOffers;
    NewsInnerHorizontalListAdapter newsinneradapter;
    TextView News_title,News_Date,newsinner_details,head_newsrelatedimage,textView_newstitle;
    WebView webview_Details;
    NewsInnerDetailsFragment newsInnerDetailsFragment;
    NewsrelatedImageAdapter newsImageAdapter;
    ArrayList<String> relatedimage_array;
    ImageButton imgBtnSlidePrevious, imgBtnSlideNext;
    ImageView newsinner_image;
    HorizontalListView horizontalListView;



   public static NewsInnerDetailsFragment newInstance(String NewsId, String NewsDate, String NewsDescription, String NewsTitle,
                                                      ArrayList<String> relatedimage_array, String newsImage, boolean isFromOffers){
       NewsInnerDetailsFragment newsInnerDetailsFragment = new NewsInnerDetailsFragment();
       newsInnerDetailsFragment.NewsId = NewsId;
       newsInnerDetailsFragment.NewsDate=NewsDate;
       newsInnerDetailsFragment.NewsDescription=NewsDescription;
       newsInnerDetailsFragment.NewsTitle=NewsTitle;
       newsInnerDetailsFragment.relatedimage_array=relatedimage_array;
       newsInnerDetailsFragment.newsImage=newsImage;
       newsInnerDetailsFragment.isFromOffers = isFromOffers;
        return newsInnerDetailsFragment;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_newsinner_details,container,false);
        ((HomePlusMainActivity) getActivity()).showActionBar();
        ((HomePlusMainActivity) getActivity()).showBottomBar();
        InitView(rootView);
        BottomBarButtonClickState buttonClickState = (BottomBarButtonClickState) Activity;
        buttonClickState.MoreButtonState();
        return rootView;
    }

    private void InitView(View rootView) {
        head_newsrelatedimage=(TextView)rootView.findViewById(R.id.head_newsrelatedimage) ;
        textView_newstitle=(TextView)rootView.findViewById(R.id.textView_newstitle) ;
        horizontalListView=(HorizontalListView)rootView.findViewById(R.id.HorizontalListView) ;
        newsinner_image=(ImageView)rootView.findViewById(R.id.newsinner_image);
        newsinner_details=(TextView)rootView.findViewById(R.id.newsinner_details);
        News_Date=(TextView)rootView.findViewById(R.id.news_inner_date);
        News_title=(TextView)rootView.findViewById(R.id.text_news_inner_title);
        if(PreferenceUtil.getLanguage(Activity).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(Activity,head_newsrelatedimage);
            FontUtils.setHomeplusRegularFont(Activity,textView_newstitle);
            FontUtils.setHomeplusRegularFont(Activity,newsinner_details);
            FontUtils.setHomeplusRegularFont(Activity,News_Date);
            FontUtils.setHomeplusRegularFont(Activity,News_title);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(Activity,head_newsrelatedimage);
            FontUtils.setHomeplusArabicRegularFont(Activity,textView_newstitle);
            FontUtils.setHomeplusArabicRegularFont(Activity,newsinner_details);
            FontUtils.setHomeplusRegularFont(Activity,News_Date);
            FontUtils.setHomeplusArabicRegularFont(Activity,News_title);
        }
        Picasso.with(Activity).load(newsImage).resize(400,400).into(newsinner_image);
        News_Date.setText(NewsDate);
        News_title.setText(NewsTitle);
        newsinner_details.setText(Html.fromHtml(NewsDescription));
        newsinneradapter=new NewsInnerHorizontalListAdapter(Activity,relatedimage_array);
        horizontalListView.setAdapter(newsinneradapter);
        newsinneradapter.notifyDataSetChanged();

    }


}
