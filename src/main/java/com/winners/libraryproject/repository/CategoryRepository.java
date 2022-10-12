package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.Set;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Modifying
    @Query("update Category c " +
           " set c.name= ?2, c.builtIn= ?3, c.sequence= ?4, c.books= ?5 " +
           " where c.id = ?1")
    void update(Long id, String name, Boolean builtIn, Integer sequence, Set<Book> books) throws HttpClientErrorException.BadRequest;
}
