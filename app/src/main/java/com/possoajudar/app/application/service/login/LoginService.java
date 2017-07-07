package com.possoajudar.app.application.service.login;

/**
 * Created by renato on 06/07/2017.
 */

public class LoginService {

    public boolean login(String name, String password){
        try{
            if(name.equals("developer") && password.equals("developer")){
                return  true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }
}
