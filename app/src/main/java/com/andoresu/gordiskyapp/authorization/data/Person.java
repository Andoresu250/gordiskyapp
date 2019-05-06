package com.andoresu.gordiskyapp.authorization.data;


import com.andoresu.gordiskyapp.utils.BaseDataModel;

import java.io.Serializable;

public class Person extends BaseDataModel implements Serializable {

    public static final String NAME = "PERSON_TAG";

    public String firstNames;
    public String lastNames;
    public String phone;
    public String identification;
    public String address;
    public Float lat;
    public Float lng;

    public Person(){}

    public Person(String firstName, String lastName, String phone, String identification, String address, Float lat, Float lng) {
        this.firstNames = firstName;
        this.lastNames = lastName;
        this.phone = phone;
        this.identification = identification;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public Person(String firstName, String lastName) {
        this.firstNames = firstName;
        this.lastNames = lastName;
    }

    public String getFullName(){
        return this.firstNames + " " + this.lastNames;
    }

    @Override
    public String toString() {

        String s = "Person: \n";
        s += "id: " + id + "\n";
        s += "firstNames: " + firstNames + "\n";
        s += "lastNames: " + lastNames + "\n";
        s += "phone: " + phone + "\n";
        s += "identification: " + identification + "\n";
        return s;
    }

}
