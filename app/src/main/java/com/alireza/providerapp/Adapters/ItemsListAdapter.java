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
 * Created by alireza on 3/31/18.
 */

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.MyViewHolder> {

    private TextView itemNameTextview;
    private TextView itemBrandTextview;
    private TextView itemPriceTextview;
    private TextView itemDescriptionTextview;

    private List<ItemModel> itemModels;

    public ItemsListAdapter(List<ItemModel> list) {
        this.itemModels = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_list_cell, parent, false);

        return new MyViewHolder(itemView);
//        return null;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            itemNameTextview = view.findViewById(R.id.item_name_textview);
            itemBrandTextview = view.findViewById(R.id.item_brand_textview);
            itemPriceTextview = view.findViewById(R.id.item_price_textview);
            itemDescriptionTextview = view.findViewById(R.id.item_description_textview);
//            title = (TextView) view.findViewById(R.id.title);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }

    @Override
    public void onBindViewHolder(ItemsListAdapter.MyViewHolder holder, int position) {
//        testText.setText("wow this is a test with position number" + position);

        if (itemModels.size() > 0) {
            String i = itemModels.get(position).getItemName();
//            boolean k = i.equals(null);
            if (itemModels.get(position).getItemName() != null)
                itemNameTextview.setText(itemModels.get(position).getItemName());
            if (itemModels.get(position).getItemPrice() != null)
                itemPriceTextview.setText(itemModels.get(position).getItemPrice());
            if (itemModels.get(position).getItemBrand() != null)
                itemBrandTextview.setText(itemModels.get(position).getItemBrand());
            if (itemModels.get(position).getItemDescription() != null)
                itemDescriptionTextview.setText(itemModels.get(position).getItemDescription());
        }


    }

    @Override
    public int getItemCount() {
        int i = itemModels.size();
//        return 1;
        return itemModels.size();
    }
}
