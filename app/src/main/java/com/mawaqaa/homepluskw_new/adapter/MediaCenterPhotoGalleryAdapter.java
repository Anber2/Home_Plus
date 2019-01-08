package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.data.MediaCenterPhotoGalleryData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 12/14/2016.
 */
public class MediaCenterPhotoGalleryAdapter  extends RecyclerView.Adapter<MediaCenterPhotoGalleryAdapter.ViewHolder>{


    Context context;
    List<MediaCenterPhotoGalleryData> photoGalleryList;
    static OnItemClickListener mItemClickListener;

    public MediaCenterPhotoGalleryAdapter(Context context, List<MediaCenterPhotoGalleryData> photoGalleryList){
        this.context = context;
        this.photoGalleryList = photoGalleryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.photo_gallery_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MediaCenterPhotoGalleryData photoGalleryData = photoGalleryList.get(position);
        Picasso.with(context).load(photoGalleryData.getAlbumCoverPhoto()).resize(400, 400).into(holder.imgViewMediaPhotoGallery);
        holder.txtTitlePhotoGallery.setText(photoGalleryData.getAlbumTitle());
        holder.txtDateInPhotoGallery.setText(photoGalleryData.getAlbumDate());

        if(position == photoGalleryList.size()-1)
            holder.viewDividerInPhoto.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return photoGalleryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgViewMediaPhotoGallery;
        TextView txtTitlePhotoGallery, txtDateInPhotoGallery;
        View viewDividerInPhoto;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitlePhotoGallery = (TextView) itemView.findViewById(R.id.txtTitlePhotoGallery);
            txtDateInPhotoGallery = (TextView) itemView.findViewById(R.id.txtDateInPhotoGallery);
            imgViewMediaPhotoGallery = (ImageView) itemView.findViewById(R.id.imgViewMediaPhotoGallery);
            viewDividerInPhoto = (View) itemView.findViewById(R.id.viewDividerInPhoto);

            //txtTitlePhotoGallery.setSelected(true);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener!=null)
                mItemClickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

}
