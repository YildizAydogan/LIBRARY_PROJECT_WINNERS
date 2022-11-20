package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.Book.BookDTO;
import com.winners.libraryproject.dto.Book.BookUpdateDTO;
import com.winners.libraryproject.entity.Author;
import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.entity.Category;
import com.winners.libraryproject.entity.Publisher;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.payload.messages.ErrorMessage;
import com.winners.libraryproject.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


@AllArgsConstructor
@Service
public class BookService {


    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;
    private PublisherRepository publisherRepository;
    private LoanRepository loanRepository;



    public Book createBook(BookDTO bookDTO) {

        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, bookDTO.getAuthorId())));

        Category category = categoryRepository.findById(bookDTO.getCategoryId().getId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, bookDTO.getCategoryId())));

        Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, bookDTO.getPublisherId())));

        Book book = new Book();

        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setPageCount(bookDTO.getPageCount());
        book.setAuthorId(bookDTO.getAuthorId());
        book.setPublisherId(bookDTO.getPublisherId());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setCategoryId(bookDTO.getCategoryId());
        book.setImage(bookDTO.getImage());
        book.setShelfCode(bookDTO.getShelfCode());

        bookRepository.save(book);

        return book;
    }


    public Book deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        boolean loanable = book.getLoanable();

        if (!loanable){
            throw new BadRequestException(ErrorMessage.BOOK_LOANED_OUT);
        }
        bookRepository.deleteById(id);

        return  book;
    }


    public Book findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        return book;
    }


    public Book updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        Book foundedBook = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        if (foundedBook.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        Author author = authorRepository.findById(bookUpdateDTO.getAuthorId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, bookUpdateDTO.getAuthorId())));

        Category category = categoryRepository.findById(bookUpdateDTO.getCategoryId().getId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, bookUpdateDTO.getCategoryId())));

        Publisher publisher = publisherRepository.findById(bookUpdateDTO.getPublisherId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, bookUpdateDTO.getPublisherId())));

        Book book = new Book();
        book.setId(foundedBook.getId());
        book.setAuthorId(bookUpdateDTO.getAuthorId());
        book.setCategoryId(bookUpdateDTO.getCategoryId());
        book.setPublisherId(bookUpdateDTO.getPublisherId());
        book.setName(bookUpdateDTO.getName());
        book.setIsbn(bookUpdateDTO.getIsbn());
        book.setPageCount(bookUpdateDTO.getPageCount());
        book.setPublishDate(bookUpdateDTO.getPublishDate());
        book.setImage(bookUpdateDTO.getImage());
        book.setShelfCode(bookUpdateDTO.getShelfCode());
        book.setCreateDate(foundedBook.getCreateDate());

        bookRepository.save(book);
        return book;
    }



    EntityManager entityManager;

    public Page findAllWithPageForMemberQuery(String name, Long cat, Long author, Long publisher, Pageable pageable) throws BadRequestException {

        if (name == null && cat == null && author == null && publisher == null){

            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        } else{
            StringBuilder sb = new StringBuilder();
            sb.append("select b.id, b.name, b.isbn from Book b where");
            if(name != null){
                sb.append(" b.name= :name and");
            }
            if( cat != null){
                sb.append( " b.categoryId.id= :cat and" );
            }
            if(author != null){
                sb.append(" b.authorId.id= :author and");
            }
            if(publisher != null){
                sb.append(" b.publisherId.id= :publisher and");
            }
            sb.append(" b.active=true");
            Query query = entityManager.createQuery(sb.toString());
            if(name != null){
                query.setParameter("name", name);
            }
            if( cat != null){
                query.setParameter("cat", cat);
            }
            if(author != null){
                query.setParameter("author", author);
            }
            if(publisher != null){
                query.setParameter("publisher", publisher);
            }
            List books = new ArrayList<>();
            books = query.getResultList();

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), books.size());
            final Page<Book> page = new PageImpl<>(books.subList(start, end), pageable, books.size());

            return page;
        }


    }

    public Page findAllWithPageForAdminQuery(String name, Long cat, Long author, Long publisher, Pageable pageable) throws BadRequestException {

        if (name == null && cat == null && author == null && publisher == null){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        } else{
            StringBuilder sb = new StringBuilder();
            sb.append("select b.id, b.name, b.isbn from Book b where");
            if(name != null){
                sb.append(" b.name= :name and");
            }
            if( cat != null){
                sb.append( " b.categoryId.id= :cat and" );
            }
            if(author != null){
                sb.append(" b.authorId.id= :author and");
            }
            if(publisher != null){
                sb.append(" b.publisherId.id= :publisher and");
            }
            sb.append(" b.id is not null");
            Query query = entityManager.createQuery(sb.toString());
            if(name != null){
                query.setParameter("name", name);
            }
            if( cat != null){
                query.setParameter("cat", cat);
            }
            if(author != null){
                query.setParameter("author", author);
            }
            if(publisher != null){
                query.setParameter("publisher", publisher);
            }
            List books = new ArrayList<>();
            books = query.getResultList();

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), books.size());
            final Page<Book> page = new PageImpl<>(books.subList(start, end), pageable, books.size());

            return page;
        }
    }


}