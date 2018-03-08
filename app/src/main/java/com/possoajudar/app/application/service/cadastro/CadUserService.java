package com.possoajudar.app.application.service.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.domain.dao.UsuarioDao;

import org.json.JSONObject;

/**
 * Created by Renato on 27/09/2017.
 */

public class CadUserService {
    Context context;
    private UsuarioDao usuarioDao;
    private ILoginView loginView;
    private ICadUserView cadUserView;

    public CadUserService(Context context, ILoginView loginView){
        this.context = context;
        this.loginView = loginView;
    }

    public CadUserService(Context context, ICadUserView cadUserView){
        this.context = context;
        this.cadUserView = cadUserView;
    }

    public boolean registerNewUser(JSONObject jsonValue) {
        try {
            usuarioDao = new UsuarioDao(this.context);
            if(! usuarioDao.check(jsonValue)){

                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.idConfServico), jsonValue.getString(context.getString(R.string.dsGeneric_A)));
                values.put(context.getString(R.string.dsLoginTblUser), jsonValue.getString(context.getString(R.string.dsGeneric_B)));

               return usuarioDao.save(values);
            }
        } catch (Exception e) {
            e.getMessage().toString();
        }
        return false;
    }
}