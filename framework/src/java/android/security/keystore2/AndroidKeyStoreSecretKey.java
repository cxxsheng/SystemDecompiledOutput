package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreSecretKey extends android.security.keystore2.AndroidKeyStoreKey implements javax.crypto.SecretKey {
    public AndroidKeyStoreSecretKey(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyMetadata keyMetadata, java.lang.String str, android.security.KeyStoreSecurityLevel keyStoreSecurityLevel) {
        super(keyDescriptor, keyMetadata.key.nspace, keyMetadata.authorizations, str, keyStoreSecurityLevel);
    }
}
