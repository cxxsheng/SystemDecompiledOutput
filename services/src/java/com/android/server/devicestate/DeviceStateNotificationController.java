package com.android.server.devicestate;

/* loaded from: classes.dex */
class DeviceStateNotificationController extends android.content.BroadcastReceiver {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String CHANNEL_ID = "DeviceStateManager";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String INTENT_ACTION_CANCEL_STATE = "com.android.server.devicestate.INTENT_ACTION_CANCEL_STATE";

    @com.android.internal.annotations.VisibleForTesting
    static final int NOTIFICATION_ID = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String NOTIFICATION_TAG = "DeviceStateManager";
    private static final java.lang.String TAG = "DeviceStateNotificationController";
    private final java.lang.Runnable mCancelStateRunnable;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final com.android.server.devicestate.DeviceStateNotificationController.NotificationInfoProvider mNotificationInfoProvider;
    private final android.app.NotificationManager mNotificationManager;
    private final android.content.pm.PackageManager mPackageManager;

    DeviceStateNotificationController(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.lang.Runnable runnable) {
        this(context, handler, runnable, new com.android.server.devicestate.DeviceStateNotificationController.NotificationInfoProvider(context), context.getPackageManager(), (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class));
    }

    @com.android.internal.annotations.VisibleForTesting
    DeviceStateNotificationController(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.lang.Runnable runnable, @android.annotation.NonNull com.android.server.devicestate.DeviceStateNotificationController.NotificationInfoProvider notificationInfoProvider, @android.annotation.NonNull android.content.pm.PackageManager packageManager, @android.annotation.NonNull android.app.NotificationManager notificationManager) {
        this.mContext = context;
        this.mHandler = handler;
        this.mCancelStateRunnable = runnable;
        this.mNotificationInfoProvider = notificationInfoProvider;
        this.mPackageManager = packageManager;
        this.mNotificationManager = notificationManager;
        this.mContext.registerReceiver(this, new android.content.IntentFilter(INTENT_ACTION_CANCEL_STATE), "android.permission.CONTROL_DEVICE_STATE", this.mHandler, 4);
    }

    void showStateActiveNotificationIfNeeded(int i, int i2) {
        com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo notificationInfo = getNotificationInfos().get(i);
        if (notificationInfo == null || !notificationInfo.hasActiveNotification()) {
            return;
        }
        java.lang.String applicationLabel = getApplicationLabel(i2);
        if (applicationLabel != null) {
            showNotification(notificationInfo.name, notificationInfo.activeNotificationTitle, java.lang.String.format(notificationInfo.activeNotificationContent, applicationLabel), true, android.R.drawable.ic_doc_word, android.app.PendingIntent.getBroadcast(this.mContext, 0, new android.content.Intent(INTENT_ACTION_CANCEL_STATE).setPackage(this.mContext.getPackageName()), 67108864), this.mContext.getString(android.R.string.delete));
        } else {
            android.util.Slog.e(TAG, "Cannot determine the requesting app name when showing state active notification. uid=" + i2 + ", state=" + i);
        }
    }

    void showThermalCriticalNotificationIfNeeded(int i) {
        com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo notificationInfo = getNotificationInfos().get(i);
        if (notificationInfo == null || !notificationInfo.hasThermalCriticalNotification()) {
            return;
        }
        showNotification(notificationInfo.name, notificationInfo.thermalCriticalNotificationTitle, notificationInfo.thermalCriticalNotificationContent, false, android.R.drawable.ic_test_icon_badge_experiment, null, null);
    }

    void showPowerSaveNotificationIfNeeded(int i) {
        com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo notificationInfo = getNotificationInfos().get(i);
        if (notificationInfo == null || !notificationInfo.hasPowerSaveModeNotification()) {
            return;
        }
        showNotification(notificationInfo.name, notificationInfo.powerSaveModeNotificationTitle, notificationInfo.powerSaveModeNotificationContent, false, android.R.drawable.ic_test_icon_badge_experiment, android.app.PendingIntent.getActivity(this.mContext, 0, new android.content.Intent("android.settings.BATTERY_SAVER_SETTINGS"), 67108864), this.mContext.getString(android.R.string.default_wallpaper_component));
    }

