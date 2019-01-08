package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.DrawerMenuListData;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.util.List;

/**
 * Created by JijoCJ on 10/26/2016.
 */
public class DrawerMenuListAdapter extends BaseAdapter {

    Context context;

    private LayoutInflater lInflater;
    private List<DrawerMenuListData> listMenu;

    public DrawerMenuListAdapter(Context context, List<DrawerMenuListData> listMenu){
        this.context = context;
        this.listMenu = listMenu;
        this.lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.drawer_menu_list_item, parent, false);

            listViewHolder.txtMenuItem = (TextView)convertView.findViewById(R.id.txtMenuItem);
            if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH)) {
                FontUtils.setHomeplusBoldFont(context, listViewHolder.txtMenuItem);
            }
            else
            {
                FontUtils.setHomeplusArabicRegularFont(context, listViewHolder.txtMenuItem);
            }
            listViewHolder.imgViewMenuIconItem = (ImageView)convertView.findViewById(R.id.imgViewMenuIconItem);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.txtMenuItem.setText(listMenu.get(position).getName());
        listViewHolder.imgViewMenuIconItem.setImageResource(listMenu.get(position).getImageId());

        return convertView;
    }

    static class ViewHolder{
        TextView txtMenuItem;
        ImageView imgViewMenuIconItem;
    }
}
