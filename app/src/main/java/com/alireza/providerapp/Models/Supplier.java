package com.alireza.providerapp.Models;

import java.util.List;

/**
 * Created by alireza on 4/1/18.
 */

public class Supplier {

    private List<ItemModel> items;

    public List<ItemModel> getItemModels() { return this.items; }

    public void setItemModels(List<ItemModel> items) { this.items = items; }

    private String mobile;

    public String getMobile() { return this.mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }

}
