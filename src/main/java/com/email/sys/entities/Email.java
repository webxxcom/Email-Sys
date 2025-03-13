package com.email.sys.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, updatable = false)
    private EmailContent emailContent;

    @Column(nullable = false)
    private boolean isStarred;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(0)")
    private LocalDateTime sendDate;

    @ManyToOne(optional = false)
    private User sender;

    @ManyToOne(optional = false)
    private User receiver;

    public Email(EmailContent emailContent, User sender, User receiver) {
        this.emailContent = emailContent;
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
}
