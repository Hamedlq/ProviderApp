package com.alireza.providerapp.Models;

import java.util.Date;
import java.util.List;

/**
 * Created by alireza on 3/31/18.
 */

public class UserModel {

    String refercode;
    String mobile;
    Date createTime;
    int smscount;
    public String name;
    public String family;
    String address;
    String propertytype;
    public String shopname;
    String shopphone;
    String shoplat;
    String shoplng;
    public List<SupplierModel> suppliers;
    public List<ItemModel> items;
}
