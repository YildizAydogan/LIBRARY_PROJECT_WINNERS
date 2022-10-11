package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
