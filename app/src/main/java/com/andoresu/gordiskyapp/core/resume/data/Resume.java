package com.andoresu.gordiskyapp.core.resume.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.andoresu.gordiskyapp.utils.MyUtils.toMoney;

public class Resume implements Serializable {

    @SerializedName("totalInverted")
    @Expose
    public Double totalInverted;
    @SerializedName("totalInvertedWithInterest")
    @Expose
    public Double totalInvertedWithInterest;
    @SerializedName("totalAssets")
    @Expose
    public Double totalAssets;
    @SerializedName("totalLiabilities")
    @Expose
    public Double totalLiabilities;
    @SerializedName("totalGain")
    @Expose
    public Double totalGain;
    @SerializedName("totalProfit")
    @Expose
    public Double totalProfit;


    public String getTotalInverted() {
        return toMoney(totalInverted);
    }

    public String getTotalInvertedWithInterest() {
        return toMoney(totalInvertedWithInterest);
    }

    public String getTotalAssets() {
        return toMoney(totalAssets);
    }

    public String getTotalLiabilities() {
        return toMoney(totalLiabilities);
    }

    public String getTotalGain() {
        return toMoney(totalGain);
    }

    public String getTotalProfit() {
        return toMoney(totalProfit);
    }
}