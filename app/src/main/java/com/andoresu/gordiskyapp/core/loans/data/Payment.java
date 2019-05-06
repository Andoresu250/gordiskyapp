package com.andoresu.gordiskyapp.core.loans.data;

import com.andoresu.gordiskyapp.utils.BaseDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import static com.andoresu.gordiskyapp.utils.MyUtils.toMoney;

public class Payment extends BaseDataModel implements Serializable {
    @SerializedName("number")
    @Expose
    public Integer number;
    @SerializedName("date")
    @Expose
    public Date date;
    @SerializedName("paidAt")
    @Expose
    public Date paidAt;
    @SerializedName("value")
    @Expose
    public Double value;
    @SerializedName("paidValue")
    @Expose
    public Double paidValue;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("isMissed")
    @Expose
    public boolean isMissed;

    public Payment(){}

    public String getValue() {
        return toMoney(value);
    }

    public String getPaidValue(){
        if (paidValue == null){
            return "N/A";
        }
        return toMoney(paidValue);
    }

    public String getDate(){
        return simpleDate(date);
    }

    public String getPaidAt(){
        if(paidAt == null){
            return "N/A";
        }
        return simpleDate(paidAt);
    }

}
