package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
public class LocationTimeZoneManagerService extends android.os.Binder {
    private static final java.lang.String ATTRIBUTION_TAG = "LocationTimeZoneService";
    private static final long BLOCKING_OP_WAIT_DURATION_MILLIS = java.time.Duration.ofSeconds(20).toMillis();
    static final java.lang.String TAG = "LocationTZDetector";

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private com.android.server.timezonedetector.location.LocationTimeZoneProviderController mLocationTimeZoneProviderController;

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerEnvironmentImpl mLocationTimeZoneProviderControllerEnvironment;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.ServiceConfigAccessor mServiceConfigAccessor;

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private final com.android.server.timezonedetector.location.LocationTimeZoneManagerService.ProviderConfig mPrimaryProviderConfig = new com.android.server.timezonedetector.location.LocationTimeZoneManagerService.ProviderConfig(0, "primary", "android.service.timezone.PrimaryLocationTimeZoneProviderService");

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private final com.android.server.timezonedetector.location.LocationTimeZoneManagerService.ProviderConfig mSecondaryProviderConfig = new com.android.server.timezonedetector.location.LocationTimeZoneManagerService.ProviderConfig(1, "secondary", "android.service.timezone.SecondaryLocationTimeZoneProviderService");

    @android.annotation.NonNull
    private final android.os.Handler mHandler = com.android.server.FgThread.getHandler();

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.ThreadingDomain mThreadingDomain = new com.android.server.timezonedetector.location.HandlerThreadingDomain(this.mHandler);

    @android.annotation.NonNull
    private final java.lang.Object mSharedLock = this.mThreadingDomain.getLockObject();

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.timezonedetector.location.LocationTimeZoneManagerService mService;

        @android.annotation.NonNull
        private final com.android.server.timezonedetector.ServiceConfigAccessor mServiceConfigAccessor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Lifecycle(@android.annotation.NonNull android.content.Context context) {
            super(context);
            java.util.Objects.requireNonNull(context);
            this.mServiceConfigAccessor = com.android.server.timezonedetector.ServiceConfigAccessorImpl.getInstance(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            android.content.Context context = getContext();
            if (this.mServiceConfigAccessor.isGeoTimeZoneDetectionFeatureSupportedInConfig()) {
                this.mService = new com.android.server.timezonedetector.location.LocationTimeZoneManagerService(context, this.mServiceConfigAccessor);
                publishBinderService("location_time_zone_manager", this.mService);
            } else {
                android.util.Slog.d(com.android.server.timezonedetector.location.LocationTimeZoneManagerService.TAG, "Geo time zone detection feature is disabled in config");
            }
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (this.mServiceConfigAccessor.isGeoTimeZoneDetectionFeatureSupportedInConfig()) {
                if (i == 500) {
                    this.mService.onSystemReady();
                } else if (i == 600) {
                    this.mService.onSystemThirdPartyAppsCanStart();
                }
            }
        }
    }

