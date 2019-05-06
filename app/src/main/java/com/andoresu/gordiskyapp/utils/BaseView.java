package com.andoresu.gordiskyapp.utils;


import com.andoresu.gordiskyapp.client.ErrorResponse;

public interface BaseView {

    void showProgressIndicator(boolean active);

    void showGlobalError(ErrorResponse errorResponse);

    void onLogoutFinish();

    void showMessage(String msg);

}
