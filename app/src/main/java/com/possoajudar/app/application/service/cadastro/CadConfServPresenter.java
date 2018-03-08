package com.possoajudar.app.application.service.cadastro;

import android.content.Context;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadConfServView;
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
                view.showCadConfServRadioOptionError(R.string.strLyCadastroDeConfServRadioOption);
                return;
            }

            JSONObject result = util.recuperaPrefUserLogado(this.context);
            String dsLogin = result.getString(context.getString(R.string.dsLoginTblUser));
            String dsSenha = result.getString(context.getString(R.string.dsSenhaTblUser));

            boolean registerSucceeded = service.registerConfServ(dsLogin, dsSenha, String.valueOf(valRadio));
            if(registerSucceeded){
                util.definePrefConfServico(context, Integer.valueOf(valRadio));

                view.startMainActivity();
            }else{
                view.showCadConfServError(R.string.strLyCadastroConfServfailed);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
