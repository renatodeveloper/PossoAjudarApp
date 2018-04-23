package com.possoajudar.app.application.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.possoajudar.app.R;
import com.possoajudar.app.application.ui.activities.MainActivity;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

/**
 * Created by renato on 09/02/2018.
 */

public class ServicoApontamento extends Service {
    /*
        final long INTERVAL = 10 * 60 * 1000; // 10 minutos
        final long INTERVAL = 1440 * 60 * 1000; // 1 dia

        1 segundo = 1000
        1 minuto  = 60000   :  final long INTERVAL = 1 * 60 * 1000; // 1 minutos
        1 hora    = 3,6e+6
        1 dia     = 8,64e+7
        1 semana  = 6,048e+8
        1 mês     = 2,628e+9
        1 ano     = 3,154e+10
     */
    public static Context context;
    int idConfServico;
    long dsConfServicoDefault;
    long INTERVAL = 1 * 60 * 1000; // INTERVALO NO QUAL O SERVICE VERIFICA O TEMPO OCIOSO
    private Timer timer = new Timer();
    static Message message;
    static Bundle bundlee;
    public static final String SERVICE_NAME = "SERVICO_APONTAMENTO";
    ActivityUtil activityUtil;


    public Timer getTimer() {
        return timer;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.scheduleAtFixedRate(timerTaskGen, 0, INTERVAL);
        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timerTaskGen.cancel();
            timer.cancel();
        }
    }

    public void stopService(){
        stopSelf();
        if (timer != null) {
            timerTaskGen.cancel();
            timer.cancel();
        }
    }

    private TimerTask timerTaskGen = new TimerTask() {
        @Override
        public void run() {
            try {
                context = getApplicationContext();
                message = new Message();
                bundlee = new Bundle();
                message.what = 0;
                if(checkTempoOcioso()){
                    message.what = 10;
                    bundlee.putString("key_gen", "_NOK");
                    message.setData(bundlee);
                    handlerS.sendMessage(message);
                }else{
                    bundlee.putString("key_gen", "_OK");
                }
                message.setData(bundlee);
                handlerS.sendMessage(message);
            } catch (Exception e) {
                stopService(new Intent(getBaseContext(), ServicoApontamento.class));

                e.getMessage().toString();
            }
        }
        Handler handlerS = new Handler(){
            public void handleMessage(Message msg) {
                Bundle bundle;
                if (msg.what == 10) {
                    bundle = msg.getData();
                    if(bundle.getString("key_gen").equals("_NOK")){
                        //activityUtil.limpaPrefConfServ(context);//02
                        stopSelf();
                        try{
                            activityUtil.definePrefFlagInfoMedidas(context);
                            Intent i = new Intent(context, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }catch(Exception e){
                            e.getMessage().toString();
                        }
                    }
                }else{
                    bundle = msg.getData();
                    if(bundle.getString("key_gen").equals("_OK")){
                        System.out.println("Sessão liberada...");
                    }
                }
            }
        };
    };

    /**
     * Recupera preferences e verifica o tempo ocioso
     */
    public boolean checkTempoOcioso(){
        context = getApplicationContext();
        try{
            activityUtil = new ActivityUtil();
            JSONObject object  = activityUtil.recuperaPrefConfServico(context);
            if (object != null && object.length()>0){
                if(object.getInt(context.getString(R.string.idConfServico))>0){
                    idConfServico = object.getInt(context.getString(R.string.idConfServico));
                    dsConfServicoDefault = object.getLong (context.getString(R.string.dsConfServicoDefault));
                    if(dsConfServicoDefault <= System.currentTimeMillis()){
                        return true;
                    }
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }
        return false;
    }
}