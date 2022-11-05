package com.winners.libraryproject.repository;

import com.winners.libraryproject.dto.BookDTO;
import com.winners.libraryproject.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.name LIKE %?1%"
            + " OR b.isbn LIKE %?1%"
            + " OR b.authorName LIKE %?1%"
            + " OR b.publisherName LIKE %?1%"
            +" OR b.categoryName LIKE %?1%")
    List<BookDTO> search(String keyword);

    //Get ALL Books
    @Query("SELECT new com.winners.libraryproject.dto.BookDTO(b) FROM Book b")
    List<BookDTO> findAllBooks();
}
