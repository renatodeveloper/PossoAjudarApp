package com.possoajudar.app.application.service;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Renato on 29/09/2017.
 */

public interface IDaoModel {
    void sucess(String dsPackage);
    void error(int resId);

    void showSucessInternoDB();
    void showErrorInternoDB(int resId);

    void showSucessExternoDB();
    void showErrorExternoDB(int resId);
}
