package com.email.sys.repositories;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class EmailRepositoryTest {

    @Autowired
    private EmailRepository emailRepository;

    User u1, u2, u3;
    Email mockEmail1, mockEmail2;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        u1 = new User("sender@mail.com", "ad");
        u2 = new User("receiver@mail.com", "d2");
        u3 = new User("anothere@ma.com", "asd");
        userRepository
                .saveAll(List.of(u1, u2, u3))
                .forEach(u ->
                                assertNotNull(u.getId(),
                                "Some of the users was not saved"));

        mockEmail1 = new Email("Email with some text", u2, u1);
        mockEmail2 = new Email("Another email but with no text", u3, u1);
        emailRepository
                .saveAll(List.of(mockEmail1, mockEmail2))
                .forEach(el ->
                        assertNotNull(el.getId(),
                                "Some of the email was not saved in DB"));
    }

    @Test
    void saveOptionalTest(){
        mockEmail1 = emailRepository.save(mockEmail1);

        Optional<Email> email = emailRepository.find(mockEmail1);
        assertTrue(email.isPresent());
        assertEquals(mockEmail1, email.get());
    }

    @Test
    void removeEmailTest(){
        mockEmail1 = emailRepository.save(mockEmail1);
        assertTrue(emailRepository.find(mockEmail1).isPresent());

        emailRepository.remove(mockEmail1);
        assertTrue(emailRepository.find(mockEmail1).isEmpty());
    }

    @Test
    void saveAllTransaction_ShouldRollbackOnFailure() {
        Email invalidEmail = new Email("Invalid", u1, null);

        List<Email> emails = List.of(mockEmail1, mockEmail2, invalidEmail);
        assertThrows(DataIntegrityViolationException.class,
                () -> emailRepository.saveAll(emails));
    }

    @Test
    void findEmailTest(){
        Optional<Email> email = emailRepository.find(mockEmail1);
        assertTrue(email.isPresent());
        assertEquals(mockEmail1, email.get());
    }

    @Test
    void getFilteredInboxTest(){
        String filter = "some";

        List<Email> filteredInbox = emailRepository.getFilteredInbox(u1, filter);
        assertFalse(filteredInbox.isEmpty());
        assertTrue(filteredInbox.getFirst().getText().contains(filter));
        assertEquals(1, filteredInbox.size());
    }

    @Test
    void toggleStarEmailTest(){
        boolean before = mockEmail1.isStarred();
        Email e = emailRepository.toggleEmailStar(mockEmail1);
        assertNotEquals(before, e.isStarred());
    }
}
