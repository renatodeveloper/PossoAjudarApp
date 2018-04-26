package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.possoajudar.app.R;
import com.possoajudar.app.application.ui.adapter.CustomAdapter;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.domain.model.ServiceResponse;
import com.possoajudar.app.infrastructure.backend.ApiClient;
import com.possoajudar.app.infrastructure.backend.ApiInterface;
import com.possoajudar.app.infrastructure.helper.BaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

public class Report extends BaseActivity  {
    private static final String TAG = "MainActivity";

    private TextView thedate;
    private ImageView viewGoCalendar;

    ListView listView;
    private static CustomAdapter adapter;
    ArrayList<Apontamento> myDataModels = null;


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


        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");//exemplo retorno error '2018/3/11'
        if(date != null && date.length()>0){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<ServiceResponse> call = apiService.getAuthMockServer("developer@possoajudar.com","dev");
                    call.enqueue(new Callback<ServiceResponse>() {
                        @Override
                        public void onResponse(retrofit.Response<ServiceResponse> response, Retrofit retrofit) {
                            String result = response.body().toString();
                            thedate.setText(response.message().toString());
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e(TAG, "Error occured while fetching post.");
                        }
                    });
                }
            }).start();
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

        myDataModels.add(new Apontamento(10, 20, "dsDataHora", "vlPeso", "vlAltura", 0, "dsStatus", 1));

        adapter = new CustomAdapter(myDataModels, getApplicationContext());

        listView.setAdapter(adapter);
    }
}
