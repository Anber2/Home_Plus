package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 1/2/2017.
 */
public class MyRequestListData {

    private String id;
    private String appliedDate;
    private String propertyLocation;
    private String propertyType;
    private String propertyPurpose;

    public MyRequestListData(String id, String appliedDate, String propertyLocation, String propertyType, String propertyPurpose){
        this.id = id;
        this.appliedDate = appliedDate;
        this.propertyLocation = propertyLocation;
        this.propertyType = propertyType;
        this.propertyPurpose = propertyPurpose;
    }

    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(String propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
