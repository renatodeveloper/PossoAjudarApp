package com.possoajudar.app.application.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.possoajudar.app.application.ui.fragments.PossoAjudarAppFrag;
import com.possoajudar.app.application.ui.recyclerview.MyRecyclerViewAdapter;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.infrastructure.helper.ActivityUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ActivityUtil activityUtil;
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


    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Main Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private Tracker mTracker;

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

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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



        listView=(ListView)findViewById(R.id.myList);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //startActivity(new Intent(MainActivity.this, ListApontamento.class));
            startActivity(new Intent(MainActivity.this, ListHistorico.class));
            return true;
        }
        if (id == R.id.action_cleanpreferences) {
            try{
                activityUtil = new ActivityUtil();
                activityUtil.cleanPrefLogado();
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
}
