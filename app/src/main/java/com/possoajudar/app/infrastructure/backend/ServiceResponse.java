package com.possoajudar.app.infrastructure.backend;

/**
 * Created by renato on 13/02/2018.
 */

public class ServiceResponse {
    public String code;
    public String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return "0000".equals(code);
    }
}
