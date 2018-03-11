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
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.domain.model.Transacao;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by renato on 05/03/2018.
 */

public class ApontamentoDao extends Apontamento implements DAO<Apontamento>{
    private MySQLiteOpenHelper openHelper;

    private DaoModelPresenter daoModelPresenter;
    private IDaoModel viewIDaoModel;

    private long _id = 0;
    public static final String TABLE_NAME_APONTAMENTO = "APONTAMENTO";

    Context context;

    //default construction

    public ApontamentoDao(Context context){
        this.daoModelPresenter = new DaoModelPresenter(context);
        this.context = context;
    }

    public long getIdDao() {
        return _id;
    }

    public void setIdDao(long _id) {
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
                _id = db.insert(TABLE_NAME_APONTAMENTO, "", values);
            } else {
                db.update(TABLE_NAME_APONTAMENTO, values, "_id=" + _id, null);
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
                    _id = db.insert(TABLE_NAME_APONTAMENTO, "", values);
                } else {
                    db.update(TABLE_NAME_APONTAMENTO, values, "_id=" + _id, null);
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
        return false;
    }

    @Override
    public Apontamento retrieve(long var1) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Apontamento> retrieveAll() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Apontamento> retrieveSome(String var1, String var2) throws Exception {
        return null;
    }

    @Override
    public Transacao transmitir() throws Exception {
        return null;
    }

    /*
        Métodos
     */
    public boolean registreNewApontamentoOlder(JSONObject jsonValueApontamento, Context context){
        SQLiteDatabase db = null;
        try{
            db = this.daoModelPresenter.getInternalDB();
            if(db!= null && jsonValueApontamento != null && jsonValueApontamento.length()>0){
                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.dsAlturaTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_A)));
                values.put(context.getString(R.string.dsPesoTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_B)));
                values.put(context.getString(R.string.dataTimeTblUserAptmento), jsonValueApontamento.getString(context.getString(R.string.dsGeneric_C)));
                values.put(context.getString(R.string.dsDataTimeTblUserAptmento),   jsonValueApontamento.getString(context.getString(R.string.dsGeneric_D)));

                int idUsuario = getIdUserPreferences(context);
                values.put(context.getString(R.string.idTblUser),   idUsuario);

                long result  = db.insert(context.getString(R.string.dsNameTblUserAptmento), null, values);
                if(result >0){
                    //check insert select... fazer com o valida user
                    String[] args = { jsonValueApontamento.getString(context.getString(R.string.dsGeneric_A)),
                            jsonValueApontamento.getString(context.getString(R.string.dsGeneric_B)),
                            jsonValueApontamento.getString(context.getString(R.string.dsGeneric_C)),
                            jsonValueApontamento.getString(context.getString(R.string.dsGeneric_D)),
                            String.valueOf(idUsuario)};
                    Cursor cursor = db.query(context.getString(R.string.dsNameTblUserAptmento), null,
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
    public int getIdUserPreferences(Context context){
        SQLiteDatabase db = null;
        try{
            ActivityUtil util = new ActivityUtil();
            JSONObject objectU = util.recuperaPrefUserLogado(context);
            String login  = objectU.getString(context.getString(R.string.dsLoginTblUser));
            String senha  = objectU.getString(context.getString(R.string.dsSenhaTblUser));

            DaoModelService daoModelService = null;
            db = this.daoModelPresenter.getInternalDB();
            String[] args = { login, senha};
            Cursor cursor = db.query(context.getString(R.string.dsNameTblUser), null, "dsLogin=? AND dsSenha=?"  , args, null,null,null);
            int qtde = cursor.getCount();
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

    public JSONArray getJSONArrayApontamentoUser(){
        SQLiteDatabase db = null;
        JSONArray arrayReturn = null;
        try{
            int idUsuarioLogado = getIdUserPreferences(context);
            DaoModelService daoModelService = null;
            db = this.daoModelPresenter.getInternalDB();
            String[] args = { String.valueOf(idUsuarioLogado)};
            Cursor cursor = db.query(context.getString(R.string.dsNameTblUserAptmento), null, "idUsuario=?"  , args, null,null,"idApontamento desc");
            int qtde = cursor.getCount();
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
                    int imc = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.imcTblUserAptmento)));
                    String dsStatus = cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsStatusTblUserAptmento)));
                    int idUsuario  = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUser)));

                    jsonObject.put("idApontamento",cursor.getInt(cursor.getColumnIndex(context.getString(R.string.idTblUserAptmento))));
                    jsonObject.put("dataHora",cursor.getInt(cursor.getColumnIndex(context.getString(R.string.dataTimeTblUserAptmento))));
                    jsonObject.put("dsDataHora",cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsDataTimeTblUserAptmento))));
                    jsonObject.put("vlAltura",cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsAlturaTblUserAptmento))));
                    jsonObject.put("vlPeso",cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsPesoTblUserAptmento))));
                    jsonObject.put("imc",cursor.getString(cursor.getColumnIndex(context.getString(R.string.imcTblUserAptmento))));
                    jsonObject.put("dsStatus",cursor.getString(cursor.getColumnIndex(context.getString(R.string.dsStatusTblUserAptmento))));
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

    public boolean ifExistApontamento (){
        SQLiteDatabase db = null;
        try{
            int idUsuarioLogado = getIdUserPreferences(context);
            db = this.daoModelPresenter.getInternalDB();
            String[] args = { String.valueOf(idUsuarioLogado)};
            Cursor cursor = db.query(context.getString(R.string.dsNameTblUserAptmento), null, "idUsuario=?"  , args, null,null,null);
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