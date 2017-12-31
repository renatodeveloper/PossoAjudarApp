package com.possoajudar.app.application.service.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.dao.DaoModelService;

import org.json.JSONObject;

/**
 * Created by Renato on 27/09/2017.
 */

public class CadUserService {
    DaoModelService daoModelService;

    public boolean registerNewUser(JSONObject jsonValueLogin, Context context){
        try{
            if(!checkUserExist(jsonValueLogin, context)){
                SQLiteDatabase database =  daoModelService.getdbInterno(context);
                if(database!= null && jsonValueLogin != null && jsonValueLogin.length()>0){
                    ContentValues values = new ContentValues();
                    values.put(context.getString(R.string.dsLoginTblUser), jsonValueLogin.getString(context.getString(R.string.dsGeneric_A)));
                    values.put(context.getString(R.string.dsSenhaTblUser), jsonValueLogin.getString(context.getString(R.string.dsGeneric_B)));
                    long result  = database.insert(context.getString(R.string.dsNameTblUser), null, values);
                    if(result >0){
                        //check insert select... fazer com o valida user
                        String[] args = { jsonValueLogin.getString(context.getString(R.string.dsGeneric_A)), jsonValueLogin.getString(context.getString(R.string.dsGeneric_B))};
                        Cursor cursor = database.query(context.getString(R.string.dsNameTblUser), null, "dsLogin=? AND dsSenha=?", args, null,null,null);
                        if(cursor.getCount()>0){
                            cursor.moveToFirst();
                            String dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                            String dsSenha = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsSenhaTblUser)));
                            if(dsLogin.length()>0 && dsSenha.length()>0){
                                return  true;
                            }
                        }

                        //String[] args = { "first string", "second@string.com" };
                        //Cursor cursor = db.query("TABLE_NAME", null, "name=? AND email=?", args, null,null,null);
                    }
                }
            }else{
                return false;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }

    public static boolean checkUserExist(JSONObject jsonValueLogin, Context context){
        DaoModelService daoModelService = null;
        try{
            SQLiteDatabase database =  daoModelService.getdbInterno(context);
            if(database!= null && jsonValueLogin != null && jsonValueLogin.length()>0){
                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.dsLoginTblUser), jsonValueLogin.getString(context.getString(R.string.dsGeneric_A)));
                //values.put(context.getString(R.string.dsSenhaTblUser), jsonValueLogin.getString(context.getString(R.string.dsGeneric_B)));
                //String[] args = { jsonValueLogin.getString(context.getString(R.string.dsGeneric_A)), jsonValueLogin.getString(context.getString(R.string.dsGeneric_B))};
                String[] args = { jsonValueLogin.getString(context.getString(R.string.dsGeneric_A))};
                Cursor cursor = database.query(context.getString(R.string.dsNameTblUser), null, "dsLogin=?", args, null,null,null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    String dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                    String dsSenha = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsSenhaTblUser)));
                    if(dsLogin.length()>0 && dsSenha.length()>0){
                        return  true;
                    }
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
}