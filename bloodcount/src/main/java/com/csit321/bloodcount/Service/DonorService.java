package com.csit321.bloodcount.Service;

import com.csit321.bloodcount.Entity.DonorEntity;
import com.csit321.bloodcount.Entity.UserEntity;
import com.csit321.bloodcount.Repository.DonorRepository;
import com.csit321.bloodcount.Repository.UserRepository;
import com.csit321.bloodcount.UserType;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DonorEntity insertDonor(DonorEntity donor) {
        try {
            UserEntity user = userRepository.findById(donor.getUser().getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User with ID " + donor.getUser().getUserId() + " not found."));

            if (user.getHospital() != null || user.getBloodBank() != null) {
                throw new IllegalStateException("User cannot be a donor if associated with a hospital or blood bank.");
            }

            if (!isValidContactInfo(donor.getContactInfo())) {
                throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
            }

            user.setUserType(UserType.DONOR);
            userRepository.save(user);
            donor.setUser(user);

            DonorEntity savedDonor = donorRepository.save(donor);

            user.setDonor(savedDonor);
            userRepository.save(user);

            return savedDonor;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert donor.", e);
        }
    }


    public List<DonorEntity> getAllDonors() {
        return donorRepository.findAll();
    }

    public DonorEntity updateDonor(int donorId, DonorEntity newDonorDetails) {
        DonorEntity donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new NoSuchElementException("Donor " + donorId + " does not exist!"));
        if (!isValidContactInfo(donor.getContactInfo())) {
            // Handle validation error
            throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
        }

        donor.setBloodType(newDonorDetails.getBloodType());
        donor.setContactInfo(newDonorDetails.getContactInfo());
        donor.setLocation(newDonorDetails.getLocation());

        UserEntity existingUser = donor.getUser();

        donorRepository.save(donor);
        return donor;
    }


    public String deleteDonor(int donorId) {
        Optional<DonorEntity> optionalDonor = donorRepository.findById(donorId);

        if (optionalDonor.isPresent()) {
            DonorEntity donor = optionalDonor.get();
            UserEntity user = donor.getUser();

            if (user != null) {
                user.setDonor(null); // Set the reference to null
                user.setUserType(UserType.USER);
                userRepository.save(user);
            }

            donorRepository.deleteById(donorId);
            return "Donor " + donorId + " is successfully deleted!";
        } else {
            return "Donor " + donorId + " does not exist.";
        }
    }
    private boolean isValidContactInfo(String contactInfo) {
        // Check if contactInfo is 11 digits starting with 09
        return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
    }
}
