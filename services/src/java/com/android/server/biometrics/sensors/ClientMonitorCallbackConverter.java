package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class ClientMonitorCallbackConverter {
    private final android.hardware.face.IFaceServiceReceiver mFaceServiceReceiver;
    private final android.hardware.fingerprint.IFingerprintServiceReceiver mFingerprintServiceReceiver;
    private final android.hardware.biometrics.IBiometricSensorReceiver mSensorReceiver;

    public ClientMonitorCallbackConverter(android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver) {
        this.mSensorReceiver = iBiometricSensorReceiver;
        this.mFaceServiceReceiver = null;
        this.mFingerprintServiceReceiver = null;
    }

    public ClientMonitorCallbackConverter(android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver) {
        this.mSensorReceiver = null;
        this.mFaceServiceReceiver = iFaceServiceReceiver;
        this.mFingerprintServiceReceiver = null;
    }

    public ClientMonitorCallbackConverter(android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver) {
        this.mSensorReceiver = null;
        this.mFaceServiceReceiver = null;
        this.mFingerprintServiceReceiver = iFingerprintServiceReceiver;
    }

    public int getModality() {
        if (this.mFaceServiceReceiver != null) {
            return 8;
        }
        if (this.mFingerprintServiceReceiver != null) {
            return 2;
        }
        return 0;
    }

    public void onAcquired(int i, int i2, int i3) throws android.os.RemoteException {
        if (this.mSensorReceiver != null) {
            this.mSensorReceiver.onAcquired(i, i2, i3);
        } else if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onAcquired(i2, i3);
        } else if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onAcquired(i2, i3);
        }
    }

    void onAuthenticationSucceeded(int i, android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, byte[] bArr, int i2, boolean z) throws android.os.RemoteException {
        if (this.mSensorReceiver != null) {
            this.mSensorReceiver.onAuthenticationSucceeded(i, bArr);
        } else if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onAuthenticationSucceeded((android.hardware.face.Face) identifier, i2, z);
        } else if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onAuthenticationSucceeded((android.hardware.fingerprint.Fingerprint) identifier, i2, z);
        }
    }

    void onAuthenticationFailed(int i) throws android.os.RemoteException {
        if (this.mSensorReceiver != null) {
            this.mSensorReceiver.onAuthenticationFailed(i);
        } else if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onAuthenticationFailed();
        } else if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onAuthenticationFailed();
        }
    }

    public void onError(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        if (this.mSensorReceiver != null) {
            this.mSensorReceiver.onError(i, i2, i3, i4);
        } else if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onError(i3, i4);
        } else if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onError(i3, i4);
        }
    }

    public void onDetected(int i, int i2, boolean z) throws android.os.RemoteException {
        if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onFaceDetected(i, i2, z);
        } else if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onFingerprintDetected(i, i2, z);
        }
    }

    void onEnrollResult(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i) throws android.os.RemoteException {
        if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onEnrollResult((android.hardware.face.Face) identifier, i);
        } else if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onEnrollResult((android.hardware.fingerprint.Fingerprint) identifier, i);
        }
    }

    public void onRemoved(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i) throws android.os.RemoteException {
        if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onRemoved((android.hardware.face.Face) identifier, i);
        } else if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onRemoved((android.hardware.fingerprint.Fingerprint) identifier, i);
        }
    }

    public void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException {
        if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onChallengeGenerated(i, i2, j);
        } else if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onChallengeGenerated(i, i2, j);
        }
    }

    public void onFeatureSet(boolean z, int i) throws android.os.RemoteException {
        if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onFeatureSet(z, i);
        }
    }

    public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws android.os.RemoteException {
        if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onFeatureGet(z, iArr, zArr);
        }
    }

    public void onUdfpsPointerDown(int i) throws android.os.RemoteException {
        if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onUdfpsPointerDown(i);
        }
    }

    public void onUdfpsPointerUp(int i) throws android.os.RemoteException {
        if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onUdfpsPointerUp(i);
        }
    }

    public void onUdfpsOverlayShown() throws android.os.RemoteException {
        if (this.mFingerprintServiceReceiver != null) {
            this.mFingerprintServiceReceiver.onUdfpsOverlayShown();
        }
    }

    public void onAuthenticationFrame(@android.annotation.NonNull android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) throws android.os.RemoteException {
        if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onAuthenticationFrame(faceAuthenticationFrame);
        }
    }

    public void onEnrollmentFrame(@android.annotation.NonNull android.hardware.face.FaceEnrollFrame faceEnrollFrame) throws android.os.RemoteException {
        if (this.mFaceServiceReceiver != null) {
            this.mFaceServiceReceiver.onEnrollmentFrame(faceEnrollFrame);
        }
    }
}
