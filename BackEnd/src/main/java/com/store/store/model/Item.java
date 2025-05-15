package com.store.store.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String location;
    private String description;
    private String category;
    private Double quantity;
    private Double price;
    private Double minimumStock;
    private int itemRef;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<ConstructionItem> constructionItems = new HashSet<>();


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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Double getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(Double minimumStock) {
        this.minimumStock = minimumStock;
    }

    public int getItemRef() {
        return itemRef;
    }

    public void setItemRef(int itemRef) {
        this.itemRef = itemRef;
    }

    public Set<ConstructionItem> getConstructionItems() {
        return constructionItems;
    }

    public void setConstructionItems(Set<ConstructionItem> constructionItems) {
        this.constructionItems = constructionItems;
    }
}