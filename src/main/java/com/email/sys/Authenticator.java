package com.email.sys;

public interface Authenticator<T> {
    boolean authorize(T what, T with);
}
