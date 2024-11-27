package com.android.server.om;

/* loaded from: classes2.dex */
public final class OverlayManagerService extends com.android.server.SystemService {
    static final boolean DEBUG = false;
    private static final java.lang.String DEFAULT_OVERLAYS_PROP = "ro.boot.vendor.overlay.theme";
    static final java.lang.String TAG = "OverlayManager";
    private final com.android.server.om.OverlayActorEnforcer mActorEnforcer;
    private final com.android.server.om.OverlayManagerServiceImpl mImpl;
    private final java.lang.Object mLock;
    private final com.android.server.om.OverlayManagerService.PackageManagerHelperImpl mPackageManager;
    private final com.android.internal.content.PackageMonitor mPackageMonitor;
    private int mPrevStartedUserId;
    private final android.os.IBinder mService;
    private final com.android.server.om.OverlayManagerSettings mSettings;
    private final android.util.AtomicFile mSettingsFile;
    private final com.android.server.pm.UserManagerService mUserManager;

    public OverlayManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        byte b = 0;
        this.mPackageMonitor = new com.android.server.om.OverlayManagerService.OverlayManagerPackageMonitor();
        this.mPrevStartedUserId = -1;
        this.mService = new com.android.server.om.OverlayManagerService.AnonymousClass1();
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#OverlayManagerService");
            this.mSettingsFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), "overlays.xml"), "overlays");
            this.mPackageManager = new com.android.server.om.OverlayManagerService.PackageManagerHelperImpl(context);
            this.mUserManager = com.android.server.pm.UserManagerService.getInstance();
            com.android.server.om.IdmapManager idmapManager = new com.android.server.om.IdmapManager(com.android.server.om.IdmapDaemon.getInstance(), this.mPackageManager);
            this.mSettings = new com.android.server.om.OverlayManagerSettings();
            this.mImpl = new com.android.server.om.OverlayManagerServiceImpl(this.mPackageManager, idmapManager, this.mSettings, com.android.internal.content.om.OverlayConfig.getSystemInstance(), getDefaultOverlayPackages());
            this.mActorEnforcer = new com.android.server.om.OverlayActorEnforcer(this.mPackageManager);
            android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
            handlerThread.start();
            this.mPackageMonitor.register(context, handlerThread.getLooper(), true);
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.USER_ADDED");
            intentFilter.addAction("android.intent.action.USER_REMOVED");
            getContext().registerReceiverAsUser(new com.android.server.om.OverlayManagerService.UserReceiver(), android.os.UserHandle.ALL, intentFilter, null, null);
            restoreSettings();
            final java.lang.String emptyIfNull = android.text.TextUtils.emptyIfNull(getContext().getString(android.R.string.config_systemShell));
            this.mSettings.removeIf(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$new$0;
                    lambda$new$0 = com.android.server.om.OverlayManagerService.lambda$new$0(emptyIfNull, (android.content.om.OverlayInfo) obj);
                    return lambda$new$0;
                }
            });
            initIfNeeded();
            onStartUser(0);
            publishBinderService("overlay", this.mService);
            publishLocalService(com.android.server.om.OverlayManagerService.class, this);
        } finally {
            android.os.Trace.traceEnd(67108864L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$new$0(java.lang.String str, android.content.om.OverlayInfo overlayInfo) {
        return overlayInfo.isFabricated && str.equals(overlayInfo.packageName);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
    }

    private void initIfNeeded() {
        java.util.List aliveUsers = ((android.os.UserManager) getContext().getSystemService(android.os.UserManager.class)).getAliveUsers();
        synchronized (this.mLock) {
            try {
                int size = aliveUsers.size();
                for (int i = 0; i < size; i++) {
                    android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) aliveUsers.get(i);
                    if (!userInfo.supportsSwitchTo() && userInfo.id != 0) {
                        updatePackageManagerLocked(this.mImpl.updateOverlaysForUser(((android.content.pm.UserInfo) aliveUsers.get(i)).id));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(com.android.server.SystemService.TargetUser targetUser) {
        onStartUser(targetUser.getUserIdentifier());
    }

    private void onStartUser(int i) {
        if (i == this.mPrevStartedUserId) {
            return;
        }
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#onStartUser " + i);
            synchronized (this.mLock) {
                updateTargetPackagesLocked(this.mImpl.updateOverlaysForUser(i));
            }
            android.os.Trace.traceEnd(67108864L);
            this.mPrevStartedUserId = i;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(67108864L);
            throw th;
        }
    }

    private static java.lang.String[] getDefaultOverlayPackages() {
        java.lang.String str = android.os.SystemProperties.get(DEFAULT_OVERLAYS_PROP);
        if (android.text.TextUtils.isEmpty(str)) {
            return libcore.util.EmptyArray.STRING;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (java.lang.String str2 : str.split(";")) {
            if (!android.text.TextUtils.isEmpty(str2)) {
                arraySet.add(str2);
            }
        }
        return (java.lang.String[]) arraySet.toArray(new java.lang.String[0]);
    }

    private final class OverlayManagerPackageMonitor extends com.android.internal.content.PackageMonitor {
        private OverlayManagerPackageMonitor() {
        }

        public void onPackageAppearedWithExtras(java.lang.String str, android.os.Bundle bundle) {
            com.android.server.om.OverlayManagerService.this.handlePackageAdd(str, bundle);
        }

        public void onPackageChangedWithExtras(java.lang.String str, android.os.Bundle bundle) {
            com.android.server.om.OverlayManagerService.this.handlePackageChange(str, bundle);
        }

        public void onPackageDisappearedWithExtras(java.lang.String str, android.os.Bundle bundle) {
            com.android.server.om.OverlayManagerService.this.handlePackageRemove(str, bundle);
        }
    }

    private int[] getUserIds(int i) {
        if (i == -1) {
            return this.mUserManager.getUserIds();
        }
        return new int[]{android.os.UserHandle.getUserId(i)};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageAdd(java.lang.String str, android.os.Bundle bundle) {
        boolean z = bundle.getBoolean("android.intent.extra.REPLACING", false);
        int[] userIds = getUserIds(bundle.getInt("android.intent.extra.UID", 0));
        if (z) {
            onPackageReplaced(str, userIds);
        } else {
            onPackageAdded(str, userIds);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageChange(java.lang.String str, android.os.Bundle bundle) {
        int[] userIds = getUserIds(bundle.getInt("android.intent.extra.UID", 0));
        if (!"android.intent.action.OVERLAY_CHANGED".equals(bundle.getString("android.intent.extra.REASON"))) {
            onPackageChanged(str, userIds);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageRemove(java.lang.String str, android.os.Bundle bundle) {
        boolean z = bundle.getBoolean("android.intent.extra.REPLACING", false);
        boolean z2 = bundle.getBoolean("android.intent.extra.SYSTEM_UPDATE_UNINSTALL", false);
        int[] userIds = getUserIds(bundle.getInt("android.intent.extra.UID", 0));
        if (z) {
            onPackageReplacing(str, z2, userIds);
        } else {
            onPackageRemoved(str, userIds);
        }
    }

    private void onPackageAdded(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr) {
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#onPackageAdded " + str);
            for (int i : iArr) {
                synchronized (this.mLock) {
                    if (this.mPackageManager.onPackageAdded(str, i) != null && !this.mPackageManager.isInstantApp(str, i)) {
                        try {
                            updateTargetPackagesLocked(this.mImpl.onPackageAdded(str, i));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.util.Slog.e(TAG, "onPackageAdded internal error", e);
                        }
                    }
                }
            }
        } finally {
            android.os.Trace.traceEnd(67108864L);
        }
    }

    private void onPackageChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr) {
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#onPackageChanged " + str);
            for (int i : iArr) {
                synchronized (this.mLock) {
                    if (this.mPackageManager.onPackageUpdated(str, i) != null && !this.mPackageManager.isInstantApp(str, i)) {
                        try {
                            updateTargetPackagesLocked(this.mImpl.onPackageChanged(str, i));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.util.Slog.e(TAG, "onPackageChanged internal error", e);
                        }
                    }
                }
            }
        } finally {
            android.os.Trace.traceEnd(67108864L);
        }
    }

    private void onPackageReplacing(@android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.NonNull int[] iArr) {
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#onPackageReplacing " + str);
            for (int i : iArr) {
                synchronized (this.mLock) {
                    if (this.mPackageManager.onPackageUpdated(str, i) != null && !this.mPackageManager.isInstantApp(str, i)) {
                        try {
                            updateTargetPackagesLocked(this.mImpl.onPackageReplacing(str, z, i));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.util.Slog.e(TAG, "onPackageReplacing internal error", e);
                        }
                    }
                }
            }
        } finally {
            android.os.Trace.traceEnd(67108864L);
        }
    }

    private void onPackageReplaced(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr) {
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#onPackageReplaced " + str);
            for (int i : iArr) {
                synchronized (this.mLock) {
                    if (this.mPackageManager.onPackageUpdated(str, i) != null && !this.mPackageManager.isInstantApp(str, i)) {
                        try {
                            updateTargetPackagesLocked(this.mImpl.onPackageReplaced(str, i));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.util.Slog.e(TAG, "onPackageReplaced internal error", e);
                        }
                    }
                }
            }
        } finally {
            android.os.Trace.traceEnd(67108864L);
        }
    }

    private void onPackageRemoved(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr) {
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#onPackageRemoved " + str);
            for (int i : iArr) {
                synchronized (this.mLock) {
                    this.mPackageManager.onPackageRemoved(str, i);
                    updateTargetPackagesLocked(this.mImpl.onPackageRemoved(str, i));
                }
            }
        } finally {
            android.os.Trace.traceEnd(67108864L);
        }
    }

    private final class UserReceiver extends android.content.BroadcastReceiver {
        private UserReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.Intent intent) {
            char c;
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case -2061058799:
                    if (action.equals("android.intent.action.USER_REMOVED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1121780209:
                    if (action.equals("android.intent.action.USER_ADDED")) {
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
                    if (intExtra != -10000) {
                        try {
                            android.os.Trace.traceBegin(67108864L, "OMS ACTION_USER_ADDED");
                            synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                                com.android.server.om.OverlayManagerService.this.updatePackageManagerLocked(com.android.server.om.OverlayManagerService.this.mImpl.updateOverlaysForUser(intExtra));
                            }
                            return;
                        } finally {
                        }
                    }
                    return;
                case 1:
                    if (intExtra != -10000) {
                        try {
                            android.os.Trace.traceBegin(67108864L, "OMS ACTION_USER_REMOVED");
                            synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                                com.android.server.om.OverlayManagerService.this.mImpl.onUserRemoved(intExtra);
                                com.android.server.om.OverlayManagerService.this.mPackageManager.forgetAllPackageInfos(intExtra);
                            }
                            return;
                        } finally {
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: com.android.server.om.OverlayManagerService$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.om.IOverlayManager.Stub {
        AnonymousClass1() {
        }

        public java.util.Map<java.lang.String, java.util.List<android.content.om.OverlayInfo>> getAllOverlays(int i) {
            java.util.Map<java.lang.String, java.util.List<android.content.om.OverlayInfo>> overlaysForUser;
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#getAllOverlays " + i);
                int handleIncomingUser = handleIncomingUser(i, "getAllOverlays");
                synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                    overlaysForUser = com.android.server.om.OverlayManagerService.this.mImpl.getOverlaysForUser(handleIncomingUser);
                }
                return overlaysForUser;
            } finally {
                android.os.Trace.traceEnd(67108864L);
            }
        }

        public java.util.List<android.content.om.OverlayInfo> getOverlayInfosForTarget(@android.annotation.Nullable java.lang.String str, int i) {
            java.util.List<android.content.om.OverlayInfo> overlayInfosForTarget;
            if (str == null) {
                return java.util.Collections.emptyList();
            }
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#getOverlayInfosForTarget " + str);
                int handleIncomingUser = handleIncomingUser(i, "getOverlayInfosForTarget");
                synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                    overlayInfosForTarget = com.android.server.om.OverlayManagerService.this.mImpl.getOverlayInfosForTarget(str, handleIncomingUser);
                }
                return overlayInfosForTarget;
            } finally {
                android.os.Trace.traceEnd(67108864L);
            }
        }

        public android.content.om.OverlayInfo getOverlayInfo(@android.annotation.Nullable java.lang.String str, int i) {
            return getOverlayInfoByIdentifier(new android.content.om.OverlayIdentifier(str), i);
        }

        public android.content.om.OverlayInfo getOverlayInfoByIdentifier(@android.annotation.Nullable android.content.om.OverlayIdentifier overlayIdentifier, int i) {
            android.content.om.OverlayInfo overlayInfo;
            if (overlayIdentifier == null || overlayIdentifier.getPackageName() == null) {
                return null;
            }
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#getOverlayInfo " + overlayIdentifier);
                int handleIncomingUser = handleIncomingUser(i, "getOverlayInfo");
                synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                    overlayInfo = com.android.server.om.OverlayManagerService.this.mImpl.getOverlayInfo(overlayIdentifier, handleIncomingUser);
                }
                return overlayInfo;
            } finally {
                android.os.Trace.traceEnd(67108864L);
            }
        }

        public boolean setEnabled(@android.annotation.Nullable java.lang.String str, boolean z, int i) {
            if (str == null) {
                return false;
            }
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#setEnabled " + str + " " + z);
                android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabled");
                enforceActor(overlayIdentifier, "setEnabled", handleIncomingUser);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                        try {
                            com.android.server.om.OverlayManagerService.this.updateTargetPackagesLocked(com.android.server.om.OverlayManagerService.this.mImpl.setEnabled(overlayIdentifier, z, handleIncomingUser));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.os.Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    android.os.Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public boolean setEnabledExclusive(@android.annotation.Nullable java.lang.String str, boolean z, int i) {
            if (str == null || !z) {
                return false;
            }
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#setEnabledExclusive " + str + " " + z);
                android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabledExclusive");
                enforceActor(overlayIdentifier, "setEnabledExclusive", handleIncomingUser);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                        try {
                            com.android.server.om.OverlayManagerService.this.mImpl.setEnabledExclusive(overlayIdentifier, false, handleIncomingUser).ifPresent(new com.android.server.om.OverlayManagerService$1$$ExternalSyntheticLambda0(com.android.server.om.OverlayManagerService.this));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            return false;
                        }
                    }
                    android.os.Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                android.os.Trace.traceEnd(67108864L);
            }
        }

        public boolean setEnabledExclusiveInCategory(@android.annotation.Nullable java.lang.String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#setEnabledExclusiveInCategory " + str);
                android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setEnabledExclusiveInCategory");
                enforceActor(overlayIdentifier, "setEnabledExclusiveInCategory", handleIncomingUser);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                        try {
                            com.android.server.om.OverlayManagerService.this.mImpl.setEnabledExclusive(overlayIdentifier, true, handleIncomingUser).ifPresent(new com.android.server.om.OverlayManagerService$1$$ExternalSyntheticLambda0(com.android.server.om.OverlayManagerService.this));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.os.Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    android.os.Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public boolean setPriority(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i) {
            if (str == null || str2 == null) {
                return false;
            }
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#setPriority " + str + " " + str2);
                android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(str);
                android.content.om.OverlayIdentifier overlayIdentifier2 = new android.content.om.OverlayIdentifier(str2);
                int handleIncomingUser = handleIncomingUser(i, "setPriority");
                enforceActor(overlayIdentifier, "setPriority", handleIncomingUser);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                        try {
                            com.android.server.om.OverlayManagerService.this.mImpl.setPriority(overlayIdentifier, overlayIdentifier2, handleIncomingUser).ifPresent(new com.android.server.om.OverlayManagerService$1$$ExternalSyntheticLambda0(com.android.server.om.OverlayManagerService.this));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.os.Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    android.os.Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public boolean setHighestPriority(@android.annotation.Nullable java.lang.String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#setHighestPriority " + str);
                android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setHighestPriority");
                enforceActor(overlayIdentifier, "setHighestPriority", handleIncomingUser);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                        try {
                            com.android.server.om.OverlayManagerService.this.updateTargetPackagesLocked(com.android.server.om.OverlayManagerService.this.mImpl.setHighestPriority(overlayIdentifier, handleIncomingUser));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.os.Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    android.os.Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public boolean setLowestPriority(@android.annotation.Nullable java.lang.String str, int i) {
            if (str == null) {
                return false;
            }
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#setLowestPriority " + str);
                android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(str);
                int handleIncomingUser = handleIncomingUser(i, "setLowestPriority");
                enforceActor(overlayIdentifier, "setLowestPriority", handleIncomingUser);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                        try {
                            com.android.server.om.OverlayManagerService.this.mImpl.setLowestPriority(overlayIdentifier, handleIncomingUser).ifPresent(new com.android.server.om.OverlayManagerService$1$$ExternalSyntheticLambda0(com.android.server.om.OverlayManagerService.this));
                        } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                            android.os.Trace.traceEnd(67108864L);
                            return false;
                        }
                    }
                    android.os.Trace.traceEnd(67108864L);
                    return true;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(67108864L);
                throw th;
            }
        }

        public java.lang.String[] getDefaultOverlayPackages() {
            java.lang.String[] defaultOverlayPackages;
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#getDefaultOverlayPackages");
                com.android.server.om.OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.MODIFY_THEME_OVERLAY", null);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                        defaultOverlayPackages = com.android.server.om.OverlayManagerService.this.mImpl.getDefaultOverlayPackages();
                    }
                    return defaultOverlayPackages;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                android.os.Trace.traceEnd(67108864L);
            }
        }

        public void invalidateCachesForOverlay(@android.annotation.Nullable java.lang.String str, int i) {
            if (str == null) {
                return;
            }
            android.content.om.OverlayIdentifier overlayIdentifier = new android.content.om.OverlayIdentifier(str);
            int handleIncomingUser = handleIncomingUser(i, "invalidateCachesForOverlay");
            enforceActor(overlayIdentifier, "invalidateCachesForOverlay", handleIncomingUser);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                    try {
                        com.android.server.om.OverlayManagerService.this.mImpl.removeIdmapForOverlay(overlayIdentifier, handleIncomingUser);
                    } catch (com.android.server.om.OverlayManagerServiceImpl.OperationFailedException e) {
                        android.util.Slog.w(com.android.server.om.OverlayManagerService.TAG, "invalidate caches for overlay '" + overlayIdentifier + "' failed", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void commit(@android.annotation.NonNull android.content.om.OverlayManagerTransaction overlayManagerTransaction) throws android.os.RemoteException {
            java.lang.String str;
            try {
                android.os.Trace.traceBegin(67108864L, "OMS#commit " + overlayManagerTransaction);
                try {
                    executeAllRequests(overlayManagerTransaction);
                } catch (java.lang.Exception e) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        com.android.server.om.OverlayManagerService.this.restoreSettings();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        android.util.Slog.d(com.android.server.om.OverlayManagerService.TAG, "commit failed: " + e.getMessage(), e);
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("commit failed");
                        if (android.os.Build.IS_DEBUGGABLE) {
                            str = ": " + e.getMessage();
                        } else {
                            str = "";
                        }
                        sb.append(str);
                        throw new java.lang.SecurityException(sb.toString());
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } finally {
                android.os.Trace.traceEnd(67108864L);
            }
        }

        private java.util.Set<android.content.pm.UserPackage> executeRequest(@android.annotation.NonNull android.content.om.OverlayManagerTransaction.Request request) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
            int i;
            java.util.Objects.requireNonNull(request, "Transaction contains a null request");
            java.util.Objects.requireNonNull(request.overlay, "Transaction overlay identifier must be non-null");
            int callingUid = android.os.Binder.getCallingUid();
            if (request.type != 2 && request.type != 3) {
                i = handleIncomingUser(request.userId, request.typeToString());
                enforceActor(request.overlay, request.typeToString(), i);
            } else {
                if (request.userId != -1) {
                    throw new java.lang.IllegalArgumentException(request.typeToString() + " unsupported for user " + request.userId);
                }
                if (callingUid == 2000) {
                    android.util.EventLog.writeEvent(1397638484, "202768292", -1, "");
                    throw new java.lang.IllegalArgumentException("Non-root shell cannot fabricate overlays");
                }
                java.lang.String packageName = request.overlay.getPackageName();
                if (callingUid != 0 && !com.android.internal.util.ArrayUtils.contains(com.android.server.om.OverlayManagerService.this.mPackageManager.getPackagesForUid(callingUid), packageName)) {
                    throw new java.lang.IllegalArgumentException("UID " + callingUid + " does own packagename " + packageName);
                }
                i = -1;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                switch (request.type) {
                    case 0:
                        java.util.Set<android.content.pm.UserPackage> emptyIfNull = com.android.internal.util.CollectionUtils.emptyIfNull(com.android.internal.util.CollectionUtils.addAll(com.android.internal.util.CollectionUtils.addAll((java.util.Set) null, com.android.server.om.OverlayManagerService.this.mImpl.setEnabled(request.overlay, true, i)), com.android.server.om.OverlayManagerService.this.mImpl.setHighestPriority(request.overlay, i)));
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return emptyIfNull;
                    case 1:
                        java.util.Set<android.content.pm.UserPackage> enabled = com.android.server.om.OverlayManagerService.this.mImpl.setEnabled(request.overlay, false, i);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return enabled;
                    case 2:
                        android.os.FabricatedOverlayInternal fabricatedOverlayInternal = (android.os.FabricatedOverlayInternal) request.extras.getParcelable("fabricated_overlay", android.os.FabricatedOverlayInternal.class);
                        java.util.Objects.requireNonNull(fabricatedOverlayInternal, "no fabricated overlay attached to request");
                        java.util.Set<android.content.pm.UserPackage> registerFabricatedOverlay = com.android.server.om.OverlayManagerService.this.mImpl.registerFabricatedOverlay(fabricatedOverlayInternal);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return registerFabricatedOverlay;
                    case 3:
                        java.util.Set<android.content.pm.UserPackage> unregisterFabricatedOverlay = com.android.server.om.OverlayManagerService.this.mImpl.unregisterFabricatedOverlay(request.overlay);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return unregisterFabricatedOverlay;
                    default:
                        throw new java.lang.IllegalArgumentException("unsupported request: " + request);
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        private void executeAllRequests(@android.annotation.NonNull android.content.om.OverlayManagerTransaction overlayManagerTransaction) throws com.android.server.om.OverlayManagerServiceImpl.OperationFailedException {
            if (overlayManagerTransaction == null) {
                throw new java.lang.IllegalArgumentException("null transaction");
            }
            synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                try {
                    java.util.Iterator requests = overlayManagerTransaction.getRequests();
                    java.util.Set set = null;
                    while (requests.hasNext()) {
                        set = com.android.internal.util.CollectionUtils.addAll(set, executeRequest((android.content.om.OverlayManagerTransaction.Request) requests.next()));
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        com.android.server.om.OverlayManagerService.this.updateTargetPackagesLocked((java.util.Set<android.content.pm.UserPackage>) set);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor2, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
            new com.android.server.om.OverlayManagerShellCommand(com.android.server.om.OverlayManagerService.this.getContext(), this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            java.lang.String str;
            com.android.server.om.DumpState dumpState = new com.android.server.om.DumpState();
            char c = 65535;
            dumpState.setUserId(-1);
            int i = 0;
            while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
                i++;
                if (!"-a".equals(str)) {
                    if ("-h".equals(str)) {
                        printWriter.println("dump [-h] [--verbose] [--user USER_ID] [[FIELD] PACKAGE]");
                        printWriter.println("  Print debugging information about the overlay manager.");
                        printWriter.println("  With optional parameter PACKAGE, limit output to the specified");
                        printWriter.println("  package. With optional parameter FIELD, limit output to");
                        printWriter.println("  the value of that SettingsItem field. Field names are");
                        printWriter.println("  case insensitive and out.println the m prefix can be omitted,");
                        printWriter.println("  so the following are equivalent: mState, mstate, State, state.");
                        return;
                    }
                    if ("--user".equals(str)) {
                        if (i >= strArr.length) {
                            printWriter.println("Error: user missing argument");
                            return;
                        }
                        try {
                            dumpState.setUserId(java.lang.Integer.parseInt(strArr[i]));
                            i++;
                        } catch (java.lang.NumberFormatException e) {
                            printWriter.println("Error: user argument is not a number: " + strArr[i]);
                            return;
                        }
                    } else if ("--verbose".equals(str)) {
                        dumpState.setVerbose(true);
                    } else {
                        printWriter.println("Unknown argument: " + str + "; use -h for help");
                    }
                }
            }
            if (i < strArr.length) {
                java.lang.String str2 = strArr[i];
                i++;
                switch (str2.hashCode()) {
                    case -1750736508:
                        if (str2.equals("targetoverlayablename")) {
                            c = 3;
                            break;
                        }
                        break;
                    case -1248283232:
                        if (str2.equals("targetpackagename")) {
                            c = 2;
                            break;
                        }
                        break;
                    case -1165461084:
                        if (str2.equals("priority")) {
                            c = '\b';
                            break;
                        }
                        break;
                    case -836029914:
                        if (str2.equals("userid")) {
                            c = 1;
                            break;
                        }
                        break;
                    case -831052100:
                        if (str2.equals("ismutable")) {
                            c = 7;
                            break;
                        }
                        break;
                    case 50511102:
                        if (str2.equals("category")) {
                            c = '\t';
                            break;
                        }
                        break;
                    case 109757585:
                        if (str2.equals("state")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 440941271:
                        if (str2.equals("isenabled")) {
                            c = 6;
                            break;
                        }
                        break;
                    case 909712337:
                        if (str2.equals("packagename")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1693907299:
                        if (str2.equals("basecodepath")) {
                            c = 4;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case '\b':
                    case '\t':
                        dumpState.setField(str2);
                        break;
                    default:
                        dumpState.setOverlyIdentifier(str2);
                        break;
                }
            }
            if (dumpState.getPackageName() == null && i < strArr.length) {
                dumpState.setOverlyIdentifier(strArr[i]);
            }
            enforceDumpPermission("dump");
            synchronized (com.android.server.om.OverlayManagerService.this.mLock) {
                try {
                    com.android.server.om.OverlayManagerService.this.mImpl.dump(printWriter, dumpState);
                    if (dumpState.getPackageName() == null) {
                        com.android.server.om.OverlayManagerService.this.mPackageManager.dump(printWriter, dumpState);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private int handleIncomingUser(int i, @android.annotation.NonNull java.lang.String str) {
            return android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, str, null);
        }

        private void enforceDumpPermission(@android.annotation.NonNull java.lang.String str) {
            com.android.server.om.OverlayManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.DUMP", str);
        }

        private void enforceActor(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, @android.annotation.NonNull java.lang.String str, int i) throws java.lang.SecurityException {
            android.content.om.OverlayInfo overlayInfo = com.android.server.om.OverlayManagerService.this.mImpl.getOverlayInfo(overlayIdentifier, i);
            if (overlayInfo == null) {
                throw new java.lang.IllegalArgumentException("Unable to retrieve overlay information for " + overlayIdentifier);
            }
            com.android.server.om.OverlayManagerService.this.mActorEnforcer.enforceActor(overlayInfo, str, android.os.Binder.getCallingUid(), i);
        }

        public java.lang.String getPartitionOrder() {
            return com.android.server.om.OverlayManagerService.this.mImpl.getOverlayConfig().getPartitionOrder();
        }

        public boolean isDefaultPartitionOrder() {
            return com.android.server.om.OverlayManagerService.this.mImpl.getOverlayConfig().isDefaultPartitionOrder();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class PackageManagerHelperImpl implements com.android.server.om.PackageManagerHelper {
        private static final java.lang.String TAB1 = "    ";
        private final android.content.Context mContext;
        private final android.util.ArrayMap<java.lang.String, com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers> mCache = new android.util.ArrayMap<>();
        private final android.util.ArraySet<java.lang.Integer> mInitializedUsers = new android.util.ArraySet<>();
        private final android.content.pm.IPackageManager mPackageManager = android.app.AppGlobals.getPackageManager();
        private final android.content.pm.PackageManagerInternal mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);

        private static class PackageStateUsers {
            private final java.util.Set<java.lang.Integer> mInstalledUsers;
            private com.android.server.pm.pkg.PackageState mPackageState;

            private PackageStateUsers(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState) {
                this.mInstalledUsers = new android.util.ArraySet();
                this.mPackageState = packageState;
            }
        }

        PackageManagerHelperImpl(android.content.Context context) {
            this.mContext = context;
        }

        @Override // com.android.server.om.PackageManagerHelper
        @android.annotation.NonNull
        public android.util.ArrayMap<java.lang.String, com.android.server.pm.pkg.PackageState> initializeForUser(final int i) {
            if (this.mInitializedUsers.add(java.lang.Integer.valueOf(i))) {
                this.mPackageManagerInternal.forEachPackageState(new java.util.function.Consumer() { // from class: com.android.server.om.OverlayManagerService$PackageManagerHelperImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.this.lambda$initializeForUser$0(i, (com.android.server.pm.pkg.PackageStateInternal) obj);
                    }
                });
            }
            android.util.ArrayMap<java.lang.String, com.android.server.pm.pkg.PackageState> arrayMap = new android.util.ArrayMap<>();
            int size = this.mCache.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers valueAt = this.mCache.valueAt(i2);
                if (valueAt.mInstalledUsers.contains(java.lang.Integer.valueOf(i))) {
                    arrayMap.put(this.mCache.keyAt(i2), valueAt.mPackageState);
                }
            }
            return arrayMap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$initializeForUser$0(int i, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
            if (packageStateInternal.getPkg() != null && packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                addPackageUser(packageStateInternal, i);
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        @android.annotation.Nullable
        public com.android.server.pm.pkg.PackageState getPackageStateForUser(@android.annotation.NonNull java.lang.String str, int i) {
            com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers packageStateUsers = this.mCache.get(str);
            if (packageStateUsers != null && packageStateUsers.mInstalledUsers.contains(java.lang.Integer.valueOf(i))) {
                return packageStateUsers.mPackageState;
            }
            try {
                if (!this.mPackageManager.isPackageAvailable(str, i)) {
                    return null;
                }
                return addPackageUser(str, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.om.OverlayManagerService.TAG, "Failed to check availability of package '" + str + "' for user " + i, e);
                return null;
            }
        }

        @android.annotation.NonNull
        private com.android.server.pm.pkg.PackageState addPackageUser(@android.annotation.NonNull java.lang.String str, int i) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPackageManagerInternal.getPackageStateInternal(str);
            if (packageStateInternal == null) {
                android.util.Slog.w(com.android.server.om.OverlayManagerService.TAG, "Android package for '" + str + "' could not be found; continuing as if package was never added", new java.lang.Throwable());
                return null;
            }
            return addPackageUser(packageStateInternal, i);
        }

        @android.annotation.NonNull
        private com.android.server.pm.pkg.PackageState addPackageUser(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, int i) {
            com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers packageStateUsers = this.mCache.get(packageState.getPackageName());
            if (packageStateUsers == null) {
                packageStateUsers = new com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers(packageState);
                this.mCache.put(packageState.getPackageName(), packageStateUsers);
            } else {
                packageStateUsers.mPackageState = packageState;
            }
            packageStateUsers.mInstalledUsers.add(java.lang.Integer.valueOf(i));
            return packageStateUsers.mPackageState;
        }

        @android.annotation.NonNull
        private void removePackageUser(@android.annotation.NonNull java.lang.String str, int i) {
            com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers packageStateUsers = this.mCache.get(str);
            if (packageStateUsers == null) {
                return;
            }
            removePackageUser(packageStateUsers, i);
        }

        @android.annotation.NonNull
        private void removePackageUser(@android.annotation.NonNull com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers packageStateUsers, int i) {
            packageStateUsers.mInstalledUsers.remove(java.lang.Integer.valueOf(i));
            if (packageStateUsers.mInstalledUsers.isEmpty()) {
                this.mCache.remove(packageStateUsers.mPackageState.getPackageName());
            }
        }

        @android.annotation.Nullable
        public com.android.server.pm.pkg.PackageState onPackageAdded(@android.annotation.NonNull java.lang.String str, int i) {
            return addPackageUser(str, i);
        }

        @android.annotation.Nullable
        public com.android.server.pm.pkg.PackageState onPackageUpdated(@android.annotation.NonNull java.lang.String str, int i) {
            return addPackageUser(str, i);
        }

        public void onPackageRemoved(@android.annotation.NonNull java.lang.String str, int i) {
            removePackageUser(str, i);
        }

        @Override // com.android.server.om.PackageManagerHelper
        public boolean isInstantApp(@android.annotation.NonNull java.lang.String str, int i) {
            return this.mPackageManagerInternal.isInstantApp(str, i);
        }

        @Override // com.android.server.om.PackageManagerHelper
        @android.annotation.NonNull
        public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> getNamedActors() {
            return com.android.server.SystemConfig.getInstance().getNamedActors();
        }

        @Override // com.android.server.om.PackageManagerHelper
        public boolean signaturesMatching(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
            try {
                return this.mPackageManager.checkSignatures(str, str2, i) == 0;
            } catch (android.os.RemoteException e) {
                return false;
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        public java.lang.String getConfigSignaturePackage() {
            java.lang.String[] knownPackageNames = this.mPackageManagerInternal.getKnownPackageNames(13, 0);
            if (knownPackageNames.length == 0) {
                return null;
            }
            return knownPackageNames[0];
        }

        @Override // com.android.server.om.PackageManagerHelper
        @android.annotation.Nullable
        public android.content.om.OverlayableInfo getOverlayableForTarget(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) throws java.io.IOException {
            com.android.server.pm.pkg.PackageState packageStateForUser = getPackageStateForUser(str, i);
            android.content.res.ApkAssets apkAssets = null;
            com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
            if (androidPackage == null) {
                throw new java.io.IOException("Unable to get target package");
            }
            try {
                apkAssets = android.content.res.ApkAssets.loadFromPath(((com.android.server.pm.pkg.AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath(), 32);
                android.content.om.OverlayableInfo overlayableInfo = apkAssets.getOverlayableInfo(str2);
                try {
                    apkAssets.close();
                } catch (java.lang.Throwable th) {
                }
                return overlayableInfo;
            } catch (java.lang.Throwable th2) {
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (java.lang.Throwable th3) {
                    }
                }
                throw th2;
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        public boolean doesTargetDefineOverlayable(java.lang.String str, int i) throws java.io.IOException {
            com.android.server.pm.pkg.PackageState packageStateForUser = getPackageStateForUser(str, i);
            android.content.res.ApkAssets apkAssets = null;
            com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
            if (androidPackage == null) {
                throw new java.io.IOException("Unable to get target package");
            }
            try {
                apkAssets = android.content.res.ApkAssets.loadFromPath(((com.android.server.pm.pkg.AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath());
                boolean definesOverlayable = apkAssets.definesOverlayable();
                try {
                    apkAssets.close();
                } catch (java.lang.Throwable th) {
                }
                return definesOverlayable;
            } catch (java.lang.Throwable th2) {
                if (apkAssets != null) {
                    try {
                        apkAssets.close();
                    } catch (java.lang.Throwable th3) {
                    }
                }
                throw th2;
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        public void enforcePermission(java.lang.String str, java.lang.String str2) throws java.lang.SecurityException {
            this.mContext.enforceCallingOrSelfPermission(str, str2);
        }

        public void forgetAllPackageInfos(int i) {
            for (int size = this.mCache.size() - 1; size >= 0; size--) {
                removePackageUser(this.mCache.valueAt(size), i);
            }
        }

        @Override // com.android.server.om.PackageManagerHelper
        @android.annotation.Nullable
        public java.lang.String[] getPackagesForUid(int i) {
            try {
                return this.mPackageManager.getPackagesForUid(i);
            } catch (android.os.RemoteException e) {
                return null;
            }
        }

        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.om.DumpState dumpState) {
            printWriter.println("AndroidPackage cache");
            if (!dumpState.isVerbose()) {
                printWriter.println(TAB1 + this.mCache.size() + " package(s)");
                return;
            }
            if (this.mCache.size() == 0) {
                printWriter.println("    <empty>");
                return;
            }
            int size = this.mCache.size();
            for (int i = 0; i < size; i++) {
                java.lang.String keyAt = this.mCache.keyAt(i);
                com.android.server.om.OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers valueAt = this.mCache.valueAt(i);
                printWriter.print(TAB1 + keyAt + ": " + valueAt.mPackageState + " users=");
                printWriter.println(android.text.TextUtils.join(", ", valueAt.mInstalledUsers));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTargetPackagesLocked(@android.annotation.Nullable android.content.pm.UserPackage userPackage) {
        if (userPackage != null) {
            updateTargetPackagesLocked(java.util.Set.of(userPackage));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTargetPackagesLocked(@android.annotation.Nullable java.util.Set<android.content.pm.UserPackage> set) {
        if (com.android.internal.util.CollectionUtils.isEmpty(set)) {
            return;
        }
        persistSettingsLocked();
        android.util.SparseArray<android.util.ArraySet<java.lang.String>> groupTargetsByUserId = groupTargetsByUserId(set);
        int size = groupTargetsByUserId.size();
        for (int i = 0; i < size; i++) {
            final android.util.ArraySet<java.lang.String> valueAt = groupTargetsByUserId.valueAt(i);
            final int keyAt = groupTargetsByUserId.keyAt(i);
            final java.util.List<java.lang.String> updatePackageManagerLocked = updatePackageManagerLocked(valueAt, keyAt);
            if (!updatePackageManagerLocked.isEmpty()) {
                com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.om.OverlayManagerService.this.lambda$updateTargetPackagesLocked$1(updatePackageManagerLocked, keyAt, valueAt);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTargetPackagesLocked$1(java.util.List list, int i, android.util.ArraySet arraySet) {
        updateActivityManager(list, i);
        broadcastActionOverlayChanged(arraySet, i);
    }

    @android.annotation.Nullable
    private static android.util.SparseArray<android.util.ArraySet<java.lang.String>> groupTargetsByUserId(@android.annotation.Nullable java.util.Set<android.content.pm.UserPackage> set) {
        final android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray = new android.util.SparseArray<>();
        com.android.internal.util.CollectionUtils.forEach(set, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda0
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.om.OverlayManagerService.lambda$groupTargetsByUserId$2(sparseArray, (android.content.pm.UserPackage) obj);
            }
        });
        return sparseArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$groupTargetsByUserId$2(android.util.SparseArray sparseArray, android.content.pm.UserPackage userPackage) throws java.lang.Exception {
        android.util.ArraySet arraySet = (android.util.ArraySet) sparseArray.get(userPackage.userId);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet();
            sparseArray.put(userPackage.userId, arraySet);
        }
        arraySet.add(userPackage.packageName);
    }

    private static void broadcastActionOverlayChanged(@android.annotation.NonNull java.util.Set<java.lang.String> set, final int i) {
        final android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        com.android.internal.util.CollectionUtils.forEach(set, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda3
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.om.OverlayManagerService.lambda$broadcastActionOverlayChanged$3(i, activityManagerInternal, (java.lang.String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$broadcastActionOverlayChanged$3(int i, android.app.ActivityManagerInternal activityManagerInternal, java.lang.String str) throws java.lang.Exception {
        android.content.Intent intent = new android.content.Intent("android.intent.action.OVERLAY_CHANGED", android.net.Uri.fromParts(com.android.server.pm.Settings.ATTR_PACKAGE, str, null));
        intent.setFlags(67108864);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.USER_ID", i);
        activityManagerInternal.broadcastIntent(intent, (android.content.IIntentReceiver) null, (java.lang.String[]) null, false, i, (int[]) null, new java.util.function.BiFunction() { // from class: com.android.server.om.OverlayManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                android.os.Bundle filterReceiverAccess;
                filterReceiverAccess = com.android.server.om.OverlayManagerService.filterReceiverAccess(((java.lang.Integer) obj).intValue(), (android.os.Bundle) obj2);
                return filterReceiverAccess;
            }
        }, (android.os.Bundle) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static android.os.Bundle filterReceiverAccess(int i, @android.annotation.NonNull android.os.Bundle bundle) {
        if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).filterAppAccess(bundle.getString("android.intent.extra.PACKAGE_NAME"), i, bundle.getInt("android.intent.extra.USER_ID"), false)) {
            return null;
        }
        return bundle;
    }

    private void updateActivityManager(@android.annotation.NonNull java.util.List<java.lang.String> list, int i) {
        try {
            android.app.ActivityManager.getService().scheduleApplicationInfoChanged(list, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "updateActivityManager remote exception", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.util.SparseArray<java.util.List<java.lang.String>> updatePackageManagerLocked(@android.annotation.Nullable java.util.Set<android.content.pm.UserPackage> set) {
        if (com.android.internal.util.CollectionUtils.isEmpty(set)) {
            return new android.util.SparseArray<>();
        }
        android.util.SparseArray<java.util.List<java.lang.String>> sparseArray = new android.util.SparseArray<>();
        android.util.SparseArray<android.util.ArraySet<java.lang.String>> groupTargetsByUserId = groupTargetsByUserId(set);
        int size = groupTargetsByUserId.size();
        for (int i = 0; i < size; i++) {
            int keyAt = groupTargetsByUserId.keyAt(i);
            sparseArray.put(keyAt, updatePackageManagerLocked(groupTargetsByUserId.valueAt(i), keyAt));
        }
        return sparseArray;
    }

    @android.annotation.NonNull
    private java.util.List<java.lang.String> updatePackageManagerLocked(@android.annotation.NonNull java.util.Collection<java.lang.String> collection, int i) {
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#updatePackageManagerLocked " + collection);
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            if (collection.contains(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
                collection = packageManagerInternal.getTargetPackageNames(i);
            }
            android.util.ArrayMap<java.lang.String, android.content.pm.overlay.OverlayPaths> arrayMap = new android.util.ArrayMap<>(collection.size());
            synchronized (this.mLock) {
                try {
                    android.content.pm.overlay.OverlayPaths enabledOverlayPaths = this.mImpl.getEnabledOverlayPaths(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, i, false);
                    for (java.lang.String str : collection) {
                        android.content.pm.overlay.OverlayPaths.Builder builder = new android.content.pm.overlay.OverlayPaths.Builder(enabledOverlayPaths);
                        if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str)) {
                            builder.addAll(this.mImpl.getEnabledOverlayPaths(str, i, true));
                        }
                        arrayMap.put(str, builder.build());
                    }
                } finally {
                }
            }
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.HashSet hashSet2 = new java.util.HashSet();
            packageManagerInternal.setEnabledOverlayPackages(i, arrayMap, hashSet, hashSet2);
            if (!hashSet2.isEmpty()) {
                for (java.lang.String str2 : collection) {
                    if (hashSet2.contains(str2)) {
                        android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Failed to change enabled overlays for %s user %d", new java.lang.Object[]{str2, java.lang.Integer.valueOf(i)}));
                    }
                }
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(hashSet);
            android.os.Trace.traceEnd(67108864L);
            return arrayList;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(67108864L);
            throw th;
        }
    }

    private void persistSettingsLocked() {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mSettingsFile.startWrite();
            this.mSettings.persist(fileOutputStream);
            this.mSettingsFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            this.mSettingsFile.failWrite(fileOutputStream);
            android.util.Slog.e(TAG, "failed to persist overlay state", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreSettings() {
        java.io.FileInputStream openRead;
        try {
            android.os.Trace.traceBegin(67108864L, "OMS#restoreSettings");
            synchronized (this.mLock) {
                if (!this.mSettingsFile.getBaseFile().exists()) {
                    return;
                }
                try {
                    openRead = this.mSettingsFile.openRead();
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.e(TAG, "failed to restore overlay state", e);
                }
                try {
                    this.mSettings.restore(openRead);
                    java.util.List<android.content.pm.UserInfo> users = this.mUserManager.getUsers(true);
                    int[] iArr = new int[users.size()];
                    for (int i = 0; i < users.size(); i++) {
                        iArr[i] = users.get(i).getUserHandle().getIdentifier();
                    }
                    java.util.Arrays.sort(iArr);
                    for (int i2 : this.mSettings.getUsers()) {
                        if (java.util.Arrays.binarySearch(iArr, i2) < 0) {
                            this.mSettings.removeUser(i2);
                        }
                    }
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
            }
        } finally {
            android.os.Trace.traceEnd(67108864L);
        }
    }
}
