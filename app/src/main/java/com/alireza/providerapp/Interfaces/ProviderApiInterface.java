package com.alireza.providerapp.Interfaces;

import com.alireza.providerapp.Models.LoginResponseModel;
import com.alireza.providerapp.Models.SupplierModel;

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
    Call<List<SupplierModel>> getSuppliersList(@Field("token") String token);

}
