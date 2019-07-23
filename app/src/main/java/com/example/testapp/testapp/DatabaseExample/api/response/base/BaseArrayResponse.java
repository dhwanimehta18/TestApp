package com.example.testapp.testapp.DatabaseExample.api.response.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseArrayResponse<T> extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseArrayResponse{" +
                "data=" + data +
                "} " + super.toString();
    }
}
