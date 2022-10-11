package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
