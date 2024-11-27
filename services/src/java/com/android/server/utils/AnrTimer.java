package com.android.server.utils;

/* loaded from: classes2.dex */
public class AnrTimer<V> implements java.lang.AutoCloseable {
    static final java.lang.String TAG = "AnrTimer";
    private static final long TRACE_TAG = 64;
    private static final java.lang.String TRACK = "AnrTimer";
    private final boolean mExtend;
    private final com.android.server.utils.AnrTimer<V>.FeatureSwitch mFeature;
    private final android.os.Handler mHandler;
    private final com.android.server.utils.AnrTimer.Injector mInjector;
    private final java.lang.String mLabel;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mMaxStarted;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<V> mTimerArgMap;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<V, java.lang.Integer> mTimerIdMap;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mTotalErrors;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mTotalExpired;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mTotalStarted;
    private final int mWhat;
    private static boolean DEBUG = false;
    private static final com.android.server.utils.AnrTimer.Injector sDefaultInjector = new com.android.server.utils.AnrTimer.Injector();

    @com.android.internal.annotations.GuardedBy({"sErrors"})
    private static final com.android.internal.util.RingBuffer<com.android.server.utils.AnrTimer.Error> sErrors = new com.android.internal.util.RingBuffer<>(com.android.server.utils.AnrTimer.Error.class, 20);

