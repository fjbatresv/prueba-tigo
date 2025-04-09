package com.javier.backend.repository;

import com.javier.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
    Page<User> findByEmailContaining(String email, Pageable pageable);
    Page<User> findByUsernameContainingAndEmailContaining(String username, String email, Pageable pageable);
} 