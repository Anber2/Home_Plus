package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 10/26/2016.
 */
public class DrawerMenuListData {

    private String name;
    private int imageId;

    public DrawerMenuListData(){

    }
    public DrawerMenuListData(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
