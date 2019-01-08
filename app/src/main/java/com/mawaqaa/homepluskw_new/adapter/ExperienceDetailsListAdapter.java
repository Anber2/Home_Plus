package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;

import java.util.List;

/**
 * Created by JijoCJ on 11/21/2016.
 */
public class ExperienceDetailsListAdapter extends RecyclerView.Adapter<ExperienceDetailsListAdapter.ViewHolder>{

    Context context;
    List<String> propertySpecList;

    public ExperienceDetailsListAdapter(Context context, List<String> propertySpecList){
        this.context = context;
        this.propertySpecList = propertySpecList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.property_spec_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtPropertySpec.setText(propertySpecList.get(position));
        setFont(holder);

    }

    private void setFont(ViewHolder holder) {
        if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(context,holder.txtPropertySpec);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(context,holder.txtPropertySpec);
        }
    }

    @Override
    public int getItemCount() {
        return propertySpecList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtPropertySpec;
        public ViewHolder(View itemView) {
            super(itemView);
            txtPropertySpec = (TextView) itemView.findViewById(R.id.txtPropertySpec);
        }
    }
}
