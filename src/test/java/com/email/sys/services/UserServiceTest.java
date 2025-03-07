package com.email.sys.services;

import com.email.sys.PasswordAuthenticator;
import com.email.sys.Result;
import com.email.sys.entities.User;
import com.email.sys.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoSpyBean
    private PasswordAuthenticator passwordAuthenticator;

    @Test
    void testUserFoundForEmail(){
        String email = "someEmail@email.com";
        User mockUser = new User(email, "password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

        Optional<User> optionalUser = userService.getForEmail(email);

        assertTrue(optionalUser.isPresent());

        User user = optionalUser.get();
        assertEquals(mockUser, user);
    }

    @Test
    void testUserNotFoundForEmail(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Optional<User> forEmail = userService.getForEmail("any@email.com");
        assertTrue(forEmail.isEmpty());
    }

    @Test
    void testTryLogInSuccess(){
        String email = "email@email.com";
        String password = "password";
        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        Result<User> result = userService.tryLogIn(email, password);
        assertTrue(result.isSuccess());
    }

    @Test
    void testLogInNoEmail(){
        String email = "";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        Result<User> result = userService.tryLogIn(email, "somePass");
        assertTrue(result.hasError(), "When user tries to log in with non-existent email then the result must be an error");
    }

    @Test
    void testLogInWrongPassword(){
        String email = "email@email.com";
        String incorrectPassword = "196726012";
        User u = new User();
        u.setEmail(email);
        u.setPassword("123456789");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(u));
        when(passwordAuthenticator.authorize(incorrectPassword, u.getPassword()))
                .thenReturn(false);

        Result<User> result = userService.tryLogIn(email, incorrectPassword);
        assertTrue(result.hasError());
    }
}
