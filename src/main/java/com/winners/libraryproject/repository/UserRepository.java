package com.winners.libraryproject.repository;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    Boolean existsByEmail(String email) throws ResourceNotFoundException;

    @Modifying
    @Query("UPDATE User u " +
            "SET u.firstName = ?2, u.lastName = ?3, u.address = ?5, " +
            "u.phone = ?6, u.email = ?8, u.resetPasswordCode = ?11 " +
            "WHERE u.id = ?1")
    void update(Long id, String firstName, String lastName, String address, String phone, String email,
                String resetPasswordCode) throws BadRequestException;
}
