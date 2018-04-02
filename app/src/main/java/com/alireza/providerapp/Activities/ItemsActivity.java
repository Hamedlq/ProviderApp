package com.alireza.providerapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alireza.providerapp.Adapters.ItemsListAdapter;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.Interfaces.ItemsApiInterface;
import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.Models.ResponseModel;
import com.alireza.providerapp.Models.SupplierItemsModel;
import com.alireza.providerapp.Models.SupplierModel;
import com.alireza.providerapp.Models.UserModel;
import com.alireza.providerapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alireza on 3/31/18.
 */

public class ItemsActivity extends NavigationActivity {

    private RecyclerView itemsListRecyclerview;
    private List<SupplierItemsModel> itemModelList;
    private ItemsListAdapter adapter;
    private OrderTouchListener orderTouchListener;
    private String authToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_items);


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup parent = (ViewGroup) findViewById(R.id.main_container);
        inflater.inflate(R.layout.activity_items, parent);
        setToolbarTitle(getString(R.string.items_list));

        itemsListRecyclerview = findViewById(R.id.items_list);

        itemModelList = new ArrayList<>();

        adapter = new ItemsListAdapter(itemModelList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        itemsListRecyclerview.setLayoutManager(mLayoutManager);
//        providersList.setItemAnimator(new DefaultItemAnimator());
        itemsListRecyclerview.setAdapter(adapter);
        getItemsFromerver();
        orderTouchListener = new OrderTouchListener() {
            @Override
            public void onBtnClick(View view, int position) {
                SupplierItemsModel model = (SupplierItemsModel) itemModelList.get(position);
                orderTheItem(authToken, model);
            }
        };
    }

    private void orderTheItem(String authToken, SupplierItemsModel model) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        ItemsApiInterface itemsApiInterface =
                retrofit.create(ItemsApiInterface.class);

        Call<ResponseModel<String>> call =
                itemsApiInterface.orderItem(authToken, model.item_id);

        call.enqueue(new Callback<ResponseModel<String>>() {
            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                int code = response.code();
                if (code == 200) {
                    ResponseModel<String> i = response.body();

                    if(i.getError()!=null){
                        Toast.makeText(ItemsActivity.this, i.getError(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ItemsActivity.this, i.getMessage().toString(), Toast.LENGTH_LONG).show();
                        gotoOrderListActivity();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                Toast.makeText(ItemsActivity.this, "failure", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void gotoOrderListActivity() {
        Intent intent = new Intent(this, OrdersListActivity.class);
        finish();
        startActivity(intent);

    }

    private void getItemsFromerver() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        ItemsApiInterface itemsApiInterface =
                retrofit.create(ItemsApiInterface.class);

        SharedPreferences prefs = this.getSharedPreferences(
                Constants.GlobalConstants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        authToken = prefs.getString(Constants.GlobalConstants.TOKEN, "");

        Call<List<UserModel>> call =
                itemsApiInterface.getItemsFromServer(authToken);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                List<UserModel> model = response.body();
                for (SupplierModel supplierModel : model.get(0).suppliers) {
                    for (ItemModel item : supplierModel.items) {
                        SupplierItemsModel sitem=new SupplierItemsModel();
                        sitem.name=supplierModel.getName();
                        sitem.family=supplierModel.getFamily();
                        sitem.mobile=supplierModel.getMobile();
                        sitem.shopname=supplierModel.getShopname();
                        sitem.shopphone=supplierModel.getShopphone();
                        sitem.item_id=item.getItem_id();
                        sitem.itemBrand=item.getItemBrand();
                        sitem.itemName=item.getItemName();
                        sitem.itemPrice=item.getItemPrice();
                        sitem.itemDescription=item.getItemDescription();
                        itemModelList.add(sitem);
                    }
                }

                //itemModelList = model.get(0).getSuppliers().get(0).getItemModels();

//                itemModelList = i.getMessage();

                adapter.notifyDataSetChanged();


                adapter = new ItemsListAdapter(itemModelList,orderTouchListener);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                itemsListRecyclerview.setLayoutManager(mLayoutManager);
                itemsListRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(ItemsActivity.this, "failure", Toast.LENGTH_LONG).show();

            }
        });

    }

    public interface OrderTouchListener {
        public void onBtnClick(View view, int position);
    }
}
