package com.csit321.bloodcount.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "tblbloodbank")
public class BloodBankEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bloodbank_id")
    private int bloodBankId;

    @Column(name = "bloodbankname") // Change the name annotation here
    private String bloodBankName; // Change the field name here

    private String location;

    @Column(name = "contact_info")
    private String contactInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserEntity user;


    public BloodBankEntity() {
    }

    public BloodBankEntity(int bloodBankId, String bloodBankName, String location, String contactInfo, UserEntity user) {
        this.bloodBankId = bloodBankId;
        this.bloodBankName = bloodBankName;
        this.location = location;
        this.contactInfo = contactInfo;
        this.user = user;
    }

    public int getBloodBankId() {
        return bloodBankId;
    }

    public void setBloodBankId(int bloodBankId) {
        this.bloodBankId = bloodBankId;
    }

    public String getBloodBankName() {
        return bloodBankName;
    }

    public void setBloodBankName(String bloodBankName) {
        this.bloodBankName = bloodBankName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
