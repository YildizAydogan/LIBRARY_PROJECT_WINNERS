package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.Loan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {

    //-----------------LOANS----search------------------------------
    @Query("SELECT r from Loan r " +
            "where r.userId.id=?1")
    List<Loan> findAllWithPageByUserId(Long userId, Pageable pageable);






}
