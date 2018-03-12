package com.possoajudar.app.application.ui.activities;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.possoajudar.app.R;
import com.possoajudar.app.application.service.IDaoModel;
import com.possoajudar.app.application.service.dao.DaoModelPresenter;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import io.fabric.sdk.android.Fabric;


/**
 * Created by renato on 11/07/2017.
 */

public class ViewSplash extends Activity implements IDaoModel {

    public ActivityUtil activityUtil;
    private DaoModelPresenter daoModelPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        try {
            activityUtil = new ActivityUtil();

            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                System.exit(1);
                /*
                int L = Build.VERSION_CODES.LOLLIPOP; //21
                String ANDROID_OS = Build.VERSION.RELEASE; //5.1.1
                int SDK_INT = android.os.Build.VERSION.SDK_INT;  //22
                int L_M = Build.VERSION_CODES.LOLLIPOP_MR1; //22
                 */
            }else{
                activityUtil.verifyStoragePermissionsAll(ViewSplash.this);
            }

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
                                        activityUtil.showDialogPermissionsSystem(ViewSplash.this);
                                    }else {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i].toString())) {
                                            activityUtil.showDialogPermissions(ViewSplash.this, permissions[i].toString());
                                        }
                                    }
                                }
                            }
                    }
                }else {

                    daoModelPresenter = new DaoModelPresenter(this, this);
                        /*
                Cria banco na memória interna e exporta esse mesmo banco para a memoria externa
             */
                    daoModelPresenter.createdbInterno();
                    //daoModelPresenter.createdbExterno();
                    //activityUtil.deleteDatabase(getApplicationContext());

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
                        //startActivity(new Intent(this, Dimensao.class));
                        return;

                    }
                }
            }
            startActivity(new Intent(this, Login.class));
            //startActivity(new Intent(this, Dimensao.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }


    @Override
    public void showErrorInternoDB(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorExternoDB(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sucess(String dsPackage) {
        Toast.makeText(this, dsPackage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSucessInternoDB() {
        try{
            //Toast.makeText(this, getString(R.string.sucessDbInterno), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public void showSucessExternoDB() {
        //Toast.makeText(this, R.string.sucessDbExterno, Toast.LENGTH_SHORT).show();
    }
}
