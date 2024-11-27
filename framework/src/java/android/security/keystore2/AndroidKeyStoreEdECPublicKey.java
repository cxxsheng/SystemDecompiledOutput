package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreEdECPublicKey extends android.security.keystore2.AndroidKeyStorePublicKey implements java.security.interfaces.EdECPublicKey {
    private static final byte[] DER_KEY_PREFIX = {48, 42, 48, 5, 6, 3, 43, 101, 112, 3, 33, 0};
    private static final int ED25519_KEY_SIZE_BYTES = 32;
    private byte[] mEncodedKey;
    private java.security.spec.EdECPoint mPoint;

    public AndroidKeyStoreEdECPublicKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, java.lang.String str, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, byte[] bArr) {
        super(keyDescriptor, keyMetadata, bArr, str, keyStoreSecurityLevel);
        this.mEncodedKey = bArr;
        int matchesPreamble = matchesPreamble(DER_KEY_PREFIX, bArr);
        if (matchesPreamble == 0) {
            throw new java.lang.IllegalArgumentException("Key size is not correct size");
        }
        this.mPoint = pointFromKeyByteArray(java.util.Arrays.copyOfRange(bArr, matchesPreamble, bArr.length));
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    android.security.keystore2.AndroidKeyStorePrivateKey getPrivateKey() {
        return new android.security.keystore2.AndroidKeyStoreEdECPrivateKey(getUserKeyDescriptor(), getKeyIdDescriptor().nspace, getAuthorizations(), "EdDSA", getSecurityLevel());
    }

    @Override // java.security.interfaces.EdECKey
    public java.security.spec.NamedParameterSpec getParams() {
        return java.security.spec.NamedParameterSpec.ED25519;
    }

    @Override // java.security.interfaces.EdECPublicKey
    public java.security.spec.EdECPoint getPoint() {
        return this.mPoint;
    }

    private static int matchesPreamble(byte[] bArr, byte[] bArr2) {
        if (bArr2.length == bArr.length + 32 && java.util.Arrays.compare(bArr, java.util.Arrays.copyOf(bArr2, bArr.length)) == 0) {
            return bArr.length;
        }
        return 0;
    }

    private static java.security.spec.EdECPoint pointFromKeyByteArray(byte[] bArr) {
        java.util.Objects.requireNonNull(bArr);
        boolean z = (bArr[bArr.length - 1] & 128) != 0;
        int length = bArr.length - 1;
        bArr[length] = (byte) (bArr[length] & Byte.MAX_VALUE);
        reverse(bArr);
        return new java.security.spec.EdECPoint(z, new java.math.BigInteger(1, bArr));
    }

    private static void reverse(byte[] bArr) {
        int i = 0;
        for (int length = bArr.length - 1; i < length; length--) {
            byte b = bArr[i];
            bArr[i] = bArr[length];
            bArr[length] = b;
            i++;
        }
    }

    @Override // android.security.keystore2.AndroidKeyStorePublicKey, android.security.keystore2.AndroidKeyStoreKey, java.security.Key
    public byte[] getEncoded() {
        return (byte[]) this.mEncodedKey.clone();
    }
}
