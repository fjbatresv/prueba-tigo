package com.javier.backend.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleUpdateRequest {
    @Size(min = 3, max = 20)
    private String name;
} 