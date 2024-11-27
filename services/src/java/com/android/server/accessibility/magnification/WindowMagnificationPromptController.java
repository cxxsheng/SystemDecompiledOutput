package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class WindowMagnificationPromptController {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String ACTION_DISMISS = "com.android.server.accessibility.magnification.action.DISMISS";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String ACTION_TURN_ON_IN_SETTINGS = "com.android.server.accessibility.magnification.action.TURN_ON_IN_SETTINGS";
    private static final android.net.Uri MAGNIFICATION_WINDOW_MODE_PROMPT_URI = android.provider.Settings.Secure.getUriFor("accessibility_show_window_magnification_prompt");
    private final android.database.ContentObserver mContentObserver = new android.database.ContentObserver(null) { // from class: com.android.server.accessibility.magnification.WindowMagnificationPromptController.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            com.android.server.accessibility.magnification.WindowMagnificationPromptController.this.onPromptSettingsValueChanged();
        }
    };
    private final android.content.Context mContext;
    private boolean mNeedToShowNotification;

    @com.android.internal.annotations.VisibleForTesting
    android.content.BroadcastReceiver mNotificationActionReceiver;
    private final android.app.NotificationManager mNotificationManager;
    private final int mUserId;

    public WindowMagnificationPromptController(@android.annotation.NonNull android.content.Context context, int i) {
        this.mContext = context;
        this.mNotificationManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
        this.mUserId = i;
        context.getContentResolver().registerContentObserver(MAGNIFICATION_WINDOW_MODE_PROMPT_URI, false, this.mContentObserver, this.mUserId);
        this.mNeedToShowNotification = isWindowMagnificationPromptEnabled();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void onPromptSettingsValueChanged() {
        boolean isWindowMagnificationPromptEnabled = isWindowMagnificationPromptEnabled();
        if (this.mNeedToShowNotification == isWindowMagnificationPromptEnabled) {
            return;
        }
        this.mNeedToShowNotification = isWindowMagnificationPromptEnabled;
        if (!this.mNeedToShowNotification) {
            unregisterReceiverIfNeeded();
            this.mNotificationManager.cancel(1004);
        }
    }

    void showNotificationIfNeeded() {
        if (this.mNeedToShowNotification) {
            android.app.Notification.Builder builder = new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.ACCESSIBILITY_MAGNIFICATION);
            java.lang.String string = this.mContext.getString(android.R.string.wfc_mode_wifi_only_summary);
            builder.setSmallIcon(android.R.drawable.ic_ab_back_material_settings).setContentTitle(this.mContext.getString(android.R.string.wfc_mode_wifi_preferred_summary)).setContentText(string).setLargeIcon(android.graphics.drawable.Icon.createWithResource(this.mContext, android.R.drawable.ic_accessibility_hearing_aid_foreground)).setTicker(this.mContext.getString(android.R.string.wfc_mode_wifi_preferred_summary)).setOnlyAlertOnce(true).setStyle(new android.app.Notification.BigTextStyle().bigText(string)).setDeleteIntent(createPendingIntent(ACTION_DISMISS)).setContentIntent(createPendingIntent(ACTION_TURN_ON_IN_SETTINGS)).setActions(buildTurnOnAction());
            this.mNotificationManager.notify(1004, builder.build());
            registerReceiverIfNeeded();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void onDestroy() {
        dismissNotification();
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    private boolean isWindowMagnificationPromptEnabled() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_show_window_magnification_prompt", 0, this.mUserId) == 1;
    }

    private android.app.Notification.Action buildTurnOnAction() {
        return new android.app.Notification.Action.Builder((android.graphics.drawable.Icon) null, this.mContext.getString(android.R.string.sync_do_nothing), createPendingIntent(ACTION_TURN_ON_IN_SETTINGS)).build();
    }

    private android.app.PendingIntent createPendingIntent(java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(str);
        intent.setPackage(this.mContext.getPackageName());
        return android.app.PendingIntent.getBroadcast(this.mContext, 0, intent, 67108864);
    }

    private void registerReceiverIfNeeded() {
        if (this.mNotificationActionReceiver != null) {
            return;
        }
        this.mNotificationActionReceiver = new com.android.server.accessibility.magnification.WindowMagnificationPromptController.NotificationActionReceiver();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(ACTION_DISMISS);
        intentFilter.addAction(ACTION_TURN_ON_IN_SETTINGS);
        this.mContext.registerReceiver(this.mNotificationActionReceiver, intentFilter, "android.permission.MANAGE_ACCESSIBILITY", null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchMagnificationSettings() {
        android.content.Intent intent = new android.content.Intent("android.settings.ACCESSIBILITY_DETAILS_SETTINGS");
        intent.addFlags(268468224);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", com.android.internal.accessibility.AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToShortString());
        intent.addFlags(268435456);
        this.mContext.startActivityAsUser(intent, android.app.ActivityOptions.makeBasic().setLaunchDisplayId(this.mContext.getDisplayId()).toBundle(), android.os.UserHandle.of(this.mUserId));
        ((android.app.StatusBarManager) this.mContext.getSystemService(android.app.StatusBarManager.class)).collapsePanels();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissNotification() {
        unregisterReceiverIfNeeded();
        this.mNotificationManager.cancel(1004);
    }

    private void unregisterReceiverIfNeeded() {
        if (this.mNotificationActionReceiver == null) {
            return;
        }
        this.mContext.unregisterReceiver(this.mNotificationActionReceiver);
        this.mNotificationActionReceiver = null;
    }

    private class NotificationActionReceiver extends android.content.BroadcastReceiver {
        private NotificationActionReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            if (android.text.TextUtils.isEmpty(action)) {
                return;
            }
            com.android.server.accessibility.magnification.WindowMagnificationPromptController.this.mNeedToShowNotification = false;
            android.provider.Settings.Secure.putIntForUser(com.android.server.accessibility.magnification.WindowMagnificationPromptController.this.mContext.getContentResolver(), "accessibility_show_window_magnification_prompt", 0, com.android.server.accessibility.magnification.WindowMagnificationPromptController.this.mUserId);
            if (com.android.server.accessibility.magnification.WindowMagnificationPromptController.ACTION_TURN_ON_IN_SETTINGS.equals(action)) {
                com.android.server.accessibility.magnification.WindowMagnificationPromptController.this.launchMagnificationSettings();
                com.android.server.accessibility.magnification.WindowMagnificationPromptController.this.dismissNotification();
            } else if (com.android.server.accessibility.magnification.WindowMagnificationPromptController.ACTION_DISMISS.equals(action)) {
                com.android.server.accessibility.magnification.WindowMagnificationPromptController.this.dismissNotification();
            }
        }
    }
}
