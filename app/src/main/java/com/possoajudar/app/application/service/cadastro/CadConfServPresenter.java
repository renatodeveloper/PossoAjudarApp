package com.possoajudar.app.application.service.cadastro;

import android.content.Context;
import android.content.Intent;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadConfServView;
import com.possoajudar.app.application.service.ServicoApontamento;
import com.possoajudar.app.application.ui.activities.CadConfServ;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONObject;

/**
 * Created by renato on 07/02/2018.
 */

public class CadConfServPresenter {
    Context context;
    private ICadConfServView view;
    private CadConfServService service;
    ActivityUtil util;

    public CadConfServPresenter(Context context, ICadConfServView view){
        this.context = context;
        service = new CadConfServService(context, view);
        this.view = view;

        util = new ActivityUtil();
        this.service = service;
    }
    public void registerConfServ(){
        try{
            int valRadio = view.getCadConfServRadioOption();
            if(valRadio == 0){
                valRadio = 1;
                //view.showCadConfServRadioOptionError(R.string.strLyCadastroDeConfServRadioOption);
                //return;
            }

            JSONObject result = util.recuperaPrefUserLogado(this.context);
            String dsNome = result.getString(context.getString(R.string.dsNomeTblUser));
            String dsLogin = result.getString(context.getString(R.string.dsLoginTblUser));


            boolean registerSucceeded = service.registerConfServ(dsNome, dsLogin, String.valueOf(valRadio));
            if(registerSucceeded){
                //recupero - limpo e redefino o time do service
                JSONObject recuperar = util.recuperaPrefConfServico(this.context);
                String idRecuperado = recuperar.getString(this.context.getResources().getString(R.string.idConfServico));
                util.limpaPrefConfServ(this.context);
                util.definePrefConfServico(this.context, valRadio);
                this.context.startService(new Intent(this.context, ServicoApontamento.class));
                view.startMainActivity();
            }else{
                view.showCadConfServError(R.string.strLyCadastroConfServfailed);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
