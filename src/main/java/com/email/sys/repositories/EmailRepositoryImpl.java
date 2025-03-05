package com.email.sys.repositories;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class EmailRepositoryImpl implements EmailRepository {
    private final EntityManager entityManager;

    public EmailRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Email getFilteredInbox(Long userId, java.lang.String filter) {
        TypedQuery<Email> q = entityManager.createQuery(
                "select em from Email em where em.receiver.id=?1 and em.text like ?2",
                Email.class
        );
        q.setParameter(1, userId);
        q.setParameter(2, "%" + filter + "%");
        return q.getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<Email> getStarredEmails(User user) {
        TypedQuery<Email> query = entityManager.createQuery(
                        "select em from Email em where em.isStarred = true and em.receiver = :user",
                        Email.class
                );
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Email toggleEmailStar(Email email) {
        Objects.requireNonNull(email).toggleStarred();
        return email;
    }

    @Override
    @Transactional
    public Email save(Email email) {
        return entityManager.merge(email);
    }
}
