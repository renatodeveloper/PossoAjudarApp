package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.possoajudar.app.R;
import com.possoajudar.app.application.ui.adapter.CustomAdapter;
import com.possoajudar.app.domain.model.Apontamento;

import java.util.ArrayList;

/**
 * Created by renato on 31/07/2017.
 */

public class ListApontamento extends Activity {
    ArrayList<Apontamento> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_apontamento);
        listView=(ListView)findViewById(R.id.mylist);


        dataModels= new ArrayList<>();

        dataModels.add(new Apontamento());
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

    }


}
