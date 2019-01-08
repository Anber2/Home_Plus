package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.util.List;

/**
 * Created by JijoCJ on 11/17/2016.
 */
public class SpinnerListAdapter extends BaseAdapter {

    Context con;
    List<String> countryList;
    int pos;
    public SpinnerListAdapter(Context con, List<String> countryList){
        this.con = con;
        this.countryList = countryList;
    }
    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        pos = position;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_country_item, null);

            holder.txtSpinnerCountryItem = (TextView) convertView.findViewById(R.id.txtSpinnerCountryItem);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();
        setData(holder);
        setFonts(holder);
        return convertView;
    }

    private void setFonts(ViewHolder holder) {
        if(PreferenceUtil.getLanguage(con).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(con, holder.txtSpinnerCountryItem);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(con, holder.txtSpinnerCountryItem);
        }
    }

    private void setData(ViewHolder holder) {
        holder.txtSpinnerCountryItem.setText(countryList.get(pos));
        holder.txtSpinnerCountryItem.setTextColor(Color.BLACK);
    }

    static class ViewHolder{
    TextView txtSpinnerCountryItem;
}
}

