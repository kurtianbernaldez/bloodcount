package com.csit321.bloodcount.Service;

import com.csit321.bloodcount.Entity.HospitalEntity;
import com.csit321.bloodcount.Entity.UserEntity;
import com.csit321.bloodcount.Repository.HospitalRepository;
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
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public HospitalEntity insertHospital(HospitalEntity hospital) {
        try {
            UserEntity user = userRepository.findById(hospital.getUser().getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User with ID " + hospital.getUser().getUserId() + " not found."));

            if (user.getDonor() != null || user.getBloodBank() != null) {
                throw new IllegalStateException("User cannot be a hospital if associated with a donor or blood bank.");
            }

            if (!isValidContactInfo(hospital.getContactInfo())) {
                throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
            }

            user.setUserType(UserType.HOSPITAL);
            userRepository.save(user);
            hospital.setUser(user);

            HospitalEntity savedHospital = hospitalRepository.save(hospital);

            // Set hospital ID in the user entity
            user.setHospital(savedHospital);
            userRepository.save(user);

            return savedHospital;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert hospital.", e);
        }
    }

    public List<HospitalEntity> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public HospitalEntity updateHospital(int hospitalId, HospitalEntity newHospitalDetails) {
        HospitalEntity hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new NoSuchElementException("Hospital " + hospitalId + " does not exist!"));

        if (!isValidContactInfo(hospital.getContactInfo())) {
            // Handle validation error
            throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
        }

        hospital.setHospitalName(newHospitalDetails.getHospitalName());
        hospital.setLocation(newHospitalDetails.getLocation());
        hospital.setContactInfo(newHospitalDetails.getContactInfo());

        UserEntity existingUser = hospital.getUser();

        hospitalRepository.save(hospital);
        return hospital;
    }
    public String deleteHospital(int hospitalId) {
        Optional<HospitalEntity> optionalHospital = hospitalRepository.findById(hospitalId);

        if (optionalHospital.isPresent()) {
            HospitalEntity hospital = optionalHospital.get();
            UserEntity user = hospital.getUser();

            if (user != null) {
                user.setHospital(null); // Set the reference to null
                user.setUserType(UserType.USER);
                userRepository.save(user);
            }

            hospitalRepository.deleteById(hospitalId);
            return "Hospital " + hospitalId + " is successfully deleted!";
        } else {
            return "Hospital " + hospitalId + " does not exist.";
        }
    }

    private boolean isValidContactInfo(String contactInfo) {
        // Check if contactInfo is 11 digits starting with 09
        return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
    }
}
