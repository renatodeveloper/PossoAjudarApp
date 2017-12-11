package com.possoajudar.app.application.service.cadastro;

import android.content.Context;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadApontamentoView;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

/**
 * Created by Renato on 03/12/2017.
 */

public class CadApontamentoPresenter {
    Context context;
    private ICadApontamentoView view;
    private CadApontamentoService service;
    ActivityUtil util;

    public CadApontamentoPresenter(ICadApontamentoView view, CadApontamentoService service, Context context){
        this.context = context;
        util = new ActivityUtil();

        this.view = view;
        this.service =  new CadApontamentoService();
    }
    public void registerApontamentoUser(){
        try{
            String altura = view.getCadApontamentoAltura();
            String peso = view.getCadApontamentoPeso();
            if(altura.isEmpty()){
                view.showCadApontamentoAlturaError(R.string.strLyCadastroApontamentoTextViewAltura);
                return;
            }
            if(peso.isEmpty()){
                view.showCadApontamentoPesoError(R.string.strLyCadastroApontamentoTextViewPeso);
                return;
            }

            boolean registerSucceeded = service.registerNewApontamento(util.getValeuJson(this.context, altura, peso), this.context);
            if(registerSucceeded){
                view.startMainListActivity();
            }else{
                view.showCadApontamentoError(R.string.strLyCadastrofailedApontamento);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
