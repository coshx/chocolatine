package com.coshx.chocolatine.promises;

import com.coshx.chocolatine.utils.Async;
import com.coshx.chocolatine.utils.actions.Action;
import com.coshx.chocolatine.utils.actions.Action0;
import com.coshx.chocolatine.utils.funcs.Func;

import java.lang.ref.WeakReference;

/**
 * BasePromise
 * <p/>
 */
public abstract class BasePromise<T extends BasePromise> implements IPromise {

    public static abstract class BaseTrigger<U extends BasePromise<U>> {
        protected WeakReference<U> parent;

        protected BaseTrigger(U parent) {
            this.parent = new WeakReference<>(parent);
        }

        protected void trigger(Func<U, Callback> getCallback, final Action<U> action) {
            BasePromise<U> p1 = parent.get();

            if (p1 != null && p1.isValid && getCallback.run((U) p1) != null) {
                final Action0 f = new Action0() {
                    @Override
                    public void run() {
                        BasePromise<U> p2 = parent.get();

                        if (p2 != null && p2.isValid) {
                            synchronized (p2.invalidationLock) {
                                if (p2.isValid) {
                                    action.run((U) p2);
                                }
                            }
                        }
                    }
                };

                if (getCallback.run((U) p1).isInBackground()) {
                    Async.background(f);
                } else {
                    Async.main(f);
                }
            }
        }
    }

    private final Object invalidationLock = new Object();

    private boolean isValid;
    private boolean nextCallInBackground;
    private boolean allInBackgroundValue;

    protected BasePromise() {
        isValid = true;
        nextCallInBackground = false;
        allInBackgroundValue = false;
    }

    protected <U> Callback<U> buildTuple(U action) {
        boolean value = allInBackgroundValue || nextCallInBackground;
        nextCallInBackground = false;

        return new Callback<>(value, action);
    }

    public T allInBackground() {
        allInBackgroundValue = true;
        return (T) this;
    }

    public T inBackground() {
        nextCallInBackground = true;
        return (T) this;
    }

    @Override
    public void invalidate() {
        synchronized (invalidationLock) {
            isValid = false;
        }
    }
}
