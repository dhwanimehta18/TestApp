package com.library.api;

import com.google.gson.Gson;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.ConnectException;

@Singleton
public class ApiManager {

    private final API mApi;
    private final NetworkUtils mNetworkUtils = General.getInstance().getAppComponent().provideNetworkUtils();
    private final Gson mGson = General.getInstance().getAppComponent().provideGson();

    private final Observable.Transformer<Object, Object> mSchedulersTransformer = observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    @Inject
    ApiManager(API api) {
        //do not instantiate directly. use di instead.
        mApi = api;
    }

    //region internal methods

    /**
     * creates observable on the top of given api observable.
     * it will manage most things about api like network checking and notifying errors.
     * <p>
     * If exception is instance of
     * <ul>
     * <li>{@link ConnectException} then error will be due to network issues.</li>
     * <li>{@link retrofit2.HttpException} then error will be from server.</li>
     * similar to when {@link Response#isSuccessful()} returns false in retrofit.
     * You can use {@link HttpException#code()} to get the http status code for error.
     * <li>For other exceptions, it will be similar to {@link okhttp3.Callback#onFailure} in retrofit.</li>
     * </ul>
     *
     * @param apiObservable observable for apis.
     * @param <T>           response type
     * @return observable on api
     */
    private <T> Observable<T> call(Observable<T> apiObservable) {
        return apiObservable
                //startWith will emit observable inside it before another emissions from source observable
                //defer will create fresh observable only when observer subscribes it
                .startWith(Observable.defer(() -> {
                    //before calling each api, network connection is checked.
                    if (!mNetworkUtils.isConnected()) {
                        //if network is not available, it will return error observable with ConnectException.
                        return Observable.error(new ConnectException("Device is not connected to network"));
                    } else {
                        //if it is available, it will return empty observable. Empty observable just emits onCompleted() immediately
                        return Observable.empty();
                    }
                })).flatMap((Func1<T, Observable<T>>) t -> {
                    if (t instanceof Response) {
                        Response response = (Response) t;
                        if (response.code() != ApiConfig.ResponseCodes.SUCCESS) {
                            try {
                                if (response.code() == ApiConfig.ResponseCodes.AUTH_TOKEN_ERROR) {
                                    return Observable.error(new HttpException(response));
                                } else {
                                    if (response.errorBody() != null) {
                                        BaseObjectResponse baseObjectResponse = mGson.fromJson(response.errorBody().string(), BaseObjectResponse.class);
                                        return Observable.error(new CustomApiError(response.code(), baseObjectResponse.getMessage()));
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            return Observable.just(t);
                        }
                    }
                    return Observable.just(t);
                })
                .doOnNext(response -> {
                    //logging response on success
                    //you can change to to something else
                    //for example, if all your apis returns error codes in success, then you can throw custom exception here
                    if (Consts.IS_DEBUGGABLE) {
                        Timber.e("Response :\n %s", response);
                    }
                })
                .doOnError(throwable -> {
                    //printing stack trace on error
                    if (Consts.IS_DEBUGGABLE) {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * returns a transformer with applied schedulers to api observable
     * use it with {@link Observable#compose}
     * <p>
     * ref. : http://blog.danlew.net/2015/03/02/dont-break-the-chain/
     * <p>
     * this will subscribe observable on {@link Schedulers#io()}
     * and observe on {@link AndroidSchedulers#mainThread()}
     *
     * @param <T> response type
     * @return transformer that applies schedulers on observable
     * <p>
     */
    @SuppressWarnings("unchecked")
    public <T> Observable.Transformer<T, T> applySchedulers() {
        //type casting is necessary to reuse our transformer instance
        //see 'Reusing Transformers' in reference given for more info
        return (Observable.Transformer<T, T>) mSchedulersTransformer;
    }
    //endregion

    //region all api requests

    /**
     * Get country api
     *
     * @return observable response of api
     */
    public Observable<Response<MultipleSelectionResponse>> callGetCountryList() {
        Observable<Response<MultipleSelectionResponse>> apiObservable = mApi.getCountry();
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get state api
     *
     * @param countryId Country Id
     * @return observable response of api
     */
    public Observable<Response<MultipleSelectionResponse>> callGetStateList(String countryId) {
        Observable<Response<MultipleSelectionResponse>> apiObservable = mApi.getState(countryId);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get city api
     *
     * @param stateId OutgoingMediaState Id
     * @return observable response of api
     */
    public Observable<Response<MultipleSelectionResponse>> callGetCityList(String stateId) {
        Observable<Response<MultipleSelectionResponse>> apiObservable = mApi.getCity(stateId);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get Nationality api
     *
     * @return observable response of api
     */
    public Observable<Response<MultipleSelectionResponse>> callGetNationalityList() {
        Observable<Response<MultipleSelectionResponse>> apiObservable = mApi.getNationality();
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get Category api
     *
     * @return observable response of api
     */
    public Observable<Response<MultipleSelectionResponse>> callGetCategory() {
        Observable<Response<MultipleSelectionResponse>> apiObservable = mApi.getCategory();
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get skills api
     *
     * @param searchString search string
     * @return observable response of api
     */
    public Observable<Response<MultipleSelectionResponse>> callGetSkills(String searchString) {
        Observable<Response<MultipleSelectionResponse>> apiObservable = mApi.getSkills(searchString);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Call sign up api
     */
    public Observable<Response<UserResponse>> callSignUp(
            RequestBody mobileNumber, RequestBody gender, RequestBody email, RequestBody password,
            RequestBody nickName, RequestBody fullName, RequestBody stateId, RequestBody cityId, RequestBody countryId,
            RequestBody birthDate, RequestBody deviceType, RequestBody pushToken, RequestBody request,
            RequestBody nationalities, MultipartBody.Part[] attachment) {
        Observable<Response<UserResponse>> apiObservable = mApi.signUp(
                mobileNumber, gender, email, password, nickName, fullName, stateId, cityId, countryId, birthDate, deviceType, pushToken,
                request, nationalities, attachment);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Call post order api
     */
    public Observable<Response<PostOrderResponse>> callPostOrder(
            RequestBody address, RequestBody lat, RequestBody lng, RequestBody radius,
            RequestBody jobAmount, RequestBody description, RequestBody orderSkills, RequestBody jobDate, RequestBody orderRecomendedTech,
            MultipartBody.Part[] attachment) {
        Observable<Response<PostOrderResponse>> apiObservable = mApi.postOrder(
                address, lat, lng, radius, jobAmount, description, jobDate, orderSkills, orderRecomendedTech, attachment);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Login api
     *
     * @param loginRequest login request
     * @return observable response of api
     */
    public Observable<Response<UserResponse>> callLogin(BaseRequest.LoginRequest loginRequest) {
        Observable<Response<UserResponse>> apiObservable = mApi.login(loginRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Add/Remove Favourite api
     *
     * @param addRemoveFavouriteRequest request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callAddRemoveFavourite(BaseRequest.AddRemoveFavouriteRequest addRemoveFavouriteRequest) {
        Observable<Response<BaseResponse>> apiObservable = mApi.addRemoveFavorite(addRemoveFavouriteRequest);
        return call(apiObservable).compose(applySchedulers());
    }


    /**
     * Forgot Password api
     *
     * @param forgotPasswordRequest Forgot Password request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callForgotPassword(BaseRequest.ForgotPasswordRequest forgotPasswordRequest) {
        Observable<Response<BaseResponse>> apiObservable = mApi.forgotPassword(forgotPasswordRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Add Bank Details Api
     *
     * @param mAddBankDetailsRequest Add Bank Details request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callAddBankRequest(BaseRequest.AddBankDetailsRequest mAddBankDetailsRequest) {
        Observable<Response<BaseResponse>> apiObservable = mApi.addBankDetails(mAddBankDetailsRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Update Bank Details Api
     *
     * @param mUpdateBankDetailsRequest Update Bank Details request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callUpdateBankRequest(BaseRequest.UpdateBankDetailsRequest mUpdateBankDetailsRequest) {
        Observable<Response<BaseResponse>> apiObservable = mApi.updateBankDetails(mUpdateBankDetailsRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Update Location api
     *
     * @param updateLocationRequest Update Location request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callUpdateLocation(BaseRequest.UpdateLocationRequest updateLocationRequest) {
        Observable<Response<BaseResponse>> apiObservable = mApi.updateLocation(updateLocationRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Change Password api
     *
     * @param changePasswordRequest Change Password request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callChangePassword(BaseRequest.ChangePasswordRequest changePasswordRequest) {
        Observable<Response<BaseResponse>> apiObservable = mApi.changePassword(changePasswordRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Change Password api
     *
     * @param addReviewRequest Change Password request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callAddReview(BaseRequest.AddReviewRequest addReviewRequest) {
        Observable<Response<BaseResponse>> apiObservable = mApi.addReview(addReviewRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Change Availability api
     *
     * @param changeAvailabilityRequest Change Availability request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callChangeAvailability(BaseRequest.ChangeAvailabilityRequest changeAvailabilityRequest) {
        Observable<Response<BaseResponse>> apiObservable = mApi.changeAvailability(changeAvailabilityRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get user details api
     *
     * @param profileId Profile Id request
     * @return observable response of api
     */
    public Observable<Response<ProfileUserResponse>> callGetUserDetails(String profileId) {
        Observable<Response<ProfileUserResponse>> apiObservable = mApi.getUserDetails(profileId);
        return call(apiObservable).compose(applySchedulers());
    }


    /**
     * Call edit profile api
     */
    public Observable<Response<UserResponse>> callEditProfile(String loginUserId, RequestBody mobileNumber, RequestBody gender,
                                                              RequestBody nickName, RequestBody fullName, RequestBody stateId,
                                                              RequestBody cityId, RequestBody countryId, RequestBody categoryId,
                                                              RequestBody birthDate, RequestBody deviceType, RequestBody isPictureRemoved,
                                                              RequestBody about, RequestBody nationalities, RequestBody skills,
                                                              MultipartBody.Part profilePicture) {
        Observable<Response<UserResponse>> apiObservable = mApi.editProfile(loginUserId, mobileNumber, gender, nickName, fullName, stateId,
                cityId, countryId, categoryId, birthDate, deviceType, isPictureRemoved, about, nationalities, skills, profilePicture);
        return call(apiObservable).compose(applySchedulers());
    }


    public Observable<Response<RequestStatusResponse>> callGetStatusOfRequest() {
        Observable<Response<RequestStatusResponse>> apiObservable = mApi.getStatusOfRequest();
        return call(apiObservable).compose(applySchedulers());
    }

    public Observable<Response<BankAccountResponse>> callGetStatusOfBanking() {
        Observable<Response<BankAccountResponse>> apiObservable = mApi.getStatusOfBanking();
        return call(apiObservable).compose(applySchedulers());
    }

    public Observable<Response<RequestStatusResponse>> callRequest() {
        Observable<Response<RequestStatusResponse>> apiObservable = mApi.requestAsProvider();
        return call(apiObservable).compose(applySchedulers());
    }

    public Observable<Response<UploadDocResponse>> callGetUploadedDoc() {
        Observable<Response<UploadDocResponse>> apiObservable = mApi.getUploadedDoc();
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Delete uploaded document of provider
     *
     * @param deleteDocument Delete document request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> deleteUploadedDoc(BaseRequest.DeleteDocument deleteDocument) {
        Observable<Response<BaseResponse>> apiObservable = mApi.deleteDoc(deleteDocument);
        return call(apiObservable).compose(applySchedulers());
    }

    public Observable<Response<UploadDocResponse>> uploadDoc(MultipartBody.Part[] attachment) {
        Observable<Response<UploadDocResponse>> apiObservable = mApi.uploadDoc(attachment);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get Home category type
     *
     * @return observable response of api
     */
    public Observable<Response<CategoryTypeResponse>> callGetHomeCategoryType() {
        Observable<Response<CategoryTypeResponse>> apiObservable = mApi.getHomeCategoryType();
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Filter
     *
     * @param filterRequest Filter Request
     * @return observable response of api
     */
    public Observable<Response<FilterResponse>> callFilter(BaseRequest.FilterRequest filterRequest) {
        Observable<Response<FilterResponse>> apiObservable = mApi.filter(filterRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Filter
     *
     * @param categoryRequest Filter Request
     * @return observable response of api
     */
    public Observable<Response<FilterResponse>> callFilterCategory(BaseRequest.CategoryRequest categoryRequest) {
        Observable<Response<FilterResponse>> apiObservable = mApi.filterCategory(categoryRequest);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Filter
     *
     * @param favouriteCategory Filter Request
     * @return observable response of api
     */

    public Observable<Response<FilterResponse>> callFavouriteCategory(BaseRequest.FavouriteCategory favouriteCategory) {
        Observable<Response<FilterResponse>> apiObservable = mApi.getFavouriteCategory(favouriteCategory);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * @param mReportForUser Report For User Request
     * @return observable response of api
     */
    public Observable<Response<ReportForUserResponse>> callReportForUser(BaseRequest.ReportForUser mReportForUser) {
        Observable<Response<ReportForUserResponse>> apiObservable = mApi.getReportForUser(mReportForUser);
        return call(apiObservable).compose(applySchedulers());
    }


    /**
     * @param mReportForOrder Report For Order Request
     * @return observable response of api
     */
    public Observable<Response<ReportForOrderResponse>> callReportForOrder(BaseRequest.ReportForOrder mReportForOrder) {
        Observable<Response<ReportForOrderResponse>> apiObservable = mApi.getReportForOrder(mReportForOrder);
        return call(apiObservable).compose(applySchedulers());
    }


    /**
     * Get profile album list
     *
     * @param skip   offset of data load
     * @param limit  data to display in single request
     * @param userId userId
     * @return observable response of api
     */
    public Observable<Response<ProfileAlbumListResponse>> callProfileAlbum(String skip, String limit, String userId) {
        Observable<Response<ProfileAlbumListResponse>> apiObservable = mApi.getProfileAlbum(skip, limit, userId);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Order list
     *
     * @param order_type Type of order eg..current,upcoming,done,cancel in 1,2,3,4
     * @param skip       offset of data load
     * @param limit      data to display in single request
     * @return observable response of api
     */
    public Observable<Response<OrderListResponse>> callOrderList(String order_type, String skip, String limit) {
        Observable<Response<OrderListResponse>> apiObservable = mApi.getOrderList(order_type, skip, limit);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Notification list
     *
     * @param skip  offset of data load
     * @param limit data to display in single request
     * @return observable response of api
     */
    public Observable<Response<NotificationListResponse>> callNotificationList(String skip, String limit) {
        Observable<Response<NotificationListResponse>> apiObservable = mApi.getNotificationList(skip, limit);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Review list
     *
     * @param userId offset of data load
     * @param skip   offset of data load
     * @param limit  data to display in single request
     * @return observable response of api
     */
    public Observable<Response<ProfileReviewListResponse>> callReviewList(String userId, String skip, String limit) {
        Observable<Response<ProfileReviewListResponse>> apiObservable = mApi.getReviewList(userId, skip, limit);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Like Dislike
     *
     * @param like    isLiked
     * @param userId  userId
     * @param orderId orderId
     * @return observable response of api
     */
    public Observable<Response<LikeDislikeBaseResponse>> callLikeDislikeOrderPost(int like, String userId, String orderId) {
        Observable<Response<LikeDislikeBaseResponse>> apiObservable = mApi.getLikeDislikeOrderPost(like, userId, orderId);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Home Feed list
     *
     * @param skip  offset of data load
     * @param limit data to display in single request
     * @return observable response of api
     */
    public Observable<Response<HomeAlbumListResponse>> callHomeFeedList(String limit, String skip) {
        Observable<Response<HomeAlbumListResponse>> apiObservable = mApi.getHomeFeedList(limit, skip);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Order list
     *
     * @param order_id order id
     * @return observable response of api
     */
    public Observable<Response<OrderDetailResponse>> callOrderDetail(String order_id) {
        Observable<Response<OrderDetailResponse>> apiObservable = mApi.getOrderDetail(order_id);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Upload requester image of job.
     *
     * @param orderId    order id
     * @param attachment attachment request
     * @return observable response of api
     */
    public Observable<Response<OrderImageResponse>> uploadOrderImage(RequestBody orderId, MultipartBody.Part[] attachment) {
        Observable<Response<OrderImageResponse>> apiObservable = mApi.uploadOrderImage(orderId, attachment);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Delete uploaded image of specific order which is provider image or reqeuster image.
     *
     * @param deleteDocument Delete document request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> deleteOrderImage(BaseRequest.DeleteOrderImage deleteDocument) {
        Observable<Response<BaseResponse>> apiObservable = mApi.deleteOrderImage(deleteDocument);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Provider bid amount of job
     *
     * @param bidAmount job amount
     * @return observable response of api
     */
    public Observable<Response<PostBidResponse>> bidAmount(BaseRequest.BidAmount bidAmount) {
        Observable<Response<PostBidResponse>> apiObservable = mApi.bidJobAmount(bidAmount);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Provider bid amount of job
     *
     * @param orderBidId Order Bid Id
     * @param bidAmount  Update bid amount
     * @return observable response of api
     */
    public Observable<Response<PostBidResponse>> updateBidAmount(String orderBidId, BaseRequest.UpdateBidAmount bidAmount) {
        Observable<Response<PostBidResponse>> apiObservable = mApi.updateBidAmount(orderBidId, bidAmount);
        return call(apiObservable).compose(applySchedulers());
    }

    public Observable<Response<AcceptBidResponse>> bidAccept(BaseRequest.AcceptBid acceptBid) {
        Observable<Response<AcceptBidResponse>> apiObservable = mApi.acceptBid(acceptBid);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get list of  provider who has accept order and make bid
     *
     * @param order_id order id
     * @return observable response of api
     */
    public Observable<Response<OrderProviderResponse>> getOrderProviderList(String order_id) {
        Observable<Response<OrderProviderResponse>> apiObservable = mApi.getOrderProviderList(order_id);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Call chat upload Attachment api
     *
     * @return observable response of api
     */
    public Observable<Response<UploadMediaResponse>> callUploadChatMedia(RequestBody fileType, RequestBody localFileName, RequestBody audioDuration, MultipartBody.Part attachment) {
        Observable<Response<UploadMediaResponse>> apiObservable = mApi.uploadChatMedia(fileType, localFileName, audioDuration, attachment);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Accept or Reject order
     *
     * @param acceptRejectOrder accept/reject order request
     * @return observable response of api
     */
    public Observable<Response<BaseResponse>> callAcceptRejectOrder(BaseRequest.AcceptRejectOrder acceptRejectOrder) {
        Observable<Response<BaseResponse>> apiObservable = mApi.acceptRejectOrder(acceptRejectOrder);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Update order status
     *
     * @param updateOrderStatus update order status request
     * @return observable response of api
     */
    public Observable<Response<OrderDetailResponse>> callUpdateOrderStatus(BaseRequest.UpdateOrderStatus updateOrderStatus) {
        Observable<Response<OrderDetailResponse>> apiObservable = mApi.updateOrderStatus(updateOrderStatus);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get feed details
     *
     * @param orderId order id request
     * @return observable response of api
     */
    public Observable<Response<FeedDetailsResponse>> callGetFeedDetails(String orderId) {
        Observable<Response<FeedDetailsResponse>> apiObservable = mApi.getFeedDetails(orderId);
        return call(apiObservable).compose(applySchedulers());
    }

    /**
     * Get Comments
     *
     * @param orderId order id request
     * @return observable response of api
     */
    public Observable<Response<GetCommentsResponse>> callGetComments(String orderId) {
        Observable<Response<GetCommentsResponse>> apiObservable = mApi.getComments(orderId);
        return call(apiObservable).compose(applySchedulers());
    }


    /**
     * Add Comment
     *
     * @param addComment add comment request
     * @return observable response of api
     */
    public Observable<Response<AddCommentResponse>> callAddComment(BaseRequest.AddComment addComment) {
        Observable<Response<AddCommentResponse>> apiObservable = mApi.addComment(addComment);
        return call(apiObservable).compose(applySchedulers());
    }

    //endregion
}
