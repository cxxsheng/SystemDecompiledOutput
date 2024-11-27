package com.android.server.clipboard;

/* loaded from: classes.dex */
public class ClipboardService extends com.android.server.SystemService {

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_CLIPBOARD_TIMEOUT_MILLIS = 3600000;
    private static final int DEFAULT_MAX_CLASSIFICATION_LENGTH = 400;
    public static final java.lang.String PROPERTY_AUTO_CLEAR_ENABLED = "auto_clear_enabled";
    public static final java.lang.String PROPERTY_AUTO_CLEAR_TIMEOUT = "auto_clear_timeout";
    private static final java.lang.String PROPERTY_MAX_CLASSIFICATION_LENGTH = "max_classification_length";
    private static final java.lang.String TAG = "ClipboardService";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mAllowVirtualDeviceSilos;
    private final android.app.ActivityManagerInternal mAmInternal;
    private final android.app.AppOpsManager mAppOps;
    private final android.view.autofill.AutofillManagerInternal mAutofillInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.Integer, com.android.server.clipboard.ClipboardService.Clipboard> mClipboards;
    private final com.android.server.contentcapture.ContentCaptureManagerInternal mContentCaptureInternal;
    private final java.util.function.Consumer<android.content.ClipData> mEmulatorClipboardMonitor;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mMaxClassificationLength;
    private final android.os.IBinder mPermissionOwner;
    private final android.content.pm.PackageManager mPm;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mShowAccessNotifications;
    private final android.app.IUriGrantsManager mUgm;
    private final com.android.server.uri.UriGrantsManagerInternal mUgmInternal;
    private final android.os.IUserManager mUm;
    private final android.companion.virtual.VirtualDeviceManager mVdm;
    private final com.android.server.companion.virtual.VirtualDeviceManagerInternal mVdmInternal;
    private android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener mVirtualDeviceListener;
    private android.content.BroadcastReceiver mVirtualDeviceRemovedReceiver;
    private final com.android.server.wm.WindowManagerInternal mWm;
    private final android.os.Handler mWorkerHandler;

