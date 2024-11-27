package com.android.server.notification;

/* loaded from: classes2.dex */
public final class FeatureFlagsImpl implements com.android.server.notification.FeatureFlags {
    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean autogroupSummaryIconUpdate() {
        return false;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean crossAppPoliteNotifications() {
        return false;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean expireBitmaps() {
        return true;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean notificationCustomViewUriRestriction() {
        return false;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean notificationHideUnusedChannels() {
        return false;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean notificationReduceMessagequeueUsage() {
        return true;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean notificationTest() {
        return false;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean politeNotifications() {
        return false;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean refactorAttentionHelper() {
        return true;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean screenshareNotificationHiding() {
        return false;
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean vibrateWhileUnlocked() {
        return false;
    }
}
