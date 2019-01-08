package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 12/29/2016.
 */
public class MediaVideoGalleryInnerData {

    String id;
    String videoAlbumName;
    String videoLink;
    String videoName;
    String videoThumbImage;

    public MediaVideoGalleryInnerData(String id, String videoAlbumName, String videoLink, String videoName, String videoThumbImage){
        this.id = id;
        this.videoAlbumName = videoAlbumName;
        this.videoLink = videoLink;
        this.videoName = videoName;
        this.videoThumbImage = videoThumbImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoAlbumName() {
        return videoAlbumName;
    }

    public void setVideoAlbumName(String videoAlbumName) {
        this.videoAlbumName = videoAlbumName;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoThumbImage() {
        return videoThumbImage;
    }

    public void setVideoThumbImage(String videoThumbImage) {
        this.videoThumbImage = videoThumbImage;
    }
}
