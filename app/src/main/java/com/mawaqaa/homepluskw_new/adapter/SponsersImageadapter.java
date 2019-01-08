package com.mawaqaa.homepluskw_new.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mawaqaa.homepluskw_new.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 2/6/2017.
 */

public class SponsersImageadapter extends PagerAdapter {
    Context act;
    List<String> realEstateBannerList;
    LayoutInflater inflater;
    ImageView imageViewHighlight;
    final int pos = 0;

    public SponsersImageadapter(Activity activity, List<String> realEstateSponsersList) {
        this.realEstateBannerList = realEstateBannerList;
       this.realEstateBannerList=realEstateSponsersList;
        this.act = activity;
    }

    @Override
    public int getCount() {
        return realEstateBannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        //ImageView imageScroll = (ImageView)  container.findViewById(R.id.imageViewHighlight);
        final int pos = position;
        inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.sponsers_list_item, null);

        imageViewHighlight = (ImageView) itemView.findViewById(R.id.imageViewsponsers);


        Picasso.with(act).load(realEstateBannerList.get(pos)).into(imageViewHighlight);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }

}
