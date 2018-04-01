package com.possoajudar.mock_server.infrastructure.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.possoajudar.mock_server.domain.model.ServiceResponse;

public class MockResponseFactory {
    private final Gson gson;
    public MockResponseFactory() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }


    public String loginSuccess() {
        ServiceResponse authResponseMock = new ServiceResponse();
        authResponseMock.code = "0000";
        authResponseMock.message = "Sucesso";
        return gson.toJson(authResponseMock);
    }

    public String loginFail() {
        ServiceResponse authResponseMock = new ServiceResponse();
        authResponseMock.code = "9999";
        authResponseMock.message = "Login ou senha inválidos. Tente novamente.";
        return gson.toJson(authResponseMock);
    }

    public String authorized() {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.code = "01";
        serviceResponse.message = "Operação autorizada";
        return gson.toJson(serviceResponse);
    }

    public String unauthorized() {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.code = "0401";
        serviceResponse.message = "Operação não autorizada";
        return gson.toJson(serviceResponse);
    }
}
