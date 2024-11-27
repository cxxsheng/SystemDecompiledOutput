package com.android.server.backup;

/* loaded from: classes.dex */
public class BackupAndRestoreFeatureFlags {
    private static final java.lang.String NAMESPACE = "backup_and_restore";

    private BackupAndRestoreFeatureFlags() {
    }

    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    public static long getBackupTransportFutureTimeoutMillis() {
        return android.provider.DeviceConfig.getLong(NAMESPACE, "backup_transport_future_timeout_millis", 600000L);
    }

    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    public static long getBackupTransportCallbackTimeoutMillis() {
        return android.provider.DeviceConfig.getLong(NAMESPACE, "backup_transport_callback_timeout_millis", com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
    }

    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    public static int getFullBackupWriteToTransportBufferSizeBytes() {
        return android.provider.DeviceConfig.getInt(NAMESPACE, "full_backup_write_to_transport_buffer_size_bytes", 8192);
    }

    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    public static int getFullBackupUtilsRouteBufferSizeBytes() {
        return android.provider.DeviceConfig.getInt(NAMESPACE, "full_backup_utils_route_buffer_size_bytes", 32768);
    }

    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    public static boolean getUnifiedRestoreContinueAfterTransportFailureInKvRestore() {
        return android.provider.DeviceConfig.getBoolean(NAMESPACE, "unified_restore_continue_after_transport_failure_in_kv_restore", true);
    }
}
