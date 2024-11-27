package com.android.server.notification;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.notification.FeatureFlags FEATURE_FLAGS = new com.android.server.notification.FeatureFlagsImpl();
    public static final java.lang.String FLAG_AUTOGROUP_SUMMARY_ICON_UPDATE = "com.android.server.notification.autogroup_summary_icon_update";
    public static final java.lang.String FLAG_CROSS_APP_POLITE_NOTIFICATIONS = "com.android.server.notification.cross_app_polite_notifications";
    public static final java.lang.String FLAG_EXPIRE_BITMAPS = "com.android.server.notification.expire_bitmaps";
    public static final java.lang.String FLAG_NOTIFICATION_CUSTOM_VIEW_URI_RESTRICTION = "com.android.server.notification.notification_custom_view_uri_restriction";
    public static final java.lang.String FLAG_NOTIFICATION_HIDE_UNUSED_CHANNELS = "com.android.server.notification.notification_hide_unused_channels";
    public static final java.lang.String FLAG_NOTIFICATION_REDUCE_MESSAGEQUEUE_USAGE = "com.android.server.notification.notification_reduce_messagequeue_usage";
    public static final java.lang.String FLAG_NOTIFICATION_TEST = "com.android.server.notification.notification_test";
    public static final java.lang.String FLAG_POLITE_NOTIFICATIONS = "com.android.server.notification.polite_notifications";
    public static final java.lang.String FLAG_REFACTOR_ATTENTION_HELPER = "com.android.server.notification.refactor_attention_helper";
    public static final java.lang.String FLAG_SCREENSHARE_NOTIFICATION_HIDING = "com.android.server.notification.screenshare_notification_hiding";
    public static final java.lang.String FLAG_VIBRATE_WHILE_UNLOCKED = "com.android.server.notification.vibrate_while_unlocked";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean autogroupSummaryIconUpdate() {
        FEATURE_FLAGS.autogroupSummaryIconUpdate();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean crossAppPoliteNotifications() {
        FEATURE_FLAGS.crossAppPoliteNotifications();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean expireBitmaps() {
        FEATURE_FLAGS.expireBitmaps();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean notificationCustomViewUriRestriction() {
        FEATURE_FLAGS.notificationCustomViewUriRestriction();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean notificationHideUnusedChannels() {
        FEATURE_FLAGS.notificationHideUnusedChannels();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean notificationReduceMessagequeueUsage() {
        FEATURE_FLAGS.notificationReduceMessagequeueUsage();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean notificationTest() {
        FEATURE_FLAGS.notificationTest();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean politeNotifications() {
        FEATURE_FLAGS.politeNotifications();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean refactorAttentionHelper() {
        FEATURE_FLAGS.refactorAttentionHelper();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean screenshareNotificationHiding() {
        FEATURE_FLAGS.screenshareNotificationHiding();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean vibrateWhileUnlocked() {
        FEATURE_FLAGS.vibrateWhileUnlocked();
        return false;
    }
}
