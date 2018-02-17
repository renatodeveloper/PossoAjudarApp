package com.possoajudar.app.application.service;
import com.possoajudar.app.domain.model.MoviesResponse;

import retrofit.Response;

/**
 * Created by renato on 13/02/2018.
 */

public interface ApplicationServiceCallback<Output> {
    void onSuccess(Response<MoviesResponse> output);
    void onError(ApplicationServiceError error);
}
