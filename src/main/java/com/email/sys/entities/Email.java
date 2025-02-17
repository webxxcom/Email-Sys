package com.email.sys.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Email {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String header;

    @Column(nullable = false, updatable = false)
    String text;

    @Column(nullable = false)
    Boolean isStarred;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(0)")
    LocalDateTime sendDate;

    @ManyToOne(optional = false)
    User sender;

    @ManyToOne(optional = false)
    User receiver;

    public Email() {
    }

    public Email(String text, User sender, User receiver) {
        this("", text, sender, receiver);
    }

    public Email(String header, String text, User sender, User receiver){
        this.text = text;
        this.header = header;
        this.sender = sender;
        this.receiver = receiver;
    }

    @PrePersist
    private void beforePersisting(){
        this.sendDate = LocalDateTime.now();
        this.isStarred = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(Boolean starred) {
        isStarred = starred;
    }

    public void toggleStarred(){
        setStarred(!isStarred);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return Objects.equals(id, email.id) && Objects.equals(text, email.text) && Objects.equals(sender, email.sender) && Objects.equals(receiver, email.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, sender, receiver);
    }

    public User getSender() {
        return sender;
    }
}
