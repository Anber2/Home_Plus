package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 12/8/2016.
 */
public class SpecialOfferConstructionData {
    private String id;
    private String description;
    private String imageUrl;
    private String offerDate;
    private String title;
    private String purpose;
    private String region;
    private String Type;


    public void setType(String type) {
        Type = type;
    }

    public String getType() {
        return Type;
    }

    //private boolean isIndividual;


    public SpecialOfferConstructionData(String id, String description, String imageUrl, String offerDate, String title, String Type){
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.offerDate = offerDate;
        this.title = title;
        this.Type = Type;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

   /* public boolean isIndividual() {
        return isIndividual;
    }
*/
  /* public void setIndividual(boolean individual) {
        isIndividual = individual;
    }
*/
    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
