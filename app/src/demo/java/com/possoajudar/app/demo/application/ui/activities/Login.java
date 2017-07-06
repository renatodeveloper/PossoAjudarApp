package com.possoajudar.app.demo.application.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.possoajudar.app.R;

/**
 * Created by renato on 06/07/2017.
 */

public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_login);
        Toast.makeText(getApplicationContext(), "DEMO", Toast.LENGTH_LONG).show();
    }
}
