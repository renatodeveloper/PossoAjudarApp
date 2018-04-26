package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.possoajudar.app.R;
import com.possoajudar.app.application.module.app.GoogleAnalyticsApplication;
import com.possoajudar.app.application.module.retrofit.ApplicationModule;
import com.possoajudar.app.application.service.ApplicationService;
import com.possoajudar.app.application.service.ApplicationServiceCallback;
import com.possoajudar.app.application.service.ApplicationServiceError;
import com.possoajudar.app.application.service.ICadApontamentoView;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.application.service.ServicoApontamento;
import com.possoajudar.app.application.service.cadastro.CadApontamentoPresenter;
import com.possoajudar.app.application.service.cadastro.CadApontamentoService;
import com.possoajudar.app.application.service.cadastro.CadUserPresenter;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.application.ui.adapter.CustomAdapter;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.domain.model.Movie;
import com.possoajudar.app.domain.model.MoviesResponse;
import com.possoajudar.app.infrastructure.Constants;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;
import com.possoajudar.app.infrastructure.helper.BaseActivity;
import com.possoajudar.app.infrastructure.helper.DialogHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindString;
import retrofit.Response;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ICadUserView, ICadApontamentoView, ApplicationServiceCallback<MoviesResponse> {

    public ActivityUtil activityUtil;
    GpsService gps;

    //*** - Implementação RecyclerView
    //private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Apontamento> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    //*** - Implementação RecyclerView

    ArrayList<Apontamento> dataModels;
    ListView listView;

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
    }

    private static CustomAdapter adapter;

    ImageView imageViewNavPhoto;
    TextView textViewNavNome;
    TextView textViewNavEmail;

    String altura = "";
    String peso = "";
    String dataTime = "";

    ProgressDialog progressDialog;
    private CadApontamentoPresenter cadApontamentoPresenter;

    private CadUserPresenter cadUserPresenter;

    EditText editTextAltura = null;
    EditText editTextPeso = null;
    static final int REQUEST_SERVER = 1;


    //ciclo on
    @Inject
    @Named(ApplicationModule.GETFAPONTAMENTOS)
    ApplicationService<Movie, MoviesResponse> getApontamentos;
    ArrayList<String> urlPropaganda;

    @BindString(R.string.text_servico_code)
    String error;

    Thread thread = null;
    Handler hdl;
    String strErro = "";
    ProgressDialog progDialog;

    //propaganda on line
    static List<Movie> movies;
    ProgressDialog dialog;
    Handler handler;
    Message message;
    public boolean flagOn;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            stopService(new Intent(getApplicationContext(), ServicoApontamento.class));
            //limpa todas as preferences
            activityUtil.cleanAllPreferences(getApplicationContext());
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Main Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        /*
            Método que executa após o serviço ter seu tempo ocioso atingido -03
        */
        JSONObject object = activityUtil.recuperaPrefFlagInfoMedidas(getApplicationContext());//referente ao ServicoApontamento inicializado
        if(object != null && object.length()>0){
            try {
                if(object.getBoolean(getApplicationContext().getString(R.string.prefArqInfoMedidasVal))){
                    exibeDialogApontamento();
                    activityUtil.limpaPrefFlagInfoMedidas(getApplicationContext());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    private Tracker mTracker;
    Dialog customDialog = null;

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        //menu ***
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //menu ***

        //navigationView ***
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header= navigationView.getHeaderView(0);
        imageViewNavPhoto  = (ImageView) header.findViewById(R.id.imageViewNavPhoto);
        textViewNavNome  = (TextView)header.findViewById(R.id.textViewNavNome);
        textViewNavEmail = (TextView)header.findViewById(R.id.textViewNavEmail);


        /*
        navHeaderNome toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        */

        //*** - Implementação RecyclerView

        /**
         *
         myOnClickListener = new MyOnClickListener(this);
         recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
         recyclerView.setHasFixedSize(true);
         layoutManager = new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setItemAnimator(new DefaultItemAnimator());

         data = new ArrayList<ApontamentoResponse>();
         for (int i = 0; i < ApontamentoResponse.getArrayDs().length; i++) {
         data.add(new ApontamentoResponse(
         ApontamentoResponse.getArrayDs()[i],
         ApontamentoResponse.getArraySubDs()[i],
         ApontamentoResponse.getArrayId()[i],
         ApontamentoResponse.getArrayImgStatus()[i]
         ));
         }
         removedItems = new ArrayList<Integer>();

         adapter = new MyRecyclerViewAdapter(data);
         recyclerView.setAdapter(adapter);
         */


        cadApontamentoPresenter = new CadApontamentoPresenter(this, this);
        activityUtil = new ActivityUtil();
        cadUserPresenter = new CadUserPresenter(this, this);

        //**************** inicio thread
        dialog = new ProgressDialog(this);
        dialog.setTitle(this.getResources().getString(R.string.strMensagemProgress));
        dialog.setMessage(this.getResources().getString(R.string.strTitleProgress));
        dialog.setCancelable(false);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                message = new Message();
                try{
                    message.arg1 = 0;
                    message.obj = "fail";
                    flagOn = false;
                    getRequisicaoApontamentos();
                }catch (Exception e){
                    e.getMessage().toString();
                }
            }
        }).start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.obj != null){
                    if((msg.arg1 == 1) && (msg.obj.equals("sucess"))){
                        if(activityUtil.verificaPrefUserLogado(MainActivity.this)){
                            try{
                                //1: exibir dados do usuário logado
                                JSONObject objectU = activityUtil.recuperaPrefUserLogado(MainActivity.this);


                                cadUserPresenter.getByteArrayPhoto();

                                textViewNavNome.setText(objectU.getString(getString(R.string.dsNomeTblUser)));
                                textViewNavEmail.setText(objectU.getString(getString(R.string.dsLoginTblUser)));

                                //2: verifica se este usuário já possui apontamento(s)
                                if(activityUtil.verificaPrefUserLogadoApontamento(MainActivity.this)|| cadApontamentoPresenter.existApontamento() ){
                                    //JSONObject objectA = activityUtil.recuperaPrefUserLogadoApontamento(getApplicationContext());
                                    //montaListApontamentoMock(objectA);
                                    try{
                                        cadApontamentoPresenter.getArrayApontamentoUser();
                                    }catch (Exception e){
                                        e.getMessage().toString();
                                    }
                                }else{
                                    exibeDialogApontamento();
                                }
                            }catch (Exception e){
                                e.getMessage().toString();
                            }
                        }
                    }else{
                        if((msg.arg1 == 0) && (msg.obj.equals("fail"))){
                            if(activityUtil.verificaPrefUserLogado(MainActivity.this)){
                                try{
                                    //1: exibir dados do usuário logado
                                    JSONObject objectU = activityUtil.recuperaPrefUserLogado(MainActivity.this);


                                    cadUserPresenter.getByteArrayPhoto();

                                    textViewNavNome.setText(objectU.getString(getString(R.string.dsNomeTblUser)));
                                    textViewNavEmail.setText(objectU.getString(getString(R.string.dsLoginTblUser)));

                                    //2: verifica se este usuário já possui apontamento(s)
                                    if(activityUtil.verificaPrefUserLogadoApontamento(MainActivity.this)|| cadApontamentoPresenter.existApontamento() ){
                                        //JSONObject objectA = activityUtil.recuperaPrefUserLogadoApontamento(getApplicationContext());
                                        //montaListApontamentoMock(objectA);
                                        try{
                                            cadApontamentoPresenter.getArrayApontamentoUser();
                                        }catch (Exception e){
                                            e.getMessage().toString();
                                        }
                                    }else{
                                        exibeDialogApontamento();
                                    }
                                }catch (Exception e){
                                    e.getMessage().toString();
                                }
                            }
                        }
                    }
                }
                dialog.dismiss();
            }
        };


        //Analytics Integration
        // Obtain the shared Tracker instance.
        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        //UNICA VEZ - 01
        JSONObject padrao = activityUtil.recuperaPrefConfServico(getApplicationContext());
        if(padrao.length()==0){
            try{
                stopService(new Intent(getApplicationContext(), ServicoApontamento.class));
                activityUtil.definePrefConfServico(getApplicationContext(), 1);//DEFINE O TEMPO EM QUE O SISTEMA DEVE SOLICITAR AS MEDIDAS
                startService(new Intent(getApplicationContext(), ServicoApontamento.class));
            }catch (Exception e){
                e.getMessage().toString();
            }
        }

        /*
            Teste de cn line usando ApiClient Retrofit
         */

        //Movie movie = new Movie();
        //getApontamentos.execute(movie, this); // referente a essa interface: ApplicationServiceCallback<MoviesResponse> cai no onSuccess ou onError
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //****************************************************** Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_apontamento) {
            try{
                exibeDialogApontamento();
            }catch (Exception e){
                e.getMessage().toString();
            }
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //startActivity(new Intent(MainActivity.this, ListApontamento.class));
            //startActivity(new Intent(MainActivity.this, ListHistorico.class));
            //startActivity(new Intent(MainActivity.this, CadConfServ.class));

            stopService(new Intent(getApplicationContext(), ServicoApontamento.class));
            startActivityForResult(new Intent(MainActivity.this, CadConfServ.class), REQUEST_SERVER);

            return true;
        }
        if (id == R.id.action_cleanpreferences) {
            try{

                stopService(new Intent(getApplicationContext(), ServicoApontamento.class));

                activityUtil.cleanAllPreferences(getApplicationContext());

                startActivity(new Intent(this, ViewSplash.class));
            }catch (Exception e){
                e.getMessage().toString();
            }
            return true;
        }

        /* Opções de teste
        if (id == R.id.action_fragment) {
            try{
                startActivity(new Intent(MainActivity.this, ContainerFragmentActivity.class));
            }catch (Exception e){
                e.getMessage().toString();
            }
            voltar true;
        }
        if (id == R.id.action_event) {
            // Get tracker.
            Tracker t = ((GoogleAnalyticsApplication) getApplication()).getDefaultTracker();

            // Build and send an Event.
            t.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.categoryId))
                    .setAction(getString(R.string.actionId))
                    .setLabel(getString(R.string.labelId))
                    .build());

            voltar true;
        }
        if (id == R.id.action_exception) {
            Exception e = null;
            try{
                int num[]={1,2,3,4};
                System.out.println(num[5]);
            }catch (Exception f){
                e = f;
            }
            if( e != null){
                Tracker t = ((GoogleAnalyticsApplication) getApplication()).getDefaultTracker();
                t.send(new HitBuilders.ExceptionBuilder()
                        .setDescription(new StandardExceptionParser(MainActivity.this, null).getDescription(Thread.currentThread().getName(), e))
                        .setFatal(false)
                        .build());
            }
            voltar true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    //****************************************************** OnNavigationItemSelectedListener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_relatorio) {
            stopService(new Intent(getApplicationContext(), ServicoApontamento.class));
            startActivity(new Intent(this, Report.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //****************************************************** Implementação RecyclerView
    /*
    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeItem(v);
        }

        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewDs);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < ApontamentoResponse.getArrayDs().length; i++) {
                if (selectedName.equals(ApontamentoResponse.getArrayDs()[i])) {
                    selectedItemId = ApontamentoResponse.getArrayId()[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }
    }
    */

    //****************************************************** ICadApontamentoView
    @Override
    public String getCadApontamentoAltura() {
        return editTextAltura.getText().toString();
    }

    @Override
    public String getCadApontamentoPeso() {
        return editTextPeso.getText().toString();
    }

    @Override
    public String getCadApontamentoDsDataTime() {
        return ActivityUtil.getDataHora(System.currentTimeMillis());
    }

    @Override
    public long getCadApontamentoDataTime() {
        return System.currentTimeMillis();
    }

    @Override
    public void onSupportContentChanged() {
        super.onSupportContentChanged();
    }

    @Override
    public JSONArray montaListaApondatamento(JSONArray jsonArray) {
        try{
            final ArrayList<Apontamento> myDataModels;
            listView=(ListView)findViewById(R.id.myList);
            myDataModels= new ArrayList<>();
            for(int x= 0; x<jsonArray.length(); x++){
                JSONObject object = (JSONObject) jsonArray.get(x);
                int    idApontamento =  object.getInt(getString(R.string.idTblUserAptmento));
                int    dataHora      =  object.getInt(getString(R.string.dataTimeTblUserAptmento));
                String dsDataHora    =  object.getString(getString(R.string.dsDataTimeTblUserAptmento));
                String vlPeso        = object.getString(getString(R.string.dsPesoTblUserAptmento));
                String vlAltura      = object.getString(getString(R.string.dsAlturaTblUserAptmento));
                int    inc           = object.getInt(getString(R.string.imcTblUserAptmento));
                String dsStatus      = object.getString(getString(R.string.dsStatusTblUserAptmento));
                int    idUsuario     = object.getInt(getString(R.string.idTblUser));

                myDataModels.add(new Apontamento(idApontamento, dataHora, dsDataHora, vlPeso, vlAltura, Float.valueOf(inc), dsStatus, idUsuario));
            }

            if(flagOn){
                adapter = new CustomAdapter(myDataModels, urlPropaganda, getApplicationContext());
                if(urlPropaganda.size()>0 && movies.size()>0){
                    //urlPropaganda.clear();
                    movies.clear();
                }
            }else{
                adapter = new CustomAdapter(myDataModels, getApplicationContext());
            }

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Apontamento dataModel= myDataModels.get(position);
                    Snackbar.make(view, dataModel.getDsStatus().toString(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                    if(flagOn){
                        showDialog(urlPropaganda.get(position));
                    }
                }
            });
        }catch (Exception e){
            e.getMessage().toString();
        }
        //Toast.makeText(this, "Total da lista de itens a ser exibido: " + String.valueOf(jsonArray.length()), Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void showCadApontamentoAlturaError(int resId) {
        editTextAltura.setError(getString(resId));
    }

    @Override
    public void showCadApontamentoPesoError(int resId) {
        editTextPeso.setError(getString(resId));
    }

    @Override
    public void showCadApontamentoDataTimeError(int resId) {
        Toast.makeText(getApplicationContext(), getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCadApontamentoError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMontaListaApontamentoError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }



    //****************************************************** ApplicationServiceCallback do processo on line

    @Override
    public void onSuccess(Response<MoviesResponse> output) {
        if(output !=null){
            movies = output.body().getResults();
            urlPropaganda = new ArrayList<>(movies.size());

            String pathPictute = "";
            for(int m=0;m<movies.size(); m++){
                pathPictute = String.valueOf(Constants.Headers.URL_IMG_COMPLETO + movies.get(m).getBackdropPath());
                System.out.print("Path image load: " + pathPictute.toString());
                Movie movie = movies.get(m);
                urlPropaganda.add(pathPictute);
                //System.out.print("Path image load: " + movie.backdropPath.toString());
            }
        }
        flagOn = true;
        message.arg1 = 1;
        message.obj = "sucess";
        handler.sendMessage(message);
    }

    @Override
    public void onError(ApplicationServiceError error) {
        try{
            //DialogHelper.showErrorDialog(this, error.getCode(), error.getMessage());
            flagOn = false;
            message.arg1 = 0;
            message.obj = "fail";
            handler.sendMessage(message);

        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    //****************************************************** dialog de exibição para o usuário informar suas medidas
    public void exibeDialogApontamento(){
        try{
            // con este tema personalizado evitamos los bordes por defecto
            customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
            //deshabilitamos el título por defecto
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //obligamos al usuario a pulsar los botones para cerrarlo
            customDialog.setCancelable(false);
            //establecemos el contenido de nuestro dialog
            customDialog.setContentView(R.layout.customdialog);

            //define o título do Dialog
            //customDialog.setTitle("Informe aqui");

            TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
            titulo.setText("Informe aqui");
            editTextAltura = (EditText) customDialog.findViewById(R.id.lyApontamentoEditTextAltura);
            editTextPeso = (EditText) customDialog.findViewById(R.id.lyApontamentoEditTextPeso);

            stopService(new Intent(getApplicationContext(), ServicoApontamento.class));

            editTextAltura.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void afterTextChanged(Editable text) {
                    if (text.length() == 1) {
                        text.append('.');
                    }
                    if(text.length() == 4){
                        editTextPeso.requestFocus();
                    }
                }
            });




            //*** get conteúdo já apontando

            try{
                JSONObject objectA = activityUtil.recuperaPrefUserLogadoApontamentoGPS(MainActivity.this);
                if(objectA != null && objectA.length()>0){
                    if(objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento)).toString().length()>0){
                        editTextAltura.setText(objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento)));
                        editTextPeso.requestFocus();
                        //altura = objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento));
                        //peso   = objectA.getString(getApplicationContext().getString(R.string.dsPesoTblUserAptmento));
                    }
                }
            }catch (Exception e){
                e.getMessage().toString();
            }


            ((Button) customDialog.findViewById(R.id.start)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    try{
                        if (editTextAltura.getText().toString().length()>0 && editTextPeso.getText().toString().length()>0) {
                            /*
                            define preferences apontamento com dados da altura, peso e GPS
                            COLAR NO CAMPO PESQUISA google maps: 40.7876165, -73.9531382   * OBEDECER A SEQUENCIA
                             */
                            gps = new GpsService(getApplicationContext());


                            JSONObject medidasJson = new JSONObject();
                            medidasJson.put(getString(R.string.dsAlturaTblUserAptmento), editTextAltura.getText().toString());
                            medidasJson.put(getString(R.string.dsPesoTblUserAptmento), editTextPeso.getText().toString());

                            activityUtil.definePrefUserLogadoApontamentoGPS(getApplicationContext(), gps, medidasJson);

                            cadApontamentoPresenter.registerApontamentoUser();
                            customDialog.dismiss();

                            //recupero - limpo e redefino o time do service
                            JSONObject recuperar = activityUtil.recuperaPrefConfServico(getApplicationContext());
                            String idRecuperado = recuperar.getString(getResources().getString(R.string.idConfServico));
                            activityUtil.limpaPrefConfServ(getApplicationContext());
                            activityUtil.definePrefConfServico(getApplicationContext(), Integer.valueOf(idRecuperado));
                            startService(new Intent(getApplicationContext(), ServicoApontamento.class));

                            //Toast.makeText(MainActivity.this, R.string.start, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, R.string.alertApontamento, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.getMessage().toString();
                    }
                }
            });

            ((Button) customDialog.findViewById(R.id.clean)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    editTextAltura.setText("");
                    editTextAltura.setFocusable(true);
                    editTextPeso.setText("");
                    //customDialog.dismiss();
                    //Toast.makeText(MainActivity.this, R.string.clean, Toast.LENGTH_SHORT).show();

                }
            });
            //exibe na tela o dialog
            customDialog.show();
            //activityUtil.limpaPrefInfoMedidas(getApplicationContext());

        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    //****************************************************** onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_SERVER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                String  retorno = data.getExtras().getString("status");
                if((retorno.length()>0) && (retorno.equals("sucess"))){
                    try{
                        JSONObject paramSevice = activityUtil.recuperaPrefConfServico(getApplicationContext());
                        String param = paramSevice.getString(getApplicationContext().getResources().getString(R.string.idConfServico));
                    }catch (Exception e){
                        e.getMessage().toString();
                    }
                }else{
                    if((retorno.length()>0) && (retorno.equals("none"))){
                        stopService(new Intent(getApplicationContext(), ServicoApontamento.class));
                        startService(new Intent(getApplicationContext(), ServicoApontamento.class));
                    }
                }
            }else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
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





