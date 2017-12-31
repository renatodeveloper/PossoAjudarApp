package com.possoajudar.app.application.service.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONArray;
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
                values.put(context.getString(R.string.dataTimeTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_C)));
                values.put(context.getString(R.string.dsDataTimeTblUserAptmento),   jsonValueApontamento.getString(context.getString(R.string.dsGeneric_D)));

                int idUsuario = getIdUserPreferences(context);
                values.put(context.getString(R.string.idTblUser),   idUsuario);

                long result  = database.insert(context.getString(R.string.dsNameTblUserAptmento), null, values);
                if(result >0){
                    //check insert select... fazer com o valida user
                    String[] args = { jsonValueApontamento.getString(context.getString(R.string.dsGeneric_A)),
                                      jsonValueApontamento.getString(context.getString(R.string.dsGeneric_B)),
                                      jsonValueApontamento.getString(context.getString(R.string.dsGeneric_C)),
                                      jsonValueApontamento.getString(context.getString(R.string.dsGeneric_D)),
                                      String.valueOf(idUsuario)};
                    Cursor cursor = database.query(context.getString(R.string.dsNameTblUserAptmento), null,
                            "vlAltura=? " +
                                    "AND vlPeso=? " +
                                    "AND dataHora=? " +
                                    "AND dsDataHora=? " +
                                    "AND idUsuario=?"  , args, null,null,null);
                    if(cursor.getCount()>0){
                        cursor.moveToFirst();
                        String dsAltura = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsAlturaTblUserAptmento)));
                        String dsPeso = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsPesoTblUserAptmento)));
                        String dsDataApontamento = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsDataTimeTblUserAptmento)));
                        String dataApontamento = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dataTimeTblUserAptmento)));
                        if(dsAltura.length()>0 && dsPeso.length()>0 && dsDataApontamento.length()>0 && dataApontamento .length()>0){
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

    /*
   Retorna idUsuário referente ao sharedPreferences
    */
    public static int getIdUserPreferences(Context context){
        try{
            ActivityUtil util = new ActivityUtil();
            JSONObject objectU = util.recuperaPrefUserLogado(context);
            String login  = objectU.getString(context.getString(R.string.dsLoginTblUser));
            String senha  = objectU.getString(context.getString(R.string.dsSenhaTblUser));

            DaoModelService daoModelService = null;
            SQLiteDatabase database =  daoModelService.getdbInterno(context);
            String[] args = { login, senha};
            Cursor cursor = database.query(context.getString(R.string.dsNameTblUser), null, "dsLogin=? AND dsSenha=?"  , args, null,null,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                String dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                int idUsuario  = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUser)));
                String dsSenha = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsSenhaTblUser)));
                if(dsLogin.length()>0 && dsSenha.length()>0 && idUsuario > 0){
                    return  idUsuario;
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return 0;
    }

    public JSONArray getJSONArrayApontamentoUser(Context context){
        JSONArray arrayReturn = null;
        try{
            int idUsuarioLogado = getIdUserPreferences(context);
            DaoModelService daoModelService = null;
            SQLiteDatabase database =  daoModelService.getdbInterno(context);
            String[] args = { String.valueOf(idUsuarioLogado)};
            Cursor cursor = database.query(context.getString(R.string.dsNameTblUserAptmento), null, "idUsuario=?"  , args, null,null,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                arrayReturn = new JSONArray(){};
                JSONObject jsonObject;
                for(int i=0; i<cursor.getCount(); i++){
                    jsonObject = new JSONObject();

                    int idApontamento = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUserAptmento)));
                    int dataHora = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.dataTimeTblUserAptmento)));
                    String dsDataHora = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsDataTimeTblUserAptmento)));
                    String dsAltura = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsAlturaTblUserAptmento)));
                    String dsPeso = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsPesoTblUserAptmento)));
                    int idUsuario  = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUser)));

                    jsonObject.put("idApontamento",cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUserAptmento))));
                    jsonObject.put("dataHora",cursor.getInt(cursor.getColumnIndex(context.getString(R.string.dataTimeTblUserAptmento))));
                    jsonObject.put("dsDataHora",cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsDataTimeTblUserAptmento))));
                    jsonObject.put("vlAltura",cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsAlturaTblUserAptmento))));
                    jsonObject.put("vlPeso",cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsPesoTblUserAptmento))));
                    jsonObject.put("idUsuario",cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUser))));
                    cursor.moveToNext();
                 arrayReturn.put(jsonObject);
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return arrayReturn;
    }

    public boolean ifExistApontamento (Context context){
        try{
            int idUsuarioLogado = getIdUserPreferences(context);
            DaoModelService daoModelService = null;
            SQLiteDatabase database =  daoModelService.getdbInterno(context);
            String[] args = { String.valueOf(idUsuarioLogado)};
            Cursor cursor = database.query(context.getString(R.string.dsNameTblUserAptmento), null, "idUsuario=?"  , args, null,null,null);
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                int idApontamento = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUserAptmento)));
                return true;
            }
            return false;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
}
