package com.possoajudar.app.infrastructure.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.widget.Toast;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.application.ui.activities.MainActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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

    public boolean verifyStoragePermissions(Context context){
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

    public void deleteDatabase(Context context) {
        ///data/data/com.possoajudar.app.demo.debug/databases/AJUDAR.db
        File data = Environment.getDataDirectory();
        String dbPath = "/data/com.possoajudar.app.demo.debug/databases/AJUDAR.db";
        String dsPathExtra = "/user/0/com.possoajudar.app.demo.debug/databases/AJUDAR.db";//mysmartphone
        File current_db = new File(data, dbPath);
        File current_dbII = new File(data, dsPathExtra);
        try {
            if(current_db.exists()){
                current_db.delete();
            }
            if(current_dbII.exists()){
                current_dbII.delete();
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

    public JSONObject getValeuJson(Context context, String... params){
        JSONObject result = new JSONObject();
        try{
            result.put(context.getString(R.string.dsGeneric_A), params[0]);
            result.put(context.getString(R.string.dsGeneric_B), params[1]);
            result.put(context.getString(R.string.dsGeneric_C), params[2]);
            result.put(context.getString(R.string.dsGeneric_D), params[3]);
            result.put(context.getString(R.string.dsGeneric_E), params[4]);
            result.put(context.getString(R.string.dsGeneric_F), params[5]);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }

    public static String getDataHora(long currentTimeMillis){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date resultdate = new Date(currentTimeMillis);
            return  sdf.format(resultdate).toString();
        }catch (Exception e){
            e.getMessage().toString();
        }
        return "";
    }

    public static String getDataHora(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date resultdate = new Date(System.currentTimeMillis());
            return  sdf.format(resultdate).toString();
        }catch (Exception e){
            e.getMessage().toString();
        }
        return "";
    }


    /* DEFINE  ** LIMPA  ** VERIFICA  ** RECUPERA */

    //************************************** TODA VEZ QUE O SERVIÇO SOLICITAR INFORMAÇÕES DE MEDIDAS DEVIDO AO TEMPO OCIOSO TER CHEGADO AO FIM O FLAG É UTILIZADO

    public static  void definePrefFlagInfoMedidas(Context context){
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArqInfoMedidas), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(context.getString(R.string.prefArqInfoMedidasVal), true);
            editor.commit();
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public JSONObject recuperaPrefFlagInfoMedidas(Context context){
        JSONObject result = new JSONObject();
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArqInfoMedidas), Context.MODE_PRIVATE);
            result.put(context.getString(R.string.prefArqInfoMedidasVal), mPrefs.getBoolean(context.getString(R.string.prefArqInfoMedidasVal), false));

        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }

    public static  void limpaPrefFlagInfoMedidas(Context context){
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArqInfoMedidas), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    //**************************************  PREFERENCES DA TELA DE LOGIN (FORM) SEND TELA DE CADASTRO DE USUÁIO

    public void definePrefFormLogin(Context context, GpsService gps, JSONObject jsonObject) {
        try{
            if(gps.canGetLocation()){

                JSONObject json = new JSONObject();
                json.put(context.getString(R.string.dsLoginTblUser), jsonObject.getString(context.getString(R.string.dsLoginTblUser)));
                json.put(context.getString(R.string.dsSenhaTblUser), jsonObject.getString(context.getString(R.string.dsSenhaTblUser)));
                json.put(context.getString(R.string.prefStatus_userLogado),true);
                json.put(context.getString(R.string.prefDataTime_userLogado), getDateTime(context));
                json.put(context.getString(R.string.prefLatitude_userLogado), gps.getLatitude());
                json.put(context.getString(R.string.prefLongitude_userLogado), gps.getLongitude());
                json.put(context.getString(R.string.prefAltitude_userLogado), gps.getAltitude());
                json.put(context.getString(R.string.prefSpeed_userLogado), gps.getSpeed());


                SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_formLogin), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPrefs.edit();


                editor.putString(context.getString(R.string.prefJSON_userLogado), json.toString());
                editor.putString(context.getString(R.string.dsLoginTblUser), json.getString(context.getString(R.string.dsLoginTblUser)));
                editor.putString(context.getString(R.string.dsSenhaTblUser), json.getString(context.getString(R.string.dsSenhaTblUser)));
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

    // RECUPERA  Usuário
    public JSONObject recuperaPrefFormLogin(Context context){
        JSONObject result = new JSONObject();
        try{

            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_formLogin), Context.MODE_PRIVATE);
            String status = mPrefs.getString(context.getString(R.string.prefStatus_userLogado), "");
            if(status.equals("true")){
                result.put(context.getString(R.string.dsLoginTblUser), mPrefs.getString(context.getString(R.string.dsLoginTblUser), ""));
                result.put(context.getString(R.string.dsLoginTblUser), mPrefs.getString(context.getString(R.string.dsLoginTblUser), ""));
                result.put(context.getString(R.string.dsSenhaTblUser), mPrefs.getString(context.getString(R.string.dsSenhaTblUser), ""));
                return result;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }
    public void limpaPrefFormLogin(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_formLogin), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }


    //**************************************  GERENCIA SE O USUÁRIO JÁ FOI LOGADO
    /* DEFINE
Guarda status do usuário logado
*/
    public void definePrefUserLogado(Context context, GpsService gps, JSONObject jsonObject) {
        try{
            if(gps.canGetLocation()){

                JSONObject json = new JSONObject();
                json.put(context.getString(R.string.dsLoginTblUser), jsonObject.getString(context.getString(R.string.dsGeneric_A)));
                json.put(context.getString(R.string.dsSenhaTblUser), jsonObject.getString(context.getString(R.string.dsGeneric_B)));
                json.put(context.getString(R.string.prefStatus_userLogado),true);
                json.put(context.getString(R.string.prefDataTime_userLogado), getDateTime(context));
                json.put(context.getString(R.string.prefLatitude_userLogado), gps.getLatitude());
                json.put(context.getString(R.string.prefLongitude_userLogado), gps.getLongitude());
                json.put(context.getString(R.string.prefAltitude_userLogado), gps.getAltitude());
                json.put(context.getString(R.string.prefSpeed_userLogado), gps.getSpeed());


                SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogado), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPrefs.edit();


                editor.putString(context.getString(R.string.prefJSON_userLogado), json.toString());
                editor.putString(context.getString(R.string.dsLoginTblUser), json.getString(context.getString(R.string.dsLoginTblUser)));
                editor.putString(context.getString(R.string.dsSenhaTblUser), json.getString(context.getString(R.string.dsSenhaTblUser)));
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

    // RECUPERA  Usuário
    public JSONObject recuperaPrefUserLogado(Context context){
        JSONObject result = new JSONObject();
        try{

            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogado), Context.MODE_PRIVATE);
            String status = mPrefs.getString(context.getString(R.string.prefStatus_userLogado), "");
            if(status.equals("true")){
                result.put(context.getString(R.string.dsLoginTblUser), mPrefs.getString(context.getString(R.string.dsLoginTblUser), ""));
                result.put(context.getString(R.string.dsLoginTblUser), mPrefs.getString(context.getString(R.string.dsLoginTblUser), ""));
                result.put(context.getString(R.string.dsSenhaTblUser), mPrefs.getString(context.getString(R.string.dsSenhaTblUser), ""));
                return result;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }


    //LIMPA Usuário logado
    public void limpaPrefUserLogado(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogado), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }

    //VERIFICA Usuário
    public boolean verificaPrefUserLogado(Context context){
        try{

            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogado), Context.MODE_PRIVATE);
            String status = mPrefs.getString(context.getString(R.string.prefStatus_userLogado), "");
            if(status.equals("true")){
                return true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    //**************************************  GERENCIA SE O USUÁRIO LOGADO JÁ TEM ALGUM APONTAMENTO

    /* DEFINE
Guarda status do apontamento do usuário
*/
    public void definePrefUserLogadoApontamentoGPS(Context context, GpsService gps, JSONObject jsonObject) {
        try{
            if(gps.canGetLocation()){

                JSONObject json = new JSONObject();
                json.put(context.getString(R.string.dsAlturaTblUserAptmento), jsonObject.getString(context.getString(R.string.dsAlturaTblUserAptmento)));
                json.put(context.getString(R.string.dsPesoTblUserAptmento), jsonObject.getString(context.getString(R.string.dsPesoTblUserAptmento)));
                json.put(context.getString(R.string.prefStatus_userLogadoApontamento),true);
                json.put(context.getString(R.string.prefDataTime_userLogado), getDateTime(context));
                json.put(context.getString(R.string.prefLatitude_userLogado), gps.getLatitude());
                json.put(context.getString(R.string.prefLongitude_userLogado), gps.getLongitude());
                json.put(context.getString(R.string.prefAltitude_userLogado), gps.getAltitude());
                json.put(context.getString(R.string.prefSpeed_userLogado), gps.getSpeed());


                SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogadoApontamento), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPrefs.edit();


                editor.putString(context.getString(R.string.prefJSON_userLogado), json.toString());
                editor.putString(context.getString(R.string.dsAlturaTblUserAptmento), json.getString(context.getString(R.string.dsAlturaTblUserAptmento)));
                editor.putString(context.getString(R.string.dsPesoTblUserAptmento), json.getString(context.getString(R.string.dsPesoTblUserAptmento)));
                editor.putString(context.getString(R.string.prefStatus_userLogadoApontamento),json.getString(context.getString(R.string.prefStatus_userLogadoApontamento)));
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

    public JSONObject recuperaPrefUserLogadoApontamentoGPS(Context context){
        JSONObject result = new JSONObject();
        try{

            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogadoApontamento), Context.MODE_PRIVATE);
            String status = mPrefs.getString(context.getString(R.string.prefStatus_userLogadoApontamento), "");
            if(status.equals("true")){
                result.put(context.getString(R.string.prefJSON_userLogado), mPrefs.getString(context.getString(R.string.prefJSON_userLogado), ""));
                result.put(context.getString(R.string.dsAlturaTblUserAptmento), mPrefs.getString(context.getString(R.string.dsAlturaTblUserAptmento), ""));
                result.put(context.getString(R.string.dsPesoTblUserAptmento), mPrefs.getString(context.getString(R.string.dsPesoTblUserAptmento), ""));
                result.put(context.getString(R.string.prefStatus_userLogadoApontamento), mPrefs.getString(context.getString(R.string.prefStatus_userLogadoApontamento), ""));
                result.put(context.getString(R.string.prefDataTime_userLogado), mPrefs.getString(context.getString(R.string.prefDataTime_userLogado), ""));
                result.put(context.getString(R.string.prefLatitude_userLogado), mPrefs.getString(context.getString(R.string.prefLatitude_userLogado), ""));
                result.put(context.getString(R.string.prefLongitude_userLogado), mPrefs.getString(context.getString(R.string.prefLongitude_userLogado), ""));
                result.put(context.getString(R.string.prefAltitude_userLogado), mPrefs.getString(context.getString(R.string.prefAltitude_userLogado), ""));
                result.put(context.getString(R.string.prefSpeed_userLogado), mPrefs.getString(context.getString(R.string.prefSpeed_userLogado), ""));
                return result;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }

    //LIMPA ApontamentoResponse
    public void limpaPrefUserLogadoApontamentoGPS(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogadoApontamento), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }
    //VERIFICA ApontamentoResponse
    public boolean verificaPrefUserLogadoApontamento(Context context){
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArq_userLogadoApontamento), Context.MODE_PRIVATE);
            String status = mPrefs.getString(context.getString(R.string.prefStatus_userLogadoApontamento), "");
            if(status.equals("true")){
                return true;
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }

    //**************************************  CONFIGURAÇÃO DO SERVIÇO - OPÇÕES TEMPO DE APONTAMENTO

    // DEFINE Configuração do Serviço - Tempo em que o usuário irá informar o seu apontamento (Alldays, Allweek, Allweekend, 15days ou Allmonth)
    public void definePrefConfServico(Context context,int idServico) {
        long INTERVAL = 0L;
        String DS_INTERVAL = "";
        try{
            //http://convertlive.com/pt/u/converter/dias/em/milissegundos#1
            switch (idServico) {
                case 1 :  DS_INTERVAL = "Todo dia";
                    INTERVAL = 86400000;
                    break;
                case 2:  DS_INTERVAL = "A cada 5 dias";
                    INTERVAL = 432000000;
                    break;
                case 3:  DS_INTERVAL = "A cada 10 dias";
                    INTERVAL = 864000000;
                    break;
                case 4:  DS_INTERVAL = "A cada 15 dias";
                    INTERVAL = 1296000000;
                    break;
                case 5:  DS_INTERVAL = "A cada 20 dias";
                    INTERVAL = 1728000000;
                    break;
            }

            long dataLimit = System.currentTimeMillis() + INTERVAL;
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArqValueConfServ), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putInt(context.getString(R.string.idConfServico), idServico);
            editor.putLong(context.getString(R.string.dsConfServicoDefault), dataLimit);

            Date x = new Date(dataLimit);
            String dsValue = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(x);

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }

    // Recuoera qual a opção de configuração selecionada pelo usuário na tela de configurações de apontamento (Alldays, Allweek, Allweekend, 15days ou Allmonth)
    public JSONObject recuperaPrefConfServico(Context context){
        JSONObject result = new JSONObject();
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArqValueConfServ), Context.MODE_PRIVATE);
            if(mPrefs.getInt(context.getString(R.string.idConfServico), 0)>0){
                result.put(context.getString(R.string.idConfServico), mPrefs.getInt(context.getString(R.string.idConfServico), 0));
                result.put(context.getString(R.string.dsConfServicoDefault), mPrefs.getLong(context.getString(R.string.dsConfServicoDefault), 0));
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }

    public void limpaPrefConfServ(Context context) {
        try{
            SharedPreferences mPrefs = context.getSharedPreferences(context.getString(R.string.prefArqValueConfServ), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.clear();

            editor.commit();

        }catch (Exception e) {
            e.getMessage().toString();
        }
    }
    //**************************************

    public static void cleanAllPreferences(Context  context){
        ActivityUtil activityUtil;
        try{
            activityUtil = new ActivityUtil();

            activityUtil.limpaPrefUserLogado(context);
            activityUtil.limpaPrefUserLogadoApontamentoGPS(context);
            activityUtil.limpaPrefFormLogin(context);
            activityUtil.limpaPrefConfServ(context);
            //activityUtil.limpaPrefFlagInfoMedidas(context);
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public void determineScreenSize(Context context) {
        if((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            Toast.makeText(context, "Large screen",Toast.LENGTH_LONG).show();
        }else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            Toast.makeText(context, "Normal sized screen" , Toast.LENGTH_LONG).show();
        }else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            Toast.makeText(context, "Small sized screen" , Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Screen size is neither large, normal or small" , Toast.LENGTH_LONG).show();
        }
    }
    /*
        Calcula o Índice de massa corporal (IMC)
     */
    public double  getIMC(double altura, double peso){
        double  immc = 0;
        try{
            immc = peso / (altura * altura);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return immc;
    }

    public String getDsStatuImc(Context context, double imc){
        Resources res = context.getResources();
        String result = "não encontrado...";
        int imcx = ((int) imc);
        try{
            if(imc >= 40){
                result = res.getString(R.string.text_result) + String.valueOf(((int) imc)) + res.getString(R.string.text_result_morbido);
            }else
            if(imc >= 35){
                result = res.getString(R.string.text_result)  + String.valueOf(((int) imc)) + res.getString(R.string.text_result_severo);
            }
            else
            if(imc >= 30){
                result = res.getString(R.string.text_result)  + String.valueOf(((int) imc)) + res.getString(R.string.text_result_obesidade);
            }
            else
            if(imc >= 25){
                result = res.getString(R.string.text_result)  + String.valueOf(((int) imc)) + res.getString(R.string.text_result_acima);
            }
            else
            if(imc >= 18.5){
                result = res.getString(R.string.text_result)  + String.valueOf(((int) imc)) + res.getString(R.string.text_result_normal);
            }
            else
            if(imc >= 17){
                result = res.getString(R.string.text_result)  + String.valueOf(((int) imc)) + res.getString(R.string.text_result_abaixo);
            }
            else
            if(imc < 17 ){
                result = res.getString(R.string.text_result)  + String.valueOf(((int) imc)) + res.getString(R.string.text_result_abaixo_normal);
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return result;
    }
}