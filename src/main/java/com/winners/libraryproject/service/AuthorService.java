package com.winners.libraryproject.service;
import com.winners.libraryproject.dto.AuthorDTO;
import com.winners.libraryproject.entity.Author;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.payload.messages.ErrorMessage;
import com.winners.libraryproject.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.function.Function;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AuthorService {

	@Autowired
    private AuthorRepository repository;

    public Author createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(author.getName());
        repository.save(author);
        return author;
    }
    public Page<AuthorDTO> getAll(Pageable pageable) {
        Page<Author> authorList = repository.findAll(pageable);
        Page<AuthorDTO> authorDTOList = authorList.map( author -> new AuthorDTO(author.getId(),author.getName()) );
        return authorDTOList;
    }

    public AuthorDTO findById(Long id) {
        Author author = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found with this id"));
        return new AuthorDTO(author.getId(), author.getName());
    }

    public Author updateAuthor(Long id, Author author) {
        Author foundAuthor = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        if(foundAuthor.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        foundAuthor.setId(id);
        foundAuthor.setName(author.getName());
        repository.save(foundAuthor);
        return foundAuthor;
    }

    public Author deleteById(Long id) {
        Author author = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        if(author.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if(!author.getBooks().isEmpty()) {
            throw  new ResourceNotFoundException("You cannot delete an author who has a book");
        }
        repository.deleteById(id);
        return author;
    }

    public Page<AuthorDTO> getAuthorPage(Pageable pageable) {
        Page<Author> authors = repository.findAll(pageable);
        Page<AuthorDTO> dtoPage = authors.map(new Function<Author, AuthorDTO>() {
            @Override
            public AuthorDTO apply(Author author) {
                return new AuthorDTO(author.getId(), author.getName());
            }
        });

        return dtoPage;
    }





}