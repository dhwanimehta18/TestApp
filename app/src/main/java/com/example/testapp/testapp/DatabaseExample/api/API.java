package com.example.testapp.testapp.DatabaseExample.api;

import com.example.testapp.testapp.DatabaseExample.api.request.base.BaseRequest;
import com.example.testapp.testapp.DatabaseExample.api.response.user.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface API {

    //Login
    //Post method with data send in raw type
    @POST(ApiConfig.EndPoints.LOGIN)
    Observable<Response<UserResponse>> login(@Body BaseRequest.LoginRequest loginRequest);

    //Sign up
    @Multipart
    @POST(ApiConfig.EndPoints.SIGN_UP)
    Observable<Response<UserResponse>> signUp(@Part(ApiConfig.Params.MOBILE_NUMBER) RequestBody mobileNumber,
                                              @Part(ApiConfig.Params.GENDER) RequestBody gender,
                                              @Part(ApiConfig.Params.EMAIL) RequestBody email,
                                              @Part(ApiConfig.Params.PASSWORD) RequestBody password,
                                              @Part(ApiConfig.Params.NICK_NAME) RequestBody nickName,
                                              @Part(ApiConfig.Params.FULL_NAME) RequestBody fullName,
                                              @Part(ApiConfig.Params.STATE_ID) RequestBody stateId,
                                              @Part(ApiConfig.Params.CITY_ID) RequestBody cityId,
                                              @Part(ApiConfig.Params.COUNTRY_ID) RequestBody countryId,
                                              @Part(ApiConfig.Params.BIRTH_DATE) RequestBody birthDate,
                                              @Part(ApiConfig.Params.DEVICE_TYPE) RequestBody deviceType,
                                              @Part(ApiConfig.Params.PUSH_TOKEN) RequestBody pushToken,
                                              @Part(ApiConfig.Params.NATIONALITIES) RequestBody nationalities,
                                              @Part MultipartBody.Part[] attachment);



    //Get User Details
    @GET(ApiConfig.EndPoints.GET_USER_DETAILS)
    Observable<Response<ProfileUserResponse>> getUserDetails(@Query(ApiConfig.Params.PROFILE_ID) String profileId);


    @FormUrlEncoded
    @POST(ApiConfig.EndPoints.NOTIFICATION_LIST)
    Observable<Response<NotificationListResponse>> getNotificationList(@Field(ApiConfig.Params.SKIP) String skip,
                                                                       @Field(ApiConfig.Params.LIMIT) String limit);


    //update bid amount
    @PUT(ApiConfig.EndPoints.UPDATE_BID + "{order_bid_id}")
    Observable<Response<PostBidResponse>> updateBidAmount(@Path(ApiConfig.Params.ORDER_BID_ID) String loginUserId,
                                                          @Body BaseRequest.UpdateBidAmount updateBidAmount);

    //Accept bid
    @POST(ApiConfig.EndPoints.ACCEPT_BID)
    Observable<Response<AcceptBidResponse>> acceptBid(@Body BaseRequest.AcceptBid acceptBid);

}