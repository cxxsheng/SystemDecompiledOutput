package com.android.server.am;

/* loaded from: classes.dex */
public final class ActiveServices {
    private static final boolean DEBUG_DELAYED_SERVICE = false;
    private static final boolean DEBUG_DELAYED_STARTS = false;
    private static final boolean DEBUG_SHORT_SERVICE = false;
    static final int DEFAULT_SERVICE_CACHED_BIND_POLICY_FLAG = 3;
    static final int DEFAULT_SERVICE_NO_BUMP_BIND_POLICY_FLAG = 7;
    static final long FGS_BG_START_RESTRICTION_CHANGE_ID = 170668199;
    public static final long FGS_BOOT_COMPLETED_RESTRICTIONS = 296558535;
    static final int FGS_IMMEDIATE_DISPLAY_MASK = 54;
    static final long FGS_START_EXCEPTION_CHANGE_ID = 174041399;
    static final int FGS_STOP_REASON_STOP_FOREGROUND = 1;
    static final int FGS_STOP_REASON_STOP_SERVICE = 2;
    static final int FGS_STOP_REASON_UNKNOWN = 0;
    static final long FGS_TYPE_CHECK_FOR_INSTANT_APPS = 261055255;
    static final int LAST_ANR_LIFETIME_DURATION_MSECS = 7200000;
    private static final boolean LOG_SERVICE_START_STOP = false;
    static final int SERVICE_BIND_OOMADJ_POLICY_FREEZE_CALLER = 8;
    static final int SERVICE_BIND_OOMADJ_POLICY_LEGACY = 0;
    static final int SERVICE_BIND_OOMADJ_POLICY_SKIP_OOM_UPDATE_ON_BIND = 2;
    static final int SERVICE_BIND_OOMADJ_POLICY_SKIP_OOM_UPDATE_ON_CONNECT = 4;
    static final int SERVICE_BIND_OOMADJ_POLICY_SKIP_OOM_UPDATE_ON_CREATE = 1;
    private static final java.lang.String TAG = "ActivityManager";
    private static final java.lang.String TAG_MU = "ActivityManager_MU";
    static final java.lang.String TAG_SERVICE = "ActivityManager";
    private static final java.lang.String TAG_SERVICE_EXECUTING = "ActivityManager";
    private final com.android.server.am.ActiveServices.ProcessAnrTimer mActiveServiceAnrTimer;
    final com.android.server.am.ActivityManagerService mAm;
    com.android.server.AppStateTracker mAppStateTracker;
    android.appwidget.AppWidgetManagerInternal mAppWidgetManagerInternal;
    java.lang.String mCachedDeviceProvisioningPackage;
    private final com.android.server.am.ActiveServices.ServiceAnrTimer mFGSAnrTimer;
    private final com.android.server.am.ForegroundServiceTypeLoggerModule mFGSLogger;
    java.lang.String mLastAnrDump;
    final int mMaxStartingBackground;
    private final com.android.server.am.ActiveServices.ServiceAnrTimer mServiceFGAnrTimer;
    private final com.android.server.am.ActiveServices.ServiceAnrTimer mShortFGSAnrTimer;
    static final java.util.concurrent.atomic.AtomicReference<android.util.Pair<java.lang.Integer, java.lang.Integer>> sNumForegroundServices = new java.util.concurrent.atomic.AtomicReference<>(new android.util.Pair(0, 0));
    private static final java.text.SimpleDateFormat DATE_FORMATTER = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final android.util.SparseArray<com.android.server.am.ActiveServices.ServiceMap> mServiceMap = new android.util.SparseArray<>();
    final android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> mServiceConnections = new android.util.ArrayMap<>();
    final java.util.ArrayList<com.android.server.am.ServiceRecord> mPendingServices = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.am.ServiceRecord> mRestartingServices = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.am.ServiceRecord> mDestroyingServices = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.am.ServiceRecord> mPendingFgsNotifications = new java.util.ArrayList<>();
    final android.util.ArrayMap<com.android.server.am.ForegroundServiceDelegation, com.android.server.am.ServiceRecord> mFgsDelegations = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mAm"})
    private long mBindServiceSeqCounter = 0;
    private boolean mFgsDeferralRateLimited = true;
    final android.util.SparseLongArray mFgsDeferralEligible = new android.util.SparseLongArray();
    final android.os.RemoteCallbackList<android.app.IForegroundServiceObserver> mFgsObservers = new android.os.RemoteCallbackList<>();
    private android.util.ArrayMap<com.android.server.am.ServiceRecord, java.util.ArrayList<java.lang.Runnable>> mPendingBringups = new android.util.ArrayMap<>();
    private java.util.ArrayList<com.android.server.am.ServiceRecord> mTmpCollectionResults = null;

