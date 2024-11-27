package android.security.identity;

/* loaded from: classes3.dex */
public abstract class IdentityCredential {
    @java.lang.Deprecated
    public abstract java.security.KeyPair createEphemeralKeyPair();

    @java.lang.Deprecated
    public abstract byte[] decryptMessageFromReader(byte[] bArr) throws android.security.identity.MessageDecryptionException;

    @java.lang.Deprecated
    public abstract byte[] encryptMessageToReader(byte[] bArr);

    public abstract java.util.Collection<java.security.cert.X509Certificate> getAuthKeysNeedingCertification();

    @java.lang.Deprecated
    public abstract int[] getAuthenticationDataUsageCount();

    public abstract java.util.Collection<java.security.cert.X509Certificate> getCredentialKeyCertificateChain();

    public abstract long getCredstoreOperationHandle();

    @java.lang.Deprecated
    public abstract android.security.identity.ResultData getEntries(byte[] bArr, java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> map, byte[] bArr2, byte[] bArr3) throws android.security.identity.SessionTranscriptMismatchException, android.security.identity.NoAuthenticationKeyAvailableException, android.security.identity.InvalidReaderSignatureException, android.security.identity.EphemeralPublicKeyNotFoundException, android.security.identity.InvalidRequestMessageException;

    @java.lang.Deprecated
    public abstract void setAllowUsingExhaustedKeys(boolean z);

    @java.lang.Deprecated
    public abstract void setAvailableAuthenticationKeys(int i, int i2);

    @java.lang.Deprecated
    public abstract void setReaderEphemeralPublicKey(java.security.PublicKey publicKey) throws java.security.InvalidKeyException;

    @java.lang.Deprecated
    public abstract void storeStaticAuthenticationData(java.security.cert.X509Certificate x509Certificate, byte[] bArr) throws android.security.identity.UnknownAuthenticationKeyException;

    protected IdentityCredential() {
    }

    @java.lang.Deprecated
    public void setAllowUsingExpiredKeys(boolean z) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void setIncrementKeyUsageCount(boolean z) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void storeStaticAuthenticationData(java.security.cert.X509Certificate x509Certificate, java.time.Instant instant, byte[] bArr) throws android.security.identity.UnknownAuthenticationKeyException {
        throw new java.lang.UnsupportedOperationException();
    }

    public byte[] proveOwnership(byte[] bArr) {
        throw new java.lang.UnsupportedOperationException();
    }

    public byte[] delete(byte[] bArr) {
        throw new java.lang.UnsupportedOperationException();
    }

    public byte[] update(android.security.identity.PersonalizationData personalizationData) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void setAvailableAuthenticationKeys(int i, int i2, long j) {
        throw new java.lang.UnsupportedOperationException();
    }

    public java.util.List<android.security.identity.AuthenticationKeyMetadata> getAuthenticationKeyMetadata() {
        throw new java.lang.UnsupportedOperationException();
    }
}
