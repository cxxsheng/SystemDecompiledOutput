package com.android.server.notification;

/* loaded from: classes2.dex */
public class CriticalNotificationExtractor implements com.android.server.notification.NotificationSignalExtractor {
    static final int CRITICAL = 0;
    static final int CRITICAL_LOW = 1;
    private static final boolean DBG = false;
    static final int NORMAL = 2;
    private static final java.lang.String TAG = "CriticalNotificationExt";
    private boolean mSupportsCriticalNotifications = false;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(android.content.Context context, com.android.server.notification.NotificationUsageStats notificationUsageStats) {
        this.mSupportsCriticalNotifications = supportsCriticalNotifications(context);
    }

    private boolean supportsCriticalNotifications(android.content.Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.type.automotive", 0);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public com.android.server.notification.RankingReconsideration process(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!this.mSupportsCriticalNotifications || notificationRecord == null || notificationRecord.getNotification() == null) {
            return null;
        }
        if (notificationRecord.isCategory("car_emergency")) {
            notificationRecord.setCriticality(0);
        } else if (notificationRecord.isCategory("car_warning")) {
            notificationRecord.setCriticality(1);
        } else {
            notificationRecord.setCriticality(2);
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setConfig(com.android.server.notification.RankingConfig rankingConfig) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(com.android.server.notification.ZenModeHelper zenModeHelper) {
    }
}
