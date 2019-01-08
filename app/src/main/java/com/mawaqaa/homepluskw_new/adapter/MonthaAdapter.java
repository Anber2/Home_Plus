package com.mawaqaa.homepluskw_new.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;

import java.util.ArrayList;

/**
 * Created by JijoCJ on 2/2/2017.
 */
public class MonthaAdapter extends BaseAdapter {

    ArrayList<String> monthList;
    int pos;
    Context context;

    private static LayoutInflater inflater=null;
    public MonthaAdapter(Activity activity, ArrayList<String> monthList) {
        Log.e("checkarray",""+monthList);
        this.context = activity;
        this.monthList = monthList;
    }

    @Override
    public int getCount() {

        return monthList.size();
    }

    @Override
    public Object getItem(int position) {
        return monthList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Holder
    {


        TextView textitemname;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        pos = position;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_country_item, null);

            holder.textitemname=(TextView) convertView.findViewById(R.id.txtSpinnerCountryItem);

            convertView.setTag(holder);
        }
      else

            holder = (Holder) convertView.getTag();
            setData(holder);
            //setFonts(holder);
            return convertView;
    }

    private void setData(Holder holder)
        {
            holder.textitemname.setText(monthList.get(pos));
    }
}
