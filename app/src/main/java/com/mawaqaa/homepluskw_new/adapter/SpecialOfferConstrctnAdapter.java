package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.data.SpecialOfferRealEstateData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 12/9/2016.
 */
public class SpecialOfferConstrctnAdapter extends RecyclerView.Adapter<SpecialOfferConstrctnAdapter.ViewHolder> {

    Context context;
    List<SpecialOfferRealEstateData> specialOfferList;

    static OnItemClickListener mOnItemClickListener;

    public SpecialOfferConstrctnAdapter(Context context, List<SpecialOfferRealEstateData> specialOfferList) {
        this.context = context;
        this.specialOfferList = specialOfferList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.special_offer_construction_list_item, parent, false);
        int i = itemView.getHeight();
        Log.e("SpecialOfferConst", "" + i);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SpecialOfferRealEstateData specialOfferConstructionData = specialOfferList.get(position);
        String specialofferType = specialOfferConstructionData.getType();
        if (specialofferType.equals("R")) {
            holder.layouttop.setVisibility(View.VISIBLE);
            holder.linearllout_spoffer_inner.setVisibility(View.VISIBLE);
            holder.spofferpurpose.setText(specialOfferConstructionData.getPurpose());
            holder.spofferregion.setText(specialOfferConstructionData.getRegion());
            //holder.spoffertype.setText(specialOfferConstructionData.getPropertyType());
            holder.txtOfferTitle.setText(specialOfferConstructionData.getPropertyType());
            holder.linerlout_sub_spofferinner.setVisibility(View.GONE);

        } else {
            holder.layouttop.setVisibility(View.VISIBLE);
            holder.linearllout_spoffer_inner.setVisibility(View.GONE);
            holder.linerlout_sub_spofferinner.setVisibility(View.VISIBLE);
            holder.spofferdescription.setText(Html.fromHtml(specialOfferConstructionData.getDescription()));
        }
        Picasso.with(context).load(specialOfferConstructionData.getImageUrl()).resize(400, 400).into(holder.imgViewSpecialOfferConstn);
        holder.txtOfferTitle.setText(specialOfferConstructionData.getTitle());
        holder.txtDateInOfferConstn.setText(specialOfferConstructionData.getOfferDate());
        // holder.txtoffer_description.setText(Html.fromHtml(specialOfferConstructionData.getDescription()));

        setFont(holder);
    }

    private void setFont(ViewHolder holder) {
       /* if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(context, holder.txtOfferTitle);
            FontUtils.setHomeplusRegularFont(context, holder.txtDateInOfferConstn);
            FontUtils.setHomeplusRegularFont(context,holder.txtoffer_description);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(context, holder.txtOfferTitle);
            FontUtils.setHomeplusRegularFont(context, holder.txtDateInOfferConstn);
            FontUtils.setHomeplusArabicRegularFont(context,holder.txtoffer_description);
        }*/
    }

    @Override
    public int getItemCount() {
        return specialOfferList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout layouttop, linearllout_spoffer_inner, linerlout_sub_spofferinner;
        public ImageView imgViewSpecialOfferConstn;
        public TextView txtDateInOfferConstn, txtOfferTitle, txtoffer_description, spofferpurpose, spofferregion, spoffertype, spofferdescription;

        public ViewHolder(View itemView) {
            super(itemView);
            layouttop = (LinearLayout) itemView.findViewById(R.id.layoutbottom);
            //set visibility

            linearllout_spoffer_inner = (LinearLayout) itemView.findViewById(R.id.linearllout_spoffer_inner);
            linerlout_sub_spofferinner = (LinearLayout) itemView.findViewById(R.id.linerlout_sub_spofferinner);
            spofferpurpose = (TextView) itemView.findViewById(R.id.spofferone);
            spofferregion = (TextView) itemView.findViewById(R.id.spoffertwo);
            //spoffertype = (TextView) itemView.findViewById(R.id.spofferthree);
            spofferdescription = (TextView) itemView.findViewById(R.id.spofferfour);
            spofferdescription.setSelected(true);
            imgViewSpecialOfferConstn = (ImageView) itemView.findViewById(R.id.imgViewSpecialOfferConstn);
            txtDateInOfferConstn = (TextView) itemView.findViewById(R.id.txtDateInOfferConstn);
            txtOfferTitle = (TextView) itemView.findViewById(R.id.txtOfferTitle);
            txtoffer_description = (TextView) itemView.findViewById(R.id.txtOfferDescription);

            txtDateInOfferConstn.setSelected(true);
            txtOfferTitle.setSelected(true);
            txtoffer_description.setSelected(true);
           // spoffertype.setSelected(true);
            spofferregion.setSelected(true);
            spofferpurpose.setSelected(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
