package com.winners.libraryproject.repository;

import com.winners.libraryproject.dto.LoanDTO;
import com.winners.libraryproject.entity.Loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {


    @Query("SELECT new com.winners.libraryproject.entity.Loan(r) FROM Loan r WHERE r.userId.id = ?1")
    Page findUserLoansSelf(Long userId, Pageable pageable);
    @Query("SELECT new com.winners.libraryproject.entity.Loan(r) FROM Loan r WHERE r.id = ?1 and r.userId.id = ?2")
    Optional<LoanDTO> getLoanByLoanId(Long userId, Long loanId);
}
