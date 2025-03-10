package com.email.sys.configurators;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfigKey {
    EMAIL(Email.class),
    USER(User.class);

    private final Class<?> clazz;
}
