package com.possoajudar.app.application.module.dagger;

/**
 * Created by renato on 27/07/2017.
 * Gerebcia as activity que irão receber a injeção de dependencia
 */
import com.possoajudar.app.application.ui.activities.Login;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(Login target);
}
