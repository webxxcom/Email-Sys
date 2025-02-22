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

    public Result<Email> toggleEmailStar(Email email) {
        try {
            em.getTransaction().begin();
            email.toggleStarred();
            em.getTransaction().commit();
            return Result.ofSuccess(email);
        } catch (Exception e) {
            return Result.ofError("Error toggling email star");
        }
    }

    public Result<User> trySignUp(String email, String password) {
        if (emailExists(email)) {
            return Result.ofError("User with such an email already exists");
        }

        em.getTransaction().begin();
        User user = em.merge(new User(email, password));
        em.getTransaction().commit();
        return Result.ofSuccess(user, "The registration was successful");
    }

    private boolean emailExists(String email) {
        Query query = em.createQuery("select 1 from User u where u.email = ?1");
        query.setParameter(1, email);
        return query.getSingleResultOrNull() != null;
    }

    public Result<User> tryLogIn(String email, String password) {
        TypedQuery<User> q = em.createQuery(
                "select u from User u where email = :email", User.class
        );
        q.setParameter("email", email);

        User user = q.getSingleResultOrNull();
        if (user == null) {
            return Result.ofError("Such email does not exist");
        }
        if (!user.getPassword().equals(password)) {
            return Result.ofError("Password is incorrect");
        }
        return Result.ofSuccess(user);
    }

    public Result<Email> sendEmail(String header, String emailText, User sender, String receiverEmail) {
        /* User with such email must exist */
        Optional<User> optionalReceiver = getForEmail(receiverEmail);
        if (optionalReceiver.isEmpty()) {
            return Result.ofError("User with such email does not exist");
        }
        User receiver = optionalReceiver.get();

        /* Can't send empty email */
        if (emailText.isEmpty()) {
            return Result.ofError("You can't send an email with empty body");
        }

        /* Persist email */
        em.getTransaction().begin();
        Email email = em.merge(new Email(header, emailText, sender, receiver));
        em.getTransaction().commit();

        sender.sendEmail(receiver, email);
        return Result.ofSuccess(email, "Message was successfully sent");
    }

    public Result<User> saveSettings(User user) {
        try {
            em.getTransaction().begin();
            Result<User> res = Result.ofSuccess(em.merge(user), "Settings were successfully saved");
            em.getTransaction().commit();

            return res;
        } catch (Exception e) {
            return Result.ofError("Settings were not saved because of some error");
        }
    }

    @Override
    public void clean() {
        em.close();
        emf.close();
    }

    public ObservableList<Email> getFilteredInbox(Long id, String filter) {
        Query q = em.createQuery("select em from Email em where em.receiver.id=?1 and em.text like ?2");
        q.setParameter(1, id);
        q.setParameter(2, "%" + filter + "%");

        return FXCollections.observableArrayList(q.getResultList());
    }
}
