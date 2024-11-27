package com.android.server.notification;

/* loaded from: classes2.dex */
public class VisibilityExtractor implements com.android.server.notification.NotificationSignalExtractor {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = "VisibilityExtractor";
    private com.android.server.notification.RankingConfig mConfig;
    private android.app.admin.DevicePolicyManager mDpm;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(android.content.Context context, com.android.server.notification.NotificationUsageStats notificationUsageStats) {
        this.mDpm = (android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public com.android.server.notification.RankingReconsideration process(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord == null || notificationRecord.getNotification() == null || this.mConfig == null) {
            return null;
        }
        int userId = notificationRecord.getUserId();
        if (userId == -1) {
            notificationRecord.setPackageVisibilityOverride(notificationRecord.getChannel().getLockscreenVisibility());
        } else {
            boolean canShowNotificationsOnLockscreen = this.mConfig.canShowNotificationsOnLockscreen(userId);
            boolean adminAllowsKeyguardFeature = adminAllowsKeyguardFeature(userId, 4);
            boolean z = notificationRecord.getChannel().getLockscreenVisibility() != -1;
            if (!canShowNotificationsOnLockscreen || !adminAllowsKeyguardFeature || !z) {
                notificationRecord.setPackageVisibilityOverride(-1);
            } else {
                boolean canShowPrivateNotificationsOnLockScreen = this.mConfig.canShowPrivateNotificationsOnLockScreen(userId);
                boolean adminAllowsKeyguardFeature2 = adminAllowsKeyguardFeature(userId, 8);
                boolean z2 = notificationRecord.getChannel().getLockscreenVisibility() != 0;
                if (!canShowPrivateNotificationsOnLockScreen || !adminAllowsKeyguardFeature2 || !z2) {
                    notificationRecord.setPackageVisibilityOverride(0);
                } else {
                    notificationRecord.setPackageVisibilityOverride(-1000);
                }
            }
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

    private boolean adminAllowsKeyguardFeature(int i, int i2) {
        return i == -1 || (this.mDpm.getKeyguardDisabledFeatures(null, i) & i2) == 0;
    }
}
