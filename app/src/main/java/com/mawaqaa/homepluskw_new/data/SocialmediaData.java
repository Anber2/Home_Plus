package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 1/20/2017.
 */
public class SocialmediaData {
    String Link;
    String Name;

    public SocialmediaData(String Link, String Name) {
        this.Link=Link;
        this.Name=Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getName() {
        return Name;
    }

    public String getLink() {
        return Link;
    }
}
