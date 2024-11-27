package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public interface ClientMonitorCallback {
    default void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
    }

    default void onBiometricAction(int i) {
    }

    default void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
    }
}
