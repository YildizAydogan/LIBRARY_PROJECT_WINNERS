package com.winners.libraryproject.controller;

import com.winners.libraryproject.dto.UserCreatedDTO;
import com.winners.libraryproject.dto.UserDTO;
import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.repository.UserRepository;
import com.winners.libraryproject.service.UserService;
import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping()
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;



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

    @Transactional
    @GetMapping("/users")
    public Object usersSearch(@And({
            @Spec(path = "page", params = "page", spec = EqualIgnoreCase.class),
            @Spec(path = "size", params = "size", spec = EqualIgnoreCase.class),
            @Spec(path = "sort", params = "date", spec = EqualIgnoreCase.class),
            @Spec(path = "type", params = "type", spec = EqualIgnoreCase.class),
        }) Specification<User> customerNameSpec){

     return userRepository.findAll((Sort) customerNameSpec);
    }





}
