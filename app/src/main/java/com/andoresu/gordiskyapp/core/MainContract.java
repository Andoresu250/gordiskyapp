package com.andoresu.gordiskyapp.core;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.utils.BaseUserActionListener;
import com.andoresu.gordiskyapp.utils.BaseView;

import okhttp3.Route;

public interface MainContract {

    interface View extends BaseView {

    }

    interface ActionsListener extends BaseUserActionListener {


    }

}