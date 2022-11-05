package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.LoanDTO;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.repository.BookRepository;
import com.winners.libraryproject.repository.LoanRepository;
import com.winners.libraryproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


   /* Örnek kod

   public Page<UserToUserDTO> getUserLoanPage(Pageable pageable){

        Page<User> users=userRepository.findAll(pageable);
        Page<UserToUserDTO> dtoPage=  users.map(user->new  UserToUserDTO(user));
        return dtoPage;
    }


    */
    public Page<LoanDTO> findAllLoansByUser (Long userId, Pageable pageable) throws ResourceNotFoundException {
       // Yanlış repo fonk. deneme amaçlı yazıldı

        Page<Loan> loans = loanRepository.findUserLoansSelf(userId,pageable);
        Page<LoanDTO> loansDTO= loans.map(loan-> new LoanDTO(loan.getId(),
                                                            loan.getUserId(),
                                                            loan.getBookId(),
                                                            loan.getLoanDate(),
                                                            loan.getExpireDate(),
                                                            loan.getReturnDate()));


       return loansDTO;

    }


    public LoanDTO getLoanById(Long loanId, Long userId) throws ResourceNotFoundException {
        return loanRepository.getLoanByLoanId(userId,loanId)
                            .orElseThrow(() -> new ResourceNotFoundException("Loan Not Found"));

    }
}
