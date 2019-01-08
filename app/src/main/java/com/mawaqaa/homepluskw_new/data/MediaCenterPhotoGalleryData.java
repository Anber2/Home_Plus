package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 12/14/2016.
 */
public class MediaCenterPhotoGalleryData {

    private String id;
    private String albumTitle;
    private String albumDate;
    private String albumImage;
    private String albumCoverPhoto;

    public MediaCenterPhotoGalleryData(String id, String albumImage, String albumTitle, String albumDate, String albumCoverPhoto){
        this.id = id;
        this.albumImage = albumImage;
        this.albumTitle = albumTitle;
        this.albumDate = albumDate;
        this.albumCoverPhoto = albumCoverPhoto;
    }

    public String getAlbumCoverPhoto() {
        return albumCoverPhoto;
    }

    public void setAlbumCoverPhoto(String albumCoverPhoto) {
        this.albumCoverPhoto = albumCoverPhoto;
    }

    public String getAlbumDate() {
        return albumDate;
    }

    public void setAlbumDate(String albumDate) {
        this.albumDate = albumDate;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImageUrl) {
        this.albumImage = albumImage;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
