package com.possoajudar.app.application.service;

/**
 * Created by renato on 13/02/2018.
 */

public interface ApplicationService <Input, Output> {
    void execute(Input input, ApplicationServiceCallback<Output> callback);
}
