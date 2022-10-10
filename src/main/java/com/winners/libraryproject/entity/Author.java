package com.winners.libraryproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")
    private Set<Book> books = new HashSet<Book>();



    @NotNull(message = "Please provide your author name")
    @Size(min = 4, max = 70, message = "author name '${validatedValue}' must be between {min} and {max} chracters long")
    @Column(name = "name",length = 70, nullable = false)
    private String name;

    @Column(name = "builtIn",nullable = false)
    private Boolean builtIn;

    public Author(String name, Boolean builtIn) {
        this.name = name;
        this.builtIn = builtIn;
    }
}
