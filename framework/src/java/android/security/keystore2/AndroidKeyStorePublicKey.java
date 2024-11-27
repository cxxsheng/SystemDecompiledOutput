package android.security.keystore2;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStorePublicKey extends android.security.keystore2.AndroidKeyStoreKey implements java.security.PublicKey {
    private final byte[] mCertificate;
    private final byte[] mCertificateChain;
    private final byte[] mEncoded;

    abstract android.security.keystore2.AndroidKeyStorePrivateKey getPrivateKey();

    public AndroidKeyStorePublicKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, byte[] bArr, java.lang.String str, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, keyMetadata.key.nspace, keyMetadata.authorizations, str, keyStoreSecurityLevel);
        this.mCertificate = keyMetadata.certificate;
        this.mCertificateChain = keyMetadata.certificateChain;
        this.mEncoded = bArr;
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public java.lang.String getFormat() {
        return "X.509";
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public byte[] getEncoded() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mEncoded);
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey
    public int hashCode() {
        return ((((super.hashCode() + 31) * 31) + java.util.Arrays.hashCode(this.mCertificate)) * 31) + java.util.Arrays.hashCode(this.mCertificateChain);
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        android.security.keystore2.AndroidKeyStorePublicKey androidKeyStorePublicKey = (android.security.keystore2.AndroidKeyStorePublicKey) obj;
        return java.util.Arrays.equals(this.mCertificate, androidKeyStorePublicKey.mCertificate) && java.util.Arrays.equals(this.mCertificateChain, androidKeyStorePublicKey.mCertificateChain);
    }
}
