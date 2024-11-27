package com.android.server.companion;

/* loaded from: classes.dex */
public class CompanionDeviceConfig {
    public static final java.lang.String ENABLE_CONTEXT_SYNC_TELECOM = "enable_context_sync_telecom";
    private static final java.lang.String NAMESPACE_COMPANION = "companion";

    public static boolean isEnabled(java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getBoolean(NAMESPACE_COMPANION, str, false);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean isEnabled(java.lang.String str, boolean z) {
        return android.provider.DeviceConfig.getBoolean(NAMESPACE_COMPANION, str, z);
    }
}
