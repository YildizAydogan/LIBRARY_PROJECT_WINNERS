package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.UserDTO;
import com.winners.libraryproject.entity.Role;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.entity.enumeration.UserRole;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.repository.RoleRepository;
import com.winners.libraryproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";


    public void register(User user){

        LocalDateTime createDate=LocalDateTime.now();

        user.setCreateDate(createDate);

        Set<Role> roles = new HashSet<>();
        Role customerRole = roleRepository.findByName(UserRole.ROLE_MEMBER);

        roles.add(customerRole);

        user.setRoles(roles);

        userRepository.save(user);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDTO findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));

        UserDTO userDTO=new UserDTO();
        userDTO.setRoles(user.getRoles());
        return new UserDTO(user.getFirstName(), user.getLastName(),user.getScore(),user.getAddress(),user.getPhone(),user.getBirthDate(),user.getEmail(),user.getCreateDate(),user.getResetPasswordCode(),user.getBuiltIn(),userDTO.getRoles() );
    }


    public void removeById(Long id){
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));

        userRepository.deleteById(id);
    }

}
