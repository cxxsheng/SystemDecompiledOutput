package com.android.server.display.notifications;

/* loaded from: classes.dex */
public class DisplayNotificationManager implements com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Listener {
    private static final int DISPLAY_NOTIFICATION_ID = 1;
    private static final java.lang.String DISPLAY_NOTIFICATION_TAG = "DisplayNotificationManager";
    private static final java.lang.String NOTIFICATION_GROUP_NAME = "DisplayNotificationManager";
    private static final long NOTIFICATION_TIMEOUT_MILLISEC = 30000;
    private static final java.lang.String TAG = "DisplayNotificationManager";
    private final boolean mConnectedDisplayErrorHandlingEnabled;
    private com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector mConnectedDisplayUsbErrorsDetector;
    private final android.content.Context mContext;
    private final com.android.server.display.ExternalDisplayStatsService mExternalDisplayStatsService;
    private final com.android.server.display.notifications.DisplayNotificationManager.Injector mInjector;
    private android.app.NotificationManager mNotificationManager;

    public interface Injector {
        @android.annotation.Nullable
        com.android.server.display.ExternalDisplayStatsService getExternalDisplayStatsService();

        @android.annotation.Nullable
        android.app.NotificationManager getNotificationManager();

        @android.annotation.Nullable
        com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector getUsbErrorsDetector();
    }

