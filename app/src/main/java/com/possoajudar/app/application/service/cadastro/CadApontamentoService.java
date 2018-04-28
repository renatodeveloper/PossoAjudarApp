package com.possoajudar.app.application.service.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadApontamentoView;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.domain.dao.ApontamentoDao;
import com.possoajudar.app.domain.dao.UsuarioDao;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Renato on 03/12/2017.
 */

public class CadApontamentoService {

    private Context context;
    private ICadApontamentoView view;
    private ApontamentoDao apontamentoDao;


    public CadApontamentoService(Context context, ICadApontamentoView view){
        this.context = context;
        this.view = view;
    }

    public boolean registreNewApontamento(JSONObject jsonValueApontamento){
        try {
            apontamentoDao = new ApontamentoDao(this.context);
            if(jsonValueApontamento != null && jsonValueApontamento.length()>0){
                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.dsAlturaTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsAlturaTblUserAptmento)));
                values.put(context.getString(R.string.dsPesoTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsPesoTblUserAptmento)));
                values.put(context.getString(R.string.dataTimeTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dataTimeTblUserAptmento)));
                values.put(context.getString(R.string.dsDataTimeTblUserAptmento),   jsonValueApontamento.getString(context.getString(R.string.dsDataTimeTblUserAptmento)));
                values.put(context.getString(R.string.imcTblUserAptmento),   jsonValueApontamento.getString(context.getString(R.string.imcTblUserAptmento)));
                values.put(context.getString(R.string.dsStatusTblUserAptmento),   jsonValueApontamento.getString(context.getString(R.string.dsStatusTblUserAptmento)));
                values.put(context.getString(R.string.dsGPD),   jsonValueApontamento.getString(context.getString(R.string.dsGPD)));
                values.put(context.getString(R.string.dsLatitude),   jsonValueApontamento.getString(context.getString(R.string.dsLatitude)));
                values.put(context.getString(R.string.dsLongitude),   jsonValueApontamento.getString(context.getString(R.string.dsLongitude)));

                int idUsuario = apontamentoDao.getIdUserPreferences(context);
                if(idUsuario>0){
                    values.put(context.getString(R.string.idTblUser),   idUsuario);
                    return  apontamentoDao.save(values);
                }else{
                    return false;
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    public JSONArray getJSONArrayApontamentoUser(){
        JSONArray jsonArray = null;
        try{
            apontamentoDao = new ApontamentoDao(this.context);
            return apontamentoDao.getJSONArrayApontamentoUser();
        }catch (Exception e){
            e.getMessage().toString();
        }
        return jsonArray;
    }

    public JSONArray getJSONArrayApontamentoUser(String date){
        JSONArray jsonArray = null;
        try{
            apontamentoDao = new ApontamentoDao(this.context);
            return apontamentoDao.getJSONArrayApontamentoUser(date);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return jsonArray;
    }


    public boolean ifExistApontamento (){
        try{
            apontamentoDao = new ApontamentoDao(this.context);
            return apontamentoDao.ifExistApontamento();
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
}
