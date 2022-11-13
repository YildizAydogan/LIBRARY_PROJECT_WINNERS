package com.winners.libraryproject.controller;


import com.winners.libraryproject.dto.LoanCreatorDTO;
import com.winners.libraryproject.dto.LoanDTO;
import com.winners.libraryproject.dto.UserToUserDTO;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
public class LoanController {


    @Autowired
    LoanService loanService;

    //-----------------LOANS----search------------------------------
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getLoansWithPageByUserId(HttpServletRequest request,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam("sort") String prop,
                                                               @RequestParam("direction") Sort.Direction direction) {

        Long userId = (Long) request.getAttribute("id");

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        List<Loan> loans = loanService.findLoansWithPageByUserId(userId, pageable);
        return new ResponseEntity<>(loans, HttpStatus.OK);

    }


//-----------------LOANS/:id----------------------------------

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoanById(HttpServletRequest request,
                                            @PathVariable(value = "id") Long loanId) {

        Long userId = (Long) request.getAttribute("id");
        Loan loan = loanService.getByIdAndUserId(loanId, userId);

        return new ResponseEntity<>(loan, HttpStatus.OK);
    }


    //-----------------/loans/user/:userId---------search-------------------------
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/loans/user/{userId}")
    public ResponseEntity<List<LoanDTO>> findAllLoansByUserId(@PathVariable Long userId,
                                                              @RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam("direction") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));


        List<LoanDTO> loansDTO = loanService.findAllLoansByUserId(userId, pageable);
        return new ResponseEntity<>(loansDTO, HttpStatus.OK);

    }

    // -----------------/loans/book/:bookId---------search-------------------------
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/loans/book/{bookId}")
    public ResponseEntity<List<Loan>> findLoanedBookByBookId(@PathVariable Long bookId,
                                                             @RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("sort") String prop,
                                                             @RequestParam("direction") Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        List<Loan> loans = loanService.getLoanedBookByBookId(bookId, pageable);

        return new ResponseEntity<>(loans, HttpStatus.OK);

    }

    //-----------------/loans/auth/:loanId----------------------------------
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/loans/auth/{id}")
    public ResponseEntity<Loan> getLoanWithId(@PathVariable(value = "id") Long loanId) {
        Loan loan = loanService.getLoanById(loanId);
        return new ResponseEntity<>(loan, HttpStatus.OK);

    }

//-----------------/loans put----------------------------------
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @PostMapping("/loans")
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody LoanCreatorDTO loanCreatorDTO){
       Loan loan = loanService.create(loanCreatorDTO);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }
//-----------------/loans/:id put----------------------------------


}
