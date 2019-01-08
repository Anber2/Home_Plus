package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.activity.VideoActivity;
import com.mawaqaa.homepluskw_new.constants.AppConstants;
import com.mawaqaa.homepluskw_new.data.MediaVideoGalleryInnerData;
import com.mawaqaa.homepluskw_new.utility.GeneralUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 12/29/2016.
 */
public class MediaVideoGalleryInnerAdapter extends RecyclerView.Adapter<MediaVideoGalleryInnerAdapter.ViewHolderVideoList> {

    Context context;
    List<MediaVideoGalleryInnerData> videoList;

    public static OnItemClickListener mItemClickListener;

    public MediaVideoGalleryInnerAdapter(Context context, List<MediaVideoGalleryInnerData> videoList){
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public ViewHolderVideoList onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.video_gallery_inner_list_item, parent, false);
        return new ViewHolderVideoList(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderVideoList holder, final int position) {
        MediaVideoGalleryInnerData videoGalleryInnerData = videoList.get(position);
        Picasso.with(context).load(videoGalleryInnerData.getVideoThumbImage()).resize(500, 500).into(holder.imgViewVideoGalleryInnerItem);
holder.imgBtnYoutubeIcon.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        MediaVideoGalleryInnerData innerVideoData = videoList.get(position);
        String videoId = GeneralUtils.getYouTubeVideoId(innerVideoData.getVideoLink());

        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(AppConstants.VIDEO_URL, videoId);
        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class ViewHolderVideoList extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgViewVideoGalleryInnerItem;
        ImageButton imgBtnYoutubeIcon;
        public ViewHolderVideoList(View itemView) {
            super(itemView);
            imgViewVideoGalleryInnerItem = (ImageView) itemView.findViewById(R.id.imgViewVideoGalleryInnerItem);
            imgBtnYoutubeIcon = (ImageButton) itemView.findViewById(R.id.imgBtnYoutubeIcon);

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
