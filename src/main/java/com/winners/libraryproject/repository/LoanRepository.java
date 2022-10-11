package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {
}