    @com.android.internal.annotations.GuardedBy({"mAm"})
    private final android.util.SparseArray<com.android.server.am.ActiveServices.AppOpCallback> mFgsAppOpCallbacks = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mAm"})
    private final android.util.ArraySet<java.lang.String> mRestartBackoffDisabledPackages = new android.util.ArraySet<>();
    boolean mScreenOn = true;
    android.util.ArraySet<java.lang.String> mAllowListWhileInUsePermissionInFgs = new android.util.ArraySet<>();
    final java.lang.Runnable mLastAnrDumpClearer = new java.lang.Runnable() { // from class: com.android.server.am.ActiveServices.1
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ActiveServices.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    com.android.server.am.ActiveServices.this.mLastAnrDump = null;
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
    };
    private final java.lang.Runnable mPostDeferredFGSNotifications = new java.lang.Runnable() { // from class: com.android.server.am.ActiveServices.5
        @Override // java.lang.Runnable
        public void run() {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ActiveServices.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    for (int size = com.android.server.am.ActiveServices.this.mPendingFgsNotifications.size() - 1; size >= 0; size--) {
                        com.android.server.am.ServiceRecord serviceRecord = com.android.server.am.ActiveServices.this.mPendingFgsNotifications.get(size);
                        if (serviceRecord.fgDisplayTime <= uptimeMillis) {
                            com.android.server.am.ActiveServices.this.mPendingFgsNotifications.remove(size);
                            if (serviceRecord.isForeground && serviceRecord.app != null) {
                                serviceRecord.postNotification(true);
                                serviceRecord.mFgsNotificationShown = true;
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
    };

    static final class ActiveForegroundApp {
        boolean mAppOnTop;
        long mEndTime;
        long mHideTime;
        java.lang.CharSequence mLabel;
        int mNumActive;
        java.lang.String mPackageName;
        boolean mShownWhileScreenOn;
        boolean mShownWhileTop;
        long mStartTime;
        long mStartVisibleTime;
        int mUid;

        ActiveForegroundApp() {
        }
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface FgsStopReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ServiceBindingOomAdjPolicy {
    }

    class BackgroundRestrictedListener implements com.android.server.AppStateTracker.BackgroundRestrictedAppListener {
        BackgroundRestrictedListener() {
        }

        public void updateBackgroundRestrictedForUidPackage(int i, java.lang.String str, boolean z) {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ActiveServices.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    com.android.server.am.ActiveServices.this.mAm.mProcessList.updateBackgroundRestrictedForUidPackageLocked(i, str, z);
                    if (!com.android.server.am.ActiveServices.this.isForegroundServiceAllowedInBackgroundRestricted(i, str) && !com.android.server.am.ActiveServices.this.isTempAllowedByAlarmClock(i)) {
                        com.android.server.am.ActiveServices.this.stopAllForegroundServicesLocked(i, str);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    void stopAllForegroundServicesLocked(int i, java.lang.String str) {
        com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = getServiceMapLocked(android.os.UserHandle.getUserId(i));
        int size = serviceMapLocked.mServicesByInstanceName.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.am.ServiceRecord valueAt = serviceMapLocked.mServicesByInstanceName.valueAt(i2);
            if ((i == valueAt.serviceInfo.applicationInfo.uid || str.equals(valueAt.serviceInfo.packageName)) && valueAt.isForeground && valueAt.mAllowStartForegroundAtEntering != 301 && !isDeviceProvisioningPackage(valueAt.packageName)) {
                arrayList.add(valueAt);
            }
        }
        int size2 = arrayList.size();
        for (int i3 = 0; i3 < size2; i3++) {
            setServiceForegroundInnerLocked((com.android.server.am.ServiceRecord) arrayList.get(i3), 0, null, 0, 0, 0);
        }
    }

    final class ServiceMap extends android.os.Handler {
        static final int MSG_BG_START_TIMEOUT = 1;
        static final int MSG_ENSURE_NOT_START_BG = 3;
        static final int MSG_UPDATE_FOREGROUND_APPS = 2;
        final android.util.ArrayMap<java.lang.String, com.android.server.am.ActiveServices.ActiveForegroundApp> mActiveForegroundApps;
        boolean mActiveForegroundAppsChanged;
        final java.util.ArrayList<com.android.server.am.ServiceRecord> mDelayedStartList;
        final java.util.ArrayList<java.lang.String> mPendingRemoveForegroundApps;
        final android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> mServicesByInstanceName;
        final android.util.ArrayMap<android.content.Intent.FilterComparison, com.android.server.am.ServiceRecord> mServicesByIntent;
        final java.util.ArrayList<com.android.server.am.ServiceRecord> mStartingBackground;
        final int mUserId;

        ServiceMap(android.os.Looper looper, int i) {
            super(looper);
            this.mServicesByInstanceName = new android.util.ArrayMap<>();
            this.mServicesByIntent = new android.util.ArrayMap<>();
            this.mDelayedStartList = new java.util.ArrayList<>();
            this.mStartingBackground = new java.util.ArrayList<>();
            this.mActiveForegroundApps = new android.util.ArrayMap<>();
            this.mPendingRemoveForegroundApps = new java.util.ArrayList<>();
            this.mUserId = i;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ActiveServices.this.mAm;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            rescheduleDelayedStartsLocked();
                        } finally {
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                case 2:
                    com.android.server.am.ActiveServices.this.updateForegroundApps(this);
                    return;
                case 3:
                    com.android.server.am.ActivityManagerService activityManagerService2 = com.android.server.am.ActiveServices.this.mAm;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService2) {
                        try {
                            rescheduleDelayedStartsLocked();
                        } finally {
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    return;
            }
        }

        void ensureNotStartingBackgroundLocked(com.android.server.am.ServiceRecord serviceRecord) {
            if (this.mStartingBackground.remove(serviceRecord)) {
                removeMessages(3);
                sendMessage(obtainMessage(3));
            }
            this.mDelayedStartList.remove(serviceRecord);
        }

        /* JADX WARN: Type inference failed for: r11v0 */
        /* JADX WARN: Type inference failed for: r11v1, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r11v3 */
        void rescheduleDelayedStartsLocked() {
            java.lang.String str;
            int i;
            java.lang.String str2;
            boolean z;
            removeMessages(1);
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int size = this.mStartingBackground.size();
            ?? r11 = 0;
            int i2 = 0;
            while (true) {
                str = "ActivityManager";
                if (i2 >= size) {
                    break;
                }
                com.android.server.am.ServiceRecord serviceRecord = this.mStartingBackground.get(i2);
                if (serviceRecord.startingBgTimeout <= uptimeMillis) {
                    android.util.Slog.i("ActivityManager", "Waited long enough for: " + serviceRecord);
                    this.mStartingBackground.remove(i2);
                    size += -1;
                    i2 += -1;
                }
                i2++;
            }
            while (true) {
                if (this.mDelayedStartList.size() > 0) {
                    if (this.mStartingBackground.size() >= com.android.server.am.ActiveServices.this.mMaxStartingBackground) {
                        i = r11;
                        break;
                    }
                    com.android.server.am.ServiceRecord remove = this.mDelayedStartList.remove((int) r11);
                    remove.delayed = r11;
                    if (remove.pendingStarts.size() <= 0) {
                        android.util.Slog.wtf(str, "**** NO PENDING STARTS! " + remove + " startReq=" + remove.startRequested + " delayedStop=" + remove.delayedStop);
                        str2 = str;
                        z = r11;
                    } else {
                        try {
                            com.android.server.am.ServiceRecord.StartItem startItem = remove.pendingStarts.get(r11);
                            str2 = str;
                            z = r11;
                            try {
                                com.android.server.am.ActiveServices.this.startServiceInnerLocked(this, startItem.intent, remove, false, true, startItem.callingId, startItem.mCallingProcessName, startItem.mCallingProcessState, remove.startRequested, startItem.mCallingPackageName);
                            } catch (android.os.TransactionTooLargeException e) {
                            }
                        } catch (android.os.TransactionTooLargeException e2) {
                            str2 = str;
                            z = r11;
                        }
                    }
                    r11 = z;
                    str = str2;
                } else {
                    i = r11;
                    break;
                }
            }
            if (this.mStartingBackground.size() > 0) {
                com.android.server.am.ServiceRecord serviceRecord2 = this.mStartingBackground.get(i);
                if (serviceRecord2.startingBgTimeout > uptimeMillis) {
                    uptimeMillis = serviceRecord2.startingBgTimeout;
                }
                sendMessageAtTime(obtainMessage(1), uptimeMillis);
            }
            if (this.mStartingBackground.size() < com.android.server.am.ActiveServices.this.mMaxStartingBackground) {
                com.android.server.am.ActiveServices.this.mAm.backgroundServicesFinishedLocked(this.mUserId);
            }
        }
    }

    public ActiveServices(com.android.server.am.ActivityManagerService activityManagerService) {
        int i;
        int i2 = 1;
        this.mAm = activityManagerService;
        try {
            i = java.lang.Integer.parseInt(android.os.SystemProperties.get("ro.config.max_starting_bg", "0"));
        } catch (java.lang.RuntimeException e) {
            i = 0;
        }
        if (i > 0) {
            i2 = i;
        } else if (!android.app.ActivityManager.isLowRamDeviceStatic()) {
            i2 = 8;
        }
        this.mMaxStartingBackground = i2;
        android.os.ServiceManager.getService("platform_compat");
        this.mFGSLogger = new com.android.server.am.ForegroundServiceTypeLoggerModule();
        this.mActiveServiceAnrTimer = new com.android.server.am.ActiveServices.ProcessAnrTimer(activityManagerService, 12, "SERVICE_TIMEOUT");
        this.mShortFGSAnrTimer = new com.android.server.am.ActiveServices.ServiceAnrTimer(activityManagerService, 78, "SHORT_FGS_TIMEOUT");
        this.mServiceFGAnrTimer = new com.android.server.am.ActiveServices.ServiceAnrTimer(activityManagerService, 66, "SERVICE_FOREGROUND_TIMEOUT");
        this.mFGSAnrTimer = new com.android.server.am.ActiveServices.ServiceAnrTimer(activityManagerService, 85, "FGS_TIMEOUT");
    }

    void systemServicesReady() {
        getAppStateTracker().addBackgroundRestrictedAppListener(new com.android.server.am.ActiveServices.BackgroundRestrictedListener());
        this.mAppWidgetManagerInternal = (android.appwidget.AppWidgetManagerInternal) com.android.server.LocalServices.getService(android.appwidget.AppWidgetManagerInternal.class);
        setAllowListWhileInUsePermissionInFgs();
        initSystemExemptedFgsTypePermission();
        initMediaProjectFgsTypeCustomPermission();
    }

    private com.android.server.AppStateTracker getAppStateTracker() {
        if (this.mAppStateTracker == null) {
            this.mAppStateTracker = (com.android.server.AppStateTracker) com.android.server.LocalServices.getService(com.android.server.AppStateTracker.class);
        }
        return this.mAppStateTracker;
    }

    private void setAllowListWhileInUsePermissionInFgs() {
        java.lang.String attentionServicePackageName = this.mAm.mContext.getPackageManager().getAttentionServicePackageName();
        if (!android.text.TextUtils.isEmpty(attentionServicePackageName)) {
            this.mAllowListWhileInUsePermissionInFgs.add(attentionServicePackageName);
        }
        java.lang.String systemCaptionsServicePackageName = this.mAm.mContext.getPackageManager().getSystemCaptionsServicePackageName();
        if (!android.text.TextUtils.isEmpty(systemCaptionsServicePackageName)) {
            this.mAllowListWhileInUsePermissionInFgs.add(systemCaptionsServicePackageName);
        }
    }

    com.android.server.am.ServiceRecord getServiceByNameLocked(android.content.ComponentName componentName, int i) {
        return getServiceMapLocked(i).mServicesByInstanceName.get(componentName);
    }

    boolean hasBackgroundServicesLocked(int i) {
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i);
        return serviceMap != null && serviceMap.mStartingBackground.size() >= this.mMaxStartingBackground;
    }

    boolean hasForegroundServiceNotificationLocked(java.lang.String str, int i, java.lang.String str2) {
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i);
        if (serviceMap != null) {
            for (int i2 = 0; i2 < serviceMap.mServicesByInstanceName.size(); i2++) {
                com.android.server.am.ServiceRecord valueAt = serviceMap.mServicesByInstanceName.valueAt(i2);
                if (valueAt.appInfo.packageName.equals(str) && valueAt.isForeground && java.util.Objects.equals(valueAt.foregroundNoti.getChannelId(), str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.am.ActiveServices.ServiceMap getServiceMapLocked(int i) {
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i);
        if (serviceMap == null) {
            com.android.server.am.ActiveServices.ServiceMap serviceMap2 = new com.android.server.am.ActiveServices.ServiceMap(this.mAm.mHandler.getLooper(), i);
            this.mServiceMap.put(i, serviceMap2);
            return serviceMap2;
        }
        return serviceMap;
    }

    android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> getServicesLocked(int i) {
        return getServiceMapLocked(i).mServicesByInstanceName;
    }

    private boolean appRestrictedAnyInBackground(int i, java.lang.String str) {
        com.android.server.AppStateTracker appStateTracker = getAppStateTracker();
        if (appStateTracker != null) {
            return appStateTracker.isAppBackgroundRestricted(i, str);
        }
        return false;
    }

    void updateAppRestrictedAnyInBackgroundLocked(int i, java.lang.String str) {
        com.android.server.am.ProcessRecord processInPackage;
        boolean appRestrictedAnyInBackground = appRestrictedAnyInBackground(i, str);
        com.android.server.am.UidRecord uidRecordLOSP = this.mAm.mProcessList.getUidRecordLOSP(i);
        if (uidRecordLOSP != null && (processInPackage = uidRecordLOSP.getProcessInPackage(str)) != null) {
            processInPackage.mState.setBackgroundRestricted(appRestrictedAnyInBackground);
        }
    }

    static java.lang.String getProcessNameForService(android.content.pm.ServiceInfo serviceInfo, android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2, boolean z3) {
        if (z) {
            return str2;
        }
        if ((serviceInfo.flags & 2) == 0 || (z3 && !isDefaultProcessService(serviceInfo))) {
            return serviceInfo.processName;
        }
        if (z2) {
            return str + ":ishared:" + str2;
        }
        return serviceInfo.processName + ":" + componentName.getClassName();
    }

    private static boolean isDefaultProcessService(android.content.pm.ServiceInfo serviceInfo) {
        return serviceInfo.applicationInfo.processName.equals(serviceInfo.processName);
    }

    private static void traceInstant(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.am.ServiceRecord serviceRecord) {
        if (!android.os.Trace.isTagEnabled(64L)) {
            return;
        }
        android.os.Trace.instant(64L, str + (serviceRecord.getComponentName() != null ? serviceRecord.getComponentName().toShortString() : "(?)"));
    }

    android.content.ComponentName startServiceLocked(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, int i, int i2, boolean z, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i3, boolean z2, int i4, java.lang.String str4, java.lang.String str5) throws android.os.TransactionTooLargeException {
        return startServiceLocked(iApplicationThread, intent, str, i, i2, z, str2, str3, i3, android.app.BackgroundStartPrivileges.NONE, z2, i4, str4, str5);
    }

    android.content.ComponentName startServiceLocked(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, int i, int i2, boolean z, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i3, android.app.BackgroundStartPrivileges backgroundStartPrivileges) throws android.os.TransactionTooLargeException {
        return startServiceLocked(iApplicationThread, intent, str, i, i2, z, str2, str3, i3, backgroundStartPrivileges, false, -1, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    android.content.ComponentName startServiceLocked(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, int i, int i2, boolean z, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i3, android.app.BackgroundStartPrivileges backgroundStartPrivileges, boolean z2, int i4, java.lang.String str4, java.lang.String str5) throws android.os.TransactionTooLargeException {
        boolean z3;
        int i5;
        boolean z4;
        int i6;
        android.content.Intent intent2;
        int i7;
        java.lang.String str6;
        boolean z5;
        boolean z6;
        boolean z7;
        com.android.server.am.ActiveServices.ServiceLookupResult serviceLookupResult;
        java.lang.String str7;
        int i8;
        int i9;
        int appStartModeLOSP;
        com.android.server.am.ProcessRecord processRecord;
        if (iApplicationThread != null) {
            com.android.server.am.ProcessRecord recordForAppLOSP = this.mAm.getRecordForAppLOSP(iApplicationThread);
            if (recordForAppLOSP == null) {
                throw new java.lang.SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + i + ") when starting service " + intent);
            }
            z3 = recordForAppLOSP.mState.getSetSchedGroup() != 0;
        } else {
            z3 = true;
        }
        com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, str5, z2, i4, str4, str, str2, i, i2, i3, true, z3, false, false, null, false, false);
        if (retrieveServiceLocked == null) {
            return null;
        }
        if (retrieveServiceLocked.record == null) {
            return new android.content.ComponentName("!", retrieveServiceLocked.permission != null ? retrieveServiceLocked.permission : "private to package");
        }
        com.android.server.am.ServiceRecord serviceRecord = retrieveServiceLocked.record;
        traceInstant("startService(): ", serviceRecord);
        setFgsRestrictionLocked(str2, i, i2, intent, serviceRecord, i3, backgroundStartPrivileges, false);
        if (!this.mAm.mUserController.exists(serviceRecord.userId)) {
            android.util.Slog.w("ActivityManager", "Trying to start service with non-existent user! " + serviceRecord.userId);
            return null;
        }
        int i10 = z2 ? i4 : serviceRecord.appInfo.uid;
        java.lang.String str8 = z2 ? str4 : serviceRecord.packageName;
        int i11 = serviceRecord.appInfo.targetSdkVersion;
        if (z2) {
            try {
                try {
                    i5 = android.app.AppGlobals.getPackageManager().getApplicationInfo(str8, 1024L, i3).targetSdkVersion;
                } catch (android.os.RemoteException e) {
                }
            } catch (android.os.RemoteException e2) {
            }
            if (!(!this.mAm.isUidActiveLOSP(i10)) && appRestrictedAnyInBackground(i10, str8) && !isTempAllowedByAlarmClock(i10) && !isDeviceProvisioningPackage(str8)) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (z) {
                i6 = i2;
            } else {
                logFgsBackgroundStart(serviceRecord);
                if (!serviceRecord.isFgsAllowedStart()) {
                    i6 = i2;
                    if (isBgFgsRestrictionEnabled(serviceRecord, i6)) {
                        java.lang.String str9 = "startForegroundService() not allowed due to mAllowStartForeground false: service " + serviceRecord.shortInstanceName;
                        android.util.Slog.w("ActivityManager", str9);
                        showFgsBgRestrictedNotificationLocked(serviceRecord);
                        logFGSStateChangeLocked(serviceRecord, 3, 0, 0, 0, 0, false);
                        if (android.app.compat.CompatChanges.isChangeEnabled(FGS_START_EXCEPTION_CHANGE_ID, i6)) {
                            throw new android.app.ForegroundServiceStartNotAllowedException(str9);
                        }
                        return null;
                    }
                } else {
                    i6 = i2;
                }
            }
            if (z) {
                intent2 = intent;
                i7 = i;
                str6 = str2;
            } else {
                switch (this.mAm.getAppOpsManager().checkOpNoThrow(76, i10, str8)) {
                    case 0:
                    case 3:
                        intent2 = intent;
                        i7 = i;
                        str6 = str2;
                        break;
                    case 1:
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("startForegroundService not allowed due to app op: service ");
                        intent2 = intent;
                        sb.append(intent2);
                        sb.append(" to ");
                        sb.append(serviceRecord.shortInstanceName);
                        sb.append(" from pid=");
                        i7 = i;
                        sb.append(i7);
                        sb.append(" uid=");
                        sb.append(i6);
                        sb.append(" pkg=");
                        str6 = str2;
                        sb.append(str6);
                        android.util.Slog.w("ActivityManager", sb.toString());
                        z6 = true;
                        z5 = false;
                        if (z4 && (serviceRecord.startRequested || z5)) {
                            z7 = z5;
                            i8 = i7;
                            i9 = i5;
                            serviceLookupResult = retrieveServiceLocked;
                            str7 = str6;
                        } else {
                            z7 = z5;
                            int i12 = i5;
                            serviceLookupResult = retrieveServiceLocked;
                            str7 = str6;
                            i8 = i7;
                            i9 = i5;
                            int i13 = i6;
                            appStartModeLOSP = this.mAm.getAppStartModeLOSP(i10, str8, i12, i, false, false, z4);
                            if (appStartModeLOSP != 0) {
                                android.util.Slog.w("ActivityManager", "Background start not allowed: service " + intent + " to " + serviceRecord.shortInstanceName + " from pid=" + i8 + " uid=" + i13 + " pkg=" + str7 + " startFg?=" + z7);
                                if (appStartModeLOSP == 1 || z6) {
                                    return null;
                                }
                                if (z4 && z7) {
                                    return null;
                                }
                                return new android.content.ComponentName("?", "app is in background uid " + this.mAm.mProcessList.getUidRecordLOSP(i10));
                            }
                        }
                        boolean z8 = (i9 >= 26 || !z7) ? z7 : false;
                        synchronized (this.mAm.mPidsSelfLocked) {
                            processRecord = this.mAm.mPidsSelfLocked.get(i8);
                        }
                        java.lang.String str10 = processRecord != null ? processRecord.processName : str7;
                        int curProcState = (processRecord == null || processRecord.getThread() == null || processRecord.isKilled()) ? -1 : processRecord.mState.getCurProcState();
                        serviceRecord.updateProcessStateOnRequest();
                        com.android.server.am.ActiveServices.ServiceLookupResult serviceLookupResult2 = serviceLookupResult;
                        if (deferServiceBringupIfFrozenLocked(serviceRecord, intent, str2, str3, i2, i, str10, curProcState, z8, z3, i3, backgroundStartPrivileges, false, null) || !requestStartTargetPermissionsReviewIfNeededLocked(serviceRecord, str2, str3, i2, intent, z3, i3, false, null)) {
                            return null;
                        }
                        android.content.ComponentName startServiceInnerLocked = startServiceInnerLocked(serviceRecord, intent, i2, i, str10, curProcState, z8, z3, backgroundStartPrivileges, str2);
                        if (serviceLookupResult2.aliasComponent != null && !startServiceInnerLocked.getPackageName().startsWith("!") && !startServiceInnerLocked.getPackageName().startsWith("?")) {
                            return serviceLookupResult2.aliasComponent;
                        }
                        return startServiceInnerLocked;
                    case 2:
                    default:
                        return new android.content.ComponentName("!!", "foreground not allowed as per app op");
                }
            }
            z5 = z;
            z6 = false;
            if (z4) {
            }
            z7 = z5;
            int i122 = i5;
            serviceLookupResult = retrieveServiceLocked;
            str7 = str6;
            i8 = i7;
            i9 = i5;
            int i132 = i6;
            appStartModeLOSP = this.mAm.getAppStartModeLOSP(i10, str8, i122, i, false, false, z4);
            if (appStartModeLOSP != 0) {
            }
        }
        i5 = i11;
        if (!(!this.mAm.isUidActiveLOSP(i10))) {
        }
        z4 = false;
        if (z) {
        }
        if (z) {
        }
        z5 = z;
        z6 = false;
        if (z4) {
        }
        z7 = z5;
        int i1222 = i5;
        serviceLookupResult = retrieveServiceLocked;
        str7 = str6;
        i8 = i7;
        i9 = i5;
        int i1322 = i6;
        appStartModeLOSP = this.mAm.getAppStartModeLOSP(i10, str8, i1222, i, false, false, z4);
        if (appStartModeLOSP != 0) {
        }
    }

    private boolean shouldAllowBootCompletedStart(com.android.server.am.ServiceRecord serviceRecord, int i) {
        if (serviceRecord.mInfoTempFgsAllowListReason != null) {
            int i2 = serviceRecord.mInfoTempFgsAllowListReason.mReasonCode;
        }
        com.android.server.am.Flags.fgsBootCompleted();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:38:0x014f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.content.ComponentName startServiceInnerLocked(com.android.server.am.ServiceRecord serviceRecord, android.content.Intent intent, int i, int i2, java.lang.String str, int i3, boolean z, boolean z2, android.app.BackgroundStartPrivileges backgroundStartPrivileges, java.lang.String str2) throws android.os.TransactionTooLargeException {
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        com.android.server.uri.NeededUriGrants checkGrantUriPermissionFromIntent = this.mAm.mUgmInternal.checkGrantUriPermissionFromIntent(intent, i, serviceRecord.packageName, serviceRecord.userId);
        unscheduleServiceRestartLocked(serviceRecord, i, false);
        boolean z7 = serviceRecord.startRequested;
        serviceRecord.lastActivity = android.os.SystemClock.uptimeMillis();
        serviceRecord.startRequested = true;
        serviceRecord.delayedStop = false;
        serviceRecord.fgRequired = z;
        serviceRecord.pendingStarts.add(new com.android.server.am.ServiceRecord.StartItem(serviceRecord, false, serviceRecord.makeNextStartId(), intent, checkGrantUriPermissionFromIntent, i, str, str2, i3));
        if (serviceRecord.isForeground || serviceRecord.fgRequired) {
            if (!com.android.server.am.ActivityManagerService.doesReasonCodeAllowSchedulingUserInitiatedJobs(serviceRecord.getFgsAllowWiu_forStart()) && !this.mAm.canScheduleUserInitiatedJobs(i, i2, str2)) {
                z6 = false;
                serviceRecord.updateAllowUiJobScheduling(z6);
                z3 = false;
            }
            z6 = true;
            serviceRecord.updateAllowUiJobScheduling(z6);
            z3 = false;
        } else {
            z3 = false;
            serviceRecord.updateAllowUiJobScheduling(false);
        }
        if (!z) {
            z4 = true;
        } else {
            synchronized (this.mAm.mProcessStats.mLock) {
                try {
                    com.android.internal.app.procstats.ServiceState tracker = serviceRecord.getTracker();
                    if (tracker == null) {
                        z4 = true;
                    } else {
                        z4 = true;
                        tracker.setForeground(true, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mAm.mAppOpsService.startOperation(android.app.AppOpsManager.getToken(this.mAm.mAppOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null, true, false, null, false, 0, -1);
        }
        com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
        if (!z2 && !z && serviceRecord.app == null && this.mAm.mUserController.hasStartedUserState(serviceRecord.userId)) {
            com.android.server.am.ProcessRecord processRecordLocked = this.mAm.getProcessRecordLocked(serviceRecord.processName, serviceRecord.appInfo.uid);
            if (processRecordLocked == null || processRecordLocked.mState.getCurProcState() > 11) {
                if (!serviceRecord.delayed) {
                    if (serviceMapLocked.mStartingBackground.size() >= this.mMaxStartingBackground) {
                        android.util.Slog.i("ActivityManager", "Delaying start of: " + serviceRecord);
                        serviceMapLocked.mDelayedStartList.add(serviceRecord);
                        serviceRecord.delayed = z4;
                        return serviceRecord.name;
                    }
                    z5 = z4;
                } else {
                    return serviceRecord.name;
                }
            } else if (processRecordLocked.mState.getCurProcState() >= 10) {
                z5 = z4;
            }
            if (backgroundStartPrivileges.allowsAny()) {
                serviceRecord.allowBgActivityStartsOnServiceStart(backgroundStartPrivileges);
            }
            return startServiceInnerLocked(serviceMapLocked, intent, serviceRecord, z2, z5, i, str, i3, z7, str2);
        }
        z5 = z3;
        if (backgroundStartPrivileges.allowsAny()) {
        }
        return startServiceInnerLocked(serviceMapLocked, intent, serviceRecord, z2, z5, i, str, i3, z7, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean requestStartTargetPermissionsReviewIfNeededLocked(final com.android.server.am.ServiceRecord serviceRecord, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i, final android.content.Intent intent, final boolean z, final int i2, boolean z2, final android.app.IServiceConnection iServiceConnection) {
        if (!this.mAm.getPackageManagerInternal().isPermissionsReviewRequired(serviceRecord.packageName, serviceRecord.userId)) {
            return true;
        }
        if (!z) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("u");
            sb.append(serviceRecord.userId);
            sb.append(z2 ? " Binding" : " Starting");
            sb.append(" a service in package");
            sb.append(serviceRecord.packageName);
            sb.append(" requires a permissions review");
            android.util.Slog.w("ActivityManager", sb.toString());
            return false;
        }
        final android.content.Intent intent2 = new android.content.Intent("android.intent.action.REVIEW_PERMISSIONS");
        intent2.addFlags(411041792);
        intent2.putExtra("android.intent.extra.PACKAGE_NAME", serviceRecord.packageName);
        if (z2) {
            intent2.putExtra("android.intent.extra.REMOTE_CALLBACK", (android.os.Parcelable) new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.am.ActiveServices.2
                public void onResult(android.os.Bundle bundle) {
                    com.android.server.am.ActivityManagerService activityManagerService;
                    com.android.server.am.ActivityManagerService activityManagerService2 = com.android.server.am.ActiveServices.this.mAm;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService2) {
                        try {
                            long clearCallingIdentity = com.android.server.am.ActiveServices.this.mAm.mInjector.clearCallingIdentity();
                            try {
                                if (!com.android.server.am.ActiveServices.this.mPendingServices.contains(serviceRecord)) {
                                    com.android.server.am.ActiveServices.this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                                if (com.android.server.am.ActiveServices.this.mAm.getPackageManagerInternal().isPermissionsReviewRequired(serviceRecord.packageName, serviceRecord.userId)) {
                                    com.android.server.am.ActiveServices.this.unbindServiceLocked(iServiceConnection);
                                } else {
                                    try {
                                        try {
                                            com.android.server.am.ActiveServices.this.bringUpServiceLocked(serviceRecord, intent.getFlags(), z, false, false, false, true, 0);
                                            activityManagerService = com.android.server.am.ActiveServices.this.mAm;
                                        } catch (android.os.RemoteException e) {
                                            activityManagerService = com.android.server.am.ActiveServices.this.mAm;
                                        }
                                        activityManagerService.updateOomAdjPendingTargetsLocked(6);
                                    } catch (java.lang.Throwable th) {
                                        com.android.server.am.ActiveServices.this.mAm.updateOomAdjPendingTargetsLocked(6);
                                        throw th;
                                    }
                                }
                                com.android.server.am.ActiveServices.this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
                                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            } catch (java.lang.Throwable th2) {
                                com.android.server.am.ActiveServices.this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
                                throw th2;
                            }
                        } catch (java.lang.Throwable th3) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th3;
                        }
                    }
                }
            }));
        } else {
            intent2.putExtra("android.intent.extra.INTENT", new android.content.IntentSender(this.mAm.mPendingIntentController.getIntentSender(4, str, str2, i, i2, null, null, 0, new android.content.Intent[]{intent}, new java.lang.String[]{intent.resolveType(this.mAm.mContext.getContentResolver())}, 1409286144, null)));
        }
        this.mAm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ActiveServices.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.am.ActiveServices.this.mAm.mContext.startActivityAsUser(intent2, new android.os.UserHandle(i2));
            }
        });
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    private boolean deferServiceBringupIfFrozenLocked(final com.android.server.am.ServiceRecord serviceRecord, final android.content.Intent intent, final java.lang.String str, @android.annotation.Nullable final java.lang.String str2, final int i, final int i2, final java.lang.String str3, final int i3, final boolean z, final boolean z2, final int i4, final android.app.BackgroundStartPrivileges backgroundStartPrivileges, final boolean z3, final android.app.IServiceConnection iServiceConnection) {
        java.util.ArrayList<java.lang.Runnable> arrayList;
        if (!this.mAm.getPackageManagerInternal().isPackageFrozen(serviceRecord.packageName, i, serviceRecord.userId)) {
            return false;
        }
        java.util.ArrayList<java.lang.Runnable> arrayList2 = this.mPendingBringups.get(serviceRecord);
        if (arrayList2 != null) {
            arrayList = arrayList2;
        } else {
            java.util.ArrayList<java.lang.Runnable> arrayList3 = new java.util.ArrayList<>();
            this.mPendingBringups.put(serviceRecord, arrayList3);
            arrayList = arrayList3;
        }
        arrayList.add(new java.lang.Runnable() { // from class: com.android.server.am.ActiveServices.4
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.am.ActivityManagerService activityManagerService;
                com.android.server.am.ActivityManagerService activityManagerService2 = com.android.server.am.ActiveServices.this.mAm;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService2) {
                    try {
                        if (!com.android.server.am.ActiveServices.this.mPendingBringups.containsKey(serviceRecord)) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (!com.android.server.am.ActiveServices.this.requestStartTargetPermissionsReviewIfNeededLocked(serviceRecord, str, str2, i, intent, z2, i4, z3, iServiceConnection)) {
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (z3) {
                            try {
                                com.android.server.am.ActiveServices.this.bringUpServiceLocked(serviceRecord, intent.getFlags(), z2, false, false, false, true, 0);
                                activityManagerService = com.android.server.am.ActiveServices.this.mAm;
                            } catch (android.os.TransactionTooLargeException e) {
                                activityManagerService = com.android.server.am.ActiveServices.this.mAm;
                            } catch (java.lang.Throwable th) {
                                com.android.server.am.ActiveServices.this.mAm.updateOomAdjPendingTargetsLocked(6);
                                throw th;
                            }
                            activityManagerService.updateOomAdjPendingTargetsLocked(6);
                        } else {
                            try {
                                com.android.server.am.ActiveServices.this.startServiceInnerLocked(serviceRecord, intent, i, i2, str3, i3, z, z2, backgroundStartPrivileges, str);
                            } catch (android.os.TransactionTooLargeException e2) {
                            }
                        }
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    } catch (java.lang.Throwable th2) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th2;
                    }
                }
            }
        });
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void schedulePendingServiceStartLocked(java.lang.String str, int i) {
        int size = this.mPendingBringups.size();
        int i2 = size - 1;
        while (i2 >= 0 && size > 0) {
            com.android.server.am.ServiceRecord keyAt = this.mPendingBringups.keyAt(i2);
            if (keyAt.userId == i && android.text.TextUtils.equals(keyAt.packageName, str)) {
                java.util.ArrayList<java.lang.Runnable> valueAt = this.mPendingBringups.valueAt(i2);
                if (valueAt != null) {
                    for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                        valueAt.get(size2).run();
                    }
                    valueAt.clear();
                }
                int size3 = this.mPendingBringups.size();
                this.mPendingBringups.remove(keyAt);
                if (size != size3) {
                    size = this.mPendingBringups.size();
                    i2 = size - 1;
                } else {
                    size = this.mPendingBringups.size();
                    i2--;
                }
            } else {
                i2--;
            }
        }
    }

    android.content.ComponentName startServiceInnerLocked(com.android.server.am.ActiveServices.ServiceMap serviceMap, android.content.Intent intent, com.android.server.am.ServiceRecord serviceRecord, boolean z, boolean z2, int i, java.lang.String str, int i2, boolean z3, java.lang.String str2) throws android.os.TransactionTooLargeException {
        boolean z4;
        boolean z5;
        int i3;
        int i4;
        synchronized (this.mAm.mProcessStats.mLock) {
            try {
                com.android.internal.app.procstats.ServiceState tracker = serviceRecord.getTracker();
                z4 = true;
                if (tracker != null) {
                    tracker.setStarted(true, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        serviceRecord.callStart = false;
        int i5 = serviceRecord.appInfo.uid;
        java.lang.String packageName = serviceRecord.name.getPackageName();
        java.lang.String className = serviceRecord.name.getClassName();
        com.android.internal.util.FrameworkStatsLog.write(99, i5, packageName, className, 1);
        this.mAm.mBatteryStatsService.noteServiceStartRunning(i5, packageName, className);
        java.lang.String bringUpServiceLocked = bringUpServiceLocked(serviceRecord, intent.getFlags(), z, false, false, false, true, 0);
        this.mAm.updateOomAdjPendingTargetsLocked(6);
        if (bringUpServiceLocked != null) {
            return new android.content.ComponentName("!!", bringUpServiceLocked);
        }
        if ((serviceRecord.appInfo.flags & 2097152) != 0) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5) {
            i3 = 2;
        } else {
            i3 = 1;
        }
        java.lang.String action = intent.getAction();
        if (serviceRecord.app == null || serviceRecord.app.getThread() == null) {
            i4 = 3;
        } else if (z3 || !serviceRecord.getConnections().isEmpty()) {
            i4 = 2;
        } else {
            i4 = 1;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, i5, i, action, 1, false, i4, getShortProcessNameForStats(i, str), getShortServiceNameForStats(serviceRecord), i3, packageName, str2, i2, serviceRecord.mProcessStateOnRequest);
        if (serviceRecord.startRequested && z2) {
            if (serviceMap.mStartingBackground.size() != 0) {
                z4 = false;
            }
            serviceMap.mStartingBackground.add(serviceRecord);
            serviceRecord.startingBgTimeout = android.os.SystemClock.uptimeMillis() + this.mAm.mConstants.BG_START_TIMEOUT;
            if (z4) {
                serviceMap.rescheduleDelayedStartsLocked();
            }
        } else if (z || serviceRecord.fgRequired) {
            serviceMap.ensureNotStartingBackgroundLocked(serviceRecord);
        }
        return serviceRecord.name;
    }

    @android.annotation.Nullable
    private java.lang.String getShortProcessNameForStats(int i, java.lang.String str) {
        java.lang.String[] packagesForUid = this.mAm.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length == 1) {
            if (android.text.TextUtils.equals(packagesForUid[0], str)) {
                return null;
            }
            if (str != null && str.startsWith(packagesForUid[0])) {
                return str.substring(packagesForUid[0].length());
            }
        }
        return str;
    }

    @android.annotation.Nullable
    private java.lang.String getShortServiceNameForStats(@android.annotation.NonNull com.android.server.am.ServiceRecord serviceRecord) {
        android.content.ComponentName componentName = serviceRecord.getComponentName();
        if (componentName != null) {
            return componentName.getShortClassName();
        }
        return null;
    }

    private void stopServiceLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z) {
        traceInstant("stopService(): ", serviceRecord);
        try {
            android.os.Trace.traceBegin(64L, "stopServiceLocked()");
            if (serviceRecord.delayed) {
                serviceRecord.delayedStop = true;
                return;
            }
            maybeStopShortFgsTimeoutLocked(serviceRecord);
            maybeStopFgsTimeoutLocked(serviceRecord);
            int i = serviceRecord.appInfo.uid;
            java.lang.String packageName = serviceRecord.name.getPackageName();
            java.lang.String className = serviceRecord.name.getClassName();
            com.android.internal.util.FrameworkStatsLog.write(99, i, packageName, className, 2);
            this.mAm.mBatteryStatsService.noteServiceStopRunning(i, packageName, className);
            serviceRecord.startRequested = false;
            if (serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                }
            }
            serviceRecord.callStart = false;
            bringDownServiceIfNeededLocked(serviceRecord, false, false, z, "stopService");
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    int stopServiceLocked(android.app.IApplicationThread iApplicationThread, android.content.Intent intent, java.lang.String str, int i, boolean z, int i2, java.lang.String str2, java.lang.String str3) {
        com.android.server.am.ProcessRecord recordForAppLOSP = this.mAm.getRecordForAppLOSP(iApplicationThread);
        if (iApplicationThread != null && recordForAppLOSP == null) {
            throw new java.lang.SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + this.mAm.mInjector.getCallingPid() + ") when stopping service " + intent);
        }
        com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, str3, z, i2, str2, str, null, this.mAm.mInjector.getCallingPid(), this.mAm.mInjector.getCallingUid(), i, false, false, false, false, null, false, false);
        if (retrieveServiceLocked == null) {
            return 0;
        }
        if (retrieveServiceLocked.record != null) {
            long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
            try {
                stopServiceLocked(retrieveServiceLocked.record, false);
                this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } catch (java.lang.Throwable th) {
                this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return -1;
    }

    void stopInBackgroundLocked(int i) {
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(android.os.UserHandle.getUserId(i));
        if (serviceMap != null) {
            java.util.ArrayList arrayList = null;
            for (int size = serviceMap.mServicesByInstanceName.size() - 1; size >= 0; size--) {
                com.android.server.am.ServiceRecord valueAt = serviceMap.mServicesByInstanceName.valueAt(size);
                if (valueAt.appInfo.uid == i && valueAt.startRequested && this.mAm.getAppStartModeLOSP(valueAt.appInfo.uid, valueAt.packageName, valueAt.appInfo.targetSdkVersion, -1, false, false, false) != 0) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    java.lang.String str = valueAt.shortInstanceName;
                    com.android.server.am.EventLogTags.writeAmStopIdleService(valueAt.appInfo.uid, str);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
                    sb.append("Stopping service due to app idle: ");
                    android.os.UserHandle.formatUid(sb, valueAt.appInfo.uid);
                    sb.append(" ");
                    android.util.TimeUtils.formatDuration(valueAt.createRealTime - android.os.SystemClock.elapsedRealtime(), sb);
                    sb.append(" ");
                    sb.append(str);
                    android.util.Slog.w("ActivityManager", sb.toString());
                    arrayList.add(valueAt);
                    if (appRestrictedAnyInBackground(valueAt.appInfo.uid, valueAt.packageName)) {
                        cancelForegroundNotificationLocked(valueAt);
                    }
                }
            }
            if (arrayList != null) {
                int size2 = arrayList.size();
                for (int i2 = size2 - 1; i2 >= 0; i2--) {
                    com.android.server.am.ServiceRecord serviceRecord = (com.android.server.am.ServiceRecord) arrayList.get(i2);
                    serviceRecord.delayed = false;
                    serviceMap.ensureNotStartingBackgroundLocked(serviceRecord);
                    stopServiceLocked(serviceRecord, true);
                }
                if (size2 > 0) {
                    this.mAm.updateOomAdjPendingTargetsLocked(18);
                }
            }
        }
    }

    void killMisbehavingService(com.android.server.am.ServiceRecord serviceRecord, int i, int i2, java.lang.String str, int i3) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                if (!serviceRecord.destroying) {
                    stopServiceLocked(serviceRecord, false);
                } else {
                    com.android.server.am.ServiceRecord remove = getServiceMapLocked(serviceRecord.userId).mServicesByInstanceName.remove(serviceRecord.instanceName);
                    if (remove != null) {
                        stopServiceLocked(remove, false);
                    }
                }
                this.mAm.crashApplicationWithType(i, i2, str, -1, "Bad notification for startForeground", true, i3);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    android.os.IBinder peekServiceLocked(android.content.Intent intent, java.lang.String str, java.lang.String str2) {
        com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, null, str, str2, this.mAm.mInjector.getCallingPid(), this.mAm.mInjector.getCallingUid(), android.os.UserHandle.getCallingUserId(), false, false, false, false, false, false);
        if (retrieveServiceLocked != null) {
            if (retrieveServiceLocked.record == null) {
                throw new java.lang.SecurityException("Permission Denial: Accessing service from pid=" + this.mAm.mInjector.getCallingPid() + ", uid=" + this.mAm.mInjector.getCallingUid() + " requires " + retrieveServiceLocked.permission);
            }
            com.android.server.am.IntentBindRecord intentBindRecord = retrieveServiceLocked.record.bindings.get(retrieveServiceLocked.record.intent);
            if (intentBindRecord != null) {
                return intentBindRecord.binder;
            }
        }
        return null;
    }

    boolean stopServiceTokenLocked(android.content.ComponentName componentName, android.os.IBinder iBinder, int i) {
        com.android.server.am.ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, android.os.UserHandle.getCallingUserId());
        if (findServiceLocked == null) {
            return false;
        }
        if (i >= 0) {
            com.android.server.am.ServiceRecord.StartItem findDeliveredStart = findServiceLocked.findDeliveredStart(i, false, false);
            if (findDeliveredStart != null) {
                while (findServiceLocked.deliveredStarts.size() > 0) {
                    com.android.server.am.ServiceRecord.StartItem remove = findServiceLocked.deliveredStarts.remove(0);
                    remove.removeUriPermissionsLocked();
                    if (remove == findDeliveredStart) {
                        break;
                    }
                }
            }
            if (findServiceLocked.getLastStartId() != i) {
                return false;
            }
            if (findServiceLocked.deliveredStarts.size() > 0) {
                android.util.Slog.w("ActivityManager", "stopServiceToken startId " + i + " is last, but have " + findServiceLocked.deliveredStarts.size() + " remaining args");
            }
        }
        maybeStopShortFgsTimeoutLocked(findServiceLocked);
        maybeStopFgsTimeoutLocked(findServiceLocked);
        int i2 = findServiceLocked.appInfo.uid;
        java.lang.String packageName = findServiceLocked.name.getPackageName();
        java.lang.String className = findServiceLocked.name.getClassName();
        com.android.internal.util.FrameworkStatsLog.write(99, i2, packageName, className, 2);
        this.mAm.mBatteryStatsService.noteServiceStopRunning(i2, packageName, className);
        findServiceLocked.startRequested = false;
        if (findServiceLocked.tracker != null) {
            synchronized (this.mAm.mProcessStats.mLock) {
                findServiceLocked.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
            }
        }
        findServiceLocked.callStart = false;
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        bringDownServiceIfNeededLocked(findServiceLocked, false, false, false, "stopServiceToken");
        this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    public void setServiceForegroundLocked(android.content.ComponentName componentName, android.os.IBinder iBinder, int i, android.app.Notification notification, int i2, int i3) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        int callingUid = this.mAm.mInjector.getCallingUid();
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        try {
            com.android.server.am.ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, callingUserId);
            if (findServiceLocked != null) {
                setServiceForegroundInnerLocked(findServiceLocked, i, notification, i2, i3, callingUid);
            }
        } finally {
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getForegroundServiceTypeLocked(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        int i;
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        try {
            com.android.server.am.ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, callingUserId);
            if (findServiceLocked == null) {
                i = 0;
            } else {
                i = findServiceLocked.foregroundServiceType;
            }
            return i;
        } finally {
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean foregroundAppShownEnoughLocked(com.android.server.am.ActiveServices.ActiveForegroundApp activeForegroundApp, long j) {
        long j2;
        activeForegroundApp.mHideTime = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        if (activeForegroundApp.mShownWhileTop) {
            return true;
        }
        if (this.mScreenOn || activeForegroundApp.mShownWhileScreenOn) {
            long j3 = activeForegroundApp.mStartVisibleTime;
            if (activeForegroundApp.mStartTime != activeForegroundApp.mStartVisibleTime) {
                j2 = this.mAm.mConstants.FGSERVICE_SCREEN_ON_AFTER_TIME;
            } else {
                j2 = this.mAm.mConstants.FGSERVICE_MIN_SHOWN_TIME;
            }
            long j4 = j3 + j2;
            if (j >= j4) {
                return true;
            }
            long j5 = j + this.mAm.mConstants.FGSERVICE_MIN_REPORT_TIME;
            if (j5 > j4) {
                j4 = j5;
            }
            activeForegroundApp.mHideTime = j4;
            return false;
        }
        long j6 = activeForegroundApp.mEndTime + this.mAm.mConstants.FGSERVICE_SCREEN_ON_BEFORE_TIME;
        if (j >= j6) {
            return true;
        }
        activeForegroundApp.mHideTime = j6;
        return false;
    }

    void updateForegroundApps(com.android.server.am.ActiveServices.ServiceMap serviceMap) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                if (serviceMap != null) {
                    serviceMap.mPendingRemoveForegroundApps.clear();
                    long j = Long.MAX_VALUE;
                    for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                        com.android.server.am.ActiveServices.ActiveForegroundApp valueAt = serviceMap.mActiveForegroundApps.valueAt(size);
                        if (valueAt.mEndTime != 0) {
                            if (foregroundAppShownEnoughLocked(valueAt, elapsedRealtime)) {
                                serviceMap.mPendingRemoveForegroundApps.add(serviceMap.mActiveForegroundApps.keyAt(size));
                                serviceMap.mActiveForegroundAppsChanged = true;
                            } else if (valueAt.mHideTime < j) {
                                j = valueAt.mHideTime;
                            }
                        }
                        if (!valueAt.mAppOnTop && !isForegroundServiceAllowedInBackgroundRestricted(valueAt.mUid, valueAt.mPackageName)) {
                            stopAllForegroundServicesLocked(valueAt.mUid, valueAt.mPackageName);
                        }
                    }
                    for (int size2 = serviceMap.mPendingRemoveForegroundApps.size() - 1; size2 >= 0; size2--) {
                        serviceMap.mActiveForegroundApps.remove(serviceMap.mPendingRemoveForegroundApps.get(size2));
                    }
                    serviceMap.removeMessages(2);
                    if (j < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                        serviceMap.sendMessageAtTime(serviceMap.obtainMessage(2), (j + android.os.SystemClock.uptimeMillis()) - android.os.SystemClock.elapsedRealtime());
                    }
                }
                serviceMap.mActiveForegroundAppsChanged = false;
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    private void requestUpdateActiveForegroundAppsLocked(com.android.server.am.ActiveServices.ServiceMap serviceMap, long j) {
        android.os.Message obtainMessage = serviceMap.obtainMessage(2);
        if (j != 0) {
            serviceMap.sendMessageAtTime(obtainMessage, (j + android.os.SystemClock.uptimeMillis()) - android.os.SystemClock.elapsedRealtime());
        } else {
            serviceMap.mActiveForegroundAppsChanged = true;
            serviceMap.sendMessage(obtainMessage);
        }
    }

    private void decActiveForegroundAppLocked(com.android.server.am.ActiveServices.ServiceMap serviceMap, com.android.server.am.ServiceRecord serviceRecord) {
        com.android.server.am.ActiveServices.ActiveForegroundApp activeForegroundApp = serviceMap.mActiveForegroundApps.get(serviceRecord.packageName);
        if (activeForegroundApp != null) {
            activeForegroundApp.mNumActive--;
            if (activeForegroundApp.mNumActive <= 0) {
                activeForegroundApp.mEndTime = android.os.SystemClock.elapsedRealtime();
                if (foregroundAppShownEnoughLocked(activeForegroundApp, activeForegroundApp.mEndTime)) {
                    serviceMap.mActiveForegroundApps.remove(serviceRecord.packageName);
                    serviceMap.mActiveForegroundAppsChanged = true;
                    requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
                } else if (activeForegroundApp.mHideTime < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                    requestUpdateActiveForegroundAppsLocked(serviceMap, activeForegroundApp.mHideTime);
                }
            }
        }
    }

    void updateScreenStateLocked(boolean z) {
        if (this.mScreenOn != z) {
            this.mScreenOn = z;
            if (z) {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                for (int size = this.mServiceMap.size() - 1; size >= 0; size--) {
                    com.android.server.am.ActiveServices.ServiceMap valueAt = this.mServiceMap.valueAt(size);
                    boolean z2 = false;
                    long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                    for (int size2 = valueAt.mActiveForegroundApps.size() - 1; size2 >= 0; size2--) {
                        com.android.server.am.ActiveServices.ActiveForegroundApp valueAt2 = valueAt.mActiveForegroundApps.valueAt(size2);
                        if (valueAt2.mEndTime == 0) {
                            if (!valueAt2.mShownWhileScreenOn) {
                                valueAt2.mShownWhileScreenOn = true;
                                valueAt2.mStartVisibleTime = elapsedRealtime;
                            }
                        } else {
                            if (!valueAt2.mShownWhileScreenOn && valueAt2.mStartVisibleTime == valueAt2.mStartTime) {
                                valueAt2.mStartVisibleTime = elapsedRealtime;
                                valueAt2.mEndTime = elapsedRealtime;
                            }
                            if (foregroundAppShownEnoughLocked(valueAt2, elapsedRealtime)) {
                                valueAt.mActiveForegroundApps.remove(valueAt2.mPackageName);
                                valueAt.mActiveForegroundAppsChanged = true;
                                z2 = true;
                            } else if (valueAt2.mHideTime < j) {
                                j = valueAt2.mHideTime;
                            }
                        }
                    }
                    if (z2) {
                        requestUpdateActiveForegroundAppsLocked(valueAt, 0L);
                    } else if (j < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                        requestUpdateActiveForegroundAppsLocked(valueAt, j);
                    }
                }
            }
        }
    }

    void foregroundServiceProcStateChangedLocked(com.android.server.am.UidRecord uidRecord) {
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(android.os.UserHandle.getUserId(uidRecord.getUid()));
        if (serviceMap != null) {
            boolean z = false;
            for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                com.android.server.am.ActiveServices.ActiveForegroundApp valueAt = serviceMap.mActiveForegroundApps.valueAt(size);
                if (valueAt.mUid == uidRecord.getUid()) {
                    if (uidRecord.getCurProcState() <= 2) {
                        if (!valueAt.mAppOnTop) {
                            valueAt.mAppOnTop = true;
                            z = true;
                        }
                        valueAt.mShownWhileTop = true;
                    } else if (valueAt.mAppOnTop) {
                        valueAt.mAppOnTop = false;
                        z = true;
                    }
                }
            }
            if (z) {
                requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
            }
        }
    }

    private boolean isForegroundServiceAllowedInBackgroundRestricted(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ProcessStateRecord processStateRecord = processRecord.mState;
        if (!isDeviceProvisioningPackage(processRecord.info.packageName) && processStateRecord.isBackgroundRestricted() && processStateRecord.getSetProcState() > 3) {
            return processStateRecord.getSetProcState() == 4 && processStateRecord.isSetBoundByNonBgRestrictedApp();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isForegroundServiceAllowedInBackgroundRestricted(int i, java.lang.String str) {
        com.android.server.am.ProcessRecord processInPackage;
        com.android.server.am.UidRecord uidRecordLOSP = this.mAm.mProcessList.getUidRecordLOSP(i);
        return (uidRecordLOSP == null || (processInPackage = uidRecordLOSP.getProcessInPackage(str)) == null || !isForegroundServiceAllowedInBackgroundRestricted(processInPackage)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTempAllowedByAlarmClock(int i) {
        com.android.server.am.ActivityManagerService.FgsTempAllowListItem isAllowlistedForFgsStartLOSP = this.mAm.isAllowlistedForFgsStartLOSP(i);
        return isAllowlistedForFgsStartLOSP != null && isAllowlistedForFgsStartLOSP.mReasonCode == 301;
    }

    void logFgsApiBeginLocked(int i, int i2, int i3) {
        synchronized (this.mFGSLogger) {
            this.mFGSLogger.logForegroundServiceApiEventBegin(i, i2, i3, "");
        }
    }

    void logFgsApiEndLocked(int i, int i2, int i3) {
        synchronized (this.mFGSLogger) {
            this.mFGSLogger.logForegroundServiceApiEventEnd(i, i2, i3);
        }
    }

    void logFgsApiStateChangedLocked(int i, int i2, int i3, int i4) {
        synchronized (this.mFGSLogger) {
            this.mFGSLogger.logForegroundServiceApiStateChanged(i, i2, i3, i4);
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    @com.android.internal.annotations.GuardedBy({"mAm"})
    private void setServiceForegroundInnerLocked(com.android.server.am.ServiceRecord r40, int r41, android.app.Notification r42, int r43, int r44, int r45) {
        /*
            Method dump skipped, instructions count: 2024
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActiveServices.setServiceForegroundInnerLocked(com.android.server.am.ServiceRecord, int, android.app.Notification, int, int, int):void");
    }

    private boolean withinFgsDeferRateLimit(com.android.server.am.ServiceRecord serviceRecord, long j) {
        if (j < serviceRecord.fgDisplayTime) {
            return false;
        }
        return j < this.mFgsDeferralEligible.get(serviceRecord.appInfo.uid, 0L);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @android.annotation.NonNull
    private android.util.Pair<java.lang.Integer, java.lang.RuntimeException> validateForegroundServiceType(com.android.server.am.ServiceRecord serviceRecord, int i, int i2, int i3) {
        java.lang.Object obj;
        android.app.ForegroundServiceTypePolicy defaultPolicy = android.app.ForegroundServiceTypePolicy.getDefaultPolicy();
        android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = defaultPolicy.getForegroundServiceTypePolicyInfo(i, i2);
        int checkForegroundServiceTypePolicy = defaultPolicy.checkForegroundServiceTypePolicy(this.mAm.mContext, serviceRecord.packageName, serviceRecord.app.uid, serviceRecord.app.getPid(), serviceRecord.isFgsAllowedWiu_forStart(), foregroundServiceTypePolicyInfo);
        switch (checkForegroundServiceTypePolicy) {
            case 2:
                java.lang.String str = "Starting FGS with type " + android.content.pm.ServiceInfo.foregroundServiceTypeToLabel(i) + " code=" + checkForegroundServiceTypePolicy + " callerApp=" + serviceRecord.app + " targetSDK=" + serviceRecord.app.info.targetSdkVersion;
                android.util.Slog.wtfQuiet("ActivityManager", str);
                android.util.Slog.w("ActivityManager", str);
                obj = null;
                break;
            case 3:
                if (i3 == -1 && i == 0) {
                    obj = new android.app.MissingForegroundServiceTypeException("Starting FGS without a type  callerApp=" + serviceRecord.app + " targetSDK=" + serviceRecord.app.info.targetSdkVersion);
                    break;
                } else {
                    obj = new android.app.InvalidForegroundServiceTypeException("Starting FGS with type " + android.content.pm.ServiceInfo.foregroundServiceTypeToLabel(i) + " callerApp=" + serviceRecord.app + " targetSDK=" + serviceRecord.app.info.targetSdkVersion + " has been prohibited");
                    break;
                }
            case 4:
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Starting FGS with type ");
                sb.append(android.content.pm.ServiceInfo.foregroundServiceTypeToLabel(i));
                sb.append(" code=");
                sb.append(checkForegroundServiceTypePolicy);
                sb.append(" callerApp=");
                sb.append(serviceRecord.app);
                sb.append(" targetSDK=");
                sb.append(serviceRecord.app.info.targetSdkVersion);
                sb.append(" requiredPermissions=");
                sb.append(foregroundServiceTypePolicyInfo.toPermissionString());
                sb.append(foregroundServiceTypePolicyInfo.hasForegroundOnlyPermission() ? " and the app must be in the eligible state/exemptions to access the foreground only permission" : "");
                java.lang.String sb2 = sb.toString();
                android.util.Slog.wtfQuiet("ActivityManager", sb2);
                android.util.Slog.w("ActivityManager", sb2);
                obj = null;
                break;
            case 5:
                java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                sb3.append("Starting FGS with type ");
                sb3.append(android.content.pm.ServiceInfo.foregroundServiceTypeToLabel(i));
                sb3.append(" callerApp=");
                sb3.append(serviceRecord.app);
                sb3.append(" targetSDK=");
                sb3.append(serviceRecord.app.info.targetSdkVersion);
                sb3.append(" requires permissions: ");
                sb3.append(foregroundServiceTypePolicyInfo.toPermissionString());
                sb3.append(foregroundServiceTypePolicyInfo.hasForegroundOnlyPermission() ? " and the app must be in the eligible state/exemptions to access the foreground only permission" : "");
                obj = new java.lang.SecurityException(sb3.toString());
                break;
            default:
                obj = null;
                break;
        }
        return android.util.Pair.create(java.lang.Integer.valueOf(checkForegroundServiceTypePolicy), obj);
    }

    private class SystemExemptedFgsTypePermission extends android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        SystemExemptedFgsTypePermission() {
            super("System exempted");
        }

        public int checkPermission(@android.annotation.NonNull android.content.Context context, int i, int i2, @android.annotation.NonNull java.lang.String str, boolean z) {
            com.android.server.am.AppRestrictionController appRestrictionController = com.android.server.am.ActiveServices.this.mAm.mAppRestrictionController;
            int potentialSystemExemptionReason = appRestrictionController.getPotentialSystemExemptionReason(i);
            if (potentialSystemExemptionReason == -1 && (potentialSystemExemptionReason = appRestrictionController.getPotentialSystemExemptionReason(i, str)) == -1) {
                potentialSystemExemptionReason = appRestrictionController.getPotentialUserAllowedExemptionReason(i, str);
            }
            if (potentialSystemExemptionReason == -1 && com.android.internal.util.ArrayUtils.contains(com.android.server.am.ActiveServices.this.mAm.getPackageManagerInternal().getKnownPackageNames(2, 0), str)) {
                potentialSystemExemptionReason = 326;
            }
            switch (potentialSystemExemptionReason) {
                case 10:
                case 11:
                case 51:
                case 55:
                case 56:
                case 63:
                case 65:
                case 300:
                case 319:
                case 320:
                case 321:
                case 322:
                case 323:
                case com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ACTIVE_DEVICE_ADMIN /* 324 */:
                case 326:
                case com.android.internal.util.FrameworkStatsLog.TIF_TUNE_CHANGED /* 327 */:
                    return 0;
                default:
                    return -1;
            }
        }
    }

    private void initSystemExemptedFgsTypePermission() {
        android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = android.app.ForegroundServiceTypePolicy.getDefaultPolicy().getForegroundServiceTypePolicyInfo(1024, 0);
        if (foregroundServiceTypePolicyInfo != null) {
            foregroundServiceTypePolicyInfo.setCustomPermission(new com.android.server.am.ActiveServices.SystemExemptedFgsTypePermission());
        }
    }

    private class MediaProjectionFgsTypeCustomPermission extends android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePermission {
        MediaProjectionFgsTypeCustomPermission() {
            super("Media projection screen capture permission");
        }

        public int checkPermission(@android.annotation.NonNull android.content.Context context, int i, int i2, @android.annotation.NonNull java.lang.String str, boolean z) {
            return com.android.server.am.ActiveServices.this.mAm.isAllowedMediaProjectionNoOpCheck(i) ? 0 : -1;
        }
    }

    private void initMediaProjectFgsTypeCustomPermission() {
        android.app.ForegroundServiceTypePolicy.ForegroundServiceTypePolicyInfo foregroundServiceTypePolicyInfo = android.app.ForegroundServiceTypePolicy.getDefaultPolicy().getForegroundServiceTypePolicyInfo(32, 0);
        if (foregroundServiceTypePolicyInfo != null) {
            foregroundServiceTypePolicyInfo.setCustomPermission(new com.android.server.am.ActiveServices.MediaProjectionFgsTypeCustomPermission());
        }
    }

    android.app.ActivityManagerInternal.ServiceNotificationPolicy applyForegroundServiceNotificationLocked(android.app.Notification notification, java.lang.String str, int i, java.lang.String str2, int i2) {
        if (str != null) {
            return android.app.ActivityManagerInternal.ServiceNotificationPolicy.NOT_FOREGROUND_SERVICE;
        }
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i2);
        if (serviceMap == null) {
            return android.app.ActivityManagerInternal.ServiceNotificationPolicy.NOT_FOREGROUND_SERVICE;
        }
        for (int i3 = 0; i3 < serviceMap.mServicesByInstanceName.size(); i3++) {
            com.android.server.am.ServiceRecord valueAt = serviceMap.mServicesByInstanceName.valueAt(i3);
            if (valueAt.isForeground && i == valueAt.foregroundId && str2.equals(valueAt.appInfo.packageName)) {
                notification.flags |= 64;
                valueAt.foregroundNoti = notification;
                if (shouldShowFgsNotificationLocked(valueAt)) {
                    valueAt.mFgsNotificationDeferred = false;
                    return android.app.ActivityManagerInternal.ServiceNotificationPolicy.SHOW_IMMEDIATELY;
                }
                startFgsDeferralTimerLocked(valueAt);
                return android.app.ActivityManagerInternal.ServiceNotificationPolicy.UPDATE_ONLY;
            }
        }
        return android.app.ActivityManagerInternal.ServiceNotificationPolicy.NOT_FOREGROUND_SERVICE;
    }

    private boolean shouldShowFgsNotificationLocked(com.android.server.am.ServiceRecord serviceRecord) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (!this.mAm.mConstants.mFlagFgsNotificationDeferralEnabled) {
            return true;
        }
        if ((serviceRecord.mFgsNotificationDeferred && uptimeMillis >= serviceRecord.fgDisplayTime) || withinFgsDeferRateLimit(serviceRecord, uptimeMillis)) {
            return true;
        }
        if (this.mAm.mConstants.mFlagFgsNotificationDeferralApiGated) {
            if (serviceRecord.appInfo.targetSdkVersion < 31) {
                return true;
            }
        }
        if (serviceRecord.mFgsNotificationShown) {
            return true;
        }
        return !serviceRecord.foregroundNoti.isForegroundDisplayForceDeferred() && (serviceRecord.foregroundNoti.shouldShowForegroundImmediately() || (serviceRecord.foregroundServiceType & 54) != 0);
    }

    private void startFgsDeferralTimerLocked(com.android.server.am.ServiceRecord serviceRecord) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        int i = serviceRecord.appInfo.uid;
        long j = uptimeMillis + (serviceRecord.isShortFgs() ? this.mAm.mConstants.mFgsNotificationDeferralIntervalForShort : this.mAm.mConstants.mFgsNotificationDeferralInterval);
        boolean z = false;
        for (int i2 = 0; i2 < this.mPendingFgsNotifications.size(); i2++) {
            com.android.server.am.ServiceRecord serviceRecord2 = this.mPendingFgsNotifications.get(i2);
            if (serviceRecord2 == serviceRecord) {
                return;
            }
            if (i == serviceRecord2.appInfo.uid) {
                j = java.lang.Math.min(j, serviceRecord2.fgDisplayTime);
            }
        }
        if (this.mFgsDeferralRateLimited) {
            this.mFgsDeferralEligible.put(i, (serviceRecord.isShortFgs() ? this.mAm.mConstants.mFgsNotificationDeferralExclusionTimeForShort : this.mAm.mConstants.mFgsNotificationDeferralExclusionTime) + j);
        }
        serviceRecord.fgDisplayTime = j;
        serviceRecord.mFgsNotificationDeferred = true;
        serviceRecord.mFgsNotificationWasDeferred = true;
        serviceRecord.mFgsNotificationShown = false;
        this.mPendingFgsNotifications.add(serviceRecord);
        if (serviceRecord.appInfo.targetSdkVersion < 31) {
            z = true;
        }
        if (z) {
            android.util.Slog.i("ActivityManager", "Deferring FGS notification in legacy app " + serviceRecord.appInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.UserHandle.formatUid(serviceRecord.appInfo.uid) + " : " + serviceRecord.foregroundNoti);
        }
        this.mAm.mHandler.postAtTime(this.mPostDeferredFGSNotifications, j);
    }

    boolean enableFgsNotificationRateLimitLocked(boolean z) {
        if (z != this.mFgsDeferralRateLimited) {
            this.mFgsDeferralRateLimited = z;
            if (!z) {
                this.mFgsDeferralEligible.clear();
            }
        }
        return z;
    }

    private void removeServiceNotificationDeferralsLocked(java.lang.String str, int i) {
        for (int size = this.mPendingFgsNotifications.size() - 1; size >= 0; size--) {
            com.android.server.am.ServiceRecord serviceRecord = this.mPendingFgsNotifications.get(size);
            if (i == serviceRecord.userId && serviceRecord.appInfo.packageName.equals(str)) {
                this.mPendingFgsNotifications.remove(size);
            }
        }
    }

    public void onForegroundServiceNotificationUpdateLocked(boolean z, android.app.Notification notification, int i, java.lang.String str, int i2) {
        int i3;
        int size = this.mPendingFgsNotifications.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            com.android.server.am.ServiceRecord serviceRecord = this.mPendingFgsNotifications.get(size);
            if (i2 == serviceRecord.userId && i == serviceRecord.foregroundId && serviceRecord.appInfo.packageName.equals(str) && z) {
                serviceRecord.mFgsNotificationShown = true;
                serviceRecord.mFgsNotificationDeferred = false;
                this.mPendingFgsNotifications.remove(size);
            }
            size--;
        }
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i2);
        if (serviceMap != null) {
            for (i3 = 0; i3 < serviceMap.mServicesByInstanceName.size(); i3++) {
                com.android.server.am.ServiceRecord valueAt = serviceMap.mServicesByInstanceName.valueAt(i3);
                if (valueAt.isForeground && i == valueAt.foregroundId && valueAt.appInfo.packageName.equals(str)) {
                    valueAt.foregroundNoti = notification;
                }
            }
        }
    }

    private void registerAppOpCallbackLocked(@android.annotation.NonNull com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord.app == null) {
            return;
        }
        int i = serviceRecord.appInfo.uid;
        com.android.server.am.ActiveServices.AppOpCallback appOpCallback = this.mFgsAppOpCallbacks.get(i);
        if (appOpCallback == null) {
            appOpCallback = new com.android.server.am.ActiveServices.AppOpCallback(serviceRecord.app, this.mAm.getAppOpsManager());
            this.mFgsAppOpCallbacks.put(i, appOpCallback);
        }
        appOpCallback.registerLocked();
    }

    private void unregisterAppOpCallbackLocked(@android.annotation.NonNull com.android.server.am.ServiceRecord serviceRecord) {
        int i = serviceRecord.appInfo.uid;
        com.android.server.am.ActiveServices.AppOpCallback appOpCallback = this.mFgsAppOpCallbacks.get(i);
        if (appOpCallback != null) {
            appOpCallback.unregisterLocked();
            if (appOpCallback.isObsoleteLocked()) {
                this.mFgsAppOpCallbacks.remove(i);
            }
        }
    }

    private static final class AppOpCallback {
        private static final int[] LOGGED_AP_OPS = {0, 1, 27, 26};
        private final android.app.AppOpsManager mAppOpsManager;
        private final com.android.server.am.ProcessRecord mProcessRecord;

        @com.android.internal.annotations.GuardedBy({"mCounterLock"})
        private final android.util.SparseIntArray mAcceptedOps = new android.util.SparseIntArray();

        @com.android.internal.annotations.GuardedBy({"mCounterLock"})
        private final android.util.SparseIntArray mRejectedOps = new android.util.SparseIntArray();
        private final java.lang.Object mCounterLock = new java.lang.Object();
        private final android.util.SparseIntArray mAppOpModes = new android.util.SparseIntArray();

        @com.android.internal.annotations.GuardedBy({"mAm"})
        private int mNumFgs = 0;

        @com.android.internal.annotations.GuardedBy({"mAm"})
        private boolean mDestroyed = false;
        private final android.app.AppOpsManager.OnOpNotedInternalListener mOpNotedCallback = new android.app.AppOpsManager.OnOpNotedInternalListener() { // from class: com.android.server.am.ActiveServices.AppOpCallback.1
            public void onOpNoted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4) {
                com.android.server.am.ActiveServices.AppOpCallback.this.incrementOpCountIfNeeded(i, i2, i4);
            }
        };
        private final android.app.AppOpsManager.OnOpStartedListener mOpStartedCallback = new android.app.AppOpsManager.OnOpStartedListener() { // from class: com.android.server.am.ActiveServices.AppOpCallback.2
            public void onOpStarted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4) {
                com.android.server.am.ActiveServices.AppOpCallback.this.incrementOpCountIfNeeded(i, i2, i4);
            }
        };

        AppOpCallback(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, @android.annotation.NonNull android.app.AppOpsManager appOpsManager) {
            this.mProcessRecord = processRecord;
            this.mAppOpsManager = appOpsManager;
            for (int i : LOGGED_AP_OPS) {
                this.mAppOpModes.put(i, appOpsManager.unsafeCheckOpRawNoThrow(i, processRecord.uid, processRecord.info.packageName));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void incrementOpCountIfNeeded(int i, int i2, int i3) {
            if (i2 == this.mProcessRecord.uid && isNotTop()) {
                incrementOpCount(i, i3 == 0);
            }
        }

        private boolean isNotTop() {
            return this.mProcessRecord.mState.getCurProcState() != 2;
        }

        private void incrementOpCount(int i, boolean z) {
            synchronized (this.mCounterLock) {
                try {
                    android.util.SparseIntArray sparseIntArray = z ? this.mAcceptedOps : this.mRejectedOps;
                    int indexOfKey = sparseIntArray.indexOfKey(i);
                    if (indexOfKey >= 0) {
                        sparseIntArray.setValueAt(indexOfKey, sparseIntArray.valueAt(indexOfKey) + 1);
                    } else {
                        sparseIntArray.put(i, 1);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void registerLocked() {
            if (isObsoleteLocked()) {
                android.util.Slog.wtf("ActivityManager", "Trying to register on a stale AppOpCallback.");
                return;
            }
            this.mNumFgs++;
            if (this.mNumFgs == 1) {
                this.mAppOpsManager.startWatchingNoted(LOGGED_AP_OPS, this.mOpNotedCallback);
                this.mAppOpsManager.startWatchingStarted(LOGGED_AP_OPS, this.mOpStartedCallback);
            }
        }

        void unregisterLocked() {
            this.mNumFgs--;
            if (this.mNumFgs <= 0) {
                this.mDestroyed = true;
                logFinalValues();
                this.mAppOpsManager.stopWatchingNoted(this.mOpNotedCallback);
                this.mAppOpsManager.stopWatchingStarted(this.mOpStartedCallback);
            }
        }

        boolean isObsoleteLocked() {
            return this.mDestroyed;
        }

        private void logFinalValues() {
            synchronized (this.mCounterLock) {
                try {
                    for (int i : LOGGED_AP_OPS) {
                        int i2 = this.mAcceptedOps.get(i);
                        int i3 = this.mRejectedOps.get(i);
                        if (i2 > 0 || i3 > 0) {
                            com.android.internal.util.FrameworkStatsLog.write(256, this.mProcessRecord.uid, i, modeToEnum(this.mAppOpModes.get(i)), i2, i3);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private static int modeToEnum(int i) {
            switch (i) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                case 3:
                default:
                    return 0;
                case 4:
                    return 3;
            }
        }
    }

    private void cancelForegroundNotificationLocked(com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord.foregroundNoti != null) {
            com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
            if (serviceMapLocked != null) {
                for (int size = serviceMapLocked.mServicesByInstanceName.size() - 1; size >= 0; size--) {
                    com.android.server.am.ServiceRecord valueAt = serviceMapLocked.mServicesByInstanceName.valueAt(size);
                    if (valueAt != serviceRecord && valueAt.isForeground && valueAt.foregroundId == serviceRecord.foregroundId && valueAt.packageName.equals(serviceRecord.packageName)) {
                        return;
                    }
                }
            }
            serviceRecord.cancelNotification();
        }
    }

    private void updateServiceForegroundLocked(com.android.server.am.ProcessServiceRecord processServiceRecord, boolean z) {
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            com.android.server.am.ServiceRecord runningServiceAt = processServiceRecord.getRunningServiceAt(numberOfRunningServices);
            if (runningServiceAt.isForeground || runningServiceAt.fgRequired) {
                int i2 = runningServiceAt.foregroundServiceType | i;
                if (runningServiceAt.foregroundServiceType != 0) {
                    z3 = true;
                    i = i2;
                } else {
                    z2 = true;
                    z3 = true;
                    i = i2;
                }
            }
        }
        this.mAm.updateProcessForegroundLocked(processServiceRecord.mApp, z3, i, z2, z);
        processServiceRecord.setHasReportedForegroundServices(z3);
    }

    void unscheduleShortFgsTimeoutLocked(com.android.server.am.ServiceRecord serviceRecord) {
        this.mShortFGSAnrTimer.cancel(serviceRecord);
        this.mAm.mHandler.removeMessages(77, serviceRecord);
        this.mAm.mHandler.removeMessages(76, serviceRecord);
    }

    private void maybeUpdateShortFgsTrackingLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z) {
        if (!serviceRecord.isShortFgs()) {
            serviceRecord.clearShortFgsInfo();
            unscheduleShortFgsTimeoutLocked(serviceRecord);
            return;
        }
        boolean hasShortFgsInfo = serviceRecord.hasShortFgsInfo();
        if (z || !hasShortFgsInfo) {
            traceInstant("short FGS start/extend: ", serviceRecord);
            serviceRecord.setShortFgsInfo(android.os.SystemClock.uptimeMillis());
            unscheduleShortFgsTimeoutLocked(serviceRecord);
            this.mAm.mHandler.sendMessageAtTime(this.mAm.mHandler.obtainMessage(76, serviceRecord), serviceRecord.getShortFgsInfo().getTimeoutTime());
            return;
        }
        serviceRecord.getShortFgsInfo().update();
    }

    private void maybeStopShortFgsTimeoutLocked(com.android.server.am.ServiceRecord serviceRecord) {
        serviceRecord.clearShortFgsInfo();
        if (!serviceRecord.isShortFgs()) {
            return;
        }
        unscheduleShortFgsTimeoutLocked(serviceRecord);
    }

    void onShortFgsTimeout(com.android.server.am.ServiceRecord serviceRecord) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (!serviceRecord.shouldTriggerShortFgsTimeout(uptimeMillis)) {
                    this.mShortFGSAnrTimer.discard(serviceRecord);
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                android.util.Slog.e("ActivityManager", "Short FGS timed out: " + serviceRecord);
                this.mShortFGSAnrTimer.accept(serviceRecord);
                traceInstant("short FGS timeout: ", serviceRecord);
                logFGSStateChangeLocked(serviceRecord, 5, uptimeMillis > serviceRecord.mFgsEnterTime ? (int) (uptimeMillis - serviceRecord.mFgsEnterTime) : 0, 0, 0, 0, false);
                try {
                    serviceRecord.app.getThread().scheduleTimeoutService(serviceRecord, serviceRecord.getShortFgsInfo().getStartId());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("ActivityManager", "Exception from scheduleTimeoutService: " + e.toString());
                }
                this.mAm.mHandler.sendMessageAtTime(this.mAm.mHandler.obtainMessage(77, serviceRecord), serviceRecord.getShortFgsInfo().getProcStateDemoteTime());
                this.mShortFGSAnrTimer.start(serviceRecord, serviceRecord.getShortFgsInfo().getAnrTime() - android.os.SystemClock.uptimeMillis());
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    boolean shouldServiceTimeOutLocked(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        try {
            com.android.server.am.ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, callingUserId);
            if (findServiceLocked != null) {
                return findServiceLocked.shouldTriggerShortFgsTimeout(android.os.SystemClock.uptimeMillis());
            }
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void onShortFgsProcstateTimeout(com.android.server.am.ServiceRecord serviceRecord) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                if (!serviceRecord.shouldDemoteShortFgsProcState(android.os.SystemClock.uptimeMillis())) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                android.util.Slog.e("ActivityManager", "Short FGS procstate demoted: " + serviceRecord);
                traceInstant("short FGS demote: ", serviceRecord);
                this.mAm.updateOomAdjLocked(serviceRecord.app, 13);
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void onShortFgsAnrTimeout(com.android.server.am.ServiceRecord serviceRecord) {
        com.android.internal.os.TimeoutRecord forShortFgsTimeout = com.android.internal.os.TimeoutRecord.forShortFgsTimeout("A foreground service of FOREGROUND_SERVICE_TYPE_SHORT_SERVICE did not stop within a timeout: " + serviceRecord.getComponentName());
        forShortFgsTimeout.mLatencyTracker.waitingOnAMSLockStarted();
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                forShortFgsTimeout.mLatencyTracker.waitingOnAMSLockEnded();
                if (!serviceRecord.shouldTriggerShortFgsAnr(android.os.SystemClock.uptimeMillis())) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                android.util.Slog.e("ActivityManager", "Short FGS ANR'ed: " + serviceRecord);
                traceInstant("short FGS ANR: ", serviceRecord);
                this.mAm.appNotResponding(serviceRecord.app, forShortFgsTimeout);
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void onFgsTimeout(com.android.server.am.ServiceRecord serviceRecord) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                int timedOutFgsType = serviceRecord.getTimedOutFgsType(uptimeMillis);
                if (timedOutFgsType == -1) {
                    this.mFGSAnrTimer.discard(serviceRecord);
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                android.util.Slog.e("ActivityManager", "FGS (" + android.content.pm.ServiceInfo.foregroundServiceTypeToLabel(timedOutFgsType) + ") timed out: " + serviceRecord);
                this.mFGSAnrTimer.accept(serviceRecord);
                traceInstant("FGS timed out: ", serviceRecord);
                logFGSStateChangeLocked(serviceRecord, 5, uptimeMillis > serviceRecord.mFgsEnterTime ? (int) (uptimeMillis - serviceRecord.mFgsEnterTime) : 0, 0, 0, 0, false);
                try {
                    serviceRecord.app.getThread().scheduleTimeoutServiceForType(serviceRecord, serviceRecord.getLastStartId(), timedOutFgsType);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("ActivityManager", "Exception from scheduleTimeoutServiceForType: " + e);
                }
                long longValue = (((java.lang.Long) serviceRecord.getEarliestStopTypeAndTime().second).longValue() + this.mAm.mConstants.mFgsAnrExtraWaitDuration) - android.os.SystemClock.uptimeMillis();
                if (android.app.Flags.introduceNewServiceOntimeoutCallback()) {
                    this.mFGSAnrTimer.start(serviceRecord, longValue);
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void maybeUpdateFgsTrackingLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z) {
        if (!serviceRecord.isFgsTimeLimited()) {
            serviceRecord.setIsFgsTimeLimited(false);
            this.mFGSAnrTimer.cancel(serviceRecord);
            this.mAm.mHandler.removeMessages(84, serviceRecord);
        } else if (z || !serviceRecord.wasFgsPreviouslyTimeLimited()) {
            traceInstant("FGS start: ", serviceRecord);
            serviceRecord.setIsFgsTimeLimited(true);
            this.mFGSAnrTimer.cancel(serviceRecord);
            this.mAm.mHandler.removeMessages(84, serviceRecord);
            this.mAm.mHandler.sendMessageAtTime(this.mAm.mHandler.obtainMessage(84, serviceRecord), ((java.lang.Long) serviceRecord.getEarliestStopTypeAndTime().second).longValue());
        }
    }

    private void maybeStopFgsTimeoutLocked(com.android.server.am.ServiceRecord serviceRecord) {
        serviceRecord.setIsFgsTimeLimited(false);
        if (!serviceRecord.isFgsTimeLimited()) {
            return;
        }
        android.util.Slog.d("ActivityManager", "Stop FGS timeout: " + serviceRecord);
        this.mFGSAnrTimer.cancel(serviceRecord);
        this.mAm.mHandler.removeMessages(84, serviceRecord);
    }

    boolean hasServiceTimedOutLocked(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        try {
            com.android.server.am.ServiceRecord findServiceLocked = findServiceLocked(componentName, iBinder, callingUserId);
            if (findServiceLocked == null) {
                return false;
            }
            return findServiceLocked.getTimedOutFgsType(android.os.SystemClock.uptimeMillis()) != -1;
        } finally {
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void onFgsAnrTimeout(com.android.server.am.ServiceRecord serviceRecord) {
        int timedOutFgsType = serviceRecord.getTimedOutFgsType(android.os.SystemClock.uptimeMillis());
        if (timedOutFgsType == -1 || !serviceRecord.wasFgsPreviouslyTimeLimited()) {
            return;
        }
        com.android.internal.os.TimeoutRecord forFgsTimeout = com.android.internal.os.TimeoutRecord.forFgsTimeout("A foreground service of type " + android.content.pm.ServiceInfo.foregroundServiceTypeToLabel(timedOutFgsType) + " did not stop within a timeout: " + serviceRecord.getComponentName());
        forFgsTimeout.mLatencyTracker.waitingOnAMSLockStarted();
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                forFgsTimeout.mLatencyTracker.waitingOnAMSLockEnded();
                android.util.Slog.e("ActivityManager", "FGS ANR'ed: " + serviceRecord);
                traceInstant("FGS ANR: ", serviceRecord);
                this.mAm.appNotResponding(serviceRecord.app, forFgsTimeout);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    private void updateAllowlistManagerLocked(com.android.server.am.ProcessServiceRecord processServiceRecord) {
        processServiceRecord.mAllowlistManager = false;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            if (processServiceRecord.getRunningServiceAt(numberOfRunningServices).allowlistManager) {
                processServiceRecord.mAllowlistManager = true;
                return;
            }
        }
    }

    private void stopServiceAndUpdateAllowlistManagerLocked(com.android.server.am.ServiceRecord serviceRecord) {
        maybeStopShortFgsTimeoutLocked(serviceRecord);
        maybeStopFgsTimeoutLocked(serviceRecord);
        com.android.server.am.ProcessServiceRecord processServiceRecord = serviceRecord.app.mServices;
        processServiceRecord.stopService(serviceRecord);
        processServiceRecord.updateBoundClientUids();
        if (serviceRecord.allowlistManager) {
            updateAllowlistManagerLocked(processServiceRecord);
        }
    }

    void updateServiceConnectionActivitiesLocked(com.android.server.am.ProcessServiceRecord processServiceRecord) {
        android.util.ArraySet arraySet = null;
        for (int i = 0; i < processServiceRecord.numberOfConnections(); i++) {
            com.android.server.am.ProcessRecord processRecord = processServiceRecord.getConnectionAt(i).binding.service.app;
            if (processRecord != null && processRecord != processServiceRecord.mApp) {
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet();
                } else if (arraySet.contains(processRecord)) {
                }
                arraySet.add(processRecord);
                updateServiceClientActivitiesLocked(processRecord.mServices, null, false);
            }
        }
    }

    private boolean updateServiceClientActivitiesLocked(com.android.server.am.ProcessServiceRecord processServiceRecord, com.android.server.am.ConnectionRecord connectionRecord, boolean z) {
        if (connectionRecord != null && connectionRecord.binding.client != null && !connectionRecord.binding.client.hasActivities()) {
            return false;
        }
        boolean z2 = false;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0 && !z2; numberOfRunningServices--) {
            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = processServiceRecord.getRunningServiceAt(numberOfRunningServices).getConnections();
            for (int size = connections.size() - 1; size >= 0 && !z2; size--) {
                java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
                int size2 = valueAt.size() - 1;
                while (true) {
                    if (size2 >= 0) {
                        com.android.server.am.ConnectionRecord connectionRecord2 = valueAt.get(size2);
                        if (connectionRecord2.binding.client != null && connectionRecord2.binding.client != processServiceRecord.mApp && connectionRecord2.binding.client.hasActivities()) {
                            z2 = true;
                            break;
                        }
                        size2--;
                    }
                }
            }
        }
        if (z2 == processServiceRecord.hasClientActivities()) {
            return false;
        }
        processServiceRecord.setHasClientActivities(z2);
        if (z) {
            this.mAm.updateLruProcessLocked(processServiceRecord.mApp, z2, null);
        }
        return true;
    }

    int bindServiceLocked(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.app.IServiceConnection iServiceConnection, long j, java.lang.String str2, boolean z, int i, java.lang.String str3, android.app.IApplicationThread iApplicationThread2, java.lang.String str4, int i2) throws android.os.TransactionTooLargeException {
        com.android.server.wm.ActivityServiceConnectionsHolder activityServiceConnectionsHolder;
        android.content.Intent intent2;
        int i3;
        android.app.PendingIntent pendingIntent;
        com.android.server.am.ProcessRecord processRecord;
        long j2;
        com.android.server.am.ActiveServices activeServices;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        com.android.server.am.ConnectionRecord connectionRecord;
        int serviceBindingOomAdjPolicyForAddLocked;
        com.android.server.am.ProcessRecord processRecord2;
        boolean z6;
        boolean z7;
        int callingPid = this.mAm.mInjector.getCallingPid();
        int callingUid = this.mAm.mInjector.getCallingUid();
        com.android.server.am.ProcessRecord recordForAppLOSP = this.mAm.getRecordForAppLOSP(iApplicationThread);
        if (recordForAppLOSP == null) {
            throw new java.lang.SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + callingPid + ") when binding service " + intent);
        }
        if (iBinder != null) {
            com.android.server.wm.ActivityServiceConnectionsHolder serviceConnectionsHolder = this.mAm.mAtmInternal.getServiceConnectionsHolder(iBinder);
            if (serviceConnectionsHolder == null) {
                android.util.Slog.w("ActivityManager", "Binding with unknown activity: " + iBinder);
                return 0;
            }
            activityServiceConnectionsHolder = serviceConnectionsHolder;
        } else {
            activityServiceConnectionsHolder = null;
        }
        boolean z8 = recordForAppLOSP.info.uid == 1000;
        if (z8) {
            intent.setDefusable(true);
            android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) intent.getParcelableExtra("android.intent.extra.client_intent");
            if (pendingIntent2 != null) {
                int intExtra = intent.getIntExtra("android.intent.extra.client_label", 0);
                if (intExtra != 0) {
                    intent2 = intent.cloneFilter();
                    pendingIntent = pendingIntent2;
                    i3 = intExtra;
                } else {
                    intent2 = intent;
                    pendingIntent = pendingIntent2;
                    i3 = intExtra;
                }
            } else {
                intent2 = intent;
                pendingIntent = pendingIntent2;
                i3 = 0;
            }
        } else {
            intent2 = intent;
            i3 = 0;
            pendingIntent = null;
        }
        if ((j & 134217728) != 0) {
            this.mAm.enforceCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "BIND_TREAT_LIKE_ACTIVITY");
        }
        if ((j & 524288) != 0 && !z8) {
            throw new java.lang.SecurityException("Non-system caller (pid=" + callingPid + ") set BIND_SCHEDULE_LIKE_TOP_APP when binding service " + intent2);
        }
        if ((j & 16777216) != 0 && !z8) {
            throw new java.lang.SecurityException("Non-system caller " + iApplicationThread + " (pid=" + callingPid + ") set BIND_ALLOW_WHITELIST_MANAGEMENT when binding service " + intent2);
        }
        long j3 = j & 4194304;
        if (j3 != 0 && !z8) {
            throw new java.lang.SecurityException("Non-system caller " + iApplicationThread + " (pid=" + callingPid + ") set BIND_ALLOW_INSTANT when binding service " + intent2);
        }
        if ((j & 65536) != 0 && !z8) {
            throw new java.lang.SecurityException("Non-system caller (pid=" + callingPid + ") set BIND_ALMOST_PERCEPTIBLE when binding service " + intent2);
        }
        if ((j & 1048576) != 0) {
            this.mAm.enforceCallingPermission("android.permission.START_ACTIVITIES_FROM_BACKGROUND", "BIND_ALLOW_BACKGROUND_ACTIVITY_STARTS");
        }
        if ((j & 262144) != 0) {
            this.mAm.enforceCallingPermission("android.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND", "BIND_ALLOW_FOREGROUND_SERVICE_STARTS_FROM_BACKGROUND");
        }
        boolean z9 = recordForAppLOSP.mState.getSetSchedGroup() != 0;
        boolean z10 = ((j & java.lang.Integer.toUnsignedLong(Integer.MIN_VALUE)) == 0 && (j & 4611686018427387904L) == 0) ? false : true;
        boolean z11 = j3 != 0;
        boolean z12 = (j & 8192) != 0;
        boolean z13 = (j & 16384) != 0 && android.content.flags.Flags.enableBindPackageIsolatedProcess();
        boolean z14 = (j & 8589934592L) != 0;
        com.android.server.am.ProcessRecord recordForAppLOSP2 = i > 0 ? this.mAm.getRecordForAppLOSP(iApplicationThread2) : null;
        android.content.Intent intent3 = intent2;
        com.android.server.wm.ActivityServiceConnectionsHolder activityServiceConnectionsHolder2 = activityServiceConnectionsHolder;
        com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent2, str2, z, i, str3, str, str4, callingPid, callingUid, i2, true, z9, z10, z11, null, z12, z13, z14);
        if (retrieveServiceLocked == null) {
            return 0;
        }
        if (retrieveServiceLocked.record == null) {
            return -1;
        }
        com.android.server.am.ServiceRecord serviceRecord = retrieveServiceLocked.record;
        com.android.server.am.AppBindRecord retrieveAppBindingLocked = serviceRecord.retrieveAppBindingLocked(intent3, recordForAppLOSP, recordForAppLOSP2);
        com.android.server.am.ProcessServiceRecord processServiceRecord = retrieveAppBindingLocked.client.mServices;
        if (processServiceRecord.numberOfConnections() >= this.mAm.mConstants.mMaxServiceConnectionsPerProcess) {
            android.util.Slog.w("ActivityManager", "bindService exceeded max service connection number per process, callerApp:" + recordForAppLOSP.processName + " intent:" + intent3);
            return 0;
        }
        synchronized (this.mAm.mPidsSelfLocked) {
            processRecord = this.mAm.mPidsSelfLocked.get(callingPid);
        }
        java.lang.String str5 = processRecord != null ? processRecord.processName : str4;
        int curProcState = (processRecord == null || processRecord.getThread() == null || processRecord.isKilled()) ? -1 : processRecord.mState.getCurProcState();
        serviceRecord.updateProcessStateOnRequest();
        boolean deferServiceBringupIfFrozenLocked = deferServiceBringupIfFrozenLocked(serviceRecord, intent3, str4, null, callingUid, callingPid, str5, curProcState, false, z9, i2, android.app.BackgroundStartPrivileges.NONE, true, iServiceConnection);
        boolean z15 = (deferServiceBringupIfFrozenLocked || requestStartTargetPermissionsReviewIfNeededLocked(serviceRecord, str4, null, callingUid, intent3, z9, i2, true, iServiceConnection)) ? false : true;
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        try {
            unscheduleServiceRestartLocked(serviceRecord, recordForAppLOSP.info.uid, false);
            if ((j & 1) != 0) {
                serviceRecord.lastActivity = android.os.SystemClock.uptimeMillis();
                if (serviceRecord.hasAutoCreateConnections()) {
                    z2 = true;
                } else {
                    synchronized (this.mAm.mProcessStats.mLock) {
                        try {
                            com.android.internal.app.procstats.ServiceState tracker = serviceRecord.getTracker();
                            if (tracker != null) {
                                z2 = true;
                                tracker.setBound(true, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                            } else {
                                z2 = true;
                            }
                        } finally {
                        }
                    }
                }
            } else {
                z2 = true;
            }
            if ((j & 2097152) != 0) {
                this.mAm.requireAllowedAssociationsLocked(serviceRecord.appInfo.packageName);
            }
            z3 = (serviceRecord.appInfo.flags & 2097152) != 0 ? z2 : false;
            z4 = serviceRecord.startRequested;
            z5 = !serviceRecord.getConnections().isEmpty() ? z2 : false;
            this.mAm.startAssociationLocked(recordForAppLOSP.uid, recordForAppLOSP.processName, recordForAppLOSP.mState.getCurProcState(), serviceRecord.appInfo.uid, serviceRecord.appInfo.longVersionCode, serviceRecord.instanceName, serviceRecord.processName);
            this.mAm.grantImplicitAccess(recordForAppLOSP.userId, intent3, recordForAppLOSP.uid, android.os.UserHandle.getAppId(serviceRecord.appInfo.uid));
            boolean z16 = z15;
            try {
                connectionRecord = new com.android.server.am.ConnectionRecord(retrieveAppBindingLocked, activityServiceConnectionsHolder2, iServiceConnection, j, i3, pendingIntent, recordForAppLOSP.uid, recordForAppLOSP.processName, str4, retrieveServiceLocked.aliasComponent);
                android.os.IBinder asBinder = iServiceConnection.asBinder();
                serviceRecord.addConnection(asBinder, connectionRecord);
                retrieveAppBindingLocked.connections.add(connectionRecord);
                if (activityServiceConnectionsHolder2 != null) {
                    activityServiceConnectionsHolder2.addConnection(connectionRecord);
                }
                processServiceRecord.addConnection(connectionRecord);
                connectionRecord.startAssociationIfNeeded();
                if (retrieveAppBindingLocked.client != serviceRecord.app && connectionRecord.hasFlag(8)) {
                    processServiceRecord.setHasAboveClient(true);
                }
                if (connectionRecord.hasFlag(16777216)) {
                    serviceRecord.allowlistManager = true;
                }
                if (connectionRecord.hasFlag(1048576)) {
                    serviceRecord.setAllowedBgActivityStartsByBinding(true);
                }
                if (connectionRecord.hasFlag(32768)) {
                    serviceRecord.isNotAppComponentUsage = true;
                }
                if (serviceRecord.app != null && serviceRecord.app.mState != null && serviceRecord.app.mState.getCurProcState() <= 2 && connectionRecord.hasFlag(65536)) {
                    serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs = android.os.SystemClock.uptimeMillis();
                }
                if (serviceRecord.app != null) {
                    updateServiceClientActivitiesLocked(serviceRecord.app.mServices, connectionRecord, true);
                }
                java.util.ArrayList<com.android.server.am.ConnectionRecord> arrayList = this.mServiceConnections.get(asBinder);
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList<>();
                    this.mServiceConnections.put(asBinder, arrayList);
                }
                arrayList.add(connectionRecord);
                com.android.server.am.ProcessRecord processRecordLocked = (serviceRecord.serviceInfo.flags & 2) != 0 ? null : this.mAm.getProcessRecordLocked(serviceRecord.processName, serviceRecord.appInfo.uid);
                serviceBindingOomAdjPolicyForAddLocked = processRecordLocked != null ? getServiceBindingOomAdjPolicyForAddLocked(retrieveAppBindingLocked.client, processRecordLocked, connectionRecord) : 0;
                if ((deferServiceBringupIfFrozenLocked || z16 || (serviceBindingOomAdjPolicyForAddLocked & 8) == 0 || !recordForAppLOSP.isFreezable()) ? false : true) {
                    processRecord2 = recordForAppLOSP;
                    this.mAm.mOomAdjuster.updateAppFreezeStateLSP(processRecord2, 4, true);
                } else {
                    processRecord2 = recordForAppLOSP;
                }
                if (connectionRecord.hasFlag(1)) {
                    try {
                        serviceRecord.lastActivity = android.os.SystemClock.uptimeMillis();
                        z6 = (serviceBindingOomAdjPolicyForAddLocked & 1) == 0;
                        if (bringUpServiceLocked(serviceRecord, intent3.getFlags(), z9, false, z16, deferServiceBringupIfFrozenLocked, true, serviceBindingOomAdjPolicyForAddLocked) != null) {
                            this.mAm.updateOomAdjPendingTargetsLocked(4);
                            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
                            return 0;
                        }
                        j2 = clearCallingIdentity;
                        z7 = false;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        j2 = clearCallingIdentity;
                        activeServices = this;
                        activeServices.mAm.mInjector.restoreCallingIdentity(j2);
                        throw th;
                    }
                } else {
                    j2 = clearCallingIdentity;
                    z7 = false;
                    z6 = false;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                activeServices = this;
                j2 = clearCallingIdentity;
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            j2 = clearCallingIdentity;
            activeServices = this;
            activeServices.mAm.mInjector.restoreCallingIdentity(j2);
            throw th;
        }
        try {
            setFgsRestrictionLocked(str4, callingPid, callingUid, intent3, serviceRecord, i2, android.app.BackgroundStartPrivileges.NONE, true);
            if (serviceRecord.app != null) {
                com.android.server.am.ProcessServiceRecord processServiceRecord2 = serviceRecord.app.mServices;
                if (connectionRecord.hasFlag(134217728)) {
                    processServiceRecord2.setTreatLikeActivity(true);
                }
                if (serviceRecord.allowlistManager) {
                    processServiceRecord2.mAllowlistManager = true;
                }
                this.mAm.updateLruProcessLocked(serviceRecord.app, (!(processRecord2.hasActivitiesOrRecentTasks() && processServiceRecord2.hasClientActivities()) && (processRecord2.mState.getCurProcState() > 2 || !connectionRecord.hasFlag(134217728))) ? z7 : true, retrieveAppBindingLocked.client);
                if (!serviceRecord.mOomAdjBumpedInExec && (serviceBindingOomAdjPolicyForAddLocked & 4) == 0) {
                    this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
                    z6 = true;
                }
            }
            if (z6) {
                this.mAm.updateOomAdjPendingTargetsLocked(4);
            }
            activeServices = this;
            try {
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.SERVICE_REQUEST_EVENT_REPORTED, serviceRecord.appInfo.uid, callingUid, com.android.server.am.ActivityManagerService.getShortAction(intent3.getAction()), 2, false, (serviceRecord.app == null || serviceRecord.app.getThread() == null) ? 3 : (z4 || z5) ? 2 : 1, getShortProcessNameForStats(callingUid, processRecord2.processName), getShortServiceNameForStats(serviceRecord), z3 ? 2 : 1, serviceRecord.packageName, processRecord2.info.packageName, processRecord2.mState.getCurProcState(), serviceRecord.mProcessStateOnRequest);
                if (serviceRecord.app != null && retrieveAppBindingLocked.intent.received) {
                    try {
                        connectionRecord.conn.connected(retrieveServiceLocked.aliasComponent != null ? retrieveServiceLocked.aliasComponent : serviceRecord.name, retrieveAppBindingLocked.intent.binder, false);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.w("ActivityManager", "Failure sending service " + serviceRecord.shortInstanceName + " to connection " + connectionRecord.conn.asBinder() + " (in " + connectionRecord.binding.client.processName + ")", e);
                    }
                    if (retrieveAppBindingLocked.intent.apps.size() == 1 && retrieveAppBindingLocked.intent.doRebind) {
                        requestServiceBindingLocked(serviceRecord, retrieveAppBindingLocked.intent, z9, true, serviceBindingOomAdjPolicyForAddLocked);
                    }
                } else if (!retrieveAppBindingLocked.intent.requested) {
                    requestServiceBindingLocked(serviceRecord, retrieveAppBindingLocked.intent, z9, false, serviceBindingOomAdjPolicyForAddLocked);
                    activeServices.maybeLogBindCrossProfileService(i2, str4, processRecord2.info.uid);
                    activeServices.getServiceMapLocked(serviceRecord.userId).ensureNotStartingBackgroundLocked(serviceRecord);
                    activeServices.mAm.mInjector.restoreCallingIdentity(j2);
                    activeServices.notifyBindingServiceEventLocked(processRecord2, str4);
                    return 1;
                }
                activeServices.maybeLogBindCrossProfileService(i2, str4, processRecord2.info.uid);
                activeServices.getServiceMapLocked(serviceRecord.userId).ensureNotStartingBackgroundLocked(serviceRecord);
                activeServices.mAm.mInjector.restoreCallingIdentity(j2);
                activeServices.notifyBindingServiceEventLocked(processRecord2, str4);
                return 1;
            } catch (java.lang.Throwable th4) {
                th = th4;
                activeServices.mAm.mInjector.restoreCallingIdentity(j2);
                throw th;
            }
        } catch (java.lang.Throwable th5) {
            th = th5;
            activeServices = this;
            activeServices.mAm.mInjector.restoreCallingIdentity(j2);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    private void notifyBindingServiceEventLocked(com.android.server.am.ProcessRecord processRecord, java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo = processRecord.info;
        if (applicationInfo != null) {
            str = applicationInfo.packageName;
        }
        if (str != null) {
            this.mAm.mHandler.obtainMessage(75, processRecord.uid, 0, str).sendToTarget();
        }
    }

    private void maybeLogBindCrossProfileService(int i, java.lang.String str, int i2) {
        int userId;
        if (android.os.UserHandle.isCore(i2) || (userId = android.os.UserHandle.getUserId(i2)) == i || !this.mAm.mUserController.isSameProfileGroup(userId, i)) {
            return;
        }
        android.app.admin.DevicePolicyEventLogger.createEvent(151).setStrings(new java.lang.String[]{str}).write();
    }

    void publishServiceLocked(com.android.server.am.ServiceRecord serviceRecord, android.content.Intent intent, android.os.IBinder iBinder) {
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        if (serviceRecord != null) {
            try {
                android.content.Intent.FilterComparison filterComparison = new android.content.Intent.FilterComparison(intent);
                com.android.server.am.IntentBindRecord intentBindRecord = serviceRecord.bindings.get(filterComparison);
                if (intentBindRecord != null && !intentBindRecord.received) {
                    intentBindRecord.binder = iBinder;
                    intentBindRecord.requested = true;
                    intentBindRecord.received = true;
                    android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = serviceRecord.getConnections();
                    for (int size = connections.size() - 1; size >= 0; size--) {
                        java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
                        for (int i = 0; i < valueAt.size(); i++) {
                            com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i);
                            if (filterComparison.equals(connectionRecord.binding.intent.intent)) {
                                try {
                                    connectionRecord.conn.connected(connectionRecord.aliasComponent != null ? connectionRecord.aliasComponent : serviceRecord.name, iBinder, false);
                                } catch (java.lang.Exception e) {
                                    android.util.Slog.w("ActivityManager", "Failure sending service " + serviceRecord.shortInstanceName + " to connection " + connectionRecord.conn.asBinder() + " (in " + connectionRecord.binding.client.processName + ")", e);
                                }
                            }
                        }
                    }
                }
                boolean contains = this.mDestroyingServices.contains(serviceRecord);
                com.android.server.am.Flags.serviceBindingOomAdjPolicy();
                serviceDoneExecutingLocked(serviceRecord, contains, false, false, 20);
            } catch (java.lang.Throwable th) {
                this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
    }

    void updateServiceGroupLocked(android.app.IServiceConnection iServiceConnection, int i, int i2) {
        java.util.ArrayList<com.android.server.am.ConnectionRecord> arrayList = this.mServiceConnections.get(iServiceConnection.asBinder());
        if (arrayList == null) {
            throw new java.lang.IllegalArgumentException("Could not find connection for " + iServiceConnection.asBinder());
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.am.ServiceRecord serviceRecord = arrayList.get(size).binding.service;
            if (serviceRecord != null && (serviceRecord.serviceInfo.flags & 2) != 0) {
                if (serviceRecord.app != null) {
                    com.android.server.am.ProcessServiceRecord processServiceRecord = serviceRecord.app.mServices;
                    if (i > 0) {
                        processServiceRecord.setConnectionService(serviceRecord);
                        processServiceRecord.setConnectionGroup(i);
                        processServiceRecord.setConnectionImportance(i2);
                    } else {
                        processServiceRecord.setConnectionService(null);
                        processServiceRecord.setConnectionGroup(0);
                        processServiceRecord.setConnectionImportance(0);
                    }
                } else if (i > 0) {
                    serviceRecord.pendingConnectionGroup = i;
                    serviceRecord.pendingConnectionImportance = i2;
                } else {
                    serviceRecord.pendingConnectionGroup = 0;
                    serviceRecord.pendingConnectionImportance = 0;
                }
            }
        }
    }

    boolean unbindServiceLocked(android.app.IServiceConnection iServiceConnection) {
        java.lang.String num;
        android.os.IBinder asBinder = iServiceConnection.asBinder();
        java.util.ArrayList<com.android.server.am.ConnectionRecord> arrayList = this.mServiceConnections.get(asBinder);
        int i = 0;
        if (arrayList == null) {
            android.util.Slog.w("ActivityManager", "Unbind failed: could not find connection for " + iServiceConnection.asBinder());
            return false;
        }
        int callingPid = this.mAm.mInjector.getCallingPid();
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        try {
            if (android.os.Trace.isTagEnabled(64L)) {
                if (arrayList.size() > 0) {
                    com.android.server.am.ConnectionRecord connectionRecord = arrayList.get(0);
                    num = connectionRecord.binding.service.shortInstanceName + " from " + connectionRecord.clientProcessName;
                } else {
                    num = java.lang.Integer.toString(callingPid);
                }
                android.os.Trace.traceBegin(64L, "unbindServiceLocked: " + num);
            }
            boolean z = false;
            while (arrayList.size() > 0) {
                com.android.server.am.ConnectionRecord connectionRecord2 = arrayList.get(i);
                int removeConnectionLocked = removeConnectionLocked(connectionRecord2, null, null, true);
                if (arrayList.size() > 0 && arrayList.get(i) == connectionRecord2) {
                    android.util.Slog.wtf("ActivityManager", "Connection " + connectionRecord2 + " not removed for binder " + asBinder);
                    arrayList.remove(i);
                }
                com.android.server.am.ProcessRecord processRecord = connectionRecord2.binding.service.app;
                if (processRecord != null) {
                    com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (processServiceRecord.mAllowlistManager) {
                        updateAllowlistManagerLocked(processServiceRecord);
                    }
                    if (connectionRecord2.hasFlag(134217728)) {
                        processServiceRecord.setTreatLikeActivity(true);
                        this.mAm.updateLruProcessLocked(processRecord, true, null);
                    }
                    if (removeConnectionLocked == 0) {
                        this.mAm.enqueueOomAdjTargetLocked(processRecord);
                        z = true;
                    }
                }
                i = 0;
            }
            if (z) {
                this.mAm.updateOomAdjPendingTargetsLocked(5);
            }
            android.os.Trace.traceEnd(64L);
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(64L);
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    void unbindFinishedLocked(com.android.server.am.ServiceRecord serviceRecord, android.content.Intent intent, boolean z) {
        boolean z2;
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        if (serviceRecord != null) {
            try {
                com.android.server.am.IntentBindRecord intentBindRecord = serviceRecord.bindings.get(new android.content.Intent.FilterComparison(intent));
                boolean contains = this.mDestroyingServices.contains(serviceRecord);
                if (intentBindRecord != null) {
                    if (intentBindRecord.apps.size() > 0 && !contains) {
                        int size = intentBindRecord.apps.size() - 1;
                        while (true) {
                            if (size >= 0) {
                                com.android.server.am.ProcessRecord processRecord = intentBindRecord.apps.valueAt(size).client;
                                if (processRecord == null || processRecord.mState.getSetSchedGroup() == 0) {
                                    size--;
                                } else {
                                    z2 = true;
                                    break;
                                }
                            } else {
                                z2 = false;
                                break;
                            }
                        }
                        try {
                            requestServiceBindingLocked(serviceRecord, intentBindRecord, z2, true, 0);
                        } catch (android.os.TransactionTooLargeException e) {
                        }
                    } else {
                        intentBindRecord.doRebind = true;
                    }
                }
                com.android.server.am.Flags.serviceBindingOomAdjPolicy();
                serviceDoneExecutingLocked(serviceRecord, contains, false, false, 5);
            } finally {
                this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private final com.android.server.am.ServiceRecord findServiceLocked(android.content.ComponentName componentName, android.os.IBinder iBinder, int i) {
        com.android.server.am.ServiceRecord serviceByNameLocked = getServiceByNameLocked(componentName, i);
        if (serviceByNameLocked == iBinder) {
            return serviceByNameLocked;
        }
        return null;
    }

    private final class ServiceLookupResult {

        @android.annotation.Nullable
        final android.content.ComponentName aliasComponent;
        final java.lang.String permission;
        final com.android.server.am.ServiceRecord record;

        ServiceLookupResult(com.android.server.am.ServiceRecord serviceRecord, android.content.ComponentName componentName) {
            this.record = serviceRecord;
            this.permission = null;
            this.aliasComponent = componentName;
        }

        ServiceLookupResult(java.lang.String str) {
            this.record = null;
            this.permission = str;
            this.aliasComponent = null;
        }
    }

    private class ServiceRestarter implements java.lang.Runnable {
        private com.android.server.am.ServiceRecord mService;

        private ServiceRestarter() {
        }

        void setService(com.android.server.am.ServiceRecord serviceRecord) {
            this.mService = serviceRecord;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ActiveServices.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    com.android.server.am.ActiveServices.this.performServiceRestartLocked(this.mService);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    private com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked(android.content.Intent intent, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        return retrieveServiceLocked(intent, str, false, -1, null, str2, str3, i, i2, i3, z, z2, z3, z4, null, z5, z6);
    }

    private java.lang.String generateAdditionalSeInfoFromService(android.content.Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("android.service.voice.HotwordDetectionService") || intent.getAction().equals("android.service.voice.VisualQueryDetectionService") || intent.getAction().equals("android.service.wearable.WearableSensingService")) {
                return ":isolatedComputeApp";
            }
            return "";
        }
        return "";
    }

    private com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked(android.content.Intent intent, java.lang.String str, boolean z, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, boolean z5, android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, boolean z6, boolean z7) {
        return retrieveServiceLocked(intent, str, z, i, str2, str3, str4, i2, i3, i4, z2, z3, z4, z5, foregroundServiceDelegationOptions, z6, z7, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0699  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x086f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:321:0x068b  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01f3  */
    /* JADX WARN: Type inference failed for: r0v105, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v45, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v64, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v69, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v87, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r10v1, types: [int] */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.am.ComponentAliasResolver] */
    /* JADX WARN: Type inference failed for: r3v23, types: [com.android.server.am.ActivityManagerService] */
    /* JADX WARN: Type inference failed for: r3v67, types: [android.content.pm.PackageManagerInternal] */
    /* JADX WARN: Type inference failed for: r48v0 */
    /* JADX WARN: Type inference failed for: r48v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r48v2 */
    /* JADX WARN: Type inference failed for: r49v0, types: [com.android.server.am.ActiveServices] */
    /* JADX WARN: Type inference failed for: r5v16, types: [android.content.pm.PackageManagerInternal] */
    /* JADX WARN: Type inference failed for: r5v41, types: [android.content.pm.PackageManagerInternal] */
    /* JADX WARN: Type inference failed for: r7v7, types: [int] */
    /* JADX WARN: Type inference failed for: r8v25 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked(android.content.Intent intent, java.lang.String str, boolean z, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, boolean z5, android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, boolean z6, boolean z7, boolean z8) {
        android.content.ComponentName componentName;
        android.content.ComponentName componentName2;
        java.lang.String str5;
        com.android.server.am.ServiceRecord serviceRecord;
        java.lang.String str6;
        java.lang.String str7;
        java.lang.String str8;
        com.android.server.am.ComponentAliasResolver.Resolution<android.content.ComponentName> resolution;
        int i5;
        com.android.server.am.ServiceRecord serviceRecord2;
        int permissionToOpCode;
        java.lang.String str9;
        int i6;
        ?? r8;
        int i7;
        java.lang.String str10;
        ?? r82;
        ?? sb;
        java.lang.String str11;
        android.content.ComponentName componentName3;
        android.content.ComponentName componentName4;
        java.lang.String str12;
        android.content.ComponentName componentName5;
        android.content.ComponentName componentName6;
        int i8;
        android.content.pm.ServiceInfo serviceInfo;
        com.android.server.am.ActiveServices.ServiceMap serviceMap;
        com.android.server.am.ServiceRecord serviceRecord3;
        android.content.Intent.FilterComparison filterComparison;
        com.android.server.am.ActiveServices.ServiceRestarter serviceRestarter;
        com.android.server.am.ServiceRecord serviceRecord4;
        int i9;
        int i10;
        java.lang.String str13;
        com.android.server.am.ActiveServices.ServiceMap serviceMap2;
        java.lang.String str14;
        int i11;
        long j;
        android.content.pm.ApplicationInfo applicationInfo;
        com.android.server.am.ServiceRecord serviceRecord5;
        if (z && str == null) {
            throw new java.lang.IllegalArgumentException("No instanceName provided for sdk sandbox process");
        }
        ?? handleIncomingUser = this.mAm.mUserController.handleIncomingUser(i2, i3, i4, false, getAllowMode(intent, str4), com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE, str4);
        com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = getServiceMapLocked(handleIncomingUser);
        com.android.server.am.ComponentAliasResolver.Resolution<android.content.ComponentName> resolveService = this.mAm.mComponentAliasResolver.resolveService(intent, str3, 0, handleIncomingUser, i3);
        if (str == null) {
            componentName = intent.getComponent();
        } else {
            android.content.ComponentName component = intent.getComponent();
            if (component == null) {
                throw new java.lang.IllegalArgumentException("Can't use custom instance name '" + str + "' without expicit component in Intent");
            }
            componentName = new android.content.ComponentName(component.getPackageName(), component.getClassName() + ":" + str);
        }
        com.android.server.am.ServiceRecord serviceRecord6 = componentName != null ? serviceMapLocked.mServicesByInstanceName.get(componentName) : null;
        if (serviceRecord6 == null && !z4 && str == null) {
            serviceRecord6 = serviceMapLocked.mServicesByIntent.get(new android.content.Intent.FilterComparison(intent));
        }
        if (serviceRecord6 != null) {
            componentName2 = componentName;
            str5 = ":";
            if (this.mAm.getPackageManagerInternal().filterAppAccess(serviceRecord6.packageName, i3, handleIncomingUser)) {
                android.util.Slog.w("ActivityManager", "Unable to start service " + intent + " U=" + handleIncomingUser + ": not found");
                return null;
            }
            if ((serviceRecord6.serviceInfo.flags & 4) != 0 && !str4.equals(serviceRecord6.packageName)) {
                serviceRecord = null;
                if (serviceRecord != null && foregroundServiceDelegationOptions != null) {
                    android.content.pm.ServiceInfo serviceInfo2 = new android.content.pm.ServiceInfo();
                    try {
                        applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(foregroundServiceDelegationOptions.mClientPackageName, 1024L, (int) handleIncomingUser);
                    } catch (android.os.RemoteException e) {
                        applicationInfo = null;
                    }
                    if (applicationInfo == null) {
                        throw new java.lang.SecurityException("startForegroundServiceDelegate failed, could not resolve client package " + str4);
                    }
                    if (applicationInfo.uid != foregroundServiceDelegationOptions.mClientUid) {
                        throw new java.lang.SecurityException("startForegroundServiceDelegate failed, uid:" + applicationInfo.uid + " does not match clientUid:" + foregroundServiceDelegationOptions.mClientUid);
                    }
                    serviceInfo2.applicationInfo = applicationInfo;
                    serviceInfo2.packageName = applicationInfo.packageName;
                    serviceInfo2.mForegroundServiceType = foregroundServiceDelegationOptions.mForegroundServiceTypes;
                    serviceInfo2.processName = applicationInfo.processName;
                    android.content.ComponentName component2 = intent.getComponent();
                    serviceInfo2.name = component2.getClassName();
                    if (z2) {
                        android.content.Intent.FilterComparison filterComparison2 = new android.content.Intent.FilterComparison(intent.cloneFilter());
                        com.android.server.am.ActiveServices.ServiceRestarter serviceRestarter2 = new com.android.server.am.ActiveServices.ServiceRestarter();
                        serviceRecord5 = new com.android.server.am.ServiceRecord(this.mAm, component2, component2, serviceInfo2.applicationInfo.packageName, serviceInfo2.applicationInfo.uid, filterComparison2, serviceInfo2, z3, serviceRestarter2, getProcessNameForService(serviceInfo2, component2, str4, null, false, false, false), -1, null, false);
                        serviceRestarter2.setService(serviceRecord5);
                        serviceMapLocked.mServicesByInstanceName.put(component2, serviceRecord5);
                        serviceMapLocked.mServicesByIntent.put(filterComparison2, serviceRecord5);
                        serviceRecord5.mRecentCallingPackage = str4;
                        serviceRecord5.mRecentCallingUid = i3;
                    } else {
                        serviceRecord5 = serviceRecord;
                    }
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    android.content.pm.ApplicationInfo applicationInfo2 = serviceRecord5.appInfo;
                    sb2.append(applicationInfo2.seInfo);
                    sb2.append(generateAdditionalSeInfoFromService(intent));
                    applicationInfo2.seInfo = sb2.toString();
                    return new com.android.server.am.ActiveServices.ServiceLookupResult(serviceRecord5, resolveService.getAlias());
                }
                if (serviceRecord != null) {
                    long j2 = z5 ? 276825088L : 268436480L;
                    if (z8) {
                        j2 |= 8589934592L;
                    }
                    try {
                        resolution = resolveService;
                        java.lang.String str15 = "association not allowed between packages ";
                        str6 = " and ";
                        ?? r9 = " U=";
                        str7 = "Service lookup failed: ";
                        java.lang.String str16 = str5;
                        int i12 = i3;
                        try {
                            android.content.pm.ResolveInfo resolveService2 = this.mAm.getPackageManagerInternal().resolveService(intent, str3, j2, handleIncomingUser, i12);
                            android.content.pm.ServiceInfo serviceInfo3 = resolveService2 != null ? resolveService2.serviceInfo : null;
                            try {
                                if (serviceInfo3 == null) {
                                    ?? sb3 = new java.lang.StringBuilder();
                                    sb3.append("Unable to start service ");
                                    sb3.append(intent);
                                    sb3.append(r9);
                                    sb3.append(handleIncomingUser);
                                    sb3.append(": not found");
                                    android.util.Slog.w("ActivityManager", sb3.toString());
                                    return null;
                                }
                                if (str != null && (serviceInfo3.flags & 2) == 0 && !z) {
                                    throw new java.lang.IllegalArgumentException("Can't use instance name '" + str + "' with non-isolated non-sdk sandbox service '" + serviceInfo3.name + "'");
                                }
                                if (z && (serviceInfo3.flags & 2) != 0) {
                                    throw new java.lang.IllegalArgumentException("Service cannot be both sdk sandbox and isolated");
                                }
                                android.content.ComponentName componentName7 = new android.content.ComponentName(serviceInfo3.applicationInfo.packageName, serviceInfo3.name);
                                android.content.ComponentName componentName8 = componentName2 != null ? componentName2 : componentName7;
                                ?? r3 = this.mAm;
                                java.lang.String packageName = componentName8.getPackageName();
                                ?? r7 = serviceInfo3.applicationInfo.uid;
                                try {
                                    if (r3.validateAssociationAllowedLocked(str4, i3, packageName, r7)) {
                                        java.lang.String str17 = serviceInfo3.applicationInfo.packageName;
                                        int i13 = serviceInfo3.applicationInfo.uid;
                                        str7 = str7;
                                        str6 = str6;
                                        try {
                                            if ((serviceInfo3.flags & 4) == 0) {
                                                str11 = str15;
                                                if (z4) {
                                                    throw new java.lang.SecurityException("BIND_EXTERNAL_SERVICE failed, " + componentName8 + " is not an externalService");
                                                }
                                                componentName3 = componentName8;
                                                componentName4 = componentName7;
                                            } else {
                                                if (!z4) {
                                                    throw new java.lang.SecurityException("BIND_EXTERNAL_SERVICE required for " + componentName8);
                                                }
                                                try {
                                                    if (!serviceInfo3.exported) {
                                                        throw new java.lang.SecurityException("BIND_EXTERNAL_SERVICE failed, " + componentName7 + " is not exported");
                                                    }
                                                    if (z7) {
                                                        throw new java.lang.SecurityException("BIND_PACKAGE_ISOLATED_PROCESS cannot be applied to an external service.");
                                                    }
                                                    if ((serviceInfo3.flags & 2) == 0) {
                                                        throw new java.lang.SecurityException("BIND_EXTERNAL_SERVICE failed, " + componentName7 + " is not an isolatedProcess");
                                                    }
                                                    if (!this.mAm.getPackageManagerInternal().isSameApp(str4, i3, handleIncomingUser)) {
                                                        throw new java.lang.SecurityException("BIND_EXTERNAL_SERVICE failed, calling package not owned by calling UID ");
                                                    }
                                                    str11 = str15;
                                                    android.content.pm.ApplicationInfo applicationInfo3 = android.app.AppGlobals.getPackageManager().getApplicationInfo(str4, 1024L, (int) handleIncomingUser);
                                                    if (applicationInfo3 == null) {
                                                        throw new java.lang.SecurityException("BIND_EXTERNAL_SERVICE failed, could not resolve client package " + str4);
                                                    }
                                                    android.content.pm.ServiceInfo serviceInfo4 = new android.content.pm.ServiceInfo(serviceInfo3);
                                                    serviceInfo4.applicationInfo = new android.content.pm.ApplicationInfo(serviceInfo4.applicationInfo);
                                                    serviceInfo4.applicationInfo.packageName = applicationInfo3.packageName;
                                                    serviceInfo4.applicationInfo.uid = applicationInfo3.uid;
                                                    android.content.ComponentName componentName9 = new android.content.ComponentName(applicationInfo3.packageName, componentName8.getClassName());
                                                    android.content.ComponentName componentName10 = new android.content.ComponentName(applicationInfo3.packageName, str == null ? componentName7.getClassName() : componentName7.getClassName() + str16 + str);
                                                    intent.setComponent(componentName9);
                                                    componentName3 = componentName9;
                                                    serviceInfo3 = serviceInfo4;
                                                    componentName4 = componentName10;
                                                } catch (android.os.RemoteException e2) {
                                                    str15 = str15;
                                                    handleIncomingUser = str15;
                                                    i6 = 0;
                                                    str9 = handleIncomingUser;
                                                    serviceRecord2 = serviceRecord;
                                                    i5 = i6;
                                                    str8 = str9;
                                                    if (serviceRecord2 == null) {
                                                    }
                                                }
                                            }
                                            if (z6 && z7) {
                                                throw new java.lang.SecurityException("Either BIND_SHARED_ISOLATED_PROCESS or BIND_PACKAGE_ISOLATED_PROCESS should be set. Not both.");
                                            }
                                            if ((z6 || z7) && (serviceInfo3.flags & 2) == 0) {
                                                throw new java.lang.SecurityException("BIND_SHARED_ISOLATED_PROCESS failed, " + componentName4 + " is not an isolatedProcess");
                                            }
                                            if (z7 && isDefaultProcessService(serviceInfo3)) {
                                                throw new java.lang.SecurityException("BIND_PACKAGE_ISOLATED_PROCESS cannot be used for services running in the main app process.");
                                            }
                                            if (z6) {
                                                if (str == null) {
                                                    throw new java.lang.IllegalArgumentException("instanceName must be provided for binding a service into a shared isolated process.");
                                                }
                                                if ((serviceInfo3.flags & 16) == 0) {
                                                    throw new java.lang.SecurityException("BIND_SHARED_ISOLATED_PROCESS failed, " + componentName4 + " has not set the allowSharedIsolatedProcess  attribute.");
                                                }
                                            }
                                            if (handleIncomingUser <= 0) {
                                                str12 = str11;
                                                componentName5 = componentName4;
                                                componentName6 = componentName3;
                                                i8 = 0;
                                                serviceInfo = serviceInfo3;
                                                serviceMap = serviceMapLocked;
                                            } else {
                                                if (this.mAm.isSystemUserOnly(serviceInfo3.flags)) {
                                                    android.util.Slog.w("ActivityManager", intent + " is only available for the SYSTEM user, calling userId is: " + handleIncomingUser);
                                                    return null;
                                                }
                                                componentName5 = componentName4;
                                                if (!this.mAm.isSingleton(serviceInfo3.processName, serviceInfo3.applicationInfo, serviceInfo3.name, serviceInfo3.flags)) {
                                                    componentName6 = componentName3;
                                                    i9 = handleIncomingUser;
                                                    i10 = 0;
                                                    str13 = str11;
                                                } else if (this.mAm.isValidSingletonCall(i3, serviceInfo3.applicationInfo.uid)) {
                                                    try {
                                                        com.android.server.am.ActiveServices.ServiceMap serviceMapLocked2 = getServiceMapLocked(0);
                                                        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
                                                        try {
                                                            j = clearCallingIdentity;
                                                            componentName6 = componentName3;
                                                            str14 = str11;
                                                        } catch (java.lang.Throwable th) {
                                                            th = th;
                                                            j = clearCallingIdentity;
                                                        }
                                                        try {
                                                            android.content.pm.ResolveInfo resolveService3 = this.mAm.getPackageManagerInternal().resolveService(intent, str3, j2, 0, i3);
                                                            if (resolveService3 == null) {
                                                                android.util.Slog.w("ActivityManager", "Unable to resolve service " + intent + r9 + "0: not found");
                                                                this.mAm.mInjector.restoreCallingIdentity(j);
                                                                return null;
                                                            }
                                                            serviceInfo3 = resolveService3.serviceInfo;
                                                            this.mAm.mInjector.restoreCallingIdentity(j);
                                                            i9 = 0;
                                                            serviceMap2 = serviceMapLocked2;
                                                            i11 = 0;
                                                            android.content.pm.ServiceInfo serviceInfo5 = new android.content.pm.ServiceInfo(serviceInfo3);
                                                            serviceInfo5.applicationInfo = this.mAm.getAppInfoForUser(serviceInfo5.applicationInfo, i9);
                                                            serviceMap = serviceMap2;
                                                            serviceInfo = serviceInfo5;
                                                            i8 = i11;
                                                            str12 = str14;
                                                        } catch (java.lang.Throwable th2) {
                                                            th = th2;
                                                            this.mAm.mInjector.restoreCallingIdentity(j);
                                                            throw th;
                                                        }
                                                    } catch (android.os.RemoteException e3) {
                                                        i6 = 0;
                                                        str9 = handleIncomingUser;
                                                        serviceRecord2 = serviceRecord;
                                                        i5 = i6;
                                                        str8 = str9;
                                                        if (serviceRecord2 == null) {
                                                        }
                                                    }
                                                } else {
                                                    componentName6 = componentName3;
                                                    i9 = handleIncomingUser;
                                                    i10 = 0;
                                                    str13 = str11;
                                                }
                                                serviceMap2 = serviceMapLocked;
                                                i11 = i10;
                                                str14 = str13;
                                                android.content.pm.ServiceInfo serviceInfo52 = new android.content.pm.ServiceInfo(serviceInfo3);
                                                serviceInfo52.applicationInfo = this.mAm.getAppInfoForUser(serviceInfo52.applicationInfo, i9);
                                                serviceMap = serviceMap2;
                                                serviceInfo = serviceInfo52;
                                                i8 = i11;
                                                str12 = str14;
                                            }
                                            com.android.server.am.ServiceRecord serviceRecord7 = serviceMap.mServicesByInstanceName.get(componentName6);
                                            if (serviceRecord7 == null && z2) {
                                                try {
                                                    filterComparison = new android.content.Intent.FilterComparison(intent.cloneFilter());
                                                    serviceRestarter = new com.android.server.am.ActiveServices.ServiceRestarter();
                                                    serviceRecord4 = new com.android.server.am.ServiceRecord(this.mAm, componentName5, componentName6, str17, i13, filterComparison, serviceInfo, z3, serviceRestarter, getProcessNameForService(serviceInfo, componentName6, str4, str, z, z6, z7), i, str2, (z6 || z7) ? true : i8);
                                                } catch (android.os.RemoteException e4) {
                                                    serviceRecord = serviceRecord7;
                                                    i6 = i8;
                                                    str9 = str12;
                                                }
                                                try {
                                                    serviceRestarter.setService(serviceRecord4);
                                                    serviceMap.mServicesByInstanceName.put(componentName6, serviceRecord4);
                                                    serviceMap.mServicesByIntent.put(filterComparison, serviceRecord4);
                                                    for (int size = this.mPendingServices.size() - 1; size >= 0; size--) {
                                                        com.android.server.am.ServiceRecord serviceRecord8 = this.mPendingServices.get(size);
                                                        if (serviceRecord8.serviceInfo.applicationInfo.uid == serviceInfo.applicationInfo.uid && serviceRecord8.instanceName.equals(componentName6)) {
                                                            this.mPendingServices.remove(size);
                                                        }
                                                    }
                                                    for (int size2 = this.mPendingBringups.size() - 1; size2 >= 0; size2--) {
                                                        com.android.server.am.ServiceRecord keyAt = this.mPendingBringups.keyAt(size2);
                                                        if (keyAt.serviceInfo.applicationInfo.uid == serviceInfo.applicationInfo.uid && keyAt.instanceName.equals(componentName6)) {
                                                            this.mPendingBringups.removeAt(size2);
                                                        }
                                                    }
                                                    serviceRecord3 = serviceRecord4;
                                                } catch (android.os.RemoteException e5) {
                                                    serviceRecord = serviceRecord4;
                                                    i6 = i8;
                                                    str9 = str12;
                                                    serviceRecord2 = serviceRecord;
                                                    i5 = i6;
                                                    str8 = str9;
                                                    if (serviceRecord2 == null) {
                                                    }
                                                }
                                            } else {
                                                serviceRecord3 = serviceRecord7;
                                            }
                                            serviceRecord2 = serviceRecord3;
                                            i5 = i8;
                                            str8 = str12;
                                        } catch (android.os.RemoteException e6) {
                                            i6 = r9;
                                            str9 = handleIncomingUser;
                                        }
                                    } else {
                                        try {
                                            sb = new java.lang.StringBuilder();
                                            r82 = str15;
                                        } catch (android.os.RemoteException e7) {
                                            r82 = str15;
                                        }
                                        try {
                                            sb.append(r82);
                                            sb.append(str4);
                                            java.lang.String str18 = str6;
                                            try {
                                                sb.append(str18);
                                                sb.append(componentName8.getPackageName());
                                                java.lang.String sb4 = sb.toString();
                                                java.lang.StringBuilder sb5 = new java.lang.StringBuilder();
                                                sb5.append(str7);
                                                sb5.append(sb4);
                                                android.util.Slog.w("ActivityManager", sb5.toString());
                                                return new com.android.server.am.ActiveServices.ServiceLookupResult(sb4);
                                            } catch (android.os.RemoteException e8) {
                                                str10 = str18;
                                                i7 = r82;
                                                str6 = str10;
                                                r8 = i7;
                                                handleIncomingUser = r8;
                                                i6 = 0;
                                                str9 = handleIncomingUser;
                                                serviceRecord2 = serviceRecord;
                                                i5 = i6;
                                                str8 = str9;
                                                if (serviceRecord2 == null) {
                                                }
                                            }
                                        } catch (android.os.RemoteException e9) {
                                            r8 = r82;
                                            handleIncomingUser = r8;
                                            i6 = 0;
                                            str9 = handleIncomingUser;
                                            serviceRecord2 = serviceRecord;
                                            i5 = i6;
                                            str8 = str9;
                                            if (serviceRecord2 == null) {
                                            }
                                        }
                                    }
                                } catch (android.os.RemoteException e10) {
                                    str7 = packageName;
                                    str10 = r7;
                                    i7 = i12;
                                }
                            } catch (android.os.RemoteException e11) {
                            }
                        } catch (android.os.RemoteException e12) {
                        }
                    } catch (android.os.RemoteException e13) {
                        str6 = " and ";
                        str7 = "Service lookup failed: ";
                        str9 = "association not allowed between packages ";
                        resolution = resolveService;
                        i6 = 0;
                    }
                } else {
                    str6 = " and ";
                    str7 = "Service lookup failed: ";
                    str8 = "association not allowed between packages ";
                    resolution = resolveService;
                    i5 = 0;
                    serviceRecord2 = serviceRecord;
                }
                if (serviceRecord2 == null) {
                    return null;
                }
                serviceRecord2.mRecentCallingPackage = str4;
                serviceRecord2.mRecentCallingUid = i3;
                try {
                    serviceRecord2.mRecentCallerApplicationInfo = this.mAm.mContext.getPackageManager().getApplicationInfoAsUser(str4, i5, android.os.UserHandle.getUserId(i3));
                } catch (android.content.pm.PackageManager.NameNotFoundException e14) {
                }
                if (!this.mAm.validateAssociationAllowedLocked(str4, i3, serviceRecord2.packageName, serviceRecord2.appInfo.uid)) {
                    java.lang.String str19 = str8 + str4 + str6 + serviceRecord2.packageName;
                    android.util.Slog.w("ActivityManager", str7 + str19);
                    return new com.android.server.am.ActiveServices.ServiceLookupResult(str19);
                }
                if (!this.mAm.mIntentFirewall.checkService(serviceRecord2.name, intent, i3, i2, str3, serviceRecord2.appInfo)) {
                    return new com.android.server.am.ActiveServices.ServiceLookupResult("blocked by firewall");
                }
                if (com.android.server.am.ActivityManagerService.checkComponentPermission(serviceRecord2.permission, i2, i3, serviceRecord2.appInfo.uid, serviceRecord2.exported) != 0) {
                    if (serviceRecord2.exported) {
                        android.util.Slog.w("ActivityManager", "Permission Denial: Accessing service " + serviceRecord2.shortInstanceName + " from pid=" + i2 + ", uid=" + i3 + " requires " + serviceRecord2.permission);
                        return new com.android.server.am.ActiveServices.ServiceLookupResult(serviceRecord2.permission);
                    }
                    android.util.Slog.w("ActivityManager", "Permission Denial: Accessing service " + serviceRecord2.shortInstanceName + " from pid=" + i2 + ", uid=" + i3 + " that is not exported from uid " + serviceRecord2.appInfo.uid);
                    return new com.android.server.am.ActiveServices.ServiceLookupResult("not exported from uid " + serviceRecord2.appInfo.uid);
                }
                if (("android.permission.BIND_HOTWORD_DETECTION_SERVICE".equals(serviceRecord2.permission) || "android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE".equals(serviceRecord2.permission)) && i3 != 1000) {
                    android.util.Slog.w("ActivityManager", "Permission Denial: Accessing service " + serviceRecord2.shortInstanceName + " from pid=" + i2 + ", uid=" + i3 + " requiring permission " + serviceRecord2.permission + " can only be bound to from the system.");
                    return new com.android.server.am.ActiveServices.ServiceLookupResult("can only be bound to by the system.");
                }
                if (serviceRecord2.permission == null || str4 == null || (permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(serviceRecord2.permission)) == -1 || this.mAm.getAppOpsManager().checkOpNoThrow(permissionToOpCode, i3, str4) == 0) {
                    java.lang.StringBuilder sb6 = new java.lang.StringBuilder();
                    android.content.pm.ApplicationInfo applicationInfo4 = serviceRecord2.appInfo;
                    sb6.append(applicationInfo4.seInfo);
                    sb6.append(generateAdditionalSeInfoFromService(intent));
                    applicationInfo4.seInfo = sb6.toString();
                    return new com.android.server.am.ActiveServices.ServiceLookupResult(serviceRecord2, resolution.getAlias());
                }
                android.util.Slog.w("ActivityManager", "Appop Denial: Accessing service " + serviceRecord2.shortInstanceName + " from pid=" + i2 + ", uid=" + i3 + " requires appop " + android.app.AppOpsManager.opToName(permissionToOpCode));
                return null;
            }
        } else {
            componentName2 = componentName;
            str5 = ":";
        }
        serviceRecord = serviceRecord6;
        if (serviceRecord != null) {
        }
        if (serviceRecord != null) {
        }
        if (serviceRecord2 == null) {
        }
    }

    private int getAllowMode(android.content.Intent intent, @android.annotation.Nullable java.lang.String str) {
        if (str != null && intent.getComponent() != null && str.equals(intent.getComponent().getPackageName())) {
            return 3;
        }
        return 1;
    }

    private void bumpServiceExecutingLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z, java.lang.String str, int i, boolean z2) {
        boolean z3;
        if (this.mAm.mBootPhase < 600 && serviceRecord.app != null && serviceRecord.app.getPid() == com.android.server.am.ActivityManagerService.MY_PID) {
            android.util.Slog.w("ActivityManager", "Too early to start/bind service in system_server: Phase=" + this.mAm.mBootPhase + " " + serviceRecord.getComponentName());
            z3 = false;
        } else {
            z3 = true;
        }
        boolean z4 = z2 && serviceRecord.app != null && (serviceRecord.app.mOptRecord.isPendingFreeze() || serviceRecord.app.mOptRecord.isFrozen());
        if (serviceRecord.executeNesting == 0) {
            serviceRecord.executeFg = z;
            synchronized (this.mAm.mProcessStats.mLock) {
                try {
                    com.android.internal.app.procstats.ServiceState tracker = serviceRecord.getTracker();
                    if (tracker != null) {
                        tracker.setExecuting(true, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (serviceRecord.app != null) {
                com.android.server.am.ProcessServiceRecord processServiceRecord = serviceRecord.app.mServices;
                processServiceRecord.startExecutingService(serviceRecord);
                processServiceRecord.setExecServicesFg(processServiceRecord.shouldExecServicesFg() || z);
                if (z3 && processServiceRecord.numberOfExecutingServices() == 1) {
                    if (!z4) {
                        scheduleServiceTimeoutLocked(serviceRecord.app);
                    } else {
                        serviceRecord.app.mServices.noteScheduleServiceTimeoutPending(true);
                    }
                }
            }
        } else if (serviceRecord.app != null && z) {
            com.android.server.am.ProcessServiceRecord processServiceRecord2 = serviceRecord.app.mServices;
            if (!processServiceRecord2.shouldExecServicesFg()) {
                processServiceRecord2.setExecServicesFg(true);
                if (z3) {
                    if (!z4) {
                        scheduleServiceTimeoutLocked(serviceRecord.app);
                    } else {
                        serviceRecord.app.mServices.noteScheduleServiceTimeoutPending(true);
                    }
                }
            }
        }
        if (i != 0 && serviceRecord.app != null && serviceRecord.app.mState.getCurProcState() > 10) {
            this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
            this.mAm.updateOomAdjPendingTargetsLocked(i);
            serviceRecord.mOomAdjBumpedInExec = true;
        }
        serviceRecord.executeFg = z | serviceRecord.executeFg;
        serviceRecord.executeNesting++;
        serviceRecord.executingStart = android.os.SystemClock.uptimeMillis();
    }

    private final boolean requestServiceBindingLocked(com.android.server.am.ServiceRecord serviceRecord, com.android.server.am.IntentBindRecord intentBindRecord, boolean z, boolean z2, int i) throws android.os.TransactionTooLargeException {
        boolean z3;
        if (serviceRecord.app != null && serviceRecord.app.getThread() != null) {
            boolean z4 = (i & 2) != 0;
            if ((!intentBindRecord.requested || z2) && intentBindRecord.apps.size() > 0) {
                try {
                    try {
                        bumpServiceExecutingLocked(serviceRecord, z, "bind", z4 ? 0 : 4, z4);
                        if (android.os.Trace.isTagEnabled(64L)) {
                            android.os.Trace.instant(64L, "requestServiceBinding=" + intentBindRecord.intent.getIntent() + ". bindSeq=" + this.mBindServiceSeqCounter);
                        }
                        android.app.IApplicationThread thread = serviceRecord.app.getThread();
                        android.content.Intent intent = intentBindRecord.intent.getIntent();
                        int reportedProcState = serviceRecord.app.mState.getReportedProcState();
                        long j = this.mBindServiceSeqCounter;
                        this.mBindServiceSeqCounter = 1 + j;
                        z3 = false;
                        try {
                            thread.scheduleBindService(serviceRecord, intent, z2, reportedProcState, j);
                            if (!z2) {
                                intentBindRecord.requested = true;
                            }
                            intentBindRecord.hasBound = true;
                            intentBindRecord.doRebind = false;
                            return true;
                        } catch (android.os.RemoteException e) {
                            boolean contains = this.mDestroyingServices.contains(serviceRecord);
                            com.android.server.am.Flags.serviceBindingOomAdjPolicy();
                            boolean z5 = z3;
                            serviceDoneExecutingLocked(serviceRecord, contains, contains, false, 5);
                            return z5;
                        }
                    } catch (android.os.TransactionTooLargeException e2) {
                        boolean contains2 = this.mDestroyingServices.contains(serviceRecord);
                        com.android.server.am.Flags.serviceBindingOomAdjPolicy();
                        serviceDoneExecutingLocked(serviceRecord, contains2, contains2, false, 5);
                        throw e2;
                    }
                } catch (android.os.RemoteException e3) {
                    z3 = false;
                }
            }
            return true;
        }
        return false;
    }

    private final boolean scheduleServiceRestartLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z) {
        java.lang.String str;
        int i;
        int i2;
        boolean z2;
        java.lang.String str2;
        boolean z3;
        boolean z4;
        int i3;
        int i4 = 0;
        if (!this.mAm.mAtmInternal.isShuttingDown()) {
            com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
            if (serviceMapLocked.mServicesByInstanceName.get(serviceRecord.instanceName) != serviceRecord) {
                android.util.Slog.wtf("ActivityManager", "Attempting to schedule restart of " + serviceRecord + " when found in map: " + serviceMapLocked.mServicesByInstanceName.get(serviceRecord.instanceName));
                return false;
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int indexOf = this.mRestartingServices.indexOf(serviceRecord);
            boolean z5 = indexOf != -1;
            if ((serviceRecord.serviceInfo.applicationInfo.flags & 8) == 0) {
                long j = this.mAm.mConstants.SERVICE_RESTART_DURATION;
                long j2 = this.mAm.mConstants.SERVICE_RESET_RUN_DURATION;
                int size = serviceRecord.deliveredStarts.size();
                if (size <= 0) {
                    z2 = false;
                } else {
                    int i5 = size - 1;
                    boolean z6 = false;
                    while (i5 >= 0) {
                        com.android.server.am.ServiceRecord.StartItem startItem = serviceRecord.deliveredStarts.get(i5);
                        startItem.removeUriPermissionsLocked();
                        if (startItem.intent != null) {
                            if (!z || (startItem.deliveryCount < 3 && startItem.doneExecutingCount < 6)) {
                                serviceRecord.pendingStarts.add(i4, startItem);
                                long uptimeMillis2 = (android.os.SystemClock.uptimeMillis() - startItem.deliveredTime) * 2;
                                if (j < uptimeMillis2) {
                                    j = uptimeMillis2;
                                }
                                if (j2 < uptimeMillis2) {
                                    j2 = uptimeMillis2;
                                }
                            } else {
                                android.util.Slog.w("ActivityManager", "Canceling start item " + startItem.intent + " in service " + serviceRecord.shortInstanceName);
                                z6 = true;
                            }
                        }
                        i5--;
                        i4 = 0;
                    }
                    serviceRecord.deliveredStarts.clear();
                    z2 = z6;
                }
                if (z) {
                    boolean canStopIfKilled = serviceRecord.canStopIfKilled(z2);
                    if (canStopIfKilled && !serviceRecord.hasAutoCreateConnections()) {
                        return false;
                    }
                    str2 = (!serviceRecord.startRequested || canStopIfKilled) ? "connection" : "start-requested";
                } else {
                    str2 = "always";
                }
                serviceRecord.totalRestartCount++;
                if (serviceRecord.restartDelay == 0) {
                    serviceRecord.restartCount++;
                    serviceRecord.restartDelay = j;
                } else if (serviceRecord.crashCount > 1) {
                    serviceRecord.restartDelay = this.mAm.mConstants.BOUND_SERVICE_CRASH_RESTART_DURATION * (serviceRecord.crashCount - 1);
                } else if (uptimeMillis <= serviceRecord.restartTime + j2) {
                    serviceRecord.restartDelay *= this.mAm.mConstants.SERVICE_RESTART_DURATION_FACTOR;
                    if (serviceRecord.restartDelay < j) {
                        serviceRecord.restartDelay = j;
                    }
                } else {
                    serviceRecord.restartCount = 1;
                    serviceRecord.restartDelay = j;
                }
                if (isServiceRestartBackoffEnabledLocked(serviceRecord.packageName)) {
                    long j3 = serviceRecord.restartDelay + uptimeMillis;
                    serviceRecord.mEarliestRestartTime = j3;
                    serviceRecord.nextRestartTime = j3;
                    if (!z5) {
                        z3 = z5;
                    } else {
                        this.mRestartingServices.remove(indexOf);
                        z3 = false;
                    }
                    if (!this.mRestartingServices.isEmpty()) {
                        long extraRestartTimeInBetweenLocked = getExtraRestartTimeInBetweenLocked() + this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN;
                        do {
                            long j4 = serviceRecord.nextRestartTime;
                            for (int size2 = this.mRestartingServices.size() - 1; size2 >= 0; size2 = i3 - 1) {
                                long j5 = this.mRestartingServices.get(size2).nextRestartTime;
                                if (j4 >= j5 - extraRestartTimeInBetweenLocked) {
                                    i3 = size2;
                                    long j6 = j5 + extraRestartTimeInBetweenLocked;
                                    if (j4 < j6) {
                                        serviceRecord.nextRestartTime = j6;
                                        serviceRecord.restartDelay = serviceRecord.nextRestartTime - uptimeMillis;
                                        z4 = true;
                                        break;
                                    }
                                } else {
                                    i3 = size2;
                                }
                                if (j4 >= j5 + extraRestartTimeInBetweenLocked) {
                                    break;
                                }
                            }
                            z4 = false;
                        } while (z4);
                    } else {
                        serviceRecord.nextRestartTime = java.lang.Math.max(getExtraRestartTimeInBetweenLocked() + uptimeMillis, serviceRecord.nextRestartTime);
                        serviceRecord.restartDelay = serviceRecord.nextRestartTime - uptimeMillis;
                    }
                    z5 = z3;
                } else {
                    serviceRecord.restartDelay = this.mAm.mConstants.SERVICE_RESTART_DURATION;
                    serviceRecord.nextRestartTime = serviceRecord.restartDelay + uptimeMillis;
                }
                str = str2;
            } else {
                serviceRecord.totalRestartCount++;
                serviceRecord.restartCount = 0;
                serviceRecord.restartDelay = 0L;
                serviceRecord.mEarliestRestartTime = 0L;
                serviceRecord.nextRestartTime = uptimeMillis;
                str = "persistent";
            }
            serviceRecord.mRestartSchedulingTime = uptimeMillis;
            if (!z5) {
                if (indexOf != -1) {
                    i = 0;
                } else {
                    i = 0;
                    serviceRecord.createdFromFg = false;
                    synchronized (this.mAm.mProcessStats.mLock) {
                        serviceRecord.makeRestarting(this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                    }
                }
                int size3 = this.mRestartingServices.size();
                int i6 = i;
                while (true) {
                    if (i6 >= size3) {
                        i2 = i;
                        break;
                    }
                    if (this.mRestartingServices.get(i6).nextRestartTime <= serviceRecord.nextRestartTime) {
                        i6++;
                    } else {
                        this.mRestartingServices.add(i6, serviceRecord);
                        i2 = 1;
                        break;
                    }
                }
                if (i2 == 0) {
                    this.mRestartingServices.add(serviceRecord);
                }
            }
            cancelForegroundNotificationLocked(serviceRecord);
            performScheduleRestartLocked(serviceRecord, "Scheduling", str, uptimeMillis);
            return true;
        }
        android.util.Slog.w("ActivityManager", "Not scheduling restart of crashed service " + serviceRecord.shortInstanceName + " - system is shutting down");
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void performScheduleRestartLocked(com.android.server.am.ServiceRecord serviceRecord, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, long j) {
        if (serviceRecord.fgRequired && serviceRecord.fgWaiting) {
            this.mServiceFGAnrTimer.cancel(serviceRecord);
            serviceRecord.fgWaiting = false;
        }
        this.mAm.mHandler.removeCallbacks(serviceRecord.restarter);
        this.mAm.mHandler.postAtTime(serviceRecord.restarter, serviceRecord.nextRestartTime);
        serviceRecord.nextRestartTime = j + serviceRecord.restartDelay;
        android.util.Slog.w("ActivityManager", str + " restart of crashed service " + serviceRecord.shortInstanceName + " in " + serviceRecord.restartDelay + "ms for " + str2);
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_SCHEDULE_SERVICE_RESTART, java.lang.Integer.valueOf(serviceRecord.userId), serviceRecord.shortInstanceName, java.lang.Long.valueOf(serviceRecord.restartDelay));
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void rescheduleServiceRestartOnMemoryPressureIfNeededLocked(int i, int i2, @android.annotation.NonNull java.lang.String str, long j) {
        if (!this.mAm.mConstants.mEnableExtraServiceRestartDelayOnMemPressure) {
            return;
        }
        performRescheduleServiceRestartOnMemoryPressureLocked(this.mAm.mConstants.mExtraServiceRestartDelayOnMemPressure[i], this.mAm.mConstants.mExtraServiceRestartDelayOnMemPressure[i2], str, j);
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void rescheduleServiceRestartOnMemoryPressureIfNeededLocked(boolean z, boolean z2, long j) {
        if (z == z2) {
            return;
        }
        long j2 = this.mAm.mConstants.mExtraServiceRestartDelayOnMemPressure[this.mAm.mAppProfiler.getLastMemoryLevelLocked()];
        long j3 = z ? j2 : 0L;
        if (!z2) {
            j2 = 0;
        }
        performRescheduleServiceRestartOnMemoryPressureLocked(j3, j2, "config", j);
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void rescheduleServiceRestartIfPossibleLocked(long j, long j2, @android.annotation.NonNull java.lang.String str, long j3) {
        long j4;
        long j5;
        long j6;
        long j7;
        long j8 = j + j2;
        long j9 = j8 * 2;
        int size = this.mRestartingServices.size();
        int i = -1;
        int i2 = 0;
        long j10 = j3;
        while (i2 < size) {
            com.android.server.am.ServiceRecord serviceRecord = this.mRestartingServices.get(i2);
            if ((serviceRecord.serviceInfo.applicationInfo.flags & 8) != 0) {
                j4 = j8;
                j5 = j9;
            } else if (isServiceRestartBackoffEnabledLocked(serviceRecord.packageName)) {
                long j11 = j10 + j8;
                long j12 = j9;
                if (j11 <= serviceRecord.mEarliestRestartTime) {
                    long j13 = serviceRecord.mEarliestRestartTime;
                    if (i2 > 0) {
                        j7 = this.mRestartingServices.get(i2 - 1).nextRestartTime + j8;
                    } else {
                        j7 = 0;
                    }
                    serviceRecord.nextRestartTime = java.lang.Math.max(j3, java.lang.Math.max(j13, j7));
                } else {
                    if (j10 <= j3) {
                        serviceRecord.nextRestartTime = java.lang.Math.max(j3, java.lang.Math.max(serviceRecord.mEarliestRestartTime, serviceRecord.mRestartSchedulingTime + j));
                    } else {
                        serviceRecord.nextRestartTime = java.lang.Math.max(j3, j11);
                    }
                    int i3 = i + 1;
                    if (i2 > i3) {
                        this.mRestartingServices.remove(i2);
                        this.mRestartingServices.add(i3, serviceRecord);
                    }
                }
                int i4 = i;
                long j14 = j10;
                int i5 = i + 1;
                while (true) {
                    if (i5 > i2) {
                        j4 = j8;
                        j5 = j12;
                        break;
                    }
                    com.android.server.am.ServiceRecord serviceRecord2 = this.mRestartingServices.get(i5);
                    long j15 = serviceRecord2.nextRestartTime;
                    if (i5 == 0) {
                        j4 = j8;
                        j6 = j14;
                    } else {
                        j4 = j8;
                        j6 = this.mRestartingServices.get(i5 - 1).nextRestartTime;
                    }
                    long j16 = j15 - j6;
                    j5 = j12;
                    if (j16 >= j5) {
                        break;
                    }
                    i4 = i5;
                    j14 = serviceRecord2.nextRestartTime;
                    i5++;
                    j12 = j5;
                    j8 = j4;
                }
                serviceRecord.restartDelay = serviceRecord.nextRestartTime - j3;
                performScheduleRestartLocked(serviceRecord, "Rescheduling", str, j3);
                i = i4;
                j10 = j14;
                i2++;
                j9 = j5;
                j8 = j4;
            } else {
                j4 = j8;
                j5 = j9;
            }
            j10 = serviceRecord.nextRestartTime;
            i = i2;
            i2++;
            j9 = j5;
            j8 = j4;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void performRescheduleServiceRestartOnMemoryPressureLocked(long j, long j2, @android.annotation.NonNull java.lang.String str, long j3) {
        boolean z;
        long j4 = j2 - j;
        if (j4 == 0) {
            return;
        }
        if (j4 <= 0) {
            if (j4 < 0) {
                rescheduleServiceRestartIfPossibleLocked(j2, this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN, str, j3);
                return;
            }
            return;
        }
        long j5 = this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN + j2;
        int size = this.mRestartingServices.size();
        long j6 = j3;
        for (int i = 0; i < size; i++) {
            com.android.server.am.ServiceRecord serviceRecord = this.mRestartingServices.get(i);
            if ((serviceRecord.serviceInfo.applicationInfo.flags & 8) != 0 || !isServiceRestartBackoffEnabledLocked(serviceRecord.packageName)) {
                j6 = serviceRecord.nextRestartTime;
            } else {
                if (j6 <= j3) {
                    long j7 = serviceRecord.nextRestartTime;
                    serviceRecord.nextRestartTime = java.lang.Math.max(j3, java.lang.Math.max(serviceRecord.mEarliestRestartTime, serviceRecord.mRestartSchedulingTime + j2));
                    z = serviceRecord.nextRestartTime != j7;
                } else if (serviceRecord.nextRestartTime - j6 >= j5) {
                    z = false;
                } else {
                    serviceRecord.nextRestartTime = java.lang.Math.max(j6 + j5, j3);
                    z = true;
                }
                serviceRecord.restartDelay = serviceRecord.nextRestartTime - j3;
                long j8 = serviceRecord.nextRestartTime;
                if (z) {
                    performScheduleRestartLocked(serviceRecord, "Rescheduling", str, j3);
                }
                j6 = j8;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    long getExtraRestartTimeInBetweenLocked() {
        if (!this.mAm.mConstants.mEnableExtraServiceRestartDelayOnMemPressure) {
            return 0L;
        }
        return this.mAm.mConstants.mExtraServiceRestartDelayOnMemPressure[this.mAm.mAppProfiler.getLastMemoryLevelLocked()];
    }

    final void performServiceRestartLocked(com.android.server.am.ServiceRecord serviceRecord) {
        if (!this.mRestartingServices.contains(serviceRecord)) {
            return;
        }
        if (!isServiceNeededLocked(serviceRecord, false, false)) {
            android.util.Slog.wtf("ActivityManager", "Restarting service that is not needed: " + serviceRecord);
            return;
        }
        try {
            bringUpServiceLocked(serviceRecord, serviceRecord.intent.getIntent().getFlags(), serviceRecord.createdFromFg, true, false, false, true, 0);
        } catch (android.os.TransactionTooLargeException e) {
        } catch (java.lang.Throwable th) {
            this.mAm.updateOomAdjPendingTargetsLocked(6);
            throw th;
        }
        this.mAm.updateOomAdjPendingTargetsLocked(6);
    }

    private final boolean unscheduleServiceRestartLocked(com.android.server.am.ServiceRecord serviceRecord, int i, boolean z) {
        if (!z && serviceRecord.restartDelay == 0) {
            return false;
        }
        boolean remove = this.mRestartingServices.remove(serviceRecord);
        if (remove || i != serviceRecord.appInfo.uid) {
            serviceRecord.resetRestartCounter();
        }
        if (remove) {
            clearRestartingIfNeededLocked(serviceRecord);
        }
        this.mAm.mHandler.removeCallbacks(serviceRecord.restarter);
        return true;
    }

    private void clearRestartingIfNeededLocked(com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord.restartTracker != null) {
            boolean z = true;
            int size = this.mRestartingServices.size() - 1;
            while (true) {
                if (size < 0) {
                    z = false;
                    break;
                } else if (this.mRestartingServices.get(size).restartTracker == serviceRecord.restartTracker) {
                    break;
                } else {
                    size--;
                }
            }
            if (!z) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.restartTracker.setRestarting(false, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                }
                serviceRecord.restartTracker = null;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    void setServiceRestartBackoffEnabledLocked(@android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.NonNull java.lang.String str2) {
        if (!z) {
            if (this.mRestartBackoffDisabledPackages.contains(str)) {
                return;
            }
            this.mRestartBackoffDisabledPackages.add(str);
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int size = this.mRestartingServices.size();
            for (int i = 0; i < size; i++) {
                com.android.server.am.ServiceRecord serviceRecord = this.mRestartingServices.get(i);
                if (android.text.TextUtils.equals(serviceRecord.packageName, str) && serviceRecord.nextRestartTime - uptimeMillis > this.mAm.mConstants.SERVICE_RESTART_DURATION) {
                    serviceRecord.restartDelay = this.mAm.mConstants.SERVICE_RESTART_DURATION;
                    serviceRecord.nextRestartTime = serviceRecord.restartDelay + uptimeMillis;
                    performScheduleRestartLocked(serviceRecord, "Rescheduling", str2, uptimeMillis);
                }
                java.util.Collections.sort(this.mRestartingServices, new java.util.Comparator() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                        int lambda$setServiceRestartBackoffEnabledLocked$0;
                        lambda$setServiceRestartBackoffEnabledLocked$0 = com.android.server.am.ActiveServices.lambda$setServiceRestartBackoffEnabledLocked$0((com.android.server.am.ServiceRecord) obj, (com.android.server.am.ServiceRecord) obj2);
                        return lambda$setServiceRestartBackoffEnabledLocked$0;
                    }
                });
            }
            return;
        }
        removeServiceRestartBackoffEnabledLocked(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$setServiceRestartBackoffEnabledLocked$0(com.android.server.am.ServiceRecord serviceRecord, com.android.server.am.ServiceRecord serviceRecord2) {
        return (int) (serviceRecord.nextRestartTime - serviceRecord2.nextRestartTime);
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    private void removeServiceRestartBackoffEnabledLocked(@android.annotation.NonNull java.lang.String str) {
        this.mRestartBackoffDisabledPackages.remove(str);
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    boolean isServiceRestartBackoffEnabledLocked(@android.annotation.NonNull java.lang.String str) {
        return !this.mRestartBackoffDisabledPackages.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String bringUpServiceLocked(com.android.server.am.ServiceRecord serviceRecord, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2) throws android.os.TransactionTooLargeException {
        try {
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.traceBegin(64L, "bringUpServiceLocked: " + serviceRecord.shortInstanceName);
            }
            java.lang.String bringUpServiceInnerLocked = bringUpServiceInnerLocked(serviceRecord, i, z, z2, z3, z4, z5, i2);
            android.os.Trace.traceEnd(64L);
            return bringUpServiceInnerLocked;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(64L);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x032c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x03d8  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0403  */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r19v14 */
    /* JADX WARN: Type inference failed for: r19v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r19v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.String bringUpServiceInnerLocked(com.android.server.am.ServiceRecord serviceRecord, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2) throws android.os.TransactionTooLargeException {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        long j;
        com.android.server.am.ProcessRecord processRecord;
        com.android.server.am.HostingRecord hostingRecord;
        com.android.server.am.ProcessRecord processRecord2;
        long j2;
        long j3;
        long j4;
        boolean z6;
        com.android.server.am.ProcessRecord startProcessLocked;
        com.android.server.am.ProcessRecord processRecord3;
        long j5;
        java.lang.String str6;
        if (serviceRecord.app != null && serviceRecord.app.isThreadReady()) {
            sendServiceArgsLocked(serviceRecord, z, false);
            return null;
        }
        if (!z2 && this.mRestartingServices.contains(serviceRecord)) {
            return null;
        }
        long elapsedRealtimeNanos = android.os.SystemClock.elapsedRealtimeNanos();
        if (this.mRestartingServices.remove(serviceRecord)) {
            clearRestartingIfNeededLocked(serviceRecord);
        }
        if (serviceRecord.delayed) {
            getServiceMapLocked(serviceRecord.userId).mDelayedStartList.remove(serviceRecord);
            serviceRecord.delayed = false;
        }
        if (!this.mAm.mUserController.hasStartedUserState(serviceRecord.userId)) {
            java.lang.String str7 = "Unable to launch app " + serviceRecord.appInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + serviceRecord.appInfo.uid + " for service " + serviceRecord.intent.getIntent() + ": user " + serviceRecord.userId + " is stopped";
            android.util.Slog.w("ActivityManager", str7);
            bringDownServiceLocked(serviceRecord, z5);
            return str7;
        }
        if (!serviceRecord.appInfo.packageName.equals(serviceRecord.mRecentCallingPackage) && !serviceRecord.isNotAppComponentUsage) {
            this.mAm.mUsageStatsService.reportEvent(serviceRecord.packageName, serviceRecord.userId, 31);
        }
        try {
            this.mAm.mPackageManagerInt.notifyComponentUsed(serviceRecord.packageName, serviceRecord.userId, serviceRecord.mRecentCallingPackage, serviceRecord.toString());
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w("ActivityManager", "Failed trying to unstop package " + serviceRecord.packageName + ": " + e);
        }
        boolean z7 = (serviceRecord.serviceInfo.flags & 2) != 0;
        java.lang.String str8 = serviceRecord.processName;
        com.android.server.am.HostingRecord hostingRecord2 = new com.android.server.am.HostingRecord(com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE, serviceRecord.instanceName, serviceRecord.definingPackageName, serviceRecord.definingUid, serviceRecord.serviceInfo.processName, getHostingRecordTriggerType(serviceRecord));
        long j6 = 64;
        if (z7) {
            str = str8;
            str2 = "ActivityManager";
            str3 = "Unable to launch app ";
            str4 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
            str5 = " for service ";
            j = elapsedRealtimeNanos;
            if (!serviceRecord.inSharedIsolatedProcess) {
                com.android.server.am.ProcessRecord processRecord4 = serviceRecord.isolationHostProc;
                if (android.webkit.WebViewZygote.isMultiprocessEnabled() && serviceRecord.serviceInfo.packageName.equals(android.webkit.WebViewZygote.getPackageName())) {
                    hostingRecord2 = com.android.server.am.HostingRecord.byWebviewZygote(serviceRecord.instanceName, serviceRecord.definingPackageName, serviceRecord.definingUid, serviceRecord.serviceInfo.processName);
                }
                if ((serviceRecord.serviceInfo.flags & 8) != 0) {
                    processRecord = processRecord4;
                    hostingRecord = com.android.server.am.HostingRecord.byAppZygote(serviceRecord.instanceName, serviceRecord.definingPackageName, serviceRecord.definingUid, serviceRecord.serviceInfo.processName);
                } else {
                    processRecord = processRecord4;
                    hostingRecord = hostingRecord2;
                }
                if (processRecord == null || z3 || z4) {
                    z6 = z5;
                    this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, processRecord, serviceRecord, false);
                } else {
                    if (serviceRecord.isSdkSandbox) {
                        startProcessLocked = this.mAm.startSdkSandboxProcessLocked(str, serviceRecord.appInfo, true, i, hostingRecord, 0, android.os.Process.toSdkSandboxUid(serviceRecord.sdkSandboxClientAppUid), serviceRecord.sdkSandboxClientAppPackage);
                        serviceRecord.isolationHostProc = startProcessLocked;
                    } else {
                        startProcessLocked = this.mAm.startProcessLocked(str, serviceRecord.appInfo, true, i, hostingRecord, 0, false, z7);
                    }
                    if (startProcessLocked == null) {
                        java.lang.String str9 = str3 + serviceRecord.appInfo.packageName + str4 + serviceRecord.appInfo.uid + str5 + serviceRecord.intent.getIntent() + ": process is bad";
                        android.util.Slog.w(str2, str9);
                        bringDownServiceLocked(serviceRecord, z5);
                        return str9;
                    }
                    z6 = z5;
                    this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, startProcessLocked, serviceRecord, true);
                    if (z7) {
                        serviceRecord.isolationHostProc = startProcessLocked;
                    }
                }
                if (serviceRecord.fgRequired) {
                    this.mAm.tempAllowlistUidLocked(serviceRecord.appInfo.uid, this.mAm.mConstants.mServiceStartForegroundTimeoutMs, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_SERVICE_LAUNCH, "fg-service-launch", 0, serviceRecord.mRecentCallingUid);
                }
                if (!this.mPendingServices.contains(serviceRecord)) {
                    this.mPendingServices.add(serviceRecord);
                }
                if (serviceRecord.delayedStop) {
                    return null;
                }
                serviceRecord.delayedStop = false;
                if (!serviceRecord.startRequested) {
                    return null;
                }
                stopServiceLocked(serviceRecord, z6);
                return null;
            }
            com.android.server.am.ProcessRecord sharedIsolatedProcess = this.mAm.mProcessList.getSharedIsolatedProcess(str, serviceRecord.appInfo.uid, serviceRecord.appInfo.packageName);
            if (sharedIsolatedProcess != null) {
                android.app.IApplicationThread thread = sharedIsolatedProcess.getThread();
                int pid = sharedIsolatedProcess.getPid();
                com.android.server.am.UidRecord uidRecord = sharedIsolatedProcess.getUidRecord();
                serviceRecord.isolationHostProc = sharedIsolatedProcess;
                try {
                    if (sharedIsolatedProcess.isThreadReady()) {
                        try {
                            if (android.os.Trace.isTagEnabled(64L)) {
                                try {
                                    j4 = 64;
                                } catch (android.os.TransactionTooLargeException e2) {
                                    e = e2;
                                    j4 = 64;
                                } catch (android.os.RemoteException e3) {
                                    e = e3;
                                    j4 = 64;
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                    j4 = 64;
                                }
                                try {
                                    android.os.Trace.traceBegin(64L, "realStartServiceLocked: " + serviceRecord.shortInstanceName);
                                } catch (android.os.TransactionTooLargeException e4) {
                                    e = e4;
                                    throw e;
                                } catch (android.os.RemoteException e5) {
                                    e = e5;
                                    j3 = j4;
                                    processRecord2 = sharedIsolatedProcess;
                                    android.util.Slog.w(str2, "Exception when starting service " + serviceRecord.shortInstanceName, e);
                                    android.os.Trace.traceEnd(j3);
                                    processRecord = processRecord2;
                                    hostingRecord = hostingRecord2;
                                    if (processRecord == null) {
                                    }
                                    z6 = z5;
                                    this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, processRecord, serviceRecord, false);
                                    if (serviceRecord.fgRequired) {
                                    }
                                    if (!this.mPendingServices.contains(serviceRecord)) {
                                    }
                                    if (serviceRecord.delayedStop) {
                                    }
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                    j2 = j4;
                                    android.os.Trace.traceEnd(j2);
                                    throw th;
                                }
                            } else {
                                j4 = 64;
                            }
                            j3 = j4;
                            processRecord2 = sharedIsolatedProcess;
                        } catch (android.os.TransactionTooLargeException e6) {
                            throw e6;
                        } catch (android.os.RemoteException e7) {
                            e = e7;
                            processRecord2 = sharedIsolatedProcess;
                            j3 = 64;
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            j2 = 64;
                        }
                        try {
                            realStartServiceLocked(serviceRecord, sharedIsolatedProcess, thread, pid, uidRecord, z, z5, 0);
                            android.os.Trace.traceEnd(j3);
                            return null;
                        } catch (android.os.TransactionTooLargeException e8) {
                            throw e8;
                        } catch (android.os.RemoteException e9) {
                            e = e9;
                            android.util.Slog.w(str2, "Exception when starting service " + serviceRecord.shortInstanceName, e);
                            android.os.Trace.traceEnd(j3);
                            processRecord = processRecord2;
                            hostingRecord = hostingRecord2;
                            if (processRecord == null) {
                            }
                            z6 = z5;
                            this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, processRecord, serviceRecord, false);
                            if (serviceRecord.fgRequired) {
                            }
                            if (!this.mPendingServices.contains(serviceRecord)) {
                            }
                            if (serviceRecord.delayedStop) {
                            }
                        }
                    } else {
                        processRecord2 = sharedIsolatedProcess;
                    }
                } catch (java.lang.Throwable th4) {
                    th = th4;
                }
            } else {
                processRecord2 = sharedIsolatedProcess;
            }
            processRecord = processRecord2;
        } else {
            com.android.server.am.ProcessRecord processRecordLocked = this.mAm.getProcessRecordLocked(str8, serviceRecord.appInfo.uid);
            if (processRecordLocked != null) {
                android.app.IApplicationThread thread2 = processRecordLocked.getThread();
                int pid2 = processRecordLocked.getPid();
                com.android.server.am.UidRecord uidRecord2 = processRecordLocked.getUidRecord();
                long isThreadReady = processRecordLocked.isThreadReady();
                try {
                    if (isThreadReady != 0) {
                        try {
                            if (android.os.Trace.isTagEnabled(64L)) {
                                try {
                                    j6 = 64;
                                    android.os.Trace.traceBegin(64L, "realStartServiceLocked: " + serviceRecord.shortInstanceName);
                                } catch (android.os.TransactionTooLargeException e10) {
                                    e = e10;
                                    j6 = 64;
                                    throw e;
                                } catch (android.os.RemoteException e11) {
                                    e = e11;
                                    j6 = 64;
                                    j5 = j6;
                                    str2 = "ActivityManager";
                                    str3 = "Unable to launch app ";
                                    str4 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
                                    str5 = " for service ";
                                    processRecord3 = processRecordLocked;
                                    j = elapsedRealtimeNanos;
                                    str6 = "Exception when starting service ";
                                    str = str8;
                                    android.util.Slog.w(str2, str6 + serviceRecord.shortInstanceName, e);
                                    android.os.Trace.traceEnd(j5);
                                    processRecord = processRecord3;
                                    hostingRecord = hostingRecord2;
                                    if (processRecord == null) {
                                    }
                                    z6 = z5;
                                    this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, processRecord, serviceRecord, false);
                                    if (serviceRecord.fgRequired) {
                                    }
                                    if (!this.mPendingServices.contains(serviceRecord)) {
                                    }
                                    if (serviceRecord.delayedStop) {
                                    }
                                } catch (java.lang.Throwable th5) {
                                    th = th5;
                                    j6 = 64;
                                    isThreadReady = j6;
                                    android.os.Trace.traceEnd(isThreadReady);
                                    throw th;
                                }
                            }
                        } catch (android.os.TransactionTooLargeException e12) {
                            e = e12;
                        } catch (android.os.RemoteException e13) {
                            e = e13;
                        } catch (java.lang.Throwable th6) {
                            th = th6;
                        }
                        try {
                            try {
                                try {
                                    processRecordLocked.addPackage(serviceRecord.appInfo.packageName, serviceRecord.appInfo.longVersionCode, this.mAm.mProcessStats);
                                    j5 = 64;
                                    j = elapsedRealtimeNanos;
                                    str6 = "Exception when starting service ";
                                    processRecord3 = processRecordLocked;
                                    str = str8;
                                    str2 = "ActivityManager";
                                    str3 = "Unable to launch app ";
                                    str4 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
                                    str5 = " for service ";
                                    try {
                                        realStartServiceLocked(serviceRecord, processRecordLocked, thread2, pid2, uidRecord2, z, z5, i2);
                                        android.os.Trace.traceEnd(64L);
                                        return null;
                                    } catch (android.os.TransactionTooLargeException e14) {
                                        throw e14;
                                    } catch (android.os.RemoteException e15) {
                                        e = e15;
                                        android.util.Slog.w(str2, str6 + serviceRecord.shortInstanceName, e);
                                        android.os.Trace.traceEnd(j5);
                                        processRecord = processRecord3;
                                        hostingRecord = hostingRecord2;
                                        if (processRecord == null) {
                                        }
                                        z6 = z5;
                                        this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, processRecord, serviceRecord, false);
                                        if (serviceRecord.fgRequired) {
                                        }
                                        if (!this.mPendingServices.contains(serviceRecord)) {
                                        }
                                        if (serviceRecord.delayedStop) {
                                        }
                                    }
                                } catch (android.os.RemoteException e16) {
                                    e = e16;
                                    str2 = "ActivityManager";
                                    str3 = "Unable to launch app ";
                                    str4 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
                                    str5 = " for service ";
                                    j = elapsedRealtimeNanos;
                                    j5 = 64;
                                    str6 = "Exception when starting service ";
                                    processRecord3 = processRecordLocked;
                                    str = str8;
                                    android.util.Slog.w(str2, str6 + serviceRecord.shortInstanceName, e);
                                    android.os.Trace.traceEnd(j5);
                                    processRecord = processRecord3;
                                    hostingRecord = hostingRecord2;
                                    if (processRecord == null) {
                                    }
                                    z6 = z5;
                                    this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, processRecord, serviceRecord, false);
                                    if (serviceRecord.fgRequired) {
                                    }
                                    if (!this.mPendingServices.contains(serviceRecord)) {
                                    }
                                    if (serviceRecord.delayedStop) {
                                    }
                                }
                            } catch (android.os.TransactionTooLargeException e17) {
                                throw e17;
                            } catch (java.lang.Throwable th7) {
                                th = th7;
                                isThreadReady = 64;
                                android.os.Trace.traceEnd(isThreadReady);
                                throw th;
                            }
                        } catch (android.os.RemoteException e18) {
                            e = e18;
                            str2 = "ActivityManager";
                            str3 = "Unable to launch app ";
                            str4 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
                            str5 = " for service ";
                            processRecord3 = processRecordLocked;
                            j = elapsedRealtimeNanos;
                            j5 = 64;
                            str6 = "Exception when starting service ";
                            str = str8;
                            android.util.Slog.w(str2, str6 + serviceRecord.shortInstanceName, e);
                            android.os.Trace.traceEnd(j5);
                            processRecord = processRecord3;
                            hostingRecord = hostingRecord2;
                            if (processRecord == null) {
                            }
                            z6 = z5;
                            this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, processRecord, serviceRecord, false);
                            if (serviceRecord.fgRequired) {
                            }
                            if (!this.mPendingServices.contains(serviceRecord)) {
                            }
                            if (serviceRecord.delayedStop) {
                            }
                        }
                    } else {
                        str2 = "ActivityManager";
                        str3 = "Unable to launch app ";
                        str4 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
                        str5 = " for service ";
                        processRecord3 = processRecordLocked;
                        j = elapsedRealtimeNanos;
                        str = str8;
                    }
                } catch (java.lang.Throwable th8) {
                    th = th8;
                }
            } else {
                str2 = "ActivityManager";
                str3 = "Unable to launch app ";
                str4 = com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
                str5 = " for service ";
                processRecord3 = processRecordLocked;
                j = elapsedRealtimeNanos;
                str = str8;
            }
            processRecord = processRecord3;
        }
        hostingRecord = hostingRecord2;
        if (processRecord == null) {
        }
        z6 = z5;
        this.mAm.mProcessList.getAppStartInfoTracker().handleProcessServiceStart(j, processRecord, serviceRecord, false);
        if (serviceRecord.fgRequired) {
        }
        if (!this.mPendingServices.contains(serviceRecord)) {
        }
        if (serviceRecord.delayedStop) {
        }
    }

    private java.lang.String getHostingRecordTriggerType(com.android.server.am.ServiceRecord serviceRecord) {
        if ("android.permission.BIND_JOB_SERVICE".equals(serviceRecord.permission) && serviceRecord.mRecentCallingUid == 1000) {
            return com.android.server.am.HostingRecord.TRIGGER_TYPE_JOB;
        }
        return "unknown";
    }

    private void requestServiceBindingsLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z, int i) throws android.os.TransactionTooLargeException {
        for (int size = serviceRecord.bindings.size() - 1; size >= 0 && requestServiceBindingLocked(serviceRecord, serviceRecord.bindings.valueAt(size), z, false, i); size--) {
        }
    }

    private int getServiceBindingOomAdjPolicyForAddLocked(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2, com.android.server.am.ConnectionRecord connectionRecord) {
        com.android.server.am.Flags.serviceBindingOomAdjPolicy();
        return 0;
    }

    private int getServiceBindingOomAdjPolicyForRemovalLocked(com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2, com.android.server.am.ConnectionRecord connectionRecord) {
        com.android.server.am.Flags.serviceBindingOomAdjPolicy();
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x013e  */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void realStartServiceLocked(com.android.server.am.ServiceRecord serviceRecord, com.android.server.am.ProcessRecord processRecord, android.app.IApplicationThread iApplicationThread, int i, com.android.server.am.UidRecord uidRecord, boolean z, boolean z2, int i2) throws android.os.RemoteException {
        ?? r5;
        com.android.server.am.ServiceRecord serviceRecord2;
        boolean contains;
        boolean z3;
        boolean z4;
        com.android.server.am.ServiceRecord serviceRecord3;
        if (iApplicationThread == null) {
            throw new android.os.RemoteException();
        }
        serviceRecord.setProcess(processRecord, iApplicationThread, i, uidRecord);
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        serviceRecord.lastActivity = uptimeMillis;
        serviceRecord.restartTime = uptimeMillis;
        boolean z5 = (i2 & 1) != 0;
        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
        boolean startService = processServiceRecord.startService(serviceRecord);
        bumpServiceExecutingLocked(serviceRecord, z, "create", 0, z5);
        this.mAm.updateLruProcessLocked(processRecord, false, null);
        updateServiceForegroundLocked(processServiceRecord, false);
        if (!z5) {
            this.mAm.enqueueOomAdjTargetLocked(processRecord);
            this.mAm.updateOomAdjPendingTargetsLocked(6);
            serviceRecord.mOomAdjBumpedInExec = true;
        }
        try {
            int i3 = serviceRecord.appInfo.uid;
            java.lang.String packageName = serviceRecord.name.getPackageName();
            java.lang.String className = serviceRecord.name.getClassName();
            com.android.internal.util.FrameworkStatsLog.write(100, i3, packageName, className);
            this.mAm.mBatteryStatsService.noteServiceStartLaunch(i3, packageName, className);
            this.mAm.notifyPackageUse(serviceRecord.serviceInfo.packageName, 1);
            iApplicationThread.scheduleCreateService(serviceRecord, serviceRecord.serviceInfo, (android.content.res.CompatibilityInfo) null, processRecord.mState.getReportedProcState());
            serviceRecord.postNotification(false);
            if (serviceRecord.allowlistManager) {
                processServiceRecord.mAllowlistManager = true;
            }
            requestServiceBindingsLocked(serviceRecord, z, i2);
            updateServiceClientActivitiesLocked(processServiceRecord, null, true);
            if (startService) {
                processServiceRecord.addBoundClientUidsOfNewService(serviceRecord);
            }
            if (!serviceRecord.startRequested || !serviceRecord.callStart || serviceRecord.pendingStarts.size() != 0) {
                z3 = false;
                z4 = z;
                serviceRecord3 = serviceRecord;
            } else {
                z3 = false;
                z4 = z;
                serviceRecord3 = serviceRecord;
                serviceRecord.pendingStarts.add(new com.android.server.am.ServiceRecord.StartItem(serviceRecord, false, serviceRecord.makeNextStartId(), null, null, 0, null, null, -1));
            }
            sendServiceArgsLocked(serviceRecord3, z4, serviceRecord3.mOomAdjBumpedInExec);
            if (serviceRecord3.delayed) {
                getServiceMapLocked(serviceRecord3.userId).mDelayedStartList.remove(serviceRecord3);
                serviceRecord3.delayed = z3;
            }
            if (serviceRecord3.delayedStop) {
                serviceRecord3.delayedStop = z3;
                if (serviceRecord3.startRequested) {
                    stopServiceLocked(serviceRecord3, z2);
                }
            }
        } catch (android.os.DeadObjectException e) {
            r5 = 0;
            serviceRecord2 = serviceRecord;
            try {
                android.util.Slog.w("ActivityManager", "Application dead when creating service " + serviceRecord2);
                this.mAm.appDiedLocked(processRecord, "Died when creating service");
                throw e;
            } catch (java.lang.Throwable th) {
                th = th;
                contains = this.mDestroyingServices.contains(serviceRecord2);
                com.android.server.am.Flags.serviceBindingOomAdjPolicy();
                serviceDoneExecutingLocked(serviceRecord, contains, contains, false, 19);
                if (startService) {
                    processServiceRecord.stopService(serviceRecord2);
                    serviceRecord2.setProcess(null, null, r5, null);
                }
                if (!contains) {
                    scheduleServiceRestartLocked(serviceRecord2, r5);
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            r5 = 0;
            serviceRecord2 = serviceRecord;
            contains = this.mDestroyingServices.contains(serviceRecord2);
            com.android.server.am.Flags.serviceBindingOomAdjPolicy();
            serviceDoneExecutingLocked(serviceRecord, contains, contains, false, 19);
            if (startService) {
            }
            if (!contains) {
            }
            throw th;
        }
    }

    private final void sendServiceArgsLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z, boolean z2) throws android.os.TransactionTooLargeException {
        int i;
        int size = serviceRecord.pendingStarts.size();
        if (size == 0) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            if (serviceRecord.pendingStarts.size() <= 0) {
                break;
            }
            com.android.server.am.ServiceRecord.StartItem remove = serviceRecord.pendingStarts.remove(0);
            if (remove.intent != null || size <= 1) {
                remove.deliveredTime = android.os.SystemClock.uptimeMillis();
                serviceRecord.deliveredStarts.add(remove);
                remove.deliveryCount++;
                if (remove.neededGrants != null) {
                    this.mAm.mUgmInternal.grantUriPermissionUncheckedFromIntent(remove.neededGrants, remove.getUriPermissionsLocked());
                }
                this.mAm.grantImplicitAccess(serviceRecord.userId, remove.intent, remove.callingId, android.os.UserHandle.getAppId(serviceRecord.appInfo.uid));
                bumpServiceExecutingLocked(serviceRecord, z, "start", 0, false);
                if (serviceRecord.fgRequired && !serviceRecord.fgWaiting) {
                    if (!serviceRecord.isForeground) {
                        scheduleServiceForegroundTransitionTimeoutLocked(serviceRecord);
                    } else {
                        serviceRecord.fgRequired = false;
                    }
                }
                i = remove.deliveryCount > 1 ? 2 : 0;
                if (remove.doneExecutingCount > 0) {
                    i |= 1;
                }
                arrayList.add(new android.app.ServiceStartArgs(remove.taskRemoved, remove.id, i, remove.intent));
            }
        }
        if (!z2) {
            this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
            this.mAm.updateOomAdjPendingTargetsLocked(6);
        }
        android.content.pm.ParceledListSlice parceledListSlice = new android.content.pm.ParceledListSlice(arrayList);
        parceledListSlice.setInlineCountLimit(4);
        try {
            serviceRecord.app.getThread().scheduleServiceArgs(serviceRecord, parceledListSlice);
            e = null;
        } catch (android.os.TransactionTooLargeException e) {
            e = e;
            android.util.Slog.w("ActivityManager", "Failed delivering service starts", e);
        } catch (android.os.RemoteException e2) {
            e = e2;
            android.util.Slog.w("ActivityManager", "Failed delivering service starts", e);
        } catch (java.lang.Exception e3) {
            e = e3;
            android.util.Slog.w("ActivityManager", "Unexpected exception", e);
        }
        if (e != null) {
            boolean contains = this.mDestroyingServices.contains(serviceRecord);
            int size2 = arrayList.size();
            while (i < size2) {
                serviceDoneExecutingLocked(serviceRecord, contains, contains, true, 19);
                i++;
            }
            this.mAm.updateOomAdjPendingTargetsLocked(19);
            if (e instanceof android.os.TransactionTooLargeException) {
                throw ((android.os.TransactionTooLargeException) e);
            }
        }
    }

    private final boolean isServiceNeededLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z, boolean z2) {
        if (serviceRecord.startRequested) {
            return true;
        }
        if (!z) {
            z2 = serviceRecord.hasAutoCreateConnections();
        }
        return z2;
    }

    private void bringDownServiceIfNeededLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z, boolean z2, boolean z3, java.lang.String str) {
        if (isServiceNeededLocked(serviceRecord, z, z2) || this.mPendingServices.contains(serviceRecord)) {
            return;
        }
        bringDownServiceLocked(serviceRecord, z3);
    }

    private void bringDownServiceLocked(final com.android.server.am.ServiceRecord serviceRecord, boolean z) {
        boolean z2;
        com.android.server.am.ActiveServices.ServiceMap serviceMap;
        if (serviceRecord.isShortFgs()) {
            android.util.Slog.w("ActivityManager", "Short FGS brought down without stopping: " + serviceRecord);
            maybeStopShortFgsTimeoutLocked(serviceRecord);
        }
        maybeStopFgsTimeoutLocked(serviceRecord);
        android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = serviceRecord.getConnections();
        int size = connections.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
            for (int i = 0; i < valueAt.size(); i++) {
                com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i);
                connectionRecord.serviceDead = true;
                connectionRecord.stopAssociation();
                try {
                    connectionRecord.conn.connected(serviceRecord.name, (android.os.IBinder) null, true);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w("ActivityManager", "Failure disconnecting service " + serviceRecord.shortInstanceName + " to connection " + valueAt.get(i).conn.asBinder() + " (in " + valueAt.get(i).binding.client.processName + ")", e);
                }
            }
            size--;
        }
        com.android.server.am.Flags.serviceBindingOomAdjPolicy();
        if (serviceRecord.app != null && serviceRecord.app.isThreadReady()) {
            boolean z3 = false;
            for (int size2 = serviceRecord.bindings.size() - 1; size2 >= 0; size2--) {
                com.android.server.am.IntentBindRecord valueAt2 = serviceRecord.bindings.valueAt(size2);
                if (valueAt2.hasBound) {
                    try {
                        bumpServiceExecutingLocked(serviceRecord, false, "bring down unbind", z3 ? 0 : 5, z3);
                        z3 |= serviceRecord.mOomAdjBumpedInExec;
                        valueAt2.hasBound = false;
                        valueAt2.requested = false;
                        serviceRecord.app.getThread().scheduleUnbindService(serviceRecord, valueAt2.intent.getIntent());
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.w("ActivityManager", "Exception when unbinding service " + serviceRecord.shortInstanceName, e2);
                        serviceProcessGoneLocked(serviceRecord, z);
                        z2 = z3;
                    }
                }
            }
            z2 = z3;
        } else {
            z2 = false;
        }
        if (serviceRecord.fgRequired) {
            android.util.Slog.w("ActivityManager", "Bringing down service while still waiting for start foreground: " + serviceRecord);
            serviceRecord.fgRequired = false;
            serviceRecord.fgWaiting = false;
            synchronized (this.mAm.mProcessStats.mLock) {
                try {
                    com.android.internal.app.procstats.ServiceState tracker = serviceRecord.getTracker();
                    if (tracker != null) {
                        tracker.setForeground(false, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                    }
                } finally {
                }
            }
            this.mAm.mAppOpsService.finishOperation(android.app.AppOpsManager.getToken(this.mAm.mAppOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null);
            this.mServiceFGAnrTimer.cancel(serviceRecord);
            if (serviceRecord.app != null) {
                android.os.Message obtainMessage = this.mAm.mHandler.obtainMessage(69);
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = serviceRecord.app;
                obtain.arg2 = serviceRecord.toString();
                obtain.arg3 = serviceRecord.getComponentName();
                obtainMessage.obj = obtain;
                this.mAm.mHandler.sendMessage(obtainMessage);
            }
        }
        serviceRecord.destroyTime = android.os.SystemClock.uptimeMillis();
        com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = getServiceMapLocked(serviceRecord.userId);
        com.android.server.am.ServiceRecord remove = serviceMapLocked.mServicesByInstanceName.remove(serviceRecord.instanceName);
        if (remove != null && remove != serviceRecord) {
            serviceMapLocked.mServicesByInstanceName.put(serviceRecord.instanceName, remove);
            throw new java.lang.IllegalStateException("Bringing down " + serviceRecord + " but actually running " + remove);
        }
        serviceMapLocked.mServicesByIntent.remove(serviceRecord.intent);
        serviceRecord.totalRestartCount = 0;
        unscheduleServiceRestartLocked(serviceRecord, 0, true);
        for (int size3 = this.mPendingServices.size() - 1; size3 >= 0; size3--) {
            if (this.mPendingServices.get(size3) == serviceRecord) {
                this.mPendingServices.remove(size3);
            }
        }
        this.mPendingBringups.remove(serviceRecord);
        cancelForegroundNotificationLocked(serviceRecord);
        boolean z4 = serviceRecord.isForeground;
        if (!z4) {
            serviceMap = serviceMapLocked;
        } else {
            maybeStopShortFgsTimeoutLocked(serviceRecord);
            maybeStopFgsTimeoutLocked(serviceRecord);
            decActiveForegroundAppLocked(serviceMapLocked, serviceRecord);
            synchronized (this.mAm.mProcessStats.mLock) {
                try {
                    com.android.internal.app.procstats.ServiceState tracker2 = serviceRecord.getTracker();
                    if (tracker2 != null) {
                        tracker2.setForeground(false, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                    }
                } finally {
                }
            }
            this.mAm.mAppOpsService.finishOperation(android.app.AppOpsManager.getToken(this.mAm.mAppOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null);
            unregisterAppOpCallbackLocked(serviceRecord);
            serviceRecord.mFgsExitTime = android.os.SystemClock.uptimeMillis();
            serviceMap = serviceMapLocked;
            logFGSStateChangeLocked(serviceRecord, 2, serviceRecord.mFgsExitTime > serviceRecord.mFgsEnterTime ? (int) (serviceRecord.mFgsExitTime - serviceRecord.mFgsEnterTime) : 0, 2, 0, 0, false);
            synchronized (this.mFGSLogger) {
                this.mFGSLogger.logForegroundServiceStop(serviceRecord.appInfo.uid, serviceRecord);
            }
            this.mAm.updateForegroundServiceUsageStats(serviceRecord.name, serviceRecord.userId, false);
        }
        serviceRecord.isForeground = false;
        serviceRecord.mFgsNotificationWasDeferred = false;
        dropFgsNotificationStateLocked(serviceRecord);
        serviceRecord.foregroundId = 0;
        serviceRecord.foregroundNoti = null;
        resetFgsRestrictionLocked(serviceRecord);
        if (z4) {
            signalForegroundServiceObserversLocked(serviceRecord);
        }
        serviceRecord.clearDeliveredStartsLocked();
        serviceRecord.pendingStarts.clear();
        serviceMap.mDelayedStartList.remove(serviceRecord);
        if (serviceRecord.app != null) {
            this.mAm.mBatteryStatsService.noteServiceStopLaunch(serviceRecord.appInfo.uid, serviceRecord.name.getPackageName(), serviceRecord.name.getClassName());
            stopServiceAndUpdateAllowlistManagerLocked(serviceRecord);
            if (serviceRecord.app.isThreadReady()) {
                this.mAm.updateLruProcessLocked(serviceRecord.app, false, null);
                updateServiceForegroundLocked(serviceRecord.app.mServices, false);
                if (serviceRecord.mIsFgsDelegate) {
                    if (serviceRecord.mFgsDelegation.mConnection != null) {
                        this.mAm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.am.ActiveServices.lambda$bringDownServiceLocked$1(com.android.server.am.ServiceRecord.this);
                            }
                        });
                    }
                    int size4 = this.mFgsDelegations.size() - 1;
                    while (true) {
                        if (size4 < 0) {
                            break;
                        }
                        if (this.mFgsDelegations.valueAt(size4) == serviceRecord) {
                            this.mFgsDelegations.removeAt(size4);
                            break;
                        }
                        size4--;
                    }
                } else {
                    try {
                        bumpServiceExecutingLocked(serviceRecord, false, "destroy", z2 ? 0 : 5, z2);
                        this.mDestroyingServices.add(serviceRecord);
                        z2 |= serviceRecord.mOomAdjBumpedInExec;
                        serviceRecord.destroying = true;
                        serviceRecord.app.getThread().scheduleStopService(serviceRecord);
                    } catch (java.lang.Exception e3) {
                        android.util.Slog.w("ActivityManager", "Exception when destroying service " + serviceRecord.shortInstanceName, e3);
                        serviceProcessGoneLocked(serviceRecord, z);
                    }
                }
            }
        }
        if (!z2) {
            this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
            if (!z) {
                this.mAm.updateOomAdjPendingTargetsLocked(19);
            }
        }
        if (serviceRecord.bindings.size() > 0) {
            serviceRecord.bindings.clear();
        }
        if (serviceRecord.restarter instanceof com.android.server.am.ActiveServices.ServiceRestarter) {
            ((com.android.server.am.ActiveServices.ServiceRestarter) serviceRecord.restarter).setService(null);
        }
        synchronized (this.mAm.mProcessStats.mLock) {
            try {
                int memFactorLocked = this.mAm.mProcessStats.getMemFactorLocked();
                if (serviceRecord.tracker != null) {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    serviceRecord.tracker.setStarted(false, memFactorLocked, uptimeMillis);
                    serviceRecord.tracker.setBound(false, memFactorLocked, uptimeMillis);
                    if (serviceRecord.executeNesting == 0) {
                        serviceRecord.tracker.clearCurrentOwner(serviceRecord, false);
                        serviceRecord.tracker = null;
                    }
                }
            } finally {
            }
        }
        serviceMap.ensureNotStartingBackgroundLocked(serviceRecord);
        updateNumForegroundServicesLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$bringDownServiceLocked$1(com.android.server.am.ServiceRecord serviceRecord) {
        serviceRecord.mFgsDelegation.mConnection.onServiceDisconnected(serviceRecord.mFgsDelegation.mOptions.getComponentName());
    }

    private void dropFgsNotificationStateLocked(com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord.foregroundNoti == null) {
            return;
        }
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(serviceRecord.userId);
        boolean z = false;
        if (serviceMap != null) {
            int size = serviceMap.mServicesByInstanceName.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                com.android.server.am.ServiceRecord valueAt = serviceMap.mServicesByInstanceName.valueAt(i);
                if (valueAt == serviceRecord || !valueAt.isForeground || serviceRecord.foregroundId != valueAt.foregroundId || !serviceRecord.appInfo.packageName.equals(valueAt.appInfo.packageName)) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            }
        } else {
            android.util.Slog.wtf("ActivityManager", "FGS " + serviceRecord + " not found!");
        }
        if (!z) {
            serviceRecord.stripForegroundServiceFlagFromNotification();
        }
    }

    int removeConnectionLocked(com.android.server.am.ConnectionRecord connectionRecord, com.android.server.am.ProcessRecord processRecord, com.android.server.wm.ActivityServiceConnectionsHolder activityServiceConnectionsHolder, boolean z) {
        int i;
        android.os.IBinder asBinder = connectionRecord.conn.asBinder();
        com.android.server.am.AppBindRecord appBindRecord = connectionRecord.binding;
        com.android.server.am.ServiceRecord serviceRecord = appBindRecord.service;
        java.util.ArrayList<com.android.server.am.ConnectionRecord> arrayList = serviceRecord.getConnections().get(asBinder);
        if (arrayList != null) {
            arrayList.remove(connectionRecord);
            if (arrayList.size() == 0) {
                serviceRecord.removeConnection(asBinder);
            }
        }
        appBindRecord.connections.remove(connectionRecord);
        connectionRecord.stopAssociation();
        if (connectionRecord.activity != null && connectionRecord.activity != activityServiceConnectionsHolder) {
            connectionRecord.activity.removeConnection(connectionRecord);
        }
        if (appBindRecord.client != processRecord) {
            com.android.server.am.ProcessServiceRecord processServiceRecord = appBindRecord.client.mServices;
            processServiceRecord.removeConnection(connectionRecord);
            if (connectionRecord.hasFlag(8)) {
                processServiceRecord.updateHasAboveClientLocked();
            }
            if (connectionRecord.hasFlag(16777216)) {
                serviceRecord.updateAllowlistManager();
                if (!serviceRecord.allowlistManager && serviceRecord.app != null) {
                    updateAllowlistManagerLocked(serviceRecord.app.mServices);
                }
            }
            if (connectionRecord.hasFlag(1048576)) {
                serviceRecord.updateIsAllowedBgActivityStartsByBinding();
            }
            if (connectionRecord.hasFlag(65536)) {
                processServiceRecord.updateHasTopStartedAlmostPerceptibleServices();
            }
            if (serviceRecord.app != null) {
                updateServiceClientActivitiesLocked(serviceRecord.app.mServices, connectionRecord, true);
            }
        }
        java.util.ArrayList<com.android.server.am.ConnectionRecord> arrayList2 = this.mServiceConnections.get(asBinder);
        if (arrayList2 != null) {
            arrayList2.remove(connectionRecord);
            if (arrayList2.size() == 0) {
                this.mServiceConnections.remove(asBinder);
            }
        }
        this.mAm.stopAssociationLocked(appBindRecord.client.uid, appBindRecord.client.processName, serviceRecord.appInfo.uid, serviceRecord.appInfo.longVersionCode, serviceRecord.instanceName, serviceRecord.processName);
        if (appBindRecord.connections.size() == 0) {
            appBindRecord.intent.apps.remove(appBindRecord.client);
        }
        if (connectionRecord.serviceDead) {
            return 0;
        }
        if (serviceRecord.app != null && serviceRecord.app.isThreadReady() && appBindRecord.intent.apps.size() == 0 && appBindRecord.intent.hasBound) {
            i = getServiceBindingOomAdjPolicyForRemovalLocked(appBindRecord.client, serviceRecord.app, connectionRecord);
            boolean z2 = (i & 4) != 0;
            try {
                bumpServiceExecutingLocked(serviceRecord, false, "unbind", z2 ? 0 : 5, z2);
                if (appBindRecord.client != serviceRecord.app && connectionRecord.notHasFlag(32) && serviceRecord.app.mState.getSetProcState() <= 13) {
                    this.mAm.updateLruProcessLocked(serviceRecord.app, false, null);
                }
                appBindRecord.intent.hasBound = false;
                appBindRecord.intent.doRebind = false;
                serviceRecord.app.getThread().scheduleUnbindService(serviceRecord, appBindRecord.intent.intent.getIntent());
            } catch (java.lang.Exception e) {
                android.util.Slog.w("ActivityManager", "Exception when unbinding service " + serviceRecord.shortInstanceName, e);
                serviceProcessGoneLocked(serviceRecord, z);
            }
        } else {
            i = 0;
        }
        if (serviceRecord.getConnections().isEmpty()) {
            this.mPendingServices.remove(serviceRecord);
            this.mPendingBringups.remove(serviceRecord);
        }
        if (connectionRecord.hasFlag(1)) {
            boolean hasAutoCreateConnections = serviceRecord.hasAutoCreateConnections();
            if (!hasAutoCreateConnections && serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord.tracker.setBound(false, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                }
            }
            bringDownServiceIfNeededLocked(serviceRecord, true, hasAutoCreateConnections, z, "removeConnection");
        }
        return i;
    }

    void serviceDoneExecutingLocked(com.android.server.am.ServiceRecord serviceRecord, int i, int i2, int i3, boolean z, android.content.Intent intent) {
        boolean contains = this.mDestroyingServices.contains(serviceRecord);
        if (serviceRecord != null) {
            if (i == 1) {
                serviceRecord.callStart = true;
                if (i3 != 1000) {
                    serviceRecord.startCommandResult = i3;
                }
                switch (i3) {
                    case 0:
                    case 1:
                        serviceRecord.findDeliveredStart(i2, false, true);
                        serviceRecord.stopIfKilled = false;
                        break;
                    case 2:
                        serviceRecord.findDeliveredStart(i2, false, true);
                        if (serviceRecord.getLastStartId() == i2) {
                            serviceRecord.stopIfKilled = true;
                            break;
                        }
                        break;
                    case 3:
                        com.android.server.am.ServiceRecord.StartItem findDeliveredStart = serviceRecord.findDeliveredStart(i2, false, false);
                        if (findDeliveredStart != null) {
                            findDeliveredStart.deliveryCount = 0;
                            findDeliveredStart.doneExecutingCount++;
                            serviceRecord.stopIfKilled = true;
                            break;
                        }
                        break;
                    case 1000:
                        serviceRecord.findDeliveredStart(i2, true, true);
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown service start result: " + i3);
                }
                if (i3 == 0) {
                    serviceRecord.callStart = false;
                }
            } else if (i == 2) {
                if (contains) {
                    if (serviceRecord.executeNesting != 1) {
                        android.util.Slog.w("ActivityManager", "Service done with onDestroy, but executeNesting=" + serviceRecord.executeNesting + ": " + serviceRecord);
                        serviceRecord.executeNesting = 1;
                    }
                } else if (serviceRecord.app != null) {
                    android.util.Slog.w("ActivityManager", "Service done with onDestroy, but not inDestroying: " + serviceRecord + ", app=" + serviceRecord.app);
                }
            }
            long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
            com.android.server.am.Flags.serviceBindingOomAdjPolicy();
            serviceDoneExecutingLocked(serviceRecord, contains, contains, z, 20);
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
            return;
        }
        android.util.Slog.w("ActivityManager", "Done executing unknown service from pid " + this.mAm.mInjector.getCallingPid());
    }

    private void serviceProcessGoneLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z) {
        if (serviceRecord.tracker != null) {
            synchronized (this.mAm.mProcessStats.mLock) {
                int memFactorLocked = this.mAm.mProcessStats.getMemFactorLocked();
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                serviceRecord.tracker.setExecuting(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setForeground(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setBound(false, memFactorLocked, uptimeMillis);
                serviceRecord.tracker.setStarted(false, memFactorLocked, uptimeMillis);
            }
        }
        serviceDoneExecutingLocked(serviceRecord, true, true, z, 12);
    }

    private void serviceDoneExecutingLocked(com.android.server.am.ServiceRecord serviceRecord, boolean z, boolean z2, boolean z3, int i) {
        serviceRecord.executeNesting--;
        if (serviceRecord.executeNesting <= 0) {
            if (serviceRecord.app != null) {
                com.android.server.am.ProcessServiceRecord processServiceRecord = serviceRecord.app.mServices;
                processServiceRecord.setExecServicesFg(false);
                processServiceRecord.stopExecutingService(serviceRecord);
                if (processServiceRecord.numberOfExecutingServices() == 0) {
                    if (serviceRecord.app.mPid != 0) {
                        this.mActiveServiceAnrTimer.cancel(serviceRecord.app);
                    }
                } else if (serviceRecord.executeFg) {
                    int numberOfExecutingServices = processServiceRecord.numberOfExecutingServices() - 1;
                    while (true) {
                        if (numberOfExecutingServices < 0) {
                            break;
                        }
                        if (!processServiceRecord.getExecutingServiceAt(numberOfExecutingServices).executeFg) {
                            numberOfExecutingServices--;
                        } else {
                            processServiceRecord.setExecServicesFg(true);
                            break;
                        }
                    }
                }
                if (z) {
                    this.mDestroyingServices.remove(serviceRecord);
                    serviceRecord.bindings.clear();
                }
                if (i != 0) {
                    if (z3) {
                        this.mAm.enqueueOomAdjTargetLocked(serviceRecord.app);
                    } else {
                        this.mAm.updateOomAdjLocked(serviceRecord.app, i);
                    }
                }
                serviceRecord.mOomAdjBumpedInExec = false;
            }
            serviceRecord.executeFg = false;
            if (serviceRecord.tracker != null) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    try {
                        int memFactorLocked = this.mAm.mProcessStats.getMemFactorLocked();
                        long uptimeMillis = android.os.SystemClock.uptimeMillis();
                        serviceRecord.tracker.setExecuting(false, memFactorLocked, uptimeMillis);
                        serviceRecord.tracker.setForeground(false, memFactorLocked, uptimeMillis);
                        if (z2) {
                            serviceRecord.tracker.clearCurrentOwner(serviceRecord, false);
                            serviceRecord.tracker = null;
                        }
                    } finally {
                    }
                }
            }
            if (z2) {
                if (serviceRecord.app != null && !serviceRecord.app.isPersistent()) {
                    stopServiceAndUpdateAllowlistManagerLocked(serviceRecord);
                }
                serviceRecord.setProcess(null, null, 0, null);
            }
        }
    }

    boolean attachApplicationLocked(com.android.server.am.ProcessRecord processRecord, java.lang.String str) throws android.os.RemoteException {
        boolean z;
        com.android.server.am.ServiceRecord serviceRecord;
        long j;
        processRecord.mState.setBackgroundRestricted(appRestrictedAnyInBackground(processRecord.uid, processRecord.info.packageName));
        if (this.mPendingServices.size() <= 0) {
            z = false;
        } else {
            com.android.server.am.ServiceRecord serviceRecord2 = null;
            int i = 0;
            z = false;
            while (i < this.mPendingServices.size()) {
                try {
                    serviceRecord = this.mPendingServices.get(i);
                } catch (android.os.RemoteException e) {
                    e = e;
                }
                try {
                    if (processRecord == serviceRecord.isolationHostProc || (processRecord.uid == serviceRecord.appInfo.uid && str.equals(serviceRecord.processName))) {
                        android.app.IApplicationThread thread = processRecord.getThread();
                        int pid = processRecord.getPid();
                        com.android.server.am.UidRecord uidRecord = processRecord.getUidRecord();
                        this.mPendingServices.remove(i);
                        int i2 = i - 1;
                        processRecord.addPackage(serviceRecord.appInfo.packageName, serviceRecord.appInfo.longVersionCode, this.mAm.mProcessStats);
                        try {
                            if (android.os.Trace.isTagEnabled(64L)) {
                                android.os.Trace.traceBegin(64L, "realStartServiceLocked: " + serviceRecord.shortInstanceName);
                            }
                            j = 64;
                            try {
                                realStartServiceLocked(serviceRecord, processRecord, thread, pid, uidRecord, serviceRecord.createdFromFg, true, 0);
                                android.os.Trace.traceEnd(64L);
                                if (!isServiceNeededLocked(serviceRecord, false, false)) {
                                    bringDownServiceLocked(serviceRecord, true);
                                }
                                this.mAm.updateOomAdjPendingTargetsLocked(6);
                                z = true;
                                i = i2;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                android.os.Trace.traceEnd(j);
                                throw th;
                            }
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            j = 64;
                        }
                    }
                    i++;
                    serviceRecord2 = serviceRecord;
                } catch (android.os.RemoteException e2) {
                    e = e2;
                    serviceRecord2 = serviceRecord;
                    android.util.Slog.w("ActivityManager", "Exception in new application when starting service " + serviceRecord2.shortInstanceName, e);
                    throw e;
                }
            }
        }
        if (this.mRestartingServices.size() > 0) {
            boolean z2 = false;
            for (int i3 = 0; i3 < this.mRestartingServices.size(); i3++) {
                com.android.server.am.ServiceRecord serviceRecord3 = this.mRestartingServices.get(i3);
                if (processRecord == serviceRecord3.isolationHostProc || (processRecord.uid == serviceRecord3.appInfo.uid && str.equals(serviceRecord3.processName))) {
                    this.mAm.mHandler.removeCallbacks(serviceRecord3.restarter);
                    this.mAm.mHandler.post(serviceRecord3.restarter);
                    z2 = true;
                }
            }
            if (z2) {
                this.mAm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.ActiveServices.this.lambda$attachApplicationLocked$2();
                    }
                });
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attachApplicationLocked$2() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                rescheduleServiceRestartIfPossibleLocked(getExtraRestartTimeInBetweenLocked(), this.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN, "other", uptimeMillis);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    void processStartTimedOutLocked(com.android.server.am.ProcessRecord processRecord) {
        int size = this.mPendingServices.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            com.android.server.am.ServiceRecord serviceRecord = this.mPendingServices.get(i);
            if ((processRecord.uid == serviceRecord.appInfo.uid && processRecord.processName.equals(serviceRecord.processName)) || serviceRecord.isolationHostProc == processRecord) {
                android.util.Slog.w("ActivityManager", "Forcing bringing down service: " + serviceRecord);
                serviceRecord.isolationHostProc = null;
                this.mPendingServices.remove(i);
                size = this.mPendingServices.size();
                i--;
                bringDownServiceLocked(serviceRecord, true);
                z = true;
            }
            i++;
        }
        if (z) {
            this.mAm.updateOomAdjPendingTargetsLocked(12);
        }
    }

    private boolean collectPackageServicesLocked(java.lang.String str, java.util.Set<java.lang.String> set, boolean z, boolean z2, android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> arrayMap) {
        boolean z3 = false;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            com.android.server.am.ServiceRecord valueAt = arrayMap.valueAt(size);
            if ((str == null || (valueAt.packageName.equals(str) && (set == null || set.contains(valueAt.name.getClassName())))) && (valueAt.app == null || z || !valueAt.app.isPersistent())) {
                if (!z2) {
                    return true;
                }
                android.util.Slog.i("ActivityManager", "  Force stopping service " + valueAt);
                if (valueAt.app != null && !valueAt.app.isPersistent()) {
                    stopServiceAndUpdateAllowlistManagerLocked(valueAt);
                }
                valueAt.setProcess(null, null, 0, null);
                valueAt.isolationHostProc = null;
                if (this.mTmpCollectionResults == null) {
                    this.mTmpCollectionResults = new java.util.ArrayList<>();
                }
                this.mTmpCollectionResults.add(valueAt);
                z3 = true;
            }
        }
        return z3;
    }

    boolean bringDownDisabledPackageServicesLocked(java.lang.String str, java.util.Set<java.lang.String> set, int i, boolean z, boolean z2, boolean z3) {
        if (this.mTmpCollectionResults != null) {
            this.mTmpCollectionResults.clear();
        }
        if (i == -1) {
            for (int size = this.mServiceMap.size() - 1; size >= 0; size--) {
                r2 |= collectPackageServicesLocked(str, set, z, z3, this.mServiceMap.valueAt(size).mServicesByInstanceName);
                if (!z3 && r2) {
                    return true;
                }
                if (z3 && set == null) {
                    forceStopPackageLocked(str, this.mServiceMap.valueAt(size).mUserId);
                }
            }
        } else {
            com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i);
            r2 = serviceMap != null ? collectPackageServicesLocked(str, set, z, z3, serviceMap.mServicesByInstanceName) : false;
            if (z3 && set == null) {
                forceStopPackageLocked(str, i);
            }
        }
        if (this.mTmpCollectionResults != null) {
            int size2 = this.mTmpCollectionResults.size();
            for (int i2 = size2 - 1; i2 >= 0; i2--) {
                bringDownServiceLocked(this.mTmpCollectionResults.get(i2), true);
            }
            if (size2 > 0) {
                this.mAm.updateOomAdjPendingTargetsLocked(22);
            }
            if (z2 && !this.mTmpCollectionResults.isEmpty()) {
                final java.util.ArrayList arrayList = (java.util.ArrayList) this.mTmpCollectionResults.clone();
                this.mAm.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.ActiveServices.lambda$bringDownDisabledPackageServicesLocked$3(arrayList);
                    }
                }, 250L);
            }
            this.mTmpCollectionResults.clear();
        }
        return r2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$bringDownDisabledPackageServicesLocked$3(java.util.ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            ((com.android.server.am.ServiceRecord) arrayList.get(i)).cancelNotification();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    private void signalForegroundServiceObserversLocked(com.android.server.am.ServiceRecord serviceRecord) {
        int beginBroadcast = this.mFgsObservers.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mFgsObservers.getBroadcastItem(i).onForegroundStateChanged(serviceRecord, serviceRecord.appInfo.packageName, serviceRecord.userId, serviceRecord.isForeground);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mFgsObservers.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mAm"})
    boolean registerForegroundServiceObserverLocked(int i, android.app.IForegroundServiceObserver iForegroundServiceObserver) {
        try {
            int size = this.mServiceMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.am.ActiveServices.ServiceMap valueAt = this.mServiceMap.valueAt(i2);
                if (valueAt != null) {
                    int size2 = valueAt.mServicesByInstanceName.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        com.android.server.am.ServiceRecord valueAt2 = valueAt.mServicesByInstanceName.valueAt(i3);
                        if (valueAt2.isForeground && i == valueAt2.appInfo.uid) {
                            iForegroundServiceObserver.onForegroundStateChanged(valueAt2, valueAt2.appInfo.packageName, valueAt2.userId, true);
                        }
                    }
                }
            }
            this.mFgsObservers.register(iForegroundServiceObserver);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e("ActivityManager", "Bad FGS observer from uid " + i);
            return false;
        }
    }

    void forceStopPackageLocked(java.lang.String str, int i) {
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i);
        if (serviceMap != null && serviceMap.mActiveForegroundApps.size() > 0) {
            for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                if (serviceMap.mActiveForegroundApps.valueAt(size).mPackageName.equals(str)) {
                    serviceMap.mActiveForegroundApps.removeAt(size);
                    serviceMap.mActiveForegroundAppsChanged = true;
                }
            }
            if (serviceMap.mActiveForegroundAppsChanged) {
                requestUpdateActiveForegroundAppsLocked(serviceMap, 0L);
            }
        }
        for (int size2 = this.mPendingBringups.size() - 1; size2 >= 0; size2--) {
            com.android.server.am.ServiceRecord keyAt = this.mPendingBringups.keyAt(size2);
            if (android.text.TextUtils.equals(keyAt.packageName, str) && keyAt.userId == i) {
                this.mPendingBringups.removeAt(size2);
            }
        }
        removeServiceRestartBackoffEnabledLocked(str);
        removeServiceNotificationDeferralsLocked(str, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void cleanUpServices(int i, android.content.ComponentName componentName, android.content.Intent intent) {
        boolean z;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> servicesLocked = getServicesLocked(i);
        boolean z2 = 1;
        for (int size = servicesLocked.size() - 1; size >= 0; size--) {
            com.android.server.am.ServiceRecord valueAt = servicesLocked.valueAt(size);
            if (valueAt.packageName.equals(componentName.getPackageName())) {
                arrayList.add(valueAt);
            }
        }
        int size2 = arrayList.size() - 1;
        boolean z3 = false;
        while (size2 >= 0) {
            com.android.server.am.ServiceRecord serviceRecord = (com.android.server.am.ServiceRecord) arrayList.get(size2);
            if (!serviceRecord.startRequested) {
                z = z2;
            } else if ((serviceRecord.serviceInfo.flags & z2) != 0) {
                android.util.Slog.i("ActivityManager", "Stopping service " + serviceRecord.shortInstanceName + ": remove task");
                stopServiceLocked(serviceRecord, z2);
                z = z2;
                z3 = z;
            } else {
                serviceRecord.pendingStarts.add(new com.android.server.am.ServiceRecord.StartItem(serviceRecord, true, serviceRecord.getLastStartId(), intent, null, 0, null, null, -1));
                if (serviceRecord.app == null || !serviceRecord.app.isThreadReady()) {
                    z = true;
                } else {
                    z = true;
                    try {
                        sendServiceArgsLocked(serviceRecord, true, false);
                    } catch (android.os.TransactionTooLargeException e) {
                    }
                }
            }
            size2--;
            z2 = z;
        }
        if (z3) {
            this.mAm.updateOomAdjPendingTargetsLocked(17);
        }
    }

    final void killServicesLocked(com.android.server.am.ProcessRecord processRecord, boolean z) {
        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
        for (int numberOfConnections = processServiceRecord.numberOfConnections() - 1; numberOfConnections >= 0; numberOfConnections--) {
            removeConnectionLocked(processServiceRecord.getConnectionAt(numberOfConnections), processRecord, null, true);
        }
        updateServiceConnectionActivitiesLocked(processServiceRecord);
        processServiceRecord.removeAllConnections();
        processServiceRecord.removeAllSdkSandboxConnections();
        processServiceRecord.mAllowlistManager = false;
        for (int numberOfRunningServices = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            com.android.server.am.ServiceRecord runningServiceAt = processServiceRecord.getRunningServiceAt(numberOfRunningServices);
            this.mAm.mBatteryStatsService.noteServiceStopLaunch(runningServiceAt.appInfo.uid, runningServiceAt.name.getPackageName(), runningServiceAt.name.getClassName());
            if (runningServiceAt.app != processRecord && runningServiceAt.app != null && !runningServiceAt.app.isPersistent()) {
                runningServiceAt.app.mServices.stopService(runningServiceAt);
                runningServiceAt.app.mServices.updateBoundClientUids();
            }
            runningServiceAt.setProcess(null, null, 0, null);
            runningServiceAt.isolationHostProc = null;
            runningServiceAt.executeNesting = 0;
            runningServiceAt.mOomAdjBumpedInExec = false;
            synchronized (this.mAm.mProcessStats.mLock) {
                runningServiceAt.forceClearTracker();
            }
            this.mDestroyingServices.remove(runningServiceAt);
            for (int size = runningServiceAt.bindings.size() - 1; size >= 0; size--) {
                com.android.server.am.IntentBindRecord valueAt = runningServiceAt.bindings.valueAt(size);
                valueAt.binder = null;
                valueAt.hasBound = false;
                valueAt.received = false;
                valueAt.requested = false;
                for (int size2 = valueAt.apps.size() - 1; size2 >= 0; size2--) {
                    com.android.server.am.ProcessRecord keyAt = valueAt.apps.keyAt(size2);
                    if (!keyAt.isKilledByAm() && keyAt.getThread() != null) {
                        com.android.server.am.AppBindRecord valueAt2 = valueAt.apps.valueAt(size2);
                        for (int size3 = valueAt2.connections.size() - 1; size3 >= 0; size3--) {
                            com.android.server.am.ConnectionRecord valueAt3 = valueAt2.connections.valueAt(size3);
                            if (!valueAt3.hasFlag(1) || !valueAt3.notHasFlag(48)) {
                            }
                        }
                    }
                }
            }
        }
        com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = getServiceMapLocked(processRecord.userId);
        for (int numberOfRunningServices2 = processServiceRecord.numberOfRunningServices() - 1; numberOfRunningServices2 >= 0; numberOfRunningServices2--) {
            com.android.server.am.ServiceRecord runningServiceAt2 = processServiceRecord.getRunningServiceAt(numberOfRunningServices2);
            if (!processRecord.isPersistent()) {
                processServiceRecord.stopService(runningServiceAt2);
                processServiceRecord.updateBoundClientUids();
            }
            com.android.server.am.ServiceRecord serviceRecord = serviceMapLocked.mServicesByInstanceName.get(runningServiceAt2.instanceName);
            if (serviceRecord != runningServiceAt2) {
                if (serviceRecord != null) {
                    android.util.Slog.wtf("ActivityManager", "Service " + runningServiceAt2 + " in process " + processRecord + " not same as in map: " + serviceRecord);
                }
            } else if (z && runningServiceAt2.crashCount >= this.mAm.mConstants.BOUND_SERVICE_MAX_CRASH_RETRY && (runningServiceAt2.serviceInfo.applicationInfo.flags & 8) == 0) {
                android.util.Slog.w("ActivityManager", "Service crashed " + runningServiceAt2.crashCount + " times, stopping: " + runningServiceAt2);
                android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_SERVICE_CRASHED_TOO_MUCH, java.lang.Integer.valueOf(runningServiceAt2.userId), java.lang.Integer.valueOf(runningServiceAt2.crashCount), runningServiceAt2.shortInstanceName, java.lang.Integer.valueOf(runningServiceAt2.app != null ? runningServiceAt2.app.getPid() : -1));
                bringDownServiceLocked(runningServiceAt2, true);
            } else if (!z || !this.mAm.mUserController.isUserRunning(runningServiceAt2.userId, 0)) {
                bringDownServiceLocked(runningServiceAt2, true);
            } else if (!scheduleServiceRestartLocked(runningServiceAt2, true)) {
                bringDownServiceLocked(runningServiceAt2, true);
            } else if (runningServiceAt2.canStopIfKilled(false)) {
                runningServiceAt2.startRequested = false;
                if (runningServiceAt2.tracker != null) {
                    synchronized (this.mAm.mProcessStats.mLock) {
                        runningServiceAt2.tracker.setStarted(false, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        this.mAm.updateOomAdjPendingTargetsLocked(19);
        if (!z) {
            processServiceRecord.stopAllServices();
            processServiceRecord.clearBoundClientUids();
            for (int size4 = this.mRestartingServices.size() - 1; size4 >= 0; size4--) {
                com.android.server.am.ServiceRecord serviceRecord2 = this.mRestartingServices.get(size4);
                if (serviceRecord2.processName.equals(processRecord.processName) && serviceRecord2.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mRestartingServices.remove(size4);
                    clearRestartingIfNeededLocked(serviceRecord2);
                }
            }
            for (int size5 = this.mPendingServices.size() - 1; size5 >= 0; size5--) {
                com.android.server.am.ServiceRecord serviceRecord3 = this.mPendingServices.get(size5);
                if (serviceRecord3.processName.equals(processRecord.processName) && serviceRecord3.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mPendingServices.remove(size5);
                }
            }
            for (int size6 = this.mPendingBringups.size() - 1; size6 >= 0; size6--) {
                com.android.server.am.ServiceRecord keyAt2 = this.mPendingBringups.keyAt(size6);
                if (keyAt2.processName.equals(processRecord.processName) && keyAt2.serviceInfo.applicationInfo.uid == processRecord.info.uid) {
                    this.mPendingBringups.removeAt(size6);
                }
            }
        }
        int size7 = this.mDestroyingServices.size();
        while (size7 > 0) {
            size7--;
            com.android.server.am.ServiceRecord serviceRecord4 = this.mDestroyingServices.get(size7);
            if (serviceRecord4.app == processRecord) {
                synchronized (this.mAm.mProcessStats.mLock) {
                    serviceRecord4.forceClearTracker();
                }
                this.mDestroyingServices.remove(size7);
            }
        }
        processServiceRecord.stopAllExecutingServices();
        processServiceRecord.noteScheduleServiceTimeoutPending(false);
    }

    android.app.ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked(com.android.server.am.ServiceRecord serviceRecord) {
        android.app.ActivityManager.RunningServiceInfo runningServiceInfo = new android.app.ActivityManager.RunningServiceInfo();
        runningServiceInfo.service = serviceRecord.name;
        if (serviceRecord.app != null) {
            runningServiceInfo.pid = serviceRecord.app.getPid();
        }
        runningServiceInfo.uid = serviceRecord.appInfo.uid;
        runningServiceInfo.process = serviceRecord.processName;
        runningServiceInfo.foreground = serviceRecord.isForeground;
        runningServiceInfo.activeSince = serviceRecord.createRealTime;
        runningServiceInfo.started = serviceRecord.startRequested;
        runningServiceInfo.clientCount = serviceRecord.getConnections().size();
        runningServiceInfo.crashCount = serviceRecord.crashCount;
        runningServiceInfo.lastActivityTime = serviceRecord.lastActivity;
        if (serviceRecord.isForeground) {
            runningServiceInfo.flags |= 2;
        }
        if (serviceRecord.startRequested) {
            runningServiceInfo.flags |= 1;
        }
        if (serviceRecord.app != null && serviceRecord.app.getPid() == com.android.server.am.ActivityManagerService.MY_PID) {
            runningServiceInfo.flags |= 4;
        }
        if (serviceRecord.app != null && serviceRecord.app.isPersistent()) {
            runningServiceInfo.flags |= 8;
        }
        android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = serviceRecord.getConnections();
        for (int size = connections.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
            for (int i = 0; i < valueAt.size(); i++) {
                com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i);
                if (connectionRecord.clientLabel != 0) {
                    runningServiceInfo.clientPackage = connectionRecord.binding.client.info.packageName;
                    runningServiceInfo.clientLabel = connectionRecord.clientLabel;
                    return runningServiceInfo;
                }
            }
        }
        return runningServiceInfo;
    }

    java.util.List<android.app.ActivityManager.RunningServiceInfo> getRunningServiceInfoLocked(int i, int i2, int i3, boolean z, boolean z2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        long clearCallingIdentity = this.mAm.mInjector.clearCallingIdentity();
        int i4 = 0;
        try {
            if (z2) {
                int[] users = this.mAm.mUserController.getUsers();
                for (int i5 = 0; i5 < users.length && arrayList.size() < i; i5++) {
                    android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> servicesLocked = getServicesLocked(users[i5]);
                    for (int i6 = 0; i6 < servicesLocked.size() && arrayList.size() < i; i6++) {
                        arrayList.add(makeRunningServiceInfoLocked(servicesLocked.valueAt(i6)));
                    }
                }
                while (i4 < this.mRestartingServices.size() && arrayList.size() < i) {
                    com.android.server.am.ServiceRecord serviceRecord = this.mRestartingServices.get(i4);
                    android.app.ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked = makeRunningServiceInfoLocked(serviceRecord);
                    makeRunningServiceInfoLocked.restarting = serviceRecord.nextRestartTime;
                    arrayList.add(makeRunningServiceInfoLocked);
                    i4++;
                }
            } else {
                int userId = android.os.UserHandle.getUserId(i3);
                android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> servicesLocked2 = getServicesLocked(userId);
                for (int i7 = 0; i7 < servicesLocked2.size() && arrayList.size() < i; i7++) {
                    com.android.server.am.ServiceRecord valueAt = servicesLocked2.valueAt(i7);
                    if (z || (valueAt.app != null && valueAt.app.uid == i3)) {
                        arrayList.add(makeRunningServiceInfoLocked(valueAt));
                    }
                }
                while (i4 < this.mRestartingServices.size() && arrayList.size() < i) {
                    com.android.server.am.ServiceRecord serviceRecord2 = this.mRestartingServices.get(i4);
                    if (serviceRecord2.userId == userId && (z || (serviceRecord2.app != null && serviceRecord2.app.uid == i3))) {
                        android.app.ActivityManager.RunningServiceInfo makeRunningServiceInfoLocked2 = makeRunningServiceInfoLocked(serviceRecord2);
                        makeRunningServiceInfoLocked2.restarting = serviceRecord2.nextRestartTime;
                        arrayList.add(makeRunningServiceInfoLocked2);
                    }
                    i4++;
                }
            }
            return arrayList;
        } finally {
            this.mAm.mInjector.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.app.PendingIntent getRunningServiceControlPanelLocked(android.content.ComponentName componentName) {
        com.android.server.am.ServiceRecord serviceByNameLocked = getServiceByNameLocked(componentName, android.os.UserHandle.getUserId(this.mAm.mInjector.getCallingUid()));
        if (serviceByNameLocked != null) {
            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = serviceByNameLocked.getConnections();
            for (int size = connections.size() - 1; size >= 0; size--) {
                java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
                for (int i = 0; i < valueAt.size(); i++) {
                    if (valueAt.get(i).clientIntent != null) {
                        return valueAt.get(i).clientIntent;
                    }
                }
            }
            return null;
        }
        return null;
    }

    void serviceTimeout(com.android.server.am.ProcessRecord processRecord) {
        com.android.internal.os.TimeoutRecord timeoutRecord;
        com.android.server.am.ServiceRecord serviceRecord;
        try {
            android.os.Trace.traceBegin(64L, "serviceTimeout()");
            com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    if (processRecord.isDebugging()) {
                        this.mActiveServiceAnrTimer.discard(processRecord);
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        android.os.Trace.traceEnd(64L);
                        return;
                    }
                    com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
                    if (processServiceRecord.numberOfExecutingServices() != 0 && processRecord.getThread() != null && !processRecord.isKilled()) {
                        this.mActiveServiceAnrTimer.accept(processRecord);
                        long uptimeMillis = android.os.SystemClock.uptimeMillis();
                        long j = uptimeMillis - (processServiceRecord.shouldExecServicesFg() ? this.mAm.mConstants.SERVICE_TIMEOUT : this.mAm.mConstants.SERVICE_BACKGROUND_TIMEOUT);
                        int numberOfExecutingServices = processServiceRecord.numberOfExecutingServices() - 1;
                        long j2 = 0;
                        while (true) {
                            timeoutRecord = null;
                            if (numberOfExecutingServices < 0) {
                                serviceRecord = null;
                                break;
                            }
                            serviceRecord = processServiceRecord.getExecutingServiceAt(numberOfExecutingServices);
                            if (serviceRecord.executingStart < j) {
                                break;
                            }
                            if (serviceRecord.executingStart > j2) {
                                j2 = serviceRecord.executingStart;
                            }
                            numberOfExecutingServices--;
                        }
                        if (serviceRecord == null || !this.mAm.mProcessList.isInLruListLOSP(processRecord)) {
                            this.mActiveServiceAnrTimer.start(processRecord, processServiceRecord.shouldExecServicesFg() ? j2 + this.mAm.mConstants.SERVICE_TIMEOUT : (j2 + this.mAm.mConstants.SERVICE_BACKGROUND_TIMEOUT) - android.os.SystemClock.uptimeMillis());
                        } else {
                            android.util.Slog.w("ActivityManager", "Timeout executing service: " + serviceRecord);
                            java.io.StringWriter stringWriter = new java.io.StringWriter();
                            com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(stringWriter, false, 1024);
                            fastPrintWriter.println(serviceRecord);
                            serviceRecord.dump((java.io.PrintWriter) fastPrintWriter, "    ");
                            fastPrintWriter.close();
                            this.mLastAnrDump = stringWriter.toString();
                            this.mAm.mHandler.removeCallbacks(this.mLastAnrDumpClearer);
                            this.mAm.mHandler.postDelayed(this.mLastAnrDumpClearer, com.android.server.usage.AppStandbyController.ConstantsObserver.DEFAULT_SYSTEM_UPDATE_TIMEOUT);
                            timeoutRecord = com.android.internal.os.TimeoutRecord.forServiceExec(serviceRecord.shortInstanceName, uptimeMillis - serviceRecord.executingStart);
                        }
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        if (timeoutRecord != null) {
                            this.mAm.mAnrHelper.appNotResponding(processRecord, timeoutRecord);
                        }
                        return;
                    }
                    this.mActiveServiceAnrTimer.discard(processRecord);
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    void serviceForegroundTimeout(com.android.server.am.ServiceRecord serviceRecord) {
        try {
            android.os.Trace.traceBegin(64L, "serviceForegroundTimeout()");
            com.android.internal.os.TimeoutRecord forServiceStartWithEndTime = com.android.internal.os.TimeoutRecord.forServiceStartWithEndTime("Context.startForegroundService() did not then call Service.startForeground(): " + serviceRecord, android.os.SystemClock.uptimeMillis());
            forServiceStartWithEndTime.mLatencyTracker.waitingOnAMSLockStarted();
            com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    forServiceStartWithEndTime.mLatencyTracker.waitingOnAMSLockEnded();
                    if (!serviceRecord.fgRequired || !serviceRecord.fgWaiting || serviceRecord.destroying) {
                        this.mServiceFGAnrTimer.discard(serviceRecord);
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    this.mServiceFGAnrTimer.accept(serviceRecord);
                    com.android.server.am.ProcessRecord processRecord = serviceRecord.app;
                    if (processRecord != null && processRecord.isDebugging()) {
                        this.mServiceFGAnrTimer.discard(serviceRecord);
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    serviceRecord.fgWaiting = false;
                    stopServiceLocked(serviceRecord, false);
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    if (processRecord != null) {
                        android.os.Message obtainMessage = this.mAm.mHandler.obtainMessage(67);
                        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                        obtain.arg1 = processRecord;
                        obtain.arg2 = forServiceStartWithEndTime;
                        obtainMessage.obj = obtain;
                        this.mAm.mHandler.sendMessageDelayed(obtainMessage, this.mAm.mConstants.mServiceStartForegroundAnrDelayMs);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    void serviceForegroundTimeoutANR(com.android.server.am.ProcessRecord processRecord, com.android.internal.os.TimeoutRecord timeoutRecord) {
        this.mAm.mAnrHelper.appNotResponding(processRecord, timeoutRecord);
    }

    public void updateServiceApplicationInfoLocked(android.content.pm.ApplicationInfo applicationInfo) {
        com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(android.os.UserHandle.getUserId(applicationInfo.uid));
        if (serviceMap != null) {
            android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> arrayMap = serviceMap.mServicesByInstanceName;
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                com.android.server.am.ServiceRecord valueAt = arrayMap.valueAt(size);
                if (applicationInfo.packageName.equals(valueAt.appInfo.packageName)) {
                    valueAt.appInfo = applicationInfo;
                    valueAt.serviceInfo.applicationInfo = applicationInfo;
                }
            }
        }
    }

    void serviceForegroundCrash(com.android.server.am.ProcessRecord processRecord, java.lang.String str, android.content.ComponentName componentName) {
        this.mAm.crashApplicationWithTypeWithExtras(processRecord.uid, processRecord.getPid(), processRecord.info.packageName, processRecord.userId, "Context.startForegroundService() did not then call Service.startForeground(): " + str, false, 1, android.app.RemoteServiceException.ForegroundServiceDidNotStartInTimeException.createExtrasForService(componentName));
    }

    private static class ProcessAnrTimer extends com.android.server.utils.AnrTimer<com.android.server.am.ProcessRecord> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ProcessAnrTimer(com.android.server.am.ActivityManagerService activityManagerService, int i, java.lang.String str) {
            super(activityManagerService.mHandler, i, str);
            java.util.Objects.requireNonNull(activityManagerService);
        }

        void start(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, long j) {
            start(processRecord, processRecord.getPid(), processRecord.uid, j);
        }
    }

    private static class ServiceAnrTimer extends com.android.server.utils.AnrTimer<com.android.server.am.ServiceRecord> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ServiceAnrTimer(com.android.server.am.ActivityManagerService activityManagerService, int i, java.lang.String str) {
            super(activityManagerService.mHandler, i, str);
            java.util.Objects.requireNonNull(activityManagerService);
        }

        void start(@android.annotation.NonNull com.android.server.am.ServiceRecord serviceRecord, long j) {
            start(serviceRecord, serviceRecord.app != null ? serviceRecord.app.getPid() : 0, serviceRecord.appInfo.uid, j);
        }
    }

    void scheduleServiceTimeoutLocked(com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.mServices.numberOfExecutingServices() == 0 || processRecord.getThread() == null) {
            return;
        }
        this.mActiveServiceAnrTimer.start(processRecord, processRecord.mServices.shouldExecServicesFg() ? this.mAm.mConstants.SERVICE_TIMEOUT : this.mAm.mConstants.SERVICE_BACKGROUND_TIMEOUT);
        processRecord.mServices.noteScheduleServiceTimeoutPending(false);
    }

    void scheduleServiceForegroundTransitionTimeoutLocked(com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord.app.mServices.numberOfExecutingServices() == 0 || serviceRecord.app.getThread() == null) {
            return;
        }
        serviceRecord.fgWaiting = true;
        this.mServiceFGAnrTimer.start(serviceRecord, this.mAm.mConstants.mServiceStartForegroundTimeoutMs);
    }

    final class ServiceDumper {
        private final java.lang.String[] args;
        private final boolean dumpAll;
        private final java.lang.String dumpPackage;
        private final java.io.FileDescriptor fd;
        private final java.io.PrintWriter pw;
        private final java.util.ArrayList<com.android.server.am.ServiceRecord> services = new java.util.ArrayList<>();
        private final long nowReal = android.os.SystemClock.elapsedRealtime();
        private boolean needSep = false;
        private boolean printedAnything = false;
        private boolean printed = false;
        private final com.android.server.am.ActivityManagerService.ItemMatcher matcher = new com.android.server.am.ActivityManagerService.ItemMatcher();

        ServiceDumper(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, boolean z, java.lang.String str) {
            this.fd = fileDescriptor;
            this.pw = printWriter;
            this.args = strArr;
            this.dumpAll = z;
            this.dumpPackage = str;
            this.matcher.build(strArr, i);
            for (int i2 : com.android.server.am.ActiveServices.this.mAm.mUserController.getUsers()) {
                com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = com.android.server.am.ActiveServices.this.getServiceMapLocked(i2);
                if (serviceMapLocked.mServicesByInstanceName.size() > 0) {
                    for (int i3 = 0; i3 < serviceMapLocked.mServicesByInstanceName.size(); i3++) {
                        com.android.server.am.ServiceRecord valueAt = serviceMapLocked.mServicesByInstanceName.valueAt(i3);
                        if (this.matcher.match(valueAt, valueAt.name) && (str == null || str.equals(valueAt.appInfo.packageName))) {
                            this.services.add(valueAt);
                        }
                    }
                }
            }
        }

        private void dumpHeaderLocked() {
            this.pw.println("ACTIVITY MANAGER SERVICES (dumpsys activity services)");
            if (com.android.server.am.ActiveServices.this.mLastAnrDump != null) {
                this.pw.println("  Last ANR service:");
                this.pw.print(com.android.server.am.ActiveServices.this.mLastAnrDump);
                this.pw.println();
            }
        }

        void dumpLocked() {
            dumpHeaderLocked();
            try {
                for (int i : com.android.server.am.ActiveServices.this.mAm.mUserController.getUsers()) {
                    int i2 = 0;
                    while (i2 < this.services.size() && this.services.get(i2).userId != i) {
                        i2++;
                    }
                    this.printed = false;
                    if (i2 < this.services.size()) {
                        this.needSep = false;
                        while (i2 < this.services.size()) {
                            com.android.server.am.ServiceRecord serviceRecord = this.services.get(i2);
                            i2++;
                            if (serviceRecord.userId != i) {
                                break;
                            } else {
                                dumpServiceLocalLocked(serviceRecord);
                            }
                        }
                        this.needSep |= this.printed;
                    }
                    dumpUserRemainsLocked(i);
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.w("ActivityManager", "Exception in dumpServicesLocked", e);
            }
            dumpRemainsLocked();
        }

        void dumpWithClient() {
            com.android.server.am.ActivityManagerService activityManagerService = com.android.server.am.ActiveServices.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    dumpHeaderLocked();
                } finally {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            try {
                for (int i : com.android.server.am.ActiveServices.this.mAm.mUserController.getUsers()) {
                    int i2 = 0;
                    while (i2 < this.services.size() && this.services.get(i2).userId != i) {
                        i2++;
                    }
                    this.printed = false;
                    if (i2 < this.services.size()) {
                        this.needSep = false;
                        while (i2 < this.services.size()) {
                            com.android.server.am.ServiceRecord serviceRecord = this.services.get(i2);
                            i2++;
                            if (serviceRecord.userId != i) {
                                break;
                            }
                            com.android.server.am.ActivityManagerService activityManagerService2 = com.android.server.am.ActiveServices.this.mAm;
                            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService2) {
                                try {
                                    dumpServiceLocalLocked(serviceRecord);
                                } finally {
                                }
                            }
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            dumpServiceClient(serviceRecord);
                        }
                        this.needSep |= this.printed;
                    }
                    com.android.server.am.ActivityManagerService activityManagerService3 = com.android.server.am.ActiveServices.this.mAm;
                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService3) {
                        try {
                            dumpUserRemainsLocked(i);
                        } finally {
                        }
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.w("ActivityManager", "Exception in dumpServicesLocked", e);
            }
            com.android.server.am.ActivityManagerService activityManagerService4 = com.android.server.am.ActiveServices.this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService4) {
                try {
                    dumpRemainsLocked();
                } finally {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }

        private void dumpUserHeaderLocked(int i) {
            if (!this.printed) {
                if (this.printedAnything) {
                    this.pw.println();
                }
                this.pw.println("  User " + i + " active services:");
                this.printed = true;
            }
            this.printedAnything = true;
            if (this.needSep) {
                this.pw.println();
            }
        }

        private void dumpServiceLocalLocked(com.android.server.am.ServiceRecord serviceRecord) {
            dumpUserHeaderLocked(serviceRecord.userId);
            this.pw.print("  * ");
            this.pw.println(serviceRecord);
            if (this.dumpAll) {
                serviceRecord.dump(this.pw, "    ");
                this.needSep = true;
                return;
            }
            this.pw.print("    app=");
            this.pw.println(serviceRecord.app);
            this.pw.print("    created=");
            android.util.TimeUtils.formatDuration(serviceRecord.createRealTime, this.nowReal, this.pw);
            this.pw.print(" started=");
            this.pw.print(serviceRecord.startRequested);
            this.pw.print(" connections=");
            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = serviceRecord.getConnections();
            this.pw.println(connections.size());
            if (connections.size() > 0) {
                this.pw.println("    Connections:");
                for (int i = 0; i < connections.size(); i++) {
                    java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(i);
                    for (int i2 = 0; i2 < valueAt.size(); i2++) {
                        com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i2);
                        this.pw.print("      ");
                        this.pw.print(connectionRecord.binding.intent.intent.getIntent().toShortString(false, false, false, false));
                        this.pw.print(" -> ");
                        com.android.server.am.ProcessRecord processRecord = connectionRecord.binding.client;
                        this.pw.println(processRecord != null ? processRecord.toShortString() : "null");
                    }
                }
            }
        }

        private void dumpServiceClient(com.android.server.am.ServiceRecord serviceRecord) {
            android.app.IApplicationThread thread;
            com.android.internal.os.TransferPipe transferPipe;
            com.android.server.am.ProcessRecord processRecord = serviceRecord.app;
            if (processRecord == null || (thread = processRecord.getThread()) == null) {
                return;
            }
            this.pw.println("    Client:");
            this.pw.flush();
            try {
                transferPipe = new com.android.internal.os.TransferPipe();
            } catch (android.os.RemoteException e) {
                this.pw.println("      Got a RemoteException while dumping the service");
            } catch (java.io.IOException e2) {
                this.pw.println("      Failure while dumping the service: " + e2);
            }
            try {
                thread.dumpService(transferPipe.getWriteFd(), serviceRecord, this.args);
                transferPipe.setBufferPrefix("      ");
                transferPipe.go(this.fd, 2000L);
                this.needSep = true;
            } finally {
                transferPipe.kill();
            }
        }

        private void dumpUserRemainsLocked(int i) {
            com.android.server.am.ActiveServices.ServiceMap serviceMapLocked = com.android.server.am.ActiveServices.this.getServiceMapLocked(i);
            this.printed = false;
            int size = serviceMapLocked.mDelayedStartList.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.am.ServiceRecord serviceRecord = serviceMapLocked.mDelayedStartList.get(i2);
                if (this.matcher.match(serviceRecord, serviceRecord.name) && (this.dumpPackage == null || this.dumpPackage.equals(serviceRecord.appInfo.packageName))) {
                    if (!this.printed) {
                        if (this.printedAnything) {
                            this.pw.println();
                        }
                        this.pw.println("  User " + i + " delayed start services:");
                        this.printed = true;
                    }
                    this.printedAnything = true;
                    this.pw.print("  * Delayed start ");
                    this.pw.println(serviceRecord);
                }
            }
            this.printed = false;
            int size2 = serviceMapLocked.mStartingBackground.size();
            for (int i3 = 0; i3 < size2; i3++) {
                com.android.server.am.ServiceRecord serviceRecord2 = serviceMapLocked.mStartingBackground.get(i3);
                if (this.matcher.match(serviceRecord2, serviceRecord2.name) && (this.dumpPackage == null || this.dumpPackage.equals(serviceRecord2.appInfo.packageName))) {
                    if (!this.printed) {
                        if (this.printedAnything) {
                            this.pw.println();
                        }
                        this.pw.println("  User " + i + " starting in background:");
                        this.printed = true;
                    }
                    this.printedAnything = true;
                    this.pw.print("  * Starting bg ");
                    this.pw.println(serviceRecord2);
                }
            }
        }

        private void dumpRemainsLocked() {
            boolean z = false;
            if (com.android.server.am.ActiveServices.this.mPendingServices.size() > 0) {
                this.printed = false;
                for (int i = 0; i < com.android.server.am.ActiveServices.this.mPendingServices.size(); i++) {
                    com.android.server.am.ServiceRecord serviceRecord = com.android.server.am.ActiveServices.this.mPendingServices.get(i);
                    if (this.matcher.match(serviceRecord, serviceRecord.name) && (this.dumpPackage == null || this.dumpPackage.equals(serviceRecord.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.pw.println();
                            }
                            this.needSep = true;
                            this.pw.println("  Pending services:");
                            this.printed = true;
                        }
                        this.pw.print("  * Pending ");
                        this.pw.println(serviceRecord);
                        serviceRecord.dump(this.pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (com.android.server.am.ActiveServices.this.mRestartingServices.size() > 0) {
                this.printed = false;
                for (int i2 = 0; i2 < com.android.server.am.ActiveServices.this.mRestartingServices.size(); i2++) {
                    com.android.server.am.ServiceRecord serviceRecord2 = com.android.server.am.ActiveServices.this.mRestartingServices.get(i2);
                    if (this.matcher.match(serviceRecord2, serviceRecord2.name) && (this.dumpPackage == null || this.dumpPackage.equals(serviceRecord2.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.pw.println();
                            }
                            this.needSep = true;
                            this.pw.println("  Restarting services:");
                            this.printed = true;
                        }
                        this.pw.print("  * Restarting ");
                        this.pw.println(serviceRecord2);
                        serviceRecord2.dump(this.pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (com.android.server.am.ActiveServices.this.mDestroyingServices.size() > 0) {
                this.printed = false;
                for (int i3 = 0; i3 < com.android.server.am.ActiveServices.this.mDestroyingServices.size(); i3++) {
                    com.android.server.am.ServiceRecord serviceRecord3 = com.android.server.am.ActiveServices.this.mDestroyingServices.get(i3);
                    if (this.matcher.match(serviceRecord3, serviceRecord3.name) && (this.dumpPackage == null || this.dumpPackage.equals(serviceRecord3.appInfo.packageName))) {
                        this.printedAnything = true;
                        if (!this.printed) {
                            if (this.needSep) {
                                this.pw.println();
                            }
                            this.needSep = true;
                            this.pw.println("  Destroying services:");
                            this.printed = true;
                        }
                        this.pw.print("  * Destroy ");
                        this.pw.println(serviceRecord3);
                        serviceRecord3.dump(this.pw, "    ");
                    }
                }
                this.needSep = true;
            }
            if (this.dumpAll) {
                this.printed = false;
                for (int i4 = 0; i4 < com.android.server.am.ActiveServices.this.mServiceConnections.size(); i4++) {
                    java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = com.android.server.am.ActiveServices.this.mServiceConnections.valueAt(i4);
                    for (int i5 = 0; i5 < valueAt.size(); i5++) {
                        com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i5);
                        if (this.matcher.match(connectionRecord.binding.service, connectionRecord.binding.service.name) && (this.dumpPackage == null || (connectionRecord.binding.client != null && this.dumpPackage.equals(connectionRecord.binding.client.info.packageName)))) {
                            this.printedAnything = true;
                            if (!this.printed) {
                                if (this.needSep) {
                                    this.pw.println();
                                }
                                this.needSep = true;
                                this.pw.println("  Connection bindings to services:");
                                this.printed = true;
                            }
                            this.pw.print("  * ");
                            this.pw.println(connectionRecord);
                            connectionRecord.dump(this.pw, "    ");
                        }
                    }
                }
            }
            if (this.matcher.all) {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                int[] users = com.android.server.am.ActiveServices.this.mAm.mUserController.getUsers();
                int length = users.length;
                int i6 = 0;
                while (i6 < length) {
                    int i7 = users[i6];
                    com.android.server.am.ActiveServices.ServiceMap serviceMap = com.android.server.am.ActiveServices.this.mServiceMap.get(i7);
                    if (serviceMap != null) {
                        boolean z2 = z;
                        for (int size = serviceMap.mActiveForegroundApps.size() - 1; size >= 0; size--) {
                            com.android.server.am.ActiveServices.ActiveForegroundApp valueAt2 = serviceMap.mActiveForegroundApps.valueAt(size);
                            if (this.dumpPackage == null || this.dumpPackage.equals(valueAt2.mPackageName)) {
                                if (!z2) {
                                    this.printedAnything = true;
                                    if (this.needSep) {
                                        this.pw.println();
                                    }
                                    this.needSep = true;
                                    this.pw.print("Active foreground apps - user ");
                                    this.pw.print(i7);
                                    this.pw.println(":");
                                    z2 = true;
                                }
                                this.pw.print("  #");
                                this.pw.print(size);
                                this.pw.print(": ");
                                this.pw.println(valueAt2.mPackageName);
                                if (valueAt2.mLabel != null) {
                                    this.pw.print("    mLabel=");
                                    this.pw.println(valueAt2.mLabel);
                                }
                                this.pw.print("    mNumActive=");
                                this.pw.print(valueAt2.mNumActive);
                                this.pw.print(" mAppOnTop=");
                                this.pw.print(valueAt2.mAppOnTop);
                                this.pw.print(" mShownWhileTop=");
                                this.pw.print(valueAt2.mShownWhileTop);
                                this.pw.print(" mShownWhileScreenOn=");
                                this.pw.println(valueAt2.mShownWhileScreenOn);
                                this.pw.print("    mStartTime=");
                                boolean z3 = z2;
                                android.util.TimeUtils.formatDuration(valueAt2.mStartTime - elapsedRealtime, this.pw);
                                this.pw.print(" mStartVisibleTime=");
                                android.util.TimeUtils.formatDuration(valueAt2.mStartVisibleTime - elapsedRealtime, this.pw);
                                this.pw.println();
                                if (valueAt2.mEndTime != 0) {
                                    this.pw.print("    mEndTime=");
                                    android.util.TimeUtils.formatDuration(valueAt2.mEndTime - elapsedRealtime, this.pw);
                                    this.pw.println();
                                }
                                z2 = z3;
                            }
                        }
                        if (serviceMap.hasMessagesOrCallbacks()) {
                            if (this.needSep) {
                                this.pw.println();
                            }
                            this.printedAnything = true;
                            this.needSep = true;
                            this.pw.print("  Handler - user ");
                            this.pw.print(i7);
                            this.pw.println(":");
                            serviceMap.dumpMine(new android.util.PrintWriterPrinter(this.pw), "    ");
                        }
                    }
                    i6++;
                    z = false;
                }
            }
            if (!this.printedAnything) {
                this.pw.println("  (nothing)");
            }
        }
    }

    com.android.server.am.ActiveServices.ServiceDumper newServiceDumperLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, boolean z, java.lang.String str) {
        return new com.android.server.am.ActiveServices.ServiceDumper(fileDescriptor, printWriter, strArr, i, z, str);
    }

    protected void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                long start = protoOutputStream.start(j);
                for (int i : this.mAm.mUserController.getUsers()) {
                    com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i);
                    if (serviceMap != null) {
                        long start2 = protoOutputStream.start(2246267895809L);
                        protoOutputStream.write(1120986464257L, i);
                        android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> arrayMap = serviceMap.mServicesByInstanceName;
                        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                            arrayMap.valueAt(i2).dumpDebug(protoOutputStream, 2246267895810L);
                        }
                        protoOutputStream.end(start2);
                    }
                }
                protoOutputStream.end(start);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    protected boolean dumpService(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String str, int[] iArr, java.lang.String[] strArr, int i, boolean z) {
        int[] users;
        try {
            boolean z2 = false;
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(false);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.function.Predicate filterRecord = com.android.internal.util.DumpUtils.filterRecord(str);
            com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                if (iArr == null) {
                    try {
                        users = this.mAm.mUserController.getUsers();
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                } else {
                    users = iArr;
                }
                for (int i2 : users) {
                    com.android.server.am.ActiveServices.ServiceMap serviceMap = this.mServiceMap.get(i2);
                    if (serviceMap != null) {
                        android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> arrayMap = serviceMap.mServicesByInstanceName;
                        for (int i3 = 0; i3 < arrayMap.size(); i3++) {
                            com.android.server.am.ServiceRecord valueAt = arrayMap.valueAt(i3);
                            if (filterRecord.test(valueAt)) {
                                arrayList.add(valueAt);
                            }
                        }
                    }
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            if (arrayList.size() <= 0) {
                this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
                return false;
            }
            arrayList.sort(java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((com.android.server.am.ServiceRecord) obj).getComponentName();
                }
            }));
            int i4 = 0;
            while (i4 < arrayList.size()) {
                if (z2) {
                    printWriter.println();
                }
                dumpService("", fileDescriptor, printWriter, (com.android.server.am.ServiceRecord) arrayList.get(i4), strArr, z);
                i4++;
                z2 = true;
            }
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
            return true;
        } catch (java.lang.Throwable th2) {
            this.mAm.mOomAdjuster.mCachedAppOptimizer.enableFreezer(true);
            throw th2;
        }
    }

    private void dumpService(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, com.android.server.am.ServiceRecord serviceRecord, java.lang.String[] strArr, boolean z) {
        android.app.IApplicationThread thread;
        java.lang.String str2 = str + "  ";
        com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                printWriter.print(str);
                printWriter.print("SERVICE ");
                printWriter.print(serviceRecord.shortInstanceName);
                printWriter.print(" ");
                printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(serviceRecord)));
                printWriter.print(" pid=");
                if (serviceRecord.app != null) {
                    printWriter.print(serviceRecord.app.getPid());
                    printWriter.print(" user=");
                    printWriter.println(serviceRecord.userId);
                } else {
                    printWriter.println("(not running)");
                }
                if (z) {
                    serviceRecord.dump(printWriter, str2);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        if (serviceRecord.app != null && (thread = serviceRecord.app.getThread()) != null) {
            printWriter.print(str);
            printWriter.println("  Client:");
            printWriter.flush();
            try {
                com.android.internal.os.TransferPipe transferPipe = new com.android.internal.os.TransferPipe();
                try {
                    thread.dumpService(transferPipe.getWriteFd(), serviceRecord, strArr);
                    transferPipe.setBufferPrefix(str + "    ");
                    transferPipe.go(fileDescriptor);
                } finally {
                    transferPipe.kill();
                }
            } catch (android.os.RemoteException e) {
                printWriter.println(str + "    Got a RemoteException while dumping the service");
            } catch (java.io.IOException e2) {
                printWriter.println(str + "    Failure while dumping the service: " + e2);
            }
        }
    }

    private void setFgsRestrictionLocked(java.lang.String str, int i, int i2, android.content.Intent intent, com.android.server.am.ServiceRecord serviceRecord, int i3, android.app.BackgroundStartPrivileges backgroundStartPrivileges, boolean z) {
        setFgsRestrictionLocked(str, i, i2, intent, serviceRecord, i3, backgroundStartPrivileges, z, false);
    }

    private void setFgsRestrictionLocked(java.lang.String str, int i, int i2, android.content.Intent intent, com.android.server.am.ServiceRecord serviceRecord, int i3, android.app.BackgroundStartPrivileges backgroundStartPrivileges, boolean z, boolean z2) {
        int i4;
        int i5;
        int i6;
        if (z) {
            i4 = serviceRecord.mAllowWiu_inBindService;
            i5 = serviceRecord.mAllowStart_inBindService;
        } else {
            i4 = serviceRecord.mAllowWiu_noBinding;
            i5 = serviceRecord.mAllowStart_noBinding;
        }
        if (i4 == -1 || i5 == -1) {
            int shouldAllowFgsWhileInUsePermissionLocked = shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, serviceRecord.app, backgroundStartPrivileges);
            if (i4 != -1) {
                i6 = i4;
            } else {
                i6 = shouldAllowFgsWhileInUsePermissionLocked;
            }
            if (i5 != -1) {
                i4 = i6;
            } else {
                i5 = shouldAllowFgsStartForegroundWithBindingCheckLocked(shouldAllowFgsWhileInUsePermissionLocked, str, i, i2, intent, serviceRecord, backgroundStartPrivileges, z);
                i4 = i6;
            }
        }
        if (z) {
            serviceRecord.mAllowWiu_inBindService = i4;
            serviceRecord.mAllowStart_inBindService = i5;
            return;
        }
        if (z2) {
            if (serviceRecord.mAllowWiu_byBindings == -1) {
                serviceRecord.mAllowWiu_byBindings = i4;
            }
            if (serviceRecord.mAllowStart_byBindings == -1) {
                serviceRecord.mAllowStart_byBindings = i5;
            }
        } else {
            serviceRecord.mAllowWiu_noBinding = i4;
            serviceRecord.mAllowStart_noBinding = i5;
        }
        if (serviceRecord.mAllowWiu_byBindings == -1) {
            serviceRecord.mAllowWiu_byBindings = shouldAllowFgsWhileInUsePermissionByBindingsLocked(i2);
        }
        if (serviceRecord.mAllowStart_byBindings == -1) {
            serviceRecord.mAllowStart_byBindings = serviceRecord.mAllowWiu_byBindings;
        }
    }

    void resetFgsRestrictionLocked(com.android.server.am.ServiceRecord serviceRecord) {
        serviceRecord.clearFgsAllowWiu();
        serviceRecord.clearFgsAllowStart();
        serviceRecord.mInfoAllowStartForeground = null;
        serviceRecord.mInfoTempFgsAllowListReason = null;
        serviceRecord.mLoggedInfoAllowStartForeground = false;
        serviceRecord.updateAllowUiJobScheduling(serviceRecord.isFgsAllowedWiu_forStart());
    }

    boolean canStartForegroundServiceLocked(int i, int i2, java.lang.String str) {
        if (!this.mAm.mConstants.mFlagBackgroundFgsStartRestrictionEnabled) {
            return true;
        }
        int shouldAllowFgsStartForegroundNoBindingCheckLocked = shouldAllowFgsStartForegroundNoBindingCheckLocked(shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, null, android.app.BackgroundStartPrivileges.NONE), i, i2, str, null, android.app.BackgroundStartPrivileges.NONE);
        if (shouldAllowFgsStartForegroundNoBindingCheckLocked == -1 && canBindingClientStartFgsLocked(i2) != null) {
            shouldAllowFgsStartForegroundNoBindingCheckLocked = 54;
        }
        return shouldAllowFgsStartForegroundNoBindingCheckLocked != -1;
    }

    private int shouldAllowFgsWhileInUsePermissionLocked(java.lang.String str, int i, final int i2, @android.annotation.Nullable com.android.server.am.ProcessRecord processRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        int i3;
        com.android.server.am.ActiveInstrumentation activeInstrumentation;
        java.lang.Integer num;
        boolean z;
        int uidStateLocked = this.mAm.getUidStateLocked(i2);
        if (uidStateLocked <= 2) {
            i3 = android.os.PowerExemptionManager.getReasonCodeFromProcState(uidStateLocked);
        } else {
            i3 = -1;
        }
        if (i3 == -1 && this.mAm.mAtmInternal.isUidForeground(i2)) {
            i3 = 50;
        }
        if (i3 == -1 && backgroundStartPrivileges.allowsBackgroundActivityStarts()) {
            i3 = 53;
        }
        if (i3 == -1) {
            switch (android.os.UserHandle.getAppId(i2)) {
                case 0:
                case 1000:
                case com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE /* 1027 */:
                case 2000:
                    z = true;
                    break;
                default:
                    z = false;
                    break;
            }
            if (z) {
                i3 = 51;
            }
        }
        if (i3 == -1 && (num = (java.lang.Integer) this.mAm.mProcessList.searchEachLruProcessesLOSP(false, new java.util.function.Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$shouldAllowFgsWhileInUsePermissionLocked$4;
                lambda$shouldAllowFgsWhileInUsePermissionLocked$4 = com.android.server.am.ActiveServices.lambda$shouldAllowFgsWhileInUsePermissionLocked$4(i2, (com.android.server.am.ProcessRecord) obj);
                return lambda$shouldAllowFgsWhileInUsePermissionLocked$4;
            }
        })) != null) {
            i3 = num.intValue();
        }
        if (i3 == -1 && this.mAm.mInternal.isTempAllowlistedForFgsWhileInUse(i2)) {
            return 70;
        }
        if (i3 == -1 && processRecord != null && (activeInstrumentation = processRecord.getActiveInstrumentation()) != null && activeInstrumentation.mHasBackgroundActivityStartsPermission) {
            i3 = 60;
        }
        if (i3 == -1 && this.mAm.checkPermission("android.permission.START_ACTIVITIES_FROM_BACKGROUND", i, i2) == 0) {
            i3 = 58;
        }
        if (i3 == -1) {
            if (verifyPackage(str, i2)) {
                if (this.mAllowListWhileInUsePermissionInFgs.contains(str)) {
                    i3 = 65;
                }
            } else {
                android.util.EventLog.writeEvent(1397638484, "215003903", java.lang.Integer.valueOf(i2), "callingPackage:" + str + " does not belong to callingUid:" + i2);
            }
        }
        if (i3 == -1 && this.mAm.mInternal.isDeviceOwner(i2)) {
            return 55;
        }
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$shouldAllowFgsWhileInUsePermissionLocked$4(int i, com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.uid == i && processRecord.getWindowProcessController().areBackgroundFgsStartsAllowed()) {
            return 52;
        }
        return null;
    }

    private int shouldAllowFgsWhileInUsePermissionByBindingsLocked(final int i) {
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        java.lang.Integer num = (java.lang.Integer) this.mAm.mProcessList.searchEachLruProcessesLOSP(false, new java.util.function.Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$shouldAllowFgsWhileInUsePermissionByBindingsLocked$5;
                lambda$shouldAllowFgsWhileInUsePermissionByBindingsLocked$5 = com.android.server.am.ActiveServices.this.lambda$shouldAllowFgsWhileInUsePermissionByBindingsLocked$5(i, arraySet, (com.android.server.am.ProcessRecord) obj);
                return lambda$shouldAllowFgsWhileInUsePermissionByBindingsLocked$5;
            }
        });
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$shouldAllowFgsWhileInUsePermissionByBindingsLocked$5(int i, android.util.ArraySet arraySet, com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.uid != i) {
            return null;
        }
        com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
        int size = processServiceRecord.mServices.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = processServiceRecord.mServices.valueAt(i2).getConnections();
            int size2 = connections.size();
            for (int i3 = 0; i3 < size2; i3++) {
                java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(i3);
                for (int i4 = 0; i4 < valueAt.size(); i4++) {
                    com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i4);
                    int i5 = connectionRecord.binding.client.uid;
                    if (i5 != i && !arraySet.contains(java.lang.Integer.valueOf(i5))) {
                        int uidStateLocked = this.mAm.getUidStateLocked(i);
                        boolean z = uidStateLocked == 2;
                        boolean z2 = uidStateLocked < 2 && connectionRecord.hasFlag(1048576);
                        if (z || z2) {
                            return java.lang.Integer.valueOf(android.os.PowerExemptionManager.getReasonCodeFromProcState(uidStateLocked));
                        }
                        arraySet.add(java.lang.Integer.valueOf(i5));
                    }
                }
            }
        }
        return null;
    }

    private java.lang.String canBindingClientStartFgsLocked(final int i) {
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.Pair pair = (android.util.Pair) this.mAm.mProcessList.searchEachLruProcessesLOSP(false, new java.util.function.Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.util.Pair lambda$canBindingClientStartFgsLocked$6;
                lambda$canBindingClientStartFgsLocked$6 = com.android.server.am.ActiveServices.this.lambda$canBindingClientStartFgsLocked$6(i, arraySet, (com.android.server.am.ProcessRecord) obj);
                return lambda$canBindingClientStartFgsLocked$6;
            }
        });
        if (pair == null) {
            return null;
        }
        return (java.lang.String) pair.second;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.util.Pair lambda$canBindingClientStartFgsLocked$6(int i, android.util.ArraySet arraySet, com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.uid == i) {
            com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
            int size = processServiceRecord.mServices.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = processServiceRecord.mServices.valueAt(i2).getConnections();
                int size2 = connections.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(i3);
                    for (int i4 = 0; i4 < valueAt.size(); i4++) {
                        com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i4);
                        com.android.server.am.ProcessRecord processRecord2 = connectionRecord.binding.client;
                        if (!processRecord2.isPersistent()) {
                            int i5 = processRecord2.mPid;
                            int i6 = processRecord2.uid;
                            if (i6 != i && !arraySet.contains(java.lang.Integer.valueOf(i6))) {
                                java.lang.String str = connectionRecord.clientPackageName;
                                int shouldAllowFgsStartForegroundNoBindingCheckLocked = shouldAllowFgsStartForegroundNoBindingCheckLocked(shouldAllowFgsWhileInUsePermissionLocked(str, i5, i6, null, android.app.BackgroundStartPrivileges.NONE), i5, i6, str, null, android.app.BackgroundStartPrivileges.NONE);
                                if (shouldAllowFgsStartForegroundNoBindingCheckLocked != -1) {
                                    return new android.util.Pair(java.lang.Integer.valueOf(shouldAllowFgsStartForegroundNoBindingCheckLocked), str);
                                }
                                arraySet.add(java.lang.Integer.valueOf(i6));
                            }
                        }
                    }
                }
            }
            return null;
        }
        return null;
    }

    private int shouldAllowFgsStartForegroundWithBindingCheckLocked(int i, java.lang.String str, int i2, int i3, android.content.Intent intent, com.android.server.am.ServiceRecord serviceRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges, boolean z) {
        int i4;
        java.lang.String str2;
        com.android.server.am.ActivityManagerService.FgsTempAllowListItem isAllowlistedForFgsStartLOSP = this.mAm.isAllowlistedForFgsStartLOSP(i3);
        serviceRecord.mInfoTempFgsAllowListReason = isAllowlistedForFgsStartLOSP;
        int shouldAllowFgsStartForegroundNoBindingCheckLocked = shouldAllowFgsStartForegroundNoBindingCheckLocked(i, i2, i3, str, serviceRecord, backgroundStartPrivileges);
        java.lang.String str3 = null;
        int i5 = -1;
        if (shouldAllowFgsStartForegroundNoBindingCheckLocked != -1) {
            i4 = shouldAllowFgsStartForegroundNoBindingCheckLocked;
            str2 = null;
        } else {
            java.lang.String canBindingClientStartFgsLocked = canBindingClientStartFgsLocked(i3);
            if (canBindingClientStartFgsLocked == null) {
                str2 = canBindingClientStartFgsLocked;
                i4 = shouldAllowFgsStartForegroundNoBindingCheckLocked;
            } else {
                str2 = canBindingClientStartFgsLocked;
                i4 = 54;
            }
        }
        int uidStateLocked = this.mAm.getUidStateLocked(i3);
        try {
            i5 = this.mAm.mContext.getPackageManager().getTargetSdkVersion(str);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
        boolean z2 = (this.mAm.getUidProcessCapabilityLocked(i3) & 16) != 0;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[callingPackage: ");
        sb.append(str);
        sb.append("; callingUid: ");
        sb.append(i3);
        sb.append("; uidState: ");
        sb.append(com.android.server.am.ProcessList.makeProcStateString(uidStateLocked));
        sb.append("; uidBFSL: ");
        sb.append(z2 ? "[BFSL]" : "n/a");
        sb.append("; intent: ");
        sb.append(intent);
        sb.append("; code:");
        sb.append(android.os.PowerExemptionManager.reasonCodeToString(i4));
        sb.append("; tempAllowListReason:<");
        if (isAllowlistedForFgsStartLOSP != null) {
            str3 = isAllowlistedForFgsStartLOSP.mReason + ",reasonCode:" + android.os.PowerExemptionManager.reasonCodeToString(isAllowlistedForFgsStartLOSP.mReasonCode) + ",duration:" + isAllowlistedForFgsStartLOSP.mDuration + ",callingUid:" + isAllowlistedForFgsStartLOSP.mCallingUid;
        }
        sb.append(str3);
        sb.append(">; targetSdkVersion:");
        sb.append(serviceRecord.appInfo.targetSdkVersion);
        sb.append("; callerTargetSdkVersion:");
        sb.append(i5);
        sb.append("; startForegroundCount:");
        sb.append(serviceRecord.mStartForegroundCount);
        sb.append("; bindFromPackage:");
        sb.append(str2);
        sb.append(": isBindService:");
        sb.append(z);
        sb.append("]");
        java.lang.String sb2 = sb.toString();
        if (!sb2.equals(serviceRecord.mInfoAllowStartForeground)) {
            serviceRecord.mLoggedInfoAllowStartForeground = false;
            serviceRecord.mInfoAllowStartForeground = sb2;
        }
        return i4;
    }

    private int shouldAllowFgsStartForegroundNoBindingCheckLocked(int i, int i2, final int i3, java.lang.String str, @android.annotation.Nullable com.android.server.am.ServiceRecord serviceRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        java.lang.String stringForUser;
        android.content.ComponentName unflattenFromString;
        com.android.server.am.ActivityManagerService.FgsTempAllowListItem isAllowlistedForFgsStartLOSP;
        int uidStateLocked;
        if (i == -1 && (uidStateLocked = this.mAm.getUidStateLocked(i3)) <= 2) {
            i = android.os.PowerExemptionManager.getReasonCodeFromProcState(uidStateLocked);
        }
        if (i == -1) {
            final boolean z = (this.mAm.getUidProcessCapabilityLocked(i3) & 16) != 0;
            java.lang.Integer num = (java.lang.Integer) this.mAm.mProcessList.searchEachLruProcessesLOSP(false, new java.util.function.Function() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Integer lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$7;
                    lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$7 = com.android.server.am.ActiveServices.this.lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$7(i3, z, (com.android.server.am.ProcessRecord) obj);
                    return lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$7;
                }
            });
            if (num != null) {
                i = num.intValue();
            }
        }
        if (i == -1 && this.mAm.checkPermission("android.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND", i2, i3) == 0) {
            i = 59;
        }
        if (i == -1 && backgroundStartPrivileges.allowsBackgroundFgsStarts()) {
            i = 53;
        }
        if (i == -1 && this.mAm.mAtmInternal.hasSystemAlertWindowPermission(i3, i2, str)) {
            i = 62;
        }
        if (i == -1 && this.mAm.mInternal.isAssociatedCompanionApp(android.os.UserHandle.getUserId(i3), i3) && (isPermissionGranted("android.permission.REQUEST_COMPANION_START_FOREGROUND_SERVICES_FROM_BACKGROUND", i2, i3) || isPermissionGranted("android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND", i2, i3))) {
            i = 57;
        }
        if (i == -1 && (isAllowlistedForFgsStartLOSP = this.mAm.isAllowlistedForFgsStartLOSP(i3)) != null) {
            if (isAllowlistedForFgsStartLOSP == com.android.server.am.ActivityManagerService.FAKE_TEMP_ALLOW_LIST_ITEM) {
                i = 300;
            } else {
                i = isAllowlistedForFgsStartLOSP.mReasonCode;
            }
        }
        if (i == -1 && android.os.UserManager.isDeviceInDemoMode(this.mAm.mContext)) {
            i = 63;
        }
        if (i == -1 && this.mAm.mInternal.isProfileOwner(i3)) {
            i = 56;
        }
        if (i == -1) {
            android.app.AppOpsManager appOpsManager = this.mAm.getAppOpsManager();
            if (this.mAm.mConstants.mFlagSystemExemptPowerRestrictionsEnabled && appOpsManager.checkOpNoThrow(128, i3, str) == 0) {
                i = com.android.internal.util.FrameworkStatsLog.TIF_TUNE_CHANGED;
            }
        }
        if (i == -1) {
            android.app.AppOpsManager appOpsManager2 = this.mAm.getAppOpsManager();
            if (appOpsManager2.checkOpNoThrow(47, i3, str) == 0) {
                i = 68;
            } else if (appOpsManager2.checkOpNoThrow(94, i3, str) == 0) {
                i = 69;
            }
        }
        if (i == -1 && (stringForUser = android.provider.Settings.Secure.getStringForUser(this.mAm.mContext.getContentResolver(), "default_input_method", android.os.UserHandle.getUserId(i3))) != null && (unflattenFromString = android.content.ComponentName.unflattenFromString(stringForUser)) != null && unflattenFromString.getPackageName().equals(str)) {
            i = 71;
        }
        if (i == -1 && this.mAm.mConstants.mFgsAllowOptOut && serviceRecord != null && serviceRecord.appInfo.hasRequestForegroundServiceExemption()) {
            return 1000;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$shouldAllowFgsStartForegroundNoBindingCheckLocked$7(int i, boolean z, com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.uid == i) {
            int curProcState = processRecord.mState.getCurProcState();
            if (curProcState <= 3 || (z && curProcState <= 5)) {
                return java.lang.Integer.valueOf(android.os.PowerExemptionManager.getReasonCodeFromProcState(curProcState));
            }
            com.android.server.am.ActiveInstrumentation activeInstrumentation = processRecord.getActiveInstrumentation();
            if (activeInstrumentation != null && activeInstrumentation.mHasBackgroundForegroundServiceStartsPermission) {
                return 61;
            }
            long lastInvisibleTime = processRecord.mState.getLastInvisibleTime();
            if (lastInvisibleTime > 0 && lastInvisibleTime < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME && android.os.SystemClock.elapsedRealtime() - lastInvisibleTime < this.mAm.mConstants.mFgToBgFgsGraceDuration) {
                return 67;
            }
            return null;
        }
        return null;
    }

    private boolean isPermissionGranted(java.lang.String str, int i, int i2) {
        return this.mAm.checkPermission(str, i, i2) == 0;
    }

    private static boolean isFgsBgStart(int i) {
        return (i == 10 || i == 11 || i == 12 || i == 50) ? false : true;
    }

    private void showFgsBgRestrictedNotificationLocked(com.android.server.am.ServiceRecord serviceRecord) {
        if (!this.mAm.mConstants.mFgsStartRestrictionNotificationEnabled) {
            return;
        }
        android.content.Context context = this.mAm.mContext;
        java.lang.String str = "App restricted: " + serviceRecord.mRecentCallingPackage;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        ((android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class)).notifyAsUser(java.lang.Long.toString(currentTimeMillis), 61, new android.app.Notification.Builder(context, com.android.internal.notification.SystemNotificationChannels.ALERTS).setGroup("com.android.fgs-bg-restricted").setSmallIcon(android.R.drawable.stat_sys_upload_anim3).setWhen(0L).setColor(context.getColor(android.R.color.system_notification_accent_color)).setTicker("Foreground Service BG-Launch Restricted").setContentTitle("Foreground Service BG-Launch Restricted").setContentText(str).setStyle(new android.app.Notification.BigTextStyle().bigText(DATE_FORMATTER.format(java.lang.Long.valueOf(currentTimeMillis)) + " " + serviceRecord.mInfoAllowStartForeground)).build(), android.os.UserHandle.ALL);
    }

    private boolean isBgFgsRestrictionEnabled(com.android.server.am.ServiceRecord serviceRecord, int i) {
        if (!this.mAm.mConstants.mFlagFgsStartRestrictionEnabled || !android.app.compat.CompatChanges.isChangeEnabled(FGS_BG_START_RESTRICTION_CHANGE_ID, serviceRecord.appInfo.uid)) {
            return false;
        }
        if (!this.mAm.mConstants.mFgsStartRestrictionCheckCallerTargetSdk) {
            return true;
        }
        com.android.server.am.Flags.newFgsRestrictionLogic();
        return android.app.compat.CompatChanges.isChangeEnabled(FGS_BG_START_RESTRICTION_CHANGE_ID, serviceRecord.mRecentCallingUid);
    }

    private void logFgsBackgroundStart(com.android.server.am.ServiceRecord serviceRecord) {
        if (!serviceRecord.mLoggedInfoAllowStartForeground) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Background started FGS: ");
            sb.append(serviceRecord.isFgsAllowedStart() ? "Allowed " : "Disallowed ");
            sb.append(serviceRecord.mInfoAllowStartForeground);
            sb.append(serviceRecord.isShortFgs() ? " (Called on SHORT_SERVICE)" : "");
            java.lang.String sb2 = sb.toString();
            if (serviceRecord.isFgsAllowedStart()) {
                if (com.android.server.am.ActivityManagerUtils.shouldSamplePackageForAtom(serviceRecord.packageName, this.mAm.mConstants.mFgsStartAllowedLogSampleRate)) {
                    android.util.Slog.wtfQuiet("ActivityManager", sb2);
                }
                android.util.Slog.i("ActivityManager", sb2);
            } else {
                android.util.Slog.wtfQuiet("ActivityManager", sb2);
                android.util.Slog.w("ActivityManager", sb2);
            }
            serviceRecord.mLoggedInfoAllowStartForeground = true;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r42v0 */
    /* JADX WARN: Type inference failed for: r42v1, types: [int] */
    /* JADX WARN: Type inference failed for: r42v2 */
    private void logFGSStateChangeLocked(com.android.server.am.ServiceRecord serviceRecord, int i, int i2, int i3, int i4, int i5, boolean z) {
        boolean z2;
        int i6;
        int i7;
        if (!com.android.server.am.ActivityManagerUtils.shouldSamplePackageForAtom(serviceRecord.packageName, this.mAm.mConstants.mFgsAtomSampleRate)) {
            return;
        }
        if (i == 1 || i == 2 || i == 5) {
            z2 = serviceRecord.mAllowWhileInUsePermissionInFgsAtEntering;
            i6 = serviceRecord.mAllowStartForegroundAtEntering;
        } else {
            z2 = serviceRecord.isFgsAllowedWiu_forCapabilities();
            i6 = serviceRecord.getFgsAllowStart();
        }
        int i8 = serviceRecord.mRecentCallerApplicationInfo != null ? serviceRecord.mRecentCallerApplicationInfo.targetSdkVersion : 0;
        com.android.internal.util.FrameworkStatsLog.write(60, serviceRecord.appInfo.uid, serviceRecord.shortInstanceName, i, z2, i6, serviceRecord.appInfo.targetSdkVersion, serviceRecord.mRecentCallingUid, i8, serviceRecord.mInfoTempFgsAllowListReason != null ? serviceRecord.mInfoTempFgsAllowListReason.mCallingUid : -1, serviceRecord.mFgsNotificationWasDeferred, serviceRecord.mFgsNotificationShown, i2, serviceRecord.mStartForegroundCount, 0, serviceRecord.mFgsHasNotificationPermission, serviceRecord.foregroundServiceType, i4, serviceRecord.mIsFgsDelegate, serviceRecord.mFgsDelegation != null ? serviceRecord.mFgsDelegation.mOptions.mClientUid : -1, serviceRecord.mFgsDelegation != null ? serviceRecord.mFgsDelegation.mOptions.mDelegationService : 0, 0, null, null, this.mAm.getUidStateLocked(serviceRecord.appInfo.uid), this.mAm.getUidProcessCapabilityLocked(serviceRecord.appInfo.uid), this.mAm.getUidStateLocked(serviceRecord.mRecentCallingUid), this.mAm.getUidProcessCapabilityLocked(serviceRecord.mRecentCallingUid), 0L, 0L, serviceRecord.mAllowWiu_noBinding, serviceRecord.mAllowWiu_inBindService, serviceRecord.mAllowWiu_byBindings, serviceRecord.mAllowStart_noBinding, serviceRecord.mAllowStart_inBindService, serviceRecord.mAllowStart_byBindings, i5, z);
        if (i == 1) {
            i7 = com.android.server.am.EventLogTags.AM_FOREGROUND_SERVICE_START;
        } else if (i == 2) {
            i7 = com.android.server.am.EventLogTags.AM_FOREGROUND_SERVICE_STOP;
        } else if (i == 3) {
            i7 = com.android.server.am.EventLogTags.AM_FOREGROUND_SERVICE_DENIED;
        } else if (i == 5) {
            i7 = com.android.server.am.EventLogTags.AM_FOREGROUND_SERVICE_TIMED_OUT;
        } else {
            return;
        }
        android.util.EventLog.writeEvent(i7, java.lang.Integer.valueOf(serviceRecord.userId), serviceRecord.shortInstanceName, java.lang.Integer.valueOf((int) z2), android.os.PowerExemptionManager.reasonCodeToString(i6), java.lang.Integer.valueOf(serviceRecord.appInfo.targetSdkVersion), java.lang.Integer.valueOf(i8), java.lang.Integer.valueOf(serviceRecord.mFgsNotificationWasDeferred ? 1 : 0), java.lang.Integer.valueOf(serviceRecord.mFgsNotificationShown ? 1 : 0), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(serviceRecord.mStartForegroundCount), fgsStopReasonToString(i3), java.lang.Integer.valueOf(serviceRecord.foregroundServiceType));
    }

    private void updateNumForegroundServicesLocked() {
        sNumForegroundServices.set(this.mAm.mProcessList.getNumForegroundServices());
    }

    boolean canAllowWhileInUsePermissionInFgsLocked(int i, int i2, java.lang.String str) {
        return shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, null, android.app.BackgroundStartPrivileges.NONE) != -1;
    }

    boolean canAllowWhileInUsePermissionInFgsLocked(int i, int i2, java.lang.String str, @android.annotation.Nullable com.android.server.am.ProcessRecord processRecord, @android.annotation.NonNull android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        return shouldAllowFgsWhileInUsePermissionLocked(str, i, i2, processRecord, backgroundStartPrivileges) != -1;
    }

    private boolean verifyPackage(java.lang.String str, int i) {
        if (i == 0 || i == 1000) {
            return true;
        }
        return this.mAm.getPackageManagerInternal().isSameApp(str, i, android.os.UserHandle.getUserId(i));
    }

    private static java.lang.String fgsStopReasonToString(int i) {
        switch (i) {
            case 1:
                return "STOP_FOREGROUND";
            case 2:
                return "STOP_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    boolean startForegroundServiceDelegateLocked(@android.annotation.NonNull android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, @android.annotation.Nullable final android.content.ServiceConnection serviceConnection) {
        com.android.server.am.ProcessRecord processRecord;
        android.app.IApplicationThread thread;
        com.android.server.am.ProcessRecord processRecord2;
        android.app.IApplicationThread iApplicationThread;
        boolean z;
        android.util.Slog.v("ActivityManager", "startForegroundServiceDelegateLocked " + foregroundServiceDelegationOptions.getDescription());
        final android.content.ComponentName componentName = foregroundServiceDelegationOptions.getComponentName();
        for (int size = this.mFgsDelegations.size() - 1; size >= 0; size--) {
            if (this.mFgsDelegations.keyAt(size).mOptions.isSameDelegate(foregroundServiceDelegationOptions)) {
                android.util.Slog.e("ActivityManager", "startForegroundServiceDelegate " + foregroundServiceDelegationOptions.getDescription() + " already exists, multiple connections are not allowed");
                return false;
            }
        }
        int i = foregroundServiceDelegationOptions.mClientPid;
        int i2 = foregroundServiceDelegationOptions.mClientUid;
        int userId = android.os.UserHandle.getUserId(i2);
        java.lang.String str = foregroundServiceDelegationOptions.mClientPackageName;
        if (!canStartForegroundServiceLocked(i, i2, str)) {
            android.util.Slog.d("ActivityManager", "startForegroundServiceDelegateLocked aborted, app is in the background");
            return false;
        }
        android.app.IApplicationThread iApplicationThread2 = foregroundServiceDelegationOptions.mClientAppThread;
        if (iApplicationThread2 != null) {
            iApplicationThread = iApplicationThread2;
            processRecord2 = this.mAm.getRecordForAppLOSP(iApplicationThread2);
        } else {
            synchronized (this.mAm.mPidsSelfLocked) {
                processRecord = this.mAm.mPidsSelfLocked.get(i);
                thread = processRecord.getThread();
            }
            processRecord2 = processRecord;
            iApplicationThread = thread;
        }
        if (processRecord2 == null) {
            throw new java.lang.SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + i + ") when startForegroundServiceDelegateLocked " + componentName);
        }
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(componentName);
        com.android.server.am.ProcessRecord processRecord3 = processRecord2;
        android.app.IApplicationThread iApplicationThread3 = iApplicationThread;
        com.android.server.am.ActiveServices.ServiceLookupResult retrieveServiceLocked = retrieveServiceLocked(intent, null, false, -1, null, null, str, i, i2, userId, true, false, false, false, foregroundServiceDelegationOptions, false, false);
        if (retrieveServiceLocked == null) {
            z = false;
        } else {
            if (retrieveServiceLocked.record != null) {
                com.android.server.am.ServiceRecord serviceRecord = retrieveServiceLocked.record;
                serviceRecord.setProcess(processRecord3, iApplicationThread3, i, null);
                serviceRecord.mIsFgsDelegate = true;
                final com.android.server.am.ForegroundServiceDelegation foregroundServiceDelegation = new com.android.server.am.ForegroundServiceDelegation(foregroundServiceDelegationOptions, serviceConnection);
                serviceRecord.mFgsDelegation = foregroundServiceDelegation;
                this.mFgsDelegations.put(foregroundServiceDelegation, serviceRecord);
                serviceRecord.isForeground = true;
                serviceRecord.mFgsEnterTime = android.os.SystemClock.uptimeMillis();
                serviceRecord.foregroundServiceType = foregroundServiceDelegationOptions.mForegroundServiceTypes;
                setFgsRestrictionLocked(str, i, i2, intent, serviceRecord, userId, android.app.BackgroundStartPrivileges.NONE, false);
                com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord3.mServices;
                processServiceRecord.startService(serviceRecord);
                updateServiceForegroundLocked(processServiceRecord, true);
                synchronized (this.mAm.mProcessStats.mLock) {
                    try {
                        com.android.internal.app.procstats.ServiceState tracker = serviceRecord.getTracker();
                        if (tracker != null) {
                            tracker.setForeground(true, this.mAm.mProcessStats.getMemFactorLocked(), android.os.SystemClock.uptimeMillis());
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                this.mAm.mBatteryStatsService.noteServiceStartRunning(i2, str, componentName.getClassName());
                this.mAm.mAppOpsService.startOperation(android.app.AppOpsManager.getToken(this.mAm.mAppOpsService), 76, serviceRecord.appInfo.uid, serviceRecord.packageName, null, true, false, null, false, 0, -1);
                registerAppOpCallbackLocked(serviceRecord);
                synchronized (this.mFGSLogger) {
                    this.mFGSLogger.logForegroundServiceStart(serviceRecord.appInfo.uid, 0, serviceRecord);
                }
                logFGSStateChangeLocked(serviceRecord, 1, 0, 0, 0, 4, false);
                if (serviceConnection != null) {
                    this.mAm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ActiveServices$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.am.ActiveServices.lambda$startForegroundServiceDelegateLocked$8(serviceConnection, componentName, foregroundServiceDelegation);
                        }
                    });
                }
                signalForegroundServiceObserversLocked(serviceRecord);
                return true;
            }
            z = false;
        }
        android.util.Slog.d("ActivityManager", "startForegroundServiceDelegateLocked retrieveServiceLocked returns null");
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startForegroundServiceDelegateLocked$8(android.content.ServiceConnection serviceConnection, android.content.ComponentName componentName, com.android.server.am.ForegroundServiceDelegation foregroundServiceDelegation) {
        serviceConnection.onServiceConnected(componentName, foregroundServiceDelegation.mBinder);
    }

    void stopForegroundServiceDelegateLocked(@android.annotation.NonNull android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions) {
        com.android.server.am.ServiceRecord serviceRecord;
        int size = this.mFgsDelegations.size();
        while (true) {
            size--;
            if (size < 0) {
                serviceRecord = null;
                break;
            } else if (this.mFgsDelegations.keyAt(size).mOptions.isSameDelegate(foregroundServiceDelegationOptions)) {
                android.util.Slog.d("ActivityManager", "stopForegroundServiceDelegateLocked " + foregroundServiceDelegationOptions.getDescription());
                serviceRecord = this.mFgsDelegations.valueAt(size);
                break;
            }
        }
        if (serviceRecord != null) {
            bringDownServiceLocked(serviceRecord, false);
            return;
        }
        android.util.Slog.e("ActivityManager", "stopForegroundServiceDelegateLocked delegate does not exist " + foregroundServiceDelegationOptions.getDescription());
    }

    void stopForegroundServiceDelegateLocked(@android.annotation.NonNull android.content.ServiceConnection serviceConnection) {
        com.android.server.am.ServiceRecord serviceRecord;
        int size = this.mFgsDelegations.size();
        while (true) {
            size--;
            if (size < 0) {
                serviceRecord = null;
                break;
            }
            com.android.server.am.ForegroundServiceDelegation keyAt = this.mFgsDelegations.keyAt(size);
            if (keyAt.mConnection == serviceConnection) {
                android.util.Slog.d("ActivityManager", "stopForegroundServiceDelegateLocked " + keyAt.mOptions.getDescription());
                serviceRecord = this.mFgsDelegations.valueAt(size);
                break;
            }
        }
        if (serviceRecord != null) {
            bringDownServiceLocked(serviceRecord, false);
        } else {
            android.util.Slog.e("ActivityManager", "stopForegroundServiceDelegateLocked delegate does not exist");
        }
    }

    private static void getClientPackages(com.android.server.am.ServiceRecord serviceRecord, android.util.ArraySet<java.lang.String> arraySet) {
        android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = serviceRecord.getConnections();
        for (int size = connections.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
            int size2 = valueAt.size();
            for (int i = 0; i < size2; i++) {
                com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i);
                if (connectionRecord.binding.client != null) {
                    arraySet.add(connectionRecord.binding.client.info.packageName);
                }
            }
        }
    }

    android.util.ArraySet<java.lang.String> getClientPackagesLocked(@android.annotation.NonNull java.lang.String str) {
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        for (int i : this.mAm.mUserController.getUsers()) {
            android.util.ArrayMap<android.content.ComponentName, com.android.server.am.ServiceRecord> servicesLocked = getServicesLocked(i);
            int size = servicesLocked.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.am.ServiceRecord valueAt = servicesLocked.valueAt(i2);
                if (valueAt.name.getPackageName().equals(str)) {
                    getClientPackages(valueAt, arraySet);
                }
            }
        }
        return arraySet;
    }

    private boolean isDeviceProvisioningPackage(java.lang.String str) {
        if (this.mCachedDeviceProvisioningPackage == null) {
            this.mCachedDeviceProvisioningPackage = this.mAm.mContext.getResources().getString(android.R.string.config_defaultTranslationService);
        }
        return this.mCachedDeviceProvisioningPackage != null && this.mCachedDeviceProvisioningPackage.equals(str);
    }
}
