package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.possoajudar.app.R;
//import com.possoajudar.app.application.module.app.LoginApplication;
import com.possoajudar.app.application.service.ILoginView;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.application.service.login.LoginPresenter;
import com.possoajudar.app.domain.model.AccessToken;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.domain.model.GDriveFiles;
import com.possoajudar.app.domain.model.OAuthToken;
import com.possoajudar.app.infrastructure.Constants;
import com.possoajudar.app.infrastructure.backend.ApiClient;
import com.possoajudar.app.infrastructure.backend.Google.ApiGoogleInterface;
import com.possoajudar.app.infrastructure.backend.Google.GoogleClient;
import com.possoajudar.app.infrastructure.backend.gitHub.GitHubClient;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONObject;

import javax.inject.Inject;


import okhttp3.HttpUrl;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Renato on 02/07/2017.
 */


public class Login extends Activity implements ILoginView {
    /*
    //Google API - INICIO
    private static final String TAG = "Login";
    //You client id, you have it from the google console when you register your project
    //     * https://console.developers.google.com/a
    private static final String CLIENT_ID = "560823489735-msn3ac6tpo6b2d5c943k0mtbnjc0iaa4.apps.googleusercontent.com";
    //The redirect uri you have define in your google console for your project
    private static final String REDIRECT_URI = "com.possoajudar.app:/urn:ietf:wg:oauth:2.0:oob";
    //The redirect root uri you have define in your google console for your project
    //     * It is also the scheme your Main Activity will react
    private static final String REDIRECT_URI_ROOT = "com.possoajudar.app";
    //You are asking to use a code when autorizing
    private static final String CODE = "code";
    //You are receiving an error when autorizing, it's embedded in this field
    private static final String ERROR_CODE = "error";
    //GrantType:You are using a code when retrieveing the token
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    //GrantType:You are using a refresh_token when retrieveing the token
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
   //The scope: what do we want to use
   //     * Here we want to be able to do anything on the user's GDrive
    public static final String API_SCOPE = "https://www.googleapis.com/auth/drive";
    //The code returned by the server at the authorization's first step
    private String code;
    //The error returned by the server at the authorization's first step
    private String error;
  */
//Google API - FIM

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

