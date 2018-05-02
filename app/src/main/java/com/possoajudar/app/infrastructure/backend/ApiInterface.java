package com.possoajudar.app.infrastructure.backend;

import com.google.gson.Gson;
import com.possoajudar.app.domain.model.Apontamento;
import com.possoajudar.app.domain.model.Cep;
import com.possoajudar.app.domain.model.ServiceResponse;
import com.possoajudar.app.domain.model.MoviesResponse;
import com.possoajudar.app.domain.model.Post;
import com.possoajudar.app.domain.model.Usuario;
import com.possoajudar.app.domain.model.UsuarioList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Url;

/**
 * Created by renato on 13/02/2018.
 */

public interface ApiInterface {
    /* themoviedb
                http://api.themoviedb.org/3/movie/popular?api_key=50a888ee9e4206496cb6ef949d8c7a44
                https://api.themoviedb.org/3/movie/now_playing?api_key=50a888ee9e4206496cb6ef949d8c7a44
     */

    @GET("movie/popular")
    Call<MoviesResponse> getApontamentoMonth(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getMoviesNow(@Query("api_key") String apiKey);



    /*  Json Place Holder   (https://jsonplaceholder.typicode.com/)
            https://jsonplaceholder.typicode.com/posts/1
            https://jsonplaceholder.typicode.com/
            http://swarajsaaj.github.io/posts/consuming-rest-api-using-retrofit-android

    Resources
        /posts	100 items
        /comments	500 items
        /albums	100 items
        /photos	5000 items
        /todos	200 items
        /users	10 items

    Routes
        GET	/posts
        GET	/posts/1
        GET	/posts/1/comments
        GET	/comments?postId=1
        GET	/posts?userId=1
        POST	/posts
        PUT	/posts/1
        PATCH	/posts/1
        DELETE	/posts/1
     */

    @GET("/posts")
    Call<List<Post>> getAllPosts();

    @GET("/posts/{id}")
    Call<Post> getPostById(@Path("id") int id);

    @GET("/cep/{cep}")
    Call<Cep> getAddress(@Path("cep") String cep);

    @GET("/user/auth/{user}/{auth}")
    Call<ServiceResponse> getAuthMockServer(@Path("user") String user, @Path("auth") String auth);


    //http://api.postmon.com.br/v1/cep/21625000

    /*
         /report/appointments/Developer
     */
    @GET("/report/appointments/{date}")
    Call<ArrayList<Apontamento>> getAppointmentsMockServerr(@Path("date") String date);

    /*
            /report/appointments?date=Developer
     */
    @POST("/report/appointments")
    @FormUrlEncoded
    Call<ArrayList<Apontamento>> getAppointmentsMockServer(@Field("headerDate") String date);


    /*
    EndPoints
     */

    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);

    @PUT("/posts/{id}")
    @FormUrlEncoded
    Call<Post> updatePost(@Path("id") long id,
                          @Field("title") String title,
                          @Field("body") String body,
                          @Field("userId") long userId);

    @DELETE("/posts/{id}")
    Call<Post> deletePost(@Path("id") long id);

    //This method is used for "POST"
    @FormUrlEncoded
    @POST("/api.php")
    Call<Post> post(
            @Field("method") String method,
            @Field("username") String username,
            @Field("password") String password
    );

    //This method is used for "GET"
    @GET("/api.php")
    Call<Post> get(
            @Query("method") String method,
            @Query("username") String username,
            @Query("password") String password
    );

    /*
        Inicio Build RESTful Services endPoints CRUD
     */

    @GET("usuarios/conection")
    Call<ServiceResponse> getStatusConection();

    @GET("usuarios/todos")
    Call<UsuarioList> getAllUsers();

    @GET("usuarios/{idUsuario}")
    Call<Usuario> getUser(@Path("idUsuario") int id);

    @GET("usuarios/find/{name}")
    Call<List<Usuario>> getUserName(@Path("name") String nameUser);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("usuarios/add")
    Call<Usuario> add(@Body Usuario body);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("usuarios/{idUsuario}")
    Call<Usuario> update(@Path("idUsuario") int id, @Body Usuario usuario);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("usuarios/{idUsuario}")
    Call<Usuario> delete(@Path("idUsuario") int id);

    /*
        Fim Build RESTful Services endPoints CRUD
     */


    @FormUrlEncoded
    @POST("/usuarios/inserir")
    Call<Usuario> insertUsuario(@Field("dsLogin") String login, @Field("dsSenha") String senha);

    @FormUrlEncoded
    @PATCH("usuarios/{id}")
    Call<Usuario> updateUsuario(@Path("idUsuario") int id, @Field("dsLogin") String nome, @Field("dsSenha") String cpf);

    @DELETE("usuarios/{id}")
    Call<Usuario> deleteUsuario(@Path("id") int id);


}