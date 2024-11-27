package com.android.server.am;

/* loaded from: classes.dex */
public final class AppRestrictionController {
    private static final java.lang.String APP_RESTRICTION_SETTINGS_DIRNAME = "apprestriction";
    private static final java.lang.String APP_RESTRICTION_SETTINGS_FILENAME = "settings.xml";
    private static final java.lang.String ATTR_CUR_LEVEL = "curlevel";
    private static final java.lang.String ATTR_LEVEL_TS = "levelts";
    private static final java.lang.String ATTR_PACKAGE = "package";
    private static final java.lang.String ATTR_REASON = "reason";
    private static final java.lang.String ATTR_UID = "uid";
    static final boolean DEBUG_BG_RESTRICTION_CONTROLLER = false;
    static final java.lang.String DEVICE_CONFIG_SUBNAMESPACE_PREFIX = "bg_";
    private static final boolean ENABLE_SHOW_FGS_MANAGER_ACTION_ON_BG_RESTRICTION = false;
    private static final boolean ENABLE_SHOW_FOREGROUND_SERVICE_MANAGER = true;
    private static final java.lang.String[] ROLES_IN_INTEREST = {"android.app.role.DIALER", "android.app.role.EMERGENCY"};
    static final int STOCK_PM_FLAGS = 819200;
    static final java.lang.String TAG = "ActivityManager";
    private static final java.lang.String TAG_SETTINGS = "settings";
    static final int TRACKER_TYPE_BATTERY = 1;
    static final int TRACKER_TYPE_BATTERY_EXEMPTION = 2;
    static final int TRACKER_TYPE_BIND_SERVICE_EVENTS = 7;
    static final int TRACKER_TYPE_BROADCAST_EVENTS = 6;
    static final int TRACKER_TYPE_FGS = 3;
    static final int TRACKER_TYPE_MEDIA_SESSION = 4;
    static final int TRACKER_TYPE_PERMISSION = 5;
    static final int TRACKER_TYPE_UNKNOWN = 0;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private final android.util.SparseArrayMap<java.lang.String, java.lang.Runnable> mActiveUids;
    final com.android.server.am.ActivityManagerService mActivityManagerService;
    private final com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener mAppIdleStateChangeListener;
    private final java.util.ArrayList<com.android.server.am.BaseAppStateTracker> mAppStateTrackers;
    private final com.android.server.AppStateTracker.BackgroundRestrictedAppListener mBackgroundRestrictionListener;
    private final android.os.HandlerExecutor mBgExecutor;
    private final com.android.server.am.AppRestrictionController.BgHandler mBgHandler;
    private final android.os.HandlerThread mBgHandlerThread;
    android.util.ArraySet<java.lang.String> mBgRestrictionExemptioFromSysConfig;
    private final android.content.BroadcastReceiver mBootReceiver;
    private final android.content.BroadcastReceiver mBroadcastReceiver;

    @com.android.internal.annotations.GuardedBy({"mCarrierPrivilegedLock"})
    private final android.util.SparseArray<java.util.Set<java.lang.String>> mCarrierPrivilegedApps;
    private final java.lang.Object mCarrierPrivilegedLock;
    private volatile java.util.ArrayList<com.android.server.am.AppRestrictionController.PhoneCarrierPrivilegesCallback> mCarrierPrivilegesCallbacks;
    private final com.android.server.am.AppRestrictionController.ConstantsObserver mConstantsObserver;
    private final android.content.Context mContext;
    private int[] mDeviceIdleAllowlist;
    private int[] mDeviceIdleExceptIdleAllowlist;
    private final com.android.server.am.AppRestrictionController.TrackerInfo mEmptyTrackerInfo;
    private final com.android.server.am.AppRestrictionController.Injector mInjector;
    private final java.lang.Object mLock;
    private final com.android.server.am.AppRestrictionController.NotificationHelper mNotificationHelper;
    private final java.util.concurrent.CopyOnWriteArraySet<android.app.ActivityManagerInternal.AppBackgroundRestrictionListener> mRestrictionListeners;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.am.AppRestrictionController.RestrictionSettings mRestrictionSettings;
    private final java.util.concurrent.atomic.AtomicBoolean mRestrictionSettingsXmlLoaded;
    private final android.app.role.OnRoleHoldersChangedListener mRoleHolderChangedListener;
    private final java.lang.Object mSettingsLock;
    private final android.util.ArraySet<java.lang.Integer> mSystemDeviceIdleAllowlist;
    private final android.util.ArraySet<java.lang.Integer> mSystemDeviceIdleExceptIdleAllowlist;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<java.lang.String, java.lang.Boolean> mSystemModulesCache;
    private final java.util.ArrayList<java.lang.Runnable> mTmpRunnables;
    private final android.app.IUidObserver mUidObserver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.ArrayList<java.lang.String>> mUidRolesMapping;

    @interface TrackerType {
    }

    interface UidBatteryUsageProvider {
        @android.annotation.NonNull
        com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage getUidBatteryUsage(int i);
    }

    final class RestrictionSettings {

        @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
        final android.util.SparseArrayMap<java.lang.String, com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings> mRestrictionLevels = new android.util.SparseArrayMap<>();

        RestrictionSettings() {
        }

        final class PkgSettings {
            private long[] mLastNotificationShownTime;
            private long mLevelChangeTime;
            private int[] mNotificationId;
            private final java.lang.String mPackageName;
            private int mReason;
            private final int mUid;
            private int mLastRestrictionLevel = 0;
            private int mCurrentRestrictionLevel = 0;

