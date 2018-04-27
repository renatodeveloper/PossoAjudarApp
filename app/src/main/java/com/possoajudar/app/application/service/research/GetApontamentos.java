package com.possoajudar.app.application.service.research;

import android.util.Log;

import com.possoajudar.app.application.service.ApplicationService;
import com.possoajudar.app.application.service.ApplicationServiceCallback;
import com.possoajudar.app.application.service.ApplicationServiceError;
import com.possoajudar.app.application.ui.activities.MainActivity;
import com.possoajudar.app.domain.model.Movie;
import com.possoajudar.app.domain.model.MoviesResponse;
import com.possoajudar.app.infrastructure.Constants;
import com.possoajudar.app.infrastructure.backend.ApiClient;
import com.possoajudar.app.infrastructure.backend.ApiInterface;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by renato on 13/02/2018.
 */

public class GetApontamentos  implements ApplicationService<Movie, MoviesResponse> {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    public void execute(Movie movie, final ApplicationServiceCallback<MoviesResponse> callback) {
        ApiInterface apiService = ApiClient.getClientMoviedb().create(ApiInterface.class);
        Call<MoviesResponse> call = apiService.getApontamentoMonth(Constants.Headers.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Response<MoviesResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<Movie> apontamentos = response.body().getResults();
                    Log.d(TAG, "Number of apontamentos received: " + apontamentos.size());
                    callback.onSuccess(response);
                } else {
                    ApplicationServiceError error = new ApplicationServiceError(String.valueOf(response.code()), response.message());
                    callback.onError(error);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                ApplicationServiceError error = new ApplicationServiceError("0","fail", null);
                callback.onError(error);
            }
        });
    }
}
