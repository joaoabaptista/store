package com.store.store.dto;

import java.math.BigDecimal;

public class ConstructionItemDTO {

    private String name;
    private Double quantity;
    private Double totalPrice;
    private int itemRef;

    public ConstructionItemDTO(int itemRef, String name, Double quantity, Double totalPrice ) {

        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.itemRef = itemRef;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return totalPrice;
    }

    public void setPrice(Double price) {
        this.totalPrice = totalPrice;
    }

    public int getItemRef() {
        return itemRef;
    }

    public void setItemRef(int itemRef) {
        this.itemRef = itemRef;
    }
}
