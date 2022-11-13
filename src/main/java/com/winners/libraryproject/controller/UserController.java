package com.winners.libraryproject.controller;

import com.winners.libraryproject.dto.*;

import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.security.jwt.JwtUtils;
import com.winners.libraryproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping()
public class UserController {

    private final UserService userService;

    public AuthenticationManager authenticationManager;
    public JwtUtils jwtUtils;


    @PostMapping(path="/register")
    public ResponseEntity<Map<String, Boolean>> registerUser(@Valid @RequestBody User user){
        //TODO yeni bir UserDTO olusturmak gerekir
        userService.register(user);

        Map<String, Boolean> map = new HashMap<>();
        map.put("User registered successfully!", true);

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/users/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userService.getAllUsers();

        return new ResponseEntity<>(users,HttpStatus.OK);

    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO user = userService.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,UserDTO>> deleteUser(@PathVariable Long id){
        UserDTO user=userService.findById(id);
        userService.removeById(id);
        Map<String, UserDTO> map = new HashMap<>();
        map.put("user deleted successfully", user);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> createdUser(@RequestBody UserCreatedDTO userCreatedDTO){
        userService.userCreated(userCreatedDTO);

        Map<String, Boolean> map = new HashMap<>();
        map.put("User registered successfully!", true);

        return new ResponseEntity<>(map,HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO loginDTO) throws AuthException {

        userService.login(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/user/loans")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Page<UserToUserDTO>> getAllUserLoansByPage(@RequestParam("page") int page,
                                                                     @RequestParam("size") int size,
                                                                     @RequestParam("sort") String prop,
                                                                     @RequestParam("type") Sort.Direction type){

        Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
        Page<UserToUserDTO> userDTOPage=userService.getUserLoanPage(pageable);
        return ResponseEntity.ok(userDTOPage);

    }
    @GetMapping("/userspage")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Page> getAllUsersByPage(@RequestParam(required = false ,value="name") String name,
                                                  @RequestParam(required = false ,value="page") int page,
                                                  @RequestParam(required = false ,value="size") int size,
                                                  @RequestParam(required = false ,value="sort") String prop,
                                                  @RequestParam(required = false ,value="type") Sort.Direction type){
        Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
        Page userDTOPage=userService.getUsersPage(name,pageable);

        return ResponseEntity.ok(userDTOPage);
    }

    @PatchMapping("/user/{id}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> memberUpdate(@PathVariable Long id,@Valid @RequestBody UserUpdateDTO userUpdateDTO){
        userService.updateUser(id,userUpdateDTO);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
