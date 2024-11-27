package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public interface CameraCaptureSessionCore {
    void closeWithoutDraining();

    android.hardware.camera2.impl.CameraDeviceImpl.StateCallbackKK getDeviceStateCallback();

    boolean isAborting();

    void replaceSessionClose();
}
