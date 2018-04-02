package com.alireza.providerapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alireza.providerapp.Activities.ItemsActivity;
import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.Models.SupplierItemsModel;
import com.alireza.providerapp.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by alireza on 3/31/18.
 */

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.MyViewHolder> {

    private TextView itemNameTextview;
    private TextView itemBrandTextview;
    private TextView itemPriceTextview;
    private TextView itemDescriptionTextview;
    private TextView supplier_name_textview;
    private TextView supplier_phone_textview;
    private Button order_btn;

    private ItemsActivity.OrderTouchListener orderTouchListener;



    private long startClickTime;
    private static final int MAX_CLICK_DURATION = 200;

    private List<SupplierItemsModel> itemModels;

    public ItemsListAdapter(List<SupplierItemsModel> list) {
        this.itemModels = list;

    }

    public ItemsListAdapter(List<SupplierItemsModel> list,ItemsActivity.OrderTouchListener orderTouchListener) {
        this.itemModels = list;
        this.orderTouchListener=orderTouchListener;
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
            supplier_name_textview = view.findViewById(R.id.supplier_name_textview);
            supplier_phone_textview = view.findViewById(R.id.supplier_phone_textview);
            order_btn=view.findViewById(R.id.order_btn);
            order_btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            startClickTime = Calendar.getInstance().getTimeInMillis();
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                            if(clickDuration < MAX_CLICK_DURATION) {
                                orderTouchListener.onBtnClick(v, getPosition());
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    return true;
                }
            });
//            title = (TextView) view.findViewById(R.id.title);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);

        }
    }

    @Override
    public void onBindViewHolder(ItemsListAdapter.MyViewHolder holder, int position) {
//        testText.setText("wow this is a test with position number" + position);

        if (itemModels.size() > 0) {
            //String i = itemModels.get(position).getItemName();
//            boolean k = i.equals(null);
            if (itemModels.get(position).itemName != null)
                itemNameTextview.setText(itemModels.get(position).itemName);
            if (itemModels.get(position).itemPrice != null)
                itemPriceTextview.setText(itemModels.get(position).itemPrice);
            if (itemModels.get(position).itemBrand != null)
                itemBrandTextview.setText(itemModels.get(position).itemBrand);
            if (itemModels.get(position).itemDescription != null)
                itemDescriptionTextview.setText(itemModels.get(position).itemDescription);
            if (itemModels.get(position).name != null){
                String name=itemModels.get(position).name + " "+itemModels.get(position).family+"("+itemModels.get(position).shopname+")";
                supplier_name_textview.setText(name);
            }
            if (itemModels.get(position).mobile != null){
                String contact=itemModels.get(position).mobile+ " - "+itemModels.get(position).shopphone;
                supplier_phone_textview.setText(contact);
            }

        }


    }

    @Override
    public int getItemCount() {
        int i = itemModels.size();
//        return 1;
        return itemModels.size();
    }
}
