package com.andoresu.gordiskyapp.utils;

public enum DistanceUnits{

    MILES("miles"), KILOMETERS("miles");

    public String units;

    private DistanceUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return units;
    }
}