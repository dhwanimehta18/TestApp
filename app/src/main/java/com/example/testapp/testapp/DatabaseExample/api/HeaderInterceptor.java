package com.example.testapp.testapp.DatabaseExample.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.library.General;
import com.library.api.response.user.UserResponse;
import com.library.util.common.Consts;
import com.library.util.preference.PrefUtils;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Locale;


public class HeaderInterceptor implements Interceptor {

    private PrefUtils mPrefUtils = General.getInstance().getAppComponent().providePrefUtils();
    private Gson mGson = General.getInstance().getAppComponent().provideGson();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();

        UserResponse.User user = mGson.fromJson(mPrefUtils.getString(Consts.SharedPrefs.USER), UserResponse.User.class);
        String accessToken = mPrefUtils.getString(Consts.SharedPrefs.AUTHORIZATION);
        builder.header(ApiConfig.Headers.LANGUAGE_CODE, mPrefUtils.getString(Consts.SharedPrefs.APPLICATION_SELECTED_LANGUAGE));
        if (user != null) {
            builder.header(ApiConfig.Headers.AUTHORIZATION, String.format(Locale.ENGLISH, Consts.StringFormats.HEADER_ACCESS_TOKEN_FORMAT, accessToken));
            builder.header(ApiConfig.Headers.USER_ID, user.get_id());
            builder.header(ApiConfig.Headers.IS_MANAGER, Boolean.toString(user.is_manager()));
        }
        return chain.proceed(builder.build());
    }

}

