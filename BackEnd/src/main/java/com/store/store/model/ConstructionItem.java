package com.store.store.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "construction_item", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"construction_id", "item_id"})
})

public class ConstructionItem {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "construction_id")
    private Construction construction;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Double quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Construction getConstruction() {
        return construction;
    }

    public void setConstruction(Construction construction) {
        this.construction = construction;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstructionItem)) return false;
        ConstructionItem that = (ConstructionItem) o;
        return Objects.equals(construction.getId(), that.construction.getId()) &&
                Objects.equals(item.getId(), that.item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(construction.getId(), item.getId());
    }
}
