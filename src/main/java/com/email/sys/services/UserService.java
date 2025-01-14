package com.email.sys.services;

import com.email.sys.Result;
import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import jakarta.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.lang.ref.Cleaner;
import java.util.Optional;

@Component
public class UserService implements Cleaner.Cleanable {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("emailSysUnit");
    private final EntityManager em = emf.createEntityManager();

    public Optional<User> getForEmail(String email) {
        TypedQuery<User> q = em.createQuery(
                "select u from User u where u.email = ?1", User.class
        );
        q.setParameter(1, email);
        return Optional.ofNullable(q.getSingleResultOrNull());
    }

    public Result<User> trySignUp(String email, String password, String confirmPassword){
        if(!password.equals(confirmPassword)){
            return Result.ofError("Passwords do not match");
        }
        if(emailExists(email)){
            return Result.ofError("User with such an email already exists");
        }

        em.getTransaction().begin();
        User user = em.merge(new User(email, password));
        em.getTransaction().commit();
        return Result.of(user);
    }

    private boolean emailExists(String email) {
        Query query = em.createQuery("select 1 from User u where u.email = ?1");
        query.setParameter(1, email);
        return query.getSingleResultOrNull() != null;
    }

    public Result<User> tryLogIn(String email, String password){
        TypedQuery<User> q = em.createQuery(
                "select u from User u where email = :email", User.class
        );
        q.setParameter("email", email);

        User user = q.getSingleResultOrNull();
        if(user == null) {
            return Result.ofError("Such email does not exist");
        }
        if(!user.getPassword().equals(password)) {
            return Result.ofError("Password is incorrect");
        }
        return Result.of(user);
    }

    public void sendEmail(User sender, String receiverEmail, String emailText){
        //TODO check if email exists
        User receiver = getForEmail(receiverEmail).get();

        em.getTransaction().begin();
        Email email = em.merge(new Email(emailText, sender, receiver));
        em.getTransaction().commit();
        System.out.println(email);

        sender.sendEmail(receiver, email);
    }

    @Override
    public void clean() {
        em.close();
        emf.close();
    }

    public ObservableList<Email> getInbox(long id) {
        Query q = em.createQuery("select u.inboxEmails from User u where u.id = ?1");
        q.setParameter(1, id);

        return FXCollections.observableArrayList(q.getResultList());
    }

    public ObservableList<Email> getSent(Long id) {
        Query q = em.createQuery("select u.sentEmails from User u where id = ?1");
        q.setParameter(1, id);

        return FXCollections.observableArrayList(q.getResultList());
    }
}
