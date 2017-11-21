package com.possoajudar.app.application.service.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.application.service.dao.DaoModelService;

/**
 * Created by Renato on 27/09/2017.
 */

public class CadUserService {
    DaoModelService daoModelService;

    public boolean registerNewUser(String email, String password, Context context){
        try{
            SQLiteDatabase database =  daoModelService.getdbInterno(context);
            if(database!=null){
                ContentValues values = new ContentValues();
                values.put("dsLogin", email);
                values.put("dsSenha", password);
                long result  = database.insert("Usuario", null, values);
                if(result >0){
                    //check insert select... fazer com o valida user
                    String[] args = { email, password};
                    Cursor cursor = database.query("Usuario", null, "dsLogin=? AND dsSenha=?", args, null,null,null);
                    if(cursor.getCount()>0){
                        cursor.moveToFirst();
                        String dsInsertLogin = cursor.getString(cursor.getColumnIndex("dsLogin"));
                        String dsInsertSenha = cursor.getString(cursor.getColumnIndex("dsSenha"));
                        if(dsInsertLogin.length()>0 && dsInsertSenha.length()>0){
                            return  true;
                        }
                    }

                    //String[] args = { "first string", "second@string.com" };
                    //Cursor cursor = db.query("TABLE_NAME", null, "name=? AND email=?", args, null,null,null);
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }
}
