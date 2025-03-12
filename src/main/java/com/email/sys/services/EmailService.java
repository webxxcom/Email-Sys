package com.email.sys.services;

import com.email.sys.ForwardedMessageHandler;
import com.email.sys.Result;
import com.email.sys.entities.Email;
import com.email.sys.entities.EmailContent;
import com.email.sys.entities.ForwardedEmail;
import com.email.sys.entities.User;
import com.email.sys.repositories.EmailContentRepository;
import com.email.sys.repositories.EmailRepository;
import com.email.sys.repositories.ForwardedEmailsRepository;
import com.email.sys.repositories.UserRepository;
import jakarta.transaction.Transactional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EmailService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final EmailContentRepository emailContentRepository;
    private final ForwardedEmailsRepository forwardedEmailsRepository;
    private final ForwardedMessageHandler forwardedMessageHandler;

    public EmailService(EmailRepository emailRepository, UserRepository userRepository, EmailContentRepository emailContentRepository, ForwardedEmailsRepository forwardedEmailsRepository, ForwardedMessageHandler forwardedMessageHandler) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.emailContentRepository = emailContentRepository;
        this.forwardedEmailsRepository = forwardedEmailsRepository;
        this.forwardedMessageHandler = forwardedMessageHandler;
    }

    public Result<Email> toggleEmailStar(Email email) {
        try {
            return Result.ofSuccess(emailRepository.toggleEmailStar(email));
        } catch (Exception e) {
            return Result.ofError("Error toggling email star");
        }
    }

    public ObservableList<Email> getFilteredInboxForUser(User user, String filter) {
        return FXCollections.observableArrayList(
                emailRepository.getFilteredInbox(user, filter)
        );
    }

    public ObservableList<Email> getSpamEmailsForUser(User user) {
        //TODO implement spam emails
        return null;
    }

    public ObservableList<Email> getStarredMessagesForUser(User user) {
        return FXCollections.observableArrayList(
                emailRepository.getStarredEmails(user)
        );
    }

    @Transactional
    public Email createEmail(String header, String text, User sender, User receiver) {
        return new Email(new EmailContent(header, text), sender, receiver);
    }

    @Transactional
    public Result<Email> sendEmail(String header, String emailText, User sender, String receiverEmail) {
        /* Receiver must exist */
        Optional<User> optionalReceiver = userRepository.findByEmail(receiverEmail);
        if (optionalReceiver.isEmpty()) {
            return Result.ofError("User with such an email does not exist");
        }
        User receiver = optionalReceiver.get();

        return Result.ofSuccess(
                emailRepository.save(createEmail(header, emailText, sender, receiver)),
                "Message was successfully sent"
        );
    }

    public ObservableList<Email> getInboxForUser(User user) {
        Collection<Email> inboxForUser = emailRepository.getInboxForUser(user);
        Map<Long, ForwardedEmail> forwardedEmailsMap = forwardedEmailsRepository.getForUser(user).stream()
                .collect(Collectors.toMap(fe -> fe.getForwardedEmailsId().getEmailId(),
                        fe -> fe));

        for(Email email : inboxForUser){
            ForwardedEmail forwardedEmail = forwardedEmailsMap.get(email.getId());
            if (forwardedEmail != null) {
                forwardedMessageHandler.handle(email, forwardedEmail);
            }
        }

        return FXCollections.observableArrayList(inboxForUser);
    }

    public ObservableList<String> getAvailableEmails() {
        return FXCollections.observableArrayList(emailRepository.getAvailableEmails());
    }

    @Transactional
    public void forward(Email email, User forwarder, User forwardTo) {
        Email forward = emailRepository.forward(email, forwarder, forwardTo);
        forwardedEmailsRepository.save(new ForwardedEmail(forward, forwarder, forwardTo));
    }
}
