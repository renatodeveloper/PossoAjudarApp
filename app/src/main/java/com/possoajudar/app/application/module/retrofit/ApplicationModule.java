package com.possoajudar.app.application.module.retrofit;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.possoajudar.app.BuildConfig;
import com.possoajudar.app.application.service.ApplicationService;
import com.possoajudar.app.application.service.research.GetApontamentos;
import com.possoajudar.app.domain.model.Movie;
import com.possoajudar.app.domain.model.MoviesResponse;
import com.possoajudar.app.infrastructure.Constants;
import com.possoajudar.app.infrastructure.backend.BackendService;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by renato on 13/02/2018.
 */

public class ApplicationModule  extends AbstractModule {
    public static final String RESEARCH = "research-service";
    public static final String GETFAPONTAMENTOS = "get-apontamentos";
    Application application;
    public ApplicationModule(Application application) {
        this.application = application;
    }


    @Override
    protected void configure() {
        bind(new TypeLiteral<ApplicationService<Movie, MoviesResponse>>() {
        }).annotatedWith(Names.named(GETFAPONTAMENTOS)).to(GetApontamentos.class);

        bind(BackendService.class).toInstance(getBackendServiceInstance());
    }

    private String printNow() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }

    private BackendService getBackendServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Headers.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.client().interceptors().add(new MyInterceptor());

        return retrofit.create(BackendService.class);
    }
    class MyInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Request newRequest = prepareRequest(request);

            logRequestData(newRequest);

            return chain.proceed(newRequest);
        }

        private void logRequestData(Request newRequest) throws IOException {
            Buffer buffer = new Buffer();

            Log.i("CustomHttpClient", newRequest.method() + " - " + newRequest.urlString());
            Log.i("CustomHttpClient", newRequest.headers().toString());

            RequestBody body = newRequest.body();

            if (body != null) {

                newRequest.body().writeTo(buffer);

                Log.i("CustomHttpClient", buffer.readUtf8());
            }
        }

        private Request prepareRequest(Request request) {
            Request newRequest;
            newRequest = request.newBuilder()
                    .addHeader(Constants.Headers.PLATFORM, "ANDROID")
                    .addHeader(Constants.Headers.PLATFORM_VERSION, Build.VERSION.CODENAME)
                    .addHeader(Constants.Headers.TIMESTAMP, printNow())
                    .addHeader(Constants.Headers.VERSION_NAME, BuildConfig.VERSION_NAME)
                    .build();
            return newRequest;
        }
    }
}