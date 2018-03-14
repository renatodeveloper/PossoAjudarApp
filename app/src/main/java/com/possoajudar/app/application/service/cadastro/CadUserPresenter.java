package com.possoajudar.app.application.service.cadastro;

import android.content.Context;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONArray;
import org.json.JSONObject;

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

            JSONObject object = new JSONObject();
            object.put(context.getString(R.string.dsLoginTblUser), cadEmailUser);
            object.put(context.getString(R.string.dsSenhaTblUser), cadSenhaUser);
            object.put(context.getString(R.string.idTblServico), 1);
            object.put(context.getString(R.string.idTblRedeSocial), 1);

            boolean registerSucceeded = service.registerNewUser(object);
            if(registerSucceeded){
                view.startMainActivity();
            }else{
                view.showCadUserError(R.string.strLyCadastrofailed);
            }

        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public void getByteArrayPhoto(){
        byte[] byteArray;
        try{
            byteArray = service.getBytePhoto();
            if(byteArray != null && byteArray.length>0){
                view.setByteArrayPhoto(byteArray);
            }else{
                view.nonePhoto();
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
