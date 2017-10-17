package com.possoajudar.app.application.ui.activities;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.possoajudar.app.R;
import com.possoajudar.app.application.module.app.GoogleAnalyticsApplication;
import com.possoajudar.app.application.ui.fragments.PossoAjudarAppFrag;

/**
 * Created by renato on 16/10/2017.
 */

public class ContainerFragmentActivity extends Activity {
    private static final String TAG = ContainerFragmentActivity.class.getSimpleName();
    private Tracker mTracker;
    String name = new String("ContainerFragmentActivity Screen");



    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_fragment);
        //Analytics Integration
        // Obtain the shared Tracker instance.
        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();


        try{
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment myFragment = (Fragment) fragmentManager.findFragmentByTag("TAG_FRAGMENT");
            if (myFragment == null) {

                myFragment = new PossoAjudarAppFrag();

                fragmentTransaction.replace(R.id.fragContainer, myFragment, "TAG_FRAGMENT");
                fragmentTransaction.commit();

            } else {
                fragmentTransaction.remove(myFragment).commit();

            }
        }catch (Exception e){
            e.getMessage().toString();
        }

    }

}
