package com.possoajudar.app.infrastructure.backend.gitHub;

import com.possoajudar.app.infrastructure.Constants;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class GitHubClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Headers.URL_GIT_HUB_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientGitHub() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Headers.URL_GIT_HUB)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
