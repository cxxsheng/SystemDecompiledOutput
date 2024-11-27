package com.android.server.notification;

/* loaded from: classes2.dex */
public class BadgeExtractor implements com.android.server.notification.NotificationSignalExtractor {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = "BadgeExtractor";
    private com.android.server.notification.RankingConfig mConfig;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(android.content.Context context, com.android.server.notification.NotificationUsageStats notificationUsageStats) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public com.android.server.notification.RankingReconsideration process(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord == null || notificationRecord.getNotification() == null || this.mConfig == null) {
            return null;
        }
        boolean badgingEnabled = this.mConfig.badgingEnabled(notificationRecord.getSbn().getUser());
        boolean canShowBadge = this.mConfig.canShowBadge(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getUid());
        if (!badgingEnabled || !canShowBadge) {
            notificationRecord.setShowBadge(false);
        } else if (notificationRecord.getChannel() != null) {
            notificationRecord.setShowBadge(notificationRecord.getChannel().canShowBadge() && canShowBadge);
        } else {
            notificationRecord.setShowBadge(canShowBadge);
        }
        if (notificationRecord.isIntercepted() && (notificationRecord.getSuppressedVisualEffects() & 64) != 0) {
            notificationRecord.setShowBadge(false);
        }
        android.app.Notification.BubbleMetadata bubbleMetadata = notificationRecord.getNotification().getBubbleMetadata();
        if (bubbleMetadata != null && bubbleMetadata.isNotificationSuppressed()) {
            notificationRecord.setShowBadge(false);
        }
        if (this.mConfig.isMediaNotificationFilteringEnabled() && notificationRecord.getNotification().isMediaNotification()) {
            notificationRecord.setShowBadge(false);
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setConfig(com.android.server.notification.RankingConfig rankingConfig) {
        this.mConfig = rankingConfig;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(com.android.server.notification.ZenModeHelper zenModeHelper) {
    }
}
