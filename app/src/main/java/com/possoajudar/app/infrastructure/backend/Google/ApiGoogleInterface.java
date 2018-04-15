package com.possoajudar.app.infrastructure.backend.Google;

import com.possoajudar.app.domain.model.GDriveFiles;
import com.possoajudar.app.domain.model.OAuthToken;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiGoogleInterface {
    /**
     * The call to request a token
     */
    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<OAuthToken> requestTokenForm(
            @Field("code")String code,
            @Field("client_id")String client_id,
//            @Field("client_secret")String client_secret, //Is not relevant for Android application
            @Field("redirect_uri")String redirect_uri,
            @Field("grant_type")String grant_type);

    /**
     * The call to refresh a token
     */
    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<OAuthToken> refreshTokenForm(
            @Field("refresh_token")String refresh_token,
            @Field("client_id")String client_id,
//            @Field("client_secret")String client_secret, //Is not relevant for Android application
            @Field("grant_type")String grant_type);
    /**
     * The call to retrieve the files of our User in GDrive
     */
    @GET("drive/v3/files")
    Call<GDriveFiles> listFiles();
}