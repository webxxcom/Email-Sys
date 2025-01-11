package com.email.sys.services;

import com.email.sys.Result;
import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.lang.ref.Cleaner;

@Component
public class UserService implements Cleaner.Cleanable {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("emailSysUnit");
    private final EntityManager em = emf.createEntityManager();

    public User getForEmail(String email) {
        TypedQuery<User> q = em.createQuery(
                "select u from User u where u.email = ?1", User.class
        );
        q.setParameter(1, email);

        User user = q.getSingleResultOrNull();
        if (user == null) {
            throw new IllegalArgumentException("An error occurred when finding user with email " + email);
        }
        return user;
    }

    public boolean trySignUp(String email, String password){
        //TODO sign up
        return true;
    }

    public Result<User> tryLogIn(String email, String password){
        TypedQuery<User> q = em.createQuery(
                "select u from User u where email = :email", User.class
        );
        q.setParameter("email", email);

        User user = q.getSingleResultOrNull();
        if(user == null)
            return Result.error("Such email does not exist");

        if(!user.getPassword().equals(password))
            return Result.error("Password is incorrect");

        return Result.success(user);
    }

    @Override
    public void clean() {
        em.close();
        emf.close();
    }

    public ObservableList<Email> getInbox(long id) {
        Query q = em.createQuery("select u.emails from User u where u.id = ?1");
        q.setParameter(1, id);

        return FXCollections.observableArrayList(q.getResultList());
    }
}
