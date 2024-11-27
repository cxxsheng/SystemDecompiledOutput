package com.android.internal.util;

/* loaded from: classes5.dex */
public class PerfettoTrigger {
    private static final java.lang.String TAG = "PerfettoTrigger";
    private static final long THROTTLE_MILLIS = 300000;
    private static final java.lang.String TRIGGER_COMMAND = "/system/bin/trigger_perfetto";
    private static final android.util.SparseLongArray sLastInvocationPerTrigger = new android.util.SparseLongArray(100);
    private static final java.lang.Object sLock = new java.lang.Object();

    public static void trigger(java.lang.String str) {
        synchronized (sLock) {
            if (android.os.SystemClock.elapsedRealtime() - sLastInvocationPerTrigger.get(str.hashCode()) < 300000) {
                android.util.Log.v(TAG, "Not triggering " + str + " - not enough time since last trigger");
                return;
            }
            sLastInvocationPerTrigger.put(str.hashCode(), android.os.SystemClock.elapsedRealtime());
            try {
                java.lang.ProcessBuilder processBuilder = new java.lang.ProcessBuilder(TRIGGER_COMMAND, str);
                android.util.Log.v(TAG, "Triggering " + java.lang.String.join(" ", processBuilder.command()));
                processBuilder.start();
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "Failed to trigger " + str, e);
            }
        }
    }
}
