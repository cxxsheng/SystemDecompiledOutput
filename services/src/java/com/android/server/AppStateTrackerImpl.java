package com.android.server;

/* loaded from: classes.dex */
public class AppStateTrackerImpl implements com.android.server.AppStateTracker {
    private static final java.lang.String APP_RESTRICTION_COUNTER_METRIC_ID = "battery.value_app_background_restricted";
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final int TARGET_OP = 70;
    android.app.ActivityManagerInternal mActivityManagerInternal;
    android.app.AppOpsManager mAppOpsManager;
    com.android.internal.app.IAppOpsService mAppOpsService;
    com.android.server.usage.AppStandbyInternal mAppStandbyInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mBatterySaverEnabled;
    private final android.content.Context mContext;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.AppStateTrackerImpl.FeatureFlagsObserver mFlagsObserver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mForceAllAppStandbyForSmallBattery;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mForceAllAppsStandby;
    private final com.android.server.AppStateTrackerImpl.MyHandler mHandler;
    android.app.IActivityManager mIActivityManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mIsPluggedIn;
    android.os.PowerManagerInternal mPowerManagerInternal;
    com.android.server.AppStateTrackerImpl.StandbyTracker mStandbyTracker;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mStarted;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.ArraySet<android.util.Pair<java.lang.Integer, java.lang.String>> mRunAnyRestrictedPackages = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseBooleanArray mActiveUids = new android.util.SparseBooleanArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] mPowerExemptSystemAppIds = new int[0];

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] mPowerExemptAllAppIds = new int[0];

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] mPowerExemptUserAppIds = new int[0];

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] mTempExemptAppIds = this.mPowerExemptAllAppIds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseSetArray<java.lang.String> mExemptedBucketPackages = new android.util.SparseSetArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.ArraySet<com.android.server.AppStateTrackerImpl.Listener> mListeners = new android.util.ArraySet<>();
    volatile java.util.Set<android.util.Pair<java.lang.Integer, java.lang.String>> mBackgroundRestrictedUidPackages = java.util.Collections.emptySet();
    private final com.android.internal.util.jobs.StatLogger mStatLogger = new com.android.internal.util.jobs.StatLogger(new java.lang.String[]{"UID_FG_STATE_CHANGED", "UID_ACTIVE_STATE_CHANGED", "RUN_ANY_CHANGED", "ALL_UNEXEMPTED", "ALL_EXEMPTION_LIST_CHANGED", "TEMP_EXEMPTION_LIST_CHANGED", "EXEMPTED_BUCKET_CHANGED", "FORCE_ALL_CHANGED", "IS_UID_ACTIVE_CACHED", "IS_UID_ACTIVE_RAW"});
    private final android.app.ActivityManagerInternal.AppBackgroundRestrictionListener mAppBackgroundRestrictionListener = new android.app.ActivityManagerInternal.AppBackgroundRestrictionListener() { // from class: com.android.server.AppStateTrackerImpl.2
        public void onAutoRestrictedBucketFeatureFlagChanged(boolean z) {
            com.android.server.AppStateTrackerImpl.this.mHandler.notifyAutoRestrictedBucketFeatureFlagChanged(z);
        }
    };
    private final android.content.BroadcastReceiver mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.AppStateTrackerImpl.3
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            java.lang.String action = intent.getAction();
            boolean z = true;
            switch (action.hashCode()) {
                case -2061058799:
                    if (action.equals("android.intent.action.USER_REMOVED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1538406691:
                    if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (intExtra > 0) {
                        com.android.server.AppStateTrackerImpl.this.mHandler.doUserRemoved(intExtra);
                        return;
                    }
                    return;
                case 1:
                    synchronized (com.android.server.AppStateTrackerImpl.this.mLock) {
                        com.android.server.AppStateTrackerImpl appStateTrackerImpl = com.android.server.AppStateTrackerImpl.this;
                        if (intent.getIntExtra("plugged", 0) == 0) {
                            z = false;
                        }
                        appStateTrackerImpl.mIsPluggedIn = z;
                    }
                    com.android.server.AppStateTrackerImpl.this.updateForceAllAppStandbyState();
                    return;
                case 2:
                    if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                        java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                        int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                        synchronized (com.android.server.AppStateTrackerImpl.this.mLock) {
                            com.android.server.AppStateTrackerImpl.this.mExemptedBucketPackages.remove(intExtra, schemeSpecificPart);
                            com.android.server.AppStateTrackerImpl.this.mRunAnyRestrictedPackages.remove(android.util.Pair.create(java.lang.Integer.valueOf(intExtra2), schemeSpecificPart));
                            com.android.server.AppStateTrackerImpl.this.updateBackgroundRestrictedUidPackagesLocked();
                            com.android.server.AppStateTrackerImpl.this.mActiveUids.delete(intExtra2);
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };

    interface Stats {
        public static final int ALL_EXEMPTION_LIST_CHANGED = 4;
        public static final int ALL_UNEXEMPTED = 3;
        public static final int EXEMPTED_BUCKET_CHANGED = 6;
        public static final int FORCE_ALL_CHANGED = 7;
        public static final int IS_UID_ACTIVE_CACHED = 8;
        public static final int IS_UID_ACTIVE_RAW = 9;
        public static final int RUN_ANY_CHANGED = 2;
        public static final int TEMP_EXEMPTION_LIST_CHANGED = 5;
        public static final int UID_ACTIVE_STATE_CHANGED = 1;
        public static final int UID_FG_STATE_CHANGED = 0;
    }

    public void addBackgroundRestrictedAppListener(@android.annotation.NonNull final com.android.server.AppStateTracker.BackgroundRestrictedAppListener backgroundRestrictedAppListener) {
        addListener(new com.android.server.AppStateTrackerImpl.Listener() { // from class: com.android.server.AppStateTrackerImpl.1
            @Override // com.android.server.AppStateTrackerImpl.Listener
            public void updateBackgroundRestrictedForUidPackage(int i, java.lang.String str, boolean z) {
                backgroundRestrictedAppListener.updateBackgroundRestrictedForUidPackage(i, str, z);
            }
        });
    }

    public boolean isAppBackgroundRestricted(int i, @android.annotation.NonNull java.lang.String str) {
        return this.mBackgroundRestrictedUidPackages.contains(android.util.Pair.create(java.lang.Integer.valueOf(i), str));
    }

    @com.android.internal.annotations.VisibleForTesting
    class FeatureFlagsObserver extends android.database.ContentObserver {
        FeatureFlagsObserver() {
            super(null);
        }

        void register() {
            com.android.server.AppStateTrackerImpl.this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("forced_app_standby_for_small_battery_enabled"), false, this);
        }

        boolean isForcedAppStandbyForSmallBatteryEnabled() {
            return com.android.server.AppStateTrackerImpl.this.injectGetGlobalSettingInt("forced_app_standby_for_small_battery_enabled", 0) == 1;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (android.provider.Settings.Global.getUriFor("forced_app_standby_for_small_battery_enabled").equals(uri)) {
                boolean isForcedAppStandbyForSmallBatteryEnabled = isForcedAppStandbyForSmallBatteryEnabled();
                synchronized (com.android.server.AppStateTrackerImpl.this.mLock) {
                    try {
                        if (com.android.server.AppStateTrackerImpl.this.mForceAllAppStandbyForSmallBattery == isForcedAppStandbyForSmallBatteryEnabled) {
                            return;
                        }
                        com.android.server.AppStateTrackerImpl.this.mForceAllAppStandbyForSmallBattery = isForcedAppStandbyForSmallBatteryEnabled;
                        com.android.server.AppStateTrackerImpl.this.updateForceAllAppStandbyState();
                        return;
                    } finally {
                    }
                }
            }
            android.util.Slog.w("AppStateTracker", "Unexpected feature flag uri encountered: " + uri);
        }
    }

    public static abstract class Listener {
        /* JADX INFO: Access modifiers changed from: private */
        public void onRunAnyAppOpsChanged(com.android.server.AppStateTrackerImpl appStateTrackerImpl, int i, @android.annotation.NonNull java.lang.String str) {
            updateJobsForUidPackage(i, str, appStateTrackerImpl.isUidActive(i));
            if (!appStateTrackerImpl.areAlarmsRestricted(i, str)) {
                unblockAlarmsForUidPackage(i, str);
            }
            if (!appStateTrackerImpl.isRunAnyInBackgroundAppOpsAllowed(i, str)) {
                android.util.Slog.v("AppStateTracker", "Package " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " toggled into fg service restriction");
                updateBackgroundRestrictedForUidPackage(i, str, true);
                return;
            }
            android.util.Slog.v("AppStateTracker", "Package " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " toggled out of fg service restriction");
            updateBackgroundRestrictedForUidPackage(i, str, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onUidActiveStateChanged(com.android.server.AppStateTrackerImpl appStateTrackerImpl, int i) {
            boolean isUidActive = appStateTrackerImpl.isUidActive(i);
            updateJobsForUid(i, isUidActive);
            updateAlarmsForUid(i);
            if (isUidActive) {
                unblockAlarmsForUid(i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onPowerSaveUnexempted(com.android.server.AppStateTrackerImpl appStateTrackerImpl) {
            updateAllJobs();
            updateAllAlarms();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onPowerSaveExemptionListChanged(com.android.server.AppStateTrackerImpl appStateTrackerImpl) {
            updateAllJobs();
            updateAllAlarms();
            unblockAllUnrestrictedAlarms();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onTempPowerSaveExemptionListChanged(com.android.server.AppStateTrackerImpl appStateTrackerImpl) {
            updateAllJobs();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onExemptedBucketChanged(com.android.server.AppStateTrackerImpl appStateTrackerImpl) {
            updateAllJobs();
            updateAllAlarms();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onForceAllAppsStandbyChanged(com.android.server.AppStateTrackerImpl appStateTrackerImpl) {
            updateAllJobs();
            updateAllAlarms();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onAutoRestrictedBucketFeatureFlagChanged(com.android.server.AppStateTrackerImpl appStateTrackerImpl, boolean z) {
            updateAllJobs();
            if (z) {
                unblockAllUnrestrictedAlarms();
            }
        }

        public void updateAllJobs() {
        }

        public void updateJobsForUid(int i, boolean z) {
        }

        public void updateJobsForUidPackage(int i, java.lang.String str, boolean z) {
        }

        public void updateBackgroundRestrictedForUidPackage(int i, java.lang.String str, boolean z) {
        }

        public void updateAllAlarms() {
        }

        public void updateAlarmsForUid(int i) {
        }

        public void unblockAllUnrestrictedAlarms() {
        }

        public void unblockAlarmsForUid(int i) {
        }

        public void unblockAlarmsForUidPackage(int i, java.lang.String str) {
        }

        public void removeAlarmsForUid(int i) {
        }

        public void handleUidCachedChanged(int i, boolean z) {
        }
    }

    public AppStateTrackerImpl(android.content.Context context, android.os.Looper looper) {
        this.mContext = context;
        this.mHandler = new com.android.server.AppStateTrackerImpl.MyHandler(looper);
    }

    public void onSystemServicesReady() {
        synchronized (this.mLock) {
            try {
                if (this.mStarted) {
                    return;
                }
                this.mStarted = true;
                android.app.IActivityManager injectIActivityManager = injectIActivityManager();
                java.util.Objects.requireNonNull(injectIActivityManager);
                this.mIActivityManager = injectIActivityManager;
                android.app.ActivityManagerInternal injectActivityManagerInternal = injectActivityManagerInternal();
                java.util.Objects.requireNonNull(injectActivityManagerInternal);
                android.app.ActivityManagerInternal activityManagerInternal = injectActivityManagerInternal;
                this.mActivityManagerInternal = injectActivityManagerInternal;
                android.app.AppOpsManager injectAppOpsManager = injectAppOpsManager();
                java.util.Objects.requireNonNull(injectAppOpsManager);
                android.app.AppOpsManager appOpsManager = injectAppOpsManager;
                this.mAppOpsManager = injectAppOpsManager;
                com.android.internal.app.IAppOpsService injectIAppOpsService = injectIAppOpsService();
                java.util.Objects.requireNonNull(injectIAppOpsService);
                this.mAppOpsService = injectIAppOpsService;
                android.os.PowerManagerInternal injectPowerManagerInternal = injectPowerManagerInternal();
                java.util.Objects.requireNonNull(injectPowerManagerInternal);
                android.os.PowerManagerInternal powerManagerInternal = injectPowerManagerInternal;
                this.mPowerManagerInternal = injectPowerManagerInternal;
                com.android.server.usage.AppStandbyInternal injectAppStandbyInternal = injectAppStandbyInternal();
                java.util.Objects.requireNonNull(injectAppStandbyInternal);
                this.mAppStandbyInternal = injectAppStandbyInternal;
                this.mFlagsObserver = new com.android.server.AppStateTrackerImpl.FeatureFlagsObserver();
                this.mFlagsObserver.register();
                this.mForceAllAppStandbyForSmallBattery = this.mFlagsObserver.isForcedAppStandbyForSmallBatteryEnabled();
                this.mStandbyTracker = new com.android.server.AppStateTrackerImpl.StandbyTracker();
                this.mAppStandbyInternal.addListener(this.mStandbyTracker);
                this.mActivityManagerInternal.addAppBackgroundRestrictionListener(this.mAppBackgroundRestrictionListener);
                try {
                    this.mIActivityManager.registerUidObserver(new com.android.server.AppStateTrackerImpl.UidObserver(), 30, -1, (java.lang.String) null);
                    this.mAppOpsService.startWatchingMode(70, (java.lang.String) null, new com.android.server.AppStateTrackerImpl.AppOpsWatcher());
                } catch (android.os.RemoteException e) {
                }
                android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                intentFilter.addAction("android.intent.action.USER_REMOVED");
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                this.mContext.registerReceiver(this.mReceiver, intentFilter);
                android.content.IntentFilter intentFilter2 = new android.content.IntentFilter("android.intent.action.PACKAGE_REMOVED");
                intentFilter2.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
                this.mContext.registerReceiver(this.mReceiver, intentFilter2);
                refreshForcedAppStandbyUidPackagesLocked();
                this.mPowerManagerInternal.registerLowPowerModeObserver(11, new java.util.function.Consumer() { // from class: com.android.server.AppStateTrackerImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.AppStateTrackerImpl.this.lambda$onSystemServicesReady$0((android.os.PowerSaveState) obj);
                    }
                });
                this.mBatterySaverEnabled = this.mPowerManagerInternal.getLowPowerState(11).batterySaverEnabled;
                updateForceAllAppStandbyState();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemServicesReady$0(android.os.PowerSaveState powerSaveState) {
        synchronized (this.mLock) {
            this.mBatterySaverEnabled = powerSaveState.batterySaverEnabled;
            updateForceAllAppStandbyState();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.app.AppOpsManager injectAppOpsManager() {
        return (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.internal.app.IAppOpsService injectIAppOpsService() {
        return com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
    }

    @com.android.internal.annotations.VisibleForTesting
    android.app.IActivityManager injectIActivityManager() {
        return android.app.ActivityManager.getService();
    }

    @com.android.internal.annotations.VisibleForTesting
    android.app.ActivityManagerInternal injectActivityManagerInternal() {
        return (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.PowerManagerInternal injectPowerManagerInternal() {
        return (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.usage.AppStandbyInternal injectAppStandbyInternal() {
        return (com.android.server.usage.AppStandbyInternal) com.android.server.LocalServices.getService(com.android.server.usage.AppStandbyInternal.class);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isSmallBatteryDevice() {
        return android.app.ActivityManager.isSmallBatteryDevice();
    }

    @com.android.internal.annotations.VisibleForTesting
    int injectGetGlobalSettingInt(java.lang.String str, int i) {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), str, i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void refreshForcedAppStandbyUidPackagesLocked() {
        this.mRunAnyRestrictedPackages.clear();
        java.util.List packagesForOps = this.mAppOpsManager.getPackagesForOps(new int[]{70});
        if (packagesForOps == null) {
            return;
        }
        int size = packagesForOps.size();
        for (int i = 0; i < size; i++) {
            android.app.AppOpsManager.PackageOps packageOps = (android.app.AppOpsManager.PackageOps) packagesForOps.get(i);
            java.util.List ops = ((android.app.AppOpsManager.PackageOps) packagesForOps.get(i)).getOps();
            for (int i2 = 0; i2 < ops.size(); i2++) {
                android.app.AppOpsManager.OpEntry opEntry = (android.app.AppOpsManager.OpEntry) ops.get(i2);
                if (opEntry.getOp() == 70 && opEntry.getMode() != 0) {
                    this.mRunAnyRestrictedPackages.add(android.util.Pair.create(java.lang.Integer.valueOf(packageOps.getUid()), packageOps.getPackageName()));
                }
            }
        }
        updateBackgroundRestrictedUidPackagesLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateBackgroundRestrictedUidPackagesLocked() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int size = this.mRunAnyRestrictedPackages.size();
        for (int i = 0; i < size; i++) {
            arraySet.add(this.mRunAnyRestrictedPackages.valueAt(i));
        }
        this.mBackgroundRestrictedUidPackages = java.util.Collections.unmodifiableSet(arraySet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForceAllAppStandbyState() {
        synchronized (this.mLock) {
            try {
                if (this.mForceAllAppStandbyForSmallBattery && isSmallBatteryDevice()) {
                    toggleForceAllAppsStandbyLocked(!this.mIsPluggedIn);
                } else {
                    toggleForceAllAppsStandbyLocked(this.mBatterySaverEnabled);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void toggleForceAllAppsStandbyLocked(boolean z) {
        if (z == this.mForceAllAppsStandby) {
            return;
        }
        this.mForceAllAppsStandby = z;
        this.mHandler.notifyForceAllAppsStandbyChanged();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int findForcedAppStandbyUidPackageIndexLocked(int i, @android.annotation.NonNull java.lang.String str) {
        int size = this.mRunAnyRestrictedPackages.size();
        if (size > 8) {
            return this.mRunAnyRestrictedPackages.indexOf(android.util.Pair.create(java.lang.Integer.valueOf(i), str));
        }
        for (int i2 = 0; i2 < size; i2++) {
            android.util.Pair<java.lang.Integer, java.lang.String> valueAt = this.mRunAnyRestrictedPackages.valueAt(i2);
            if (((java.lang.Integer) valueAt.first).intValue() == i && str.equals(valueAt.second)) {
                return i2;
            }
        }
        return -1;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isRunAnyRestrictedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        return findForcedAppStandbyUidPackageIndexLocked(i, str) >= 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean updateForcedAppStandbyUidPackageLocked(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        int findForcedAppStandbyUidPackageIndexLocked = findForcedAppStandbyUidPackageIndexLocked(i, str);
        if ((findForcedAppStandbyUidPackageIndexLocked >= 0) == z) {
            return false;
        }
        if (z) {
            this.mRunAnyRestrictedPackages.add(android.util.Pair.create(java.lang.Integer.valueOf(i), str));
        } else {
            this.mRunAnyRestrictedPackages.removeAt(findForcedAppStandbyUidPackageIndexLocked);
        }
        updateBackgroundRestrictedUidPackagesLocked();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean addUidToArray(android.util.SparseBooleanArray sparseBooleanArray, int i) {
        if (android.os.UserHandle.isCore(i) || sparseBooleanArray.get(i)) {
            return false;
        }
        sparseBooleanArray.put(i, true);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean removeUidFromArray(android.util.SparseBooleanArray sparseBooleanArray, int i, boolean z) {
        if (android.os.UserHandle.isCore(i) || !sparseBooleanArray.get(i)) {
            return false;
        }
        if (z) {
            sparseBooleanArray.delete(i);
            return true;
        }
        sparseBooleanArray.put(i, false);
        return true;
    }

    private final class UidObserver extends android.app.UidObserver {
        private UidObserver() {
        }

        public void onUidActive(int i) {
            com.android.server.AppStateTrackerImpl.this.mHandler.onUidActive(i);
        }

        public void onUidGone(int i, boolean z) {
            com.android.server.AppStateTrackerImpl.this.mHandler.onUidGone(i, z);
        }

        public void onUidIdle(int i, boolean z) {
            com.android.server.AppStateTrackerImpl.this.mHandler.onUidIdle(i, z);
        }

        public void onUidCachedChanged(int i, boolean z) {
            com.android.server.AppStateTrackerImpl.this.mHandler.onUidCachedChanged(i, z);
        }
    }

    private final class AppOpsWatcher extends com.android.internal.app.IAppOpsCallback.Stub {
        private AppOpsWatcher() {
        }

        public void opChanged(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            boolean z = false;
            try {
                if (com.android.server.AppStateTrackerImpl.this.mAppOpsService.checkOperation(70, i2, str) != 0) {
                    z = true;
                }
            } catch (android.os.RemoteException e) {
            }
            if (z) {
                com.android.modules.expresslog.Counter.logIncrementWithUid(com.android.server.AppStateTrackerImpl.APP_RESTRICTION_COUNTER_METRIC_ID, i2);
            }
            synchronized (com.android.server.AppStateTrackerImpl.this.mLock) {
                try {
                    if (com.android.server.AppStateTrackerImpl.this.updateForcedAppStandbyUidPackageLocked(i2, str, z)) {
                        com.android.server.AppStateTrackerImpl.this.mHandler.notifyRunAnyAppOpsChanged(i2, str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class StandbyTracker extends com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener {
        StandbyTracker() {
        }

        public void onAppIdleStateChanged(java.lang.String str, int i, boolean z, int i2, int i3) {
            boolean remove;
            synchronized (com.android.server.AppStateTrackerImpl.this.mLock) {
                try {
                    if (i2 == 5) {
                        remove = com.android.server.AppStateTrackerImpl.this.mExemptedBucketPackages.add(i, str);
                    } else {
                        remove = com.android.server.AppStateTrackerImpl.this.mExemptedBucketPackages.remove(i, str);
                    }
                    if (remove) {
                        com.android.server.AppStateTrackerImpl.this.mHandler.notifyExemptedBucketChanged();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.AppStateTrackerImpl.Listener[] cloneListeners() {
        com.android.server.AppStateTrackerImpl.Listener[] listenerArr;
        synchronized (this.mLock) {
            listenerArr = (com.android.server.AppStateTrackerImpl.Listener[]) this.mListeners.toArray(new com.android.server.AppStateTrackerImpl.Listener[this.mListeners.size()]);
        }
        return listenerArr;
    }

    private class MyHandler extends android.os.Handler {
        private static final int MSG_ALL_EXEMPTION_LIST_CHANGED = 5;
        private static final int MSG_ALL_UNEXEMPTED = 4;
        private static final int MSG_AUTO_RESTRICTED_BUCKET_FEATURE_FLAG_CHANGED = 11;
        private static final int MSG_EXEMPTED_BUCKET_CHANGED = 10;
        private static final int MSG_FORCE_ALL_CHANGED = 7;
        private static final int MSG_ON_UID_ACTIVE = 12;
        private static final int MSG_ON_UID_CACHED = 15;
        private static final int MSG_ON_UID_GONE = 13;
        private static final int MSG_ON_UID_IDLE = 14;
        private static final int MSG_RUN_ANY_CHANGED = 3;
        private static final int MSG_TEMP_EXEMPTION_LIST_CHANGED = 6;
        private static final int MSG_UID_ACTIVE_STATE_CHANGED = 0;
        private static final int MSG_USER_REMOVED = 8;

        MyHandler(android.os.Looper looper) {
            super(looper);
        }

        public void notifyUidActiveStateChanged(int i) {
            obtainMessage(0, i, 0).sendToTarget();
        }

        public void notifyRunAnyAppOpsChanged(int i, @android.annotation.NonNull java.lang.String str) {
            obtainMessage(3, i, 0, str).sendToTarget();
        }

        public void notifyAllUnexempted() {
            removeMessages(4);
            obtainMessage(4).sendToTarget();
        }

        public void notifyAllExemptionListChanged() {
            removeMessages(5);
            obtainMessage(5).sendToTarget();
        }

        public void notifyTempExemptionListChanged() {
            removeMessages(6);
            obtainMessage(6).sendToTarget();
        }

        public void notifyForceAllAppsStandbyChanged() {
            removeMessages(7);
            obtainMessage(7).sendToTarget();
        }

        public void notifyExemptedBucketChanged() {
            removeMessages(10);
            obtainMessage(10).sendToTarget();
        }

        public void notifyAutoRestrictedBucketFeatureFlagChanged(boolean z) {
            removeMessages(11);
            obtainMessage(11, z ? 1 : 0, 0).sendToTarget();
        }

        public void doUserRemoved(int i) {
            obtainMessage(8, i, 0).sendToTarget();
        }

        public void onUidActive(int i) {
            obtainMessage(12, i, 0).sendToTarget();
        }

        public void onUidGone(int i, boolean z) {
            obtainMessage(13, i, z ? 1 : 0).sendToTarget();
        }

        public void onUidIdle(int i, boolean z) {
            obtainMessage(14, i, z ? 1 : 0).sendToTarget();
        }

        public void onUidCachedChanged(int i, boolean z) {
            obtainMessage(15, i, z ? 1 : 0).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 8:
                    com.android.server.AppStateTrackerImpl.this.handleUserRemoved(message.arg1);
                    return;
                default:
                    synchronized (com.android.server.AppStateTrackerImpl.this.mLock) {
                        try {
                            if (com.android.server.AppStateTrackerImpl.this.mStarted) {
                                com.android.server.AppStateTrackerImpl appStateTrackerImpl = com.android.server.AppStateTrackerImpl.this;
                                long time = com.android.server.AppStateTrackerImpl.this.mStatLogger.getTime();
                                switch (message.what) {
                                    case 0:
                                        for (com.android.server.AppStateTrackerImpl.Listener listener : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                                            listener.onUidActiveStateChanged(appStateTrackerImpl, message.arg1);
                                        }
                                        com.android.server.AppStateTrackerImpl.this.mStatLogger.logDurationStat(1, time);
                                        return;
                                    case 1:
                                    case 2:
                                    case 9:
                                    default:
                                        return;
                                    case 3:
                                        for (com.android.server.AppStateTrackerImpl.Listener listener2 : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                                            listener2.onRunAnyAppOpsChanged(appStateTrackerImpl, message.arg1, (java.lang.String) message.obj);
                                        }
                                        com.android.server.AppStateTrackerImpl.this.mStatLogger.logDurationStat(2, time);
                                        return;
                                    case 4:
                                        for (com.android.server.AppStateTrackerImpl.Listener listener3 : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                                            listener3.onPowerSaveUnexempted(appStateTrackerImpl);
                                        }
                                        com.android.server.AppStateTrackerImpl.this.mStatLogger.logDurationStat(3, time);
                                        return;
                                    case 5:
                                        for (com.android.server.AppStateTrackerImpl.Listener listener4 : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                                            listener4.onPowerSaveExemptionListChanged(appStateTrackerImpl);
                                        }
                                        com.android.server.AppStateTrackerImpl.this.mStatLogger.logDurationStat(4, time);
                                        return;
                                    case 6:
                                        for (com.android.server.AppStateTrackerImpl.Listener listener5 : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                                            listener5.onTempPowerSaveExemptionListChanged(appStateTrackerImpl);
                                        }
                                        com.android.server.AppStateTrackerImpl.this.mStatLogger.logDurationStat(5, time);
                                        return;
                                    case 7:
                                        for (com.android.server.AppStateTrackerImpl.Listener listener6 : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                                            listener6.onForceAllAppsStandbyChanged(appStateTrackerImpl);
                                        }
                                        com.android.server.AppStateTrackerImpl.this.mStatLogger.logDurationStat(7, time);
                                        return;
                                    case 8:
                                        com.android.server.AppStateTrackerImpl.this.handleUserRemoved(message.arg1);
                                        return;
                                    case 10:
                                        for (com.android.server.AppStateTrackerImpl.Listener listener7 : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                                            listener7.onExemptedBucketChanged(appStateTrackerImpl);
                                        }
                                        com.android.server.AppStateTrackerImpl.this.mStatLogger.logDurationStat(6, time);
                                        return;
                                    case 11:
                                        boolean z = message.arg1 == 1;
                                        for (com.android.server.AppStateTrackerImpl.Listener listener8 : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                                            listener8.onAutoRestrictedBucketFeatureFlagChanged(appStateTrackerImpl, z);
                                        }
                                        return;
                                    case 12:
                                        handleUidActive(message.arg1);
                                        return;
                                    case 13:
                                        handleUidGone(message.arg1);
                                        if (message.arg2 != 0) {
                                            handleUidDisabled(message.arg1);
                                            return;
                                        }
                                        return;
                                    case 14:
                                        handleUidIdle(message.arg1);
                                        if (message.arg2 != 0) {
                                            handleUidDisabled(message.arg1);
                                            return;
                                        }
                                        return;
                                    case 15:
                                        handleUidCached(message.arg1, message.arg2 != 0);
                                        return;
                                }
                            }
                            return;
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
            }
        }

        private void handleUidCached(int i, boolean z) {
            for (com.android.server.AppStateTrackerImpl.Listener listener : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                listener.handleUidCachedChanged(i, z);
            }
        }

        private void handleUidDisabled(int i) {
            for (com.android.server.AppStateTrackerImpl.Listener listener : com.android.server.AppStateTrackerImpl.this.cloneListeners()) {
                listener.removeAlarmsForUid(i);
            }
        }

        public void handleUidActive(int i) {
            synchronized (com.android.server.AppStateTrackerImpl.this.mLock) {
                try {
                    if (com.android.server.AppStateTrackerImpl.addUidToArray(com.android.server.AppStateTrackerImpl.this.mActiveUids, i)) {
                        com.android.server.AppStateTrackerImpl.this.mHandler.notifyUidActiveStateChanged(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void handleUidGone(int i) {
            removeUid(i, true);
        }

        public void handleUidIdle(int i) {
            removeUid(i, false);
        }

        private void removeUid(int i, boolean z) {
            synchronized (com.android.server.AppStateTrackerImpl.this.mLock) {
                try {
                    if (com.android.server.AppStateTrackerImpl.removeUidFromArray(com.android.server.AppStateTrackerImpl.this.mActiveUids, i, z)) {
                        com.android.server.AppStateTrackerImpl.this.mHandler.notifyUidActiveStateChanged(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void handleUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mRunAnyRestrictedPackages.size() - 1; size >= 0; size--) {
                    if (android.os.UserHandle.getUserId(((java.lang.Integer) this.mRunAnyRestrictedPackages.valueAt(size).first).intValue()) == i) {
                        this.mRunAnyRestrictedPackages.removeAt(size);
                    }
                }
                updateBackgroundRestrictedUidPackagesLocked();
                cleanUpArrayForUser(this.mActiveUids, i);
                this.mExemptedBucketPackages.remove(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void cleanUpArrayForUser(android.util.SparseBooleanArray sparseBooleanArray, int i) {
        for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
            if (android.os.UserHandle.getUserId(sparseBooleanArray.keyAt(size)) == i) {
                sparseBooleanArray.removeAt(size);
            }
        }
    }

    public void setPowerSaveExemptionListAppIds(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        synchronized (this.mLock) {
            try {
                int[] iArr5 = this.mPowerExemptAllAppIds;
                int[] iArr6 = this.mTempExemptAppIds;
                this.mPowerExemptSystemAppIds = iArr;
                this.mPowerExemptAllAppIds = iArr2;
                this.mTempExemptAppIds = iArr4;
                this.mPowerExemptUserAppIds = iArr3;
                if (isAnyAppIdUnexempt(iArr5, this.mPowerExemptAllAppIds)) {
                    this.mHandler.notifyAllUnexempted();
                } else if (!java.util.Arrays.equals(iArr5, this.mPowerExemptAllAppIds)) {
                    this.mHandler.notifyAllExemptionListChanged();
                }
                if (!java.util.Arrays.equals(iArr6, this.mTempExemptAppIds)) {
                    this.mHandler.notifyTempExemptionListChanged();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isAnyAppIdUnexempt(int[] iArr, int[] iArr2) {
        boolean z;
        boolean z2;
        int i = 0;
        int i2 = 0;
        while (true) {
            z = i >= iArr.length;
            z2 = i2 >= iArr2.length;
            if (z || z2) {
                break;
            }
            int i3 = iArr[i];
            int i4 = iArr2[i2];
            if (i3 == i4) {
                i++;
                i2++;
            } else {
                if (i3 < i4) {
                    return true;
                }
                i2++;
            }
        }
        if (z) {
            return false;
        }
        return z2;
    }

    public void addListener(@android.annotation.NonNull com.android.server.AppStateTrackerImpl.Listener listener) {
        synchronized (this.mLock) {
            this.mListeners.add(listener);
        }
    }

    public boolean areAlarmsRestricted(int i, @android.annotation.NonNull java.lang.String str) {
        boolean z = false;
        if (isUidActive(i)) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (com.android.internal.util.jobs.ArrayUtils.contains(this.mPowerExemptAllAppIds, android.os.UserHandle.getAppId(i))) {
                    return false;
                }
                if (!this.mActivityManagerInternal.isBgAutoRestrictedBucketFeatureFlagEnabled() && isRunAnyRestrictedLocked(i, str)) {
                    z = true;
                }
                return z;
            } finally {
            }
        }
    }

    public boolean areAlarmsRestrictedByBatterySaver(int i, @android.annotation.NonNull java.lang.String str) {
        if (isUidActive(i)) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (com.android.internal.util.jobs.ArrayUtils.contains(this.mPowerExemptAllAppIds, android.os.UserHandle.getAppId(i))) {
                    return false;
                }
                int userId = android.os.UserHandle.getUserId(i);
                if (this.mAppStandbyInternal.isAppIdleEnabled() && !this.mAppStandbyInternal.isInParole() && this.mExemptedBucketPackages.contains(userId, str)) {
                    return false;
                }
                return this.mForceAllAppsStandby;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean areJobsRestricted(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        if (isUidActive(i)) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                int appId = android.os.UserHandle.getAppId(i);
                if (!com.android.internal.util.jobs.ArrayUtils.contains(this.mPowerExemptAllAppIds, appId) && !com.android.internal.util.jobs.ArrayUtils.contains(this.mTempExemptAppIds, appId)) {
                    if (!this.mActivityManagerInternal.isBgAutoRestrictedBucketFeatureFlagEnabled() && isRunAnyRestrictedLocked(i, str)) {
                        return true;
                    }
                    if (z) {
                        return false;
                    }
                    int userId = android.os.UserHandle.getUserId(i);
                    if (this.mAppStandbyInternal.isAppIdleEnabled() && !this.mAppStandbyInternal.isInParole() && this.mExemptedBucketPackages.contains(userId, str)) {
                        return false;
                    }
                    return this.mForceAllAppsStandby;
                }
                return false;
            } finally {
            }
        }
    }

    public boolean isUidActive(int i) {
        boolean z;
        if (android.os.UserHandle.isCore(i)) {
            return true;
        }
        synchronized (this.mLock) {
            z = this.mActiveUids.get(i);
        }
        return z;
    }

    public boolean isUidActiveSynced(int i) {
        if (isUidActive(i)) {
            return true;
        }
        long time = this.mStatLogger.getTime();
        boolean isUidActive = this.mActivityManagerInternal.isUidActive(i);
        this.mStatLogger.logDurationStat(9, time);
        return isUidActive;
    }

    public boolean isForceAllAppsStandbyEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mForceAllAppsStandby;
        }
        return z;
    }

    public boolean isRunAnyInBackgroundAppOpsAllowed(int i, @android.annotation.NonNull java.lang.String str) {
        boolean z;
        synchronized (this.mLock) {
            z = !isRunAnyRestrictedLocked(i, str);
        }
        return z;
    }

    public boolean isUidPowerSaveExempt(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = com.android.internal.util.jobs.ArrayUtils.contains(this.mPowerExemptAllAppIds, android.os.UserHandle.getAppId(i));
        }
        return contains;
    }

    public boolean isUidPowerSaveUserExempt(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = com.android.internal.util.jobs.ArrayUtils.contains(this.mPowerExemptUserAppIds, android.os.UserHandle.getAppId(i));
        }
        return contains;
    }

    public boolean isUidPowerSaveIdleExempt(int i) {
        boolean z;
        int appId = android.os.UserHandle.getAppId(i);
        synchronized (this.mLock) {
            try {
                z = com.android.internal.util.jobs.ArrayUtils.contains(this.mPowerExemptUserAppIds, appId) || com.android.internal.util.jobs.ArrayUtils.contains(this.mPowerExemptSystemAppIds, appId);
            } finally {
            }
        }
        return z;
    }

    public boolean isUidTempPowerSaveExempt(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = com.android.internal.util.jobs.ArrayUtils.contains(this.mTempExemptAppIds, android.os.UserHandle.getAppId(i));
        }
        return contains;
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Current AppStateTracker State:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("Force all apps standby: ");
                indentingPrintWriter.println(isForceAllAppsStandbyEnabled());
                indentingPrintWriter.print("Small Battery Device: ");
                indentingPrintWriter.println(isSmallBatteryDevice());
                indentingPrintWriter.print("Force all apps standby for small battery device: ");
                indentingPrintWriter.println(this.mForceAllAppStandbyForSmallBattery);
                indentingPrintWriter.print("Plugged In: ");
                indentingPrintWriter.println(this.mIsPluggedIn);
                indentingPrintWriter.print("Active uids: ");
                dumpUids(indentingPrintWriter, this.mActiveUids);
                indentingPrintWriter.print("System exemption list appids: ");
                indentingPrintWriter.println(java.util.Arrays.toString(this.mPowerExemptSystemAppIds));
                indentingPrintWriter.print("Except-idle + user exemption list appids: ");
                indentingPrintWriter.println(java.util.Arrays.toString(this.mPowerExemptAllAppIds));
                indentingPrintWriter.print("User exemption list appids: ");
                indentingPrintWriter.println(java.util.Arrays.toString(this.mPowerExemptUserAppIds));
                indentingPrintWriter.print("Temp exemption list appids: ");
                indentingPrintWriter.println(java.util.Arrays.toString(this.mTempExemptAppIds));
                indentingPrintWriter.println("Exempted bucket packages:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mExemptedBucketPackages.size(); i++) {
                    indentingPrintWriter.print("User ");
                    indentingPrintWriter.print(this.mExemptedBucketPackages.keyAt(i));
                    indentingPrintWriter.println();
                    indentingPrintWriter.increaseIndent();
                    for (int i2 = 0; i2 < this.mExemptedBucketPackages.sizeAt(i); i2++) {
                        indentingPrintWriter.print((java.lang.String) this.mExemptedBucketPackages.valueAt(i, i2));
                        indentingPrintWriter.println();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Restricted packages:");
                indentingPrintWriter.increaseIndent();
                java.util.Iterator<android.util.Pair<java.lang.Integer, java.lang.String>> it = this.mRunAnyRestrictedPackages.iterator();
                while (it.hasNext()) {
                    android.util.Pair<java.lang.Integer, java.lang.String> next = it.next();
                    indentingPrintWriter.print(android.os.UserHandle.formatUid(((java.lang.Integer) next.first).intValue()));
                    indentingPrintWriter.print(" ");
                    indentingPrintWriter.print((java.lang.String) next.second);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
                this.mStatLogger.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dumpUids(java.io.PrintWriter printWriter, android.util.SparseBooleanArray sparseBooleanArray) {
        printWriter.print("[");
        java.lang.String str = "";
        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            if (sparseBooleanArray.valueAt(i)) {
                printWriter.print(str);
                printWriter.print(android.os.UserHandle.formatUid(sparseBooleanArray.keyAt(i)));
                str = " ";
            }
        }
        printWriter.println("]");
    }

    public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            try {
                long start = protoOutputStream.start(j);
                protoOutputStream.write(1133871366145L, isForceAllAppsStandbyEnabled());
                protoOutputStream.write(1133871366150L, isSmallBatteryDevice());
                protoOutputStream.write(1133871366151L, this.mForceAllAppStandbyForSmallBattery);
                protoOutputStream.write(1133871366152L, this.mIsPluggedIn);
                for (int i = 0; i < this.mActiveUids.size(); i++) {
                    if (this.mActiveUids.valueAt(i)) {
                        protoOutputStream.write(2220498092034L, this.mActiveUids.keyAt(i));
                    }
                }
                for (int i2 : this.mPowerExemptSystemAppIds) {
                    protoOutputStream.write(2220498092046L, i2);
                }
                for (int i3 : this.mPowerExemptAllAppIds) {
                    protoOutputStream.write(2220498092035L, i3);
                }
                for (int i4 : this.mPowerExemptUserAppIds) {
                    protoOutputStream.write(2220498092044L, i4);
                }
                for (int i5 : this.mTempExemptAppIds) {
                    protoOutputStream.write(2220498092036L, i5);
                }
                for (int i6 = 0; i6 < this.mExemptedBucketPackages.size(); i6++) {
                    for (int i7 = 0; i7 < this.mExemptedBucketPackages.sizeAt(i6); i7++) {
                        long start2 = protoOutputStream.start(2246267895818L);
                        protoOutputStream.write(1120986464257L, this.mExemptedBucketPackages.keyAt(i6));
                        protoOutputStream.write(1138166333442L, (java.lang.String) this.mExemptedBucketPackages.valueAt(i6, i7));
                        protoOutputStream.end(start2);
                    }
                }
                java.util.Iterator<android.util.Pair<java.lang.Integer, java.lang.String>> it = this.mRunAnyRestrictedPackages.iterator();
                while (it.hasNext()) {
                    android.util.Pair<java.lang.Integer, java.lang.String> next = it.next();
                    long start3 = protoOutputStream.start(2246267895813L);
                    protoOutputStream.write(1120986464257L, ((java.lang.Integer) next.first).intValue());
                    protoOutputStream.write(1138166333442L, (java.lang.String) next.second);
                    protoOutputStream.end(start3);
                }
                this.mStatLogger.dumpProto(protoOutputStream, 1146756268041L);
                protoOutputStream.end(start);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
