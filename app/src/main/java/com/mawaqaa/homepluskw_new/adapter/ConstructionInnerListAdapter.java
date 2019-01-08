package com.mawaqaa.homepluskw_new.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.ConstructionInnerData;
import com.mawaqaa.homepluskw_new.utility.FontUtils;
import com.mawaqaa.homepluskw_new.utility.PreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 11/14/2016.
 */
public class ConstructionInnerListAdapter extends RecyclerView.Adapter<ConstructionInnerListAdapter.IndividualInnerViewHolder>{

    private static final String TAG = "ConstcnInnerListAdapter";
    Context context;
    List<ConstructionInnerData> individualInnerList;
   public static String lang;

    static OnItemClickListener mItemClickListener;
    static String Typecheck;
    public ConstructionInnerListAdapter(Context context, List<ConstructionInnerData> individualInnerList,String Type){
        this.context = context;
        this.individualInnerList = individualInnerList;
        this.Typecheck=Type;
    }

    @Override
    public IndividualInnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.construction_inner_list_item, parent, false);
        lang = PreferenceUtil.getLanguage(context);
        return new IndividualInnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IndividualInnerViewHolder holder, int position) {
        ConstructionInnerData innerData = individualInnerList.get(position);
        Picasso.with(context).load(innerData.getProfilePhotoUrl()).into(holder.imgViewConstnCorporate);
        holder.txtCorporateNameValue.setText(innerData.getName());
        holder.txtCorporateLocationValue.setText(innerData.getLocation());
       int from_value;
        try {
            from_value = Integer.parseInt(innerData.getPersonsRated());
        }
        catch(NumberFormatException ex) {
            from_value = 0; // default ??
        }
        Log.e("ratingcount","???"+from_value);
       // holder.txtRateCount.setText("(" + context.getString(R.string.txt_rating_count ,  from_value ) + ")");
        if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH)) {
            //holder.txtRateCount.setText("(" + context.getString(R.string.txt_rating_count) + " " + from_value + ")");
            holder.txtRateCount.setText("(" + from_value + " " + context.getString(R.string.txt_rating_count) + ")");

        }else{
            holder.txtRateCount.setText("(" + from_value + " " + context.getString(R.string.txt_rating_count)  + ")");
        }
        //holder.txtRateCount.setText("(" + context.getString(R.string.txt_rating_count ,  Integer.parseInt(innerData.getPersonsRated()) ) + ")");
       // holder.ratingBarCorporateConstn.setRating(Float.parseFloat(innerData.getAverageRating()));
        float fom_value;
        try {
            fom_value = Float.parseFloat(innerData.getAverageRating());
        }
        catch(NumberFormatException ex) {
            fom_value = (float) 0.0; // default ??
        }
        holder.ratingBarCorporateConstn.setRating(fom_value);
        holder.ratingBarCorporateConstn.setIsIndicator(true);
        setFonts(holder);

    }

    private void setFonts(IndividualInnerViewHolder holder) {
        if(PreferenceUtil.getLanguage(context).equals(AppConstants.HP_ENGLISH)) {
            FontUtils.setHomeplusRegularFont(context, holder.txtCorporateNameValue);
            FontUtils.setHomeplusRegularFont(context, holder.txtCorporateLocationValue);
            FontUtils.setHomeplusRegularFont(context, holder.txtRateCount);

            FontUtils.setHomeplusRegularFont(context, holder.individual_inner_name);
            FontUtils.setHomeplusRegularFont(context, holder.indiviaualinner_location);
            FontUtils.setHomeplusRegularFont(context, holder.individualtct_rating_head);
        }
        else
        {
            FontUtils.setHomeplusArabicRegularFont(context, holder.txtCorporateNameValue);
            FontUtils.setHomeplusArabicRegularFont(context, holder.txtCorporateLocationValue);
            FontUtils.setHomeplusArabicRegularFont(context, holder.txtRateCount);
            FontUtils.setHomeplusArabicRegularFont(context, holder.individual_inner_name);
            FontUtils.setHomeplusArabicRegularFont(context, holder.indiviaualinner_location);
            FontUtils.setHomeplusArabicRegularFont(context, holder.individualtct_rating_head);
        }
    }

    @Override
    public int getItemCount() {
        return individualInnerList.size();
    }

    public static class IndividualInnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imgViewConstnCorporate;
        public TextView txtCorporateNameValue, txtCorporateLocationValue, txtRateCount,individual_inner_name,indiviaualinner_location,
                individualtct_rating_head;
        public RatingBar ratingBarCorporateConstn;
        public View basic_view;
        //View myView1, myView2;

        public IndividualInnerViewHolder(View itemView) {
            super(itemView);
            /*myView1 = (View) itemView.findViewById(R.id.myView1);
            myView2 = (View) itemView.findViewById(R.id.myView2);
            myView1.setLayerType(View.LAYER_TYPE_SOFTWARE, null); // instead of hardware accelaration
            myView2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);*/
        /*   basic_view = (View)itemView.findViewById(R.id.basic_view2);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)basic_view.getLayoutParams();
            params.setMargins(128, 0, 10, 0); //substitute parameters for left, top, right, bottom
            basic_view.setLayoutParams(params);*/
            imgViewConstnCorporate = (ImageView) itemView.findViewById(R.id.imgViewConstnCorporate);
            txtCorporateNameValue = (TextView) itemView.findViewById(R.id.txtCorporateNameValue);
            txtCorporateLocationValue = (TextView) itemView.findViewById(R.id.txtCorporateLocationValue);
            txtRateCount = (TextView) itemView.findViewById(R.id.txtRateCount);
            individual_inner_name = (TextView) itemView.findViewById(R.id.individual_inner_name);
            indiviaualinner_location = (TextView) itemView.findViewById(R.id.indiviaualinner_location);
            if(lang.equals(AppConstants.HP_ENGLISH)) {
                if (Typecheck.equals("Corporate")) {
                    indiviaualinner_location.setText(itemView.getResources().getString(R.string.txt_location));
                    basic_view = (View) itemView.findViewById(R.id.basic_view2);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) basic_view.getLayoutParams();
                    params.setMargins(135, 0, 10, 0); //substitute parameters for left, top, right, bottom
                    basic_view.setLayoutParams(params);
                } else {
                    indiviaualinner_location.setText(itemView.getResources().getString(R.string.txt_nationality));
                    basic_view = (View) itemView.findViewById(R.id.basic_view2);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) basic_view.getLayoutParams();
                    params.setMargins(180, 0, 10, 0); //substitute parameters for left, top, right, bottom
                    basic_view.setLayoutParams(params);
                }
            }else{
                if (Typecheck.equals("Corporate")) {
                    indiviaualinner_location.setText(itemView.getResources().getString(R.string.txt_location));
                }else{
                    indiviaualinner_location.setText(itemView.getResources().getString(R.string.txt_nationality));
                }
            }
            individualtct_rating_head = (TextView) itemView.findViewById(R.id.individualtct_rating_head);
            ratingBarCorporateConstn = (RatingBar) itemView.findViewById(R.id.ratingBarCorporateConstn);
            ratingBarCorporateConstn.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Log.e(TAG, "Rate Changed");
                }
            });
            txtCorporateNameValue.setSelected(true);
            txtCorporateLocationValue.setSelected(true);
            txtRateCount.setSelected(true);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(mItemClickListener != null){
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListener{
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
}
