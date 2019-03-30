package com.quizorus.backend.repository;

import com.quizorus.backend.model.Role;
import com.quizorus.backend.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
    
}
