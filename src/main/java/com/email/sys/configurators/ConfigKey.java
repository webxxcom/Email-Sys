package com.email.sys.configurators;

import com.email.sys.entities.Email;
import com.email.sys.entities.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ConfigKey {
    EMAIL(Email.class),
    USER(User.class);

    private final Class<?> clazz;
}
