package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mawaqaa.homepluskw_new.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 12/22/2016.
 */
public class PhotoGalleryInnerListAdapter extends RecyclerView.Adapter<PhotoGalleryInnerListAdapter.ViewHolderPhtoGallery> {

    Context context;
    List<String> photoGalleryList;
    static OnItemClickListener mItemClickListener;
    public PhotoGalleryInnerListAdapter(Context context, List<String> photoGalleryList){
        this.context = context;
        this.photoGalleryList = photoGalleryList;
    }

    @Override
    public ViewHolderPhtoGallery onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.photo_gallery_inner_list_item, parent, false);
        return new ViewHolderPhtoGallery(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderPhtoGallery holder, int position) {
        Picasso.with(context).load(photoGalleryList.get(position)).resize(550, 550).into(holder.imgViewPhotoGalleryItem);
    }

    @Override
    public int getItemCount() {
        return photoGalleryList.size();
    }

    public static class ViewHolderPhtoGallery extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imgViewPhotoGalleryItem;
        public ViewHolderPhtoGallery(View itemView) {
            super(itemView);
            imgViewPhotoGalleryItem = (ImageView) itemView.findViewById(R.id.imgViewPhotoGalleryItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener!=null){
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener){
        this.mItemClickListener = mOnItemClickListener;
    }
}
