package com.store.store.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OBRA")
public class Construction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private int constructionRef;
    private String location;
    private Date startDate;
    private String status;
    private String projectManager;
    private BigDecimal budget;
    private String clientName;
    private String permitNumber;

    private double latitude;
    private double longitude;

    @OneToMany(mappedBy = "construction", cascade = CascadeType.ALL)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getConstructionRef() {
        return constructionRef;
    }

    public void setConstructionRef(int constructionRef) {
        this.constructionRef = constructionRef;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPermitNumber() {
        return permitNumber;
    }

    public void setPermitNumber(String permitNumber) {
        this.permitNumber = permitNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Set<ConstructionItem> getConstructionItems() {
        return constructionItems;
    }

    public void setConstructionItems(Set<ConstructionItem> constructionItems) {
        this.constructionItems = constructionItems;
    }
}
