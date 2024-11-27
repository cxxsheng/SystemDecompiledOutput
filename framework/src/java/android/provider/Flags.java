package android.provider;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.provider.FeatureFlags FEATURE_FLAGS = new android.provider.FeatureFlagsImpl();
    public static final java.lang.String FLAG_BACKUP_TASKS_SETTINGS_SCREEN = "android.provider.backup_tasks_settings_screen";
    public static final java.lang.String FLAG_SYSTEM_SETTINGS_DEFAULT = "android.provider.system_settings_default";
    public static final java.lang.String FLAG_USER_KEYS = "android.provider.user_keys";

    public static boolean backupTasksSettingsScreen() {
        return FEATURE_FLAGS.backupTasksSettingsScreen();
    }

    public static boolean systemSettingsDefault() {
        return FEATURE_FLAGS.systemSettingsDefault();
    }

    public static boolean userKeys() {
        return FEATURE_FLAGS.userKeys();
    }
}
