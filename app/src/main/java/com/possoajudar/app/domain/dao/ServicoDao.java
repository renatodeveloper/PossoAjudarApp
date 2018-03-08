package com.possoajudar.app.domain.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.IDaoModel;
import com.possoajudar.app.application.service.dao.DaoModelPresenter;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.domain.DAO;
import com.possoajudar.app.domain.model.Servico;
import com.possoajudar.app.domain.model.Transacao;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by renato on 08/03/2018.
 */

public class ServicoDao extends Servico implements DAO<Servico> {
    private MySQLiteOpenHelper openHelper;

    private DaoModelPresenter daoModelPresenter;
    private IDaoModel viewIDaoModel;

    private long _id = 0;
    public static final String TABLE_SERVICO = "SERVICO";

    Context context;

    //default construction

    public ServicoDao(Context context){
        this.daoModelPresenter = new DaoModelPresenter(context);
        this.context = context;
    }
    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public void createTable() throws Exception {

        SQLiteDatabase db = null;
        try {
            db = this.daoModelPresenter.getInternalDB();
            openHelper.startdb(db);
        } finally {
            this.daoModelPresenter.closeInternalDB(db);
        }

    }

    @Override
    public boolean save() throws Exception {
        boolean result = false;

        SQLiteDatabase db = null;
        try {
            db = this.daoModelPresenter.getInternalDB();
            //ContentValues values = DatabaseUtils.toValues(new Object[] {this});
            ContentValues values = null;
            if (_id < 1) {
                _id = db.insert(TABLE_SERVICO, "", values);
            } else {
                db.update(TABLE_SERVICO, values, "_id=" + _id, null);
            }
            result = true;
        } catch (SQLiteException e){
            e.getMessage().toString();
            return result;
        }finally {
            db.close();
        }

        return result;
    }

    @Override
    public boolean save(ContentValues values) throws Exception {
        try{
            boolean result = false;

            SQLiteDatabase db = null;
            try {
                db = this.daoModelPresenter.getInternalDB();
                //ContentValues values = DatabaseUtils.toValues(new Object[] {this});
                //ContentValues values = null;
                if (_id < 1) {
                    _id = db.insert(TABLE_SERVICO, "", values);
                } else {
                    db.update(TABLE_SERVICO, values, "_id=" + _id, null);
                }
                result = true;
            } catch (SQLiteException e){
                e.getMessage().toString();
                return result;
            }finally {
                db.close();
            }

            return result;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    @Override
    public boolean remove() throws Exception {
        return false;
    }

    @Override
    public boolean check(JSONObject object) throws Exception {
        SQLiteDatabase db = null;
        try {
            db = this.daoModelPresenter.getInternalDB();
            if(db!= null && object != null && object.length()>0){
                String[] args = { object.getString(object.getString(context.getString(R.string.dsGeneric_B)))};
                Cursor cursor = db.query(this.context.getString(R.string.dsNameTblUser), null, "dsLogin=?", args, null,null,null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    int idServico = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idConfServico)));
                    String dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                    int idUser = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUser)));
                    ContentValues values = new ContentValues();
                    values.put(context.getString(R.string.idTblUser), idUser);
                    values.put(context.getString(R.string.idConfServico), idServico);
                    values.put(context.getString(R.string.dsLoginTblUser), dsLogin);
                    this.save();
                    if(idServico>0 && dsLogin.length()>0){

                        return  true;
                    }
                }else{
                    return false;
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    @Override
    public Servico retrieve(long var1) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Servico> retrieveAll() throws Exception {
        ArrayList<Servico> list = null;
        SQLiteDatabase db = null;
        Servico servico;
        try {
            db = this.daoModelPresenter.getInternalDB();
            if(db!= null){
                String[] args = { "", ""};
                Cursor cursor = db.query(this.context.getString(R.string.dsNameTblServico), null, "idServico!=? AND dsServico!=?", args, null,null,null);
                if(cursor.getCount()>0){
                    list = new ArrayList<>();
                    cursor.moveToFirst();
                    int idServico = 0;
                    String dsServico = "";

                    for(int u=0; u< cursor.getCount(); u++){
                        servico = new Servico();
                        idServico = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblServico)));
                        dsServico = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsServico)));
                        list.add(servico);
                    }
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }

        return list;
    }

    @Override
    public ArrayList<Servico> retrieveSome(String var1, String var2) throws Exception {
        return null;
    }

    @Override
    public Transacao transmitir() throws Exception {
        return null;
    }

}
