package com.android.server.appwidget;

/* loaded from: classes.dex */
class AppWidgetServiceImpl extends com.android.internal.appwidget.IAppWidgetService.Stub implements com.android.server.WidgetBackupProvider, android.app.admin.DevicePolicyManagerInternal.OnCrossProfileWidgetProvidersChangeListener {
    private static final int CURRENT_VERSION = 1;
    private static final boolean DEBUG = false;
    private static final int ID_PROVIDER_CHANGED = 1;
    private static final int ID_VIEWS_UPDATE = 0;
    private static final int KEYGUARD_HOST_ID = 1262836039;
    private static final int MIN_UPDATE_PERIOD = 1800000;
    private static final java.lang.String NEW_KEYGUARD_HOST_PACKAGE = "com.android.keyguard";
    private static final java.lang.String OLD_KEYGUARD_HOST_PACKAGE = "android";
    private static final java.lang.String STATE_FILENAME = "appwidgets.xml";
    private static final java.lang.String TAG = "AppWidgetServiceImpl";
    private static final int TAG_UNDEFINED = -1;
    private static final int UNKNOWN_UID = -1;
    private static final int UNKNOWN_USER_ID = -10;
    private static final java.util.concurrent.atomic.AtomicLong UPDATE_COUNTER = new java.util.concurrent.atomic.AtomicLong();
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    private android.app.AlarmManager mAlarmManager;
    private android.app.AppOpsManager mAppOpsManager;
    private android.app.AppOpsManagerInternal mAppOpsManagerInternal;
    private com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController mBackupRestoreController;
    private android.os.Handler mCallbackHandler;
    private final android.content.Context mContext;
    private android.app.admin.DevicePolicyManagerInternal mDevicePolicyManagerInternal;
    private android.os.Bundle mInteractiveBroadcast;
    private boolean mIsCombinedBroadcastEnabled;
    private android.app.KeyguardManager mKeyguardManager;
    private int mMaxWidgetBitmapMemory;
    private android.content.pm.IPackageManager mPackageManager;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private boolean mSafeMode;
    private android.os.Handler mSaveStateHandler;
    private com.android.server.appwidget.AppWidgetServiceImpl.SecurityPolicy mSecurityPolicy;
    private android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal;
    private android.os.UserManager mUserManager;
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            java.lang.String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
            switch (action.hashCode()) {
                case -1238404651:
                    if (action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1001645458:
                    if (action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -864107122:
                    if (action.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1290767157:
                    if (action.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                        c = 3;
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
                case 1:
                    synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                        com.android.server.appwidget.AppWidgetServiceImpl.this.reloadWidgetsMaskedState(intExtra);
                    }
                    return;
                case 2:
                    com.android.server.appwidget.AppWidgetServiceImpl.this.onPackageBroadcastReceived(intent, getSendingUserId());
                    com.android.server.appwidget.AppWidgetServiceImpl.this.updateWidgetPackageSuspensionMaskedState(intent, true, getSendingUserId());
                    return;
                case 3:
                    com.android.server.appwidget.AppWidgetServiceImpl.this.onPackageBroadcastReceived(intent, getSendingUserId());
                    com.android.server.appwidget.AppWidgetServiceImpl.this.updateWidgetPackageSuspensionMaskedState(intent, false, getSendingUserId());
                    return;
                default:
                    com.android.server.appwidget.AppWidgetServiceImpl.this.onPackageBroadcastReceived(intent, getSendingUserId());
                    return;
            }
        }
    };
    private final java.util.HashMap<android.util.Pair<java.lang.Integer, android.content.Intent.FilterComparison>, java.util.HashSet<java.lang.Integer>> mRemoteViewsServicesAppWidgets = new java.util.HashMap<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.Widget> mWidgets = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.Host> mHosts = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.Provider> mProviders = new java.util.ArrayList<>();
    private final android.util.ArraySet<android.util.Pair<java.lang.Integer, java.lang.String>> mPackagesWithBindWidgetPermission = new android.util.ArraySet<>();
    private final android.util.SparseBooleanArray mLoadedUserIds = new android.util.SparseBooleanArray();
    private final java.lang.Object mWidgetPackagesLock = new java.lang.Object();
    private final android.util.SparseArray<android.util.ArraySet<java.lang.String>> mWidgetPackages = new android.util.SparseArray<>();
    private final android.util.SparseIntArray mNextAppWidgetIds = new android.util.SparseIntArray();

    AppWidgetServiceImpl(android.content.Context context) {
        this.mContext = context;
    }

    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    public void onStart() {
        this.mPackageManager = android.app.AppGlobals.getPackageManager();
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService("user");
        this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService("appops");
        this.mKeyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService("keyguard");
        this.mDevicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (android.appwidget.flags.Flags.removeAppWidgetServiceIoFromCriticalPath()) {
            this.mSaveStateHandler = new android.os.Handler(com.android.internal.os.BackgroundThread.get().getLooper(), new android.os.Handler.Callback() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda0
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(android.os.Message message) {
                    boolean handleSaveMessage;
                    handleSaveMessage = com.android.server.appwidget.AppWidgetServiceImpl.this.handleSaveMessage(message);
                    return handleSaveMessage;
                }
            });
        } else {
            this.mSaveStateHandler = com.android.internal.os.BackgroundThread.getHandler();
        }
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, -2, false);
        serviceThread.start();
        this.mCallbackHandler = new com.android.server.appwidget.AppWidgetServiceImpl.CallbackHandler(serviceThread.getLooper());
        this.mBackupRestoreController = new com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController();
        this.mSecurityPolicy = new com.android.server.appwidget.AppWidgetServiceImpl.SecurityPolicy();
        this.mIsCombinedBroadcastEnabled = android.provider.DeviceConfig.getBoolean("systemui", "combined_broadcast_enabled", true);
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setBackgroundActivityStartsAllowed(false);
        makeBasic.setInteractive(true);
        this.mInteractiveBroadcast = makeBasic.toBundle();
        computeMaximumWidgetBitmapMemory();
        registerBroadcastReceiver();
        registerOnCrossProfileProvidersChangedListener();
        com.android.server.LocalServices.addService(android.appwidget.AppWidgetManagerInternal.class, new com.android.server.appwidget.AppWidgetServiceImpl.AppWidgetManagerLocal());
    }

    void systemServicesReady() {
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mAppOpsManagerInternal = (android.app.AppOpsManagerInternal) com.android.server.LocalServices.getService(android.app.AppOpsManagerInternal.class);
        this.mUsageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
    }

    private void computeMaximumWidgetBitmapMemory() {
        android.view.Display displayNoVerify = this.mContext.getDisplayNoVerify();
        android.graphics.Point point = new android.graphics.Point();
        displayNoVerify.getRealSize(point);
        this.mMaxWidgetBitmapMemory = point.x * 6 * point.y;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleSaveMessage(android.os.Message message) {
        int i;
        android.util.SparseArray<byte[]> saveStateToByteArrayLocked;
        int i2 = message.what;
        synchronized (this.mLock) {
            android.os.Trace.traceBegin(64L, "convert_state_to_bytes");
            ensureGroupStateLoadedLocked(i2, false);
            saveStateToByteArrayLocked = saveStateToByteArrayLocked(i2);
            android.os.Trace.traceEnd(64L);
        }
        android.os.Trace.traceBegin(64L, "byte_to_disk_io");
        for (i = 0; i < saveStateToByteArrayLocked.size(); i++) {
            int keyAt = saveStateToByteArrayLocked.keyAt(i);
            byte[] valueAt = saveStateToByteArrayLocked.valueAt(i);
            android.util.AtomicFile savedStateFile = getSavedStateFile(keyAt);
            try {
                java.io.FileOutputStream startWrite = savedStateFile.startWrite();
                try {
                    startWrite.write(valueAt);
                    savedStateFile.finishWrite(startWrite);
                } catch (java.io.IOException e) {
                    android.util.Log.e(TAG, "Failed to write state byte stream to file", e);
                    savedStateFile.failWrite(startWrite);
                }
            } catch (java.io.IOException e2) {
                android.util.Log.e(TAG, "Failed to start writing stream", e2);
            }
        }
        android.os.Trace.traceEnd(64L);
        return true;
    }

    private void registerBroadcastReceiver() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, this.mCallbackHandler);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter2, null, this.mCallbackHandler);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
        intentFilter3.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter3.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter3, null, this.mCallbackHandler);
        android.content.IntentFilter intentFilter4 = new android.content.IntentFilter();
        intentFilter4.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter4.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter4, null, this.mCallbackHandler);
    }

    private void registerOnCrossProfileProvidersChangedListener() {
        if (this.mDevicePolicyManagerInternal != null) {
            this.mDevicePolicyManagerInternal.addOnCrossProfileWidgetProvidersChangeListener(this);
        }
    }

    public void setSafeMode(boolean z) {
        this.mSafeMode = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00d3, code lost:
    
        r11 = r7.length;
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d5, code lost:
    
        if (r3 >= r11) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00d7, code lost:
    
        r0 = r0 | removeHostsAndProvidersForPackageLocked(r7[r3], r12);
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e1, code lost:
    
        r3 = r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onPackageBroadcastReceived(android.content.Intent intent, int i) {
        char c;
        boolean z;
        int i2;
        java.lang.String[] stringArrayExtra;
        boolean z2;
        boolean z3;
        int uidForPackage;
        java.lang.String schemeSpecificPart;
        java.lang.String action = intent.getAction();
        boolean z4 = true;
        int i3 = 0;
        boolean z5 = false;
        switch (action.hashCode()) {
            case -1403934493:
                if (action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1338021860:
                if (action.equals("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1001645458:
                if (action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 267468725:
                if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1290767157:
                if (action.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
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
            case 1:
                z = true;
                i2 = -1;
                stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z2 = false;
                if ((stringArrayExtra != null || stringArrayExtra.length == 0) && i2 == -1) {
                    return;
                }
                synchronized (this.mLock) {
                    try {
                        if (this.mUserManager.isUserUnlockingOrUnlocked(i) && !isProfileWithLockedParent(i)) {
                            ensureGroupStateLoadedLocked(i, false);
                            android.os.Bundle extras = intent.getExtras();
                            if (z2 || z) {
                                if (!z2 || (extras != null && extras.getBoolean("android.intent.extra.REPLACING", false))) {
                                    z4 = false;
                                }
                                boolean z6 = false;
                                for (java.lang.String str : stringArrayExtra) {
                                    z6 |= updateProvidersForPackageLocked(str, i, null);
                                    if (z4 && i == 0 && (uidForPackage = getUidForPackage(str, i)) >= 0) {
                                        resolveHostUidLocked(str, uidForPackage);
                                    }
                                }
                                z5 = z6;
                            } else if (i2 != -1) {
                                z5 = false | clearPreviewsForUidLocked(i2);
                            } else if (extras != null && extras.getBoolean("android.intent.extra.REPLACING", false)) {
                                z4 = false;
                                break;
                            }
                            if (z5) {
                                saveGroupStateAsync(i);
                                scheduleNotifyGroupHostsForProvidersChangedLocked(i);
                                this.mBackupRestoreController.widgetComponentsChanged(i);
                            }
                            return;
                        }
                        return;
                    } finally {
                    }
                }
            case 2:
                z3 = true;
                i2 = -1;
                stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z2 = z3;
                z = false;
                if (stringArrayExtra != null) {
                    break;
                }
                return;
            case 3:
                z3 = false;
                i2 = -1;
                stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z2 = z3;
                z = false;
                if (stringArrayExtra != null) {
                }
                return;
            case 4:
                i2 = intent.getIntExtra("android.intent.extra.UID", -1);
                z = false;
                z2 = false;
                stringArrayExtra = null;
                if (stringArrayExtra != null) {
                }
                return;
            default:
                android.net.Uri data = intent.getData();
                if (data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                    return;
                }
                stringArrayExtra = new java.lang.String[]{schemeSpecificPart};
                z2 = "android.intent.action.PACKAGE_ADDED".equals(action);
                z = "android.intent.action.PACKAGE_CHANGED".equals(action);
                i2 = -1;
                if (stringArrayExtra != null) {
                }
                return;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean clearPreviewsForUidLocked(int i) {
        int size = this.mProviders.size();
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i2);
            if (provider.id.uid == i) {
                z |= provider.clearGeneratedPreviewsLocked();
            }
        }
        return z;
    }

    void reloadWidgetsMaskedStateForGroup(int i) {
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                reloadWidgetsMaskedState(i);
                for (int i2 : this.mUserManager.getEnabledProfileIds(i)) {
                    reloadWidgetsMaskedState(i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadWidgetsMaskedState(int i) {
        boolean z;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
            boolean z2 = !this.mUserManager.isUserUnlockingOrUnlocked(i);
            boolean isQuietModeEnabled = userInfo.isQuietModeEnabled();
            int size = this.mProviders.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i2);
                if (provider.getUserId() == i) {
                    boolean maskedByLockedProfileLocked = provider.setMaskedByLockedProfileLocked(z2) | provider.setMaskedByQuietProfileLocked(isQuietModeEnabled);
                    try {
                        try {
                            z = this.mPackageManager.isPackageSuspendedForUser(provider.id.componentName.getPackageName(), provider.getUserId());
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(TAG, "Failed to query application info", e);
                        }
                    } catch (java.lang.IllegalArgumentException e2) {
                        z = false;
                    }
                    maskedByLockedProfileLocked |= provider.setMaskedBySuspendedPackageLocked(z);
                    if (maskedByLockedProfileLocked) {
                        if (provider.isMaskedLocked()) {
                            maskWidgetsViewsLocked(provider, null);
                        } else {
                            unmaskWidgetsViewsLocked(provider);
                        }
                    }
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWidgetPackageSuspensionMaskedState(android.content.Intent intent, boolean z, int i) {
        java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
        if (stringArrayExtra == null) {
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(java.util.Arrays.asList(stringArrayExtra));
        synchronized (this.mLock) {
            try {
                int size = this.mProviders.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i2);
                    if (provider.getUserId() == i && arraySet.contains(provider.id.componentName.getPackageName()) && provider.setMaskedBySuspendedPackageLocked(z)) {
                        if (provider.isMaskedLocked()) {
                            maskWidgetsViewsLocked(provider, null);
                        } else {
                            unmaskWidgetsViewsLocked(provider);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void maskWidgetsViewsLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        android.content.Intent createConfirmDeviceCredentialIntent;
        android.graphics.drawable.Icon createWithResource;
        int size = provider.widgets.size();
        if (size == 0) {
            return;
        }
        android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(this.mContext.getPackageName(), android.R.layout.web_text_view_dropdown);
        android.content.pm.ApplicationInfo applicationInfo = provider.info.providerInfo.applicationInfo;
        int userId = provider.getUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            boolean z = true;
            if (provider.maskedByQuietProfile) {
                createConfirmDeviceCredentialIntent = com.android.internal.app.UnlaunchableAppActivity.createInQuietModeDialogIntent(userId);
            } else if (provider.maskedBySuspendedPackage) {
                boolean hasBadge = this.mUserManager.hasBadge(userId);
                android.content.pm.UserPackage suspendingPackage = this.mPackageManagerInternal.getSuspendingPackage(applicationInfo.packageName, userId);
                if (suspendingPackage != null && "android".equals(suspendingPackage.packageName)) {
                    createConfirmDeviceCredentialIntent = this.mDevicePolicyManagerInternal.createShowAdminSupportIntent(userId, true);
                } else {
                    createConfirmDeviceCredentialIntent = com.android.internal.app.SuspendedAppActivity.createSuspendedAppInterceptIntent(applicationInfo.packageName, suspendingPackage, this.mPackageManagerInternal.getSuspendedDialogInfo(applicationInfo.packageName, suspendingPackage, userId), (android.os.Bundle) null, (android.content.IntentSender) null, userId);
                }
                z = hasBadge;
            } else {
                createConfirmDeviceCredentialIntent = this.mKeyguardManager.createConfirmDeviceCredentialIntent(null, null, userId);
                if (createConfirmDeviceCredentialIntent != null) {
                    createConfirmDeviceCredentialIntent.setFlags(276824064);
                }
            }
            if (applicationInfo.icon != 0) {
                createWithResource = android.graphics.drawable.Icon.createWithResource(applicationInfo.packageName, applicationInfo.icon);
            } else {
                createWithResource = android.graphics.drawable.Icon.createWithResource(this.mContext, android.R.drawable.sym_def_app_icon);
            }
            remoteViews.setImageViewIcon(android.R.id.wipe, createWithResource);
            if (!z) {
                remoteViews.setViewVisibility(android.R.id.withText, 4);
            }
            for (int i = 0; i < size; i++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget2 = provider.widgets.get(i);
                if (widget == null || widget == widget2) {
                    if (createConfirmDeviceCredentialIntent != null) {
                        remoteViews.setOnClickPendingIntent(android.R.id.background, android.app.PendingIntent.getActivity(this.mContext, widget2.appWidgetId, createConfirmDeviceCredentialIntent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD));
                    }
                    if (widget2.replaceWithMaskedViewsLocked(remoteViews)) {
                        scheduleNotifyUpdateAppWidgetLocked(widget2, widget2.getEffectiveViewsLocked());
                    }
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void unmaskWidgetsViewsLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider) {
        int size = provider.widgets.size();
        for (int i = 0; i < size; i++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = provider.widgets.get(i);
            if (widget.clearMaskedViewsLocked()) {
                scheduleNotifyUpdateAppWidgetLocked(widget, widget.getEffectiveViewsLocked());
            }
        }
    }

    private void resolveHostUidLocked(java.lang.String str, int i) {
        int size = this.mHosts.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Host host = this.mHosts.get(i2);
            if (host.id.uid == -1 && str.equals(host.id.packageName)) {
                host.id = new com.android.server.appwidget.AppWidgetServiceImpl.HostId(i, host.id.hostId, host.id.packageName);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void ensureGroupStateLoadedLocked(int i) {
        ensureGroupStateLoadedLocked(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void ensureGroupStateLoadedLocked(int i, boolean z) {
        if (z && !isUserRunningAndUnlocked(i)) {
            throw new java.lang.IllegalStateException("User " + i + " must be unlocked for widgets to be available");
        }
        if (z && isProfileWithLockedParent(i)) {
            throw new java.lang.IllegalStateException("Profile " + i + " must have unlocked parent");
        }
        int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
        android.util.IntArray intArray = new android.util.IntArray(1);
        for (int i2 : enabledGroupProfileIds) {
            if (!this.mLoadedUserIds.get(i2)) {
                this.mLoadedUserIds.put(i2, true);
                intArray.add(i2);
            }
        }
        if (intArray.size() <= 0) {
            return;
        }
        int[] array = intArray.toArray();
        clearProvidersAndHostsTagsLocked();
        loadGroupWidgetProvidersLocked(array);
        loadGroupStateLocked(array);
    }

    private boolean isUserRunningAndUnlocked(int i) {
        return this.mUserManager.isUserUnlockingOrUnlocked(i);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            synchronized (this.mLock) {
                try {
                    if (strArr.length > 0 && "--proto".equals(strArr[0])) {
                        dumpProto(fileDescriptor);
                    } else {
                        dumpInternalLocked(printWriter);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void dumpProto(java.io.FileDescriptor fileDescriptor) {
        android.util.Slog.i(TAG, "dump proto for " + this.mWidgets.size() + " widgets");
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        int size = this.mWidgets.size();
        for (int i = 0; i < size; i++) {
            dumpProtoWidget(protoOutputStream, this.mWidgets.get(i));
        }
        protoOutputStream.flush();
    }

    private void dumpProtoWidget(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        if (widget.host == null || widget.provider == null) {
            android.util.Slog.d(TAG, "skip dumping widget because host or provider is null: widget.host=" + widget.host + " widget.provider=" + widget.provider);
            return;
        }
        long start = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1133871366145L, widget.host.getUserId() != widget.provider.getUserId());
        protoOutputStream.write(1133871366146L, widget.host.callbacks == null);
        protoOutputStream.write(1138166333443L, widget.host.id.packageName);
        protoOutputStream.write(1138166333444L, widget.provider.id.componentName.getPackageName());
        protoOutputStream.write(1138166333445L, widget.provider.id.componentName.getClassName());
        if (widget.options != null) {
            protoOutputStream.write(1133871366154L, widget.options.getBoolean("appWidgetRestoreCompleted"));
            protoOutputStream.write(1120986464262L, widget.options.getInt("appWidgetMinWidth", 0));
            protoOutputStream.write(1120986464263L, widget.options.getInt("appWidgetMinHeight", 0));
            protoOutputStream.write(1120986464264L, widget.options.getInt("appWidgetMaxWidth", 0));
            protoOutputStream.write(1120986464265L, widget.options.getInt("appWidgetMaxHeight", 0));
        }
        protoOutputStream.end(start);
    }

    private void dumpInternalLocked(java.io.PrintWriter printWriter) {
        int size = this.mProviders.size();
        printWriter.println("Providers:");
        for (int i = 0; i < size; i++) {
            dumpProviderLocked(this.mProviders.get(i), i, printWriter);
        }
        int size2 = this.mWidgets.size();
        printWriter.println(" ");
        printWriter.println("Widgets:");
        for (int i2 = 0; i2 < size2; i2++) {
            dumpWidget(this.mWidgets.get(i2), i2, printWriter);
        }
        int size3 = this.mHosts.size();
        printWriter.println(" ");
        printWriter.println("Hosts:");
        for (int i3 = 0; i3 < size3; i3++) {
            dumpHost(this.mHosts.get(i3), i3, printWriter);
        }
        int size4 = this.mPackagesWithBindWidgetPermission.size();
        printWriter.println(" ");
        printWriter.println("Grants:");
        for (int i4 = 0; i4 < size4; i4++) {
            dumpGrant(this.mPackagesWithBindWidgetPermission.valueAt(i4), i4, printWriter);
        }
    }

    public android.content.pm.ParceledListSlice<android.appwidget.PendingHostUpdate> startListening(com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost, java.lang.String str, int i, int[] iArr) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.isInstantAppLocked(str, callingUserId)) {
                    android.util.Slog.w(TAG, "Instant package " + str + " cannot host app widgets");
                    return android.content.pm.ParceledListSlice.emptyList();
                }
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Host lookupOrAddHostLocked = lookupOrAddHostLocked(new com.android.server.appwidget.AppWidgetServiceImpl.HostId(android.os.Binder.getCallingUid(), i, str));
                lookupOrAddHostLocked.callbacks = iAppWidgetHost;
                long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
                java.util.ArrayList arrayList = new java.util.ArrayList(iArr.length);
                android.util.LongSparseArray<android.appwidget.PendingHostUpdate> longSparseArray = new android.util.LongSparseArray<>();
                for (int i2 : iArr) {
                    longSparseArray.clear();
                    lookupOrAddHostLocked.getPendingUpdatesForIdLocked(this.mContext, i2, longSparseArray);
                    int size = longSparseArray.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        arrayList.add(longSparseArray.valueAt(i3));
                    }
                }
                lookupOrAddHostLocked.lastWidgetUpdateSequenceNo = incrementAndGet;
                return new android.content.pm.ParceledListSlice<>(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopListening(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, false);
                com.android.server.appwidget.AppWidgetServiceImpl.Host lookupHostLocked = lookupHostLocked(new com.android.server.appwidget.AppWidgetServiceImpl.HostId(android.os.Binder.getCallingUid(), i, str));
                if (lookupHostLocked != null) {
                    lookupHostLocked.callbacks = null;
                    pruneHostLocked(lookupHostLocked);
                    this.mAppOpsManagerInternal.updateAppWidgetVisibility(lookupHostLocked.getWidgetUidsIfBound(), false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int allocateAppWidgetId(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.isInstantAppLocked(str, callingUserId)) {
                    android.util.Slog.w(TAG, "Instant package " + str + " cannot host app widgets");
                    return 0;
                }
                ensureGroupStateLoadedLocked(callingUserId);
                if (this.mNextAppWidgetIds.indexOfKey(callingUserId) < 0) {
                    this.mNextAppWidgetIds.put(callingUserId, 1);
                }
                int incrementAndGetAppWidgetIdLocked = incrementAndGetAppWidgetIdLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Host lookupOrAddHostLocked = lookupOrAddHostLocked(new com.android.server.appwidget.AppWidgetServiceImpl.HostId(android.os.Binder.getCallingUid(), i, str));
                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = new com.android.server.appwidget.AppWidgetServiceImpl.Widget();
                widget.appWidgetId = incrementAndGetAppWidgetIdLocked;
                widget.host = lookupOrAddHostLocked;
                lookupOrAddHostLocked.widgets.add(widget);
                addWidgetLocked(widget);
                saveGroupStateAsync(callingUserId);
                return incrementAndGetAppWidgetIdLocked;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setAppWidgetHidden(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId, false);
                com.android.server.appwidget.AppWidgetServiceImpl.Host lookupHostLocked = lookupHostLocked(new com.android.server.appwidget.AppWidgetServiceImpl.HostId(android.os.Binder.getCallingUid(), i, str));
                if (lookupHostLocked != null) {
                    this.mAppOpsManagerInternal.updateAppWidgetVisibility(lookupHostLocked.getWidgetUidsIfBound(), false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void deleteAppWidgetId(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    return;
                }
                deleteAppWidgetLocked(lookupWidgetLocked);
                saveGroupStateAsync(callingUserId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean hasBindAppWidgetPermission(java.lang.String str, int i) {
        this.mSecurityPolicy.enforceModifyAppWidgetBindPermissions(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(i);
                if (getUidForPackage(str, i) < 0) {
                    return false;
                }
                return this.mPackagesWithBindWidgetPermission.contains(android.util.Pair.create(java.lang.Integer.valueOf(i), str));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setBindAppWidgetPermission(java.lang.String str, int i, boolean z) {
        this.mSecurityPolicy.enforceModifyAppWidgetBindPermissions(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(i);
                if (getUidForPackage(str, i) < 0) {
                    return;
                }
                android.util.Pair<java.lang.Integer, java.lang.String> create = android.util.Pair.create(java.lang.Integer.valueOf(i), str);
                if (z) {
                    this.mPackagesWithBindWidgetPermission.add(create);
                } else {
                    this.mPackagesWithBindWidgetPermission.remove(create);
                }
                saveGroupStateAsync(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.content.IntentSender createAppWidgetConfigIntentSender(java.lang.String str, int i, int i2) {
        android.content.IntentSender intentSender;
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    throw new java.lang.IllegalArgumentException("Bad widget id " + i);
                }
                com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = lookupWidgetLocked.provider;
                if (provider == null) {
                    throw new java.lang.IllegalArgumentException("Widget not bound " + i);
                }
                android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_CONFIGURE");
                intent.putExtra("appWidgetId", i);
                intent.setComponent(provider.getInfoLocked(this.mContext).configure);
                intent.setFlags(i2 & (-196));
                android.app.ActivityOptions pendingIntentCreatorBackgroundActivityStartMode = android.app.ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    intentSender = android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1409286144, pendingIntentCreatorBackgroundActivityStartMode.toBundle(), new android.os.UserHandle(provider.getUserId())).getIntentSender();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return intentSender;
    }

    public boolean bindAppWidgetId(java.lang.String str, int i, int i2, android.content.ComponentName componentName, android.os.Bundle bundle) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        if (!this.mSecurityPolicy.isEnabledGroupProfile(i2) || !this.mSecurityPolicy.isProviderInCallerOrInProfileAndWhitelListed(componentName.getPackageName(), i2)) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                if (!this.mSecurityPolicy.hasCallerBindPermissionOrBindWhiteListedLocked(str)) {
                    return false;
                }
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    android.util.Slog.e(TAG, "Bad widget id " + i);
                    return false;
                }
                if (lookupWidgetLocked.provider != null) {
                    android.util.Slog.e(TAG, "Widget id " + i + " already bound to: " + lookupWidgetLocked.provider.id);
                    return false;
                }
                int uidForPackage = getUidForPackage(componentName.getPackageName(), i2);
                if (uidForPackage < 0) {
                    android.util.Slog.e(TAG, "Package " + componentName.getPackageName() + " not installed  for profile " + i2);
                    return false;
                }
                com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(uidForPackage, componentName));
                if (lookupProviderLocked == null) {
                    android.util.Slog.e(TAG, "No widget provider " + componentName + " for profile " + i2);
                    return false;
                }
                if (lookupProviderLocked.zombie) {
                    android.util.Slog.e(TAG, "Can't bind to a 3rd party provider in safe mode " + lookupProviderLocked);
                    return false;
                }
                lookupWidgetLocked.provider = lookupProviderLocked;
                lookupWidgetLocked.options = bundle != null ? cloneIfLocalBinder(bundle) : new android.os.Bundle();
                if (!lookupWidgetLocked.options.containsKey("appWidgetCategory")) {
                    lookupWidgetLocked.options.putInt("appWidgetCategory", 1);
                }
                lookupProviderLocked.widgets.add(lookupWidgetLocked);
                onWidgetProviderAddedOrChangedLocked(lookupWidgetLocked);
                if (lookupProviderLocked.widgets.size() == 1) {
                    sendEnableAndUpdateIntentLocked(lookupProviderLocked, new int[]{i});
                } else {
                    sendUpdateIntentLocked(lookupProviderLocked, new int[]{i}, true);
                }
                registerForBroadcastsLocked(lookupProviderLocked, getWidgetIds(lookupProviderLocked.widgets));
                saveGroupStateAsync(callingUserId);
                android.util.Slog.i(TAG, "Bound widget " + i + " to provider " + lookupProviderLocked.id);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int[] getAppWidgetIds(android.content.ComponentName componentName) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(android.os.Binder.getCallingUid(), componentName));
                if (lookupProviderLocked != null) {
                    return getWidgetIds(lookupProviderLocked.widgets);
                }
                return new int[0];
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int[] getAppWidgetIdsForHost(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Host lookupHostLocked = lookupHostLocked(new com.android.server.appwidget.AppWidgetServiceImpl.HostId(android.os.Binder.getCallingUid(), i, str));
                if (lookupHostLocked != null) {
                    return getWidgetIds(lookupHostLocked.widgets);
                }
                return new int[0];
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean bindRemoteViewsService(java.lang.String str, int i, android.content.Intent intent, android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.app.IServiceConnection iServiceConnection, long j) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    throw new java.lang.IllegalArgumentException("Bad widget id");
                }
                if (lookupWidgetLocked.provider == null) {
                    throw new java.lang.IllegalArgumentException("No provider for widget " + i);
                }
                android.content.ComponentName component = intent.getComponent();
                if (!component.getPackageName().equals(lookupWidgetLocked.provider.id.componentName.getPackageName())) {
                    throw new java.lang.SecurityException("The taget service not in the same package as the widget provider");
                }
                this.mSecurityPolicy.enforceServiceExistsAndRequiresBindRemoteViewsPermission(component, lookupWidgetLocked.provider.getUserId());
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (android.app.ActivityManager.getService().bindService(iApplicationThread, iBinder, intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), iServiceConnection, j & 33554433, this.mContext.getOpPackageName(), lookupWidgetLocked.provider.getUserId()) != 0) {
                        incrementAppWidgetServiceRefCount(i, android.util.Pair.create(java.lang.Integer.valueOf(lookupWidgetLocked.provider.id.uid), new android.content.Intent.FilterComparison(intent)));
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                } catch (android.os.RemoteException e) {
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public void deleteHost(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Host lookupHostLocked = lookupHostLocked(new com.android.server.appwidget.AppWidgetServiceImpl.HostId(android.os.Binder.getCallingUid(), i, str));
                if (lookupHostLocked == null) {
                    return;
                }
                deleteHostLocked(lookupHostLocked);
                saveGroupStateAsync(callingUserId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void deleteAllHosts() {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                boolean z = false;
                for (int size = this.mHosts.size() - 1; size >= 0; size--) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Host host = this.mHosts.get(size);
                    if (host.id.uid == android.os.Binder.getCallingUid()) {
                        deleteHostLocked(host);
                        z = true;
                    }
                }
                if (z) {
                    saveGroupStateAsync(callingUserId);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.appwidget.AppWidgetProviderInfo getAppWidgetInfo(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null || lookupWidgetLocked.provider == null || lookupWidgetLocked.provider.zombie) {
                    return null;
                }
                return cloneIfLocalBinder(lookupWidgetLocked.provider.getInfoLocked(this.mContext));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.widget.RemoteViews getAppWidgetViews(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    return null;
                }
                return cloneIfLocalBinder(lookupWidgetLocked.getEffectiveViewsLocked());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateAppWidgetOptions(java.lang.String str, int i, android.os.Bundle bundle) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                if (lookupWidgetLocked == null) {
                    return;
                }
                lookupWidgetLocked.options.putAll(bundle);
                sendOptionsChangedIntentLocked(lookupWidgetLocked);
                saveGroupStateAsync(callingUserId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.os.Bundle getAppWidgetOptions(java.lang.String str, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                if (lookupWidgetLocked != null && lookupWidgetLocked.options != null) {
                    return cloneIfLocalBinder(lookupWidgetLocked.options);
                }
                return android.os.Bundle.EMPTY;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews) {
        updateAppWidgetIds(str, iArr, remoteViews, false);
    }

    public void partiallyUpdateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews) {
        updateAppWidgetIds(str, iArr, remoteViews, true);
    }

    public void notifyProviderInheritance(@android.annotation.Nullable android.content.ComponentName[] componentNameArr) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (componentNameArr == null) {
            return;
        }
        for (android.content.ComponentName componentName : componentNameArr) {
            if (componentName == null) {
                return;
            }
            this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        }
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                for (android.content.ComponentName componentName2 : componentNameArr) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(android.os.Binder.getCallingUid(), componentName2));
                    if (lookupProviderLocked == null || lookupProviderLocked.info == null) {
                        return;
                    }
                    lookupProviderLocked.info.isExtendedFromAppWidgetProvider = true;
                }
                saveGroupStateAsync(callingUserId);
            } finally {
            }
        }
    }

    public void notifyAppWidgetViewDataChanged(java.lang.String str, int[] iArr, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        if (iArr == null || iArr.length == 0) {
            return;
        }
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                for (int i2 : iArr) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i2, android.os.Binder.getCallingUid(), str);
                    if (lookupWidgetLocked != null) {
                        scheduleNotifyAppWidgetViewDataChanged(lookupWidgetLocked, i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateAppWidgetProvider(android.content.ComponentName componentName, android.widget.RemoteViews remoteViews) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId = new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(android.os.Binder.getCallingUid(), componentName);
                com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked == null) {
                    android.util.Slog.w(TAG, "Provider doesn't exist " + providerId);
                    return;
                }
                java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.Widget> arrayList = lookupProviderLocked.widgets;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    updateAppWidgetInstanceLocked(arrayList.get(i), remoteViews, false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateAppWidgetProviderInfo(android.content.ComponentName componentName, java.lang.String str) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId = new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(android.os.Binder.getCallingUid(), componentName);
                com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked == null) {
                    throw new java.lang.IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                if (java.util.Objects.equals(lookupProviderLocked.infoTag, str)) {
                    return;
                }
                java.lang.String str2 = str == null ? "android.appwidget.provider" : str;
                android.appwidget.AppWidgetProviderInfo parseAppWidgetProviderInfo = parseAppWidgetProviderInfo(this.mContext, providerId, lookupProviderLocked.getPartialInfoLocked().providerInfo, str2);
                if (parseAppWidgetProviderInfo == null) {
                    throw new java.lang.IllegalArgumentException("Unable to parse " + str2 + " meta-data to a valid AppWidget provider");
                }
                lookupProviderLocked.setInfoLocked(parseAppWidgetProviderInfo);
                lookupProviderLocked.infoTag = str;
                int size = lookupProviderLocked.widgets.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = lookupProviderLocked.widgets.get(i);
                    scheduleNotifyProviderChangedLocked(widget);
                    updateAppWidgetInstanceLocked(widget, widget.views, false);
                }
                saveGroupStateAsync(callingUserId);
                scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isRequestPinAppWidgetSupported() {
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.isCallerInstantAppLocked()) {
                    android.util.Slog.w(TAG, "Instant uid " + android.os.Binder.getCallingUid() + " query information about app widgets");
                    return false;
                }
                return ((android.content.pm.ShortcutServiceInternal) com.android.server.LocalServices.getService(android.content.pm.ShortcutServiceInternal.class)).isRequestPinItemSupported(android.os.UserHandle.getCallingUserId(), 2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean requestPinAppWidget(java.lang.String str, android.content.ComponentName componentName, android.os.Bundle bundle, android.content.IntentSender intentSender) {
        com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId;
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(userId);
                java.lang.String packageName = componentName.getPackageName();
                if (!this.mPackageManagerInternal.isSameApp(packageName, callingUid, userId)) {
                    if (!injectHasAccessWidgetsPermission(android.os.Binder.getCallingPid(), callingUid)) {
                        return false;
                    }
                    providerId = new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(this.mPackageManagerInternal.getPackageUid(packageName, 0L, userId), componentName);
                } else {
                    providerId = new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(callingUid, componentName);
                }
                com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked == null || lookupProviderLocked.zombie) {
                    return false;
                }
                android.appwidget.AppWidgetProviderInfo infoLocked = lookupProviderLocked.getInfoLocked(this.mContext);
                if ((infoLocked.widgetCategory & 1) == 0) {
                    return false;
                }
                return ((android.content.pm.ShortcutServiceInternal) com.android.server.LocalServices.getService(android.content.pm.ShortcutServiceInternal.class)).requestPinAppWidget(str, infoLocked, bundle, intentSender, userId);
            } finally {
            }
        }
    }

    private boolean injectHasAccessWidgetsPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.CLEAR_APP_USER_DATA", i, i2) == 0;
    }

    public android.content.pm.ParceledListSlice<android.appwidget.AppWidgetProviderInfo> getInstalledProvidersForProfile(int i, int i2, java.lang.String str) {
        int identifier;
        int callingUserId = android.os.UserHandle.getCallingUserId();
        int callingUid = android.os.Binder.getCallingUid();
        if (!this.mSecurityPolicy.isEnabledGroupProfile(i2)) {
            return null;
        }
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.isCallerInstantAppLocked()) {
                    android.util.Slog.w(TAG, "Instant uid " + callingUid + " cannot access widget providers");
                    return android.content.pm.ParceledListSlice.emptyList();
                }
                ensureGroupStateLoadedLocked(callingUserId);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int size = this.mProviders.size();
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i3);
                    java.lang.String packageName = provider.id.componentName.getPackageName();
                    boolean z = str == null || packageName.equals(str);
                    if (!provider.zombie && z) {
                        android.appwidget.AppWidgetProviderInfo infoLocked = provider.getInfoLocked(this.mContext);
                        if ((infoLocked.widgetCategory & i) != 0 && (identifier = infoLocked.getProfile().getIdentifier()) == i2 && this.mSecurityPolicy.isProviderInCallerOrInProfileAndWhitelListed(packageName, identifier) && !this.mPackageManagerInternal.filterAppAccess(packageName, callingUid, i2)) {
                            arrayList.add(cloneIfLocalBinder(infoLocked));
                        }
                    }
                }
                return new android.content.pm.ParceledListSlice<>(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateAppWidgetIds(java.lang.String str, int[] iArr, android.widget.RemoteViews remoteViews, boolean z) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (iArr == null || iArr.length == 0) {
            return;
        }
        this.mSecurityPolicy.enforceCallFromPackage(str);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                for (int i : iArr) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, android.os.Binder.getCallingUid(), str);
                    if (lookupWidgetLocked != null) {
                        updateAppWidgetInstanceLocked(lookupWidgetLocked, remoteViews, z);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int incrementAndGetAppWidgetIdLocked(int i) {
        int peekNextAppWidgetIdLocked = peekNextAppWidgetIdLocked(i) + 1;
        this.mNextAppWidgetIds.put(i, peekNextAppWidgetIdLocked);
        return peekNextAppWidgetIdLocked;
    }

    private void setMinAppWidgetIdLocked(int i, int i2) {
        if (peekNextAppWidgetIdLocked(i) < i2) {
            this.mNextAppWidgetIds.put(i, i2);
        }
    }

    private int peekNextAppWidgetIdLocked(int i) {
        if (this.mNextAppWidgetIds.indexOfKey(i) < 0) {
            return 1;
        }
        return this.mNextAppWidgetIds.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.appwidget.AppWidgetServiceImpl.Host lookupOrAddHostLocked(com.android.server.appwidget.AppWidgetServiceImpl.HostId hostId) {
        com.android.server.appwidget.AppWidgetServiceImpl.Host lookupHostLocked = lookupHostLocked(hostId);
        if (lookupHostLocked != null) {
            return lookupHostLocked;
        }
        com.android.server.appwidget.AppWidgetServiceImpl.Host host = new com.android.server.appwidget.AppWidgetServiceImpl.Host();
        host.id = hostId;
        this.mHosts.add(host);
        return host;
    }

    private void deleteHostLocked(com.android.server.appwidget.AppWidgetServiceImpl.Host host) {
        for (int size = host.widgets.size() - 1; size >= 0; size--) {
            deleteAppWidgetLocked(host.widgets.remove(size));
        }
        this.mHosts.remove(host);
        host.callbacks = null;
    }

    private void deleteAppWidgetLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        decrementAppWidgetServiceRefCount(widget);
        com.android.server.appwidget.AppWidgetServiceImpl.Host host = widget.host;
        host.widgets.remove(widget);
        pruneHostLocked(host);
        removeWidgetLocked(widget);
        com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = widget.provider;
        if (provider != null) {
            provider.widgets.remove(widget);
            if (!provider.zombie) {
                sendDeletedIntentLocked(widget);
                if (provider.widgets.isEmpty()) {
                    cancelBroadcastsLocked(provider);
                    sendDisabledIntentLocked(provider);
                }
            }
        }
    }

    private void cancelBroadcastsLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider) {
        if (provider.broadcast != null) {
            final android.app.PendingIntent pendingIntent = provider.broadcast;
            this.mSaveStateHandler.post(new java.lang.Runnable() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.appwidget.AppWidgetServiceImpl.this.lambda$cancelBroadcastsLocked$0(pendingIntent);
                }
            });
            provider.broadcast = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelBroadcastsLocked$0(android.app.PendingIntent pendingIntent) {
        this.mAlarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private void destroyRemoteViewsService(final android.content.Intent intent, com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        android.content.ServiceConnection serviceConnection = new android.content.ServiceConnection() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                try {
                    com.android.internal.widget.IRemoteViewsFactory.Stub.asInterface(iBinder).onDestroy(intent);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.appwidget.AppWidgetServiceImpl.TAG, "Error calling remove view factory", e);
                }
                com.android.server.appwidget.AppWidgetServiceImpl.this.mContext.unbindService(this);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
            }
        };
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.bindServiceAsUser(intent, serviceConnection, 33554433, widget.provider.id.getProfile());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void incrementAppWidgetServiceRefCount(int i, android.util.Pair<java.lang.Integer, android.content.Intent.FilterComparison> pair) {
        java.util.HashSet<java.lang.Integer> hashSet;
        if (this.mRemoteViewsServicesAppWidgets.containsKey(pair)) {
            hashSet = this.mRemoteViewsServicesAppWidgets.get(pair);
        } else {
            java.util.HashSet<java.lang.Integer> hashSet2 = new java.util.HashSet<>();
            this.mRemoteViewsServicesAppWidgets.put(pair, hashSet2);
            hashSet = hashSet2;
        }
        hashSet.add(java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decrementAppWidgetServiceRefCount(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        java.util.Iterator<android.util.Pair<java.lang.Integer, android.content.Intent.FilterComparison>> it = this.mRemoteViewsServicesAppWidgets.keySet().iterator();
        while (it.hasNext()) {
            android.util.Pair<java.lang.Integer, android.content.Intent.FilterComparison> next = it.next();
            java.util.HashSet<java.lang.Integer> hashSet = this.mRemoteViewsServicesAppWidgets.get(next);
            if (hashSet.remove(java.lang.Integer.valueOf(widget.appWidgetId)) && hashSet.isEmpty()) {
                destroyRemoteViewsService(((android.content.Intent.FilterComparison) next.second).getIntent(), widget);
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveGroupStateAsync(int i) {
        if (android.appwidget.flags.Flags.removeAppWidgetServiceIoFromCriticalPath()) {
            this.mSaveStateHandler.removeMessages(i);
            this.mSaveStateHandler.sendEmptyMessage(i);
        } else {
            this.mSaveStateHandler.post(new com.android.server.appwidget.AppWidgetServiceImpl.SaveStateRunnable(i));
        }
    }

    private void updateAppWidgetInstanceLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget, android.widget.RemoteViews remoteViews, boolean z) {
        int estimateMemoryUsage;
        if (widget != null && widget.provider != null && !widget.provider.zombie && !widget.host.zombie) {
            if (z && widget.views != null) {
                widget.views.mergeRemoteViews(remoteViews);
            } else {
                widget.views = remoteViews;
            }
            if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != 1000 && widget.views != null && (estimateMemoryUsage = widget.views.estimateMemoryUsage()) > this.mMaxWidgetBitmapMemory) {
                widget.views = null;
                throw new java.lang.IllegalArgumentException("RemoteViews for widget update exceeds maximum bitmap memory usage (used: " + estimateMemoryUsage + ", max: " + this.mMaxWidgetBitmapMemory + ")");
            }
            scheduleNotifyUpdateAppWidgetLocked(widget, widget.getEffectiveViewsLocked());
        }
    }

    private void scheduleNotifyAppWidgetViewDataChanged(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget, int i) {
        if (i == 0 || i == 1) {
            return;
        }
        long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
        if (widget != null) {
            widget.updateSequenceNos.put(i, incrementAndGet);
        }
        if (widget == null || widget.host == null || widget.host.zombie || widget.host.callbacks == null || widget.provider == null || widget.provider.zombie) {
            return;
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = widget.host;
        obtain.arg2 = widget.host.callbacks;
        obtain.arg3 = java.lang.Long.valueOf(incrementAndGet);
        obtain.argi1 = widget.appWidgetId;
        obtain.argi2 = i;
        this.mCallbackHandler.obtainMessage(4, obtain).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyAppWidgetViewDataChanged(com.android.server.appwidget.AppWidgetServiceImpl.Host host, com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost, int i, int i2, long j) {
        try {
            iAppWidgetHost.viewDataChanged(i, i2);
            host.lastWidgetUpdateSequenceNo = j;
        } catch (android.os.RemoteException e) {
            iAppWidgetHost = null;
        }
        synchronized (this.mLock) {
            if (iAppWidgetHost == null) {
                try {
                    host.callbacks = null;
                    for (android.util.Pair<java.lang.Integer, android.content.Intent.FilterComparison> pair : this.mRemoteViewsServicesAppWidgets.keySet()) {
                        if (this.mRemoteViewsServicesAppWidgets.get(pair).contains(java.lang.Integer.valueOf(i))) {
                            bindService(((android.content.Intent.FilterComparison) pair.second).getIntent(), new android.content.ServiceConnection() { // from class: com.android.server.appwidget.AppWidgetServiceImpl.3
                                @Override // android.content.ServiceConnection
                                public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                                    try {
                                        com.android.internal.widget.IRemoteViewsFactory.Stub.asInterface(iBinder).onDataSetChangedAsync();
                                    } catch (android.os.RemoteException e2) {
                                        android.util.Slog.e(com.android.server.appwidget.AppWidgetServiceImpl.TAG, "Error calling onDataSetChangedAsync()", e2);
                                    }
                                    com.android.server.appwidget.AppWidgetServiceImpl.this.mContext.unbindService(this);
                                }

                                @Override // android.content.ServiceConnection
                                public void onServiceDisconnected(android.content.ComponentName componentName) {
                                }
                            }, new android.os.UserHandle(android.os.UserHandle.getUserId(((java.lang.Integer) pair.first).intValue())));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void scheduleNotifyUpdateAppWidgetLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget, android.widget.RemoteViews remoteViews) {
        long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
        if (widget != null) {
            if (widget.trackingUpdate) {
                widget.trackingUpdate = false;
                android.util.Log.i(TAG, "Widget update received " + widget.toString());
                android.os.Trace.asyncTraceEnd(64L, "appwidget update-intent " + widget.provider.id.toString(), widget.appWidgetId);
            }
            widget.updateSequenceNos.put(0, incrementAndGet);
        }
        if (widget == null || widget.provider == null || widget.provider.zombie || widget.host.callbacks == null || widget.host.zombie) {
            return;
        }
        if (remoteViews != null) {
            android.widget.RemoteViews remoteViews2 = new android.widget.RemoteViews(remoteViews);
            remoteViews2.setProviderInstanceId(incrementAndGet);
            remoteViews = remoteViews2;
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = widget.host;
        obtain.arg2 = widget.host.callbacks;
        obtain.arg3 = remoteViews;
        obtain.arg4 = java.lang.Long.valueOf(incrementAndGet);
        obtain.argi1 = widget.appWidgetId;
        this.mCallbackHandler.obtainMessage(1, obtain).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyUpdateAppWidget(com.android.server.appwidget.AppWidgetServiceImpl.Host host, com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost, int i, android.widget.RemoteViews remoteViews, long j) {
        try {
            iAppWidgetHost.updateAppWidget(i, remoteViews);
            host.lastWidgetUpdateSequenceNo = j;
        } catch (android.os.RemoteException e) {
            synchronized (this.mLock) {
                android.util.Slog.e(TAG, "Widget host dead: " + host.id, e);
                host.callbacks = null;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void scheduleNotifyProviderChangedLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
        if (widget != null) {
            widget.updateSequenceNos.clear();
            widget.updateSequenceNos.append(1, incrementAndGet);
        }
        if (widget == null || widget.provider == null || widget.provider.zombie || widget.host.callbacks == null || widget.host.zombie) {
            return;
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = widget.host;
        obtain.arg2 = widget.host.callbacks;
        obtain.arg3 = widget.provider.getInfoLocked(this.mContext);
        obtain.arg4 = java.lang.Long.valueOf(incrementAndGet);
        obtain.argi1 = widget.appWidgetId;
        this.mCallbackHandler.obtainMessage(2, obtain).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyProviderChanged(com.android.server.appwidget.AppWidgetServiceImpl.Host host, com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost, int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, long j) {
        try {
            iAppWidgetHost.providerChanged(i, appWidgetProviderInfo);
            host.lastWidgetUpdateSequenceNo = j;
        } catch (android.os.RemoteException e) {
            synchronized (this.mLock) {
                android.util.Slog.e(TAG, "Widget host dead: " + host.id, e);
                host.callbacks = null;
            }
        }
    }

    private void scheduleNotifyAppWidgetRemovedLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        long incrementAndGet = UPDATE_COUNTER.incrementAndGet();
        if (widget != null) {
            if (widget.trackingUpdate) {
                widget.trackingUpdate = false;
                android.util.Log.i(TAG, "Widget removed " + widget.toString());
                android.os.Trace.asyncTraceEnd(64L, "appwidget update-intent " + widget.provider.id.toString(), widget.appWidgetId);
            }
            widget.updateSequenceNos.clear();
        }
        if (widget == null || widget.provider == null || widget.provider.zombie || widget.host.callbacks == null || widget.host.zombie) {
            return;
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = widget.host;
        obtain.arg2 = widget.host.callbacks;
        obtain.arg3 = java.lang.Long.valueOf(incrementAndGet);
        obtain.argi1 = widget.appWidgetId;
        this.mCallbackHandler.obtainMessage(5, obtain).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyAppWidgetRemoved(com.android.server.appwidget.AppWidgetServiceImpl.Host host, com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost, int i, long j) {
        try {
            iAppWidgetHost.appWidgetRemoved(i);
            host.lastWidgetUpdateSequenceNo = j;
        } catch (android.os.RemoteException e) {
            synchronized (this.mLock) {
                android.util.Slog.e(TAG, "Widget host dead: " + host.id, e);
                host.callbacks = null;
            }
        }
    }

    private void scheduleNotifyGroupHostsForProvidersChangedLocked(int i) {
        int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
        for (int size = this.mHosts.size() - 1; size >= 0; size--) {
            com.android.server.appwidget.AppWidgetServiceImpl.Host host = this.mHosts.get(size);
            int length = enabledGroupProfileIds.length;
            boolean z = false;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (host.getUserId() != enabledGroupProfileIds[i2]) {
                    i2++;
                } else {
                    z = true;
                    break;
                }
            }
            if (z && host != null && !host.zombie && host.callbacks != null) {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = host;
                obtain.arg2 = host.callbacks;
                this.mCallbackHandler.obtainMessage(3, obtain).sendToTarget();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyProvidersChanged(com.android.server.appwidget.AppWidgetServiceImpl.Host host, com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost) {
        try {
            iAppWidgetHost.providersChanged();
        } catch (android.os.RemoteException e) {
            synchronized (this.mLock) {
                android.util.Slog.e(TAG, "Widget host dead: " + host.id, e);
                host.callbacks = null;
            }
        }
    }

    private static boolean isLocalBinder() {
        return android.os.Process.myPid() == android.os.Binder.getCallingPid();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.widget.RemoteViews cloneIfLocalBinder(android.widget.RemoteViews remoteViews) {
        if (isLocalBinder() && remoteViews != null) {
            return remoteViews.clone();
        }
        return remoteViews;
    }

    private static android.appwidget.AppWidgetProviderInfo cloneIfLocalBinder(android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
        if (isLocalBinder() && appWidgetProviderInfo != null) {
            return appWidgetProviderInfo.clone();
        }
        return appWidgetProviderInfo;
    }

    private static android.os.Bundle cloneIfLocalBinder(android.os.Bundle bundle) {
        if (isLocalBinder() && bundle != null) {
            return (android.os.Bundle) bundle.clone();
        }
        return bundle;
    }

    private com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked(int i, int i2, java.lang.String str) {
        int size = this.mWidgets.size();
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = this.mWidgets.get(i3);
            if (widget.appWidgetId == i && this.mSecurityPolicy.canAccessAppWidget(widget, i2, str)) {
                return widget;
            }
        }
        return null;
    }

    private com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked(com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId) {
        int size = this.mProviders.size();
        for (int i = 0; i < size; i++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i);
            if (provider.id.equals(providerId)) {
                return provider;
            }
        }
        return null;
    }

    private com.android.server.appwidget.AppWidgetServiceImpl.Host lookupHostLocked(com.android.server.appwidget.AppWidgetServiceImpl.HostId hostId) {
        int size = this.mHosts.size();
        for (int i = 0; i < size; i++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Host host = this.mHosts.get(i);
            if (host.id.equals(hostId)) {
                return host;
            }
        }
        return null;
    }

    private void pruneHostLocked(com.android.server.appwidget.AppWidgetServiceImpl.Host host) {
        if (host.widgets.size() == 0 && host.callbacks == null) {
            this.mHosts.remove(host);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void loadGroupWidgetProvidersLocked(int[] iArr) {
        android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_UPDATE");
        java.util.ArrayList arrayList = null;
        for (int i : iArr) {
            java.util.List<android.content.pm.ResolveInfo> queryIntentReceivers = queryIntentReceivers(intent, i);
            if (queryIntentReceivers != null && !queryIntentReceivers.isEmpty()) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.addAll(queryIntentReceivers);
            }
        }
        int size = arrayList == null ? 0 : arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            addProviderLocked((android.content.pm.ResolveInfo) arrayList.get(i2));
        }
    }

    private boolean addProviderLocked(android.content.pm.ResolveInfo resolveInfo) {
        if ((resolveInfo.activityInfo.applicationInfo.flags & 262144) != 0) {
            return false;
        }
        android.content.ComponentName componentName = new android.content.ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        byte b = 0;
        byte b2 = 0;
        com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId = new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(resolveInfo.activityInfo.applicationInfo.uid, componentName);
        com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(providerId);
        if (lookupProviderLocked == null) {
            lookupProviderLocked = lookupProviderLocked(new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(-1, componentName));
        }
        android.appwidget.AppWidgetProviderInfo createPartialProviderInfo = createPartialProviderInfo(providerId, resolveInfo, lookupProviderLocked);
        if ((android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.disablePrivateSpaceItemsOnHome() && createPartialProviderInfo != null && this.mUserManager.getUserProperties(createPartialProviderInfo.getProfile()).areItemsRestrictedOnHomeScreen()) || createPartialProviderInfo == null) {
            return false;
        }
        if (lookupProviderLocked != null) {
            if (lookupProviderLocked.zombie && !this.mSafeMode) {
                lookupProviderLocked.id = providerId;
                lookupProviderLocked.zombie = false;
                lookupProviderLocked.setPartialInfoLocked(createPartialProviderInfo);
                return true;
            }
            return true;
        }
        com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = new com.android.server.appwidget.AppWidgetServiceImpl.Provider();
        provider.id = providerId;
        provider.setPartialInfoLocked(createPartialProviderInfo);
        this.mProviders.add(provider);
        return true;
    }

    private void deleteWidgetsLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, int i) {
        for (int size = provider.widgets.size() - 1; size >= 0; size--) {
            com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = provider.widgets.get(size);
            if (i == -1 || i == widget.host.getUserId()) {
                provider.widgets.remove(size);
                updateAppWidgetInstanceLocked(widget, null, false);
                widget.host.widgets.remove(widget);
                removeWidgetLocked(widget);
                widget.provider = null;
                pruneHostLocked(widget.host);
                widget.host = null;
            }
        }
    }

    private void deleteProviderLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider) {
        deleteWidgetsLocked(provider, -1);
        this.mProviders.remove(provider);
        cancelBroadcastsLocked(provider);
    }

    private void sendEnableAndUpdateIntentLocked(@android.annotation.NonNull com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, int[] iArr) {
        if (!(this.mIsCombinedBroadcastEnabled && provider.info != null && provider.info.isExtendedFromAppWidgetProvider)) {
            sendEnableIntentLocked(provider);
            sendUpdateIntentLocked(provider, iArr, true);
        } else {
            android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_ENABLE_AND_UPDATE");
            intent.putExtra("appWidgetIds", iArr);
            intent.setComponent(provider.id.componentName);
            sendBroadcastAsUser(intent, provider.id.getProfile(), true);
        }
    }

    private void sendEnableIntentLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider) {
        android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_ENABLED");
        intent.setComponent(provider.id.componentName);
        sendBroadcastAsUser(intent, provider.id.getProfile(), true);
    }

    private void sendUpdateIntentLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, int[] iArr, boolean z) {
        android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra("appWidgetIds", iArr);
        intent.setComponent(provider.id.componentName);
        sendBroadcastAsUser(intent, provider.id.getProfile(), z);
    }

    private void sendDeletedIntentLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_DELETED");
        intent.setComponent(widget.provider.id.componentName);
        intent.putExtra("appWidgetId", widget.appWidgetId);
        sendBroadcastAsUser(intent, widget.provider.id.getProfile(), false);
    }

    private void sendDisabledIntentLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider) {
        android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_DISABLED");
        intent.setComponent(provider.id.componentName);
        sendBroadcastAsUser(intent, provider.id.getProfile(), false);
    }

    public void sendOptionsChangedIntentLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_UPDATE_OPTIONS");
        intent.setComponent(widget.provider.id.componentName);
        intent.putExtra("appWidgetId", widget.appWidgetId);
        intent.putExtra("appWidgetOptions", widget.options);
        sendBroadcastAsUser(intent, widget.provider.id.getProfile(), true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void registerForBroadcastsLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, int[] iArr) {
        android.appwidget.AppWidgetProviderInfo infoLocked = provider.getInfoLocked(this.mContext);
        if (infoLocked.updatePeriodMillis > 0) {
            boolean z = provider.broadcast != null;
            android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_UPDATE");
            intent.putExtra("appWidgetIds", iArr);
            intent.setComponent(infoLocked.provider);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                provider.broadcast = android.app.PendingIntent.getBroadcastAsUser(this.mContext, 1, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, infoLocked.getProfile());
                if (!z) {
                    final long max = java.lang.Math.max(infoLocked.updatePeriodMillis, MIN_UPDATE_PERIOD);
                    final android.app.PendingIntent pendingIntent = provider.broadcast;
                    this.mSaveStateHandler.post(new java.lang.Runnable() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.appwidget.AppWidgetServiceImpl.this.lambda$registerForBroadcastsLocked$1(max, pendingIntent);
                        }
                    });
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerForBroadcastsLocked$1(long j, android.app.PendingIntent pendingIntent) {
        this.mAlarmManager.setInexactRepeating(2, android.os.SystemClock.elapsedRealtime() + j, j, pendingIntent);
    }

    private static int[] getWidgetIds(java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.Widget> arrayList) {
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = arrayList.get(i).appWidgetId;
        }
        return iArr;
    }

    private static void dumpProviderLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, int i, java.io.PrintWriter printWriter) {
        android.appwidget.AppWidgetProviderInfo partialInfoLocked = provider.getPartialInfoLocked();
        printWriter.print("  [");
        printWriter.print(i);
        printWriter.print("] provider ");
        printWriter.println(provider.id);
        printWriter.print("    min=(");
        printWriter.print(partialInfoLocked.minWidth);
        printWriter.print("x");
        printWriter.print(partialInfoLocked.minHeight);
        printWriter.print(")   minResize=(");
        printWriter.print(partialInfoLocked.minResizeWidth);
        printWriter.print("x");
        printWriter.print(partialInfoLocked.minResizeHeight);
        printWriter.print(") updatePeriodMillis=");
        printWriter.print(partialInfoLocked.updatePeriodMillis);
        printWriter.print(" resizeMode=");
        printWriter.print(partialInfoLocked.resizeMode);
        printWriter.print(" widgetCategory=");
        printWriter.print(partialInfoLocked.widgetCategory);
        printWriter.print(" autoAdvanceViewId=");
        printWriter.print(partialInfoLocked.autoAdvanceViewId);
        printWriter.print(" initialLayout=#");
        printWriter.print(java.lang.Integer.toHexString(partialInfoLocked.initialLayout));
        printWriter.print(" initialKeyguardLayout=#");
        printWriter.print(java.lang.Integer.toHexString(partialInfoLocked.initialKeyguardLayout));
        printWriter.print("   zombie=");
        printWriter.println(provider.zombie);
    }

    private static void dumpHost(com.android.server.appwidget.AppWidgetServiceImpl.Host host, int i, java.io.PrintWriter printWriter) {
        printWriter.print("  [");
        printWriter.print(i);
        printWriter.print("] hostId=");
        printWriter.println(host.id);
        printWriter.print("    callbacks=");
        printWriter.println(host.callbacks);
        printWriter.print("    widgets.size=");
        printWriter.print(host.widgets.size());
        printWriter.print(" zombie=");
        printWriter.println(host.zombie);
    }

    private static void dumpGrant(android.util.Pair<java.lang.Integer, java.lang.String> pair, int i, java.io.PrintWriter printWriter) {
        printWriter.print("  [");
        printWriter.print(i);
        printWriter.print(']');
        printWriter.print(" user=");
        printWriter.print(pair.first);
        printWriter.print(" package=");
        printWriter.println((java.lang.String) pair.second);
    }

    private static void dumpWidget(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget, int i, java.io.PrintWriter printWriter) {
        printWriter.print("  [");
        printWriter.print(i);
        printWriter.print("] id=");
        printWriter.println(widget.appWidgetId);
        printWriter.print("    host=");
        printWriter.println(widget.host.id);
        if (widget.provider != null) {
            printWriter.print("    provider=");
            printWriter.println(widget.provider.id);
        }
        if (widget.host != null) {
            printWriter.print("    host.callbacks=");
            printWriter.println(widget.host.callbacks);
        }
        if (widget.views != null) {
            printWriter.print("    views=");
            printWriter.println(widget.views);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void serializeProvider(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, boolean z) throws java.io.IOException {
        java.util.Objects.requireNonNull(typedXmlSerializer);
        java.util.Objects.requireNonNull(provider);
        typedXmlSerializer.startTag((java.lang.String) null, "p");
        typedXmlSerializer.attribute((java.lang.String) null, "pkg", provider.id.componentName.getPackageName());
        typedXmlSerializer.attribute((java.lang.String) null, "cl", provider.id.componentName.getClassName());
        typedXmlSerializer.attributeIntHex((java.lang.String) null, "tag", provider.tag);
        if (!android.text.TextUtils.isEmpty(provider.infoTag)) {
            typedXmlSerializer.attribute((java.lang.String) null, "info_tag", provider.infoTag);
        }
        if (z && provider.mInfoParsed) {
            com.android.server.appwidget.AppWidgetXmlUtil.writeAppWidgetProviderInfoLocked(typedXmlSerializer, provider.info);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "p");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void serializeHost(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.appwidget.AppWidgetServiceImpl.Host host) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "h");
        typedXmlSerializer.attribute((java.lang.String) null, "pkg", host.id.packageName);
        typedXmlSerializer.attributeIntHex((java.lang.String) null, "id", host.id.hostId);
        typedXmlSerializer.attributeIntHex((java.lang.String) null, "tag", host.tag);
        typedXmlSerializer.endTag((java.lang.String) null, "h");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void serializeAppWidget(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.appwidget.AppWidgetServiceImpl.Widget widget, boolean z) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "g");
        typedXmlSerializer.attributeIntHex((java.lang.String) null, "id", widget.appWidgetId);
        typedXmlSerializer.attributeIntHex((java.lang.String) null, "rid", widget.restoredId);
        typedXmlSerializer.attributeIntHex((java.lang.String) null, "h", widget.host.tag);
        if (widget.provider != null) {
            typedXmlSerializer.attributeIntHex((java.lang.String) null, "p", widget.provider.tag);
        }
        if (widget.options != null) {
            int i = widget.options.getInt("appWidgetMinWidth");
            int i2 = widget.options.getInt("appWidgetMinHeight");
            int i3 = widget.options.getInt("appWidgetMaxWidth");
            int i4 = widget.options.getInt("appWidgetMaxHeight");
            if (i <= 0) {
                i = 0;
            }
            typedXmlSerializer.attributeIntHex((java.lang.String) null, "min_width", i);
            if (i2 <= 0) {
                i2 = 0;
            }
            typedXmlSerializer.attributeIntHex((java.lang.String) null, "min_height", i2);
            if (i3 <= 0) {
                i3 = 0;
            }
            typedXmlSerializer.attributeIntHex((java.lang.String) null, "max_width", i3);
            if (i4 <= 0) {
                i4 = 0;
            }
            typedXmlSerializer.attributeIntHex((java.lang.String) null, "max_height", i4);
            typedXmlSerializer.attributeIntHex((java.lang.String) null, "host_category", widget.options.getInt("appWidgetCategory"));
            if (z) {
                typedXmlSerializer.attributeBoolean((java.lang.String) null, "restore_completed", widget.options.getBoolean("appWidgetRestoreCompleted"));
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, "g");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.Bundle parseWidgetIdOptions(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        android.os.Bundle bundle = new android.os.Bundle();
        if (typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "restore_completed", false)) {
            bundle.putBoolean("appWidgetRestoreCompleted", true);
        }
        int attributeIntHex = typedXmlPullParser.getAttributeIntHex((java.lang.String) null, "min_width", -1);
        if (attributeIntHex != -1) {
            bundle.putInt("appWidgetMinWidth", attributeIntHex);
        }
        int attributeIntHex2 = typedXmlPullParser.getAttributeIntHex((java.lang.String) null, "min_height", -1);
        if (attributeIntHex2 != -1) {
            bundle.putInt("appWidgetMinHeight", attributeIntHex2);
        }
        int attributeIntHex3 = typedXmlPullParser.getAttributeIntHex((java.lang.String) null, "max_width", -1);
        if (attributeIntHex3 != -1) {
            bundle.putInt("appWidgetMaxWidth", attributeIntHex3);
        }
        int attributeIntHex4 = typedXmlPullParser.getAttributeIntHex((java.lang.String) null, "max_height", -1);
        if (attributeIntHex4 != -1) {
            bundle.putInt("appWidgetMaxHeight", attributeIntHex4);
        }
        int attributeIntHex5 = typedXmlPullParser.getAttributeIntHex((java.lang.String) null, "host_category", -1);
        if (attributeIntHex5 != -1) {
            bundle.putInt("appWidgetCategory", attributeIntHex5);
        }
        return bundle;
    }

    public java.util.List<java.lang.String> getWidgetParticipants(int i) {
        return this.mBackupRestoreController.getWidgetParticipants(i);
    }

    public byte[] getWidgetState(java.lang.String str, int i) {
        return this.mBackupRestoreController.getWidgetState(str, i);
    }

    public void systemRestoreStarting(int i) {
        this.mBackupRestoreController.systemRestoreStarting(i);
    }

    public void restoreWidgetState(java.lang.String str, byte[] bArr, int i) {
        this.mBackupRestoreController.restoreWidgetState(str, bArr, i);
    }

    public void systemRestoreFinished(int i) {
        this.mBackupRestoreController.systemRestoreFinished(i);
    }

    private android.appwidget.AppWidgetProviderInfo createPartialProviderInfo(com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId, android.content.pm.ResolveInfo resolveInfo, com.android.server.appwidget.AppWidgetServiceImpl.Provider provider) {
        boolean z;
        android.os.Bundle bundle = resolveInfo.activityInfo.metaData;
        if (bundle == null) {
            return null;
        }
        if (provider != null && !android.text.TextUtils.isEmpty(provider.infoTag)) {
            z = bundle.getInt(provider.infoTag) != 0;
        } else {
            z = false;
        }
        if (!(z | (bundle.getInt("android.appwidget.provider") != 0))) {
            return null;
        }
        android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = new android.appwidget.AppWidgetProviderInfo();
        appWidgetProviderInfo.provider = providerId.componentName;
        appWidgetProviderInfo.providerInfo = resolveInfo.activityInfo;
        return appWidgetProviderInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.appwidget.AppWidgetProviderInfo parseAppWidgetProviderInfo(android.content.Context context, com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId, android.content.pm.ActivityInfo activityInfo, java.lang.String str) {
        int next;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            android.content.res.XmlResourceParser loadXmlMetaData = activityInfo.loadXmlMetaData(packageManager, str);
            try {
                if (loadXmlMetaData == null) {
                    android.util.Slog.w(TAG, "No " + str + " meta-data for AppWidget provider '" + providerId + '\'');
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return null;
                }
                android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"appwidget-provider".equals(loadXmlMetaData.getName())) {
                    android.util.Slog.w(TAG, "Meta-data does not start with appwidget-provider tag for AppWidget provider " + providerId.componentName + " for user " + providerId.uid);
                    loadXmlMetaData.close();
                    return null;
                }
                android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = new android.appwidget.AppWidgetProviderInfo();
                appWidgetProviderInfo.provider = providerId.componentName;
                appWidgetProviderInfo.providerInfo = activityInfo;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(packageManager.getApplicationInfoAsUser(activityInfo.packageName, 0, android.os.UserHandle.getUserId(providerId.uid)));
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.AppWidgetProviderInfo);
                    android.util.TypedValue peekValue = obtainAttributes.peekValue(1);
                    appWidgetProviderInfo.minWidth = peekValue != null ? peekValue.data : 0;
                    android.util.TypedValue peekValue2 = obtainAttributes.peekValue(2);
                    appWidgetProviderInfo.minHeight = peekValue2 != null ? peekValue2.data : 0;
                    android.util.TypedValue peekValue3 = obtainAttributes.peekValue(9);
                    appWidgetProviderInfo.minResizeWidth = peekValue3 != null ? peekValue3.data : appWidgetProviderInfo.minWidth;
                    android.util.TypedValue peekValue4 = obtainAttributes.peekValue(10);
                    appWidgetProviderInfo.minResizeHeight = peekValue4 != null ? peekValue4.data : appWidgetProviderInfo.minHeight;
                    android.util.TypedValue peekValue5 = obtainAttributes.peekValue(15);
                    appWidgetProviderInfo.maxResizeWidth = peekValue5 != null ? peekValue5.data : 0;
                    android.util.TypedValue peekValue6 = obtainAttributes.peekValue(16);
                    appWidgetProviderInfo.maxResizeHeight = peekValue6 != null ? peekValue6.data : 0;
                    appWidgetProviderInfo.targetCellWidth = obtainAttributes.getInt(17, 0);
                    appWidgetProviderInfo.targetCellHeight = obtainAttributes.getInt(18, 0);
                    appWidgetProviderInfo.updatePeriodMillis = obtainAttributes.getInt(3, 0);
                    appWidgetProviderInfo.initialLayout = obtainAttributes.getResourceId(4, 0);
                    appWidgetProviderInfo.initialKeyguardLayout = obtainAttributes.getResourceId(11, 0);
                    java.lang.String string = obtainAttributes.getString(5);
                    if (string != null) {
                        appWidgetProviderInfo.configure = new android.content.ComponentName(providerId.componentName.getPackageName(), string);
                    }
                    appWidgetProviderInfo.label = activityInfo.loadLabel(packageManager).toString();
                    appWidgetProviderInfo.icon = activityInfo.getIconResource();
                    appWidgetProviderInfo.previewImage = obtainAttributes.getResourceId(6, 0);
                    appWidgetProviderInfo.previewLayout = obtainAttributes.getResourceId(14, 0);
                    appWidgetProviderInfo.autoAdvanceViewId = obtainAttributes.getResourceId(7, -1);
                    appWidgetProviderInfo.resizeMode = obtainAttributes.getInt(8, 0);
                    appWidgetProviderInfo.widgetCategory = obtainAttributes.getInt(12, 1);
                    appWidgetProviderInfo.widgetFeatures = obtainAttributes.getInt(13, 0);
                    appWidgetProviderInfo.descriptionRes = obtainAttributes.getResourceId(0, 0);
                    obtainAttributes.recycle();
                    loadXmlMetaData.close();
                    return appWidgetProviderInfo;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                if (loadXmlMetaData != null) {
                    try {
                        loadXmlMetaData.close();
                    } catch (java.lang.Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.w(TAG, "XML parsing failed for AppWidget provider " + providerId.componentName + " for user " + providerId.uid, e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getUidForPackage(java.lang.String str, int i) {
        android.content.pm.PackageInfo packageInfo;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            packageInfo = this.mPackageManager.getPackageInfo(str, 0L, i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (android.os.RemoteException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            packageInfo = null;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return -1;
        }
        return packageInfo.applicationInfo.uid;
    }

    private android.content.pm.ActivityInfo getProviderInfo(android.content.ComponentName componentName, int i) {
        android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setComponent(componentName);
        java.util.List<android.content.pm.ResolveInfo> queryIntentReceivers = queryIntentReceivers(intent, i);
        if (!queryIntentReceivers.isEmpty()) {
            return queryIntentReceivers.get(0).activityInfo;
        }
        return null;
    }

    private java.util.List<android.content.pm.ResolveInfo> queryIntentReceivers(android.content.Intent intent, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mPackageManager.queryIntentReceivers(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (isProfileWithUnlockedParent(i) ? 269222016 : 268435584) | 1024, i).getList();
        } catch (android.os.RemoteException e) {
            return java.util.Collections.emptyList();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void handleUserUnlocked(int i) {
        if (isProfileWithLockedParent(i)) {
            return;
        }
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            android.util.Slog.w(TAG, "User " + i + " is no longer unlocked - exiting");
            return;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            try {
                android.os.Trace.traceBegin(64L, "appwidget ensure");
                ensureGroupStateLoadedLocked(i);
                android.os.Trace.traceEnd(64L);
                android.os.Trace.traceBegin(64L, "appwidget reload");
                reloadWidgetsMaskedStateForGroup(this.mSecurityPolicy.getGroupParent(i));
                android.os.Trace.traceEnd(64L);
                int size = this.mProviders.size();
                for (int i2 = 0; i2 < size; i2++) {
                    final com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i2);
                    if (provider.getUserId() == i && provider.widgets.size() > 0) {
                        android.os.Trace.traceBegin(64L, "appwidget init " + provider.id.componentName.getPackageName());
                        provider.widgets.forEach(new java.util.function.Consumer() { // from class: com.android.server.appwidget.AppWidgetServiceImpl$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.appwidget.AppWidgetServiceImpl.lambda$handleUserUnlocked$2(com.android.server.appwidget.AppWidgetServiceImpl.Provider.this, (com.android.server.appwidget.AppWidgetServiceImpl.Widget) obj);
                            }
                        });
                        int[] widgetIds = getWidgetIds(provider.widgets);
                        sendEnableAndUpdateIntentLocked(provider, widgetIds);
                        registerForBroadcastsLocked(provider, widgetIds);
                        android.os.Trace.traceEnd(64L);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.util.Slog.i(TAG, "Processing of handleUserUnlocked u" + i + " took " + (android.os.SystemClock.elapsedRealtime() - elapsedRealtime) + " ms");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleUserUnlocked$2(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        widget.trackingUpdate = true;
        android.os.Trace.asyncTraceBegin(64L, "appwidget update-intent " + provider.id.toString(), widget.appWidgetId);
        android.util.Log.i(TAG, "Widget update scheduled on unlock " + widget.toString());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void loadGroupStateLocked(int[] iArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i = 0;
        for (int i2 : iArr) {
            try {
                java.io.FileInputStream openRead = getSavedStateFile(i2).openRead();
                try {
                    i = readProfileStateFromFileLocked(openRead, i2, arrayList);
                    if (openRead != null) {
                        openRead.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed to read state: " + e);
            }
        }
        if (i >= 0) {
            bindLoadedWidgetsLocked(arrayList);
            performUpgradeLocked(i);
            return;
        }
        android.util.Slog.w(TAG, "Failed to read state, clearing widgets and hosts.");
        clearWidgetsLocked();
        this.mHosts.clear();
        int size = this.mProviders.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mProviders.get(i3).widgets.clear();
        }
    }

    private void bindLoadedWidgetsLocked(java.util.List<com.android.server.appwidget.AppWidgetServiceImpl.LoadedWidgetState> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            com.android.server.appwidget.AppWidgetServiceImpl.LoadedWidgetState remove = list.remove(size);
            com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = remove.widget;
            widget.provider = findProviderByTag(remove.providerTag);
            if (widget.provider != null) {
                widget.host = findHostByTag(remove.hostTag);
                if (widget.host != null) {
                    widget.provider.widgets.add(widget);
                    widget.host.widgets.add(widget);
                    addWidgetLocked(widget);
                }
            }
        }
    }

    private com.android.server.appwidget.AppWidgetServiceImpl.Provider findProviderByTag(int i) {
        if (i < 0) {
            return null;
        }
        int size = this.mProviders.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i2);
            if (provider.tag == i) {
                return provider;
            }
        }
        return null;
    }

    private com.android.server.appwidget.AppWidgetServiceImpl.Host findHostByTag(int i) {
        if (i < 0) {
            return null;
        }
        int size = this.mHosts.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Host host = this.mHosts.get(i2);
            if (host.tag == i) {
                return host;
            }
        }
        return null;
    }

    void addWidgetLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        this.mWidgets.add(widget);
        onWidgetProviderAddedOrChangedLocked(widget);
    }

    void onWidgetProviderAddedOrChangedLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        if (widget.provider == null) {
            return;
        }
        int userId = widget.provider.getUserId();
        synchronized (this.mWidgetPackagesLock) {
            try {
                android.util.ArraySet<java.lang.String> arraySet = this.mWidgetPackages.get(userId);
                if (arraySet == null) {
                    android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray = this.mWidgetPackages;
                    android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>();
                    sparseArray.put(userId, arraySet2);
                    arraySet = arraySet2;
                }
                arraySet.add(widget.provider.id.componentName.getPackageName());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (widget.provider.isMaskedLocked()) {
            maskWidgetsViewsLocked(widget.provider, widget);
        } else {
            widget.clearMaskedViewsLocked();
        }
    }

    void removeWidgetLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        this.mWidgets.remove(widget);
        onWidgetRemovedLocked(widget);
        scheduleNotifyAppWidgetRemovedLocked(widget);
    }

    private void onWidgetRemovedLocked(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget) {
        if (widget.provider == null) {
            return;
        }
        int userId = widget.provider.getUserId();
        java.lang.String packageName = widget.provider.id.componentName.getPackageName();
        synchronized (this.mWidgetPackagesLock) {
            try {
                android.util.ArraySet<java.lang.String> arraySet = this.mWidgetPackages.get(userId);
                if (arraySet == null) {
                    return;
                }
                int size = this.mWidgets.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Widget widget2 = this.mWidgets.get(i);
                    if (widget2.provider != null && widget2.provider.getUserId() == userId && packageName.equals(widget2.provider.id.componentName.getPackageName())) {
                        return;
                    }
                }
                arraySet.remove(packageName);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearWidgetsLocked() {
        this.mWidgets.clear();
        onWidgetsClearedLocked();
    }

    private void onWidgetsClearedLocked() {
        synchronized (this.mWidgetPackagesLock) {
            this.mWidgetPackages.clear();
        }
    }

    public boolean isBoundWidgetPackage(java.lang.String str, int i) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Only the system process can call this");
        }
        synchronized (this.mWidgetPackagesLock) {
            try {
                android.util.ArraySet<java.lang.String> arraySet = this.mWidgetPackages.get(i);
                if (arraySet != null) {
                    return arraySet.contains(str);
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<byte[]> saveStateToByteArrayLocked(int i) {
        tagProvidersAndHosts();
        int[] enabledGroupProfileIds = this.mSecurityPolicy.getEnabledGroupProfileIds(i);
        android.util.SparseArray<byte[]> sparseArray = new android.util.SparseArray<>();
        for (int i2 : enabledGroupProfileIds) {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            if (writeProfileStateToStreamLocked(byteArrayOutputStream, i2)) {
                sparseArray.put(i2, byteArrayOutputStream.toByteArray());
            }
        }
        return sparseArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void saveStateLocked(int i) {
        tagProvidersAndHosts();
        for (int i2 : this.mSecurityPolicy.getEnabledGroupProfileIds(i)) {
            android.util.AtomicFile savedStateFile = getSavedStateFile(i2);
            try {
                java.io.FileOutputStream startWrite = savedStateFile.startWrite();
                if (writeProfileStateToStreamLocked(startWrite, i2)) {
                    savedStateFile.finishWrite(startWrite);
                } else {
                    savedStateFile.failWrite(startWrite);
                    android.util.Slog.w(TAG, "Failed to save state, restoring backup.");
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed open state file for write: " + e);
            }
        }
    }

    private void tagProvidersAndHosts() {
        int size = this.mProviders.size();
        for (int i = 0; i < size; i++) {
            this.mProviders.get(i).tag = i;
        }
        int size2 = this.mHosts.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mHosts.get(i2).tag = i2;
        }
    }

    private void clearProvidersAndHostsTagsLocked() {
        int size = this.mProviders.size();
        for (int i = 0; i < size; i++) {
            this.mProviders.get(i).tag = -1;
        }
        int size2 = this.mHosts.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mHosts.get(i2).tag = -1;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean writeProfileStateToStreamLocked(java.io.OutputStream outputStream, int i) {
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, "gs");
            resolveSerializer.attributeInt((java.lang.String) null, "version", 1);
            int size = this.mProviders.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i2);
                if (provider.getUserId() == i) {
                    serializeProvider(resolveSerializer, provider, true);
                }
            }
            int size2 = this.mHosts.size();
            for (int i3 = 0; i3 < size2; i3++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Host host = this.mHosts.get(i3);
                if (host.getUserId() == i) {
                    serializeHost(resolveSerializer, host);
                }
            }
            int size3 = this.mWidgets.size();
            for (int i4 = 0; i4 < size3; i4++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = this.mWidgets.get(i4);
                if (widget.host.getUserId() == i) {
                    serializeAppWidget(resolveSerializer, widget, true);
                }
            }
            java.util.Iterator<android.util.Pair<java.lang.Integer, java.lang.String>> it = this.mPackagesWithBindWidgetPermission.iterator();
            while (it.hasNext()) {
                android.util.Pair<java.lang.Integer, java.lang.String> next = it.next();
                if (((java.lang.Integer) next.first).intValue() == i) {
                    resolveSerializer.startTag((java.lang.String) null, "b");
                    resolveSerializer.attribute((java.lang.String) null, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, (java.lang.String) next.second);
                    resolveSerializer.endTag((java.lang.String) null, "b");
                }
            }
            resolveSerializer.endTag((java.lang.String) null, "gs");
            resolveSerializer.endDocument();
            return true;
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to write state: " + e);
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int readProfileStateFromFileLocked(java.io.FileInputStream fileInputStream, int i, java.util.List<com.android.server.appwidget.AppWidgetServiceImpl.LoadedWidgetState> list) {
        int next;
        int uidForPackage;
        android.content.ComponentName componentName;
        android.content.pm.ActivityInfo providerInfo;
        try {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
            int i2 = -1;
            int i3 = -1;
            int i4 = -1;
            do {
                next = resolvePullParser.next();
                if (next == 2) {
                    java.lang.String name = resolvePullParser.getName();
                    byte b = 0;
                    byte b2 = 0;
                    byte b3 = 0;
                    if ("gs".equals(name)) {
                        i4 = resolvePullParser.getAttributeInt((java.lang.String) null, "version", 0);
                    } else if ("p".equals(name)) {
                        i2++;
                        java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, "pkg");
                        java.lang.String attributeValue2 = resolvePullParser.getAttributeValue((java.lang.String) null, "cl");
                        java.lang.String canonicalPackageName = getCanonicalPackageName(attributeValue, attributeValue2, i);
                        if (canonicalPackageName != null && (uidForPackage = getUidForPackage(canonicalPackageName, i)) >= 0 && (providerInfo = getProviderInfo((componentName = new android.content.ComponentName(canonicalPackageName, attributeValue2)), i)) != null) {
                            com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId = new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(uidForPackage, componentName);
                            com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(providerId);
                            if (lookupProviderLocked == null && this.mSafeMode) {
                                android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = new android.appwidget.AppWidgetProviderInfo();
                                appWidgetProviderInfo.provider = providerId.componentName;
                                appWidgetProviderInfo.providerInfo = providerInfo;
                                com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = new com.android.server.appwidget.AppWidgetServiceImpl.Provider();
                                provider.setPartialInfoLocked(appWidgetProviderInfo);
                                provider.zombie = true;
                                provider.id = providerId;
                                this.mProviders.add(provider);
                                lookupProviderLocked = provider;
                            } else {
                                android.appwidget.AppWidgetProviderInfo readAppWidgetProviderInfoLocked = com.android.server.appwidget.AppWidgetXmlUtil.readAppWidgetProviderInfoLocked(resolvePullParser);
                                if (readAppWidgetProviderInfoLocked != null) {
                                    readAppWidgetProviderInfoLocked.provider = providerId.componentName;
                                    readAppWidgetProviderInfoLocked.providerInfo = providerInfo;
                                    lookupProviderLocked.setInfoLocked(readAppWidgetProviderInfoLocked);
                                }
                            }
                            lookupProviderLocked.tag = resolvePullParser.getAttributeIntHex((java.lang.String) null, "tag", i2);
                            lookupProviderLocked.infoTag = resolvePullParser.getAttributeValue((java.lang.String) null, "info_tag");
                        }
                    } else if ("h".equals(name)) {
                        i3++;
                        com.android.server.appwidget.AppWidgetServiceImpl.Host host = new com.android.server.appwidget.AppWidgetServiceImpl.Host();
                        java.lang.String attributeValue3 = resolvePullParser.getAttributeValue((java.lang.String) null, "pkg");
                        int uidForPackage2 = getUidForPackage(attributeValue3, i);
                        if (uidForPackage2 < 0) {
                            host.zombie = true;
                        }
                        if (!host.zombie || this.mSafeMode) {
                            int attributeIntHex = resolvePullParser.getAttributeIntHex((java.lang.String) null, "id");
                            host.tag = resolvePullParser.getAttributeIntHex((java.lang.String) null, "tag", i3);
                            host.id = new com.android.server.appwidget.AppWidgetServiceImpl.HostId(uidForPackage2, attributeIntHex, attributeValue3);
                            this.mHosts.add(host);
                        }
                    } else if ("b".equals(name)) {
                        java.lang.String attributeValue4 = resolvePullParser.getAttributeValue((java.lang.String) null, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
                        if (getUidForPackage(attributeValue4, i) >= 0) {
                            this.mPackagesWithBindWidgetPermission.add(android.util.Pair.create(java.lang.Integer.valueOf(i), attributeValue4));
                        }
                    } else if ("g".equals(name)) {
                        com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = new com.android.server.appwidget.AppWidgetServiceImpl.Widget();
                        widget.appWidgetId = resolvePullParser.getAttributeIntHex((java.lang.String) null, "id");
                        setMinAppWidgetIdLocked(i, widget.appWidgetId + 1);
                        widget.restoredId = resolvePullParser.getAttributeIntHex((java.lang.String) null, "rid", 0);
                        widget.options = parseWidgetIdOptions(resolvePullParser);
                        list.add(new com.android.server.appwidget.AppWidgetServiceImpl.LoadedWidgetState(widget, resolvePullParser.getAttributeIntHex((java.lang.String) null, "h"), resolvePullParser.getAttributeValue((java.lang.String) null, "p") != null ? resolvePullParser.getAttributeIntHex((java.lang.String) null, "p") : -1));
                    }
                }
            } while (next != 1);
            return i4;
        } catch (java.io.IOException | java.lang.IndexOutOfBoundsException | java.lang.NullPointerException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.w(TAG, "failed parsing " + e);
            return -1;
        }
    }

    private void performUpgradeLocked(int i) {
        int uidForPackage;
        if (i < 1) {
            android.util.Slog.v(TAG, "Upgrading widget database from " + i + " to 1");
        }
        if (i == 0) {
            com.android.server.appwidget.AppWidgetServiceImpl.Host lookupHostLocked = lookupHostLocked(new com.android.server.appwidget.AppWidgetServiceImpl.HostId(android.os.Process.myUid(), KEYGUARD_HOST_ID, "android"));
            if (lookupHostLocked != null && (uidForPackage = getUidForPackage(NEW_KEYGUARD_HOST_PACKAGE, 0)) >= 0) {
                lookupHostLocked.id = new com.android.server.appwidget.AppWidgetServiceImpl.HostId(uidForPackage, KEYGUARD_HOST_ID, NEW_KEYGUARD_HOST_PACKAGE);
            }
            i = 1;
        }
        if (i != 1) {
            throw new java.lang.IllegalStateException("Failed to upgrade widget database");
        }
    }

    private static java.io.File getStateFile(int i) {
        return new java.io.File(android.os.Environment.getUserSystemDirectory(i), STATE_FILENAME);
    }

    private static android.util.AtomicFile getSavedStateFile(int i) {
        java.io.File userSystemDirectory = android.os.Environment.getUserSystemDirectory(i);
        java.io.File stateFile = getStateFile(i);
        if (!stateFile.exists() && i == 0) {
            if (!userSystemDirectory.exists()) {
                userSystemDirectory.mkdirs();
            }
            new java.io.File("/data/system/appwidgets.xml").renameTo(stateFile);
        }
        return new android.util.AtomicFile(stateFile);
    }

    void onUserStopped(int i) {
        synchronized (this.mLock) {
            try {
                int size = this.mWidgets.size() - 1;
                while (true) {
                    boolean z = false;
                    if (size < 0) {
                        break;
                    }
                    com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = this.mWidgets.get(size);
                    boolean z2 = widget.host.getUserId() == i;
                    boolean z3 = widget.provider != null;
                    if (z3 && widget.provider.getUserId() == i) {
                        z = true;
                    }
                    if (z2 && (!z3 || z)) {
                        removeWidgetLocked(widget);
                        widget.host.widgets.remove(widget);
                        widget.host = null;
                        if (z3) {
                            widget.provider.widgets.remove(widget);
                            widget.provider = null;
                        }
                    }
                    size--;
                }
                boolean z4 = false;
                for (int size2 = this.mHosts.size() - 1; size2 >= 0; size2--) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Host host = this.mHosts.get(size2);
                    if (host.getUserId() == i) {
                        z4 |= !host.widgets.isEmpty();
                        deleteHostLocked(host);
                    }
                }
                for (int size3 = this.mPackagesWithBindWidgetPermission.size() - 1; size3 >= 0; size3--) {
                    if (((java.lang.Integer) this.mPackagesWithBindWidgetPermission.valueAt(size3).first).intValue() == i) {
                        this.mPackagesWithBindWidgetPermission.removeAt(size3);
                    }
                }
                int indexOfKey = this.mLoadedUserIds.indexOfKey(i);
                if (indexOfKey >= 0) {
                    this.mLoadedUserIds.removeAt(indexOfKey);
                }
                int indexOfKey2 = this.mNextAppWidgetIds.indexOfKey(i);
                if (indexOfKey2 >= 0) {
                    this.mNextAppWidgetIds.removeAt(indexOfKey2);
                }
                if (z4) {
                    saveGroupStateAsync(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyResourceOverlaysToWidgetsLocked(java.util.Set<java.lang.String> set, int i, boolean z) {
        android.content.pm.ApplicationInfo applicationInfo;
        android.content.pm.ApplicationInfo applicationInfo2;
        int size = this.mProviders.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i2);
            if (provider.getUserId() == i) {
                java.lang.String packageName = provider.id.componentName.getPackageName();
                if (z || set.contains(packageName)) {
                    try {
                        applicationInfo = this.mPackageManager.getApplicationInfo(packageName, 1024L, i);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to retrieve app info for " + packageName + " userId=" + i, e);
                        applicationInfo = null;
                    }
                    if (applicationInfo != null && provider.info != null && provider.info.providerInfo != null && (applicationInfo2 = provider.info.providerInfo.applicationInfo) != null && applicationInfo.sourceDir.equals(applicationInfo2.sourceDir)) {
                        android.content.pm.ApplicationInfo applicationInfo3 = new android.content.pm.ApplicationInfo(applicationInfo2);
                        applicationInfo3.overlayPaths = applicationInfo.overlayPaths == null ? null : (java.lang.String[]) applicationInfo.overlayPaths.clone();
                        applicationInfo3.resourceDirs = applicationInfo.resourceDirs != null ? (java.lang.String[]) applicationInfo.resourceDirs.clone() : null;
                        provider.info.providerInfo.applicationInfo = applicationInfo3;
                        int size2 = provider.widgets.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = provider.widgets.get(i3);
                            if (widget.views != null) {
                                widget.views.updateAppInfo(applicationInfo3);
                            }
                            if (widget.maskedViews != null) {
                                widget.maskedViews.updateAppInfo(applicationInfo3);
                            }
                        }
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean updateProvidersForPackageLocked(java.lang.String str, int i, java.util.Set<com.android.server.appwidget.AppWidgetServiceImpl.ProviderId> set) {
        java.util.HashSet hashSet = new java.util.HashSet();
        android.content.Intent intent = new android.content.Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentReceivers = queryIntentReceivers(intent, i);
        int size = queryIntentReceivers == null ? 0 : queryIntentReceivers.size();
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ResolveInfo resolveInfo = queryIntentReceivers.get(i2);
            android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
            if ((activityInfo.applicationInfo.flags & 262144) == 0 && str.equals(activityInfo.packageName)) {
                com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId = new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(activityInfo.applicationInfo.uid, new android.content.ComponentName(activityInfo.packageName, activityInfo.name));
                com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(providerId);
                if (lookupProviderLocked == null) {
                    if (addProviderLocked(resolveInfo)) {
                        hashSet.add(providerId);
                        z = true;
                    }
                } else {
                    android.appwidget.AppWidgetProviderInfo createPartialProviderInfo = createPartialProviderInfo(providerId, resolveInfo, lookupProviderLocked);
                    if (createPartialProviderInfo != null) {
                        hashSet.add(providerId);
                        lookupProviderLocked.setPartialInfoLocked(createPartialProviderInfo);
                        int size2 = lookupProviderLocked.widgets.size();
                        if (size2 > 0) {
                            int[] widgetIds = getWidgetIds(lookupProviderLocked.widgets);
                            cancelBroadcastsLocked(lookupProviderLocked);
                            registerForBroadcastsLocked(lookupProviderLocked, widgetIds);
                            for (int i3 = 0; i3 < size2; i3++) {
                                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = lookupProviderLocked.widgets.get(i3);
                                widget.views = null;
                                scheduleNotifyProviderChangedLocked(widget);
                            }
                            sendUpdateIntentLocked(lookupProviderLocked, widgetIds, false);
                        }
                    }
                    z = true;
                }
            }
        }
        for (int size3 = this.mProviders.size() - 1; size3 >= 0; size3--) {
            com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(size3);
            if (str.equals(provider.id.componentName.getPackageName()) && provider.getUserId() == i && !hashSet.contains(provider.id)) {
                if (set != null) {
                    set.add(provider.id);
                }
                deleteProviderLocked(provider);
                z = true;
            }
        }
        return z;
    }

    private void removeWidgetsForPackageLocked(java.lang.String str, int i, int i2) {
        int size = this.mProviders.size();
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i3);
            if (str.equals(provider.id.componentName.getPackageName()) && provider.getUserId() == i && provider.widgets.size() > 0) {
                deleteWidgetsLocked(provider, i2);
            }
        }
    }

    private boolean removeProvidersForPackageLocked(java.lang.String str, int i) {
        boolean z = false;
        for (int size = this.mProviders.size() - 1; size >= 0; size--) {
            com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(size);
            if (str.equals(provider.id.componentName.getPackageName()) && provider.getUserId() == i) {
                deleteProviderLocked(provider);
                z = true;
            }
        }
        return z;
    }

    private boolean removeHostsAndProvidersForPackageLocked(java.lang.String str, int i) {
        boolean removeProvidersForPackageLocked = removeProvidersForPackageLocked(str, i);
        for (int size = this.mHosts.size() - 1; size >= 0; size--) {
            com.android.server.appwidget.AppWidgetServiceImpl.Host host = this.mHosts.get(size);
            if (str.equals(host.id.packageName) && host.getUserId() == i) {
                deleteHostLocked(host);
                removeProvidersForPackageLocked = true;
            }
        }
        return removeProvidersForPackageLocked;
    }

    private java.lang.String getCanonicalPackageName(java.lang.String str, java.lang.String str2, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.AppGlobals.getPackageManager().getReceiverInfo(new android.content.ComponentName(str, str2), 0L, i);
            return str;
        } catch (android.os.RemoteException e) {
            java.lang.String[] currentToCanonicalPackageNames = this.mContext.getPackageManager().currentToCanonicalPackageNames(new java.lang.String[]{str});
            if (currentToCanonicalPackageNames != null && currentToCanonicalPackageNames.length > 0) {
                return currentToCanonicalPackageNames[0];
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, userHandle, null, z ? this.mInteractiveBroadcast : null);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void bindService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.os.UserHandle userHandle) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.bindServiceAsUser(intent, serviceConnection, 33554433, userHandle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void unbindService(android.content.ServiceConnection serviceConnection) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.unbindService(serviceConnection);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onCrossProfileWidgetProvidersChanged(int i, java.util.List<java.lang.String> list) {
        int profileParent = this.mSecurityPolicy.getProfileParent(i);
        if (profileParent != i) {
            synchronized (this.mLock) {
                try {
                    android.util.ArraySet arraySet = new android.util.ArraySet();
                    int size = this.mProviders.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i2);
                        if (provider.getUserId() == i) {
                            arraySet.add(provider.id.componentName.getPackageName());
                        }
                    }
                    int size2 = list.size();
                    boolean z = false;
                    for (int i3 = 0; i3 < size2; i3++) {
                        java.lang.String str = list.get(i3);
                        arraySet.remove(str);
                        z |= updateProvidersForPackageLocked(str, i, null);
                    }
                    int size3 = arraySet.size();
                    for (int i4 = 0; i4 < size3; i4++) {
                        removeWidgetsForPackageLocked((java.lang.String) arraySet.valueAt(i4), i, profileParent);
                    }
                    if (z || size3 > 0) {
                        saveGroupStateAsync(i);
                        scheduleNotifyGroupHostsForProvidersChangedLocked(i);
                    }
                } finally {
                }
            }
        }
    }

    private boolean isProfileWithLockedParent(int i) {
        android.content.pm.UserInfo profileParent;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
            if (userInfo != null && userInfo.isProfile() && (profileParent = this.mUserManager.getProfileParent(i)) != null) {
                if (!isUserRunningAndUnlocked(profileParent.getUserHandle().getIdentifier())) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean isProfileWithUnlockedParent(int i) {
        android.content.pm.UserInfo profileParent;
        android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
        if (userInfo != null && userInfo.isProfile() && (profileParent = this.mUserManager.getProfileParent(i)) != null && this.mUserManager.isUserUnlockingOrUnlocked(profileParent.getUserHandle())) {
            return true;
        }
        return false;
    }

    public void noteAppWidgetTapped(java.lang.String str, int i) {
        this.mSecurityPolicy.enforceCallFromPackage(str);
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mActivityManagerInternal.getUidProcessState(callingUid) > 2) {
                return;
            }
            synchronized (this.mLock) {
                com.android.server.appwidget.AppWidgetServiceImpl.Widget lookupWidgetLocked = lookupWidgetLocked(i, callingUid, str);
                if (lookupWidgetLocked == null) {
                    return;
                }
                com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId = lookupWidgetLocked.provider.id;
                java.lang.String packageName = providerId.componentName.getPackageName();
                if (packageName == null) {
                    return;
                }
                android.util.SparseArray sparseArray = new android.util.SparseArray();
                sparseArray.put(providerId.uid, packageName);
                this.mAppOpsManagerInternal.updateAppWidgetVisibility(sparseArray, true);
                reportWidgetInteractionEvent(packageName, android.os.UserHandle.getUserId(providerId.uid), "tap");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void reportWidgetInteractionEvent(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        if (android.app.usage.Flags.userInteractionTypeApi()) {
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            persistableBundle.putString("android.app.usage.extra.EVENT_CATEGORY", "android.appwidget");
            persistableBundle.putString("android.app.usage.extra.EVENT_ACTION", str2);
            this.mUsageStatsManagerInternal.reportUserInteractionEvent(str, i, persistableBundle);
            return;
        }
        this.mUsageStatsManagerInternal.reportEvent(str, i, 7);
    }

    @android.annotation.Nullable
    public android.widget.RemoteViews getWidgetPreview(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.ComponentName componentName, int i, int i2) {
        int identifier;
        android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(str);
        ensureWidgetCategoryCombinationIsValid(i2);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(i);
                int size = this.mProviders.size();
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.mProviders.get(i3);
                    android.content.ComponentName componentName2 = provider.id.componentName;
                    if (!provider.zombie && componentName.equals(componentName2) && (identifier = provider.getInfoLocked(this.mContext).getProfile().getIdentifier()) == i) {
                        int callingUid = android.os.Binder.getCallingUid();
                        java.lang.String packageName = componentName2.getPackageName();
                        boolean isProviderInCallerOrInProfileAndWhitelListed = this.mSecurityPolicy.isProviderInCallerOrInProfileAndWhitelListed(packageName, identifier);
                        boolean filterAppAccess = this.mPackageManagerInternal.filterAppAccess(packageName, callingUid, identifier);
                        boolean isProviderInPackageForUid = this.mSecurityPolicy.isProviderInPackageForUid(provider, callingUid, str);
                        boolean hasCallerBindPermissionOrBindWhiteListedLocked = this.mSecurityPolicy.hasCallerBindPermissionOrBindWhiteListedLocked(str);
                        if (isProviderInCallerOrInProfileAndWhitelListed && !filterAppAccess && (isProviderInPackageForUid || hasCallerBindPermissionOrBindWhiteListedLocked)) {
                            return provider.getGeneratedPreviewLocked(i2);
                        }
                    }
                }
                throw new java.lang.IllegalArgumentException(componentName + " is not a valid AppWidget provider");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setWidgetPreview(@android.annotation.NonNull android.content.ComponentName componentName, int i, @android.annotation.NonNull android.widget.RemoteViews remoteViews) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        ensureWidgetCategoryCombinationIsValid(i);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(android.os.Binder.getCallingUid(), componentName));
                if (lookupProviderLocked == null) {
                    throw new java.lang.IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                lookupProviderLocked.setGeneratedPreviewLocked(i, remoteViews);
                scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeWidgetPreview(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mSecurityPolicy.enforceCallFromPackage(componentName.getPackageName());
        ensureWidgetCategoryCombinationIsValid(i);
        synchronized (this.mLock) {
            try {
                ensureGroupStateLoadedLocked(callingUserId);
                com.android.server.appwidget.AppWidgetServiceImpl.Provider lookupProviderLocked = lookupProviderLocked(new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(android.os.Binder.getCallingUid(), componentName));
                if (lookupProviderLocked == null) {
                    throw new java.lang.IllegalArgumentException(componentName + " is not a valid AppWidget provider");
                }
                if (lookupProviderLocked.removeGeneratedPreviewLocked(i)) {
                    scheduleNotifyGroupHostsForProvidersChangedLocked(callingUserId);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void ensureWidgetCategoryCombinationIsValid(int i) {
        if ((i & (-8)) != 0) {
            throw new java.lang.IllegalArgumentException(i + " is not a valid widget category combination");
        }
    }

    private final class CallbackHandler extends android.os.Handler {
        public static final int MSG_NOTIFY_APP_WIDGET_REMOVED = 5;
        public static final int MSG_NOTIFY_PROVIDERS_CHANGED = 3;
        public static final int MSG_NOTIFY_PROVIDER_CHANGED = 2;
        public static final int MSG_NOTIFY_UPDATE_APP_WIDGET = 1;
        public static final int MSG_NOTIFY_VIEW_DATA_CHANGED = 4;

        public CallbackHandler(android.os.Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.appwidget.AppWidgetServiceImpl.Host host = (com.android.server.appwidget.AppWidgetServiceImpl.Host) someArgs.arg1;
                    com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost = (com.android.internal.appwidget.IAppWidgetHost) someArgs.arg2;
                    android.widget.RemoteViews remoteViews = (android.widget.RemoteViews) someArgs.arg3;
                    long longValue = ((java.lang.Long) someArgs.arg4).longValue();
                    int i = someArgs.argi1;
                    someArgs.recycle();
                    com.android.server.appwidget.AppWidgetServiceImpl.this.handleNotifyUpdateAppWidget(host, iAppWidgetHost, i, remoteViews, longValue);
                    break;
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.appwidget.AppWidgetServiceImpl.Host host2 = (com.android.server.appwidget.AppWidgetServiceImpl.Host) someArgs2.arg1;
                    com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost2 = (com.android.internal.appwidget.IAppWidgetHost) someArgs2.arg2;
                    android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = (android.appwidget.AppWidgetProviderInfo) someArgs2.arg3;
                    long longValue2 = ((java.lang.Long) someArgs2.arg4).longValue();
                    int i2 = someArgs2.argi1;
                    someArgs2.recycle();
                    com.android.server.appwidget.AppWidgetServiceImpl.this.handleNotifyProviderChanged(host2, iAppWidgetHost2, i2, appWidgetProviderInfo, longValue2);
                    break;
                case 3:
                    com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.appwidget.AppWidgetServiceImpl.Host host3 = (com.android.server.appwidget.AppWidgetServiceImpl.Host) someArgs3.arg1;
                    com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost3 = (com.android.internal.appwidget.IAppWidgetHost) someArgs3.arg2;
                    someArgs3.recycle();
                    com.android.server.appwidget.AppWidgetServiceImpl.this.handleNotifyProvidersChanged(host3, iAppWidgetHost3);
                    break;
                case 4:
                    com.android.internal.os.SomeArgs someArgs4 = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.appwidget.AppWidgetServiceImpl.Host host4 = (com.android.server.appwidget.AppWidgetServiceImpl.Host) someArgs4.arg1;
                    com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost4 = (com.android.internal.appwidget.IAppWidgetHost) someArgs4.arg2;
                    long longValue3 = ((java.lang.Long) someArgs4.arg3).longValue();
                    int i3 = someArgs4.argi1;
                    int i4 = someArgs4.argi2;
                    someArgs4.recycle();
                    com.android.server.appwidget.AppWidgetServiceImpl.this.handleNotifyAppWidgetViewDataChanged(host4, iAppWidgetHost4, i3, i4, longValue3);
                    break;
                case 5:
                    com.android.internal.os.SomeArgs someArgs5 = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.appwidget.AppWidgetServiceImpl.Host host5 = (com.android.server.appwidget.AppWidgetServiceImpl.Host) someArgs5.arg1;
                    com.android.internal.appwidget.IAppWidgetHost iAppWidgetHost5 = (com.android.internal.appwidget.IAppWidgetHost) someArgs5.arg2;
                    long longValue4 = ((java.lang.Long) someArgs5.arg3).longValue();
                    int i5 = someArgs5.argi1;
                    someArgs5.recycle();
                    com.android.server.appwidget.AppWidgetServiceImpl.this.handleNotifyAppWidgetRemoved(host5, iAppWidgetHost5, i5, longValue4);
                    break;
            }
        }
    }

    private final class SecurityPolicy {
        private SecurityPolicy() {
        }

        public boolean isEnabledGroupProfile(int i) {
            return isParentOrProfile(android.os.UserHandle.getCallingUserId(), i) && isProfileEnabled(i);
        }

        public int[] getEnabledGroupProfileIds(int i) {
            int groupParent = getGroupParent(i);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.appwidget.AppWidgetServiceImpl.this.mUserManager.getEnabledProfileIds(groupParent);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void enforceServiceExistsAndRequiresBindRemoteViewsPermission(android.content.ComponentName componentName, int i) {
            android.content.pm.ServiceInfo serviceInfo;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                serviceInfo = com.android.server.appwidget.AppWidgetServiceImpl.this.mPackageManager.getServiceInfo(componentName, 4096L, i);
            } catch (android.os.RemoteException e) {
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            if (serviceInfo != null) {
                if (!"android.permission.BIND_REMOTEVIEWS".equals(serviceInfo.permission)) {
                    throw new java.lang.SecurityException("Service " + componentName + " in user " + i + "does not require android.permission.BIND_REMOTEVIEWS");
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            throw new java.lang.SecurityException("Service " + componentName + " not installed for user " + i);
        }

        public void enforceModifyAppWidgetBindPermissions(java.lang.String str) {
            com.android.server.appwidget.AppWidgetServiceImpl.this.mContext.enforceCallingPermission("android.permission.MODIFY_APPWIDGET_BIND_PERMISSIONS", "hasBindAppWidgetPermission packageName=" + str);
        }

        public boolean isCallerInstantAppLocked() {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.lang.String[] packagesForUid = com.android.server.appwidget.AppWidgetServiceImpl.this.mPackageManager.getPackagesForUid(callingUid);
                if (!com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
                    boolean isInstantApp = com.android.server.appwidget.AppWidgetServiceImpl.this.mPackageManager.isInstantApp(packagesForUid[0], android.os.UserHandle.getUserId(callingUid));
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return isInstantApp;
                }
            } catch (android.os.RemoteException e) {
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }

        public boolean isInstantAppLocked(java.lang.String str, int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                boolean isInstantApp = com.android.server.appwidget.AppWidgetServiceImpl.this.mPackageManager.isInstantApp(str, i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return isInstantApp;
            } catch (android.os.RemoteException e) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void enforceCallFromPackage(java.lang.String str) {
            com.android.server.appwidget.AppWidgetServiceImpl.this.mAppOpsManager.checkPackage(android.os.Binder.getCallingUid(), str);
        }

        public boolean hasCallerBindPermissionOrBindWhiteListedLocked(java.lang.String str) {
            try {
                com.android.server.appwidget.AppWidgetServiceImpl.this.mContext.enforceCallingOrSelfPermission("android.permission.BIND_APPWIDGET", null);
                return true;
            } catch (java.lang.SecurityException e) {
                if (!isCallerBindAppWidgetAllowListedLocked(str)) {
                    return false;
                }
                return true;
            }
        }

        private boolean isCallerBindAppWidgetAllowListedLocked(java.lang.String str) {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (com.android.server.appwidget.AppWidgetServiceImpl.this.getUidForPackage(str, callingUserId) < 0) {
                throw new java.lang.IllegalArgumentException("No package " + str + " for user " + callingUserId);
            }
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                try {
                    com.android.server.appwidget.AppWidgetServiceImpl.this.ensureGroupStateLoadedLocked(callingUserId);
                    return com.android.server.appwidget.AppWidgetServiceImpl.this.mPackagesWithBindWidgetPermission.contains(android.util.Pair.create(java.lang.Integer.valueOf(callingUserId), str));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean canAccessAppWidget(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget, int i, java.lang.String str) {
            if (isHostInPackageForUid(widget.host, i, str) || isProviderInPackageForUid(widget.provider, i, str) || isHostAccessingProvider(widget.host, widget.provider, i, str)) {
                return true;
            }
            int userId = android.os.UserHandle.getUserId(i);
            return (widget.host.getUserId() == userId || (widget.provider != null && widget.provider.getUserId() == userId)) && com.android.server.appwidget.AppWidgetServiceImpl.this.mContext.checkCallingPermission("android.permission.BIND_APPWIDGET") == 0;
        }

        private boolean isParentOrProfile(int i, int i2) {
            return i == i2 || getProfileParent(i2) == i;
        }

        public boolean isProviderInCallerOrInProfileAndWhitelListed(java.lang.String str, int i) {
            int callingUserId = android.os.UserHandle.getCallingUserId();
            if (i == callingUserId) {
                return true;
            }
            if (getProfileParent(i) != callingUserId) {
                return false;
            }
            return isProviderWhiteListed(str, i);
        }

        public boolean isProviderWhiteListed(java.lang.String str, int i) {
            if (com.android.server.appwidget.AppWidgetServiceImpl.this.mDevicePolicyManagerInternal == null) {
                return false;
            }
            return com.android.server.appwidget.AppWidgetServiceImpl.this.mDevicePolicyManagerInternal.getCrossProfileWidgetProviders(i).contains(str);
        }

        public int getProfileParent(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.pm.UserInfo profileParent = com.android.server.appwidget.AppWidgetServiceImpl.this.mUserManager.getProfileParent(i);
                if (profileParent != null) {
                    return profileParent.getUserHandle().getIdentifier();
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return -10;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getGroupParent(int i) {
            int profileParent = com.android.server.appwidget.AppWidgetServiceImpl.this.mSecurityPolicy.getProfileParent(i);
            return profileParent != -10 ? profileParent : i;
        }

        public boolean isHostInPackageForUid(com.android.server.appwidget.AppWidgetServiceImpl.Host host, int i, java.lang.String str) {
            return host.id.uid == i && host.id.packageName.equals(str);
        }

        public boolean isProviderInPackageForUid(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, int i, java.lang.String str) {
            return provider != null && provider.id.uid == i && provider.id.componentName.getPackageName().equals(str);
        }

        public boolean isHostAccessingProvider(com.android.server.appwidget.AppWidgetServiceImpl.Host host, com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, int i, java.lang.String str) {
            return host.id.uid == i && provider != null && provider.id.componentName.getPackageName().equals(str);
        }

        private boolean isProfileEnabled(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.pm.UserInfo userInfo = com.android.server.appwidget.AppWidgetServiceImpl.this.mUserManager.getUserInfo(i);
                if (userInfo != null) {
                    if (userInfo.isEnabled()) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Provider {
        private static final int[] WIDGET_CATEGORY_FLAGS = {1, 2, 4};
        android.app.PendingIntent broadcast;
        android.util.SparseArray<android.widget.RemoteViews> generatedPreviews;
        com.android.server.appwidget.AppWidgetServiceImpl.ProviderId id;
        android.appwidget.AppWidgetProviderInfo info;
        java.lang.String infoTag;
        boolean mInfoParsed;
        boolean maskedByLockedProfile;
        boolean maskedByQuietProfile;
        boolean maskedBySuspendedPackage;
        int tag;
        java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.Widget> widgets;
        boolean zombie;

        private Provider() {
            this.widgets = new java.util.ArrayList<>();
            this.generatedPreviews = new android.util.SparseArray<>(3);
            this.mInfoParsed = false;
            this.tag = -1;
        }

        public int getUserId() {
            return android.os.UserHandle.getUserId(this.id.uid);
        }

        public boolean isInPackageForUser(java.lang.String str, int i) {
            return getUserId() == i && this.id.componentName.getPackageName().equals(str);
        }

        public boolean hostedByPackageForUser(java.lang.String str, int i) {
            int size = this.widgets.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = this.widgets.get(i2);
                if (str.equals(widget.host.id.packageName) && widget.host.getUserId() == i) {
                    return true;
                }
            }
            return false;
        }

        @com.android.internal.annotations.GuardedBy({"this.mLock"})
        public android.appwidget.AppWidgetProviderInfo getInfoLocked(android.content.Context context) {
            android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo;
            if (!this.mInfoParsed) {
                if (!this.zombie) {
                    if (android.text.TextUtils.isEmpty(this.infoTag)) {
                        appWidgetProviderInfo = null;
                    } else {
                        appWidgetProviderInfo = com.android.server.appwidget.AppWidgetServiceImpl.parseAppWidgetProviderInfo(context, this.id, this.info.providerInfo, this.infoTag);
                    }
                    if (appWidgetProviderInfo == null) {
                        appWidgetProviderInfo = com.android.server.appwidget.AppWidgetServiceImpl.parseAppWidgetProviderInfo(context, this.id, this.info.providerInfo, "android.appwidget.provider");
                    }
                    if (appWidgetProviderInfo != null) {
                        this.info = appWidgetProviderInfo;
                        updateGeneratedPreviewCategoriesLocked();
                    }
                }
                this.mInfoParsed = true;
            }
            return this.info;
        }

        @com.android.internal.annotations.GuardedBy({"AppWidgetServiceImpl.mLock"})
        public android.appwidget.AppWidgetProviderInfo getPartialInfoLocked() {
            return this.info;
        }

        @com.android.internal.annotations.GuardedBy({"AppWidgetServiceImpl.mLock"})
        public void setPartialInfoLocked(android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
            this.info = appWidgetProviderInfo;
            this.mInfoParsed = false;
        }

        @com.android.internal.annotations.GuardedBy({"AppWidgetServiceImpl.mLock"})
        public void setInfoLocked(android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
            this.info = appWidgetProviderInfo;
            this.mInfoParsed = true;
        }

        @com.android.internal.annotations.GuardedBy({"this.mLock"})
        @android.annotation.Nullable
        public android.widget.RemoteViews getGeneratedPreviewLocked(int i) {
            for (int i2 = 0; i2 < this.generatedPreviews.size(); i2++) {
                if ((this.generatedPreviews.keyAt(i2) & i) != 0) {
                    return this.generatedPreviews.valueAt(i2);
                }
            }
            return null;
        }

        @com.android.internal.annotations.GuardedBy({"this.mLock"})
        public void setGeneratedPreviewLocked(int i, @android.annotation.NonNull android.widget.RemoteViews remoteViews) {
            for (int i2 : WIDGET_CATEGORY_FLAGS) {
                if ((i & i2) != 0) {
                    this.generatedPreviews.put(i2, remoteViews);
                }
            }
            updateGeneratedPreviewCategoriesLocked();
        }

        @com.android.internal.annotations.GuardedBy({"this.mLock"})
        public boolean removeGeneratedPreviewLocked(int i) {
            boolean z = false;
            for (int i2 : WIDGET_CATEGORY_FLAGS) {
                if ((i & i2) != 0) {
                    z |= this.generatedPreviews.removeReturnOld(i2) != null;
                }
            }
            if (z) {
                updateGeneratedPreviewCategoriesLocked();
            }
            return z;
        }

        @com.android.internal.annotations.GuardedBy({"this.mLock"})
        public boolean clearGeneratedPreviewsLocked() {
            if (this.generatedPreviews.size() > 0) {
                this.generatedPreviews.clear();
                updateGeneratedPreviewCategoriesLocked();
                return true;
            }
            return false;
        }

        @com.android.internal.annotations.GuardedBy({"this.mLock"})
        private void updateGeneratedPreviewCategoriesLocked() {
            this.info.generatedPreviewCategories = 0;
            for (int i = 0; i < this.generatedPreviews.size(); i++) {
                this.info.generatedPreviewCategories |= this.generatedPreviews.keyAt(i);
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Provider{");
            sb.append(this.id);
            sb.append(this.zombie ? " Z" : "");
            sb.append('}');
            return sb.toString();
        }

        public boolean setMaskedByQuietProfileLocked(boolean z) {
            boolean z2 = this.maskedByQuietProfile;
            this.maskedByQuietProfile = z;
            return z != z2;
        }

        public boolean setMaskedByLockedProfileLocked(boolean z) {
            boolean z2 = this.maskedByLockedProfile;
            this.maskedByLockedProfile = z;
            return z != z2;
        }

        public boolean setMaskedBySuspendedPackageLocked(boolean z) {
            boolean z2 = this.maskedBySuspendedPackage;
            this.maskedBySuspendedPackage = z;
            return z != z2;
        }

        public boolean isMaskedLocked() {
            return this.maskedByQuietProfile || this.maskedByLockedProfile || this.maskedBySuspendedPackage;
        }

        public boolean shouldBePersisted() {
            return (this.widgets.isEmpty() && android.text.TextUtils.isEmpty(this.infoTag)) ? false : true;
        }
    }

    private static final class ProviderId {
        final android.content.ComponentName componentName;
        final int uid;

        private ProviderId(int i, android.content.ComponentName componentName) {
            this.uid = i;
            this.componentName = componentName;
        }

        public android.os.UserHandle getProfile() {
            return android.os.UserHandle.getUserHandleForUid(this.uid);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.appwidget.AppWidgetServiceImpl.ProviderId.class != obj.getClass()) {
                return false;
            }
            com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId = (com.android.server.appwidget.AppWidgetServiceImpl.ProviderId) obj;
            if (this.uid != providerId.uid) {
                return false;
            }
            if (this.componentName == null) {
                if (providerId.componentName != null) {
                    return false;
                }
            } else if (!this.componentName.equals(providerId.componentName)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.uid * 31) + (this.componentName != null ? this.componentName.hashCode() : 0);
        }

        public java.lang.String toString() {
            return "ProviderId{user:" + android.os.UserHandle.getUserId(this.uid) + ", app:" + android.os.UserHandle.getAppId(this.uid) + ", cmp:" + this.componentName + '}';
        }
    }

    private static final class Host {
        private static final boolean DEBUG = true;
        private static final java.lang.String TAG = "AppWidgetServiceHost";
        com.android.internal.appwidget.IAppWidgetHost callbacks;
        com.android.server.appwidget.AppWidgetServiceImpl.HostId id;
        long lastWidgetUpdateSequenceNo;
        int tag;
        java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.Widget> widgets;
        boolean zombie;

        private Host() {
            this.widgets = new java.util.ArrayList<>();
            this.tag = -1;
        }

        public int getUserId() {
            return android.os.UserHandle.getUserId(this.id.uid);
        }

        public boolean isInPackageForUser(java.lang.String str, int i) {
            return getUserId() == i && this.id.packageName.equals(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hostsPackageForUser(java.lang.String str, int i) {
            int size = this.widgets.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = this.widgets.get(i2).provider;
                if (provider != null && provider.getUserId() == i && str.equals(provider.id.componentName.getPackageName())) {
                    return true;
                }
            }
            return false;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void getPendingUpdatesForIdLocked(android.content.Context context, int i, android.util.LongSparseArray<android.appwidget.PendingHostUpdate> longSparseArray) {
            android.appwidget.PendingHostUpdate updateAppWidget;
            long j = this.lastWidgetUpdateSequenceNo;
            int size = this.widgets.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = this.widgets.get(i2);
                if (widget.appWidgetId == i) {
                    for (int size2 = widget.updateSequenceNos.size() - 1; size2 >= 0; size2--) {
                        long valueAt = widget.updateSequenceNos.valueAt(size2);
                        if (valueAt > j) {
                            int keyAt = widget.updateSequenceNos.keyAt(size2);
                            switch (keyAt) {
                                case 0:
                                    updateAppWidget = android.appwidget.PendingHostUpdate.updateAppWidget(i, com.android.server.appwidget.AppWidgetServiceImpl.cloneIfLocalBinder(widget.getEffectiveViewsLocked()));
                                    break;
                                case 1:
                                    updateAppWidget = android.appwidget.PendingHostUpdate.providerChanged(i, widget.provider.getInfoLocked(context));
                                    break;
                                default:
                                    updateAppWidget = android.appwidget.PendingHostUpdate.viewDataChanged(i, keyAt);
                                    break;
                            }
                            longSparseArray.put(valueAt, updateAppWidget);
                        }
                    }
                    return;
                }
            }
            longSparseArray.put(this.lastWidgetUpdateSequenceNo, android.appwidget.PendingHostUpdate.appWidgetRemoved(i));
        }

        public android.util.SparseArray<java.lang.String> getWidgetUidsIfBound() {
            android.util.SparseArray<java.lang.String> sparseArray = new android.util.SparseArray<>();
            for (int size = this.widgets.size() - 1; size >= 0; size--) {
                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = this.widgets.get(size);
                if (widget.provider == null) {
                    android.util.Slog.d(TAG, "Widget with no provider " + widget.toString());
                } else {
                    com.android.server.appwidget.AppWidgetServiceImpl.ProviderId providerId = widget.provider.id;
                    sparseArray.put(providerId.uid, providerId.componentName.getPackageName());
                }
            }
            return sparseArray;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Host{");
            sb.append(this.id);
            sb.append(this.zombie ? " Z" : "");
            sb.append('}');
            return sb.toString();
        }
    }

    private static final class HostId {
        final int hostId;
        final java.lang.String packageName;
        final int uid;

        public HostId(int i, int i2, java.lang.String str) {
            this.uid = i;
            this.hostId = i2;
            this.packageName = str;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.appwidget.AppWidgetServiceImpl.HostId.class != obj.getClass()) {
                return false;
            }
            com.android.server.appwidget.AppWidgetServiceImpl.HostId hostId = (com.android.server.appwidget.AppWidgetServiceImpl.HostId) obj;
            if (this.uid != hostId.uid || this.hostId != hostId.hostId) {
                return false;
            }
            if (this.packageName == null) {
                if (hostId.packageName != null) {
                    return false;
                }
            } else if (!this.packageName.equals(hostId.packageName)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.uid * 31) + this.hostId) * 31) + (this.packageName != null ? this.packageName.hashCode() : 0);
        }

        public java.lang.String toString() {
            return "HostId{user:" + android.os.UserHandle.getUserId(this.uid) + ", app:" + android.os.UserHandle.getAppId(this.uid) + ", hostId:" + this.hostId + ", pkg:" + this.packageName + '}';
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Widget {
        int appWidgetId;
        com.android.server.appwidget.AppWidgetServiceImpl.Host host;
        android.widget.RemoteViews maskedViews;
        android.os.Bundle options;
        com.android.server.appwidget.AppWidgetServiceImpl.Provider provider;
        int restoredId;
        boolean trackingUpdate;
        android.util.SparseLongArray updateSequenceNos;
        android.widget.RemoteViews views;

        private Widget() {
            this.updateSequenceNos = new android.util.SparseLongArray(2);
            this.trackingUpdate = false;
        }

        public java.lang.String toString() {
            return "AppWidgetId{" + this.appWidgetId + ':' + this.host + ':' + this.provider + '}';
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean replaceWithMaskedViewsLocked(android.widget.RemoteViews remoteViews) {
            this.maskedViews = remoteViews;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean clearMaskedViewsLocked() {
            if (this.maskedViews != null) {
                this.maskedViews = null;
                return true;
            }
            return false;
        }

        public android.widget.RemoteViews getEffectiveViewsLocked() {
            return this.maskedViews != null ? this.maskedViews : this.views;
        }
    }

    private class LoadedWidgetState {
        final int hostTag;
        final int providerTag;
        final com.android.server.appwidget.AppWidgetServiceImpl.Widget widget;

        public LoadedWidgetState(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget, int i, int i2) {
            this.widget = widget;
            this.hostTag = i;
            this.providerTag = i2;
        }
    }

    private final class SaveStateRunnable implements java.lang.Runnable {
        final int mUserId;

        public SaveStateRunnable(int i) {
            this.mUserId = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                android.os.Trace.traceBegin(64L, "convert_state_and_io");
                com.android.server.appwidget.AppWidgetServiceImpl.this.ensureGroupStateLoadedLocked(this.mUserId, false);
                com.android.server.appwidget.AppWidgetServiceImpl.this.saveStateLocked(this.mUserId);
                android.os.Trace.traceEnd(64L);
            }
        }
    }

    private final class BackupRestoreController {
        private static final boolean DEBUG = true;
        private static final java.lang.String TAG = "BackupRestoreController";
        private static final int WIDGET_STATE_VERSION = 2;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mHasSystemRestoreFinished;
        private final android.util.SparseArray<java.util.Set<java.lang.String>> mPrunedAppsPerUser;
        private final java.util.HashMap<com.android.server.appwidget.AppWidgetServiceImpl.Host, java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord>> mUpdatesByHost;
        private final java.util.HashMap<com.android.server.appwidget.AppWidgetServiceImpl.Provider, java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord>> mUpdatesByProvider;

        private BackupRestoreController() {
            this.mPrunedAppsPerUser = new android.util.SparseArray<>();
            this.mUpdatesByProvider = new java.util.HashMap<>();
            this.mUpdatesByHost = new java.util.HashMap<>();
        }

        public java.util.List<java.lang.String> getWidgetParticipants(int i) {
            android.util.Slog.i(TAG, "Getting widget participants for user: " + i);
            java.util.HashSet hashSet = new java.util.HashSet();
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                try {
                    int size = com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = (com.android.server.appwidget.AppWidgetServiceImpl.Widget) com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.get(i2);
                        if (isProviderAndHostInUser(widget, i)) {
                            hashSet.add(widget.host.id.packageName);
                            com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = widget.provider;
                            if (provider != null) {
                                hashSet.add(provider.id.componentName.getPackageName());
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return new java.util.ArrayList(hashSet);
        }

        public byte[] getWidgetState(java.lang.String str, int i) {
            android.util.Slog.i(TAG, "Getting widget state for user: " + i);
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                if (!packageNeedsWidgetBackupLocked(str, i)) {
                    return null;
                }
                try {
                    com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
                    newFastSerializer.setOutput(byteArrayOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                    newFastSerializer.startDocument((java.lang.String) null, true);
                    newFastSerializer.startTag((java.lang.String) null, "ws");
                    newFastSerializer.attributeInt((java.lang.String) null, "version", 2);
                    newFastSerializer.attribute((java.lang.String) null, "pkg", str);
                    int size = com.android.server.appwidget.AppWidgetServiceImpl.this.mProviders.size();
                    int i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = (com.android.server.appwidget.AppWidgetServiceImpl.Provider) com.android.server.appwidget.AppWidgetServiceImpl.this.mProviders.get(i3);
                        if (provider.shouldBePersisted()) {
                            if (!provider.isInPackageForUser(str, i) && !provider.hostedByPackageForUser(str, i)) {
                            }
                            provider.tag = i2;
                            com.android.server.appwidget.AppWidgetServiceImpl.serializeProvider(newFastSerializer, provider, false);
                            i2++;
                        }
                    }
                    int size2 = com.android.server.appwidget.AppWidgetServiceImpl.this.mHosts.size();
                    int i4 = 0;
                    for (int i5 = 0; i5 < size2; i5++) {
                        com.android.server.appwidget.AppWidgetServiceImpl.Host host = (com.android.server.appwidget.AppWidgetServiceImpl.Host) com.android.server.appwidget.AppWidgetServiceImpl.this.mHosts.get(i5);
                        if (!host.widgets.isEmpty() && (host.isInPackageForUser(str, i) || host.hostsPackageForUser(str, i))) {
                            host.tag = i4;
                            com.android.server.appwidget.AppWidgetServiceImpl.serializeHost(newFastSerializer, host);
                            i4++;
                        }
                    }
                    int size3 = com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.size();
                    for (int i6 = 0; i6 < size3; i6++) {
                        com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = (com.android.server.appwidget.AppWidgetServiceImpl.Widget) com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.get(i6);
                        com.android.server.appwidget.AppWidgetServiceImpl.Provider provider2 = widget.provider;
                        if (widget.host.isInPackageForUser(str, i) || (provider2 != null && provider2.isInPackageForUser(str, i))) {
                            com.android.server.appwidget.AppWidgetServiceImpl.serializeAppWidget(newFastSerializer, widget, false);
                        }
                    }
                    newFastSerializer.endTag((java.lang.String) null, "ws");
                    newFastSerializer.endDocument();
                    return byteArrayOutputStream.toByteArray();
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Unable to save widget state for " + str);
                    return null;
                }
            }
        }

        public void systemRestoreStarting(int i) {
            android.util.Slog.i(TAG, "System restore starting for user: " + i);
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                this.mHasSystemRestoreFinished = false;
                getPrunedAppsLocked(i).clear();
                this.mUpdatesByProvider.clear();
                this.mUpdatesByHost.clear();
            }
        }

        public void restoreWidgetState(java.lang.String str, byte[] bArr, int i) {
            java.util.ArrayList arrayList;
            java.util.ArrayList arrayList2;
            com.android.modules.utils.TypedXmlPullParser newFastPullParser;
            int next;
            android.util.Slog.i(TAG, "Restoring widget state for user:" + i + " package: " + str);
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            try {
                try {
                    arrayList = new java.util.ArrayList();
                    arrayList2 = new java.util.ArrayList();
                    newFastPullParser = android.util.Xml.newFastPullParser();
                    newFastPullParser.setInput(byteArrayInputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.w(TAG, "Unable to restore widget state for " + str);
                }
                synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                    do {
                        next = newFastPullParser.next();
                        if (next == 2) {
                            java.lang.String name = newFastPullParser.getName();
                            byte b = 0;
                            byte b2 = 0;
                            if ("ws".equals(name)) {
                                int attributeInt = newFastPullParser.getAttributeInt((java.lang.String) null, "version");
                                if (attributeInt > 2) {
                                    android.util.Slog.w(TAG, "Unable to process state version " + attributeInt);
                                    return;
                                }
                                if (!str.equals(newFastPullParser.getAttributeValue((java.lang.String) null, "pkg"))) {
                                    android.util.Slog.w(TAG, "Package mismatch in ws");
                                    return;
                                }
                            } else {
                                int i2 = -1;
                                if ("p".equals(name)) {
                                    android.content.ComponentName componentName = new android.content.ComponentName(newFastPullParser.getAttributeValue((java.lang.String) null, "pkg"), newFastPullParser.getAttributeValue((java.lang.String) null, "cl"));
                                    com.android.server.appwidget.AppWidgetServiceImpl.Provider findProviderLocked = findProviderLocked(componentName, i);
                                    if (findProviderLocked == null) {
                                        android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = new android.appwidget.AppWidgetProviderInfo();
                                        appWidgetProviderInfo.provider = componentName;
                                        com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = new com.android.server.appwidget.AppWidgetServiceImpl.Provider();
                                        provider.id = new com.android.server.appwidget.AppWidgetServiceImpl.ProviderId(i2, componentName);
                                        provider.setPartialInfoLocked(appWidgetProviderInfo);
                                        provider.zombie = true;
                                        com.android.server.appwidget.AppWidgetServiceImpl.this.mProviders.add(provider);
                                        findProviderLocked = provider;
                                    }
                                    android.util.Slog.i(TAG, "   provider " + findProviderLocked.id);
                                    arrayList.add(findProviderLocked);
                                } else if ("h".equals(name)) {
                                    java.lang.String attributeValue = newFastPullParser.getAttributeValue((java.lang.String) null, "pkg");
                                    com.android.server.appwidget.AppWidgetServiceImpl.Host lookupOrAddHostLocked = com.android.server.appwidget.AppWidgetServiceImpl.this.lookupOrAddHostLocked(new com.android.server.appwidget.AppWidgetServiceImpl.HostId(com.android.server.appwidget.AppWidgetServiceImpl.this.getUidForPackage(attributeValue, i), newFastPullParser.getAttributeIntHex((java.lang.String) null, "id"), attributeValue));
                                    arrayList2.add(lookupOrAddHostLocked);
                                    android.util.Slog.i(TAG, "   host[" + arrayList2.size() + "]: {" + lookupOrAddHostLocked.id + "}");
                                } else if ("g".equals(name)) {
                                    int attributeIntHex = newFastPullParser.getAttributeIntHex((java.lang.String) null, "id");
                                    com.android.server.appwidget.AppWidgetServiceImpl.Host host = (com.android.server.appwidget.AppWidgetServiceImpl.Host) arrayList2.get(newFastPullParser.getAttributeIntHex((java.lang.String) null, "h"));
                                    int attributeIntHex2 = newFastPullParser.getAttributeIntHex((java.lang.String) null, "p", -1);
                                    com.android.server.appwidget.AppWidgetServiceImpl.Provider provider2 = attributeIntHex2 != -1 ? (com.android.server.appwidget.AppWidgetServiceImpl.Provider) arrayList.get(attributeIntHex2) : null;
                                    pruneWidgetStateLocked(host.id.packageName, i);
                                    if (provider2 != null) {
                                        pruneWidgetStateLocked(provider2.id.componentName.getPackageName(), i);
                                    }
                                    com.android.server.appwidget.AppWidgetServiceImpl.Widget findRestoredWidgetLocked = findRestoredWidgetLocked(attributeIntHex, host, provider2);
                                    if (findRestoredWidgetLocked == null) {
                                        findRestoredWidgetLocked = new com.android.server.appwidget.AppWidgetServiceImpl.Widget();
                                        findRestoredWidgetLocked.appWidgetId = com.android.server.appwidget.AppWidgetServiceImpl.this.incrementAndGetAppWidgetIdLocked(i);
                                        findRestoredWidgetLocked.restoredId = attributeIntHex;
                                        findRestoredWidgetLocked.options = com.android.server.appwidget.AppWidgetServiceImpl.parseWidgetIdOptions(newFastPullParser);
                                        findRestoredWidgetLocked.host = host;
                                        findRestoredWidgetLocked.host.widgets.add(findRestoredWidgetLocked);
                                        findRestoredWidgetLocked.provider = provider2;
                                        if (findRestoredWidgetLocked.provider != null) {
                                            findRestoredWidgetLocked.provider.widgets.add(findRestoredWidgetLocked);
                                        }
                                        android.util.Slog.i(TAG, "New restored id " + attributeIntHex + " now " + findRestoredWidgetLocked);
                                        com.android.server.appwidget.AppWidgetServiceImpl.this.addWidgetLocked(findRestoredWidgetLocked);
                                    }
                                    if (findRestoredWidgetLocked.provider == null || findRestoredWidgetLocked.provider.getPartialInfoLocked() == null) {
                                        android.util.Slog.w(TAG, "Missing provider for restored widget " + findRestoredWidgetLocked);
                                    } else {
                                        stashProviderRestoreUpdateLocked(findRestoredWidgetLocked.provider, attributeIntHex, findRestoredWidgetLocked.appWidgetId);
                                    }
                                    stashHostRestoreUpdateLocked(findRestoredWidgetLocked.host, attributeIntHex, findRestoredWidgetLocked.appWidgetId);
                                    android.util.Slog.i(TAG, "   instance: " + attributeIntHex + " -> " + findRestoredWidgetLocked.appWidgetId + " :: p=" + findRestoredWidgetLocked.provider);
                                }
                            }
                        }
                    } while (next != 1);
                }
            } finally {
                com.android.server.appwidget.AppWidgetServiceImpl.this.saveGroupStateAsync(i);
            }
        }

        public void systemRestoreFinished(int i) {
            android.util.Slog.i(TAG, "systemRestoreFinished for " + i);
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                this.mHasSystemRestoreFinished = true;
                maybeSendWidgetRestoreBroadcastsLocked(i);
            }
        }

        public void widgetComponentsChanged(int i) {
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                try {
                    if (this.mHasSystemRestoreFinished) {
                        maybeSendWidgetRestoreBroadcastsLocked(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void maybeSendWidgetRestoreBroadcastsLocked(int i) {
            java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord> arrayList;
            android.util.Slog.i(TAG, "maybeSendWidgetRestoreBroadcasts for " + i);
            android.os.UserHandle userHandle = new android.os.UserHandle(i);
            java.util.Iterator<java.util.Map.Entry<com.android.server.appwidget.AppWidgetServiceImpl.Provider, java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord>>> it = this.mUpdatesByProvider.entrySet().iterator();
            while (true) {
                boolean z = true;
                if (!it.hasNext()) {
                    break;
                }
                java.util.Map.Entry<com.android.server.appwidget.AppWidgetServiceImpl.Provider, java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord>> next = it.next();
                com.android.server.appwidget.AppWidgetServiceImpl.Provider key = next.getKey();
                if (!key.zombie) {
                    java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord> value = next.getValue();
                    int countPendingUpdates = countPendingUpdates(value);
                    android.util.Slog.i(TAG, "Provider " + key + " pending: " + countPendingUpdates);
                    if (countPendingUpdates > 0) {
                        int[] iArr = new int[countPendingUpdates];
                        int[] iArr2 = new int[countPendingUpdates];
                        int size = value.size();
                        int i2 = 0;
                        int i3 = 0;
                        while (i3 < size) {
                            com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord restoreUpdateRecord = value.get(i3);
                            if (!restoreUpdateRecord.notified) {
                                restoreUpdateRecord.notified = z;
                                iArr[i2] = restoreUpdateRecord.oldId;
                                iArr2[i2] = restoreUpdateRecord.newId;
                                i2++;
                                android.util.Slog.i(TAG, "   " + restoreUpdateRecord.oldId + " => " + restoreUpdateRecord.newId);
                            }
                            i3++;
                            z = true;
                        }
                        sendWidgetRestoreBroadcastLocked("android.appwidget.action.APPWIDGET_RESTORED", key, null, iArr, iArr2, userHandle);
                    }
                }
            }
            for (java.util.Map.Entry<com.android.server.appwidget.AppWidgetServiceImpl.Host, java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord>> entry : this.mUpdatesByHost.entrySet()) {
                com.android.server.appwidget.AppWidgetServiceImpl.Host key2 = entry.getKey();
                if (key2.id.uid != -1) {
                    java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord> value2 = entry.getValue();
                    int countPendingUpdates2 = countPendingUpdates(value2);
                    android.util.Slog.i(TAG, "Host " + key2 + " pending: " + countPendingUpdates2);
                    if (countPendingUpdates2 > 0) {
                        int[] iArr3 = new int[countPendingUpdates2];
                        int[] iArr4 = new int[countPendingUpdates2];
                        int size2 = value2.size();
                        int i4 = 0;
                        int i5 = 0;
                        while (i4 < size2) {
                            com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord restoreUpdateRecord2 = value2.get(i4);
                            if (restoreUpdateRecord2.notified) {
                                arrayList = value2;
                            } else {
                                restoreUpdateRecord2.notified = true;
                                iArr3[i5] = restoreUpdateRecord2.oldId;
                                iArr4[i5] = restoreUpdateRecord2.newId;
                                i5++;
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                sb.append("   ");
                                arrayList = value2;
                                sb.append(restoreUpdateRecord2.oldId);
                                sb.append(" => ");
                                sb.append(restoreUpdateRecord2.newId);
                                android.util.Slog.i(TAG, sb.toString());
                            }
                            i4++;
                            value2 = arrayList;
                        }
                        sendWidgetRestoreBroadcastLocked("android.appwidget.action.APPWIDGET_HOST_RESTORED", null, key2, iArr3, iArr4, userHandle);
                    }
                }
            }
        }

        private com.android.server.appwidget.AppWidgetServiceImpl.Provider findProviderLocked(android.content.ComponentName componentName, int i) {
            int size = com.android.server.appwidget.AppWidgetServiceImpl.this.mProviders.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = (com.android.server.appwidget.AppWidgetServiceImpl.Provider) com.android.server.appwidget.AppWidgetServiceImpl.this.mProviders.get(i2);
                if (provider.getUserId() == i && provider.id.componentName.equals(componentName)) {
                    return provider;
                }
            }
            return null;
        }

        private com.android.server.appwidget.AppWidgetServiceImpl.Widget findRestoredWidgetLocked(int i, com.android.server.appwidget.AppWidgetServiceImpl.Host host, com.android.server.appwidget.AppWidgetServiceImpl.Provider provider) {
            android.util.Slog.i(TAG, "Find restored widget: id=" + i + " host=" + host + " provider=" + provider);
            if (provider == null || host == null) {
                return null;
            }
            int size = com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = (com.android.server.appwidget.AppWidgetServiceImpl.Widget) com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.get(i2);
                if (widget.restoredId == i && widget.host.id.equals(host.id) && widget.provider.id.equals(provider.id)) {
                    android.util.Slog.i(TAG, "   Found at " + i2 + " : " + widget);
                    return widget;
                }
            }
            return null;
        }

        private boolean packageNeedsWidgetBackupLocked(java.lang.String str, int i) {
            int size = com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = (com.android.server.appwidget.AppWidgetServiceImpl.Widget) com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.get(i2);
                if (isProviderAndHostInUser(widget, i)) {
                    if (widget.host.isInPackageForUser(str, i)) {
                        return true;
                    }
                    com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = widget.provider;
                    if (provider != null && provider.isInPackageForUser(str, i)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void stashProviderRestoreUpdateLocked(com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, int i, int i2) {
            java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord> arrayList = this.mUpdatesByProvider.get(provider);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList<>();
                this.mUpdatesByProvider.put(provider, arrayList);
            } else if (alreadyStashed(arrayList, i, i2)) {
                android.util.Slog.i(TAG, "ID remap " + i + " -> " + i2 + " already stashed for " + provider);
                return;
            }
            arrayList.add(new com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord(i, i2));
        }

        private boolean alreadyStashed(java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord> arrayList, int i, int i2) {
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord restoreUpdateRecord = arrayList.get(i3);
                if (restoreUpdateRecord.oldId == i && restoreUpdateRecord.newId == i2) {
                    return true;
                }
            }
            return false;
        }

        private void stashHostRestoreUpdateLocked(com.android.server.appwidget.AppWidgetServiceImpl.Host host, int i, int i2) {
            java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord> arrayList = this.mUpdatesByHost.get(host);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList<>();
                this.mUpdatesByHost.put(host, arrayList);
            } else if (alreadyStashed(arrayList, i, i2)) {
                android.util.Slog.i(TAG, "ID remap " + i + " -> " + i2 + " already stashed for " + host);
                return;
            }
            arrayList.add(new com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord(i, i2));
        }

        private void sendWidgetRestoreBroadcastLocked(java.lang.String str, com.android.server.appwidget.AppWidgetServiceImpl.Provider provider, com.android.server.appwidget.AppWidgetServiceImpl.Host host, int[] iArr, int[] iArr2, android.os.UserHandle userHandle) {
            android.content.Intent intent = new android.content.Intent(str);
            intent.putExtra("appWidgetOldIds", iArr);
            intent.putExtra("appWidgetIds", iArr2);
            if (provider != null) {
                intent.setComponent(provider.id.componentName);
                com.android.server.appwidget.AppWidgetServiceImpl.this.sendBroadcastAsUser(intent, userHandle, true);
            }
            if (host != null) {
                intent.setComponent(null);
                intent.setPackage(host.id.packageName);
                intent.putExtra("hostId", host.id.hostId);
                com.android.server.appwidget.AppWidgetServiceImpl.this.sendBroadcastAsUser(intent, userHandle, true);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void pruneWidgetStateLocked(java.lang.String str, int i) {
            java.util.Set<java.lang.String> prunedAppsLocked = getPrunedAppsLocked(i);
            if (!prunedAppsLocked.contains(str)) {
                android.util.Slog.i(TAG, "pruning widget state for restoring package " + str);
                for (int size = com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.size() + (-1); size >= 0; size--) {
                    com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = (com.android.server.appwidget.AppWidgetServiceImpl.Widget) com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.get(size);
                    com.android.server.appwidget.AppWidgetServiceImpl.Host host = widget.host;
                    com.android.server.appwidget.AppWidgetServiceImpl.Provider provider = widget.provider;
                    if (host.hostsPackageForUser(str, i) || (provider != null && provider.isInPackageForUser(str, i))) {
                        host.widgets.remove(widget);
                        provider.widgets.remove(widget);
                        com.android.server.appwidget.AppWidgetServiceImpl.this.decrementAppWidgetServiceRefCount(widget);
                        com.android.server.appwidget.AppWidgetServiceImpl.this.removeWidgetLocked(widget);
                    }
                }
                prunedAppsLocked.add(str);
                return;
            }
            android.util.Slog.i(TAG, "already pruned " + str + ", continuing normally");
        }

        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        private java.util.Set<java.lang.String> getPrunedAppsLocked(int i) {
            if (!this.mPrunedAppsPerUser.contains(i)) {
                this.mPrunedAppsPerUser.set(i, new android.util.ArraySet());
            }
            return this.mPrunedAppsPerUser.get(i);
        }

        private boolean isProviderAndHostInUser(com.android.server.appwidget.AppWidgetServiceImpl.Widget widget, int i) {
            return widget.host.getUserId() == i && (widget.provider == null || widget.provider.getUserId() == i);
        }

        private int countPendingUpdates(java.util.ArrayList<com.android.server.appwidget.AppWidgetServiceImpl.BackupRestoreController.RestoreUpdateRecord> arrayList) {
            int size = arrayList.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                if (!arrayList.get(i2).notified) {
                    i++;
                }
            }
            return i;
        }

        private class RestoreUpdateRecord {
            public int newId;
            public boolean notified = false;
            public int oldId;

            public RestoreUpdateRecord(int i, int i2) {
                this.oldId = i;
                this.newId = i2;
            }
        }
    }

    private class AppWidgetManagerLocal extends android.appwidget.AppWidgetManagerInternal {
        private AppWidgetManagerLocal() {
        }

        public android.util.ArraySet<java.lang.String> getHostedWidgetPackages(int i) {
            android.util.ArraySet<java.lang.String> arraySet;
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                try {
                    int size = com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.size();
                    arraySet = null;
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.appwidget.AppWidgetServiceImpl.Widget widget = (com.android.server.appwidget.AppWidgetServiceImpl.Widget) com.android.server.appwidget.AppWidgetServiceImpl.this.mWidgets.get(i2);
                        if (widget.host.id.uid == i && widget.provider != null) {
                            if (arraySet == null) {
                                arraySet = new android.util.ArraySet<>();
                            }
                            arraySet.add(widget.provider.id.componentName.getPackageName());
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return arraySet;
        }

        public void unlockUser(int i) {
            com.android.server.appwidget.AppWidgetServiceImpl.this.handleUserUnlocked(i);
        }

        public void applyResourceOverlaysToWidgets(java.util.Set<java.lang.String> set, int i, boolean z) {
            synchronized (com.android.server.appwidget.AppWidgetServiceImpl.this.mLock) {
                com.android.server.appwidget.AppWidgetServiceImpl.this.applyResourceOverlaysToWidgetsLocked(new java.util.HashSet(set), i, z);
            }
        }
    }
}
