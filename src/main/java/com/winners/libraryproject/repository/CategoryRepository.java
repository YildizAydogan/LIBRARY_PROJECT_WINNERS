package com.winners.libraryproject.repository;
import com.winners.libraryproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

/*    @Modifying
    @Query("update Category c " +
           " set c.name= ?2, c.builtIn= ?3, c.sequence= ?4, c.books= ?5 " +
           " where c.id = ?1")
    void update(Long id, String name, Boolean builtIn, Integer sequence, Set<Book> books) throws HttpClientErrorException.BadRequest;*/
}
