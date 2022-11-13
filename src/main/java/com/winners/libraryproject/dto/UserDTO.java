package com.winners.libraryproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winners.libraryproject.entity.Role;
import com.winners.libraryproject.entity.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
    private Integer score;
    private String address;
    private String phone;
    private Date birthDate;
    private String email;
    @JsonIgnore
    private String password;
    private LocalDateTime createDate;

    private Boolean builtIn;
    private Set<String> roles;

    public UserDTO(String firstName, String lastName, Integer score, String address, String phone, Date birthDate, String email, LocalDateTime createDate, Boolean builtIn, Set<String> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
        this.address = address;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.createDate = createDate;
        this.builtIn = builtIn;
        this.roles = roles;
    }

    public void setRoles(Set<Role>roles){
        Set<String>roles1=new HashSet<>();

        Role[] role=roles.toArray(new Role[roles.size()]);

        for (int i=0;i<roles.size();i++){
            if(role[i].getName().equals(UserRole.ROLE_ADMIN))
                roles1.add("Admin");
            else if (role[i].getName().equals(UserRole.ROLE_STAFF)) {
                roles1.add("Staff");
            } else
                roles1.add("Member");
        }
        this.roles=roles1;
    }
}
