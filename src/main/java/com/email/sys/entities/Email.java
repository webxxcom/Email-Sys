package com.email.sys.entities;

import jakarta.persistence.*;

@Entity
public class Email {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, updatable = false)
    String text;

    @ManyToOne
    User sender;
}
