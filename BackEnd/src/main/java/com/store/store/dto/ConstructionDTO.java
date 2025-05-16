package com.store.store.dto;

import com.store.store.model.Construction;

public class ConstructionDTO {
    private int id;
    private String name;
    private String location;
    private String status;

    public ConstructionDTO(Construction construction) {
        this.id = construction.getId();
        this.name = construction.getName();
        this.location = construction.getLocation();
        this.status = construction.getStatus();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
