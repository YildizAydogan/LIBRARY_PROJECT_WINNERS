package com.winners.libraryproject.controller;

import com.winners.libraryproject.dto.BookDTO;
import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.service.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;
    private PublisherService publisherService;
    private LoanService loanService;

    public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService, PublisherService publisherService, LoanService loanService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
        this.loanService = loanService;
    }

    //-----------------FIND ALL BOOKS----------------------------------
    @GetMapping
    public String findAllBooks(Model model) {
        List<BookDTO> books = bookService.findAllBooks();

        model.addAttribute("books", books);
        return "list-books";
    }

    //-----------------SEARCH ALL BOOKS----------------------------------
    @GetMapping("/searchBook")
    public String searchBook(@Param("keyword") String keyword, Model model) {
        List<BookDTO> books = bookService.searchBooks(keyword);

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "list-books";
    }

    //-----------------FIND BOOK BY ID----------------------------------
    @GetMapping("/books/{id}")
    public String findBookById(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findBookById(id);

        model.addAttribute("book", book);
        return "list-book";
    }

    //-----------------SHOW CREATE FORM----------------------------------
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateForm(Book book, Model model) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("authors", authorService.findAllAuthors());
        model.addAttribute("publishers", publisherService.findAllPublishers());
        //model.addAttribute("loans",loanService.findAllLoans());
        return "add-book";
    }

    //-----------------CREATE A BOOK----------------------------------

    @PostMapping("/add-book")
    @PreAuthorize("hasRole('ADMIN')")
    public String createBook(Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }
        bookService.createBook(book);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

    //-----------------SHOW UPDATE FORM----------------------------------
    @PutMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final Book book = bookService.findBookById(id);

        model.addAttribute("book", book);
        return "update-book";
    }

    //-----------------UPDATE BOOK BY ID----------------------------------
    @PutMapping("/update-book/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateBook(@PathVariable("id") Long id, Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        bookService.updateBook(book,id);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

    //-----------------DELETE BOOK BY ID----------------------------------
    @DeleteMapping("/remove-book/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable("id") Long id, Model model) {
        bookService.deleteBook(id);

        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }
}