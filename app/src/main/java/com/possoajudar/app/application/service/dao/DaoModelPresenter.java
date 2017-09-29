package com.possoajudar.app.application.service.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.IDaoModel;
import com.possoajudar.app.domain.dao.MySQLiteOpenHelper;

/**
 * Created by Renato on 29/09/2017.
 */

public class DaoModelPresenter {
    private IDaoModel view;
    Context context;
    private MySQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    public DaoModelPresenter(IDaoModel view, Context context){
        this.view = view;
        this.context = context;
    }

    public void createDbInterno(){
        try{
            openHelper = new MySQLiteOpenHelper(this.context);
            if(openHelper == null){
                view.showdbInternoError(R.string.errorDbCreateMemoriaInterna);
                return;
            }
            db = openHelper.getWritableDatabase();
            if(db == null){
                view.showdbInternoError(R.string.errorDbWriteMemoriaInterna);
                return;
            }
            String bla = db.getPath().toString();
            if (openHelper.flCreate) {
                openHelper.flCreate = false;
                openHelper.startdb(db);
            }else if (openHelper.flUpgrade) {
                openHelper.flUpgrade = false;
                openHelper.onUpgrade(db, openHelper.oldVersion, openHelper.newVersion);
            }
            view.startdbSucess();
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public void getDbInterno(){
        try{
            openHelper = new MySQLiteOpenHelper(this.context);
            if(openHelper == null){
                view.showdbInternoError(R.string.errorDbCreateMemoriaInterna);
                return;
            }
            db = openHelper.getWritableDatabase();
            if(db == null){
                view.showdbInternoError(R.string.errorDbWriteMemoriaInterna);
                return;
            }
            String bla = db.getPath().toString();
            if (openHelper.flCreate) {
                openHelper.flCreate = false;
                openHelper.startdb(db);
            }else if (openHelper.flUpgrade) {
                openHelper.flUpgrade = false;
                openHelper.onUpgrade(db, openHelper.oldVersion, openHelper.newVersion);
            }
            view.getdbInterno(db);
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
