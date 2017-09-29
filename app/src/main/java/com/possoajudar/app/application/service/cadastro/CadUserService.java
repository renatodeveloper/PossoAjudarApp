package com.possoajudar.app.application.service.cadastro;

/**
 * Created by Renato on 27/09/2017.
 */

public class CadUserService {
    public boolean registerNewUser(String email, String password){
        try{
            if(email.equals("developer") && password.equals("developer")){
                //insert new usuário
                return  true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }
}
