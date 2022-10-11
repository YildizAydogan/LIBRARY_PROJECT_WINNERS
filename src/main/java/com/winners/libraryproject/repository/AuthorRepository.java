package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
