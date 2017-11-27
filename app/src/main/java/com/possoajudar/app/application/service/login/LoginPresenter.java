package com.possoajudar.app.application.service.login;

import android.content.Context;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

/**
 * Created by renato on 06/07/2017.
 */

public class LoginPresenter {
    Context context;
    ActivityUtil util;
    private ILoginView view;
    private LoginService service;

    public LoginPresenter(ILoginView view, LoginService service, Context context){
        this.context = context;
        util = new ActivityUtil();
        service = new LoginService();
        this.view = view;
        this.service = service;
    }


    public void onLoginClicked() {
        try {

            String username = view.getUsername();
            if (username.isEmpty()) {
                view.showUsernameError(R.string.strLyLoginUsername_error);
                return;
            }
            String password = view.getPassword();
            if (password.isEmpty()) {
                view.showPasswordError(R.string.strLyLoginPassword_error);
                return;
            }

            boolean loginSucceeded = service.login(util.getValeuJson(this.context, username, password), this.context);
            if(loginSucceeded){
                view.startMainActivity();
            }else{
                view.showLoginError(R.string.strLyLoginloginfailed);
            }

        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
