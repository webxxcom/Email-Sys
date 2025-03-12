package com.email.sys.repositories;

import com.email.sys.entities.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final User mockUser1 = new User("Email@Email.com", "pass");
    private final User mockUser2 = new User("cool@hj.oi", "pass");

    @BeforeEach
    void setUp(){
        User savedUser = userRepository.save(mockUser1);

        assertNotNull(savedUser.getId(), "User ID should not be null after saving");
        assertEquals(mockUser1.getEmail(), savedUser.getEmail(), "User email should be the same after saving");
        userRepository.save(mockUser2);
    }

    @Test
    void testGetForEmail(){
        Optional<User> optionalUser = userRepository.findByEmail(mockUser1.getEmail());
        assertFalse(optionalUser.isEmpty());
        assertEquals(mockUser1.getEmail(), optionalUser.get().getEmail());
    }

    @Test
    void testGetForEmailAbsent(){
        Optional<User> optionalUser = userRepository.findByEmail("emailWhichDoesNotExist@cool.exe");
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void testUserWithEmailExists(){
        assertTrue(userRepository.userWithEmailExists(mockUser1.getEmail()));
    }

    @Test
    void testUserWithEmailNoExists(){
        assertFalse(userRepository.userWithEmailExists("9sghysdf g789 sghd7fsdfgh9sdghf"));
        assertFalse(userRepository.userWithEmailExists(""));
        assertFalse(userRepository.userWithEmailExists("           "));
        assertFalse(userRepository.userWithEmailExists("emailWithNoAt"));
    }

    @Test
    void getForUserEmailLike(){
        String emailLike = "coo";
        Collection<User> emailsLike = userRepository.getUsersWithEmailLike(emailLike);
        Optional<User> optional = emailsLike.stream().findFirst();
        assertTrue(optional.isPresent());
        assertTrue(optional.get().getEmail().contains(emailLike));
        assertEquals(1, emailsLike.size());
    }
}
