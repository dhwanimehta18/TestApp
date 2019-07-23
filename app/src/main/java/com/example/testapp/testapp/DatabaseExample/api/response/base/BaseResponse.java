package com.example.testapp.testapp.DatabaseExample.api.response.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private int status;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public boolean hasSuccess() {
        return status == 200;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
