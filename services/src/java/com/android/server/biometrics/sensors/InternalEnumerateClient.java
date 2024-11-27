package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class InternalEnumerateClient<T> extends com.android.server.biometrics.sensors.HalClientMonitor<T> implements com.android.server.biometrics.sensors.EnumerateConsumer {
    private static final java.lang.String TAG = "Biometrics/InternalEnumerateClient";
    private java.util.List<? extends android.hardware.biometrics.BiometricAuthenticator.Identifier> mEnrolledList;
    private java.util.List<android.hardware.biometrics.BiometricAuthenticator.Identifier> mUnknownHALTemplates;
    private com.android.server.biometrics.sensors.BiometricUtils mUtils;

    protected InternalEnumerateClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<? extends android.hardware.biometrics.BiometricAuthenticator.Identifier> list, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, supplier, iBinder, null, i, str, 0, i2, biometricLogger, biometricContext);
        this.mUnknownHALTemplates = new java.util.ArrayList();
        this.mEnrolledList = list;
        this.mUtils = biometricUtils;
    }

    @Override // com.android.server.biometrics.sensors.EnumerateConsumer
    public void onEnumerationResult(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i) {
        handleEnumeratedTemplate(identifier);
        if (i == 0) {
            doTemplateCleanup();
            this.mCallback.onClientFinished(this, true);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    private void handleEnumeratedTemplate(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier) {
        if (identifier == null) {
            android.util.Slog.d(TAG, "Null identifier");
            return;
        }
        android.util.Slog.v(TAG, "handleEnumeratedTemplate: " + identifier.getBiometricId());
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= this.mEnrolledList.size()) {
                break;
            }
            if (this.mEnrolledList.get(i).getBiometricId() != identifier.getBiometricId()) {
                i++;
            } else {
                this.mEnrolledList.remove(i);
                z = true;
                break;
            }
        }
        if (!z && identifier.getBiometricId() != 0) {
            this.mUnknownHALTemplates.add(identifier);
        }
        android.util.Slog.v(TAG, "Matched: " + z);
    }

    private void doTemplateCleanup() {
        if (this.mEnrolledList == null) {
            android.util.Slog.d(TAG, "Null enrolledList");
            return;
        }
        for (int i = 0; i < this.mEnrolledList.size(); i++) {
            android.hardware.biometrics.BiometricAuthenticator.Identifier identifier = this.mEnrolledList.get(i);
            android.util.Slog.e(TAG, "doTemplateCleanup(): Removing dangling template from framework: " + identifier.getBiometricId() + " " + ((java.lang.Object) identifier.getName()));
            this.mUtils.removeBiometricForUser(getContext(), getTargetUserId(), identifier.getBiometricId());
            getLogger().logUnknownEnrollmentInFramework();
        }
        this.mEnrolledList.clear();
    }

    public java.util.List<android.hardware.biometrics.BiometricAuthenticator.Identifier> getUnknownHALTemplates() {
        return this.mUnknownHALTemplates;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 6;
    }
}
