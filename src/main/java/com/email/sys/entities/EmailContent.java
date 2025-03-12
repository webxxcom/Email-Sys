package com.email.sys.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Data @NoArgsConstructor
public class EmailContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String header;

    @Column(updatable = false, nullable = false)
    private String text;

    public EmailContent(String text) {
        this("", text);
    }

    public EmailContent(String header, String text) {
        this.header = header;
        this.text = text;
    }
}
