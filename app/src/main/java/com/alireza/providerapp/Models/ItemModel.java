package com.alireza.providerapp.Models;

import java.util.Date;

/**
 * Created by alireza on 3/31/18.
 */

public class ItemModel {

    private String item_id;
    private String itemName;
    private String itemBrand;
    private String itemPrice;
    private String itemDescription;
    private SupplierModel supplier_id;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public SupplierModel getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(SupplierModel supplier_id) {
        this.supplier_id = supplier_id;
    }
}
