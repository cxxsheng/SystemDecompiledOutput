package com.android.server.notification;

/* loaded from: classes2.dex */
public class ZenModeExtractor implements com.android.server.notification.NotificationSignalExtractor {
    private com.android.server.notification.ZenModeHelper mZenModeHelper;
    private static final java.lang.String TAG = "ZenModeExtractor";
    private static final boolean DBG = android.util.Log.isLoggable(TAG, 3);

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(android.content.Context context, com.android.server.notification.NotificationUsageStats notificationUsageStats) {
        if (DBG) {
            android.util.Slog.d(TAG, "Initializing  " + getClass().getSimpleName() + ".");
        }
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public com.android.server.notification.RankingReconsideration process(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord == null || notificationRecord.getNotification() == null) {
            if (DBG) {
                android.util.Slog.d(TAG, "skipping empty notification");
            }
            return null;
        }
        if (this.mZenModeHelper == null) {
            if (DBG) {
                android.util.Slog.d(TAG, "skipping - no zen info available");
            }
            return null;
        }
        notificationRecord.setIntercepted(this.mZenModeHelper.shouldIntercept(notificationRecord));
        if (notificationRecord.isIntercepted()) {
            notificationRecord.setSuppressedVisualEffects(this.mZenModeHelper.getConsolidatedNotificationPolicy().suppressedVisualEffects);
        } else {
            notificationRecord.setSuppressedVisualEffects(0);
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setConfig(com.android.server.notification.RankingConfig rankingConfig) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(com.android.server.notification.ZenModeHelper zenModeHelper) {
        this.mZenModeHelper = zenModeHelper;
    }
}
