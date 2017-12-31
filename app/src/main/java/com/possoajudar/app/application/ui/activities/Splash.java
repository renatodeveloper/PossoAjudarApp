package com.possoajudar.app.application.ui.activities;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.possoajudar.app.R;
import com.possoajudar.app.application.service.IDaoModel;
import com.possoajudar.app.application.service.dao.DaoModelPresenter;
import com.possoajudar.app.domain.dao.MySQLiteOpenHelper;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


/**
 * Created by renato on 11/07/2017.
 */

public class Splash extends Activity implements IDaoModel {

    public ActivityUtil activityUtil;
    private DaoModelPresenter daoModelPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        try {
            activityUtil = new ActivityUtil();

            activityUtil.verifyStoragePermissionsAll(Splash.this);
            //activityUtil.verifyStoragePermissions(Splash.this);
            /*
            if(activityUtil.checkVersaoSDK(Splash.this)){
                checkUserLogado();
            }
             */
            /*
                Cria banco na memória interna e exporta esse mesmo banco para a memoria externa
             */
            daoModelPresenter = new DaoModelPresenter(this, getApplicationContext());
            daoModelPresenter.createdbInterno();

            //daoModelPresenter.createdbExterno();

            //activityUtil.deleteDatabase(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ActivityUtil.REQUEST_CODE_PERMISSION: {
                boolean flagDenied = false;
                for(int x : grantResults){
                    if(x == -1){
                        flagDenied = true;
                        break;
                    }
                }
                if(flagDenied){
                    if(grantResults != null && grantResults.length>0){
                            for(int i=0; i< grantResults.length; i++){
                                if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                                    // user rejected the permission - PERMISSION_DENIED is '-1' | ACCEPT is 0
                                    boolean checkNaoPetube = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i].toString());//devolve false quando selecionado
                                    if(!checkNaoPetube){
                                        activityUtil.showDialogPermissionsSystem(Splash.this);
                                    }else {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i].toString())) {
                                            activityUtil.showDialogPermissions(Splash.this, permissions[i].toString());
                                        }
                                    }
                                }
                            }
                    }
                }else {
                    checkUserLogado();
                }

            return;
        }
        }
    }


    public void checkUserLogado(){
        try{

            SharedPreferences facePref = getApplicationContext().getSharedPreferences(getApplicationContext().getString(R.string.prefArq_userLogado), Context.MODE_PRIVATE);
            if (facePref != null) {
                if (facePref.getString(getApplicationContext().getString(R.string.prefStatus_userLogado), null) != null) {
                    String sjonPerfil = (facePref.getString(getApplicationContext().getString(R.string.prefStatus_userLogado), null));
                    if (sjonPerfil.equals("true")) {
                        startActivity(new Intent(this, MainActivity.class));
                        return;

                    }
                }
            }
            startActivity(new Intent(this, Login.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }


    @Override
    public void showErrorInternoDB(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorExternoDB(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSucessInternoDB() {
        try{
            Toast.makeText(this, R.string.sucessDbInterno, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public void showSucessExternoDB() {
        Toast.makeText(this, R.string.sucessDbExterno, Toast.LENGTH_LONG).show();
    }
}
