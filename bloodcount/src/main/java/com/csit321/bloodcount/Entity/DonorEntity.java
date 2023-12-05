package com.csit321.bloodcount.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "tblblooddonor")
public class DonorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private int donorId;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "contact_information")
    private String contactInfo;

    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserEntity user;


    public DonorEntity() {
    }

    public DonorEntity(int donorId, String bloodType, String contactInfo, String location, UserEntity user) {
        this.donorId = donorId;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.location = location;
        this.user = user;
    }
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
