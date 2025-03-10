package com.email.sys.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false, updatable = false)
    private String text;

    @Column(nullable = false)
    private boolean isStarred;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(0)")
    private LocalDateTime sendDate;

    @ManyToOne(optional = false)
    private User sender;

    @ManyToOne(optional = false)
    private User receiver;

    public Email(String text, User sender, User receiver) {
        this("", text, sender, receiver);
    }

    public Email(String header, String text, User sender, User receiver) {
        this.text = text;
        this.header = header;
        this.sender = sender;
        this.receiver = receiver;
    }

    @PrePersist
    private void beforePersisting() {
        if (sendDate == null) sendDate = LocalDateTime.now();
    }

    public void toggleStarred() {
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
}
