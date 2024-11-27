package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class HidlToAidlSessionAdapter implements android.hardware.biometrics.fingerprint.ISession {

    @com.android.internal.annotations.VisibleForTesting
    static final int ENROLL_TIMEOUT_SEC = 60;
    private final java.lang.String TAG = "HidlToAidlSessionAdapter";
    private com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlCallbackConverter mHidlToAidlCallbackConverter;

    @android.annotation.NonNull
    private final java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> mSession;
    private final int mUserId;

    public HidlToAidlSessionAdapter(java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, int i, com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler aidlResponseHandler) {
        this.mSession = supplier;
        this.mUserId = i;
        setCallback(aidlResponseHandler);
    }

    public android.os.IBinder asBinder() {
        return null;
    }

    public void generateChallenge() throws android.os.RemoteException {
        this.mHidlToAidlCallbackConverter.onChallengeGenerated(this.mSession.get().preEnroll());
    }

    public void revokeChallenge(long j) throws android.os.RemoteException {
        this.mSession.get().postEnroll();
        this.mHidlToAidlCallbackConverter.onChallengeRevoked(0L);
    }

    public android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
        this.mSession.get().enroll(com.android.server.biometrics.HardwareAuthTokenUtils.toByteArray(hardwareAuthToken), this.mUserId, 60);
        return new com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter.Cancellation();
    }

    public android.hardware.biometrics.common.ICancellationSignal authenticate(long j) throws android.os.RemoteException {
        this.mSession.get().authenticate(j, this.mUserId);
        return new com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter.Cancellation();
    }

    public android.hardware.biometrics.common.ICancellationSignal detectInteraction() throws android.os.RemoteException {
        this.mSession.get().authenticate(0L, this.mUserId);
        return new com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter.Cancellation();
    }

    public void enumerateEnrollments() throws android.os.RemoteException {
        this.mSession.get().enumerate();
    }

    public void removeEnrollments(int[] iArr) throws android.os.RemoteException {
        if (iArr.length > 1) {
            this.mSession.get().remove(this.mUserId, 0);
        } else {
            this.mSession.get().remove(this.mUserId, iArr[0]);
        }
    }

    public void onPointerDown(int i, int i2, int i3, float f, float f2) throws android.os.RemoteException {
        com.android.server.biometrics.sensors.fingerprint.UdfpsHelper.onFingerDown(this.mSession.get(), i2, i3, f, f2);
    }

    public void onPointerUp(int i) throws android.os.RemoteException {
        com.android.server.biometrics.sensors.fingerprint.UdfpsHelper.onFingerUp(this.mSession.get());
    }

    public void getAuthenticatorId() throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "getAuthenticatorId unsupported in HIDL");
        this.mHidlToAidlCallbackConverter.unsupportedClientScheduled(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGetAuthenticatorIdClient.class);
    }

    public void invalidateAuthenticatorId() throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "invalidateAuthenticatorId unsupported in HIDL");
        this.mHidlToAidlCallbackConverter.unsupportedClientScheduled(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInvalidationClient.class);
    }

    public void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
        this.mHidlToAidlCallbackConverter.onResetLockout();
    }

    public void close() throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "close unsupported in HIDL");
    }

    public void onUiReady() throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "onUiReady unsupported in HIDL");
    }

    public android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "authenticateWithContext unsupported in HIDL");
        return authenticate(j);
    }

    public android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "enrollWithContext unsupported in HIDL");
        return enroll(hardwareAuthToken);
    }

    public android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "enrollWithContext unsupported in HIDL");
        return detectInteraction();
    }

    public void onPointerDownWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "onPointerDownWithContext unsupported in HIDL");
        onPointerDown(pointerContext.pointerId, (int) pointerContext.x, (int) pointerContext.y, pointerContext.minor, pointerContext.major);
    }

    public void onPointerUpWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "onPointerUpWithContext unsupported in HIDL");
        onPointerUp(pointerContext.pointerId);
    }

    public void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "onContextChanged unsupported in HIDL");
    }

    public void onPointerCancelWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "onPointerCancelWithContext unsupported in HIDL");
    }

    public void setIgnoreDisplayTouches(boolean z) throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "setIgnoreDisplayTouches unsupported in HIDL");
    }

    public int getInterfaceVersion() throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "getInterfaceVersion unsupported in HIDL");
        return 0;
    }

    public java.lang.String getInterfaceHash() throws android.os.RemoteException {
        android.util.Log.e("HidlToAidlSessionAdapter", "getInterfaceHash unsupported in HIDL");
        return null;
    }

    public long getAuthenticatorIdForUpdateClient() throws android.os.RemoteException {
        return this.mSession.get().getAuthenticatorId();
    }

    public void setActiveGroup(int i, java.lang.String str) throws android.os.RemoteException {
        this.mSession.get().setActiveGroup(i, str);
    }

    private void setCallback(com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler aidlResponseHandler) {
        this.mHidlToAidlCallbackConverter = new com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlCallbackConverter(aidlResponseHandler);
        try {
            if (this.mSession.get() == null) {
                android.util.Slog.e("HidlToAidlSessionAdapter", "Unable to set HIDL callback. HIDL daemon is null.");
                return;
            }
            long notify = this.mSession.get().setNotify(this.mHidlToAidlCallbackConverter);
            android.util.Slog.d("HidlToAidlSessionAdapter", "Fingerprint HAL ready, HAL ID: " + notify);
            if (notify == 0) {
                android.util.Slog.d("HidlToAidlSessionAdapter", "Unable to set HIDL callback.");
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.d("HidlToAidlSessionAdapter", "Failed to set callback");
        }
    }

    private class Cancellation extends android.hardware.biometrics.common.ICancellationSignal.Stub {
        Cancellation() {
        }

        public void cancel() throws android.os.RemoteException {
            try {
                ((android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint) com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter.this.mSession.get()).cancel();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e("HidlToAidlSessionAdapter", "Remote exception when requesting cancel", e);
            }
        }

        public int getInterfaceVersion() throws android.os.RemoteException {
            return 0;
        }

        public java.lang.String getInterfaceHash() throws android.os.RemoteException {
            return null;
        }
    }
}
