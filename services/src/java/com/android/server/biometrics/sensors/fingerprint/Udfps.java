package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface Udfps {
    boolean isPointerDown();

    void onPointerDown(android.hardware.biometrics.fingerprint.PointerContext pointerContext);

    void onPointerUp(android.hardware.biometrics.fingerprint.PointerContext pointerContext);

    void onUdfpsUiEvent(int i);
}
