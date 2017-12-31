package com.possoajudar.app.application.service.cadastro;

import android.content.Context;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadApontamentoView;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONArray;

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
            long dataTime = view.getCadApontamentoDataTime();
            String dsDataTime = view.getCadApontamentoDsDataTime();

            if(altura.isEmpty()){
                view.showCadApontamentoAlturaError(R.string.strLyCadastroApontamentoTextViewAltura);
                return;
            }
            if(peso.isEmpty()){
                view.showCadApontamentoPesoError(R.string.strLyCadastroApontamentoTextViewPeso);
                return;
            }
            if(dataTime <= 0){
                view.showCadApontamentoDataTimeError(R.string.strLyCadastroApontamentoStrDataTime);
                return;
            }
            if(dsDataTime.isEmpty()){
                view.showCadApontamentoDataTimeError(R.string.strLyCadastroApontamentoStrDataTime);
                return;
            }

            boolean registerSucceeded = service.registerNewApontamento(util.getValeuJson(this.context, altura, peso, String.valueOf(dataTime), dsDataTime), this.context);
            JSONArray jsonArray;
            if(registerSucceeded){
                jsonArray = service.getJSONArrayApontamentoUser(context);
                if(jsonArray != null && jsonArray.length()>0){
                    view.montaListaApondatamento(jsonArray);
                }else{
                    view.showMontaListaApontamentoError(R.string.strLyMontaListafailedApontamento);
                }
            }else{
                view.showMontaListaApontamentoError(R.string.strLyMontaListafailedApontamento);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public void getArrayApontamentoUser(Context context){
        JSONArray jsonArray;
        try{
            jsonArray = service.getJSONArrayApontamentoUser(context);
            if(jsonArray != null && jsonArray.length()>0){
                view.montaListaApondatamento(jsonArray);
            }else{
                view.showMontaListaApontamentoError(R.string.strLyMontaListafailedApontamento);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public boolean existApontamento(Context context){
        try{
            return  service.ifExistApontamento(context);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
}
