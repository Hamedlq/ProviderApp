package com.alireza.providerapp.Interfaces;

import com.alireza.providerapp.Models.LoginResponseModel;
import com.alireza.providerapp.Models.ReceiveTokenModel;
import com.alireza.providerapp.Models.ResponseModel;
import com.alireza.providerapp.Models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alireza on 3/26/18.
 */

public interface UserApiInterface {

    @FormUrlEncoded
    @POST("user/sendUserInfo")
    Call<ResponseModel<UserModel>> sendUserInfoToServer(@Field("token") String authtoken,
                                                        @Field("name") String name,
                                                        @Field("family") String family,
                                                        @Field("address") String address,
                                                        @Field("shopname") String shopname,
                                                        @Field("shopphone") String shopphone,
                                                        @Field("propertytype") String propertytype);

}
