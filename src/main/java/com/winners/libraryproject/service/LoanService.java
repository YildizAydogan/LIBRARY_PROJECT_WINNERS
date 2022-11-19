package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.Loan.LoanCreatorDTO;
import com.winners.libraryproject.dto.Loan.LoanDTO;
import com.winners.libraryproject.dto.Loan.LoanUpdateResponse;
import com.winners.libraryproject.dto.Loan.UpdateLoanDTO;
import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.repository.BookRepository;
import com.winners.libraryproject.repository.LoanRepository;
import com.winners.libraryproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class LoanService {


    @Autowired
    LoanRepository loanRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    //-----------------LOANS----search------------------------------
    @Transactional(readOnly = true)
    public List<Loan> findLoansWithPageByUserId(Long userId, Pageable pageable) {
        return loanRepository.findAllWithPageByUserId(userId, pageable);

    }

    //-----------------LOANS/:id----------------------------------
    public Loan getByIdAndUserId(Long loanId, Long userId) {
        Loan loan = loanRepository.findByIdAndUserId(loanId, userId);
        return loan;
    }

    //-----------------/loans/user/:userId---------search-------------------------
    @Transactional
    public List<LoanDTO> findAllLoansByUserId(Long userId, Pageable pageable) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User not found with this id :", userId)));

        return loanRepository.findLoansByUserIdPage(userId, pageable);

    }

    // -----------------/loans/book/:bookId---------search-------------------------
    public List<Loan> getLoanedBookByBookId(Long bookId, Pageable pageable) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Book not found with this id :", bookId)));

        return loanRepository.findAllByBookId(book, pageable);

    }

    //-----------------/loans/auth/:loanId----------------------------------
    @Transactional
    public Loan getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() ->
                new BadRequestException(String.format("Loan not found with this id :", loanId)));

        return loan;
    }


    //-----------------/loans put----------------------------------

    public Loan create(LoanCreatorDTO loanCreatorDTO) {
        Loan loan = new Loan();
        List<LoanDTO> loanList = loanRepository.findLoansByUserId(loanCreatorDTO.getUserId().getId());

        int notReturnedBook = 0;
        for (LoanDTO loanx : loanList) {
            if(loanx.getReturnDate().equals(null)){
                notReturnedBook++;
                if (loanx.getReturnDate().before(Date.valueOf(LocalDate.now()))){
                  throw  new BadRequestException(String.format("You have unreturned book :", loanx.getBookId().getId()));
                }
            }
        }

        if(!loanCreatorDTO.getBookId().getLoanable()){
            throw new BadRequestException(String.format("Book is not loanable :", loanCreatorDTO.getBookId().getId()));
        }


        int userScore = loanCreatorDTO.getUserId().getScore();
            loan.setUserId(loanCreatorDTO.getUserId());
            loan.setBookId(loanCreatorDTO.getBookId());
            loan.setNotes(loanCreatorDTO.getNotes());
            loan.setLoanDate(Date.valueOf(LocalDate.now()));

            if(userScore>=2||notReturnedBook<5){
                loan.setExpireDate(Date.valueOf(LocalDate.now().plusDays(20)));
            } else if (userScore==1||notReturnedBook<4) {
                loan.setExpireDate(Date.valueOf(LocalDate.now().plusDays(15)));
            } else if (userScore==0||notReturnedBook<3) {
                loan.setExpireDate(Date.valueOf(LocalDate.now().plusDays(10)));
            } else if (userScore==-1||notReturnedBook<2) {
                loan.setExpireDate(Date.valueOf(LocalDate.now().plusDays(6)));
            } else if (userScore<=-2||notReturnedBook<1) {
                loan.setExpireDate(Date.valueOf(LocalDate.now().plusDays(3)));
            }

            loanRepository.save(loan);
            loanCreatorDTO.getBookId().setLoanable(false);
            return loan;

    }
    //-----------------/loans/:id put----------------------------------

    //TODO Bu kod gözden geçirilmeli.
    public LoanUpdateResponse updateLoan(Long loanId, UpdateLoanDTO updateLoanDTO) throws BadRequestException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(()->
                new ResourceNotFoundException(String.format("This loan not found : ", loanId)));

        User user = loan.getUserId();
        Book book = loan.getBookId();

        try {
            if(updateLoanDTO.getReturnDate()!=null){
                book.setLoanable(true);
                loan.setReturnDate(updateLoanDTO.getReturnDate());
                bookRepository.save(book);
                loanRepository.save(loan);
                if(updateLoanDTO.getReturnDate().equals(loan.getReturnDate()) || updateLoanDTO.getReturnDate().before(loan.getReturnDate())){
                    user.setScore(user.getScore()+1);
                    userRepository.save(user);
                    return new LoanUpdateResponse(loan);
                }else{
                    user.setScore(user.getScore()-1);
                    userRepository.save(user);
                    return new LoanUpdateResponse(loan);
                }
            }else{
                loan.setExpireDate(updateLoanDTO.getExpireDate());
                loan.setNotes(updateLoanDTO.getNotes());
                bookRepository.save(book);
                userRepository.save(user);
                loanRepository.save(loan);
                return new LoanUpdateResponse(loan);

            }
        }catch(RuntimeException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }finally{
            return new LoanUpdateResponse(loan);
        }

    }


}
