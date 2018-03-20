package com.possoajudar.app.application.service.cadastro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.application.ui.activities.MainActivity;
import com.possoajudar.app.domain.dao.UsuarioDao;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

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
                //String s = (this.cadUserView.getByteArrayPhoto() != null) ? this.cadUserView.getCadUserEmail()+ "-" + System.currentTimeMillis() : "nonephoto";
                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.dsLoginTblUser), jsonValue.getString(context.getString(R.string.dsLoginTblUser)));
                values.put(context.getString(R.string.dsSenhaTblUser), jsonValue.getString(context.getString(R.string.dsSenhaTblUser)));
                values.put(context.getString(R.string.idTblServico), jsonValue.getString(context.getString(R.string.idTblServico)));
                values.put(context.getString(R.string.idTblRedeSocial), jsonValue.getString(context.getString(R.string.idTblRedeSocial)));
                values.put(context.getString(R.string.namePhoto), (this.cadUserView.getByteArrayPhoto() != null) ? this.cadUserView.getCadUserEmail()+ "-" + System.currentTimeMillis() : "nonephoto");
                values.put(context.getString(R.string.bytePhoto), this.cadUserView.getByteArrayPhoto());

               return usuarioDao.save(values);
            }
        } catch (Exception e) {
            e.getMessage().toString();
        }
        return false;
    }

    public  byte[] getBytePhoto(){
        byte[] bytePhoto = null;
        ActivityUtil util;
        try{
            util = new ActivityUtil();
            JSONObject objectU = util.recuperaPrefUserLogado(this.context);

            usuarioDao = new UsuarioDao(this.context);
            return usuarioDao.getBytePhoto(objectU);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return bytePhoto;
    }
}