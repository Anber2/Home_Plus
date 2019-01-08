package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.RealEstateListData;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 11/2/2016.
 */
public class RealEstateListAdapter extends RecyclerView.Adapter<RealEstateListAdapter.RealEstateViewHolder>{

    private List<RealEstateListData> realEstateList;
    private Context context;
    static OnItemClickListener mItemClickListener;

    @Override
    public RealEstateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.real_estate_grid_item, parent, false);
        //itemView.setOnClickListener(new MyOnClickListener());
        return new RealEstateViewHolder(itemView);
    }


    public RealEstateListAdapter(Context context, List<RealEstateListData> realEstateList){
        this.context = context;
        this.realEstateList = realEstateList;
    }

    @Override
    public void onBindViewHolder(RealEstateViewHolder holder, int position) {
        RealEstateListData listData = realEstateList.get(position);
        if(!listData.getImageUrl().equals(""))
            Picasso.with(context).load(listData.getImageUrl()).resize(200,200).into(holder.imgViewRealEstateItem);


        holder.txtPropertyPurpose.setText(listData.getPropertyPurpose());


        holder.txtPropertyLocation.setText(listData.getPropertyRegion());
        holder.txtPropertyPrice.setText(listData.getPrice()+" "+context.getResources().getString(R.string.txt_kd));
        holder.txtPropertyLocation.setSelected(true);
        holder.txtPropertyPrice.setSelected(true);
        if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(context,holder.txtPropertyPurpose);
            FontUtils.setHomeplusRegularFont(context,holder.txtPropertyLocation);
            FontUtils.setHomeplusRegularFont(context,holder.txtPropertyPrice);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(context,holder.txtPropertyPurpose);
            FontUtils.setHomeplusArabicRegularFont(context,holder.txtPropertyLocation);
            FontUtils.setHomeplusRegularFont(context,holder.txtPropertyPrice);
        }

    }

    @Override
    public int getItemCount() {
        return realEstateList.size();
    }

    public static class RealEstateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imgViewRealEstateItem;
        public TextView txtPropertyPurpose, txtPropertyLocation, txtPropertyPrice;

        public RealEstateViewHolder(View itemView) {
            super(itemView);
            imgViewRealEstateItem = (ImageView) itemView.findViewById(R.id.imgViewRealEstateItem);
            txtPropertyPurpose = (TextView) itemView.findViewById(R.id.txtPropertyPurpose);
            txtPropertyLocation = (TextView) itemView.findViewById(R.id.txtPropertyLocation);
            txtPropertyPrice = (TextView) itemView.findViewById(R.id.txtPropertyPrice);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }


    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
