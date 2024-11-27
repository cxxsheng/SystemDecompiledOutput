package com.android.server;

/* loaded from: classes.dex */
public final class SensitiveContentProtectionManagerService extends com.android.server.SystemService {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "SensitiveContentProtect";
    private android.util.ArraySet<java.lang.String> mExemptedPackages;

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.SensitiveContentProtectionManagerService.NotificationListener mNotificationListener;

    @com.android.internal.annotations.GuardedBy({"mSensitiveContentProtectionLock"})
    private boolean mProjectionActive;
    private final android.media.projection.MediaProjectionManager.Callback mProjectionCallback;

    @android.annotation.Nullable
    private android.media.projection.MediaProjectionManager mProjectionManager;
    final java.lang.Object mSensitiveContentProtectionLock;

    @android.annotation.Nullable
    private com.android.server.wm.WindowManagerInternal mWindowManager;

    public SensitiveContentProtectionManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context);
        this.mExemptedPackages = null;
        this.mSensitiveContentProtectionLock = new java.lang.Object();
        this.mProjectionActive = false;
        this.mProjectionCallback = new android.media.projection.MediaProjectionManager.Callback() { // from class: com.android.server.SensitiveContentProtectionManagerService.1
            public void onStart(android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
                android.os.Trace.beginSection("SensitiveContentProtectionManagerService.onProjectionStart");
                try {
                    com.android.server.SensitiveContentProtectionManagerService.this.onProjectionStart(mediaProjectionInfo);
                } finally {
                    android.os.Trace.endSection();
                }
            }

            public void onStop(android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
                android.os.Trace.beginSection("SensitiveContentProtectionManagerService.onProjectionStop");
                try {
                    com.android.server.SensitiveContentProtectionManagerService.this.onProjectionEnd();
                } finally {
                    android.os.Trace.endSection();
                }
            }
        };
        if (android.permission.flags.Flags.sensitiveNotificationAppProtection()) {
            this.mNotificationListener = new com.android.server.SensitiveContentProtectionManagerService.NotificationListener();
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i != 1000) {
            return;
        }
        init((android.media.projection.MediaProjectionManager) getContext().getSystemService(android.media.projection.MediaProjectionManager.class), (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class), getExemptedPackages());
        if (android.view.flags.Flags.sensitiveContentAppProtection()) {
            publishBinderService("sensitive_content_protection_service", new com.android.server.SensitiveContentProtectionManagerService.SensitiveContentProtectionManagerServiceBinder());
        }
    }

    private android.util.ArraySet<java.lang.String> getExemptedPackages() {
        return com.android.server.SystemConfig.getInstance().getBugreportWhitelistedPackages();
    }

    @com.android.internal.annotations.VisibleForTesting
    void init(android.media.projection.MediaProjectionManager mediaProjectionManager, com.android.server.wm.WindowManagerInternal windowManagerInternal, android.util.ArraySet<java.lang.String> arraySet) {
        java.util.Objects.requireNonNull(mediaProjectionManager);
        java.util.Objects.requireNonNull(windowManagerInternal);
        this.mProjectionManager = mediaProjectionManager;
        this.mWindowManager = windowManagerInternal;
        this.mExemptedPackages = arraySet;
        this.mProjectionManager.addCallback(this.mProjectionCallback, getContext().getMainThreadHandler());
        if (android.permission.flags.Flags.sensitiveNotificationAppProtection()) {
            try {
                this.mNotificationListener.registerAsSystemService(getContext(), new android.content.ComponentName(getContext(), (java.lang.Class<?>) com.android.server.SensitiveContentProtectionManagerService.NotificationListener.class), -1);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onDestroy() {
        if (this.mProjectionManager != null) {
            this.mProjectionManager.removeCallback(this.mProjectionCallback);
        }
        if (android.permission.flags.Flags.sensitiveNotificationAppProtection()) {
            try {
                this.mNotificationListener.unregisterAsSystemService();
            } catch (android.os.RemoteException e) {
            }
        }
        if (this.mWindowManager != null) {
            onProjectionEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProjectionStart(android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
        if (this.mExemptedPackages != null && this.mExemptedPackages.contains(mediaProjectionInfo.getPackageName())) {
            android.util.Log.w(TAG, mediaProjectionInfo.getPackageName() + " is exempted from screen share protection.");
            return;
        }
        if (android.provider.Settings.Global.getInt(getContext().getContentResolver(), "disable_screen_share_protections_for_apps_and_notifications", 0) != 0) {
            android.util.Log.w(TAG, "Screen share protections disabled, ignoring projection start");
            return;
        }
        synchronized (this.mSensitiveContentProtectionLock) {
            try {
                this.mProjectionActive = true;
                if (android.permission.flags.Flags.sensitiveNotificationAppProtection()) {
                    updateAppsThatShouldBlockScreenCapture();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProjectionEnd() {
        synchronized (this.mSensitiveContentProtectionLock) {
            this.mProjectionActive = false;
            this.mWindowManager.clearBlockedApps();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppsThatShouldBlockScreenCapture() {
        android.service.notification.NotificationListenerService.RankingMap rankingMap;
        try {
            rankingMap = this.mNotificationListener.getCurrentRanking();
        } catch (java.lang.SecurityException e) {
            android.util.Log.e(TAG, "SensitiveContentProtectionManagerService doesn't have access.", e);
            rankingMap = null;
        }
        updateAppsThatShouldBlockScreenCapture(rankingMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppsThatShouldBlockScreenCapture(android.service.notification.NotificationListenerService.RankingMap rankingMap) {
        android.service.notification.StatusBarNotification[] statusBarNotificationArr;
        try {
            statusBarNotificationArr = this.mNotificationListener.getActiveNotifications();
        } catch (java.lang.SecurityException e) {
            android.util.Log.e(TAG, "SensitiveContentProtectionManagerService doesn't have access.", e);
            statusBarNotificationArr = new android.service.notification.StatusBarNotification[0];
        }
        this.mWindowManager.addBlockScreenCaptureForApps(getSensitivePackagesFromNotifications(statusBarNotificationArr, rankingMap));
    }

    private android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> getSensitivePackagesFromNotifications(@android.annotation.NonNull android.service.notification.StatusBarNotification[] statusBarNotificationArr, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
        android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> arraySet = new android.util.ArraySet<>();
        if (rankingMap == null) {
            android.util.Log.w(TAG, "Ranking map not initialized.");
            return arraySet;
        }
        for (android.service.notification.StatusBarNotification statusBarNotification : statusBarNotificationArr) {
            com.android.server.wm.SensitiveContentPackages.PackageInfo sensitivePackageFromNotification = getSensitivePackageFromNotification(statusBarNotification, rankingMap);
            if (sensitivePackageFromNotification != null) {
                arraySet.add(sensitivePackageFromNotification);
            }
        }
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.wm.SensitiveContentPackages.PackageInfo getSensitivePackageFromNotification(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
        if (statusBarNotification == null) {
            android.util.Log.w(TAG, "Unable to protect null notification");
            return null;
        }
        if (rankingMap == null) {
            android.util.Log.w(TAG, "Ranking map not initialized.");
            return null;
        }
        android.service.notification.NotificationListenerService.Ranking rawRankingObject = rankingMap.getRawRankingObject(statusBarNotification.getKey());
        if (rawRankingObject == null || !rawRankingObject.hasSensitiveContent()) {
            return null;
        }
        return new com.android.server.wm.SensitiveContentPackages.PackageInfo(statusBarNotification.getPackageName(), statusBarNotification.getUid());
    }

    @com.android.internal.annotations.VisibleForTesting
    class NotificationListener extends android.service.notification.NotificationListenerService {
        NotificationListener() {
        }

        @Override // android.service.notification.NotificationListenerService
        public void onListenerConnected() {
            super.onListenerConnected();
            android.os.Trace.beginSection("SensitiveContentProtectionManagerService.onListenerConnected");
            try {
                synchronized (com.android.server.SensitiveContentProtectionManagerService.this.mSensitiveContentProtectionLock) {
                    try {
                        if (com.android.server.SensitiveContentProtectionManagerService.this.mProjectionActive) {
                            com.android.server.SensitiveContentProtectionManagerService.this.updateAppsThatShouldBlockScreenCapture();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Trace.endSection();
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationPosted(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
            super.onNotificationPosted(statusBarNotification, rankingMap);
            android.os.Trace.beginSection("SensitiveContentProtectionManagerService.onNotificationPosted");
            try {
                synchronized (com.android.server.SensitiveContentProtectionManagerService.this.mSensitiveContentProtectionLock) {
                    if (!com.android.server.SensitiveContentProtectionManagerService.this.mProjectionActive) {
                        return;
                    }
                    com.android.server.wm.SensitiveContentPackages.PackageInfo sensitivePackageFromNotification = com.android.server.SensitiveContentProtectionManagerService.this.getSensitivePackageFromNotification(statusBarNotification, rankingMap);
                    if (sensitivePackageFromNotification != null) {
                        com.android.server.SensitiveContentProtectionManagerService.this.mWindowManager.addBlockScreenCaptureForApps(new android.util.ArraySet<>(java.util.Set.of(sensitivePackageFromNotification)));
                    }
                }
            } finally {
                android.os.Trace.endSection();
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationRankingUpdate(android.service.notification.NotificationListenerService.RankingMap rankingMap) {
            super.onNotificationRankingUpdate(rankingMap);
            android.os.Trace.beginSection("SensitiveContentProtectionManagerService.onNotificationRankingUpdate");
            try {
                synchronized (com.android.server.SensitiveContentProtectionManagerService.this.mSensitiveContentProtectionLock) {
                    try {
                        if (com.android.server.SensitiveContentProtectionManagerService.this.mProjectionActive) {
                            com.android.server.SensitiveContentProtectionManagerService.this.updateAppsThatShouldBlockScreenCapture(rankingMap);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Trace.endSection();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setSensitiveContentProtection(android.os.IBinder iBinder, java.lang.String str, int i, boolean z) {
        synchronized (this.mSensitiveContentProtectionLock) {
            try {
                if (this.mProjectionActive) {
                    com.android.server.wm.SensitiveContentPackages.PackageInfo packageInfo = new com.android.server.wm.SensitiveContentPackages.PackageInfo(str, i, iBinder);
                    android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> arraySet = new android.util.ArraySet<>();
                    arraySet.add(packageInfo);
                    if (z) {
                        this.mWindowManager.addBlockScreenCaptureForApps(arraySet);
                    } else {
                        this.mWindowManager.removeBlockScreenCaptureForApps(arraySet);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private final class SensitiveContentProtectionManagerServiceBinder extends android.view.ISensitiveContentProtectionManager.Stub {
        private final android.content.pm.PackageManagerInternal mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);

        SensitiveContentProtectionManagerServiceBinder() {
        }

        public void setSensitiveContentProtection(android.os.IBinder iBinder, java.lang.String str, boolean z) {
            android.os.Trace.beginSection("SensitiveContentProtectionManagerService.setSensitiveContentProtection");
            try {
                int callingUid = android.os.Binder.getCallingUid();
                verifyCallingPackage(callingUid, str);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.SensitiveContentProtectionManagerService.this.setSensitiveContentProtection(iBinder, str, callingUid, z);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                android.os.Trace.endSection();
            }
        }

        private void verifyCallingPackage(int i, java.lang.String str) {
            if (this.mPackageManagerInternal.getPackageUid(str, 0L, android.os.UserHandle.getUserId(i)) != i) {
                throw new java.lang.SecurityException("Specified calling package [" + str + "] does not match the calling uid " + i);
            }
        }
    }
}
