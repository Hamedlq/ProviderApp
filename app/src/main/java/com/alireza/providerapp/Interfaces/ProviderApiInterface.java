package com.alireza.providerapp.Interfaces;

import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.Models.LoginResponseModel;
import com.alireza.providerapp.Models.ResponseModel;
import com.alireza.providerapp.Models.SupplierModel;
import com.alireza.providerapp.Models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alireza on 3/31/18.
 */

public interface ProviderApiInterface {

    @FormUrlEncoded
    @POST("user/getsuppliers")
    Call<List<UserModel>> getSuppliersList(@Field("token") String token);

    @FormUrlEncoded
    @POST("order/userOrders")
    Call<List<UserModel>> getOrdersList(@Field("token") String token);


    @FormUrlEncoded
    @POST("item/insertItem")
    Call<ResponseModel<String>> insertItem(@Field("token") String authtoken,
                                           @Field("itemName") String name,
                                           @Field("itemBrand") String brand,
                                           @Field("itemPrice") String price,
                                           @Field("itemDescription") String description);

    @FormUrlEncoded
    @POST("order/cancelOrder")
    Call<ResponseModel<String>> cancelOrder(@Field("token") String authtoken,
                                           @Field("item_id") String item_id);

}
