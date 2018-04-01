package com.possoajudar.mock_server.application.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.possoajudar.mock_server.R;
import com.possoajudar.mock_server.infrastructure.backend.DefaultResponseHandler;
import com.possoajudar.mock_server.infrastructure.backend.MockResponseHandler;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public class ScrollingActivity extends AppCompatActivity {

    private MockWebServer mockWebServer;
    private MockResponseHandler mockResponseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startMockServer(view);
            }
        });

        mockResponseHandler = new DefaultResponseHandler();

        /* teste path...
        http://localhost:8043/api/user/auth/developer@possoajudar.com/developer
        http://localhost:8043/api/user/researchGrupo
        http://localhost:8043/api/auth/conexao
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
            shutdownMockWebServer(item.getActionView());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //*****************************************************************************************START
    public void startMockServer(final View view){
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        mockWebServer = new MockWebServer();
                        mockWebServer.setDispatcher(new Dispatcher() {
                            @Override
                            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                                return new DefaultResponseHandler().response(request);

                            }
                        });
                        mockWebServer.start(8043);
                        Snackbar.make(view, "Your mock server is running at " + 8043, Snackbar.LENGTH_LONG).show();
                    }catch (Exception e){
                        e.getMessage().toString();
                    }
                }
            }).start();
        }catch (Exception e){
            Log.e(getString(R.string.app_name), "An error occurred on startMockWebServer", e);
        }
    }

    //******************************************************************************************STOP
    public void shutdownMockWebServer(View view) {
        try {
            String message = "You stopped your mock server";
            if (mockWebServer == null) {
                message = "Your mock server is not running yet";
            } else {
                mockWebServer.shutdown();
                mockWebServer = null;
            }
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "An error occurred on shutdownMockWebServer", e);
        }
    }
}
