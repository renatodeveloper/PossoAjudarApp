package com.possoajudar.app.application.service.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.domain.dao.UsuarioDao;

import org.json.JSONObject;

import javax.xml.transform.sax.TemplatesHandler;

/**
 * Created by renato on 06/07/2017.
 */

public class LoginService {
    private Context context;
    private ILoginView view;
    private UsuarioDao usuarioDao;

    private DaoModelService daoModelService;

    public LoginService(Context context, ILoginView view){
        this.context = context;
        this.view = view;
    }

    public boolean login(JSONObject jsonValueLogin){
        try{
            usuarioDao = new UsuarioDao(this.context);

            return  usuarioDao.check(jsonValueLogin);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }

/*

    public boolean login(JSONObject jsonValueLogin, Context context){
        try{
            SQLiteDatabase database =  daoModelService.getdbInterno(context);

            if(database!= null && jsonValueLogin != null && jsonValueLogin.length()>0){
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
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }
 */

}
