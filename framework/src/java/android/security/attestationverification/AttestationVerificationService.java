package android.security.attestationverification;

/* loaded from: classes3.dex */
public abstract class AttestationVerificationService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.security.attestationverification.AttestationVerificationService";

    public abstract int onVerifyPeerDeviceAttestation(android.os.Bundle bundle, byte[] bArr);
}
