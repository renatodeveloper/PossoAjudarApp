package com.possoajudar.app.application.ui.activities;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ApplicationServiceError;
import com.possoajudar.app.application.service.IDaoModel;
import com.possoajudar.app.application.service.dao.DaoModelPresenter;
import com.possoajudar.app.domain.model.Cep;
import com.possoajudar.app.domain.model.Movie;
import com.possoajudar.app.domain.model.MoviesResponse;
import com.possoajudar.app.domain.model.Post;
import com.possoajudar.app.domain.model.Usuario;
import com.possoajudar.app.infrastructure.Constants;
import com.possoajudar.app.infrastructure.backend.ApiClient;
import com.possoajudar.app.infrastructure.backend.ApiInterface;
import com.possoajudar.app.infrastructure.backend.okhttp.ApiClientOkHttp;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import io.reactivex.disposables.Disposable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;


/**
 * Created by renato on 11/07/2017.
 */

public class ViewSplash extends Activity implements IDaoModel {

    private static final String TAG = "ViewSplash";
    public ActivityUtil activityUtil;
    private DaoModelPresenter daoModelPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        try {
            // INCIO: Testando programação reativa com RxJava  e RxAndroid
            /*


            Usuario usuario = new Usuario();
            usuario.dsLogin = "Developer";
            usuario.dsSenha = "RxJavaAndroid";

            Observable<Usuario> observable = Observable.just(usuario);

            Observer<Usuario> observer = new Observer<Usuario>() {
                @Override
                public void onSubscribe(Disposable d) {
                }
                @Override
                public void onNext(Usuario usuario) {
                    Log.i(TAG, "onNext A: " + usuario.dsLogin);
                }
                @Override
                public void onError(Throwable e) {
                }
                @Override
                public void onComplete() {
                    Log.i(TAG, "onComplete A");
                }
            };

            observable.subscribe(observer);

            //Emitindo varios sinais
            Usuario[] usuarios = new Usuario[3];
            for(int i=0; i<3; i++){
                usuarios[i] = new Usuario();
                usuarios[i].dsLogin = "Usuário: " + i;
            }

            Observable<Usuario> observableX = Observable.fromArray(usuarios);//emiti vários usuários
            Observer<Usuario> observerX = new Observer<Usuario>() {
                @Override
                public void onSubscribe(Disposable d) {
                }
                @Override
                public void onNext(Usuario usuario) {//Callback quando esse observador receber um usuário
                    Log.i(TAG, "onNext B" + usuario.dsLogin);
                }
                @Override
                public void onError(Throwable e) {
                }
                @Override
                public void onComplete() {
                    Log.i(TAG, "onComplete B");
                }
            };
            //Canal de assinatura faz a inscrição
            observableX.subscribe(observerX);
            */

        // FIM: Testando programação reativa com RxJava  e RxAndroid


            //*********************************************************************** INICIO TESTE VOLLEY

            /*
            Getting Started with Android Volley
             */
            //RequestQueue queue = Volley.newRequestQueue(this);

            //String URL = Constants.Headers.URL_CORREIO_CEP + getApplicationContext().getResources().getString(R.string.dsCep);

            /*
            Making GET Requests
             */
            /*
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response != null) {
                                try{
                                    String cidade = response.getString("cidade");
                                    String bairro = response.getString("bairro");
                                }catch (Exception e){
                                    e.getMessage().toString();
                                }
                            }
                            Log.d("Response", response.toString());
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                        }
                    }
            );
            queue.add(getRequest);// add it to the RequestQueue
            */


            //*************************************Making GET Requests**************************************
            /*
            final String url = "http://httpbin.org/get?param1=hello";
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                        }
                    }
            );
            // add it to the RequestQueue
            queue.add(getRequest);
             */
            //*************************************Making POST Requests**************************************
            /*
            String URL_post = "http://httpbin.org/post";
            StringRequest postRequest = new StringRequest(Request.Method.POST, URL_post,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", "Alif");
                    params.put("domain", "http://itsalif.info");
                    return params;
                }
            };
            queue.add(postRequest);
            */

            //*************************************Making PUT Requests**************************************
            /*

           String URL_put = "http://httpbin.org/put";
            StringRequest putRequest = new StringRequest(Request.Method.PUT, URL_put,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String>  params = new HashMap<String, String> ();
                    params.put("name", "Posso Ajudar");
                    params.put("domain", "http://possoajudar.info");

                    return params;
                }

            };
            queue.add(putRequest);

             */
            //*************************************Making DELETE Requests**************************************

            /*
            String URL_del = "http://httpbin.org/delete";
            JSONObject params = new JSONObject();
            try {
                params.put("email", "bla@developer.com");
                params.put("senha", "suave");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest getRequest = new JsonObjectRequest
                    (Request.Method.DELETE, URL_del, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG + ": ", "delete onResponse : " + response.toString());
                            if (null != response.toString()){
                                }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (null != error.networkResponse) {
                                Log.d(TAG + ": ", "delete Error Response code: " + error.networkResponse.statusCode);
                            }
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "application/json");
                    params.put("Content-type", "application/json");
                    return params;
                }
            };
            ;
            queue.add(getRequest);// add it to the RequestQueue
            */
            //*********************************************************************** FIM TESTE VOLLEY

            //*********************************************************************** INICIO TESTE OkHttp

            /*
                    Inicio

            ApiClientOkHttp okHttp = new ApiClientOkHttp();
            String cep = getApplicationContext().getResources().getString(R.string.dsCep);
            okHttp.get(Constants.Headers.URL_CORREIO_CEP + cep, new okhttp3.Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            // Something went wrong
                        }
                        @Override
                        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String responseStr = response.body().string();
                                if(responseStr != null){
                                    try{
                                        JSONObject object = new JSONObject(responseStr);
                                        String cidade = object.getString("cidade");
                                        String bairro = object.getString("bairro");

                                    }catch (Exception e){
                                        e.getMessage().toString();
                                    }
                                }
                                // Do what you want to do with the response.
                            } else {
                                // Request not successful
                            }
                        }
                    });

                */
                    //fim OkHttp
            //*********************************************************************** INICIO TESTE Retrofit
            /*

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<Post>> call = apiService.getAllPosts();
            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Response<List<Post>> response, Retrofit retrofit) {
                    response.body().get(0);
                  displayPost(response.body().get(0));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "Error occured while fetching post.");
                }
            });


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Post> call = apiService.getPostById(1);
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Response<Post> response, Retrofit retrofit) {
                    response.body().toString();
                    displayPost(response.body());
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "Error occured while fetching post.");
                }
            });

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Cep> call = apiService.getAddress(getResources().getString(R.string.dsCep));
            call.enqueue(new Callback<Cep>() {
                @Override
                public void onResponse(Response<Cep> response, Retrofit retrofit) {
                    response.body().toString();

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "Error occured while fetching post.");
                }
            });
            */

                //fim retrofit


                    activityUtil = new ActivityUtil();

            //activityUtil.deleteDatabase(getApplicationContext());

            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                //System.exit(1);
                /*
                int L = Build.VERSION_CODES.LOLLIPOP; //21
                String ANDROID_OS = Build.VERSION.RELEASE; //5.1.1
                int SDK_INT = android.os.Build.VERSION.SDK_INT;  //22
                int L_M = Build.VERSION_CODES.LOLLIPOP_MR1; //22
                 */
                daoModelPresenter = new DaoModelPresenter(this, this);
                        /*
                Cria banco na memória interna e exporta esse mesmo banco para a memoria externa
             */
                daoModelPresenter.createdbInterno();
                //daoModelPresenter.createdbExterno();
                //activityUtil.deleteDatabase(getApplicationContext());

                checkUserLogado();

            }else{
                activityUtil.verifyStoragePermissionsAll(ViewSplash.this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ActivityUtil.REQUEST_CODE_PERMISSION: {
                boolean flagDenied = false;
                for(int x : grantResults){
                    if(x == -1){
                        flagDenied = true;
                        break;
                    }
                }
                if(flagDenied){
                    if(grantResults != null && grantResults.length>0){
                            for(int i=0; i< grantResults.length; i++){
                                if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                                    // user rejected the permission - PERMISSION_DENIED is '-1' | ACCEPT is 0
                                    boolean checkNaoPetube = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i].toString());//devolve false quando selecionado
                                    if(!checkNaoPetube){
                                        activityUtil.showDialogPermissionsSystem(ViewSplash.this);
                                    }else {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i].toString())) {
                                            activityUtil.showDialogPermissions(ViewSplash.this, permissions[i].toString());
                                        }
                                    }
                                }
                            }
                    }
                }else {

                    daoModelPresenter = new DaoModelPresenter(this, this);
                        /*
                Cria banco na memória interna e exporta esse mesmo banco para a memoria externa
             */
                    daoModelPresenter.createdbInterno();
                    //daoModelPresenter.createdbExterno();
                    //activityUtil.deleteDatabase(getApplicationContext());

                    checkUserLogado();
                }

            return;
        }
        }
    }


    public void checkUserLogado(){
        try{

            SharedPreferences facePref = getApplicationContext().getSharedPreferences(getApplicationContext().getString(R.string.prefArq_userLogado), Context.MODE_PRIVATE);
            if (facePref != null) {
                if (facePref.getString(getApplicationContext().getString(R.string.prefStatus_userLogado), null) != null) {
                    String sjonPerfil = (facePref.getString(getApplicationContext().getString(R.string.prefStatus_userLogado), null));
                    if (sjonPerfil.equals("true")) {
                        startActivity(new Intent(this, MainActivity.class));
                        //startActivity(new Intent(this, Dimensao.class));
                        return;

                    }
                }
            }
            startActivity(new Intent(this, Login.class));
            //startActivity(new Intent(this, Dimensao.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }


    @Override
    public void showErrorInternoDB(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorExternoDB(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sucess(String dsPackage) {
        Toast.makeText(this, dsPackage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSucessInternoDB() {
        try{
            //Toast.makeText(this, getString(R.string.sucessDbInterno), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login.class));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public void showSucessExternoDB() {
        //Toast.makeText(this, R.string.sucessDbExterno, Toast.LENGTH_SHORT).show();
    }

    /*TESTE RETROFIT
    private void displayPost(Post post) {
        String postId = post.getId().toString();
        String title = post.getTitle().toString();
        String body = post.getBody().toString();
    }
     */

}
