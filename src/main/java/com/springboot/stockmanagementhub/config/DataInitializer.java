package com.springboot.stockmanagementhub.config;

import com.springboot.stockmanagementhub.model.Role;
import com.springboot.stockmanagementhub.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleService roleService;

    @PostConstruct
    public void init() {
        createRoleIfNotExists("MANAGER");
        createRoleIfNotExists("CUSTOMER");
        createRoleIfNotExists("SALES ATTENDANT");
    }

    private void createRoleIfNotExists(String roleTitle) {
        try {
            roleService.getRoleByTitle(roleTitle);
        } catch (IllegalArgumentException e) {
            Role newRole = new Role();
            newRole.setRoleTitle(roleTitle);
            roleService.createRole(newRole);
        }
    }
}