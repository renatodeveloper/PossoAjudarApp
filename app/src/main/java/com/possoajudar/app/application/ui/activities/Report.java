package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.possoajudar.app.R;
import com.possoajudar.app.application.service.ICadApontamentoView;
import com.possoajudar.app.application.service.cadastro.CadApontamentoPresenter;
import com.possoajudar.app.application.ui.adapter.CustomAdapter;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.domain.model.ServiceResponse;
import com.possoajudar.app.infrastructure.backend.ApiClient;
import com.possoajudar.app.infrastructure.backend.ApiInterface;
import com.possoajudar.app.infrastructure.helper.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

public class Report extends BaseActivity implements ICadApontamentoView {
    private static final String TAG = "MainActivity";

    static public Context context;
    private TextView thedate;
    private ImageView viewGoCalendar;

    ListView listView;
    private static CustomAdapter adapter;
    ArrayList<Apontamento> myDataModels = null;
    String date = "";

    private CadApontamentoPresenter presenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_report;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thedate = (TextView) findViewById(R.id.date);
        viewGoCalendar = (ImageView) findViewById(R.id.viewGoCalendar);

        viewGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this,CalendarActivity.class);
                startActivity(intent);
            }
        });


        presenter = new CadApontamentoPresenter(this, this);

        context = getApplicationContext();
        Intent incoming = getIntent();
        date  = incoming.getStringExtra("date");//exemplo retorno error '2018/3/11'
        if(date != null && date.length()>0){
            try{
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Call<ArrayList<Apontamento>> call = apiService.getAppointmentsMockServer(date);
                        call.enqueue(new Callback<ArrayList<Apontamento>>() {
                            @Override
                            public void onResponse(retrofit.Response<ArrayList<Apontamento>> response, Retrofit retrofit) {
                                try {
                                    if (response.body() != null) {
                                        ArrayList<Apontamento> list = response.body();
                                        JSONArray jsonArray = new JSONArray();
                                        JSONObject object;
                                        if (response != null && list.size() > 0) {
                                            for (int x = 0; x < list.size(); x++) {
                                                object = new JSONObject();
                                                object.put(getString(R.string.idTblUserAptmento), list.get(x).idApontamento);
                                                object.put(getString(R.string.dataTimeTblUserAptmento), list.get(x).dataHora);
                                                object.put(getString(R.string.dsDataTimeTblUserAptmento), list.get(x).dsDataHora);
                                                object.put(getString(R.string.dsPesoTblUserAptmento), list.get(x).vlPeso);
                                                object.put(getString(R.string.dsAlturaTblUserAptmento), list.get(x).vlAltura);
                                                object.put(getString(R.string.imcTblUserAptmento), list.get(x).imc);
                                                object.put(getString(R.string.dsStatusTblUserAptmento), list.get(x).dsStatus);
                                                object.put(getString(R.string.idTblUser), list.get(x).idUsuario);
                                                jsonArray.put(object);
                                            }
                                            montaListaApondatamento(jsonArray);
                                        }
                                    }else{
                                        showConnectionRefusedError(date);
                                    }
                                    thedate.setText(date);
                                    date = "";
                                }catch (Exception e){
                                    e.getMessage().toString();
                                }
                            }
                            @Override
                            public void onFailure(Throwable t) {
                                //Connection refused
                                showConnectionRefusedError(date);
                                Log.e(TAG, "Error occured while fetching post.");
                            }
                        });

            }catch (Exception e){
                e.getMessage().toString();
            }
        }

        thedate.setText(date);



        listView=(ListView)findViewById(R.id.myList);
        myDataModels= new ArrayList<>();


        /*
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

         */
        /*


        myDataModels.add(new Apontamento(10, 20, "dsDataHora", "vlPeso", "vlAltura", 0, "dsStatus", 1));

        adapter = new CustomAdapter(myDataModels, getApplicationContext());

        listView.setAdapter(adapter);
        */
    }

    @Override
    public String getCadApontamentoAltura() {
        return null;
    }

    @Override
    public String getCadApontamentoPeso() {
        return null;
    }

    @Override
    public String getCadApontamentoDsDataTime() {
        return null;
    }

    @Override
    public long getCadApontamentoDataTime() {
        return 0;
    }

    @Override
    public JSONArray montaListaApondatamento(JSONArray jsonArray) {
        try{
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


                adapter = new CustomAdapter(myDataModels, getApplicationContext());

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Apontamento dataModel= myDataModels.get(position);
                        Snackbar.make(view, dataModel.getDsStatus().toString(), Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();
                    }
                });
            }catch (Exception e){
                e.getMessage().toString();
            }
            //Toast.makeText(this, "Total da lista de itens a ser exibido: " + String.valueOf(jsonArray.length()), Toast.LENGTH_LONG).show();
            return null;
        }catch (Exception e){
            e.getMessage().toString();
        }
        return null;
    }

    @Override
    public void showMontaListaApontamentoSemApontamento(int resId) {
        Toast.makeText(getApplicationContext(), getString(resId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCadApontamentoAlturaError(int resId) {

    }

    @Override
    public void showCadApontamentoPesoError(int resId) {

    }

    @Override
    public void showCadApontamentoDataTimeError(int resId) {

    }

    @Override
    public void showCadApontamentoError(int resId) {

    }

    @Override
    public void showMontaListaApontamentoError(int resId) {

    }

    @Override
    public void showConnectionRefusedError(String date) {
        JSONArray jsonArray;
        try{
            presenter.getArrayApontamentoUser(date);
        }catch (Exception e){
            e.getMessage().toString();
        }
    }

    //****************************************************** onKeyDown
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
