package com.android.server.health;

/* loaded from: classes2.dex */
class HealthHalCallbackHidl extends android.hardware.health.V2_1.IHealthInfoCallback.Stub implements com.android.server.health.HealthServiceWrapperHidl.Callback {
    private static final java.lang.String TAG = com.android.server.health.HealthHalCallbackHidl.class.getSimpleName();
    private com.android.server.health.HealthInfoCallback mCallback;

    private static void traceBegin(java.lang.String str) {
        android.os.Trace.traceBegin(524288L, str);
    }

    private static void traceEnd() {
        android.os.Trace.traceEnd(524288L);
    }

    HealthHalCallbackHidl(@android.annotation.NonNull com.android.server.health.HealthInfoCallback healthInfoCallback) {
        this.mCallback = healthInfoCallback;
    }

    @Override // android.hardware.health.V2_0.IHealthInfoCallback
    public void healthInfoChanged(android.hardware.health.V2_0.HealthInfo healthInfo) {
        android.hardware.health.V2_1.HealthInfo healthInfo2 = new android.hardware.health.V2_1.HealthInfo();
        healthInfo2.legacy = healthInfo;
        healthInfo2.batteryCapacityLevel = -1;
        healthInfo2.batteryChargeTimeToFullNowSeconds = -1L;
        this.mCallback.update(android.hardware.health.Translate.h2aTranslate(healthInfo2));
    }

    @Override // android.hardware.health.V2_1.IHealthInfoCallback
    public void healthInfoChanged_2_1(android.hardware.health.V2_1.HealthInfo healthInfo) {
        this.mCallback.update(android.hardware.health.Translate.h2aTranslate(healthInfo));
    }

    @Override // com.android.server.health.HealthServiceWrapperHidl.Callback
    public void onRegistration(android.hardware.health.V2_0.IHealth iHealth, android.hardware.health.V2_0.IHealth iHealth2, java.lang.String str) {
        int registerCallback;
        if (iHealth2 == null) {
            return;
        }
        traceBegin("HealthUnregisterCallback");
        if (iHealth != null) {
            try {
                try {
                    int unregisterCallback = iHealth.unregisterCallback(this);
                    if (unregisterCallback != 0) {
                        android.util.Slog.w(TAG, "health: cannot unregister previous callback: " + android.hardware.health.V2_0.Result.toString(unregisterCallback));
                    }
                } finally {
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "health: cannot unregister previous callback (transaction error): " + e.getMessage());
            }
        }
        traceEnd();
        traceBegin("HealthRegisterCallback");
        try {
            try {
                registerCallback = iHealth2.registerCallback(this);
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "health: cannot register callback (transaction error): " + e2.getMessage());
            }
            if (registerCallback == 0) {
                iHealth2.update();
                return;
            }
            android.util.Slog.w(TAG, "health: cannot register callback: " + android.hardware.health.V2_0.Result.toString(registerCallback));
        } finally {
        }
    }
}
