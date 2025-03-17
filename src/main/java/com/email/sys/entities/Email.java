package com.email.sys.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Email{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String header;

    @Column(updatable = false, nullable = false, length = 3000)
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
        this.header = header;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Email(@NonNull Email email){
        this(email.getHeader(), email.getText(), email.getSender(), email.getReceiver());
    }

    @PrePersist
    private void beforePersisting() {
        if (sendDate == null) sendDate = LocalDateTime.now();
    }

    public void toggleStarred() {
        setStarred(!isStarred);
    }
}
