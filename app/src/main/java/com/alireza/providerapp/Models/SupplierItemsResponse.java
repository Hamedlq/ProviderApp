package com.alireza.providerapp.Models;

import java.util.List;

/**
 * Created by alireza on 4/1/18.
 */

public class SupplierItemsResponse {

    private List<Supplier> suppliers;

    public List<Supplier> getSuppliers() { return this.suppliers; }

    public void setSuppliers(List<Supplier> suppliers) { this.suppliers = suppliers; }
}
