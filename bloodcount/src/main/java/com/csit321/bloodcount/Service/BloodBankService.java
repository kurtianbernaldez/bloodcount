        package com.csit321.bloodcount.Service;

        import com.csit321.bloodcount.Entity.BloodBankEntity;
        import com.csit321.bloodcount.Entity.UserEntity;
        import com.csit321.bloodcount.Repository.BloodBankRepository;
        import com.csit321.bloodcount.Repository.UserRepository;
        import com.csit321.bloodcount.UserType;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.NoSuchElementException;
        import java.util.Optional;
        import java.util.regex.Pattern;

        @Service
        public class BloodBankService {

            @Autowired
            private BloodBankRepository bloodBankRepository;

            @Autowired
            private UserRepository userRepository;

            @Autowired
            private PasswordEncoder passwordEncoder;

            public BloodBankEntity insertBloodBank(BloodBankEntity bloodBank) {
                try {
                    UserEntity user = userRepository.findById(bloodBank.getUser().getUserId())
                            .orElseThrow(() -> new NoSuchElementException("User with ID " + bloodBank.getUser().getUserId() + " not found."));

                    if (user.getDonor() != null || user.getHospital() != null) {
                        throw new IllegalStateException("User cannot be a blood bank if associated with a donor or hospital.");
                    }

                    if (!isValidContactInfo(bloodBank.getContactInfo())) {
                        throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
                    }

                    user.setUserType(UserType.BLOODBANK);
                    userRepository.save(user);
                    bloodBank.setUser(user);

                    BloodBankEntity savedBloodBank = bloodBankRepository.save(bloodBank);

                    // Set blood bank ID in the user entity
                    user.setBloodBank(savedBloodBank);
                    userRepository.save(user);

                    return savedBloodBank;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to insert blood bank.", e);
                }
            }

            public List<BloodBankEntity> getAllBloodBanks() {
                return bloodBankRepository.findAll();
            }

            public BloodBankEntity updateBloodBank(int bloodBankId, BloodBankEntity newBloodBankDetails) {
                BloodBankEntity bloodBank = bloodBankRepository.findById(bloodBankId)
                        .orElseThrow(() -> new NoSuchElementException("Blood bank with ID " + bloodBankId + " does not exist!"));

                if (!isValidContactInfo(newBloodBankDetails.getContactInfo())) {
                    // Handle validation error
                    throw new IllegalArgumentException("Invalid contact info. It should be 11 digits starting with 09.");
                }

                bloodBank.setBloodBankName(newBloodBankDetails.getBloodBankName());
                bloodBank.setLocation(newBloodBankDetails.getLocation());
                bloodBank.setContactInfo(newBloodBankDetails.getContactInfo());

                UserEntity existingUser = bloodBank.getUser();

                bloodBankRepository.save(bloodBank);

                return bloodBank;
            }

            public String deleteBloodBank(int bloodBankId) {
                Optional<BloodBankEntity> optionalBloodBank = bloodBankRepository.findById(bloodBankId);

                if (optionalBloodBank.isPresent()) {
                    BloodBankEntity bloodBank = optionalBloodBank.get();
                    UserEntity user = bloodBank.getUser();

                    if (user != null) {
                        user.setBloodBank(null); // Set the reference to null
                        user.setUserType(UserType.USER);
                        userRepository.save(user);
                    }

                    bloodBankRepository.deleteById(bloodBankId);
                    return "Blood Bank " + bloodBankId + " is successfully deleted!";
                } else {
                    return "Blood Bank " + bloodBankId + " does not exist.";
                }
            }

            private boolean isValidContactInfo(String contactInfo) {
                // Check if contactInfo is 11 digits starting with 09
                return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
            }
        }
