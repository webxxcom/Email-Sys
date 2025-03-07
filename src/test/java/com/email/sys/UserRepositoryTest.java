package com.email.sys;

import com.email.sys.entities.User;
import com.email.sys.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final User mockUser = new User("Email@Email.com", "pass");

    @BeforeEach
    void setUp(){
        User savedUser = userRepository.save(mockUser);

        assertNotNull(savedUser.getId(), "User ID should not be null after saving");
        assertEquals(mockUser.getEmail(), savedUser.getEmail(), "User email should be the same after saving");
    }

    @Test
    void testGetForEmail(){
        Optional<User> optionalUser = userRepository.findByEmail(mockUser.getEmail());
        assertFalse(optionalUser.isEmpty());
        assertEquals(mockUser.getEmail(), optionalUser.get().getEmail());
    }

    @Test
    void testGetForEmailAbsent(){
        Optional<User> optionalUser = userRepository.findByEmail("emailWhichDoesNotExist@cool.exe");
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void testUserWithEmailExists(){
        assertTrue(userRepository.userWithEmailExists(mockUser.getEmail()));
    }

    @Test
    void testUserWithEmailNoExists(){
        assertFalse(userRepository.userWithEmailExists("9sghysdf g789 sghd7fsdfgh9sdghf"));
        assertFalse(userRepository.userWithEmailExists(""));
        assertFalse(userRepository.userWithEmailExists("           "));
        assertFalse(userRepository.userWithEmailExists("emailWithNoAt"));
    }
}