    LocationTimeZoneManagerService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.timezonedetector.ServiceConfigAccessor serviceConfigAccessor) {
        this.mContext = context.createAttributionContext(ATTRIBUTION_TAG);
        java.util.Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
    }

    void onSystemReady() {
        this.mServiceConfigAccessor.addLocationTimeZoneManagerConfigListener(new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda4
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.handleServiceConfigurationChangedOnMainThread();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleServiceConfigurationChangedOnMainThread() {
        this.mThreadingDomain.post(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.restartIfRequiredOnDomainThread();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restartIfRequiredOnDomainThread() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                if (this.mLocationTimeZoneProviderController != null) {
                    stopOnDomainThread();
                    startOnDomainThread();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onSystemThirdPartyAppsCanStart() {
        startInternal(false);
    }

    void start() {
        enforceManageTimeZoneDetectorPermission();
        startInternal(true);
    }

    private void startInternal(boolean z) {
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.startOnDomainThread();
            }
        };
        if (z) {
            this.mThreadingDomain.postAndWait(runnable, BLOCKING_OP_WAIT_DURATION_MILLIS);
        } else {
            this.mThreadingDomain.post(runnable);
        }
    }

    void startWithTestProviders(@android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final java.lang.String str2, final boolean z) {
        enforceManageTimeZoneDetectorPermission();
        if (str == null && str2 == null) {
            throw new java.lang.IllegalArgumentException("One or both test package names must be provided.");
        }
        this.mThreadingDomain.postAndWait(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.lambda$startWithTestProviders$0(str, str2, z);
            }
        }, BLOCKING_OP_WAIT_DURATION_MILLIS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startWithTestProviders$0(java.lang.String str, java.lang.String str2, boolean z) {
        synchronized (this.mSharedLock) {
            stopOnDomainThread();
            this.mServiceConfigAccessor.setTestPrimaryLocationTimeZoneProviderPackageName(str);
            this.mServiceConfigAccessor.setTestSecondaryLocationTimeZoneProviderPackageName(str2);
            this.mServiceConfigAccessor.setRecordStateChangesForTests(z);
            startOnDomainThread();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startOnDomainThread() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                if (!this.mServiceConfigAccessor.isGeoTimeZoneDetectionFeatureSupported()) {
                    debugLog("Not starting location_time_zone_manager: it is disabled in service config");
                    return;
                }
                if (this.mLocationTimeZoneProviderController == null) {
                    com.android.server.timezonedetector.location.LocationTimeZoneProvider createProvider = this.mPrimaryProviderConfig.createProvider();
                    com.android.server.timezonedetector.location.LocationTimeZoneProvider createProvider2 = this.mSecondaryProviderConfig.createProvider();
                    com.android.server.timezonedetector.location.LocationTimeZoneProviderController locationTimeZoneProviderController = new com.android.server.timezonedetector.location.LocationTimeZoneProviderController(this.mThreadingDomain, new com.android.server.timezonedetector.location.RealControllerMetricsLogger(), createProvider, createProvider2, this.mServiceConfigAccessor.getRecordStateChangesForTests());
                    com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerEnvironmentImpl locationTimeZoneProviderControllerEnvironmentImpl = new com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerEnvironmentImpl(this.mThreadingDomain, this.mServiceConfigAccessor, locationTimeZoneProviderController);
                    locationTimeZoneProviderController.initialize(locationTimeZoneProviderControllerEnvironmentImpl, new com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerCallbackImpl(this.mThreadingDomain));
                    this.mLocationTimeZoneProviderControllerEnvironment = locationTimeZoneProviderControllerEnvironmentImpl;
                    this.mLocationTimeZoneProviderController = locationTimeZoneProviderController;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void stop() {
        enforceManageTimeZoneDetectorPermission();
        this.mThreadingDomain.postAndWait(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.stopOnDomainThread();
            }
        }, BLOCKING_OP_WAIT_DURATION_MILLIS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopOnDomainThread() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                if (this.mLocationTimeZoneProviderController != null) {
                    this.mLocationTimeZoneProviderController.destroy();
                    this.mLocationTimeZoneProviderController = null;
                    this.mLocationTimeZoneProviderControllerEnvironment.destroy();
                    this.mLocationTimeZoneProviderControllerEnvironment = null;
                    this.mServiceConfigAccessor.resetVolatileTestConfig();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.timezonedetector.location.LocationTimeZoneManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    void clearRecordedProviderStates() {
        enforceManageTimeZoneDetectorPermission();
        this.mThreadingDomain.postAndWait(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.lambda$clearRecordedProviderStates$1();
            }
        }, BLOCKING_OP_WAIT_DURATION_MILLIS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearRecordedProviderStates$1() {
        synchronized (this.mSharedLock) {
            try {
                if (this.mLocationTimeZoneProviderController != null) {
                    this.mLocationTimeZoneProviderController.clearRecordedStates();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState getStateForTests() {
        enforceManageTimeZoneDetectorPermission();
        try {
            return (com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState) this.mThreadingDomain.postAndWait(new java.util.concurrent.Callable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneManagerService$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final java.lang.Object call() {
                    com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState lambda$getStateForTests$2;
                    lambda$getStateForTests$2 = com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.lambda$getStateForTests$2();
                    return lambda$getStateForTests$2;
                }
            }, BLOCKING_OP_WAIT_DURATION_MILLIS);
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState lambda$getStateForTests$2() throws java.lang.Exception {
        synchronized (this.mSharedLock) {
            try {
                if (this.mLocationTimeZoneProviderController == null) {
                    return null;
                }
                return this.mLocationTimeZoneProviderController.getStateForTests();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.Binder
    protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
            synchronized (this.mSharedLock) {
                try {
                    indentingPrintWriter.println("LocationTimeZoneManagerService:");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("Primary provider config:");
                    indentingPrintWriter.increaseIndent();
                    this.mPrimaryProviderConfig.dump(indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("Secondary provider config:");
                    indentingPrintWriter.increaseIndent();
                    this.mSecondaryProviderConfig.dump(indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    if (this.mLocationTimeZoneProviderController == null) {
                        indentingPrintWriter.println("{Stopped}");
                    } else {
                        this.mLocationTimeZoneProviderController.dump(indentingPrintWriter, strArr);
                    }
                    indentingPrintWriter.decreaseIndent();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    static void debugLog(java.lang.String str) {
        if (android.util.Log.isLoggable(TAG, 3)) {
            android.util.Slog.d(TAG, str);
        }
    }

    static void infoLog(java.lang.String str) {
        if (android.util.Log.isLoggable(TAG, 4)) {
            android.util.Slog.i(TAG, str);
        }
    }

    static void warnLog(java.lang.String str) {
        warnLog(str, null);
    }

    static void warnLog(java.lang.String str, @android.annotation.Nullable java.lang.Throwable th) {
        if (android.util.Log.isLoggable(TAG, 5)) {
            android.util.Slog.w(TAG, str, th);
        }
    }

    private void enforceManageTimeZoneDetectorPermission() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_TIME_AND_ZONE_DETECTION", "manage time and time zone detection");
    }

    private final class ProviderConfig implements com.android.server.timezonedetector.Dumpable {
        private final int mIndex;

        @android.annotation.NonNull
        private final java.lang.String mName;

        @android.annotation.NonNull
        private final java.lang.String mServiceAction;

        /* JADX WARN: Code restructure failed: missing block: B:4:0x0008, code lost:
        
            if (r2 <= 1) goto L8;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        ProviderConfig(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
            boolean z = i >= 0;
            com.android.internal.util.Preconditions.checkArgument(z);
            this.mIndex = i;
            java.util.Objects.requireNonNull(str);
            this.mName = str;
            java.util.Objects.requireNonNull(str2);
            this.mServiceAction = str2;
        }

        @android.annotation.NonNull
        com.android.server.timezonedetector.location.LocationTimeZoneProvider createProvider() {
            com.android.server.timezonedetector.location.RealProviderMetricsLogger realProviderMetricsLogger = new com.android.server.timezonedetector.location.RealProviderMetricsLogger(this.mIndex);
            if (java.util.Objects.equals(getMode(), com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED)) {
                return new com.android.server.timezonedetector.location.DisabledLocationTimeZoneProvider(realProviderMetricsLogger, com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mThreadingDomain, this.mName, com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mServiceConfigAccessor.getRecordStateChangesForTests());
            }
            return new com.android.server.timezonedetector.location.BinderLocationTimeZoneProvider(realProviderMetricsLogger, com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mThreadingDomain, this.mName, createBinderProxy(), com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mServiceConfigAccessor.getRecordStateChangesForTests());
        }

        @Override // com.android.server.timezonedetector.Dumpable
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
            indentingPrintWriter.printf("getMode()=%s\n", new java.lang.Object[]{getMode()});
            indentingPrintWriter.printf("getPackageName()=%s\n", new java.lang.Object[]{getPackageName()});
        }

        @android.annotation.NonNull
        private java.lang.String getMode() {
            if (this.mIndex == 0) {
                return com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mServiceConfigAccessor.getPrimaryLocationTimeZoneProviderMode();
            }
            return com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mServiceConfigAccessor.getSecondaryLocationTimeZoneProviderMode();
        }

        @android.annotation.NonNull
        private com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy createBinderProxy() {
            java.lang.String str = this.mServiceAction;
            boolean isTestProvider = isTestProvider();
            return new com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy(com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mContext, com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mHandler, com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mThreadingDomain, str, getPackageName(), isTestProvider);
        }

        private boolean isTestProvider() {
            if (this.mIndex == 0) {
                return com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mServiceConfigAccessor.isTestPrimaryLocationTimeZoneProvider();
            }
            return com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mServiceConfigAccessor.isTestSecondaryLocationTimeZoneProvider();
        }

        @android.annotation.NonNull
        private java.lang.String getPackageName() {
            if (this.mIndex == 0) {
                return com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mServiceConfigAccessor.getPrimaryLocationTimeZoneProviderPackageName();
            }
            return com.android.server.timezonedetector.location.LocationTimeZoneManagerService.this.mServiceConfigAccessor.getSecondaryLocationTimeZoneProviderPackageName();
        }
    }
}
