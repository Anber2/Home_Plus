package com.mawaqaa.homepluskw_new.adapter;

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
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 3/2/2017.
 */

public class HighlightsImageAdaptersponsers extends PagerAdapter {
    String TAG = "fgjgvjsd";
    Context act;
    List<String> realEstateBannerList;
    LayoutInflater inflater;
    ImageView sponsers1, sponsers2, sponsers3;
    final int pos = 0;
    int index = 0;
    int pagecount = 0;
    int imagescount = 0;
    int reminder = 0;

    public HighlightsImageAdaptersponsers(HomePlusBaseActivity activity, List<String> realEstateBannerList) {
        this.act = activity;
        this.realEstateBannerList = realEstateBannerList;
       /* if(PreferenceUtil.getLanguage(activity).equals(AppConstants.EXPO_ARABIC)) {
            Collections.reverse(serviceData);
        }*/
        this.act = activity;
    }

    @Override
    public int getCount() {
       /* Log.e("ADAPTER", "Imagecount"+realEstateBannerList.size()/3+"");
        return realEstateBannerList.size()%3;*/
        imagescount = realEstateBannerList.size();

        pagecount = imagescount / 3;
        reminder = imagescount % 3;
        if (reminder > 0) {
            pagecount = pagecount + 1;
        }
        Log.e("AdapterJ>>", reminder
                + ">>>>>" + pagecount);
        return pagecount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        //ImageView imageScroll = (ImageView)  container.findViewById(R.id.imageViewHighlight);

        Log.e("position", "" + position);
        inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.single_sponsers_item, null);

        sponsers1 = (ImageView) itemView.findViewById(R.id.sponserimage1);
        sponsers2 = (ImageView) itemView.findViewById(R.id.sponserimage2);
        sponsers3 = (ImageView) itemView.findViewById(R.id.sponserimage3);
        Log.e(TAG, "Position>>>" + position + "PageCount>>>>>>" + pagecount);
        if (position < pagecount - 1) {
            if ((position * 3) + 0 < realEstateBannerList.size()) {

                Picasso.with(act).load(realEstateBannerList.get((position * 3) + 0)).into(sponsers1);
            }
            if ((position * 3) + 1 < realEstateBannerList.size()) {
                Picasso.with(act).load(realEstateBannerList.get((position * 3) + 1)).into(sponsers2);
            }
            if ((position * 3) + 2 < realEstateBannerList.size()) {
                Picasso.with(act).load(realEstateBannerList.get((position * 3) + 2)).into(sponsers3);
            }
        } else {
            Log.e("Eslse case", "dsgag");
            switch (reminder) {
                case 1:
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 3)).into(sponsers1);
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 2)).into(sponsers2);
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 1)).into(sponsers3);
                    break;
                case 2:
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 3)).into(sponsers1);
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 2)).into(sponsers2);
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 1)).into(sponsers3);
                    break;
                default:
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 3)).into(sponsers1);
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 2)).into(sponsers2);
                    Picasso.with(act).load(realEstateBannerList.get(realEstateBannerList.size() - 1)).into(sponsers3);
                    break;
            }
        }


        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }

}

