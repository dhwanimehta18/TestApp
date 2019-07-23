package com.example.testapp.testapp.DatabaseExample.api.response.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseObjectResponse<T> extends BaseResponse {

    @SerializedName("data")
    @Expose
    private T data;

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseObjectResponse{" +
                "data=" + data +
                "} " + super.toString();
    }
}
