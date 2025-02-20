package com.email.sys.loaders;

public class LoadingException extends RuntimeException{
    public LoadingException() {
    }

    public LoadingException(String message) {
        super(message);
    }

    public LoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadingException(Throwable cause) {
        super(cause);
    }

    public LoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
