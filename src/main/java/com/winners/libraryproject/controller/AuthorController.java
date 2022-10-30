package com.winners.libraryproject.controller;

import com.winners.libraryproject.entity.Author;
import com.winners.libraryproject.service.AuthorService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/authors")
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String findAllAuthors(Model model) {
        final List<Author> authors = authorService.findAllAuthors();

        model.addAttribute("authors", authors);
        return "list-authors";
    }

    @GetMapping("/author/{id}")
    public String findAuthorById(@PathVariable("id") Long id, Model model) {
        final Author author = authorService.findAuthorById(id);

        model.addAttribute("author", author);
        return "list-author";
    }

    @PostMapping("/addAuthor")
    public String showCreateForm(Author author) {
        return "add-author";
    }

    @PostMapping("/add-author")
    public String createAuthor(Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-author";
        }

        authorService.createAuthor(author);
        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }

    @PutMapping("/updateAuthor/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final Author author = authorService.findAuthorById(id);

        model.addAttribute("author", author);
        return "update-author";
    }

    @RequestMapping("/update-author/{id}")
    public String updateAuthor(@PathVariable("id") Long id, Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            author.setId(id);
            return "update-author";
        }

        authorService.updateAuthor(author);
        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }

    @DeleteMapping("/remove-author/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, Model model) {
        authorService.deleteAuthor(id);

        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }


}
