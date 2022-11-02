package com.winners.libraryproject.dto;

import com.winners.libraryproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserToResponse {

    private Long id;
    private String firstName;


    public UserToResponse(User user){
        this.id= user.getId();
        this.firstName=getFirstName();
    }
}
