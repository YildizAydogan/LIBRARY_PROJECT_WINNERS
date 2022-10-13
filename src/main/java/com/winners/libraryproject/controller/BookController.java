package com.winners.libraryproject.controller;

import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.service.AuthorService;
import com.winners.libraryproject.service.BookService;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private AuthorService authorService;


    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String findAllBooks(Model model) {
        List<Book> books = bookService.findAllBooks();

        model.addAttribute("books", books);
        return "list-books";
    }

    @GetMapping("/searchBook")
    public String searchBook(@Param("keyword") String keyword, Model model) {
        List<Book> books = bookService.searchBooks(keyword);

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "list-books";
    }

    @GetMapping("/books/{id}")
    public String findBookById(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findBookById(id);

        model.addAttribute("book", book);
        return "list-book";
    }

    @GetMapping("/add")
    public String showCreateForm(Book book, Model model) {
        //model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("authors", authorService.findAllAuthors());
        //model.addAttribute("publishers", publisherService.findAllPublishers());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String createBook(Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }

        bookService.createBook(book);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

    @PutMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final Book book = bookService.findBookById(id);

        model.addAttribute("book", book);
        return "update-book";
    }

    @PutMapping("/update-book/{id}")
    public String updateBook(@PathVariable("id") Long id, Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        bookService.updateBook(book);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

    @RequestMapping("/remove-book/{id}")
    public String deleteBook(@PathVariable("id") Long id, Model model) {
        bookService.deleteBook(id);

        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }
}