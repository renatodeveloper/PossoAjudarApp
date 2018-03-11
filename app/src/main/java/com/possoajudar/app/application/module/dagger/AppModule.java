package com.possoajudar.app.application.module.dagger;
import android.app.Application;

import com.possoajudar.app.domain.model.Apontamento;

import dagger.Module;
import dagger.Provides;
/**
 * Created by renato on 27/07/2017.
 * Prover as instancias necessarias
 */
@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public Apontamento providesApontamento(){
        return  new Apontamento();
    }

}

