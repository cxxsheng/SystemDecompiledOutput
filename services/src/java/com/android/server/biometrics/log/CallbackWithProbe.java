package com.android.server.biometrics.log;

/* loaded from: classes.dex */
public class CallbackWithProbe<T extends com.android.server.biometrics.log.Probe> implements com.android.server.biometrics.sensors.ClientMonitorCallback {
    private final T mProbe;
    private final boolean mStartWithClient;

    public CallbackWithProbe(@android.annotation.NonNull T t, boolean z) {
        this.mProbe = t;
        this.mStartWithClient = z;
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (this.mStartWithClient) {
            this.mProbe.enable();
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
        this.mProbe.destroy();
    }

    @android.annotation.NonNull
    public T getProbe() {
        return this.mProbe;
    }
}
