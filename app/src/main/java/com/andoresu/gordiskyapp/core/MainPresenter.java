package com.andoresu.gordiskyapp.core;

import android.content.Context;
import android.support.annotation.NonNull;

import com.andoresu.gordiskyapp.authorization.login.LoginService;
import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.security.SecureData;
import com.andoresu.gordiskyapp.utils.SimpleResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainPresenter implements MainContract.ActionsListener{


    private String TAG = "MUVITTAG_" + MainPresenter.class.getSimpleName();

    private final MainContract.View mainView;

    private final Context context;

    private final LoginService loginService;

    public MainPresenter(@NonNull MainContract.View mainView, @NonNull Context context, String authToken){
        this.mainView = mainView;
        this.context = context;
        this.loginService = ServiceGenerator.createService(LoginService.class, authToken);

    }

    @Override
    public void logout() {
        loginService.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<SimpleResponse>>(mainView){
                    @Override
                    public void onNext(Response<SimpleResponse> simpleResponseResponse) {
                        super.onNext(simpleResponseResponse);
                        SecureData.removeToken();
                        mainView.onLogoutFinish();
                    }
                });
    }

}
