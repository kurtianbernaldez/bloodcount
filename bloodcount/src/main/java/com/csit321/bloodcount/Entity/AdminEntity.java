package com.csit321.bloodcount.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import javax.persistence.*;

@Entity
@Table(name="tbladmin")
//@DiscriminatorValue("ADMIN")
public class AdminEntity { //extends UserEntity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "contact_information")
    private String contactInfo;

    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "admin"})
    private UserEntity user;


    public AdminEntity() {
    }

    public AdminEntity(int adminId, String contactInfo, String status, UserEntity user) {
        this.adminId = adminId;
        this.contactInfo = contactInfo;
        this.status = status;
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
