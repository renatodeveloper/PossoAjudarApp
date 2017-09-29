package com.possoajudar.app.application.service;

/**
 * Created by Renato on 27/09/2017.
 */

public interface ICadUserView {
    String getCadUserEmail();
    String getCadUserSenha();

    void showCadUserEmailError(int resId);
    void showCadUserPasswordError(int resId);
    void showCadUserError(int resId);
    void startMainActivity();

}