            PkgSettings(java.lang.String str, int i) {
                this.mPackageName = str;
                this.mUid = i;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            int update(int i, int i2, int i3) {
                if (i != this.mCurrentRestrictionLevel) {
                    this.mLastRestrictionLevel = this.mCurrentRestrictionLevel;
                    this.mCurrentRestrictionLevel = i;
                    this.mLevelChangeTime = com.android.server.am.AppRestrictionController.this.mInjector.currentTimeMillis();
                    this.mReason = (i2 & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK) | (i3 & 255);
                    com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(1, this.mUid, i, this.mPackageName).sendToTarget();
                }
                return this.mLastRestrictionLevel;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            public java.lang.String toString() {
                java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
                sb.append("RestrictionLevel{");
                sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
                sb.append(':');
                sb.append(this.mPackageName);
                sb.append('/');
                sb.append(android.os.UserHandle.formatUid(this.mUid));
                sb.append('}');
                sb.append(' ');
                sb.append(android.app.ActivityManager.restrictionLevelToName(this.mCurrentRestrictionLevel));
                sb.append('(');
                sb.append(android.app.usage.UsageStatsManager.reasonToString(this.mReason));
                sb.append(')');
                return sb.toString();
            }

            void dump(java.io.PrintWriter printWriter, long j) {
                synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                    try {
                        printWriter.print(toString());
                        if (this.mLastRestrictionLevel != 0) {
                            printWriter.print('/');
                            printWriter.print(android.app.ActivityManager.restrictionLevelToName(this.mLastRestrictionLevel));
                        }
                        printWriter.print(" levelChange=");
                        android.util.TimeUtils.formatDuration(this.mLevelChangeTime - j, printWriter);
                        if (this.mLastNotificationShownTime != null) {
                            for (int i = 0; i < this.mLastNotificationShownTime.length; i++) {
                                if (this.mLastNotificationShownTime[i] > 0) {
                                    printWriter.print(" lastNoti(");
                                    com.android.server.am.AppRestrictionController.NotificationHelper unused = com.android.server.am.AppRestrictionController.this.mNotificationHelper;
                                    printWriter.print(com.android.server.am.AppRestrictionController.NotificationHelper.notificationTypeToString(i));
                                    printWriter.print(")=");
                                    android.util.TimeUtils.formatDuration(this.mLastNotificationShownTime[i] - j, printWriter);
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                printWriter.print(" effectiveExemption=");
                printWriter.print(android.os.PowerExemptionManager.reasonCodeToString(com.android.server.am.AppRestrictionController.this.getBackgroundRestrictionExemptionReason(this.mUid)));
            }

            java.lang.String getPackageName() {
                return this.mPackageName;
            }

            int getUid() {
                return this.mUid;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            int getCurrentRestrictionLevel() {
                return this.mCurrentRestrictionLevel;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            int getLastRestrictionLevel() {
                return this.mLastRestrictionLevel;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            int getReason() {
                return this.mReason;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            long getLastNotificationTime(int i) {
                if (this.mLastNotificationShownTime == null) {
                    return 0L;
                }
                return this.mLastNotificationShownTime[i];
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            void setLastNotificationTime(int i, long j) {
                setLastNotificationTime(i, j, true);
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            @com.android.internal.annotations.VisibleForTesting
            void setLastNotificationTime(int i, long j, boolean z) {
                if (this.mLastNotificationShownTime == null) {
                    this.mLastNotificationShownTime = new long[2];
                }
                this.mLastNotificationShownTime[i] = j;
                if (z && com.android.server.am.AppRestrictionController.this.mRestrictionSettingsXmlLoaded.get()) {
                    com.android.server.am.AppRestrictionController.RestrictionSettings.this.schedulePersistToXml(android.os.UserHandle.getUserId(this.mUid));
                }
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            int getNotificationId(int i) {
                if (this.mNotificationId == null) {
                    return 0;
                }
                return this.mNotificationId[i];
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            void setNotificationId(int i, int i2) {
                if (this.mNotificationId == null) {
                    this.mNotificationId = new int[2];
                }
                this.mNotificationId[i] = i2;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            @com.android.internal.annotations.VisibleForTesting
            void setLevelChangeTime(long j) {
                this.mLevelChangeTime = j;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            public java.lang.Object clone() {
                com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings = com.android.server.am.AppRestrictionController.RestrictionSettings.this.new PkgSettings(this.mPackageName, this.mUid);
                pkgSettings.mCurrentRestrictionLevel = this.mCurrentRestrictionLevel;
                pkgSettings.mLastRestrictionLevel = this.mLastRestrictionLevel;
                pkgSettings.mLevelChangeTime = this.mLevelChangeTime;
                pkgSettings.mReason = this.mReason;
                if (this.mLastNotificationShownTime != null) {
                    pkgSettings.mLastNotificationShownTime = java.util.Arrays.copyOf(this.mLastNotificationShownTime, this.mLastNotificationShownTime.length);
                }
                if (this.mNotificationId != null) {
                    pkgSettings.mNotificationId = java.util.Arrays.copyOf(this.mNotificationId, this.mNotificationId.length);
                }
                return pkgSettings;
            }

            @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
            public boolean equals(java.lang.Object obj) {
                if (obj == this) {
                    return true;
                }
                if (obj == null || !(obj instanceof com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings)) {
                    return false;
                }
                com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings = (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) obj;
                if (pkgSettings.mUid == this.mUid && pkgSettings.mCurrentRestrictionLevel == this.mCurrentRestrictionLevel && pkgSettings.mLastRestrictionLevel == this.mLastRestrictionLevel && pkgSettings.mLevelChangeTime == this.mLevelChangeTime && pkgSettings.mReason == this.mReason && android.text.TextUtils.equals(pkgSettings.mPackageName, this.mPackageName) && java.util.Arrays.equals(pkgSettings.mLastNotificationShownTime, this.mLastNotificationShownTime) && java.util.Arrays.equals(pkgSettings.mNotificationId, this.mNotificationId)) {
                    return true;
                }
                return false;
            }
        }

        int update(java.lang.String str, int i, int i2, int i3, int i4) {
            int update;
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings restrictionSettingsLocked = getRestrictionSettingsLocked(i, str);
                    if (restrictionSettingsLocked == null) {
                        restrictionSettingsLocked = new com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings(str, i);
                        this.mRestrictionLevels.add(i, str, restrictionSettingsLocked);
                    }
                    update = restrictionSettingsLocked.update(i2, i3, i4);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return update;
        }

        int getReason(java.lang.String str, int i) {
            int reason;
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings = (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) this.mRestrictionLevels.get(i, str);
                    reason = pkgSettings != null ? pkgSettings.getReason() : 256;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return reason;
        }

        int getRestrictionLevel(int i) {
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    int indexOfKey = this.mRestrictionLevels.indexOfKey(i);
                    if (indexOfKey < 0) {
                        return 0;
                    }
                    int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(indexOfKey);
                    if (numElementsForKeyAt == 0) {
                        return 0;
                    }
                    int i2 = 0;
                    for (int i3 = 0; i3 < numElementsForKeyAt; i3++) {
                        com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings = (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) this.mRestrictionLevels.valueAt(indexOfKey, i3);
                        if (pkgSettings != null) {
                            int currentRestrictionLevel = pkgSettings.getCurrentRestrictionLevel();
                            if (i2 != 0) {
                                currentRestrictionLevel = java.lang.Math.min(i2, currentRestrictionLevel);
                            }
                            i2 = currentRestrictionLevel;
                        }
                    }
                    return i2;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        int getRestrictionLevel(int i, java.lang.String str) {
            int restrictionLevel;
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings restrictionSettingsLocked = getRestrictionSettingsLocked(i, str);
                    restrictionLevel = restrictionSettingsLocked == null ? getRestrictionLevel(i) : restrictionSettingsLocked.getCurrentRestrictionLevel();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return restrictionLevel;
        }

        int getRestrictionLevel(java.lang.String str, int i) {
            return getRestrictionLevel(com.android.server.am.AppRestrictionController.this.mInjector.getPackageManagerInternal().getPackageUid(str, 819200L, i), str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getLastRestrictionLevel(int i, java.lang.String str) {
            int lastRestrictionLevel;
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings = (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) this.mRestrictionLevels.get(i, str);
                lastRestrictionLevel = pkgSettings == null ? 0 : pkgSettings.getLastRestrictionLevel();
            }
            return lastRestrictionLevel;
        }

        @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
        void forEachPackageInUidLocked(int i, @android.annotation.NonNull com.android.internal.util.function.TriConsumer<java.lang.String, java.lang.Integer, java.lang.Integer> triConsumer) {
            int indexOfKey = this.mRestrictionLevels.indexOfKey(i);
            if (indexOfKey < 0) {
                return;
            }
            int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(indexOfKey);
            for (int i2 = 0; i2 < numElementsForKeyAt; i2++) {
                com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings = (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) this.mRestrictionLevels.valueAt(indexOfKey, i2);
                triConsumer.accept((java.lang.String) this.mRestrictionLevels.keyAt(indexOfKey, i2), java.lang.Integer.valueOf(pkgSettings.getCurrentRestrictionLevel()), java.lang.Integer.valueOf(pkgSettings.getReason()));
            }
        }

        @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
        void forEachUidLocked(@android.annotation.NonNull java.util.function.Consumer<java.lang.Integer> consumer) {
            for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                consumer.accept(java.lang.Integer.valueOf(this.mRestrictionLevels.keyAt(numMaps)));
            }
        }

        @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
        com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings getRestrictionSettingsLocked(int i, java.lang.String str) {
            return (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) this.mRestrictionLevels.get(i, str);
        }

        void removeUser(int i) {
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                        if (android.os.UserHandle.getUserId(this.mRestrictionLevels.keyAt(numMaps)) == i) {
                            this.mRestrictionLevels.deleteAt(numMaps);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void removePackage(java.lang.String str, int i) {
            removePackage(str, i, true);
        }

        void removePackage(java.lang.String str, int i, boolean z) {
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    int indexOfKey = this.mRestrictionLevels.indexOfKey(i);
                    this.mRestrictionLevels.delete(i, str);
                    if (indexOfKey >= 0 && this.mRestrictionLevels.numElementsForKeyAt(indexOfKey) == 0) {
                        this.mRestrictionLevels.deleteAt(indexOfKey);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z && com.android.server.am.AppRestrictionController.this.mRestrictionSettingsXmlLoaded.get()) {
                schedulePersistToXml(android.os.UserHandle.getUserId(i));
            }
        }

        void removeUid(int i) {
            removeUid(i, true);
        }

        void removeUid(int i, boolean z) {
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                this.mRestrictionLevels.delete(i);
            }
            if (z && com.android.server.am.AppRestrictionController.this.mRestrictionSettingsXmlLoaded.get()) {
                schedulePersistToXml(android.os.UserHandle.getUserId(i));
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void reset() {
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                        this.mRestrictionLevels.deleteAt(numMaps);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void resetToDefault() {
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                this.mRestrictionLevels.forEach(new java.util.function.Consumer() { // from class: com.android.server.am.AppRestrictionController$RestrictionSettings$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.am.AppRestrictionController.RestrictionSettings.lambda$resetToDefault$0((com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) obj);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$resetToDefault$0(com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings) {
            pkgSettings.mCurrentRestrictionLevel = 0;
            pkgSettings.mLastRestrictionLevel = 0;
            pkgSettings.mLevelChangeTime = 0L;
            pkgSettings.mReason = 256;
            if (pkgSettings.mLastNotificationShownTime != null) {
                for (int i = 0; i < pkgSettings.mLastNotificationShownTime.length; i++) {
                    pkgSettings.mLastNotificationShownTime[i] = 0;
                }
            }
        }

        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                this.mRestrictionLevels.forEach(new java.util.function.Consumer() { // from class: com.android.server.am.AppRestrictionController$RestrictionSettings$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        arrayList.add((com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) obj);
                    }
                });
            }
            java.util.Collections.sort(arrayList, java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.server.am.AppRestrictionController$RestrictionSettings$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    return ((com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) obj).getUid();
                }
            }));
            long currentTimeMillis = com.android.server.am.AppRestrictionController.this.mInjector.currentTimeMillis();
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                printWriter.print(str);
                printWriter.print('#');
                printWriter.print(i);
                printWriter.print(' ');
                ((com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) arrayList.get(i)).dump(printWriter, currentTimeMillis);
                printWriter.println();
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void schedulePersistToXml(int i) {
            com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(11, i, 0).sendToTarget();
        }

        @com.android.internal.annotations.VisibleForTesting
        void scheduleLoadFromXml() {
            com.android.server.am.AppRestrictionController.this.mBgHandler.sendEmptyMessage(10);
        }

        @com.android.internal.annotations.VisibleForTesting
        java.io.File getXmlFileNameForUser(int i) {
            return new java.io.File(new java.io.File(com.android.server.am.AppRestrictionController.this.mInjector.getDataSystemDeDirectory(i), com.android.server.am.AppRestrictionController.APP_RESTRICTION_SETTINGS_DIRNAME), com.android.server.am.AppRestrictionController.APP_RESTRICTION_SETTINGS_FILENAME);
        }

        @com.android.internal.annotations.VisibleForTesting
        void loadFromXml(boolean z) {
            for (int i : com.android.server.am.AppRestrictionController.this.mInjector.getUserManagerInternal().getUserIds()) {
                loadFromXml(i, z);
            }
            com.android.server.am.AppRestrictionController.this.mRestrictionSettingsXmlLoaded.set(true);
        }

        void loadFromXml(int i, boolean z) {
            java.io.File xmlFileNameForUser = getXmlFileNameForUser(i);
            if (!xmlFileNameForUser.exists()) {
                return;
            }
            long[] jArr = new long[2];
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(xmlFileNameForUser);
                try {
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next != 1) {
                            if (next == 2) {
                                java.lang.String name = resolvePullParser.getName();
                                if (!com.android.server.am.AppRestrictionController.TAG_SETTINGS.equals(name)) {
                                    android.util.Slog.w(com.android.server.am.AppRestrictionController.TAG, "Unexpected tag name: " + name);
                                } else {
                                    loadOneFromXml(resolvePullParser, elapsedRealtime, jArr, z);
                                }
                            }
                        } else {
                            fileInputStream.close();
                            return;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void loadOneFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, long j, long[] jArr, boolean z) {
            char c;
            for (int i = 0; i < jArr.length; i++) {
                jArr[i] = 0;
            }
            int i2 = 0;
            int i3 = 0;
            int i4 = 256;
            long j2 = 0;
            java.lang.String str = null;
            for (int i5 = 0; i5 < typedXmlPullParser.getAttributeCount(); i5++) {
                try {
                    java.lang.String attributeName = typedXmlPullParser.getAttributeName(i5);
                    java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(i5);
                    switch (attributeName.hashCode()) {
                        case -934964668:
                            if (attributeName.equals("reason")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case -807062458:
                            if (attributeName.equals("package")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 115792:
                            if (attributeName.equals("uid")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 69785859:
                            if (attributeName.equals(com.android.server.am.AppRestrictionController.ATTR_LEVEL_TS)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 569868612:
                            if (attributeName.equals(com.android.server.am.AppRestrictionController.ATTR_CUR_LEVEL)) {
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
                            i3 = java.lang.Integer.parseInt(attributeValue);
                            continue;
                        case 1:
                            str = attributeValue;
                            continue;
                        case 2:
                            i2 = java.lang.Integer.parseInt(attributeValue);
                            continue;
                        case 3:
                            j2 = java.lang.Long.parseLong(attributeValue);
                            continue;
                        case 4:
                            i4 = java.lang.Integer.parseInt(attributeValue);
                            continue;
                        default:
                            jArr[com.android.server.am.AppRestrictionController.NotificationHelper.notificationTimeAttrToType(attributeName)] = java.lang.Long.parseLong(attributeValue);
                            continue;
                    }
                } catch (java.lang.IllegalArgumentException e) {
                }
            }
            if (i3 != 0) {
                synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                    try {
                        com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings restrictionSettingsLocked = getRestrictionSettingsLocked(i3, str);
                        if (restrictionSettingsLocked == null) {
                            return;
                        }
                        for (int i6 = 0; i6 < jArr.length; i6++) {
                            if (restrictionSettingsLocked.getLastNotificationTime(i6) == 0 && jArr[i6] != 0) {
                                restrictionSettingsLocked.setLastNotificationTime(i6, jArr[i6], false);
                            }
                        }
                        if (restrictionSettingsLocked.mCurrentRestrictionLevel >= i2) {
                            return;
                        }
                        long j3 = j2;
                        int i7 = i2;
                        int appStandbyBucket = com.android.server.am.AppRestrictionController.this.mInjector.getAppStandbyInternal().getAppStandbyBucket(str, android.os.UserHandle.getUserId(i3), j, false);
                        if (z) {
                            com.android.server.am.AppRestrictionController.this.applyRestrictionLevel(str, i3, i7, com.android.server.am.AppRestrictionController.this.mEmptyTrackerInfo, appStandbyBucket, true, 65280 & i4, i4 & 255);
                        } else {
                            restrictionSettingsLocked.update(i7, 65280 & i4, i4 & 255);
                        }
                        synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                            restrictionSettingsLocked.setLevelChangeTime(j3);
                        }
                    } finally {
                    }
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void persistToXml(int i) {
            java.io.FileOutputStream fileOutputStream;
            java.io.File xmlFileNameForUser = getXmlFileNameForUser(i);
            java.io.File parentFile = xmlFileNameForUser.getParentFile();
            if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                android.util.Slog.w(com.android.server.am.AppRestrictionController.TAG, "Failed to create folder for " + i);
                return;
            }
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(xmlFileNameForUser);
            try {
                fileOutputStream = atomicFile.startWrite();
                try {
                    fileOutputStream.write(toXmlByteArray(i));
                    atomicFile.finishWrite(fileOutputStream);
                } catch (java.lang.Exception e) {
                    e = e;
                    android.util.Slog.e(com.android.server.am.AppRestrictionController.TAG, "Failed to write file " + xmlFileNameForUser, e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                }
            } catch (java.lang.Exception e2) {
                e = e2;
                fileOutputStream = null;
            }
        }

        private byte[] toXmlByteArray(int i) {
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(byteArrayOutputStream);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                        try {
                            for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                                for (int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                                    com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings = (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) this.mRestrictionLevels.valueAt(numMaps, numElementsForKeyAt);
                                    int uid = pkgSettings.getUid();
                                    if (android.os.UserHandle.getUserId(uid) == i) {
                                        resolveSerializer.startTag((java.lang.String) null, com.android.server.am.AppRestrictionController.TAG_SETTINGS);
                                        resolveSerializer.attributeInt((java.lang.String) null, "uid", uid);
                                        resolveSerializer.attribute((java.lang.String) null, "package", pkgSettings.getPackageName());
                                        resolveSerializer.attributeInt((java.lang.String) null, com.android.server.am.AppRestrictionController.ATTR_CUR_LEVEL, pkgSettings.mCurrentRestrictionLevel);
                                        resolveSerializer.attributeLong((java.lang.String) null, com.android.server.am.AppRestrictionController.ATTR_LEVEL_TS, pkgSettings.mLevelChangeTime);
                                        resolveSerializer.attributeInt((java.lang.String) null, "reason", pkgSettings.mReason);
                                        for (int i2 = 0; i2 < 2; i2++) {
                                            resolveSerializer.attributeLong((java.lang.String) null, com.android.server.am.AppRestrictionController.NotificationHelper.notificationTypeToTimeAttr(i2), pkgSettings.getLastNotificationTime(i2));
                                        }
                                        resolveSerializer.endTag((java.lang.String) null, com.android.server.am.AppRestrictionController.TAG_SETTINGS);
                                    }
                                }
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    resolveSerializer.endDocument();
                    resolveSerializer.flush();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } catch (java.io.IOException e) {
                return null;
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void removeXml() {
            for (int i : com.android.server.am.AppRestrictionController.this.mInjector.getUserManagerInternal().getUserIds()) {
                getXmlFileNameForUser(i).delete();
            }
        }

        public java.lang.Object clone() {
            com.android.server.am.AppRestrictionController.RestrictionSettings restrictionSettings = com.android.server.am.AppRestrictionController.this.new RestrictionSettings();
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                        for (int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                            restrictionSettings.mRestrictionLevels.add(this.mRestrictionLevels.keyAt(numMaps), (java.lang.String) this.mRestrictionLevels.keyAt(numMaps, numElementsForKeyAt), (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) ((com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) this.mRestrictionLevels.valueAt(numMaps, numElementsForKeyAt)).clone());
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return restrictionSettings;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof com.android.server.am.AppRestrictionController.RestrictionSettings)) {
                return false;
            }
            android.util.SparseArrayMap<java.lang.String, com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings> sparseArrayMap = ((com.android.server.am.AppRestrictionController.RestrictionSettings) obj).mRestrictionLevels;
            synchronized (com.android.server.am.AppRestrictionController.this.mSettingsLock) {
                try {
                    if (sparseArrayMap.numMaps() == this.mRestrictionLevels.numMaps()) {
                        for (int numMaps = this.mRestrictionLevels.numMaps() - 1; numMaps >= 0; numMaps--) {
                            int keyAt = this.mRestrictionLevels.keyAt(numMaps);
                            if (sparseArrayMap.numElementsForKey(keyAt) == this.mRestrictionLevels.numElementsForKeyAt(numMaps)) {
                                for (int numElementsForKeyAt = this.mRestrictionLevels.numElementsForKeyAt(numMaps) - 1; numElementsForKeyAt >= 0; numElementsForKeyAt--) {
                                    com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings pkgSettings = (com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings) this.mRestrictionLevels.valueAt(numMaps, numElementsForKeyAt);
                                    if (!pkgSettings.equals(sparseArrayMap.get(keyAt, pkgSettings.getPackageName()))) {
                                        return false;
                                    }
                                }
                            } else {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class ConstantsObserver implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        static final long DEFAULT_BG_ABUSIVE_NOTIFICATION_MINIMAL_INTERVAL_MS = 2592000000L;
        static final boolean DEFAULT_BG_AUTO_RESTRICTED_BUCKET_ON_BG_RESTRICTION = false;
        static final boolean DEFAULT_BG_AUTO_RESTRICT_ABUSIVE_APPS = true;
        static final long DEFAULT_BG_LONG_FGS_NOTIFICATION_MINIMAL_INTERVAL_MS = 2592000000L;
        static final boolean DEFAULT_BG_PROMPT_FGS_ON_LONG_RUNNING = true;
        static final boolean DEFAULT_BG_PROMPT_FGS_WITH_NOTIFICATION_ON_LONG_RUNNING = false;
        static final java.lang.String KEY_BG_ABUSIVE_NOTIFICATION_MINIMAL_INTERVAL = "bg_abusive_notification_minimal_interval";
        static final java.lang.String KEY_BG_AUTO_RESTRICTED_BUCKET_ON_BG_RESTRICTION = "bg_auto_restricted_bucket_on_bg_restricted";
        static final java.lang.String KEY_BG_AUTO_RESTRICT_ABUSIVE_APPS = "bg_auto_restrict_abusive_apps";
        static final java.lang.String KEY_BG_LONG_FGS_NOTIFICATION_MINIMAL_INTERVAL = "bg_long_fgs_notification_minimal_interval";
        static final java.lang.String KEY_BG_PROMPT_ABUSIVE_APPS_TO_BG_RESTRICTED = "bg_prompt_abusive_apps_to_bg_restricted";
        static final java.lang.String KEY_BG_PROMPT_FGS_ON_LONG_RUNNING = "bg_prompt_fgs_on_long_running";
        static final java.lang.String KEY_BG_PROMPT_FGS_WITH_NOTIFICATION_ON_LONG_RUNNING = "bg_prompt_fgs_with_noti_on_long_running";
        static final java.lang.String KEY_BG_PROMPT_FGS_WITH_NOTIFICATION_TO_BG_RESTRICTED = "bg_prompt_fgs_with_noti_to_bg_restricted";
        static final java.lang.String KEY_BG_RESTRICTION_EXEMPTED_PACKAGES = "bg_restriction_exempted_packages";
        volatile long mBgAbusiveNotificationMinIntervalMs;
        volatile boolean mBgAutoRestrictAbusiveApps;
        volatile boolean mBgAutoRestrictedBucket;
        volatile long mBgLongFgsNotificationMinIntervalMs;
        volatile boolean mBgPromptAbusiveAppsToBgRestricted;
        volatile boolean mBgPromptFgsOnLongRunning;
        volatile boolean mBgPromptFgsWithNotiOnLongRunning;
        volatile boolean mBgPromptFgsWithNotiToBgRestricted;
        volatile java.util.Set<java.lang.String> mBgRestrictionExemptedPackages = java.util.Collections.emptySet();
        final boolean mDefaultBgPromptAbusiveAppToBgRestricted;
        final boolean mDefaultBgPromptFgsWithNotiToBgRestricted;

        ConstantsObserver(android.os.Handler handler, android.content.Context context) {
            this.mDefaultBgPromptFgsWithNotiToBgRestricted = context.getResources().getBoolean(android.R.bool.config_bg_prompt_fgs_with_noti_to_bg_restricted);
            this.mDefaultBgPromptAbusiveAppToBgRestricted = context.getResources().getBoolean(android.R.bool.config_bg_prompt_abusive_apps_to_bg_restricted);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            java.lang.String str;
            char c;
            java.util.Iterator it = properties.getKeyset().iterator();
            while (it.hasNext() && (str = (java.lang.String) it.next()) != null && str.startsWith(com.android.server.am.AppRestrictionController.DEVICE_CONFIG_SUBNAMESPACE_PREFIX)) {
                switch (str.hashCode()) {
                    case -1918659497:
                        if (str.equals(KEY_BG_PROMPT_ABUSIVE_APPS_TO_BG_RESTRICTED)) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1199889595:
                        if (str.equals(KEY_BG_AUTO_RESTRICT_ABUSIVE_APPS)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -582264882:
                        if (str.equals(KEY_BG_PROMPT_FGS_ON_LONG_RUNNING)) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -395763044:
                        if (str.equals(KEY_BG_AUTO_RESTRICTED_BUCKET_ON_BG_RESTRICTION)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -157665503:
                        if (str.equals(KEY_BG_RESTRICTION_EXEMPTED_PACKAGES)) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 854605367:
                        if (str.equals(KEY_BG_ABUSIVE_NOTIFICATION_MINIMAL_INTERVAL)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 892275457:
                        if (str.equals(KEY_BG_LONG_FGS_NOTIFICATION_MINIMAL_INTERVAL)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1771474142:
                        if (str.equals(KEY_BG_PROMPT_FGS_WITH_NOTIFICATION_ON_LONG_RUNNING)) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1965398671:
                        if (str.equals(KEY_BG_PROMPT_FGS_WITH_NOTIFICATION_TO_BG_RESTRICTED)) {
                            c = 4;
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
                        updateBgAutoRestrictedBucketChanged();
                        break;
                    case 1:
                        updateBgAutoRestrictAbusiveApps();
                        break;
                    case 2:
                        updateBgAbusiveNotificationMinimalInterval();
                        break;
                    case 3:
                        updateBgLongFgsNotificationMinimalInterval();
                        break;
                    case 4:
                        updateBgPromptFgsWithNotiToBgRestricted();
                        break;
                    case 5:
                        updateBgPromptFgsWithNotiOnLongRunning();
                        break;
                    case 6:
                        updateBgPromptFgsOnLongRunning();
                        break;
                    case 7:
                        updateBgPromptAbusiveAppToBgRestricted();
                        break;
                    case '\b':
                        updateBgRestrictionExemptedPackages();
                        break;
                }
                com.android.server.am.AppRestrictionController.this.onPropertiesChanged(str);
            }
        }

        public void start() {
            updateDeviceConfig();
        }

        void updateDeviceConfig() {
            updateBgAutoRestrictedBucketChanged();
            updateBgAutoRestrictAbusiveApps();
            updateBgAbusiveNotificationMinimalInterval();
            updateBgLongFgsNotificationMinimalInterval();
            updateBgPromptFgsWithNotiToBgRestricted();
            updateBgPromptFgsWithNotiOnLongRunning();
            updateBgPromptFgsOnLongRunning();
            updateBgPromptAbusiveAppToBgRestricted();
            updateBgRestrictionExemptedPackages();
        }

        private void updateBgAutoRestrictedBucketChanged() {
            boolean z = this.mBgAutoRestrictedBucket;
            this.mBgAutoRestrictedBucket = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_AUTO_RESTRICTED_BUCKET_ON_BG_RESTRICTION, false);
            if (z != this.mBgAutoRestrictedBucket) {
                com.android.server.am.AppRestrictionController.this.dispatchAutoRestrictedBucketFeatureFlagChanged(this.mBgAutoRestrictedBucket);
            }
        }

        private void updateBgAutoRestrictAbusiveApps() {
            this.mBgAutoRestrictAbusiveApps = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_AUTO_RESTRICT_ABUSIVE_APPS, true);
        }

        private void updateBgAbusiveNotificationMinimalInterval() {
            this.mBgAbusiveNotificationMinIntervalMs = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_ABUSIVE_NOTIFICATION_MINIMAL_INTERVAL, com.android.server.usage.UnixCalendar.MONTH_IN_MILLIS);
        }

        private void updateBgLongFgsNotificationMinimalInterval() {
            this.mBgLongFgsNotificationMinIntervalMs = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_LONG_FGS_NOTIFICATION_MINIMAL_INTERVAL, com.android.server.usage.UnixCalendar.MONTH_IN_MILLIS);
        }

        private void updateBgPromptFgsWithNotiToBgRestricted() {
            this.mBgPromptFgsWithNotiToBgRestricted = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_PROMPT_FGS_WITH_NOTIFICATION_TO_BG_RESTRICTED, this.mDefaultBgPromptFgsWithNotiToBgRestricted);
        }

        private void updateBgPromptFgsWithNotiOnLongRunning() {
            this.mBgPromptFgsWithNotiOnLongRunning = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_PROMPT_FGS_WITH_NOTIFICATION_ON_LONG_RUNNING, false);
        }

        private void updateBgPromptFgsOnLongRunning() {
            this.mBgPromptFgsOnLongRunning = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_PROMPT_FGS_ON_LONG_RUNNING, true);
        }

        private void updateBgPromptAbusiveAppToBgRestricted() {
            this.mBgPromptAbusiveAppsToBgRestricted = android.provider.DeviceConfig.getBoolean("activity_manager", KEY_BG_PROMPT_ABUSIVE_APPS_TO_BG_RESTRICTED, this.mDefaultBgPromptAbusiveAppToBgRestricted);
        }

        private void updateBgRestrictionExemptedPackages() {
            java.lang.String string = android.provider.DeviceConfig.getString("activity_manager", KEY_BG_RESTRICTION_EXEMPTED_PACKAGES, (java.lang.String) null);
            if (string == null) {
                this.mBgRestrictionExemptedPackages = java.util.Collections.emptySet();
                return;
            }
            java.lang.String[] split = string.split(",");
            android.util.ArraySet arraySet = new android.util.ArraySet();
            for (java.lang.String str : split) {
                arraySet.add(str);
            }
            this.mBgRestrictionExemptedPackages = java.util.Collections.unmodifiableSet(arraySet);
        }

        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.println("BACKGROUND RESTRICTION POLICY SETTINGS:");
            java.lang.String str2 = "  " + str;
            printWriter.print(str2);
            printWriter.print(KEY_BG_AUTO_RESTRICTED_BUCKET_ON_BG_RESTRICTION);
            printWriter.print('=');
            printWriter.println(this.mBgAutoRestrictedBucket);
            printWriter.print(str2);
            printWriter.print(KEY_BG_AUTO_RESTRICT_ABUSIVE_APPS);
            printWriter.print('=');
            printWriter.println(this.mBgAutoRestrictAbusiveApps);
            printWriter.print(str2);
            printWriter.print(KEY_BG_ABUSIVE_NOTIFICATION_MINIMAL_INTERVAL);
            printWriter.print('=');
            printWriter.println(this.mBgAbusiveNotificationMinIntervalMs);
            printWriter.print(str2);
            printWriter.print(KEY_BG_LONG_FGS_NOTIFICATION_MINIMAL_INTERVAL);
            printWriter.print('=');
            printWriter.println(this.mBgLongFgsNotificationMinIntervalMs);
            printWriter.print(str2);
            printWriter.print(KEY_BG_PROMPT_FGS_ON_LONG_RUNNING);
            printWriter.print('=');
            printWriter.println(this.mBgPromptFgsOnLongRunning);
            printWriter.print(str2);
            printWriter.print(KEY_BG_PROMPT_FGS_WITH_NOTIFICATION_ON_LONG_RUNNING);
            printWriter.print('=');
            printWriter.println(this.mBgPromptFgsWithNotiOnLongRunning);
            printWriter.print(str2);
            printWriter.print(KEY_BG_PROMPT_FGS_WITH_NOTIFICATION_TO_BG_RESTRICTED);
            printWriter.print('=');
            printWriter.println(this.mBgPromptFgsWithNotiToBgRestricted);
            printWriter.print(str2);
            printWriter.print(KEY_BG_PROMPT_ABUSIVE_APPS_TO_BG_RESTRICTED);
            printWriter.print('=');
            printWriter.println(this.mBgPromptAbusiveAppsToBgRestricted);
            printWriter.print(str2);
            printWriter.print(KEY_BG_RESTRICTION_EXEMPTED_PACKAGES);
            printWriter.print('=');
            printWriter.println(this.mBgRestrictionExemptedPackages.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TrackerInfo {
        final byte[] mInfo;
        final int mType;

        TrackerInfo() {
            this.mType = 0;
            this.mInfo = null;
        }

        TrackerInfo(int i, byte[] bArr) {
            this.mType = i;
            this.mInfo = bArr;
        }
    }

    public void addAppBackgroundRestrictionListener(@android.annotation.NonNull android.app.ActivityManagerInternal.AppBackgroundRestrictionListener appBackgroundRestrictionListener) {
        this.mRestrictionListeners.add(appBackgroundRestrictionListener);
    }

    AppRestrictionController(android.content.Context context, com.android.server.am.ActivityManagerService activityManagerService) {
        this(new com.android.server.am.AppRestrictionController.Injector(context), activityManagerService);
    }

    AppRestrictionController(com.android.server.am.AppRestrictionController.Injector injector, com.android.server.am.ActivityManagerService activityManagerService) {
        this.mAppStateTrackers = new java.util.ArrayList<>();
        this.mRestrictionSettings = new com.android.server.am.AppRestrictionController.RestrictionSettings();
        this.mRestrictionListeners = new java.util.concurrent.CopyOnWriteArraySet<>();
        this.mActiveUids = new android.util.SparseArrayMap<>();
        this.mTmpRunnables = new java.util.ArrayList<>();
        this.mDeviceIdleAllowlist = new int[0];
        this.mDeviceIdleExceptIdleAllowlist = new int[0];
        this.mSystemDeviceIdleAllowlist = new android.util.ArraySet<>();
        this.mSystemDeviceIdleExceptIdleAllowlist = new android.util.ArraySet<>();
        this.mLock = new java.lang.Object();
        this.mSettingsLock = new java.lang.Object();
        this.mRoleHolderChangedListener = new android.app.role.OnRoleHoldersChangedListener() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda8
            public final void onRoleHoldersChanged(java.lang.String str, android.os.UserHandle userHandle) {
                com.android.server.am.AppRestrictionController.this.onRoleHoldersChanged(str, userHandle);
            }
        };
        this.mUidRolesMapping = new android.util.SparseArray<>();
        this.mSystemModulesCache = new java.util.HashMap<>();
        this.mCarrierPrivilegedLock = new java.lang.Object();
        this.mCarrierPrivilegedApps = new android.util.SparseArray<>();
        this.mRestrictionSettingsXmlLoaded = new java.util.concurrent.atomic.AtomicBoolean();
        this.mEmptyTrackerInfo = new com.android.server.am.AppRestrictionController.TrackerInfo();
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.am.AppRestrictionController.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                int intExtra;
                java.lang.String schemeSpecificPart;
                int intExtra2;
                intent.getAction();
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -2061058799:
                        if (action.equals("android.intent.action.USER_REMOVED")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1749672628:
                        if (action.equals("android.intent.action.UID_REMOVED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -755112654:
                        if (action.equals("android.intent.action.USER_STARTED")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -742246786:
                        if (action.equals("android.intent.action.USER_STOPPED")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1093296680:
                        if (action.equals("android.telephony.action.MULTI_SIM_CONFIG_CHANGED")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1121780209:
                        if (action.equals("android.intent.action.USER_ADDED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1580442797:
                        if (action.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                            c = 1;
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
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false) && (intExtra = intent.getIntExtra("android.intent.extra.UID", -1)) >= 0) {
                            com.android.server.am.AppRestrictionController.this.onUidAdded(intExtra);
                            break;
                        }
                        break;
                    case 1:
                        int intExtra3 = intent.getIntExtra("android.intent.extra.UID", -1);
                        android.net.Uri data = intent.getData();
                        if (intExtra3 >= 0 && data != null && (schemeSpecificPart = data.getSchemeSpecificPart()) != null) {
                            com.android.server.am.AppRestrictionController.this.onPackageRemoved(schemeSpecificPart, intExtra3);
                            break;
                        }
                        break;
                    case 2:
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false) && (intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1)) >= 0) {
                            com.android.server.am.AppRestrictionController.this.onUidRemoved(intExtra2);
                            break;
                        }
                        break;
                    case 3:
                        int intExtra4 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra4 >= 0) {
                            com.android.server.am.AppRestrictionController.this.onUserAdded(intExtra4);
                            break;
                        }
                        break;
                    case 4:
                        int intExtra5 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra5 >= 0) {
                            com.android.server.am.AppRestrictionController.this.onUserStarted(intExtra5);
                            break;
                        }
                        break;
                    case 5:
                        int intExtra6 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra6 >= 0) {
                            com.android.server.am.AppRestrictionController.this.onUserStopped(intExtra6);
                            break;
                        }
                        break;
                    case 6:
                        int intExtra7 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra7 >= 0) {
                            com.android.server.am.AppRestrictionController.this.onUserRemoved(intExtra7);
                            break;
                        }
                        break;
                    case 7:
                        com.android.server.am.AppRestrictionController.this.unregisterCarrierPrivilegesCallbacks();
                        com.android.server.am.AppRestrictionController.this.registerCarrierPrivilegesCallbacks();
                        break;
                }
            }
        };
        this.mBootReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.am.AppRestrictionController.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                intent.getAction();
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -905063602:
                        if (action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        com.android.server.am.AppRestrictionController.this.onLockedBootCompleted();
                        break;
                }
            }
        };
        this.mBackgroundRestrictionListener = new com.android.server.AppStateTracker.BackgroundRestrictedAppListener() { // from class: com.android.server.am.AppRestrictionController.3
            public void updateBackgroundRestrictedForUidPackage(int i, java.lang.String str, boolean z) {
                com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(0, i, z ? 1 : 0, str).sendToTarget();
            }
        };
        this.mAppIdleStateChangeListener = new com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener() { // from class: com.android.server.am.AppRestrictionController.4
            public void onAppIdleStateChanged(java.lang.String str, int i, boolean z, int i2, int i3) {
                com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(2, i, i2, str).sendToTarget();
            }

            public void onUserInteractionStarted(java.lang.String str, int i) {
                com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(3, i, 0, str).sendToTarget();
            }
        };
        this.mUidObserver = new android.app.UidObserver() { // from class: com.android.server.am.AppRestrictionController.5
            public void onUidStateChanged(int i, int i2, long j, int i3) {
                com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(8, i, i2).sendToTarget();
            }

            public void onUidIdle(int i, boolean z) {
                com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(5, i, z ? 1 : 0).sendToTarget();
            }

            public void onUidGone(int i, boolean z) {
                com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(7, i, z ? 1 : 0).sendToTarget();
            }

            public void onUidActive(int i) {
                com.android.server.am.AppRestrictionController.this.mBgHandler.obtainMessage(6, i, 0).sendToTarget();
            }
        };
        this.mInjector = injector;
        this.mContext = injector.getContext();
        this.mActivityManagerService = activityManagerService;
        this.mBgHandlerThread = new android.os.HandlerThread("bgres-controller", 10);
        this.mBgHandlerThread.start();
        this.mBgHandler = new com.android.server.am.AppRestrictionController.BgHandler(this.mBgHandlerThread.getLooper(), injector);
        this.mBgExecutor = new android.os.HandlerExecutor(this.mBgHandler);
        this.mConstantsObserver = new com.android.server.am.AppRestrictionController.ConstantsObserver(this.mBgHandler, this.mContext);
        this.mNotificationHelper = new com.android.server.am.AppRestrictionController.NotificationHelper(this);
        injector.initAppStateTrackers(this);
    }

    void onSystemReady() {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("activity_manager", this.mBgExecutor, this.mConstantsObserver);
        this.mConstantsObserver.start();
        initBgRestrictionExemptioFromSysConfig();
        initRestrictionStates();
        initSystemModuleNames();
        initRolesInInterest();
        registerForUidObservers();
        registerForSystemBroadcasts();
        registerCarrierPrivilegesCallbacks();
        this.mNotificationHelper.onSystemReady();
        this.mInjector.getAppStateTracker().addBackgroundRestrictedAppListener(this.mBackgroundRestrictionListener);
        this.mInjector.getAppStandbyInternal().addListener(this.mAppIdleStateChangeListener);
        this.mInjector.getRoleManager().addOnRoleHoldersChangedListenerAsUser(this.mBgExecutor, this.mRoleHolderChangedListener, android.os.UserHandle.ALL);
        this.mInjector.scheduleInitTrackers(this.mBgHandler, new java.lang.Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.AppRestrictionController.this.lambda$onSystemReady$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$0() {
        int size = this.mAppStateTrackers.size();
        for (int i = 0; i < size; i++) {
            this.mAppStateTrackers.get(i).onSystemReady();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void resetRestrictionSettings() {
        synchronized (this.mSettingsLock) {
            this.mRestrictionSettings.reset();
        }
        initRestrictionStates();
    }

    @com.android.internal.annotations.VisibleForTesting
    void tearDown() {
        android.provider.DeviceConfig.removeOnPropertiesChangedListener(this.mConstantsObserver);
        unregisterForUidObservers();
        unregisterForSystemBroadcasts();
        this.mRestrictionSettings.removeXml();
    }

    private void initBgRestrictionExemptioFromSysConfig() {
        com.android.server.SystemConfig systemConfig = com.android.server.SystemConfig.getInstance();
        this.mBgRestrictionExemptioFromSysConfig = systemConfig.getBgRestrictionExemption();
        loadAppIdsFromPackageList(systemConfig.getAllowInPowerSaveExceptIdle(), this.mSystemDeviceIdleExceptIdleAllowlist);
        loadAppIdsFromPackageList(systemConfig.getAllowInPowerSave(), this.mSystemDeviceIdleAllowlist);
    }

    private void loadAppIdsFromPackageList(android.util.ArraySet<java.lang.String> arraySet, android.util.ArraySet<java.lang.Integer> arraySet2) {
        android.content.pm.PackageManager packageManager = this.mInjector.getPackageManager();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            try {
                android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(arraySet.valueAt(size), 1048576);
                if (applicationInfo != null) {
                    arraySet2.add(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(applicationInfo.uid)));
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
    }

    private boolean isExemptedFromSysConfig(java.lang.String str) {
        return this.mBgRestrictionExemptioFromSysConfig != null && this.mBgRestrictionExemptioFromSysConfig.contains(str);
    }

    private void initRestrictionStates() {
        int[] userIds = this.mInjector.getUserManagerInternal().getUserIds();
        for (int i : userIds) {
            refreshAppRestrictionLevelForUser(i, 1024, 2);
        }
        if (!this.mInjector.isTest()) {
            this.mRestrictionSettings.scheduleLoadFromXml();
            for (int i2 : userIds) {
                this.mRestrictionSettings.schedulePersistToXml(i2);
            }
        }
    }

    private void initSystemModuleNames() {
        java.util.List<android.content.pm.ModuleInfo> installedModules = this.mInjector.getPackageManager().getInstalledModules(0);
        if (installedModules == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                java.util.Iterator<android.content.pm.ModuleInfo> it = installedModules.iterator();
                while (it.hasNext()) {
                    this.mSystemModulesCache.put(it.next().getPackageName(), java.lang.Boolean.TRUE);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0043, code lost:
    
        if (r0.applicationInfo.sourceDir.startsWith(android.os.Environment.getApexDirectory().getAbsolutePath()) != false) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean isSystemModule(java.lang.String str) {
        boolean z;
        synchronized (this.mLock) {
            try {
                java.lang.Boolean bool = this.mSystemModulesCache.get(str);
                if (bool != null) {
                    return bool.booleanValue();
                }
                android.content.pm.PackageManager packageManager = this.mInjector.getPackageManager();
                boolean z2 = true;
                try {
                    z = packageManager.getModuleInfo(str, 0) != null;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    z = false;
                }
                if (!z) {
                    try {
                        android.content.pm.PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                        if (packageInfo != null) {
                        }
                        z2 = false;
                        z = z2;
                    } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                    }
                }
                synchronized (this.mLock) {
                    this.mSystemModulesCache.put(str, java.lang.Boolean.valueOf(z));
                }
                return z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void registerForUidObservers() {
        try {
            this.mInjector.getIActivityManager().registerUidObserver(this.mUidObserver, 15, 4, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
        } catch (android.os.RemoteException e) {
        }
    }

    private void unregisterForUidObservers() {
        try {
            this.mInjector.getIActivityManager().unregisterUidObserver(this.mUidObserver);
        } catch (android.os.RemoteException e) {
        }
    }

    private void refreshAppRestrictionLevelForUser(int i, int i2, int i3) {
        java.util.List<android.app.usage.AppStandbyInfo> appStandbyBuckets = this.mInjector.getAppStandbyInternal().getAppStandbyBuckets(i);
        if (com.android.internal.util.ArrayUtils.isEmpty(appStandbyBuckets)) {
            return;
        }
        android.content.pm.PackageManagerInternal packageManagerInternal = this.mInjector.getPackageManagerInternal();
        for (android.app.usage.AppStandbyInfo appStandbyInfo : appStandbyBuckets) {
            int packageUid = packageManagerInternal.getPackageUid(appStandbyInfo.mPackageName, 819200L, i);
            if (packageUid < 0) {
                android.util.Slog.e(TAG, "Unable to find " + appStandbyInfo.mPackageName + "/u" + i);
            } else {
                android.util.Pair<java.lang.Integer, com.android.server.am.AppRestrictionController.TrackerInfo> calcAppRestrictionLevel = calcAppRestrictionLevel(i, packageUid, appStandbyInfo.mPackageName, appStandbyInfo.mStandbyBucket, false, false);
                applyRestrictionLevel(appStandbyInfo.mPackageName, packageUid, ((java.lang.Integer) calcAppRestrictionLevel.first).intValue(), (com.android.server.am.AppRestrictionController.TrackerInfo) calcAppRestrictionLevel.second, appStandbyInfo.mStandbyBucket, true, i2, i3);
            }
        }
    }

    void refreshAppRestrictionLevelForUid(int i, int i2, int i3, boolean z) {
        java.lang.String[] packagesForUid = this.mInjector.getPackageManager().getPackagesForUid(i);
        if (com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
            return;
        }
        com.android.server.usage.AppStandbyInternal appStandbyInternal = this.mInjector.getAppStandbyInternal();
        int userId = android.os.UserHandle.getUserId(i);
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        int i4 = 0;
        for (int length = packagesForUid.length; i4 < length; length = length) {
            java.lang.String str = packagesForUid[i4];
            int appStandbyBucket = appStandbyInternal.getAppStandbyBucket(str, userId, elapsedRealtime, false);
            android.util.Pair<java.lang.Integer, com.android.server.am.AppRestrictionController.TrackerInfo> calcAppRestrictionLevel = calcAppRestrictionLevel(userId, i, str, appStandbyBucket, z, true);
            applyRestrictionLevel(str, i, ((java.lang.Integer) calcAppRestrictionLevel.first).intValue(), (com.android.server.am.AppRestrictionController.TrackerInfo) calcAppRestrictionLevel.second, appStandbyBucket, true, i2, i3);
            i4++;
        }
    }

    private android.util.Pair<java.lang.Integer, com.android.server.am.AppRestrictionController.TrackerInfo> calcAppRestrictionLevel(int i, int i2, java.lang.String str, int i3, boolean z, boolean z2) {
        int i4;
        if (this.mInjector.getAppHibernationInternal().isHibernatingForUser(str, i)) {
            return new android.util.Pair<>(60, this.mEmptyTrackerInfo);
        }
        int i5 = 20;
        com.android.server.am.AppRestrictionController.TrackerInfo trackerInfo = null;
        switch (i3) {
            case 5:
                break;
            case 50:
                i5 = 50;
                break;
            default:
                if (this.mInjector.getAppStateTracker().isAppBackgroundRestricted(i2, str)) {
                    return new android.util.Pair<>(50, this.mEmptyTrackerInfo);
                }
                if (i3 == 45) {
                    i4 = 40;
                } else {
                    i4 = 30;
                }
                if (!z2) {
                    i5 = i4;
                    break;
                } else {
                    android.util.Pair<java.lang.Integer, com.android.server.am.AppRestrictionController.TrackerInfo> calcAppRestrictionLevelFromTackers = calcAppRestrictionLevelFromTackers(i2, str, 100);
                    int intValue = ((java.lang.Integer) calcAppRestrictionLevelFromTackers.first).intValue();
                    if (intValue == 20) {
                        return new android.util.Pair<>(20, (com.android.server.am.AppRestrictionController.TrackerInfo) calcAppRestrictionLevelFromTackers.second);
                    }
                    if (intValue <= i4) {
                        i5 = i4;
                    } else {
                        trackerInfo = (com.android.server.am.AppRestrictionController.TrackerInfo) calcAppRestrictionLevelFromTackers.second;
                        i5 = intValue;
                    }
                    if (i5 == 50) {
                        if (z) {
                            this.mBgHandler.obtainMessage(4, i2, 0, str).sendToTarget();
                        }
                        android.util.Pair<java.lang.Integer, com.android.server.am.AppRestrictionController.TrackerInfo> calcAppRestrictionLevelFromTackers2 = calcAppRestrictionLevelFromTackers(i2, str, 50);
                        int intValue2 = ((java.lang.Integer) calcAppRestrictionLevelFromTackers2.first).intValue();
                        trackerInfo = (com.android.server.am.AppRestrictionController.TrackerInfo) calcAppRestrictionLevelFromTackers2.second;
                        i5 = intValue2;
                        break;
                    }
                }
                break;
        }
        return new android.util.Pair<>(java.lang.Integer.valueOf(i5), trackerInfo);
    }

    private android.util.Pair<java.lang.Integer, com.android.server.am.AppRestrictionController.TrackerInfo> calcAppRestrictionLevelFromTackers(int i, java.lang.String str, int i2) {
        com.android.server.am.AppRestrictionController.TrackerInfo trackerInfo;
        int i3 = 0;
        com.android.server.am.BaseAppStateTracker baseAppStateTracker = null;
        int i4 = 0;
        for (int size = this.mAppStateTrackers.size() - 1; size >= 0; size--) {
            i3 = java.lang.Math.max(i3, this.mAppStateTrackers.get(size).getPolicy().getProposedRestrictionLevel(str, i, i2));
            if (i3 != i4) {
                baseAppStateTracker = this.mAppStateTrackers.get(size);
                i4 = i3;
            }
        }
        if (baseAppStateTracker == null) {
            trackerInfo = this.mEmptyTrackerInfo;
        } else {
            trackerInfo = new com.android.server.am.AppRestrictionController.TrackerInfo(baseAppStateTracker.getType(), baseAppStateTracker.getTrackerInfoForStatsd(i));
        }
        return new android.util.Pair<>(java.lang.Integer.valueOf(i3), trackerInfo);
    }

    private static int standbyBucketToRestrictionLevel(int i) {
        switch (i) {
            case 5:
                return 20;
            case 10:
            case 20:
            case 30:
            case 40:
                return 30;
            case 45:
                return 40;
            case 50:
                return 50;
            default:
                return 0;
        }
    }

    int getRestrictionLevel(int i) {
        return this.mRestrictionSettings.getRestrictionLevel(i);
    }

    int getRestrictionLevel(int i, java.lang.String str) {
        return this.mRestrictionSettings.getRestrictionLevel(i, str);
    }

    int getRestrictionLevel(java.lang.String str, int i) {
        return this.mRestrictionSettings.getRestrictionLevel(str, i);
    }

    boolean isAutoRestrictAbusiveAppEnabled() {
        return this.mConstantsObserver.mBgAutoRestrictAbusiveApps;
    }

    long getForegroundServiceTotalDurations(java.lang.String str, int i, long j, int i2) {
        return this.mInjector.getAppFGSTracker().getTotalDurations(str, i, j, com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(i2));
    }

    long getForegroundServiceTotalDurations(int i, long j, int i2) {
        return this.mInjector.getAppFGSTracker().getTotalDurations(i, j, com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(i2));
    }

    long getForegroundServiceTotalDurationsSince(java.lang.String str, int i, long j, long j2, int i2) {
        return this.mInjector.getAppFGSTracker().getTotalDurationsSince(str, i, j, j2, com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(i2));
    }

    long getForegroundServiceTotalDurationsSince(int i, long j, long j2, int i2) {
        return this.mInjector.getAppFGSTracker().getTotalDurationsSince(i, j, j2, com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(i2));
    }

    long getMediaSessionTotalDurations(java.lang.String str, int i, long j) {
        return this.mInjector.getAppMediaSessionTracker().getTotalDurations(str, i, j);
    }

    long getMediaSessionTotalDurations(int i, long j) {
        return this.mInjector.getAppMediaSessionTracker().getTotalDurations(i, j);
    }

    long getMediaSessionTotalDurationsSince(java.lang.String str, int i, long j, long j2) {
        return this.mInjector.getAppMediaSessionTracker().getTotalDurationsSince(str, i, j, j2);
    }

    long getMediaSessionTotalDurationsSince(int i, long j, long j2) {
        return this.mInjector.getAppMediaSessionTracker().getTotalDurationsSince(i, j, j2);
    }

    long getCompositeMediaPlaybackDurations(java.lang.String str, int i, long j, long j2) {
        long max = java.lang.Math.max(0L, j - j2);
        return java.lang.Math.max(getMediaSessionTotalDurationsSince(str, i, max, j), getForegroundServiceTotalDurationsSince(str, i, max, j, 2));
    }

    long getCompositeMediaPlaybackDurations(int i, long j, long j2) {
        long max = java.lang.Math.max(0L, j - j2);
        return java.lang.Math.max(getMediaSessionTotalDurationsSince(i, max, j), getForegroundServiceTotalDurationsSince(i, max, j, 2));
    }

    boolean hasForegroundServices(java.lang.String str, int i) {
        return this.mInjector.getAppFGSTracker().hasForegroundServices(str, i);
    }

    boolean hasForegroundServices(int i) {
        return this.mInjector.getAppFGSTracker().hasForegroundServices(i);
    }

    boolean hasForegroundServiceNotifications(java.lang.String str, int i) {
        return this.mInjector.getAppFGSTracker().hasForegroundServiceNotifications(str, i);
    }

    boolean hasForegroundServiceNotifications(int i) {
        return this.mInjector.getAppFGSTracker().hasForegroundServiceNotifications(i);
    }

    com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage getUidBatteryExemptedUsageSince(int i, long j, long j2, int i2) {
        return this.mInjector.getAppBatteryExemptionTracker().getUidBatteryExemptedUsageSince(i, j, j2, i2);
    }

    @android.annotation.NonNull
    com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage getUidBatteryUsage(int i) {
        return this.mInjector.getUidBatteryUsageProvider().getUidBatteryUsage(i);
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println("APP BACKGROUND RESTRICTIONS");
        java.lang.String str2 = "  " + str;
        printWriter.print(str2);
        printWriter.println("BACKGROUND RESTRICTION LEVEL SETTINGS");
        this.mRestrictionSettings.dump(printWriter, "  " + str2);
        this.mConstantsObserver.dump(printWriter, "  " + str2);
        int size = this.mAppStateTrackers.size();
        for (int i = 0; i < size; i++) {
            printWriter.println();
            this.mAppStateTrackers.get(i).dump(printWriter, str2);
        }
    }

    void dumpAsProto(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).dumpAsProto(protoOutputStream, i);
        }
    }

    private int getRestrictionLevelStatsd(int i) {
        switch (i) {
            case 0:
                break;
            case 10:
                break;
            case 20:
                break;
            case 30:
                break;
            case 40:
                break;
            case 50:
                break;
            case 60:
                break;
        }
        return 0;
    }

    private int getThresholdStatsd(int i) {
        switch (i) {
            case 1024:
                return 2;
            case 1536:
                return 1;
            default:
                return 0;
        }
    }

    private int getTrackerTypeStatsd(@com.android.server.am.AppRestrictionController.TrackerType int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            default:
                return 0;
        }
    }

    private int getExemptionReasonStatsd(int i, int i2) {
        if (i2 != 20) {
            return 1;
        }
        return android.os.PowerExemptionManager.getExemptionReasonForStatsd(getBackgroundRestrictionExemptionReason(i));
    }

    private int getOptimizationLevelStatsd(int i) {
        switch (i) {
            case 0:
                break;
            case 10:
                break;
            case 30:
                break;
            case 50:
                break;
        }
        return 0;
    }

    private int getTargetSdkStatsd(java.lang.String str) {
        android.content.pm.PackageManager packageManager = this.mInjector.getPackageManager();
        if (packageManager == null) {
            return 0;
        }
        try {
            android.content.pm.PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null || packageInfo.applicationInfo == null) {
                return 0;
            }
            int i = packageInfo.applicationInfo.targetSdkVersion;
            if (i < 31) {
                return 1;
            }
            if (i < 33) {
                return 2;
            }
            if (i != 33) {
                return 0;
            }
            return 3;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v6 */
    void applyRestrictionLevel(final java.lang.String str, final int i, final int i2, com.android.server.am.AppRestrictionController.TrackerInfo trackerInfo, int i3, boolean z, int i4, int i5) {
        com.android.server.am.AppRestrictionController.TrackerInfo trackerInfo2;
        int i6;
        int i7;
        java.lang.Object obj;
        int i8;
        boolean z2;
        int appStandbyBucketReason;
        final com.android.server.usage.AppStandbyInternal appStandbyInternal = this.mInjector.getAppStandbyInternal();
        if (trackerInfo != null) {
            trackerInfo2 = trackerInfo;
        } else {
            trackerInfo2 = this.mEmptyTrackerInfo;
        }
        synchronized (this.mSettingsLock) {
            try {
                final int restrictionLevel = getRestrictionLevel(i, str);
                if (restrictionLevel == i2) {
                    return;
                }
                if (standbyBucketToRestrictionLevel(i3) == i2 && (appStandbyBucketReason = appStandbyInternal.getAppStandbyBucketReason(str, android.os.UserHandle.getUserId(i), android.os.SystemClock.elapsedRealtime())) != 0) {
                    i7 = appStandbyBucketReason & 255;
                    i6 = appStandbyBucketReason & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK;
                } else {
                    i6 = i4;
                    i7 = i5;
                }
                int reason = this.mRestrictionSettings.getReason(str, i);
                final int i9 = i7;
                final int i10 = i6;
                this.mRestrictionSettings.update(str, i, i2, i6, i9);
                if (!z || i3 == 5) {
                    return;
                }
                if (i2 >= 40 && restrictionLevel < 40) {
                    if (i3 != 45) {
                        if (this.mConstantsObserver.mBgAutoRestrictedBucket || i2 == 40) {
                            java.lang.Object obj2 = this.mSettingsLock;
                            synchronized (obj2) {
                                try {
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                }
                                try {
                                    if (this.mActiveUids.indexOfKey(i, str) < 0) {
                                        obj = obj2;
                                        i8 = restrictionLevel;
                                        z2 = true;
                                    } else {
                                        obj = obj2;
                                        i8 = restrictionLevel;
                                        final com.android.server.am.AppRestrictionController.TrackerInfo trackerInfo3 = trackerInfo2;
                                        this.mActiveUids.add(i, str, new java.lang.Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda6
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                com.android.server.am.AppRestrictionController.this.lambda$applyRestrictionLevel$1(appStandbyInternal, str, i, i10, i9, restrictionLevel, i2, trackerInfo3);
                                            }
                                        });
                                        z2 = false;
                                    }
                                    if (z2) {
                                        appStandbyInternal.restrictApp(str, android.os.UserHandle.getUserId(i), i10, i9);
                                        logAppBackgroundRestrictionInfo(str, i, i8, i2, trackerInfo2, i10);
                                        return;
                                    }
                                    return;
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                    ?? r16 = obj2;
                                    throw th;
                                }
                            }
                        }
                        return;
                    }
                    return;
                }
                if (restrictionLevel >= 40 && i2 < 40) {
                    synchronized (this.mSettingsLock) {
                        try {
                            if (this.mActiveUids.indexOfKey(i, str) >= 0) {
                                this.mActiveUids.add(i, str, (java.lang.Object) null);
                            }
                        } finally {
                        }
                    }
                    appStandbyInternal.maybeUnrestrictApp(str, android.os.UserHandle.getUserId(i), reason & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK, reason & 255, i10, i9);
                    logAppBackgroundRestrictionInfo(str, i, restrictionLevel, i2, trackerInfo2, i10);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyRestrictionLevel$1(com.android.server.usage.AppStandbyInternal appStandbyInternal, java.lang.String str, int i, int i2, int i3, int i4, int i5, com.android.server.am.AppRestrictionController.TrackerInfo trackerInfo) {
        appStandbyInternal.restrictApp(str, android.os.UserHandle.getUserId(i), i2, i3);
        logAppBackgroundRestrictionInfo(str, i, i4, i5, trackerInfo, i2);
    }

    private void logAppBackgroundRestrictionInfo(java.lang.String str, int i, int i2, int i3, @android.annotation.NonNull com.android.server.am.AppRestrictionController.TrackerInfo trackerInfo, int i4) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO, i, getRestrictionLevelStatsd(i3), getThresholdStatsd(i4), getTrackerTypeStatsd(trackerInfo.mType), trackerInfo.mType == 3 ? trackerInfo.mInfo : null, trackerInfo.mType == 1 ? trackerInfo.mInfo : null, trackerInfo.mType == 6 ? trackerInfo.mInfo : null, trackerInfo.mType == 7 ? trackerInfo.mInfo : null, getExemptionReasonStatsd(i, i3), getOptimizationLevelStatsd(i3), getTargetSdkStatsd(str), android.app.ActivityManager.isLowRamDeviceStatic(), getRestrictionLevelStatsd(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBackgroundRestrictionChanged(int i, java.lang.String str, boolean z) {
        int i2;
        int size = this.mAppStateTrackers.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mAppStateTrackers.get(i3).onBackgroundRestrictionChanged(i, str, z);
        }
        int appStandbyBucket = this.mInjector.getAppStandbyInternal().getAppStandbyBucket(str, android.os.UserHandle.getUserId(i), android.os.SystemClock.elapsedRealtime(), false);
        if (z) {
            applyRestrictionLevel(str, i, 50, this.mEmptyTrackerInfo, appStandbyBucket, true, 1024, 2);
            this.mBgHandler.obtainMessage(9, i, 0, str).sendToTarget();
            return;
        }
        int lastRestrictionLevel = this.mRestrictionSettings.getLastRestrictionLevel(i, str);
        if (appStandbyBucket == 5) {
            i2 = 5;
        } else if (lastRestrictionLevel != 40) {
            i2 = 40;
        } else {
            i2 = 45;
        }
        android.util.Pair<java.lang.Integer, com.android.server.am.AppRestrictionController.TrackerInfo> calcAppRestrictionLevel = calcAppRestrictionLevel(android.os.UserHandle.getUserId(i), i, str, i2, false, true);
        applyRestrictionLevel(str, i, ((java.lang.Integer) calcAppRestrictionLevel.first).intValue(), (com.android.server.am.AppRestrictionController.TrackerInfo) calcAppRestrictionLevel.second, appStandbyBucket, true, 768, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchAppRestrictionLevelChanges(final int i, final java.lang.String str, final int i2) {
        this.mRestrictionListeners.forEach(new java.util.function.Consumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.app.ActivityManagerInternal.AppBackgroundRestrictionListener) obj).onRestrictionLevelChanged(i, str, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchAutoRestrictedBucketFeatureFlagChanged(final boolean z) {
        final com.android.server.usage.AppStandbyInternal appStandbyInternal = this.mInjector.getAppStandbyInternal();
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSettingsLock) {
            this.mRestrictionSettings.forEachUidLocked(new java.util.function.Consumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.AppRestrictionController.this.lambda$dispatchAutoRestrictedBucketFeatureFlagChanged$6(arrayList, z, appStandbyInternal, (java.lang.Integer) obj);
                }
            });
        }
        for (int i = 0; i < arrayList.size(); i++) {
            ((java.lang.Runnable) arrayList.get(i)).run();
        }
        this.mRestrictionListeners.forEach(new java.util.function.Consumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.app.ActivityManagerInternal.AppBackgroundRestrictionListener) obj).onAutoRestrictedBucketFeatureFlagChanged(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchAutoRestrictedBucketFeatureFlagChanged$6(final java.util.ArrayList arrayList, final boolean z, final com.android.server.usage.AppStandbyInternal appStandbyInternal, final java.lang.Integer num) {
        this.mRestrictionSettings.forEachPackageInUidLocked(num.intValue(), new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda3
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                com.android.server.am.AppRestrictionController.lambda$dispatchAutoRestrictedBucketFeatureFlagChanged$5(arrayList, z, appStandbyInternal, num, (java.lang.String) obj, (java.lang.Integer) obj2, (java.lang.Integer) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dispatchAutoRestrictedBucketFeatureFlagChanged$5(java.util.ArrayList arrayList, boolean z, final com.android.server.usage.AppStandbyInternal appStandbyInternal, final java.lang.Integer num, final java.lang.String str, java.lang.Integer num2, final java.lang.Integer num3) {
        java.lang.Runnable runnable;
        if (num2.intValue() == 50) {
            if (z) {
                runnable = new java.lang.Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.AppRestrictionController.lambda$dispatchAutoRestrictedBucketFeatureFlagChanged$3(appStandbyInternal, str, num, num3);
                    }
                };
            } else {
                runnable = new java.lang.Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.AppRestrictionController.lambda$dispatchAutoRestrictedBucketFeatureFlagChanged$4(appStandbyInternal, str, num, num3);
                    }
                };
            }
            arrayList.add(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dispatchAutoRestrictedBucketFeatureFlagChanged$3(com.android.server.usage.AppStandbyInternal appStandbyInternal, java.lang.String str, java.lang.Integer num, java.lang.Integer num2) {
        appStandbyInternal.restrictApp(str, android.os.UserHandle.getUserId(num.intValue()), num2.intValue() & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK, num2.intValue() & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dispatchAutoRestrictedBucketFeatureFlagChanged$4(com.android.server.usage.AppStandbyInternal appStandbyInternal, java.lang.String str, java.lang.Integer num, java.lang.Integer num2) {
        appStandbyInternal.maybeUnrestrictApp(str, android.os.UserHandle.getUserId(num.intValue()), num2.intValue() & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK, num2.intValue() & 255, 768, 6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAppStandbyBucketChanged(int i, java.lang.String str, int i2) {
        int packageUid = this.mInjector.getPackageManagerInternal().getPackageUid(str, 819200L, i2);
        android.util.Pair<java.lang.Integer, com.android.server.am.AppRestrictionController.TrackerInfo> calcAppRestrictionLevel = calcAppRestrictionLevel(i2, packageUid, str, i, false, false);
        applyRestrictionLevel(str, packageUid, ((java.lang.Integer) calcAppRestrictionLevel.first).intValue(), (com.android.server.am.AppRestrictionController.TrackerInfo) calcAppRestrictionLevel.second, i, false, 256, 0);
    }

    void handleRequestBgRestricted(java.lang.String str, int i) {
        this.mNotificationHelper.postRequestBgRestrictedIfNecessary(str, i);
    }

    void handleCancelRequestBgRestricted(java.lang.String str, int i) {
        this.mNotificationHelper.cancelRequestBgRestrictedIfNecessary(str, i);
    }

    void handleUidProcStateChanged(int i, int i2) {
        int size = this.mAppStateTrackers.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mAppStateTrackers.get(i3).onUidProcStateChanged(i, i2);
        }
    }

    void handleUidGone(int i) {
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).onUidGone(i);
        }
    }

    static class NotificationHelper {
        static final java.lang.String ACTION_FGS_MANAGER_TRAMPOLINE = "com.android.server.am.ACTION_FGS_MANAGER_TRAMPOLINE";
        static final java.lang.String GROUP_KEY = "com.android.app.abusive_bg_apps";
        static final int NOTIFICATION_TYPE_ABUSIVE_CURRENT_DRAIN = 0;
        static final int NOTIFICATION_TYPE_LAST = 2;
        static final int NOTIFICATION_TYPE_LONG_RUNNING_FGS = 1;
        static final java.lang.String PACKAGE_SCHEME = "package";
        static final int SUMMARY_NOTIFICATION_ID = 203105544;
        private final com.android.server.am.AppRestrictionController mBgController;
        private final android.content.Context mContext;
        private final com.android.server.am.AppRestrictionController.Injector mInjector;
        private final java.lang.Object mLock;
        private final android.app.NotificationManager mNotificationManager;
        private final java.lang.Object mSettingsLock;
        static final java.lang.String[] NOTIFICATION_TYPE_STRINGS = {"Abusive current drain", "Long-running FGS"};
        static final java.lang.String ATTR_LAST_BATTERY_NOTIFICATION_TIME = "last_batt_noti_ts";
        static final java.lang.String ATTR_LAST_LONG_FGS_NOTIFICATION_TIME = "last_long_fgs_noti_ts";
        static final java.lang.String[] NOTIFICATION_TIME_ATTRS = {ATTR_LAST_BATTERY_NOTIFICATION_TIME, ATTR_LAST_LONG_FGS_NOTIFICATION_TIME};
        private final android.content.BroadcastReceiver mActionButtonReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.am.AppRestrictionController.NotificationHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                intent.getAction();
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -2048453630:
                        if (action.equals(com.android.server.am.AppRestrictionController.NotificationHelper.ACTION_FGS_MANAGER_TRAMPOLINE)) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        com.android.server.am.AppRestrictionController.NotificationHelper.this.cancelRequestBgRestrictedIfNecessary(intent.getStringExtra("android.intent.extra.PACKAGE_NAME"), intent.getIntExtra("android.intent.extra.UID", 0));
                        android.content.Intent intent2 = new android.content.Intent("android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER");
                        intent2.addFlags(16777216);
                        com.android.server.am.AppRestrictionController.NotificationHelper.this.mContext.sendBroadcastAsUser(intent2, android.os.UserHandle.SYSTEM);
                        break;
                }
            }
        };

        @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
        private int mNotificationIDStepper = 203105545;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface NotificationType {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        static int notificationTimeAttrToType(@android.annotation.NonNull java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -1157017279:
                    if (str.equals(ATTR_LAST_LONG_FGS_NOTIFICATION_TIME)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 17543473:
                    if (str.equals(ATTR_LAST_BATTERY_NOTIFICATION_TIME)) {
                        c = 0;
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
                    return 0;
                case 1:
                    return 1;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
        }

        @android.annotation.NonNull
        static java.lang.String notificationTypeToTimeAttr(int i) {
            return NOTIFICATION_TIME_ATTRS[i];
        }

        static java.lang.String notificationTypeToString(int i) {
            return NOTIFICATION_TYPE_STRINGS[i];
        }

        NotificationHelper(com.android.server.am.AppRestrictionController appRestrictionController) {
            this.mBgController = appRestrictionController;
            this.mInjector = appRestrictionController.mInjector;
            this.mNotificationManager = this.mInjector.getNotificationManager();
            this.mLock = appRestrictionController.mLock;
            this.mSettingsLock = appRestrictionController.mSettingsLock;
            this.mContext = this.mInjector.getContext();
        }

        void onSystemReady() {
            this.mContext.registerReceiverForAllUsers(this.mActionButtonReceiver, new android.content.IntentFilter(ACTION_FGS_MANAGER_TRAMPOLINE), "android.permission.MANAGE_ACTIVITY_TASKS", this.mBgController.mBgHandler, 4);
        }

        void postRequestBgRestrictedIfNecessary(java.lang.String str, int i) {
            if (!this.mBgController.mConstantsObserver.mBgPromptAbusiveAppsToBgRestricted) {
                return;
            }
            android.content.Intent intent = new android.content.Intent("android.settings.VIEW_ADVANCED_POWER_USAGE_DETAIL");
            intent.setData(android.net.Uri.fromParts("package", str, null));
            intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF);
            android.app.PendingIntent activityAsUser = android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, null, android.os.UserHandle.of(android.os.UserHandle.getUserId(i)));
            boolean hasForegroundServices = this.mBgController.hasForegroundServices(str, i);
            boolean hasForegroundServiceNotifications = this.mBgController.hasForegroundServiceNotifications(str, i);
            if (!this.mBgController.mConstantsObserver.mBgPromptFgsWithNotiToBgRestricted && hasForegroundServices && hasForegroundServiceNotifications) {
                return;
            }
            postNotificationIfNecessary(0, android.R.string.notification_channel_sim, android.R.string.notification_channel_call_forward, activityAsUser, str, i, null);
        }

        void postLongRunningFgsIfNecessary(java.lang.String str, int i) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO, i, this.mBgController.getRestrictionLevel(i), 0, 3, this.mInjector.getAppFGSTracker().getTrackerInfoForStatsd(i), (byte[]) null, (byte[]) null, (byte[]) null, android.os.PowerExemptionManager.getExemptionReasonForStatsd(this.mBgController.getBackgroundRestrictionExemptionReason(i)), 0, 0, android.app.ActivityManager.isLowRamDeviceStatic(), this.mBgController.getRestrictionLevel(i));
            if (!this.mBgController.mConstantsObserver.mBgPromptFgsOnLongRunning) {
                return;
            }
            if (!this.mBgController.mConstantsObserver.mBgPromptFgsWithNotiOnLongRunning && this.mBgController.hasForegroundServiceNotifications(str, i)) {
                return;
            }
            android.content.Intent intent = new android.content.Intent("android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER");
            intent.addFlags(16777216);
            postNotificationIfNecessary(1, android.R.string.notification_channel_sim_high_prio, android.R.string.notification_channel_car_mode, android.app.PendingIntent.getBroadcastAsUser(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, android.os.UserHandle.SYSTEM), str, i, null);
        }

        long getNotificationMinInterval(int i) {
            switch (i) {
                case 0:
                    return this.mBgController.mConstantsObserver.mBgAbusiveNotificationMinIntervalMs;
                case 1:
                    return this.mBgController.mConstantsObserver.mBgLongFgsNotificationMinIntervalMs;
                default:
                    return 0L;
            }
        }

        int getNotificationIdIfNecessary(int i, java.lang.String str, int i2) {
            synchronized (this.mSettingsLock) {
                try {
                    com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings restrictionSettingsLocked = this.mBgController.mRestrictionSettings.getRestrictionSettingsLocked(i2, str);
                    if (restrictionSettingsLocked == null) {
                        return 0;
                    }
                    long currentTimeMillis = this.mInjector.currentTimeMillis();
                    long lastNotificationTime = restrictionSettingsLocked.getLastNotificationTime(i);
                    if (lastNotificationTime != 0 && lastNotificationTime + getNotificationMinInterval(i) > currentTimeMillis) {
                        return 0;
                    }
                    restrictionSettingsLocked.setLastNotificationTime(i, currentTimeMillis);
                    int notificationId = restrictionSettingsLocked.getNotificationId(i);
                    if (notificationId <= 0) {
                        notificationId = this.mNotificationIDStepper;
                        this.mNotificationIDStepper = notificationId + 1;
                        restrictionSettingsLocked.setNotificationId(i, notificationId);
                    }
                    return notificationId;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void postNotificationIfNecessary(int i, int i2, int i3, android.app.PendingIntent pendingIntent, java.lang.String str, int i4, @android.annotation.Nullable android.app.Notification.Action[] actionArr) {
            int notificationIdIfNecessary = getNotificationIdIfNecessary(i, str, i4);
            if (notificationIdIfNecessary <= 0) {
                return;
            }
            android.content.pm.PackageManagerInternal packageManagerInternal = this.mInjector.getPackageManagerInternal();
            android.content.pm.PackageManager packageManager = this.mInjector.getPackageManager();
            android.content.pm.ApplicationInfo applicationInfo = packageManagerInternal.getApplicationInfo(str, 819200L, 1000, android.os.UserHandle.getUserId(i4));
            postNotification(notificationIdIfNecessary, str, i4, this.mContext.getString(i2), this.mContext.getString(i3, applicationInfo != null ? applicationInfo.loadLabel(packageManager) : str), applicationInfo != null ? android.graphics.drawable.Icon.createWithResource(str, applicationInfo.icon) : null, pendingIntent, actionArr);
        }

        void postNotification(int i, java.lang.String str, int i2, java.lang.String str2, java.lang.String str3, android.graphics.drawable.Icon icon, android.app.PendingIntent pendingIntent, @android.annotation.Nullable android.app.Notification.Action[] actionArr) {
            android.os.UserHandle of = android.os.UserHandle.of(android.os.UserHandle.getUserId(i2));
            postSummaryNotification(of);
            android.app.Notification.Builder contentIntent = new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.ABUSIVE_BACKGROUND_APPS).setAutoCancel(true).setGroup(GROUP_KEY).setWhen(this.mInjector.currentTimeMillis()).setSmallIcon(android.R.drawable.stat_sys_warning).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).setContentTitle(str2).setContentText(str3).setContentIntent(pendingIntent);
            if (icon != null) {
                contentIntent.setLargeIcon(icon);
            }
            if (actionArr != null) {
                for (android.app.Notification.Action action : actionArr) {
                    contentIntent.addAction(action);
                }
            }
            android.app.Notification build = contentIntent.build();
            build.extras.putString("android.intent.extra.PACKAGE_NAME", str);
            this.mNotificationManager.notifyAsUser(null, i, build, of);
        }

        private void postSummaryNotification(@android.annotation.NonNull android.os.UserHandle userHandle) {
            this.mNotificationManager.notifyAsUser(null, SUMMARY_NOTIFICATION_ID, new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.ABUSIVE_BACKGROUND_APPS).setGroup(GROUP_KEY).setGroupSummary(true).setStyle(new android.app.Notification.BigTextStyle()).setSmallIcon(android.R.drawable.stat_sys_warning).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).build(), userHandle);
        }

        void cancelRequestBgRestrictedIfNecessary(java.lang.String str, int i) {
            int notificationId;
            synchronized (this.mSettingsLock) {
                try {
                    com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings restrictionSettingsLocked = this.mBgController.mRestrictionSettings.getRestrictionSettingsLocked(i, str);
                    if (restrictionSettingsLocked != null && (notificationId = restrictionSettingsLocked.getNotificationId(0)) > 0) {
                        this.mNotificationManager.cancel(notificationId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void cancelLongRunningFGSNotificationIfNecessary(java.lang.String str, int i) {
            int notificationId;
            synchronized (this.mSettingsLock) {
                try {
                    com.android.server.am.AppRestrictionController.RestrictionSettings.PkgSettings restrictionSettingsLocked = this.mBgController.mRestrictionSettings.getRestrictionSettingsLocked(i, str);
                    if (restrictionSettingsLocked != null && (notificationId = restrictionSettingsLocked.getNotificationId(1)) > 0) {
                        this.mNotificationManager.cancel(notificationId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void handleUidInactive(int i, boolean z) {
        java.util.ArrayList<java.lang.Runnable> arrayList = this.mTmpRunnables;
        synchronized (this.mSettingsLock) {
            try {
                int indexOfKey = this.mActiveUids.indexOfKey(i);
                if (indexOfKey < 0) {
                    return;
                }
                int numElementsForKeyAt = this.mActiveUids.numElementsForKeyAt(indexOfKey);
                for (int i2 = 0; i2 < numElementsForKeyAt; i2++) {
                    java.lang.Runnable runnable = (java.lang.Runnable) this.mActiveUids.valueAt(indexOfKey, i2);
                    if (runnable != null) {
                        arrayList.add(runnable);
                    }
                }
                this.mActiveUids.deleteAt(indexOfKey);
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    arrayList.get(i3).run();
                }
                arrayList.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void handleUidActive(final int i) {
        synchronized (this.mSettingsLock) {
            final com.android.server.usage.AppStandbyInternal appStandbyInternal = this.mInjector.getAppStandbyInternal();
            final int userId = android.os.UserHandle.getUserId(i);
            this.mRestrictionSettings.forEachPackageInUidLocked(i, new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda9
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    com.android.server.am.AppRestrictionController.this.lambda$handleUidActive$9(i, appStandbyInternal, userId, (java.lang.String) obj, (java.lang.Integer) obj2, (java.lang.Integer) obj3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleUidActive$9(int i, final com.android.server.usage.AppStandbyInternal appStandbyInternal, final int i2, final java.lang.String str, java.lang.Integer num, final java.lang.Integer num2) {
        if (this.mConstantsObserver.mBgAutoRestrictedBucket && num.intValue() == 50) {
            this.mActiveUids.add(i, str, new java.lang.Runnable() { // from class: com.android.server.am.AppRestrictionController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.AppRestrictionController.lambda$handleUidActive$8(appStandbyInternal, str, i2, num2);
                }
            });
        } else {
            this.mActiveUids.add(i, str, (java.lang.Object) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleUidActive$8(com.android.server.usage.AppStandbyInternal appStandbyInternal, java.lang.String str, int i, java.lang.Integer num) {
        appStandbyInternal.restrictApp(str, i, num.intValue() & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK, num.intValue() & 255);
    }

    boolean isOnDeviceIdleAllowlist(int i) {
        int appId = android.os.UserHandle.getAppId(i);
        return java.util.Arrays.binarySearch(this.mDeviceIdleAllowlist, appId) >= 0 || java.util.Arrays.binarySearch(this.mDeviceIdleExceptIdleAllowlist, appId) >= 0;
    }

    boolean isOnSystemDeviceIdleAllowlist(int i) {
        int appId = android.os.UserHandle.getAppId(i);
        return this.mSystemDeviceIdleAllowlist.contains(java.lang.Integer.valueOf(appId)) || this.mSystemDeviceIdleExceptIdleAllowlist.contains(java.lang.Integer.valueOf(appId));
    }

    void setDeviceIdleAllowlist(int[] iArr, int[] iArr2) {
        this.mDeviceIdleAllowlist = iArr;
        this.mDeviceIdleExceptIdleAllowlist = iArr2;
    }

    int getBackgroundRestrictionExemptionReason(int i) {
        int potentialSystemExemptionReason = getPotentialSystemExemptionReason(i);
        if (potentialSystemExemptionReason != -1) {
            return potentialSystemExemptionReason;
        }
        java.lang.String[] packagesForUid = this.mInjector.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null) {
            for (java.lang.String str : packagesForUid) {
                int potentialSystemExemptionReason2 = getPotentialSystemExemptionReason(i, str);
                if (potentialSystemExemptionReason2 != -1) {
                    return potentialSystemExemptionReason2;
                }
            }
            for (java.lang.String str2 : packagesForUid) {
                int potentialUserAllowedExemptionReason = getPotentialUserAllowedExemptionReason(i, str2);
                if (potentialUserAllowedExemptionReason != -1) {
                    return potentialUserAllowedExemptionReason;
                }
            }
        }
        return -1;
    }

    int getPotentialSystemExemptionReason(int i) {
        if (android.os.UserHandle.isCore(i)) {
            return 51;
        }
        if (isOnSystemDeviceIdleAllowlist(i)) {
            return 300;
        }
        if (android.os.UserManager.isDeviceInDemoMode(this.mContext)) {
            return 63;
        }
        if (this.mInjector.getUserManagerInternal().hasUserRestriction("no_control_apps", android.os.UserHandle.getUserId(i))) {
            return 323;
        }
        android.app.ActivityManagerInternal activityManagerInternal = this.mInjector.getActivityManagerInternal();
        if (activityManagerInternal.isDeviceOwner(i)) {
            return 55;
        }
        if (activityManagerInternal.isProfileOwner(i)) {
            return 56;
        }
        int uidProcessState = activityManagerInternal.getUidProcessState(i);
        if (uidProcessState <= 0) {
            return 10;
        }
        if (uidProcessState <= 1) {
            return 11;
        }
        return -1;
    }

    int getPotentialSystemExemptionReason(int i, java.lang.String str) {
        android.content.pm.PackageManagerInternal packageManagerInternal = this.mInjector.getPackageManagerInternal();
        com.android.server.usage.AppStandbyInternal appStandbyInternal = this.mInjector.getAppStandbyInternal();
        android.app.AppOpsManager appOpsManager = this.mInjector.getAppOpsManager();
        com.android.server.am.ActivityManagerService activityManagerService = this.mInjector.getActivityManagerService();
        int userId = android.os.UserHandle.getUserId(i);
        if (isSystemModule(str)) {
            return 320;
        }
        if (isCarrierApp(str)) {
            return 321;
        }
        if (isExemptedFromSysConfig(str) || this.mConstantsObserver.mBgRestrictionExemptedPackages.contains(str)) {
            return 300;
        }
        if (packageManagerInternal.isPackageStateProtected(str, userId)) {
            return 322;
        }
        if (appStandbyInternal.isActiveDeviceAdmin(str, userId)) {
            return com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ACTIVE_DEVICE_ADMIN;
        }
        if (activityManagerService.mConstants.mFlagSystemExemptPowerRestrictionsEnabled && appOpsManager.checkOpNoThrow(128, i, str) == 0) {
            return com.android.internal.util.FrameworkStatsLog.TIF_TUNE_CHANGED;
        }
        return -1;
    }

    int getPotentialUserAllowedExemptionReason(int i, java.lang.String str) {
        android.app.AppOpsManager appOpsManager = this.mInjector.getAppOpsManager();
        if (appOpsManager.checkOpNoThrow(47, i, str) == 0) {
            return 68;
        }
        if (appOpsManager.checkOpNoThrow(94, i, str) == 0) {
            return 69;
        }
        if (isRoleHeldByUid("android.app.role.DIALER", i)) {
            return com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ROLE_DIALER;
        }
        if (isRoleHeldByUid("android.app.role.EMERGENCY", i)) {
            return 319;
        }
        if (isOnDeviceIdleAllowlist(i)) {
            return 65;
        }
        if (this.mInjector.getActivityManagerInternal().isAssociatedCompanionApp(android.os.UserHandle.getUserId(i), i)) {
            return 57;
        }
        return -1;
    }

    private boolean isCarrierApp(java.lang.String str) {
        synchronized (this.mCarrierPrivilegedLock) {
            try {
                if (this.mCarrierPrivilegedApps != null) {
                    for (int size = this.mCarrierPrivilegedApps.size() - 1; size >= 0; size--) {
                        if (this.mCarrierPrivilegedApps.valueAt(size).contains(str)) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerCarrierPrivilegesCallbacks() {
        android.telephony.TelephonyManager telephonyManager = this.mInjector.getTelephonyManager();
        if (telephonyManager == null) {
            return;
        }
        int activeModemCount = telephonyManager.getActiveModemCount();
        java.util.ArrayList<com.android.server.am.AppRestrictionController.PhoneCarrierPrivilegesCallback> arrayList = new java.util.ArrayList<>();
        for (int i = 0; i < activeModemCount; i++) {
            com.android.server.am.AppRestrictionController.PhoneCarrierPrivilegesCallback phoneCarrierPrivilegesCallback = new com.android.server.am.AppRestrictionController.PhoneCarrierPrivilegesCallback(i);
            arrayList.add(phoneCarrierPrivilegesCallback);
            telephonyManager.registerCarrierPrivilegesCallback(i, this.mBgExecutor, phoneCarrierPrivilegesCallback);
        }
        this.mCarrierPrivilegesCallbacks = arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterCarrierPrivilegesCallbacks() {
        java.util.ArrayList<com.android.server.am.AppRestrictionController.PhoneCarrierPrivilegesCallback> arrayList;
        android.telephony.TelephonyManager telephonyManager = this.mInjector.getTelephonyManager();
        if (telephonyManager != null && (arrayList = this.mCarrierPrivilegesCallbacks) != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                telephonyManager.unregisterCarrierPrivilegesCallback(arrayList.get(size));
            }
            this.mCarrierPrivilegesCallbacks = null;
        }
    }

    private class PhoneCarrierPrivilegesCallback implements android.telephony.TelephonyManager.CarrierPrivilegesCallback {
        private final int mPhoneId;

        PhoneCarrierPrivilegesCallback(int i) {
            this.mPhoneId = i;
        }

        public void onCarrierPrivilegesChanged(@android.annotation.NonNull java.util.Set<java.lang.String> set, @android.annotation.NonNull java.util.Set<java.lang.Integer> set2) {
            synchronized (com.android.server.am.AppRestrictionController.this.mCarrierPrivilegedLock) {
                com.android.server.am.AppRestrictionController.this.mCarrierPrivilegedApps.put(this.mPhoneId, java.util.Collections.unmodifiableSet(set));
            }
        }
    }

    private boolean isRoleHeldByUid(@android.annotation.NonNull java.lang.String str, int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                java.util.ArrayList<java.lang.String> arrayList = this.mUidRolesMapping.get(i);
                z = arrayList != null && arrayList.indexOf(str) >= 0;
            } finally {
            }
        }
        return z;
    }

    private void initRolesInInterest() {
        int[] userIds = this.mInjector.getUserManagerInternal().getUserIds();
        for (java.lang.String str : ROLES_IN_INTEREST) {
            if (this.mInjector.getRoleManager().isRoleAvailable(str)) {
                for (int i : userIds) {
                    onRoleHoldersChanged(str, android.os.UserHandle.of(i));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRoleHoldersChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        java.util.List roleHoldersAsUser = this.mInjector.getRoleManager().getRoleHoldersAsUser(str, userHandle);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int identifier = userHandle.getIdentifier();
        if (roleHoldersAsUser != null) {
            android.content.pm.PackageManagerInternal packageManagerInternal = this.mInjector.getPackageManagerInternal();
            java.util.Iterator it = roleHoldersAsUser.iterator();
            while (it.hasNext()) {
                arraySet.add(java.lang.Integer.valueOf(packageManagerInternal.getPackageUid((java.lang.String) it.next(), 819200L, identifier)));
            }
        }
        synchronized (this.mLock) {
            try {
                for (int size = this.mUidRolesMapping.size() - 1; size >= 0; size--) {
                    int keyAt = this.mUidRolesMapping.keyAt(size);
                    if (android.os.UserHandle.getUserId(keyAt) == identifier) {
                        java.util.ArrayList<java.lang.String> valueAt = this.mUidRolesMapping.valueAt(size);
                        int indexOf = valueAt.indexOf(str);
                        boolean contains = arraySet.contains(java.lang.Integer.valueOf(keyAt));
                        if (indexOf >= 0) {
                            if (!contains) {
                                valueAt.remove(indexOf);
                                if (valueAt.isEmpty()) {
                                    this.mUidRolesMapping.removeAt(size);
                                }
                            }
                        } else if (contains) {
                            valueAt.add(str);
                            arraySet.remove(java.lang.Integer.valueOf(keyAt));
                        }
                    }
                }
                for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                    java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
                    arrayList.add(str);
                    this.mUidRolesMapping.put(((java.lang.Integer) arraySet.valueAt(size2)).intValue(), arrayList);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.os.Handler getBackgroundHandler() {
        return this.mBgHandler;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.HandlerThread getBackgroundHandlerThread() {
        return this.mBgHandlerThread;
    }

    java.lang.Object getLock() {
        return this.mLock;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addAppStateTracker(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker baseAppStateTracker) {
        this.mAppStateTrackers.add(baseAppStateTracker);
    }

    <T extends com.android.server.am.BaseAppStateTracker> T getAppStateTracker(java.lang.Class<T> cls) {
        java.util.Iterator<com.android.server.am.BaseAppStateTracker> it = this.mAppStateTrackers.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            if (cls.isAssignableFrom(t.getClass())) {
                return t;
            }
        }
        return null;
    }

    void postLongRunningFgsIfNecessary(java.lang.String str, int i) {
        this.mNotificationHelper.postLongRunningFgsIfNecessary(str, i);
    }

    void cancelLongRunningFGSNotificationIfNecessary(java.lang.String str, int i) {
        this.mNotificationHelper.cancelLongRunningFGSNotificationIfNecessary(str, i);
    }

    java.lang.String getPackageName(int i) {
        return this.mInjector.getPackageName(i);
    }

    static class BgHandler extends android.os.Handler {
        static final int MSG_APP_RESTRICTION_LEVEL_CHANGED = 1;
        static final int MSG_APP_STANDBY_BUCKET_CHANGED = 2;
        static final int MSG_BACKGROUND_RESTRICTION_CHANGED = 0;
        static final int MSG_CANCEL_REQUEST_BG_RESTRICTED = 9;
        static final int MSG_LOAD_RESTRICTION_SETTINGS = 10;
        static final int MSG_PERSIST_RESTRICTION_SETTINGS = 11;
        static final int MSG_REQUEST_BG_RESTRICTED = 4;
        static final int MSG_UID_ACTIVE = 6;
        static final int MSG_UID_GONE = 7;
        static final int MSG_UID_IDLE = 5;
        static final int MSG_UID_PROC_STATE_CHANGED = 8;
        static final int MSG_USER_INTERACTION_STARTED = 3;
        private final com.android.server.am.AppRestrictionController.Injector mInjector;

        BgHandler(android.os.Looper looper, com.android.server.am.AppRestrictionController.Injector injector) {
            super(looper);
            this.mInjector = injector;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.server.am.AppRestrictionController appRestrictionController = this.mInjector.getAppRestrictionController();
            switch (message.what) {
                case 0:
                    appRestrictionController.handleBackgroundRestrictionChanged(message.arg1, (java.lang.String) message.obj, message.arg2 == 1);
                    break;
                case 1:
                    appRestrictionController.dispatchAppRestrictionLevelChanges(message.arg1, (java.lang.String) message.obj, message.arg2);
                    break;
                case 2:
                    appRestrictionController.handleAppStandbyBucketChanged(message.arg2, (java.lang.String) message.obj, message.arg1);
                    break;
                case 3:
                    appRestrictionController.onUserInteractionStarted((java.lang.String) message.obj, message.arg1);
                    break;
                case 4:
                    appRestrictionController.handleRequestBgRestricted((java.lang.String) message.obj, message.arg1);
                    break;
                case 5:
                    appRestrictionController.handleUidInactive(message.arg1, message.arg2 == 1);
                    break;
                case 6:
                    appRestrictionController.handleUidActive(message.arg1);
                    break;
                case 7:
                    appRestrictionController.handleUidInactive(message.arg1, message.arg2 == 1);
                    appRestrictionController.handleUidGone(message.arg1);
                    break;
                case 8:
                    appRestrictionController.handleUidProcStateChanged(message.arg1, message.arg2);
                    break;
                case 9:
                    appRestrictionController.handleCancelRequestBgRestricted((java.lang.String) message.obj, message.arg1);
                    break;
                case 10:
                    appRestrictionController.mRestrictionSettings.loadFromXml(true);
                    break;
                case 11:
                    appRestrictionController.mRestrictionSettings.persistToXml(message.arg1);
                    break;
            }
        }
    }

    static class Injector {
        private android.app.ActivityManagerInternal mActivityManagerInternal;
        private com.android.server.am.AppBatteryExemptionTracker mAppBatteryExemptionTracker;
        private com.android.server.am.AppBatteryTracker mAppBatteryTracker;
        private com.android.server.am.AppFGSTracker mAppFGSTracker;
        private com.android.server.apphibernation.AppHibernationManagerInternal mAppHibernationInternal;
        private com.android.server.am.AppMediaSessionTracker mAppMediaSessionTracker;
        private android.app.AppOpsManager mAppOpsManager;
        private com.android.server.am.AppPermissionTracker mAppPermissionTracker;
        private com.android.server.am.AppRestrictionController mAppRestrictionController;
        private com.android.server.usage.AppStandbyInternal mAppStandbyInternal;
        private com.android.server.AppStateTracker mAppStateTracker;
        private final android.content.Context mContext;
        private android.app.IActivityManager mIActivityManager;
        private android.app.NotificationManager mNotificationManager;
        private android.content.pm.PackageManagerInternal mPackageManagerInternal;
        private android.app.role.RoleManager mRoleManager;
        private android.telephony.TelephonyManager mTelephonyManager;
        private com.android.server.pm.UserManagerInternal mUserManagerInternal;

        Injector(android.content.Context context) {
            this.mContext = context;
        }

        android.content.Context getContext() {
            return this.mContext;
        }

        void initAppStateTrackers(com.android.server.am.AppRestrictionController appRestrictionController) {
            this.mAppRestrictionController = appRestrictionController;
            this.mAppBatteryTracker = new com.android.server.am.AppBatteryTracker(this.mContext, appRestrictionController);
            this.mAppBatteryExemptionTracker = new com.android.server.am.AppBatteryExemptionTracker(this.mContext, appRestrictionController);
            this.mAppFGSTracker = new com.android.server.am.AppFGSTracker(this.mContext, appRestrictionController);
            this.mAppMediaSessionTracker = new com.android.server.am.AppMediaSessionTracker(this.mContext, appRestrictionController);
            this.mAppPermissionTracker = new com.android.server.am.AppPermissionTracker(this.mContext, appRestrictionController);
            appRestrictionController.mAppStateTrackers.add(this.mAppBatteryTracker);
            appRestrictionController.mAppStateTrackers.add(this.mAppBatteryExemptionTracker);
            appRestrictionController.mAppStateTrackers.add(this.mAppFGSTracker);
            appRestrictionController.mAppStateTrackers.add(this.mAppMediaSessionTracker);
            appRestrictionController.mAppStateTrackers.add(this.mAppPermissionTracker);
            appRestrictionController.mAppStateTrackers.add(new com.android.server.am.AppBroadcastEventsTracker(this.mContext, appRestrictionController));
            appRestrictionController.mAppStateTrackers.add(new com.android.server.am.AppBindServiceEventsTracker(this.mContext, appRestrictionController));
        }

        android.app.ActivityManagerInternal getActivityManagerInternal() {
            if (this.mActivityManagerInternal == null) {
                this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            }
            return this.mActivityManagerInternal;
        }

        com.android.server.am.AppRestrictionController getAppRestrictionController() {
            return this.mAppRestrictionController;
        }

        android.app.AppOpsManager getAppOpsManager() {
            if (this.mAppOpsManager == null) {
                this.mAppOpsManager = (android.app.AppOpsManager) getContext().getSystemService(android.app.AppOpsManager.class);
            }
            return this.mAppOpsManager;
        }

        com.android.server.usage.AppStandbyInternal getAppStandbyInternal() {
            if (this.mAppStandbyInternal == null) {
                this.mAppStandbyInternal = (com.android.server.usage.AppStandbyInternal) com.android.server.LocalServices.getService(com.android.server.usage.AppStandbyInternal.class);
            }
            return this.mAppStandbyInternal;
        }

        com.android.server.apphibernation.AppHibernationManagerInternal getAppHibernationInternal() {
            if (this.mAppHibernationInternal == null) {
                this.mAppHibernationInternal = (com.android.server.apphibernation.AppHibernationManagerInternal) com.android.server.LocalServices.getService(com.android.server.apphibernation.AppHibernationManagerInternal.class);
            }
            return this.mAppHibernationInternal;
        }

        com.android.server.AppStateTracker getAppStateTracker() {
            if (this.mAppStateTracker == null) {
                this.mAppStateTracker = (com.android.server.AppStateTracker) com.android.server.LocalServices.getService(com.android.server.AppStateTracker.class);
            }
            return this.mAppStateTracker;
        }

        android.app.IActivityManager getIActivityManager() {
            return android.app.ActivityManager.getService();
        }

        com.android.server.pm.UserManagerInternal getUserManagerInternal() {
            if (this.mUserManagerInternal == null) {
                this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
            }
            return this.mUserManagerInternal;
        }

        android.content.pm.PackageManagerInternal getPackageManagerInternal() {
            if (this.mPackageManagerInternal == null) {
                this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            }
            return this.mPackageManagerInternal;
        }

        android.content.pm.PackageManager getPackageManager() {
            return getContext().getPackageManager();
        }

        android.app.NotificationManager getNotificationManager() {
            if (this.mNotificationManager == null) {
                this.mNotificationManager = (android.app.NotificationManager) getContext().getSystemService(android.app.NotificationManager.class);
            }
            return this.mNotificationManager;
        }

        android.app.role.RoleManager getRoleManager() {
            if (this.mRoleManager == null) {
                this.mRoleManager = (android.app.role.RoleManager) getContext().getSystemService(android.app.role.RoleManager.class);
            }
            return this.mRoleManager;
        }

        android.telephony.TelephonyManager getTelephonyManager() {
            if (this.mTelephonyManager == null) {
                this.mTelephonyManager = (android.telephony.TelephonyManager) getContext().getSystemService(android.telephony.TelephonyManager.class);
            }
            return this.mTelephonyManager;
        }

        com.android.server.am.AppFGSTracker getAppFGSTracker() {
            return this.mAppFGSTracker;
        }

        com.android.server.am.AppMediaSessionTracker getAppMediaSessionTracker() {
            return this.mAppMediaSessionTracker;
        }

        com.android.server.am.ActivityManagerService getActivityManagerService() {
            return this.mAppRestrictionController.mActivityManagerService;
        }

        com.android.server.am.AppRestrictionController.UidBatteryUsageProvider getUidBatteryUsageProvider() {
            return this.mAppBatteryTracker;
        }

        com.android.server.am.AppBatteryExemptionTracker getAppBatteryExemptionTracker() {
            return this.mAppBatteryExemptionTracker;
        }

        com.android.server.am.AppPermissionTracker getAppPermissionTracker() {
            return this.mAppPermissionTracker;
        }

        java.lang.String getPackageName(int i) {
            android.content.pm.ApplicationInfo applicationInfo;
            com.android.server.am.ActivityManagerService activityManagerService = getActivityManagerService();
            synchronized (activityManagerService.mPidsSelfLocked) {
                try {
                    com.android.server.am.ProcessRecord processRecord = activityManagerService.mPidsSelfLocked.get(i);
                    if (processRecord != null && (applicationInfo = processRecord.info) != null) {
                        return applicationInfo.packageName;
                    }
                    return null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void scheduleInitTrackers(android.os.Handler handler, java.lang.Runnable runnable) {
            handler.post(runnable);
        }

        java.io.File getDataSystemDeDirectory(int i) {
            return android.os.Environment.getDataSystemDeDirectory(i);
        }

        long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }

        boolean isTest() {
            return false;
        }
    }

    private void registerForSystemBroadcasts() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverForAllUsers(this.mBroadcastReceiver, intentFilter, null, this.mBgHandler);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.intent.action.UID_REMOVED");
        this.mContext.registerReceiverForAllUsers(this.mBroadcastReceiver, intentFilter2, null, this.mBgHandler);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
        intentFilter3.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        this.mContext.registerReceiverAsUser(this.mBootReceiver, android.os.UserHandle.SYSTEM, intentFilter3, null, this.mBgHandler);
        this.mContext.registerReceiverForAllUsers(this.mBroadcastReceiver, new android.content.IntentFilter("android.telephony.action.MULTI_SIM_CONFIG_CHANGED"), null, this.mBgHandler);
    }

    private void unregisterForSystemBroadcasts() {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mBootReceiver);
    }

    void forEachTracker(java.util.function.Consumer<com.android.server.am.BaseAppStateTracker> consumer) {
        int size = this.mAppStateTrackers.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(this.mAppStateTrackers.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserAdded(int i) {
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).onUserAdded(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStarted(int i) {
        refreshAppRestrictionLevelForUser(i, 1024, 2);
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).onUserStarted(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStopped(int i) {
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).onUserStopped(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserRemoved(int i) {
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).onUserRemoved(i);
        }
        this.mRestrictionSettings.removeUser(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUidAdded(int i) {
        refreshAppRestrictionLevelForUid(i, 1536, 0, false);
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).onUidAdded(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageRemoved(java.lang.String str, int i) {
        this.mRestrictionSettings.removePackage(str, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUidRemoved(int i) {
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).onUidRemoved(i);
        }
        this.mRestrictionSettings.removeUid(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLockedBootCompleted() {
        int size = this.mAppStateTrackers.size();
        for (int i = 0; i < size; i++) {
            this.mAppStateTrackers.get(i).onLockedBootCompleted();
        }
    }

    boolean isBgAutoRestrictedBucketFeatureFlagEnabled() {
        return this.mConstantsObserver.mBgAutoRestrictedBucket;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPropertiesChanged(java.lang.String str) {
        int size = this.mAppStateTrackers.size();
        for (int i = 0; i < size; i++) {
            this.mAppStateTrackers.get(i).onPropertiesChanged(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserInteractionStarted(java.lang.String str, int i) {
        int packageUid = this.mInjector.getPackageManagerInternal().getPackageUid(str, 819200L, i);
        int size = this.mAppStateTrackers.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAppStateTrackers.get(i2).onUserInteractionStarted(str, packageUid);
        }
    }
}
