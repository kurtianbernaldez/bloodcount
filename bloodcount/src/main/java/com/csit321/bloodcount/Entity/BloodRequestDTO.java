package com.csit321.bloodcount.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class BloodRequestDTO {
    private int requestId;
    private String bloodType;
    private int quantity;
    private String requestDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = true)
    @JsonIgnoreProperties("bloodRequests")
    private UserEntity user;

    public BloodRequestDTO() {
    }

    public BloodRequestDTO(int requestId, String bloodType, int quantity, String requestDate, UserEntity user) {
        this.requestId = requestId;
        this.bloodType = bloodType;
        this.quantity = quantity;
        this.requestDate = requestDate;
        this.user = user;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
