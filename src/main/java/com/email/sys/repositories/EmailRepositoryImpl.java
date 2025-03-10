package com.email.sys.repositories;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class EmailRepositoryImpl implements EmailRepository {
    private final EntityManager entityManager;
    private final EmailRepositoryImpl self;

    public EmailRepositoryImpl(EntityManager entityManager, @Lazy EmailRepositoryImpl self) {
        this.entityManager = entityManager;
        this.self = self;
    }

    @Override
    public List<Email> getFilteredInbox(User user, String filter) {
        TypedQuery<Email> q = entityManager.createQuery(
                "select em from Email em where em.receiver=:user and em.text like :filter",
                Email.class
        );
        q.setParameter("user", user);
        q.setParameter("filter", "%" + filter + "%");
        return q.getResultList();
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
    public Email toggleEmailStar(@NonNull Email email) {
        Email e = entityManager.find(Email.class, email.getId());
        Objects.requireNonNull(e).toggleStarred();
        email.setStarred(e.isStarred());
        return email;
    }

    @Override
    @Transactional
    public Email save(@NonNull Email email) {
        if (email.getId() == null) {
            entityManager.persist(email);
            return email;
        }
        return entityManager.merge(email);
    }

    @Override
    @Transactional
    public List<Email> saveAll(@NonNull List<Email> email) {
        return email.stream()
                .map(self::save)
                .toList();
    }

    @Override
    public Optional<Email> find(Email email) {
        return Optional.ofNullable(entityManager.find(Email.class, email.getId()));
    }

    @Override
    public long count(User user) {
        TypedQuery<Long> q = entityManager.createQuery(
                "select count(e) from Email e where e.receiver = :user",
                Long.class
        );
        q.setParameter("user", user);
        return q.getResultStream().toList().getFirst();
    }

    @Override
    public List<Email> getEmailsForUser(User user) {
        TypedQuery<Email> query = entityManager.createQuery(
                "select e from Email e where e.receiver = :user",
                Email.class
        );
        return query
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public void remove(Email email) {
        entityManager.remove(email);
    }

    @Override
    public Collection<Email> getInboxForUser(User user) {
        return entityManager
                .createQuery("select e from Email e where e.receiver = :user", Email.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Collection<String> getAvailableEmails() {
        return entityManager.createQuery("select distinct e.receiver.email from Email e", String.class)
                .setMaxResults(10)
                .getResultList();
    }
}
