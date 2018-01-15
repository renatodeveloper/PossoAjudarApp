package com.possoajudar.app.application.ui.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.possoajudar.app.R;
import com.possoajudar.app.application.module.app.GoogleAnalyticsApplication;
import com.possoajudar.app.application.service.ICadApontamentoView;
import com.possoajudar.app.application.service.ICadUserView;
import com.possoajudar.app.application.service.cadastro.CadApontamentoPresenter;
import com.possoajudar.app.application.service.cadastro.CadApontamentoService;
import com.possoajudar.app.application.service.gps.GpsService;
import com.possoajudar.app.application.ui.adapter.CustomAdapter;
import com.possoajudar.app.application.ui.fragments.PossoAjudarAppFrag;
import com.possoajudar.app.application.ui.recyclerview.MyRecyclerViewAdapter;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ICadApontamentoView{

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
    private static CustomAdapter adapter;

    TextView textViewNavNome;
    TextView textViewNavEmail;

    String altura = "";
    String peso = "";
    String dataTime = "";

    ProgressDialog progressDialog;
    private CadApontamentoPresenter cadApontamentoPresenter;
    private CadApontamentoService cadApontamentoService;

    EditText editTextAltura = null;
    EditText editTextPeso = null;


    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Main Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private Tracker mTracker;
    Dialog customDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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



        data = new ArrayList<Apontamento>();
        for (int i = 0; i < Apontamento.getArrayDs().length; i++) {
            data.add(new Apontamento(
                    Apontamento.getArrayDs()[i],
                    Apontamento.getArraySubDs()[i],
                    Apontamento.getArrayId()[i],
                    Apontamento.getArrayImgStatus()[i]
            ));
        }

        removedItems = new ArrayList<Integer>();

        adapter = new MyRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
            */

        try{

            cadApontamentoPresenter = new CadApontamentoPresenter(this, cadApontamentoService, getApplicationContext());
            progressDialog = new ProgressDialog(this);

            activityUtil = new ActivityUtil();
            // ESCREVER INSERT
            if(activityUtil.verificaPrefUserLogado(getApplicationContext())){
                JSONObject objectU = activityUtil.recuperaPrefUserLogado(getApplicationContext());
                String senha  = objectU.getString(getString(R.string.dsSenhaTblUser));
                textViewNavNome.setText(objectU.getString(getString(R.string.dsLoginTblUser)));
                textViewNavEmail.setText(objectU.getString(getString(R.string.dsLoginTblUser)));

                //se já tem apontamento para este usuário
                if(activityUtil.verificaPrefUserLogadoApontamento(getApplicationContext())|| cadApontamentoPresenter.existApontamento(getApplicationContext()) ){
                    //JSONObject objectA = activityUtil.recuperaPrefUserLogadoApontamento(getApplicationContext());
                    //montaListApontamentoMock(objectA);
                    try{
                        cadApontamentoPresenter.getArrayApontamentoUser(getApplicationContext());
                    }catch (Exception e){
                        e.getMessage().toString();
                    }

                }else{
                    exibeDialogApontamento();
                }
            }
        }catch (Exception e){
            e.getMessage().toString();
        }

        //Analytics Integration
        // Obtain the shared Tracker instance.
        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

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
            startActivity(new Intent(MainActivity.this, ListHistorico.class));
            return true;
        }
        if (id == R.id.action_cleanpreferences) {
            try{
                activityUtil = new ActivityUtil();
                activityUtil.limpaPrefUserLogado(getApplicationContext());
                activityUtil.limpaPrefUserLogadoApontamento(getApplicationContext());
                startActivity(new Intent(this, Splash.class));
            }catch (Exception e){
                e.getMessage().toString();
            }
            return true;
        }

        if (id == R.id.action_fragment) {
            try{
                startActivity(new Intent(MainActivity.this, ContainerFragmentActivity.class));
            }catch (Exception e){
                e.getMessage().toString();
            }
            return true;
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

            return true;
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
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

/**
 *

//*** - Implementação RecyclerView
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
            for (int i = 0; i < Apontamento.getArrayDs().length; i++) {
                if (selectedName.equals(Apontamento.getArrayDs()[i])) {
                    selectedItemId = Apontamento.getArrayId()[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }
    }
 */

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

            //*** get conteúdo já apontando
            JSONObject objectA = activityUtil.recuperaPrefUserLogadoApontamento(getApplicationContext());
            if(objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento)).toString().length()>0){
                editTextAltura.setText(objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento)));
                editTextPeso.requestFocus();
                //altura = objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento));
                //peso   = objectA.getString(getApplicationContext().getString(R.string.dsPesoTblUserAptmento));
            }

            ((Button) customDialog.findViewById(R.id.start)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    try{
                        if (editTextAltura.getText().toString().length()>0 && editTextPeso.getText().toString().length()>0) {
                            cadApontamentoPresenter.registerApontamentoUser();

                            //define preferences apontamento
                            gps = new GpsService(getApplicationContext());
                            JSONObject value = activityUtil.getValeuJson(getApplicationContext(),editTextAltura.getText().toString(), editTextPeso.getText().toString());
                            activityUtil.definePrefUserLogadoApontamento(getApplicationContext(), gps, value);
                            JSONObject objectA = activityUtil.recuperaPrefUserLogadoApontamento(getApplicationContext());

                            customDialog.dismiss();

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
                    editTextPeso.setText("");
                    //customDialog.dismiss();
                    //Toast.makeText(MainActivity.this, R.string.clean, Toast.LENGTH_SHORT).show();

                }
            });
            //exibe na tela o dialog
            customDialog.show();


        }catch (Exception e){
            e.getMessage().toString();
        }
    }
    /*
    public void montaListApontamento(JSONObject objectA){
        try{
            altura = objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento));
            peso   = objectA.getString(getApplicationContext().getString(R.string.dsPesoTblUserAptmento));
            dataTime = objectA.getString(getApplicationContext().getString(R.string.prefDataTime_userLogado));
            listView=(ListView)findViewById(R.id.myList);
            dataModels= new ArrayList<>();
            dataModels.add(new Apontamento("Data", "26-1-17 - 13:34:08", peso + " - " + altura,"September 23, 2008"));
            adapter= new CustomAdapter(dataModels,getApplicationContext());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Apontamento dataModel= dataModels.get(position);

                    Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        }catch (Exception e){
            e.getMessage().toString();
        }
    }
*/
    public void montaListApontamentoMock(JSONObject objectA){
        try{
            altura = objectA.getString(getApplicationContext().getString(R.string.dsAlturaTblUserAptmento));
            peso   = objectA.getString(getApplicationContext().getString(R.string.dsPesoTblUserAptmento));

            listView=(ListView)findViewById(R.id.myList);

            dataModels= new ArrayList<>();
            dataModels.add(new Apontamento("Data", "26-1-17 - 13:34:08", peso + " - " + altura,"September 23, 2008"));
            dataModels.add(new Apontamento("Apple Pie", "Android 1.0", "1","September 23, 2008"));
            dataModels.add(new Apontamento("Banana Bread", "Android 1.1", "2","February 9, 2009"));
            dataModels.add(new Apontamento("Cupcake", "Android 1.5", "3","April 27, 2009"));
            dataModels.add(new Apontamento("Donut","Android 1.6","4","September 15, 2009"));
            dataModels.add(new Apontamento("Eclair", "Android 2.0", "5","October 26, 2009"));
            dataModels.add(new Apontamento("Froyo", "Android 2.2", "8","May 20, 2010"));
            dataModels.add(new Apontamento("Gingerbread", "Android 2.3", "9","December 6, 2010"));
            dataModels.add(new Apontamento("Honeycomb","Android 3.0","11","February 22, 2011"));
            dataModels.add(new Apontamento("Ice Cream Sandwich", "Android 4.0", "14","October 18, 2011"));
            dataModels.add(new Apontamento("Jelly Bean", "Android 4.2", "16","July 9, 2012"));
            dataModels.add(new Apontamento("Kitkat", "Android 4.4", "19","October 31, 2013"));
            dataModels.add(new Apontamento("Lollipop","Android 5.0","21","November 12, 2014"));
            dataModels.add(new Apontamento("Marshmallow", "Android 6.0", "23","October 5, 2015"));

            adapter= new CustomAdapter(dataModels,getApplicationContext());

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Apontamento dataModel= dataModels.get(position);

                    Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }
            });
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

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
                    String dsAltura      = object.getString(getString(R.string.dsAlturaTblUserAptmento));
                    String dsPeso        = object.getString(getString(R.string.dsPesoTblUserAptmento));
                    int    idUsuario     = object.getInt(getString(R.string.idTblUser));


                    myDataModels.add(new Apontamento("Data", dsDataHora, dsPeso + " - " + dsAltura,dsDataHora));
                }
                adapter = new CustomAdapter(myDataModels,getApplicationContext());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Apontamento dataModel= myDataModels.get(position);
                        Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();
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

    /*
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}