    public ClipboardService(android.content.Context context) {
        super(context);
        this.mClipboards = new android.util.SparseArrayMap<>();
        this.mShowAccessNotifications = true;
        this.mAllowVirtualDeviceSilos = true;
        this.mMaxClassificationLength = 400;
        this.mLock = new java.lang.Object();
        this.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mUgm = android.app.UriGrantsManager.getService();
        this.mUgmInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
        this.mWm = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mVdmInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        this.mVdm = this.mVdmInternal == null ? null : (android.companion.virtual.VirtualDeviceManager) getContext().getSystemService(android.companion.virtual.VirtualDeviceManager.class);
        this.mPm = getContext().getPackageManager();
        this.mUm = android.os.ServiceManager.getService("user");
        this.mAppOps = (android.app.AppOpsManager) getContext().getSystemService("appops");
        this.mContentCaptureInternal = (com.android.server.contentcapture.ContentCaptureManagerInternal) com.android.server.LocalServices.getService(com.android.server.contentcapture.ContentCaptureManagerInternal.class);
        this.mAutofillInternal = (android.view.autofill.AutofillManagerInternal) com.android.server.LocalServices.getService(android.view.autofill.AutofillManagerInternal.class);
        this.mPermissionOwner = this.mUgmInternal.newUriPermissionOwner("clipboard");
        if (android.os.Build.IS_EMULATOR) {
            this.mEmulatorClipboardMonitor = new com.android.server.clipboard.EmulatorClipboardMonitor(new java.util.function.Consumer() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.clipboard.ClipboardService.this.lambda$new$0((android.content.ClipData) obj);
                }
            });
        } else {
            this.mEmulatorClipboardMonitor = new java.util.function.Consumer() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.clipboard.ClipboardService.lambda$new$1((android.content.ClipData) obj);
                }
            };
        }
        updateConfig();
        android.provider.DeviceConfig.addOnPropertiesChangedListener("clipboard", getContext().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda5
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.clipboard.ClipboardService.this.lambda$new$2(properties);
            }
        });
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mWorkerHandler = handlerThread.getThreadHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.content.ClipData clipData) {
        synchronized (this.mLock) {
            try {
                com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = getClipboardLocked(0, 0);
                if (clipboardLocked != null) {
                    setPrimaryClipInternalLocked(clipboardLocked, clipData, 1000, (java.lang.String) null);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$1(android.content.ClipData clipData) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(android.provider.DeviceConfig.Properties properties) {
        updateConfig();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("clipboard", new com.android.server.clipboard.ClipboardService.ClipboardImpl());
        if (!android.companion.virtual.flags.Flags.vdmPublicApis() && this.mVdmInternal != null) {
            registerVirtualDeviceBroadcastReceiver();
        } else if (android.companion.virtual.flags.Flags.vdmPublicApis() && this.mVdm != null) {
            registerVirtualDeviceListener();
        }
    }

    private void registerVirtualDeviceBroadcastReceiver() {
        if (this.mVirtualDeviceRemovedReceiver != null) {
            return;
        }
        this.mVirtualDeviceRemovedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.clipboard.ClipboardService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (!intent.getAction().equals("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED")) {
                    return;
                }
                int intExtra = intent.getIntExtra("android.companion.virtual.extra.VIRTUAL_DEVICE_ID", -1);
                synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                    try {
                        for (int numMaps = com.android.server.clipboard.ClipboardService.this.mClipboards.numMaps() - 1; numMaps >= 0; numMaps--) {
                            com.android.server.clipboard.ClipboardService.this.mClipboards.delete(com.android.server.clipboard.ClipboardService.this.mClipboards.keyAt(numMaps), java.lang.Integer.valueOf(intExtra));
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        getContext().registerReceiver(this.mVirtualDeviceRemovedReceiver, new android.content.IntentFilter("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED"), 4);
    }

    private void registerVirtualDeviceListener() {
        if (this.mVirtualDeviceListener != null) {
            return;
        }
        this.mVirtualDeviceListener = new android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener() { // from class: com.android.server.clipboard.ClipboardService.2
            public void onVirtualDeviceClosed(int i) {
                synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                    try {
                        for (int numMaps = com.android.server.clipboard.ClipboardService.this.mClipboards.numMaps() - 1; numMaps >= 0; numMaps--) {
                            com.android.server.clipboard.ClipboardService.this.mClipboards.delete(com.android.server.clipboard.ClipboardService.this.mClipboards.keyAt(numMaps), java.lang.Integer.valueOf(i));
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mVdm.registerVirtualDeviceListener(getContext().getMainExecutor(), this.mVirtualDeviceListener);
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mClipboards.delete(targetUser.getUserIdentifier());
        }
    }

    private void updateConfig() {
        synchronized (this.mLock) {
            this.mShowAccessNotifications = android.provider.DeviceConfig.getBoolean("clipboard", "show_access_notifications", true);
            this.mAllowVirtualDeviceSilos = android.provider.DeviceConfig.getBoolean("clipboard", "allow_virtualdevice_silos", true);
            this.mMaxClassificationLength = android.provider.DeviceConfig.getInt("clipboard", PROPERTY_MAX_CLASSIFICATION_LENGTH, 400);
        }
    }

    private class ListenerInfo {
        final java.lang.String mAttributionTag;
        final java.lang.String mPackageName;
        final int mUid;

        ListenerInfo(int i, java.lang.String str, java.lang.String str2) {
            this.mUid = i;
            this.mPackageName = str;
            this.mAttributionTag = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Clipboard {
        public final int deviceId;
        java.lang.String mPrimaryClipPackage;
        android.view.textclassifier.TextClassifier mTextClassifier;
        android.content.ClipData primaryClip;
        public final int userId;
        final android.os.RemoteCallbackList<android.content.IOnPrimaryClipChangedListener> primaryClipListeners = new android.os.RemoteCallbackList<>();
        int primaryClipUid = 9999;
        final android.util.SparseBooleanArray mNotifiedUids = new android.util.SparseBooleanArray();
        final android.util.SparseBooleanArray mNotifiedTextClassifierUids = new android.util.SparseBooleanArray();
        final java.util.HashSet<java.lang.String> activePermissionOwners = new java.util.HashSet<>();

        Clipboard(int i, int i2) {
            this.userId = i;
            this.deviceId = i2;
        }
    }

    private boolean isInternalSysWindowAppWithWindowFocus(java.lang.String str) {
        if (this.mPm.checkPermission("android.permission.INTERNAL_SYSTEM_WINDOW", str) == 0 && this.mWm.isUidFocused(android.os.Binder.getCallingUid())) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIntendingUserId(java.lang.String str, int i) {
        int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
        if (!android.os.UserManager.supportsMultipleUsers() || userId == i) {
            return userId;
        }
        return this.mAmInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 2, "checkClipboardServiceCallingUser", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIntendingUid(java.lang.String str, int i) {
        return android.os.UserHandle.getUid(getIntendingUserId(str, i), android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIntendingDeviceId(int i, int i2) {
        boolean z;
        int i3;
        if (this.mVdmInternal == null) {
            return 0;
        }
        android.util.ArraySet<java.lang.Integer> deviceIdsForUid = this.mVdmInternal.getDeviceIdsForUid(i2);
        synchronized (this.mLock) {
            try {
                if (!this.mAllowVirtualDeviceSilos && (!deviceIdsForUid.isEmpty() || i != 0)) {
                    return -1;
                }
                java.util.Iterator<java.lang.Integer> it = deviceIdsForUid.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = true;
                        break;
                    }
                    if (!deviceUsesDefaultClipboard(it.next().intValue())) {
                        z = false;
                        break;
                    }
                }
                if (i == 0) {
                    return z ? 0 : -1;
                }
                if (deviceUsesDefaultClipboard(i)) {
                    i3 = 0;
                } else {
                    i3 = i;
                }
                if (this.mVdmInternal.getDeviceOwnerUid(i) == i2 || deviceIdsForUid.contains(java.lang.Integer.valueOf(i)) || (i3 == 0 && z)) {
                    return i3;
                }
                int intValue = deviceIdsForUid.valueAt(0).intValue();
                if (deviceUsesDefaultClipboard(intValue)) {
                    return 0;
                }
                return intValue;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean deviceUsesDefaultClipboard(int i) {
        return i == 0 || this.mVdm == null || this.mVdm.getDevicePolicy(i, 4) == 1;
    }

    private class ClipboardImpl extends android.content.IClipboard.Stub {
        private final android.os.Handler mClipboardClearHandler;

        private ClipboardImpl() {
            this.mClipboardClearHandler = new com.android.server.clipboard.ClipboardService.ClipboardImpl.ClipboardClearHandler(com.android.server.clipboard.ClipboardService.this.mWorkerHandler.getLooper());
        }

        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (java.lang.RuntimeException e) {
                if (!(e instanceof java.lang.SecurityException)) {
                    android.util.Slog.wtf("clipboard", "Exception: ", e);
                }
                throw e;
            }
        }

        public void setPrimaryClip(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2) {
            checkAndSetPrimaryClip(clipData, str, str2, i, i2, str);
        }

        @android.annotation.EnforcePermission("android.permission.SET_CLIP_SOURCE")
        public void setPrimaryClipAsPackage(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) {
            setPrimaryClipAsPackage_enforcePermission();
            checkAndSetPrimaryClip(clipData, str, str2, i, i2, str3);
        }

        public boolean areClipboardAccessNotificationsEnabledForUser(int i) {
            if (com.android.server.clipboard.ClipboardService.this.getContext().checkCallingOrSelfPermission("android.permission.MANAGE_CLIPBOARD_ACCESS_NOTIFICATION") != 0) {
                throw new java.lang.SecurityException("areClipboardAccessNotificationsEnable requires permission MANAGE_CLIPBOARD_ACCESS_NOTIFICATION");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.provider.Settings.Secure.getIntForUser(com.android.server.clipboard.ClipboardService.this.getContext().getContentResolver(), "clipboard_show_access_notifications", getDefaultClipboardAccessNotificationsSetting(), i) != 0;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) {
            if (com.android.server.clipboard.ClipboardService.this.getContext().checkCallingOrSelfPermission("android.permission.MANAGE_CLIPBOARD_ACCESS_NOTIFICATION") != 0) {
                throw new java.lang.SecurityException("areClipboardAccessNotificationsEnable requires permission MANAGE_CLIPBOARD_ACCESS_NOTIFICATION");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.provider.Settings.Secure.putInt(com.android.server.clipboard.ClipboardService.this.getContext().createContextAsUser(android.os.UserHandle.of(i), 0).getContentResolver(), "clipboard_show_access_notifications", z ? 1 : 0);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private int getDefaultClipboardAccessNotificationsSetting() {
            return android.provider.DeviceConfig.getBoolean("clipboard", "show_access_notifications", true) ? 1 : 0;
        }

        private void checkAndSetPrimaryClip(android.content.ClipData clipData, java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) {
            if (clipData == null || clipData.getItemCount() <= 0) {
                throw new java.lang.IllegalArgumentException("No items");
            }
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int userId = android.os.UserHandle.getUserId(intendingUid);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (!com.android.server.clipboard.ClipboardService.this.clipboardAccessAllowed(30, str, str2, intendingUid, userId, intendingDeviceId)) {
                return;
            }
            com.android.server.clipboard.ClipboardService.this.checkDataOwner(clipData, intendingUid);
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                scheduleAutoClear(i, intendingUid, intendingDeviceId);
                com.android.server.clipboard.ClipboardService.this.setPrimaryClipInternalLocked(clipData, intendingUid, intendingDeviceId, str3);
            }
        }

        private void scheduleAutoClear(int i, int i2, int i3) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (android.provider.DeviceConfig.getBoolean("clipboard", com.android.server.clipboard.ClipboardService.PROPERTY_AUTO_CLEAR_ENABLED, true)) {
                    android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i3));
                    this.mClipboardClearHandler.removeEqualMessages(101, pair);
                    this.mClipboardClearHandler.sendMessageDelayed(android.os.Message.obtain(this.mClipboardClearHandler, 101, i, i2, pair), getTimeoutForAutoClear());
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private long getTimeoutForAutoClear() {
            return android.provider.DeviceConfig.getLong("clipboard", com.android.server.clipboard.ClipboardService.PROPERTY_AUTO_CLEAR_TIMEOUT, 3600000L);
        }

        public void clearPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) {
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int userId = android.os.UserHandle.getUserId(intendingUid);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (!com.android.server.clipboard.ClipboardService.this.clipboardAccessAllowed(30, str, str2, intendingUid, userId, intendingDeviceId)) {
                return;
            }
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                this.mClipboardClearHandler.removeEqualMessages(101, new android.util.Pair(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
                com.android.server.clipboard.ClipboardService.this.setPrimaryClipInternalLocked((android.content.ClipData) null, intendingUid, intendingDeviceId, str);
            }
        }

        public android.content.ClipData getPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) {
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int userId = android.os.UserHandle.getUserId(intendingUid);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (!com.android.server.clipboard.ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId) || com.android.server.clipboard.ClipboardService.this.isDeviceLocked(userId, i2)) {
                return null;
            }
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                try {
                    try {
                        com.android.server.clipboard.ClipboardService.this.addActiveOwnerLocked(intendingUid, intendingDeviceId, str);
                        com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = com.android.server.clipboard.ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                        if (clipboardLocked == null) {
                            return null;
                        }
                        com.android.server.clipboard.ClipboardService.this.showAccessNotificationLocked(str, intendingUid, userId, clipboardLocked);
                        com.android.server.clipboard.ClipboardService.this.notifyTextClassifierLocked(clipboardLocked, str, intendingUid);
                        if (clipboardLocked.primaryClip != null) {
                            scheduleAutoClear(i, intendingUid, intendingDeviceId);
                        }
                        return clipboardLocked.primaryClip;
                    } catch (java.lang.SecurityException e) {
                        android.util.Slog.i(com.android.server.clipboard.ClipboardService.TAG, "Could not grant permission to primary clip. Clearing clipboard.");
                        com.android.server.clipboard.ClipboardService.this.setPrimaryClipInternalLocked((android.content.ClipData) null, intendingUid, intendingDeviceId, str);
                        return null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.content.ClipDescription getPrimaryClipDescription(java.lang.String str, java.lang.String str2, int i, int i2) {
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int userId = android.os.UserHandle.getUserId(intendingUid);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            boolean clipboardAccessAllowed = com.android.server.clipboard.ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId, false);
            android.content.ClipDescription clipDescription = null;
            if (!clipboardAccessAllowed || com.android.server.clipboard.ClipboardService.this.isDeviceLocked(userId, i2)) {
                return null;
            }
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                try {
                    com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = com.android.server.clipboard.ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                    if (clipboardLocked != null && clipboardLocked.primaryClip != null) {
                        clipDescription = clipboardLocked.primaryClip.getDescription();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return clipDescription;
        }

        public boolean hasPrimaryClip(java.lang.String str, java.lang.String str2, int i, int i2) {
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int userId = android.os.UserHandle.getUserId(intendingUid);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            boolean clipboardAccessAllowed = com.android.server.clipboard.ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId, false);
            boolean z = false;
            if (!clipboardAccessAllowed || com.android.server.clipboard.ClipboardService.this.isDeviceLocked(userId, i2)) {
                return false;
            }
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                try {
                    com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = com.android.server.clipboard.ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                    if (clipboardLocked != null && clipboardLocked.primaryClip != null) {
                        z = true;
                    }
                } finally {
                }
            }
            return z;
        }

        public void addPrimaryClipChangedListener(android.content.IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, java.lang.String str, java.lang.String str2, int i, int i2) {
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int userId = android.os.UserHandle.getUserId(intendingUid);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (intendingDeviceId == -1) {
                android.util.Slog.i(com.android.server.clipboard.ClipboardService.TAG, "addPrimaryClipChangedListener invalid deviceId for userId:" + i + " uid:" + intendingUid + " callingPackage:" + str + " requestedDeviceId:" + i2);
                return;
            }
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                try {
                    com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = com.android.server.clipboard.ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                    if (clipboardLocked == null) {
                        return;
                    }
                    clipboardLocked.primaryClipListeners.register(iOnPrimaryClipChangedListener, com.android.server.clipboard.ClipboardService.this.new ListenerInfo(intendingUid, str, str2));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void removePrimaryClipChangedListener(android.content.IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener, java.lang.String str, java.lang.String str2, int i, int i2) {
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int intendingUserId = com.android.server.clipboard.ClipboardService.this.getIntendingUserId(str, i);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (intendingDeviceId == -1) {
                android.util.Slog.i(com.android.server.clipboard.ClipboardService.TAG, "removePrimaryClipChangedListener invalid deviceId for userId:" + i + " uid:" + intendingUid + " callingPackage:" + str);
                return;
            }
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                try {
                    com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = com.android.server.clipboard.ClipboardService.this.getClipboardLocked(intendingUserId, intendingDeviceId);
                    if (clipboardLocked != null) {
                        clipboardLocked.primaryClipListeners.unregister(iOnPrimaryClipChangedListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean hasClipboardText(java.lang.String str, java.lang.String str2, int i, int i2) {
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int userId = android.os.UserHandle.getUserId(intendingUid);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            boolean clipboardAccessAllowed = com.android.server.clipboard.ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId, false);
            boolean z = false;
            if (!clipboardAccessAllowed || com.android.server.clipboard.ClipboardService.this.isDeviceLocked(userId, i2)) {
                return false;
            }
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                try {
                    com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = com.android.server.clipboard.ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                    if (clipboardLocked == null || clipboardLocked.primaryClip == null) {
                        return false;
                    }
                    java.lang.CharSequence text = clipboardLocked.primaryClip.getItemAt(0).getText();
                    if (text != null && text.length() > 0) {
                        z = true;
                    }
                    return z;
                } finally {
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.SET_CLIP_SOURCE")
        public java.lang.String getPrimaryClipSource(java.lang.String str, java.lang.String str2, int i, int i2) {
            getPrimaryClipSource_enforcePermission();
            int intendingUid = com.android.server.clipboard.ClipboardService.this.getIntendingUid(str, i);
            int userId = android.os.UserHandle.getUserId(intendingUid);
            int intendingDeviceId = com.android.server.clipboard.ClipboardService.this.getIntendingDeviceId(i2, intendingUid);
            if (!com.android.server.clipboard.ClipboardService.this.clipboardAccessAllowed(29, str, str2, intendingUid, userId, intendingDeviceId, false) || com.android.server.clipboard.ClipboardService.this.isDeviceLocked(userId, i2)) {
                return null;
            }
            synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                try {
                    com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = com.android.server.clipboard.ClipboardService.this.getClipboardLocked(userId, intendingDeviceId);
                    if (clipboardLocked == null || clipboardLocked.primaryClip == null) {
                        return null;
                    }
                    return clipboardLocked.mPrimaryClipPackage;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private class ClipboardClearHandler extends android.os.Handler {
            public static final int MSG_CLEAR = 101;

            ClipboardClearHandler(android.os.Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(@android.annotation.NonNull android.os.Message message) {
                switch (message.what) {
                    case 101:
                        int i = message.arg1;
                        int i2 = message.arg2;
                        int intValue = ((java.lang.Integer) ((android.util.Pair) message.obj).second).intValue();
                        synchronized (com.android.server.clipboard.ClipboardService.this.mLock) {
                            try {
                                com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = com.android.server.clipboard.ClipboardService.this.getClipboardLocked(i, intValue);
                                if (clipboardLocked != null && clipboardLocked.primaryClip != null) {
                                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CLIPBOARD_CLEARED, 1);
                                    com.android.server.clipboard.ClipboardService.this.setPrimaryClipInternalLocked((android.content.ClipData) null, i2, intValue, (java.lang.String) null);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        return;
                    default:
                        android.util.Slog.wtf(com.android.server.clipboard.ClipboardService.TAG, "ClipboardClearHandler received unknown message " + message.what);
                        return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public com.android.server.clipboard.ClipboardService.Clipboard getClipboardLocked(int i, int i2) {
        com.android.server.clipboard.ClipboardService.Clipboard clipboard = (com.android.server.clipboard.ClipboardService.Clipboard) this.mClipboards.get(i, java.lang.Integer.valueOf(i2));
        if (clipboard == null) {
            try {
                if (!this.mUm.isUserRunning(i)) {
                    android.util.Slog.w(TAG, "getClipboardLocked called with not running userId " + i);
                    return null;
                }
                if (i2 != 0 && !this.mVdm.isValidVirtualDeviceId(i2)) {
                    android.util.Slog.w(TAG, "getClipboardLocked called with invalid (possibly released) deviceId " + i2);
                    return null;
                }
                com.android.server.clipboard.ClipboardService.Clipboard clipboard2 = new com.android.server.clipboard.ClipboardService.Clipboard(i, i2);
                this.mClipboards.add(i, java.lang.Integer.valueOf(i2), clipboard2);
                return clipboard2;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException calling UserManager: " + e);
                return null;
            }
        }
        return clipboard;
    }

    java.util.List<android.content.pm.UserInfo> getRelatedProfiles(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                return this.mUm.getProfiles(i, true);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote Exception calling UserManager: " + e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean hasRestriction(java.lang.String str, int i) {
        try {
            return this.mUm.hasUserRestriction(str, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote Exception calling UserManager.getUserRestrictions: ", e);
            return true;
        }
    }

    void setPrimaryClipInternal(@android.annotation.Nullable android.content.ClipData clipData, int i) {
        synchronized (this.mLock) {
            setPrimaryClipInternalLocked(clipData, i, 0, (java.lang.String) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void setPrimaryClipInternalLocked(@android.annotation.Nullable android.content.ClipData clipData, int i, int i2, @android.annotation.Nullable java.lang.String str) {
        int size;
        com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked;
        if (i2 == 0) {
            this.mEmulatorClipboardMonitor.accept(clipData);
        }
        int userId = android.os.UserHandle.getUserId(i);
        com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked2 = getClipboardLocked(userId, i2);
        if (clipboardLocked2 == null) {
            return;
        }
        setPrimaryClipInternalLocked(clipboardLocked2, clipData, i, str);
        java.util.List<android.content.pm.UserInfo> relatedProfiles = getRelatedProfiles(userId);
        if (relatedProfiles != null && (size = relatedProfiles.size()) > 1) {
            if (!(!hasRestriction("no_cross_profile_copy_paste", userId))) {
                clipData = null;
            } else if (clipData != null) {
                android.content.ClipData clipData2 = new android.content.ClipData(clipData);
                for (int itemCount = clipData2.getItemCount() - 1; itemCount >= 0; itemCount--) {
                    clipData2.setItemAt(itemCount, new android.content.ClipData.Item(clipData2.getItemAt(itemCount)));
                }
                clipData2.fixUrisLight(userId);
                clipData = clipData2;
            }
            for (int i3 = 0; i3 < size; i3++) {
                int i4 = relatedProfiles.get(i3).id;
                if (i4 != userId && (!hasRestriction("no_sharing_into_profile", i4)) && (clipboardLocked = getClipboardLocked(i4, i2)) != null) {
                    setPrimaryClipInternalNoClassifyLocked(clipboardLocked, clipData, i, str);
                }
            }
        }
    }

    void setPrimaryClipInternal(com.android.server.clipboard.ClipboardService.Clipboard clipboard, @android.annotation.Nullable android.content.ClipData clipData, int i) {
        synchronized (this.mLock) {
            setPrimaryClipInternalLocked(clipboard, clipData, i, (java.lang.String) null);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setPrimaryClipInternalLocked(com.android.server.clipboard.ClipboardService.Clipboard clipboard, @android.annotation.Nullable android.content.ClipData clipData, int i, @android.annotation.Nullable java.lang.String str) {
        int userId = android.os.UserHandle.getUserId(i);
        if (clipData != null) {
            startClassificationLocked(clipData, userId, clipboard.deviceId);
        }
        setPrimaryClipInternalNoClassifyLocked(clipboard, clipData, i, str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setPrimaryClipInternalNoClassifyLocked(com.android.server.clipboard.ClipboardService.Clipboard clipboard, @android.annotation.Nullable android.content.ClipData clipData, int i, @android.annotation.Nullable java.lang.String str) {
        android.content.ClipDescription description;
        revokeUris(clipboard);
        clipboard.activePermissionOwners.clear();
        if (clipData == null && clipboard.primaryClip == null) {
            return;
        }
        clipboard.primaryClip = clipData;
        clipboard.mNotifiedUids.clear();
        clipboard.mNotifiedTextClassifierUids.clear();
        if (clipData != null) {
            clipboard.primaryClipUid = i;
            clipboard.mPrimaryClipPackage = str;
        } else {
            clipboard.primaryClipUid = 9999;
            clipboard.mPrimaryClipPackage = null;
        }
        if (clipData != null && (description = clipData.getDescription()) != null) {
            description.setTimestamp(java.lang.System.currentTimeMillis());
        }
        sendClipChangedBroadcast(clipboard);
    }

    private void sendClipChangedBroadcast(com.android.server.clipboard.ClipboardService.Clipboard clipboard) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        int beginBroadcast = clipboard.primaryClipListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                com.android.server.clipboard.ClipboardService.ListenerInfo listenerInfo = (com.android.server.clipboard.ClipboardService.ListenerInfo) clipboard.primaryClipListeners.getBroadcastCookie(i);
                if (clipboardAccessAllowed(29, listenerInfo.mPackageName, listenerInfo.mAttributionTag, listenerInfo.mUid, android.os.UserHandle.getUserId(listenerInfo.mUid), clipboard.deviceId)) {
                    clipboard.primaryClipListeners.getBroadcastItem(i).dispatchPrimaryClipChanged();
                }
            } catch (android.os.RemoteException | java.lang.SecurityException e) {
            } catch (java.lang.Throwable th) {
                clipboard.primaryClipListeners.finishBroadcast();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        clipboard.primaryClipListeners.finishBroadcast();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startClassificationLocked(@android.annotation.NonNull final android.content.ClipData clipData, final int i, final int i2) {
        final java.lang.CharSequence text = clipData.getItemCount() == 0 ? null : clipData.getItemAt(0).getText();
        if (android.text.TextUtils.isEmpty(text) || text.length() > this.mMaxClassificationLength) {
            clipData.getDescription().setClassificationStatus(2);
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            final android.view.textclassifier.TextClassifier createTextClassificationSession = createTextClassificationManagerAsUser(i).createTextClassificationSession(new android.view.textclassifier.TextClassificationContext.Builder(getContext().getPackageName(), "clipboard").build());
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if (text.length() > createTextClassificationSession.getMaxGenerateLinksTextLength()) {
                clipData.getDescription().setClassificationStatus(2);
            } else {
                this.mWorkerHandler.post(new java.lang.Runnable() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.clipboard.ClipboardService.this.lambda$startClassificationLocked$3(text, clipData, createTextClassificationSession, i, i2);
                    }
                });
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doClassification, reason: merged with bridge method [inline-methods] */
    public void lambda$startClassificationLocked$3(java.lang.CharSequence charSequence, android.content.ClipData clipData, android.view.textclassifier.TextClassifier textClassifier, int i, int i2) {
        int i3;
        com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked;
        android.view.textclassifier.TextLinks generateLinks = textClassifier.generateLinks(new android.view.textclassifier.TextLinks.Request.Builder(charSequence).build());
        android.util.ArrayMap<java.lang.String, java.lang.Float> arrayMap = new android.util.ArrayMap<>();
        java.util.Iterator<android.view.textclassifier.TextLinks.TextLink> it = generateLinks.getLinks().iterator();
        while (true) {
            i3 = 0;
            if (!it.hasNext()) {
                break;
            }
            android.view.textclassifier.TextLinks.TextLink next = it.next();
            while (i3 < next.getEntityCount()) {
                java.lang.String entity = next.getEntity(i3);
                float confidenceScore = next.getConfidenceScore(entity);
                if (confidenceScore > arrayMap.getOrDefault(entity, java.lang.Float.valueOf(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE)).floatValue()) {
                    arrayMap.put(entity, java.lang.Float.valueOf(confidenceScore));
                }
                i3++;
            }
        }
        synchronized (this.mLock) {
            try {
                com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked2 = getClipboardLocked(i, i2);
                if (clipboardLocked2 == null) {
                    return;
                }
                if (clipboardLocked2.primaryClip == clipData) {
                    applyClassificationAndSendBroadcastLocked(clipboardLocked2, arrayMap, generateLinks, textClassifier);
                    java.util.List<android.content.pm.UserInfo> relatedProfiles = getRelatedProfiles(i);
                    if (relatedProfiles != null) {
                        int size = relatedProfiles.size();
                        while (i3 < size) {
                            int i4 = relatedProfiles.get(i3).id;
                            if (i4 != i && (!hasRestriction("no_sharing_into_profile", i4)) && (clipboardLocked = getClipboardLocked(i4, i2)) != null && hasTextLocked(clipboardLocked, charSequence)) {
                                applyClassificationAndSendBroadcastLocked(clipboardLocked, arrayMap, generateLinks, textClassifier);
                            }
                            i3++;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void applyClassificationAndSendBroadcastLocked(com.android.server.clipboard.ClipboardService.Clipboard clipboard, android.util.ArrayMap<java.lang.String, java.lang.Float> arrayMap, android.view.textclassifier.TextLinks textLinks, android.view.textclassifier.TextClassifier textClassifier) {
        clipboard.mTextClassifier = textClassifier;
        clipboard.primaryClip.getDescription().setConfidenceScores(arrayMap);
        if (!textLinks.getLinks().isEmpty()) {
            clipboard.primaryClip.getItemAt(0).setTextLinks(textLinks);
        }
        sendClipChangedBroadcast(clipboard);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasTextLocked(com.android.server.clipboard.ClipboardService.Clipboard clipboard, @android.annotation.NonNull java.lang.CharSequence charSequence) {
        return clipboard.primaryClip != null && clipboard.primaryClip.getItemCount() > 0 && charSequence.equals(clipboard.primaryClip.getItemAt(0).getText());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeviceLocked(int i, int i2) {
        boolean z;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) getContext().getSystemService(android.app.KeyguardManager.class);
            if (keyguardManager != null) {
                if (keyguardManager.isDeviceLocked(i, i2)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void checkUriOwner(android.net.Uri uri, int i) {
        if (uri == null || !com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mUgmInternal.checkGrantUriPermission(i, null, android.content.ContentProvider.getUriWithoutUserId(uri), 1, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(i)));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void checkItemOwner(android.content.ClipData.Item item, int i) {
        if (item.getUri() != null) {
            checkUriOwner(item.getUri(), i);
        }
        android.content.Intent intent = item.getIntent();
        if (intent != null && intent.getData() != null) {
            checkUriOwner(intent.getData(), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkDataOwner(android.content.ClipData clipData, int i) {
        int itemCount = clipData.getItemCount();
        for (int i2 = 0; i2 < itemCount; i2++) {
            checkItemOwner(clipData.getItemAt(i2), i);
        }
    }

    private void grantUriPermission(android.net.Uri uri, int i, java.lang.String str, int i2) {
        if (uri == null || !com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mUgm.grantUriPermissionFromOwner(this.mPermissionOwner, i, str, android.content.ContentProvider.getUriWithoutUserId(uri), 1, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(i)), i2);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    private void grantItemPermission(android.content.ClipData.Item item, int i, java.lang.String str, int i2) {
        if (item.getUri() != null) {
            grantUriPermission(item.getUri(), i, str, i2);
        }
        android.content.Intent intent = item.getIntent();
        if (intent != null && intent.getData() != null) {
            grantUriPermission(intent.getData(), i, str, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void addActiveOwnerLocked(int i, int i2, java.lang.String str) {
        android.content.pm.PackageInfo packageInfo;
        android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            packageInfo = packageManager.getPackageInfo(str, 0L, callingUserId);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        if (packageInfo == null) {
            throw new java.lang.IllegalArgumentException("Unknown package " + str);
        }
        if (!android.os.UserHandle.isSameApp(packageInfo.applicationInfo.uid, i)) {
            throw new java.lang.SecurityException("Calling uid " + i + " does not own package " + str);
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        com.android.server.clipboard.ClipboardService.Clipboard clipboardLocked = getClipboardLocked(android.os.UserHandle.getUserId(i), i2);
        if (clipboardLocked != null && clipboardLocked.primaryClip != null && !clipboardLocked.activePermissionOwners.contains(str)) {
            int itemCount = clipboardLocked.primaryClip.getItemCount();
            for (int i3 = 0; i3 < itemCount; i3++) {
                grantItemPermission(clipboardLocked.primaryClip.getItemAt(i3), clipboardLocked.primaryClipUid, str, android.os.UserHandle.getUserId(i));
            }
            clipboardLocked.activePermissionOwners.add(str);
        }
    }

    private void revokeUriPermission(android.net.Uri uri, int i) {
        if (uri == null || !com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mUgmInternal.revokeUriPermissionFromOwner(this.mPermissionOwner, android.content.ContentProvider.getUriWithoutUserId(uri), 1, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(i)));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void revokeItemPermission(android.content.ClipData.Item item, int i) {
        if (item.getUri() != null) {
            revokeUriPermission(item.getUri(), i);
        }
        android.content.Intent intent = item.getIntent();
        if (intent != null && intent.getData() != null) {
            revokeUriPermission(intent.getData(), i);
        }
    }

    private void revokeUris(com.android.server.clipboard.ClipboardService.Clipboard clipboard) {
        if (clipboard.primaryClip == null) {
            return;
        }
        int itemCount = clipboard.primaryClip.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            revokeItemPermission(clipboard.primaryClip.getItemAt(i), clipboard.primaryClipUid);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean clipboardAccessAllowed(int i, java.lang.String str, java.lang.String str2, int i2, int i3, int i4) {
        return clipboardAccessAllowed(i, str, str2, i2, i3, i4, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean clipboardAccessAllowed(int i, java.lang.String str, java.lang.String str2, int i2, int i3, int i4, boolean z) {
        boolean isDefaultIme;
        int checkOp;
        this.mAppOps.checkPackage(i2, str);
        if (i4 == -1) {
            android.util.Slog.w(TAG, "Clipboard access denied to " + i2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str + " due to invalid device id");
            return false;
        }
        if (this.mPm.checkPermission("android.permission.READ_CLIPBOARD_IN_BACKGROUND", str) == 0) {
            isDefaultIme = true;
        } else {
            isDefaultIme = isDefaultIme(i3, str);
        }
        switch (i) {
            case 29:
                if (!isDefaultIme) {
                    isDefaultIme = isDefaultDeviceAndUidFocused(i4, i2) || isVirtualDeviceAndUidFocused(i4, i2) || isInternalSysWindowAppWithWindowFocus(str);
                }
                if (!isDefaultIme && this.mContentCaptureInternal != null) {
                    isDefaultIme = this.mContentCaptureInternal.isContentCaptureServiceForUser(i2, i3);
                }
                if (!isDefaultIme && this.mAutofillInternal != null) {
                    isDefaultIme = this.mAutofillInternal.isAugmentedAutofillServiceForUser(i2, i3);
                }
                if (!isDefaultIme && i4 != 0) {
                    if (this.mVdmInternal != null && this.mVdmInternal.getDeviceOwnerUid(i4) == i2) {
                        isDefaultIme = true;
                        break;
                    } else {
                        isDefaultIme = false;
                        break;
                    }
                }
                break;
            case 30:
                isDefaultIme = true;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Unknown clipboard appop " + i);
        }
        if (!isDefaultIme) {
            android.util.Slog.e(TAG, "Denying clipboard access to " + str + ", application is not in focus nor is it a system service for user " + i3);
            return false;
        }
        if (z) {
            checkOp = this.mAppOps.noteOp(i, i2, str, str2, (java.lang.String) null);
        } else {
            checkOp = this.mAppOps.checkOp(i, i2, str);
        }
        return checkOp == 0;
    }

    private boolean isDefaultDeviceAndUidFocused(int i, int i2) {
        return i == 0 && this.mWm.isUidFocused(i2);
    }

    private boolean isVirtualDeviceAndUidFocused(int i, int i2) {
        if (i == 0 || this.mVdm == null) {
            return false;
        }
        return this.mVdm.getDeviceIdForDisplayId(this.mWm.getTopFocusedDisplayId()) == i && this.mWm.isUidFocused(i2);
    }

    private boolean isDefaultIme(int i, java.lang.String str) {
        android.content.ComponentName unflattenFromString;
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(getContext().getContentResolver(), "default_input_method", i);
        if (android.text.TextUtils.isEmpty(stringForUser) || (unflattenFromString = android.content.ComponentName.unflattenFromString(stringForUser)) == null) {
            return false;
        }
        return unflattenFromString.getPackageName().equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void showAccessNotificationLocked(final java.lang.String str, int i, final int i2, com.android.server.clipboard.ClipboardService.Clipboard clipboard) {
        if (clipboard.primaryClip == null || android.provider.Settings.Secure.getInt(getContext().getContentResolver(), "clipboard_show_access_notifications", this.mShowAccessNotifications ? 1 : 0) == 0 || android.os.UserHandle.isSameApp(i, clipboard.primaryClipUid) || isDefaultIme(i2, str)) {
            return;
        }
        if (this.mContentCaptureInternal != null && this.mContentCaptureInternal.isContentCaptureServiceForUser(i, i2)) {
            return;
        }
        if ((this.mAutofillInternal != null && this.mAutofillInternal.isAugmentedAutofillServiceForUser(i, i2)) || this.mPm.checkPermission("android.permission.SUPPRESS_CLIPBOARD_ACCESS_NOTIFICATION", str) == 0) {
            return;
        }
        if ((clipboard.deviceId != 0 && this.mVdmInternal.getDeviceOwnerUid(clipboard.deviceId) == i) || clipboard.mNotifiedUids.get(i)) {
            return;
        }
        final android.util.ArraySet<android.content.Context> toastContexts = getToastContexts(clipboard);
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                com.android.server.clipboard.ClipboardService.this.lambda$showAccessNotificationLocked$4(str, i2, toastContexts);
            }
        });
        clipboard.mNotifiedUids.put(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAccessNotificationLocked$4(java.lang.String str, int i, android.util.ArraySet arraySet) throws java.lang.Exception {
        android.widget.Toast makeText;
        try {
            java.lang.String string = getContext().getString(android.R.string.notification_title, this.mPm.getApplicationLabel(this.mPm.getApplicationInfoAsUser(str, 0, i)));
            android.util.Slog.i(TAG, string);
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                android.content.Context context = (android.content.Context) arraySet.valueAt(i2);
                if (android.util.SafetyProtectionUtils.shouldShowSafetyProtectionResources(getContext())) {
                    makeText = android.widget.Toast.makeCustomToastWithIcon(context, com.android.server.UiThread.get().getLooper(), string, 1, getContext().getDrawable(android.R.drawable.ic_safety_protection));
                } else {
                    makeText = android.widget.Toast.makeText(context, com.android.server.UiThread.get().getLooper(), string, 1);
                }
                makeText.show();
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
    }

    private android.util.ArraySet<android.content.Context> getToastContexts(com.android.server.clipboard.ClipboardService.Clipboard clipboard) throws java.lang.IllegalStateException {
        android.view.Display display;
        android.util.ArraySet<android.content.Context> arraySet = new android.util.ArraySet<>();
        if (clipboard.deviceId != 0) {
            android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) getContext().getSystemService(android.hardware.display.DisplayManager.class);
            int topFocusedDisplayId = this.mWm.getTopFocusedDisplayId();
            android.util.ArraySet<java.lang.Integer> displayIdsForDevice = this.mVdmInternal.getDisplayIdsForDevice(clipboard.deviceId);
            if (displayIdsForDevice.contains(java.lang.Integer.valueOf(topFocusedDisplayId)) && (display = displayManager.getDisplay(topFocusedDisplayId)) != null) {
                arraySet.add(getContext().createDisplayContext(display));
                return arraySet;
            }
            for (int i = 0; i < displayIdsForDevice.size(); i++) {
                android.view.Display display2 = displayManager.getDisplay(displayIdsForDevice.valueAt(i).intValue());
                if (display2 != null) {
                    arraySet.add(getContext().createDisplayContext(display2));
                }
            }
            if (!arraySet.isEmpty()) {
                return arraySet;
            }
            android.util.Slog.e(TAG, "getToastContexts Couldn't find any VirtualDisplays for VirtualDevice " + clipboard.deviceId);
        }
        arraySet.add(getContext());
        return arraySet;
    }

    private static boolean isText(@android.annotation.NonNull android.content.ClipData clipData) {
        if (clipData.getItemCount() > 1) {
            return false;
        }
        android.content.ClipData.Item itemAt = clipData.getItemAt(0);
        return !android.text.TextUtils.isEmpty(itemAt.getText()) && itemAt.getUri() == null && itemAt.getIntent() == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyTextClassifierLocked(final com.android.server.clipboard.ClipboardService.Clipboard clipboard, final java.lang.String str, int i) {
        final android.view.textclassifier.TextClassifier textClassifier;
        if (clipboard.primaryClip == null || clipboard.primaryClip.getItemAt(0) == null || !isText(clipboard.primaryClip) || (textClassifier = clipboard.mTextClassifier) == null || !this.mWm.isUidFocused(i) || clipboard.mNotifiedTextClassifierUids.get(i)) {
            return;
        }
        clipboard.mNotifiedTextClassifierUids.put(i, true);
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.clipboard.ClipboardService.lambda$notifyTextClassifierLocked$5(str, clipboard, textClassifier);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyTextClassifierLocked$5(java.lang.String str, com.android.server.clipboard.ClipboardService.Clipboard clipboard, android.view.textclassifier.TextClassifier textClassifier) throws java.lang.Exception {
        textClassifier.onTextClassifierEvent(((android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent.Builder) ((android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent.Builder) new android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent.Builder(22).setEventContext(new android.view.textclassifier.TextClassificationContext.Builder(str, "clipboard").build())).setExtras(android.os.Bundle.forPair("source_package", clipboard.mPrimaryClipPackage))).build());
    }

    private android.view.textclassifier.TextClassificationManager createTextClassificationManagerAsUser(int i) {
        return (android.view.textclassifier.TextClassificationManager) getContext().createContextAsUser(android.os.UserHandle.of(i), 0).getSystemService(android.view.textclassifier.TextClassificationManager.class);
    }
}
