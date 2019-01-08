package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 2/27/2017.
 */

public class HomeSpecialOfferData {
    String Description;
    String Id;
    String Image;
    String Title;
    String Type;

    public void setType(String type) {
        Type = type;
    }

    public String getType() {
        return Type;
    }

    public HomeSpecialOfferData(String Description, String Id, String Image, String Title,String Type) {
        this.Description=Description;
        this.Id=Id;
        this.Image=Image;
        this.Title=Title;
        this.Type=Type;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setDescription(String description) {
        Description = description;
    }



    public String getTitle() {
        return Title;
    }

    public String getImage() {
        return Image;
    }

    public String getId() {
        return Id;
    }

    public String getDescription() {
        return Description;
    }




}
