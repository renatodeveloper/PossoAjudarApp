package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.application.service.cadastro.CadUserPresenter;
import com.possoajudar.app.application.service.cadastro.CadUserService;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by Renato on 26/09/2017.
 */

public class CadUsuario extends RoboActivity implements ICadUserView {

    @InjectView(R.id.lyCadUserEditTextEmail) EditText userEmail;
    @InjectView(R.id.lyCadUserEditTextSenha) EditText userSenha;
    @InjectView(R.id.lyCadUserImageViewCadastrar) ImageView addUser;
    @InjectView(R.id.lyCadUserImageViewLimpar) ImageView clanUser;

    ProgressDialog progressDialog;
    public ActivityUtil activityUtil;
    private CadUserPresenter cadUserPresenter;
    private CadUserService cadUserService;

    GpsService gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_cad_usuario);

        cadUserPresenter = new CadUserPresenter(this, cadUserService);

        progressDialog = new ProgressDialog(this);

        try{
            activityUtil = new ActivityUtil();
        }catch (Exception e){
            e.getMessage().toString();
        }

        addUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Atenção");
                //progressDialog.show();
                //Toast.makeText(getApplicationContext(), "Olá..", Toast.LENGTH_LONG).show();
                cadUserPresenter.registerNewUser();
            }
        });

        clanUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail.setText("");
                userSenha.setText("");
            }
        });
    }

    @Override
    public String getCadUserEmail() {
        return userEmail.getText().toString();
    }

    @Override
    public String getCadUserSenha() {
        return userSenha.getText().toString();
    }

    @Override
    public void showCadUserEmailError(int resId) {
        userEmail.setError(getString(resId));
    }

    @Override
    public void showCadUserPasswordError(int resId) {
        userSenha.setError(getString(resId));
    }

    @Override
    public void showCadUserError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void startMainActivity() {
        gps = new GpsService(getApplicationContext());
        if(gps.canGetLocation()){
            activityUtil.definePrefLogado(getApplicationContext(), gps);
            startActivity(new Intent(this, MainActivity.class));
            //new ActivityUtil(this).startMainActivity();
        }else{
            gps.showSettingsAlert(this);
        }
    }
}