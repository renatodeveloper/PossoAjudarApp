package com.possoajudar.app.infrastructure.backend.gitHub;

import com.possoajudar.app.domain.model.AccessToken;
import com.possoajudar.app.domain.model.GitHubRepo;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

public interface GitHubInterface {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
        @Field("client_id") String clientId,
        @Field("client_secret") String clientSecret,
        @Field("code") String code
    );


    @FormUrlEncoded
    @POST("/oauth/token")
    Call<AccessToken> getNewAccessToken(
            @Field("code") String code,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);


    @FormUrlEncoded
    @POST("/oauth/token")
    Call<AccessToken> getRefreshAccessToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);


    @GET("/users/{user}/repos")
    Call<String> listReposStr(@Path("user") String user);

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);


}
