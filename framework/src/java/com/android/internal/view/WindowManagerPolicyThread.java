package com.android.internal.view;

/* loaded from: classes5.dex */
public class WindowManagerPolicyThread {
    static android.os.Looper mLooper;
    static java.lang.Thread mThread;

    public static void set(java.lang.Thread thread, android.os.Looper looper) {
        mThread = thread;
        mLooper = looper;
    }

    public static java.lang.Thread getThread() {
        return mThread;
    }

    public static android.os.Looper getLooper() {
        return mLooper;
    }
}
