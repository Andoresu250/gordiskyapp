package com.andoresu.gordiskyapp.core.newloan.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Frequency {
    EMPTY("Seleccione [...]", -1),
    DAILY("Diario", 1),
    WEEKLY("Semanal", 7),
    BIWEEKLY("Quincenal", 14),
    MONTHLY("Mensual", 30),
    BIMONTHLY("BiMensual", 60),
    OTHER("Personalizada", null);



    public final String name;
    public final Integer value;

    Frequency(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static final Frequency[] FREQUENCIES = {EMPTY, DAILY , WEEKLY , BIWEEKLY , MONTHLY , BIMONTHLY , OTHER};
    public static final ArrayList<Frequency> FREQUENCIES_LIST = new ArrayList<>(Arrays.asList(FREQUENCIES));

    @Override
    public String toString() {
        return name;
    }


}