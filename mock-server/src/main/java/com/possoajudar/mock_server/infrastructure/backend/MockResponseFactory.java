package com.possoajudar.mock_server.infrastructure.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.possoajudar.mock_server.domain.model.Apontamento;
import com.possoajudar.mock_server.domain.model.ServiceResponse;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public String appointments() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);

        Apontamento apontamento;
        ArrayList<Apontamento> list = new ArrayList<>();

        for(int x=1; x<11;x++){
            apontamento = new Apontamento();

            apontamento.idApontamento = x;
            apontamento.dataHora = (int) System.currentTimeMillis();
            apontamento.dsDataHora = sdf.format(resultdate);
            apontamento.vlPeso = String.valueOf(70 + x);
            apontamento.vlAltura = "1.75";
            apontamento.imc = 2500;
            apontamento.dsStatus = "Mockado";
            apontamento.idUsuario = x;

            list.add(apontamento);
        }
        return gson.toJson(list);
    }

}
