package com.email.sys;

public class Result<T> {
    private final String error;
    private final T data;

    private Result(String error, T data) {
        this.error = error;
        this.data = data;
    }

    public static <E> Result<E> error(String error){
        return new Result<>(error, null);
    }

    public static <E> Result<E> success(E data){
        return new Result<>(null, data);
    }

    public static <E> Result<E> errorIf(boolean flag, String errorText) {
        return flag ? Result.error(errorText) : Result.success(null);
    }

    public boolean isSuccess(){
        return error == null;
    }

    public String getError(){
        return error;
    }

    public T getData(){
        return data;
    }

    public boolean hasError() {
        return error != null;
    }
}
