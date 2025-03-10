package com.email.sys.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor @Setter @Getter
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDate createdOn;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserSettings userSettings;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatar;

    @OneToMany(mappedBy = "sender")
    private List<Email> sentEmails;

    @OneToMany(mappedBy = "receiver")
    private List<Email> inboxEmails;

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
    public java.lang.String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
