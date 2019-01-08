package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.data.MediaCenterVideoGalleryData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 12/27/2016.
 */
public class MediaCenterVideoGalleryAdapter extends RecyclerView.Adapter<MediaCenterVideoGalleryAdapter.ViewHolderVideoGallery> {

    Context context;
    List<MediaCenterVideoGalleryData> videoGalleryList;
    static OnItemClickListener mItemClickListener;

    public MediaCenterVideoGalleryAdapter(Context context, List<MediaCenterVideoGalleryData> videoGalleryList){
        this.context = context;
        this.videoGalleryList = videoGalleryList;
    }

    @Override
    public ViewHolderVideoGallery onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.video_gallery_list_item, parent, false);
        return new ViewHolderVideoGallery(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderVideoGallery holder, int position) {
        MediaCenterVideoGalleryData videoGalleryData = videoGalleryList.get(position);
        Picasso.with(context).load(videoGalleryData.getVideoCoverImage()).resize(400,400).into(holder.imgViewVideoGalleryItem);
        holder.txtVideoAlbumName.setText(videoGalleryData.getAlbumName());
        holder.txtVideoDate.setText(videoGalleryData.getDate());
    }

    @Override
    public int getItemCount() {
        return videoGalleryList.size();
    }

    public static class ViewHolderVideoGallery extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtVideoAlbumName, txtVideoDate;
        ImageView imgViewVideoGalleryItem;

        public ViewHolderVideoGallery(View itemView) {
            super(itemView);
            txtVideoAlbumName = (TextView) itemView.findViewById(R.id.txtVideoAlbumName);
            txtVideoDate = (TextView) itemView.findViewById(R.id.txtVideoDate);
            imgViewVideoGalleryItem = (ImageView) itemView.findViewById(R.id.imgViewVideoGalleryItem);

            txtVideoDate.setSelected(true);
            txtVideoAlbumName.setSelected(true);

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