    public DisplayNotificationManager(final com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, final android.content.Context context, final com.android.server.display.ExternalDisplayStatsService externalDisplayStatsService) {
        this(displayManagerFlags, context, new com.android.server.display.notifications.DisplayNotificationManager.Injector() { // from class: com.android.server.display.notifications.DisplayNotificationManager.1
            @Override // com.android.server.display.notifications.DisplayNotificationManager.Injector
            @android.annotation.Nullable
            public android.app.NotificationManager getNotificationManager() {
                return (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
            }

            @Override // com.android.server.display.notifications.DisplayNotificationManager.Injector
            @android.annotation.Nullable
            public com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector getUsbErrorsDetector() {
                return new com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector(displayManagerFlags, context);
            }

            @Override // com.android.server.display.notifications.DisplayNotificationManager.Injector
            @android.annotation.Nullable
            public com.android.server.display.ExternalDisplayStatsService getExternalDisplayStatsService() {
                return externalDisplayStatsService;
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    DisplayNotificationManager(com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, android.content.Context context, com.android.server.display.notifications.DisplayNotificationManager.Injector injector) {
        this.mConnectedDisplayErrorHandlingEnabled = displayManagerFlags.isConnectedDisplayErrorHandlingEnabled();
        this.mContext = context;
        this.mInjector = injector;
        this.mExternalDisplayStatsService = injector.getExternalDisplayStatsService();
    }

    public void onBootCompleted() {
        this.mNotificationManager = this.mInjector.getNotificationManager();
        if (this.mNotificationManager == null) {
            android.util.Slog.e("DisplayNotificationManager", "onBootCompleted: NotificationManager is null");
            return;
        }
        this.mConnectedDisplayUsbErrorsDetector = this.mInjector.getUsbErrorsDetector();
        if (this.mConnectedDisplayUsbErrorsDetector != null) {
            this.mConnectedDisplayUsbErrorsDetector.registerListener(this);
        }
    }

    @Override // com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Listener
    public void onDisplayPortLinkTrainingFailure() {
        if (!this.mConnectedDisplayErrorHandlingEnabled) {
            android.util.Slog.d("DisplayNotificationManager", "onDisplayPortLinkTrainingFailure: mConnectedDisplayErrorHandlingEnabled is false");
        } else {
            this.mExternalDisplayStatsService.onDisplayPortLinkTrainingFailure();
            sendErrorNotification(createErrorNotification(android.R.string.config_wimaxStateTrackerClassname, android.R.string.config_wimaxServiceJarLocation, android.R.drawable.unlock_wave));
        }
    }

    @Override // com.android.server.display.notifications.ConnectedDisplayUsbErrorsDetector.Listener
    public void onCableNotCapableDisplayPort() {
        if (!this.mConnectedDisplayErrorHandlingEnabled) {
            android.util.Slog.d("DisplayNotificationManager", "onCableNotCapableDisplayPort: mConnectedDisplayErrorHandlingEnabled is false");
        } else {
            this.mExternalDisplayStatsService.onCableNotCapableDisplayPort();
            sendErrorNotification(createErrorNotification(android.R.string.config_wimaxStateTrackerClassname, android.R.string.config_wimaxServiceJarLocation, android.R.drawable.unlock_wave));
        }
    }

    public void onHotplugConnectionError() {
        if (!this.mConnectedDisplayErrorHandlingEnabled) {
            android.util.Slog.d("DisplayNotificationManager", "onHotplugConnectionError: mConnectedDisplayErrorHandlingEnabled is false");
        } else {
            this.mExternalDisplayStatsService.onHotplugConnectionError();
            sendErrorNotification(createErrorNotification(android.R.string.config_wimaxStateTrackerClassname, android.R.string.config_wimaxServiceJarLocation, android.R.drawable.unlock_wave));
        }
    }

    public void onHighTemperatureExternalDisplayNotAllowed() {
        if (!this.mConnectedDisplayErrorHandlingEnabled) {
            android.util.Slog.d("DisplayNotificationManager", "onHighTemperatureExternalDisplayNotAllowed: mConnectedDisplayErrorHandlingEnabled is false");
        } else {
            sendErrorNotification(createErrorNotification(android.R.string.config_wimaxStateTrackerClassname, android.R.string.config_wimaxServiceClassname, android.R.drawable.ic_text_dot));
        }
    }

    public void cancelNotifications() {
        if (this.mNotificationManager == null) {
            android.util.Slog.e("DisplayNotificationManager", "Can't cancelNotifications: NotificationManager is null");
        } else {
            this.mNotificationManager.cancel("DisplayNotificationManager", 1);
        }
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    private void sendErrorNotification(android.app.Notification notification) {
        if (this.mNotificationManager == null) {
            android.util.Slog.e("DisplayNotificationManager", "Can't sendErrorNotification: NotificationManager is null");
        } else {
            this.mNotificationManager.notify("DisplayNotificationManager", 1, notification);
        }
    }

    private android.app.Notification createErrorNotification(int i, int i2, int i3) {
        int i4;
        android.content.res.Resources resources = this.mContext.getResources();
        java.lang.CharSequence text = resources.getText(i);
        java.lang.CharSequence text2 = resources.getText(i2);
        try {
            android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{android.R.attr.colorError});
            try {
                i4 = obtainStyledAttributes.getColor(0, 0);
                try {
                    obtainStyledAttributes.close();
                } catch (android.content.res.Resources.NotFoundException e) {
                    e = e;
                    android.util.Slog.e("DisplayNotificationManager", "colorError attribute is not found: " + e.getMessage());
                    return new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.ALERTS).setGroup("DisplayNotificationManager").setSmallIcon(i3).setWhen(0L).setTimeoutAfter(30000L).setOngoing(false).setTicker(text).setColor(i4).setContentTitle(text).setContentText(text2).setVisibility(1).setCategory("err").build();
                }
            } finally {
            }
        } catch (android.content.res.Resources.NotFoundException e2) {
            e = e2;
            i4 = 0;
            android.util.Slog.e("DisplayNotificationManager", "colorError attribute is not found: " + e.getMessage());
            return new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.ALERTS).setGroup("DisplayNotificationManager").setSmallIcon(i3).setWhen(0L).setTimeoutAfter(30000L).setOngoing(false).setTicker(text).setColor(i4).setContentTitle(text).setContentText(text2).setVisibility(1).setCategory("err").build();
        }
        return new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.ALERTS).setGroup("DisplayNotificationManager").setSmallIcon(i3).setWhen(0L).setTimeoutAfter(30000L).setOngoing(false).setTicker(text).setColor(i4).setContentTitle(text).setContentText(text2).setVisibility(1).setCategory("err").build();
    }
}
