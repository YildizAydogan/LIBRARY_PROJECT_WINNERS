package com.winners.libraryproject.service.impl;

import com.winners.libraryproject.dto.BookDTO;
import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
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

    //-----------------FIND ALL BOOKS----------------------------------
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BookDTO> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    //-----------------SEARCH BOOKS--------------------------------------

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BookDTO> searchBooks(String keyword) {
        if (keyword != null) {
            return bookRepository.search(keyword);
        }
        return bookRepository.findAllBooks();
    }

    //-----------------FIND BOOK BY ID---------------------------
    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MSG, id)));
    }


    //-----------------------CREATE BOOK-------------------------
    @Override
    public void createBook(Book book) throws BadRequestException {
        //TODO: Image file can add

        book.setBuiltIn(false);
        bookRepository.save(book);
    }



    //-----------------------UPDATE BOOK-------------------------
    @Override
    public void updateBook(Book book, Long id) {
        book.setId(id);
        Book existBook = bookRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MSG,id)));
        if(existBook.getBuiltIn()){
            throw new BadRequestException("You don't have permission to update this book with id : "+id);
        }
        book.setBuiltIn(false);
        bookRepository.save(book);
    }

    //-----------------------DELETE BOOK-------------------------
    @Override
    public void deleteBook(Long id) throws ResourceNotFoundException{
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MSG, id)));
        if(book.getBuiltIn()){
            throw new BadRequestException("You don't have permission to delete this book with id : "+id);
        }
        bookRepository.deleteById(book.getId());
    }


}
