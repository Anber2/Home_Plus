package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.utility.FontUtils;

import java.util.List;

/**
 * Created by JijoCJ on 11/8/2016.
 */
public class RealEstatePropertySpecListAdapter extends RecyclerView.Adapter<RealEstatePropertySpecListAdapter.PropertySpecViewHolder>{

    Context context;
    List<String> propertySpecList;

    public RealEstatePropertySpecListAdapter(Context context, List<String> propertySpecList){
        this.context = context;
        this.propertySpecList = propertySpecList;
    }

    @Override
    public PropertySpecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.property_spec_list_item, parent, false);
        return new PropertySpecViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PropertySpecViewHolder holder, int position) {
        holder.txtPropertySpec.setText(propertySpecList.get(position));
        FontUtils.setHomeplusRegularFont(context,holder.txtPropertySpec);
    }

    @Override
    public int getItemCount() {
        return propertySpecList.size();
    }

    public static class PropertySpecViewHolder extends RecyclerView.ViewHolder{
        static TextView txtPropertySpec;
        public PropertySpecViewHolder(View itemView) {
            super(itemView);
            txtPropertySpec = (TextView) itemView.findViewById(R.id.txtPropertySpec);
        }
    }
}
