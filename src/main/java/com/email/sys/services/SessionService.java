package com.email.sys.services;

import com.email.sys.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionService {

    User user;
    UserService us;

    @Autowired
    public SessionService(UserService us) {
        this.us = us;
        this.user = us.getForEmail("em");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
