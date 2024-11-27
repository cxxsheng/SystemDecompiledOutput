package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public interface BiometricNotification {
    void sendFaceEnrollNotification(@android.annotation.NonNull android.content.Context context);

    void sendFpEnrollNotification(@android.annotation.NonNull android.content.Context context);
}
