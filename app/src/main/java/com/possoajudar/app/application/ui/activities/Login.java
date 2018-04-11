package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.possoajudar.app.BuildConfig;
import com.possoajudar.app.R;
//import com.possoajudar.app.application.module.app.LoginApplication;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.gitHub.GitHubClient;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.application.service.login.LoginPresenter;
import com.possoajudar.app.application.service.login.LoginService;
import com.possoajudar.app.domain.model.AccessToken;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.domain.model.GitHubRepo;
import com.possoajudar.app.infrastructure.Constants;
import com.possoajudar.app.infrastructure.backend.ApiClient;
import com.possoajudar.app.infrastructure.backend.ApiInterface;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;



import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Renato on 02/07/2017.
 */


public class Login extends Activity implements ILoginView {
    /* Butter Knife
               @BindView(R.id.input_email) EditText usernameView;
               @BindView(R.id.input_password) EditText passwordView;
               @BindView(R.id.sucessologin) TextView sucessologin;
        */

    /* RoboGuice
    public class Login extends RoboActivity implements ILoginView {...
                @InjectView(R.id.input_email)    EditText    usernameView;
                @InjectView(R.id.input_password) EditText    passwordView;
                @InjectView(R.id.sucessologin)   TextView    sucessologin;
                @InjectView(R.id.btn_login)      AppCompatButton      onLoginClick;
     */

    private EditText usernameView;
    private EditText passwordView;
    TextView sucessologin;
    TextView newcountView;

    private LoginPresenter loginPresenter;
    public ActivityUtil activityUtil;
    GpsService gps;

    private AdView mAdView;


    private GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;

