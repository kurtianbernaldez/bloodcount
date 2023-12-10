    package com.csit321.bloodcount.Service;

    import com.csit321.bloodcount.Entity.UserEntity;
    import com.csit321.bloodcount.Repository.UserRepository;
    import com.csit321.bloodcount.UserType;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.web.bind.annotation.CrossOrigin;

    import java.util.List;
    import java.util.NoSuchElementException;

    @CrossOrigin(origins = "http://localhost:3000")
    @Service
    public class UserService {

        @Autowired
        private UserRepository userRepository;
        private final BCryptPasswordEncoder passwordEncoder;

        public UserService(BCryptPasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        public UserEntity insertUser(UserEntity user) {
            // Password validation
            if (!isValidPassword(user.getPassword())) {
                throw new IllegalArgumentException("Invalid password format. It must be at least 8 characters with 1 uppercase letter.");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }

        public List<UserEntity> getAllUsers() {
            return userRepository.findAll();
        }

        public UserEntity updateUser(int userId, UserEntity newUserDetails) {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User " + userId + " does not exist!"));

            user.setFirstName(newUserDetails.getFirstName());
            user.setLastName(newUserDetails.getLastName());
            user.setGender(newUserDetails.getGender());
            user.setEmail(newUserDetails.getEmail());
            user.setUserType(newUserDetails.getUserType());

            // Save the changes using userRepository.save
            return userRepository.saveAndFlush(user);
        }


        public String deleteUser(int userId) {
            UserEntity user = userRepository.findById(userId)
                    .orElse(null);

            if (user != null) {
                // Mark the user as deleted
                user.setDeleted(true);
                userRepository.save(user);
                return "User " + userId + " is marked as deleted.";
            } else {
                return "User " + userId + " does not exist.";
            }
        }

        public boolean isValidPassword(String password) {
            // Password should be at least 8 characters with 1 uppercase letter
            return password.matches("^(?=.*[A-Z]).{8,}$");
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
