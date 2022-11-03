package com.winners.libraryproject.repository;

import com.winners.libraryproject.dto.LoanDTO;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {

}
