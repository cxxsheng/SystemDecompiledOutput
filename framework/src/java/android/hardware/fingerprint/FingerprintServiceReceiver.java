package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public class FingerprintServiceReceiver extends android.hardware.fingerprint.IFingerprintServiceReceiver.Stub {
    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onEnrollResult(android.hardware.fingerprint.Fingerprint fingerprint, int i) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAcquired(int i, int i2) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAuthenticationSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i, boolean z) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onFingerprintDetected(int i, int i2, boolean z) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAuthenticationFailed() throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onError(int i, int i2) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onRemoved(android.hardware.fingerprint.Fingerprint fingerprint, int i) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsPointerDown(int i) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsPointerUp(int i) throws android.os.RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsOverlayShown() throws android.os.RemoteException {
    }
}
