package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreECPrivateKey extends android.security.keystore2.AndroidKeyStorePrivateKey implements java.security.interfaces.ECKey {
    private final java.security.spec.ECParameterSpec mParams;

    public AndroidKeyStoreECPrivateKey(android.system.keystore2.KeyDescriptor keyDescriptor, long j, android.system.keystore2.Authorization[] authorizationArr, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel, java.security.spec.ECParameterSpec eCParameterSpec) {
        super(keyDescriptor, j, authorizationArr, android.security.keystore.KeyProperties.KEY_ALGORITHM_EC, keyStoreSecurityLevel);
        this.mParams = eCParameterSpec;
    }

    @Override // java.security.interfaces.ECKey
    public java.security.spec.ECParameterSpec getParams() {
        return this.mParams;
    }
}
