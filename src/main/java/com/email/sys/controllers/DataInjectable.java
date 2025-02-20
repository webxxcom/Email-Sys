package com.email.sys.controllers;

public interface DataInjectable<T> {
    void inject(T data);
    void init();
}
