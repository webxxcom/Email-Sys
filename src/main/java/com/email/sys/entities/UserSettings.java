package com.email.sys.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class UserSettings {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    User user;

    @Column(nullable = false)
    boolean sendNotifications;

    public UserSettings(){

    }

    public UserSettings(UserSettings other) {
        this.id = other.id;
        this.user = other.user;
        this.sendNotifications = other.sendNotifications;
    }

    @PrePersist
    void initialize(){
        sendNotifications = true;
    }

    public Long getId() {
        return id;
    }

    public boolean isSendNotifications() {
        return sendNotifications;
    }

    public void setSendNotifications(boolean sendNotifications) {
        this.sendNotifications = sendNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSettings that)) return false;
        return sendNotifications == that.sendNotifications
                && Objects.equals(id, that.id)
                && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, sendNotifications);
    }
}
