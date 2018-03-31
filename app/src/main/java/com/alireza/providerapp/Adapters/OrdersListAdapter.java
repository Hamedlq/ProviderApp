package com.alireza.providerapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.R;

import java.util.List;

/**
 * Created by alireza on 3/30/18.
 */

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {

    //    List<Model> modelList = new ArrayList<>();
    private TextView name_tv, price_tv;
    private TextView brand_tv;
    private TextView description_tv;

    private List<ItemModel> orderModels;

    public OrdersListAdapter(List<ItemModel> list) {
        this.orderModels = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_list_item, parent, false);

        return new MyViewHolder(itemView);
//        return null;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
            price_tv = view.findViewById(R.id.price_textview);
            name_tv= view.findViewById(R.id.name_textview);
            brand_tv= view.findViewById(R.id.brand_textview);
//            familyTextview = view.findViewById(R.id.family_textview);
            description_tv = view.findViewById(R.id.description_textview);
//            title = (TextView) view.findViewById(R.id.title);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }

    @Override
    public void onBindViewHolder(OrdersListAdapter.MyViewHolder holder, int position) {
//        testText.setText("wow this is a test with position number" + position);
        name_tv.setText(orderModels.get(position).getItemName());
        price_tv.setText(orderModels.get(position).getItemPrice());
        brand_tv.setText(orderModels.get(position).getItemBrand());
        description_tv.setText(orderModels.get(position).getItemDescription());
    }

    @Override
    public int getItemCount() {
        int i = orderModels.size();
        return orderModels.size();
    }
}
