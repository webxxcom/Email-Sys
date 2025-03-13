package com.email.sys.configurators;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component @ToString @EqualsAndHashCode
public class ConfigStorage {

    private final Map<ConfigKey, Object> config = new HashMap<>();

    public void add(ConfigKey name, Object object) {
        config.put(name, object);
    }

    @SuppressWarnings("unchecked")
    public <T> T getIfPresent(ConfigKey name) {
        return ((T) config.getOrDefault(name, null));
    }

    public void remove(ConfigKey key) {
        config.remove(key);
    }
}
