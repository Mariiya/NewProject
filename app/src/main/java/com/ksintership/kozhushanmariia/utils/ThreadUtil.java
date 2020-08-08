package com.ksintership.kozhushanmariia.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

public class ThreadUtil {
    private static volatile Handler handler;

    public static void runOnMain(final @NonNull Runnable runnable) {
        if (isMainThread()) runnable.run();
        else getHandler().post(runnable);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private static Handler getHandler() {
        if (handler == null) {
            synchronized (ThreadUtil.class) {
                if (handler == null) {
                    handler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return handler;
    }

}
