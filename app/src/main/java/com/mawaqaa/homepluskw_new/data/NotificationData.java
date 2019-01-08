package com.mawaqaa.homepluskw_new.data;

import android.util.Log;

/**
 * Created by aswathy on 1/3/2017.
 */
public class NotificationData {
    private String Id;
    String poston;
    private String location;
    private String purpose;
    private String proptype;
    private String Readystatus;
    private String Proprtytitle;

    private String IsIndividual;
    private String IsRequest;
    private String ReturnImage;


    public void setPoston(String poston) {
        this.poston = poston;
    }

    public String getPoston() {
        return poston;
    }

    public void setProptype(String proptype) {
        this.proptype = proptype;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProptype() {
        return proptype;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getLocation() {

        return location;
    }



    public String getIsIndividual() {
        return IsIndividual;
    }

    public void setReturnImage(String returnImage) {
        ReturnImage = returnImage;
    }

    public String getReturnImage() {
        return ReturnImage;
    }

    public void setIsRequest(String isRequest) {
        IsRequest = isRequest;
    }

    public void setIsIndividual(String isIndividual) {
        IsIndividual = isIndividual;
    }

    public String getIsRequest() {
        return IsRequest;
    }

    public NotificationData(String Id, String location, String purpose,String proptype,String Readystatus, String Proprtytitle,String Returnimage,String poston){
        this.Id=Id;
        this.location=location;
        this.purpose=purpose;
        this.Readystatus=Readystatus;
        this.Proprtytitle=Proprtytitle;
        this.proptype=proptype;
        this.ReturnImage=Returnimage;

        this.poston=poston;
        Log.e("notificationdata",">>>"+ReturnImage);


    }

    public void setReadystatus(String readystatus) {
        Readystatus = readystatus;
    }

    public void setProprtytitle(String proprtytitle) {
        Proprtytitle = proprtytitle;
    }



    public void setId(String id) {
        Id = id;
    }


    public String getReadystatus() {
        return Readystatus;
    }

    public String getProprtytitle() {
        return Proprtytitle;
    }



    public String getId() {
        return Id;
    }






}
