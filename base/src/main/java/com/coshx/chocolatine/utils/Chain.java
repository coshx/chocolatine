package com.coshx.chocolatine.utils;

import com.coshx.chocolatine.utils.actions.Action;
import com.coshx.chocolatine.utils.actions.Action0;
import com.coshx.chocolatine.utils.actions.Action2;

/**
 * Chain
 * <p/>
 */
public class Chain {
    public static class Element<T, U> {
        Action0   runCommand;
        Action<U> nextCommand;

        Element() {
        }

        void next(U u) {
            nextCommand.run(u);
        }

        public <V> Element<U, V> then(final Action2<U, Action<V>> command) {
            final Element<U, V> e = new Element<>();

            nextCommand = new Action<U>() {
                @Override
                public void run(U u) {
                    command.run(u, new Action<V>() {
                        @Override
                        public void run(V v) {
                            e.next(v);
                        }
                    });
                }
            };
            e.runCommand = runCommand;

            return e;
        }

        public void endWith(Action<U> command) {
            nextCommand = command;
            runCommand.run();
        }
    }

    public static <T> Element<Void, T> startWith(final Action<Action<T>> command) {
        final Element<Void, T> e = new Element<>();

        e.runCommand = new Action0() {
            @Override
            public void run() {
                command.run(new Action<T>() {
                    @Override
                    public void run(T t) {
                        e.next(t);
                    }
                });
            }
        };

        return e;
    }
}
