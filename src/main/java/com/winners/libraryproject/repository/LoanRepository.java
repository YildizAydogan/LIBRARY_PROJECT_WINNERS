package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Transactional
@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {

}
