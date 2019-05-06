package com.andoresu.gordiskyapp.authorization.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;


import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.authorization.data.LoginRequest;
import com.andoresu.gordiskyapp.authorization.data.LoginUser;
import com.andoresu.gordiskyapp.authorization.data.User;
import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.security.SecureData;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.andoresu.gordiskyapp.client.GsonBuilderUtils.getUserGson;
import static com.andoresu.gordiskyapp.utils.MyUtils.checkNullEmpty;

@SuppressLint("LogNotTimber")
public class LoginPresenter implements LoginContract.ActionsListener {

    private final static String TAG = "GORDISKY_" + LoginPresenter.class.getSimpleName();

    private final LoginContract.View loginView;

    private final Context context;

    private LoginService loginService;

    public LoginPresenter(@NonNull LoginContract.View loginView, Context context) {
        this.loginView = loginView;
        this.context = context;
        this.loginService = ServiceGenerator.createService(LoginService.class, getUserGson());
    }

    @Override
    public void sendLoginRequest(LoginUser loginUser) {

        Log.i(TAG, "sendLoginRequest: 111");

        boolean hasError = false;
        Resources res = null;
        if (null != context){
            res = context.getResources();
        }

        if (checkNullEmpty(loginUser.email)){
            hasError = true;
            if (null == res) {
                loginView.showUsernameError("");
            }
            else {
                loginView.showUsernameError(String.format(res.getString(R.string.error_blank), res.getString(R.string.input_user)));
            }
        }

        if (checkNullEmpty(loginUser.password)){
            hasError = true;
            if (null == res) {
                loginView.showPasswordError("");
            }
            else {
                loginView.showPasswordError(String.format(res.getString(R.string.error_blank), res.getString(R.string.input_password)));
            }
        }

        if (hasError){
            loginView.onLoginFinish();
            return;
        }

        Log.i(TAG, "sendLoginRequest: aqui");
        
        loginService.login(new LoginRequest(loginUser))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<User>>(loginView){
                    @Override
                    public void onNext(Response<User> userResponse) {
                        super.onNext(userResponse);
                        Log.i(TAG, "onNext: asd");
                        if(userResponse.isSuccessful()){
                            User user = userResponse.body();
                            if(null != user){
                                Log.i(TAG, "onNext: 1");
                                if(user.isCompany()){
                                    Log.i(TAG, "onNext: is company");
                                    SecureData.saveToken(user.token);
                                    loginView.onLoginSuccess(user);
                                }else {
                                    Log.i(TAG, "onNext: no company");
                                    logout(user);
                                    Log.i(TAG, "onNext: login finish");
                                    loginView.onLoginFinish();
                                }
                            }else{ //user null can't deserialize
                                loginView.showLoginError();
                            }
                        }
                        loginView.onLoginFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        loginView.onLoginFinish();
                    }

                });

    }

    @Override
    public void logout(@NonNull User user) {
        SecureData.removeToken();
        LoginService loginService = ServiceGenerator.createService(LoginService.class, user.token);
        loginService.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<>(loginView));
    }

    @Override
    public void checkSession() {

        final String token = SecureData.getToken();
        if(checkNullEmpty(token)){
            return;
        }
        loginService.check(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<User>>(loginView, false){

                    @Override
                    public void onNext(Response<User> userResponse) {
                        super.onNext(userResponse);
                        if(userResponse.isSuccessful()){
                            User user = userResponse.body();
                            if(user != null){
                                loginView.onLoginFinish();
                                loginView.onLoginSuccess(user);
                            }else{
                                SecureData.removeToken();
                            }
                        }else{
                            Log.e(TAG,  "login not success");
                            SecureData.removeToken();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,  "Login failed");
                        e.printStackTrace();
                        super.onError(e);
                        SecureData.removeToken();
                    }
                });
    }
}
