package com.possoajudar.app.application.service;

/**
 * Created by Renato on 27/09/2017.
 */

public interface ICadUserView {
    String getCadNome();
    String getCadUserEmail();
    String getCadUserSenha();

    byte[] getByteArrayPhoto();

    void setByteArrayPhoto(byte[] byteArray);
    void nonePhoto();

    void showCadUserNomeError(int resId);
    void showCadUserEmailError(int resId);
    void showCadUserPasswordError(int resId);
    void showCadUserError(int resId);
    void startMainActivity();

}
