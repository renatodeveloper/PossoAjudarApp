package com.possoajudar.app.infrastructure.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.widget.Toast;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.application.ui.activities.Login;
import com.possoajudar.app.application.ui.activities.MainActivity;
import com.possoajudar.app.application.ui.activities.Splash;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by renato on 06/07/2017.
 */

public class ActivityUtil {
    Context context;

    //localização...
    public static final int REQUEST_CODE_PERMISSION = 1;
    public static String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    //localização...

    public ActivityUtil() {
    }


    public void startMainActivity(){
        this.context.startActivity(new Intent(context, MainActivity.class));
    }

    /*
    Guarda status do usuário logado
     */
    public void definePrefLogado(Context context, GpsService gps) {
        try{
            if(gps.canGetLocation()){

                JSONObject json = new JSONObject();
                json.put(context.getString(R.string.prefStatus_userLogado),true);
                json.put(context.getString(R.string.prefDataTime_userLogado), getDateTime(context));
                json.put(context.getString(R.string.prefLatitude_userLogado), gps.getLatitude());
                json.put(context.getString(R.string.prefLongitude_userLogado), gps.getLongitude());
                json.put(context.getString(R.string.prefAltitude_userLogado), gps.getAltitude());
                json.put(context.getString(R.string.prefSpeed_userLogado), gps.getSpeed());


                SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogado), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString(context.getString(R.string.prefJSON_userLogado), json.toString());
                editor.putString(context.getString(R.string.prefStatus_userLogado),json.getString(context.getString(R.string.prefStatus_userLogado)));
                editor.putString(context.getString(R.string.prefDataTime_userLogado), json.getString(context.getString(R.string.prefDataTime_userLogado)));
                editor.putString(context.getString(R.string.prefLatitude_userLogado),json.getString(context.getString(R.string.prefLatitude_userLogado)));
                editor.putString(context.getString(R.string.prefLongitude_userLogado),json.getString(context.getString(R.string.prefLongitude_userLogado)));
                editor.putString(context.getString(R.string.prefAltitude_userLogado),json.getString(context.getString(R.string.prefAltitude_userLogado)));
                editor.putString(context.getString(R.string.prefSpeed_userLogado),json.getString(context.getString(R.string.prefSpeed_userLogado)));
                editor.commit();
            }else{
                gps.showSettingsAlert();
            }
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

    public String getDateTime(Context context){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.str_SimpleDateFormat));
            Date resultdate = new Date(System.currentTimeMillis());
            return sdf.format(resultdate);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return null;
    }

    public static boolean checkVersaoSDK(Context context){
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(context, mPermission) != MockPackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
                    }else{
                       return  true;
                    }
                }else{
                  return  true;
                }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }

    public static void showDialogPermissions(final Context context, String valeu){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.str_atencao));
        builder.setMessage(valeu + context.getString(R.string.str_infoPermissao));
        builder.setPositiveButton(context.getString(R.string.str_finalizar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();
                System.exit(0);
            }
        });

        /* builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        }); */

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showDialogPermissionsSystem(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.str_atencao));
        builder.setMessage(context.getString(R.string.str_infoPermissaoNeverAgain));
        builder.setPositiveButton(context.getString(R.string.str_finalizar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();
                System.exit(0);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void showSettingsAlert(final Context context){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
