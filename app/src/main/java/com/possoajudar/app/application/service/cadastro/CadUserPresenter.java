package com.possoajudar.app.application.service.cadastro;

import android.content.Context;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

/**
 * Created by Renato on 27/09/2017.
 */

public class CadUserPresenter {
    Context context;
    private ICadUserView view;
    private CadUserService service;
    ActivityUtil util;
    public CadUserPresenter(Context context, ICadUserView view){
        this.context = context;
        service = new CadUserService(context, view);
        this.view = view;

        util = new ActivityUtil();
        this.service = service;
    }

    public void registerNewUser(){
        try{
            String cadEmailUser = view.getCadUserEmail();
            String cadSenhaUser = view.getCadUserSenha();
            if(cadEmailUser.isEmpty()){
                view.showCadUserEmailError(R.string.strLyCadastroDeUsuarioEditTextHintEmail);
                return;
            }
            if(cadSenhaUser.isEmpty()){
                view.showCadUserPasswordError(R.string.strLyCadastroDeUsuarioEditTextHintSenha);
                return;
            }

            boolean registerSucceeded = service.registerNewUser(util.getValeuJson(this.context, cadEmailUser, cadSenhaUser));
            if(registerSucceeded){
                view.startMainActivity();
            }else{
                view.showCadUserError(R.string.strLyCadastrofailed);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
