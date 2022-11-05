package com.winners.libraryproject.controller;


import com.winners.libraryproject.dto.LoanDTO;
import com.winners.libraryproject.dto.UserToUserDTO;
import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/loans")
public class LoanController {

    /* 1-Role ayarlamaları yapılacak

     */

    @Autowired
    LoanService loanService;

//-----------------LOANS----search------------------------------
    @GetMapping("")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Page<LoanDTO>> getAllLoansByUser(@RequestParam("page") int page,
                                                                     @RequestParam("size") int size,
                                                                     @RequestParam("sort") String prop,
                                                                     @RequestParam("type") Sort.Direction type,
                                                                     HttpServletRequest request){

        Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
        Long userId = (long)request.getAttribute("id");
        Page<LoanDTO> loanDTOPageable=loanService.findAllLoansByUser(userId,pageable);
        return ResponseEntity.ok(loanDTOPageable);
    }
//-----------------LOANS/:id----------------------------------

    /* 1- Sadece ilgili kullanıcı erişmeli

     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<LoanDTO> getLoansById(@PathVariable Long loanId, HttpServletRequest request) {
        LoanDTO loan = loanService.getLoansById(loanId);
        Long userId = (long)request.getAttribute("id");
        return ResponseEntity.ok(loan);
    }

//-----------------/loans/user/:userId---------search-------------------------


// -----------------/loans/book/:bookId---------search-------------------------


//-----------------/loans/auth/:loanId----------------------------------

    @GetMapping("/auth/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<LoanDTO> getAnyLoansById(@PathVariable Long id) {
        LoanDTO loan = loanService.getLoansById(id);
        return ResponseEntity.ok(loan);
    }


//-----------------/loans put----------------------------------


//-----------------/loans/:id put----------------------------------









}
