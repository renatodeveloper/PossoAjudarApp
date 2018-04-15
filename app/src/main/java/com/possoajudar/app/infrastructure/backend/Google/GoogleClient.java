package com.possoajudar.app.infrastructure.backend.Google;

import com.possoajudar.app.infrastructure.Constants;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class GoogleClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Headers.BASE_URL_GOOGLE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
