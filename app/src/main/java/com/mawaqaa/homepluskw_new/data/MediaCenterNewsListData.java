package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 12/26/2016.
 */
public class MediaCenterNewsListData {

    String id;
    String imageUrl;
    String newsDate;
    String newsDescription;
    String newsTitle;

    public void setNewsImage(String newsImage) {
        NewsImage = newsImage;
    }

    public String getNewsImage() {
        return NewsImage;
    }

    String NewsImage;


    public MediaCenterNewsListData(String id, String imageUrl, String newsDate, String newsDescription, String newsTitle,String NewsImage){
        this.id = id;
        this.imageUrl = imageUrl;
        this.newsDate = newsDate;
        this.newsDescription = newsDescription;
        this.newsTitle = newsTitle;
        this.NewsImage=NewsImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

}
