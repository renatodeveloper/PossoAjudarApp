package com.possoajudar.app.application.service;

/**
 * Created by renato on 06/07/2017.
 */

public interface ILoginView {
    String getUsername();
    String getPassword();

    void showUsernameError(int resId);
    void showPasswordError(int resId);
    void showLoginError(int resId);
    void startMainActivity();
}

