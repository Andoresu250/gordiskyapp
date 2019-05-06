package com.andoresu.gordiskyapp.authorization.data;

public class Company extends Profile {

    public String name;
    public String nit;
    public String phone;
    public String address;

    public Company() {
        super(User.TYPE_COMPANY);
    }

    @Override
    public String toString() {
        String s = "";
        s += "id: " + id + "\n";
        s += "name: " + name + "\n";
        s += "nit: " + nit + "\n";
        s += "phone: " + phone + "\n";
        s += "address: " + address + "\n";
        return s;
    }
}
