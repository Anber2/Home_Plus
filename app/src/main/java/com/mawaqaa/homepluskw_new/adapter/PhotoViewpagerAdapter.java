package com.mawaqaa.homepluskw_new.adapter;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mawaqaa.homepluskw_new.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 1/6/2017.
 */
public class PhotoViewpagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ImageView imageViewHighlight;
    List<String> photoList;
    public PhotoViewpagerAdapter(Activity activity, List<String> photoList) {
        this.context=activity;
        this.photoList=photoList;

        Log.e("adapterclass",""+photoList);

    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public Object instantiateItem(View container, int position) {
        //ImageView imageScroll = (ImageView)  container.findViewById(R.id.imageViewHighlight);
        final int pos = position;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.highlights_list_item, null);

        imageViewHighlight = (ImageView) itemView.findViewById(R.id.imageViewHighlight);


        Picasso.with(context).load(photoList.get(pos)).resize(400,400).into(imageViewHighlight);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
}
