package com.android.server.vr;

/* loaded from: classes2.dex */
public class VrManagerService extends com.android.server.SystemService implements com.android.server.vr.EnabledComponentsObserver.EnabledComponentChangeListener, com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver {
    static final boolean DBG = false;
    private static final int EVENT_LOG_SIZE = 64;
    private static final int FLAG_ALL = 7;
    private static final int FLAG_AWAKE = 1;
    private static final int FLAG_KEYGUARD_UNLOCKED = 4;
    private static final int FLAG_NONE = 0;
    private static final int FLAG_SCREEN_ON = 2;
    private static final int INVALID_APPOPS_MODE = -1;
    private static final int MSG_PENDING_VR_STATE_CHANGE = 1;
    private static final int MSG_PERSISTENT_VR_MODE_STATE_CHANGE = 2;
    private static final int MSG_VR_STATE_CHANGE = 0;
    private static final int PENDING_STATE_DELAY_MS = 300;
    public static final java.lang.String TAG = "VrManagerService";
    private static final com.android.server.utils.ManagedApplicationService.BinderChecker sBinderChecker = new com.android.server.utils.ManagedApplicationService.BinderChecker() { // from class: com.android.server.vr.VrManagerService.3
        @Override // com.android.server.utils.ManagedApplicationService.BinderChecker
        public android.os.IInterface asInterface(android.os.IBinder iBinder) {
            return android.service.vr.IVrListener.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.utils.ManagedApplicationService.BinderChecker
        public boolean checkType(android.os.IInterface iInterface) {
            return iInterface instanceof android.service.vr.IVrListener;
        }
    };
    private boolean mBootsToVr;
    private com.android.server.vr.EnabledComponentsObserver mComponentObserver;
    private android.content.Context mContext;
    private com.android.server.utils.ManagedApplicationService mCurrentVrCompositorService;
    private android.content.ComponentName mCurrentVrModeComponent;
    private int mCurrentVrModeUser;
    private com.android.server.utils.ManagedApplicationService mCurrentVrService;
    private android.content.ComponentName mDefaultVrService;
    private final com.android.server.utils.ManagedApplicationService.EventCallback mEventCallback;
    private boolean mGuard;
    private final android.os.Handler mHandler;
    private final java.lang.Object mLock;
    private boolean mLogLimitHit;
    private final java.util.ArrayDeque<com.android.server.utils.ManagedApplicationService.LogFormattable> mLoggingDeque;
    private final com.android.server.vr.VrManagerService.NotificationAccessManager mNotifAccessManager;
    private android.app.INotificationManager mNotificationManager;
    private final android.os.IBinder mOverlayToken;
    private com.android.server.vr.VrManagerService.VrState mPendingState;
    private boolean mPersistentVrModeEnabled;
    private final android.os.RemoteCallbackList<android.service.vr.IPersistentVrStateCallbacks> mPersistentVrStateRemoteCallbacks;
    private int mPreviousCoarseLocationMode;
    private int mPreviousManageOverlayMode;
    private boolean mRunning2dInVr;
    private boolean mStandby;
    private int mSystemSleepFlags;
    private boolean mUseStandbyToExitVrMode;
    private boolean mUserUnlocked;
    private com.android.server.vr.Vr2dDisplay mVr2dDisplay;
    private int mVrAppProcessId;
    private final android.service.vr.IVrManager mVrManager;
    private boolean mVrModeAllowed;
    private boolean mVrModeEnabled;
    private final android.os.RemoteCallbackList<android.service.vr.IVrStateCallbacks> mVrStateRemoteCallbacks;
    private boolean mWasDefaultGranted;

    private static native void initializeNative();

    private static native void setVrModeNative(boolean z);

    private void updateVrModeAllowedLocked() {
        com.android.server.vr.VrManagerService.VrState vrState;
        boolean z = (this.mSystemSleepFlags == 7 || (this.mBootsToVr && this.mUseStandbyToExitVrMode)) && this.mUserUnlocked && !(this.mStandby && this.mUseStandbyToExitVrMode);
        if (this.mVrModeAllowed != z) {
            this.mVrModeAllowed = z;
            if (this.mVrModeAllowed) {
                if (this.mBootsToVr) {
                    setPersistentVrModeEnabled(true);
                }
                if (this.mBootsToVr && !this.mVrModeEnabled) {
                    setVrMode(true, this.mDefaultVrService, 0, -1, null);
                    return;
                }
                return;
            }
            setPersistentModeAndNotifyListenersLocked(false);
            if (this.mVrModeEnabled && this.mCurrentVrService != null) {
                vrState = new com.android.server.vr.VrManagerService.VrState(this.mVrModeEnabled, this.mRunning2dInVr, this.mCurrentVrService.getComponent(), this.mCurrentVrService.getUserId(), this.mVrAppProcessId, this.mCurrentVrModeComponent);
            } else {
                vrState = null;
            }
            this.mPendingState = vrState;
            updateCurrentVrServiceLocked(false, false, null, 0, -1, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreenOn(boolean z) {
        setSystemState(2, z);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onAwakeStateChanged(boolean z) {
        setSystemState(1, z);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onKeyguardStateChanged(boolean z) {
        setSystemState(4, !z);
    }

    private void setSystemState(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                int i2 = this.mSystemSleepFlags;
                if (z) {
                    this.mSystemSleepFlags = i | this.mSystemSleepFlags;
                } else {
                    this.mSystemSleepFlags = (~i) & this.mSystemSleepFlags;
                }
                if (i2 != this.mSystemSleepFlags) {
                    updateVrModeAllowedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private java.lang.String getStateAsString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append((this.mSystemSleepFlags & 1) != 0 ? "awake, " : "");
        sb.append((this.mSystemSleepFlags & 2) != 0 ? "screen_on, " : "");
        sb.append((this.mSystemSleepFlags & 4) != 0 ? "keyguard_off" : "");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUserUnlocked() {
        synchronized (this.mLock) {
            this.mUserUnlocked = true;
            updateVrModeAllowedLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStandbyEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mBootsToVr) {
                    android.util.Slog.e(TAG, "Attempting to set standby mode on a non-standalone device");
                } else {
                    this.mStandby = z;
                    updateVrModeAllowedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static class SettingEvent implements com.android.server.utils.ManagedApplicationService.LogFormattable {
        public final long timestamp = java.lang.System.currentTimeMillis();
        public final java.lang.String what;

        SettingEvent(java.lang.String str) {
            this.what = str;
        }

        @Override // com.android.server.utils.ManagedApplicationService.LogFormattable
        public java.lang.String toLogString(java.text.SimpleDateFormat simpleDateFormat) {
            return simpleDateFormat.format(new java.util.Date(this.timestamp)) + "   " + this.what;
        }
    }

    private static class VrState implements com.android.server.utils.ManagedApplicationService.LogFormattable {
        final android.content.ComponentName callingPackage;
        final boolean defaultPermissionsGranted;
        final boolean enabled;
        final int processId;
        final boolean running2dInVr;
        final android.content.ComponentName targetPackageName;
        final long timestamp;
        final int userId;

        VrState(boolean z, boolean z2, android.content.ComponentName componentName, int i, int i2, android.content.ComponentName componentName2) {
            this.enabled = z;
            this.running2dInVr = z2;
            this.userId = i;
            this.processId = i2;
            this.targetPackageName = componentName;
            this.callingPackage = componentName2;
            this.defaultPermissionsGranted = false;
            this.timestamp = java.lang.System.currentTimeMillis();
        }

        VrState(boolean z, boolean z2, android.content.ComponentName componentName, int i, int i2, android.content.ComponentName componentName2, boolean z3) {
            this.enabled = z;
            this.running2dInVr = z2;
            this.userId = i;
            this.processId = i2;
            this.targetPackageName = componentName;
            this.callingPackage = componentName2;
            this.defaultPermissionsGranted = z3;
            this.timestamp = java.lang.System.currentTimeMillis();
        }

        @Override // com.android.server.utils.ManagedApplicationService.LogFormattable
        public java.lang.String toLogString(java.text.SimpleDateFormat simpleDateFormat) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(simpleDateFormat.format(new java.util.Date(this.timestamp)));
            sb.append("  ");
            sb.append("State changed to:");
            sb.append("  ");
            sb.append(this.enabled ? "ENABLED" : "DISABLED");
            sb.append("\n");
            if (this.enabled) {
                sb.append("  ");
                sb.append("User=");
                sb.append(this.userId);
                sb.append("\n");
                sb.append("  ");
                sb.append("Current VR Activity=");
                android.content.ComponentName componentName = this.callingPackage;
                java.lang.String str = com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG;
                sb.append(componentName == null ? com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : this.callingPackage.flattenToString());
                sb.append("\n");
                sb.append("  ");
                sb.append("Bound VrListenerService=");
                if (this.targetPackageName != null) {
                    str = this.targetPackageName.flattenToString();
                }
                sb.append(str);
                sb.append("\n");
                if (this.defaultPermissionsGranted) {
                    sb.append("  ");
                    sb.append("Default permissions granted to the bound VrListenerService.");
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
    }

    private final class NotificationAccessManager {
        private final android.util.SparseArray<android.util.ArraySet<java.lang.String>> mAllowedPackages;
        private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mNotificationAccessPackageToUserId;

        private NotificationAccessManager() {
            this.mAllowedPackages = new android.util.SparseArray<>();
            this.mNotificationAccessPackageToUserId = new android.util.ArrayMap<>();
        }

        public void update(java.util.Collection<java.lang.String> collection) {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            android.util.ArraySet<java.lang.String> arraySet = this.mAllowedPackages.get(currentUser);
            if (arraySet == null) {
                arraySet = new android.util.ArraySet<>();
            }
            for (int size = this.mNotificationAccessPackageToUserId.size() - 1; size >= 0; size--) {
                int intValue = this.mNotificationAccessPackageToUserId.valueAt(size).intValue();
                if (intValue != currentUser) {
                    java.lang.String keyAt = this.mNotificationAccessPackageToUserId.keyAt(size);
                    com.android.server.vr.VrManagerService.this.revokeNotificationListenerAccess(keyAt, intValue);
                    com.android.server.vr.VrManagerService.this.revokeNotificationPolicyAccess(keyAt);
                    com.android.server.vr.VrManagerService.this.revokeCoarseLocationPermissionIfNeeded(keyAt, intValue);
                    this.mNotificationAccessPackageToUserId.removeAt(size);
                }
            }
            java.util.Iterator<java.lang.String> it = arraySet.iterator();
            while (it.hasNext()) {
                java.lang.String next = it.next();
                if (!collection.contains(next)) {
                    com.android.server.vr.VrManagerService.this.revokeNotificationListenerAccess(next, currentUser);
                    com.android.server.vr.VrManagerService.this.revokeNotificationPolicyAccess(next);
                    com.android.server.vr.VrManagerService.this.revokeCoarseLocationPermissionIfNeeded(next, currentUser);
                    this.mNotificationAccessPackageToUserId.remove(next);
                }
            }
            for (java.lang.String str : collection) {
                if (!arraySet.contains(str)) {
                    com.android.server.vr.VrManagerService.this.grantNotificationPolicyAccess(str);
                    com.android.server.vr.VrManagerService.this.grantNotificationListenerAccess(str, currentUser);
                    com.android.server.vr.VrManagerService.this.grantCoarseLocationPermissionIfNeeded(str, currentUser);
                    this.mNotificationAccessPackageToUserId.put(str, java.lang.Integer.valueOf(currentUser));
                }
            }
            arraySet.clear();
            arraySet.addAll(collection);
            this.mAllowedPackages.put(currentUser, arraySet);
        }
    }

    @Override // com.android.server.vr.EnabledComponentsObserver.EnabledComponentChangeListener
    public void onEnabledComponentChanged() {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<android.content.ComponentName> enabled = this.mComponentObserver.getEnabled(android.app.ActivityManager.getCurrentUser());
                android.util.ArraySet arraySet = new android.util.ArraySet();
                java.util.Iterator<android.content.ComponentName> it = enabled.iterator();
                while (it.hasNext()) {
                    android.content.ComponentName next = it.next();
                    if (isDefaultAllowed(next.getPackageName())) {
                        arraySet.add(next.getPackageName());
                    }
                }
                this.mNotifAccessManager.update(arraySet);
                if (this.mVrModeAllowed) {
                    consumeAndApplyPendingStateLocked(false);
                    if (this.mCurrentVrService == null) {
                        return;
                    }
                    updateCurrentVrServiceLocked(this.mVrModeEnabled, this.mRunning2dInVr, this.mCurrentVrService.getComponent(), this.mCurrentVrService.getUserId(), this.mVrAppProcessId, this.mCurrentVrModeComponent);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceCallerPermissionAnyOf(java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            if (this.mContext.checkCallingOrSelfPermission(str) == 0) {
                return;
            }
        }
        throw new java.lang.SecurityException("Caller does not hold at least one of the permissions: " + java.util.Arrays.toString(strArr));
    }

    private final class LocalService extends com.android.server.vr.VrManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.vr.VrManagerInternal
        public void setVrMode(boolean z, android.content.ComponentName componentName, int i, int i2, android.content.ComponentName componentName2) {
            com.android.server.vr.VrManagerService.this.setVrMode(z, componentName, i, i2, componentName2);
        }

        @Override // com.android.server.vr.VrManagerInternal
        public void onScreenStateChanged(boolean z) {
            com.android.server.vr.VrManagerService.this.setScreenOn(z);
        }

        @Override // com.android.server.vr.VrManagerInternal
        public boolean isCurrentVrListener(java.lang.String str, int i) {
            return com.android.server.vr.VrManagerService.this.isCurrentVrListener(str, i);
        }

        @Override // com.android.server.vr.VrManagerInternal
        public int hasVrPackage(android.content.ComponentName componentName, int i) {
            return com.android.server.vr.VrManagerService.this.hasVrPackage(componentName, i);
        }

        @Override // com.android.server.vr.VrManagerInternal
        public void setPersistentVrModeEnabled(boolean z) {
            com.android.server.vr.VrManagerService.this.setPersistentVrModeEnabled(z);
        }

        @Override // com.android.server.vr.VrManagerInternal
        public void setVr2dDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties) {
            com.android.server.vr.VrManagerService.this.setVr2dDisplayProperties(vr2dDisplayProperties);
        }

        @Override // com.android.server.vr.VrManagerInternal
        public int getVr2dDisplayId() {
            return com.android.server.vr.VrManagerService.this.getVr2dDisplayId();
        }

        @Override // com.android.server.vr.VrManagerInternal
        public void addPersistentVrModeStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) {
            com.android.server.vr.VrManagerService.this.addPersistentStateCallback(iPersistentVrStateCallbacks);
        }
    }

    public VrManagerService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mOverlayToken = new android.os.Binder();
        this.mVrStateRemoteCallbacks = new android.os.RemoteCallbackList<>();
        this.mPersistentVrStateRemoteCallbacks = new android.os.RemoteCallbackList<>();
        this.mPreviousCoarseLocationMode = -1;
        this.mPreviousManageOverlayMode = -1;
        this.mLoggingDeque = new java.util.ArrayDeque<>(64);
        this.mNotifAccessManager = new com.android.server.vr.VrManagerService.NotificationAccessManager();
        this.mSystemSleepFlags = 5;
        this.mEventCallback = new com.android.server.utils.ManagedApplicationService.EventCallback() { // from class: com.android.server.vr.VrManagerService.1
            @Override // com.android.server.utils.ManagedApplicationService.EventCallback
            public void onServiceEvent(com.android.server.utils.ManagedApplicationService.LogEvent logEvent) {
                android.content.ComponentName component;
                com.android.server.vr.VrManagerService.this.logEvent(logEvent);
                synchronized (com.android.server.vr.VrManagerService.this.mLock) {
                    try {
                        component = com.android.server.vr.VrManagerService.this.mCurrentVrService == null ? null : com.android.server.vr.VrManagerService.this.mCurrentVrService.getComponent();
                        if (component != null && component.equals(logEvent.component) && (logEvent.event == 2 || logEvent.event == 3)) {
                            com.android.server.vr.VrManagerService.this.callFocusedActivityChangedLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (!com.android.server.vr.VrManagerService.this.mBootsToVr && logEvent.event == 4) {
                    if (component == null || component.equals(logEvent.component)) {
                        android.util.Slog.e(com.android.server.vr.VrManagerService.TAG, "VrListenerSevice has died permanently, leaving system VR mode.");
                        com.android.server.vr.VrManagerService.this.setPersistentVrModeEnabled(false);
                    }
                }
            }
        };
        this.mHandler = new android.os.Handler() { // from class: com.android.server.vr.VrManagerService.2
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                boolean z;
                switch (message.what) {
                    case 0:
                        z = message.arg1 == 1;
                        int beginBroadcast = com.android.server.vr.VrManagerService.this.mVrStateRemoteCallbacks.beginBroadcast();
                        while (beginBroadcast > 0) {
                            beginBroadcast--;
                            try {
                                com.android.server.vr.VrManagerService.this.mVrStateRemoteCallbacks.getBroadcastItem(beginBroadcast).onVrStateChanged(z);
                            } catch (android.os.RemoteException e) {
                            }
                        }
                        com.android.server.vr.VrManagerService.this.mVrStateRemoteCallbacks.finishBroadcast();
                        return;
                    case 1:
                        synchronized (com.android.server.vr.VrManagerService.this.mLock) {
                            try {
                                if (com.android.server.vr.VrManagerService.this.mVrModeAllowed) {
                                    com.android.server.vr.VrManagerService.this.consumeAndApplyPendingStateLocked();
                                }
                            } finally {
                            }
                        }
                        return;
                    case 2:
                        z = message.arg1 == 1;
                        int beginBroadcast2 = com.android.server.vr.VrManagerService.this.mPersistentVrStateRemoteCallbacks.beginBroadcast();
                        while (beginBroadcast2 > 0) {
                            beginBroadcast2--;
                            try {
                                com.android.server.vr.VrManagerService.this.mPersistentVrStateRemoteCallbacks.getBroadcastItem(beginBroadcast2).onPersistentVrStateChanged(z);
                            } catch (android.os.RemoteException e2) {
                            }
                        }
                        com.android.server.vr.VrManagerService.this.mPersistentVrStateRemoteCallbacks.finishBroadcast();
                        return;
                    default:
                        throw new java.lang.IllegalStateException("Unknown message type: " + message.what);
                }
            }
        };
        this.mVrManager = new android.service.vr.IVrManager.Stub() { // from class: com.android.server.vr.VrManagerService.4
            public void registerListener(android.service.vr.IVrStateCallbacks iVrStateCallbacks) {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE");
                if (iVrStateCallbacks == null) {
                    throw new java.lang.IllegalArgumentException("Callback binder object is null.");
                }
                com.android.server.vr.VrManagerService.this.addStateCallback(iVrStateCallbacks);
            }

            public void unregisterListener(android.service.vr.IVrStateCallbacks iVrStateCallbacks) {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE");
                if (iVrStateCallbacks == null) {
                    throw new java.lang.IllegalArgumentException("Callback binder object is null.");
                }
                com.android.server.vr.VrManagerService.this.removeStateCallback(iVrStateCallbacks);
            }

            public void registerPersistentVrStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE");
                if (iPersistentVrStateCallbacks == null) {
                    throw new java.lang.IllegalArgumentException("Callback binder object is null.");
                }
                com.android.server.vr.VrManagerService.this.addPersistentStateCallback(iPersistentVrStateCallbacks);
            }

            public void unregisterPersistentVrStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE");
                if (iPersistentVrStateCallbacks == null) {
                    throw new java.lang.IllegalArgumentException("Callback binder object is null.");
                }
                com.android.server.vr.VrManagerService.this.removePersistentStateCallback(iPersistentVrStateCallbacks);
            }

            public boolean getVrModeState() {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE");
                return com.android.server.vr.VrManagerService.this.getVrMode();
            }

            public boolean getPersistentVrModeEnabled() {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE");
                return com.android.server.vr.VrManagerService.this.getPersistentVrMode();
            }

            public void setPersistentVrModeEnabled(boolean z) {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.RESTRICTED_VR_ACCESS");
                com.android.server.vr.VrManagerService.this.setPersistentVrModeEnabled(z);
            }

            public void setVr2dDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties) {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.RESTRICTED_VR_ACCESS");
                com.android.server.vr.VrManagerService.this.setVr2dDisplayProperties(vr2dDisplayProperties);
            }

            public int getVr2dDisplayId() {
                return com.android.server.vr.VrManagerService.this.getVr2dDisplayId();
            }

            public void setAndBindCompositor(java.lang.String str) {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.RESTRICTED_VR_ACCESS");
                com.android.server.vr.VrManagerService.this.setAndBindCompositor(str == null ? null : android.content.ComponentName.unflattenFromString(str));
            }

            public void setStandbyEnabled(boolean z) {
                com.android.server.vr.VrManagerService.this.enforceCallerPermissionAnyOf("android.permission.ACCESS_VR_MANAGER");
                com.android.server.vr.VrManagerService.this.setStandbyEnabled(z);
            }

            protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
                if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.vr.VrManagerService.this.mContext, com.android.server.vr.VrManagerService.TAG, printWriter)) {
                    printWriter.println("********* Dump of VrManagerService *********");
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("VR mode is currently: ");
                    sb.append(com.android.server.vr.VrManagerService.this.mVrModeAllowed ? "allowed" : "disallowed");
                    printWriter.println(sb.toString());
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    sb2.append("Persistent VR mode is currently: ");
                    sb2.append(com.android.server.vr.VrManagerService.this.mPersistentVrModeEnabled ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
                    printWriter.println(sb2.toString());
                    java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                    sb3.append("Currently bound VR listener service: ");
                    sb3.append(com.android.server.vr.VrManagerService.this.mCurrentVrService == null ? com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : com.android.server.vr.VrManagerService.this.mCurrentVrService.getComponent().flattenToString());
                    printWriter.println(sb3.toString());
                    java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
                    sb4.append("Currently bound VR compositor service: ");
                    sb4.append(com.android.server.vr.VrManagerService.this.mCurrentVrCompositorService == null ? com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : com.android.server.vr.VrManagerService.this.mCurrentVrCompositorService.getComponent().flattenToString());
                    printWriter.println(sb4.toString());
                    printWriter.println("Previous state transitions:\n");
                    com.android.server.vr.VrManagerService.this.dumpStateTransitions(printWriter);
                    printWriter.println("\n\nRemote Callbacks:");
                    int beginBroadcast = com.android.server.vr.VrManagerService.this.mVrStateRemoteCallbacks.beginBroadcast();
                    while (true) {
                        int i = beginBroadcast - 1;
                        if (beginBroadcast <= 0) {
                            break;
                        }
                        printWriter.print("  ");
                        printWriter.print(com.android.server.vr.VrManagerService.this.mVrStateRemoteCallbacks.getBroadcastItem(i));
                        if (i > 0) {
                            printWriter.println(",");
                        }
                        beginBroadcast = i;
                    }
                    com.android.server.vr.VrManagerService.this.mVrStateRemoteCallbacks.finishBroadcast();
                    printWriter.println("\n\nPersistent Vr State Remote Callbacks:");
                    int beginBroadcast2 = com.android.server.vr.VrManagerService.this.mPersistentVrStateRemoteCallbacks.beginBroadcast();
                    while (true) {
                        int i2 = beginBroadcast2 - 1;
                        if (beginBroadcast2 <= 0) {
                            break;
                        }
                        printWriter.print("  ");
                        printWriter.print(com.android.server.vr.VrManagerService.this.mPersistentVrStateRemoteCallbacks.getBroadcastItem(i2));
                        if (i2 > 0) {
                            printWriter.println(",");
                        }
                        beginBroadcast2 = i2;
                    }
                    com.android.server.vr.VrManagerService.this.mPersistentVrStateRemoteCallbacks.finishBroadcast();
                    printWriter.println("\n");
                    printWriter.println("Installed VrListenerService components:");
                    int i3 = com.android.server.vr.VrManagerService.this.mCurrentVrModeUser;
                    android.util.ArraySet<android.content.ComponentName> installed = com.android.server.vr.VrManagerService.this.mComponentObserver.getInstalled(i3);
                    if (installed == null || installed.size() == 0) {
                        printWriter.println(com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                    } else {
                        java.util.Iterator<android.content.ComponentName> it = installed.iterator();
                        while (it.hasNext()) {
                            android.content.ComponentName next = it.next();
                            printWriter.print("  ");
                            printWriter.println(next.flattenToString());
                        }
                    }
                    printWriter.println("Enabled VrListenerService components:");
                    android.util.ArraySet<android.content.ComponentName> enabled = com.android.server.vr.VrManagerService.this.mComponentObserver.getEnabled(i3);
                    if (enabled == null || enabled.size() == 0) {
                        printWriter.println(com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                    } else {
                        java.util.Iterator<android.content.ComponentName> it2 = enabled.iterator();
                        while (it2.hasNext()) {
                            android.content.ComponentName next2 = it2.next();
                            printWriter.print("  ");
                            printWriter.println(next2.flattenToString());
                        }
                    }
                    printWriter.println("\n");
                    printWriter.println("********* End of VrManagerService Dump *********");
                }
            }
        };
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        synchronized (this.mLock) {
            initializeNative();
            this.mContext = getContext();
        }
        boolean z = false;
        this.mBootsToVr = android.os.SystemProperties.getBoolean("ro.boot.vr", false);
        if (this.mBootsToVr && android.os.SystemProperties.getBoolean("persist.vr.use_standby_to_exit_vr_mode", true)) {
            z = true;
        }
        this.mUseStandbyToExitVrMode = z;
        publishLocalService(com.android.server.vr.VrManagerInternal.class, new com.android.server.vr.VrManagerService.LocalService());
        publishBinderService("vrmanager", this.mVrManager.asBinder());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).registerScreenObserver(this);
            this.mNotificationManager = android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
            synchronized (this.mLock) {
                android.os.Looper mainLooper = android.os.Looper.getMainLooper();
                android.os.Handler handler = new android.os.Handler(mainLooper);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(this);
                this.mComponentObserver = com.android.server.vr.EnabledComponentsObserver.build(this.mContext, handler, "enabled_vr_listeners", mainLooper, "android.permission.BIND_VR_LISTENER_SERVICE", "android.service.vr.VrListenerService", this.mLock, arrayList);
                this.mComponentObserver.rebuildAll();
            }
            android.util.ArraySet<android.content.ComponentName> defaultVrComponents = com.android.server.SystemConfig.getInstance().getDefaultVrComponents();
            if (defaultVrComponents.size() > 0) {
                this.mDefaultVrService = defaultVrComponents.valueAt(0);
            } else {
                android.util.Slog.i(TAG, "No default vr listener service found.");
            }
            this.mVr2dDisplay = new com.android.server.vr.Vr2dDisplay((android.hardware.display.DisplayManager) getContext().getSystemService("display"), (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class), (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class), this.mVrManager);
            this.mVr2dDisplay.init(getContext(), this.mBootsToVr);
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.USER_UNLOCKED");
            getContext().registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.vr.VrManagerService.5
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                        com.android.server.vr.VrManagerService.this.setUserUnlocked();
                    }
                }
            }, intentFilter);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mComponentObserver.onUsersChanged();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.vr.VrManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.vr.VrManagerService.this.lambda$onUserSwitching$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserSwitching$0() {
        synchronized (this.mLock) {
            this.mComponentObserver.onUsersChanged();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mComponentObserver.onUsersChanged();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mComponentObserver.onUsersChanged();
        }
    }

    private void updateOverlayStateLocked(java.lang.String str, int i, int i2) {
        android.os.PackageTagsList packageTagsList;
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) getContext().getSystemService(android.app.AppOpsManager.class);
        if (i2 != i) {
            appOpsManager.setUserRestrictionForUser(24, false, this.mOverlayToken, null, i2);
        }
        if (str == null) {
            packageTagsList = null;
        } else {
            packageTagsList = new android.os.PackageTagsList.Builder(1).add(str).build();
        }
        appOpsManager.setUserRestrictionForUser(24, this.mVrModeEnabled, this.mOverlayToken, packageTagsList, i);
    }

    private void updateDependentAppOpsLocked(java.lang.String str, int i, java.lang.String str2, int i2) {
        if (java.util.Objects.equals(str, str2)) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            updateOverlayStateLocked(str, i, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean updateCurrentVrServiceLocked(boolean z, boolean z2, @android.annotation.NonNull android.content.ComponentName componentName, int i, int i2, android.content.ComponentName componentName2) {
        boolean z3;
        boolean z4;
        boolean z5;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            boolean z6 = this.mComponentObserver.isValid(componentName, i) == 0;
            boolean z7 = z6 && z;
            if (!this.mVrModeEnabled && !z7) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return z6;
            }
            java.lang.String packageName = this.mCurrentVrService != null ? this.mCurrentVrService.getComponent().getPackageName() : null;
            int i3 = this.mCurrentVrModeUser;
            changeVrModeLocked(z7);
            if (z7) {
                if (this.mCurrentVrService == null) {
                    createAndConnectService(componentName, i);
                    z3 = true;
                    z4 = false;
                } else if (this.mCurrentVrService.disconnectIfNotMatching(componentName, i)) {
                    android.util.Slog.i(TAG, "VR mode component changed to " + componentName + ", disconnecting " + this.mCurrentVrService.getComponent() + " for user " + this.mCurrentVrService.getUserId());
                    updateCompositorServiceLocked(com.android.server.am.ProcessList.INVALID_ADJ, null);
                    createAndConnectService(componentName, i);
                    z3 = true;
                    z4 = false;
                } else {
                    z3 = false;
                    z4 = true;
                }
            } else if (this.mCurrentVrService != null) {
                android.util.Slog.i(TAG, "Leaving VR mode, disconnecting " + this.mCurrentVrService.getComponent() + " for user " + this.mCurrentVrService.getUserId());
                this.mCurrentVrService.disconnect();
                updateCompositorServiceLocked(com.android.server.am.ProcessList.INVALID_ADJ, null);
                this.mCurrentVrService = null;
                z3 = false;
                z4 = false;
            } else {
                z3 = false;
                z4 = true;
            }
            if (((componentName2 != null || this.mPersistentVrModeEnabled) && !java.util.Objects.equals(componentName2, this.mCurrentVrModeComponent)) || this.mRunning2dInVr != z2) {
                z3 = true;
            }
            this.mCurrentVrModeComponent = componentName2;
            this.mRunning2dInVr = z2;
            this.mVrAppProcessId = i2;
            if (this.mCurrentVrModeUser != i) {
                this.mCurrentVrModeUser = i;
                z5 = true;
            } else {
                z5 = z3;
            }
            updateDependentAppOpsLocked(this.mCurrentVrService != null ? this.mCurrentVrService.getComponent().getPackageName() : null, this.mCurrentVrModeUser, packageName, i3);
            if (this.mCurrentVrService != null && z5) {
                callFocusedActivityChangedLocked();
            }
            if (!z4) {
                logStateLocked();
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return z6;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callFocusedActivityChangedLocked() {
        final android.content.ComponentName componentName = this.mCurrentVrModeComponent;
        final boolean z = this.mRunning2dInVr;
        final int i = this.mVrAppProcessId;
        this.mCurrentVrService.sendEvent(new com.android.server.utils.ManagedApplicationService.PendingEvent() { // from class: com.android.server.vr.VrManagerService.6
            @Override // com.android.server.utils.ManagedApplicationService.PendingEvent
            public void runEvent(android.os.IInterface iInterface) throws android.os.RemoteException {
                ((android.service.vr.IVrListener) iInterface).focusedActivityChanged(componentName, z, i);
            }
        });
    }

    private boolean isDefaultAllowed(java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo;
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 128);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return false;
        }
        if (!applicationInfo.isSystemApp() && !applicationInfo.isUpdatedSystemApp()) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void grantNotificationPolicyAccess(java.lang.String str) {
        ((android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class)).setNotificationPolicyAccessGranted(str, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void revokeNotificationPolicyAccess(java.lang.String str) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        notificationManager.removeAutomaticZenRules(str);
        notificationManager.setNotificationPolicyAccessGranted(str, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void grantNotificationListenerAccess(java.lang.String str, int i) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        java.util.Iterator<android.content.ComponentName> it = com.android.server.vr.EnabledComponentsObserver.loadComponentNames(this.mContext.getPackageManager(), i, "android.service.notification.NotificationListenerService", "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE").iterator();
        while (it.hasNext()) {
            android.content.ComponentName next = it.next();
            if (java.util.Objects.equals(next.getPackageName(), str)) {
                try {
                    notificationManager.setNotificationListenerAccessGrantedForUser(next, i, true);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(TAG, "Could not grant NLS access to package " + str, e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void revokeNotificationListenerAccess(java.lang.String str, int i) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        for (android.content.ComponentName componentName : notificationManager.getEnabledNotificationListeners(i)) {
            if (componentName != null && componentName.getPackageName().equals(str)) {
                notificationManager.setNotificationListenerAccessGrantedForUser(componentName, i, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void grantCoarseLocationPermissionIfNeeded(java.lang.String str, int i) {
        if (!isPermissionUserUpdated("android.permission.ACCESS_COARSE_LOCATION", str, i)) {
            try {
                this.mContext.getPackageManager().grantRuntimePermission(str, "android.permission.ACCESS_COARSE_LOCATION", new android.os.UserHandle(i));
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.w(TAG, "Could not grant coarse location permission, package " + str + " was removed.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void revokeCoarseLocationPermissionIfNeeded(java.lang.String str, int i) {
        if (!isPermissionUserUpdated("android.permission.ACCESS_COARSE_LOCATION", str, i)) {
            try {
                this.mContext.getPackageManager().revokeRuntimePermission(str, "android.permission.ACCESS_COARSE_LOCATION", new android.os.UserHandle(i));
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.w(TAG, "Could not revoke coarse location permission, package " + str + " was removed.");
            }
        }
    }

    private boolean isPermissionUserUpdated(java.lang.String str, java.lang.String str2, int i) {
        return (this.mContext.getPackageManager().getPermissionFlags(str, str2, new android.os.UserHandle(i)) & 3) != 0;
    }

    private android.util.ArraySet<java.lang.String> getNotificationListeners(android.content.ContentResolver contentResolver, int i) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(contentResolver, "enabled_notification_listeners", i);
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        if (stringForUser != null) {
            for (java.lang.String str : stringForUser.split(":")) {
                if (!android.text.TextUtils.isEmpty(str)) {
                    arraySet.add(str);
                }
            }
        }
        return arraySet;
    }

    private static java.lang.String formatSettings(java.util.Collection<java.lang.String> collection) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        boolean z = true;
        for (java.lang.String str : collection) {
            if (!"".equals(str)) {
                if (!z) {
                    sb.append(':');
                }
                sb.append(str);
                z = false;
            }
        }
        return sb.toString();
    }

    private void createAndConnectService(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        this.mCurrentVrService = createVrListenerService(componentName, i);
        this.mCurrentVrService.connect();
        android.util.Slog.i(TAG, "Connecting " + componentName + " for user " + i);
    }

    private void changeVrModeLocked(boolean z) {
        if (this.mVrModeEnabled != z) {
            this.mVrModeEnabled = z;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("VR mode ");
            sb.append(this.mVrModeEnabled ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
            android.util.Slog.i(TAG, sb.toString());
            setVrModeNative(this.mVrModeEnabled);
            onVrModeChangedLocked();
        }
    }

    private void onVrModeChangedLocked() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(0, this.mVrModeEnabled ? 1 : 0, 0));
    }

    private com.android.server.utils.ManagedApplicationService createVrListenerService(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        return com.android.server.utils.ManagedApplicationService.build(this.mContext, componentName, i, android.R.string.user_switched, "android.settings.VR_LISTENER_SETTINGS", sBinderChecker, true, this.mBootsToVr ? 1 : 2, this.mHandler, this.mEventCallback);
    }

    private com.android.server.utils.ManagedApplicationService createVrCompositorService(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        return com.android.server.utils.ManagedApplicationService.build(this.mContext, componentName, i, 0, null, null, true, this.mBootsToVr ? 1 : 3, this.mHandler, this.mEventCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void consumeAndApplyPendingStateLocked() {
        consumeAndApplyPendingStateLocked(true);
    }

    private void consumeAndApplyPendingStateLocked(boolean z) {
        if (this.mPendingState != null) {
            updateCurrentVrServiceLocked(this.mPendingState.enabled, this.mPendingState.running2dInVr, this.mPendingState.targetPackageName, this.mPendingState.userId, this.mPendingState.processId, this.mPendingState.callingPackage);
            this.mPendingState = null;
        } else if (z) {
            updateCurrentVrServiceLocked(false, false, null, 0, -1, null);
        }
    }

    private void logStateLocked() {
        logEvent(new com.android.server.vr.VrManagerService.VrState(this.mVrModeEnabled, this.mRunning2dInVr, this.mCurrentVrService == null ? null : this.mCurrentVrService.getComponent(), this.mCurrentVrModeUser, this.mVrAppProcessId, this.mCurrentVrModeComponent, this.mWasDefaultGranted));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logEvent(com.android.server.utils.ManagedApplicationService.LogFormattable logFormattable) {
        synchronized (this.mLoggingDeque) {
            try {
                if (this.mLoggingDeque.size() == 64) {
                    this.mLoggingDeque.removeFirst();
                    this.mLogLimitHit = true;
                }
                this.mLoggingDeque.add(logFormattable);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpStateTransitions(java.io.PrintWriter printWriter) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        synchronized (this.mLoggingDeque) {
            try {
                if (this.mLoggingDeque.size() == 0) {
                    printWriter.print("  ");
                    printWriter.println(com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                }
                if (this.mLogLimitHit) {
                    printWriter.println("...");
                }
                java.util.Iterator<com.android.server.utils.ManagedApplicationService.LogFormattable> it = this.mLoggingDeque.iterator();
                while (it.hasNext()) {
                    printWriter.println(it.next().toLogString(simpleDateFormat));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001f A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:34:0x0009, B:8:0x0016, B:12:0x001f, B:13:0x0025, B:15:0x0038, B:16:0x003a, B:20:0x003e, B:22:0x0042, B:24:0x0046, B:25:0x004d, B:26:0x004f, B:28:0x0051, B:29:0x0067), top: B:33:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038 A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:34:0x0009, B:8:0x0016, B:12:0x001f, B:13:0x0025, B:15:0x0038, B:16:0x003a, B:20:0x003e, B:22:0x0042, B:24:0x0046, B:25:0x004d, B:26:0x004f, B:28:0x0051, B:29:0x0067), top: B:33:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setVrMode(boolean z, @android.annotation.NonNull android.content.ComponentName componentName, int i, int i2, @android.annotation.NonNull android.content.ComponentName componentName2) {
        boolean z2;
        boolean z3;
        android.content.ComponentName componentName3;
        synchronized (this.mLock) {
            if (!z) {
                try {
                    if (!this.mPersistentVrModeEnabled) {
                        z2 = false;
                        z3 = z && this.mPersistentVrModeEnabled;
                        if (!z3) {
                            componentName3 = this.mDefaultVrService;
                        } else {
                            componentName3 = componentName;
                        }
                        com.android.server.vr.VrManagerService.VrState vrState = new com.android.server.vr.VrManagerService.VrState(z2, z3, componentName3, i, i2, componentName2);
                        if (this.mVrModeAllowed) {
                            this.mPendingState = vrState;
                            return;
                        }
                        if (!z2 && this.mCurrentVrService != null) {
                            if (this.mPendingState == null) {
                                this.mHandler.sendEmptyMessageDelayed(1, 300L);
                            }
                            this.mPendingState = vrState;
                            return;
                        } else {
                            this.mHandler.removeMessages(1);
                            this.mPendingState = null;
                            updateCurrentVrServiceLocked(z2, z3, componentName3, i, i2, componentName2);
                            return;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            z2 = true;
            if (z) {
            }
            if (!z3) {
            }
            com.android.server.vr.VrManagerService.VrState vrState2 = new com.android.server.vr.VrManagerService.VrState(z2, z3, componentName3, i, i2, componentName2);
            if (this.mVrModeAllowed) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPersistentVrModeEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                setPersistentModeAndNotifyListenersLocked(z);
                if (!z) {
                    setVrMode(false, null, 0, -1, null);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setVr2dDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mVr2dDisplay != null) {
                this.mVr2dDisplay.setVirtualDisplayProperties(vr2dDisplayProperties);
            } else {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                android.util.Slog.w(TAG, "Vr2dDisplay is null!");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVr2dDisplayId() {
        if (this.mVr2dDisplay != null) {
            return this.mVr2dDisplay.getVirtualDisplayId();
        }
        android.util.Slog.w(TAG, "Vr2dDisplay is null!");
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAndBindCompositor(android.content.ComponentName componentName) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                updateCompositorServiceLocked(callingUserId, componentName);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void updateCompositorServiceLocked(int i, android.content.ComponentName componentName) {
        if (this.mCurrentVrCompositorService != null && this.mCurrentVrCompositorService.disconnectIfNotMatching(componentName, i)) {
            android.util.Slog.i(TAG, "Disconnecting compositor service: " + this.mCurrentVrCompositorService.getComponent());
            this.mCurrentVrCompositorService = null;
        }
        if (componentName != null && this.mCurrentVrCompositorService == null) {
            android.util.Slog.i(TAG, "Connecting compositor service: " + componentName);
            this.mCurrentVrCompositorService = createVrCompositorService(componentName, i);
            this.mCurrentVrCompositorService.connect();
        }
    }

    private void setPersistentModeAndNotifyListenersLocked(boolean z) {
        if (this.mPersistentVrModeEnabled == z) {
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Persistent VR mode ");
        sb.append(z ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
        java.lang.String sb2 = sb.toString();
        android.util.Slog.i(TAG, sb2);
        logEvent(new com.android.server.vr.VrManagerService.SettingEvent(sb2));
        this.mPersistentVrModeEnabled = z;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, this.mPersistentVrModeEnabled ? 1 : 0, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int hasVrPackage(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        int isValid;
        synchronized (this.mLock) {
            isValid = this.mComponentObserver.isValid(componentName, i);
        }
        return isValid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCurrentVrListener(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                boolean z = false;
                if (this.mCurrentVrService == null) {
                    return false;
                }
                if (this.mCurrentVrService.getComponent().getPackageName().equals(str) && i == this.mCurrentVrService.getUserId()) {
                    z = true;
                }
                return z;
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addStateCallback(android.service.vr.IVrStateCallbacks iVrStateCallbacks) {
        this.mVrStateRemoteCallbacks.register(iVrStateCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeStateCallback(android.service.vr.IVrStateCallbacks iVrStateCallbacks) {
        this.mVrStateRemoteCallbacks.unregister(iVrStateCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addPersistentStateCallback(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) {
        this.mPersistentVrStateRemoteCallbacks.register(iPersistentVrStateCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removePersistentStateCallback(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks) {
        this.mPersistentVrStateRemoteCallbacks.unregister(iPersistentVrStateCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getVrMode() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mVrModeEnabled;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getPersistentVrMode() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPersistentVrModeEnabled;
        }
        return z;
    }
}
