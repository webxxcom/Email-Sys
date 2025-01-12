package com.email.sys.services;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.lang.ref.Cleaner;

@Component
public class EmailService implements Cleaner.Cleanable {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("emailSysUnit");
    private final EntityManager em = emf.createEntityManager();


    public void addEmail(Email email){

    }


    @Override
    public void clean() {
        em.close();
        emf.close();
    }
}
