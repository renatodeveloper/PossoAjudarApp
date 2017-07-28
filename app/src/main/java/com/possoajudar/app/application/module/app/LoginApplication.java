package com.possoajudar.app.application.module.app;

import android.app.Application;
import android.content.Context;

import com.possoajudar.app.application.module.dagger.AppComponent;
import com.possoajudar.app.application.module.dagger.AppModule;
import com.possoajudar.app.application.module.dagger.DaggerAppComponent;
import com.possoajudar.app.application.ui.activities.Login;

/**
 * Created by renato on 27/07/2017.
 */

public class LoginApplication  extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //appComponent =  DaggerAppComponent.builder().appModule(new AppModule()).build();
        appComponent = initDagger(this);
    }

    protected AppComponent initDagger(LoginApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

    public AppComponent getAppComponent() {

        return appComponent;
    }

}
