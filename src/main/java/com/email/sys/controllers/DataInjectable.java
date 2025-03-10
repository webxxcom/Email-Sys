package com.email.sys.controllers;

import com.email.sys.configurators.ConfigStorage;

public interface DataInjectable {
    void inject(ConfigStorage data);

    void init();
}
