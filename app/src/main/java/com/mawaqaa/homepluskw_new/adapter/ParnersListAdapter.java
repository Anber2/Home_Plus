package com.mawaqaa.homepluskw_new.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.data.PartnersData;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.mawaqaa.homepluskw_new.views.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by JijoCJ on 1/9/2017.
 */
public class ParnersListAdapter extends BaseAdapter {
    private String TAG = "ParnersListAdapter";
    Context context;
    ArrayList<PartnersData> partners_list;
    LayoutInflater layoutInflater;
    ViewHolder viewHolder;
    PartnersData partnersData;
    public ParnersListAdapter(Activity activity, ArrayList<PartnersData> partners_list) {

        this.context=activity;
        this.partners_list=partners_list;

    }

    @Override
    public int getCount() {
        return partners_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView ==  null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.single_sponsers_item,null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.imgViewRelProd);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        partnersData = partners_list.get(position);
        Log.e(TAG,"Related Image URL:"+partners_list.get(position).getImg());
        int color = new Color().parseColor("#FFFFFF");
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(color)
                .borderWidthDp(2)
                .cornerRadiusDp(5)
                .oval(false)
                .build();
        if(PreferenceUtil.getLanguage(context).equalsIgnoreCase("en"))
            Picasso.with(context).load(partners_list.get(position).getImg()).
                    placeholder(R.drawable.progress_animation).fit().
                    transform(transformation).
                    into(viewHolder.imageView);
        else
            Picasso.with(context).load(partners_list.get(position).getImg()).
                    placeholder(R.drawable.progress_animation).fit().
                    transform(transformation).
                    into(viewHolder.imageView);
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
    }

}
