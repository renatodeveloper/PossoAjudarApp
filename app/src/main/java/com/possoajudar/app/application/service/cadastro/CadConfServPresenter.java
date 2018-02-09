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

    public CadConfServPresenter(ICadConfServView view, CadConfServService service, Context context){
        this.context = context;
        util = new ActivityUtil();
        service = new CadConfServService();
        this.view = view;
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
            boolean registerSucceeded = service.registerConfServ(util.getValeuJson(this.context, String.valueOf(valRadio), dsLogin), this.context);
            if(registerSucceeded){
                view.startMainActivity();
            }else{
                view.showCadConfServError(R.string.strLyCadastroConfServfailed);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
