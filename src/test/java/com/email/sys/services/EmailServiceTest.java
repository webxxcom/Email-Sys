package com.email.sys.services;

import com.email.sys.ForwardedMessageHandler;
import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import com.email.sys.repositories.EmailRepository;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @MockitoBean
    private EmailRepository emailRepository;
    @Autowired
    private ForwardedMessageHandler forwardedMessageHandler;

    @Test
    void testGetFilteredInbox(){
        String filterText = "important";

        Email email1 = new Email();
        email1.setText("Some important message");

        Email email2 = new Email();
        email2.setText("Random spam");

        List<Email> emails = List.of(email1, email2);

        when(emailRepository.getFilteredInbox(any(), anyString())).thenReturn(emails);

        User user = new User();
        user.setId(1L);
        user.setInboxEmails(new HashSet<>(emails));
        ObservableList<Email> filteredInbox = emailService.getFilteredInboxForUser(
                user, filterText
        );
        assertFalse(filteredInbox.isEmpty());
        assertFalse(filteredInbox.filtered(
                el -> el.getText().contains(filterText)).isEmpty()
        );
        verify(emailRepository, times(1)).getFilteredInbox(any(), anyString());
    }

    @Test
    void testIsStarredEmails(){
        Email e1 = new Email();
        e1.setId(1L);
        e1.setStarred(false);

        Email e2 = new Email();
        e2.setId(2L);
        e2.setStarred(true);

        Set<Email> emails = Set.of(e1, e2);
        User u = new User();
        u.setInboxEmails(emails);

        when(emailRepository.getStarredEmails(u)).thenReturn(List.of(e2));

        ObservableList<Email> starredMessagesForUser = emailService.getStarredMessagesForUser(u);
        assertFalse(starredMessagesForUser.isEmpty());
        assertEquals(1, starredMessagesForUser.size());
        assertEquals(e2, starredMessagesForUser.getFirst());
        verify(emailRepository, times(1)).getStarredEmails(u);
    }
}
