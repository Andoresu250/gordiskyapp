package com.andoresu.gordiskyapp.authorization.login;

import com.andoresu.gordiskyapp.authorization.data.LoginUser;
import com.andoresu.gordiskyapp.authorization.data.User;
import com.andoresu.gordiskyapp.utils.BaseView;


public interface LoginContract {

    interface View extends BaseView {

        void showUsernameError(String error);

        void showPasswordError(String error);

        void showUserTypeError();

        void showUserSavedError();

        void showTokenSaveError();

        void showLoginError();

        void onLoginFinish();

        void onLoginSuccess(User user);

    }

    interface ActionsListener{

        void sendLoginRequest(LoginUser loginUser);

        void logout(User user);

        void checkSession();
    }

}
