package com.possoajudar.app.domain.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.IDaoModel;
import com.possoajudar.app.application.service.dao.DaoModelPresenter;
import com.possoajudar.app.domain.DAO;
import com.possoajudar.app.domain.model.Transacao;
import com.possoajudar.app.domain.model.Usuario;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Renato on 27/09/2017.
 */

public class UsuarioDao extends Usuario implements DAO<Usuario> {
    private MySQLiteOpenHelper openHelper;

    private DaoModelPresenter daoModelPresenter;
    private IDaoModel viewIDaoModel;

    public long _id = 0;
    public static final String TABLE_NAME_USER = "USUARIO";

    Context context;

    //default construction
    public UsuarioDao(Context context){
        this.daoModelPresenter = new DaoModelPresenter(context);
        this.context = context;
    }
    public long getId() {
        SQLiteDatabase db = null;
        try{
            db = this.daoModelPresenter.getInternalDB();
            if(db!= null){
                String[] args = { this.dsLogin, this.dsSenha};
                Cursor cursor = db.query(this.context.getString(R.string.dsNameTblUser), null, "dsLogin=? AND dsSenha=?", args, null,null,null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    _id = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUser)));
                    String dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                    String dsSenha = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsSenhaTblUser)));
                    if(_id > 0 && dsLogin.length()>0 && dsSenha.length()>0){
                        return  this._id;
                    }
                }else{
                    return 0;
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
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
                _id = db.insert(TABLE_NAME_USER, "", values);
            } else {
                db.update(TABLE_NAME_USER, values, "_id=" + _id, null);
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
                    _id = db.insert(TABLE_NAME_USER, "", values);
                } else {
                    db.update(TABLE_NAME_USER, values, "idUsuario=" + _id, null);
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



    public byte[] getBytePhoto(JSONObject object) throws Exception {
        SQLiteDatabase db = null;
        try {
            db = this.daoModelPresenter.getInternalDB();
            if(db!= null && object != null && object.length()>0){
                String[] args = { object.getString(this.context.getString(R.string.dsLoginTblUser))};
                Cursor cursor = db.query(this.context.getString(R.string.dsNameTblUser), null, "dsLogin=?", args, null,null,null);
                int qtde = cursor.getCount();
                if(cursor.getCount()>0){
                    cursor.moveToFirst();

                    byte[] bytePhoto = cursor.getBlob(cursor.getColumnIndex(context.getString(R.string.bytePhoto)));
                    if(bytePhoto != null && bytePhoto.length>0){
                        return  bytePhoto;
                    }
                }else{
                    return null;
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return null;
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
                String[] args = { object.getString(this.context.getString(R.string.dsLoginTblUser)), object.getString(context.getString(R.string.dsSenhaTblUser))};
                Cursor cursor = db.query(this.context.getString(R.string.dsNameTblUser), null, "dsLogin=? AND dsSenha=?", args, null,null,null);
                int qtde = cursor.getCount();
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    String dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                    String dsSenha = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsSenhaTblUser)));
                    if(dsLogin.length()>0 && dsSenha.length()>0){
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
    public Usuario retrieve(long var1) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Usuario> retrieveAll() throws Exception {
        ArrayList<Usuario> list = null;
        SQLiteDatabase db = null;
        Usuario usuario;
        try {
            db = this.daoModelPresenter.getInternalDB();
            if(db!= null){
                String[] args = { "", ""};
                Cursor cursor = db.query(this.context.getString(R.string.dsNameTblUser), null, "dsLogin!=? AND dsSenha!=?", args, null,null,null);
                if(cursor.getCount()>0){
                    list = new ArrayList<>();
                    cursor.moveToFirst();
                    String dsLogin = "";
                    String dsSenha = "";

                    for(int u=0; u< cursor.getCount(); u++){
                        usuario = new Usuario();
                        dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                        dsSenha = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsSenhaTblUser)));
                        list.add(usuario);
                    }

                    //String dsLogin = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsLoginTblUser)));
                    //String dsSenha = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsSenhaTblUser)));
                    //if(dsLogin.length()>0 && dsSenha.length()>0){
                        //return  true;
                    //}
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }

        return list;
    }

    @Override
    public ArrayList<Usuario> retrieveSome(String var1, String var2) throws Exception {
        return null;
    }

    @Override
    public Transacao transmitir() throws Exception {
        return null;
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
  /*
    DaoModelService daoModelService;

    public boolean registerNewUser(JSONObject jsonValueLogin, Context context){
        try{
            if(!checkUserExist(jsonValueLogin, context)){
                SQLiteDatabase database =  daoModelService.getdbInterno(context);
                if(database!= null && jsonValueLogin != null && jsonValueLogin.length()>0){
                    ContentValues values = new ContentValues();
                    values.put(context.getString(R.string.dsLoginTblUser), jsonValueLogin.getString(context.getString(R.string.dsGeneric_A)));
                    values.put(context.getString(R.string.dsSenhaTblUser), jsonValueLogin.getString(context.getString(R.string.dsGeneric_B)));
                    values.put(context.getString(R.string.idConfServico), context.getString(R.string.dsConfServicoDefaultAlldays));
                    long result  = database.insert(context.getString(R.string.dsNameTblUser), null, values);
                    if(result >0){
                        //check insert select... fazer com o valida user
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

                        //String[] args = { "first string", "second@string.com" };
                        //Cursor cursor = db.query("TABLE_NAME", null, "name=? AND email=?", args, null,null,null);
                    }
                }
            }else{
                return false;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }

    public static boolean checkUserExist(JSONObject jsonValueLogin, Context context){
        DaoModelService daoModelService = null;
        try{
            SQLiteDatabase database =  daoModelService.getdbInterno(context);
            if(database!= null && jsonValueLogin != null && jsonValueLogin.length()>0){
                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.dsLoginTblUser), jsonValueLogin.getString(context.getString(R.string.dsGeneric_A)));
                //values.put(context.getString(R.string.dsSenhaTblUser), jsonValueLogin.getString(context.getString(R.string.dsGeneric_B)));
                //String[] args = { jsonValueLogin.getString(context.getString(R.string.dsGeneric_A)), jsonValueLogin.getString(context.getString(R.string.dsGeneric_B))};
                String[] args = { jsonValueLogin.getString(context.getString(R.string.dsGeneric_A))};
                Cursor cursor = database.query(context.getString(R.string.dsNameTblUser), null, "dsLogin=?", args, null,null,null);
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
        return false;
    }

     */
}