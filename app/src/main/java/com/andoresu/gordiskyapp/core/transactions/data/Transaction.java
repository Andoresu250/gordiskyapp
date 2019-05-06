package com.andoresu.gordiskyapp.core.transactions.data;

import com.andoresu.gordiskyapp.utils.BaseDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.andoresu.gordiskyapp.utils.MyUtils.toMoney;

public class Transaction extends BaseDataModel implements Serializable {

    public static final String ACTIVO = "activo";
    public static final String PASIVO = "pasivo";

    @SerializedName("value")
    @Expose
    public Double value;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("mode")
    @Expose
    public String mode;

    public boolean isActivo(){
        return ACTIVO.equals(mode);
    }

    public String getValue(){
        return toMoney(value);
    }

}
