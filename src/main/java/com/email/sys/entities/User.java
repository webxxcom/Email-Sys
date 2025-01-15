package com.email.sys.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    LocalDate createdOn;

    @OneToMany(mappedBy = "sender")
    private List<Email> sentEmails;

    @OneToMany(mappedBy = "receiver")
    private List<Email> inboxEmails;

    public User(){

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @PrePersist
    private void beforePersisting() {
        this.createdOn = LocalDateTime.now().toLocalDate();

    }

    public void sendEmail(User to, Email email){
        Objects.requireNonNull(email);
        Objects.requireNonNull(to);

        sentEmails.add(email);
        to.inboxEmails.add(email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Email> getSentEmails() {
        return sentEmails;
    }

    public void setSentEmails(List<Email> sentEmails) {
        this.sentEmails = sentEmails;
    }

    public List<Email> getInboxEmails() {
        return inboxEmails;
    }

    public void setInboxEmails(List<Email> inboxEmails) {
        this.inboxEmails = inboxEmails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(sentEmails, user.sentEmails) && Objects.equals(inboxEmails, user.inboxEmails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, sentEmails, inboxEmails);
    }
}
