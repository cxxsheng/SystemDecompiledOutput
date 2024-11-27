package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class BiometricNotificationImpl implements com.android.server.biometrics.sensors.BiometricNotification {
    @Override // com.android.server.biometrics.sensors.BiometricNotification
    public void sendFaceEnrollNotification(@android.annotation.NonNull android.content.Context context) {
        com.android.server.biometrics.sensors.BiometricNotificationUtils.showFaceEnrollNotification(context);
    }

    @Override // com.android.server.biometrics.sensors.BiometricNotification
    public void sendFpEnrollNotification(@android.annotation.NonNull android.content.Context context) {
        com.android.server.biometrics.sensors.BiometricNotificationUtils.showFingerprintEnrollNotification(context);
    }
}
