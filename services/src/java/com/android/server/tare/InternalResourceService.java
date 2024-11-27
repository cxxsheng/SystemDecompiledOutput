package com.android.server.tare;

/* loaded from: classes2.dex */
public class InternalResourceService extends com.android.server.SystemService {
    private static final java.lang.String ALARM_TAG_WEALTH_RECLAMATION = "*tare.reclamation*";
    public static final boolean DEBUG = android.util.Log.isLoggable("TARE", 3);
    private static final float DEFAULT_UNUSED_RECLAMATION_PERCENTAGE = 0.1f;
    static final long INSTALLER_FIRST_SETUP_GRACE_PERIOD_MS = 604800000;
    private static final long MIN_UNUSED_TIME_MS = 259200000;
    private static final int MSG_CLEAN_UP_TEMP_VIP_LIST = 5;
    private static final int MSG_NOTIFY_AFFORDABILITY_CHANGE_LISTENER = 0;
    private static final int MSG_NOTIFY_STATE_CHANGE_LISTENER = 4;
    private static final int MSG_NOTIFY_STATE_CHANGE_LISTENERS = 3;
    private static final int MSG_PROCESS_USAGE_EVENT = 2;
    private static final int MSG_SCHEDULE_UNUSED_WEALTH_RECLAMATION_EVENT = 1;
    private static final int PACKAGE_QUERY_FLAGS = 1074532352;
    private static final int QUANTITATIVE_EASING_BATTERY_THRESHOLD = 50;
    private static final long RECLAMATION_STARTUP_DELAY_MS = 30000;
    private static final long STOCK_ADJUSTMENT_FIRST_SETUP_GRACE_PERIOD_MS = 432000000;
    private static final int STOCK_RECALCULATION_BATTERY_THRESHOLD = 80;
    private static final long STOCK_RECALCULATION_DELAY_MS = 57600000;
    private static final long STOCK_RECALCULATION_MIN_DATA_DURATION_MS = 28800000;
    public static final java.lang.String TAG = "TARE-IRS";
    static final long UNUSED_RECLAMATION_PERIOD_MS = 86400000;
    private final com.android.server.tare.Agent mAgent;
    private final com.android.server.tare.Analyst mAnalyst;
    private final com.android.internal.app.IAppOpsCallback mApbListener;
    private com.android.internal.app.IAppOpsService mAppOpsService;
    private final android.os.BatteryManagerInternal mBatteryManagerInternal;
    private volatile int mBootPhase;
    private final android.content.BroadcastReceiver mBroadcastReceiver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.tare.CompleteEconomicPolicy mCompleteEconomicPolicy;
    private final com.android.server.tare.InternalResourceService.ConfigObserver mConfigObserver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentBatteryLevel;
    private final int mDefaultTargetBackgroundBatteryLifeHours;
    private android.os.IDeviceIdleController mDeviceIdleController;
    private final com.android.server.tare.InternalResourceService.EconomyManagerStub mEconomyManagerStub;
    private volatile int mEnabledMode;
    private volatile boolean mExemptListLoaded;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArraySet<java.lang.String> mExemptedApps;
    private final android.os.Handler mHandler;
    private volatile boolean mHasBattery;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mInstallers;
    private final java.lang.Object mLock;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mPackageToUidCache"})
    private final android.util.SparseArrayMap<java.lang.String, java.lang.Integer> mPackageToUidCache;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.tare.InstalledPackageInfo> mPkgCache;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseSetArray<java.lang.String> mRestrictedApps;
    private final com.android.server.tare.Scribe mScribe;

    @com.android.internal.annotations.GuardedBy({"mStateChangeListeners"})
    private final android.util.SparseSetArray<com.android.server.tare.EconomyManagerInternal.TareStateChangeListener> mStateChangeListeners;
    private final android.app.usage.UsageStatsManagerInternal.UsageEventListener mSurveillanceAgent;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mTargetBackgroundBatteryLifeHours;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, java.lang.Long> mTemporaryVips;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseSetArray<java.lang.String> mUidToPackageCache;
    private final android.app.AlarmManager.OnAlarmListener mUnusedWealthReclamationListener;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, java.lang.Boolean> mVipOverrides;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.lang.String mWellbeingPackage;

