package com.possoajudar.app.application.service.cadastro;

import android.content.Context;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadApontamentoView;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONArray;
import org.json.JSONObject;

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

                //GPS
                JSONObject gpsJson = util.recuperaPrefUserLogadoApontamentoGPS(context);
                String dsGPS    =  gpsJson.getString(context.getString(R.string.prefJSON_userLogado));
                String latitude  = gpsJson.getString(context.getString(R.string.prefLatitude_userLogado));
                String longitude = gpsJson.getString(context.getString(R.string.prefLongitude_userLogado));

                //IMC
                double alt = Double.parseDouble(altura);
                double pes = Double.parseDouble(peso);

                double  imc =  util.getIMC(alt,pes);
                String dsStatus = util.getDsStatuImc(this.context, imc);

                JSONObject  objApontamento = new JSONObject();
                            objApontamento.put(context.getString(R.string.dsAlturaTblUserAptmento), altura);
                            objApontamento.put(context.getString(R.string.dsPesoTblUserAptmento), peso);
                            objApontamento.put(context.getString(R.string.dataTimeTblUserAptmento), dataTime);
                            objApontamento.put(context.getString(R.string.dsDataTimeTblUserAptmento), dsDataTime);
                            objApontamento.put(context.getString(R.string.imcTblUserAptmento), imc);
                            objApontamento.put(context.getString(R.string.dsStatusTblUserAptmento), dsStatus);
                            objApontamento.put(context.getString(R.string.dsGPD), dsGPS);
                            objApontamento.put(context.getString(R.string.dsLatitude), latitude);
                            objApontamento.put(context.getString(R.string.dsLongitude), longitude);
                //boolean registerSucceeded = service.registreNewApontamento(util.getValeuJson(this.context, altura, peso, String.valueOf(dataTime), dsDataTime, String.valueOf(imc), dsStatus));
                boolean registerSucceeded = service.registreNewApontamento(objApontamento);
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
