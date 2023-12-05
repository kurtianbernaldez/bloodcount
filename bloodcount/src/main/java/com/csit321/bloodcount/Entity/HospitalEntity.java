package com.csit321.bloodcount.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "tblhospital")
public class HospitalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private int hospitalId;

    @Column(name = "hospital_name")
    private String hospitalName;

    private String location;

    @Column(name = "contact_info")
    private String contactInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserEntity user;


    // Constructors, getters, setters...

    public HospitalEntity() {
    }

    public HospitalEntity(int hospitalId, String hospitalName, String location, String contactInfo, UserEntity user) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.location = location;
        this.contactInfo = contactInfo;
        this.user = user;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
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
