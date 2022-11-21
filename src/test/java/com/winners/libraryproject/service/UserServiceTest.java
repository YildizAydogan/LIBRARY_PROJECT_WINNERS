package com.winners.libraryproject.service;


import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.repository.RoleRepository;
import com.winners.libraryproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    String name="Test_Name";
    Long userId;

    @Test
    void register() throws ParseException {
        User user=new User();
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        user.setFirstName(name);
        user.setLastName("Test_LastName");
        user.setScore(1);
        user.setAddress("Test_Adress");
        user.setPhone("(541) 317-8828");
        user.setBirthDate(simpleDateFormat.parse("12-01-2013"));
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        user.setCreateDate(LocalDateTime.now());
        user.setResetPasswordCode("fawer");
        user.setBuiltIn(false);

       User userMock= Mockito.mock(User.class);

       //Mockito.when(userMock.getId()).thenReturn(1L);
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

       User result= userService.register(user);

        System.out.println(result.getFirstName());
       assertEquals(result.getFirstName(),user.getFirstName());
       //assertEquals(result.getId(),1L);


    }

    @Test
    void getAllUsers() {
       // Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList());
        List<User> user=userService.getAllUsers();

        System.out.println(user.size());

        assertTrue(user.size()==0);
    }

    @Test
    void findById() {

    }

    @Test
    void removeById() {
    }

    @Test
    void login() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void userCreated() {
    }


}