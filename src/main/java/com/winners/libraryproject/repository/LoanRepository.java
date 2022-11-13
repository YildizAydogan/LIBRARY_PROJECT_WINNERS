package com.winners.libraryproject.repository;

import com.winners.libraryproject.dto.LoanDTO;
import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    //-----------------LOANS----search------------------------------
    @Query("SELECT r from Loan r " +
            "where r.userId.id=?1")
    List<Loan> findAllWithPageByUserId(Long userId, Pageable pageable);

    //-----------------LOANS/:id----------------------------------
    @Query("SELECT r from Loan r WHERE r.id = ?1 and r.userId.id = ?2")
    Loan findByIdAndUserId(Long loanId, Long userId);

    //-----------------/loans/user/:userId---------search-------------------------
    @Query("SELECT r from Loan r " +
            "where r.userId.id=?1")
    List<LoanDTO> findLoansByUserIdPage(Long userId, Pageable pageable);


    // -----------------/loans/book/:bookId---------search-------------------------
    @Query("SELECT r from Loan r " +
            "where r.bookId.id=?1")
    List<Loan> findAllByBookId(Book book, Pageable pageable);

    //-----------------/loans/auth/:loanId----------------------------------
        //Jpa repositories methods used

    //-----------------/loans post----------------------------------
    @Query("SELECT r from Loan r " +
            "where r.userId.id=?1")
    List<LoanDTO> findLoansByUserId(Long userId);

    //-----------------/loans/:id put----------------------------------


}
