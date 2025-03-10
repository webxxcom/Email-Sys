package com.email.sys.configurators;

import com.email.sys.controllers.DataInjectable;
import org.springframework.stereotype.Component;

@Component
public class DataInjectableConfigurator implements Configurator {
    private final ConfigStorage configStorage;

    public DataInjectableConfigurator(ConfigStorage configStorage) {
        this.configStorage = configStorage;
    }

    @Override
    public void configure(Object object) {
        if(object instanceof DataInjectable dataInjectable)
            dataInjectable.inject(configStorage);
    }
}
