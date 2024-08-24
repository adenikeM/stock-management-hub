package com.springboot.stockmanagementhub.repository;

import com.springboot.stockmanagementhub.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
