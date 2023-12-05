    package com.csit321.bloodcount.Entity;

    import com.csit321.bloodcount.UserType;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import org.apache.catalina.User;

    import javax.persistence.*;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Table(name = "tbluser")
    public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private int userId;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        private String gender;
        private String email;

        @Column(name = "password")
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(name = "user_type")
        private UserType userType = UserType.USER;

        @Column(name = "is_deleted")
        private boolean isDeleted;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        @JsonIgnoreProperties({"user"})
        private DonorEntity donor;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        @JsonIgnoreProperties({"user"})
        private HospitalEntity hospital;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        @JsonIgnoreProperties({"user"})
        private BloodBankEntity bloodBank;

        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
        @JsonIgnoreProperties({"user"})
        private AdminEntity admin;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        @JsonIgnoreProperties({"user"})
        private List<BloodRequestEntity> bloodRequests;

        public UserEntity() {
        }

        public UserEntity(int userId, String firstName, String lastName, String gender, String email, String password) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.email = email;
            this.password = password;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public DonorEntity getDonor() {
            return donor;
        }

        public void setDonor(DonorEntity donor) {
            this.donor = donor;
        }

        public HospitalEntity getHospital() {
            return hospital;
        }

        public void setHospital(HospitalEntity hospital) {
            this.hospital = hospital;
        }

        public BloodBankEntity getBloodBank() {
            return bloodBank;
        }

        public void setBloodBank(BloodBankEntity bloodBank) {
            this.bloodBank = bloodBank;
        }

        public AdminEntity getAdmin() {
            return admin;
        }

        public void setAdmin(AdminEntity admin) {
            this.admin = admin;
        }
        

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public UserType getUserType() {
            return userType;
        }

        public void setUserType(UserType userType) {
            this.userType = userType;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public List<BloodRequestEntity> getBloodRequests() {
            return bloodRequests;
        }

        public void setBloodRequests(List<BloodRequestEntity> bloodRequests) {
            this.bloodRequests = bloodRequests;

        }

    }
