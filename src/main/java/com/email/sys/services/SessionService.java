package com.email.sys.services;

import com.email.sys.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.lang.ref.Cleaner;

@Setter @Getter
@Component
public class SessionService implements Cleaner.Cleanable {
    private User user;

    @Override
    public void clean() {
        user = null;
    }
}
