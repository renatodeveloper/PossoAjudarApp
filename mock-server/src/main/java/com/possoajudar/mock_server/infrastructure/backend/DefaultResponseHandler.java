package com.possoajudar.mock_server.infrastructure.backend;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public class DefaultResponseHandler implements MockResponseHandler {
    String path;
    @Override
    public MockResponse response(RecordedRequest request) {
        MockResponseFactory mockResponseFactory = new MockResponseFactory();
        path = request.getPath();
        int responseCode = 200;
        String responseBody = "";
        try {
            if (path.contains("/user/auth/")) {
                if (path.contains("developer@possoajudar.com/dev")) {
                    responseBody = mockResponseFactory.loginSuccess();
                    MockResponse mockResponse = new MockResponse().setResponseCode(responseCode).setBody(responseBody);
                    mockResponse.addHeader("token", "ASDH3-23R3ER-WEFWQEFE-EFW");
                    return mockResponse;
                }else {
                    responseBody = mockResponseFactory.loginFail();
                }
            }else {
                responseCode = 404;
            }
        } catch (Exception e) {
            return new MockResponse().setResponseCode(404);
        }
        return new MockResponse().setResponseCode(responseCode).setBody(responseBody);
    }
}
