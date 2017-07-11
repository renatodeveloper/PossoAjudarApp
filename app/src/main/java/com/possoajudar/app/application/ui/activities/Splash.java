package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.possoajudar.app.R;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

/**
 * Created by renato on 11/07/2017.
 */

public class Splash extends Activity {
    public ActivityUtil activityUtil;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUserLogado();
    }

    public void checkUserLogado(){
        try{
            activityUtil = new ActivityUtil(getApplicationContext());
            SharedPreferences facePref = getApplicationContext().getSharedPreferences(getString(R.string.prefArq_userLogado), MODE_PRIVATE);
            if (facePref != null) {
                if(facePref.getBoolean(getApplicationContext().getString(R.string.prefStatus_userLogado), false)){
                    startActivity(new Intent(this, MainActivity.class));
                    return;
                }
            }
            startActivity(new Intent(this, Login.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
