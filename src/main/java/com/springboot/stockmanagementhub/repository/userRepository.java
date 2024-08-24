package com.springboot.stockmanagementhub.repository;

import com.springboot.stockmanagementhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<User, Long> {
}
