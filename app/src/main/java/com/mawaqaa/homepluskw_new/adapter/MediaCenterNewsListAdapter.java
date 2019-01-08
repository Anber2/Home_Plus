package com.mawaqaa.homepluskw_new.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.homepluskw_new.R;
import com.mawaqaa.homepluskw_new.data.MediaCenterNewsListData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JijoCJ on 12/26/2016.
 */
public class MediaCenterNewsListAdapter extends RecyclerView.Adapter<MediaCenterNewsListAdapter.ViewHolderNewsList>{

    Context context;
    List<MediaCenterNewsListData> newsList;
    static OnItemClickListener mItemClickListener;

    public MediaCenterNewsListAdapter(Context context, List<MediaCenterNewsListData> newsList){
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public ViewHolderNewsList onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        return new ViewHolderNewsList(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderNewsList holder, int position) {
        MediaCenterNewsListData newsListData = newsList.get(position);

        Picasso.with(context).load(newsListData.getImageUrl()).resize(550, 550).into(holder.imgViewMediaNews);
        holder.txtDateInNews.setText(newsListData.getNewsDate());
        holder.txtTitleInNews.setText(newsListData.getNewsTitle());
        holder.txtDescriptionInNews.setText((newsListData.getNewsDescription().startsWith("<html"))? Html.fromHtml(newsListData.getNewsDescription()):
                newsListData.getNewsDescription());
        if(position== newsList.size()-1)
            holder.viewDividerInPhoto.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolderNewsList extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtDateInNews, txtTitleInNews, txtDescriptionInNews;
        ImageView imgViewMediaNews;
        View viewDividerInPhoto;
        public ViewHolderNewsList(View itemView) {
            super(itemView);

            txtDateInNews = (TextView) itemView.findViewById(R.id.txtDateInNews);
            txtTitleInNews = (TextView) itemView.findViewById(R.id.txtTitleInNews);
            txtDescriptionInNews = (TextView) itemView.findViewById(R.id.txtDescriptionInNews);
            //txtNewsReadMore = (TextView) itemView.findViewById(R.id.txtNewsReadMore);
            imgViewMediaNews = (ImageView) itemView.findViewById(R.id.imgViewMediaNews);
            viewDividerInPhoto = (View) itemView.findViewById(R.id.viewDividerInPhoto);

            txtDateInNews.setSelected(true);
            txtTitleInNews.setSelected(true);
            itemView.setOnClickListener(this);

        }

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
