package com.cyh.utils;

public final class SleepUtil {

    public static void sleepMilli(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
