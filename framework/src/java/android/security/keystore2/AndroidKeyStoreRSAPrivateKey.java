package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreRSAPrivateKey extends android.security.keystore2.AndroidKeyStorePrivateKey implements java.security.interfaces.RSAKey {
    private final java.math.BigInteger mModulus;

    public AndroidKeyStoreRSAPrivateKey(android.system.keystore2.KeyDescriptor keyDescriptor, long j, android.system.keystore2.Authorization[] authorizationArr, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, java.math.BigInteger bigInteger) {
        super(keyDescriptor, j, authorizationArr, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA, keyStoreSecurityLevel);
        this.mModulus = bigInteger;
    }

    @Override // java.security.interfaces.RSAKey
    public java.math.BigInteger getModulus() {
        return this.mModulus;
    }
}
