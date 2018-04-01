package com.possoajudar.mock_server.infrastructure.backend;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public interface MockResponseHandler {
    MockResponse response(final RecordedRequest request);
}
