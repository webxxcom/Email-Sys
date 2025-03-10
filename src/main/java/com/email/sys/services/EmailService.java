package com.email.sys.services;

import com.email.sys.Result;
import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import com.email.sys.repositories.EmailRepository;
import com.email.sys.repositories.UserRepository;
import jakarta.transaction.Transactional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmailService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;

    public EmailService(EmailRepository emailRepository, UserRepository userRepository) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
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

    public Email createEmail(String header, String text, User sender, User receiver){
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

    public ObservableList<Email> getInboxForUser(User user) {
        return FXCollections.observableArrayList(emailRepository.getInboxForUser(user));
    }

    public ObservableList<String> getAvailableEmails() {
        return FXCollections.observableArrayList(emailRepository.getAvailableEmails());
    }
}
