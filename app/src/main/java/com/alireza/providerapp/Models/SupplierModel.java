package com.alireza.providerapp.Models;

import java.util.Date;

/**
 * Created by alireza on 3/31/18.
 */

public class SupplierModel {

    private String refercode;
    private String introducecode;
    String mobile;
    Date createTime;
    int smscount;
    String name;
    String family;
    String address;
    String propertytype;
    String shopname;
    String shopphone;
    String shoplat;
    String shoplng;
    int __v;
//    SupplierModel suppliers;


    public String getRefercode() {
        return refercode;
    }

    public void setRefercode(String refercode) {
        this.refercode = refercode;
    }

    public String getIntroducecode() {
        return introducecode;
    }

    public void setIntroducecode(String introducecode) {
        this.introducecode = introducecode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getSmscount() {
        return smscount;
    }

    public void setSmscount(int smscount) {
        this.smscount = smscount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPropertytype() {
        return propertytype;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopphone() {
        return shopphone;
    }

    public void setShopphone(String shopphone) {
        this.shopphone = shopphone;
    }

    public String getShoplat() {
        return shoplat;
    }

    public void setShoplat(String shoplat) {
        this.shoplat = shoplat;
    }

    public String getShoplng() {
        return shoplng;
    }

    public void setShoplng(String shoplng) {
        this.shoplng = shoplng;
    }

//    public SupplierModel getSuppliers() {
//        return suppliers;
//    }
//
//    public void setSuppliers(SupplierModel suppliers) {
//        this.suppliers = suppliers;
//    }
}
