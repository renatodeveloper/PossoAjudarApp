package com.possoajudar.app.application.service;

import org.json.JSONArray;

/**
 * Created by Renato on 03/12/2017.
 */

public interface ICadApontamentoView {
    String getCadApontamentoAltura();
    String getCadApontamentoPeso();
    String getCadApontamentoDsDataTime();
    long   getCadApontamentoDataTime();
    JSONArray montaListaApondatamento(JSONArray jsonArray);


    void showCadApontamentoAlturaError(int resId);
    void showCadApontamentoPesoError(int resId);
    void showCadApontamentoDataTimeError(int resId);
    void showCadApontamentoError(int resId);
    void showMontaListaApontamentoError(int resId);
    //void startMainListActivity();
}
