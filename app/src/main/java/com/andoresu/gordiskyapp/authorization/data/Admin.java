package com.andoresu.gordiskyapp.authorization.data;

import java.io.Serializable;

public class Admin extends Profile implements Serializable {

    public String name;

    public Admin(){
        super(User.TYPE_ADMIN);
    }

    @Override
    public String toString() {
        String s = "\n";
        s += ("     Admin\n");
        s += ("     id: " + id + "\n");
        s += ("     profileType: " + type + "\n");
        return s;
    }

}
