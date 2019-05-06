package com.andoresu.gordiskyapp.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class MyMaterialSpinner extends MaterialSpinner {

    public MyMaterialSpinner(Context context) {
        super(context);
        setMyOnClickListener();
    }

    public MyMaterialSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyOnClickListener();
    }

    public MyMaterialSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMyOnClickListener();
    }

    private void setMyOnClickListener(){
        setOnClickListener(view -> MyUtils.closeKeyboard(MyMaterialSpinner.this));
    }
}
