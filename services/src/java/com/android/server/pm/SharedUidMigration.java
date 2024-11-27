package com.android.server.pm;

/* loaded from: classes2.dex */
public final class SharedUidMigration {
    public static final int BEST_EFFORT = 2;
    private static final int DEFAULT = 1;
    public static final int LIVE_TRANSITION = 4;
    public static final int NEW_INSTALL_ONLY = 1;
    public static final java.lang.String PROPERTY_KEY = "persist.debug.pm.shared_uid_migration_strategy";
    public static final int TRANSITION_AT_BOOT = 3;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Strategy {
    }

    private SharedUidMigration() {
    }

    public static boolean isDisabled() {
        return false;
    }

    public static int getCurrentStrategy() {
        int i;
        if (android.os.Build.IS_USERDEBUG && (i = android.os.SystemProperties.getInt(PROPERTY_KEY, 1)) <= 2 && i >= 1) {
            return i;
        }
        return 1;
    }

    public static boolean applyStrategy(int i) {
        return !isDisabled() && getCurrentStrategy() >= i;
    }
}
