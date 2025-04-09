package com.javier.backend.service;

import com.javier.backend.dto.RoleCreateRequest;
import com.javier.backend.dto.RoleResponse;
import com.javier.backend.dto.RoleUpdateRequest;
import com.javier.backend.model.Role;
import com.javier.backend.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;
    private RoleCreateRequest createRequest;
    private RoleUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        createRequest = new RoleCreateRequest();
        createRequest.setName("ROLE_NEW");

        updateRequest = new RoleUpdateRequest();
        updateRequest.setName("ROLE_UPDATED");
    }

    @Test
    void getAllRoles_ShouldReturnListOfRoles() {
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));

        var result = roleService.getAllRoles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(role.getId(), result.get(0).getId());
        assertEquals(role.getName(), result.get(0).getName());
        verify(roleRepository).findAll();
    }

    @Test
    void getRoleById_ShouldReturnRole() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        RoleResponse result = roleService.getRoleById(1L);

        assertNotNull(result);
        assertEquals(role.getId(), result.getId());
        assertEquals(role.getName(), result.getName());
        verify(roleRepository).findById(1L);
    }

    @Test
    void getRoleById_ShouldThrowException_WhenRoleNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.getRoleById(1L));
        verify(roleRepository).findById(1L);
    }

    @Test
    void createRole_ShouldCreateNewRole() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        RoleResponse result = roleService.createRole(createRequest);

        assertNotNull(result);
        assertEquals(role.getId(), result.getId());
        assertEquals(role.getName(), result.getName());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void createRole_ShouldThrowException_WhenNameExists() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));

        assertThrows(IllegalArgumentException.class, () -> roleService.createRole(createRequest));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void updateRole_ShouldUpdateRole() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        RoleResponse result = roleService.updateRole(1L, updateRequest);

        assertNotNull(result);
        assertEquals(role.getId(), result.getId());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void updateRole_ShouldThrowException_WhenRoleNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.updateRole(1L, updateRequest));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void deleteRole_ShouldDeleteRole() {
        when(roleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(roleRepository).deleteById(1L);

        assertDoesNotThrow(() -> roleService.deleteRole(1L));
        verify(roleRepository).deleteById(1L);
    }

    @Test
    void deleteRole_ShouldThrowException_WhenRoleNotFound() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> roleService.deleteRole(1L));
        verify(roleRepository, never()).deleteById(anyLong());
    }
} 