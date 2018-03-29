package com.alireza.providerapp.Interfaces;

import com.alireza.providerapp.Models.LoginResponseModel;
import com.alireza.providerapp.Models.ReceiveTokenModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alireza on 3/26/18.
 */

public interface LoginApiInterface {

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponseModel> sendUserPhoneNumber(@Field("mobile") String mobile,
                                                 @Field("refercode") int referCode);

    @FormUrlEncoded
    @POST("user/sendConfirmCode")
    Call<LoginResponseModel> sendConfirmCode(@Field("mobile") String mobile,
                                             @Field("counter") int counter);

    @FormUrlEncoded
    @POST("user/confirmSmsCode")
    Call<ReceiveTokenModel> sendConfirmCodeBackToServer(@Field("mobile") String mobile,
                                                        @Field("vCode") int verificationnCode);
}
