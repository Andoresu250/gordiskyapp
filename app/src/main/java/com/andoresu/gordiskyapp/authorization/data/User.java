package com.andoresu.gordiskyapp.authorization.data;

import com.andoresu.gordiskyapp.utils.BaseDataModel;

import java.io.Serializable;
import java.util.Date;

public class User extends BaseDataModel implements Serializable {

    private static String TAG = "GORDISKY_" + "User";

    public static String NAME = "USER_TAG";

    public static final String TYPE_PERSON = "Person";
    public static final String TYPE_COMPANY = "Company";
    public static final String TYPE_ADMIN = "Admin";

    public String username;
    public String email;
    public String state;
    public String profileType;
    public String token;
    public Profile profile;

    public User(){}

    public boolean isAdmin() {
        return profile instanceof Admin;
    }

    public boolean isCompany(){
        return profile instanceof Company;
    }


    public void setType(){
        if(this.profile instanceof Company){
            this.profileType = TYPE_COMPANY;
        }else if(this.profile instanceof Admin){
            this.profileType = TYPE_ADMIN;
        }
    }

    public String getName(){
        if(profile != null){
            if(isCompany()){
                return ((Company)profile).name;
            }
            if(isCompany()){
                return ((Admin)profile).name;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "\n";
        s += ("id: " + id + "\n");
        s += ("username: " + username + "\n");
        s += ("email: " + email + "\n");
        s += ("state: " + state + "\n");
        s += ("profileType: " + profileType + "\n");
        s += ("createdAt: " + createdAt.toString() + "\n");
        s += ("token: " + token + "\n");
        s += ("profile: " + profile.toString());
        return s;
    }
}