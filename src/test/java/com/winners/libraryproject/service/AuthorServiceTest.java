package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.AuthorDTO;
import com.winners.libraryproject.entity.Author;
import com.winners.libraryproject.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    AuthorService authorService;
    @Mock
    AuthorRepository authorRepository;


    @Test
    void createAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Test-Name");
        Author authorMock = mock(Author.class);

        when(authorRepository.save(ArgumentMatchers.any(Author.class))).thenReturn(authorMock);
        Author result = authorService.createAuthor(authorDTO);


        assertEquals(authorDTO.getName(),result.getName());
    }

    @Test
    void getAll() {

        /*

         Author author = new Author();
        author.setName("Test-Name");
        author.setId(2L);

       List<Author> authorList = new ArrayList<>();
       authorList.add(author);
       when(authorRepository.findAll(Pageable.unpaged())).thenReturn((Page<Author>) authorList);
       Page<AuthorDTO> authorListResult = authorService.getAll(Pageable.unpaged());

       assertEquals(authorListResult.getSize(),1);

        */
    }

    @Test
    void findById() {

        Author author = new Author();
        author.setName("Test-Name");
        author.setId(2L);
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author));
        AuthorDTO resultDTO = authorService.findById(2L);

        assertEquals(author.getName(),resultDTO.getName());
        assertEquals(author.getId(),resultDTO.getId());

    }

    @Test
    void updateAuthor() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getAuthorPage() {
    }
}