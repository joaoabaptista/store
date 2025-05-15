package com.store.store.dto;

import java.math.BigDecimal;

public class ItemDTO {
    private int id;
    private String name;
    private Double quantity;
    private Double price;
    private int itemRef;


    public ItemDTO(int id, String name, Double quantity, Double price, int itemRef) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.itemRef = itemRef;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getItemRef() {
        return itemRef;
    }

    public void setItemRef(int itemRef) {
        this.itemRef = itemRef;
    }
}