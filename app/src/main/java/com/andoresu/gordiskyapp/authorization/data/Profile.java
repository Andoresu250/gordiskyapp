package com.andoresu.gordiskyapp.authorization.data;


import com.andoresu.gordiskyapp.utils.BaseDataModel;

import java.io.Serializable;


public abstract class Profile extends BaseDataModel implements Serializable{

    public String type;

    public Profile(){}

    public Profile(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String s = "\n";
        s += ("     type: " + type);
        return s;
    }
}