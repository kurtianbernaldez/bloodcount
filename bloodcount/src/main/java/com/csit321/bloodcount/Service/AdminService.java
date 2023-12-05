package com.csit321.bloodcount.Service;

import com.csit321.bloodcount.Entity.*;
import com.csit321.bloodcount.Repository.AdminRepository;
import com.csit321.bloodcount.Repository.DonorRepository;
import com.csit321.bloodcount.Repository.UserRepository;
import com.csit321.bloodcount.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@SuppressWarnings("ReturnInsideFinallyBlock")
@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminEntity insertAdmin(AdminEntity admin){
        try {
            UserEntity user = userRepository.findById(admin.getUser().getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User with ID " + admin.getUser().getUserId() + " not found."));


            if (!isValidContactInfo(admin.getContactInfo())) {
                throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
            }

            user.setUserType(UserType.ADMIN);
            userRepository.save(user);
            admin.setUser(user);

            AdminEntity savedAdmin = adminRepository.save(admin);

            // Set hospital ID in the user entity
            user.setAdmin(savedAdmin);
            userRepository.save(user);

            return savedAdmin;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert hospital.", e);
        }
    }

    public List<AdminEntity> getAllAdmins() { return adminRepository.findAll();}

    public AdminEntity updateAdmin(int adminId, AdminEntity newAdminDetails){
        AdminEntity admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new NoSuchElementException("Admin with ID " + adminId + " does not exist!"));

        if (!isValidContactInfo(newAdminDetails.getContactInfo())) {
            // Handle validation error
            throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
        }

        admin.setAdminId(newAdminDetails.getAdminId());
        admin.setContactInfo(newAdminDetails.getContactInfo());
        admin.setStatus(newAdminDetails.getStatus());

        UserEntity existingUser = admin.getUser();

        adminRepository.save(admin);

        return admin;
    }

    public String deleteAdmin(int adminId) {
        Optional<AdminEntity> optionalAdmin = adminRepository.findById(adminId);

        if (optionalAdmin.isPresent()) {
            AdminEntity admin = optionalAdmin.get();
            UserEntity user = admin.getUser();

            if (user != null) {
                user.setAdmin(null); // Set the reference to null
                user.setUserType(UserType.USER);
                userRepository.save(user);
            }

            adminRepository.deleteById(adminId);
            return "Admin " + adminId + " is successfully deleted!";
        } else {
            return "Admin " + adminId + " does not exist.";
        }
    }
    private boolean isValidContactInfo(String contactInfo) {
        // Check if contactInfo is 11 digits starting with 09
        return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
    }
}
