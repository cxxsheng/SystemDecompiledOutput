package com.android.server;

/* loaded from: classes.dex */
public class PackageWatchdog {
    private static final java.lang.String ATTR_DURATION = "duration";
    private static final java.lang.String ATTR_EXPLICIT_HEALTH_CHECK_DURATION = "health-check-duration";
    private static final java.lang.String ATTR_MITIGATION_CALLS = "mitigation-calls";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_PASSED_HEALTH_CHECK = "passed-health-check";
    private static final java.lang.String ATTR_VERSION = "version";
    private static final int DB_VERSION = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_BOOT_LOOP_TRIGGER_COUNT = 5;
    private static final boolean DEFAULT_EXPLICIT_HEALTH_CHECK_ENABLED = true;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_TRIGGER_FAILURE_COUNT = 5;
    public static final int FAILURE_REASON_APP_CRASH = 3;
    public static final int FAILURE_REASON_APP_NOT_RESPONDING = 4;
    public static final int FAILURE_REASON_BOOT_LOOP = 5;
    public static final int FAILURE_REASON_EXPLICIT_HEALTH_CHECK = 2;
    public static final int FAILURE_REASON_NATIVE_CRASH = 1;
    public static final int FAILURE_REASON_UNKNOWN = 0;
    private static final java.lang.String METADATA_FILE = "/metadata/watchdog/mitigation_count.txt";
    static final java.lang.String PROPERTY_WATCHDOG_EXPLICIT_HEALTH_CHECK_ENABLED = "watchdog_explicit_health_check_enabled";
    static final java.lang.String PROPERTY_WATCHDOG_TRIGGER_DURATION_MILLIS = "watchdog_trigger_failure_duration_millis";
    static final java.lang.String PROPERTY_WATCHDOG_TRIGGER_FAILURE_COUNT = "watchdog_trigger_failure_count";
    private static final java.lang.String TAG = "PackageWatchdog";
    private static final java.lang.String TAG_OBSERVER = "observer";
    private static final java.lang.String TAG_PACKAGE = "package";
    private static final java.lang.String TAG_PACKAGE_WATCHDOG = "package-watchdog";

