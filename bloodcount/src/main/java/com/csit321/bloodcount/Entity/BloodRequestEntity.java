package com.csit321.bloodcount.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "tblbloodrequest")
public class BloodRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int requestId;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "request_date")
    private String requestDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = true)
    @JsonIgnoreProperties("bloodRequests")
    private UserEntity user;

    public BloodRequestEntity() {
    }

    public BloodRequestEntity(int requestId, String bloodType, int quantity, String requestDate, UserEntity user) {
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
