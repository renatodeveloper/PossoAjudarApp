package com.possoajudar.app.application.service.cadastro;

import android.content.Context;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadUserView;

/**
 * Created by Renato on 27/09/2017.
 */

public class CadUserPresenter {
    Context context;
    private ICadUserView view;
    private CadUserService service;

    public CadUserPresenter(ICadUserView view, CadUserService service, Context context){
        this.context = context;
        service = new CadUserService();
        this.view = view;
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
            boolean registerSucceeded = service.registerNewUser(cadEmailUser, cadSenhaUser, this.context);
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
