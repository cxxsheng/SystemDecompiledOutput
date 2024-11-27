package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public class UdfpsHelper {
    private static final java.lang.String TAG = "UdfpsHelper";

    public static void onFingerDown(android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint iBiometricsFingerprint, int i, int i2, float f, float f2) {
        android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint castFrom = android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint.castFrom((android.os.IHwInterface) iBiometricsFingerprint);
        if (castFrom == null) {
            android.util.Slog.v(TAG, "onFingerDown | failed to cast the HIDL to V2_3");
            return;
        }
        try {
            castFrom.onFingerDown(i, i2, f, f2);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "onFingerDown | RemoteException: ", e);
        }
    }

    public static void onFingerUp(android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint iBiometricsFingerprint) {
        android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint castFrom = android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint.castFrom((android.os.IHwInterface) iBiometricsFingerprint);
        if (castFrom == null) {
            android.util.Slog.v(TAG, "onFingerUp | failed to cast the HIDL to V2_3");
            return;
        }
        try {
            castFrom.onFingerUp();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "onFingerUp | RemoteException: ", e);
        }
    }

    public static boolean isValidAcquisitionMessage(@android.annotation.NonNull android.content.Context context, int i, int i2) {
        return android.hardware.fingerprint.FingerprintManager.getAcquiredString(context, i, i2) != null;
    }
}
