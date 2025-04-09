package com.javier.backend.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<RoleResponse> roles;
} 