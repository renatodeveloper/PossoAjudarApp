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
                    .baseUrl(Constants.Headers.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}