package com.email.sys.services;

import com.email.sys.ForwardedMessageHandler;
import com.email.sys.Result;
import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import com.email.sys.repositories.EmailRepository;
import com.email.sys.repositories.UserRepository;
import jakarta.transaction.Transactional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EmailService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final ForwardedMessageHandler forwardedMessageHandler;

    public EmailService(EmailRepository emailRepository, UserRepository userRepository, ForwardedMessageHandler forwardedMessageHandler) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
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

    public ObservableList<Email> getInboxForUser(User user) {
        Collection<Email> inboxEmails = emailRepository.getInboxForUser(user);

        return FXCollections.observableArrayList(inboxEmails
                .stream()
                .sorted((o1, o2) -> o2.getSendDate().compareTo(o1.getSendDate()))
                .toList()
        );
    }

    public ObservableList<String> getAvailableEmails() {
        return FXCollections.observableArrayList(emailRepository.getAvailableEmails());
    }

    public ObservableList<Email> getSentForUser(@NonNull User user) {
        return FXCollections.observableArrayList(emailRepository.getSentForUser(user));
    }

    @Transactional
    public Result<Email> forward(Email email, User forwarder, User forwardTo) {
        if(email == null){
            return Result.ofError("No email was chosen to forward.");
        } else if(forwarder == null){
            return Result.ofError("The current user session is invalid.");
        } else if(forwardTo == null){
            return Result.ofError("Please specify the email recipient");
        }

        Email save = emailRepository.save(forwardedMessageHandler.handle(email, forwarder, forwardTo));
        return Result.ofSuccess(save, "Email was successfully forwarded");
    }

    @Transactional
    public Email createEmail(String header, String text, User sender, User receiver) {
        return new Email(header, text, sender, receiver);
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

    @Transactional
    public Result<Email> sendEmail(Email email) {
        return Result.ofSuccess(
                emailRepository.save(email),
                "Message was successfully sent"
        );
    }
}
