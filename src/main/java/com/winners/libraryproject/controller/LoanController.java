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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    /* 1-Role ayarlamaları yapılacak

     */

    @Autowired
    LoanService loanService;

//-----------------LOANS----search------------------------------

    @GetMapping()
    public ResponseEntity<List<Loan>>getLoansWithPageByUserId(HttpServletRequest request,
                                                              @RequestParam ("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam("direction") Sort.Direction direction){

        Long userId=(Long)request.getAttribute("id");

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        List<Loan>loans=loanService.findLoansWithPageByUserId(userId,pageable);
        return new ResponseEntity<>(loans, HttpStatus.OK);

    }

    /*
@GetMapping("")
@PreAuthorize("hasRole('MEMBER')")
public ResponseEntity<LoanDTO> getAllLoansByUser(HttpServletRequest request){


    Long userId = (long)request.getAttribute("id");
    LoanDTO loanDTO=loanService.findAllLoansByUser(userId);
    return ResponseEntity.ok(loanDTO);
}
*/
    /*  @GetMapping("")
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
    
    */

//-----------------LOANS/:id----------------------------------

    /* 1- Sadece ilgili kullanıcı erişmeli




    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId, HttpServletRequest request) {

        Long userId = (long)request.getAttribute("id");
        Loan loan = loanService.getLoanById(loanId,userId);
        return ResponseEntity.ok(loan);
    }
 */
//-----------------/loans/user/:userId---------search-------------------------


// -----------------/loans/book/:bookId---------search-------------------------


//-----------------/loans/auth/:loanId----------------------------------
/*
    @GetMapping("/auth/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<LoanDTO> getAnyLoansById(@PathVariable Long id) {
        LoanDTO loan = loanService.getLoansById(id);
        return ResponseEntity.ok(loan);
    }
*/

//-----------------/loans put----------------------------------


//-----------------/loans/:id put----------------------------------









}
