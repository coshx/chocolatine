package com.coshx.chocolatine.promises;

/**
 * Callback
 * <p/>
 */
public class Callback<T> {
    private boolean inBackground;
    private T       action;

    public Callback(boolean inBackground, T action) {
        this.inBackground = inBackground;
        this.action = action;
    }

    public Callback(T action) {
        this(false, action);
    }

    public boolean isInBackground() {
        return inBackground;
    }

    public T getAction() {
        return action;
    }
}
