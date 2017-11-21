package com.possoajudar.app.application.service.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.IDaoModel;
import com.possoajudar.app.domain.dao.MySQLiteOpenHelper;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

/**
 * Created by Renato on 29/09/2017.
 */

public class DaoModelPresenter {
    private IDaoModel view;
    private DaoModelService service;


    public DaoModelPresenter(IDaoModel view, Context context){
        this.view = view;
        service = new DaoModelService(context);
    }

    public void createdbInterno(){
        try{
                if(service.createdbInterno()){
                    service.exportInternoToExternoDB();
                    view.showSucessInternoDB();
                }else{
                    view.showErrorInternoDB(R.string.errorDbCreateMemoriaInterna);
                }

        }catch (Exception e){
            view.showErrorInternoDB(R.string.errorDbCreateMemoriaInterna);
            e.getMessage().toString();
        }
    }

    public void createdbExterno(){
        try{
                if(service.createFolder()){
                    if(service.createdbExterno()){
                        view.showSucessExternoDB();
                    }else{
                        view.showErrorExternoDB(R.string.errorDbCreateMemoriaExterna);
                    }
                }else{
                    view.showErrorExternoDB(R.string.errorCreateFolderExterna);
                }

        }catch (Exception e){
            view.showErrorExternoDB(R.string.errorDbCreateMemoriaExterna);
            e.getMessage().toString();
        }
    }


    public void checkExistDbInterno(){
        SQLiteDatabase database;
        try{
            database = service.getdbExterno();
            if(database != null){
                database.execSQL("");
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public void checkExistDbExterno(){
        try{

        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}