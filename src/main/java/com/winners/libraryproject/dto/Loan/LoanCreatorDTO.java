package com.winners.libraryproject.dto.Loan;

import com.winners.libraryproject.entity.Book;
import com.winners.libraryproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.Date;

@Data
@AllArgsConstructor
public class LoanCreatorDTO {

    private User userId;

    private Book bookId;

    String notes;



}
