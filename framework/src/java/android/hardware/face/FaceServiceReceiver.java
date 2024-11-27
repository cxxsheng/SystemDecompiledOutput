package android.hardware.face;

/* loaded from: classes2.dex */
public class FaceServiceReceiver extends android.hardware.face.IFaceServiceReceiver.Stub {
    @Override // android.hardware.face.IFaceServiceReceiver
    public void onEnrollResult(android.hardware.face.Face face, int i) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAcquired(int i, int i2) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationSucceeded(android.hardware.face.Face face, int i, boolean z) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFaceDetected(int i, int i2, boolean z) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationFailed() throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onError(int i, int i2) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onRemoved(android.hardware.face.Face face, int i) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFeatureSet(boolean z, int i) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onChallengeGenerated(int i, int i2, long j) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationFrame(android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) throws android.os.RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onEnrollmentFrame(android.hardware.face.FaceEnrollFrame faceEnrollFrame) throws android.os.RemoteException {
    }
}