//****************************************************** online

    // Métodos que solicita os filmes ( nosso exemplo ) o retorno cai em onSuccess ou onError
    public boolean  getRequisicaoApontamentos(){
        try{
            Movie movie = new Movie();
            getApontamentos.execute(movie, this);
            return true;
        }catch (Exception e){
            DialogHelper.showErrorDialog(getApplicationContext(),error, e.getMessage().toString());
        }
        return false;
    }

    /*
     Método older
     public void montaListApontamento(JSONObject objectA){
        try{
            altura = objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento));
            peso   = objectA.getString(getApplicationContext().getString(R.string.dsPesoTblUserAptmento));
            dataTime = objectA.getString(getApplicationContext().getString(R.string.prefDataTime_userLogado));
            listView=(ListView)findViewById(R.id.myList);
            dataModels= new ArrayList<>();
            dataModels.add(new ApontamentoResponse("Data", "26-1-17 - 13:34:08", peso + " - " + altura,"September 23, 2008"));
            adapter= new CustomAdapter(dataModels,getApplicationContext());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ApontamentoResponse dataModel= dataModels.get(position);

                    Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

      @Override
    public void startMainListActivity() {
        try{
            gps = new GpsService(getApplicationContext());
            JSONObject value = activityUtil.getValeuJson(getApplicationContext(),this.getCadApontamentoAltura(), this.getCadApontamentoPeso());
            activityUtil.definePrefUserLogadoApontamento(getApplicationContext(), gps, value);
            JSONObject objectA = activityUtil.recuperaPrefUserLogadoApontamento(this);
            montaListApontamento(objectA);
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
     */

    //Montagem de lista mockado
    public void montaListApontamentoMock(JSONObject objectA){
        try{
            altura = objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento));
            peso   = objectA.getString(getApplicationContext().getString(R.string.dsPesoTblUserAptmento));

            listView=(ListView)findViewById(R.id.myList);

            dataModels= new ArrayList<>();
            dataModels.add(new Apontamento());

            adapter= new CustomAdapter(dataModels,null, getApplicationContext());//array da propaganda

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Apontamento dataModel= dataModels.get(position);

                    Snackbar.make(view, dataModel.getVlPeso()+"\n"+dataModel.getVlAltura()+" API: "+dataModel.getDsDataHora(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public String getCadNome() {
        return null;
    }

    @Override
    public String getCadUserEmail() {
        return null;
    }

    @Override
    public String getCadUserSenha() {
        return null;
    }

    @Override
    public byte[] getByteArrayPhoto() {
        return new byte[0];
    }

    @Override
    public void setByteArrayPhoto(byte[] byteArray) {
        try{
            imageViewNavPhoto.setImageBitmap(BitmapFactory.decodeByteArray( byteArray,
                    0,byteArray.length));
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public void nonePhoto() {
        try{
            imageViewNavPhoto.setImageResource(R.mipmap.person);
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    @Override
    public void showCadUserNomeError(int resId) {

    }

    @Override
    public void showCadUserEmailError(int resId) {

    }

    @Override
    public void showCadUserPasswordError(int resId) {

    }

    @Override
    public void showCadUserError(int resId) {

    }

    @Override
    public void startMainActivity() {

    }

    public void showDialog(String urlIMG){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_propaganda, null);
        dialogBuilder.setView(dialogView);
        ImageView imageViewPropaganda = (ImageView) dialogView.findViewById(R.id.imageViewPropaganda);
        TextView text = (TextView) dialogView.findViewById(R.id.viewPropaganda);


        Picasso.with(this)
                .load(urlIMG)
                .resize(350,350).into(imageViewPropaganda);


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }
}