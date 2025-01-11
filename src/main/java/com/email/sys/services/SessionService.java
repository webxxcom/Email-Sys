package com.email.sys.services;

import com.email.sys.entities.User;
import org.springframework.stereotype.Component;

@Component
public class SessionService {

    User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
