package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.*;
import com.winners.libraryproject.entity.Role;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.entity.enumeration.UserRole;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ConflictException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.repository.RoleRepository;
import com.winners.libraryproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";



    public void register(User user){

        if (userRepository.existsByEmail(user.getEmail())){
            throw new ConflictException("Error: Email is already in use!");
        }

        LocalDateTime createDate=LocalDateTime.now();
        user.setCreateDate(createDate);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        Role memberRole = roleRepository.findByName(UserRole.ROLE_MEMBER);

        roles.add(memberRole);

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

    public void login(String email,String password) throws AuthException {
        try {
            Optional<User> user= userRepository.findByEmail(email);

            if (!BCrypt.checkpw(password, user.get().getPassword()))
                throw new AuthException("invalid credentials");

        }catch (Exception e){
            throw new AuthException("invalid credentials");
        }
    }

    public void updateUser(Long  id, UserUpdateDTO userUpdateDTO){
        boolean emailExists = userRepository.existsByEmail(userUpdateDTO.getEmail());

        Optional<User> userDetails=userRepository.findById(id);

        if (userDetails.get().getBuiltIn()) {
            throw new BadRequestException("You dont have permission to update user info!");
        }

        if (emailExists && !userUpdateDTO.getEmail().equals(userDetails.get().getEmail())){
            throw new ConflictException("Error: Email is already in use!");
        }
        User user=new User();
        user.setId(id);
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setAddress(userUpdateDTO.getAddress());
        user.setPhone(userUpdateDTO.getPhone());
        user.setBirthDate(userUpdateDTO.getBirthDate());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPassword(userUpdateDTO.getPassword());

        userRepository.save(user);
    }


    public void userCreated(UserCreatedDTO userCreatedDTO){


        Set<Role> roles=new HashSet<>();
        Role memberRole ;
        if (userCreatedDTO.getRoleId()==1){
            memberRole = roleRepository.findByName(UserRole.ROLE_ADMIN);
        }else if (userCreatedDTO.getRoleId()==2){
            memberRole = roleRepository.findByName(UserRole.ROLE_STAFF);
        }else {
            memberRole = roleRepository.findByName(UserRole.ROLE_MEMBER);
        }
        roles.add(memberRole);


        User user= new User();
        user.setId(0L);
        user.setFirstName(userCreatedDTO.getFirstName());
        user.setLastName(userCreatedDTO.getLastName());
        user.setAddress(userCreatedDTO.getAddress());
        user.setPhone(userCreatedDTO.getPhone());
        user.setBirthDate(userCreatedDTO.getBirthDate());
        user.setEmail(userCreatedDTO.getEmail());
        user.setPassword(userCreatedDTO.getPassword());
        user.setCreateDate(userCreatedDTO.getCreateDate());
        user.setResetPasswordCode(userCreatedDTO.getResetPasswordCode());
        user.setRoles(roles);

        userRepository.save(user);
    }

    public Page<UserToUserDTO> getUserLoanPage(Pageable pageable){

        Page<User> users=userRepository.findAll(pageable);
        Page<UserToUserDTO> dtoPage=  users.map(user->new  UserToUserDTO(user));
        return dtoPage;
    }

    public Page getUsersPage(String name ,Pageable pageable){

        if (name!=null){
            return userRepository.findUsersWithQuery(name, pageable);
        }else {
            Page<User> users = userRepository.findAll(pageable);
            Page dtoPage = users.map(user -> new UserToResponse(user));
            return dtoPage;
        }
    }


    public Role addRoles(String userRoles) {

        if (userRoles == null) {
            return roleRepository.findByName(UserRole.ROLE_MEMBER);

        } else {

            if ( "Administrator".equals(userRoles)) {
                return roleRepository.findByName(UserRole.ROLE_ADMIN);

            } else{
                return roleRepository.findByName(UserRole.ROLE_STAFF);

            }

        }
    }


   /* public void updatePassword(Long id, String newPassword, String oldPassword) throws BadRequestException {
        Optional<User> user = userRepository.findById(id);
        if (user.get().getBuiltIn()) {
            throw new BadRequestException("You dont have permission to update password!");
        }

        if (!BCrypt.hashpw(oldPassword, user.get().getPassword()).equals(user.get().getPassword()))
            throw new BadRequestException("password does not match");

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.get().setPassword(hashedPassword);

        userRepository.save(user.get());
    }*/


}