    void cancelNotification(int i) {
        if (getNotificationInfos().get(i) == null) {
            return;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.devicestate.DeviceStateNotificationController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicestate.DeviceStateNotificationController.this.lambda$cancelNotification$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelNotification$0() {
        this.mNotificationManager.cancel("DeviceStateManager", 1);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable android.content.Intent intent) {
        if (intent != null && INTENT_ACTION_CANCEL_STATE.equals(intent.getAction())) {
            this.mCancelStateRunnable.run();
        }
    }

    private void showNotification(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, boolean z, int i, @android.annotation.Nullable android.app.PendingIntent pendingIntent, @android.annotation.Nullable java.lang.String str4) {
        final android.app.NotificationChannel notificationChannel = new android.app.NotificationChannel("DeviceStateManager", str, 4);
        final android.app.Notification.Builder category = new android.app.Notification.Builder(this.mContext, "DeviceStateManager").setSmallIcon(i).setContentTitle(str2).setContentText(str3).setSubText(str).setLocalOnly(true).setOngoing(z).setCategory("sys");
        if (pendingIntent != null && str4 != null) {
            category.addAction(new android.app.Notification.Action.Builder((android.graphics.drawable.Icon) null, str4, pendingIntent).build());
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.devicestate.DeviceStateNotificationController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicestate.DeviceStateNotificationController.this.lambda$showNotification$1(notificationChannel, category);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNotification$1(android.app.NotificationChannel notificationChannel, android.app.Notification.Builder builder) {
        this.mNotificationManager.createNotificationChannel(notificationChannel);
        this.mNotificationManager.notify("DeviceStateManager", 1, builder.build());
    }

    private android.util.SparseArray<com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo> getNotificationInfos() {
        return this.mNotificationInfoProvider.getNotificationInfos(this.mContext.getResources().getConfiguration().getLocales().get(0));
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class NotificationInfoProvider {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @com.android.internal.annotations.VisibleForTesting
        @android.annotation.Nullable
        java.util.Locale mCachedLocale;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        private android.util.SparseArray<com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo> mCachedNotificationInfos;

        @android.annotation.NonNull
        private final android.content.Context mContext;
        private final java.lang.Object mLock = new java.lang.Object();

        NotificationInfoProvider(@android.annotation.NonNull android.content.Context context) {
            this.mContext = context;
        }

        @android.annotation.NonNull
        public android.util.SparseArray<com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo> getNotificationInfos(@android.annotation.NonNull java.util.Locale locale) {
            android.util.SparseArray<com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo> sparseArray;
            synchronized (this.mLock) {
                try {
                    if (!locale.equals(this.mCachedLocale)) {
                        refreshNotificationInfos(locale);
                    }
                    sparseArray = this.mCachedNotificationInfos;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return sparseArray;
        }

        @com.android.internal.annotations.VisibleForTesting
        java.util.Locale getCachedLocale() {
            java.util.Locale locale;
            synchronized (this.mLock) {
                locale = this.mCachedLocale;
            }
            return locale;
        }

        @com.android.internal.annotations.VisibleForTesting
        public void refreshNotificationInfos(java.util.Locale locale) {
            synchronized (this.mLock) {
                this.mCachedLocale = locale;
                this.mCachedNotificationInfos = loadNotificationInfos();
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        public android.util.SparseArray<com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo> loadNotificationInfos() {
            android.util.SparseArray<com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo> sparseArray = new android.util.SparseArray<>();
            int[] intArray = this.mContext.getResources().getIntArray(android.R.array.device_state_notification_names);
            java.lang.String[] stringArray = this.mContext.getResources().getStringArray(android.R.array.demo_device_provisioning_known_signers);
            java.lang.String[] stringArray2 = this.mContext.getResources().getStringArray(android.R.array.default_wallpaper_component_per_device_color);
            java.lang.String[] stringArray3 = this.mContext.getResources().getStringArray(android.R.array.cross_profile_apps);
            java.lang.String[] stringArray4 = this.mContext.getResources().getStringArray(android.R.array.device_state_notification_power_save_titles);
            java.lang.String[] stringArray5 = this.mContext.getResources().getStringArray(android.R.array.device_state_notification_power_save_contents);
            java.lang.String[] stringArray6 = this.mContext.getResources().getStringArray(android.R.array.device_state_notification_active_titles);
            java.lang.String[] stringArray7 = this.mContext.getResources().getStringArray(android.R.array.device_state_notification_active_contents);
            if (intArray.length != stringArray.length || intArray.length != stringArray2.length || intArray.length != stringArray3.length || intArray.length != stringArray4.length || intArray.length != stringArray5.length || intArray.length != stringArray6.length || intArray.length != stringArray7.length) {
                throw new java.lang.IllegalStateException("The length of state identifiers and notification texts must match!");
            }
            for (int i = 0; i < intArray.length; i++) {
                int i2 = intArray[i];
                if (i2 != -1) {
                    sparseArray.put(i2, new com.android.server.devicestate.DeviceStateNotificationController.NotificationInfo(stringArray[i], stringArray2[i], stringArray3[i], stringArray4[i], stringArray5[i], stringArray6[i], stringArray7[i]));
                }
            }
            return sparseArray;
        }
    }

    @android.annotation.Nullable
    private java.lang.String getApplicationLabel(int i) {
        try {
            return this.mPackageManager.getApplicationInfo(this.mPackageManager.getNameForUid(i), android.content.pm.PackageManager.ApplicationInfoFlags.of(0L)).loadLabel(this.mPackageManager).toString();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class NotificationInfo {
        public final java.lang.String activeNotificationContent;
        public final java.lang.String activeNotificationTitle;
        public final java.lang.String name;
        public final java.lang.String powerSaveModeNotificationContent;
        public final java.lang.String powerSaveModeNotificationTitle;
        public final java.lang.String thermalCriticalNotificationContent;
        public final java.lang.String thermalCriticalNotificationTitle;

        NotificationInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7) {
            this.name = str;
            this.activeNotificationTitle = str2;
            this.activeNotificationContent = str3;
            this.thermalCriticalNotificationTitle = str4;
            this.thermalCriticalNotificationContent = str5;
            this.powerSaveModeNotificationTitle = str6;
            this.powerSaveModeNotificationContent = str7;
        }

        boolean hasActiveNotification() {
            return this.activeNotificationTitle != null && this.activeNotificationTitle.length() > 0;
        }

        boolean hasThermalCriticalNotification() {
            return this.thermalCriticalNotificationTitle != null && this.thermalCriticalNotificationTitle.length() > 0;
        }

        boolean hasPowerSaveModeNotification() {
            return this.powerSaveModeNotificationTitle != null && this.powerSaveModeNotificationTitle.length() > 0;
        }
    }
}
