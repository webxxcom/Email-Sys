package com.email.sys.repositories;

import com.email.sys.entities.Email;
import com.email.sys.entities.EmailContent;
import com.email.sys.entities.ForwardedEmail;
import com.email.sys.entities.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ForwardedEmailRepositoryTest {

    ForwardedEmail forwardedEmail;
    Email theEmail;
    User sender;
    User forwarder;
    User forwardedTo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private ForwardedEmailsRepository forwardedEmailsRepository;


    @BeforeEach
    void setUp(){
        sender = new User("sender@Emaik.com", "ASD");
        forwarder = new User("SomeEmail@email.com", "ADS");
        forwardedTo = new User("forwardedToEmail@Ema.com", "asd");
        userRepository.saveAllAndFlush(List.of(sender, forwardedTo, forwarder))
                .forEach(u -> assertNotNull(u.getId(), "Users were not saved to DB"));

        theEmail = new Email(new EmailContent("Email text is not absent"), sender, forwarder);
        assertNotNull(emailRepository.save(theEmail).getId(), "Email was not saved to DB");

        forwardedEmail = new ForwardedEmail(theEmail, forwarder, forwardedTo);
    }

    @Test
    void saveAndFindForwardedEmailTest(){
        ForwardedEmail save = forwardedEmailsRepository.save(forwardedEmail);
        List<ForwardedEmail> forwardedEmailList = (List<ForwardedEmail>) forwardedEmailsRepository.getForUser(forwardedTo);

        assertFalse(forwardedEmailList.isEmpty());
        assertEquals(save, forwardedEmailList.getFirst());
    }

    @Test
    void getForwardedForOnlyUserTest(){
        User anotherForwardedTo = new User("anotherFord@masd.com", "ASD");
        userRepository.save(anotherForwardedTo);

        ForwardedEmail save = forwardedEmailsRepository.save(forwardedEmail);
        ForwardedEmail anotherSave = forwardedEmailsRepository.save(new ForwardedEmail(theEmail, forwarder, anotherForwardedTo));
        List<ForwardedEmail> anotherForwardedEmailList = forwardedEmailsRepository.getForUser(anotherForwardedTo);
        assertFalse(anotherForwardedEmailList.isEmpty(), "The list of forwarded emails for another user should not be empty");
        assertEquals(1, anotherForwardedEmailList.size(), "The number of forwarded messages for another user should be 1");
        assertEquals(anotherSave, anotherForwardedEmailList.getFirst(), "The obtained forwarded email must be equal to the saved one");

        List<ForwardedEmail> forwardedEmailList = forwardedEmailsRepository.getForUser(forwardedTo);
        assertFalse(forwardedEmailList.isEmpty());
        assertEquals(1, forwardedEmailList.size());
        assertEquals(save, forwardedEmailList.getFirst());
    }
}
