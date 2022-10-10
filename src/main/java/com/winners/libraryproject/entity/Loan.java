package com.winners.libraryproject.entity;

<<<<<<< HEAD

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;





@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book bookId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Germany")
    @NotNull(message = "Please enter a valid Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanDate;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Germany")
    @NotNull(message = "Please enter a valid Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Germany")
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;


    @Lob
    @Column(columnDefinition = "text")
    String notes;


}




