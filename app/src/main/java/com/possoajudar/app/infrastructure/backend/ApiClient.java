package com.possoajudar.app.infrastructure.backend;

import com.possoajudar.app.infrastructure.Constants;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by renato on 13/02/2018.
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Headers.URL_JSON_MOCKSERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientWS_RESTful() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Headers.URL_RESTfull)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientMoviedb() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Headers.URL_MOVIEdb)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /*


    public static Retrofit getClientGoogle() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Headers.URL_GOOGLE_API_OAuth)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
      */
}
