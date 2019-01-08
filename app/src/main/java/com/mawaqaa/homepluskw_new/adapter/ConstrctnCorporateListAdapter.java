package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.data.ConstructionData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 11/12/2016.
 */
public class ConstrctnCorporateListAdapter extends RecyclerView.Adapter<ConstrctnCorporateListAdapter.ConstrtnCorporateViewHolder>{

    Context context;
    List<ConstructionData> individualList;

    public ConstrctnCorporateListAdapter(Context context, List<ConstructionData> individualList){
        this.context = context;
        this.individualList = individualList;
    }

    @Override
    public ConstrtnCorporateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.construction_inner_list_item, parent, false);
        return new ConstrtnCorporateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConstrtnCorporateViewHolder holder, int position) {
        ConstructionData individualData = individualList.get(position);

        Picasso.with(context).load(individualData.getImageUrl()).resize(350,400).into(holder.imgViewConstnCorporate);
       // holder.txtServiceTitle.setText(individualData.getTitle());
        //holder.txtServiceTitle.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return individualList.size();
    }

    public static class ConstrtnCorporateViewHolder extends RecyclerView.ViewHolder{

        ImageView imgViewConstnCorporate;
        TextView txtServiceTitle;

        public ConstrtnCorporateViewHolder(View itemView) {
            super(itemView);
            imgViewConstnCorporate = (ImageView) itemView.findViewById(R.id.imgViewConstnCorporate);
            //txtServiceTitle = (TextView) itemView.findViewById(R.id.txtServiceTitle);
        }
    }
}

