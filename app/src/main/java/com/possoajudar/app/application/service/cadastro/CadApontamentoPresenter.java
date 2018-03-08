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

    public CadApontamentoPresenter(Context context, ICadApontamentoView view){
        this.context = context;
        this.service =  new CadApontamentoService(context, view);
        this.view = view;

        util = new ActivityUtil();
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

            boolean registerSucceeded = service.registreNewApontamento(util.getValeuJson(this.context, altura, peso, String.valueOf(dataTime), dsDataTime));
            JSONArray jsonArray;
            if(registerSucceeded){
                jsonArray = service.getJSONArrayApontamentoUser();
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

    public void getArrayApontamentoUser(){
        JSONArray jsonArray;
        try{
            jsonArray = service.getJSONArrayApontamentoUser();
            if(jsonArray != null && jsonArray.length()>0){
                view.montaListaApondatamento(jsonArray);
            }else{
                view.showMontaListaApontamentoError(R.string.strLyMontaListafailedApontamento);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public boolean existApontamento(){
        try{
            return  service.ifExistApontamento();
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
}
