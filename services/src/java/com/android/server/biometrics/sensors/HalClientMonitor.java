package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class HalClientMonitor<T> extends com.android.server.biometrics.sensors.BaseClientMonitor {

    @android.annotation.NonNull
    protected final java.util.function.Supplier<T> mLazyDaemon;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.OperationContextExt mOperationContext;

    protected abstract void startHalOperation();

    public abstract void unableToStart();

    public HalClientMonitor(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, iBinder, clientMonitorCallbackConverter, i, str, i2, i3, biometricLogger, biometricContext);
        this.mLazyDaemon = supplier;
        this.mOperationContext = new com.android.server.biometrics.log.OperationContextExt(isBiometricPrompt(), clientMonitorCallbackConverter != null ? clientMonitorCallbackConverter.getModality() : 0);
    }

    @android.annotation.Nullable
    public T getFreshDaemon() {
        return this.mLazyDaemon.get();
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void destroy() {
        super.destroy();
        unsubscribeBiometricContext();
    }

    public boolean isBiometricPrompt() {
        return getCookie() != 0;
    }

    protected com.android.server.biometrics.log.OperationContextExt getOperationContext() {
        return getBiometricContext().updateContext(this.mOperationContext, isCryptoOperation());
    }

    protected com.android.server.biometrics.sensors.ClientMonitorCallback getBiometricContextUnsubscriber() {
        return new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.HalClientMonitor.1
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                com.android.server.biometrics.sensors.HalClientMonitor.this.unsubscribeBiometricContext();
            }
        };
    }

    protected void unsubscribeBiometricContext() {
        getBiometricContext().unsubscribe(this.mOperationContext);
    }
}
