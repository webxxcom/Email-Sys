package com.email.sys.configurators;

import com.email.sys.controllers.DataInjectable;
import org.springframework.stereotype.Component;

@Component
public class DataInjectableConfigurator implements Configurator {
    @Override
    public void configure(Object object, ConfigStorage config) {
        if(!(object instanceof DataInjectable dataInjectable))
            throw new RuntimeException();

        dataInjectable.inject(config);
    }

}
