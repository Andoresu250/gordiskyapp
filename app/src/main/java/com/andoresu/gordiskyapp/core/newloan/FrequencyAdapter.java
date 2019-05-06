package com.andoresu.gordiskyapp.core.newloan;

import android.content.Context;

import com.andoresu.gordiskyapp.core.newloan.data.Frequency;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;

import java.util.List;

public class FrequencyAdapter extends MaterialSpinnerAdapter<Frequency> {

    public FrequencyAdapter(Context context, List<Frequency> items) {
        super(context, items);
    }
}
