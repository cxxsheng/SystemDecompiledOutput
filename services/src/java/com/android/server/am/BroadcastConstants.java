package com.android.server.am;

/* loaded from: classes.dex */
public class BroadcastConstants {
    private static final boolean DEFAULT_CORE_DEFER_UNTIL_ACTIVE = true;
    private static final float DEFAULT_DEFERRAL_DECAY_FACTOR = 0.75f;
    private static final long DEFAULT_DEFERRAL_FLOOR = 0;
    private static final long DEFAULT_DELAY_CACHED_MILLIS = 120000;
    private static final long DEFAULT_DELAY_FOREGROUND_PROC_MILLIS = -120000;
    private static final long DEFAULT_DELAY_NORMAL_MILLIS = 500;
    private static final long DEFAULT_DELAY_PERSISTENT_PROC_MILLIS = -120000;
    private static final long DEFAULT_DELAY_URGENT_MILLIS = -120000;
    private static final int DEFAULT_EXTRA_RUNNING_URGENT_PROCESS_QUEUES = 1;
    private static final int DEFAULT_MAX_CONSECUTIVE_NORMAL_DISPATCHES = 10;
    private static final int DEFAULT_MAX_CONSECUTIVE_URGENT_DISPATCHES = 3;
    private static final int DEFAULT_MAX_CORE_RUNNING_BLOCKING_BROADCASTS;
    private static final int DEFAULT_MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS;
    private static final int DEFAULT_MAX_HISTORY_COMPLETE_SIZE;
    private static final int DEFAULT_MAX_HISTORY_SUMMARY_SIZE;
    private static final int DEFAULT_MAX_PENDING_BROADCASTS;
    private static final int DEFAULT_MAX_RUNNING_ACTIVE_BROADCASTS;
    private static final int DEFAULT_MAX_RUNNING_PROCESS_QUEUES;
    private static final boolean DEFAULT_MODERN_QUEUE_ENABLED = true;
    private static final long DEFAULT_PENDING_COLD_START_CHECK_INTERVAL_MILLIS = 30000;
    public static final int DEFER_BOOT_COMPLETED_BROADCAST_ALL = 1;
    public static final int DEFER_BOOT_COMPLETED_BROADCAST_BACKGROUND_RESTRICTED_ONLY = 2;
    static final long DEFER_BOOT_COMPLETED_BROADCAST_CHANGE_ID = 203704822;
    public static final int DEFER_BOOT_COMPLETED_BROADCAST_NONE = 0;
    public static final int DEFER_BOOT_COMPLETED_BROADCAST_TARGET_T_ONLY = 4;
    static final java.lang.String KEY_ALLOW_BG_ACTIVITY_START_TIMEOUT = "bcast_allow_bg_activity_start_timeout";
    private static final java.lang.String KEY_CORE_DEFER_UNTIL_ACTIVE = "bcast_core_defer_until_active";
    private static final java.lang.String KEY_CORE_MAX_RUNNING_BLOCKING_BROADCASTS = "bcast_max_core_running_blocking_broadcasts";
    private static final java.lang.String KEY_CORE_MAX_RUNNING_NON_BLOCKING_BROADCASTS = "bcast_max_core_running_non_blocking_broadcasts";
    static final java.lang.String KEY_DEFERRAL = "bcast_deferral";
    static final java.lang.String KEY_DEFERRAL_DECAY_FACTOR = "bcast_deferral_decay_factor";
    static final java.lang.String KEY_DEFERRAL_FLOOR = "bcast_deferral_floor";
    private static final java.lang.String KEY_DELAY_CACHED_MILLIS = "bcast_delay_cached_millis";
    private static final java.lang.String KEY_DELAY_FOREGROUND_PROC_MILLIS = "bcast_delay_foreground_proc_millis";
    private static final java.lang.String KEY_DELAY_NORMAL_MILLIS = "bcast_delay_normal_millis";
    private static final java.lang.String KEY_DELAY_PERSISTENT_PROC_MILLIS = "bcast_delay_persistent_proc_millis";
    private static final java.lang.String KEY_DELAY_URGENT_MILLIS = "bcast_delay_urgent_millis";
    private static final java.lang.String KEY_EXTRA_RUNNING_URGENT_PROCESS_QUEUES = "bcast_extra_running_urgent_process_queues";
    private static final java.lang.String KEY_MAX_CONSECUTIVE_NORMAL_DISPATCHES = "bcast_max_consecutive_normal_dispatches";
    private static final java.lang.String KEY_MAX_CONSECUTIVE_URGENT_DISPATCHES = "bcast_max_consecutive_urgent_dispatches";
    private static final java.lang.String KEY_MAX_HISTORY_COMPLETE_SIZE = "bcast_max_history_complete_size";
    private static final java.lang.String KEY_MAX_HISTORY_SUMMARY_SIZE = "bcast_max_history_summary_size";
    private static final java.lang.String KEY_MAX_PENDING_BROADCASTS = "bcast_max_pending_broadcasts";
    private static final java.lang.String KEY_MAX_RUNNING_ACTIVE_BROADCASTS = "bcast_max_running_active_broadcasts";
    private static final java.lang.String KEY_MAX_RUNNING_PROCESS_QUEUES = "bcast_max_running_process_queues";
    private static final java.lang.String KEY_MODERN_QUEUE_ENABLED = "modern_queue_enabled";
    private static final java.lang.String KEY_PENDING_COLD_START_CHECK_INTERVAL_MILLIS = "pending_cold_start_check_interval_millis";
    static final java.lang.String KEY_SLOW_TIME = "bcast_slow_time";
    static final java.lang.String KEY_TIMEOUT = "bcast_timeout";
    private static final java.lang.String TAG = "BroadcastConstants";
    private android.content.ContentResolver mResolver;
    private java.lang.String mSettingsKey;
    private com.android.server.am.BroadcastConstants.SettingsObserver mSettingsObserver;
    private static final long DEFAULT_TIMEOUT = android.os.Build.HW_TIMEOUT_MULTIPLIER * 10000;
    private static final long DEFAULT_SLOW_TIME = android.os.Build.HW_TIMEOUT_MULTIPLIER * 5000;
    private static final long DEFAULT_DEFERRAL = android.os.Build.HW_TIMEOUT_MULTIPLIER * 5000;
    private static final long DEFAULT_ALLOW_BG_ACTIVITY_START_TIMEOUT = android.os.Build.HW_TIMEOUT_MULTIPLIER * 10000;
    public long TIMEOUT = DEFAULT_TIMEOUT;
    public long SLOW_TIME = DEFAULT_SLOW_TIME;
    public long DEFERRAL = DEFAULT_DEFERRAL;
    public float DEFERRAL_DECAY_FACTOR = DEFAULT_DEFERRAL_DECAY_FACTOR;
    public long DEFERRAL_FLOOR = 0;
    public long ALLOW_BG_ACTIVITY_START_TIMEOUT = DEFAULT_ALLOW_BG_ACTIVITY_START_TIMEOUT;
    public boolean MODERN_QUEUE_ENABLED = true;
    public int MAX_RUNNING_PROCESS_QUEUES = DEFAULT_MAX_RUNNING_PROCESS_QUEUES;
    public int EXTRA_RUNNING_URGENT_PROCESS_QUEUES = 1;
    public int MAX_CONSECUTIVE_URGENT_DISPATCHES = 3;
    public int MAX_CONSECUTIVE_NORMAL_DISPATCHES = 10;
    public int MAX_RUNNING_ACTIVE_BROADCASTS = DEFAULT_MAX_RUNNING_ACTIVE_BROADCASTS;
    public int MAX_CORE_RUNNING_BLOCKING_BROADCASTS = DEFAULT_MAX_CORE_RUNNING_BLOCKING_BROADCASTS;
    public int MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS = DEFAULT_MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS;
    public int MAX_PENDING_BROADCASTS = DEFAULT_MAX_PENDING_BROADCASTS;
    public long DELAY_NORMAL_MILLIS = 500;
    public long DELAY_CACHED_MILLIS = 120000;
    public long DELAY_URGENT_MILLIS = -120000;
    public long DELAY_FOREGROUND_PROC_MILLIS = -120000;
    public long DELAY_PERSISTENT_PROC_MILLIS = -120000;
    public int MAX_HISTORY_COMPLETE_SIZE = DEFAULT_MAX_HISTORY_COMPLETE_SIZE;
    public int MAX_HISTORY_SUMMARY_SIZE = DEFAULT_MAX_HISTORY_SUMMARY_SIZE;
    public boolean CORE_DEFER_UNTIL_ACTIVE = true;
    public long PENDING_COLD_START_CHECK_INTERVAL_MILLIS = 30000;
    private final android.util.KeyValueListParser mParser = new android.util.KeyValueListParser(',');

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeferBootCompletedBroadcastType {
    }

