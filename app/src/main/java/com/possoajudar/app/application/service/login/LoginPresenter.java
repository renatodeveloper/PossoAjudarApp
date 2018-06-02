package com.possoajudar.app.application.service.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.ui.activities.Login;
import com.possoajudar.app.domain.dao.UsuarioDao;
import com.possoajudar.app.domain.model.Transacao;
import com.possoajudar.app.domain.model.Usuario;
import com.possoajudar.app.infrastructure.backend.ApiClient;
import com.possoajudar.app.infrastructure.backend.ApiInterface;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by renato on 06/07/2017.
 */

public class LoginPresenter {
    Context context;
    ActivityUtil util;

    private ILoginView view;
    private LoginService service;

    ApiInterface apiService;

    protected static final  int TIMER_RUNTIME = 10000;
    public boolean mbActive;


    public LoginPresenter(Context context, ILoginView view){
        this.context = context;
        this.service = new LoginService(context, view);
        this.view = view;

        util = new ActivityUtil();
    }


    public void onLoginClicked() {
       final Usuario usuario;
        try {

            String username = view.getUsername();
            if (username.isEmpty()) {
                view.showUsernameError(R.string.strLyLoginUsername_error);
                return;
            }
            final String password = view.getPassword();
            if (password.isEmpty()) {
                view.showPasswordError(R.string.strLyLoginPassword_error);
                return;
            }


            Login.usernameView.setEnabled(false);
            Login.passwordView.setEnabled(false);
            Login.sucessologin.setEnabled(false);
            Login.onLoginClick.setEnabled(false);
            Login.newcountView.setEnabled(false);
            Login.myProgressBar.setVisibility(View.VISIBLE);

            final Thread timerThread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    mbActive = true;
                    try{
                        final Usuario usuario = new Usuario();
                        usuario.dsLogin = view.getUsername();
                        usuario.dsSenha = view.getPassword();

                        apiService = ApiClient.getClientWS_RESTful().create(ApiInterface.class);
                        Call<Usuario> call = apiService.verificar(usuario);
                        call.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                                if(response != null){
                                    view.startMainActivity();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                if(t != null){
                                    resultFail(t);
                                }
                            }
                        });

                        int waited = 0;
                        while (mbActive && (waited < TIMER_RUNTIME)){
                            sleep(200);
                            if(mbActive){
                                waited += 200;
                                updateProgress(waited);
                            }
                        }
                    }catch (InterruptedException e){
                        e.getMessage().toString();
                    }finally {
                        onUploaded();//carregado...
                    }
                }
            };
            timerThread.start();



            /*
            boolean loginSucceeded = service.login(null);
            if(loginSucceeded){
                view.startMainActivity();
            }else{
                view.showLoginError(R.string.strLyLoginloginfailed);
            }
             */
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    public void updateProgress(final int timePassed){
        if(null != Login.myProgressBar){
            final int progress = Login.myProgressBar.getMax() * timePassed / TIMER_RUNTIME;
            Login.myProgressBar.setProgress(progress);
        }
    }


    public void onUploaded(){
        Log.d("mensagemFinal", "Sua barra de login acabou de carregar!!!");
    }

    public void resultFail(Object o){
        try{
            if(null != o){
                Log.i("mensagemResult",  o.toString());
            }

            boolean loginSucceeded = service.login(null);
            if(loginSucceeded){
                view.startMainActivity();
            }else{
                Login.usernameView.setEnabled(true);
                Login.passwordView.setEnabled(true);
                Login.sucessologin.setEnabled(true);
                Login.onLoginClick.setEnabled(true);
                Login.newcountView.setEnabled(true);
                Login.myProgressBar.setVisibility(View.INVISIBLE);
                view.showLoginError(R.string.strLyLoginloginfailed);
            }


        }catch (Exception e){
            e.getMessage().toString();
        }
    }
}
