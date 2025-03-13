package com.email.sys.loaders;

import java.io.Serial;

public class LoadingException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1317383944963489071L;

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
