package com.possoajudar.app.application.service.cadastro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.dao.DaoModelService;
import com.possoajudar.app.application.ui.activities.MainActivity;
import com.possoajudar.app.domain.dao.UsuarioDao;
import com.possoajudar.app.domain.model.ServiceResponse;
import com.possoajudar.app.domain.model.Usuario;
import com.possoajudar.app.domain.model.UsuarioList;
import com.possoajudar.app.infrastructure.backend.ApiClient;
import com.possoajudar.app.infrastructure.backend.ApiInterface;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Renato on 27/09/2017.
 */

public class CadUserService {
    Context context;
    private UsuarioDao usuarioDao;
    private ILoginView loginView;
    private ICadUserView cadUserView;

    public CadUserService(Context context, ILoginView loginView){
        this.context = context;
        this.loginView = loginView;
    }

    public CadUserService(Context context, ICadUserView cadUserView){
        this.context = context;
        this.cadUserView = cadUserView;
    }

    public boolean registerNewUser(JSONObject jsonValue) {
        try {
            usuarioDao = new UsuarioDao(this.context);
            if(! usuarioDao.check(jsonValue)){
                //String s = (this.cadUserView.getByteArrayPhoto() != null) ? this.cadUserView.getCadUserEmail()+ "-" + System.currentTimeMillis() : "nonephoto";
                ContentValues values = new ContentValues();
                values.put(context.getString(R.string.dsNomeTblUser), jsonValue.getString(context.getString(R.string.dsNomeTblUser)));
                values.put(context.getString(R.string.dsLoginTblUser), jsonValue.getString(context.getString(R.string.dsLoginTblUser)));
                values.put(context.getString(R.string.dsSenhaTblUser), jsonValue.getString(context.getString(R.string.dsSenhaTblUser)));
                values.put(context.getString(R.string.idTblServico), jsonValue.getString(context.getString(R.string.idTblServico)));
                values.put(context.getString(R.string.idTblRedeSocial), jsonValue.getString(context.getString(R.string.idTblRedeSocial)));
                values.put(context.getString(R.string.namePhoto), (this.cadUserView.getByteArrayPhoto() != null) ? this.cadUserView.getCadUserEmail()+ "-" + System.currentTimeMillis() : "nonephoto");
                values.put(context.getString(R.string.bytePhoto), this.cadUserView.getByteArrayPhoto());

                return usuarioDao.save(values);
            }

            /*
            Teste de consumo de endPoint webserver  01 ao o7 - crud

            Usuario userAdd = new Usuario();
            userAdd.dsNome = "Renato Rodrigues";
            userAdd.dsLogin = "developer.renato";
            userAdd.dsSenha = "developer";

            ApiInterface  apiInterface = ApiClient.getClientWS_RESTful().create(ApiInterface.class);
             */

            /* 01
            Call<ServiceResponse> call = apiInterface.getStatusConection();
           call.enqueue(new Callback<ServiceResponse>() {
               @Override
               public void onResponse(Response<ServiceResponse> response, Retrofit retrofit) {
                   if(response != null){

                   }
               }

               @Override
               public void onFailure(Throwable t) {
                   if(t != null){

                   }
               }
           });
            */

            /* 02
            Call<UsuarioList> call = apiInterface.getAllUsers();
            call.enqueue(new Callback<UsuarioList>() {
                @Override
                public void onResponse(Response<UsuarioList> response, Retrofit retrofit) {
                    if(response != null){

                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if(t != null){

                    }
                }
            });
             */

            /* 03
            Call<Usuario> call = apiInterface.getUser(1);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                    if(response != null){

                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if(t != null){

                    }
                }
            });
             */

            /* 04
            Call<List<Usuario>>  call = apiInterface.getUserName("Renato");
            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Response<List<Usuario>> response, Retrofit retrofit) {
                    if(response != null){

                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if(t != null){

                    }
                }
            });

             */

            /* 05
            Call<Usuario> call = apiInterface.add(userAdd);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                        if(response != null){

                        }
                }

                @Override
                public void onFailure(Throwable t) {
                    if(t != null){

                    }
                }
            });
             */

            /* 06
            Call<Usuario> call = apiInterface.update(1, userAdd);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                    if(response != null){

                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if(t != null){

                    }
                }
            });

             */

            /* 07
            Call<Usuario> call = apiInterface.delete(1);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                    if(response != null){

                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if(t != null){

                    }
                }
            });


             */


        } catch (Exception e) {
            e.getMessage().toString();
        }
        return false;
    }

    public  byte[] getBytePhoto(){
        byte[] bytePhoto = null;
        ActivityUtil util;
        try{
            util = new ActivityUtil();
            JSONObject objectU = util.recuperaPrefUserLogado(this.context);

            usuarioDao = new UsuarioDao(this.context);
            return usuarioDao.getBytePhoto(objectU);
        }catch (Exception e){
            e.getMessage().toString();
        }
        return bytePhoto;
    }
}