    @com.android.internal.annotations.GuardedBy({"PackageWatchdog.class"})
    private static com.android.server.PackageWatchdog sPackageWatchdog;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.PackageWatchdog.ObserverInternal> mAllObservers;
    private final com.android.server.PackageWatchdog.BootThreshold mBootThreshold;
    private final android.net.ConnectivityModuleConnector mConnectivityModuleConnector;
    private final android.content.Context mContext;
    private final com.android.server.ExplicitHealthCheckController mHealthCheckController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsHealthCheckEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsPackagesReady;
    private final java.lang.Object mLock;
    private final android.os.Handler mLongTaskHandler;
    private long mNumberOfNativeCrashPollsRemaining;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mOnPropertyChangedListener;
    private final android.util.AtomicFile mPolicyFile;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Set<java.lang.String> mRequestedHealthCheckPackages;
    private final java.lang.Runnable mSaveToFile;
    private final android.os.Handler mShortTaskHandler;
    private final java.lang.Runnable mSyncRequests;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSyncRequired;
    private final java.lang.Runnable mSyncStateWithScheduledReason;
    private final com.android.server.PackageWatchdog.SystemClock mSystemClock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mTriggerFailureCount;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mTriggerFailureDurationMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mUptimeAtLastStateSync;
    private static final long NATIVE_CRASH_POLLING_INTERVAL_MILLIS = java.util.concurrent.TimeUnit.SECONDS.toMillis(30);

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_TRIGGER_FAILURE_DURATION_MS = (int) java.util.concurrent.TimeUnit.MINUTES.toMillis(1);

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_OBSERVING_DURATION_MS = java.util.concurrent.TimeUnit.DAYS.toMillis(2);

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_DEESCALATION_WINDOW_MS = java.util.concurrent.TimeUnit.HOURS.toMillis(1);
    private static final long NUMBER_OF_NATIVE_CRASH_POLLS = 10;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_BOOT_LOOP_TRIGGER_WINDOW_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(NUMBER_OF_NATIVE_CRASH_POLLS);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FailureReasons {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HealthCheckState {
        public static final int ACTIVE = 0;
        public static final int FAILED = 3;
        public static final int INACTIVE = 1;
        public static final int PASSED = 2;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PackageHealthObserverImpact {
        public static final int USER_IMPACT_LEVEL_0 = 0;
        public static final int USER_IMPACT_LEVEL_10 = 10;
        public static final int USER_IMPACT_LEVEL_100 = 100;
        public static final int USER_IMPACT_LEVEL_30 = 30;
        public static final int USER_IMPACT_LEVEL_50 = 50;
        public static final int USER_IMPACT_LEVEL_70 = 70;
        public static final int USER_IMPACT_LEVEL_90 = 90;
    }

    @java.lang.FunctionalInterface
    @com.android.internal.annotations.VisibleForTesting
    interface SystemClock {
        long uptimeMillis();
    }

    private PackageWatchdog(android.content.Context context) {
        this(context, new android.util.AtomicFile(new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "system"), "package-watchdog.xml")), new android.os.Handler(android.os.Looper.myLooper()), android.util.BackgroundThread.getHandler(), new com.android.server.ExplicitHealthCheckController(context), android.net.ConnectivityModuleConnector.getInstance(), new com.android.server.PackageWatchdog.SystemClock() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda1
            @Override // com.android.server.PackageWatchdog.SystemClock
            public final long uptimeMillis() {
                return android.os.SystemClock.uptimeMillis();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    PackageWatchdog(android.content.Context context, android.util.AtomicFile atomicFile, android.os.Handler handler, android.os.Handler handler2, com.android.server.ExplicitHealthCheckController explicitHealthCheckController, android.net.ConnectivityModuleConnector connectivityModuleConnector, com.android.server.PackageWatchdog.SystemClock systemClock) {
        this.mLock = new java.lang.Object();
        this.mAllObservers = new android.util.ArrayMap<>();
        this.mSyncRequests = new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.PackageWatchdog.this.syncRequests();
            }
        };
        this.mSyncStateWithScheduledReason = new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.PackageWatchdog.this.syncStateWithScheduledReason();
            }
        };
        this.mSaveToFile = new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.PackageWatchdog.this.saveToFile();
            }
        };
        this.mOnPropertyChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda13
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.PackageWatchdog.this.onPropertyChanged(properties);
            }
        };
        this.mRequestedHealthCheckPackages = new android.util.ArraySet();
        this.mIsHealthCheckEnabled = true;
        this.mTriggerFailureDurationMs = DEFAULT_TRIGGER_FAILURE_DURATION_MS;
        this.mTriggerFailureCount = 5;
        this.mSyncRequired = false;
        this.mContext = context;
        this.mPolicyFile = atomicFile;
        this.mShortTaskHandler = handler;
        this.mLongTaskHandler = handler2;
        this.mHealthCheckController = explicitHealthCheckController;
        this.mConnectivityModuleConnector = connectivityModuleConnector;
        this.mSystemClock = systemClock;
        this.mNumberOfNativeCrashPollsRemaining = NUMBER_OF_NATIVE_CRASH_POLLS;
        this.mBootThreshold = new com.android.server.PackageWatchdog.BootThreshold(5, DEFAULT_BOOT_LOOP_TRIGGER_WINDOW_MS);
        loadFromFile();
        sPackageWatchdog = this;
    }

    public static com.android.server.PackageWatchdog getInstance(android.content.Context context) {
        com.android.server.PackageWatchdog packageWatchdog;
        synchronized (com.android.server.PackageWatchdog.class) {
            try {
                if (sPackageWatchdog == null) {
                    new com.android.server.PackageWatchdog(context);
                }
                packageWatchdog = sPackageWatchdog;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return packageWatchdog;
    }

    public void onPackagesReady() {
        synchronized (this.mLock) {
            this.mIsPackagesReady = true;
            this.mHealthCheckController.setCallbacks(new java.util.function.Consumer() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.PackageWatchdog.this.lambda$onPackagesReady$0((java.lang.String) obj);
                }
            }, new java.util.function.Consumer() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.PackageWatchdog.this.lambda$onPackagesReady$1((java.util.List) obj);
                }
            }, new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.PackageWatchdog.this.onSyncRequestNotified();
                }
            });
            setPropertyChangedListenerLocked();
            updateConfigs();
            registerConnectivityModuleHealthListener();
        }
    }

    public void registerHealthObserver(com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver) {
        synchronized (this.mLock) {
            try {
                com.android.server.PackageWatchdog.ObserverInternal observerInternal = this.mAllObservers.get(packageHealthObserver.getName());
                if (observerInternal != null) {
                    observerInternal.registeredObserver = packageHealthObserver;
                } else {
                    com.android.server.PackageWatchdog.ObserverInternal observerInternal2 = new com.android.server.PackageWatchdog.ObserverInternal(packageHealthObserver.getName(), new java.util.ArrayList());
                    observerInternal2.registeredObserver = packageHealthObserver;
                    this.mAllObservers.put(packageHealthObserver.getName(), observerInternal2);
                    syncState("added new observer");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void startObservingHealth(final com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver, final java.util.List<java.lang.String> list, long j) {
        if (list.isEmpty()) {
            android.util.Slog.wtf(TAG, "No packages to observe, " + packageHealthObserver.getName());
            return;
        }
        if (j < 1) {
            android.util.Slog.wtf(TAG, "Invalid duration " + j + "ms for observer " + packageHealthObserver.getName() + ". Not observing packages " + list);
            j = DEFAULT_OBSERVING_DURATION_MS;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            com.android.server.PackageWatchdog.MonitoredPackage newMonitoredPackage = newMonitoredPackage(list.get(i), j, false);
            if (newMonitoredPackage != null) {
                arrayList.add(newMonitoredPackage);
            } else {
                android.util.Slog.w(TAG, "Failed to create MonitoredPackage for pkg=" + list.get(i));
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mLongTaskHandler.post(new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.PackageWatchdog.this.lambda$startObservingHealth$2(packageHealthObserver, list, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObservingHealth$2(com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver, java.util.List list, java.util.List list2) {
        syncState("observing new packages");
        synchronized (this.mLock) {
            try {
                com.android.server.PackageWatchdog.ObserverInternal observerInternal = this.mAllObservers.get(packageHealthObserver.getName());
                if (observerInternal == null) {
                    android.util.Slog.d(TAG, packageHealthObserver.getName() + " started monitoring health of packages " + list);
                    this.mAllObservers.put(packageHealthObserver.getName(), new com.android.server.PackageWatchdog.ObserverInternal(packageHealthObserver.getName(), list2));
                } else {
                    android.util.Slog.d(TAG, packageHealthObserver.getName() + " added the following packages to monitor " + list);
                    observerInternal.updatePackagesLocked(list2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        registerHealthObserver(packageHealthObserver);
        syncState("updated observers");
    }

    public void unregisterHealthObserver(final com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver) {
        this.mLongTaskHandler.post(new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.PackageWatchdog.this.lambda$unregisterHealthObserver$3(packageHealthObserver);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterHealthObserver$3(com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver) {
        synchronized (this.mLock) {
            this.mAllObservers.remove(packageHealthObserver.getName());
        }
        syncState("unregistering observer: " + packageHealthObserver.getName());
    }

    public void onPackageFailure(final java.util.List<android.content.pm.VersionedPackage> list, final int i) {
        if (list == null) {
            android.util.Slog.w(TAG, "Could not resolve a list of failing packages");
        } else {
            this.mLongTaskHandler.post(new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.PackageWatchdog.this.lambda$onPackageFailure$4(i, list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPackageFailure$4(int i, java.util.List list) {
        int i2;
        int i3;
        synchronized (this.mLock) {
            try {
                if (this.mAllObservers.isEmpty()) {
                    return;
                }
                if (i == 1 || i == 2) {
                    handleFailureImmediately(list, i);
                } else {
                    for (int i4 = 0; i4 < list.size(); i4++) {
                        android.content.pm.VersionedPackage versionedPackage = (android.content.pm.VersionedPackage) list.get(i4);
                        com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver = null;
                        int i5 = Integer.MAX_VALUE;
                        com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage = null;
                        for (int i6 = 0; i6 < this.mAllObservers.size(); i6++) {
                            com.android.server.PackageWatchdog.ObserverInternal valueAt = this.mAllObservers.valueAt(i6);
                            com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver2 = valueAt.registeredObserver;
                            if (packageHealthObserver2 != null && valueAt.onPackageFailureLocked(versionedPackage.getPackageName())) {
                                com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage2 = valueAt.getMonitoredPackage(versionedPackage.getPackageName());
                                if (monitoredPackage2 == null) {
                                    i3 = 1;
                                } else {
                                    i3 = monitoredPackage2.getMitigationCountLocked() + 1;
                                }
                                int onHealthCheckFailed = packageHealthObserver2.onHealthCheckFailed(versionedPackage, i, i3);
                                if (onHealthCheckFailed != 0 && onHealthCheckFailed < i5) {
                                    monitoredPackage = monitoredPackage2;
                                    packageHealthObserver = packageHealthObserver2;
                                    i5 = onHealthCheckFailed;
                                }
                            }
                        }
                        if (packageHealthObserver != null) {
                            if (monitoredPackage == null) {
                                i2 = 1;
                            } else {
                                monitoredPackage.noteMitigationCallLocked();
                                i2 = monitoredPackage.getMitigationCountLocked();
                            }
                            packageHealthObserver.execute(versionedPackage, i, i2);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void handleFailureImmediately(java.util.List<android.content.pm.VersionedPackage> list, int i) {
        int onHealthCheckFailed;
        com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver = null;
        android.content.pm.VersionedPackage versionedPackage = list.size() > 0 ? list.get(0) : null;
        java.util.Iterator<com.android.server.PackageWatchdog.ObserverInternal> it = this.mAllObservers.values().iterator();
        int i2 = Integer.MAX_VALUE;
        while (it.hasNext()) {
            com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver2 = it.next().registeredObserver;
            if (packageHealthObserver2 != null && (onHealthCheckFailed = packageHealthObserver2.onHealthCheckFailed(versionedPackage, i, 1)) != 0 && onHealthCheckFailed < i2) {
                packageHealthObserver = packageHealthObserver2;
                i2 = onHealthCheckFailed;
            }
        }
        if (packageHealthObserver != null) {
            packageHealthObserver.execute(versionedPackage, i, 1);
        }
    }

    public void noteBoot() {
        int onBootLoop;
        synchronized (this.mLock) {
            try {
                if (this.mBootThreshold.incrementAndTest()) {
                    this.mBootThreshold.reset();
                    int mitigationCount = this.mBootThreshold.getMitigationCount() + 1;
                    com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver = null;
                    int i = Integer.MAX_VALUE;
                    for (int i2 = 0; i2 < this.mAllObservers.size(); i2++) {
                        com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver2 = this.mAllObservers.valueAt(i2).registeredObserver;
                        if (packageHealthObserver2 != null && (onBootLoop = packageHealthObserver2.onBootLoop(mitigationCount)) != 0 && onBootLoop < i) {
                            packageHealthObserver = packageHealthObserver2;
                            i = onBootLoop;
                        }
                    }
                    if (packageHealthObserver != null) {
                        this.mBootThreshold.setMitigationCount(mitigationCount);
                        this.mBootThreshold.saveMitigationCountToMetadata();
                        packageHealthObserver.executeBootLoopMitigation(mitigationCount);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void writeNow() {
        synchronized (this.mLock) {
            try {
                if (!this.mAllObservers.isEmpty()) {
                    this.mLongTaskHandler.removeCallbacks(this.mSaveToFile);
                    pruneObserversLocked();
                    saveToFile();
                    android.util.Slog.i(TAG, "Last write to update package durations");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setExplicitHealthCheckEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                this.mIsHealthCheckEnabled = z;
                this.mHealthCheckController.setEnabled(z);
                this.mSyncRequired = true;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("health check state ");
                sb.append(z ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
                syncState(sb.toString());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: checkAndMitigateNativeCrashes, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$scheduleCheckAndMitigateNativeCrashes$6() {
        this.mNumberOfNativeCrashPollsRemaining--;
        if ("1".equals(android.os.SystemProperties.get("sys.init.updatable_crashing"))) {
            onPackageFailure(java.util.Collections.EMPTY_LIST, 1);
        } else if (this.mNumberOfNativeCrashPollsRemaining > 0) {
            this.mShortTaskHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.PackageWatchdog.this.lambda$checkAndMitigateNativeCrashes$5();
                }
            }, NATIVE_CRASH_POLLING_INTERVAL_MILLIS);
        }
    }

    public void scheduleCheckAndMitigateNativeCrashes() {
        android.util.Slog.i(TAG, "Scheduling " + this.mNumberOfNativeCrashPollsRemaining + " polls to check and mitigate native crashes");
        this.mShortTaskHandler.post(new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.PackageWatchdog.this.lambda$scheduleCheckAndMitigateNativeCrashes$6();
            }
        });
    }

    public interface PackageHealthObserver {
        boolean execute(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage, int i, int i2);

        java.lang.String getName();

        int onHealthCheckFailed(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage, int i, int i2);

        default int onBootLoop(int i) {
            return 0;
        }

        default boolean executeBootLoopMitigation(int i) {
            return false;
        }

        default boolean isPersistent() {
            return false;
        }

        default boolean mayObservePackage(java.lang.String str) {
            return false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    long getTriggerFailureCount() {
        long j;
        synchronized (this.mLock) {
            j = this.mTriggerFailureCount;
        }
        return j;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getTriggerFailureDurationMs() {
        long j;
        synchronized (this.mLock) {
            j = this.mTriggerFailureDurationMs;
        }
        return j;
    }

    private void syncRequestsAsync() {
        this.mShortTaskHandler.removeCallbacks(this.mSyncRequests);
        this.mShortTaskHandler.post(this.mSyncRequests);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncRequests() {
        boolean z;
        synchronized (this.mLock) {
            try {
                if (this.mIsPackagesReady) {
                    java.util.Set<java.lang.String> packagesPendingHealthChecksLocked = getPackagesPendingHealthChecksLocked();
                    if (!this.mSyncRequired) {
                        if (packagesPendingHealthChecksLocked.equals(this.mRequestedHealthCheckPackages)) {
                            if (packagesPendingHealthChecksLocked.isEmpty()) {
                            }
                        }
                    }
                    this.mRequestedHealthCheckPackages = packagesPendingHealthChecksLocked;
                    z = true;
                }
                z = false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            android.util.Slog.i(TAG, "Syncing health check requests for packages: " + this.mRequestedHealthCheckPackages);
            this.mHealthCheckController.syncRequests(this.mRequestedHealthCheckPackages);
            this.mSyncRequired = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onHealthCheckPassed, reason: merged with bridge method [inline-methods] */
    public void lambda$onPackagesReady$0(java.lang.String str) {
        boolean z;
        android.util.Slog.i(TAG, "Health check passed for package: " + str);
        synchronized (this.mLock) {
            z = false;
            for (int i = 0; i < this.mAllObservers.size(); i++) {
                try {
                    com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage = this.mAllObservers.valueAt(i).getMonitoredPackage(str);
                    if (monitoredPackage != null) {
                        z |= monitoredPackage.getHealthCheckStateLocked() != monitoredPackage.tryPassHealthCheckLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (z) {
            syncState("health check passed for " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSupportedPackages, reason: merged with bridge method [inline-methods] */
    public void lambda$onPackagesReady$1(java.util.List<android.service.watchdog.ExplicitHealthCheckService.PackageConfig> list) {
        boolean z;
        int tryPassHealthCheckLocked;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (android.service.watchdog.ExplicitHealthCheckService.PackageConfig packageConfig : list) {
            arrayMap.put(packageConfig.getPackageName(), java.lang.Long.valueOf(packageConfig.getHealthCheckTimeoutMillis()));
        }
        synchronized (this.mLock) {
            try {
                android.util.Slog.d(TAG, "Received supported packages " + list);
                java.util.Iterator<com.android.server.PackageWatchdog.ObserverInternal> it = this.mAllObservers.values().iterator();
                z = false;
                while (it.hasNext()) {
                    for (com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage : it.next().getMonitoredPackages().values()) {
                        java.lang.String name = monitoredPackage.getName();
                        int healthCheckStateLocked = monitoredPackage.getHealthCheckStateLocked();
                        if (arrayMap.containsKey(name)) {
                            tryPassHealthCheckLocked = monitoredPackage.setHealthCheckActiveLocked(((java.lang.Long) arrayMap.get(name)).longValue());
                        } else {
                            tryPassHealthCheckLocked = monitoredPackage.tryPassHealthCheckLocked();
                        }
                        z |= healthCheckStateLocked != tryPassHealthCheckLocked;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            syncState("updated health check supported packages " + list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSyncRequestNotified() {
        synchronized (this.mLock) {
            this.mSyncRequired = true;
            syncRequestsAsync();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Set<java.lang.String> getPackagesPendingHealthChecksLocked() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<com.android.server.PackageWatchdog.ObserverInternal> it = this.mAllObservers.values().iterator();
        while (it.hasNext()) {
            for (com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage : it.next().getMonitoredPackages().values()) {
                java.lang.String name = monitoredPackage.getName();
                if (monitoredPackage.isPendingHealthChecksLocked()) {
                    arraySet.add(name);
                }
            }
        }
        return arraySet;
    }

    private void syncState(java.lang.String str) {
        synchronized (this.mLock) {
            android.util.Slog.i(TAG, "Syncing state, reason: " + str);
            pruneObserversLocked();
            saveToFileAsync();
            syncRequestsAsync();
            scheduleNextSyncStateLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncStateWithScheduledReason() {
        syncState("scheduled");
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void scheduleNextSyncStateLocked() {
        long nextStateSyncMillisLocked = getNextStateSyncMillisLocked();
        this.mShortTaskHandler.removeCallbacks(this.mSyncStateWithScheduledReason);
        if (nextStateSyncMillisLocked == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            android.util.Slog.i(TAG, "Cancelling state sync, nothing to sync");
            this.mUptimeAtLastStateSync = 0L;
        } else {
            this.mUptimeAtLastStateSync = this.mSystemClock.uptimeMillis();
            this.mShortTaskHandler.postDelayed(this.mSyncStateWithScheduledReason, nextStateSyncMillisLocked);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long getNextStateSyncMillisLocked() {
        long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        for (int i = 0; i < this.mAllObservers.size(); i++) {
            android.util.ArrayMap<java.lang.String, com.android.server.PackageWatchdog.MonitoredPackage> monitoredPackages = this.mAllObservers.valueAt(i).getMonitoredPackages();
            for (int i2 = 0; i2 < monitoredPackages.size(); i2++) {
                long shortestScheduleDurationMsLocked = monitoredPackages.valueAt(i2).getShortestScheduleDurationMsLocked();
                if (shortestScheduleDurationMsLocked < j) {
                    j = shortestScheduleDurationMsLocked;
                }
            }
        }
        return j;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void pruneObserversLocked() {
        long uptimeMillis = this.mUptimeAtLastStateSync == 0 ? 0L : this.mSystemClock.uptimeMillis() - this.mUptimeAtLastStateSync;
        if (uptimeMillis <= 0) {
            android.util.Slog.i(TAG, "Not pruning observers, elapsed time: " + uptimeMillis + "ms");
            return;
        }
        java.util.Iterator<com.android.server.PackageWatchdog.ObserverInternal> it = this.mAllObservers.values().iterator();
        while (it.hasNext()) {
            com.android.server.PackageWatchdog.ObserverInternal next = it.next();
            java.util.Set<com.android.server.PackageWatchdog.MonitoredPackage> prunePackagesLocked = next.prunePackagesLocked(uptimeMillis);
            if (!prunePackagesLocked.isEmpty()) {
                onHealthCheckFailed(next, prunePackagesLocked);
            }
            if (next.getMonitoredPackages().isEmpty() && (next.registeredObserver == null || !next.registeredObserver.isPersistent())) {
                android.util.Slog.i(TAG, "Discarding observer " + next.name + ". All packages expired");
                it.remove();
            }
        }
    }

    private void onHealthCheckFailed(final com.android.server.PackageWatchdog.ObserverInternal observerInternal, final java.util.Set<com.android.server.PackageWatchdog.MonitoredPackage> set) {
        this.mLongTaskHandler.post(new java.lang.Runnable() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.PackageWatchdog.this.lambda$onHealthCheckFailed$7(observerInternal, set);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHealthCheckFailed$7(com.android.server.PackageWatchdog.ObserverInternal observerInternal, java.util.Set set) {
        synchronized (this.mLock) {
            try {
                com.android.server.PackageWatchdog.PackageHealthObserver packageHealthObserver = observerInternal.registeredObserver;
                if (packageHealthObserver != null) {
                    java.util.Iterator it = set.iterator();
                    while (it.hasNext()) {
                        android.content.pm.VersionedPackage versionedPackage = getVersionedPackage(((com.android.server.PackageWatchdog.MonitoredPackage) it.next()).getName());
                        if (versionedPackage != null) {
                            android.util.Slog.i(TAG, "Explicit health check failed for package " + versionedPackage);
                            packageHealthObserver.execute(versionedPackage, 2, 1);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.content.pm.PackageInfo getPackageInfo(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        try {
            return packageManager.getPackageInfo(str, 4194304);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return packageManager.getPackageInfo(str, 1073741824);
        }
    }

    @android.annotation.Nullable
    private android.content.pm.VersionedPackage getVersionedPackage(java.lang.String str) {
        if (this.mContext.getPackageManager() == null || android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new android.content.pm.VersionedPackage(str, getPackageInfo(str).getLongVersionCode());
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private void loadFromFile() {
        this.mAllObservers.clear();
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = this.mPolicyFile.openRead();
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, TAG_PACKAGE_WATCHDOG);
                int depth = resolvePullParser.getDepth();
                while (com.android.internal.util.XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                    com.android.server.PackageWatchdog.ObserverInternal read = com.android.server.PackageWatchdog.ObserverInternal.read(resolvePullParser, this);
                    if (read != null) {
                        this.mAllObservers.put(read.name, read);
                    }
                }
            } catch (java.io.FileNotFoundException e) {
            } catch (java.io.IOException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e2) {
                android.util.Slog.wtf(TAG, "Unable to read monitored packages, deleting file", e2);
                this.mPolicyFile.delete();
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(fileInputStream);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPropertyChanged(android.provider.DeviceConfig.Properties properties) {
        try {
            updateConfigs();
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Failed to reload device config changes");
        }
    }

    private void setPropertyChangedListenerLocked() {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("rollback", this.mContext.getMainExecutor(), this.mOnPropertyChangedListener);
    }

    @com.android.internal.annotations.VisibleForTesting
    void removePropertyChangedListener() {
        android.provider.DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertyChangedListener);
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateConfigs() {
        synchronized (this.mLock) {
            try {
                this.mTriggerFailureCount = android.provider.DeviceConfig.getInt("rollback", PROPERTY_WATCHDOG_TRIGGER_FAILURE_COUNT, 5);
                if (this.mTriggerFailureCount <= 0) {
                    this.mTriggerFailureCount = 5;
                }
                this.mTriggerFailureDurationMs = android.provider.DeviceConfig.getInt("rollback", PROPERTY_WATCHDOG_TRIGGER_DURATION_MILLIS, DEFAULT_TRIGGER_FAILURE_DURATION_MS);
                if (this.mTriggerFailureDurationMs <= 0) {
                    this.mTriggerFailureDurationMs = DEFAULT_TRIGGER_FAILURE_DURATION_MS;
                }
                setExplicitHealthCheckEnabled(android.provider.DeviceConfig.getBoolean("rollback", PROPERTY_WATCHDOG_EXPLICIT_HEALTH_CHECK_ENABLED, true));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void registerConnectivityModuleHealthListener() {
        this.mConnectivityModuleConnector.registerHealthListener(new android.net.ConnectivityModuleConnector.ConnectivityModuleHealthListener() { // from class: com.android.server.PackageWatchdog$$ExternalSyntheticLambda6
            @Override // android.net.ConnectivityModuleConnector.ConnectivityModuleHealthListener
            public final void onNetworkStackFailure(java.lang.String str) {
                com.android.server.PackageWatchdog.this.lambda$registerConnectivityModuleHealthListener$8(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerConnectivityModuleHealthListener$8(java.lang.String str) {
        android.content.pm.VersionedPackage versionedPackage = getVersionedPackage(str);
        if (versionedPackage == null) {
            android.util.Slog.wtf(TAG, "NetworkStack failed but could not find its package");
        } else {
            onPackageFailure(java.util.Collections.singletonList(versionedPackage), 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean saveToFile() {
        android.util.Slog.i(TAG, "Saving observer state to file");
        synchronized (this.mLock) {
            try {
                java.io.FileOutputStream startWrite = this.mPolicyFile.startWrite();
                try {
                    try {
                        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((java.lang.String) null, true);
                        resolveSerializer.startTag((java.lang.String) null, TAG_PACKAGE_WATCHDOG);
                        resolveSerializer.attributeInt((java.lang.String) null, ATTR_VERSION, 1);
                        for (int i = 0; i < this.mAllObservers.size(); i++) {
                            this.mAllObservers.valueAt(i).writeLocked(resolveSerializer);
                        }
                        resolveSerializer.endTag((java.lang.String) null, TAG_PACKAGE_WATCHDOG);
                        resolveSerializer.endDocument();
                        this.mPolicyFile.finishWrite(startWrite);
                        libcore.io.IoUtils.closeQuietly(startWrite);
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(TAG, "Failed to save monitored packages, restoring backup", e);
                        this.mPolicyFile.failWrite(startWrite);
                        libcore.io.IoUtils.closeQuietly(startWrite);
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    libcore.io.IoUtils.closeQuietly(startWrite);
                    throw th;
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.w(TAG, "Cannot update monitored packages", e2);
                return false;
            }
        }
        return true;
    }

    private void saveToFileAsync() {
        if (!this.mLongTaskHandler.hasCallbacks(this.mSaveToFile)) {
            this.mLongTaskHandler.post(this.mSaveToFile);
        }
    }

    public static java.lang.String longArrayQueueToString(android.util.LongArrayQueue longArrayQueue) {
        if (longArrayQueue.size() > 0) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(longArrayQueue.get(0));
            for (int i = 1; i < longArrayQueue.size(); i++) {
                sb.append(",");
                sb.append(longArrayQueue.get(i));
            }
            return sb.toString();
        }
        return "";
    }

    public static android.util.LongArrayQueue parseLongArrayQueue(java.lang.String str) {
        android.util.LongArrayQueue longArrayQueue = new android.util.LongArrayQueue();
        if (!android.text.TextUtils.isEmpty(str)) {
            for (java.lang.String str2 : str.split(",")) {
                longArrayQueue.addLast(java.lang.Long.parseLong(str2));
            }
        }
        return longArrayQueue;
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Package Watchdog status");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                for (java.lang.String str : this.mAllObservers.keySet()) {
                    indentingPrintWriter.println("Observer name: " + str);
                    indentingPrintWriter.increaseIndent();
                    this.mAllObservers.get(str).dump(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ObserverInternal {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.ArrayMap<java.lang.String, com.android.server.PackageWatchdog.MonitoredPackage> mPackages = new android.util.ArrayMap<>();
        public final java.lang.String name;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        public com.android.server.PackageWatchdog.PackageHealthObserver registeredObserver;

        ObserverInternal(java.lang.String str, java.util.List<com.android.server.PackageWatchdog.MonitoredPackage> list) {
            this.name = str;
            updatePackagesLocked(list);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean writeLocked(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) {
            try {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.PackageWatchdog.TAG_OBSERVER);
                typedXmlSerializer.attribute((java.lang.String) null, "name", this.name);
                for (int i = 0; i < this.mPackages.size(); i++) {
                    this.mPackages.valueAt(i).writeLocked(typedXmlSerializer);
                }
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.PackageWatchdog.TAG_OBSERVER);
                return true;
            } catch (java.io.IOException e) {
                android.util.Slog.w(com.android.server.PackageWatchdog.TAG, "Cannot save observer", e);
                return false;
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updatePackagesLocked(java.util.List<com.android.server.PackageWatchdog.MonitoredPackage> list) {
            for (int i = 0; i < list.size(); i++) {
                com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage = list.get(i);
                com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage2 = getMonitoredPackage(monitoredPackage.getName());
                if (monitoredPackage2 != null) {
                    monitoredPackage2.updateHealthCheckDuration(monitoredPackage.mDurationMs);
                } else {
                    putMonitoredPackage(monitoredPackage);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public java.util.Set<com.android.server.PackageWatchdog.MonitoredPackage> prunePackagesLocked(long j) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            java.util.Iterator<com.android.server.PackageWatchdog.MonitoredPackage> it = this.mPackages.values().iterator();
            while (it.hasNext()) {
                com.android.server.PackageWatchdog.MonitoredPackage next = it.next();
                int healthCheckStateLocked = next.getHealthCheckStateLocked();
                int handleElapsedTimeLocked = next.handleElapsedTimeLocked(j);
                if (healthCheckStateLocked != 3 && handleElapsedTimeLocked == 3) {
                    android.util.Slog.i(com.android.server.PackageWatchdog.TAG, "Package " + next.getName() + " failed health check");
                    arraySet.add(next);
                }
                if (next.isExpiredLocked()) {
                    it.remove();
                }
            }
            return arraySet;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean onPackageFailureLocked(java.lang.String str) {
            if (getMonitoredPackage(str) == null && this.registeredObserver.isPersistent() && this.registeredObserver.mayObservePackage(str)) {
                putMonitoredPackage(com.android.server.PackageWatchdog.sPackageWatchdog.newMonitoredPackage(str, com.android.server.PackageWatchdog.DEFAULT_OBSERVING_DURATION_MS, false));
            }
            com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage = getMonitoredPackage(str);
            if (monitoredPackage != null) {
                return monitoredPackage.onFailureLocked();
            }
            return false;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public android.util.ArrayMap<java.lang.String, com.android.server.PackageWatchdog.MonitoredPackage> getMonitoredPackages() {
            return this.mPackages;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        public com.android.server.PackageWatchdog.MonitoredPackage getMonitoredPackage(java.lang.String str) {
            return this.mPackages.get(str);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void putMonitoredPackage(com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage) {
            this.mPackages.put(monitoredPackage.getName(), monitoredPackage);
        }

        public static com.android.server.PackageWatchdog.ObserverInternal read(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.PackageWatchdog packageWatchdog) {
            java.lang.String str;
            if (!com.android.server.PackageWatchdog.TAG_OBSERVER.equals(typedXmlPullParser.getName())) {
                str = null;
            } else {
                str = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
                if (android.text.TextUtils.isEmpty(str)) {
                    android.util.Slog.wtf(com.android.server.PackageWatchdog.TAG, "Unable to read observer name");
                    return null;
                }
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                try {
                    if ("package".equals(typedXmlPullParser.getName())) {
                        try {
                            com.android.server.PackageWatchdog.MonitoredPackage parseMonitoredPackage = packageWatchdog.parseMonitoredPackage(typedXmlPullParser);
                            if (parseMonitoredPackage != null) {
                                arrayList.add(parseMonitoredPackage);
                            }
                        } catch (java.lang.NumberFormatException e) {
                            android.util.Slog.wtf(com.android.server.PackageWatchdog.TAG, "Skipping package for observer " + str, e);
                        }
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                    android.util.Slog.wtf(com.android.server.PackageWatchdog.TAG, "Unable to read observer " + str, e2);
                    return null;
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            return new com.android.server.PackageWatchdog.ObserverInternal(str, arrayList);
        }

        public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Persistent: " + (this.registeredObserver != null && this.registeredObserver.isPersistent()));
            for (java.lang.String str : this.mPackages.keySet()) {
                com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage = getMonitoredPackage(str);
                indentingPrintWriter.println(str + ": ");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("# Failures: " + monitoredPackage.mFailureHistory.size());
                indentingPrintWriter.println("Monitoring duration remaining: " + monitoredPackage.mDurationMs + "ms");
                indentingPrintWriter.println("Explicit health check duration: " + monitoredPackage.mHealthCheckDurationMs + "ms");
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Health check state: ");
                sb.append(monitoredPackage.toString(monitoredPackage.mHealthCheckState));
                indentingPrintWriter.println(sb.toString());
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    com.android.server.PackageWatchdog.MonitoredPackage newMonitoredPackage(java.lang.String str, long j, boolean z) {
        return newMonitoredPackage(str, j, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, z, new android.util.LongArrayQueue());
    }

    com.android.server.PackageWatchdog.MonitoredPackage newMonitoredPackage(java.lang.String str, long j, long j2, boolean z, android.util.LongArrayQueue longArrayQueue) {
        return new com.android.server.PackageWatchdog.MonitoredPackage(str, j, j2, z, longArrayQueue);
    }

    com.android.server.PackageWatchdog.MonitoredPackage parseMonitoredPackage(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
        return newMonitoredPackage(typedXmlPullParser.getAttributeValue((java.lang.String) null, "name"), typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_DURATION), typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_EXPLICIT_HEALTH_CHECK_DURATION), typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_PASSED_HEALTH_CHECK), parseLongArrayQueue(typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_MITIGATION_CALLS)));
    }

    class MonitoredPackage {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private long mDurationMs;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mHasPassedHealthCheck;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private long mHealthCheckDurationMs;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.LongArrayQueue mMitigationCalls;
        private final java.lang.String mPackageName;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.LongArrayQueue mFailureHistory = new android.util.LongArrayQueue();
        private int mHealthCheckState = 1;

        MonitoredPackage(java.lang.String str, long j, long j2, boolean z, android.util.LongArrayQueue longArrayQueue) {
            this.mHealthCheckDurationMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            this.mPackageName = str;
            this.mDurationMs = j;
            this.mHealthCheckDurationMs = j2;
            this.mHasPassedHealthCheck = z;
            this.mMitigationCalls = longArrayQueue;
            updateHealthCheckStateLocked();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void writeLocked(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, "package");
            typedXmlSerializer.attribute((java.lang.String) null, "name", getName());
            typedXmlSerializer.attributeLong((java.lang.String) null, com.android.server.PackageWatchdog.ATTR_DURATION, this.mDurationMs);
            typedXmlSerializer.attributeLong((java.lang.String) null, com.android.server.PackageWatchdog.ATTR_EXPLICIT_HEALTH_CHECK_DURATION, this.mHealthCheckDurationMs);
            typedXmlSerializer.attributeBoolean((java.lang.String) null, com.android.server.PackageWatchdog.ATTR_PASSED_HEALTH_CHECK, this.mHasPassedHealthCheck);
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.PackageWatchdog.ATTR_MITIGATION_CALLS, com.android.server.PackageWatchdog.longArrayQueueToString(normalizeMitigationCalls()));
            typedXmlSerializer.endTag((java.lang.String) null, "package");
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean onFailureLocked() {
            long uptimeMillis = com.android.server.PackageWatchdog.this.mSystemClock.uptimeMillis();
            this.mFailureHistory.addLast(uptimeMillis);
            while (uptimeMillis - this.mFailureHistory.peekFirst() > com.android.server.PackageWatchdog.this.mTriggerFailureDurationMs) {
                this.mFailureHistory.removeFirst();
            }
            boolean z = this.mFailureHistory.size() >= com.android.server.PackageWatchdog.this.mTriggerFailureCount;
            if (z) {
                this.mFailureHistory.clear();
            }
            return z;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void noteMitigationCallLocked() {
            this.mMitigationCalls.addLast(com.android.server.PackageWatchdog.this.mSystemClock.uptimeMillis());
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public int getMitigationCountLocked() {
            try {
                long uptimeMillis = com.android.server.PackageWatchdog.this.mSystemClock.uptimeMillis();
                while (uptimeMillis - this.mMitigationCalls.peekFirst() > com.android.server.PackageWatchdog.DEFAULT_DEESCALATION_WINDOW_MS) {
                    this.mMitigationCalls.removeFirst();
                }
            } catch (java.util.NoSuchElementException e) {
            }
            return this.mMitigationCalls.size();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public android.util.LongArrayQueue normalizeMitigationCalls() {
            android.util.LongArrayQueue longArrayQueue = new android.util.LongArrayQueue();
            long uptimeMillis = com.android.server.PackageWatchdog.this.mSystemClock.uptimeMillis();
            for (int i = 0; i < this.mMitigationCalls.size(); i++) {
                longArrayQueue.addLast(this.mMitigationCalls.get(i) - uptimeMillis);
            }
            return longArrayQueue;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public int setHealthCheckActiveLocked(long j) {
            if (j <= 0) {
                android.util.Slog.wtf(com.android.server.PackageWatchdog.TAG, "Cannot set non-positive health check duration " + j + "ms for package " + getName() + ". Using total duration " + this.mDurationMs + "ms instead");
                j = this.mDurationMs;
            }
            if (this.mHealthCheckState == 1) {
                this.mHealthCheckDurationMs = j;
            }
            return updateHealthCheckStateLocked();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public int handleElapsedTimeLocked(long j) {
            if (j <= 0) {
                android.util.Slog.w(com.android.server.PackageWatchdog.TAG, "Cannot handle non-positive elapsed time for package " + getName());
                return this.mHealthCheckState;
            }
            this.mDurationMs -= j;
            if (this.mHealthCheckState == 0) {
                this.mHealthCheckDurationMs -= j;
            }
            return updateHealthCheckStateLocked();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updateHealthCheckDuration(long j) {
            this.mDurationMs = j;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public int tryPassHealthCheckLocked() {
            if (this.mHealthCheckState != 3) {
                this.mHasPassedHealthCheck = true;
            }
            return updateHealthCheckStateLocked();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getName() {
            return this.mPackageName;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public int getHealthCheckStateLocked() {
            return this.mHealthCheckState;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public long getShortestScheduleDurationMsLocked() {
            return java.lang.Math.min(toPositive(this.mDurationMs), isPendingHealthChecksLocked() ? toPositive(this.mHealthCheckDurationMs) : com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean isExpiredLocked() {
            return this.mDurationMs <= 0;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean isPendingHealthChecksLocked() {
            return this.mHealthCheckState == 0 || this.mHealthCheckState == 1;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private int updateHealthCheckStateLocked() {
            int i = this.mHealthCheckState;
            if (this.mHasPassedHealthCheck) {
                this.mHealthCheckState = 2;
            } else if (this.mHealthCheckDurationMs <= 0 || this.mDurationMs <= 0) {
                this.mHealthCheckState = 3;
            } else if (this.mHealthCheckDurationMs == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                this.mHealthCheckState = 1;
            } else {
                this.mHealthCheckState = 0;
            }
            if (i != this.mHealthCheckState) {
                android.util.Slog.i(com.android.server.PackageWatchdog.TAG, "Updated health check state for package " + getName() + ": " + toString(i) + " -> " + toString(this.mHealthCheckState));
            }
            return this.mHealthCheckState;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String toString(int i) {
            switch (i) {
                case 0:
                    return "ACTIVE";
                case 1:
                    return "INACTIVE";
                case 2:
                    return "PASSED";
                case 3:
                    return "FAILED";
                default:
                    return "UNKNOWN";
            }
        }

        private long toPositive(long j) {
            return j > 0 ? j : com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }

        @com.android.internal.annotations.VisibleForTesting
        boolean isEqualTo(com.android.server.PackageWatchdog.MonitoredPackage monitoredPackage) {
            return getName().equals(monitoredPackage.getName()) && this.mDurationMs == monitoredPackage.mDurationMs && this.mHasPassedHealthCheck == monitoredPackage.mHasPassedHealthCheck && this.mHealthCheckDurationMs == monitoredPackage.mHealthCheckDurationMs && this.mMitigationCalls.toString().equals(monitoredPackage.mMitigationCalls.toString());
        }
    }

    class BootThreshold {
        private final int mBootTriggerCount;
        private final long mTriggerWindow;

        BootThreshold(int i, long j) {
            this.mBootTriggerCount = i;
            this.mTriggerWindow = j;
        }

        public void reset() {
            setStart(0L);
            setCount(0);
        }

        protected int getCount() {
            return ((java.lang.Integer) android.sysprop.CrashRecoveryProperties.rescueBootCount().orElse(0)).intValue();
        }

        protected void setCount(int i) {
            android.sysprop.CrashRecoveryProperties.rescueBootCount(java.lang.Integer.valueOf(i));
        }

        public long getStart() {
            return ((java.lang.Long) android.sysprop.CrashRecoveryProperties.rescueBootStart().orElse(0L)).longValue();
        }

        public int getMitigationCount() {
            return ((java.lang.Integer) android.sysprop.CrashRecoveryProperties.bootMitigationCount().orElse(0)).intValue();
        }

        public void setStart(long j) {
            android.sysprop.CrashRecoveryProperties.rescueBootStart(java.lang.Long.valueOf(getStartTime(j)));
        }

        public void setMitigationStart(long j) {
            android.sysprop.CrashRecoveryProperties.bootMitigationStart(java.lang.Long.valueOf(getStartTime(j)));
        }

        public long getMitigationStart() {
            return ((java.lang.Long) android.sysprop.CrashRecoveryProperties.bootMitigationStart().orElse(0L)).longValue();
        }

        public void setMitigationCount(int i) {
            android.sysprop.CrashRecoveryProperties.bootMitigationCount(java.lang.Integer.valueOf(i));
        }

        private static long constrain(long j, long j2, long j3) {
            return j < j2 ? j2 : j > j3 ? j3 : j;
        }

        public long getStartTime(long j) {
            return constrain(j, 0L, com.android.server.PackageWatchdog.this.mSystemClock.uptimeMillis());
        }

        public void saveMitigationCountToMetadata() {
            try {
                java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(com.android.server.PackageWatchdog.METADATA_FILE));
                try {
                    bufferedWriter.write(java.lang.String.valueOf(getMitigationCount()));
                    bufferedWriter.close();
                } finally {
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.PackageWatchdog.TAG, "Could not save metadata to file: " + e);
            }
        }

        public void readMitigationCountFromMetadataIfNecessary() {
            java.io.File file = new java.io.File(com.android.server.PackageWatchdog.METADATA_FILE);
            if (file.exists()) {
                try {
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(com.android.server.PackageWatchdog.METADATA_FILE));
                    try {
                        setMitigationCount(java.lang.Integer.parseInt(bufferedReader.readLine()));
                        file.delete();
                        bufferedReader.close();
                    } finally {
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.i(com.android.server.PackageWatchdog.TAG, "Could not read metadata file: " + e);
                }
            }
        }

        public boolean incrementAndTest() {
            readMitigationCountFromMetadataIfNecessary();
            long uptimeMillis = com.android.server.PackageWatchdog.this.mSystemClock.uptimeMillis();
            if (uptimeMillis - getStart() < 0) {
                android.util.Slog.e(com.android.server.PackageWatchdog.TAG, "Window was less than zero. Resetting start to current time.");
                setStart(uptimeMillis);
                setMitigationStart(uptimeMillis);
            }
            if (uptimeMillis - getMitigationStart() > com.android.server.PackageWatchdog.DEFAULT_DEESCALATION_WINDOW_MS) {
                setMitigationCount(0);
                setMitigationStart(uptimeMillis);
            }
            long start = uptimeMillis - getStart();
            if (start >= this.mTriggerWindow) {
                setCount(1);
                setStart(uptimeMillis);
                return false;
            }
            int count = getCount() + 1;
            setCount(count);
            com.android.server.EventLogTags.writeRescueNote(0, count, start);
            return count >= this.mBootTriggerCount;
        }
    }
}
