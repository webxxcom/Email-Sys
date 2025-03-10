package com.email.sys;

import org.springframework.stereotype.Component;

@Component
public class PasswordAuthenticator implements Authenticator<String> {
    @Override
    public boolean authorize(String password, String correctPassword) {
        return password.equals(correctPassword);
    }
}
