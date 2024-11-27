package android.security.identity;

/* loaded from: classes3.dex */
public abstract class WritableIdentityCredential {
    public abstract java.util.Collection<java.security.cert.X509Certificate> getCredentialKeyCertificateChain(byte[] bArr);

    public abstract byte[] personalize(android.security.identity.PersonalizationData personalizationData);

    protected WritableIdentityCredential() {
    }
}
