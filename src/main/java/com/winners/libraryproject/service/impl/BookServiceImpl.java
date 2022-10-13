package com.winners.libraryproject.service.impl;

import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.exception.ResourceNotFoundException;
import com.winners.libraryproject.repository.BookRepository;
import com.winners.libraryproject.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private final static String BOOK_NOT_FOUND_MSG = "book with id %d not found";

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Book> searchBooks(String keyword) {
        if (keyword != null) {
            return bookRepository.search(keyword);
        }
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MSG, id)));
    }

    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MSG, id)));
        bookRepository.deleteById(book.getId());
    }
}
