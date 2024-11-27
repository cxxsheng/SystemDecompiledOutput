package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class TimeZoneDetectorService extends android.app.timezonedetector.ITimeZoneDetectorService.Stub implements android.os.IBinder.DeathRecipient {
    static final boolean DBG = false;
    static final java.lang.String TAG = "time_zone_detector";

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.CallerIdentityInjector mCallerIdentityInjector;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.TimeZoneDetectorStrategy mTimeZoneDetectorStrategy;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mListeners"})
    private final android.util.ArrayMap<android.os.IBinder, android.app.time.ITimeZoneDetectorListener> mListeners = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mDumpables"})
    private final java.util.List<com.android.server.timezonedetector.Dumpable> mDumpables = new java.util.ArrayList();

    public static final class Lifecycle extends com.android.server.SystemService {
        public Lifecycle(@android.annotation.NonNull android.content.Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v1, types: [android.os.IBinder, com.android.server.timezonedetector.TimeZoneDetectorService] */
        @Override // com.android.server.SystemService
        public void onStart() {
            android.content.Context context = getContext();
            android.os.Handler handler = com.android.server.FgThread.getHandler();
            final com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl create = com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.create(handler, com.android.server.timezonedetector.ServiceConfigAccessorImpl.getInstance(context));
            com.android.server.timezonedetector.DeviceActivityMonitor create2 = com.android.server.timezonedetector.DeviceActivityMonitorImpl.create(context, handler);
            create2.addListener(new com.android.server.timezonedetector.DeviceActivityMonitor.Listener() { // from class: com.android.server.timezonedetector.TimeZoneDetectorService.Lifecycle.1
                @Override // com.android.server.timezonedetector.DeviceActivityMonitor.Listener
                public void onFlightComplete() {
                    create.enableTelephonyTimeZoneFallback("onFlightComplete()");
                }
            });
            publishLocalService(com.android.server.timezonedetector.TimeZoneDetectorInternal.class, new com.android.server.timezonedetector.TimeZoneDetectorInternalImpl(context, handler, com.android.server.timezonedetector.CurrentUserIdentityInjector.REAL, create));
            ?? timeZoneDetectorService = new com.android.server.timezonedetector.TimeZoneDetectorService(context, handler, com.android.server.timezonedetector.CallerIdentityInjector.REAL, create);
            timeZoneDetectorService.addDumpable(create2);
            publishBinderService(com.android.server.timezonedetector.TimeZoneDetectorService.TAG, timeZoneDetectorService);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public TimeZoneDetectorService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.timezonedetector.CallerIdentityInjector callerIdentityInjector, @android.annotation.NonNull com.android.server.timezonedetector.TimeZoneDetectorStrategy timeZoneDetectorStrategy) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
        java.util.Objects.requireNonNull(callerIdentityInjector);
        this.mCallerIdentityInjector = callerIdentityInjector;
        java.util.Objects.requireNonNull(timeZoneDetectorStrategy);
        this.mTimeZoneDetectorStrategy = timeZoneDetectorStrategy;
        this.mTimeZoneDetectorStrategy.addChangeListener(new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.timezonedetector.TimeZoneDetectorService$$ExternalSyntheticLambda3
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                com.android.server.timezonedetector.TimeZoneDetectorService.this.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.TimeZoneDetectorService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.TimeZoneDetectorService.this.handleChangeOnHandlerThread();
            }
        });
    }

    @android.annotation.NonNull
    public android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig() {
        return getCapabilitiesAndConfig(this.mCallerIdentityInjector.getCallingUserId());
    }

    android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig(int i) {
        enforceManageTimeZoneDetectorPermission();
        int resolveUserId = this.mCallerIdentityInjector.resolveUserId(i, "getCapabilitiesAndConfig");
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeZoneDetectorStrategy.getCapabilitiesAndConfig(resolveUserId, false);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean updateConfiguration(@android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration) {
        return updateConfiguration(this.mCallerIdentityInjector.getCallingUserId(), timeZoneConfiguration);
    }

    boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration) {
        int resolveUserId = this.mCallerIdentityInjector.resolveUserId(i, "updateConfiguration");
        enforceManageTimeZoneDetectorPermission();
        java.util.Objects.requireNonNull(timeZoneConfiguration);
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeZoneDetectorStrategy.updateConfiguration(resolveUserId, timeZoneConfiguration, false);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void addListener(@android.annotation.NonNull android.app.time.ITimeZoneDetectorListener iTimeZoneDetectorListener) {
        enforceManageTimeZoneDetectorPermission();
        java.util.Objects.requireNonNull(iTimeZoneDetectorListener);
        synchronized (this.mListeners) {
            android.os.IBinder asBinder = iTimeZoneDetectorListener.asBinder();
            if (this.mListeners.containsKey(asBinder)) {
                return;
            }
            try {
                asBinder.linkToDeath(this, 0);
                this.mListeners.put(asBinder, iTimeZoneDetectorListener);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to linkToDeath() for listener=" + iTimeZoneDetectorListener, e);
            }
        }
    }

    public void removeListener(@android.annotation.NonNull android.app.time.ITimeZoneDetectorListener iTimeZoneDetectorListener) {
        enforceManageTimeZoneDetectorPermission();
        java.util.Objects.requireNonNull(iTimeZoneDetectorListener);
        synchronized (this.mListeners) {
            try {
                android.os.IBinder asBinder = iTimeZoneDetectorListener.asBinder();
                boolean z = false;
                if (this.mListeners.remove(asBinder) != null) {
                    asBinder.unlinkToDeath(this, 0);
                    z = true;
                }
                if (!z) {
                    android.util.Slog.w(TAG, "Client asked to remove listener=" + iTimeZoneDetectorListener + ", but no listeners were removed. mListeners=" + this.mListeners);
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

    void handleChangeOnHandlerThread() {
        synchronized (this.mListeners) {
            int size = this.mListeners.size();
            for (int i = 0; i < size; i++) {
                android.app.time.ITimeZoneDetectorListener valueAt = this.mListeners.valueAt(i);
                try {
                    valueAt.onChange();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Unable to notify listener=" + valueAt, e);
                }
            }
        }
    }

    void handleLocationAlgorithmEvent(@android.annotation.NonNull final com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        enforceSuggestGeolocationTimeZonePermission();
        java.util.Objects.requireNonNull(locationAlgorithmEvent);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.TimeZoneDetectorService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.TimeZoneDetectorService.this.lambda$handleLocationAlgorithmEvent$1(locationAlgorithmEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleLocationAlgorithmEvent$1(com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        this.mTimeZoneDetectorStrategy.handleLocationAlgorithmEvent(locationAlgorithmEvent);
    }

    @android.annotation.NonNull
    public android.app.time.TimeZoneState getTimeZoneState() {
        enforceManageTimeZoneDetectorPermission();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeZoneDetectorStrategy.getTimeZoneState();
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void setTimeZoneState(@android.annotation.NonNull android.app.time.TimeZoneState timeZoneState) {
        enforceManageTimeZoneDetectorPermission();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            this.mTimeZoneDetectorStrategy.setTimeZoneState(timeZoneState);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean confirmTimeZone(@android.annotation.NonNull java.lang.String str) {
        enforceManageTimeZoneDetectorPermission();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeZoneDetectorStrategy.confirmTimeZone(str);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setManualTimeZone(@android.annotation.NonNull android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) {
        enforceManageTimeZoneDetectorPermission();
        int callingUserId = this.mCallerIdentityInjector.getCallingUserId();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeZoneDetectorStrategy.suggestManualTimeZone(callingUserId, manualTimeZoneSuggestion, false);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean suggestManualTimeZone(@android.annotation.NonNull android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) {
        enforceSuggestManualTimeZonePermission();
        java.util.Objects.requireNonNull(manualTimeZoneSuggestion);
        int callingUserId = this.mCallerIdentityInjector.getCallingUserId();
        long clearCallingIdentity = this.mCallerIdentityInjector.clearCallingIdentity();
        try {
            return this.mTimeZoneDetectorStrategy.suggestManualTimeZone(callingUserId, manualTimeZoneSuggestion, false);
        } finally {
            this.mCallerIdentityInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void suggestTelephonyTimeZone(@android.annotation.NonNull final android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) {
        enforceSuggestTelephonyTimeZonePermission();
        java.util.Objects.requireNonNull(telephonyTimeZoneSuggestion);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.TimeZoneDetectorService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.TimeZoneDetectorService.this.lambda$suggestTelephonyTimeZone$2(telephonyTimeZoneSuggestion);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$suggestTelephonyTimeZone$2(android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) {
        this.mTimeZoneDetectorStrategy.suggestTelephonyTimeZone(telephonyTimeZoneSuggestion);
    }

    boolean isTelephonyTimeZoneDetectionSupported() {
        enforceManageTimeZoneDetectorPermission();
        return this.mTimeZoneDetectorStrategy.isTelephonyTimeZoneDetectionSupported();
    }

    boolean isGeoTimeZoneDetectionSupported() {
        enforceManageTimeZoneDetectorPermission();
        return this.mTimeZoneDetectorStrategy.isGeoTimeZoneDetectionSupported();
    }

    void enableTelephonyFallback(@android.annotation.NonNull java.lang.String str) {
        enforceManageTimeZoneDetectorPermission();
        this.mTimeZoneDetectorStrategy.enableTelephonyTimeZoneFallback(str);
    }

    void addDumpable(@android.annotation.NonNull com.android.server.timezonedetector.Dumpable dumpable) {
        synchronized (this.mDumpables) {
            this.mDumpables.add(dumpable);
        }
    }

    @android.annotation.NonNull
    com.android.server.timezonedetector.MetricsTimeZoneDetectorState generateMetricsState() {
        enforceManageTimeZoneDetectorPermission();
        return this.mTimeZoneDetectorStrategy.generateMetricsState();
    }

    protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
            this.mTimeZoneDetectorStrategy.dump(indentingPrintWriter, strArr);
            synchronized (this.mDumpables) {
                try {
                    java.util.Iterator<com.android.server.timezonedetector.Dumpable> it = this.mDumpables.iterator();
                    while (it.hasNext()) {
                        it.next().dump(indentingPrintWriter, strArr);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            indentingPrintWriter.flush();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.timezonedetector.TimeZoneDetectorShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private void enforceManageTimeZoneDetectorPermission() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_TIME_AND_ZONE_DETECTION", "manage time and time zone detection");
    }

    private void enforceSuggestGeolocationTimeZonePermission() {
        this.mContext.enforceCallingPermission("android.permission.SET_TIME_ZONE", "suggest geolocation time zone");
    }

    private void enforceSuggestTelephonyTimeZonePermission() {
        this.mContext.enforceCallingPermission("android.permission.SUGGEST_TELEPHONY_TIME_AND_ZONE", "suggest telephony time and time zone");
    }

    private void enforceSuggestManualTimeZonePermission() {
        this.mContext.enforceCallingPermission("android.permission.SUGGEST_MANUAL_TIME_AND_ZONE", "suggest manual time and time zone");
    }
}
