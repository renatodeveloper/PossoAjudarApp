package com.possoajudar.app.application.service.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.possoajudar.app.R;
import com.possoajudar.app.domain.dao.MySQLiteOpenHelper;
import com.possoajudar.app.domain.dao.UsuarioDao;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Renato on 20/11/2017.
 */

/*
    ATENÇÃO: SÓ CRIA, FECHA E DEVOLVE REFERENCIA DO BANCO
 */

public class DaoModelService {
    Context context;
    private MySQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    DaoModelService (Context context){
        this.context = context;
    }

    public boolean createdbInterno(){
        try{
            openHelper = new MySQLiteOpenHelper(this.context);
            if(openHelper == null){
                return false;
            }
            db = openHelper.getWritableDatabase();
            if(db == null){
                return false;
            }
            String dsPathDb = db.getPath().toString();
            if (openHelper.flCreate) {
                openHelper.flCreate = false;//flag só entra a primeira vez, ao criar
                openHelper.startdb(db);
            }else if (openHelper.flUpgrade) {
                openHelper.flUpgrade = false;
                openHelper.onUpgrade(db, openHelper.oldVersion, openHelper.newVersion);
            }
            return true;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    public boolean createFolder(){
        try{
            if(ActivityUtil.checkIfExistFolder(this.context)){
                return true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    public boolean createdbExterno(){
        try{
            openHelper = new MySQLiteOpenHelper(this.context, this.context.getResources().getString(R.string.folder));
            if(openHelper == null){
                return false;
            }
            db = openHelper.getWritableDatabase();
            if(db == null){
                return false;
            }
            String bla = db.getPath().toString();
            if (openHelper.flCreate) {
                openHelper.flCreate = false;//flag só entra a primeira vez, ao criar
                openHelper.startdb(db);
            }else if (openHelper.flUpgrade) {
                openHelper.flUpgrade = false;
                openHelper.onUpgrade(db, openHelper.oldVersion, openHelper.newVersion);
            }
            return true;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }


    public SQLiteDatabase getdbInterno(){
        try{
            openHelper = new MySQLiteOpenHelper(this.context);
            if(openHelper == null){
                return null;
            }
            db = openHelper.getWritableDatabase();
            if(db == null){
                return null;
            }
            return  db;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return null;
    }

    public static SQLiteDatabase getdbInterno(Context context){
        try{
            MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(context);
            SQLiteDatabase db;
            if(openHelper == null){
                return null;
            }
            db = openHelper.getWritableDatabase();
            if(db == null){
                return null;
            }
            return  db;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return null;
    }

    public SQLiteDatabase getdbExterno(){
        try{
            openHelper = new MySQLiteOpenHelper(this.context, this.context.getResources().getString(R.string.folder));
            if(openHelper == null){
                return null;
            }
            db = openHelper.getWritableDatabase();
            if(db == null) {
                return null;
            }
            return db;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return null;
    }

    public boolean exportInternoToExternoDB(){
        try{
            SQLiteDatabase database = getdbInterno();
            String realPath =  database.getPath().toString();

            String path = Environment.getExternalStorageDirectory().toString() + "/POSSO_DESENV/";
            File direct = new File(path);
            if (!direct.exists()) {
                if (!direct.mkdirs()) {
                    throw new Exception("Error ao criar o diretorio...");
                }
            }
            if(direct.isDirectory()){
                File sd = new File(Environment.getExternalStorageDirectory(), "/POSSO_DESENV/");
                File data = Environment.getDataDirectory();

                FileChannel origem = null;
                FileChannel destino = null;

                //String dbPath = "/user/0/com.possoajudar.app.debug/databases/AJUDAR.db"; //check this path!!!
                String dbPath_backup = "AJUDAR_DESENV.db";

                //File current_db = new File(data, dbPath);
                File current_db = new File(realPath);
                File backupDB = new File(sd, dbPath_backup);
                try {
                    sd.mkdirs();
                    origem = new FileInputStream(current_db).getChannel();
                    destino = new FileOutputStream(backupDB).getChannel();
                    destino.transferFrom(origem, 0, origem.size());
                    origem.close();
                    destino.close();
                } catch (IOException e) {
                    e.getMessage().toString();
                }
                //* após a copia ele só aparece no fileExplore, não aparece no sdCard, acesse via app fileExplore
            }
            return true;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
    public void closeDb(SQLiteDatabase db){
        try{
            if(db == null){
                if(db.isOpen()){
                    db.close();
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}