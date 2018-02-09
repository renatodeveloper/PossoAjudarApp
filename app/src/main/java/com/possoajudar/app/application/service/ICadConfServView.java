package com.possoajudar.app.application.service;

/**
 * Created by renato on 07/02/2018.
 */

public interface ICadConfServView {
    int getCadConfServRadioOption();
    void showCadConfServRadioOptionError(int resId);
    void showCadConfServError(int resId);
    void startMainActivity();
}
