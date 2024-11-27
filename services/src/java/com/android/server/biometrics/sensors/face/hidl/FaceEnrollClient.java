package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class FaceEnrollClient extends com.android.server.biometrics.sensors.EnrollClient<android.hardware.biometrics.face.V1_0.IBiometricsFace> {
    private static final java.lang.String TAG = "FaceEnrollClient";

    @android.annotation.NonNull
    private final int[] mDisabledFeatures;

    @android.annotation.NonNull
    private final int[] mEnrollIgnoreList;

    @android.annotation.NonNull
    private final int[] mEnrollIgnoreListVendor;

    FaceEnrollClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.lang.String str, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.face.Face> biometricUtils, @android.annotation.NonNull int[] iArr, int i2, @android.annotation.Nullable android.view.Surface surface, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, bArr, str, biometricUtils, i2, i3, false, biometricLogger, biometricContext, android.hardware.biometrics.BiometricFaceConstants.reasonToMetric(faceEnrollOptions.getEnrollReason()));
        setRequestId(j);
        this.mDisabledFeatures = java.util.Arrays.copyOf(iArr, iArr.length);
        this.mEnrollIgnoreList = getContext().getResources().getIntArray(android.R.array.config_ethernet_interfaces);
        this.mEnrollIgnoreListVendor = getContext().getResources().getIntArray(android.R.array.config_face_acquire_keyguard_ignorelist);
        android.util.Slog.w(TAG, "EnrollOptions " + android.hardware.face.FaceEnrollOptions.enrollReasonToString(faceEnrollOptions.getEnrollReason()));
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        com.android.server.biometrics.sensors.BiometricNotificationUtils.cancelFaceEnrollNotification(getContext());
        com.android.server.biometrics.sensors.BiometricNotificationUtils.cancelFaceReEnrollNotification(getContext());
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.ClientMonitorCallback wrapCallbackForStart(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        return new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(getLogger().getAmbientLightProbe(true), clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    protected boolean hasReachedEnrollmentLimit() {
        if (this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).size() >= getContext().getResources().getInteger(android.R.integer.config_externalDisplayPeakRefreshRate)) {
            android.util.Slog.w(TAG, "Too many faces registered, user: " + getTargetUserId());
            return true;
        }
        return false;
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        boolean z;
        if (i == 22) {
            z = !com.android.server.biometrics.Utils.listContains(this.mEnrollIgnoreListVendor, i2);
        } else {
            z = !com.android.server.biometrics.Utils.listContains(this.mEnrollIgnoreList, i);
        }
        onAcquiredInternal(i, i2, z);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        java.util.ArrayList<java.lang.Byte> arrayList = new java.util.ArrayList<>();
        for (byte b : this.mHardwareAuthToken) {
            arrayList.add(java.lang.Byte.valueOf(b));
        }
        java.util.ArrayList<java.lang.Integer> arrayList2 = new java.util.ArrayList<>();
        for (int i : this.mDisabledFeatures) {
            arrayList2.add(java.lang.Integer.valueOf(i));
        }
        try {
            if (getFreshDaemon().enroll(arrayList, this.mTimeoutSec, arrayList2) != 0) {
                onError(2, 0);
                this.mCallback.onClientFinished(this, false);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting enroll", e);
            onError(2, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    protected void stopHalOperation() {
        try {
            getFreshDaemon().cancel();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting cancel", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
