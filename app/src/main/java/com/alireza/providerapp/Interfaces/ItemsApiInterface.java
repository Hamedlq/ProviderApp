package com.alireza.providerapp.Interfaces;

import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.Models.ResponseModel;
import com.alireza.providerapp.Models.SupplierItemsResponse;
import com.alireza.providerapp.Models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alireza on 3/31/18.
 */

public interface ItemsApiInterface {
    @FormUrlEncoded
    @POST("item/useritems")
    Call<List<SupplierItemsResponse>> getItemsFromServer(@Field("token") String authtoken);
}
