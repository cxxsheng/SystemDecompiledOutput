package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreXDHPublicKey extends android.security.keystore2.AndroidKeyStorePublicKey implements java.security.interfaces.XECPublicKey {
    private static final int X25519_KEY_SIZE_BYTES = 32;
    private static final byte[] X509_PREAMBLE = {48, 42, 48, 5, 6, 3, 43, 101, 110, 3, 33, 0};
    private static final byte[] X509_PREAMBLE_WITH_NULL = {48, 44, 48, 7, 6, 3, 43, 101, 110, 5, 0, 3, 33, 0};
    private final byte[] mEncodedKey;
    private final int mPreambleLength;

    public AndroidKeyStoreXDHPublicKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, java.lang.String str, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, byte[] bArr) {
        super(keyDescriptor, keyMetadata, bArr, str, keyStoreSecurityLevel);
        this.mEncodedKey = bArr;
        if (this.mEncodedKey == null) {
            throw new java.lang.IllegalArgumentException("empty encoded key.");
        }
        this.mPreambleLength = matchesPreamble(X509_PREAMBLE, this.mEncodedKey) | matchesPreamble(X509_PREAMBLE_WITH_NULL, this.mEncodedKey);
        if (this.mPreambleLength == 0) {
            throw new java.lang.IllegalArgumentException("Key size is not correct size");
        }
    }

    private static int matchesPreamble(byte[] bArr, byte[] bArr2) {
        if (bArr2.length == bArr.length + 32 && java.util.Arrays.compare(bArr, 0, bArr.length, bArr2, 0, bArr.length) == 0) {
            return bArr.length;
        }
        return 0;
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    android.security.keystore2.AndroidKeyStorePrivateKey getPrivateKey() {
        return new android.security.keystore2.AndroidKeyStoreXDHPrivateKey(getUserKeyDescriptor(), getKeyIdDescriptor().nspace, getAuthorizations(), android.security.keystore.KeyProperties.KEY_ALGORITHM_XDH, getSecurityLevel());
    }

    @Override // java.security.interfaces.XECPublicKey
    public java.math.BigInteger getU() {
        return new java.math.BigInteger(java.util.Arrays.copyOfRange(this.mEncodedKey, this.mPreambleLength, this.mEncodedKey.length));
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey, android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public byte[] getEncoded() {
        return (byte[]) this.mEncodedKey.clone();
    }

    @Override // android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public java.lang.String getAlgorithm() {
        return android.security.keystore.KeyProperties.KEY_ALGORITHM_XDH;
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey, android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public java.lang.String getFormat() {
        return "x.509";
    }

    @Override // java.security.interfaces.XECKey
    public java.security.spec.AlgorithmParameterSpec getParams() {
        return java.security.spec.NamedParameterSpec.X25519;
    }
}
