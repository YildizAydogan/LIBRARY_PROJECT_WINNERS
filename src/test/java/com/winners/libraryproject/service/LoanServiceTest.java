package com.winners.libraryproject.service;

import com.winners.libraryproject.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    LoanRepository loanRepository;

    @Test
    void findLoansWithPageByUserId() {

    }

    @Test
    void getByIdAndUserId() {
    }

    @Test
    void findAllLoansByUserId() {
    }

    @Test
    void getLoanedBookByBookId() {
    }

    @Test
    void getLoanById() {
    }

    @Test
    void create() {
    }

    @Test
    void updateLoan() {
    }
}