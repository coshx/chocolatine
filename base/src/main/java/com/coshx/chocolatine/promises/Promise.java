package com.coshx.chocolatine.promises;

import android.util.Pair;

import com.coshx.chocolatine.utils.actions.Action;
import com.coshx.chocolatine.utils.funcs.Func;

/**
 * Promise
 * <p/>
 */
public class Promise<T> extends BasePromise<Promise<T>> {

    public static class Builder {
        public static <U> Pair<Promise<U>, Trigger<U>> build() {
            Promise<U> p = new Promise<>();

            return new Pair<>(p, new Trigger<>(p));
        }
    }

    public static class Trigger<U> extends BaseTrigger<Promise<U>> {
        private Trigger(Promise<U> parent) {
            super(parent);
        }

        public void onSuccess(final U u) {
            trigger(new Func<Promise<U>, Callback>() {
                @Override
                public Callback run(Promise<U> parent) {
                    return parent.onSuccessAction;
                }
            }, new Action<Promise<U>>() {
                @Override
                public void run(Promise<U> parent) {
                    parent.onSuccessAction.getAction().run(u);
                }
            });
        }
    }

    private Callback<Action<T>> onSuccessAction;

    private Promise() {
        super();
    }

    public Promise<T> onSuccess(Action<T> action) {
        onSuccessAction = buildTuple(action);
        return this;
    }
}