          /*
          Consumindo a API do Google A

        //Manage the callback case:
        Uri data = getIntent().getData();
        if (data != null && !TextUtils.isEmpty(data.getScheme())) {
            if (REDIRECT_URI_ROOT.equals(data.getScheme())) {
                code = data.getQueryParameter(CODE);
                error=data.getQueryParameter(ERROR_CODE);
                Log.e(TAG, "onCreate: handle result of authorization with code :" + code);
                if (!TextUtils.isEmpty(code)) {
                    getTokenFormUrl();//2 RECEBEDO CODE
                }
                if(!TextUtils.isEmpty(error)) {
                    //a problem occurs, the user reject our granting request or something like that
                    Toast.makeText(this, R.string.loginactivity_grantsfails_quit,Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onCreate: handle result of authorization with error :" + error);
                    //then die
                    finish();
                }
            }
        } else {
            //Manage the start application case:
            //If you don't have a token yet or if your token has expired , ask for it
            OAuthToken oauthToken=OAuthToken.Factory.create();
            if (oauthToken==null
                    ||oauthToken.getAccessToken()==null) {
                //first case==first token request
                if(oauthToken==null||oauthToken.getRefreshToken()==null){
                    Log.e(TAG, "onCreate: Launching authorization (first step)");
                    //first step of OAUth: the authorization step
                    makeAuthorizationRequest();//1 SOLICITANDO CODE
                }else{
                    Log.e(TAG, "onCreate: refreshing the token :" + oauthToken);
                    //refresh token case
                    refreshTokenFormUrl(oauthToken);
                }
            }
            //else just launch your MainActivity
            else {
                Log.e(TAG, "onCreate: Token available, just launch MainActivity");
                startMainActivity(false);
            }
        }
 */

    }

    /*
        Google API - INICIO
     */

    /***********************************************************
     *  Managing Authotization and Token process
     **********************************************************/

    /**
     * Make the Authorization request
     */
    /*


    private void makeAuthorizationRequest() {
        HttpUrl authorizeUrl = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth") //
                .newBuilder() //
                .addQueryParameter("client_id", CLIENT_ID)
                .addQueryParameter("scope", API_SCOPE)
                .addQueryParameter("redirect_uri", REDIRECT_URI)
                .addQueryParameter("response_type", CODE)
                .build();
        Intent i = new Intent(Intent.ACTION_VIEW);
        Log.e(TAG, "the url is : " + String.valueOf(authorizeUrl.url()));
        i.setData(Uri.parse(String.valueOf(authorizeUrl.url())));
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
  */

    /**
     * Refresh the OAuth token
     */
    /*


    private void refreshTokenFormUrl(OAuthToken oauthToken) {

        ApiGoogleInterface  oAuthServer = GoogleClient.getClient().create(ApiGoogleInterface.class);
        Call<OAuthToken> refreshTokenFormCall = oAuthServer.refreshTokenForm(
                oauthToken.getRefreshToken(),
                CLIENT_ID,
                GRANT_TYPE_REFRESH_TOKEN
        );

        refreshTokenFormCall.enqueue(new Callback<OAuthToken>() {
            @Override
            public void onResponse(Response<OAuthToken> response, Retrofit retrofit) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call refreshTokenFormUrl succeed with code=" + response.code() + " and has body = " + response.body());
                //ok we have the token
                response.body().save();
                startMainActivity(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call refreshTokenFormCall failed", throwable);
            }
        });
    }
     */

    /**
     * Retrieve the OAuth token
     */
    /*


    private void getTokenFormUrl() {//3 SOLICITANDO TOKEN COM O CODE RECEBIDO
        ApiGoogleInterface  oAuthServer = GoogleClient.getClient().create(ApiGoogleInterface.class);
        Call<OAuthToken> getRequestTokenFormCall = oAuthServer.requestTokenForm(
                code,
                CLIENT_ID,
                REDIRECT_URI,
                GRANT_TYPE_AUTHORIZATION_CODE
        );

        getRequestTokenFormCall.enqueue(new Callback<OAuthToken>() {
            @Override
            public void onResponse(Response<OAuthToken> response, Retrofit retrofit) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call getRequestTokenFormCall succeed with code=" + response.code() + " and has body = " + response.body());
                //ok we have the token
                response.body().save();
                startMainActivity(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call getRequestTokenFormCall failed", throwable);
            }
        });

    }
 */

    /***********************************************************
     *  Business Methods
     **********************************************************/
    /**
     * Make an Https call using the Authentication token
     * Here we list the files on GDrive
     */
    /*


    private void startMainActivity(boolean newtask) {//4 UTILIZANDO TOKEN E TENDO ACESSO ATRAVES DO MESMO
        ApiGoogleInterface  oAuthServer = GoogleClient.getClient().create(ApiGoogleInterface.class);
        Call<GDriveFiles> listFilesCall = oAuthServer.listFiles();
        listFilesCall.enqueue(new Callback<GDriveFiles>() {
            @Override
            public void onResponse(Response<GDriveFiles> response, Retrofit retrofit) {
                Log.e(TAG,"The call listFilesCall succeed with [code="+response.code()+" and has body = "+response.body()+" and message = "+response.message()+" ]");
                //ok we have the list of files on GDrive
                String result= "";
                if(response.code()==200&&response.body()!=null){
                    result  = response.body().toString();
                    if(result!=null){

                    }
                }else if(response.code()==400){
                    result = response.message()+"\r\n"+getString(R.string.http_code_400);
                }else if(response.code()==401){
                    result = response.message()+"\r\n"+getString(R.string.http_code_401);
                }else if(response.code()==403){
                    result = response.message()+"\r\n"+getString(R.string.http_code_403);
                }else if(response.code()==404){
                    result = response.message()+"\r\n"+getString(R.string.http_code_404);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG,"The call listFilesCall failed", throwable);
            }
        });

    }
        */
    //Google API - FIM


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
            JSONObject object = new JSONObject();
            try{
                JSONObject resultLogin = activityUtil.recuperaPrefUserLogado(Login.this);
                String dsNome = resultLogin.getString(getApplicationContext().getResources().getString(R.string.dsNomeTblUser));
                String dsLogin = resultLogin.getString(getApplicationContext().getResources().getString(R.string.dsLoginTblUser));

                object.put(getApplicationContext().getResources().getString(R.string.dsNomeTblUser),dsNome);
                object.put(getApplicationContext().getResources().getString(R.string.dsLoginTblUser),dsLogin);
                activityUtil.definePrefUserLogado(getApplicationContext(), gps, object);
                sucessologin.setText("SUCESSO");
                startActivity(new Intent(this, MainActivity.class));
            }catch (Exception e){
                e.getMessage().toString();
            };
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