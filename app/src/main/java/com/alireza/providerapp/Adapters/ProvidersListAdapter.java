package com.alireza.providerapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alireza.providerapp.Models.SupplierModel;
import com.alireza.providerapp.R;

import java.util.List;

/**
 * Created by alireza on 3/30/18.
 */

public class ProvidersListAdapter extends RecyclerView.Adapter<ProvidersListAdapter.MyViewHolder> {

    //    List<Model> modelList = new ArrayList<>();
    private TextView testText, nameTexxtview;
    private TextView familyTextview;
    private TextView addressTextview;
    private TextView phone_textview;
    private TextView cell_phone_textview;

    private List<SupplierModel> supplierModels;

    public ProvidersListAdapter(List<SupplierModel> list) {
        this.supplierModels = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.providers_list_item, parent, false);

        return new MyViewHolder(itemView);
//        return null;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
            testText = view.findViewById(R.id.store_name);
            nameTexxtview = view.findViewById(R.id.name_textview);
//            familyTextview = view.findViewById(R.id.family_textview);
            addressTextview = view.findViewById(R.id.address_textview);
            phone_textview = view.findViewById(R.id.phone_textview);
            cell_phone_textview = view.findViewById(R.id.cell_phone_textview);
//            title = (TextView) view.findViewById(R.id.title);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }

    @Override
    public void onBindViewHolder(ProvidersListAdapter.MyViewHolder holder, int position) {
//        testText.setText("wow this is a test with position number" + position);
        testText.setText(supplierModels.get(position).getShopname());
        String name=supplierModels.get(position).getName()+" "+supplierModels.get(position).getFamily();
        nameTexxtview.setText(name);
//        familyTextview.setText(supplierModels.get(position).getFamily());
        addressTextview.setText(supplierModels.get(position).getAddress());
        phone_textview.setText(supplierModels.get(position).getShopphone());
        cell_phone_textview.setText(supplierModels.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        int i = supplierModels.size();
        return supplierModels.size();
    }
}
