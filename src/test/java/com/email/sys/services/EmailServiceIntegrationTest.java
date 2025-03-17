package com.email.sys.services;

import com.email.sys.ForwardedMessageHandler;
import com.email.sys.Result;
import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import com.email.sys.repositories.EmailRepository;
import com.email.sys.repositories.UserRepository;
import jakarta.transaction.Transactional;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Rollback
class EmailServiceIntegrationTest {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private ForwardedMessageHandler forwardedMessageHandler;

    User us1, us2, us3;
    Email em1;

    @BeforeEach
    void setUp(){
        us1 = new User("us1", "pa");
        us2 = new User("us2", "pa");
        us3 = new User("us3", "pa");
        userRepository.saveAll(List.of(us1, us2, us3)).forEach(us -> assertNotNull(us.getId()));
        Result<Email> emailResult = emailService.sendEmail(
                new Email("Greetings", "Hello!!", us1, us2)
        );
        assertTrue(emailResult.isSuccess());
        em1 = emailResult.data();
    }

    @Test
    void forwardSingleEmailTest(){
        Result<Email> forward = emailService.forward(em1, us2, us3);
        assertTrue(forward.isSuccess());

        Email forwardedEmail = forward.data();
        Email expectedForwardedEmail = forwardedMessageHandler.handle(em1, us2, us3);
        assertEquals(expectedForwardedEmail.getHeader(), forwardedEmail.getHeader());
        assertEquals(expectedForwardedEmail.getText(), forwardedEmail.getText());
    }

    @Test
    void forwardMultipleEmailsTest(){
        int repeatTimes = 10;
        List<User> users = List.of(us1, us2, us3);
        Email email = em1;
        for(int i = 1; i < repeatTimes; ++i) {
            User u1 = users.get(i % users.size());
            User u2 = users.get((i + 1) % users.size());
            Result<Email> forward = emailService.forward(email, u1, u2);
            assertTrue(forward.isSuccess());

            Email forwardedEmail = forward.data();
            Email expectedForwardedEmail = forwardedMessageHandler.handle(email, u1, u2);
            assertEquals(expectedForwardedEmail.getHeader(), forwardedEmail.getHeader());
            assertEquals(expectedForwardedEmail.getText(), forwardedEmail.getText());
            assertEquals(1, forwardedEmail.getHeader().split
                            (ForwardedMessageHandler.HEADER_APPEND, -1).length - 1,
                    "Header should contain 'FWD: ' only once");

            String expectedBodyPrefix = MessageFormat.format(
                    ForwardedMessageHandler.BODY_TEMPLATE,
                    email.getSendDate().toString(),
                    email.getSender().getEmail(),
                    email.getReceiver().getEmail()
            );
            assertTrue(forwardedEmail.getText().startsWith(expectedBodyPrefix),
                    "Forwarded email body does not start correctly");
            assertTrue(forwardedEmail.getText().endsWith(email.getText()),
                    "Original email content is missing or altered");

            email = forwardedEmail;
        }
    }

    @Test
    void getInboxForUser_shouldReturnSortedEmails() {
        // Arrange: Create sample emails with different send dates
        Email email1 = new Email("Header1", "Message 1", us2, us1);
        email1.setSendDate(LocalDateTime.of(2024, 3, 1, 12, 0));

        Email email2 = new Email("Header2", "Message 2", us3, us1);
        email2.setSendDate(LocalDateTime.of(2024, 3, 2, 14, 30));

        Email email3 = new Email("Header3", "Message 3", us3, us1);
        email3.setSendDate(LocalDateTime.of(2024, 3, 1, 18, 45));

        List<Email> emails = List.of(email1, email2, email3);
        emailRepository.saveAll(emails);

        ObservableList<Email> inbox = emailService.getInboxForUser(us1);

        assertEquals(3, inbox.size());
        assertEquals(email2, inbox.get(0), "Newest email should be first");
        assertEquals(email3, inbox.get(1), "Second newest email should be second");
        assertEquals(email1, inbox.get(2), "Oldest email should be last");
    }

    @Test
    void getInboxForUser_shouldReturnEmptyListWhenNoEmails() {
        ObservableList<Email> inbox = emailService.getInboxForUser(us1);
        assertTrue(inbox.isEmpty(), "Inbox should be empty for a user with no emails");
    }
}
