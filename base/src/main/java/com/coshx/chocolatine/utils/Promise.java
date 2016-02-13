package com.coshx.chocolatine.utils;

import android.util.Pair;

import com.coshx.chocolatine.utils.actions.Action;
import com.coshx.chocolatine.utils.actions.Action0;

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

        public void onSuccess(final U u) {
            Promise<U> p1 = this.parent.get();

            if (p1 != null && p1.isValid) {
                Action0 action = new Action0() {
                    @Override
                    public void run() {
                        Promise<U> p2 = Trigger.this.parent.get();
                        synchronized (p2.invalidationLock) {
                            if (p2.isValid) {
                                p2.onSuccessAction.run(u);
                            }
                        }
                    }
                };
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

    private boolean isValid;

    private Action<T> onSuccessAction;
    private boolean   runOnSuccessOnMain;

    private Promise() {
        isValid = true;
        runOnSuccessOnMain = true;
    }

    public Promise<T> onSuccess(Action<T> action) {
        onSuccessAction = action;
        runOnSuccessOnMain = true;
        return this;
    }

    public Promise<T> onSuccessInBackground(Action<T> action) {
        onSuccessAction = action;
        runOnSuccessOnMain = false;
        return this;
    }

    @Override
    public void invalidate() {
        synchronized (invalidationLock) {
            isValid = false;
        }
    }
}
