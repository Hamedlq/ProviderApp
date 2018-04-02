package com.alireza.providerapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alireza.providerapp.Activities.OrdersListActivity;
import com.alireza.providerapp.Models.ItemModel;
import com.alireza.providerapp.Models.SupplierItemsModel;
import com.alireza.providerapp.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by alireza on 3/30/18.
 */

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {

    //    List<Model> modelList = new ArrayList<>();
    private TextView name_tv, price_tv;
    private TextView brand_tv;
    private TextView description_tv;
    private TextView supplier_name_textview;
    private TextView supplier_phone_textview;
    private Button cancel_btn;

    private List<SupplierItemsModel> orderModels;

    private OrdersListActivity.CancelOrderTouchListener cancelOrderTouchListener;

    private long startClickTime;
    private static final int MAX_CLICK_DURATION = 200;


    public OrdersListAdapter(List<SupplierItemsModel> list) {
        this.orderModels = list;
    }


    public OrdersListAdapter(List<SupplierItemsModel> list,OrdersListActivity.CancelOrderTouchListener cancelOrderTouchListener) {
        this.orderModels = list;
        this.cancelOrderTouchListener=cancelOrderTouchListener;
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
            price_tv = view.findViewById(R.id.item_price_textview);
            name_tv= view.findViewById(R.id.item_name_textview);
            brand_tv= view.findViewById(R.id.item_brand_textview);
            description_tv = view.findViewById(R.id.item_description_textview);
            supplier_name_textview = view.findViewById(R.id.supplier_name_textview);
            supplier_phone_textview = view.findViewById(R.id.supplier_phone_textview);
            cancel_btn=view.findViewById(R.id.cancel_btn);
            cancel_btn.setOnTouchListener(new View.OnTouchListener() {
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
                                cancelOrderTouchListener.onBtnClick(v, getPosition());
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    return true;
                }
            });

        }
    }

    @Override
    public void onBindViewHolder(OrdersListAdapter.MyViewHolder holder, int position) {
//        testText.setText("wow this is a test with position number" + position);
        name_tv.setText(orderModels.get(position).itemName);
        price_tv.setText(orderModels.get(position).itemPrice);
        brand_tv.setText(orderModels.get(position).itemBrand);
        description_tv.setText(orderModels.get(position).itemDescription);
        String name=orderModels.get(position).name + " "+orderModels.get(position).family+"("+orderModels.get(position).shopname+")";
        supplier_name_textview.setText(name);
        String contact=orderModels.get(position).mobile+ " - "+orderModels.get(position).shopphone;
        supplier_phone_textview.setText(contact);
    }

    @Override
    public int getItemCount() {
        int i = orderModels.size();
        return orderModels.size();
    }
}
