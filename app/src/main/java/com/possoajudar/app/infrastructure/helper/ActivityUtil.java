package com.possoajudar.app.infrastructure.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.possoajudar.app.R;
import com.possoajudar.app.application.ui.activities.MainActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by renato on 06/07/2017.
 */

public class ActivityUtil {
    private Context context;

    public ActivityUtil() {
    }

    public ActivityUtil(Context context) {

        this.context = context;
    }

    public void startMainActivity(){
        this.context.startActivity(new Intent(context, MainActivity.class));
    }

    /*
    Guarda status do usuário logado
     */
    public void definePrefLogado() {
        try{
            JSONObject json = new JSONObject();
            json.put(context.getString(R.string.prefStatus_userLogado),true);
            json.put(context.getString(R.string.prefDataTime_userLogado), getDateTime());

            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogado), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(context.getString(R.string.prefJSON_userLogado), json.toString());
            editor.putBoolean(context.getString(R.string.prefStatus_userLogado), true);
            editor.putString(context.getString(R.string.prefDataTime_userLogado), getDateTime());
            editor.commit();
        }catch (Exception e) {
            e.getMessage().toString();
        }
    }

        /*
    Limpa status do usuário logado
    */
    public void cleanPrefLogado() {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogado), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }

    public String getDateTime(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date resultdate = new Date(System.currentTimeMillis());
            return sdf.format(resultdate);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return null;
    }
}
