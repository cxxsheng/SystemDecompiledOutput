package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreEdECPrivateKey extends android.security.keystore2.AndroidKeyStorePrivateKey implements java.security.interfaces.EdECKey {
    public AndroidKeyStoreEdECPrivateKey(android.system.keystore2.KeyDescriptor keyDescriptor, long j, android.system.keystore2.Authorization[] authorizationArr, java.lang.String str, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, j, authorizationArr, str, keyStoreSecurityLevel);
    }

    @Override // java.security.interfaces.EdECKey
    public java.security.spec.NamedParameterSpec getParams() {
        return java.security.spec.NamedParameterSpec.ED25519;
    }
}
