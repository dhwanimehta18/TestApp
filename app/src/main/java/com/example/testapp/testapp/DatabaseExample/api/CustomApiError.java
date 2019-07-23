package com.example.testapp.testapp.DatabaseExample.api;


public class CustomApiError extends RuntimeException {
    private final int code;
    private final String error;

    public CustomApiError(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return "{" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}';
    }
}
