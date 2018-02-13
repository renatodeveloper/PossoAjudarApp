package com.possoajudar.app.application.service.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ServicoApontamento;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by renato on 07/02/2018.
 */

public class CadConfServService {
    ActivityUtil activityUtil;
    public boolean registerConfServ(JSONObject jsonValue, Context context){
        DaoModelService daoModelService = null;
        try{
            SQLiteDatabase database =  daoModelService.getdbInterno(context);
            if(database!= null && jsonValue != null && jsonValue.length()>0){
                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.idConfServico), jsonValue.getString(context.getString(R.string.dsGeneric_A)));
                values.put(context.getString(R.string.dsLoginTblUser), jsonValue.getString(context.getString(R.string.dsGeneric_B)));
                //String[] args = { jsonValue.getString(context.getString(R.string.dsGeneric_A)), jsonValue.getString(context.getString(R.string.dsGeneric_B))};
                String[] args = { jsonValue.getString(context.getString(R.string.dsGeneric_B))};

                //String[] args = { "first string", "second@string.com" };
                //Cursor cursor = db.query("TABLE_NAME", null, "name=? AND email=?", args, null,null,null);

                Cursor cursor = database.query(context.getString(R.string.dsNameTblUser), null, "dsLogin=?", args, null,null,null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    int idServico = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idConfServico)));
                    String dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                    int idUser = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUser)));

                    if(dsLogin.length()>0 && idServico>0){
                        values.clear();
                        values = new ContentValues();
                        values.put(context.getString(R.string.idConfServico), jsonValue.getString(context.getString(R.string.dsGeneric_A)));
                        int result = 0;
                        activityUtil = new ActivityUtil();
                        result = database.update(context.getString(R.string.dsNameTblUser), values, "dsLogin=?", args);
                        values.clear();;
                        if(result>0){
                            activityUtil.definePrefConfServico(context, Integer.valueOf(jsonValue.getString(context.getString(R.string.dsGeneric_A))));
                            JSONObject obj = activityUtil.recuperaPrefConfServico(context);
                            if (obj != null) {
                                String[] argsId= {String.valueOf(idUser)};
                                cursor = database.query(context.getString(R.string.dsNameTblUserAptmento), null, "idUsuario=?", argsId, null,null,null);
                                if(cursor.getCount()>0){
                                    cursor.moveToLast();
                                    int idApontamento = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUserAptmento)));
                                    long dataHoraUltimoApontamento = cursor.getLong(cursor.getColumnIndex(context.getString(R.string.dataTimeTblUserAptmento)));
                                }
                                return true;
                            }
                        }
                    }
                }else{
                    return false;
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  true;
    }
}