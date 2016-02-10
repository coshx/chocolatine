package com.coshx.chocolatine.utils;

/**
 * FinalWrapper
 * <p/>
 * Wrapper for using local variables in lambda functions
 */
public class FinalWrapper<T> {
    private T value;

    public FinalWrapper() {
    }

    public FinalWrapper(T t) {
        value = t;
    }

    public T get() {
        return value;
    }

    public void set(T t) {
        value = t;
    }
}
