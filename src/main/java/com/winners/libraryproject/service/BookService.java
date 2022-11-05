package com.winners.libraryproject.service;


import com.winners.libraryproject.dto.BookDTO;
import com.winners.libraryproject.entity.Book;
import java.util.List;

public interface BookService {
    public List<BookDTO> findAllBooks();

    public List<BookDTO> searchBooks(String keyword);

    public Book findBookById(Long id);

    public void createBook(Book book);

    public void deleteBook(Long id);

    public void updateBook(Book book,Long id);
}
