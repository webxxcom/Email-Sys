package com.email.sys.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    UserSettings userSettings;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatar;

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

    public User(User other) {
        this.id = other.id;
        this.email = other.email;
        this.password = other.password;
        this.createdOn = other.createdOn;
        this.avatar = other.avatar;
        this.sentEmails = other.sentEmails;
        this.inboxEmails = other.inboxEmails;
        this.userSettings = new UserSettings(other.userSettings);
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

    public byte[] getAvatarBytes() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(createdOn, user.createdOn)
                && Objects.equals(userSettings, user.userSettings)
                && Objects.deepEquals(avatar, user.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, createdOn, userSettings, Arrays.hashCode(avatar));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdOn=" + createdOn +
                ", avatar=" + Arrays.toString(avatar) +
                ", sentEmails=" + sentEmails +
                ", inboxEmails=" + inboxEmails +
                '}';
    }
}
