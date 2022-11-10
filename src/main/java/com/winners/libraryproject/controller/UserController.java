package com.winners.libraryproject.controller;

import com.winners.libraryproject.dto.UserDTO;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(path="/register")
    public ResponseEntity<Map<String, Boolean>> registerUser(@RequestBody User user){
        userService.register(user);

        Map<String, Boolean> map = new HashMap<>();
        map.put("User registered successfully!", true);

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userService.getAllUsers();

        return new ResponseEntity<>(users,HttpStatus.OK);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO user = userService.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String,UserDTO>> deleteUser(@PathVariable Long id){
        UserDTO user=userService.findById(id);
        userService.removeById(id);
        Map<String, UserDTO> map = new HashMap<>();
        map.put("user deleted successfully", user);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, Boolean>> createdUser(@RequestBody User user){
        userService.createdUser(user);

        Map<String, Boolean> map = new HashMap<>();
        map.put("User registered successfully!", true);

        return new ResponseEntity<>(map,HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userMap) throws AuthException {
        String email =  userMap.get("email");
        String password =  userMap.get("password");

        userService.login(email, password);


        return new ResponseEntity<>("login succesfully", HttpStatus.OK);
    }

}