    static {
        DEFAULT_MAX_RUNNING_PROCESS_QUEUES = android.app.ActivityManager.isLowRamDeviceStatic() ? 2 : 4;
        DEFAULT_MAX_RUNNING_ACTIVE_BROADCASTS = android.app.ActivityManager.isLowRamDeviceStatic() ? 8 : 16;
        DEFAULT_MAX_CORE_RUNNING_BLOCKING_BROADCASTS = android.app.ActivityManager.isLowRamDeviceStatic() ? 8 : 16;
        DEFAULT_MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS = android.app.ActivityManager.isLowRamDeviceStatic() ? 32 : 64;
        DEFAULT_MAX_PENDING_BROADCASTS = android.app.ActivityManager.isLowRamDeviceStatic() ? 128 : 256;
        DEFAULT_MAX_HISTORY_COMPLETE_SIZE = android.app.ActivityManager.isLowRamDeviceStatic() ? 64 : 256;
        DEFAULT_MAX_HISTORY_SUMMARY_SIZE = android.app.ActivityManager.isLowRamDeviceStatic() ? 256 : 1024;
    }

    class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            com.android.server.am.BroadcastConstants.this.updateSettingsConstants();
        }
    }

    public BroadcastConstants(java.lang.String str) {
        this.mSettingsKey = str;
        updateDeviceConfigConstants();
    }

    public void startObserving(android.os.Handler handler, android.content.ContentResolver contentResolver) {
        this.mResolver = contentResolver;
        this.mSettingsObserver = new com.android.server.am.BroadcastConstants.SettingsObserver(handler);
        this.mResolver.registerContentObserver(android.provider.Settings.Global.getUriFor(this.mSettingsKey), false, this.mSettingsObserver);
        updateSettingsConstants();
        android.provider.DeviceConfig.addOnPropertiesChangedListener("activity_manager_native_boot", new android.os.HandlerExecutor(handler), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.BroadcastConstants$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.am.BroadcastConstants.this.updateDeviceConfigConstants(properties);
            }
        });
        updateDeviceConfigConstants();
    }

    public int getMaxRunningQueues() {
        return this.MAX_RUNNING_PROCESS_QUEUES + this.EXTRA_RUNNING_URGENT_PROCESS_QUEUES;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSettingsConstants() {
        synchronized (this) {
            try {
                try {
                    this.mParser.setString(android.provider.Settings.Global.getString(this.mResolver, this.mSettingsKey));
                    this.TIMEOUT = this.mParser.getLong(KEY_TIMEOUT, this.TIMEOUT);
                    this.SLOW_TIME = this.mParser.getLong(KEY_SLOW_TIME, this.SLOW_TIME);
                    this.DEFERRAL = this.mParser.getLong(KEY_DEFERRAL, this.DEFERRAL);
                    this.DEFERRAL_DECAY_FACTOR = this.mParser.getFloat(KEY_DEFERRAL_DECAY_FACTOR, this.DEFERRAL_DECAY_FACTOR);
                    this.DEFERRAL_FLOOR = this.mParser.getLong(KEY_DEFERRAL_FLOOR, this.DEFERRAL_FLOOR);
                    this.ALLOW_BG_ACTIVITY_START_TIMEOUT = this.mParser.getLong(KEY_ALLOW_BG_ACTIVITY_START_TIMEOUT, this.ALLOW_BG_ACTIVITY_START_TIMEOUT);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.e(TAG, "Bad broadcast settings in key '" + this.mSettingsKey + "'", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    private static java.lang.String propertyFor(@android.annotation.NonNull java.lang.String str) {
        return "persist.device_config.activity_manager_native_boot." + str;
    }

    @android.annotation.NonNull
    private static java.lang.String propertyOverrideFor(@android.annotation.NonNull java.lang.String str) {
        return "persist.sys.activity_manager_native_boot." + str;
    }

    static boolean getDeviceConfigBoolean(@android.annotation.NonNull java.lang.String str, boolean z) {
        return android.os.SystemProperties.getBoolean(propertyOverrideFor(str), android.os.SystemProperties.getBoolean(propertyFor(str), z));
    }

    private int getDeviceConfigInt(@android.annotation.NonNull java.lang.String str, int i) {
        return android.os.SystemProperties.getInt(propertyOverrideFor(str), android.os.SystemProperties.getInt(propertyFor(str), i));
    }

    private long getDeviceConfigLong(@android.annotation.NonNull java.lang.String str, long j) {
        return android.os.SystemProperties.getLong(propertyOverrideFor(str), android.os.SystemProperties.getLong(propertyFor(str), j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceConfigConstants(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
        updateDeviceConfigConstants();
    }

    private void updateDeviceConfigConstants() {
        synchronized (this) {
            this.MODERN_QUEUE_ENABLED = getDeviceConfigBoolean(KEY_MODERN_QUEUE_ENABLED, true);
            this.MAX_RUNNING_PROCESS_QUEUES = getDeviceConfigInt(KEY_MAX_RUNNING_PROCESS_QUEUES, DEFAULT_MAX_RUNNING_PROCESS_QUEUES);
            this.EXTRA_RUNNING_URGENT_PROCESS_QUEUES = getDeviceConfigInt(KEY_EXTRA_RUNNING_URGENT_PROCESS_QUEUES, 1);
            this.MAX_CONSECUTIVE_URGENT_DISPATCHES = getDeviceConfigInt(KEY_MAX_CONSECUTIVE_URGENT_DISPATCHES, 3);
            this.MAX_CONSECUTIVE_NORMAL_DISPATCHES = getDeviceConfigInt(KEY_MAX_CONSECUTIVE_NORMAL_DISPATCHES, 10);
            this.MAX_RUNNING_ACTIVE_BROADCASTS = getDeviceConfigInt(KEY_MAX_RUNNING_ACTIVE_BROADCASTS, DEFAULT_MAX_RUNNING_ACTIVE_BROADCASTS);
            this.MAX_CORE_RUNNING_BLOCKING_BROADCASTS = getDeviceConfigInt(KEY_CORE_MAX_RUNNING_BLOCKING_BROADCASTS, DEFAULT_MAX_CORE_RUNNING_BLOCKING_BROADCASTS);
            this.MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS = getDeviceConfigInt(KEY_CORE_MAX_RUNNING_NON_BLOCKING_BROADCASTS, DEFAULT_MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS);
            this.MAX_PENDING_BROADCASTS = getDeviceConfigInt(KEY_MAX_PENDING_BROADCASTS, DEFAULT_MAX_PENDING_BROADCASTS);
            this.DELAY_NORMAL_MILLIS = getDeviceConfigLong(KEY_DELAY_NORMAL_MILLIS, 500L);
            this.DELAY_CACHED_MILLIS = getDeviceConfigLong(KEY_DELAY_CACHED_MILLIS, 120000L);
            this.DELAY_URGENT_MILLIS = getDeviceConfigLong(KEY_DELAY_URGENT_MILLIS, -120000L);
            this.DELAY_FOREGROUND_PROC_MILLIS = getDeviceConfigLong(KEY_DELAY_FOREGROUND_PROC_MILLIS, -120000L);
            this.DELAY_PERSISTENT_PROC_MILLIS = getDeviceConfigLong(KEY_DELAY_PERSISTENT_PROC_MILLIS, -120000L);
            this.MAX_HISTORY_COMPLETE_SIZE = getDeviceConfigInt(KEY_MAX_HISTORY_COMPLETE_SIZE, DEFAULT_MAX_HISTORY_COMPLETE_SIZE);
            this.MAX_HISTORY_SUMMARY_SIZE = getDeviceConfigInt(KEY_MAX_HISTORY_SUMMARY_SIZE, DEFAULT_MAX_HISTORY_SUMMARY_SIZE);
            this.CORE_DEFER_UNTIL_ACTIVE = getDeviceConfigBoolean(KEY_CORE_DEFER_UNTIL_ACTIVE, true);
            this.PENDING_COLD_START_CHECK_INTERVAL_MILLIS = getDeviceConfigLong(KEY_PENDING_COLD_START_CHECK_INTERVAL_MILLIS, 30000L);
        }
        com.android.server.am.BroadcastRecord.CORE_DEFER_UNTIL_ACTIVE = this.CORE_DEFER_UNTIL_ACTIVE;
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this) {
            indentingPrintWriter.print("Broadcast parameters (key=");
            indentingPrintWriter.print(this.mSettingsKey);
            indentingPrintWriter.print(", observing=");
            indentingPrintWriter.print(this.mSettingsObserver != null);
            indentingPrintWriter.println("):");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_TIMEOUT, android.util.TimeUtils.formatDuration(this.TIMEOUT)).println();
            indentingPrintWriter.print(KEY_SLOW_TIME, android.util.TimeUtils.formatDuration(this.SLOW_TIME)).println();
            indentingPrintWriter.print(KEY_DEFERRAL, android.util.TimeUtils.formatDuration(this.DEFERRAL)).println();
            indentingPrintWriter.print(KEY_DEFERRAL_DECAY_FACTOR, java.lang.Float.valueOf(this.DEFERRAL_DECAY_FACTOR)).println();
            indentingPrintWriter.print(KEY_DEFERRAL_FLOOR, java.lang.Long.valueOf(this.DEFERRAL_FLOOR)).println();
            indentingPrintWriter.print(KEY_ALLOW_BG_ACTIVITY_START_TIMEOUT, android.util.TimeUtils.formatDuration(this.ALLOW_BG_ACTIVITY_START_TIMEOUT)).println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.print("Broadcast parameters (namespace=");
            indentingPrintWriter.print("activity_manager_native_boot");
            indentingPrintWriter.println("):");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_MODERN_QUEUE_ENABLED, java.lang.Boolean.valueOf(this.MODERN_QUEUE_ENABLED)).println();
            indentingPrintWriter.print(KEY_MAX_RUNNING_PROCESS_QUEUES, java.lang.Integer.valueOf(this.MAX_RUNNING_PROCESS_QUEUES)).println();
            indentingPrintWriter.print(KEY_MAX_RUNNING_ACTIVE_BROADCASTS, java.lang.Integer.valueOf(this.MAX_RUNNING_ACTIVE_BROADCASTS)).println();
            indentingPrintWriter.print(KEY_CORE_MAX_RUNNING_BLOCKING_BROADCASTS, java.lang.Integer.valueOf(this.MAX_CORE_RUNNING_BLOCKING_BROADCASTS)).println();
            indentingPrintWriter.print(KEY_CORE_MAX_RUNNING_NON_BLOCKING_BROADCASTS, java.lang.Integer.valueOf(this.MAX_CORE_RUNNING_NON_BLOCKING_BROADCASTS)).println();
            indentingPrintWriter.print(KEY_MAX_PENDING_BROADCASTS, java.lang.Integer.valueOf(this.MAX_PENDING_BROADCASTS)).println();
            indentingPrintWriter.print(KEY_DELAY_NORMAL_MILLIS, android.util.TimeUtils.formatDuration(this.DELAY_NORMAL_MILLIS)).println();
            indentingPrintWriter.print(KEY_DELAY_CACHED_MILLIS, android.util.TimeUtils.formatDuration(this.DELAY_CACHED_MILLIS)).println();
            indentingPrintWriter.print(KEY_DELAY_URGENT_MILLIS, android.util.TimeUtils.formatDuration(this.DELAY_URGENT_MILLIS)).println();
            indentingPrintWriter.print(KEY_DELAY_FOREGROUND_PROC_MILLIS, android.util.TimeUtils.formatDuration(this.DELAY_FOREGROUND_PROC_MILLIS)).println();
            indentingPrintWriter.print(KEY_DELAY_PERSISTENT_PROC_MILLIS, android.util.TimeUtils.formatDuration(this.DELAY_PERSISTENT_PROC_MILLIS)).println();
            indentingPrintWriter.print(KEY_MAX_HISTORY_COMPLETE_SIZE, java.lang.Integer.valueOf(this.MAX_HISTORY_COMPLETE_SIZE)).println();
            indentingPrintWriter.print(KEY_MAX_HISTORY_SUMMARY_SIZE, java.lang.Integer.valueOf(this.MAX_HISTORY_SUMMARY_SIZE)).println();
            indentingPrintWriter.print(KEY_MAX_CONSECUTIVE_URGENT_DISPATCHES, java.lang.Integer.valueOf(this.MAX_CONSECUTIVE_URGENT_DISPATCHES)).println();
            indentingPrintWriter.print(KEY_MAX_CONSECUTIVE_NORMAL_DISPATCHES, java.lang.Integer.valueOf(this.MAX_CONSECUTIVE_NORMAL_DISPATCHES)).println();
            indentingPrintWriter.print(KEY_CORE_DEFER_UNTIL_ACTIVE, java.lang.Boolean.valueOf(this.CORE_DEFER_UNTIL_ACTIVE)).println();
            indentingPrintWriter.print(KEY_PENDING_COLD_START_CHECK_INTERVAL_MILLIS, java.lang.Long.valueOf(this.PENDING_COLD_START_CHECK_INTERVAL_MILLIS)).println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
    }
}
