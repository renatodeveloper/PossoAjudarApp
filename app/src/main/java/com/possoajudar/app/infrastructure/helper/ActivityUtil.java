package com.possoajudar.app.infrastructure.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by renato on 06/07/2017.
 */

public class ActivityUtil {
    Context context;

    //localização...
    public static final int REQUEST_CODE_PERMISSION = 1;
    public static String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    //localização...



    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
        };

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

    public static boolean verifyStoragePermissions(Context context){
        try{
            if (ActivityCompat.checkSelfPermission(context, mPermission) != MockPackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
                return true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }

    public void verifyStoragePermissionsAll(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_CODE_PERMISSION
        );
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

    /*
    Cria o folder caso não exista
     */
    public static boolean checkIfExistFolder(Context context){
        File f;
        String path = "";
        try{
            path = Environment.getExternalStorageDirectory().toString() + context.getResources().getString(R.string.folder);
            f = new File(path);
            if (!f.exists()) {
                if (!f.mkdirs()) {
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return  false;
    }


    public boolean ifExistDatabase() {
        File data = Environment.getDataDirectory();
        String dbPath = "/user/0/com.possoajudar.app.debug/databases/AJUDAR.db";
        File current_db = new File(data, dbPath);
        try {
            if(current_db.exists()){
                return true;
            }
        } catch (Exception e) {
            e.getMessage().toString();
        }
        return false;
    }

    public void deleteDatabase() {
        File data = Environment.getDataDirectory();
        String dbPath = "/user/0/com.possoajudar.app.debug/databases/AJUDAR.db";
        File current_db = new File(data, dbPath);
        try {
            if(current_db.exists()){
                current_db.delete();
            }
        } catch (Exception e) {
            e.getMessage().toString();
        }
    }
    /*
    Exporte da memoria interna para a externa
     */
    public void exportDatabse()  throws Exception{

        String path = Environment.getExternalStorageDirectory().toString() + "/POSSO/";
        File direct = new File(path);
        if (!direct.exists()) {
            if (!direct.mkdirs()) {
                throw new Exception("Error ao criar o diretorio...");
            }
        }
        if(direct.isDirectory()){
            File sd = new File(Environment.getExternalStorageDirectory(), "/POSSO/");
            File data = Environment.getDataDirectory();

            FileChannel origem = null;
            FileChannel destino = null;

            String dbPath = "/user/0/com.possoajudar.app.debug/databases/AJUDAR.db";
            String dbPath_backup = "AJUDAR_dbInterno.db";

            File current_db = new File(data, dbPath);
            File backupDB = new File(sd, dbPath_backup);
            try {
                sd.mkdirs();
                origem = new FileInputStream(current_db).getChannel();
                destino = new FileOutputStream(backupDB).getChannel();
                destino.transferFrom(origem, 0, origem.size());
                origem.close();
                destino.close();
            } catch (IOException e) {
                e.getMessage().toString();
            }
            //* após a copia ele só aparece no fileExplore, não aparece no sdCard, acesse via app fileExplore
        }
    }

}
