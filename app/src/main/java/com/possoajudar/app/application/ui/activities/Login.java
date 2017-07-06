package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.login.LoginPresenter;
import com.possoajudar.app.application.service.login.LoginService;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;


/**
 * Created by Renato on 02/07/2017.
 */

public class Login extends Activity implements ILoginView {

    private EditText usernameView;
    private EditText passwordView;
    private LoginPresenter loginPresenter;
    private LoginService loginService;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_login);

        usernameView = (EditText) findViewById(R.id.input_email);
        passwordView = (EditText) findViewById(R.id.input_password);

        loginPresenter = new LoginPresenter(this, loginService);
    }


    public void onLoginClicked(View view) {
        loginPresenter.onLoginClicked();
    }

    @Override
    public String getUsername() {
        return usernameView.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordView.getText().toString();
    }

    @Override
    public void showUsernameError(int resId) {
        usernameView.setError(getString(resId));
        Toast.makeText(this, getString(resId), LENGTH_LONG).show();
    }

    @Override
    public void showPasswordError(int resId) {
        passwordView.setError(getString(resId));
    }

    @Override
    public void showLoginError(int resId) {
        Toast.makeText(this, getString(resId), LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        Toast.makeText(this, getString(R.string.strLyLoginStatusloginok), LENGTH_SHORT).show();
        new ActivityUtil(this).startMainActivity();
    }
}
