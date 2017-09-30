package com.possoajudar.app.application.service;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Renato on 29/09/2017.
 */

public interface IDaoModel {
    SQLiteDatabase getdbInterno(SQLiteDatabase sqLiteDatabase);
    SQLiteDatabase getdbExterno(SQLiteDatabase sqLiteDatabase);

    void showdbInternoError(int resId);
    void showdbExternoError(int resId);
    void startdbSucess();
    void startdbSucessSDCARD();
}
