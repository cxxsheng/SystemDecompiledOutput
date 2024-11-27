package com.android.server.wifi;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes.dex */
public class SupplicantManager {
    private static final java.lang.String TAG = "SupplicantManager";
    private static final java.lang.String WPA_SUPPLICANT_DAEMON_NAME = "wpa_supplicant";

    private SupplicantManager() {
    }

    public static void start() {
        try {
            android.os.SystemService.start(WPA_SUPPLICANT_DAEMON_NAME);
        } catch (java.lang.RuntimeException e) {
            throw new java.util.NoSuchElementException("Failed to start Supplicant");
        }
    }

    public static void stop() {
        try {
            android.os.SystemService.stop(WPA_SUPPLICANT_DAEMON_NAME);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w(TAG, "Failed to stop Supplicant", e);
        }
    }
}
