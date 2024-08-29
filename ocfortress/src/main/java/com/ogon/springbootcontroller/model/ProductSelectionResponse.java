package com.ogon.springbootcontroller.model;

import java.util.List;

public class ProductSelectionResponse {

    private List<ProductSelection> productListItems;

    public List<ProductSelection> getProductListItems() {
        return productListItems;
    }

    public void setProductListItems(List<ProductSelection> productListItems) {
        this.productListItems = productListItems;
    }
}

