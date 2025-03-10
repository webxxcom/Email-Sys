package com.email.sys.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class UserSettings {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    private User user;

    @Column(nullable = false)
    private boolean sendNotifications;

    public UserSettings(UserSettings other) {
        this.id = other.id;
        this.user = other.user;
        this.sendNotifications = other.sendNotifications;
    }

    @PrePersist
    void initialize() {
        sendNotifications = true;
    }
}
