package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.Role;
import com.winners.libraryproject.entity.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(UserRole name);
}
