package com.municipal.user.service;

import com.municipal.user.dto.LoginRequest;
import com.municipal.user.dto.RegisterRequest;
import com.municipal.user.model.Role;
import com.municipal.user.model.User;
import com.municipal.user.repository.UserRepository;
import com.municipal.user.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    public Optional<AuthResult> authenticate(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPasswordHash()))
                .map(u -> new AuthResult(u, jwtService.generateToken(u.getId(), u.getUsername(), u.getRole().name())));
    }

    public static class AuthResult {
        private final User user;
        private final String token;

        public AuthResult(User user, String token) {
            this.user = user;
            this.token = token;
        }

        public User getUser() { return user; }
        public String getToken() { return token; }
    }
}