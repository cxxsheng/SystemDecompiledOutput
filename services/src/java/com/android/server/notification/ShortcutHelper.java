package com.android.server.notification;

/* loaded from: classes2.dex */
public class ShortcutHelper {
    private static final android.content.IntentFilter SHARING_FILTER = new android.content.IntentFilter();
    private static final java.lang.String TAG = "ShortcutHelper";
    private java.util.HashMap<java.lang.String, java.util.HashMap<java.lang.String, java.lang.String>> mActiveShortcutBubbles = new java.util.HashMap<>();
    private final android.content.pm.LauncherApps.Callback mLauncherAppsCallback = new android.content.pm.LauncherApps.Callback() { // from class: com.android.server.notification.ShortcutHelper.1
        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageRemoved(java.lang.String str, android.os.UserHandle userHandle) {
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageAdded(java.lang.String str, android.os.UserHandle userHandle) {
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageChanged(java.lang.String str, android.os.UserHandle userHandle) {
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesAvailable(java.lang.String[] strArr, android.os.UserHandle userHandle, boolean z) {
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesUnavailable(java.lang.String[] strArr, android.os.UserHandle userHandle, boolean z) {
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onShortcutsChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.NonNull android.os.UserHandle userHandle) {
            boolean z;
            java.util.HashMap hashMap = (java.util.HashMap) com.android.server.notification.ShortcutHelper.this.mActiveShortcutBubbles.get(str);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (hashMap != null) {
                for (java.lang.String str2 : new java.util.HashSet(hashMap.keySet())) {
                    int i = 0;
                    while (true) {
                        if (i >= list.size()) {
                            z = false;
                            break;
                        } else if (!list.get(i).getId().equals(str2)) {
                            i++;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        arrayList.add((java.lang.String) hashMap.get(str2));
                        hashMap.remove(str2);
                        if (hashMap.isEmpty()) {
                            com.android.server.notification.ShortcutHelper.this.mActiveShortcutBubbles.remove(str);
                            if (com.android.server.notification.ShortcutHelper.this.mLauncherAppsCallbackRegistered && com.android.server.notification.ShortcutHelper.this.mActiveShortcutBubbles.isEmpty()) {
                                com.android.server.notification.ShortcutHelper.this.mLauncherAppsService.unregisterCallback(com.android.server.notification.ShortcutHelper.this.mLauncherAppsCallback);
                                com.android.server.notification.ShortcutHelper.this.mLauncherAppsCallbackRegistered = false;
                            }
                        }
                    }
                }
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                java.lang.String str3 = (java.lang.String) arrayList.get(i2);
                if (com.android.server.notification.ShortcutHelper.this.mShortcutListener != null) {
                    com.android.server.notification.ShortcutHelper.this.mShortcutListener.onShortcutRemoved(str3);
                }
            }
        }
    };
    private boolean mLauncherAppsCallbackRegistered;
    private android.content.pm.LauncherApps mLauncherAppsService;
    private com.android.server.notification.ShortcutHelper.ShortcutListener mShortcutListener;
    private android.content.pm.ShortcutServiceInternal mShortcutServiceInternal;
    private android.os.UserManager mUserManager;

    interface ShortcutListener {
        void onShortcutRemoved(java.lang.String str);
    }

    static {
        try {
            SHARING_FILTER.addDataType("*/*");
        } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
            android.util.Slog.e(TAG, "Bad mime type", e);
        }
    }

    ShortcutHelper(android.content.pm.LauncherApps launcherApps, com.android.server.notification.ShortcutHelper.ShortcutListener shortcutListener, android.content.pm.ShortcutServiceInternal shortcutServiceInternal, android.os.UserManager userManager) {
        this.mLauncherAppsService = launcherApps;
        this.mShortcutListener = shortcutListener;
        this.mShortcutServiceInternal = shortcutServiceInternal;
        this.mUserManager = userManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setLauncherApps(android.content.pm.LauncherApps launcherApps) {
        this.mLauncherAppsService = launcherApps;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setShortcutServiceInternal(android.content.pm.ShortcutServiceInternal shortcutServiceInternal) {
        this.mShortcutServiceInternal = shortcutServiceInternal;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setUserManager(android.os.UserManager userManager) {
        this.mUserManager = userManager;
    }

    public static boolean isConversationShortcut(android.content.pm.ShortcutInfo shortcutInfo, android.content.pm.ShortcutServiceInternal shortcutServiceInternal, int i) {
        if (shortcutInfo == null || !shortcutInfo.isLongLived() || !shortcutInfo.isEnabled()) {
            return false;
        }
        return true;
    }

    android.content.pm.ShortcutInfo getValidShortcutInfo(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        if (this.mLauncherAppsService == null || !this.mUserManager.isUserUnlocked(userHandle)) {
            return null;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (str == null || str2 == null || userHandle == null) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        }
        try {
            android.content.pm.LauncherApps.ShortcutQuery shortcutQuery = new android.content.pm.LauncherApps.ShortcutQuery();
            shortcutQuery.setPackage(str2);
            shortcutQuery.setShortcutIds(java.util.Arrays.asList(str));
            shortcutQuery.setQueryFlags(3089);
            java.util.List<android.content.pm.ShortcutInfo> shortcuts = this.mLauncherAppsService.getShortcuts(shortcutQuery, userHandle);
            android.content.pm.ShortcutInfo shortcutInfo = (shortcuts == null || shortcuts.size() <= 0) ? null : shortcuts.get(0);
            if (isConversationShortcut(shortcutInfo, this.mShortcutServiceInternal, userHandle.getIdentifier())) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return shortcutInfo;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    void cacheShortcut(android.content.pm.ShortcutInfo shortcutInfo, android.os.UserHandle userHandle) {
        if (shortcutInfo.isLongLived() && !shortcutInfo.isCached()) {
            this.mShortcutServiceInternal.cacheShortcuts(userHandle.getIdentifier(), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, shortcutInfo.getPackage(), java.util.Collections.singletonList(shortcutInfo.getId()), shortcutInfo.getUserId(), 16384);
        }
    }

    void maybeListenForShortcutChangesForBubbles(com.android.server.notification.NotificationRecord notificationRecord, boolean z, android.os.Handler handler) {
        java.lang.String str;
        if (notificationRecord.getNotification().getBubbleMetadata() != null) {
            str = notificationRecord.getNotification().getBubbleMetadata().getShortcutId();
        } else {
            str = null;
        }
        if (!z && !android.text.TextUtils.isEmpty(str) && notificationRecord.getShortcutInfo() != null && notificationRecord.getShortcutInfo().getId().equals(str)) {
            java.util.HashMap<java.lang.String, java.lang.String> hashMap = this.mActiveShortcutBubbles.get(notificationRecord.getSbn().getPackageName());
            if (hashMap == null) {
                hashMap = new java.util.HashMap<>();
            }
            hashMap.put(str, notificationRecord.getKey());
            this.mActiveShortcutBubbles.put(notificationRecord.getSbn().getPackageName(), hashMap);
            if (!this.mLauncherAppsCallbackRegistered) {
                this.mLauncherAppsService.registerCallback(this.mLauncherAppsCallback, handler);
                this.mLauncherAppsCallbackRegistered = true;
                return;
            }
            return;
        }
        java.util.HashMap<java.lang.String, java.lang.String> hashMap2 = this.mActiveShortcutBubbles.get(notificationRecord.getSbn().getPackageName());
        if (hashMap2 != null) {
            if (!android.text.TextUtils.isEmpty(str)) {
                hashMap2.remove(str);
            } else {
                for (java.lang.String str2 : new java.util.HashSet(hashMap2.keySet())) {
                    if (notificationRecord.getKey().equals(hashMap2.get(str2))) {
                        hashMap2.remove(str2);
                    }
                }
            }
            if (hashMap2.isEmpty()) {
                this.mActiveShortcutBubbles.remove(notificationRecord.getSbn().getPackageName());
            }
        }
        if (this.mLauncherAppsCallbackRegistered && this.mActiveShortcutBubbles.isEmpty()) {
            this.mLauncherAppsService.unregisterCallback(this.mLauncherAppsCallback);
            this.mLauncherAppsCallbackRegistered = false;
        }
    }
}
