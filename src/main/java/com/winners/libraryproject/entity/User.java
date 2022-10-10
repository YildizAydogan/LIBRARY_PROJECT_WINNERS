package com.winners.libraryproject.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 30)
    @NotNull(message = "Please enter your firstName")
    private String firstName;

    @Size(min = 2, max = 30)
    @NotNull(message = "Please enter your lastName")
    private String lastName;

    @Size(min = -2, max = +2)
    @NotNull(message = "Please enter your score")
    private Integer score = 0;

    @Size(min = 10, max = 100)
    @NotNull(message = "Please enter your address")
    private String address;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    @Size(min = 2, max = 30)
    @NotNull(message = "Please enter your phone number")
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "Turkey")
    @NotNull(message = "Please enter the pick up time of the birthDate")
    @Column(nullable = false)
    private Date birthDate;

    @Email(message = "Please enter valid email")
    @Size(min = 10, max = 80)
    @NotNull(message = "Please enter your email")
    private String email;


    @NotNull(message = "Please enter your password")
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Turkey")
    @NotNull(message = "Please enter the pick up time of the createDate")
    @Column(nullable = false)
    private LocalDateTime createDate;

    @NotNull(message = "Please enter your password")
    @Column(nullable = false)
    private String resetPasswordCode;


    @NotNull(message = "Please enter your builtIn")
    private Boolean builtIn = false;

    @ManyToMany()
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Loan> loans;


}
