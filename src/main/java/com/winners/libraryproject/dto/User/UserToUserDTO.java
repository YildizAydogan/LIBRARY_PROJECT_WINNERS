package com.winners.libraryproject.dto.User;

import com.winners.libraryproject.entity.Loan;
import com.winners.libraryproject.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserToUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Date birthDate;
    private String email;
    private Boolean builtIn;
    private Set<String> roles;
    private Set<Loan> loan;

    public UserToUserDTO(User user){
        this.id=user.getId();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.address=user.getAddress();
        this.phone=user.getPhone();
        this.birthDate=user.getBirthDate();
        this.email=user.getEmail();
        this.builtIn=user.getBuiltIn();
        this.roles= Collections.singleton(user.getRoles().toString());
        this.loan=user.getLoans();
    }
}
