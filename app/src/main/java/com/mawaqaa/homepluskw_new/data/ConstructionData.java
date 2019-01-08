package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 11/12/2016.
 */
public class ConstructionData {
    private String id;
    private String imageUrl;
    private String title;

    public ConstructionData(String id, String imageUrl, String title){
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