    public static boolean flagGitHub = true;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Inject
    Apontamento apontamento;

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }



    /*
            TESTE LOGIN GitHub
    @Override
    protected void onResume() {
        super.onResume();
        //GitHub Retrofit OAuth   2: RECEBE CODE
        String redirectUri = getApplication().getResources().getString(R.string.redirectUri);

        Uri uri = getIntent().getData();
        if(uri != null && uri.toString().startsWith(redirectUri)) {
            String code = uri.getQueryParameter("code");
            if(code != null) {

                String clientId = getApplication().getResources().getString(R.string.clienteId);
                String clientSecret = getApplication().getResources().getString(R.string.clienteSecret);

                final SharedPreferences prefs = this.getSharedPreferences(
                        BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);


                // 3: SOLCITA TOKEN com o code recebido
                GitHubClient gitHubClient = ApiClient.getClientGitHub().create(GitHubClient.class);
                Call<AccessToken> callToken = gitHubClient.getAccessToken(clientId, clientSecret,code);
                callToken.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Response<AccessToken> response, Retrofit retrofit) {
                        int statusCode = response.code();
                        if(statusCode == 200) {
                            AccessToken token = response.body();
                            prefs.edit().putBoolean("oauth.loggedin", true).apply();
                            prefs.edit().putString("oauth.accesstoken", token.getAccessToken()).apply();
                            prefs.edit().putString("oauth.refreshtoken", token.getRefreshToken()).apply();
                            prefs.edit().putString("oauth.tokentype", token.getTokenType()).apply();

                            // TODO Show the user they are logged in
                        } else {
                            // TODO Handle errors on a failed response
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });

                SharedPreferences mPrefs = this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
                String oauthAccesstoken = mPrefs.getString("oauth.accesstoken", "");


               // Retrofit.Builder builder = new Retrofit.Builder()
                        //.baseUrl("https://api.github.com/")
                       // .addConverterFactory(GsonConverterFactory.create());
               // Retrofit retrofit = builder.build();
               // GitHubClient clientT = retrofit.create(GitHubClient.class);
              //  Call<List<GitHubRepo>> callL = clientT.reposForUser("renatodeveloper");



                GitHubClient client  = ApiClient.getClient().create(GitHubClient.class);
                Call<List<GitHubRepo>> call = client.reposForUser("renatodeveloper");
                call.enqueue(new Callback<List<GitHubRepo>>() {
                    @Override
                    public void onResponse(Response<List<GitHubRepo>> response, Retrofit retrofit) {
                        if(response != null){
                            List<GitHubRepo> repos = response.body();
                            if(repos!= null){

                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
            }
        }
    }
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_login);
        //ButterKnife.bind(this);
       //((LoginApplication) getApplication()).getAppComponent().inject(this);

        //final String URL = BuildConfig.API_URL;
        try{
            activityUtil = new ActivityUtil();
        }catch (Exception e){
            e.getMessage().toString();
        }

        usernameView = (EditText) findViewById(R.id.input_email);
        passwordView = (EditText) findViewById(R.id.input_password);
        sucessologin = (TextView) findViewById(R.id.sucessologin);
        newcountView = (TextView) findViewById(R.id.newcount);

        loginPresenter = new LoginPresenter(this, this);

        newcountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    gps = new GpsService(getApplicationContext());
                    if(gps.canGetLocation()){
                        if(usernameView.getText().toString().length()>0 || passwordView.getText().toString().length()>0){
                            JSONObject userJson = new JSONObject();
                            userJson.put(getString(R.string.dsLoginTblUser), usernameView.getText().toString());
                            userJson.put(getString(R.string.dsSenhaTblUser), passwordView.getText().toString());
                            activityUtil.definePrefFormLogin(getApplicationContext(), gps, userJson);
                            startActivity(new Intent(Login.this, CadUsuario.class));
                        }else{
                            startActivity(new Intent(Login.this, CadUsuario.class));
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Habilite o GPS", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.getMessage().toString();
                }
            }
        });

        /* Test: RoboGuice
           onLoginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "RoboGuice", Toast.LENGTH_LONG).show();
            }
        });
         */


        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //Toast.makeText(getApplicationContext(), "onAdLoaded...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                //Toast.makeText(getApplicationContext(), "onAdFailedToLoad...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                //Toast.makeText(getApplicationContext(), "onAdOpened...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                //Toast.makeText(getApplicationContext(), "onAdLeftApplication...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                //Toast.makeText(getApplicationContext(), "onAdClosed...", Toast.LENGTH_LONG).show();
            }
        });


          /*GitHub Retrofit OAuth   1: SOLICITA CODE
        if(flagGitHub){
            String client_id = getApplication().getResources().getString(R.string.clienteId);
            String redirect_uri = getApplication().getResources().getString(R.string.redirectUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize" + "?client_id=" + client_id + "&scope=repo&redirect_uri=" + redirect_uri));
            startActivity(intent);

            flagGitHub = false;
        }
        */
    }

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

     */
    /*




    /* butterknife.OnClick
         @OnClick(R.id.btn_login)
        public void submit() {
            // TODO submit data to server...
            Toast.makeText(getApplicationContext(), "Butter Knife", Toast.LENGTH_LONG).show();
        }
     */


    public void onLoginClick(View view) {
        //Toast.makeText(getApplicationContext(), "Dagger 2: " + apontamento.toString() , Toast.LENGTH_LONG).show();
        loginPresenter.onLoginClicked();
        //throw new RuntimeException("This is a crash");
    }

    @Override
    public String getUsername() {return usernameView.getText().toString();}

    @Override
    public String getPassword() {
        return passwordView.getText().toString();
    }

    @Override
    public void showUsernameError(int resId) {
        usernameView.setError(getString(resId));
    }

    @Override
    public void showPasswordError(int resId) {
        passwordView.setError(getString(resId));
    }

    @Override
    public void showLoginError(int resId) {
        sucessologin.setText("ERRO LOGIN");
        Toast.makeText(this, getString(resId), LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        gps = new GpsService(getApplicationContext());
        if(gps.canGetLocation()){
            activityUtil.definePrefUserLogado(getApplicationContext(), gps, activityUtil.getValeuJson(this, usernameView.getText().toString(), passwordView.getText().toString()));
            sucessologin.setText("SUCESSO");
            startActivity(new Intent(this, MainActivity.class));
            //new ActivityUtil(this).startMainActivity();
        }else{
            gps.showSettingsAlert(this);
        }
    }

    //****************************************************** onKeyDown
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}