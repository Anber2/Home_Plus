package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 11/16/2016.
 */
public class LocationListData {

    private String countryId;
    private String countryCode;
    private String countryName;

    public LocationListData(String countryId, String countryCode, String countryName){
        this.countryId = countryId;
        this.countryCode = countryCode;
        this.countryName = countryName;
    }
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
