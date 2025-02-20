package com.email.sys.loaders;

public interface Loader<R, P> {
    R load(P param);
}
