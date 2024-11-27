package com.android.server.location.gnss;

/* loaded from: classes2.dex */
class NtpNetworkTimeHelper extends com.android.server.location.gnss.NetworkTimeHelper {
    private static final long MAX_RETRY_INTERVAL = 14400000;

    @com.android.internal.annotations.VisibleForTesting
    static final long NTP_INTERVAL = 86400000;

    @com.android.internal.annotations.VisibleForTesting
    static final long RETRY_INTERVAL = 300000;
    private static final int STATE_IDLE = 2;
    private static final int STATE_PENDING_NETWORK = 0;
    private static final int STATE_RETRIEVING_AND_INJECTING = 1;
    private static final java.lang.String WAKELOCK_KEY = "NtpTimeHelper";
    private static final long WAKELOCK_TIMEOUT_MILLIS = 60000;
    private final com.android.server.location.gnss.NetworkTimeHelper.InjectTimeCallback mCallback;
    private final android.net.ConnectivityManager mConnMgr;
    private final android.util.LocalLog mDumpLog;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mInjectNtpTimeState;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.location.gnss.ExponentialBackOff mNtpBackOff;
    private final android.util.NtpTrustedTime mNtpTime;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mPeriodicTimeInjection;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private static final java.lang.String TAG = "NtpNetworkTimeHelper";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    @com.android.internal.annotations.VisibleForTesting
    NtpNetworkTimeHelper(android.content.Context context, android.os.Looper looper, com.android.server.location.gnss.NetworkTimeHelper.InjectTimeCallback injectTimeCallback, android.util.NtpTrustedTime ntpTrustedTime) {
        this.mDumpLog = new android.util.LocalLog(10, false);
        this.mNtpBackOff = new com.android.server.location.gnss.ExponentialBackOff(300000L, 14400000L);
        this.mInjectNtpTimeState = 0;
        this.mConnMgr = (android.net.ConnectivityManager) context.getSystemService("connectivity");
        this.mCallback = injectTimeCallback;
        this.mNtpTime = ntpTrustedTime;
        this.mHandler = new android.os.Handler(looper);
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService("power")).newWakeLock(1, WAKELOCK_KEY);
    }

    NtpNetworkTimeHelper(android.content.Context context, android.os.Looper looper, com.android.server.location.gnss.NetworkTimeHelper.InjectTimeCallback injectTimeCallback) {
        this(context, looper, injectTimeCallback, android.util.NtpTrustedTime.getInstance(context));
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper
    synchronized void setPeriodicTimeInjectionMode(boolean z) {
        if (z) {
            this.mPeriodicTimeInjection = true;
        }
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper
    void demandUtcTimeInjection() {
        lambda$blockingGetNtpTimeAndInject$0("demandUtcTimeInjection");
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper
    synchronized void onNetworkAvailable() {
        if (this.mInjectNtpTimeState == 0) {
            lambda$blockingGetNtpTimeAndInject$0("onNetworkAvailable");
        }
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper
    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("NtpNetworkTimeHelper:");
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        synchronized (this) {
            indentingPrintWriter.println("mInjectNtpTimeState=" + this.mInjectNtpTimeState);
            indentingPrintWriter.println("mPeriodicTimeInjection=" + this.mPeriodicTimeInjection);
            indentingPrintWriter.println("mNtpBackOff=" + this.mNtpBackOff);
        }
        indentingPrintWriter.println("Debug log:");
        indentingPrintWriter.increaseIndent();
        this.mDumpLog.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("NtpTrustedTime:");
        indentingPrintWriter.increaseIndent();
        this.mNtpTime.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    private boolean isNetworkConnected() {
        android.net.NetworkInfo activeNetworkInfo = this.mConnMgr.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: retrieveAndInjectNtpTime, reason: merged with bridge method [inline-methods] */
    public synchronized void lambda$blockingGetNtpTimeAndInject$0(java.lang.String str) {
        if (this.mInjectNtpTimeState == 1) {
            return;
        }
        if (!isNetworkConnected()) {
            maybeInjectCachedNtpTime(str + "[Network not connected]");
            this.mInjectNtpTimeState = 0;
            return;
        }
        this.mInjectNtpTimeState = 1;
        this.mWakeLock.acquire(60000L);
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.location.gnss.NtpNetworkTimeHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.NtpNetworkTimeHelper.this.blockingGetNtpTimeAndInject();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void blockingGetNtpTimeAndInject() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        android.util.NtpTrustedTime.TimeResult cachedTimeResult = this.mNtpTime.getCachedTimeResult();
        long j = 86400000;
        boolean forceRefresh = (cachedTimeResult == null || cachedTimeResult.getAgeMillis() >= 86400000) ? this.mNtpTime.forceRefresh() : true;
        synchronized (this) {
            try {
                this.mInjectNtpTimeState = 2;
                if (maybeInjectCachedNtpTime("blockingGetNtpTimeAndInject:, debugId=" + elapsedRealtime + ", refreshSuccess=" + forceRefresh)) {
                    this.mNtpBackOff.reset();
                } else {
                    logWarn("maybeInjectCachedNtpTime() returned false");
                    j = this.mNtpBackOff.nextBackoffMillis();
                }
                if (this.mPeriodicTimeInjection || !forceRefresh) {
                    logDebug("blockingGetNtpTimeAndInject: Scheduling later NTP retrieval, debugId=" + elapsedRealtime + ", mPeriodicTimeInjection=" + this.mPeriodicTimeInjection + ", refreshSuccess=" + forceRefresh + ", delayMillis=" + j);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("scheduled: debugId=");
                    sb.append(elapsedRealtime);
                    final java.lang.String sb2 = sb.toString();
                    this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.location.gnss.NtpNetworkTimeHelper$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.location.gnss.NtpNetworkTimeHelper.this.lambda$blockingGetNtpTimeAndInject$0(sb2);
                        }
                    }, j);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mWakeLock.release();
    }

    private synchronized boolean maybeInjectCachedNtpTime(java.lang.String str) {
        android.util.NtpTrustedTime.TimeResult cachedTimeResult = this.mNtpTime.getCachedTimeResult();
        if (cachedTimeResult == null || cachedTimeResult.getAgeMillis() >= 86400000) {
            logDebug("maybeInjectCachedNtpTime: Not injecting latest NTP time, reason=" + str + ", ntpResult=" + cachedTimeResult);
            return false;
        }
        final long timeMillis = cachedTimeResult.getTimeMillis();
        logDebug("maybeInjectCachedNtpTime: Injecting latest NTP time, reason=" + str + ", ntpResult=" + cachedTimeResult + ", System time offset millis=" + (timeMillis - java.lang.System.currentTimeMillis()));
        final long elapsedRealtimeMillis = cachedTimeResult.getElapsedRealtimeMillis();
        final int uncertaintyMillis = cachedTimeResult.getUncertaintyMillis();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.NtpNetworkTimeHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.NtpNetworkTimeHelper.this.lambda$maybeInjectCachedNtpTime$1(timeMillis, elapsedRealtimeMillis, uncertaintyMillis);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeInjectCachedNtpTime$1(long j, long j2, int i) {
        this.mCallback.injectTime(j, j2, i);
    }

    private void logWarn(java.lang.String str) {
        this.mDumpLog.log(str);
        android.util.Log.e(TAG, str);
    }

    private void logDebug(java.lang.String str) {
        this.mDumpLog.log(str);
        if (DEBUG) {
            android.util.Log.d(TAG, str);
        }
    }
}
