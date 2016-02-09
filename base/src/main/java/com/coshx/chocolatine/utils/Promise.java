package com.coshx.chocolatine.utils;

import android.util.Pair;

import com.coshx.chocolatine.utils.actions.Action;

import java.lang.ref.WeakReference;

/**
 * Promise
 * <p/>
 */
public class Promise<T> implements IPromise {

    public static class Trigger<U> {
        private WeakReference<Promise<U>> parent;

        private Trigger(Promise<U> parent) {
            this.parent = new WeakReference<Promise<U>>(parent);
        }

        public void onSuccess(U u) {
            Promise<U> p = this.parent.get();
            if (p != null && p.isValid) {
                synchronized (p.invalidationLock) {
                    if (p.isValid) {
                        p.onSuccessfulAction.run(u);
                    }
                }
            }
        }
    }

    public static class Builder {
        public static <U> Pair<Promise<U>, Trigger<U>> build() {
            Promise<U> p = new Promise<>();

            return new Pair<>(p, new Trigger<>(p));
        }
    }

    private Object invalidationLock = new Object();

    private Action<T> onSuccessfulAction;
    private boolean   isValid;

    private Promise() {
        isValid = true;
    }

    public Promise<T> onSuccess(Action<T> action) {
        onSuccessfulAction = action;
        return this;
    }

    @Override
    public void invalidate() {
        synchronized (invalidationLock) {
            isValid = false;
        }
    }
}
