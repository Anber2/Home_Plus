package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 10/27/2016.
 */
public class SlideShowListAdapter extends PagerAdapter {
    Context act;
    List<String> bannerList;
    LayoutInflater inflater;

    public SlideShowListAdapter(HomePlusBaseActivity activity, List<String> imageList) {
        this.bannerList = imageList;
        /*if(PreferenceUtil.getLanguage(activity).equals(AppConstants.EXPO_ARABIC)) {
            Collections.reverse(serviceData);
        }*/
        this.act = activity;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        //ImageView imageScroll = (ImageView)  container.findViewById(R.id.imageViewHighlight);
        inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.sliding_highlights_list_item, null);

        ImageView img = (ImageView) itemView.findViewById(R.id.imageViewHighlight);


       if(bannerList.get(position).equals("null")||bannerList.get(position).equals(""))
           Picasso.with(act).load(R.drawable.null_image).resize(800, 800).into(img);
        else
            Picasso.with(act).load(bannerList.get(position)).resize(800,800).into(img);
        // Log.e("Zoeed ", "" + position + "..." + img.isZoomed());
        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);
    }
}