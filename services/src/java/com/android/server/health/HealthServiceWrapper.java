package com.android.server.health;

/* loaded from: classes2.dex */
public abstract class HealthServiceWrapper {
    @com.android.internal.annotations.VisibleForTesting
    abstract android.os.HandlerThread getHandlerThread();

    public abstract android.hardware.health.HealthInfo getHealthInfo() throws android.os.RemoteException;

    public abstract int getProperty(int i, android.os.BatteryProperty batteryProperty) throws android.os.RemoteException;

    public abstract void scheduleUpdate() throws android.os.RemoteException;

    public static com.android.server.health.HealthServiceWrapper create(@android.annotation.Nullable com.android.server.health.HealthInfoCallback healthInfoCallback) throws android.os.RemoteException, java.util.NoSuchElementException {
        return create(healthInfoCallback == null ? null : new com.android.server.health.HealthRegCallbackAidl(healthInfoCallback), new com.android.server.health.HealthServiceWrapperAidl.ServiceManagerStub() { // from class: com.android.server.health.HealthServiceWrapper.1
        }, healthInfoCallback != null ? new com.android.server.health.HealthHalCallbackHidl(healthInfoCallback) : null, new com.android.server.health.HealthServiceWrapperHidl.IServiceManagerSupplier() { // from class: com.android.server.health.HealthServiceWrapper.2
        }, new com.android.server.health.HealthServiceWrapperHidl.IHealthSupplier() { // from class: com.android.server.health.HealthServiceWrapper.3
        });
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    static com.android.server.health.HealthServiceWrapper create(@android.annotation.Nullable com.android.server.health.HealthRegCallbackAidl healthRegCallbackAidl, @android.annotation.NonNull com.android.server.health.HealthServiceWrapperAidl.ServiceManagerStub serviceManagerStub, @android.annotation.Nullable com.android.server.health.HealthServiceWrapperHidl.Callback callback, @android.annotation.NonNull com.android.server.health.HealthServiceWrapperHidl.IServiceManagerSupplier iServiceManagerSupplier, @android.annotation.NonNull com.android.server.health.HealthServiceWrapperHidl.IHealthSupplier iHealthSupplier) throws android.os.RemoteException, java.util.NoSuchElementException {
        try {
            return new com.android.server.health.HealthServiceWrapperAidl(healthRegCallbackAidl, serviceManagerStub);
        } catch (java.util.NoSuchElementException e) {
            return new com.android.server.health.HealthServiceWrapperHidl(callback, iServiceManagerSupplier, iHealthSupplier);
        }
    }
}
