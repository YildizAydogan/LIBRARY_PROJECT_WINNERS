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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    //-----------------LOANS----search------------------------------
    @Transactional(readOnly=true)
    public List<Loan> findLoansWithPageByUserId(Long userId, Pageable pageable) {
        return  loanRepository.findAllWithPageByUserId(userId,pageable);

    }


   /* Örnek kod

   public Page<UserToUserDTO> getUserLoanPage(Pageable pageable){

        Page<User> users=userRepository.findAll(pageable);
        Page<UserToUserDTO> dtoPage=  users.map(user->new  UserToUserDTO(user));
        return dtoPage;
    }
*/

    /*
  public LoanDTO findAllLoansByUser (Long userId) throws ResourceNotFoundException {
       // Yanlış repo fonk. deneme amaçlı yazıldı

        Loan loan = loanRepository.findUserLoansSelf(userId);


       return new LoanDTO(loan.getId(), loan.getUserId(),loan.getBookId(),loan.getLoanDate(),loan.getExpireDate(),loan.getReturnDate());

    }

   
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


    public Loan getLoanById(Long loanId, Long userId) throws ResourceNotFoundException {
        return loanRepository.getLoanByLoanId(loanId,userId)
                            .orElseThrow(() -> new ResourceNotFoundException("Loan Not Found"));

    }

    */
}
