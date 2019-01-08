package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 12/7/2016.
 */
public class SpecialOfferRealEstateData {

    private String description;
    private String id;
    private String imageUrl;
    private String offerDate;
    private String title;
    private String purpose;

    public void setType(String type) {
        this.type = type;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public String getPurpose() {
        return purpose;
    }

    private String region;
    private String type;
    String PropertyType;

    public void setPropertyType(String propertyType) {
        PropertyType = propertyType;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public SpecialOfferRealEstateData(String id, String description, String imageUrl, String offerDate, String title, String purpose, String region, String type, String propertytype){

        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.offerDate = offerDate;
        this.title = title;
        this.purpose=purpose;
        this.region=region;
        this.type=type;
        this.PropertyType=propertytype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
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
