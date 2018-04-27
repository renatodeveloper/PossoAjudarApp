package com.possoajudar.mock_server.infrastructure.backend;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public class DefaultResponseHandler implements MockResponseHandler {
    @Override
    public MockResponse response(RecordedRequest request) {
        MockResponseFactory mockResponseFactory = new MockResponseFactory();
        int responseCode = 200;
        String responseBody = "";
        try {
            if (request.getPath().contains("/user/auth/")) {
                if (request.getPath().contains("developer@possoajudar.com/dev")) {
                    responseBody = mockResponseFactory.loginSuccess();
                    MockResponse mockResponse = new MockResponse().setResponseCode(responseCode).setBody(responseBody);
                    mockResponse.addHeader("token", "ASDH3-23R3ER-WEFWQEFE-EFW");
                    return mockResponse;
                }else {
                    responseBody = mockResponseFactory.loginFail();
                }
            } else
            if (request.getPath().startsWith("/report/appointments")) {
                responseBody = mockResponseFactory.appointments();

                String value = request.getBody().readUtf8();
                if(value.length()>0){ }
                    MockResponse mockResponse = new MockResponse().setResponseCode(responseCode).setBody(responseBody);
                    mockResponse.addHeader("token", "ASDH3-23R3ER-WEFWQEFE-EFW");
                    return mockResponse;
                }else {
                    responseCode = 404;
                }
        } catch (Exception e) {
            return new MockResponse().setResponseCode(404);
        }
        return new MockResponse().setResponseCode(responseCode).setBody(responseBody);
    }
}
