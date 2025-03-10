package com.email.sys.configurators;

import org.springframework.stereotype.Component;

@Component
public interface Configurator {
    void configure(Object object);
}
