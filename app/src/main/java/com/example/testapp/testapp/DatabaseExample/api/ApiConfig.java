package com.example.testapp.testapp.DatabaseExample.api;


import com.example.testapp.testapp.BuildConfig;

public class ApiConfig {

    //base server url
    public static final String BASE_URL = BuildConfig.LOCAL_BASE_URL + BuildConfig.API_VERSION;//Api
    private static final String BASE_API_NAMESPACE = BuildConfig.API_NAMESPACE;

    //Response cache size for OkHTTP - 10 MB
    public static final long MAX_HTTP_CACHE_SIZE = 10 * 1024 * 1024;

    public static final String TEXT_PLAIN = "text/plain";
    public static final String FORM_DATA = "multipart/form-data";

    /**
     * all url paths appending to {@link #BASE_URL}
     */
    public interface EndPoints {
        //API Endpoints

    }

    /**
     * header key/values
     *
     * @see HeaderInterceptor
     */
    public interface Headers {
        String AUTHORIZATION = "Authorization";
        String LANGUAGE_CODE = "language_code";
        String USER_ID = "user_id";
    }

    /**
     * parameters
     */
    public interface Params {
        //API Params
        String COUNTRY_ID = "country_id";
        String ID = "id";
    }

    /**
     * all api specific response codes given from server side
     */
    public interface ResponseCodes {
        int SUCCESS = 200;
        int NOT_FOUND = 404;
        int CONFLICT = 400;
        int AUTH_TOKEN_ERROR = 401;
        int INVALID_TOKEN_ERROR = 417;
    }

    /**
     * network timeouts - in seconds
     */
    public interface Timeouts {
        int CONNECT = 120;
        int READ = 120;
        int WRITE = 120;
        long SOCKET_CONNECT = 20;
        long SOCKET_RECONNECT_DELAY = 2;
    }
}
