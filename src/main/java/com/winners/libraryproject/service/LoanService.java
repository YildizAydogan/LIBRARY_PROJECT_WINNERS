package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.LoanDTO;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.repository.BookRepository;
import com.winners.libraryproject.repository.LoanRepository;
import com.winners.libraryproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class LoanService {

    /* 1-LoanDto yapıp role e göre notes bilgisi çıkartılacak
        2-Search mevzuu halledilecek

     */

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    public LoanDTO getLoansById (Long id) throws ResourceNotFoundException {
       Optional<Loan> answer = loanRepository.findById(id);
       LoanDTO loanDTO = new LoanDTO();
            loanDTO.setLoanDate(answer.get().getLoanDate());
            loanDTO.setId((answer.get().getId()));
            loanDTO.setBookId(answer.get().getBookId());
            loanDTO.setExpireDate(answer.get().getExpireDate());
            loanDTO.setReturnDate(answer.get().getReturnDate());
            loanDTO.setUserId(answer.get().getUserId());
       return loanDTO;

    }


}
