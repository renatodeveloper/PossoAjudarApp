package com.possoajudar.app.application.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.possoajudar.app.R;
import com.possoajudar.app.application.module.app.GoogleAnalyticsApplication;
import com.possoajudar.app.application.ui.activities.ContainerFragmentActivity;

/**
 * Created by renato on 16/10/2017.
 */

public class PossoAjudarAppFrag extends Fragment {

    private static final String TAG = PossoAjudarAppFrag.class.getSimpleName();
    private Tracker mTracker;
    String name = new String("Fragment PossoAjudarApp");

    public PossoAjudarAppFrag(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleAnalyticsApplication application = (GoogleAnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.posso_ajudar_app_fragment, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName(name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }
}
