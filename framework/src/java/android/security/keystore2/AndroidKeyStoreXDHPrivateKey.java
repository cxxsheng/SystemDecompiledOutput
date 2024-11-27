package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreXDHPrivateKey extends android.security.keystore2.AndroidKeyStorePrivateKey implements java.security.interfaces.XECPrivateKey {
    public AndroidKeyStoreXDHPrivateKey(android.system.keystore2.KeyDescriptor keyDescriptor, long j, android.system.keystore2.Authorization[] authorizationArr, java.lang.String str, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, j, authorizationArr, str, keyStoreSecurityLevel);
    }

    @Override // java.security.interfaces.XECKey
    public java.security.spec.NamedParameterSpec getParams() {
        return java.security.spec.NamedParameterSpec.X25519;
    }

    @Override // java.security.interfaces.XECPrivateKey
    public java.util.Optional<byte[]> getScalar() {
        return java.util.Optional.empty();
    }
}
