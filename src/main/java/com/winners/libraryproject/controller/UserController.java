package com.winners.libraryproject.controller;

import com.winners.libraryproject.dto.UserDTO;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

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

}
