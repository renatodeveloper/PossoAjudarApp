package com.possoajudar.app.infrastructure.helper;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.possoajudar.app.R;

import butterknife.ButterKnife;
import roboguice.activity.RoboActionBarActivity;

/**
 * Created by renato on 14/02/2018.
 */

public abstract class BaseActivity extends RoboActionBarActivity {
    protected ActionBar supportActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    protected abstract int getContentView();
}
