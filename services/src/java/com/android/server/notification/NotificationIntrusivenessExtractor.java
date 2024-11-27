package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationIntrusivenessExtractor implements com.android.server.notification.NotificationSignalExtractor {

    @com.android.internal.annotations.VisibleForTesting
    static final long HANG_TIME_MS = 10000;
    private static final java.lang.String TAG = "IntrusivenessExtractor";
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
        long j = 10000;
        if (notificationRecord.getFreshnessMs(java.lang.System.currentTimeMillis()) < 10000 && notificationRecord.getImportance() >= 3) {
            if (notificationRecord.getSound() != null && notificationRecord.getSound() != android.net.Uri.EMPTY) {
                notificationRecord.setRecentlyIntrusive(true);
            }
            if (notificationRecord.getVibration() != null) {
                notificationRecord.setRecentlyIntrusive(true);
            }
            if (notificationRecord.getNotification().fullScreenIntent != null) {
                notificationRecord.setRecentlyIntrusive(true);
            }
        }
        if (!notificationRecord.isRecentlyIntrusive()) {
            return null;
        }
        return new com.android.server.notification.RankingReconsideration(notificationRecord.getKey(), j) { // from class: com.android.server.notification.NotificationIntrusivenessExtractor.1
            @Override // com.android.server.notification.RankingReconsideration
            public void work() {
            }

            @Override // com.android.server.notification.RankingReconsideration
            public void applyChangesLocked(com.android.server.notification.NotificationRecord notificationRecord2) {
                if (java.lang.System.currentTimeMillis() - notificationRecord2.getLastIntrusive() >= 10000) {
                    notificationRecord2.setRecentlyIntrusive(false);
                }
            }
        };
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setConfig(com.android.server.notification.RankingConfig rankingConfig) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(com.android.server.notification.ZenModeHelper zenModeHelper) {
    }
}
