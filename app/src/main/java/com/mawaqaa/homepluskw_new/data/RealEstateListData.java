package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 11/2/2016.
 */
public class RealEstateListData {

    private String id;
    private String imageUrl;
    private String locationName;
    private String price;
    private String propertyName;
    private String propertyType;
    private String propertyPurpose;
    private String propertyRegion;


    public RealEstateListData(String id, String imageUrl, String locationName, String price, String propertyName,
                              String propertyType, String propertyPurpose, String propertyRegion){
        this.id = id;
        this.imageUrl = imageUrl;
        this.locationName = locationName;
        this.price = price;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyPurpose = propertyPurpose;
        this.propertyRegion = propertyRegion;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPropertyRegion() {
        return propertyRegion;
    }

    public void setPropertyRegion(String propertyRegion) {
        this.propertyRegion = propertyRegion;
    }

    public String getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(String propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
