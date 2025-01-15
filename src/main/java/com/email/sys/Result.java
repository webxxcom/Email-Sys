package com.email.sys;

public class Result<T> {
    private final String message;
    private final T data;

    private Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <E> Result<E> ofError(String message){
        return new Result<>(message, null);
    }

    public static <E> Result<E> ofSuccess(E data){
        return new Result<>(null, data);
    }

    public static <E> Result<E> ofSuccess(E data, String message){
        return new Result<>(message, data);
    }

    public boolean hasError() {
        return data == null;
    }

    public boolean isSuccess(){
        return !hasError();
    }

    public String getMessage(){
        return message;
    }

    public T getData(){
        return data;
    }
}
