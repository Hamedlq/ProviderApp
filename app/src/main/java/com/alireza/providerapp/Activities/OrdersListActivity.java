package com.alireza.providerapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alireza.providerapp.Adapters.OrdersListAdapter;
import com.alireza.providerapp.Adapters.ProvidersListAdapter;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.Interfaces.ProviderApiInterface;
import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.Models.ResponseModel;
import com.alireza.providerapp.Models.SupplierItemsModel;
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

public class OrdersListActivity extends NavigationActivity {
    private RecyclerView ordersList;
    private List<SupplierItemsModel> orderModelList;
    private List<UserModel> userOrderModelList;
    private OrdersListAdapter adapter;
    private String authToken;
    private CancelOrderTouchListener cancelOrderTouchListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_orders_list);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup parent = (ViewGroup)findViewById(R.id.main_container);
        inflater.inflate(R.layout.activity_orders_list, parent);
        setToolbarTitle(getString(R.string.orders_list));

        ordersList = findViewById(R.id.orders_list);
        orderModelList = new ArrayList<>();
        adapter = new OrdersListAdapter(orderModelList,cancelOrderTouchListener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ordersList.setLayoutManager(mLayoutManager);

        ordersList.setAdapter(adapter);
        getOrderListFromServer();

        cancelOrderTouchListener = new CancelOrderTouchListener() {
            @Override
            public void onBtnClick(View view, int position) {
                SupplierItemsModel model = (SupplierItemsModel) orderModelList.get(position);
                cancelOrder(authToken, model);
            }
        };
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
        authToken = prefs.getString(Constants.GlobalConstants.TOKEN,"null");


        Call<List<UserModel>> call =
                providerApiInterface.getOrdersList(authToken);


        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                int code = response.code();
                if (code == 200){
                    userOrderModelList = response.body();
                    for (ItemModel item : userOrderModelList.get(0).items) {
                        SupplierItemsModel sitem=new SupplierItemsModel();
                        sitem.name=item.getSupplier_id().getName();
                        sitem.family=item.getSupplier_id().getFamily();
                        sitem.mobile=item.getSupplier_id().getMobile();
                        sitem.shopname=item.getSupplier_id().getShopname();
                        sitem.shopphone=item.getSupplier_id().getShopphone();
                        sitem.item_id=item.getItem_id();
                        sitem.itemBrand=item.getItemBrand();
                        sitem.itemName=item.getItemName();
                        sitem.itemPrice=item.getItemPrice();
                        sitem.itemDescription=item.getItemDescription();
                        orderModelList.add(sitem);
                    }

                    adapter.notifyDataSetChanged();

                    adapter = new OrdersListAdapter(orderModelList,cancelOrderTouchListener );
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

    private void cancelOrder(String authToken, SupplierItemsModel model) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        ProviderApiInterface providerApiInterface =
                retrofit.create(ProviderApiInterface.class);


        Call<ResponseModel<String>> call =
                providerApiInterface.cancelOrder(authToken,model.item_id);

        call.enqueue(new Callback<ResponseModel<String>>() {
            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                int code = response.code();
                if (code == 200) {
                    ResponseModel<String> i = response.body();

                    if(i.getError()!=null){
                        Toast.makeText(OrdersListActivity.this, i.getError(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(OrdersListActivity.this, i.getMessage().toString(), Toast.LENGTH_LONG).show();
                        gotoOrderListActivity();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                Toast.makeText(OrdersListActivity.this, "failure", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void gotoOrderListActivity() {
        Intent intent = new Intent(this, OrdersListActivity.class);
        finish();
        startActivity(intent);

    }


    public interface CancelOrderTouchListener {
        public void onBtnClick(View view, int position);
    }
}
