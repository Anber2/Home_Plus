package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 1/9/2017.
 */
public class PartnersData {
   String Url;
    String Img;
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public PartnersData(String Img) {
        this.Img=Img;
         //this.Url=Url;
        // this.Img=Img;

    }

    public void setImg(String img) {
        Img = img;
    }

    public String getImg() {
        return Img;
    }


}
