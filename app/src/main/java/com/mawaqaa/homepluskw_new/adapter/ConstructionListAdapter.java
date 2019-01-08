package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.ConstructionData;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 11/12/2016.
 */
public class ConstructionListAdapter extends RecyclerView.Adapter<ConstructionListAdapter.ConstrtnIndividualViewHolder>{

    Context context;
    List<ConstructionData> individualList;

    static OnItemClickListener mItemClickListener;


    public ConstructionListAdapter(Context context, List<ConstructionData> individualList){
        this.context = context;
        this.individualList = individualList;
    }

    @Override
    public ConstrtnIndividualViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.construction_list_item, parent, false);

        return new ConstrtnIndividualViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConstrtnIndividualViewHolder holder, int position) {
        ConstructionData individualData = individualList.get(position);
        Picasso.with(context).load(individualData.getImageUrl()).into(holder.imgViewContnIndividualItem);
        holder.txtServiceTitle.setText(individualData.getTitle());
        int cellHeight = holder.itemView.getHeight();
        Log.d("hieght",""+ cellHeight);
        holder.txtServiceTitle.setSelected(true);
        setFont(holder);
    }

    private void setFont(ConstrtnIndividualViewHolder holder) {
        if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(context, holder.txtServiceTitle);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(context, holder.txtServiceTitle);
        }
    }

    @Override
    public int getItemCount() {
        return individualList.size();
    }

    public static class ConstrtnIndividualViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgViewContnIndividualItem;
        TextView txtServiceTitle;

        public ConstrtnIndividualViewHolder(View itemView) {
            super(itemView);
            imgViewContnIndividualItem = (ImageView) itemView.findViewById(R.id.imgViewContnIndividualItem);
            txtServiceTitle = (TextView) itemView.findViewById(R.id.txtServiceTitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null){
                mItemClickListener.onItemClick(v, getPosition());
            }

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
