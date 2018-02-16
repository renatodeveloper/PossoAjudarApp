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

    }


}
