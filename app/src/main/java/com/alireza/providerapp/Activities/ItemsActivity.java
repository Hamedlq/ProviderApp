package com.alireza.providerapp.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alireza.providerapp.Adapters.ItemsListAdapter;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.Interfaces.ItemsApiInterface;
import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.Models.ResponseModel;
import com.alireza.providerapp.Models.Supplier;
import com.alireza.providerapp.Models.SupplierItemsResponse;
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
    private List<ItemModel> itemModelList;
    private ItemsListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_items);


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup parent = (ViewGroup)findViewById(R.id.main_container);
        inflater.inflate(R.layout.activity_items, parent);


        itemsListRecyclerview = findViewById(R.id.items_list);

        itemModelList = new ArrayList<>();

        adapter = new ItemsListAdapter(itemModelList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        itemsListRecyclerview.setLayoutManager(mLayoutManager);
//        providersList.setItemAnimator(new DefaultItemAnimator());


        itemsListRecyclerview.setAdapter(adapter);

        getItemsFromerver();
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
        String authToken = prefs.getString(Constants.GlobalConstants.TOKEN, "");

        Call<List<UserModel>> call =
                itemsApiInterface.getItemsFromServer(authToken);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                List<UserModel> model = response.body();
                for (SupplierModel supplierModel : model.get(0).suppliers) {
                    for (ItemModel item: supplierModel.items){
                        itemModelList.add(item);
                    }
                }

                //itemModelList = model.get(0).getSuppliers().get(0).getItemModels();

//                itemModelList = i.getMessage();

                adapter.notifyDataSetChanged();


                adapter = new ItemsListAdapter(itemModelList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                itemsListRecyclerview.setLayoutManager(mLayoutManager);
                itemsListRecyclerview.setAdapter(adapter);

                Toast.makeText(ItemsActivity.this, R.string.success, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(ItemsActivity.this, "failure", Toast.LENGTH_LONG).show();

            }
        });



        /*Call<ResponseModel<List<ItemModel>>> call =
                itemsApiInterface.getItemsFromServer(authToken);

        call.enqueue(new Callback<ResponseModel<List<ItemModel>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<ItemModel>>> call, Response<ResponseModel<List<ItemModel>>> response) {
                Toast.makeText(ItemsActivity.this, "success", Toast.LENGTH_LONG).show();
                int code = response.code();
                if (code == 200) {
                    ResponseModel<List<ItemModel>> i = response.body();
                    itemModelList = i.getMessage();

                    adapter.notifyDataSetChanged();


                    adapter = new ItemsListAdapter(itemModelList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    itemsListRecyclerview.setLayoutManager(mLayoutManager);
                    itemsListRecyclerview.setAdapter(adapter);

                    Toast.makeText(ItemsActivity.this, R.string.success, Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onFailure(Call<ResponseModel<List<ItemModel>>> call, Throwable t) {
                Toast.makeText(ItemsActivity.this, "failure", Toast.LENGTH_LONG).show();

            }
        });*/


    }
}
