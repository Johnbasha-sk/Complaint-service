package com.municipality.userservice.repository;

import com.municipality.userservice.entity.Role;
import com.municipality.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndIsActive(String username, Boolean isActive);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByIsActiveOrderByCreatedDateDesc(Boolean isActive);
}