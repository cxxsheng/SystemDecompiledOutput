package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class ClientMonitorCompositeCallback implements com.android.server.biometrics.sensors.ClientMonitorCallback {

    @android.annotation.NonNull
    private final java.util.List<com.android.server.biometrics.sensors.ClientMonitorCallback> mCallbacks = new java.util.ArrayList();

    public ClientMonitorCompositeCallback(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback... clientMonitorCallbackArr) {
        for (com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback : clientMonitorCallbackArr) {
            if (clientMonitorCallback != null) {
                this.mCallbacks.add(clientMonitorCallback);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).onClientStarted(baseClientMonitor);
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onBiometricAction(int i) {
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            this.mCallbacks.get(i2).onBiometricAction(i);
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
        for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
            this.mCallbacks.get(size).onClientFinished(baseClientMonitor, z);
        }
    }
}
