package com.winners.libraryproject.controller;
import com.winners.libraryproject.dto.AuthorDTO;
import com.winners.libraryproject.entity.Author;
import com.winners.libraryproject.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthorController {
    @Autowired
    private final AuthorService authorService;

    @PostMapping("/authors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> createAuthor(@Valid @RequestBody AuthorDTO authorDTO){

     Author newAuthor  = authorService.createAuthor(authorDTO);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", newAuthor.getId().toString());
        map.put("name : ",newAuthor.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }


    @GetMapping("/authors")
    public ResponseEntity<Page<AuthorDTO>> getAllUserByPage(@RequestParam("page") int page,
                                                            @RequestParam("size") int size,
                                                            @RequestParam("sort") String prop,
                                                            @RequestParam("direction") Direction direction){

        Pageable pageable=PageRequest.of(page, size, Sort.by(direction,prop));
        Page<AuthorDTO> userDTOPage=authorService.getAuthorPage(pageable);
        return ResponseEntity.ok(userDTOPage);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Map<String,String>>  findById(@PathVariable("id") Long id){
        AuthorDTO authorDTO= authorService.findById(id);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", authorDTO.getId().toString());
        map.put("name : ",authorDTO.getName());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("/authors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody Author author){
        Author author1 = authorService.updateAuthor(id,author);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", author1.getId().toString());
        map.put("name : ",author1.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }
    @DeleteMapping("/authors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> deleteById(@PathVariable("id") Long id){
       Author author= authorService.deleteById(id);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", author.getId().toString());
        map.put("name : ",author.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }



}