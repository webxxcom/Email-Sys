package com.email.sys.services;

import com.email.sys.Result;
import com.email.sys.entities.User;
import com.email.sys.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("test")
class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void signUpUserIsCorrectTest(){
        Result<User> result = userService.trySignUp("gh0eriopa80agh89eir0agh89eioprw", "pass");

        assertTrue(result.isSuccess());
        User user = result.data();
        assertNotNull(user.getUserSettings(), "User settings were not created when signing up");
    }

    @Test
    void logInUser_MustHaveNotNullRelationsTest(){
        User u = new User("em", "pass");
        if (!userRepository.exists(Example.of(u))) {
            assertNotNull(userRepository.save(u).getId(), "User repository can't save an entity");
        }

        Result<User> userResult = userService.tryLogIn(u.getEmail(), u.getPassword());
        assertTrue(userResult.isSuccess(), "User was not logged in due to " + userResult.message());

        User user = userResult.data();
        assertNotNull(user.getId(), "User was logged in but not initialized with id");
        assertNotNull(user.getUserSettings(), "User settings were not obtained");
        assertEquals(u.getEmail(), user.getEmail());
    }
}
