package com.alireza.providerapp.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alireza.providerapp.Adapters.OrdersListAdapter;
import com.alireza.providerapp.Adapters.ProvidersListAdapter;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.Interfaces.ProviderApiInterface;
import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.Models.SupplierModel;
import com.alireza.providerapp.Models.UserModel;
import com.alireza.providerapp.R;
import com.google.gson.Gson;

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

public class OrdersListActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private List<ItemModel> orderModelList;
    private List<UserModel> userOrderModelList;
    private OrdersListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        ordersList = findViewById(R.id.orders_list);
        orderModelList = new ArrayList<>();
        adapter = new OrdersListAdapter(orderModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ordersList.setLayoutManager(mLayoutManager);

        ordersList.setAdapter(adapter);
        getOrderListFromServer();
    }

    private void getOrderListFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        ProviderApiInterface providerApiInterface =
                retrofit.create(ProviderApiInterface.class);

        SharedPreferences prefs =
                getSharedPreferences(Constants.GlobalConstants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
        String token = prefs.getString(Constants.GlobalConstants.TOKEN,"null");


        Call<List<UserModel>> call =
                providerApiInterface.getOrdersList(token);


        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                int code = response.code();
                if (code == 200){
                    Toast.makeText(OrdersListActivity.this, "success", Toast.LENGTH_LONG).show();
                    userOrderModelList = response.body();
                    //Gson gson = new Gson();
                    for (ItemModel itemModel : userOrderModelList.get(0).items) {
                        //ItemModel theModel = gson.fromJson(itemModel, ItemModel.class);
                        orderModelList.add(itemModel);
                    }

                    adapter.notifyDataSetChanged();

                    adapter = new OrdersListAdapter(orderModelList );
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    ordersList.setLayoutManager(mLayoutManager);
                    ordersList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(OrdersListActivity.this, "failure", Toast.LENGTH_LONG).show();

            }
        });

    }
}
