package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.User;
import com.winners.libraryproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    String name="Hasan Basri test01";
    Long userId;
    @Test
    public void register() throws ParseException {
        User user=new User();
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        user.setFirstName(name);
        user.setLastName("Hasan Basri test01");
        user.setScore(1);
        user.setAddress("Hasan Basri test01");
        user.setPhone("(541) 317-8828");
        user.setBirthDate(simpleDateFormat.parse("12-01-2013"));
        user.setEmail("hasanbasri@mynet.com");
        user.setPassword("$2a$10$m6sGgv3WQJP0aM1uWYpZfeuKA153q2h66G6tVt6OdYZw1VEKi4Au6");
        user.setCreateDate(LocalDateTime.now());
        user.setResetPasswordCode("fawer");
        user.setBuiltIn(false);

        repository.save(user);
        Optional<User> user1=repository.findByEmail("hasanbasri@mynet.com");
        userId=user1.get().getId();
        System.out.println(user1.get().getFirstName());
        System.out.println(userId);
        assertEquals(user1.get().getFirstName(),user.getFirstName());
    }

    @Test
    public void findById(){
        Long id=21L;
        System.out.println(userId);
        Optional<User> user=repository.findById(id);

        String responseEmail=user.get().getEmail();
        System.out.println(responseEmail);

        assertTrue(responseEmail.equals("hsn@gmail.com"));
    }

    @Test
    public void getAllUsers(){
        List<User> user=repository.findAll();

        System.out.println(user.size());

        assertTrue(user.size()>0);
    }

    @Test
    public void removeById(){
        repository.deleteById(36L);
       // Optional<User> user=repository.findById(userId);


    }


}
