package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.possoajudar.app.R;


/**
 * Created by Renato on 02/07/2017.
 */

public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_login);
        Toast.makeText(getApplicationContext(), "MAIN", Toast.LENGTH_LONG).show();
    }
}
