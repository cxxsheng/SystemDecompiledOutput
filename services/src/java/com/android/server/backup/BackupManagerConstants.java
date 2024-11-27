package com.android.server.backup;

/* loaded from: classes.dex */
public class BackupManagerConstants extends android.util.KeyValueSettingObserver {

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String BACKUP_FINISHED_NOTIFICATION_RECEIVERS = "backup_finished_notification_receivers";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String DEFAULT_BACKUP_FINISHED_NOTIFICATION_RECEIVERS = "";

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS = 86400000;

    @com.android.internal.annotations.VisibleForTesting
    public static final int DEFAULT_FULL_BACKUP_REQUIRED_NETWORK_TYPE = 2;

    @com.android.internal.annotations.VisibleForTesting
    public static final boolean DEFAULT_FULL_BACKUP_REQUIRE_CHARGING = true;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_KEY_VALUE_BACKUP_FUZZ_MILLISECONDS = 600000;

    @com.android.internal.annotations.VisibleForTesting
    public static final long DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS = 14400000;

    @com.android.internal.annotations.VisibleForTesting
    public static final int DEFAULT_KEY_VALUE_BACKUP_REQUIRED_NETWORK_TYPE = 1;

    @com.android.internal.annotations.VisibleForTesting
    public static final boolean DEFAULT_KEY_VALUE_BACKUP_REQUIRE_CHARGING = true;

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String FULL_BACKUP_INTERVAL_MILLISECONDS = "full_backup_interval_milliseconds";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String FULL_BACKUP_REQUIRED_NETWORK_TYPE = "full_backup_required_network_type";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String FULL_BACKUP_REQUIRE_CHARGING = "full_backup_require_charging";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String KEY_VALUE_BACKUP_FUZZ_MILLISECONDS = "key_value_backup_fuzz_milliseconds";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS = "key_value_backup_interval_milliseconds";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String KEY_VALUE_BACKUP_REQUIRED_NETWORK_TYPE = "key_value_backup_required_network_type";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String KEY_VALUE_BACKUP_REQUIRE_CHARGING = "key_value_backup_require_charging";
    private static final java.lang.String SETTING = "backup_manager_constants";
    private static final java.lang.String TAG = "BackupManagerConstants";
    private java.lang.String[] mBackupFinishedNotificationReceivers;
    private long mFullBackupIntervalMilliseconds;
    private boolean mFullBackupRequireCharging;
    private int mFullBackupRequiredNetworkType;
    private long mKeyValueBackupFuzzMilliseconds;
    private long mKeyValueBackupIntervalMilliseconds;
    private boolean mKeyValueBackupRequireCharging;
    private int mKeyValueBackupRequiredNetworkType;

    public BackupManagerConstants(android.os.Handler handler, android.content.ContentResolver contentResolver) {
        super(handler, contentResolver, android.provider.Settings.Secure.getUriFor(SETTING));
    }

    public java.lang.String getSettingValue(android.content.ContentResolver contentResolver) {
        return android.provider.Settings.Secure.getStringForUser(contentResolver, SETTING, contentResolver.getUserId());
    }

    public synchronized void update(android.util.KeyValueListParser keyValueListParser) {
        try {
            this.mKeyValueBackupIntervalMilliseconds = keyValueListParser.getLong(KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS, 14400000L);
            this.mKeyValueBackupFuzzMilliseconds = keyValueListParser.getLong(KEY_VALUE_BACKUP_FUZZ_MILLISECONDS, 600000L);
            this.mKeyValueBackupRequireCharging = keyValueListParser.getBoolean(KEY_VALUE_BACKUP_REQUIRE_CHARGING, true);
            this.mKeyValueBackupRequiredNetworkType = keyValueListParser.getInt(KEY_VALUE_BACKUP_REQUIRED_NETWORK_TYPE, 1);
            this.mFullBackupIntervalMilliseconds = keyValueListParser.getLong(FULL_BACKUP_INTERVAL_MILLISECONDS, 86400000L);
            this.mFullBackupRequireCharging = keyValueListParser.getBoolean(FULL_BACKUP_REQUIRE_CHARGING, true);
            this.mFullBackupRequiredNetworkType = keyValueListParser.getInt(FULL_BACKUP_REQUIRED_NETWORK_TYPE, 2);
            java.lang.String string = keyValueListParser.getString(BACKUP_FINISHED_NOTIFICATION_RECEIVERS, "");
            if (string.isEmpty()) {
                this.mBackupFinishedNotificationReceivers = new java.lang.String[0];
            } else {
                this.mBackupFinishedNotificationReceivers = string.split(":");
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized long getKeyValueBackupIntervalMilliseconds() {
        android.util.Slog.v(TAG, "getKeyValueBackupIntervalMilliseconds(...) returns " + this.mKeyValueBackupIntervalMilliseconds);
        return this.mKeyValueBackupIntervalMilliseconds;
    }

    public synchronized long getKeyValueBackupFuzzMilliseconds() {
        android.util.Slog.v(TAG, "getKeyValueBackupFuzzMilliseconds(...) returns " + this.mKeyValueBackupFuzzMilliseconds);
        return this.mKeyValueBackupFuzzMilliseconds;
    }

    public synchronized boolean getKeyValueBackupRequireCharging() {
        android.util.Slog.v(TAG, "getKeyValueBackupRequireCharging(...) returns " + this.mKeyValueBackupRequireCharging);
        return this.mKeyValueBackupRequireCharging;
    }

    public synchronized int getKeyValueBackupRequiredNetworkType() {
        android.util.Slog.v(TAG, "getKeyValueBackupRequiredNetworkType(...) returns " + this.mKeyValueBackupRequiredNetworkType);
        return this.mKeyValueBackupRequiredNetworkType;
    }

    public synchronized long getFullBackupIntervalMilliseconds() {
        android.util.Slog.v(TAG, "getFullBackupIntervalMilliseconds(...) returns " + this.mFullBackupIntervalMilliseconds);
        return this.mFullBackupIntervalMilliseconds;
    }

    public synchronized boolean getFullBackupRequireCharging() {
        android.util.Slog.v(TAG, "getFullBackupRequireCharging(...) returns " + this.mFullBackupRequireCharging);
        return this.mFullBackupRequireCharging;
    }

    public synchronized int getFullBackupRequiredNetworkType() {
        android.util.Slog.v(TAG, "getFullBackupRequiredNetworkType(...) returns " + this.mFullBackupRequiredNetworkType);
        return this.mFullBackupRequiredNetworkType;
    }

    public synchronized java.lang.String[] getBackupFinishedNotificationReceivers() {
        android.util.Slog.v(TAG, "getBackupFinishedNotificationReceivers(...) returns " + android.text.TextUtils.join(", ", this.mBackupFinishedNotificationReceivers));
        return this.mBackupFinishedNotificationReceivers;
    }
}
