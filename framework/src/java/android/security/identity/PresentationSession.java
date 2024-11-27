package android.security.identity;

/* loaded from: classes3.dex */
public abstract class PresentationSession {
    public abstract android.security.identity.CredentialDataResult getCredentialData(java.lang.String str, android.security.identity.CredentialDataRequest credentialDataRequest) throws android.security.identity.NoAuthenticationKeyAvailableException, android.security.identity.InvalidReaderSignatureException, android.security.identity.InvalidRequestMessageException, android.security.identity.EphemeralPublicKeyNotFoundException;

    public abstract long getCredstoreOperationHandle();

    public abstract java.security.KeyPair getEphemeralKeyPair();

    public abstract void setReaderEphemeralPublicKey(java.security.PublicKey publicKey) throws java.security.InvalidKeyException;

    public abstract void setSessionTranscript(byte[] bArr);

    protected PresentationSession() {
    }
}
