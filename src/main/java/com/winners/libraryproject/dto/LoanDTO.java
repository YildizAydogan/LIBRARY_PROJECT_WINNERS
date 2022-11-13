package com.winners.libraryproject.dto;

import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;



import java.util.Date;

@Data
@AllArgsConstructor
public class LoanDTO {

    private Long id;



    private User userId;



    private Book bookId;


    private Date loanDate;


    private Date expireDate;


    private Date returnDate;



}
