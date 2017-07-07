package com.possoajudar.app.application.service.login;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ILoginView;

/**
 * Created by renato on 06/07/2017.
 */

public class LoginPresenter {
    private ILoginView view;
    private LoginService service;

    public LoginPresenter(ILoginView view, LoginService service){
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

            boolean loginSucceeded = service.login(username, password);
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
