package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.HomePlusBaseActivity;
import com.mawaqaa.homepluskw_new.data.HomeSpecialOfferData;
import com.mawaqaa.homepluskw_new.fragments.ConstrctnCorporateDetailsFrag;
import com.mawaqaa.homepluskw_new.fragments.ConstrctnIndividualDetailsFrag;
import com.mawaqaa.homepluskw_new.fragments.RealEstateDeatailsInnerFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 12/7/2016.
 */
public class HomefragmentAdapter extends PagerAdapter implements View.OnClickListener {
    Context context;
    HomeSpecialOfferData hoespecialofferdata;
    List<HomeSpecialOfferData> offerList;
    LayoutInflater inflater;
    public ImageView imgViewSpecialOfferRealEstate;
    public TextView txtOfferDescription, txtTitle, txtDate;
    static OnItemClickListener mOnItemClickListener;

    public HomefragmentAdapter(Context context, List<HomeSpecialOfferData> offerList){
        this.context = context;
        this.offerList = offerList;
    }

    @Override
    public int getCount() {
        return offerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);

    }
    @Override
    public Object instantiateItem(View container, final int position) {
        //ImageView imageScroll = (ImageView)  container.findViewById(R.id.imageViewHighlight);
        final int pos = position;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.single_homefragment_item, null);
        imgViewSpecialOfferRealEstate = (ImageView) itemView.findViewById(R.id.imgViewSpecialOfferRealEstate);
       /* txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        txtOfferDescription = (TextView) itemView.findViewById(R.id.txtOfferDescription);
        txtDate.setSelected(true);
        txtTitle.setSelected(true);*/
        hoespecialofferdata = offerList.get(position);
        Picasso.with(context).load(hoespecialofferdata.getImage()).resize(400, 400).into(imgViewSpecialOfferRealEstate);
        /*txtTitle.setText(realEstateData.getTitle());
        txtOfferDescription.setText(Html.fromHtml(realEstateData.getDescription()));
        txtDate.setText(realEstateData.getOfferDate());

        if (PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH))
        {
            FontUtils.setHomeplusRegularFont(context,txtTitle);
            FontUtils.setHomeplusRegularFont(context,txtDate);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(context,txtTitle);
            FontUtils.setHomeplusArabicRegularFont(context,txtDate);

        }*/
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hoespecialofferdata.getType().equals("R")) {

                    hoespecialofferdata = offerList.get(position);
                    HomePlusBaseActivity.getHpBaseActivity().pushFragments(RealEstateDeatailsInnerFragment.newInstance(hoespecialofferdata.getId(), true), false, true);
                }
                else if(hoespecialofferdata.getType().equals("I"))
                {
                    hoespecialofferdata = offerList.get(position);
                    HomePlusBaseActivity.getHpBaseActivity().pushFragments(ConstrctnIndividualDetailsFrag.newInstance(hoespecialofferdata.getId(), true), false, true);
                }
                else
                {
                    hoespecialofferdata = offerList.get(position);
                    HomePlusBaseActivity.getHpBaseActivity().pushFragments(ConstrctnCorporateDetailsFrag.newInstance(hoespecialofferdata.getId(), true), false, true);
                }
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
