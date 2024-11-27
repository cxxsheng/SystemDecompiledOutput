package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class DeviceConfigWrapper {
    private static final java.lang.String TAG = "DeviceConfigWrapper";

    boolean getBoolean(java.lang.String str, boolean z) {
        return android.provider.DeviceConfig.getBoolean("hdmi_control", str, z);
    }

    void addOnPropertiesChangedListener(java.util.concurrent.Executor executor, android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("hdmi_control", executor, onPropertiesChangedListener);
    }
}
