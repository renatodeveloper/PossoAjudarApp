package com.possoajudar.app.application.service;

/**
 * Created by Renato on 03/12/2017.
 */

public interface ICadApontamentoView {
    String getCadApontamentoAltura();
    String getCadApontamentoPeso();

    void showCadApontamentoAlturaError(int resId);
    void showCadApontamentoPesoError(int resId);
    void showCadApontamentoError(int resId);
    void startMainListActivity();
}
