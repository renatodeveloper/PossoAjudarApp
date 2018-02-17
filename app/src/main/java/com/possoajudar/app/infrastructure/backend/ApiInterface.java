package com.possoajudar.app.infrastructure.backend;

import com.possoajudar.app.domain.model.MoviesResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by renato on 13/02/2018.
 */

public interface ApiInterface {
    /*
    @GET("apontamento/week")
    Call<ApontamentoResponse> getApontamentoWeek(@Query("api_key") String apiKey);

    @GET("apontamento/weekend")
    Call<ApontamentoResponse> getApontamentoWeekend(@Query("api_key") String apiKey);

    @GET("apontamento/year")
    Call<ApontamentoResponse> getApontamentoYear(@Query("api_key") String apiKey);

    @GET("apontamento/{id}")
    Call<ApontamentoResponse> getApontamentoDetails(@Path("id") int id, @Query("api_key") String apiKey);

     */


    //http://api.themoviedb.org/3/movie/popular?api_key=50a888ee9e4206496cb6ef949d8c7a44
    //https://api.themoviedb.org/3/movie/now_playing?api_key=50a888ee9e4206496cb6ef949d8c7a44
    @GET("movie/popular")
    Call<MoviesResponse> getApontamentoMonth(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getMoviesNow(@Query("api_key") String apiKey);



    /*
    https://webmaniabr.com/docs/rest-api-correios/
    developer.renato@gmail.com
    REST API dos Correios
        Olá,
            Utilize a API REST da WebmaniaBR® para consultar o CEP nos Correios grátis e de uso ilimitado. Segue abaixo as credenciais da sua aplicação:
                Documentação: http://webmania.me/1XHNtS2
                app_key: bGo65isFUKC3KVOJBu7QfJixtE1HjALa
                app_secret: TwtaphSYxFWvbjfmlQJmyy5U4vphhFZRqWhzVAbYbDmDyWwz
            Faça o primeiro teste no link https://webmaniabr.com/api/1/cep/80540-180/.

Atenciosamente,
Equipe WebmaniaBR®
     */
}
