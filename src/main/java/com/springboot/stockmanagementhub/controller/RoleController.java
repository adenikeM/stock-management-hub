package com.springboot.stockmanagementhub.controller;

import com.springboot.stockmanagementhub.model.Role;
import com.springboot.stockmanagementhub.model.dto.ErrorResponse;
import com.springboot.stockmanagementhub.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.springboot.stockmanagementhub.model.dto.ErrorResponse.buildErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@Slf4j
@RequestMapping("api")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRole() {
        return ResponseEntity.ok().body(roleService.getAllRole());
    }

    @GetMapping("v2/roles")
    public ResponseEntity<List<Role>> getAllRole2(
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){

        Page<Role> roles = roleService.getAllRole2(pageNo,pageSize);
        return ResponseEntity.ok(roles.getContent());
    }

    @GetMapping("/v3/roles")
    public ResponseEntity<List<Role>> getAllRole3(Pageable pageable ){
        Page<Role> roles = roleService.getAllRole3(pageable);
        return ResponseEntity.ok(roles.getContent());
    }
    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRoleByID(@PathVariable Long id) {
        log.info("Get Role id by " + id);
        if (id < 1) {
            ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid Role ID",
                    "Role ID cannot be less than 1",
                    List.of("ID must be 1 or greater")
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return roleService.getRole(id)
                          .map(role -> ResponseEntity.ok().body(role))
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/roles")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        log.info("Request to create role => {}", role);
        if (role.getId() != null) {
            ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid Role Creation",
                    "ID should be null for new role creation",
                    List.of("Role ID must be null when creating a new role")
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        return ResponseEntity.ok().body(roleService.createRole(role));
    }

    @PutMapping("/roles")
    public ResponseEntity<?> updateRole(@RequestBody Role role) {
        if (role.getId() == null) {
            ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid Role Update",
                    "ID cannot be null for role update",
                    List.of("Role ID must be provided for update")
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Optional<Role> updatedRole = roleService.updateRole(role);
        if (updatedRole.isPresent()) {
            return ResponseEntity.ok(updatedRole);
        } else {
            ErrorResponse errorResponse = ErrorResponse.buildErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Role Not Found",
                    "Role with the provided ID does not exist",
                    List.of("Check if the Role ID is correct and exists")
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}

