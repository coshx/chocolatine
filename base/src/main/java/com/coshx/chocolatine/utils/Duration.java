package com.coshx.chocolatine.utils;

/**
 * Duration
 * <p/>
 */
public class Duration {
    private double milliseconds;

    private Duration() {
    }

    public static Duration fromMilliseconds(double value) {
        Duration d = new Duration();

        d.milliseconds = value;

        return d;
    }

    public static Duration fromMilliseconds(long value) {
        Duration d = new Duration();

        d.milliseconds = value;

        return d;
    }

    public static Duration fromSeconds(double value) {
        Duration d = new Duration();

        d.milliseconds = value * 1000;

        return d;
    }

    public static Duration fromSeconds(long value) {
        Duration d = new Duration();

        d.milliseconds = value * 1000;

        return d;
    }

    public double toMilliseconds() {
        return milliseconds;
    }

    public double toSeconds() {
        return toMilliseconds() / 1000;
    }

    public long toTimer() {
        return (long) toMilliseconds();
    }
}
