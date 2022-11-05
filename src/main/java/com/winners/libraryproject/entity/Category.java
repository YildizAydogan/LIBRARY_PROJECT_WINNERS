package com.winners.libraryproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    private Long id;

    @NotNull(message="Name can not be null")
    @NotBlank(message="Name can not be white space")
    @Size(min=2,max=80, message="Name '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 80, nullable = false)
    private String name;

    private Boolean builtIn=false;

    @NotNull(message="Sequence can not be null")
    private int sequence;

    @OneToMany(mappedBy="categoryId")
    @JsonIgnoreProperties("categoryId")
    private List<Book> books;

}
