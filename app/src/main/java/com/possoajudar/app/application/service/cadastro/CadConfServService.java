package com.possoajudar.app.application.service.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadConfServView;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.ServicoApontamento;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.domain.dao.ServicoDao;
import com.possoajudar.app.domain.dao.UsuarioDao;
import com.possoajudar.app.domain.model.Servico;
import com.possoajudar.app.domain.model.Usuario;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by renato on 07/02/2018.
 */

public class CadConfServService {

    Context context;
    private ServicoDao servicoDao;
    private UsuarioDao usuarioDao;
    private ICadConfServView iCadConfServView;


    public CadConfServService(Context context, ICadConfServView view){
        this.context = context;
        this.iCadConfServView = view;
    }


    public boolean registerConfServ(String ... params) {
        try {
            usuarioDao = new UsuarioDao(this.context);


            usuarioDao.dsLogin =   params[0].toString();
            usuarioDao.dsSenha =   params[1].toString();
            usuarioDao.idServico = Integer.valueOf(params[2].toString());
            usuarioDao.setId(usuarioDao.getId());

            ContentValues   values = new ContentValues();
                            values.put(this.context.getString(R.string.idTblUser), usuarioDao._id);
                            values.put(this.context.getString(R.string.dsLoginTblUser), usuarioDao.dsLogin);
                            values.put(this.context.getString(R.string.dsSenhaTblUser), usuarioDao.dsSenha);
                            values.put(this.context.getString(R.string.idTblServico),usuarioDao.idServico );
            return usuarioDao.save(values);
        } catch (Exception e) {
            e.getMessage().toString();
        }
        return false;
    }

}