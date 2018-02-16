package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.possoajudar.app.BuildConfig;
import com.possoajudar.app.R;
//import com.possoajudar.app.application.module.app.LoginApplication;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.application.service.login.LoginPresenter;
import com.possoajudar.app.application.service.login.LoginService;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import javax.inject.Inject;


import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Renato on 02/07/2017.
 */


public class Login extends Activity implements ILoginView {
    /* Butter Knife
               @BindView(R.id.input_email) EditText usernameView;
               @BindView(R.id.input_password) EditText passwordView;
               @BindView(R.id.sucessologin) TextView sucessologin;
        */

    /* RoboGuice
    public class Login extends RoboActivity implements ILoginView {...
                @InjectView(R.id.input_email)    EditText    usernameView;
                @InjectView(R.id.input_password) EditText    passwordView;
                @InjectView(R.id.sucessologin)   TextView    sucessologin;
                @InjectView(R.id.btn_login)      AppCompatButton      onLoginClick;
     */

    private EditText usernameView;
    private EditText passwordView;
    TextView sucessologin;
    TextView newcountView;

    private LoginPresenter loginPresenter;
    private LoginService loginService;
    public ActivityUtil activityUtil;
    GpsService gps;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Inject
    Apontamento apontamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_login);
        //ButterKnife.bind(this);
       //((LoginApplication) getApplication()).getAppComponent().inject(this);

        //final String URL = BuildConfig.API_URL;
        try{
            activityUtil = new ActivityUtil();
        }catch (Exception e){
            e.getMessage().toString();
        }

        usernameView = (EditText) findViewById(R.id.input_email);
        passwordView = (EditText) findViewById(R.id.input_password);
        sucessologin = (TextView) findViewById(R.id.sucessologin);
        newcountView = (TextView) findViewById(R.id.newcount);

        loginPresenter = new LoginPresenter(this, loginService, getApplicationContext());

        newcountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    gps = new GpsService(getApplicationContext());
                    if(gps.canGetLocation()){
                        if(usernameView.getText().toString().length()>0 || passwordView.getText().toString().length()>0){
                            activityUtil.definePrefFormLogin(getApplicationContext(), gps, activityUtil.getValeuJson(getApplicationContext(), usernameView.getText().toString(), passwordView.getText().toString()));
                            startActivity(new Intent(Login.this, CadUsuario.class));
                        }else{
                            startActivity(new Intent(Login.this, CadUsuario.class));
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Habilite o GPS", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.getMessage().toString();
                }
            }
        });

        /* Test: RoboGuice
           onLoginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "RoboGuice", Toast.LENGTH_LONG).show();
            }
        });
         */
    }

    /* butterknife.OnClick
         @OnClick(R.id.btn_login)
        public void submit() {
            // TODO submit data to server...
            Toast.makeText(getApplicationContext(), "Butter Knife", Toast.LENGTH_LONG).show();
        }
     */


    public void onLoginClick(View view) {
        //Toast.makeText(getApplicationContext(), "Dagger 2: " + apontamento.toString() , Toast.LENGTH_LONG).show();
        loginPresenter.onLoginClicked();
        //throw new RuntimeException("This is a crash");
    }

    @Override
    public String getUsername() {return usernameView.getText().toString();}

    @Override
    public String getPassword() {
        return passwordView.getText().toString();
    }

    @Override
    public void showUsernameError(int resId) {
        usernameView.setError(getString(resId));
    }

    @Override
    public void showPasswordError(int resId) {
        passwordView.setError(getString(resId));
    }

    @Override
    public void showLoginError(int resId) {
        sucessologin.setText("ERRO LOGIN");
        Toast.makeText(this, getString(resId), LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        gps = new GpsService(getApplicationContext());
        if(gps.canGetLocation()){
            activityUtil.definePrefUserLogado(getApplicationContext(), gps, activityUtil.getValeuJson(this, usernameView.getText().toString(), passwordView.getText().toString()));
            sucessologin.setText("SUCESSO");
            startActivity(new Intent(this, MainActivity.class));
            //new ActivityUtil(this).startMainActivity();
        }else{
            gps.showSettingsAlert(this);
        }
    }
}