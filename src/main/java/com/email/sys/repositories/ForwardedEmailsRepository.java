package com.email.sys.repositories;

import com.email.sys.entities.ForwardedEmail;
import com.email.sys.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ForwardedEmailsRepository {

    private final EntityManager entityManager;

    public ForwardedEmailsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public ForwardedEmail save(@NonNull ForwardedEmail fe){
        return entityManager.merge(fe);
    }

    public List<ForwardedEmail> getForUser(@NonNull User user){
        return entityManager.createQuery(
                        "select fe from ForwardedEmail fe where fe.forwardedEmailsId.forwardedToId = :id",
                        ForwardedEmail.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

}
