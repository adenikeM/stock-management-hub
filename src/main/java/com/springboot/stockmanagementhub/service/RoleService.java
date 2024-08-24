package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.Role;
import com.springboot.stockmanagementhub.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public Page<Role> getAllRole2(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return roleRepository.findAll(pageable);
    }
    public Page<Role> getAllRole3(Pageable pageable){
        return roleRepository.findAll(pageable);
    }
    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }

    public Role getRoleByTitle(String roleTitle){
        return roleRepository.findRoleByRoleTitle(roleTitle)
                             .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> updateRole(Role role) {
        roleRepository.findById(role.getId());
        if (role.getId() == null) {
            throw new IllegalArgumentException("Role id must not be null");
        }
        return Optional.of(roleRepository.save(role));
    }

    public void deleteRole(Long id) {
        roleRepository.findById(id);
    }

}
