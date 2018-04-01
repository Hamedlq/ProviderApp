package com.alireza.providerapp.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alireza.providerapp.Adapters.ProvidersListAdapter;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.Interfaces.LoginApiInterface;
import com.alireza.providerapp.Interfaces.ProviderApiInterface;
import com.alireza.providerapp.Models.LoginResponseModel;
import com.alireza.providerapp.Models.SupplierModel;
import com.alireza.providerapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alireza on 3/30/18.
 */

public class ProvidersListActivity extends NavigationActivity {
    private RecyclerView providersList;
    private List<SupplierModel> supplierModelList;
    private ProvidersListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_providers_list);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup parent = (ViewGroup)findViewById(R.id.main_container);
        inflater.inflate(R.layout.activity_providers_list, parent);

        providersList = findViewById(R.id.providers_list);

        supplierModelList = new ArrayList<>();

        adapter = new ProvidersListAdapter(supplierModelList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        providersList.setLayoutManager(mLayoutManager);
//        providersList.setItemAnimator(new DefaultItemAnimator());
        providersList.setAdapter(adapter);
        getSuppliersListFromServer();
    }

    private void getSuppliersListFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        ProviderApiInterface providerApiInterface =
                retrofit.create(ProviderApiInterface.class);

        SharedPreferences prefs =
                getSharedPreferences(Constants.GlobalConstants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
        String token = prefs.getString(Constants.GlobalConstants.TOKEN,"null");


        Call<List<SupplierModel>> call =
                providerApiInterface.getSuppliersList(token);


        call.enqueue(new Callback<List<SupplierModel>>() {
            @Override
            public void onResponse(Call<List<SupplierModel>> call, Response<List<SupplierModel>> response) {
                int code = response.code();
                if (code == 200){
                    Toast.makeText(ProvidersListActivity.this, "success", Toast.LENGTH_LONG).show();
                    supplierModelList = response.body();

                    adapter.notifyDataSetChanged();


                    adapter = new ProvidersListAdapter(supplierModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    providersList.setLayoutManager(mLayoutManager);
//        providersList.setItemAnimator(new DefaultItemAnimator());
                    providersList.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<SupplierModel>> call, Throwable t) {
                Toast.makeText(ProvidersListActivity.this, "failure", Toast.LENGTH_LONG).show();

            }
        });

    }
}
