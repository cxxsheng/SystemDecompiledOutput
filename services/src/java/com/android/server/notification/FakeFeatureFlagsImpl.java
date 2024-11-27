package com.android.server.notification;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements com.android.server.notification.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.server.notification.Flags.FLAG_AUTOGROUP_SUMMARY_ICON_UPDATE, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_CROSS_APP_POLITE_NOTIFICATIONS, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_EXPIRE_BITMAPS, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_NOTIFICATION_CUSTOM_VIEW_URI_RESTRICTION, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_NOTIFICATION_HIDE_UNUSED_CHANNELS, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_NOTIFICATION_REDUCE_MESSAGEQUEUE_USAGE, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_NOTIFICATION_TEST, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_POLITE_NOTIFICATIONS, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_REFACTOR_ATTENTION_HELPER, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_SCREENSHARE_NOTIFICATION_HIDING, false), java.util.Map.entry(com.android.server.notification.Flags.FLAG_VIBRATE_WHILE_UNLOCKED, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.server.notification.Flags.FLAG_AUTOGROUP_SUMMARY_ICON_UPDATE, com.android.server.notification.Flags.FLAG_CROSS_APP_POLITE_NOTIFICATIONS, com.android.server.notification.Flags.FLAG_EXPIRE_BITMAPS, com.android.server.notification.Flags.FLAG_NOTIFICATION_CUSTOM_VIEW_URI_RESTRICTION, com.android.server.notification.Flags.FLAG_NOTIFICATION_HIDE_UNUSED_CHANNELS, com.android.server.notification.Flags.FLAG_NOTIFICATION_REDUCE_MESSAGEQUEUE_USAGE, com.android.server.notification.Flags.FLAG_NOTIFICATION_TEST, com.android.server.notification.Flags.FLAG_POLITE_NOTIFICATIONS, com.android.server.notification.Flags.FLAG_REFACTOR_ATTENTION_HELPER, com.android.server.notification.Flags.FLAG_SCREENSHARE_NOTIFICATION_HIDING, com.android.server.notification.Flags.FLAG_VIBRATE_WHILE_UNLOCKED, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean autogroupSummaryIconUpdate() {
        return getValue(com.android.server.notification.Flags.FLAG_AUTOGROUP_SUMMARY_ICON_UPDATE);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean crossAppPoliteNotifications() {
        return getValue(com.android.server.notification.Flags.FLAG_CROSS_APP_POLITE_NOTIFICATIONS);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean expireBitmaps() {
        return getValue(com.android.server.notification.Flags.FLAG_EXPIRE_BITMAPS);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean notificationCustomViewUriRestriction() {
        return getValue(com.android.server.notification.Flags.FLAG_NOTIFICATION_CUSTOM_VIEW_URI_RESTRICTION);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean notificationHideUnusedChannels() {
        return getValue(com.android.server.notification.Flags.FLAG_NOTIFICATION_HIDE_UNUSED_CHANNELS);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean notificationReduceMessagequeueUsage() {
        return getValue(com.android.server.notification.Flags.FLAG_NOTIFICATION_REDUCE_MESSAGEQUEUE_USAGE);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean notificationTest() {
        return getValue(com.android.server.notification.Flags.FLAG_NOTIFICATION_TEST);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean politeNotifications() {
        return getValue(com.android.server.notification.Flags.FLAG_POLITE_NOTIFICATIONS);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean refactorAttentionHelper() {
        return getValue(com.android.server.notification.Flags.FLAG_REFACTOR_ATTENTION_HELPER);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean screenshareNotificationHiding() {
        return getValue(com.android.server.notification.Flags.FLAG_SCREENSHARE_NOTIFICATION_HIDING);
    }

    @Override // com.android.server.notification.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean vibrateWhileUnlocked() {
        return getValue(com.android.server.notification.Flags.FLAG_VIBRATE_WHILE_UNLOCKED);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str)) {
            isOptimizationEnabled();
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    private boolean isOptimizationEnabled() {
        return false;
    }
}
