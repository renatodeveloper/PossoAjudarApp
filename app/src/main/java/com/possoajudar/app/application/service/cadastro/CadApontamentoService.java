package com.possoajudar.app.application.service.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.dao.DaoModelService;

import org.json.JSONObject;

/**
 * Created by Renato on 03/12/2017.
 */

public class CadApontamentoService {
    DaoModelService daoModelService;
    public boolean registerNewApontamento(JSONObject jsonValueApontamento, Context context){
        try{
            SQLiteDatabase database =  daoModelService.getdbInterno(context);
            if(database!= null && jsonValueApontamento != null && jsonValueApontamento.length()>0){
                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.dsAlturaTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_A)));
                values.put(context.getString(R.string.dsPesoTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_B)));
                long result  = database.insert(context.getString(R.string.dsNameTblUserAptmento), null, values);
                if(result >0){
                    //check insert select... fazer com o valida user
                    String[] args = { jsonValueApontamento.getString(context.getString(R.string.dsGeneric_A)), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_B))};
                    Cursor cursor = database.query(context.getString(R.string.dsNameTblUserAptmento), null, "vlAltura=? AND vlPeso=?", args, null,null,null);
                    if(cursor.getCount()>0){
                        cursor.moveToFirst();
                        String dsAltura = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsAlturaTblUserAptmento)));
                        String dsPeso = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsPesoTblUserAptmento)));
                        if(dsAltura.length()>0 && dsPeso.length()>0){
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
