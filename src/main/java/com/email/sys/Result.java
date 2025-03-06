package com.email.sys;

import lombok.NonNull;

public record Result<T>(String message, T data) {
    public static <E> Result<E> ofError(String message) {
        return new Result<>(message, null);
    }

    public static <E> Result<E> ofSuccess(@NonNull E data) {
        return new Result<>(null, data);
    }

    public static <E> Result<E> ofSuccess(@NonNull E data, String message) {
        return new Result<>(message, data);
    }

    public boolean hasError() {
        return data == null;
    }

    public boolean isSuccess() {
        return !hasError();
    }
}
