package com.email.sys.repositories;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository {
    Email getFilteredInbox(Long userId, java.lang.String filter);

    List<Email> getStarredEmails(User user);

    Email toggleEmailStar(Email email);

    Email save(Email email);
}
