package com.email.sys.repositories;

import com.email.sys.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private final EntityManager em;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> q = em.createQuery(
                "select u from User u where u.email = :email", User.class
        );
        q.setParameter("email", email);

        return Optional.ofNullable(q.getResultList().stream().findFirst().orElse(null));
    }

    @Override
    @Transactional
    public User save(User user) {
        User merge = em.merge(user);
        log.info("Saved " + merge);
        return merge;
    }

    @Override
    public boolean userWithEmailExists(String email) {
        Query query = em.createQuery("select count(u) from User u where u.email = :email");
        query.setParameter("email", email);
        return ((long) query.getSingleResult()) > 0;
    }
}
