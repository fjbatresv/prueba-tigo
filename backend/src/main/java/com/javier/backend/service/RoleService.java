package com.javier.backend.service;

import com.javier.backend.dto.RoleCreateRequest;
import com.javier.backend.dto.RoleResponse;
import com.javier.backend.dto.RoleUpdateRequest;
import com.javier.backend.model.Role;
import com.javier.backend.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::mapToRoleResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(this::mapToRoleResponse)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
    }

    @Transactional
    public RoleResponse createRole(RoleCreateRequest request) {
        if (roleRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Role name is already taken!");
        }

        Role role = new Role();
        role.setName(request.getName());
        return mapToRoleResponse(roleRepository.save(role));
    }

    @Transactional
    public RoleResponse updateRole(Long id, RoleUpdateRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        if (request.getName() != null && !request.getName().equals(role.getName())) {
            if (roleRepository.findByName(request.getName()).isPresent()) {
                throw new IllegalArgumentException("Role name is already taken!");
            }
            role.setName(request.getName());
        }

        return mapToRoleResponse(roleRepository.save(role));
    }

    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

    private RoleResponse mapToRoleResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        return response;
    }
} 