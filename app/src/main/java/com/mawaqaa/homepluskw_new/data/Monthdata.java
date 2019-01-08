package com.mawaqaa.homepluskw_new.data;

/**
 * Created by JijoCJ on 2/2/2017.
 */

public class Monthdata {
    String month;
    String MonthValue;

    public Monthdata(String month, String MonthValue) {
        this.month=month;
        this.MonthValue=MonthValue;
    }


    public void setMonthValue(String monthValue) {
        MonthValue = monthValue;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonthValue() {
        return MonthValue;
    }

    public String getMonth() {
        return month;
    }
}
