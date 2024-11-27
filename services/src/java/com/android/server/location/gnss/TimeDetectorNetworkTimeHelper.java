package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class TimeDetectorNetworkTimeHelper extends com.android.server.location.gnss.NetworkTimeHelper {

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_NETWORK_TIME_AGE_MILLIS = 86400000;
    static final int NTP_REFRESH_INTERVAL_MILLIS = 86400000;
    private final android.util.LocalLog mDumpLog = new android.util.LocalLog(10, false);

    @android.annotation.NonNull
    private final com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment mEnvironment;

    @android.annotation.NonNull
    private final com.android.server.location.gnss.NetworkTimeHelper.InjectTimeCallback mInjectTimeCallback;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mNetworkTimeInjected;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mPeriodicTimeInjectionEnabled;
    private static final java.lang.String TAG = "TDNetworkTimeHelper";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    interface Environment {
        void clearDelayedTimeQueryCallback();

        long elapsedRealtimeMillis();

        @android.annotation.Nullable
        com.android.server.timedetector.NetworkTimeSuggestion getLatestNetworkTime();

        void requestDelayedTimeQueryCallback(com.android.server.location.gnss.TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper, long j);

        void requestImmediateTimeQueryCallback(com.android.server.location.gnss.TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper, java.lang.String str);

        void setNetworkTimeUpdateListener(com.android.server.timezonedetector.StateChangeListener stateChangeListener);
    }

    public static boolean isInUse() {
        return true;
    }

    TimeDetectorNetworkTimeHelper(@android.annotation.NonNull com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment environment, @android.annotation.NonNull com.android.server.location.gnss.NetworkTimeHelper.InjectTimeCallback injectTimeCallback) {
        java.util.Objects.requireNonNull(injectTimeCallback);
        this.mInjectTimeCallback = injectTimeCallback;
        java.util.Objects.requireNonNull(environment);
        this.mEnvironment = environment;
        this.mEnvironment.setNetworkTimeUpdateListener(new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.location.gnss.TimeDetectorNetworkTimeHelper$$ExternalSyntheticLambda0
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.this.onNetworkTimeAvailable();
            }
        });
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper
    synchronized void setPeriodicTimeInjectionMode(boolean z) {
        try {
            this.mPeriodicTimeInjectionEnabled = z;
            if (!z) {
                removePeriodicNetworkTimeQuery();
            }
            this.mEnvironment.requestImmediateTimeQueryCallback(this, "setPeriodicTimeInjectionMode(" + z + ")");
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    void onNetworkTimeAvailable() {
        this.mEnvironment.requestImmediateTimeQueryCallback(this, "onNetworkTimeAvailable");
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper
    void onNetworkAvailable() {
        synchronized (this) {
            try {
                if (!this.mNetworkTimeInjected) {
                    this.mEnvironment.requestImmediateTimeQueryCallback(this, "onNetworkAvailable");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper
    void demandUtcTimeInjection() {
        this.mEnvironment.requestImmediateTimeQueryCallback(this, "demandUtcTimeInjection");
    }

    void delayedQueryAndInjectNetworkTime() {
        queryAndInjectNetworkTime("delayedTimeQueryCallback");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void queryAndInjectNetworkTime(@android.annotation.NonNull java.lang.String str) {
        com.android.server.timedetector.NetworkTimeSuggestion latestNetworkTime = this.mEnvironment.getLatestNetworkTime();
        maybeInjectNetworkTime(latestNetworkTime, str);
        removePeriodicNetworkTimeQuery();
        if (this.mPeriodicTimeInjectionEnabled) {
            logToDumpLog("queryAndInjectNtpTime: Scheduling periodic query reason=" + str + " latestNetworkTime=" + latestNetworkTime + " maxDelayMillis=86400000");
            this.mEnvironment.requestDelayedTimeQueryCallback(this, (long) 86400000);
        }
    }

    private long calculateTimeSignalAgeMillis(@android.annotation.Nullable com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion) {
        if (networkTimeSuggestion == null) {
            return com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
        return this.mEnvironment.elapsedRealtimeMillis() - networkTimeSuggestion.getUnixEpochTime().getElapsedRealtimeMillis();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void maybeInjectNetworkTime(@android.annotation.Nullable com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion, @android.annotation.NonNull java.lang.String str) {
        if (calculateTimeSignalAgeMillis(networkTimeSuggestion) > 86400000) {
            logToDumpLog("maybeInjectNetworkTime: Not injecting latest network time latestNetworkTime=" + networkTimeSuggestion + " reason=" + str);
            return;
        }
        android.app.time.UnixEpochTime unixEpochTime = networkTimeSuggestion.getUnixEpochTime();
        long unixEpochTimeMillis = unixEpochTime.getUnixEpochTimeMillis();
        logToDumpLog("maybeInjectNetworkTime: Injecting latest network time latestNetworkTime=" + networkTimeSuggestion + " reason=" + str + " System time offset millis=" + (unixEpochTimeMillis - java.lang.System.currentTimeMillis()));
        this.mInjectTimeCallback.injectTime(unixEpochTimeMillis, unixEpochTime.getElapsedRealtimeMillis(), networkTimeSuggestion.getUncertaintyMillis());
        this.mNetworkTimeInjected = true;
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper
    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.println("TimeDetectorNetworkTimeHelper:");
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        synchronized (this) {
            indentingPrintWriter.println("mPeriodicTimeInjectionEnabled=" + this.mPeriodicTimeInjectionEnabled);
        }
        indentingPrintWriter.println("Debug log:");
        this.mDumpLog.dump(indentingPrintWriter);
    }

    private void logToDumpLog(@android.annotation.NonNull java.lang.String str) {
        this.mDumpLog.log(str);
        if (DEBUG) {
            android.util.Log.d(TAG, str);
        }
    }

    private void removePeriodicNetworkTimeQuery() {
        this.mEnvironment.clearDelayedTimeQueryCallback();
    }

    static class EnvironmentImpl implements com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment {
        private final android.os.Handler mHandler;
        private final java.lang.Object mScheduledRunnableToken = new java.lang.Object();
        private final java.lang.Object mImmediateRunnableToken = new java.lang.Object();
        private final com.android.server.timedetector.TimeDetectorInternal mTimeDetectorInternal = (com.android.server.timedetector.TimeDetectorInternal) com.android.server.LocalServices.getService(com.android.server.timedetector.TimeDetectorInternal.class);

        EnvironmentImpl(android.os.Looper looper) {
            this.mHandler = new android.os.Handler(looper);
        }

        @Override // com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment
        public long elapsedRealtimeMillis() {
            return android.os.SystemClock.elapsedRealtime();
        }

        @Override // com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment
        public com.android.server.timedetector.NetworkTimeSuggestion getLatestNetworkTime() {
            return this.mTimeDetectorInternal.getLatestNetworkSuggestion();
        }

        @Override // com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment
        public void setNetworkTimeUpdateListener(com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
            this.mTimeDetectorInternal.addNetworkTimeUpdateListener(stateChangeListener);
        }

        @Override // com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment
        public void requestImmediateTimeQueryCallback(final com.android.server.location.gnss.TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper, final java.lang.String str) {
            synchronized (this) {
                this.mHandler.removeCallbacksAndMessages(this.mImmediateRunnableToken);
                this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.location.gnss.TimeDetectorNetworkTimeHelper$EnvironmentImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.this.queryAndInjectNetworkTime(str);
                    }
                }, this.mImmediateRunnableToken, 0L);
            }
        }

        @Override // com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment
        public void requestDelayedTimeQueryCallback(final com.android.server.location.gnss.TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper, long j) {
            synchronized (this) {
                clearDelayedTimeQueryCallback();
                android.os.Handler handler = this.mHandler;
                java.util.Objects.requireNonNull(timeDetectorNetworkTimeHelper);
                handler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.location.gnss.TimeDetectorNetworkTimeHelper$EnvironmentImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.this.delayedQueryAndInjectNetworkTime();
                    }
                }, this.mScheduledRunnableToken, j);
            }
        }

        @Override // com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.Environment
        public synchronized void clearDelayedTimeQueryCallback() {
            this.mHandler.removeCallbacksAndMessages(this.mScheduledRunnableToken);
        }
    }
}
