package com.email.sys.loaders;

import jakarta.annotation.Nullable;

public interface ConfigurableLoader<R, P> extends Loader<R, P>{
    @Override
    default R load(P param){
        return load(param, null);
    }

    <T> R load(P param, @Nullable T data);
}
