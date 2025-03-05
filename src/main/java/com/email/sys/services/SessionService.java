package com.email.sys.services;

import com.email.sys.entities.User;
import org.springframework.stereotype.Component;

import java.lang.ref.Cleaner;

@Component
public class SessionService implements Cleaner.Cleanable {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void clean() {
        user = null;
    }
}
