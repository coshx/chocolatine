package com.coshx.chocolatine.utils;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.coshx.chocolatine.utils.actions.Action0;

/**
 * Async
 * <p/>
 * Based on https://github.com/duemunk/Async
 */
public class Async {
    public interface Job {
        void cancel();
    }

    private static class BackgroundJob extends AsyncTask implements Job {
        private Action0 action;

        BackgroundJob(Action0 action) {
            this.action = action;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            action.run();
            return null;
        }

        @Override
        public void cancel() {
            cancel(false);
        }
    }

    private static class MainJob implements Job {
        private Handler  handler;
        private Runnable action;

        MainJob(Handler handler, Runnable action) {
            this.handler = handler;
            this.action = action;
        }

        @Override
        public void cancel() {
            handler.removeCallbacks(action);
        }
    }

    public static Job background(final Action0 action) {
        final BackgroundJob job = new BackgroundJob(action);

        main(new Action0() {
            @Override
            public void run() {
                job.execute();
            }
        });

        return job;
    }

    public static Job main(final Action0 action) {
        Handler handler;
        Job job;
        Runnable runnable;

        runnable = new Runnable() {
            @Override
            public void run() {
                action.run();
            }
        };
        handler = new Handler(Looper.getMainLooper());
        job = new MainJob(handler, runnable);

        handler.post(runnable);

        return job;
    }
}
