package com.winners.libraryproject.repository;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //    @Query("SELECT u From User u Where u.email = ?1")
    Optional<User> findByEmail(String email);


//    User findByEmail(String email);

    Boolean existsByEmail(String email) throws ResourceNotFoundException;

  /*  @Modifying
    @Query("UPDATE User u " +
            "SET u.firstName = ?2, u.lastName = ?3, u.address = ?5, " +
            "u.phone = ?6, u.email = ?8, u.resetPasswordCode = ?11 " +
            "WHERE u.id = ?1")
    void update(Long id, String firstName, String lastName, String address, String phone, String email,
                String resetPasswordCode) throws BadRequestException;
                */

    @Query(value="select u.id, u.firstName from User u where u.firstName=:name")
    Page findUsersWithQuery(@Param("name") String name, Pageable pageable);

}
