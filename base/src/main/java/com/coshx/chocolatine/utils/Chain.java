package com.coshx.chocolatine.utils;

import com.coshx.chocolatine.utils.actions.Action;
import com.coshx.chocolatine.utils.actions.Action0;
import com.coshx.chocolatine.utils.actions.Action2;

/**
 * Chain
 * <p/>
 */
public class Chain<T> {
    private Action0   runCommand;
    private Action<T> nextCommand;

    private Chain() {
    }

    public <U> Chain<U> then(final Action2<T, Action<U>> command) {
        final Chain<U> e = new Chain<>();

        nextCommand = new Action<T>() {
            @Override
            public void run(T t) {
                command.run(t, e.nextCommand);
            }
        };
        e.runCommand = runCommand;

        return e;
    }

    public void endWith(Action<T> command) {
        nextCommand = command;
        runCommand.run();
    }

    public static <U> Chain<U> startWith(final Action<Action<U>> command) {
        final Chain<U> e = new Chain<>();

        e.runCommand = new Action0() {
            @Override
            public void run() {
                command.run(e.nextCommand);
            }
        };

        return e;
    }
}
