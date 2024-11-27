package com.android.server.health;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public class HealthRegCallbackAidl {
    private static final java.lang.String TAG = "HealthRegCallbackAidl";
    private final android.hardware.health.IHealthInfoCallback mHalInfoCallback = new com.android.server.health.HealthRegCallbackAidl.HalInfoCallback();
    private final com.android.server.health.HealthInfoCallback mServiceInfoCallback;

    HealthRegCallbackAidl(@android.annotation.Nullable com.android.server.health.HealthInfoCallback healthInfoCallback) {
        this.mServiceInfoCallback = healthInfoCallback;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public void onRegistration(@android.annotation.Nullable android.hardware.health.IHealth iHealth, @android.annotation.NonNull android.hardware.health.IHealth iHealth2) {
        if (this.mServiceInfoCallback == null) {
            return;
        }
        android.os.Trace.traceBegin(524288L, "HealthUnregisterCallbackAidl");
        try {
            unregisterCallback(iHealth, this.mHalInfoCallback);
            android.os.Trace.traceEnd(524288L);
            android.os.Trace.traceBegin(524288L, "HealthRegisterCallbackAidl");
            try {
                registerCallback(iHealth2, this.mHalInfoCallback);
            } finally {
            }
        } finally {
        }
    }

    private static void unregisterCallback(@android.annotation.Nullable android.hardware.health.IHealth iHealth, android.hardware.health.IHealthInfoCallback iHealthInfoCallback) {
        if (iHealth == null) {
            return;
        }
        try {
            iHealth.unregisterCallback(iHealthInfoCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "health: cannot unregister previous callback (transaction error): " + e.getMessage());
        }
    }

    private static void registerCallback(@android.annotation.NonNull android.hardware.health.IHealth iHealth, android.hardware.health.IHealthInfoCallback iHealthInfoCallback) {
        try {
            iHealth.registerCallback(iHealthInfoCallback);
            try {
                iHealth.update();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "health: cannot update after registering health info callback", e);
            }
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "health: cannot register callback, framework may cease to receive updates on health / battery info!", e2);
        }
    }

    private class HalInfoCallback extends android.hardware.health.IHealthInfoCallback.Stub {
        private HalInfoCallback() {
        }

        @Override // android.hardware.health.IHealthInfoCallback
        public void healthInfoChanged(android.hardware.health.HealthInfo healthInfo) throws android.os.RemoteException {
            com.android.server.health.HealthRegCallbackAidl.this.mServiceInfoCallback.update(healthInfo);
        }

        @Override // android.hardware.health.IHealthInfoCallback
        public java.lang.String getInterfaceHash() {
            return "3bab6273a5491102b29c9d7a1f0efa749533f46d";
        }

        @Override // android.hardware.health.IHealthInfoCallback
        public int getInterfaceVersion() {
            return 3;
        }
    }
}
