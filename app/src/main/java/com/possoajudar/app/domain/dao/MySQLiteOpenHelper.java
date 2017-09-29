package com.possoajudar.app.domain.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.possoajudar.app.R;

import java.io.IOException;

/**
 * Created by Renato on 27/09/2017.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    /** Mantém rastreamento do contexto para que possamos carregar SQL */
    private Context contexto = null;
    private static final String DB_NAME = "AJUDAR.db";
    private static final int DB_VERSION = 1;
    public boolean flCreate = false;
    public boolean flUpgrade = false;
    public int oldVersion;
    public int newVersion;

    public void MySQLiteOpenHelper() {
    }

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.flCreate = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.flUpgrade = true;
        try {
            if (oldVersion < 2) {
                String[] sql = contexto.getString(R.string.sqlAlterTblUsuario).split("\n");
                db.beginTransaction();
                try{
                    // Cria a tabela e testa os dados
                    ExecutarComandosSQL(db, sql);
                    db.setTransactionSuccessful();
                }
                catch (SQLException e) {
                    e.toString();
                }
            }
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Inicia as tabalas padrões
     * @param db A base de dados onde os comandos serão executados
     */
    public void startdb(SQLiteDatabase db) throws IOException {
        String[] sql = contexto.getString(R.string.sqlCreateTableUsuario).split("\n");
        db.beginTransaction();
        try{
            // Cria a tabela e testa os dados
            ExecutarComandosSQL(db, sql);
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            e.toString();
        }
        finally {
            db.endTransaction();
        }
    }

    /**
     * Executa todos os comandos SQL passados no vetor String[]
     * @param db A base de dados onde os comandos serão executados
     * @param sql Um vetor de comandos SQL a serem executados
     */
    private void ExecutarComandosSQL(SQLiteDatabase db, String[] sql) {
        try{
            for( String s : sql )
                if (s.trim().length()>0)
                    db.execSQL(s);
        }catch (SQLException e){
            e.getMessage().toString();
        }
    }
}