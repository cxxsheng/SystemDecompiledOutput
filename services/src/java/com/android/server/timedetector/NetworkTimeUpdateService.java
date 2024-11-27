package com.android.server.timedetector;

/* loaded from: classes2.dex */
public class NetworkTimeUpdateService extends android.os.Binder {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = "NetworkTimeUpdateService";
    private final android.net.ConnectivityManager mCM;
    private final android.content.Context mContext;
    private final com.android.server.timedetector.NetworkTimeUpdateService.Engine mEngine;
    private final android.os.Handler mHandler;
    private final android.util.NtpTrustedTime mNtpTrustedTime;
    private final com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks mRefreshCallbacks;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.net.Network mDefaultNetwork = null;

    @com.android.internal.annotations.VisibleForTesting
    interface Engine {

        public interface RefreshCallbacks {
            void scheduleNextRefresh(long j);

            void submitSuggestion(@android.annotation.NonNull com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion);
        }

        void dump(@android.annotation.NonNull java.io.PrintWriter printWriter);

        boolean forceRefreshForTests(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks refreshCallbacks);

        void refreshAndRescheduleIfRequired(@android.annotation.Nullable android.net.Network network, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks refreshCallbacks);
    }

    public NetworkTimeUpdateService(@android.annotation.NonNull android.content.Context context) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mCM = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService(android.os.PowerManager.class)).newWakeLock(1, TAG);
        this.mNtpTrustedTime = android.util.NtpTrustedTime.getInstance(context);
        this.mEngine = new com.android.server.timedetector.NetworkTimeUpdateService.EngineImpl(new java.util.function.Supplier() { // from class: com.android.server.timedetector.NetworkTimeUpdateService$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime());
            }
        }, this.mContext.getResources().getInteger(android.R.integer.config_notificationsBatteryLowARGB), this.mContext.getResources().getInteger(android.R.integer.config_notificationsBatteryLowBehavior), this.mContext.getResources().getInteger(android.R.integer.config_notificationsBatteryMediumARGB), this.mNtpTrustedTime);
        final android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        final com.android.server.timedetector.TimeDetectorInternal timeDetectorInternal = (com.android.server.timedetector.TimeDetectorInternal) com.android.server.LocalServices.getService(com.android.server.timedetector.TimeDetectorInternal.class);
        this.mRefreshCallbacks = new com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks() { // from class: com.android.server.timedetector.NetworkTimeUpdateService.1
            private final android.app.AlarmManager.OnAlarmListener mOnAlarmListener;

            {
                this.mOnAlarmListener = new com.android.server.timedetector.NetworkTimeUpdateService.ScheduledRefreshAlarmListener();
            }

            @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks
            public void scheduleNextRefresh(long j) {
                alarmManager.cancel(this.mOnAlarmListener);
                alarmManager.set(3, j, "NetworkTimeUpdateService.POLL", this.mOnAlarmListener, null);
            }

            @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks
            public void submitSuggestion(com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion) {
                timeDetectorInternal.suggestNetworkTime(networkTimeSuggestion);
            }
        };
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = handlerThread.getThreadHandler();
    }

    public void systemRunning() {
        this.mCM.registerDefaultNetworkCallback(new com.android.server.timedetector.NetworkTimeUpdateService.NetworkConnectivityCallback(), this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("auto_time"), false, new com.android.server.timedetector.NetworkTimeUpdateService.AutoTimeSettingObserver(this.mHandler, this.mContext));
    }

    @android.annotation.RequiresPermission("android.permission.SET_TIME")
    void setServerConfigForTests(@android.annotation.Nullable android.util.NtpTrustedTime.NtpConfig ntpConfig) {
        this.mContext.enforceCallingPermission("android.permission.SET_TIME", "set NTP server config for tests");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNtpTrustedTime.setServerConfigForTests(ntpConfig);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.RequiresPermission("android.permission.SET_TIME")
    boolean forceRefreshForTests() {
        android.net.Network network;
        this.mContext.enforceCallingPermission("android.permission.SET_TIME", "force network time refresh");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                network = this.mDefaultNetwork;
            }
            if (network != null) {
                return this.mEngine.forceRefreshForTests(network, this.mRefreshCallbacks);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPollNetworkTime(@android.annotation.NonNull java.lang.String str) {
        android.net.Network network;
        synchronized (this.mLock) {
            network = this.mDefaultNetwork;
        }
        this.mWakeLock.acquire();
        try {
            this.mEngine.refreshAndRescheduleIfRequired(network, str, this.mRefreshCallbacks);
        } finally {
            this.mWakeLock.release();
        }
    }

    private class ScheduledRefreshAlarmListener implements android.app.AlarmManager.OnAlarmListener, java.lang.Runnable {
        private ScheduledRefreshAlarmListener() {
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            com.android.server.timedetector.NetworkTimeUpdateService.this.mHandler.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.timedetector.NetworkTimeUpdateService.this.onPollNetworkTime("scheduled refresh");
        }
    }

    private class NetworkConnectivityCallback extends android.net.ConnectivityManager.NetworkCallback {
        private NetworkConnectivityCallback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(@android.annotation.NonNull android.net.Network network) {
            android.util.Log.d(com.android.server.timedetector.NetworkTimeUpdateService.TAG, java.lang.String.format("New default network %s; checking time.", network));
            synchronized (com.android.server.timedetector.NetworkTimeUpdateService.this.mLock) {
                com.android.server.timedetector.NetworkTimeUpdateService.this.mDefaultNetwork = network;
            }
            com.android.server.timedetector.NetworkTimeUpdateService.this.onPollNetworkTime("network available");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(@android.annotation.NonNull android.net.Network network) {
            synchronized (com.android.server.timedetector.NetworkTimeUpdateService.this.mLock) {
                try {
                    if (network.equals(com.android.server.timedetector.NetworkTimeUpdateService.this.mDefaultNetwork)) {
                        com.android.server.timedetector.NetworkTimeUpdateService.this.mDefaultNetwork = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private class AutoTimeSettingObserver extends android.database.ContentObserver {
        private final android.content.Context mContext;

        AutoTimeSettingObserver(@android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.content.Context context) {
            super(handler);
            java.util.Objects.requireNonNull(context);
            this.mContext = context;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (isAutomaticTimeEnabled()) {
                com.android.server.timedetector.NetworkTimeUpdateService.this.onPollNetworkTime("automatic time enabled");
            }
        }

        private boolean isAutomaticTimeEnabled() {
            return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "auto_time", 0) != 0;
        }
    }

    @Override // android.os.Binder
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            synchronized (this.mLock) {
                printWriter.println("mDefaultNetwork=" + this.mDefaultNetwork);
            }
            this.mEngine.dump(printWriter);
            printWriter.println();
        }
    }

    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.timedetector.NetworkTimeUpdateServiceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class EngineImpl implements com.android.server.timedetector.NetworkTimeUpdateService.Engine {
        private final java.util.function.Supplier<java.lang.Long> mElapsedRealtimeMillisSupplier;

        @com.android.internal.annotations.GuardedBy({"this"})
        private java.lang.Long mLastRefreshAttemptElapsedRealtimeMillis;

        @android.annotation.NonNull
        private final android.util.LocalLog mLocalDebugLog = new android.util.LocalLog(30, false);
        private final int mNormalPollingIntervalMillis;
        private final android.util.NtpTrustedTime mNtpTrustedTime;
        private final int mShortPollingIntervalMillis;

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mTryAgainCounter;
        private final int mTryAgainTimesMax;

        @com.android.internal.annotations.VisibleForTesting
        EngineImpl(@android.annotation.NonNull java.util.function.Supplier<java.lang.Long> supplier, int i, int i2, int i3, @android.annotation.NonNull android.util.NtpTrustedTime ntpTrustedTime) {
            java.util.Objects.requireNonNull(supplier);
            this.mElapsedRealtimeMillisSupplier = supplier;
            if (i2 > i) {
                throw new java.lang.IllegalArgumentException(java.lang.String.format("shortPollingIntervalMillis (%s) > normalPollingIntervalMillis (%s)", java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i)));
            }
            this.mNormalPollingIntervalMillis = i;
            this.mShortPollingIntervalMillis = i2;
            this.mTryAgainTimesMax = i3;
            java.util.Objects.requireNonNull(ntpTrustedTime);
            this.mNtpTrustedTime = ntpTrustedTime;
        }

        @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine
        public boolean forceRefreshForTests(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks refreshCallbacks) {
            boolean tryRefresh = tryRefresh(network);
            logToDebugAndDumpsys("forceRefreshForTests: refreshSuccessful=" + tryRefresh);
            if (tryRefresh) {
                android.util.NtpTrustedTime.TimeResult cachedTimeResult = this.mNtpTrustedTime.getCachedTimeResult();
                if (cachedTimeResult == null) {
                    logToDebugAndDumpsys("forceRefreshForTests: cachedTimeResult unexpectedly null");
                } else {
                    makeNetworkTimeSuggestion(cachedTimeResult, "EngineImpl.forceRefreshForTests()", refreshCallbacks);
                }
            }
            return tryRefresh;
        }

        @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine
        public void refreshAndRescheduleIfRequired(@android.annotation.Nullable android.net.Network network, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks refreshCallbacks) {
            boolean z;
            boolean z2;
            long j;
            if (network == null) {
                logToDebugAndDumpsys("refreshIfRequiredAndReschedule: reason=" + str + ": No default network available. No refresh attempted and no next attempt scheduled.");
                return;
            }
            android.util.NtpTrustedTime.TimeResult cachedTimeResult = this.mNtpTrustedTime.getCachedTimeResult();
            synchronized (this) {
                try {
                    long longValue = this.mElapsedRealtimeMillisSupplier.get().longValue();
                    z = calculateTimeResultAgeMillis(cachedTimeResult, longValue) >= ((long) this.mNormalPollingIntervalMillis) && isRefreshAllowed(longValue);
                } finally {
                }
            }
            if (!z) {
                z2 = false;
            } else {
                z2 = tryRefresh(network);
            }
            synchronized (this) {
                try {
                    android.util.NtpTrustedTime.TimeResult cachedTimeResult2 = this.mNtpTrustedTime.getCachedTimeResult();
                    long longValue2 = this.mElapsedRealtimeMillisSupplier.get().longValue();
                    long calculateTimeResultAgeMillis = calculateTimeResultAgeMillis(cachedTimeResult2, longValue2);
                    if (z) {
                        if (z2) {
                            this.mTryAgainCounter = 0;
                        } else if (this.mTryAgainTimesMax < 0) {
                            this.mTryAgainCounter = 1;
                        } else {
                            this.mTryAgainCounter++;
                            if (this.mTryAgainCounter > this.mTryAgainTimesMax) {
                                this.mTryAgainCounter = 0;
                            }
                        }
                    }
                    if (calculateTimeResultAgeMillis < this.mNormalPollingIntervalMillis) {
                        this.mTryAgainCounter = 0;
                    }
                    if (calculateTimeResultAgeMillis < this.mNormalPollingIntervalMillis) {
                        makeNetworkTimeSuggestion(cachedTimeResult2, str, refreshCallbacks);
                    }
                    long j2 = this.mTryAgainCounter > 0 ? this.mShortPollingIntervalMillis : this.mNormalPollingIntervalMillis;
                    if (calculateTimeResultAgeMillis < j2) {
                        j = cachedTimeResult2.getElapsedRealtimeMillis() + j2;
                    } else if (this.mLastRefreshAttemptElapsedRealtimeMillis != null) {
                        j = this.mLastRefreshAttemptElapsedRealtimeMillis.longValue() + j2;
                    } else {
                        android.util.Log.w(com.android.server.timedetector.NetworkTimeUpdateService.TAG, "mLastRefreshAttemptElapsedRealtimeMillis unexpectedly missing. Scheduling using currentElapsedRealtimeMillis");
                        logToDebugAndDumpsys("mLastRefreshAttemptElapsedRealtimeMillis unexpectedly missing. Scheduling using currentElapsedRealtimeMillis");
                        j = longValue2 + j2;
                    }
                    if (j <= longValue2) {
                        android.util.Log.w(com.android.server.timedetector.NetworkTimeUpdateService.TAG, "nextRefreshElapsedRealtimeMillis is a time in the past. Scheduling using currentElapsedRealtimeMillis instead");
                        logToDebugAndDumpsys("nextRefreshElapsedRealtimeMillis is a time in the past. Scheduling using currentElapsedRealtimeMillis instead");
                        j = longValue2 + j2;
                    }
                    refreshCallbacks.scheduleNextRefresh(j);
                    logToDebugAndDumpsys("refreshIfRequiredAndReschedule: network=" + network + ", reason=" + str + ", initialTimeResult=" + cachedTimeResult + ", shouldAttemptRefresh=" + z + ", refreshSuccessful=" + z2 + ", currentElapsedRealtimeMillis=" + formatElapsedRealtimeMillis(longValue2) + ", latestTimeResult=" + cachedTimeResult2 + ", mTryAgainCounter=" + this.mTryAgainCounter + ", refreshAttemptDelayMillis=" + j2 + ", nextRefreshElapsedRealtimeMillis=" + formatElapsedRealtimeMillis(j));
                } finally {
                }
            }
        }

        private static java.lang.String formatElapsedRealtimeMillis(long j) {
            return java.time.Duration.ofMillis(j) + " (" + j + ")";
        }

        private static long calculateTimeResultAgeMillis(@android.annotation.Nullable android.util.NtpTrustedTime.TimeResult timeResult, long j) {
            return timeResult == null ? com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME : timeResult.getAgeMillis(j);
        }

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean isRefreshAllowed(long j) {
            return this.mLastRefreshAttemptElapsedRealtimeMillis == null || j >= this.mLastRefreshAttemptElapsedRealtimeMillis.longValue() + ((long) this.mShortPollingIntervalMillis);
        }

        private boolean tryRefresh(@android.annotation.NonNull android.net.Network network) {
            long longValue = this.mElapsedRealtimeMillisSupplier.get().longValue();
            synchronized (this) {
                this.mLastRefreshAttemptElapsedRealtimeMillis = java.lang.Long.valueOf(longValue);
            }
            return this.mNtpTrustedTime.forceRefresh(network);
        }

        private void makeNetworkTimeSuggestion(@android.annotation.NonNull android.util.NtpTrustedTime.TimeResult timeResult, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks refreshCallbacks) {
            com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion = new com.android.server.timedetector.NetworkTimeSuggestion(new android.app.time.UnixEpochTime(timeResult.getElapsedRealtimeMillis(), timeResult.getTimeMillis()), timeResult.getUncertaintyMillis());
            networkTimeSuggestion.addDebugInfo(str);
            networkTimeSuggestion.addDebugInfo(timeResult.toString());
            refreshCallbacks.submitSuggestion(networkTimeSuggestion);
        }

        @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine
        public void dump(java.io.PrintWriter printWriter) {
            java.lang.String formatElapsedRealtimeMillis;
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
            indentingPrintWriter.println("mNormalPollingIntervalMillis=" + this.mNormalPollingIntervalMillis);
            indentingPrintWriter.println("mShortPollingIntervalMillis=" + this.mShortPollingIntervalMillis);
            indentingPrintWriter.println("mTryAgainTimesMax=" + this.mTryAgainTimesMax);
            synchronized (this) {
                try {
                    if (this.mLastRefreshAttemptElapsedRealtimeMillis == null) {
                        formatElapsedRealtimeMillis = "null";
                    } else {
                        formatElapsedRealtimeMillis = formatElapsedRealtimeMillis(this.mLastRefreshAttemptElapsedRealtimeMillis.longValue());
                    }
                    indentingPrintWriter.println("mLastRefreshAttemptElapsedRealtimeMillis=" + formatElapsedRealtimeMillis);
                    indentingPrintWriter.println("mTryAgainCounter=" + this.mTryAgainCounter);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            indentingPrintWriter.println();
            indentingPrintWriter.println("NtpTrustedTime:");
            indentingPrintWriter.increaseIndent();
            this.mNtpTrustedTime.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("Debug log:");
            indentingPrintWriter.increaseIndent();
            this.mLocalDebugLog.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }

        private void logToDebugAndDumpsys(java.lang.String str) {
            this.mLocalDebugLog.log(str);
        }
    }
}
