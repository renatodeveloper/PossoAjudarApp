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
                values.put(context.getString(R.string.dsAlturaTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_A)));
                values.put(context.getString(R.string.dsPesoTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_B)));
                values.put(context.getString(R.string.dataTimeTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_C)));
                values.put(context.getString(R.string.dsDataTimeTblUserAptmento),   jsonValueApontamento.getString(context.getString(R.string.dsGeneric_D)));

                int idUsuario = apontamentoDao.getIdUserPreferences(context);
                values.put(context.getString(R.string.idTblUser),   idUsuario);
                return  apontamentoDao.save(values);
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
