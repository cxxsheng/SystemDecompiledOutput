package com.android.internal.os;

/* loaded from: classes4.dex */
public class BinderInternal {
    private static final java.lang.String TAG = "BinderInternal";
    static long sLastGcTime;
    static java.lang.ref.WeakReference<com.android.internal.os.BinderInternal.GcWatcher> sGcWatcher = new java.lang.ref.WeakReference<>(new com.android.internal.os.BinderInternal.GcWatcher());
    static java.util.ArrayList<java.lang.Runnable> sGcWatchers = new java.util.ArrayList<>();
    static java.lang.Runnable[] sTmpWatchers = new java.lang.Runnable[1];
    static final com.android.internal.os.BinderInternal.BinderProxyLimitListenerDelegate sBinderProxyLimitListenerDelegate = new com.android.internal.os.BinderInternal.BinderProxyLimitListenerDelegate();

    public interface BinderProxyLimitListener {
        void onLimitReached(int i);
    }

    public static class CallSession {
        public java.lang.Class<? extends android.os.Binder> binderClass;
        long cpuTimeStarted;
        boolean exceptionThrown;
        public boolean recordedCall;
        long timeStarted;
        public int transactionCode;
    }

    public interface CallStatsObserver {
        void noteBinderThreadNativeIds(int[] iArr);

        void noteCallStats(int i, long j, java.util.Collection<com.android.internal.os.BinderCallsStats.CallStat> collection);
    }

    public interface Observer {
        void callEnded(com.android.internal.os.BinderInternal.CallSession callSession, int i, int i2, int i3);

        com.android.internal.os.BinderInternal.CallSession callStarted(android.os.Binder binder, int i, int i2);

        void callThrewException(com.android.internal.os.BinderInternal.CallSession callSession, java.lang.Exception exc);
    }

    @java.lang.FunctionalInterface
    public interface WorkSourceProvider {
        int resolveWorkSourceUid(int i);
    }

    public static final native void disableBackgroundScheduling(boolean z);

    public static final native android.os.IBinder getContextObject();

    static final native void handleGc();

    public static final native void joinThreadPool();

    public static final native int nGetBinderProxyCount(int i);

    public static final native android.util.SparseIntArray nGetBinderProxyPerUidCounts();

    public static final native void nSetBinderProxyCountEnabled(boolean z);

    public static final native void nSetBinderProxyCountWatermarks(int i, int i2);

    public static final native void setMaxThreads(int i);

    static final class GcWatcher {
        GcWatcher() {
        }

        protected void finalize() throws java.lang.Throwable {
            com.android.internal.os.BinderInternal.handleGc();
            com.android.internal.os.BinderInternal.sLastGcTime = android.os.SystemClock.uptimeMillis();
            synchronized (com.android.internal.os.BinderInternal.sGcWatchers) {
                com.android.internal.os.BinderInternal.sTmpWatchers = (java.lang.Runnable[]) com.android.internal.os.BinderInternal.sGcWatchers.toArray(com.android.internal.os.BinderInternal.sTmpWatchers);
            }
            for (int i = 0; i < com.android.internal.os.BinderInternal.sTmpWatchers.length; i++) {
                if (com.android.internal.os.BinderInternal.sTmpWatchers[i] != null) {
                    com.android.internal.os.BinderInternal.sTmpWatchers[i].run();
                }
            }
            com.android.internal.os.BinderInternal.sGcWatcher = new java.lang.ref.WeakReference<>(new com.android.internal.os.BinderInternal.GcWatcher());
        }
    }

    public static void addGcWatcher(java.lang.Runnable runnable) {
        synchronized (sGcWatchers) {
            sGcWatchers.add(runnable);
        }
    }

    public static long getLastGcTime() {
        return sLastGcTime;
    }

    public static void forceGc(java.lang.String str) {
        android.util.EventLog.writeEvent(2741, str);
        dalvik.system.VMRuntime.getRuntime().requestConcurrentGC();
    }

    static void forceBinderGc() {
        forceGc("Binder");
    }

    public static void binderProxyLimitCallbackFromNative(int i) {
        sBinderProxyLimitListenerDelegate.notifyClient(i);
    }

    public static void setBinderProxyCountCallback(com.android.internal.os.BinderInternal.BinderProxyLimitListener binderProxyLimitListener, android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(handler, "Must provide NonNull Handler to setBinderProxyCountCallback when setting BinderProxyLimitListener");
        sBinderProxyLimitListenerDelegate.setListener(binderProxyLimitListener, handler);
    }

    public static void clearBinderProxyCountCallback() {
        sBinderProxyLimitListenerDelegate.setListener(null, null);
    }

    private static class BinderProxyLimitListenerDelegate {
        private com.android.internal.os.BinderInternal.BinderProxyLimitListener mBinderProxyLimitListener;
        private android.os.Handler mHandler;

        private BinderProxyLimitListenerDelegate() {
        }

        void setListener(com.android.internal.os.BinderInternal.BinderProxyLimitListener binderProxyLimitListener, android.os.Handler handler) {
            synchronized (this) {
                this.mBinderProxyLimitListener = binderProxyLimitListener;
                this.mHandler = handler;
            }
        }

        void notifyClient(final int i) {
            synchronized (this) {
                if (this.mBinderProxyLimitListener != null) {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.internal.os.BinderInternal.BinderProxyLimitListenerDelegate.1
                        @Override // java.lang.Runnable
                        public void run() {
                            com.android.internal.os.BinderInternal.BinderProxyLimitListenerDelegate.this.mBinderProxyLimitListener.onLimitReached(i);
                        }
                    });
                }
            }
        }
    }
}
