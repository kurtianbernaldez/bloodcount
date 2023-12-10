package com.csit321.bloodcount.Service;

import com.csit321.bloodcount.Entity.BloodRequestDTO;
import com.csit321.bloodcount.Entity.BloodRequestEntity;
import com.csit321.bloodcount.Entity.UserEntity;
import com.csit321.bloodcount.Repository.BloodRequestRepository;
import com.csit321.bloodcount.Repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BloodRequestService {

    @Autowired
    BloodRequestRepository bloodRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public BloodRequestEntity insertBloodRequest(BloodRequestEntity bloodRequest) {
        try {
            UserEntity user = userRepository.findById(bloodRequest.getUser().getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User with ID " + bloodRequest.getUser().getUserId() + " not found."));

            // Set bloodRequest to null in case it has an ID (for new insertions)
            bloodRequest.setRequestId(0);
            bloodRequest.setUser(user);

            BloodRequestEntity savedBloodRequest = bloodRequestRepository.save(bloodRequest);

            List<BloodRequestEntity> userBloodRequests = user.getBloodRequests();
            if (userBloodRequests == null) {
                userBloodRequests = new ArrayList<>();
            }
            userBloodRequests.add(savedBloodRequest);

            // Save the user after modifying the blood requests list
            user.setBloodRequests(userBloodRequests);
            userRepository.save(user);

            return savedBloodRequest;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert blood request.", e);
        }
    }

    public List<BloodRequestDTO> getAllBloodRequests() {
        List<BloodRequestEntity> bloodRequests = bloodRequestRepository.findAll();

        // Convert entities to DTOs
        List<BloodRequestDTO> bloodRequestDTOs = bloodRequests.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return bloodRequestDTOs;
    }

    private BloodRequestDTO convertToDTO(BloodRequestEntity bloodRequest) {
        return new BloodRequestDTO(
                bloodRequest.getRequestId(),
                bloodRequest.getBloodType(),
                bloodRequest.getQuantity(),
                bloodRequest.getRequestDate(),
                bloodRequest.getUser()
                // Set other fields as needed
        );
    }

    @Transactional
    public BloodRequestEntity updateBloodRequest(int bloodRequestId, BloodRequestEntity newBloodRequestDetails) {
        try {
            BloodRequestEntity bloodRequest = bloodRequestRepository.findById(bloodRequestId)
                    .orElseThrow(() -> new NoSuchElementException("Blood Request with ID " + bloodRequestId + " does not exist!"));

            // Update the fields with new values
            bloodRequest.setBloodType(newBloodRequestDetails.getBloodType());
            bloodRequest.setQuantity(newBloodRequestDetails.getQuantity());
            bloodRequest.setRequestDate(newBloodRequestDetails.getRequestDate());

            // Save the updated blood request
            return bloodRequestRepository.save(bloodRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update blood request.", e);
        }
    }

    public String deleteBloodRequest(int bloodRequestId) {
        try {
            Optional<BloodRequestEntity> optionalBloodRequest = bloodRequestRepository.findById(bloodRequestId);

            if (optionalBloodRequest.isPresent()) {
                BloodRequestEntity bloodRequest = optionalBloodRequest.get();
                UserEntity user = bloodRequest.getUser();

                if (user != null) {
                    List<BloodRequestEntity> userBloodRequests = user.getBloodRequests();
                    if (userBloodRequests != null) {
                        userBloodRequests.remove(bloodRequest);
                        user.setBloodRequests(userBloodRequests);
                    }

                    // Save the user without modifying other properties
                    userRepository.save(user);
                }

                // Delete the blood request
                bloodRequestRepository.deleteById(bloodRequestId);

                return "Blood Request " + bloodRequestId + " is successfully deleted!";
            } else {
                return "Blood Request " + bloodRequestId + " does not exist.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete blood request.", e);
        }
    }


    private boolean isValidContactInfo(String contactInfo) {
        // Check if contactInfo is 11 digits starting with 09
        return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
    }
}
