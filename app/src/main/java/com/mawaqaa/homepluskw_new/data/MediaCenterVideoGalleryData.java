package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 12/27/2016.
 */
public class MediaCenterVideoGalleryData {
    String id;
    String albumName;
    String date;
    String videoCoverImage;

    public MediaCenterVideoGalleryData(String id, String albumName, String date, String videoCoverImage){
        this.id = id;
        this.albumName = albumName;
        this.date = date;
        this.videoCoverImage = videoCoverImage;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoCoverImage() {
        return videoCoverImage;
    }

    public void setVideoCoverImage(String videoCoverImage) {
        this.videoCoverImage = videoCoverImage;
    }
}
