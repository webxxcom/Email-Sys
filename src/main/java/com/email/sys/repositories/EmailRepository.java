package com.email.sys.repositories;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository {
    List<Email> getFilteredInbox(User user, String filter);

    List<Email> getStarredEmails(User user);

    Email toggleEmailStar(Email email);

    Email save(Email email);
    List<Email> saveAll(List<Email> email);

    Optional<Email> find(Email mockEmail1);

    long count(User user);

    List<Email> getEmailsForUser(User user);

    void remove(Email mockEmail1);

    Collection<Email> getInboxForUser(User user);

    Collection<String> getAvailableEmails();
}
