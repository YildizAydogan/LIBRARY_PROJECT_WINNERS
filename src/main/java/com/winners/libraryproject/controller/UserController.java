package com.winners.libraryproject.controller;

import com.winners.libraryproject.dto.UserCreatedDTO;
import com.winners.libraryproject.dto.UserDTO;

import com.winners.libraryproject.dto.UserToUserDTO;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
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

    @PostMapping("/users")
    public ResponseEntity<Map<String, Boolean>> createdUser(@RequestBody UserCreatedDTO userCreatedDTO){
        userService.userCreated(userCreatedDTO);

        Map<String, Boolean> map = new HashMap<>();
        map.put("User registered successfully!", true);

        return new ResponseEntity<>(map,HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userMap) throws AuthException {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        userService.login(email, password);


        return new ResponseEntity<>("login succesfully", HttpStatus.OK);
    }

    @GetMapping("/user/loans")
    public ResponseEntity<Page<UserToUserDTO>> getAllUserLoansByPage(@RequestParam("page") int page,
                                                                     @RequestParam("size") int size,
                                                                     @RequestParam("sort") String prop,
                                                                     @RequestParam("type") Sort.Direction type){

        Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
        Page<UserToUserDTO> userDTOPage=userService.getUserLoanPage(pageable);
        return ResponseEntity.ok(userDTOPage);

    }
    @GetMapping("/users1")

    public ResponseEntity<Page> getAllUsersByPage(@RequestParam(required = false ,value="name") String name,
                                                  @RequestParam(required = false ,value="page") int page,
                                                  @RequestParam(required = false ,value="size") int size,
                                                  @RequestParam(required = false ,value="sort") String prop,
                                                  @RequestParam(required = false ,value="type") Sort.Direction type){
        Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
        Page userDTOPage=userService.getUsersPage(name,pageable);

        return ResponseEntity.ok(userDTOPage);
    }
}
