package com.android.server.notification;

/* loaded from: classes2.dex */
public interface FeatureFlags {
    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean autogroupSummaryIconUpdate();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean crossAppPoliteNotifications();

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    boolean expireBitmaps();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean notificationCustomViewUriRestriction();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean notificationHideUnusedChannels();

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    boolean notificationReduceMessagequeueUsage();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean notificationTest();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean politeNotifications();

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    boolean refactorAttentionHelper();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean screenshareNotificationHiding();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean vibrateWhileUnlocked();
}
