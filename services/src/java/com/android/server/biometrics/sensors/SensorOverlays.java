package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public final class SensorOverlays {
    private static final java.lang.String TAG = "SensorOverlays";

    @android.annotation.NonNull
    private final java.util.Optional<android.hardware.fingerprint.ISidefpsController> mSidefpsController;

    @android.annotation.NonNull
    private final java.util.Optional<android.hardware.fingerprint.IUdfpsOverlayController> mUdfpsOverlayController;

    @java.lang.FunctionalInterface
    public interface OverlayControllerConsumer<T> {
        void accept(T t) throws android.os.RemoteException;
    }

    public SensorOverlays(@android.annotation.Nullable android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController, @android.annotation.Nullable android.hardware.fingerprint.ISidefpsController iSidefpsController) {
        this.mUdfpsOverlayController = java.util.Optional.ofNullable(iUdfpsOverlayController);
        this.mSidefpsController = java.util.Optional.ofNullable(iSidefpsController);
    }

    public SensorOverlays(@android.annotation.Nullable android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) {
        this.mUdfpsOverlayController = java.util.Optional.ofNullable(iUdfpsOverlayController);
        this.mSidefpsController = java.util.Optional.empty();
    }

    public void show(int i, int i2, @android.annotation.NonNull final com.android.server.biometrics.sensors.AcquisitionClient<?> acquisitionClient) {
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        if (this.mSidefpsController.isPresent()) {
            try {
                this.mSidefpsController.get().show(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception when showing the side-fps overlay", e);
            }
        }
        if (this.mUdfpsOverlayController.isPresent()) {
            try {
                this.mUdfpsOverlayController.get().showUdfpsOverlay(acquisitionClient.getRequestId(), i, i2, new android.hardware.fingerprint.IUdfpsOverlayControllerCallback.Stub() { // from class: com.android.server.biometrics.sensors.SensorOverlays.1
                    public void onUserCanceled() {
                        acquisitionClient.onUserCanceled();
                    }
                });
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "Remote exception when showing the UDFPS overlay", e2);
            }
        }
    }

    public void hide(int i) {
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        if (this.mSidefpsController.isPresent()) {
            try {
                this.mSidefpsController.get().hide(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception when hiding the side-fps overlay", e);
            }
        }
        if (this.mUdfpsOverlayController.isPresent()) {
            try {
                this.mUdfpsOverlayController.get().hideUdfpsOverlay(i);
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "Remote exception when hiding the UDFPS overlay", e2);
            }
        }
    }

    public void ifUdfps(com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer<android.hardware.fingerprint.IUdfpsOverlayController> overlayControllerConsumer) {
        if (this.mUdfpsOverlayController.isPresent()) {
            try {
                overlayControllerConsumer.accept(this.mUdfpsOverlayController.get());
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception using overlay controller", e);
            }
        }
    }
}
