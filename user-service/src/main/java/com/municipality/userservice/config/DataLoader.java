package com.municipality.userservice.config;

import com.municipality.userservice.entity.Role;
import com.municipality.userservice.entity.User;
import com.municipality.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create default users if they don't exist
        createDefaultUserIfNotExists("citizen", "citizen@municipality.com", "password", Role.CITIZEN);
        createDefaultUserIfNotExists("staff", "staff@municipality.com", "password", Role.STAFF);
        createDefaultUserIfNotExists("admin", "admin@municipality.com", "password", Role.ADMIN);
    }

    private void createDefaultUserIfNotExists(String username, String email, String password, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userRepository.save(user);
            System.out.println("Created default user: " + username + " with role: " + role);
        }
    }
}