    @com.android.internal.annotations.GuardedBy({"sAnrTimerList"})
    private static final android.util.LongSparseArray<java.lang.ref.WeakReference<com.android.server.utils.AnrTimer>> sAnrTimerList = new android.util.LongSparseArray<>();

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeAnrTimerAccept(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeAnrTimerCancel(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeAnrTimerClose(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native long nativeAnrTimerCreate(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean nativeAnrTimerDiscard(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAnrTimerDump(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeAnrTimerStart(long j, int i, int i2, long j2, boolean z);

    private static native boolean nativeAnrTimerSupported();

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean anrTimerServiceEnabled() {
        com.android.server.utils.Flags.anrTimerService();
        return false;
    }

    static class Injector {
        Injector() {
        }

        boolean anrTimerServiceEnabled() {
            return com.android.server.utils.AnrTimer.anrTimerServiceEnabled();
        }
    }

    private static final class Error {
        final java.lang.String arg;
        final java.lang.String issue;
        final java.lang.String operation;
        final java.lang.StackTraceElement[] stack;
        final java.lang.String tag;
        final long timestamp = android.os.SystemClock.elapsedRealtime();

        Error(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, @android.annotation.NonNull java.lang.StackTraceElement[] stackTraceElementArr, @android.annotation.NonNull java.lang.String str4) {
            this.issue = str;
            this.operation = str2;
            this.tag = str3;
            this.stack = stackTraceElementArr;
            this.arg = str4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter, int i) {
            indentingPrintWriter.format("%2d: op:%s tag:%s issue:%s arg:%s\n", new java.lang.Object[]{java.lang.Integer.valueOf(i), this.operation, this.tag, this.issue, this.arg});
            indentingPrintWriter.println("    date:" + android.text.format.TimeMigrationUtils.formatMillisWithFixedFormat((java.lang.System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime()) + this.timestamp));
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < this.stack.length; i2++) {
                indentingPrintWriter.println("    " + this.stack[i2].toString());
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    AnrTimer(@android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.NonNull com.android.server.utils.AnrTimer.Injector injector) {
        this.mLock = new java.lang.Object();
        this.mTimerIdMap = new android.util.ArrayMap<>();
        this.mTimerArgMap = new android.util.SparseArray<>();
        boolean z2 = false;
        this.mMaxStarted = 0;
        this.mTotalStarted = 0;
        this.mTotalErrors = 0;
        this.mTotalExpired = 0;
        this.mHandler = handler;
        this.mWhat = i;
        this.mLabel = str;
        this.mExtend = z;
        this.mInjector = injector;
        if (this.mInjector.anrTimerServiceEnabled() && nativeTimersSupported()) {
            z2 = true;
        }
        this.mFeature = createFeatureSwitch(z2);
    }

    private com.android.server.utils.AnrTimer<V>.FeatureSwitch createFeatureSwitch(boolean z) {
        if (!z) {
            return new com.android.server.utils.AnrTimer.FeatureDisabled();
        }
        try {
            return new com.android.server.utils.AnrTimer.FeatureEnabled();
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e("AnrTimer", e.toString());
            return new com.android.server.utils.AnrTimer.FeatureDisabled();
        }
    }

    public AnrTimer(@android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        this(handler, i, str, z, sDefaultInjector);
    }

    public AnrTimer(@android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.NonNull java.lang.String str) {
        this(handler, i, str, false);
    }

    public boolean serviceEnabled() {
        return this.mFeature.enabled();
    }

    private void trace(java.lang.String str, int i, int i2, int i3, long j) {
        android.os.Trace.instantForTrack(TRACE_TAG, "AnrTimer", android.text.TextUtils.formatSimple("%s(%d,%d,%d,%s,%d)", new java.lang.Object[]{str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), this.mLabel, java.lang.Long.valueOf(j)}));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trace(java.lang.String str, int i) {
        android.os.Trace.instantForTrack(TRACE_TAG, "AnrTimer", android.text.TextUtils.formatSimple("%s(%d)", new java.lang.Object[]{str, java.lang.Integer.valueOf(i)}));
    }

    private abstract class FeatureSwitch {
        abstract boolean accept(@android.annotation.NonNull V v);

        abstract boolean cancel(@android.annotation.NonNull V v);

        abstract void close();

        abstract boolean discard(@android.annotation.NonNull V v);

        abstract void dump(java.io.PrintWriter printWriter, boolean z);

        abstract boolean enabled();

        abstract void start(@android.annotation.NonNull V v, int i, int i2, long j);

        private FeatureSwitch() {
        }
    }

    private class FeatureDisabled extends com.android.server.utils.AnrTimer<V>.FeatureSwitch {
        private FeatureDisabled() {
            super();
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        void start(@android.annotation.NonNull V v, int i, int i2, long j) {
            com.android.server.utils.AnrTimer.this.mHandler.sendMessageDelayed(com.android.server.utils.AnrTimer.this.mHandler.obtainMessage(com.android.server.utils.AnrTimer.this.mWhat, v), j);
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        boolean cancel(@android.annotation.NonNull V v) {
            com.android.server.utils.AnrTimer.this.mHandler.removeMessages(com.android.server.utils.AnrTimer.this.mWhat, v);
            return true;
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        boolean accept(@android.annotation.NonNull V v) {
            return true;
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        boolean discard(@android.annotation.NonNull V v) {
            return true;
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        boolean enabled() {
            return false;
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        void dump(java.io.PrintWriter printWriter, boolean z) {
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        void close() {
        }
    }

    private class FeatureEnabled extends com.android.server.utils.AnrTimer<V>.FeatureSwitch {
        private long mNative;

        FeatureEnabled() {
            super();
            this.mNative = 0L;
            this.mNative = com.android.server.utils.AnrTimer.this.nativeAnrTimerCreate(com.android.server.utils.AnrTimer.this.mLabel);
            if (this.mNative == 0) {
                throw new java.lang.IllegalArgumentException("unable to create native timer");
            }
            synchronized (com.android.server.utils.AnrTimer.sAnrTimerList) {
                com.android.server.utils.AnrTimer.sAnrTimerList.put(this.mNative, new java.lang.ref.WeakReference(com.android.server.utils.AnrTimer.this));
            }
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        void start(@android.annotation.NonNull V v, int i, int i2, long j) {
            synchronized (com.android.server.utils.AnrTimer.this.mLock) {
                try {
                    if (com.android.server.utils.AnrTimer.this.mTimerIdMap.containsKey(v)) {
                        cancel(v);
                    }
                    int nativeAnrTimerStart = com.android.server.utils.AnrTimer.nativeAnrTimerStart(this.mNative, i, i2, j, com.android.server.utils.AnrTimer.this.mExtend);
                    if (nativeAnrTimerStart > 0) {
                        com.android.server.utils.AnrTimer.this.mTimerIdMap.put(v, java.lang.Integer.valueOf(nativeAnrTimerStart));
                        com.android.server.utils.AnrTimer.this.mTimerArgMap.put(nativeAnrTimerStart, v);
                        com.android.server.utils.AnrTimer.this.mTotalStarted++;
                        com.android.server.utils.AnrTimer.this.mMaxStarted = java.lang.Math.max(com.android.server.utils.AnrTimer.this.mMaxStarted, com.android.server.utils.AnrTimer.this.mTimerIdMap.size());
                    } else {
                        throw new java.lang.RuntimeException("unable to start timer");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        boolean cancel(@android.annotation.NonNull V v) {
            synchronized (com.android.server.utils.AnrTimer.this.mLock) {
                try {
                    java.lang.Integer removeLocked = removeLocked(v);
                    if (removeLocked == null) {
                        return false;
                    }
                    if (com.android.server.utils.AnrTimer.nativeAnrTimerCancel(this.mNative, removeLocked.intValue())) {
                        return true;
                    }
                    com.android.server.utils.AnrTimer.this.mHandler.removeMessages(com.android.server.utils.AnrTimer.this.mWhat, v);
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        boolean accept(@android.annotation.NonNull V v) {
            synchronized (com.android.server.utils.AnrTimer.this.mLock) {
                try {
                    java.lang.Integer removeLocked = removeLocked(v);
                    if (removeLocked == null) {
                        com.android.server.utils.AnrTimer.this.notFoundLocked("accept", v);
                        return false;
                    }
                    com.android.server.utils.AnrTimer.nativeAnrTimerAccept(this.mNative, removeLocked.intValue());
                    com.android.server.utils.AnrTimer.this.trace("accept", removeLocked.intValue());
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        boolean discard(@android.annotation.NonNull V v) {
            synchronized (com.android.server.utils.AnrTimer.this.mLock) {
                try {
                    java.lang.Integer removeLocked = removeLocked(v);
                    if (removeLocked == null) {
                        com.android.server.utils.AnrTimer.this.notFoundLocked("discard", v);
                        return false;
                    }
                    com.android.server.utils.AnrTimer.nativeAnrTimerDiscard(this.mNative, removeLocked.intValue());
                    com.android.server.utils.AnrTimer.this.trace("discard", removeLocked.intValue());
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        boolean enabled() {
            return true;
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        void dump(java.io.PrintWriter printWriter, boolean z) {
            synchronized (com.android.server.utils.AnrTimer.this.mLock) {
                try {
                    if (this.mNative != 0) {
                        com.android.server.utils.AnrTimer.nativeAnrTimerDump(this.mNative, z);
                    } else {
                        printWriter.println("closed");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.utils.AnrTimer.FeatureSwitch
        void close() {
            synchronized (com.android.server.utils.AnrTimer.sAnrTimerList) {
                com.android.server.utils.AnrTimer.sAnrTimerList.remove(this.mNative);
            }
            synchronized (com.android.server.utils.AnrTimer.this.mLock) {
                try {
                    if (this.mNative != 0) {
                        com.android.server.utils.AnrTimer.nativeAnrTimerClose(this.mNative);
                    }
                    this.mNative = 0L;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private java.lang.Integer removeLocked(V v) {
            java.lang.Integer num = (java.lang.Integer) com.android.server.utils.AnrTimer.this.mTimerIdMap.remove(v);
            if (num != null) {
                synchronized (com.android.server.utils.AnrTimer.this.mTimerArgMap) {
                    com.android.server.utils.AnrTimer.this.mTimerArgMap.remove(num.intValue());
                }
            }
            return num;
        }
    }

    public void start(@android.annotation.NonNull V v, int i, int i2, long j) {
        this.mFeature.start(v, i, i2, j < 0 ? 0L : j);
    }

    public boolean cancel(@android.annotation.NonNull V v) {
        return this.mFeature.cancel(v);
    }

    public boolean accept(@android.annotation.NonNull V v) {
        return this.mFeature.accept(v);
    }

    public boolean discard(@android.annotation.NonNull V v) {
        return this.mFeature.discard(v);
    }

    @com.android.internal.annotations.Keep
    private boolean expire(int i, int i2, int i3, long j) {
        trace("expired", i, i2, i3, j);
        synchronized (this.mLock) {
            try {
                V v = this.mTimerArgMap.get(i);
                if (v != null) {
                    this.mTotalExpired++;
                    this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, this.mWhat, v));
                    return true;
                }
                android.util.Log.e("AnrTimer", android.text.TextUtils.formatSimple("failed to expire timer %s:%d : arg not found", new java.lang.Object[]{this.mLabel, java.lang.Integer.valueOf(i)}));
                this.mTotalErrors++;
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mFeature.close();
    }

    protected void finalize() throws java.lang.Throwable {
        close();
        super.finalize();
    }

    private void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.format("timer: %s\n", new java.lang.Object[]{this.mLabel});
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.format("started=%d maxStarted=%d running=%d expired=%d errors=%d\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mTotalStarted), java.lang.Integer.valueOf(this.mMaxStarted), java.lang.Integer.valueOf(this.mTimerIdMap.size()), java.lang.Integer.valueOf(this.mTotalExpired), java.lang.Integer.valueOf(this.mTotalErrors)});
            indentingPrintWriter.decreaseIndent();
            this.mFeature.dump(indentingPrintWriter, false);
        }
    }

    static void debug(boolean z) {
        DEBUG = z;
    }

    private static long now() {
        return android.os.SystemClock.uptimeMillis();
    }

    private static void dumpErrors(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (sErrors) {
            try {
                if (sErrors.size() == 0) {
                    return;
                }
                com.android.server.utils.AnrTimer.Error[] errorArr = (com.android.server.utils.AnrTimer.Error[]) sErrors.toArray();
                indentingPrintWriter.println("Errors");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < errorArr.length; i++) {
                    if (errorArr[i] != null) {
                        errorArr[i].dump(indentingPrintWriter, i);
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void recordErrorLocked(java.lang.String str, java.lang.String str2, java.lang.Object obj) {
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        java.lang.String objects = java.util.Objects.toString(obj);
        java.lang.StackTraceElement[] stackTraceElementArr = (java.lang.StackTraceElement[]) java.util.Arrays.copyOfRange(stackTrace, 6, 9);
        synchronized (sErrors) {
            sErrors.append(new com.android.server.utils.AnrTimer.Error(str2, str, this.mLabel, stackTraceElementArr, objects));
        }
        if (DEBUG) {
            android.util.Log.w("AnrTimer", str + " " + str2 + " " + this.mLabel + " timer " + objects);
        }
        this.mTotalErrors++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notFoundLocked(java.lang.String str, java.lang.Object obj) {
        recordErrorLocked(str, "notFound", obj);
    }

    @com.android.internal.annotations.VisibleForTesting
    static void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, boolean z, @android.annotation.NonNull com.android.server.utils.AnrTimer.Injector injector) {
        if (injector.anrTimerServiceEnabled()) {
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
            indentingPrintWriter.println("AnrTimer statistics");
            indentingPrintWriter.increaseIndent();
            synchronized (sAnrTimerList) {
                try {
                    int size = sAnrTimerList.size();
                    indentingPrintWriter.println("reporting " + size + " timers");
                    for (int i = 0; i < size; i++) {
                        com.android.server.utils.AnrTimer anrTimer = sAnrTimerList.valueAt(i).get();
                        if (anrTimer != null) {
                            anrTimer.dump(indentingPrintWriter);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z) {
                dumpErrors(indentingPrintWriter);
            }
            indentingPrintWriter.format("AnrTimerEnd\n", new java.lang.Object[0]);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, boolean z) {
        dump(printWriter, z, sDefaultInjector);
    }

    public static boolean nativeTimersSupported() {
        try {
            return nativeAnrTimerSupported();
        } catch (java.lang.UnsatisfiedLinkError e) {
            return false;
        }
    }
}
