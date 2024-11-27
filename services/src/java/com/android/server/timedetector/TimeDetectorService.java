package com.android.server.timedetector;

/* loaded from: classes2.dex */
public final class TimeDetectorService extends android.app.timedetector.ITimeDetectorService.Stub implements android.os.IBinder.DeathRecipient {
    static final java.lang.String TAG = "time_detector";

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.CallerIdentityInjector mCallerIdentityInjector;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mListeners"})
    private final android.util.ArrayMap<android.os.IBinder, android.app.time.ITimeDetectorListener> mListeners = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.NtpTrustedTime mNtpTrustedTime;

    @android.annotation.NonNull
    private final com.android.server.timedetector.TimeDetectorStrategy mTimeDetectorStrategy;

    public static class Lifecycle extends com.android.server.SystemService {
        public Lifecycle(@android.annotation.NonNull android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            android.content.Context context = getContext();
            android.os.Handler handler = com.android.server.FgThread.getHandler();
            com.android.server.timedetector.ServiceConfigAccessor serviceConfigAccessorImpl = com.android.server.timedetector.ServiceConfigAccessorImpl.getInstance(context);
            com.android.server.timedetector.TimeDetectorStrategy create = com.android.server.timedetector.TimeDetectorStrategyImpl.create(context, handler, serviceConfigAccessorImpl);
            publishLocalService(com.android.server.timedetector.TimeDetectorInternal.class, new com.android.server.timedetector.TimeDetectorInternalImpl(context, handler, com.android.server.timezonedetector.CurrentUserIdentityInjector.REAL, serviceConfigAccessorImpl, create));
            publishBinderService(com.android.server.timedetector.TimeDetectorService.TAG, new com.android.server.timedetector.TimeDetectorService(context, handler, com.android.server.timezonedetector.CallerIdentityInjector.REAL, create, android.util.NtpTrustedTime.getInstance(context)));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public TimeDetectorService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.timezonedetector.CallerIdentityInjector callerIdentityInjector, @android.annotation.NonNull com.android.server.timedetector.TimeDetectorStrategy timeDetectorStrategy, @android.annotation.NonNull android.util.NtpTrustedTime ntpTrustedTime) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
        java.util.Objects.requireNonNull(callerIdentityInjector);
        this.mCallerIdentityInjector = callerIdentityInjector;
        java.util.Objects.requireNonNull(timeDetectorStrategy);
        this.mTimeDetectorStrategy = timeDetectorStrategy;
        java.util.Objects.requireNonNull(ntpTrustedTime);
        this.mNtpTrustedTime = ntpTrustedTime;
        this.mTimeDetectorStrategy.addChangeListener(new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.timedetector.TimeDetectorService$$ExternalSyntheticLambda4
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                com.android.server.timedetector.TimeDetectorService.this.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timedetector.TimeDetectorService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timedetector.TimeDetectorService.this.handleChangeOnHandlerThread();
            }
        });
    }

    @android.annotation.NonNull
    public android.app.time.TimeCapabilitiesAndConfig getCapabilitiesAndConfig() {
        return getTimeCapabilitiesAndConfig(this.mCallerIdentityInjector.getCallingUserId());
    }

    private android.app.time.TimeCapabilitiesAndConfig getTimeCapabilitiesAndConfig(int i) {
        enforceManageTimeDetectorPermission();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeDetectorStrategy.getCapabilitiesAndConfig(i, false);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean updateConfiguration(@android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration) {
        return updateConfiguration(this.mCallerIdentityInjector.getCallingUserId(), timeConfiguration);
    }

    boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration) {
        int resolveUserId = this.mCallerIdentityInjector.resolveUserId(i, "updateConfiguration");
        enforceManageTimeDetectorPermission();
        java.util.Objects.requireNonNull(timeConfiguration);
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeDetectorStrategy.updateConfiguration(resolveUserId, timeConfiguration, false);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addListener(@android.annotation.NonNull android.app.time.ITimeDetectorListener iTimeDetectorListener) {
        enforceManageTimeDetectorPermission();
        java.util.Objects.requireNonNull(iTimeDetectorListener);
        synchronized (this.mListeners) {
            android.os.IBinder asBinder = iTimeDetectorListener.asBinder();
            if (this.mListeners.containsKey(asBinder)) {
                return;
            }
            try {
                asBinder.linkToDeath(this, 0);
                this.mListeners.put(asBinder, iTimeDetectorListener);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to linkToDeath() for listener=" + iTimeDetectorListener, e);
            }
        }
    }

    public void removeListener(@android.annotation.NonNull android.app.time.ITimeDetectorListener iTimeDetectorListener) {
        enforceManageTimeDetectorPermission();
        java.util.Objects.requireNonNull(iTimeDetectorListener);
        synchronized (this.mListeners) {
            try {
                android.os.IBinder asBinder = iTimeDetectorListener.asBinder();
                boolean z = false;
                if (this.mListeners.remove(asBinder) != null) {
                    asBinder.unlinkToDeath(this, 0);
                    z = true;
                }
                if (!z) {
                    android.util.Slog.w(TAG, "Client asked to remove listener=" + iTimeDetectorListener + ", but no listeners were removed. mListeners=" + this.mListeners);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Slog.wtf(TAG, "binderDied() called unexpectedly.");
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(android.os.IBinder iBinder) {
        synchronized (this.mListeners) {
            try {
                boolean z = true;
                int size = this.mListeners.size() - 1;
                while (true) {
                    if (size < 0) {
                        z = false;
                        break;
                    } else if (!this.mListeners.keyAt(size).equals(iBinder)) {
                        size--;
                    } else {
                        this.mListeners.removeAt(size);
                        break;
                    }
                }
                if (!z) {
                    android.util.Slog.w(TAG, "Notified of binder death for who=" + iBinder + ", but did not remove any listeners. mListeners=" + this.mListeners);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChangeOnHandlerThread() {
        synchronized (this.mListeners) {
            int size = this.mListeners.size();
            for (int i = 0; i < size; i++) {
                android.app.time.ITimeDetectorListener valueAt = this.mListeners.valueAt(i);
                try {
                    valueAt.onChange();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Unable to notify listener=" + valueAt, e);
                }
            }
        }
    }

    public android.app.time.TimeState getTimeState() {
        enforceManageTimeDetectorPermission();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeDetectorStrategy.getTimeState();
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void setTimeState(@android.annotation.NonNull android.app.time.TimeState timeState) {
        enforceManageTimeDetectorPermission();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            this.mTimeDetectorStrategy.setTimeState(timeState);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean confirmTime(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime) {
        enforceManageTimeDetectorPermission();
        java.util.Objects.requireNonNull(unixEpochTime);
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeDetectorStrategy.confirmTime(unixEpochTime);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setManualTime(@android.annotation.NonNull android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) {
        enforceManageTimeDetectorPermission();
        java.util.Objects.requireNonNull(manualTimeSuggestion);
        int callingUserId = this.mCallerIdentityInjector.getCallingUserId();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeDetectorStrategy.suggestManualTime(callingUserId, manualTimeSuggestion, false);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void suggestTelephonyTime(@android.annotation.NonNull final android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) {
        enforceSuggestTelephonyTimePermission();
        java.util.Objects.requireNonNull(telephonyTimeSuggestion);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timedetector.TimeDetectorService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timedetector.TimeDetectorService.this.lambda$suggestTelephonyTime$1(telephonyTimeSuggestion);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$suggestTelephonyTime$1(android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) {
        this.mTimeDetectorStrategy.suggestTelephonyTime(telephonyTimeSuggestion);
    }

    public boolean suggestManualTime(@android.annotation.NonNull android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) {
        enforceSuggestManualTimePermission();
        java.util.Objects.requireNonNull(manualTimeSuggestion);
        int callingUserId = this.mCallerIdentityInjector.getCallingUserId();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeDetectorStrategy.suggestManualTime(callingUserId, manualTimeSuggestion, false);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void suggestNetworkTime(@android.annotation.NonNull final com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion) {
        enforceSuggestNetworkTimePermission();
        java.util.Objects.requireNonNull(networkTimeSuggestion);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timedetector.TimeDetectorService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timedetector.TimeDetectorService.this.lambda$suggestNetworkTime$2(networkTimeSuggestion);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$suggestNetworkTime$2(com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion) {
        this.mTimeDetectorStrategy.suggestNetworkTime(networkTimeSuggestion);
    }

    void clearLatestNetworkTime() {
        enforceSuggestNetworkTimePermission();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            this.mTimeDetectorStrategy.clearLatestNetworkSuggestion();
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.app.time.UnixEpochTime latestNetworkTime() {
        com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion;
        if (com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.isInUse()) {
            networkTimeSuggestion = this.mTimeDetectorStrategy.getLatestNetworkSuggestion();
        } else {
            android.util.NtpTrustedTime.TimeResult cachedTimeResult = this.mNtpTrustedTime.getCachedTimeResult();
            if (cachedTimeResult != null) {
                networkTimeSuggestion = new com.android.server.timedetector.NetworkTimeSuggestion(new android.app.time.UnixEpochTime(cachedTimeResult.getElapsedRealtimeMillis(), cachedTimeResult.getTimeMillis()), cachedTimeResult.getUncertaintyMillis());
            } else {
                networkTimeSuggestion = null;
            }
        }
        if (networkTimeSuggestion == null) {
            throw new android.os.ParcelableException(new java.time.DateTimeException("Missing network time fix"));
        }
        return networkTimeSuggestion.getUnixEpochTime();
    }

    @android.annotation.Nullable
    com.android.server.timedetector.NetworkTimeSuggestion getLatestNetworkSuggestion() {
        return this.mTimeDetectorStrategy.getLatestNetworkSuggestion();
    }

    void suggestGnssTime(@android.annotation.NonNull final com.android.server.timedetector.GnssTimeSuggestion gnssTimeSuggestion) {
        enforceSuggestGnssTimePermission();
        java.util.Objects.requireNonNull(gnssTimeSuggestion);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timedetector.TimeDetectorService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timedetector.TimeDetectorService.this.lambda$suggestGnssTime$3(gnssTimeSuggestion);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$suggestGnssTime$3(com.android.server.timedetector.GnssTimeSuggestion gnssTimeSuggestion) {
        this.mTimeDetectorStrategy.suggestGnssTime(gnssTimeSuggestion);
    }

    public void suggestExternalTime(@android.annotation.NonNull final android.app.time.ExternalTimeSuggestion externalTimeSuggestion) {
        enforceSuggestExternalTimePermission();
        java.util.Objects.requireNonNull(externalTimeSuggestion);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timedetector.TimeDetectorService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timedetector.TimeDetectorService.this.lambda$suggestExternalTime$4(externalTimeSuggestion);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$suggestExternalTime$4(android.app.time.ExternalTimeSuggestion externalTimeSuggestion) {
        this.mTimeDetectorStrategy.suggestExternalTime(externalTimeSuggestion);
    }

    void setNetworkTimeForSystemClockForTests(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, int i) {
        enforceSuggestNetworkTimePermission();
        if (com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.isInUse()) {
            com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion = new com.android.server.timedetector.NetworkTimeSuggestion(unixEpochTime, i);
            networkTimeSuggestion.addDebugInfo("Injected for tests");
            this.mTimeDetectorStrategy.suggestNetworkTime(networkTimeSuggestion);
        } else {
            this.mNtpTrustedTime.setCachedTimeResult(new android.util.NtpTrustedTime.TimeResult(unixEpochTime.getUnixEpochTimeMillis(), unixEpochTime.getElapsedRealtimeMillis(), i, java.net.InetSocketAddress.createUnresolved("time.set.for.tests", 123)));
        }
    }

    void clearNetworkTimeForSystemClockForTests() {
        enforceSuggestNetworkTimePermission();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            if (com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.isInUse()) {
                this.mTimeDetectorStrategy.clearLatestNetworkSuggestion();
            } else {
                this.mNtpTrustedTime.clearCachedTimeResult();
            }
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
            this.mTimeDetectorStrategy.dump(indentingPrintWriter, strArr);
            indentingPrintWriter.flush();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.timedetector.TimeDetectorShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private void enforceSuggestTelephonyTimePermission() {
        this.mContext.enforceCallingPermission("android.permission.SUGGEST_TELEPHONY_TIME_AND_ZONE", "suggest telephony time and time zone");
    }

    private void enforceSuggestManualTimePermission() {
        this.mContext.enforceCallingPermission("android.permission.SUGGEST_MANUAL_TIME_AND_ZONE", "suggest manual time and time zone");
    }

    private void enforceSuggestNetworkTimePermission() {
        this.mContext.enforceCallingPermission("android.permission.SET_TIME", "suggest network time");
    }

    private void enforceSuggestGnssTimePermission() {
        this.mContext.enforceCallingPermission("android.permission.SET_TIME", "suggest gnss time");
    }

    private void enforceSuggestExternalTimePermission() {
        this.mContext.enforceCallingPermission("android.permission.SUGGEST_EXTERNAL_TIME", "suggest time from external source");
    }

    private void enforceManageTimeDetectorPermission() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_TIME_AND_ZONE_DETECTION", "manage time and time zone detection");
    }
}
