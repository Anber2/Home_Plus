package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.SpecialOfferRealEstateData;
import com.mawaqaa.homepluskw_new.fragments.RealEstateDeatailsInnerFragment;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 12/7/2016.
 */
public class SpecialOfferRealEstateAdapter extends PagerAdapter implements View.OnClickListener {
    Context context;
    SpecialOfferRealEstateData realEstateData;
    List<SpecialOfferRealEstateData> offerList;
    LayoutInflater inflater;
    public ImageView imgViewSpecialOfferRealEstate;
    public TextView txtOfferDescription, txtTitle, txtDate,spofferpurpose,spofferregion,spoffertype,spofferdescription,
            offer_purpose,offer_region,offer_propertytype;
   static OnItemClickListener mOnItemClickListener;
LinearLayout layouttop, linearllout_type_r, linerlout_type_ic;

    public SpecialOfferRealEstateAdapter(Context context, List<SpecialOfferRealEstateData> offerList){
        this.context = context;
        this.offerList = offerList;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);

    }
    @Override
    public Object instantiateItem(View container, int position) {
        //ImageView imageScroll = (ImageView)  container.findViewById(R.id.imageViewHighlight);
        final int pos = position;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.special_offer_real_estate_list_item, null);
        layouttop=(LinearLayout)itemView.findViewById(R.id.layoutbottom);
        //set visibility
        //offer_purpose=(TextView)itemView.findViewById(R.id.offer_purpose) ;
      //  offer_region=(TextView)itemView.findViewById(R.id.offer_region) ;
        //offer_propertytype=(TextView)itemView.findViewById(R.id.offer_propertytype) ;
        linearllout_type_r =(LinearLayout)itemView.findViewById(R.id.linearllout_spoffer_inner);
        linerlout_type_ic =(LinearLayout)itemView.findViewById(R.id.linerlout_sub_spofferinner);
        spofferpurpose=(TextView)itemView.findViewById(R.id.spofferone) ;
        spofferpurpose.setSelected(true);
        spofferregion=(TextView)itemView.findViewById(R.id.spoffertwo) ;
        spofferregion.setSelected(true);
       // spoffertype=(TextView)itemView.findViewById(R.id.spofferthree) ;
        //spoffertype.setSelected(true);
        spofferdescription=(TextView)itemView.findViewById(R.id.spofferfour) ;
        spofferdescription.setSelected(true);
        imgViewSpecialOfferRealEstate = (ImageView) itemView.findViewById(R.id.imgViewSpecialOfferRealEstate);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        txtOfferDescription = (TextView) itemView.findViewById(R.id.txtOfferDescription);
        txtDate.setSelected(true);
        txtTitle.setSelected(true);
         realEstateData = offerList.get(0);
        Log.e("oggerimage","****"+realEstateData.getImageUrl());
        Picasso.with(context).load(realEstateData.getImageUrl()).resize(400, 400).into(imgViewSpecialOfferRealEstate);
       txtTitle.setText(realEstateData.getTitle());
        //txtTitle.setText(realEstateData.getPropertyType());

        txtDate.setText(realEstateData.getOfferDate());
        String specialofferType=realEstateData.getType();
        if(specialofferType.equals("R"))
        { layouttop.setVisibility(View.VISIBLE);
            linearllout_type_r.setVisibility(View.VISIBLE);
            Log.e(">>>>>",realEstateData.getPurpose()+">>>>>"+realEstateData.getRegion()+">>>>>>>"+realEstateData.getPropertyType());
            spofferpurpose.setText(realEstateData.getPurpose());
            spofferregion.setText(realEstateData.getRegion());
            txtTitle.setText(realEstateData.getPropertyType());
            linerlout_type_ic.setVisibility(View.GONE);

        }
        else
        {
            layouttop.setVisibility(View.VISIBLE);
            linearllout_type_r.setVisibility(View.GONE);
            linerlout_type_ic.setVisibility(View.VISIBLE);
            Log.e("SpecialofferAdapter",">>>>"+Html.fromHtml(realEstateData.getDescription()));
            spofferdescription.setText(Html.fromHtml(realEstateData.getDescription()));
        }

        if (PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(context,txtTitle);
            FontUtils.setHomeplusRegularFont(context,txtDate);
            FontUtils.setHomeplusRegularFont(context,spofferpurpose);
            FontUtils.setHomeplusRegularFont(context,spofferregion);
            FontUtils.setHomeplusRegularFont(context,spoffertype);
            FontUtils.setHomeplusRegularFont(context,offer_purpose);
            FontUtils.setHomeplusRegularFont(context,offer_region);
            FontUtils.setHomeplusRegularFont(context,offer_propertytype);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(context,txtTitle);
            FontUtils.setHomeplusRegularFont(context,txtDate);
            FontUtils.setHomeplusArabicRegularFont(context,spofferpurpose);
            FontUtils.setHomeplusArabicRegularFont(context,spofferregion);
            FontUtils.setHomeplusArabicRegularFont(context,spoffertype);
            FontUtils.setHomeplusArabicRegularFont(context,offer_purpose);
            FontUtils.setHomeplusArabicRegularFont(context,offer_region);
            FontUtils.setHomeplusArabicRegularFont(context,offer_propertytype);


        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePlusBaseActivity.getHpBaseActivity().pushFragments(RealEstateDeatailsInnerFragment.newInstance(realEstateData.getId(), true), false, true);
            }
        });
        ((ViewPager) container).addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,getItemPosition(context));

        }
    }
   /* @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.special_offer_real_estate_list_item, parent, false);
        return new ViewHolder(itemView);
    }*/

    /*@Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SpecialOfferRealEstateData realEstateData = offerList.get(position);
        Picasso.with(context).load(realEstateData.getImageUrl()).resize(400, 400).into(holder.imgViewSpecialOfferRealEstate);
        holder.txtTitle.setText(realEstateData.getTitle());
        FontUtils.setHomeplusRegularFont(context,holder.txtTitle);
        holder.txtDate.setText(realEstateData.getOfferDate());
        FontUtils.setHomeplusRegularFont(context,holder.txtDate);
        //if(realEstateData.getDescription().startsWith("<html"))
           holder.txtOfferDescription.setText(Html.fromHtml(realEstateData.getDescription()));
        FontUtils.setHomeplusRegularFont(context,holder.txtOfferDescription);
        //else
            //holder.txtOfferDescription.setText(realEstateData.getDescription());

    }*/

   /* @Override
    public int getItemCount() {
        return offerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imgViewSpecialOfferRealEstate;
        public TextView txtOfferDescription, txtTitle, txtDate;
        public ViewHolder(View itemView) {
            super(itemView);
            imgViewSpecialOfferRealEstate = (ImageView) itemView.findViewById(R.id.imgViewSpecialOfferRealEstate);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtOfferDescription = (TextView) itemView.findViewById(R.id.txtOfferDescription);

            txtDate.setSelected(true);
            txtTitle.setSelected(true);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }*/
   public interface OnItemClickListener{
       public void onItemClick(View view , int position);
   }
  public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener){
       this.mOnItemClickListener = mOnItemClickListener;
   }
}
