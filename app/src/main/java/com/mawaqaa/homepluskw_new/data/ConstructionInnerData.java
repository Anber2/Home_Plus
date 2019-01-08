package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 11/14/2016.
 */
public class ConstructionInnerData {

    private String id;
    private String name;
    private String averageRating;
    private String personsRated;
    private String profilePhotoUrl;
    private String location;


    public ConstructionInnerData(String id, String name, String averageRating, String personsRated, String profilePhotoUrl, String location){
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.personsRated = personsRated;
        this.profilePhotoUrl = profilePhotoUrl;
        this.location = location;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonsRated() {
        return personsRated;
    }

    public void setPersonsRated(String personsRated) {
        this.personsRated = personsRated;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
