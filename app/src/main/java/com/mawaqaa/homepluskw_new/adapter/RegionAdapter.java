package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.util.List;

import static com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity.con;

/**
 * Created by JijoCJ on 2/16/2017.
 */

public class RegionAdapter extends BaseAdapter

{
    Context context;
    List<String> regionList;
    int pos;
    public RegionAdapter(HomePlusBaseActivity activity, List<String> regionList) {
        this.context=activity;
        this.regionList=regionList;
    }

    @Override
    public int getCount() {
        return regionList.size();
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
        ViewHolder holder = new RegionAdapter.ViewHolder();
        pos = position;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_country_item, null);

            holder.txtSpinnerCountryItem = (TextView) convertView.findViewById(R.id.txtSpinnerCountryItem);

            convertView.setTag(holder);
        }else
            holder = (RegionAdapter.ViewHolder) convertView.getTag();
        setData(holder);
        setFonts(holder);
        return convertView;
    }

    private void setFonts(ViewHolder holder) {
        if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(context, holder.txtSpinnerCountryItem);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(con, holder.txtSpinnerCountryItem);
        }
    }

    private void setData(ViewHolder holder) {
        holder.txtSpinnerCountryItem.setText(regionList.get(pos));
        holder.txtSpinnerCountryItem.setTextColor(Color.BLACK);
    }
    static class ViewHolder{
        TextView txtSpinnerCountryItem;
    }
}