    public InternalResourceService(android.content.Context context) {
        super(context);
        int i;
        this.mLock = new java.lang.Object();
        this.mPkgCache = new android.util.SparseArrayMap<>();
        this.mUidToPackageCache = new android.util.SparseSetArray<>();
        this.mPackageToUidCache = new android.util.SparseArrayMap<>();
        this.mStateChangeListeners = new android.util.SparseSetArray<>();
        this.mRestrictedApps = new android.util.SparseSetArray<>();
        this.mExemptedApps = new android.util.ArraySet<>();
        this.mVipOverrides = new android.util.SparseArrayMap<>();
        this.mTemporaryVips = new android.util.SparseArrayMap<>();
        this.mInstallers = new android.util.SparseArrayMap<>();
        this.mHasBattery = true;
        this.mApbListener = new com.android.internal.app.IAppOpsCallback.Stub() { // from class: com.android.server.tare.InternalResourceService.1
            public void opChanged(int i2, int i3, java.lang.String str, java.lang.String str2) {
                boolean z = false;
                try {
                    if (com.android.server.tare.InternalResourceService.this.mAppOpsService.checkOperation(70, i3, str) != 0) {
                        z = true;
                    }
                } catch (android.os.RemoteException e) {
                }
                int userId = android.os.UserHandle.getUserId(i3);
                synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                    try {
                        if (z) {
                            if (com.android.server.tare.InternalResourceService.this.mRestrictedApps.add(userId, str)) {
                                com.android.server.tare.InternalResourceService.this.mAgent.onAppRestrictedLocked(userId, str);
                            }
                        } else if (com.android.server.tare.InternalResourceService.this.mRestrictedApps.remove(android.os.UserHandle.getUserId(i3), str)) {
                            com.android.server.tare.InternalResourceService.this.mAgent.onAppUnrestrictedLocked(userId, str);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.tare.InternalResourceService.2
            @android.annotation.Nullable
            private java.lang.String getPackageName(android.content.Intent intent) {
                android.net.Uri data = intent.getData();
                if (data != null) {
                    return data.getSchemeSpecificPart();
                }
                return null;
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -2061058799:
                        if (action.equals("android.intent.action.USER_REMOVED")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1538406691:
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -757780528:
                        if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -625323454:
                        if (action.equals("android.intent.action.BATTERY_LEVEL_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -65633567:
                        if (action.equals("android.os.action.POWER_SAVE_WHITELIST_CHANGED")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1121780209:
                        if (action.equals("android.intent.action.USER_ADDED")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1580442797:
                        if (action.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
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
                        boolean booleanExtra = intent.getBooleanExtra("present", com.android.server.tare.InternalResourceService.this.mHasBattery);
                        if (com.android.server.tare.InternalResourceService.this.mHasBattery != booleanExtra) {
                            com.android.server.tare.InternalResourceService.this.mHasBattery = booleanExtra;
                            com.android.server.tare.InternalResourceService.this.mConfigObserver.updateEnabledStatus();
                            break;
                        }
                        break;
                    case 1:
                        com.android.server.tare.InternalResourceService.this.onBatteryLevelChanged();
                        break;
                    case 2:
                        com.android.server.tare.InternalResourceService.this.onPackageRemoved(intent.getIntExtra("android.intent.extra.UID", -1), getPackageName(intent));
                        break;
                    case 3:
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            com.android.server.tare.InternalResourceService.this.onPackageAdded(intent.getIntExtra("android.intent.extra.UID", -1), getPackageName(intent));
                            break;
                        }
                        break;
                    case 4:
                        com.android.server.tare.InternalResourceService.this.onPackageForceStopped(android.os.UserHandle.getUserId(intent.getIntExtra("android.intent.extra.UID", -1)), getPackageName(intent));
                        break;
                    case 5:
                        com.android.server.tare.InternalResourceService.this.onUserAdded(intent.getIntExtra("android.intent.extra.user_handle", 0));
                        break;
                    case 6:
                        com.android.server.tare.InternalResourceService.this.onUserRemoved(intent.getIntExtra("android.intent.extra.user_handle", 0));
                        break;
                    case 7:
                        com.android.server.tare.InternalResourceService.this.onExemptionListChanged();
                        break;
                }
            }
        };
        this.mSurveillanceAgent = new android.app.usage.UsageStatsManagerInternal.UsageEventListener() { // from class: com.android.server.tare.InternalResourceService.3
            @Override // android.app.usage.UsageStatsManagerInternal.UsageEventListener
            public void onUsageEvent(int i2, @android.annotation.NonNull android.app.usage.UsageEvents.Event event) {
                switch (event.getEventType()) {
                    case 1:
                    case 2:
                    case 7:
                    case 9:
                    case 10:
                    case 12:
                    case 23:
                    case 24:
                        com.android.server.tare.InternalResourceService.this.mHandler.obtainMessage(2, i2, 0, event).sendToTarget();
                        break;
                    default:
                        if (com.android.server.tare.InternalResourceService.DEBUG) {
                            android.util.Slog.d(com.android.server.tare.InternalResourceService.TAG, "Dropping event " + event.getEventType());
                            break;
                        }
                        break;
                }
            }
        };
        this.mUnusedWealthReclamationListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.tare.InternalResourceService.4
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                    com.android.server.tare.InternalResourceService.this.mAgent.reclaimUnusedAssetsLocked(0.10000000149011612d, com.android.server.tare.InternalResourceService.MIN_UNUSED_TIME_MS, false);
                    com.android.server.tare.InternalResourceService.this.mScribe.setLastReclamationTimeLocked(com.android.server.tare.TareUtils.getCurrentTimeMillis());
                    com.android.server.tare.InternalResourceService.this.scheduleUnusedWealthReclamationLocked();
                }
            }
        };
        this.mHandler = new com.android.server.tare.InternalResourceService.IrsHandler(com.android.server.tare.TareHandlerThread.get().getLooper());
        this.mBatteryManagerInternal = (android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class);
        this.mPackageManager = context.getPackageManager();
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mEconomyManagerStub = new com.android.server.tare.InternalResourceService.EconomyManagerStub();
        this.mAnalyst = new com.android.server.tare.Analyst();
        this.mScribe = new com.android.server.tare.Scribe(this, this.mAnalyst);
        this.mCompleteEconomicPolicy = new com.android.server.tare.CompleteEconomicPolicy(this);
        this.mAgent = new com.android.server.tare.Agent(this, this.mScribe, this.mAnalyst);
        this.mConfigObserver = new com.android.server.tare.InternalResourceService.ConfigObserver(this.mHandler, context);
        if (this.mPackageManager.hasSystemFeature("android.hardware.type.watch")) {
            i = 100;
        } else {
            i = 40;
        }
        this.mDefaultTargetBackgroundBatteryLifeHours = i;
        this.mTargetBackgroundBatteryLifeHours = this.mDefaultTargetBackgroundBatteryLifeHours;
        publishLocalService(com.android.server.tare.EconomyManagerInternal.class, new com.android.server.tare.InternalResourceService.LocalService());
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("tare", this.mEconomyManagerStub);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        this.mBootPhase = i;
        switch (i) {
            case 500:
                this.mAppOpsService = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
                this.mDeviceIdleController = android.os.IDeviceIdleController.Stub.asInterface(android.os.ServiceManager.getService("deviceidle"));
                this.mConfigObserver.start();
                onBootPhaseSystemServicesReady();
                break;
            case 600:
                onBootPhaseThirdPartyAppsCanStart();
                break;
            case 1000:
                onBootPhaseBootCompleted();
                break;
        }
    }

    @android.annotation.NonNull
    java.lang.Object getLock() {
        return this.mLock;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.tare.CompleteEconomicPolicy getCompleteEconomicPolicyLocked() {
        return this.mCompleteEconomicPolicy;
    }

    int getAppUpdateResponsibilityCount(int i, @android.annotation.NonNull java.lang.String str) {
        int size;
        synchronized (this.mLock) {
            size = com.android.internal.util.jobs.ArrayUtils.size((java.util.Collection<?>) this.mInstallers.get(i, str));
        }
        return size;
    }

    @android.annotation.NonNull
    android.util.SparseArrayMap<java.lang.String, com.android.server.tare.InstalledPackageInfo> getInstalledPackages() {
        android.util.SparseArrayMap<java.lang.String, com.android.server.tare.InstalledPackageInfo> sparseArrayMap;
        synchronized (this.mLock) {
            sparseArrayMap = this.mPkgCache;
        }
        return sparseArrayMap;
    }

    @android.annotation.NonNull
    java.util.List<com.android.server.tare.InstalledPackageInfo> getInstalledPackages(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mPkgCache.indexOfKey(i);
                if (indexOfKey < 0) {
                    return arrayList;
                }
                for (int numElementsForKeyAt = this.mPkgCache.numElementsForKeyAt(indexOfKey) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                    arrayList.add((com.android.server.tare.InstalledPackageInfo) this.mPkgCache.valueAt(indexOfKey, numElementsForKeyAt));
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    com.android.server.tare.InstalledPackageInfo getInstalledPackageInfo(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.tare.InstalledPackageInfo installedPackageInfo;
        synchronized (this.mLock) {
            installedPackageInfo = (com.android.server.tare.InstalledPackageInfo) this.mPkgCache.get(i, str);
        }
        return installedPackageInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getConsumptionLimitLocked() {
        return (this.mCurrentBatteryLevel * this.mScribe.getSatiatedConsumptionLimitLocked()) / 100;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getMinBalanceLocked(int i, @android.annotation.NonNull java.lang.String str) {
        return (this.mCurrentBatteryLevel * this.mCompleteEconomicPolicy.getMinSatiatedBalance(i, str)) / 100;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getInitialSatiatedConsumptionLimitLocked() {
        return this.mCompleteEconomicPolicy.getInitialSatiatedConsumptionLimit();
    }

    long getRealtimeSinceFirstSetupMs() {
        return this.mScribe.getRealtimeSinceFirstSetupMs(android.os.SystemClock.elapsedRealtime());
    }

    int getUid(int i, @android.annotation.NonNull java.lang.String str) {
        int intValue;
        synchronized (this.mPackageToUidCache) {
            try {
                java.lang.Integer num = (java.lang.Integer) this.mPackageToUidCache.get(i, str);
                if (num == null) {
                    num = java.lang.Integer.valueOf(this.mPackageManagerInternal.getPackageUid(str, 0L, i));
                    this.mPackageToUidCache.add(i, str, num);
                }
                intValue = num.intValue();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return intValue;
    }

    int getEnabledMode() {
        return this.mEnabledMode;
    }

    int getEnabledMode(int i) {
        synchronized (this.mLock) {
            try {
                if (!this.mCompleteEconomicPolicy.isPolicyEnabled(i)) {
                    return 0;
                }
                return this.mEnabledMode;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isHeadlessSystemApp(int i, @android.annotation.NonNull java.lang.String str) {
        if (str == null) {
            android.util.Slog.wtfStack(TAG, "isHeadlessSystemApp called with null package");
            return false;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.tare.InstalledPackageInfo installedPackageInfo = getInstalledPackageInfo(i, str);
                if (installedPackageInfo != null && installedPackageInfo.isHeadlessSystemApp) {
                    return true;
                }
                return str.equals(this.mWellbeingPackage);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isPackageExempted(int i, @android.annotation.NonNull java.lang.String str) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mExemptedApps.contains(str);
        }
        return contains;
    }

    boolean isPackageRestricted(int i, @android.annotation.NonNull java.lang.String str) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mRestrictedApps.contains(i, str);
        }
        return contains;
    }

    boolean isSystem(int i, @android.annotation.NonNull java.lang.String str) {
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str)) {
            return true;
        }
        return android.os.UserHandle.isCore(getUid(i, str));
    }

    boolean isVip(int i, @android.annotation.NonNull java.lang.String str) {
        return isVip(i, str, android.os.SystemClock.elapsedRealtime());
    }

    boolean isVip(int i, @android.annotation.NonNull java.lang.String str, long j) {
        synchronized (this.mLock) {
            try {
                java.lang.Boolean bool = (java.lang.Boolean) this.mVipOverrides.get(i, str);
                if (bool != null) {
                    return bool.booleanValue();
                }
                boolean z = true;
                if (isSystem(i, str)) {
                    return true;
                }
                synchronized (this.mLock) {
                    try {
                        java.lang.Long l = (java.lang.Long) this.mTemporaryVips.get(i, str);
                        if (l == null) {
                            return false;
                        }
                        if (j > l.longValue()) {
                            z = false;
                        }
                        return z;
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    void onBatteryLevelChanged() {
        synchronized (this.mLock) {
            try {
                int currentBatteryLevel = getCurrentBatteryLevel();
                this.mAnalyst.noteBatteryLevelChange(currentBatteryLevel);
                boolean z = currentBatteryLevel > this.mCurrentBatteryLevel;
                if (z) {
                    if (currentBatteryLevel >= 80) {
                        maybeAdjustDesiredStockLevelLocked();
                    }
                    this.mAgent.distributeBasicIncomeLocked(currentBatteryLevel);
                } else if (currentBatteryLevel == this.mCurrentBatteryLevel) {
                    return;
                }
                this.mCurrentBatteryLevel = currentBatteryLevel;
                adjustCreditSupplyLocked(z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onDeviceStateChanged() {
        synchronized (this.mLock) {
            this.mAgent.onDeviceStateChangedLocked();
        }
    }

    void onExemptionListChanged() {
        int[] userIds = this.mUserManagerInternal.getUserIds();
        synchronized (this.mLock) {
            android.util.ArraySet<java.lang.String> arraySet = this.mExemptedApps;
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            try {
                this.mExemptedApps = new android.util.ArraySet<>(this.mDeviceIdleController.getFullPowerWhitelist());
                this.mExemptListLoaded = true;
            } catch (android.os.RemoteException e) {
            }
            for (int size = this.mExemptedApps.size() - 1; size >= 0; size--) {
                java.lang.String valueAt = this.mExemptedApps.valueAt(size);
                if (!arraySet.contains(valueAt)) {
                    arraySet2.add(valueAt);
                }
                arraySet.remove(valueAt);
            }
            for (int size2 = arraySet2.size() - 1; size2 >= 0; size2--) {
                java.lang.String str = (java.lang.String) arraySet2.valueAt(size2);
                for (int i : userIds) {
                    if (getUid(i, str) >= 0) {
                        this.mAgent.onAppExemptedLocked(i, str);
                    }
                }
            }
            for (int size3 = arraySet.size() - 1; size3 >= 0; size3--) {
                java.lang.String valueAt2 = arraySet.valueAt(size3);
                for (int i2 : userIds) {
                    if (getUid(i2, valueAt2) >= 0) {
                        this.mAgent.onAppUnexemptedLocked(i2, valueAt2);
                    }
                }
            }
        }
    }

    void onPackageAdded(int i, @android.annotation.NonNull java.lang.String str) {
        int userId = android.os.UserHandle.getUserId(i);
        try {
            android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(str, PACKAGE_QUERY_FLAGS, userId);
            synchronized (this.mPackageToUidCache) {
                this.mPackageToUidCache.add(userId, str, java.lang.Integer.valueOf(i));
            }
            synchronized (this.mLock) {
                try {
                    com.android.server.tare.InstalledPackageInfo installedPackageInfo = new com.android.server.tare.InstalledPackageInfo(getContext(), userId, packageInfoAsUser);
                    maybeUpdateInstallerStatusLocked((com.android.server.tare.InstalledPackageInfo) this.mPkgCache.add(userId, str, installedPackageInfo), installedPackageInfo);
                    this.mUidToPackageCache.add(i, str);
                    this.mAgent.grantBirthrightLocked(userId, str);
                    if (installedPackageInfo.installerPackageName != null) {
                        this.mAgent.noteInstantaneousEventLocked(userId, installedPackageInfo.installerPackageName, com.android.server.tare.JobSchedulerEconomicPolicy.REWARD_APP_INSTALL, null);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.wtf(TAG, "PM couldn't find newly added package: " + str, e);
        }
    }

    void onPackageForceStopped(int i, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            this.mAgent.reclaimAllAssetsLocked(i, str, 8);
        }
    }

    void onPackageRemoved(int i, @android.annotation.NonNull java.lang.String str) {
        android.util.ArraySet arraySet;
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mPackageToUidCache) {
            this.mPackageToUidCache.delete(userId, str);
        }
        synchronized (this.mLock) {
            try {
                this.mUidToPackageCache.remove(i, str);
                this.mVipOverrides.delete(userId, str);
                com.android.server.tare.InstalledPackageInfo installedPackageInfo = (com.android.server.tare.InstalledPackageInfo) this.mPkgCache.delete(userId, str);
                this.mInstallers.delete(userId, str);
                if (installedPackageInfo != null && installedPackageInfo.installerPackageName != null && (arraySet = (android.util.ArraySet) this.mInstallers.get(userId, installedPackageInfo.installerPackageName)) != null) {
                    arraySet.remove(str);
                }
                this.mAgent.onPackageRemovedLocked(userId, str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onUidStateChanged(int i) {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<java.lang.String> packagesForUidLocked = getPackagesForUidLocked(i);
                if (packagesForUidLocked == null) {
                    android.util.Slog.e(TAG, "Don't have packages for uid " + i);
                } else {
                    this.mAgent.onAppStatesChangedLocked(android.os.UserHandle.getUserId(i), packagesForUidLocked);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onUserAdded(int i) {
        synchronized (this.mLock) {
            try {
                java.util.List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(PACKAGE_QUERY_FLAGS, i);
                for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
                    com.android.server.tare.InstalledPackageInfo installedPackageInfo = new com.android.server.tare.InstalledPackageInfo(getContext(), i, (android.content.pm.PackageInfo) installedPackagesAsUser.get(size));
                    maybeUpdateInstallerStatusLocked((com.android.server.tare.InstalledPackageInfo) this.mPkgCache.add(i, installedPackageInfo.packageName, installedPackageInfo), installedPackageInfo);
                }
                this.mAgent.grantBirthrightsLocked(i);
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                this.mScribe.setUserAddedTimeLocked(i, elapsedRealtime);
                grantInstallersTemporaryVipStatusLocked(i, elapsedRealtime, 604800000L);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                this.mVipOverrides.delete(i);
                int indexOfKey = this.mPkgCache.indexOfKey(i);
                if (indexOfKey >= 0) {
                    for (int numElementsForKeyAt = this.mPkgCache.numElementsForKeyAt(indexOfKey) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                        this.mUidToPackageCache.remove(((com.android.server.tare.InstalledPackageInfo) this.mPkgCache.valueAt(indexOfKey, numElementsForKeyAt)).uid);
                    }
                }
                this.mInstallers.delete(i);
                this.mPkgCache.delete(i);
                this.mAgent.onUserRemovedLocked(i);
                this.mScribe.onUserRemovedLocked(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void maybePerformQuantitativeEasingLocked() {
        if (this.mConfigObserver.ENABLE_TIP3) {
            maybeAdjustDesiredStockLevelLocked();
            return;
        }
        if (getRealtimeSinceFirstSetupMs() < STOCK_ADJUSTMENT_FIRST_SETUP_GRACE_PERIOD_MS) {
            return;
        }
        long remainingConsumableCakesLocked = this.mScribe.getRemainingConsumableCakesLocked();
        if (this.mCurrentBatteryLevel <= 50 || remainingConsumableCakesLocked > 0) {
            return;
        }
        long satiatedConsumptionLimitLocked = this.mScribe.getSatiatedConsumptionLimitLocked();
        long min = java.lang.Math.min((((this.mCurrentBatteryLevel - 50) * satiatedConsumptionLimitLocked) / 100) + satiatedConsumptionLimitLocked, this.mCompleteEconomicPolicy.getMaxSatiatedConsumptionLimit());
        if (min != satiatedConsumptionLimitLocked) {
            android.util.Slog.i(TAG, "Increasing consumption limit from " + com.android.server.tare.TareUtils.cakeToString(satiatedConsumptionLimitLocked) + " to " + com.android.server.tare.TareUtils.cakeToString(min));
            this.mScribe.setConsumptionLimitLocked(min);
            adjustCreditSupplyLocked(true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void maybeAdjustDesiredStockLevelLocked() {
        long max;
        if (!this.mConfigObserver.ENABLE_TIP3 || getRealtimeSinceFirstSetupMs() < STOCK_ADJUSTMENT_FIRST_SETUP_GRACE_PERIOD_MS) {
            return;
        }
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        if (currentTimeMillis - this.mScribe.getLastStockRecalculationTimeLocked() < STOCK_RECALCULATION_DELAY_MS || this.mCurrentBatteryLevel <= 80) {
            return;
        }
        long batteryScreenOffDurationMs = this.mAnalyst.getBatteryScreenOffDurationMs();
        if (batteryScreenOffDurationMs < STOCK_RECALCULATION_MIN_DATA_DURATION_MS) {
            return;
        }
        long batteryScreenOffDischargeMah = this.mAnalyst.getBatteryScreenOffDischargeMah();
        if (batteryScreenOffDischargeMah == 0) {
            android.util.Slog.i(TAG, "Total discharge was 0");
            return;
        }
        long batteryFullCharge = this.mBatteryManagerInternal.getBatteryFullCharge() / 1000;
        long j = ((batteryFullCharge * batteryScreenOffDurationMs) / batteryScreenOffDischargeMah) / 3600000;
        long j2 = (j * 100) / this.mTargetBackgroundBatteryLifeHours;
        if (DEBUG) {
            android.util.Slog.d(TAG, "maybeAdjustDesiredStockLevelLocked: screenOffMs=" + batteryScreenOffDurationMs + " dischargeMah=" + batteryScreenOffDischargeMah + " capacityMah=" + batteryFullCharge + " estimatedLifeHours=" + j + " %ofTarget=" + j2);
        }
        long satiatedConsumptionLimitLocked = this.mScribe.getSatiatedConsumptionLimitLocked();
        if (j2 > 105) {
            max = java.lang.Math.min((long) (satiatedConsumptionLimitLocked * 1.01d), this.mCompleteEconomicPolicy.getMaxSatiatedConsumptionLimit());
        } else if (j2 < 100) {
            max = java.lang.Math.max((long) (satiatedConsumptionLimitLocked * 0.98d), this.mCompleteEconomicPolicy.getMinSatiatedConsumptionLimit());
        } else {
            return;
        }
        if (max != satiatedConsumptionLimitLocked) {
            android.util.Slog.i(TAG, "Adjusting consumption limit from " + com.android.server.tare.TareUtils.cakeToString(satiatedConsumptionLimitLocked) + " to " + com.android.server.tare.TareUtils.cakeToString(max) + " because drain was " + j2 + "% of target");
            this.mScribe.setConsumptionLimitLocked(max);
            adjustCreditSupplyLocked(true);
            this.mScribe.setLastStockRecalculationTimeLocked(currentTimeMillis);
        }
    }

    void postAffordabilityChanged(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote) {
        if (DEBUG) {
            android.util.Slog.d(TAG, i + ":" + str + " affordability changed to " + actionAffordabilityNote.isCurrentlyAffordable());
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = i;
        obtain.arg1 = str;
        obtain.arg2 = actionAffordabilityNote;
        this.mHandler.obtainMessage(0, obtain).sendToTarget();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void adjustCreditSupplyLocked(boolean z) {
        long consumptionLimitLocked = getConsumptionLimitLocked();
        long remainingConsumableCakesLocked = this.mScribe.getRemainingConsumableCakesLocked();
        if (remainingConsumableCakesLocked == consumptionLimitLocked) {
            return;
        }
        if (remainingConsumableCakesLocked > consumptionLimitLocked) {
            this.mScribe.adjustRemainingConsumableCakesLocked(consumptionLimitLocked - remainingConsumableCakesLocked);
        } else if (z) {
            this.mScribe.adjustRemainingConsumableCakesLocked((long) ((this.mCurrentBatteryLevel / 100.0d) * (consumptionLimitLocked - remainingConsumableCakesLocked)));
        }
        this.mAgent.onCreditSupplyChanged();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void grantInstallersTemporaryVipStatusLocked(int i, long j, long j2) {
        java.lang.Long l;
        long j3 = j + j2;
        int indexOfKey = this.mPkgCache.indexOfKey(i);
        if (indexOfKey < 0) {
            return;
        }
        for (int numElementsForKey = this.mPkgCache.numElementsForKey(indexOfKey) - 1; numElementsForKey >= 0; numElementsForKey--) {
            com.android.server.tare.InstalledPackageInfo installedPackageInfo = (com.android.server.tare.InstalledPackageInfo) this.mPkgCache.valueAt(indexOfKey, numElementsForKey);
            if (installedPackageInfo.isSystemInstaller && ((l = (java.lang.Long) this.mTemporaryVips.get(i, installedPackageInfo.packageName)) == null || l.longValue() < j3)) {
                this.mTemporaryVips.add(i, installedPackageInfo.packageName, java.lang.Long.valueOf(j3));
            }
        }
        this.mHandler.sendEmptyMessageDelayed(5, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void processUsageEventLocked(int i, @android.annotation.NonNull android.app.usage.UsageEvents.Event event) {
        if (this.mEnabledMode == 0) {
        }
        java.lang.String packageName = event.getPackageName();
        if (DEBUG) {
            android.util.Slog.d(TAG, "Processing event " + event.getEventType() + " (" + event.mInstanceId + ") for " + com.android.server.tare.TareUtils.appToString(i, packageName));
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        switch (event.getEventType()) {
            case 1:
                this.mAgent.noteOngoingEventLocked(i, packageName, android.hardware.audio.common.V2_0.AudioDevice.IN_AMBIENT, java.lang.String.valueOf(event.mInstanceId), elapsedRealtime);
                break;
            case 2:
            case 23:
            case 24:
                this.mAgent.stopOngoingActionLocked(i, packageName, android.hardware.audio.common.V2_0.AudioDevice.IN_AMBIENT, java.lang.String.valueOf(event.mInstanceId), elapsedRealtime, com.android.server.tare.TareUtils.getCurrentTimeMillis());
                break;
            case 7:
            case 9:
                this.mAgent.noteInstantaneousEventLocked(i, packageName, android.hardware.audio.common.V2_0.AudioDevice.IN_BUILTIN_MIC, null);
                break;
            case 10:
            case 12:
                this.mAgent.noteInstantaneousEventLocked(i, packageName, Integer.MIN_VALUE, null);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void scheduleUnusedWealthReclamationLocked() {
        final long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        final long max = java.lang.Math.max(30000 + currentTimeMillis, this.mScribe.getLastReclamationTimeLocked() + 86400000);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.tare.InternalResourceService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.tare.InternalResourceService.this.lambda$scheduleUnusedWealthReclamationLocked$0(max, currentTimeMillis);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleUnusedWealthReclamationLocked$0(long j, long j2) {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) getContext().getSystemService(android.app.AlarmManager.class);
        if (alarmManager != null) {
            alarmManager.setWindow(3, android.os.SystemClock.elapsedRealtime() + (j - j2), 1800000L, ALARM_TAG_WEALTH_RECLAMATION, this.mUnusedWealthReclamationListener, this.mHandler);
        } else {
            this.mHandler.sendEmptyMessageDelayed(1, 30000L);
        }
    }

    private int getCurrentBatteryLevel() {
        return this.mBatteryManagerInternal.getBatteryLevel();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.ArraySet<java.lang.String> getPackagesForUidLocked(int i) {
        java.lang.String[] packagesForUid;
        android.util.ArraySet<java.lang.String> arraySet = this.mUidToPackageCache.get(i);
        if (arraySet == null && (packagesForUid = this.mPackageManager.getPackagesForUid(i)) != null) {
            for (java.lang.String str : packagesForUid) {
                this.mUidToPackageCache.add(i, str);
            }
            return this.mUidToPackageCache.get(i);
        }
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTareSupported() {
        return this.mHasBattery;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void loadInstalledPackageListLocked() {
        this.mPkgCache.clear();
        for (int i : this.mUserManagerInternal.getUserIds()) {
            java.util.List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(PACKAGE_QUERY_FLAGS, i);
            for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
                com.android.server.tare.InstalledPackageInfo installedPackageInfo = new com.android.server.tare.InstalledPackageInfo(getContext(), i, (android.content.pm.PackageInfo) installedPackagesAsUser.get(size));
                maybeUpdateInstallerStatusLocked((com.android.server.tare.InstalledPackageInfo) this.mPkgCache.add(i, installedPackageInfo.packageName, installedPackageInfo), installedPackageInfo);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeUpdateInstallerStatusLocked(@android.annotation.Nullable com.android.server.tare.InstalledPackageInfo installedPackageInfo, @android.annotation.NonNull com.android.server.tare.InstalledPackageInfo installedPackageInfo2) {
        android.util.ArraySet arraySet;
        boolean z = true;
        if (installedPackageInfo != null) {
            z = true ^ java.util.Objects.equals(installedPackageInfo.installerPackageName, installedPackageInfo2.installerPackageName);
        } else if (installedPackageInfo2.installerPackageName == null) {
            z = false;
        }
        if (!z) {
            return;
        }
        int userId = android.os.UserHandle.getUserId(installedPackageInfo2.uid);
        java.lang.String str = installedPackageInfo2.packageName;
        if (installedPackageInfo != null && (arraySet = (android.util.ArraySet) this.mInstallers.get(userId, installedPackageInfo.installerPackageName)) != null) {
            arraySet.remove(str);
        }
        if (installedPackageInfo2.installerPackageName != null) {
            android.util.ArraySet arraySet2 = (android.util.ArraySet) this.mInstallers.get(userId, installedPackageInfo2.installerPackageName);
            if (arraySet2 == null) {
                arraySet2 = new android.util.ArraySet();
                this.mInstallers.add(userId, installedPackageInfo2.installerPackageName, arraySet2);
            }
            arraySet2.add(str);
        }
    }

    private void registerListeners() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.BATTERY_LEVEL_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter2.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter2, null, null);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter("android.intent.action.USER_REMOVED");
        intentFilter3.addAction("android.intent.action.USER_ADDED");
        getContext().registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter3, null, null);
        ((android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class)).registerListener(this.mSurveillanceAgent);
        try {
            this.mAppOpsService.startWatchingMode(70, (java.lang.String) null, this.mApbListener);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupHeavyWork() {
        android.util.SparseLongArray realtimeSinceUsersAddedLocked;
        if (this.mBootPhase < 600 || this.mEnabledMode == 0) {
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mCompleteEconomicPolicy.setup(this.mConfigObserver.getAllDeviceConfigProperties());
                loadInstalledPackageListLocked();
                boolean z = !this.mScribe.recordExists();
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                if (z) {
                    this.mAgent.grantBirthrightsLocked();
                    this.mScribe.setConsumptionLimitLocked(this.mCompleteEconomicPolicy.getInitialSatiatedConsumptionLimit());
                    this.mScribe.setLastReclamationTimeLocked(com.android.server.tare.TareUtils.getCurrentTimeMillis());
                    realtimeSinceUsersAddedLocked = new android.util.SparseLongArray();
                } else {
                    this.mScribe.loadFromDiskLocked();
                    if (this.mScribe.getSatiatedConsumptionLimitLocked() >= this.mCompleteEconomicPolicy.getMinSatiatedConsumptionLimit() && this.mScribe.getSatiatedConsumptionLimitLocked() <= this.mCompleteEconomicPolicy.getMaxSatiatedConsumptionLimit()) {
                        adjustCreditSupplyLocked(true);
                        realtimeSinceUsersAddedLocked = this.mScribe.getRealtimeSinceUsersAddedLocked(elapsedRealtime);
                    }
                    this.mScribe.setConsumptionLimitLocked(this.mCompleteEconomicPolicy.getInitialSatiatedConsumptionLimit());
                    realtimeSinceUsersAddedLocked = this.mScribe.getRealtimeSinceUsersAddedLocked(elapsedRealtime);
                }
                for (int i : this.mUserManagerInternal.getUserIds()) {
                    long j = realtimeSinceUsersAddedLocked.get(i, 0L);
                    if (j < 604800000) {
                        grantInstallersTemporaryVipStatusLocked(i, elapsedRealtime, 604800000 - j);
                    }
                }
                scheduleUnusedWealthReclamationLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void onBootPhaseSystemServicesReady() {
        boolean booleanExtra;
        if (this.mBootPhase < 500 || this.mEnabledMode == 0) {
            return;
        }
        synchronized (this.mLock) {
            try {
                registerListeners();
                this.mWellbeingPackage = this.mPackageManager.getWellbeingPackageName();
                this.mCurrentBatteryLevel = getCurrentBatteryLevel();
                android.content.Intent registerReceiver = getContext().registerReceiver(null, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED"));
                if (registerReceiver != null && this.mHasBattery != (booleanExtra = registerReceiver.getBooleanExtra("present", true))) {
                    this.mHasBattery = booleanExtra;
                    this.mConfigObserver.updateEnabledStatus();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void onBootPhaseThirdPartyAppsCanStart() {
        if (this.mBootPhase < 600 || this.mEnabledMode == 0) {
            return;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.tare.InternalResourceService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.tare.InternalResourceService.this.setupHeavyWork();
            }
        });
    }

    private void onBootPhaseBootCompleted() {
        if (this.mBootPhase < 1000 || this.mEnabledMode == 0) {
            return;
        }
        synchronized (this.mLock) {
            if (!this.mExemptListLoaded) {
                try {
                    this.mExemptedApps = new android.util.ArraySet<>(this.mDeviceIdleController.getFullPowerWhitelist());
                    this.mExemptListLoaded = true;
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupEverything() {
        if (this.mEnabledMode == 0) {
            return;
        }
        if (this.mBootPhase >= 500) {
            onBootPhaseSystemServicesReady();
        }
        if (this.mBootPhase >= 600) {
            onBootPhaseThirdPartyAppsCanStart();
        }
        if (this.mBootPhase >= 1000) {
            onBootPhaseBootCompleted();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tearDownEverything() {
        if (this.mEnabledMode != 0) {
            return;
        }
        synchronized (this.mLock) {
            this.mAgent.tearDownLocked();
            this.mAnalyst.tearDown();
            this.mCompleteEconomicPolicy.tearDown();
            this.mExemptedApps.clear();
            this.mExemptListLoaded = false;
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.tare.InternalResourceService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.tare.InternalResourceService.this.lambda$tearDownEverything$1();
                }
            });
            this.mPkgCache.clear();
            this.mScribe.tearDownLocked();
            this.mUidToPackageCache.clear();
            getContext().unregisterReceiver(this.mBroadcastReceiver);
            ((android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class)).unregisterListener(this.mSurveillanceAgent);
            try {
                this.mAppOpsService.stopWatchingMode(this.mApbListener);
            } catch (android.os.RemoteException e) {
            }
        }
        synchronized (this.mPackageToUidCache) {
            this.mPackageToUidCache.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tearDownEverything$1() {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) getContext().getSystemService(android.app.AlarmManager.class);
        if (alarmManager != null) {
            alarmManager.cancel(this.mUnusedWealthReclamationListener);
        }
    }

    private final class IrsHandler extends android.os.Handler {
        IrsHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = 0;
            switch (message.what) {
                case 0:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    int i2 = someArgs.argi1;
                    java.lang.String str = (java.lang.String) someArgs.arg1;
                    com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote = (com.android.server.tare.Agent.ActionAffordabilityNote) someArgs.arg2;
                    actionAffordabilityNote.getListener().onAffordabilityChanged(i2, str, actionAffordabilityNote.getActionBill(), actionAffordabilityNote.isCurrentlyAffordable());
                    someArgs.recycle();
                    return;
                case 1:
                    removeMessages(1);
                    synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                        com.android.server.tare.InternalResourceService.this.scheduleUnusedWealthReclamationLocked();
                    }
                    return;
                case 2:
                    int i3 = message.arg1;
                    android.app.usage.UsageEvents.Event event = (android.app.usage.UsageEvents.Event) message.obj;
                    synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                        com.android.server.tare.InternalResourceService.this.processUsageEventLocked(i3, event);
                    }
                    return;
                case 3:
                    int i4 = message.arg1;
                    synchronized (com.android.server.tare.InternalResourceService.this.mStateChangeListeners) {
                        try {
                            int size = com.android.server.tare.InternalResourceService.this.mStateChangeListeners.size();
                            while (i < size) {
                                int keyAt = com.android.server.tare.InternalResourceService.this.mStateChangeListeners.keyAt(i);
                                if ((keyAt & i4) != 0) {
                                    android.util.ArraySet arraySet = com.android.server.tare.InternalResourceService.this.mStateChangeListeners.get(keyAt);
                                    int enabledMode = com.android.server.tare.InternalResourceService.this.getEnabledMode(keyAt);
                                    for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                                        ((com.android.server.tare.EconomyManagerInternal.TareStateChangeListener) arraySet.valueAt(size2)).onTareEnabledModeChanged(enabledMode);
                                    }
                                }
                                i++;
                            }
                        } finally {
                        }
                    }
                    return;
                case 4:
                    ((com.android.server.tare.EconomyManagerInternal.TareStateChangeListener) message.obj).onTareEnabledModeChanged(com.android.server.tare.InternalResourceService.this.getEnabledMode(message.arg1));
                    return;
                case 5:
                    removeMessages(5);
                    synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                        try {
                            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                            long j = Long.MAX_VALUE;
                            while (i < com.android.server.tare.InternalResourceService.this.mTemporaryVips.numMaps()) {
                                int keyAt2 = com.android.server.tare.InternalResourceService.this.mTemporaryVips.keyAt(i);
                                for (int numElementsForKeyAt = com.android.server.tare.InternalResourceService.this.mTemporaryVips.numElementsForKeyAt(i) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                                    java.lang.String str2 = (java.lang.String) com.android.server.tare.InternalResourceService.this.mTemporaryVips.keyAt(i, numElementsForKeyAt);
                                    java.lang.Long l = (java.lang.Long) com.android.server.tare.InternalResourceService.this.mTemporaryVips.valueAt(i, numElementsForKeyAt);
                                    if (l == null || l.longValue() < elapsedRealtime) {
                                        com.android.server.tare.InternalResourceService.this.mTemporaryVips.delete(keyAt2, str2);
                                    } else {
                                        j = java.lang.Math.min(j, l.longValue());
                                    }
                                }
                                i++;
                            }
                            if (j < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                                sendEmptyMessageDelayed(5, j - elapsedRealtime);
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    final class EconomyManagerStub extends android.app.tare.IEconomyManager.Stub {
        EconomyManagerStub() {
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            boolean z;
            long clearCallingIdentity;
            if (!com.android.internal.util.jobs.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.tare.InternalResourceService.this.getContext(), com.android.server.tare.InternalResourceService.TAG, printWriter)) {
                return;
            }
            try {
                if (!com.android.internal.util.jobs.ArrayUtils.isEmpty(strArr)) {
                    z = false;
                    java.lang.String str = strArr[0];
                    if ("-h".equals(str) || "--help".equals(str)) {
                        com.android.server.tare.InternalResourceService.dumpHelp(printWriter);
                        return;
                    }
                    if (!"-a".equals(str)) {
                        if (str.length() > 0 && str.charAt(0) == '-') {
                            printWriter.println("Unknown option: " + str);
                            return;
                        }
                    }
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    com.android.server.tare.InternalResourceService.this.dumpInternal(new android.util.IndentingPrintWriter(printWriter, "  "), z);
                    return;
                }
                com.android.server.tare.InternalResourceService.this.dumpInternal(new android.util.IndentingPrintWriter(printWriter, "  "), z);
                return;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            z = true;
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        }

        public int getEnabledMode() {
            return com.android.server.tare.InternalResourceService.this.getEnabledMode();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor2, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr) {
            return new com.android.server.tare.TareShellCommand(com.android.server.tare.InternalResourceService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }
    }

    private final class LocalService implements com.android.server.tare.EconomyManagerInternal {
        private static final long FOREVER_MS = 851472000000L;

        private LocalService() {
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public void registerAffordabilityChangeListener(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener affordabilityChangeListener, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
            if (!com.android.server.tare.InternalResourceService.this.isTareSupported() || com.android.server.tare.InternalResourceService.this.isSystem(i, str)) {
                return;
            }
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                com.android.server.tare.InternalResourceService.this.mAgent.registerAffordabilityChangeListenerLocked(i, str, affordabilityChangeListener, actionBill);
            }
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public void unregisterAffordabilityChangeListener(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener affordabilityChangeListener, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
            if (com.android.server.tare.InternalResourceService.this.isSystem(i, str)) {
                return;
            }
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                com.android.server.tare.InternalResourceService.this.mAgent.unregisterAffordabilityChangeListenerLocked(i, str, affordabilityChangeListener, actionBill);
            }
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public void registerTareStateChangeListener(@android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.TareStateChangeListener tareStateChangeListener, int i) {
            if (!com.android.server.tare.InternalResourceService.this.isTareSupported()) {
                return;
            }
            synchronized (com.android.server.tare.InternalResourceService.this.mStateChangeListeners) {
                try {
                    if (com.android.server.tare.InternalResourceService.this.mStateChangeListeners.add(i, tareStateChangeListener)) {
                        com.android.server.tare.InternalResourceService.this.mHandler.obtainMessage(4, i, 0, tareStateChangeListener).sendToTarget();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public void unregisterTareStateChangeListener(@android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.TareStateChangeListener tareStateChangeListener) {
            synchronized (com.android.server.tare.InternalResourceService.this.mStateChangeListeners) {
                try {
                    for (int size = com.android.server.tare.InternalResourceService.this.mStateChangeListeners.size() - 1; size >= 0; size--) {
                        com.android.server.tare.InternalResourceService.this.mStateChangeListeners.get(com.android.server.tare.InternalResourceService.this.mStateChangeListeners.keyAt(size)).remove(tareStateChangeListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public boolean canPayFor(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
            boolean z = true;
            if (com.android.server.tare.InternalResourceService.this.mEnabledMode == 0 || com.android.server.tare.InternalResourceService.this.isVip(i, str)) {
                return true;
            }
            java.util.List<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> anticipatedActions = actionBill.getAnticipatedActions();
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                long j = 0;
                for (int i2 = 0; i2 < anticipatedActions.size(); i2++) {
                    try {
                        com.android.server.tare.EconomyManagerInternal.AnticipatedAction anticipatedAction = anticipatedActions.get(i2);
                        com.android.server.tare.EconomicPolicy.Cost costOfAction = com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getCostOfAction(anticipatedAction.actionId, i, str);
                        j += (costOfAction.price * anticipatedAction.numInstantaneousCalls) + (costOfAction.price * (anticipatedAction.ongoingDurationMs / 1000));
                    } finally {
                    }
                }
                if (com.android.server.tare.InternalResourceService.this.mAgent.getBalanceLocked(i, str) < j || com.android.server.tare.InternalResourceService.this.mScribe.getRemainingConsumableCakesLocked() < j) {
                    z = false;
                }
            }
            return z;
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public long getMaxDurationMs(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
            if (com.android.server.tare.InternalResourceService.this.mEnabledMode == 0 || com.android.server.tare.InternalResourceService.this.isVip(i, str)) {
                return FOREVER_MS;
            }
            java.util.List<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> anticipatedActions = actionBill.getAnticipatedActions();
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                long j = 0;
                for (int i2 = 0; i2 < anticipatedActions.size(); i2++) {
                    try {
                        j += com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getCostOfAction(anticipatedActions.get(i2).actionId, i, str).price;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (j == 0) {
                    return FOREVER_MS;
                }
                return (java.lang.Math.min(com.android.server.tare.InternalResourceService.this.mAgent.getBalanceLocked(i, str), com.android.server.tare.InternalResourceService.this.mScribe.getRemainingConsumableCakesLocked()) * 1000) / j;
            }
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public int getEnabledMode() {
            return com.android.server.tare.InternalResourceService.this.mEnabledMode;
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public int getEnabledMode(int i) {
            return com.android.server.tare.InternalResourceService.this.getEnabledMode(i);
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public void noteInstantaneousEvent(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2) {
            if (com.android.server.tare.InternalResourceService.this.mEnabledMode == 0) {
                return;
            }
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                com.android.server.tare.InternalResourceService.this.mAgent.noteInstantaneousEventLocked(i, str, i2, str2);
            }
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public void noteOngoingEventStarted(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2) {
            if (com.android.server.tare.InternalResourceService.this.mEnabledMode == 0) {
                return;
            }
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                com.android.server.tare.InternalResourceService.this.mAgent.noteOngoingEventLocked(i, str, i2, str2, android.os.SystemClock.elapsedRealtime());
            }
        }

        @Override // com.android.server.tare.EconomyManagerInternal
        public void noteOngoingEventStopped(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2) {
            if (com.android.server.tare.InternalResourceService.this.mEnabledMode == 0) {
                return;
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                com.android.server.tare.InternalResourceService.this.mAgent.stopOngoingActionLocked(i, str, i2, str2, elapsedRealtime, currentTimeMillis);
            }
        }
    }

    private class ConfigObserver extends android.database.ContentObserver implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private static final boolean DEFAULT_ENABLE_TIP3 = true;
        private static final java.lang.String KEY_ENABLE_TIP3 = "enable_tip3";
        private static final java.lang.String KEY_TARGET_BACKGROUND_BATTERY_LIFE_HOURS = "target_bg_battery_life_hrs";
        public boolean ENABLE_TIP3;
        private final android.content.ContentResolver mContentResolver;

        ConfigObserver(android.os.Handler handler, android.content.Context context) {
            super(handler);
            this.ENABLE_TIP3 = true;
            this.mContentResolver = context.getContentResolver();
        }

        public void start() {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("tare", com.android.server.tare.TareHandlerThread.getExecutor(), this);
            this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("enable_tare"), false, this);
            this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("tare_alarm_manager_constants"), false, this);
            this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("tare_job_scheduler_constants"), false, this);
            onPropertiesChanged(getAllDeviceConfigProperties());
            updateEnabledStatus();
        }

        @android.annotation.NonNull
        android.provider.DeviceConfig.Properties getAllDeviceConfigProperties() {
            return android.provider.DeviceConfig.getProperties("tare", new java.lang.String[0]);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (uri.equals(android.provider.Settings.Global.getUriFor("enable_tare"))) {
                updateEnabledStatus();
            } else if (uri.equals(android.provider.Settings.Global.getUriFor("tare_alarm_manager_constants")) || uri.equals(android.provider.Settings.Global.getUriFor("tare_job_scheduler_constants"))) {
                updateEconomicPolicy();
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            char c;
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                try {
                    boolean z = false;
                    for (java.lang.String str : properties.getKeyset()) {
                        if (str != null) {
                            switch (str.hashCode()) {
                                case -1428824012:
                                    if (str.equals(KEY_ENABLE_TIP3)) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -300584602:
                                    if (str.equals("enable_tare_mode")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1536945124:
                                    if (str.equals(KEY_TARGET_BACKGROUND_BATTERY_LIFE_HOURS)) {
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
                                    updateEnabledStatus();
                                    break;
                                case 1:
                                    this.ENABLE_TIP3 = properties.getBoolean(str, true);
                                    break;
                                case 2:
                                    synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                                        com.android.server.tare.InternalResourceService.this.mTargetBackgroundBatteryLifeHours = properties.getInt(str, com.android.server.tare.InternalResourceService.this.mDefaultTargetBackgroundBatteryLifeHours);
                                        com.android.server.tare.InternalResourceService.this.maybeAdjustDesiredStockLevelLocked();
                                    }
                                    break;
                                default:
                                    if (!z && (str.startsWith("am") || str.startsWith("js") || str.startsWith(com.android.server.power.LowPowerStandbyController.DeviceConfigWrapper.FEATURE_FLAG_ENABLE_POLICY))) {
                                        updateEconomicPolicy();
                                        z = true;
                                    }
                                    break;
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateEnabledStatus() {
            int i;
            int i2 = android.provider.DeviceConfig.getInt("tare", "enable_tare_mode", 0);
            if (com.android.server.tare.InternalResourceService.this.isTareSupported()) {
                i = android.provider.Settings.Global.getInt(this.mContentResolver, "enable_tare", i2);
            } else {
                i = 0;
            }
            boolean z = true;
            if (i != 0 && i != 1 && i != 2) {
                i = 0;
            }
            if (com.android.server.tare.InternalResourceService.this.mEnabledMode != i) {
                if (com.android.server.tare.InternalResourceService.this.mEnabledMode != 0 && i != 0) {
                    z = false;
                }
                com.android.server.tare.InternalResourceService.this.mEnabledMode = i;
                if (z) {
                    if (com.android.server.tare.InternalResourceService.this.mEnabledMode != 0) {
                        com.android.server.tare.InternalResourceService.this.setupEverything();
                    } else {
                        com.android.server.tare.InternalResourceService.this.tearDownEverything();
                    }
                }
                com.android.server.tare.InternalResourceService.this.mHandler.obtainMessage(3, 805306368, 0).sendToTarget();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x00a0 A[Catch: all -> 0x0076, TryCatch #0 {all -> 0x0076, blocks: (B:4:0x0007, B:6:0x0042, B:8:0x004c, B:10:0x0067, B:13:0x008b, B:15:0x00a0, B:16:0x0078, B:17:0x00b0), top: B:3:0x0007 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void updateEconomicPolicy() {
            int enabledPolicyIds;
            synchronized (com.android.server.tare.InternalResourceService.this.mLock) {
                try {
                    long minSatiatedConsumptionLimit = com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getMinSatiatedConsumptionLimit();
                    long maxSatiatedConsumptionLimit = com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getMaxSatiatedConsumptionLimit();
                    int enabledPolicyIds2 = com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getEnabledPolicyIds();
                    com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.tearDown();
                    com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy = new com.android.server.tare.CompleteEconomicPolicy(com.android.server.tare.InternalResourceService.this);
                    if (com.android.server.tare.InternalResourceService.this.mEnabledMode != 0 && com.android.server.tare.InternalResourceService.this.mBootPhase >= 600) {
                        com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.setup(getAllDeviceConfigProperties());
                        if (minSatiatedConsumptionLimit == com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getMinSatiatedConsumptionLimit()) {
                            if (maxSatiatedConsumptionLimit != com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getMaxSatiatedConsumptionLimit()) {
                            }
                            com.android.server.tare.InternalResourceService.this.mAgent.onPricingChangedLocked();
                            enabledPolicyIds = com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getEnabledPolicyIds();
                            if (enabledPolicyIds2 != enabledPolicyIds) {
                                com.android.server.tare.InternalResourceService.this.mHandler.obtainMessage(3, enabledPolicyIds ^ enabledPolicyIds2, 0).sendToTarget();
                            }
                        }
                        com.android.server.tare.InternalResourceService.this.mScribe.setConsumptionLimitLocked(com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getInitialSatiatedConsumptionLimit());
                        com.android.server.tare.InternalResourceService.this.mAgent.onPricingChangedLocked();
                        enabledPolicyIds = com.android.server.tare.InternalResourceService.this.mCompleteEconomicPolicy.getEnabledPolicyIds();
                        if (enabledPolicyIds2 != enabledPolicyIds) {
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    int executeClearVip(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                android.util.SparseSetArray<java.lang.String> sparseSetArray = new android.util.SparseSetArray<>();
                for (int numMaps = this.mVipOverrides.numMaps() - 1; numMaps >= 0; numMaps--) {
                    int keyAt = this.mVipOverrides.keyAt(numMaps);
                    for (int numElementsForKeyAt = this.mVipOverrides.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                        sparseSetArray.add(keyAt, (java.lang.String) this.mVipOverrides.keyAt(numMaps, numElementsForKeyAt));
                    }
                }
                this.mVipOverrides.clear();
                if (this.mEnabledMode != 0) {
                    this.mAgent.onVipStatusChangedLocked(sparseSetArray);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println("Cleared all VIP statuses");
        return 0;
    }

    int executeSetVip(@android.annotation.NonNull java.io.PrintWriter printWriter, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Boolean bool) {
        boolean z;
        synchronized (this.mLock) {
            try {
                boolean isVip = isVip(i, str);
                if (bool == null) {
                    this.mVipOverrides.delete(i, str);
                } else {
                    this.mVipOverrides.add(i, str, bool);
                }
                z = isVip(i, str) != isVip;
                if (this.mEnabledMode != 0 && z) {
                    this.mAgent.onVipStatusChangedLocked(i, str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println(com.android.server.tare.TareUtils.appToString(i, str) + " VIP status set to " + bool + ". Final VIP state changed? " + z);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dumpHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Resource Economy (economy) dump options:");
        printWriter.println("  [-h|--help] [package] ...");
        printWriter.println("    -h | --help: print this help");
        printWriter.println("  [package] is an optional package name to limit the output to.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(android.util.IndentingPrintWriter indentingPrintWriter, boolean z) {
        if (!isTareSupported()) {
            indentingPrintWriter.print("Unsupported by device");
            return;
        }
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.print("Enabled mode: ");
                indentingPrintWriter.println(android.app.tare.EconomyManager.enabledModeToString(this.mEnabledMode));
                indentingPrintWriter.print("Current battery level: ");
                indentingPrintWriter.println(this.mCurrentBatteryLevel);
                long consumptionLimitLocked = getConsumptionLimitLocked();
                indentingPrintWriter.print("Consumption limit (current/initial-satiated/current-satiated): ");
                indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(consumptionLimitLocked));
                indentingPrintWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(this.mCompleteEconomicPolicy.getInitialSatiatedConsumptionLimit()));
                indentingPrintWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                indentingPrintWriter.println(com.android.server.tare.TareUtils.cakeToString(this.mScribe.getSatiatedConsumptionLimitLocked()));
                indentingPrintWriter.print("Target bg battery life (hours): ");
                indentingPrintWriter.print(this.mTargetBackgroundBatteryLifeHours);
                indentingPrintWriter.print(" (");
                indentingPrintWriter.print(java.lang.String.format("%.2f", java.lang.Float.valueOf(100.0f / this.mTargetBackgroundBatteryLifeHours)));
                indentingPrintWriter.println("%/hr)");
                long remainingConsumableCakesLocked = this.mScribe.getRemainingConsumableCakesLocked();
                indentingPrintWriter.print("Goods remaining: ");
                indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(remainingConsumableCakesLocked));
                indentingPrintWriter.print(" (");
                indentingPrintWriter.print(java.lang.String.format("%.2f", java.lang.Float.valueOf((remainingConsumableCakesLocked * 100.0f) / consumptionLimitLocked)));
                indentingPrintWriter.println("% of current limit)");
                indentingPrintWriter.print("Device wealth: ");
                indentingPrintWriter.println(com.android.server.tare.TareUtils.cakeToString(this.mScribe.getCakesInCirculationForLoggingLocked()));
                indentingPrintWriter.println();
                indentingPrintWriter.print("Exempted apps", this.mExemptedApps);
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Wellbeing app=");
                indentingPrintWriter.println(this.mWellbeingPackage == null ? com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : this.mWellbeingPackage);
                indentingPrintWriter.println();
                indentingPrintWriter.print("VIPs:");
                indentingPrintWriter.increaseIndent();
                boolean z2 = false;
                for (int i = 0; i < this.mVipOverrides.numMaps(); i++) {
                    int keyAt = this.mVipOverrides.keyAt(i);
                    int i2 = 0;
                    while (i2 < this.mVipOverrides.numElementsForKeyAt(i)) {
                        java.lang.String str = (java.lang.String) this.mVipOverrides.keyAt(i, i2);
                        indentingPrintWriter.println();
                        indentingPrintWriter.print(com.android.server.tare.TareUtils.appToString(keyAt, str));
                        indentingPrintWriter.print("=");
                        indentingPrintWriter.print(this.mVipOverrides.valueAt(i, i2));
                        i2++;
                        z2 = true;
                    }
                }
                if (z2) {
                    indentingPrintWriter.println();
                } else {
                    indentingPrintWriter.print(" None");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Temp VIPs:");
                indentingPrintWriter.increaseIndent();
                boolean z3 = false;
                for (int i3 = 0; i3 < this.mTemporaryVips.numMaps(); i3++) {
                    int keyAt2 = this.mTemporaryVips.keyAt(i3);
                    int i4 = 0;
                    while (i4 < this.mTemporaryVips.numElementsForKeyAt(i3)) {
                        java.lang.String str2 = (java.lang.String) this.mTemporaryVips.keyAt(i3, i4);
                        indentingPrintWriter.println();
                        indentingPrintWriter.print(com.android.server.tare.TareUtils.appToString(keyAt2, str2));
                        indentingPrintWriter.print("=");
                        indentingPrintWriter.print(this.mTemporaryVips.valueAt(i3, i4));
                        i4++;
                        z3 = true;
                    }
                }
                if (z3) {
                    indentingPrintWriter.println();
                } else {
                    indentingPrintWriter.print(" None");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Installers:");
                indentingPrintWriter.increaseIndent();
                for (int i5 = 0; i5 < this.mInstallers.numMaps(); i5++) {
                    int keyAt3 = this.mInstallers.keyAt(i5);
                    for (int i6 = 0; i6 < this.mInstallers.numElementsForKeyAt(i5); i6++) {
                        indentingPrintWriter.print(com.android.server.tare.TareUtils.appToString(keyAt3, (java.lang.String) this.mInstallers.keyAt(i5, i6)));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.print(((android.util.ArraySet) this.mInstallers.valueAt(i5, i6)).size());
                        indentingPrintWriter.println(" apps");
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                this.mCompleteEconomicPolicy.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                this.mScribe.dumpLocked(indentingPrintWriter, z);
                indentingPrintWriter.println();
                this.mAgent.dumpLocked(indentingPrintWriter);
                indentingPrintWriter.println();
                this.mAnalyst.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Interesting apps:");
                indentingPrintWriter.increaseIndent();
                boolean z4 = false;
                for (int i7 = 0; i7 < this.mPkgCache.numMaps(); i7++) {
                    for (int i8 = 0; i8 < this.mPkgCache.numElementsForKeyAt(i7); i8++) {
                        com.android.server.tare.InstalledPackageInfo installedPackageInfo = (com.android.server.tare.InstalledPackageInfo) this.mPkgCache.valueAt(i7, i8);
                        if (installedPackageInfo.hasCode && installedPackageInfo.isHeadlessSystemApp && !android.os.UserHandle.isCore(installedPackageInfo.uid)) {
                            indentingPrintWriter.println();
                            indentingPrintWriter.print(installedPackageInfo);
                            z4 = true;
                        }
                    }
                }
                if (z4) {
                    indentingPrintWriter.println();
                } else {
                    indentingPrintWriter.print(" None");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
