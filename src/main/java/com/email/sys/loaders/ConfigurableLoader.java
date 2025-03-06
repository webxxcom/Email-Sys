package com.email.sys.loaders;

import com.email.sys.configurators.ConfigStorage;
import jakarta.annotation.Nullable;

public interface ConfigurableLoader<R, P> extends Loader<R, P>{
    @Override
    default R load(P param){
        return load(param, null);
    }

    R load(P param, @Nullable ConfigStorage data);
}
