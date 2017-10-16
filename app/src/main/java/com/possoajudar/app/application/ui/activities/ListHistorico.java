package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.possoajudar.app.R;
import com.possoajudar.app.application.module.app.GoogleAnalyticsApplication;
import com.possoajudar.app.application.ui.adapter.CustomAdapter;
import com.possoajudar.app.application.ui.recyclerview.MyRecyclerViewAdapter;
import com.possoajudar.app.domain.model.Apontamento;

import java.util.ArrayList;

/**
 * Created by Renato on 06/08/2017.
 */

public class ListHistorico extends Activity {
    //*** - Implementação RecyclerView
    //private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Apontamento> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private static MyRecyclerViewAdapter adapter;


    private static final String TAG = CadUsuario.class.getSimpleName();
    private Tracker mTracker;
    private String name = "ListHistorico";
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_historico);

        //*** - Implementação RecyclerView

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


        //Analytics Integration
        // Obtain the shared Tracker instance.
        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

    }

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
         //adapter.notifyItemRemoved(selectedItemPosition);
     }
     }

}
