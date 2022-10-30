package com.winners.libraryproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;



    @Size(min= 2, max = 50)
    @NotNull(message = "Please enter a valid Name!")
    @Column(nullable = false)
    private String name;



    @Column(nullable = false)
    private Boolean builtIn = false;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer sequence;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<Book> books ;



}
