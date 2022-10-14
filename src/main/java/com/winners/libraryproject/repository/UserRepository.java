package com.winners.libraryproject.repository;
import com.winners.libraryproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //    @Query("SELECT u From User u Where u.email = ?1")
    Optional<User> findByEmail(String email);



}
